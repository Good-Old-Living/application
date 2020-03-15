package meru.application.security.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;

import app.domain.security.NewUser;
import meru.app.binding.http.HttpAppRequest;
import meru.app.binding.http.servlet.AppServlet;
import meru.app.security.AuthToken;
import meru.app.session.Session;
import meru.application.security.SecurityAppProperty;
import meru.exception.AppException;
import meru.exception.InvalidLoginException;
import meru.sys.IOSystem;
import meru.sys.lang.StringHelper;
import meru.template.TemplateData;
import meru.template.TemplateEngine;
import meru.template.TemplateStringData;

@MultipartConfig
public class AccountServlet extends AppServlet {

  private static final long serialVersionUID = 1L;

  private AccountManager accountManager;
  private String appBaseUrl;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    accountManager = AccountManager.getInstance();
    appBaseUrl = "/";
  }

  @Override
  protected void delete(HttpAppRequest appRequest) throws IOException {
  }

  @Override
  protected void get(HttpAppRequest appRequest) throws ServletException,
                                                IOException {
    String pathInfo = appRequest.getRequest().getPathInfo();
    if (pathInfo.equals("/logout")) {
      mSessionManager.unsetCookie("E",
                                  appRequest.getResponse());
      mSessionManager.unsetCookie(Session.SESSION_KEY,
                                  appRequest.getResponse());
      appRequest.getResponse().sendRedirect(appBaseUrl);
    } else {
      if (pathInfo.equals("/glogin")) {
        loggedInBySocial(appRequest,
                         "g");
      } else if (pathInfo.equals("/fblogin")) {
        loggedInBySocial(appRequest,
                         "f");
      }
    }
  }

  private AuthToken login(HttpAppRequest appRequest) {
    String userName = appRequest.getSingleParameter("email");
    String password = appRequest.getSingleParameter("password");

    AuthToken authToken = accountManager.authenticate(userName,
                                                      password);

    if (authToken.getUserId() != null) {
      mSessionManager.userLoggedIn(authToken,
                                   appRequest.getRequest(),
                                   appRequest.getResponse());
    }

    return authToken;
  }

  private AuthToken loggedInBySocial(HttpAppRequest appRequest,
                                     String provider) {
    String email = appRequest.getSingleParameter("email");
    String name = appRequest.getSingleParameter("name");
    String providerId = appRequest.getSingleParameter("pid");

    AuthToken authToken = accountManager.authenticateByProvider(name,
                                                                email,
                                                                provider,
                                                                providerId);

    if (authToken.getUserId() != null) {
      mSessionManager.userLoggedIn(authToken,
                                   appRequest.getRequest(),
                                   appRequest.getResponse());
    }

    return authToken;
  }

  private NewUser register(HttpAppRequest appRequest) {

    String name = appRequest.getSingleParameter("name");
    String email = appRequest.getSingleParameter("email");
    String mobile = appRequest.getSingleParameter("mobile");
    String password = appRequest.getSingleParameter("password");
    String rpassword = appRequest.getSingleParameter("rpassword");
    String roleId = appRequest.getSingleParameter("roleId");
    if (roleId == null) {
      roleId = "202";
    }

    if (rpassword != null && !password.equals(rpassword)) {
      throw new RuntimeException("Passwords do not match");
    }

    NewUser newUser = accountManager.createTempNewUser(name,
                                                       email,
                                                       mobile,
                                                       password,
                                                       roleId);
    return newUser;
  }

  @Override
  protected void post(HttpAppRequest appRequest) throws ServletException,
                                                 IOException {

    String pathInfo = appRequest.getRequest().getPathInfo();

    if (pathInfo.equals("/applogin")) {

      try {

        login(appRequest);

      } catch (InvalidLoginException ex) {
        throw new AppException(ex.getMessage());
      }

    } else if (pathInfo.equals("/appregister")) {

      try {

        register(appRequest);
      } catch (AppException ex) {
        throw ex;

      } catch (Exception ex) {
        ex.printStackTrace();
        throw new AppException(ex.getMessage());
      }

    } else if (pathInfo.equals("/sendotp")) {

      String mobileNo = appRequest.getSingleParameter("mobile");

      try {

        accountManager.sendOTPToMobile(mobileNo);
      } catch (AppException ex) {
        throw ex;

      } catch (Exception ex) {
        ex.printStackTrace();
        throw new AppException(ex.getMessage());
      }

    } else if (pathInfo.equals("/otplogin")) {

      String mobileNo = appRequest.getSingleParameter("mobile");
      String otp = appRequest.getSingleParameter("otp");
      try {

        AuthToken authToken = accountManager.otpLogin(mobileNo,
                                                      otp);
        if (authToken.getUserId() != null) {
          mSessionManager.userLoggedIn(authToken,
                                       appRequest.getRequest(),
                                       appRequest.getResponse());
        }

      } catch (AppException ex) {
        throw ex;

      } catch (Exception ex) {
        ex.printStackTrace();
        throw new AppException(ex.getMessage());
      }

    } else if (pathInfo.equals("/login")) {

      try {
        AuthToken authToken = login(appRequest);

        String home = authToken.getHome();

        String redirectTo = appRequest.getSingleParameter("redirectTo");

        if (home != null) {
          redirectTo = home;
        }
        TemplateStringData templateData = new TemplateStringData(redirectTo);
        sentResponse("login-success.html",
                     templateData,
                     appRequest.getResponse().getWriter());

      } catch (Exception e) {

        e.printStackTrace();

        sentResponse("login-failure.html",
                     new TemplateStringData(e.getMessage()),
                     appRequest.getResponse().getWriter());

      }

    } else if (pathInfo.equals("/register")) {
      // String name = appRequest.getSingleParameter("name");
      // String email = appRequest.getSingleParameter("email");
      // String mobile = appRequest.getSingleParameter("mobile");
      // String password = appRequest.getSingleParameter("password");
      // String rpassword =
      // appRequest.getSingleParameter("rpassword");
      // String roleId = appRequest.getSingleParameter("roleId");
      // String appId = appRequest.getSingleParameter("appId");

      try {
        // if (!password.equals(rpassword)) {
        // throw new RuntimeException("Passwords do not match");
        // }
        //
        // NewUser newUser = accountManager.createTempNewUser(name,
        // email,
        // mobile,
        // password,
        // Long.parseLong(roleId),
        // appId);

        register(appRequest);

        sentResponse("register-success.html",
                     new TemplateStringData("success"),
                     appRequest.getResponse().getWriter());

      } catch (Exception e) {

        e.printStackTrace();
        sentResponse("register-failure.html",
                     new TemplateStringData(e.getMessage()),
                     appRequest.getResponse().getWriter());
      }
    } else if (pathInfo.equals("/changePasswd")) {
      String userId = appRequest.getSingleParameter("userId");
      String oldPassword = appRequest.getSingleParameter("oldPassword");
      String newPassword = appRequest.getSingleParameter("newPassword");
      String rnewPassword = appRequest.getSingleParameter("rnewPassword");

      try {
        if (StringHelper.isNullOrEmpty(oldPassword)
            || StringHelper.isNullOrEmpty(newPassword)
            || StringHelper.isNullOrEmpty(rnewPassword)) {
          throw new RuntimeException("Password fields can not be empty");
        }

        if (!newPassword.equals(rnewPassword)) {
          throw new RuntimeException("New passwords do not match");
        }

        accountManager.changePassword(userId,
                                      oldPassword,
                                      newPassword);
        sentResponse("passwd-success.html",
                     new TemplateStringData(""),
                     appRequest.getResponse().getWriter());
      } catch (Exception e) {
        e.printStackTrace();
        sentResponse("passwd-failure.html",
                     new TemplateStringData(e.getMessage()),
                     appRequest.getResponse().getWriter());
      }
    } else if (pathInfo.equals("/mailResetPasswd")) {
      String email = appRequest.getSingleParameter("email");
      try {
        if (StringHelper.isNullOrEmpty(email)) {
          throw new RuntimeException("Email can not be empty");
        }
        accountManager.resetPassword(email);

        sentResponse("passwd-email-success.html",
                     new TemplateStringData(""),
                     appRequest.getResponse().getWriter());
      } catch (Exception e) {
        e.printStackTrace();
        sentResponse("passwd-failure.html",
                     new TemplateStringData(e.getMessage()),
                     appRequest.getResponse().getWriter());
      }

    } else if (pathInfo.equals("/resetPasswd")) {

      String userId = appRequest.getSingleParameter("userId");
      String newPassword = appRequest.getSingleParameter("newPassword");
      String rnewPassword = appRequest.getSingleParameter("rnewPassword");

      try {
        if (StringHelper.isNullOrEmpty(newPassword)
            || StringHelper.isNullOrEmpty(rnewPassword)) {
          throw new RuntimeException("Password fields can not be empty");
        }

        if (!newPassword.equals(rnewPassword)) {
          throw new RuntimeException("New passwords do not match");
        }

        accountManager.resetPassword(userId,
                                     newPassword);

        sentResponse("passwd-success.html",
                     new TemplateStringData(""),
                     appRequest.getResponse().getWriter());
      } catch (Exception e) {
        e.printStackTrace();
        sentResponse("passwd-failure.html",
                     new TemplateStringData(e.getMessage()),
                     appRequest.getResponse().getWriter());
      }
    }

  }

  private void sentResponse(String fileName,
                            TemplateData templateData,
                            PrintWriter writer) {
    String template = IOSystem.read(mAppContext.getInputStream(SecurityAppProperty.TEMPLATE_DIR_ACCOUNT
        + fileName));
    String message = TemplateEngine.getText(template,
                                            templateData);

    writer.write(message);
  }

}