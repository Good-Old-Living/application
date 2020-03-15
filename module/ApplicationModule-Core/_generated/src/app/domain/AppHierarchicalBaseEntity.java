package app.domain;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AppHierarchicalBaseEntity extends AppEntity implements app.entity.Hierarchical {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="qualified_name")
    private java.lang.String qualifiedName;

    @Column(name="category")
    private java.lang.String category;

    @Column(name="parent_id")
    private Long parentId;

    @Column(name="type", nullable=false)
    private java.lang.String type;

    @Column(name="kind", nullable=false)
    private java.lang.String kind;

    @Column(name="action")
    private java.lang.String action;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getQualifiedName() {
        
        return qualifiedName;
    }

    public void setQualifiedName(java.lang.String qualifiedName) {

        this.qualifiedName = qualifiedName;
    }

    public java.lang.String getCategory() {
        
        return category;
    }

    public void setCategory(java.lang.String category) {

        this.category = category;
    }

    public Long getParentId() {
        
        return parentId;
    }

    public void setParentId(Long parentId) {

        this.parentId = parentId;
    }

    public java.lang.String getType() {
        
        return type;
    }

    public void setType(java.lang.String type) {

        this.type = type;
    }

    public java.lang.String getKind() {
        
        return kind;
    }

    public void setKind(java.lang.String kind) {

        this.kind = kind;
    }

    public java.lang.String getAction() {
        
        return action;
    }

    public void setAction(java.lang.String action) {

        this.action = action;
    }

    public String toString() {
        return name;
    }
}
