package meru.application.security.lifecycle;

import app.domain.security.NewUser;
import app.domain.security.User;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.app.service.ServiceManager;
import meru.application.security.SecurityAppProperty;
import meru.application.security.UserType;
import meru.comm.mobile.http.HttpSMSSender;
import meru.persistence.EntityQuery;
import meru.sys.IOSystem;

public abstract class AccountLifeCycle<T> extends AbstractEntityLifeCycle<T> {

  private static final String COUNTRY_CODE = "91";
  private HttpSMSSender smsSender;

  @Override
  public void init() {
    smsSender = serviceManager.getService(ServiceManager.SMS_SENDER, HttpSMSSender.class);
  }

  protected void sendOTPSMS(String mobileNo, String accessToken) {

    String text = IOSystem.read(appContext.getInputStream(SecurityAppProperty.TEMPLATE_DIR_SMS + "GOLWebOTP.txt"));
    text = text.replace("#{code}", accessToken);
    smsSender.send(COUNTRY_CODE+mobileNo, text);
  }

  protected User getUserNoThrow(String userName) {

    UserType userType = UserType.getUserType(userName);

    EntityQuery<User> entityQuery = appEngine.createQuery(User.class);
    entityQuery.addQueryParameter(userType.getAttrName(), userName);

    return (User) appEngine.getFirst(entityQuery);

  }

  protected User getUser(String userName) {

    User user = getUserNoThrow(userName);

    if (user == null) {
      throw new RuntimeException("The user with '" + userName + "' does not exist");
    }

    return user;
  }

  protected boolean userExists(String userName) {

    return getUserNoThrow(userName) != null;
  }

  protected User createUser(NewUser newUser) {

    User user = new User();

//    user.setName(newUser.getName());
//    user.setEmail(newUser.getEmail());
//    user.setMobile(newUser.getMobile());
//    user.setPassword(newUser.getPassword());
//    user.setPrimaryRole(newUser.getPrimaryRole());
//    user.setState(AccountState.Active.getCode());
//
//    appEngine.save(user);

    return user;
  }

}
