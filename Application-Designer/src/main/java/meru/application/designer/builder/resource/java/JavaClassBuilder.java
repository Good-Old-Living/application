package meru.application.designer.builder.resource.java;

import meru.application.designer.domain.model.Entity;

public class JavaClassBuilder extends JavaEntityBuilder<Entity> {

    public JavaClassBuilder() {

    }

    public void addEntityDeclaration() throws Exception {

        mFileWriter.write(new String[] { "public" });

        if (mEntity.isAbstract()) {
            mFileWriter.write(new String[] { " abstract" });
        }

        mFileWriter.write(new String[] { " class ", mEntity.getName() });

        if (mEntity.getExtendEntity() != null) {
            mFileWriter.write(new String[] { " extends ",
                                            mEntity.getExtendEntity()
                                                   .getName() });
        }

        if ((mEntity.getImplementInterfaces() != null)
                && (!mEntity.getImplementInterfaces()
                            .isEmpty())) {
            mFileWriter.write(new String[] { " implements " });
            int count = 0;

            for (String interfaceName : mEntity.getImplementInterfaces()) {
                if (count++ > 0) {
                    mFileWriter.write(new String[] { "," });
                }
                mFileWriter.write(new String[] { interfaceName });
            }

        }

        mFileWriter.writeln(" {");
    }
}