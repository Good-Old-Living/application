package meru.application.designer.builder.xsd;

import java.util.Collection;
import java.util.List;

import meru.application.designer.Application;
import meru.application.designer.builder.BuilderConfig;
import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.builder.EntityRegistry;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.Entity;
import meru.application.designer.domain.model.EntityInterest;
import meru.application.designer.domain.model.character.HierarchicalCharacteristic;
import meru.application.designer.domain.model.character.NameableCharacteristic;
import meru.application.designer.domain.model.character.UniqueKeyCharacteristic;
import app.entity.Hierarchical;
import app.entity.Nameable;

import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSType;

public class ComplexType implements EntityBuilder<XSComplexType, Entity> {
    private ContentType mContentType;
    private EntityRegistry mEntityRegistry;

    public ComplexType(EntityRegistry entityRegistry,
                       BuilderConfig builderConfig) {

        mEntityRegistry = entityRegistry;
        mContentType = new ContentType();
    }

    public Entity build(XSComplexType complexType) {

        Entity entity = null;

        XSContentType contentType = complexType.getContentType();

        XSSimpleType simpleType = contentType.asSimpleType();

        if (simpleType == null) {
            System.out.println("     Building the ComplexType : "
                    + complexType.getName());
            entity = new Entity(complexType.getTargetNamespace(),
                                complexType.getName());

            
            String isTransient = complexType.getForeignAttribute(Application.ENTITY_ANNOTATION_NAMESPACE, "transient");
            
            if (isTransient != null) {
              entity.setTransient(isTransient.equalsIgnoreCase("true"));
            }
            
            entity.setAbstract(complexType.isAbstract());

            List<Attribute> attrList = null;

            switch (complexType.getDerivationMethod()) {
            case XSComplexType.RESTRICTION:
                attrList = mContentType.build(contentType);
                setCharacteristics(complexType,
                                   entity);
                break;
            case XSComplexType.EXTENSION:
                setBaseEntity(complexType.getBaseType(),
                              entity);
                setCharacteristics(complexType,
                                   entity);
                attrList = mContentType.build(complexType.getExplicitContent());
            case 3:
            case 4:
            }

            entity.setAttributes(attrList);

            Collection<? extends XSAttributeUse> attrs = complexType.getAttributeUses();
            if (attrs != null && !attrs.isEmpty()) {
                for (XSAttributeUse attrUse : attrs) {

                    XSAttributeDecl attr = attrUse.getDecl();
                    String attrName = attr.getName();
                    if (attrName.equalsIgnoreCase(UniqueKeyCharacteristic.NAME)) {

                        entity.addCharacteristic(new UniqueKeyCharacteristic(attr.getDefaultValue().value));

                    }
                }
            }
        }

        return entity;
    }

    private Entity setBaseEntity(XSType baseType,
                               Entity entity) {

        Entity baseEntity = mEntityRegistry.getEntity(baseType.getTargetNamespace(),
                                                      baseType.getName());
        if (baseEntity == null) {

            EntityInterest interest = new EntityInterest(baseType.getTargetNamespace(),
                                                         baseType.getName(),
                                                         "extends");

            mEntityRegistry.addObserver(interest,
                                        entity);
            
            if (baseType instanceof XSComplexType) {
                baseEntity = build((XSComplexType) baseType);
                
                if (!mEntityRegistry.containsEntity(baseEntity)) {
                    mEntityRegistry.addEntity(baseEntity);
                  }
                
            }

            // EntityInterest interest = new
            // EntityInterest(baseType.getTargetNamespace(),
            // baseType.getName(),
            // "extends");

           
        }
        else {
            entity.setExtendEntity(baseEntity);
        }
        
        return baseEntity;
    }

    private void setCharacteristics(XSComplexType complexType,
                                    Entity entity) {

        for (XSAttributeUse attribute : complexType.getAttributeUses()) {
            String name = attribute.getDecl()
                                   .getName();
            if (name.equals(HierarchicalCharacteristic.NAME)) {
                entity.addImplementInterface(Hierarchical.class.getName());
            }
            else if (name.equals(NameableCharacteristic.NAME)) {
                entity.addImplementInterface(Nameable.class.getName());
            }
        }

    }
}