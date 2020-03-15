package meru.application.designer.builder.xsd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meru.application.designer.builder.BuilderConfig;
import meru.application.designer.builder.EntityBuilder;
import meru.application.designer.builder.EntityRegistry;
import meru.application.designer.domain.model.Entity;

import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSimpleType;

public class SchemaBuilder implements EntityBuilder<XSSchema, List<Entity>> {
  private ComplexType mComplexType;
  private SimpleType mSimpleType;
  private EntityRegistry mEntityRegistry;

  public SchemaBuilder(EntityRegistry entityRegistry,
                       BuilderConfig builderConfig) {

    mEntityRegistry = entityRegistry;
    mComplexType = new ComplexType(entityRegistry,
                                   builderConfig);
    mSimpleType = new SimpleType();
  }

  public List<Entity> build(XSSchema schema) {
    return build(schema,
                 null);
  }

  public List<Entity> build(XSSchema schema,
                            String name) {

    List<Entity> entityList = new ArrayList<Entity>();

    Map<String, XSSimpleType> simpleTypeMap = schema.getSimpleTypes();
    XSSimpleType simpleType;
    if (simpleTypeMap != null) {
      for (Map.Entry<String, XSSimpleType> entry : simpleTypeMap.entrySet()) {
        simpleType = (XSSimpleType) entry.getValue();

        if (name == null || simpleType.getName()
                                      .equals(name)) {

          Object object = mSimpleType.build(simpleType);

          if (!(object instanceof Entity))
            continue;
          Entity entity = (Entity) object;
          if (!mEntityRegistry.containsEntity(entity)) {

            mEntityRegistry.addEntity(entity);
          }

          entityList.add(entity);
        }

      }

    }

    Map<String, XSComplexType> complexTypeMap = schema.getComplexTypes();

    if (complexTypeMap != null) {
      for (Map.Entry<String, XSComplexType> entry : complexTypeMap.entrySet()) {
        XSComplexType complexType = (XSComplexType) entry.getValue();

        if (name == null || complexType.getName()
                                       .equals(name)) {

          Entity entity = mComplexType.build(complexType);
          if (!mEntityRegistry.containsEntity(entity)) {
            mEntityRegistry.addEntity(entity);
          }

          entityList.add(entity);
        }
      }
    }

    return entityList;
  }
}