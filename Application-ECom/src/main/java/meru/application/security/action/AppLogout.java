package meru.application.security.action;

import java.io.IOException;

import meru.app.binding.http.HttpAppRequest;
import meru.app.session.http.HttpSessionManager;

public class AppLogout extends LogoutAction {


  public AppLogout(HttpSessionManager sessionManager) {
    super(sessionManager);
  }

  @Override
  protected void sendResponse(HttpAppRequest appRequest) throws IOException {
    
  }

}
