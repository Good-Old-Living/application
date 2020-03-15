package meru.application.security.account;

import javax.persistence.PersistenceException;

import app.domain.comm.SendEmail;
import app.domain.security.NewUser;
import app.domain.security.Role;
import app.domain.security.User;
import meru.app.AppContext;
import meru.app.engine.AppEngine;
import meru.app.security.AuthToken;
import meru.application.security.Password;
import meru.application.security.SecurityAppProperty;
import meru.application.security.UserType;
import meru.exception.AppException;
import meru.exception.InvalidLoginException;
import meru.persistence.EntityQuery;
import meru.sys.AppHelper;
import meru.sys.IOSystem;
import meru.sys.SystemCalendar;
import meru.sys.lang.StringHelper;
import meru.sys.uid.UIDGenerator;
import meru.sys.uid.key.RandomIntegerKeyGenerator;
import meru.template.TemplateEngine;
import meru.template.TemplateMultiData;

public class AccountManager {

  private static final String DEFAULT_ROLE_ID = "202";

  private AppContext appContext;
  private AppEngine appEngine;
  private UIDGenerator uidGenerator;
  private RandomIntegerKeyGenerator randomIntegerKeyGenerator;
  private SystemCalendar systemCalendar;

  // TODO
  private static AccountManager INSTANCE;

  public AccountManager(AppContext appContext,
                        AppEngine appEngine) {
    this.appContext = appContext;
    this.appEngine = appEngine;
    this.uidGenerator = appContext.getUIDGenerator();
    randomIntegerKeyGenerator = new RandomIntegerKeyGenerator((byte) 4);
    systemCalendar = SystemCalendar.getInstance();

    INSTANCE = this;

  }

  public static AccountManager getInstance() {
    return INSTANCE;
  }

  User createUser(String name,
                  String email,
                  String roleId,
                  String provider,
                  String providerId) {
    email = email.trim();
    User user = new User();

    user.setName(name);
    user.setEmail(email);
    user.setPassword("paaril-social");
    Role role = appEngine.get(Role.class,
                              roleId);
    if (role == null) {
      throw new RuntimeException("A Role with id '" + roleId
          + "' could not be found");
    }
    user.setPrimaryRole(role);
    user.setProvider(provider);
    user.setProviderId(providerId);
    user.setState(AccountState.Active.getCode());

    appEngine.save(user);

    return user;
  }

  User createMobileUser(String mobileNo,
                        String password,
                        Role role) {
    mobileNo = mobileNo.trim();
    User user = new User();

    user.setMobile(mobileNo);
    user.setPassword(password);
    user.setPrimaryRole(role);
    user.setState(AccountState.Active.getCode());

    appEngine.save(user);

    return user;
  }

  public AuthToken authenticate(String userName,
                                String password) {

    userName = userName.trim();
    User user = validateUser(userName,
                             password);

    String info = user.getInfo();

    return new AuthToken(uidGenerator.getUId(),
                         user.getName(),
                         (info == null) ? String.valueOf(user.getId()) : info,
                         user.getPrimaryRole().getHome());
  }

  public AuthToken authenticateByProvider(String name,
                                          String email,
                                          String provider,
                                          String providerId) {
    email = email.trim();

    User user;

    if (email != null) {
      user = getUserNoThrow(email);
    } else if (provider != null && providerId != null) {
      user = getUserByProvider(provider,
                               providerId);
    } else {
      throw new AppException("Invalid login request [" + email + ", " + provider
          + ", " + providerId + "]");
    }

    if (user == null) {
      user = createUser(name,
                        email,
                        "202",
                        provider,
                        providerId);
    }
    String info = user.getInfo();

    return new AuthToken(uidGenerator.getUId(),
                         user.getName(),
                         (info == null) ? String.valueOf(user.getId()) : info,
                         user.getPrimaryRole().getHome());
  }

