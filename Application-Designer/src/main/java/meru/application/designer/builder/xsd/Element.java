package meru.application.designer.builder.xsd;

import java.util.ArrayList;
import java.util.List;

import meru.application.designer.Application;
import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.AttributeType;
import meru.application.designer.domain.model.DataType;
import meru.application.designer.domain.model.Facet;
import meru.application.designer.domain.model.Visibility;

import com.sun.xml.xsom.ForeignAttributes;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSType;
import com.sun.xml.xsom.XmlString;

public class Element implements EntityBuilder<XSElementDecl, Attribute> {
  private SimpleType mSimpleType = new SimpleType();

  @SuppressWarnings("rawtypes")
  public Attribute build(XSElementDecl element) {

    System.out.println("          Adding the attribute : " + element.getName());

    XSType type = element.getType();

    DataType dataType = null;
    List<Facet> facetList = null;

    
    if (type.isSimpleType()) {
      if (type.getName() == null)
        dataType = DataType.getDataType(type.getBaseType().getName());
      else {
        dataType = DataType.getDataType(type.getName());
      }

      Object object = this.mSimpleType.build(type.asSimpleType());

      if (object instanceof List) {
        facetList = new ArrayList<Facet>();

        for (Object obj : (List) object) {
          facetList.add((Facet) obj);
        }
      }
    }
    else {
      dataType = getComplexDataType(type);
    }

    Attribute attribute = new Attribute(Visibility.PRIVATE, dataType, element.getName());

    attribute.setNullable(element.isNillable());
    attribute.setReference(element.isGlobal());

    XmlString defaultValue = element.getDefaultValue();
    if (defaultValue != null) {
      attribute.setDefaultValue(defaultValue.value);
    }

    List<? extends ForeignAttributes> fAttrs = element.getForeignAttributes();

    if (fAttrs != null) {
      for (ForeignAttributes attrs : fAttrs) {
        String value = attrs.getValue(Application.ENTITY_ANNOTATION_NAMESPACE, "sequence");

        if (value != null && value.trim().length() > 0) {
          attribute.addAttribute("sequence", value);
        }

        value = attrs.getValue(Application.ENTITY_ANNOTATION_NAMESPACE, "mappedBy");

        if (value != null && value.trim().length() > 0) {
          attribute.addAttribute("mappedBy", value);
        }

        value = attrs.getValue(Application.ENTITY_ANNOTATION_NAMESPACE, "unique");

        if (value != null && value.trim().equalsIgnoreCase("true")) {
          attribute.setUnique(true);
        }

        value = attrs.getValue(Application.ENTITY_ANNOTATION_NAMESPACE, "transient");

        if (value != null && value.trim().equalsIgnoreCase("true")) {
          attribute.setTransient(true);
        }

      }

    }

    if (facetList != null) {
      attribute.setFacets(facetList);
    }

    return attribute;
  }

  private DataType getComplexDataType(XSType type) {

    AttributeType attrType = null;

    if (type.getName().equals("EnumEntity"))
      attrType = AttributeType.COMPLEX_ENUM;
    else {
      attrType = AttributeType.COMPLEX;
    }

    DataType dataType = new DataType(type.getTargetNamespace(), type.getName(), attrType);

    return dataType;
  }

  public static boolean isMultiValued(XSParticle particle) {

    return (particle.getMaxOccurs() == -1) || (particle.getMaxOccurs() > 1);
  }

  public static boolean isRequired(XSParticle particle) {

    return particle.getMinOccurs() == 1;
  }
}