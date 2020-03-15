package meru.erp.mdm.bp.lifecycle;

import app.domain.location.HousingComplexAddress;
import app.erp.mdm.bp.CustomerAddress;
import meru.app.engine.entity.AbstractEntityLifeCycle;

public class CustomerAddressLifeCycle extends AbstractEntityLifeCycle<CustomerAddress>  {

  
  @Override
  public boolean preCreate(CustomerAddress customerAddress) {
    toUpperCaseBlock(customerAddress);
    return true;
  }

  @Override
  public boolean preModify(CustomerAddress customerAddress) {
    toUpperCaseBlock(customerAddress);
    return true;
  }
  
  private void toUpperCaseBlock(CustomerAddress customerAddress) {
    HousingComplexAddress hcAddress = customerAddress.getHousingComplexAddress();
    
    if (hcAddress != null) {
      hcAddress.setBlock(hcAddress.getBlock().toUpperCase());
    }
  }
}