  public void sendOTPToMobile(String mobileNo) {

    mobileNo = checkMobileNumber(mobileNo);

    User user = getUserByMobile(mobileNo);
    if (user == null) {

      EntityQuery<NewUser> entityFilter = appEngine.createQuery(NewUser.class);
      entityFilter.addQueryParameter(UserType.MOBILE.getAttrName(),
                                     mobileNo);
      NewUser newUser = appEngine.getFirst(entityFilter);

      if (newUser == null) {

        createTempNewUser(null,
                          null,
                          mobileNo,
                          null,
                          DEFAULT_ROLE_ID);
      } else {

        String password = randomIntegerKeyGenerator.getKey();
        newUser.setMobileAccessToken(password);
        newUser.setPassword(Password.encryptPassword(password));
        appEngine.save(newUser);

      }
    } else {
      String password = randomIntegerKeyGenerator.getKey();
      user.setPassword(Password.encryptPassword(password));
      user.setAccessToken(password);
      appEngine.save(user);
    }
  }

  public AuthToken otpLogin(String mobileNo,
                            String otp) {
    mobileNo = checkMobileNumber(mobileNo);
    User user = getUserByMobile(mobileNo);
    String encOtp = Password.encryptPassword(otp);
    if (user == null) {

      EntityQuery<NewUser> entityFilter = appEngine.createQuery(NewUser.class);
      entityFilter.addQueryParameter(UserType.MOBILE.getAttrName(),
                                     mobileNo);

      NewUser newUser = appEngine.getFirst(entityFilter);

      if (newUser == null) {
        throw new InvalidLoginException("Unknown mobile number");
      }

      if (newUser.getPassword().equals(encOtp)) {

        user = createMobileUser(mobileNo,
                                newUser.getPassword(),
                                newUser.getPrimaryRole());

      } else {
        throw new InvalidLoginException("Wrong OTP");
      }

    }

    if (user.getPassword().equals(encOtp)) {

      String info = user.getInfo();

      return new AuthToken(uidGenerator.getUId(),
                           user.getName(),
                           (info == null) ? String.valueOf(user.getId()) : info,
                           user.getPrimaryRole().getHome());

    }

    throw new InvalidLoginException("Invalid OTP");

  }

  private User validateUser(String userName,
                            String password) {
    if (StringHelper.isNullOrEmpty(userName)
        || StringHelper.isNullOrEmpty(password)) {
      throw new InvalidLoginException("Invalid User Name or Password");
    }

    /*
     * UserType userType = UserType.getUserType(userName); if (userType ==
     * UserType.MOBILE) { userName = "91" + userName; }
     */
    User user = getUser(userName);
    password = Password.encryptPassword(password);

    if (!password.equals(user.getPassword())) {
      throw new InvalidLoginException("Invalid User Name or Password");
    }

    return user;
  }

  private User getUserNoThrow(String userName) {

    UserType userType = UserType.getUserType(userName);

    EntityQuery<User> entityFilter = appEngine.createQuery(User.class);
    entityFilter.addQueryParameter(userType.getAttrName(),
                                   userName);

    return (User) appEngine.getFirst(entityFilter);

  }

  private User getUserByProvider(String provider,
                                 String providerId) {

    EntityQuery<User> entityFilter = appEngine.createQuery(User.class);
    entityFilter.addQueryParameter("provider",
                                   provider);
    entityFilter.addQueryParameter("providerId",
                                   providerId);

    return (User) appEngine.getFirst(entityFilter);

  }

  private User getUser(String userName) {

    User user = getUserNoThrow(userName);

    if (user == null) {
      throw new InvalidLoginException("'" + userName + "' does not exist");
    }

    return user;
  }

  private User getUserByMobile(String mobileNo) {

    EntityQuery<User> entityFilter = appEngine.createQuery(User.class);
    entityFilter.addQueryParameter(UserType.MOBILE.getAttrName(),
                                   mobileNo);

    User user = (User) appEngine.getFirst(entityFilter);

    return user;
  }

  public boolean userExists(String userName) {

    return getUserNoThrow(userName) != null;
  }

  private void checkUserExistence(String userId) {
    if (userExists(userId)) {
      throw new RuntimeException("'" + userId + "' already exists");
    }
  }

  private void checkNewUserExistence(String userId) {

    UserType userType = UserType.getUserType(userId);

    EntityQuery<NewUser> entityFilter = appEngine.createQuery(NewUser.class);
    entityFilter.addQueryParameter(userType.getAttrName(),
                                   userId);

    NewUser newUser = appEngine.getFirst(entityFilter);

    if (newUser != null) {
      throw new RuntimeException("'" + userId + "' already exists");
    }
  }

