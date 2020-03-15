package meru.application.designer.builder.resource.java;

import java.io.File;
import java.io.IOException;

import meru.app.registry.EntityClassRegistry;
import meru.application.designer.builder.resource.ResourceBuilder;
import meru.application.designer.builder.resource.SrcFileWriter;

public class ResourceClassProviderBuilder
    extends ResourceBuilder {

  protected SrcFileWriter mFileWriter;


  public void init() throws Exception {

    
    String className = mBuilderConfig.getApplicationName().replaceAll("[-]", "_")+"EntityClassRegistry";
    
    String baseClass =  EntityClassRegistry.class.getName(); //mBuilderConfig.getEntityClassRegistry().getName();
    
    if (baseClass == null) {
    
      //baseClass = DesignerEntityClassRegistry.class.getName();
    }

    File srcFile = new File(mBuilderConfig.getDeployHome(), "src");
    String classFileName = className + ".java";

    srcFile.mkdirs();
    
    File classFile = new File(srcFile, "app"+File.separator+classFileName);
    classFile.getParentFile().mkdir();

    mFileWriter = new SrcFileWriter(classFile);


    mFileWriter.writeln("package app;");
    
    mFileWriter.write(new String[] {"public", " class ", className });

    mFileWriter.write(new String[] { " extends ",
                                     baseClass });

    mFileWriter.writeln(" {");
    
    mFileWriter.indent();
    mFileWriter.writeIndent();
    mFileWriter.writeln(new String[]{"protected void populateClassMap() {"});
    
    mFileWriter.indent();
    mFileWriter.writeIndent();
    mFileWriter.writeln("super.populateClassMap();");
  }


  @Override
  public void build() throws Exception {
    mFileWriter.writeIndent();
    mFileWriter.writeln(new String[]{"addResourceClass(\"", 
                                     mEntity.getName(), 
                                     "\", ",
                                     mEntity.getFullClassName(),
                                     ".class);"});
  }


  public void finish() throws IOException {
    mFileWriter.outdent();
    mFileWriter.writeIndent();
    mFileWriter.writeln("}");
    
    mFileWriter.outdent();
    mFileWriter.writeIndent();
    
    mFileWriter.writeln("}").close();
  }
  
  public void onError(Exception exception) throws Exception {
    if (mFileWriter != null) {
      mFileWriter.close();
    }
  }
  
}
