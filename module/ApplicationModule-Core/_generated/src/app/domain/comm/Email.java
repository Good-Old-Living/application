package app.domain.comm;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="comm_email")
public class Email {


    @Id
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="sender", nullable=false)
    private java.lang.String sender;

    @Column(name="receiver", nullable=false)
    private java.lang.String receiver;

    @Column(name="message", nullable=false)
    private java.lang.String message;

    @Column(name="subject", nullable=false)
    private java.lang.String subject;

    @Column(name="received_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar receivedOn;

    @Column(name="state", nullable=false)
    private Integer state;

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

    public java.lang.String getMessage() {
        
        return message;
    }

    public void setMessage(java.lang.String message) {

        this.message = message;
    }

    public java.lang.String getSubject() {
        
        return subject;
    }

    public void setSubject(java.lang.String subject) {

        this.subject = subject;
    }

    public java.util.Calendar getReceivedOn() {
        
        return receivedOn;
    }

    public void setReceivedOn(java.util.Calendar receivedOn) {

        this.receivedOn = receivedOn;
    }

    public Integer getState() {
        
        return state;
    }

    public void setState(Integer state) {

        this.state = state;
    }
}
