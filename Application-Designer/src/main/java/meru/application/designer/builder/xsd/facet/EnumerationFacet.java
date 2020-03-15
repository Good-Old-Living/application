package meru.application.designer.builder.xsd.facet;

import java.util.Collection;

import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.domain.model.ArgumentValue;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.DataType;
import meru.application.designer.domain.model.EnumEntity;
import meru.application.designer.domain.model.EnumValue;
import meru.application.designer.domain.model.Visibility;

import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSimpleType;

public class EnumerationFacet implements
    EntityBuilder<XSSimpleType, EnumEntity> {

  public EnumEntity build(XSSimpleType simpleType) {
    EnumEntity enumEntity = null;

    if (simpleType.isRestriction()) {
      String type = simpleType.getBaseType()
                              .getName();
      DataType dataType = DataType.getDataType(type);

      enumEntity = new EnumEntity(simpleType.getTargetNamespace(),
                                  simpleType.getName(),
                                  dataType);

      enumEntity.addAttribute(new Attribute(Visibility.PRIVATE,
                                            dataType,
                                            "value"));
      enumEntity.addAttribute(new Attribute(Visibility.PRIVATE,
                                            dataType,
                                            "id"));

      XSRestrictionSimpleType resType = simpleType.asRestriction();
      Collection<? extends XSFacet> facets = resType.getDeclaredFacets();

      if ((facets != null) && (!facets.isEmpty())) {
        for (XSFacet facet : facets) {
          if (facet.getName()
                   .equals("enumeration")) {
            enumEntity.addEnumValue(getEnumValue(facet,
                                                 dataType));
          }
        }
      }

    }

    return enumEntity;
  }

  private EnumValue getEnumValue(XSFacet facet,
                                 DataType dataType) {
    String value = facet.getValue().value;
    EnumValue enumValue = new EnumValue(getEnumValue(value));

    enumValue.addArgumentValue(new ArgumentValue(dataType,
                                                 "value",
                                                 value));
    enumValue.addArgumentValue(new ArgumentValue(dataType,
                                                 "id",
                                                 getId(facet)));

    return enumValue;
  }

  private String getEnumValue(String value) {
    return value;
  }

  private Object getId(XSFacet facet) {
    String id = facet.getForeignAttribute("antril.designer.domain",
                                          "id");

    if (id == null) {
      throw new RuntimeException("Missing 'Id' attribute in the enumeration '"
          + facet.getValue() + "'");
    }

    return id;
  }
}