package meru.application.security.lifecycle;

import app.domain.security.User;

public class UserLifeCycle extends AccountLifeCycle<User> {

  @Override
  public boolean preCreate(User newUser) {
    return true;
  }

  @Override
  public User postCreate(User user) {
    return user;
  }

}
