package app.domain;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BusinessApplication {


    @Id
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="uid", nullable=false)
    private java.lang.String uid;

    @Column(name="root", nullable=false)
    private java.lang.String root;

    public Long getId() {
        
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getUid() {
        
        return uid;
    }

    public void setUid(java.lang.String uid) {

        this.uid = uid;
    }

    public java.lang.String getRoot() {
        
        return root;
    }

    public void setRoot(java.lang.String root) {

        this.root = root;
    }

    public String toString() {
        return name;
    }
}
