package meru.application.designer.builder.resource.java;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import meru.application.designer.builder.EnumBoolean;
import meru.application.designer.builder.resource.ResourceBuilder;
import meru.application.designer.builder.resource.SrcFileWriter;
import meru.application.designer.domain.model.Argument;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.DataType;
import meru.application.designer.domain.model.Entity;
import meru.application.designer.domain.model.Visibility;
import app.entity.YesNoBoolean;

public abstract class JavaEntityBuilder<T extends Entity> extends
        ResourceBuilder {

    protected File mSrcDir;
    protected SrcFileWriter mFileWriter;
    private List<Decorator> mDecorators;

    public JavaEntityBuilder() {

        mDecorators = new ArrayList<Decorator>(1);
    }

    public void build() throws Exception {

        if (mEntity.isInherited()) return;
        
        mSrcDir = new File(mBuilderConfig.getDeployHome(),
                           "src");

        File file = null;
        String classFileName = mEntity.getName() + ".java";

        if (mEntity.getPackage() == null) {
            file = new File(mSrcDir,
                            classFileName);
        }
        else {
            File parentDir = new File(mSrcDir,
                                      mEntity.getPackage()
                                             .replace('.',
                                                      '/'));

            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            file = new File(parentDir,
                            classFileName);
        }

        mFileWriter = new SrcFileWriter(file);
        setFileWriter();

        if (mEntity.getPackage() != null) {
            mFileWriter.writeln(new String[] { "package ",
                                              mEntity.getJavaPackageName(),
                                              ";" });
            mFileWriter.writeln(new String[0]);
        }

        postPackage();

        if (mEntity.getImports() != null) {
            for (String importClass : mEntity.getImports()) {
                mFileWriter.writeln(new String[] { "import ", importClass, ";" });
            }
        }

        if (mEntity.getExtendEntity() != null && mEntity.getExtendEntity()
                                                        .getPackage() != null) {
            mFileWriter.writeln(new String[] { "import ",
                                              mEntity.getExtendEntity()
                                                     .getJavaPackageName(),
                                              ".",
                                              mEntity.getExtendEntity()
                                                     .getName() + ";" });
        }

        postImports();

        mFileWriter.writeln(new String[0]);

        decorate(mEntity);
        addEntityDeclaration();

        mFileWriter.writeln(new String[0]);
        mFileWriter.indent();

        postEntityDeclaration();

        addAttributes();

        postAttributes();

        addPropertyAccessors();

        addToStringMethod();

        close();

    }

    public abstract void addEntityDeclaration() throws Exception;

    public void postPackage() throws Exception {

    }

    public void postImports() throws Exception {

    }

    public void postEntityDeclaration() throws Exception {

    }

    public void postAttributes() throws Exception {

    }

    public JavaEntityBuilder<T> addAttributes() throws Exception {

        List<Attribute> attributes = mEntity.getAttributes();
        if (attributes != null) {
            for (Attribute attribute : mEntity.getAttributes()) {
                decorate(attribute);
                addAttribute(attribute);
            }
        }

        return this;
    }

    public JavaEntityBuilder<T> addAttribute(Attribute attribute) throws IOException {

        mFileWriter.writeIndent();
        if (attribute.getVisibility() != null) {
            mFileWriter.write(new String[] { attribute.getVisibility()
                                                      .toString(), " " });
        }

        JavaDataType dataType = JavaDataType.getJavaDataType(attribute.getDataType());

        if (attribute.isMultiValued()) {
            if (dataType.isEnum()) {
                mFileWriter.writeln(new String[] { List.class.getName(),
                                                  "<",
                                                  JavaDataType.STRING.getType(),
                                                  "> ",
                                                  getMemberFieldName(attribute),
                                                  ";" });
            }
            else {
                mFileWriter.writeln(new String[] { List.class.getName(),
                                                  "<",
                                                  dataType.getType(),
                                                  "> ",
                                                  getMemberFieldName(attribute),
                                                  ";" });
            }

        }
        else if (dataType.isEnum()) {
            mFileWriter.writeln(new String[] { JavaDataType.STRING.getType(),
                                              " ",
                                              getMemberFieldName(attribute),
                                              ";" });
        }
        else {
            String type = (attribute.isBooleanType()) ? getBooleanType(attribute)
                    : dataType.getType();

            if (attribute.isTransient()) {
                mFileWriter.write(new String[] { "transient " });
            }

            mFileWriter.write(type)
                       .write(" ")
                       .write(getMemberFieldName(attribute));
            if (attribute.isBooleanType() && !attribute.isTransient()) {
            	mFileWriter.write(" = \""+YesNoBoolean.No.getValue()+"\"");
            }
            

            mFileWriter.writeln(";");
        }

        if ((attribute.isBooleanType()) && (!attribute.isTransient())) {
            Attribute boolAttr = getBooleanAttribute(attribute);
            addAttribute(boolAttr);
        }
        return this;
    }

    public JavaEntityBuilder<T> startConstructor(Visibility visibility,
                                                 List<Argument> arguments) throws IOException {

        startMethod(visibility,
                    null,
                    mEntity.getName(),
                    (arguments == null) ? null
                            : (Argument[]) arguments.toArray(new Argument[0]));

        if (arguments != null) {
            for (Argument argument : arguments) {
                addMethodContent(getMemberFieldName(argument) + " = "
                        + argument.getName() + ";");
            }
        }

        return this;
    }

    public JavaEntityBuilder<T> startConstructor(Visibility visibility,
                                                 Argument[] arguments) throws IOException {

        return startMethod(visibility,
                           null,
                           mEntity.getName(),
                           arguments);
    }

    public JavaEntityBuilder<T> endConstructor() throws IOException {

        return endMethod();
    }

    public void addPropertyAccessors() throws IOException {

        addPropertyAccessors(false);
    }

    public void addPropertyAccessors(boolean readOnly) throws IOException {

        List<Attribute> attributes = mEntity.getAttributes();

        if (attributes != null) {

            for (Attribute attribute : mEntity.getAttributes()) {
                addPropertyAccessors(attribute,
                                     readOnly);

                if ((attribute.isBooleanType()) && (!attribute.isTransient())) {
                    Attribute boolAttr = getBooleanAttribute(attribute);
                    addPropertyAccessors(boolAttr,
                                         readOnly);
                }
            }
        }
    }

    public void addPropertyAccessors(Attribute attribute,
                                     boolean readOnly) throws IOException {

        String javaName = Introspector.decapitalize(attribute.getName());

        JavaDataType dataType = JavaDataType.getJavaDataType(attribute.getDataType());

        if (dataType.isEnum()) {
            dataType = JavaDataType.STRING;
        }
        else if (attribute.isBooleanType()) {
            if (attribute.isTransient())
                dataType = JavaDataType.BOOLEAN;
            else {
                dataType = JavaDataType.STRING;
            }

        }

        String retType = null;
        if (attribute.isMultiValued())
            retType = List.class.getName() + "<" + dataType.getType() + ">";
        else {
            retType = dataType.getType();
        }

        startMethod(Visibility.PUBLIC,
                    retType,
                    "get" + attribute.getAttributeBeanName());

        if (attribute.isOwningAttribute()) {
            mFileWriter.writeln(new String[0])
                       .writeIndent()
                       .write(new String[] { "if (",
                                            getMemberFieldName(attribute),
                                            " == null)" });
            openBlock();

            if (attribute.isMultiValued())
                mFileWriter.writeIndent()
                           .writeln(new String[] { getAssignMemberFieldName(attribute),
                                                  " = new ",
                                                  ArrayList.class.getName(),
                                                  "<",
                                                  dataType.getType(),
                                                  ">();" });
            else {
                // mFileWriter.writeIndent()
                // .writeln(new String[] { getAssignMemberFieldName(attribute),
                // " = new ",
                // dataType.getType(),
                // "();" });
            }
            endBlock();
        }

        mFileWriter.writeIndent();
        if ((attribute.isBooleanType()) && (attribute.isTransient())) {
            String attrName = getBooleanStringAttributeName(attribute);
            addMethodContent("return " + attrName + " != null && " + attrName
                    + ".equals(" + EnumBoolean.TRUE.booleanJavaString() + ");");
        }
        else {
            addMethodContent("return " + getMemberFieldName(attribute) + ";");
        }

        endMethod();

        if (readOnly)
            return;
        DataType argDataType = null;
        if (attribute.isBooleanType()) {
            if (attribute.isTransient())
                argDataType = DataType.BOOLEAN;
            else
                argDataType = DataType.STRING;
        }
        else {
            argDataType = attribute.getDataType();
        }

        Argument argument = new Argument(argDataType,
                                         javaName,
                                         attribute.isMultiValued());

        startMethod(Visibility.PUBLIC,
                    "void",
                    "set" + attribute.getAttributeBeanName(),
                    argument);

        if ((attribute.isBooleanType()) && (attribute.isTransient())) {
            String attrName = getBooleanStringAttributeName(attribute);
            addMethodContent(attrName + " = " + javaName + " ? "
                    + EnumBoolean.TRUE.booleanJavaString() + " : "
                    + EnumBoolean.FALSE.booleanJavaString() + ";");
        }
        else {
            addMethodContent(getAssignMemberFieldName(attribute) + " = "
                    + javaName + ";");
        }

        endMethod();

        if (attribute.isBooleanType()) {
            addBooleanMethod(attribute);
        }
    }

    private void addBooleanMethod(Attribute attribute) throws IOException {

        startMethod(Visibility.PUBLIC,
                    "boolean",
                    attribute.getName());

        addMethodContent("return \"Y\".equals(" + attribute.getName() + ");");

        endMethod();
    }

    public JavaEntityBuilder<T> startMethod(Visibility visibility,
                                            String returnType,
                                            String name) throws IOException {

        return startMethod(visibility,
                           returnType,
                           name,
                           (Argument[]) null);
    }

    public JavaEntityBuilder<T> startMethod(Visibility visibility,
                                            String returnType,
                                            String name,
                                            Argument argument) throws IOException {

        return startMethod(visibility,
                           returnType,
                           name,
                           new Argument[] { argument });
    }

    public JavaEntityBuilder<T> startMethod(Visibility visibility,
                                            String returnType,
                                            String name,
                                            Argument[] arguments) throws IOException {

        mFileWriter.writeln(new String[0])
                   .writeIndent();

        if (visibility != null) {
            mFileWriter.write(new String[] { visibility.toString(), " " });
        }

        if (returnType != null) {
            mFileWriter.write(new String[] { returnType, " " });
        }

        mFileWriter.write(new String[] { name, "(" });

        addArguments(arguments);

        mFileWriter.write(new String[] { ")" });
        openBlock();

        return this;
    }

    public JavaEntityBuilder<T> addMethodContent(String str) throws IOException {

        mFileWriter.writeln(new String[0])
                   .writeIndent()
                   .writeln(str);

        return this;
    }

    public JavaEntityBuilder<T> endMethod() throws IOException {

        endBlock();

        return this;
    }

    public void close() throws IOException {

        mFileWriter.writeln("}")
                   .close();
    }

    private void openBlock() throws IOException {

        mFileWriter.writeln(" {");
        mFileWriter.indent();
    }

    private void endBlock() throws IOException {

        mFileWriter.outdent();
        mFileWriter.writeIndent()
                   .writeln("}");
    }

    private String getMemberFieldName(Attribute attribute) {

        return attribute.getName();
    }

    private String getAssignMemberFieldName(Attribute attribute) {

        return "this." + attribute.getName();
    }

    private String getMemberFieldName(Argument attribute) {

        return "this." + attribute.getName();
    }

    private void addArguments(Argument[] arguments) throws IOException {

        if (arguments != null) {
            int count = 0;
            for (Argument argument : arguments) {
                if (count++ > 0) {
                    mFileWriter.write(new String[] { ", " });
                }

                JavaDataType dataType = JavaDataType.getJavaDataType(argument.getDataType());

                if (dataType.isEnum()) {
                    dataType = JavaDataType.STRING;
                }

                String argType = null;
                if (argument.isMultiValued())
                    argType = List.class.getName() + "<" + dataType.getType()
                            + ">";
                else {
                    argType = dataType.getType();
                }

                mFileWriter.write(new String[] { argType,
                                                " ",
                                                argument.getName() });
            }
        }
    }

    private void addToStringMethod() throws IOException {

        Attribute nameAttr = mEntity.getNameAttribute();

        if (nameAttr == null || !(nameAttr.getDataType() == DataType.STRING))
            return;
        startMethod(Visibility.PUBLIC,
                    "String",
                    "toString");
        mFileWriter.writeIndent();
        mFileWriter.writeln(new String[] { "return ",
                                          getMemberFieldName(nameAttr),
                                          ";" });

        endMethod();
    }

    public void addDecorator(Decorator decorator) {

        mDecorators.add(decorator);
    }

    private void setFileWriter() throws Exception {

        for (Decorator decorator : mDecorators)
            decorator.setFileWriter(mFileWriter);
    }

    private void decorate(Entity entity) throws Exception {

        for (Decorator decorator : mDecorators)
            decorator.decorate(entity);
    }

    private void decorate(Attribute attribute) throws Exception {

        for (Decorator decorator : mDecorators)
            decorator.decorate(attribute);
    }

    public static String getBooleanType(Attribute attribute) {

        if (!attribute.isBooleanType()) {
            throw new RuntimeException("Not a boolean type");
        }

        return (attribute.isTransient()) ? "boolean" : "String";
    }

    public static Attribute getBooleanAttribute(Attribute attribute) {

        Attribute boolAttr = null;
        try {
            boolAttr = (Attribute) attribute.clone();
            boolAttr.setName(attribute.getName() + "Boolean");
            boolAttr.setTransient(true);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return boolAttr;
    }

    public static String getBooleanStringAttributeName(Attribute attribute) {

        if (!attribute.isBooleanType()) {
            throw new RuntimeException("Not a boolean type");
        }

        String name = attribute.getName();

        return name.substring(0,
                              name.indexOf("Boolean"));
    }

    public void onError(Exception exception) throws Exception {
        if (mFileWriter != null) {
            mFileWriter.close();
        }
    }
}