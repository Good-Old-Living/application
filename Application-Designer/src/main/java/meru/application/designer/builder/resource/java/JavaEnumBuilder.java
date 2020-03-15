package meru.application.designer.builder.resource.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import meru.application.designer.domain.model.Argument;
import meru.application.designer.domain.model.ArgumentValue;
import meru.application.designer.domain.model.DataType;
import meru.application.designer.domain.model.EnumEntity;
import meru.application.designer.domain.model.EnumValue;
import meru.application.designer.domain.model.Visibility;

public class JavaEnumBuilder extends JavaEntityBuilder<EnumEntity> {
  
  public JavaEnumBuilder() {

  }
  public boolean isBuildable() {
    return mEntity instanceof EnumEntity;
  }


  public void addEntityDeclaration() throws IOException {

    this.mFileWriter.write(new String[] { "public enum ",
                                         ((EnumEntity) this.mEntity).getName() });

    this.mFileWriter.writeln(" {");
  }


  public void postEntityDeclaration()
      throws IOException {

    int count = 0;

    for (EnumValue enumValue : ((EnumEntity) this.mEntity).getEnumValues()) {
      String name = getEnumName(enumValue);

      if (count++ > 0) {
        this.mFileWriter.writeln(",");
      }

      if (((EnumEntity) this.mEntity).getDataType() != DataType.STRING)
        continue;
      this.mFileWriter.writeIndent()
                      .write(new String[] {
                                           name, "(" });

      int argCount = 0;

      for (ArgumentValue argValue : enumValue.getArgumentValues()) {
        if (argCount++ > 0) {
          this.mFileWriter.write(new String[] { ", " });
        }

        addArgumentValue(argValue);
      }

      this.mFileWriter.write(new String[] { ")" });
    }

    this.mFileWriter.write(new String[] { ";" });

    this.mFileWriter.writeln(new String[0]);
    this.mFileWriter.writeln(new String[0]);
  }


  public void addPropertyAccessors() throws IOException {

    addPropertyAccessors(true);
  }


  public void postAttributes() throws IOException {

    if ((((EnumEntity) this.mEntity).getEnumValues() == null)
        || (((EnumEntity) this.mEntity).getEnumValues().isEmpty())) {
      return;
    }

    EnumValue enumValue = (EnumValue) ((EnumEntity) this.mEntity).getEnumValues()
                                                                 .get(0);
    List<Argument> argList = new ArrayList<Argument>();
    for (ArgumentValue argValue : enumValue.getArgumentValues()) {
      argList.add(argValue);
    }

    startConstructor(Visibility.PRIVATE, argList);

    endConstructor();
  }


  private void addArgumentValue(ArgumentValue argValue) throws IOException {

    this.mFileWriter.write(new String[] { "\"",
                                         argValue.getValue().toString(),
                                         "\"" });
  }


  private String getEnumName(EnumValue enumValue) {

    return enumValue.getName()
                    .toUpperCase()
                    .replaceAll("[-/ ]", "_");
  }
}