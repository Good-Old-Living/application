package meru.application.security.action;

import java.io.IOException;

import meru.app.binding.http.HttpAppRequest;
import meru.app.session.Session;
import meru.app.session.http.HttpSessionManager;

public class LogoutAction extends AccountAction {

  private String appBaseUrl = "/";
  private HttpSessionManager sessionManager;

  public LogoutAction(HttpSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public void perform(HttpAppRequest appRequest) throws IOException {
    sessionManager.unsetCookie("E",
        appRequest.getResponse());
    sessionManager.unsetCookie(Session.SESSION_KEY,
        appRequest.getResponse());

    if ("true".equals(appRequest.getSingleParameter("isweb"))) {

      sendResponse(appRequest);
    }
  }

  protected void sendResponse(HttpAppRequest appRequest) throws IOException {
    appRequest.getResponse().sendRedirect(appBaseUrl);
  }

}
