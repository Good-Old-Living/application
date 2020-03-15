package meru.erp.account.lifecycle;

import app.domain.account.RegisteredNewUser;
import app.erp.mdm.bp.Customer;
import meru.app.engine.entity.AbstractEntityLifeCycle;

public class EComRegisteredNewUserLifeCycle extends AbstractEntityLifeCycle<RegisteredNewUser> {

  @Override
  public boolean preCreate(RegisteredNewUser newUser) {

    Customer customer = new Customer();
    customer.setUserId(newUser.getId());

    String name = newUser.getName();
    String email = newUser.getEmail();
    if (name == null && email != null) {
      name = email.substring(0,
                             email.indexOf('@'));
    }
    customer.setName(name);
    customer.setEmail(email);
    customer.setMobile(newUser.getMobile());
    appEngine.save(customer);

//    CustomerAddress customerAddress = new CustomerAddress();
//    customerAddress.setCustomerId(customer.getId());
//    customerAddress.setName(name);
//    customerAddress.setMobile(newUser.getMobile());
//    appEngine.save(customerAddress);

    return false;
  }

  @Override
  public boolean preModify(RegisteredNewUser newUser) {
    preCreate(newUser);
    return false;
  }

}
