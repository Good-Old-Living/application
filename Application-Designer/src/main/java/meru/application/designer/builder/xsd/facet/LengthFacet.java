package meru.application.designer.builder.xsd.facet;

import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.domain.model.Facet;

import com.sun.xml.xsom.XSFacet;

public class LengthFacet
    implements EntityBuilder<XSFacet, Facet> {

  public Facet build(XSFacet facet) {

    String value = facet.getValue().value;

    if ((value == null) || (value.trim().length() == 0)) {
      throw new RuntimeException("Invalid value for 'maxLength' facet");
    }

    return new Facet("length", Integer.valueOf(Integer.parseInt(value)));
  }
}