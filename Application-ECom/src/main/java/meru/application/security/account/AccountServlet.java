package meru.application.security.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;

import meru.app.binding.http.HttpAppRequest;
import meru.app.binding.http.servlet.AppServlet;
import meru.application.security.action.AccountAction;
import meru.application.security.action.AppNewUserOTPAction;
import meru.application.security.action.ChangePasswordAction;
import meru.application.security.action.LoginAction;
import meru.application.security.action.LoginOTPAction;
import meru.application.security.action.LogoutAction;
import meru.application.security.action.NewUserAction;
import meru.application.security.action.NewUserOTPAction;
import meru.application.security.action.ResetPasswordAction;
import meru.application.security.action.ResetPasswordOTPAction;

@MultipartConfig
public class AccountServlet extends AppServlet {

  private static final long serialVersionUID = 1L;

  private AccountManager accountManager;

  private Map<String, AccountAction> accoundActionMap;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    accountManager = AccountManager.getInstance();

    accoundActionMap = new HashMap<>(2);
    addAction("/register",
              new NewUserAction());
    addAction("/verifyMobile",
              new NewUserOTPAction());
    addAction("/login",
              new LoginAction(mSessionManager));
    addAction("/logout",
              new LogoutAction(mSessionManager));
    addAction("/sendPasswdRestOTP",
              new ResetPasswordOTPAction());
    addAction("/resetPasswd",
              new ResetPasswordAction());
    addAction("/changePasswd",
              new ChangePasswordAction());

    // Android app

    addAction("/sendAndOTP",
              new AppNewUserOTPAction());
    addAction("/loginOTP",
              new LoginOTPAction(mSessionManager));
  }

  private void addAction(String name,
                         AccountAction accountAction) {

    accountAction.setContext(mAppContext,
                             accountManager.getAppEngine(),
                             accountManager.getServiceManager());
    accoundActionMap.put(name,
                         accountAction);
  }

  @Override
  protected void delete(HttpAppRequest appRequest) throws IOException {
  }

  @Override
  protected void get(HttpAppRequest appRequest) throws ServletException,
                                                IOException {
    String pathInfo = appRequest.getRequest().getPathInfo();

    AccountAction accAction = accoundActionMap.get(pathInfo);
    if (accAction != null) {
      accAction.act(appRequest);
    }
  }

  @Override
  protected void post(HttpAppRequest appRequest) throws ServletException,
                                                 IOException {

    String pathInfo = appRequest.getRequest().getPathInfo();

    AccountAction accAction = accoundActionMap.get(pathInfo);
    if (accAction != null) {
      accAction.act(appRequest);
    }

  }

}