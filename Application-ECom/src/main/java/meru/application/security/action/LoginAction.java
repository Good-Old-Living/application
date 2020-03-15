package meru.application.security.action;

import java.io.IOException;

import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.app.security.AuthToken;
import meru.app.session.http.HttpSessionManager;
import meru.application.security.Password;
import meru.exception.InvalidLoginException;
import meru.sys.lang.StringHelper;
import meru.sys.uid.UIDGenerator;
import meru.template.TemplateStringData;

public class LoginAction extends AccountAction {

  protected UIDGenerator uidGenerator;
  protected HttpSessionManager sessionManager;

  public LoginAction(HttpSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  protected void init() {
    uidGenerator = appContext.getUIDGenerator();
  }

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String mobile = appRequest.getSingleParameter("mobile");
    String password = appRequest.getSingleParameter("password");

    User user = validateUser(mobile,
        password);


    AuthToken authToken = setSession(user, appRequest);

    String home = authToken.getHome();

    String redirectTo = appRequest.getSingleParameter("redirectTo");

    if (home != null) {
      redirectTo = home;
    }
    TemplateStringData templateData = new TemplateStringData(redirectTo);
    sendResponse("login-success.html",
        templateData,
        appRequest.getResponse().getWriter());

  }

  @Override
  protected void onFailure(HttpAppRequest appRequest, Exception e) throws IOException {
    sendResponse("login-failure.html",
        new TemplateStringData(e.getMessage()),
        appRequest.getResponse().getWriter());

  }

  private User validateUser(String mobile, String password) {
    if (StringHelper.isNullOrEmpty(mobile) || StringHelper.isNullOrEmpty(password)) {
      throw new InvalidLoginException("Invalid Mobile Number or Password");
    }

    User user = getUser(mobile);

    if (user == null) {
      throw new InvalidLoginException("Invalid Mobile number or Password");
    }

    password = Password.encryptPassword(password);

    if (!password.equals(user.getPassword())) {
      throw new InvalidLoginException("Invalid Mobile number or Password");
    }

    return user;
  }
  
  protected AuthToken setSession(User user, HttpAppRequest appRequest) {
    String info = user.getInfo();

    AuthToken authToken = new AuthToken(uidGenerator.getUId(), user.getMobile(),
        (info == null) ? String.valueOf(user.getId()) : info, user.getPrimaryRole().getHome());

    if (authToken.getUserId() != null) {
      sessionManager.userLoggedIn(authToken,
          appRequest.getRequest(),
          appRequest.getResponse());
    }
    
    return authToken;
  }

}
