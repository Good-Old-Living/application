package meru.application.designer.builder.resource.java.jpa;

import java.io.File;
import java.io.IOException;

import meru.application.designer.builder.resource.ResourceBuilder;
import meru.application.designer.builder.resource.SrcFileWriter;
import meru.io.jar.JarBuilder;

public class PersistenceXMLBuilder extends ResourceBuilder {

    private static final String PROP_PERSISTENCE_UNIT = "persistence.unit";
    private static final String PROP_DATASOURCE_JNDI = "persistence.datasource-jndi";
    private static final String PROP_JPA_PROVIDER_CLASS = "persistence.jpa-provider-class";

    private static final String JNDI_CONTEXT = "java:comp/env/";

    private SrcFileWriter mFileWriter;
    private File mPersistenceDir;
    private File mPersistenceFile;
    private String mProviderClass;
    private String mPersistenceUnit;
    private String mDataSource;
    private String mTransType = "RESOURCE_LOCAL";

    public void init() throws IOException {

        mPersistenceUnit = mBuilderConfig.getProperty(PROP_PERSISTENCE_UNIT);

        if (mPersistenceUnit == null || mPersistenceUnit.trim()
                                                        .length() == 0) {
            throw new RuntimeException("The property '" + PROP_PERSISTENCE_UNIT
                    + "' must be specified in config.ini");
        }

        mDataSource = mBuilderConfig.getProperty(PROP_DATASOURCE_JNDI);

        if (mDataSource == null || mDataSource.trim()
                                              .length() == 0) {
            throw new RuntimeException("The property '" + PROP_DATASOURCE_JNDI
                    + "' must be specified in config.ini");
        }

        mProviderClass = mBuilderConfig.getProperty(PROP_JPA_PROVIDER_CLASS);

        if (mProviderClass == null || mProviderClass.trim()
                                                    .length() == 0) {
            throw new RuntimeException("The property '"
                    + PROP_JPA_PROVIDER_CLASS
                    + "' must be specified in config.ini");
        }

        mPersistenceDir = new File(mBuilderConfig.getDeployHome(),
                                   "persistence");
        File metaInfDir = new File(mPersistenceDir,
                                   "META-INF");
        metaInfDir.mkdirs();

        mPersistenceFile = new File(metaInfDir,
                                    "persistence.xml");

        mFileWriter = new SrcFileWriter(mPersistenceFile);

        startPersistence();

        /*
         * String accPackage = "kaanthal.app.account";
         * 
         * try { build(new Entity(accPackage, "Permission")); build(new
         * Entity(accPackage, "Role")); build(new Entity(accPackage,
         * "UserRoleAssoc")); build(new Entity(accPackage, "User")); build(new
         * Entity(accPackage, "Resource")); } catch (Exception e) { throw new
         * RuntimeException(e); }
         */

    }

    public void build() throws IOException {

        String packageName = mEntity.getJavaPackageName();

        System.out.println("         ######    Adding the class to persistence.xml : "
                + packageName + "." + mEntity.getName());

        mFileWriter.writeln()
                   .writeIndent()
                   .write(new String[] { "<class>" });

        if (packageName != null) {
            mFileWriter.write(new String[] { packageName, "." });
        }

        mFileWriter.write(new String[] { mEntity.getName(), "</class>" });

    }

    private void startPersistence() throws IOException {

        mFileWriter.writeln("<?xml version='1.0'?>");

        mFileWriter.writeln("<persistence xmlns='http://java.sun.com/xml/ns/persistence' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd' version='2.0'>");

        mFileWriter.indent()
                   .writeIndent();
        mFileWriter.write(new String[] { "<persistence-unit  name=\"",
                                        mPersistenceUnit,
                                        "\"" });

        if (mTransType != null) {
            mFileWriter.write(new String[] { " transaction-type=\"",
                                            mTransType,
                                            "\"" });
        }

        mFileWriter.writeln(">");

        mFileWriter.indent();
        if (mProviderClass != null) {
            mFileWriter.writeIndent()
                       .writeln(new String[] { "<provider>",
                                              mProviderClass,
                                              "</provider>" });
        }

        if (mDataSource != null) {
            mFileWriter.writeIndent()
                       .writeln(new String[] { "<jta-data-source>",
                                              JNDI_CONTEXT,
                                              mDataSource,
                                              "</jta-data-source>" });
        }
    }

    private void endPersistence() throws IOException {

        mFileWriter.writeln()
                   .writeIndent();
        mFileWriter.writeln("<shared-cache-mode>NONE</shared-cache-mode>");

        addProperties();
        mFileWriter.writeln()
                   .outdent()
                   .writeIndent()
                   .writeln(new String[] { "</persistence-unit>" });

        mFileWriter.outdent()
                   .write(new String[] { "</persistence>" });
    }

    private void addProperties() throws IOException {

        /*
         * Map<String, String> propMap = mBuilderConfig.getProperties();
         * 
         * if (propMap != null && !propMap.isEmpty()) { mFileWriter.writeln()
         * .writeIndent() .write(new String[] { "<properties>" });
         * 
         * mFileWriter.indent();
         * 
         * for (Map.Entry<String, String> property : propMap.entrySet()) {
         * mFileWriter.writeln() .writeIndent() .write(new String[] {
         * "<property name=\"", property.getKey(), "\" value=\"",
         * property.getValue(), "\"/>" }); }
         * 
         * mFileWriter.writeln() .outdent() .writeIndent() .write(new String[] {
         * "</properties>" });
         * 
         * // mFileWriter.outdent(); }
         */
    }

    public void finish() throws IOException {

        endPersistence();

        mFileWriter.close();

        File jarFile = new File(mBuilderConfig.getDeployHome(),
                                "app-persistence.jar");

        JarBuilder jarBuilder = new JarBuilder(jarFile,
                                               mPersistenceDir);

        jarBuilder.build();
    }

    public void onError(Exception exception) throws Exception {
        mFileWriter.close();

    }
}