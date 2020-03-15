package meru.application.security.action;

import java.io.IOException;

import javax.persistence.PersistenceException;

import app.domain.security.NewUser;
import meru.app.binding.http.HttpAppRequest;
import meru.application.AppType;
import meru.application.security.Password;
import meru.exception.AppException;
import meru.sys.lang.StringHelper;
import meru.template.TemplateStringData;

public class NewUserOTPAction extends AccountAction {

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    createNewUser(appRequest,
                  AppType.WEB);

    sendResponse("register-otp.html",
                 new TemplateStringData("An OTP has been sent to your mobile"),
                 appRequest.getResponse().getWriter());

  }

  protected void createNewUser(HttpAppRequest appRequest,
                               AppType appType) throws IOException {

    String mobile = appRequest.getSingleParameter("mobile");

    if (StringHelper.isNullOrEmpty(mobile)) {
      throw new AppException("Please provide mobile");
    }

    NewUser user = new NewUser();
    mobile = checkMobileNumber(mobile);

    checkUserExistence(mobile);

    NewUser existUser = getNewUser(mobile);

    if (existUser != null) {
      user = existUser;
    }

    user.setMobile(mobile);
    String otp = randomIntegerKeyGenerator.getKey();
    System.out.println(">>>>> OTP " + otp);
    user.setOtp(otp);
    user.setMobileAccessToken(Password.encryptPassword(otp));

    try {

      appEngine.save(user);

    } catch (PersistenceException e) {

      Throwable throwable = e.getCause();
      if ("ConstraintViolationException".equalsIgnoreCase(throwable.getClass().getSimpleName())) {

        throw new IllegalArgumentException("'" + mobile + "' already exists");

      }

      throw e;

    }

    sendOTP(mobile,
            otp,
            appType);
  }

  @Override
  protected void onFailure(HttpAppRequest appRequest,
                           Exception e) throws IOException {
    sendResponse("register-failure.html",
                 new TemplateStringData(e.getMessage()),
                 appRequest.getResponse().getWriter());
  }

}
