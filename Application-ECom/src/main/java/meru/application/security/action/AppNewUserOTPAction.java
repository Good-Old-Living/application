package meru.application.security.action;

import java.io.IOException;

import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.application.AppType;
import meru.application.security.Password;
import meru.exception.AppException;
import meru.sys.lang.StringHelper;

public class AppNewUserOTPAction extends NewUserOTPAction {

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String mobile = appRequest.getSingleParameter("mobile");

    if (StringHelper.isNullOrEmpty(mobile)) {
      throw new AppException("Please provide mobile");
    }

    mobile = checkMobileNumber(mobile);

    User user = getUser(mobile);
    if (user == null) {
      
      createNewUser(appRequest, AppType.ANDROID);
      
    } else {
      
      updateUserOTP(user, AppType.ANDROID);
      
    }
    
  }

  protected void onFailure(HttpAppRequest appRequest, Exception e) throws IOException {

  }
}
