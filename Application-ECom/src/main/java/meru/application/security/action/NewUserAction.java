package meru.application.security.action;

import java.io.IOException;

import javax.persistence.PersistenceException;

import app.domain.security.NewUser;
import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.application.security.Password;
import meru.exception.AppException;
import meru.sys.lang.StringHelper;
import meru.template.TemplateStringData;

public class NewUserAction extends AccountAction {

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String name = appRequest.getSingleParameter("name");
    String mobile = appRequest.getSingleParameter("mobile");
    String otp = appRequest.getSingleParameter("otp");
    String password = appRequest.getSingleParameter("password");
    String rpassword = appRequest.getSingleParameter("rpassword");
    String roleId = appRequest.getSingleParameter("roleId");
    
    if (StringHelper.isNullOrEmpty(name)) {
      throw new AppException("Please provide name");
    }

    if (StringHelper.isNullOrEmpty(mobile)) {
      throw new AppException("Please provide mobile");
    }

    if (StringHelper.isNullOrEmpty(otp)) {
      throw new AppException("Please provide OTP");
    }

    validatePasswords(password,
        rpassword);

    mobile = checkMobileNumber(mobile);

    NewUser newUser = getNewUser(mobile);
    if (newUser == null) {
      throw new AppException("We are unable to fnd your number in our system");
    }
    
    String encOtp = Password.encryptPassword(otp);
    if (!encOtp.equals(newUser.getMobileAccessToken())) {
      throw new IllegalArgumentException("The OTP is invalid");
    }

    User user = createUser(mobile,
        roleId);

    if (password != null) {
      password = Password.encryptPassword(password);
    }

    user.setPassword(password);

    try {

      appEngine.save(user);

      postUserCreate(name,
          user,
          newUser);

    } catch (PersistenceException e) {
      e.printStackTrace();
      Throwable throwable = e.getCause();
      if ("ConstraintViolationException".equalsIgnoreCase(throwable.getClass().getSimpleName())) {

        throw new IllegalArgumentException("'" + mobile + "' already exists");

      }

      throw e;

    }

    sendResponse("register-success.html",
        new TemplateStringData("success"),
        appRequest.getResponse().getWriter());

  }

  @Override
  protected void onFailure(HttpAppRequest appRequest, Exception e) throws IOException {
    sendResponse("register-failure.html",
        new TemplateStringData(e.getMessage()),
        appRequest.getResponse().getWriter());
  }
}
