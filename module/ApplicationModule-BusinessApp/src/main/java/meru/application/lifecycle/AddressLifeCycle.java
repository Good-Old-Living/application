package meru.application.lifecycle;

import app.domain.location.Address;
import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.data.format.json.JsonHelper;

public class AddressLifeCycle extends AbstractEntityLifeCycle<Address> {

  @Override
  public boolean preCreate(Address address) {
    updateAddress(address);

    return true;
  }

  @Override
  public boolean preModify(Address address) {
    updateAddress(address);
    return true;
  }

  private void updateAddress(Address address) {

    String desc = JsonHelper.escapeNewLine(address.getAddress());
    address.setAddress(desc);

  }

}
