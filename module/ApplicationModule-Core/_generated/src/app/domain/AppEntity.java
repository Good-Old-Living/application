package app.domain;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AppEntity {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    public Long getId() {
        
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
