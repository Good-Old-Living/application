package meru.application.security.lifecycle;

import app.domain.security.NewUser;
import meru.application.security.account.AccountManager;

public class NewUserLifeCycle extends AccountLifeCycle<NewUser> {

  @Override
  public void init() {
    super.init();
    new AccountManager(appContext, appEngine, serviceManager);
  }

  @Override
  public boolean preCreate(NewUser newUser) {
    return true;
  }

  @Override
  public Object postCreate(NewUser newUser) {

    return postModify(newUser);
  }

  @Override
  public Object postModify(NewUser newUser) {

//    if (newUser.getMobile() != null) {
//     sendOTPSMS(newUser.getMobile(), newUser.getOtp());
//    }
    return null;
  }

}
