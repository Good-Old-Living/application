package meru.application.designer.builder.resource.db.mysql;

import java.io.IOException;
import java.util.List;

import meru.application.designer.builder.EnumBoolean;
import meru.application.designer.builder.resource.db.SQLBuilder;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.DataType;
import meru.application.designer.domain.model.Entity;
import meru.application.designer.domain.model.Facet;
import meru.application.designer.domain.model.character.Characteristic;
import meru.application.designer.domain.model.character.UniqueKeyCharacteristic;

public class MySQLBuilder extends SQLBuilder {

  // private int index;

  public MySQLBuilder() {

    super("mysql");
  }

  public void buildSQL() throws IOException {

    mFileWriter.writeln(new String[] { "drop table if exists ", mTableName, ";" });

    mTableWriter.writeln(new String[0])
                .writeln(new String[] { "create table ", mTableName, " (" });

    mTableWriter.indent();
    addColumns(mEntity);

    addConstraints(mEntity);

    mTableWriter.outdent();

    mTableWriter.writeln(new String[0])
                .writeln(") ENGINE=InnoDB;");

  }

  private void addColumns(Entity entity) throws IOException {

    if (entity.getExtendEntity() != null) {
      addColumns(entity.getExtendEntity());

      if (entity.getAttributes() != null) {

        mTableWriter.write(new String[] { "," })
                    .writeln(new String[0]);
      }
    }

    if (entity.getAttributes() == null)
      return;

    int count = 0;
    for (Attribute attribute : entity.getAttributes()) {

      if (attribute.isTransient()) {
        continue;
      }

      if (attribute.isComplexType() && !attribute.isReference()) {
        // continue;
      }

      if (count++ > 0) {
        mTableWriter.writeln(",");
      }

      MySQLDataType dataType = MySQLDataType.getSQLDataType(attribute.getDataType());

      mTableWriter.writeIndent()
                  .write(attribute.getDatabaseColumnName())
                  .write(" ");

      if (dataType.getDataType() == DataType.STRING) {
        Facet maxLength = attribute.getFacet("maxLength");

        int maxLengthValue = VARCHAR_MAX_LENGTH;

        if (maxLength != null) {
          maxLengthValue = ((Integer) maxLength.getValue()).intValue();
        }

        if (maxLengthValue > 5000) {
          mTableWriter.write(MySQLDataType.TEXT.getType());
        }
        else {

          String type = dataType.getType();
          mTableWriter.write(type);

          mTableWriter.write(new String[] { "(", String.valueOf(maxLengthValue), ")" });

        }
      }
      else {
        mTableWriter.write(dataType.getType());
      }

      if (attribute.getDataType()
                   .isEnum()) {
        Facet maxLength = attribute.getFacet("maxLength");

        int maxLengthValue = VARCHAR_MAX_LENGTH;

        if (maxLength != null) {
          maxLengthValue = ((Integer) maxLength.getValue()).intValue();
        }
        else if (attribute.getDataType()
                          .isEnum()) {
          maxLengthValue = ENUM_MAX_LENGTH;
        }

        mTableWriter.write(new String[] { "(", String.valueOf(maxLengthValue), ")" });
      }

      if (attribute.isRequired()) {
        mTableWriter.write(" not null");
      }
      else {
        if (dataType == MySQLDataType.DATE_TIME) {
          mTableWriter.write(" null");
        }
      }

      if (attribute.isId()) {
        mTableWriter.write(" primary key");

        String value = attribute.getAttribute("sequence");

        if (value != null) {

          if (value.equalsIgnoreCase("Identity")) {

            mTableWriter.write(" auto_increment");
          }
        }
      }

      if (attribute.getDefaultValue() != null) {
        String defaultValue = attribute.getDefaultValue();

        DataType attrDataType = attribute.getDataType();

        if (attrDataType.isBoolean()) {
          EnumBoolean bool = EnumBoolean.getEnumBoolean(Boolean.valueOf(defaultValue)
                                                               .booleanValue());
          defaultValue = bool.booleanSQLString();

        }
        else if (attrDataType == DataType.DATE) {

          if (!defaultValue.equals("sysdate")) {
            throw new RuntimeException("Invalid default value for DATE : " + defaultValue);
          }

          defaultValue = "current_date";
        }
        else if (attrDataType == DataType.DATE_TIME) {

          if (!defaultValue.equals("systimestamp")) {
            throw new RuntimeException("Invalid default value for DATETIME : " + defaultValue);
          }

          defaultValue = "current_timestamp";
        }

        mTableWriter.write(new String[] { " default ", defaultValue });
      }

    }
  }

  private void addConstraints(Entity entity) throws IOException {

    if (entity.getExtendEntity() != null) {
      addConstraints(entity.getExtendEntity());
    }

    if (entity.getAttributes() != null) {

      for (Attribute attribute : entity.getAttributes()) {

        if (attribute.isUnique()) {

          mTableWriter.writeln(",");
          mTableWriter.writeIndent()
                      .write(new String[] { "UNIQUE KEY (", attribute.getDatabaseColumnName(), ")" });
        }

      }
    }

    if (entity.containsCharacteristics()) {
      for (Characteristic<?> characteristic : entity.getCharacteristics()) {

        String name = characteristic.getName();
        if (name.equalsIgnoreCase(UniqueKeyCharacteristic.NAME)) {

          @SuppressWarnings("unchecked")
          List<String[]> keyList = (List<String[]>) characteristic.getValue();

          for (String[] attrs : keyList) {

            String indexName = UniqueKeyCharacteristic.SHORT_NAME + "_" + entity.getDatabaseTableName(); // index++;
            String indexColumns = "";
            for (String attrName : attrs) {
              indexColumns += "_" + attrName;
            }

            indexName += "_" + String.valueOf(indexColumns.hashCode())
                                     .replace('-',
                                              '_');

            mTableWriter.writeln(",");
            mTableWriter.writeIndent()
                        .write("CONSTRAINT " + indexName + " UNIQUE (");

            int i = 0;
            for (String attrName : attrs) {

              Attribute attr = mEntity.getAttributeInHierarchy(attrName);
              if (attr == null) {

                throw new RuntimeException("Unknown attribute '" + attrName + "' in uniqueKey of the entity '"
                    + mEntity.getName() + "'");
              }

              if (i++ > 0) {
                mTableWriter.write(",");
              }

              mTableWriter.write(attr.getDatabaseColumnName());

            }

            mTableWriter.write(")");
          }
        }

      }
    }

  }
}