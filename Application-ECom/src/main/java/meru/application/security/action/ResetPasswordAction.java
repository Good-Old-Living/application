package meru.application.security.action;

import java.io.IOException;

import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.application.security.Password;
import meru.exception.AppException;
import meru.sys.lang.StringHelper;
import meru.template.TemplateStringData;

public class ResetPasswordAction extends AccountAction {

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String mobile = appRequest.getSingleParameter("mobile");
    String otp = appRequest.getSingleParameter("otp");
    String password = appRequest.getSingleParameter("password");
    String rpassword = appRequest.getSingleParameter("rpassword");

    if (StringHelper.isNullOrEmpty(mobile)) {
      throw new AppException("Please provide mobile");
    }

    if (StringHelper.isNullOrEmpty(otp)) {
      throw new AppException("Please provide OTP");
    }

    validatePasswords(password, rpassword);

    mobile = checkMobileNumber(mobile);
    
   
    User user = getUser(mobile);
    
    if (user == null) {
      throw new AppException("Unknown mobile number");
    }
    
    String encOtp = Password.encryptPassword(otp);
    if (!encOtp.equals(user.getMobileAccessToken())) {
      throw new IllegalArgumentException("The OTP is invalid");
    }

    if (password != null) {
      password = Password.encryptPassword(password);
    }

    user.setPassword(password);

    appEngine.save(user);

    sendResponse("password-reset.html",
        new TemplateStringData("success"),
        appRequest.getResponse().getWriter());

  }

  @Override
  protected void onFailure(HttpAppRequest appRequest, Exception e) throws IOException {
    sendResponse("password-reset-failure.html",
        new TemplateStringData(e.getMessage()),
        appRequest.getResponse().getWriter());
  }
}
