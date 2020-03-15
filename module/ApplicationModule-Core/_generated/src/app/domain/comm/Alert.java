package app.domain.comm;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="comm_alert")
public class Alert {


    @Id
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="sender", nullable=false)
    private java.lang.String sender;

    @Column(name="receiver", nullable=false)
    private java.lang.String receiver;

    @Column(name="category", nullable=false)
    private java.lang.String category;

    @Column(name="type", nullable=false)
    private java.lang.String type;

    @Column(name="description", nullable=false)
    private java.lang.String description;

    @Column(name="target", nullable=false)
    private java.lang.String target;

    @Column(name="received_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar receivedOn;

    public Long getId() {
        
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public java.lang.String getSender() {
        
        return sender;
    }

    public void setSender(java.lang.String sender) {

        this.sender = sender;
    }

    public java.lang.String getReceiver() {
        
        return receiver;
    }

    public void setReceiver(java.lang.String receiver) {

        this.receiver = receiver;
    }

    public java.lang.String getCategory() {
        
        return category;
    }

    public void setCategory(java.lang.String category) {

        this.category = category;
    }

    public java.lang.String getType() {
        
        return type;
    }

    public void setType(java.lang.String type) {

        this.type = type;
    }

    public java.lang.String getDescription() {
        
        return description;
    }

    public void setDescription(java.lang.String description) {

        this.description = description;
    }

    public java.lang.String getTarget() {
        
        return target;
    }

    public void setTarget(java.lang.String target) {

        this.target = target;
    }

    public java.util.Calendar getReceivedOn() {
        
        return receivedOn;
    }

    public void setReceivedOn(java.util.Calendar receivedOn) {

        this.receivedOn = receivedOn;
    }
}