  private String checkMobileNumber(String mobileNo) {
    
    mobileNo = mobileNo.trim();
    if (mobileNo.length() == 10) {
      if (!AppHelper.isValidMobileNumber(mobileNo)) {
        throw new AppException("Invalid mobile number '" + mobileNo + "'");
      }
      mobileNo = "91" + mobileNo;
    }

    return mobileNo;
  }
  
  public NewUser createTempNewUser(String userName,
                                   String email,
                                   String mobile,
                                   String password,
                                   String roleId) {

    NewUser user = new NewUser();

    String userId = null;

    
    if (!StringHelper.isNullOrEmpty(email)) {
      email = email.trim();
      if (!AppHelper.isValidEmail(email)) {
        throw new AppException("Invalid email '" + email + "'");
      }
      checkNewUserExistence(email);
      checkUserExistence(email);
      userId = email;
      user.setEmail(userId);
      user.setEmailAccessToken(uidGenerator.getUId());
    }
    if (!StringHelper.isNullOrEmpty(mobile)) {
      userId = checkMobileNumber(mobile);

      checkNewUserExistence(userId);
      checkUserExistence(userId);
      user.setMobile(userId);
      password = randomIntegerKeyGenerator.getKey();
      user.setMobileAccessToken(password);
    }

    if (StringHelper.isNullOrEmpty(userId)
        || StringHelper.isNullOrEmpty(password)) {

      throw new RuntimeException("Invalid UserName or Password");
    }

    Role role = appEngine.get(Role.class,
                              roleId);

    if (role == null) {
      throw new RuntimeException("Unknown Role: " + roleId);
    }

    if (password != null) {
      password = Password.encryptPassword(password);
    }

    user.setPassword(password);
    user.setPrimaryRole(role);
    user.setRegisteredOn(systemCalendar.getUTCCalendar());
    try {

      appEngine.save(user);

    } catch (PersistenceException e) {

      Throwable throwable = e.getCause();
      if ("ConstraintViolationException".equalsIgnoreCase(throwable.getClass()
                                                                   .getSimpleName())) {

        throw new IllegalArgumentException("'" + userId + "' already exists");

      }

      throw e;

    }

    return user;
  }

  public void changePassword(String userId,
                             String oldPassword,
                             String newPassword) {

    User user = appEngine.get(User.class,
                              userId);
    if (user == null) {
      throw new RuntimeException("The user with id[" + userId
          + "] does not exist");
    }

    String password = Password.encryptPassword(oldPassword);

    if (!password.equals(user.getPassword())) {
      throw new RuntimeException("Wrong old password");
    }

    password = Password.encryptPassword(newPassword);

    user.setPassword(password);

    appEngine.save(user);
  }

  public void resetPassword(String userId,
                            String newPassword) {

    User user = appEngine.get(User.class,
                              userId);
    if (user == null) {
      throw new RuntimeException("The user with id[" + userId
          + "] does not exist");
    }

    String password = Password.encryptPassword(newPassword);

    user.setPassword(password);

    appEngine.save(user);
  }

  public void resetPassword(String email) {
    email = email.trim();
    User user = getUser(email);
    String template = IOSystem.read(appContext.getInputStream(SecurityAppProperty.TEMPLATE_DIR_MAIL
        + "ResetPassword.html"));

    TemplateMultiData templateData = new TemplateMultiData();
    templateData.addObject("user",
                           user);
    templateData.addObject("appContext",
                           appContext);
    String message = TemplateEngine.getText(template,
                                            templateData);

    SendEmail sendEmail = new SendEmail();
    sendEmail.setTos(email);
    sendEmail.setSubject("Reset Password");
    sendEmail.setMessage(message);
    sendEmail.setContentType("text/html");
    sendEmail.setReference("resetpass: " + user.getAccessToken());
    sendEmail.setSentOn(SystemCalendar.getInstance().getUTCCalendar());
    sendEmail.setState("N");

    appEngine.save(sendEmail);

  }
}