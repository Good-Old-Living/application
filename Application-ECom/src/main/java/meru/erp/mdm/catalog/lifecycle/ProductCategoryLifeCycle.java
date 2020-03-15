package meru.erp.mdm.catalog.lifecycle;

import java.util.List;

import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.persistence.AttributeOperator;
import meru.persistence.EntityQuery;
import app.erp.mdm.catalog.ProductCategory;

public class ProductCategoryLifeCycle extends
    AbstractEntityLifeCycle<ProductCategory> {

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(ProductCategory category) {

    setQualifiedName(category);

    return true;
  }

  @Override
  public boolean preModify(ProductCategory category) {

    setQualifiedName(category);

    ProductCategory currCategory = appEngine.get(ProductCategory.class,
                                                 category.getId());
    if (!currCategory.getQualifiedName()
                     .equals(category.getQualifiedName())) {

      String currQName = currCategory.getQualifiedName();
      String newQName = category.getQualifiedName();
      EntityQuery<ProductCategory> entityQuery = appEngine.createQuery(ProductCategory.class);
      entityQuery.addQueryParameter("qualifiedName",
                                    AttributeOperator.LIKE,
                                    currCategory.getQualifiedName());
      int currLength = currQName.length();
      List<ProductCategory> categories = appEngine.get(entityQuery);
      for (ProductCategory cat : categories) {

        String qName = cat.getQualifiedName();

        String nQName = newQName;

        if (qName.length() > currLength) {
          nQName += qName.substring(currLength);
        }
        cat.setQualifiedName(nQName);
        appEngine.save(cat);

      }
    }
    return true;
  }

  private void setQualifiedName(ProductCategory category) {
    Long parentId = category.getParentId();
    String dn = null;

    dn = "/" + category.getName();
    if (parentId != null) {
      ProductCategory parent = appEngine.get(ProductCategory.class,
                                             parentId);
      dn = parent.getQualifiedName() + dn;
    }

    category.setQualifiedName(dn);
  }

}
