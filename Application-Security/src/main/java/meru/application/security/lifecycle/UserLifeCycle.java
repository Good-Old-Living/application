package meru.application.security.lifecycle;

import app.domain.security.NewUser;
import app.domain.security.Role;
import app.domain.security.User;
import meru.app.service.ServiceManager;
import meru.application.security.account.AccountManager;
import meru.exception.AppException;
import meru.exception.SystemException;
import meru.exception.code.SecurityErrorCode;
import meru.messaging.MessageSender;
import meru.sys.lang.StringHelper;

public class UserLifeCycle extends AccountLifeCycle<User> {

  private String appRoot;
  private MessageSender messageSender;

  public UserLifeCycle() throws Exception {

    // messageSender = new HttpMessageSender();
    // appRoot = "http://localhost:8080/";
  }

  @Override
  public void init() {
    super.init();
    new AccountManager(appContext,
                       appEngine);
    messageSender = serviceManager.getService(ServiceManager.MESSAGE_SENDER,
                                              MessageSender.class);
    appRoot = appConfig.getProperty("app.base.url");
  }

  @Override
  public boolean preCreate(User newUser) {

    String userName = newUser.getName();

    if (userName == null) {
      userName = newUser.getEmail();
    }

    if (userName == null) {
      userName = newUser.getMobile();
    }

    String password = newUser.getPassword();

    if (StringHelper.isNullOrEmpty(userName)
        || StringHelper.isNullOrEmpty(password)) {
      throw new AppException(SecurityErrorCode.INVALID_USER_PASSWORD);
    }

    if (userExists(userName)) {
      throw new AppException(SecurityErrorCode.USER_ALREADY_EXISTS,
                             userName);
    }

    // if (newUser.getName() == null) {
    // newUser.setName(userName);
    // }

    popuplateUser(newUser);

    newUser.setState("A");

    return true;
  }

  @Override
  public User postCreate(User user) {

    StringBuilder jsonBuilder = new StringBuilder();

    String name = user.getName();
    if (name == null) {
      name = user.getEmail();
    }

    jsonBuilder.append("{\"id\":\"").append(user.getId()).append("\"");
    if (name != null) {
      jsonBuilder.append(",\"name\":\"").append(name).append("\"");
    }

    if (user.getEmail() != null) {
      jsonBuilder.append(",\"email\":\"").append(user.getEmail()).append("\"");
    }

    if (user.getMobile() != null) {
      jsonBuilder.append(",\"mobile\":\"")
                 .append(user.getMobile())
                 .append("\"");

    }

    jsonBuilder.append("}");

    // if ("paaril-social".equals(user.getPassword())) {
    messageSender.send(appRoot + "/e/RegisteredNewUser?new",
                       jsonBuilder.toString(),
                       "application/json");

    // }
    return user;
  }

  @Override
  public boolean preModify(User user) {
    popuplateUser(user);
    return true;

  }

  @Override
  public Object postModify(User user) {

    if (user.getMobile() != null) {
      sendOTPSMS(user.getMobile(),
                 user.getAccessToken());
    }
    return null;
  }

  private void popuplateUser(User user) {
    Long roleId = user.getPrimaryRole().getId();

    Role role = appEngine.get(Role.class,
                              roleId);
    if (role == null) {
      throw new SystemException("Unknown Role id : " + roleId);
    }

    // String password = Password.encryptPassword(user.getPassword());
    //
    // user.setPassword(password);
  }
}
