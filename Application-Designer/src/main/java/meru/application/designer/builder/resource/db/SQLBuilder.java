package meru.application.designer.builder.resource.db;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import meru.application.designer.builder.resource.ResourceBuilder;
import meru.application.designer.builder.resource.SrcFileWriter;

public abstract class SQLBuilder extends ResourceBuilder {

  protected static final int VARCHAR_MAX_LENGTH = 100;
  protected static final int ENUM_MAX_LENGTH = 5;

  protected String mDatabaseName;
  protected String mTableName;

  protected SrcFileWriter mFileWriter;
  protected StringWriter mWriter;
  protected SrcFileWriter mTableWriter;

  public SQLBuilder(String databaseName) {

    mDatabaseName = databaseName;
  }

  public void init() throws IOException {

    File sqlDir = new File(mBuilderConfig.getDeployHome(),
                           "sql");
    sqlDir.mkdir();

    File sqlFile = new File(sqlDir,
                            "schema_" + mDatabaseName + ".sql");

    mFileWriter = new SrcFileWriter(sqlFile);
    mWriter = new StringWriter();
    mTableWriter = new SrcFileWriter(mWriter);
  }

  public void build() throws IOException {

    if (mEntity.isAbstract() || mEntity.isTransient()) {
      return;
    }

    
    mTableName = mEntity.getDatabaseTableName();
    
    buildSQL();
  }

  protected abstract void buildSQL() throws IOException;

  public void finish() throws IOException {

    mFileWriter.write(new String[] { mWriter.toString() });
    mTableWriter.close();
    mFileWriter.close();
  }

  public void onError(Exception exception) throws Exception {
    
    if (mFileWriter != null) {
      mFileWriter.close();
      mTableWriter.close();
    }
    
  }
}