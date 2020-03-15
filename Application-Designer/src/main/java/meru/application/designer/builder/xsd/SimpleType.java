package meru.application.designer.builder.xsd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.builder.xsd.facet.EnumerationFacet;
import meru.application.designer.builder.xsd.facet.LengthFacet;
import meru.application.designer.builder.xsd.facet.MaxLengthFacet;
import meru.application.designer.domain.model.Facet;

import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSimpleType;

public class SimpleType
    implements EntityBuilder<XSSimpleType, Object> {
  
  
  
  
  private MaxLengthFacet mMaxLength;
  private EnumerationFacet mEnumeration;


  public SimpleType() {

    this.mEnumeration = new EnumerationFacet();
    this.mMaxLength = new MaxLengthFacet();
  }


  public Object build(XSSimpleType simpleType) {

    if (simpleType.isRestriction()) {
      return processRestriction(simpleType);
    }

    return null;
  }


  private Object processRestriction(XSSimpleType simpleType) {

    XSRestrictionSimpleType resType = simpleType.asRestriction();
    Collection<? extends XSFacet> facets = resType.getDeclaredFacets();

    List<Facet> facetList = new ArrayList<Facet>();

    if (facets != null && !facets.isEmpty()) {
      
      for (XSFacet facet : facets) {
      
        String facetName = facet.getName();
        if (facetName.equals("enumeration")) {
          return this.mEnumeration.build(simpleType);
        }
        else if (facetName.equals("maxLength")) {
                
          facetList.add(mMaxLength.build(facet));
        }
        else if (facetName.equals("length")) {
          
          facetList.add(new LengthFacet().build(facet));
        }
      }

    }

    return facetList;
  }
}