package meru.application.security.action;

import java.io.IOException;

import app.domain.security.User;
import meru.app.binding.http.HttpAppRequest;
import meru.application.security.Password;
import meru.exception.AppException;
import meru.sys.lang.StringHelper;
import meru.template.TemplateStringData;

public class ChangePasswordAction extends AccountAction {

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {

    String userId = appRequest.getSingleParameter("userId");
    String oldPassword = appRequest.getSingleParameter("oldPassword");
    String newPassword = appRequest.getSingleParameter("newPassword");
    String rnewPassword = appRequest.getSingleParameter("rnewPassword");

    if (StringHelper.isNullOrEmpty(userId)) {
      throw new AppException("Unable to identify the user");
    }

    if (StringHelper.isNullOrEmpty(oldPassword)) {
      throw new AppException("Please provide your old password");
    }

    validatePasswords(newPassword, rnewPassword);
    
    Long customerId = Long.valueOf(userId);
    User user = appEngine.get(User.class, customerId);
    
    if (user == null) {
      throw new AppException("Unable to locate the user");
    }
    
    String encPasswd = Password.encryptPassword(oldPassword);
    if (!encPasswd.equals(user.getPassword())) {
      throw new IllegalArgumentException("Old password is incorrect");
    }

    String password = Password.encryptPassword(newPassword);

    user.setPassword(password);

    appEngine.save(user);

    sendResponse("passwd-success.html",
        new TemplateStringData("success"),
        appRequest.getResponse().getWriter());

  }

  @Override
  protected void onFailure(HttpAppRequest appRequest, Exception e) throws IOException {
    sendResponse("passwd-failure.html",
        new TemplateStringData(e.getMessage()),
        appRequest.getResponse().getWriter());
  }
}
