package meru.application.designer.builder.resource.java;

import meru.application.designer.builder.resource.SrcFileWriter;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.Entity;

public interface Decorator {
  
  public void setFileWriter(SrcFileWriter writer);


  public void decorate(Entity entity)
      throws Exception;


  public void decorate(Attribute attribute)
      throws Exception;
}