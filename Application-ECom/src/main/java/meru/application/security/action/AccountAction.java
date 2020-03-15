package meru.application.security.action;

import java.io.IOException;
import java.io.PrintWriter;

import app.domain.security.NewUser;
import app.domain.security.Role;
import app.domain.security.User;
import app.erp.mdm.bp.Customer;
import meru.app.AppContext;
import meru.app.binding.http.HttpAppRequest;
import meru.app.engine.AppEngine;
import meru.app.service.ServiceManager;
import meru.application.AppConstants;
import meru.application.AppType;
import meru.application.security.Password;
import meru.application.security.SecurityAppProperty;
import meru.comm.mobile.http.HttpSMSSender;
import meru.exception.AppException;
import meru.persistence.EntityQuery;
import meru.sys.AppHelper;
import meru.sys.IOSystem;
import meru.sys.SystemCalendar;
import meru.sys.lang.StringHelper;
import meru.sys.uid.key.RandomIntegerKeyGenerator;
import meru.template.TemplateData;
import meru.template.TemplateEngine;

public abstract class AccountAction {

  protected AppContext appContext;
  protected AppEngine appEngine;
  private HttpSMSSender smsSender;
  protected RandomIntegerKeyGenerator randomIntegerKeyGenerator;
  protected SystemCalendar systemCalendar;

  public AccountAction() {
    randomIntegerKeyGenerator = new RandomIntegerKeyGenerator((byte) 4);
    systemCalendar = SystemCalendar.getInstance();
  }

  public void setContext(AppContext appContext,
                         AppEngine appEngine,
                         ServiceManager serviceManager) {
    this.appContext = appContext;
    this.appEngine = appEngine;
    smsSender = serviceManager.getService(ServiceManager.SMS_SENDER,
                                          HttpSMSSender.class);
    init();
  }

  protected void init() {

  }

  public void sendResponse(String fileName,
                           TemplateData templateData,
                           PrintWriter writer) {
    String template = IOSystem.read(appContext.getInputStream(SecurityAppProperty.TEMPLATE_DIR_ACCOUNT + fileName));
    String message = TemplateEngine.getText(template,
                                            templateData);

    writer.write(message);
  }

  public final void act(HttpAppRequest appRequest) throws IOException {
    try {
      perform(appRequest);
    } catch (Exception e) {
      e.printStackTrace();
      onFailure(appRequest,
                e);
      throw new AppException(e.getMessage());
    }
  }

  protected void onFailure(HttpAppRequest appRequest,
                           Exception e) throws IOException {

  }

  abstract protected void perform(HttpAppRequest appRequest) throws IOException;

  static String checkMobileNumber(String mobileNo) {

    mobileNo = mobileNo.trim();
    if (mobileNo.length() != 10 || !AppHelper.isValidMobileNumber(mobileNo)) {
      throw new AppException("Invalid mobile number '" + mobileNo + "'");
    }

    /*
     * if (mobileNo.length() == 10) { mobileNo = "91" + mobileNo; }
     */
    return mobileNo;
  }

  protected User getUser(String mobile) {

    EntityQuery<User> entityFilter = appEngine.createQuery(User.class);
    entityFilter.addQueryParameter("mobile",
                                   mobile);

    User newUser = appEngine.getFirst(entityFilter);

    return newUser;
  }

  protected User createUser(String mobile,
                            String roleId) {

    checkUserExistence(mobile);

    if (roleId == null) {
      roleId = "202";
    }

    Role role = appEngine.get(Role.class,
                              roleId);

    if (role == null) {
      throw new RuntimeException("Unknown Role: " + roleId);
    }

    User user = new User();
    user.setMobile(mobile);
    user.setPrimaryRole(role);
    user.setCreatedOn(systemCalendar.getUTCCalendar().getTime());

    user.setState("A");

    return user;
  }
  
  protected void updateUserOTP(User user, AppType appType) {
    String otp = randomIntegerKeyGenerator.getKey();
    System.out.println(">>>>> OTP " + otp);
    user.setMobileAccessToken(Password.encryptPassword(otp));

    appEngine.save(user);
    
    sendOTP(user.getMobile(), otp, appType);
  }

  protected void postUserCreate(String name,
                                User user,
                                NewUser newUser) {
    Customer customer = new Customer();
    customer.setName(name);
    customer.setMobile(user.getMobile());
    customer.setUserId(user.getId());

    appEngine.save(customer);

    appEngine.remove(NewUser.class,
                     newUser.getId());

  }

  protected NewUser getNewUser(String mobile) {

    EntityQuery<NewUser> entityFilter = appEngine.createQuery(NewUser.class);
    entityFilter.addQueryParameter("mobile",
                                   mobile);

    NewUser newUser = appEngine.getFirst(entityFilter);

    return newUser;
  }

  protected void checkUserExistence(String mobile) {
    if (getUser(mobile) != null) {
      throw new RuntimeException("'" + mobile + "' already exists");
    }
  }

  protected void sendOTP(String mobileNo,
                         String otp,
                         AppType appType) {

    String otpFile = null;
    switch (appType) {
    case ANDROID:
      otpFile = "OTPAndroid.txt";
      break;
    default:
      otpFile = "GOLWebOTP.txt";
    }

    String text = IOSystem.read(appContext.getInputStream(SecurityAppProperty.TEMPLATE_DIR_SMS + otpFile));
    text = text.replace("#{code}",
                        otp);
    System.out.println(">> SMS "+text+" "+AppConstants.COUNTRY_PHONE_CODE + mobileNo);
    smsSender.send(AppConstants.COUNTRY_PHONE_CODE + mobileNo,
                   text);

  }

  protected void validatePasswords(String password,
                                   String rpassword) {

    if (StringHelper.isNullOrEmpty(password) || !password.equals(rpassword)) {
      throw new AppException("Passwords do not match");
    }
  }

}
