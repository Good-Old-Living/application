package meru.application.designer.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import meru.app.config.AppConfig;
import meru.application.designer.Application;
import meru.persistence.EntityQuery;
import meru.persistence.PersistentStore;
import meru.repository.metadata.file.FileMetadataRepository;

public class DesignerResourceStore implements PersistentStore {

    private File mAppRoot;
    private FileMetadataRepository mMetadataRepository;
    private ApplicationRepository mApplicationRepository;

    public DesignerResourceStore(AppConfig appConfig) {

        String appHome = appConfig.getProperty("applications-home");
        mAppRoot = new File(appHome);

        mAppRoot.mkdir();

        mMetadataRepository = new FileMetadataRepository(mAppRoot);
        mApplicationRepository = new ApplicationRepository(mMetadataRepository);

    }

    @Override
    public <T> T createEntity(T resource) {
        return null;
    }

    @Override
    public <T> T updateEntity(T resource) {
        return null;
    }

    @Override
    public <T> void deleteEntity(T resource) {
    }

    @Override
    public <T> void deleteEntity(Class<T> resourceClass,
                                 Object id) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getEntity(Class<T> resourceClass,
                           Object id) {
        return (T) mApplicationRepository.getApplication((String) id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getEntities(EntityQuery<T> entityQuery) {
    	if (entityQuery.getEntityClass().getSimpleName().equals("Property")) {
    		return (List<T>) new ArrayList<Object>(0);
    	}
        return (List<T>) mApplicationRepository.getApplications();
    }

    @Override
    public <T> T getFirstEntity(EntityQuery<T> entityQuery) {
        return null;
    }

    @Override
    public long nextSequenceNumber(String sequenceName) {
        return 0;
    }

    @Override
    public void closeCurrentThreadSession() {

    }

    @Override
    public <T> EntityQuery<T> createQuery(Class<T> entityClass) {
        if (entityClass == Application.class || entityClass.getSimpleName().equals("Property")) {
            return new EntityQueryImpl<T>(entityClass);
        }

        return null;
    }

    class EntityQueryImpl<T> extends EntityQuery<T> {

        public EntityQueryImpl(Class<T> entityClass) {
            super(entityClass);
        }

    }
}
