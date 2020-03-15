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
@Table(name="comm_send_email")
public class SendEmail {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="tos", nullable=false)
    private java.lang.String tos;

    @Column(name="ccs")
    private java.lang.String ccs;

    @Column(name="bccs")
    private java.lang.String bccs;

    @Column(name="subject", nullable=false)
    private java.lang.String subject;

    @Column(name="message", nullable=false)
    private java.lang.String message;

    @Column(name="content_type", nullable=false)
    private java.lang.String contentType;

    @Column(name="sent_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar sentOn;

    @Column(name="reference")
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

    public java.lang.String getTos() {
        
        return tos;
    }

    public void setTos(java.lang.String tos) {

        this.tos = tos;
    }

    public java.lang.String getCcs() {
        
        return ccs;
    }

    public void setCcs(java.lang.String ccs) {

        this.ccs = ccs;
    }

    public java.lang.String getBccs() {
        
        return bccs;
    }

    public void setBccs(java.lang.String bccs) {

        this.bccs = bccs;
    }

    public java.lang.String getSubject() {
        
        return subject;
    }

    public void setSubject(java.lang.String subject) {

        this.subject = subject;
    }

    public java.lang.String getMessage() {
        
        return message;
    }

    public void setMessage(java.lang.String message) {

        this.message = message;
    }

    public java.lang.String getContentType() {
        
        return contentType;
    }

    public void setContentType(java.lang.String contentType) {

        this.contentType = contentType;
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
