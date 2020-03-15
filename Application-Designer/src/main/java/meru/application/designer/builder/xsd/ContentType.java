package meru.application.designer.builder.xsd;

import java.util.ArrayList;
import java.util.List;

import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.domain.model.Attribute;

import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSTerm;

public class ContentType
    implements EntityBuilder<XSContentType, List<Attribute>> {
  private ModelGroup mModelGroup;
  private Element mElement;


  public ContentType() {

    this.mModelGroup = new ModelGroup();

    this.mElement = new Element();
  }


  public List<Attribute> build(XSContentType contentType) {

    if (contentType.asParticle() != null) {
      return build(contentType.asParticle());
    }
    return null;
  }


  private List<Attribute> build(XSParticle particle) {

    List<Attribute> attrList = null;

    XSTerm term = particle.getTerm();

    if (term.isModelGroup()) {
      attrList = this.mModelGroup.build(term.asModelGroup());
    }
    else if (term.isElementDecl()) {
      XSElementDecl element = term.asElementDecl();
      Attribute attribute = this.mElement.build(element);
      attribute.setMultiValued(Element.isMultiValued(particle));
      attrList = new ArrayList<Attribute>();
      attrList.add(attribute);
    }

    return attrList;
  }
}