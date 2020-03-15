package app.domain.comm;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="comm_send_s_m_s")
public class SendSMS {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="number", nullable=false)
    private java.lang.String number;

    @Column(name="message", nullable=false)
    private java.lang.String message;

    @Column(name="sent_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar sentOn;

    @Column(name="reference", nullable=false)
    private java.lang.String reference;

    @Column(name="delivered_on")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar deliveredOn;

    @Column(name="state", nullable=false)
    private java.lang.String state;

    public Long getId() {
        
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public java.lang.String getNumber() {
        
        return number;
    }

    public void setNumber(java.lang.String number) {

        this.number = number;
    }

    public java.lang.String getMessage() {
        
        return message;
    }

    public void setMessage(java.lang.String message) {

        this.message = message;
    }

    public java.util.Calendar getSentOn() {
        
        return sentOn;
    }

    public void setSentOn(java.util.Calendar sentOn) {

        this.sentOn = sentOn;
    }

    public java.lang.String getReference() {
        
        return reference;
    }

    public void setReference(java.lang.String reference) {

        this.reference = reference;
    }

    public java.util.Calendar getDeliveredOn() {
        
        return deliveredOn;
    }

    public void setDeliveredOn(java.util.Calendar deliveredOn) {

        this.deliveredOn = deliveredOn;
    }

    public java.lang.String getState() {
        
        return state;
    }

    public void setState(java.lang.String state) {

        this.state = state;
    }
}
