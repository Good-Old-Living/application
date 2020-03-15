package meru.application.security.action;

import java.io.IOException;

import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.application.AppType;
import meru.exception.AppException;
import meru.sys.lang.StringHelper;
import meru.template.TemplateStringData;

public class ResetPasswordOTPAction extends AccountAction {

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String mobile = appRequest.getSingleParameter("mobile");

    if (StringHelper.isNullOrEmpty(mobile)) {
      throw new AppException("Please provide mobile number");
    }

    User user = getUser(mobile);
    if (user == null) {
      throw new AppException(mobile + " has not been registered with us");
    }

    updateUserOTP(user,
                  AppType.WEB);

    sendResponse("password-reset-otp.html",
                 new TemplateStringData("An OTP has been sent to your mobile"),
                 appRequest.getResponse().getWriter());

  }

  @Override
  protected void onFailure(HttpAppRequest appRequest,
                           Exception e) throws IOException {
    sendResponse("password-reset-failure.html",
                 new TemplateStringData(e.getMessage()),
                 appRequest.getResponse().getWriter());
  }

}
