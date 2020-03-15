package app.domain.image;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityImage {


    @Id
    @Column(name="id", nullable=false)
    private java.lang.String id;

    @Column(name="relative_path", nullable=false)
    private java.lang.String relativePath;

    @Column(name="entity_id", nullable=false)
    private java.lang.String entityId;

    @Column(name="image", nullable=false)
    private java.lang.String image;

    @Column(name="size", nullable=false)
    private java.lang.String size;

    @Column(name="is_default")
    private String isDefault = "N";
    private transient boolean isDefaultBoolean;

    public java.lang.String getId() {
        
        return id;
    }

    public void setId(java.lang.String id) {

        this.id = id;
    }

    public java.lang.String getRelativePath() {
        
        return relativePath;
    }

    public void setRelativePath(java.lang.String relativePath) {

        this.relativePath = relativePath;
    }

    public java.lang.String getEntityId() {
        
        return entityId;
    }

    public void setEntityId(java.lang.String entityId) {

        this.entityId = entityId;
    }

    public java.lang.String getImage() {
        
        return image;
    }

    public void setImage(java.lang.String image) {

        this.image = image;
    }

    public java.lang.String getSize() {
        
        return size;
    }

    public void setSize(java.lang.String size) {

        this.size = size;
    }

    public java.lang.String getIsDefault() {
        
        return isDefault;
    }

    public void setIsDefault(java.lang.String isDefault) {

        this.isDefault = isDefault;
    }

    public boolean isDefault() {

        return "Y".equals(isDefault);
    }

    public Boolean getIsDefaultBoolean() {
        
        return isDefault != null && isDefault.equals("Y");
    }

    public void setIsDefaultBoolean(Boolean isDefaultBoolean) {

        isDefault = isDefaultBoolean ? "Y" : "N";
    }

    public boolean isDefaultBoolean() {

        return "Y".equals(isDefaultBoolean);
    }
}
