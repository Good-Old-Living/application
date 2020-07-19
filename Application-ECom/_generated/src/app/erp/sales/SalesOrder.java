package app.erp.sales;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="sales_sales_order")
public class SalesOrder extends AppEntity {


    @Column(name="order_id", nullable=false)
    private java.lang.String orderId;

    @Column(name="customer_id", nullable=false)
    private java.lang.String customerId;

    @Column(name="transaction_id")
    private java.lang.String transactionId;

    @Column(name="session_id", nullable=false)
    private java.lang.String sessionId;

    @OneToOne
    @JoinColumn(name="delivery_address_id", nullable=false)
    private app.erp.mdm.bp.CustomerAddress deliveryAddress;

    @Column(name="delivery_address_text")
    private java.lang.String deliveryAddressText;

    @OneToOne
    @JoinColumn(name="payment_method_id")
    private app.erp.finance.PaymentMethod paymentMethod;

    @Column(name="created_on", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar createdOn;

    @Column(name="amount", nullable=false)
    private Float amount;

    @Column(name="code")
    private java.lang.String code;

    @OneToOne
    @JoinColumn(name="state_id", nullable=false)
    private app.domain.AppEntityState state;

    @Column(name="payment_received")
    private String paymentReceived = "N";
    private transient boolean paymentReceivedBoolean;

    @Column(name="delivery_instructions")
    private java.lang.String deliveryInstructions;

    @OneToOne
    @JoinColumn(name="payment_mode_id", nullable=false)
    private app.domain.PropertyGroup paymentMode;

    @Column(name="payment_order_id")
    private java.lang.String paymentOrderId;

    @Column(name="payment_id")
    private java.lang.String paymentId;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="payment_transaction_id")
    private app.erp.sales.PaymentTransaction paymentTransaction;

    public java.lang.String getOrderId() {
        
        return orderId;
    }

    public void setOrderId(java.lang.String orderId) {

        this.orderId = orderId;
    }

    public java.lang.String getCustomerId() {
        
        return customerId;
    }

    public void setCustomerId(java.lang.String customerId) {

        this.customerId = customerId;
    }

    public java.lang.String getTransactionId() {
        
        return transactionId;
    }

    public void setTransactionId(java.lang.String transactionId) {

        this.transactionId = transactionId;
    }

    public java.lang.String getSessionId() {
        
        return sessionId;
    }

    public void setSessionId(java.lang.String sessionId) {

        this.sessionId = sessionId;
    }

    public app.erp.mdm.bp.CustomerAddress getDeliveryAddress() {
        
        return deliveryAddress;
    }

    public void setDeliveryAddress(app.erp.mdm.bp.CustomerAddress deliveryAddress) {

        this.deliveryAddress = deliveryAddress;
    }

    public java.lang.String getDeliveryAddressText() {
        
        return deliveryAddressText;
    }

    public void setDeliveryAddressText(java.lang.String deliveryAddressText) {

        this.deliveryAddressText = deliveryAddressText;
    }

    public app.erp.finance.PaymentMethod getPaymentMethod() {
        
        return paymentMethod;
    }

    public void setPaymentMethod(app.erp.finance.PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;
    }

    public java.util.Calendar getCreatedOn() {
        
        return createdOn;
    }

    public void setCreatedOn(java.util.Calendar createdOn) {

        this.createdOn = createdOn;
    }

    public Float getAmount() {
        
        return amount;
    }

    public void setAmount(Float amount) {

        this.amount = amount;
    }

    public java.lang.String getCode() {
        
        return code;
    }

    public void setCode(java.lang.String code) {

        this.code = code;
    }

    public app.domain.AppEntityState getState() {
        
        return state;
    }

    public void setState(app.domain.AppEntityState state) {

        this.state = state;
    }

    public java.lang.String getPaymentReceived() {
        
        return paymentReceived;
    }

    public void setPaymentReceived(java.lang.String paymentReceived) {

        this.paymentReceived = paymentReceived;
    }

    public boolean paymentReceived() {

        return "Y".equals(paymentReceived);
    }

    public Boolean getPaymentReceivedBoolean() {
        
        return paymentReceived != null && paymentReceived.equals("Y");
    }

    public void setPaymentReceivedBoolean(Boolean paymentReceivedBoolean) {

        paymentReceived = paymentReceivedBoolean ? "Y" : "N";
    }

    public boolean paymentReceivedBoolean() {

        return "Y".equals(paymentReceivedBoolean);
    }

    public java.lang.String getDeliveryInstructions() {
        
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(java.lang.String deliveryInstructions) {

        this.deliveryInstructions = deliveryInstructions;
    }

    public app.domain.PropertyGroup getPaymentMode() {
        
        return paymentMode;
    }

    public void setPaymentMode(app.domain.PropertyGroup paymentMode) {

        this.paymentMode = paymentMode;
    }

    public java.lang.String getPaymentOrderId() {
        
        return paymentOrderId;
    }

    public void setPaymentOrderId(java.lang.String paymentOrderId) {

        this.paymentOrderId = paymentOrderId;
    }

    public java.lang.String getPaymentId() {
        
        return paymentId;
    }

    public void setPaymentId(java.lang.String paymentId) {

        this.paymentId = paymentId;
    }

    public app.erp.sales.PaymentTransaction getPaymentTransaction() {

        if (paymentTransaction == null) {
        }
        
        return paymentTransaction;
    }

    public void setPaymentTransaction(app.erp.sales.PaymentTransaction paymentTransaction) {

        this.paymentTransaction = paymentTransaction;
    }
}
