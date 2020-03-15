package meru.application.security.action;

import java.io.IOException;
import java.util.UUID;

import app.domain.security.NewUser;
import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.app.session.http.HttpSessionManager;
import meru.application.security.Password;
import meru.exception.AppException;

public class LoginOTPAction extends LoginAction {


  public LoginOTPAction(HttpSessionManager sessionManager) {
    super(sessionManager);
  }

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String mobile = appRequest.getSingleParameter("mobile");
    String otp = appRequest.getSingleParameter("otp");

    mobile = checkMobileNumber(mobile);
    String encOtp = Password.encryptPassword(otp);

    User user = getUser(mobile);

    if (user == null) {

      NewUser newUser = getNewUser(mobile);

      if (newUser == null) {

        throw new AppException("Login failed");

      } else {

        if (!encOtp.equals(newUser.getMobileAccessToken())) {
          throw new IllegalArgumentException("The OTP is invalid");
        }

        user = createUser(mobile,
            null);
        user.setPassword(getPassword());
        appEngine.save(user);
        postUserCreate(null,
            user,
            newUser);
      }
    } else {
      if (!encOtp.equals(user.getMobileAccessToken())) {
        throw new IllegalArgumentException("Invalid login");
      }

    }

    setSession(user,
        appRequest);

  }
  
  @Override
  protected void onFailure(HttpAppRequest appRequest, Exception e) throws IOException {
    
  }
  
  private String getPassword() {
    return UUID.randomUUID()
    .toString();
  }

}
