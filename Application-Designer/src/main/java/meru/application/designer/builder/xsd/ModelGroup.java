package meru.application.designer.builder.xsd;

import java.util.ArrayList;
import java.util.List;

import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.domain.model.Attribute;

import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSTerm;

public class ModelGroup
    implements EntityBuilder<XSModelGroup, List<Attribute>> {
  private Element mElement;


  public ModelGroup() {

    this.mElement = new Element();
  }


  public List<Attribute> build(XSModelGroup modelGroup) {

    XSParticle[] particles = modelGroup.getChildren();

    if (particles == null) {
      return null;
    }

    List<Attribute> attrList = new ArrayList<Attribute>(particles.length);

    if (particles != null) {
      for (XSParticle particle : particles) {
        XSTerm term = particle.getTerm();

        if (!term.isElementDecl())
          continue;
        Attribute attribute = this.mElement.build(term.asElementDecl());
        attribute.setMultiValued(Element.isMultiValued(particle));
        attribute.setRequired(Element.isRequired(particle));
        attrList.add(attribute);
      }

    }

    return attrList;
  }
}