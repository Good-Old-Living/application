package meru.core.lifecycle;

import java.util.List;

import app.domain.location.CityArea;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.persistence.EntityQuery;

public class CityAreaLifeCycle extends BusinessAppEntityLifeCycle<CityArea> {

  @Override
  public void init() {

  }

  @Override
  public List<CityArea> preGet(EntityQuery<CityArea> resourceFilter) {
 //   resourceFilter.addQueryParameter("isActive", "Y");
    return super.preGet(resourceFilter);
  }



}
