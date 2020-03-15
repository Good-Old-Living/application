package meru.image.lifecycle;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import meru.app.engine.entity.AbstractEntityLifeCycle;
import meru.image.ImageSize;
import meru.image.repository.ImageRepository;
import meru.persistence.EntityQuery;
import app.domain.image.EntityImage;

public abstract class EntityImageLifeCycle<T extends EntityImage> extends AbstractEntityLifeCycle<T> {

    public ImageRepository<T> imageRepository;
    protected final List<T> EMPTY_LIST = new ArrayList<T>(0);

    protected String getImgHome() {
        return "entity";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {

        ParameterizedType parameterizedType = (ParameterizedType) this.getClass()
                                                                      .getGenericSuperclass();

        Class<T> entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];

        imageRepository = new ImageRepository<T>(this.appContext.getApplicationRoot(),
                                                 getImgHome(),
                                                 entityClass,
                                                 ImageSize.Size150x150,
                                                 ImageSize.Size300x300);
        configureImageRepository(imageRepository);
    }

    
    protected void configureImageRepository(ImageRepository<T> imageRepository) {
        
    }
    
    @Override
    public boolean preCreate(T image) {
        imageRepository.storeImage(image);
        return false;
    }

    @Override
    public boolean preModify(T image) {
        imageRepository.storeImage(image);
        return false;
    }

    @Override
    public boolean preDelete(Class<T> resourceClass,
                             Object id) {
        imageRepository.deleteImage((String) id);
        return false;
    }

    @Override
    public List<T> preGet(EntityQuery<T> entityQuery) {
        Object entityId = entityQuery.getQueryParameterValue("entityId");

        if (entityId == null) {
            return EMPTY_LIST;
        }

        Object parentId = entityQuery.getQueryParameterValue("parentId");

        if (parentId == null) {
            parentId = entityId;
        }
        
        String size = (String)entityQuery.getQueryParameterValue("size");
        ImageSize imageSize = null;
        if (size != null) {
          imageSize = ImageSize.getImageSize(size);
        }
        return imageRepository.getEntityImages(getRelativePath(entityQuery),
                                               (String) entityId,
                                               imageSize);
    }
    
    protected String getRelativePath(EntityQuery<T> entityQuery) {
        return null;
    }

    @Override
    public T preGet(Class<T> entityClass,
                    Object id) {
        return imageRepository.getEntityImage((String)id);
    }

}
