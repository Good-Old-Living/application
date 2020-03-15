package meru.application.designer.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import meru.application.designer.Application;
import meru.application.designer.domain.model.character.Characteristic;
import meru.sys.JVM;

public class Entity implements EntityObserver {
  public static final String OBSERVE_TYPE_EXTEND_ENTITY = "extends";
  private String mPackage;
  private String mName;
  private Entity mExtendEntity;
  private boolean mIsAbstract;
  private boolean mIsTransient;
  private List<String> mImplementInterfaces;
  private List<Attribute> mAttributes;
  private List<String> mImports;
  private List<Characteristic<?>> mCharacteristics;

  private boolean mIsInherited;
  
  private Map<String, String> mContextProperties;
  

  public Entity(String packageName, String name) {

    if (packageName != null && packageName.trim()
                                          .length() > 0) {
      mPackage = packageName;
    }
    mName = name;

    mImplementInterfaces = new ArrayList<String>(1);
    mAttributes = new ArrayList<Attribute>();
    mImports = new ArrayList<String>();
  }

  public void setContextProperties(Map<String, String> contextProperties) {
    mContextProperties = contextProperties;
  }

  public String getContextProperty(String propertyName) {

    if (mContextProperties == null) {
      return null;
    }

    return mContextProperties.get(propertyName);
  }

  public void setAbstract(boolean isAbstract) {

    mIsAbstract = isAbstract;
  }

  public boolean isAbstract() {

    return mIsAbstract;
  }

  public void setTransient(boolean isTransient) {

    mIsTransient = isTransient;
  }

  public boolean isTransient() {

    return mIsTransient;
  }
  
  public void setInherited(boolean isInherited) {

      mIsInherited = isInherited;
    }

    public boolean isInherited() {

      return mIsInherited;
    }

  public void setExtendEntity(Entity extendEntity) {

    mExtendEntity = extendEntity;
  }

  public Entity getExtendEntity() {

    return mExtendEntity;
  }

  public String getPackage() {

    return mPackage;
  }

  public String getName() {

    return mName;
  }

  public void addImplementInterface(String name) {

    mImplementInterfaces.add(name);
  }

  public List<String> getImplementInterfaces() {

    return mImplementInterfaces;
  }

  protected boolean containsCharacteristic(Characteristic<?> characteristic) {
    if (mCharacteristics != null) {
      for (Characteristic<?> charac : mCharacteristics) {
        if (charac.equals(characteristic)) {
          return true;
        }
      }
    }
    return false;
  }

  public void addCharacteristic(Characteristic<?> characteristic) {

    if (mCharacteristics == null) {
      mCharacteristics = new ArrayList<Characteristic<?>>(1);
    }

    if (containsCharacteristic(characteristic)
        || (mExtendEntity != null && mExtendEntity.containsCharacteristic(characteristic))) {
      System.out.println(">> WARNING: Duplicate "
          + characteristic.getClass()
                          .getSimpleName() + ": " + characteristic);
      return;
    }

    mCharacteristics.add(characteristic);
  }

  public boolean containsCharacteristics() {
    return mCharacteristics != null && !mCharacteristics.isEmpty();
  }

  public Characteristic<?> getCharacteristics(String name) {

    if (mCharacteristics != null) {
      for (Characteristic<?> characteristic : mCharacteristics) {
        if (characteristic.getName()
                          .equals(name)) {
          return characteristic;
        }
      }
    }

    return null;
  }

  public List<Characteristic<?>> getCharacteristics() {
    return mCharacteristics;
  }

  public void setAttributes(List<Attribute> attributes) {

    mAttributes = attributes;
  }

  public void addAttribute(Attribute attribute) {

    mAttributes.add(attribute);
  }

  public List<Attribute> getAttributes() {

    return mAttributes;
  }

  public Attribute getNameAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.getName()
                     .equals("name")) {
          return attribute;
        }
      }

    }

    return null;
  }

  public Attribute getAttribute(String name) {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.getName()
                     .equals(name)) {
          return attribute;
        }
      }

    }

    return null;
  }

  public Attribute getAttributeInHierarchy(String name) {

    Entity entity = this;

    while (entity != null) {

      Attribute attribute = entity.getAttribute(name);
      if (attribute != null) {
        return attribute;
      }
      entity = entity.mExtendEntity;
    }

    return null;
  }

  public void addImport(String importstmt) {

    mImports.add(importstmt);
  }

  public List<String> getImports() {

    return mImports;
  }

  public boolean containsAttributes() {

    return (mAttributes != null) && (!mAttributes.isEmpty());
  }

  public boolean containsSimpleAttributes() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.getDataType()
                     .isSimpleType()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsIdAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.isId()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsDateAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if ((attribute.getDataType() == DataType.DATE)
            || (attribute.getDataType() == DataType.DATE_TIME)) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsMultiValuedAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.isMultiValued()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsComplexAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.getDataType()
                     .isComplexType()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsSingleValuedComplexAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.getDataType()
                     .isComplexType() && !attribute.isMultiValued()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsReferenceAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.isReference()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsComplexEnumAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.isComplexEnumType()) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean containsOwningComplexAttribute() {

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        if (attribute.isOwningAttribute()) {
          return true;
        }
      }
    }

    return false;
  }

  public void observe(EntityInterest interest,
                      Entity entity) {

    String userObject = (String) interest.getUserObject();

    if (userObject.equals("extends"))
      setExtendEntity(entity);
  }

  public String getJavaPackageName() {

    String packageName = getPackage();
    if (packageName != null) {
      return packageName.replace('/',
                                 '.');
    }

    return null;
  }

  public String getFullClassName() {

    String packageName = getJavaPackageName();

    return packageName == null ? getName() : getJavaPackageName() + "."
        + getName();
  }

  public String getDatabaseTableName() {

    String name = getName();

    name = Attribute.getDatabaseName(name);

    String tablePrefix = getContextProperty(Application.PROP_DATABASE_TABLE_PREFIX);
    if (tablePrefix != null) {
      name = tablePrefix + "_" + name;
    }

    return name;
  }

  public String toString() {

    StringBuilder strBuilder = new StringBuilder(super.getClass()
                                                      .getSimpleName() + "['"
        + mName + "' {" + mPackage + "}]");

    if (mExtendEntity != null) {
      strBuilder.append(" extends '")
                .append(mExtendEntity)
                .append("'");
    }

    if (mAttributes != null) {
      for (Attribute attribute : mAttributes) {
        strBuilder.append(JVM.SystemProperty.NEW_LINE)
                  .append("      ")
                  .append(attribute);
      }
    }

    return strBuilder.toString();
  }
}