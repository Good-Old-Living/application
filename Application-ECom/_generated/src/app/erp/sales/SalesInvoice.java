package app.erp.sales;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="sales_sales_invoice")
public class SalesInvoice extends AppEntity {


    @Column(name="number", nullable=false)
    private java.lang.String number;

    @OneToOne
    @JoinColumn(name="sales_order_id", nullable=false)
    private app.erp.sales.SalesOrder salesOrder;

    @Column(name="notes")
    private java.lang.String notes;

    public java.lang.String getNumber() {
        
        return number;
    }

    public void setNumber(java.lang.String number) {

        this.number = number;
    }

    public app.erp.sales.SalesOrder getSalesOrder() {
        
        return salesOrder;
    }

    public void setSalesOrder(app.erp.sales.SalesOrder salesOrder) {

        this.salesOrder = salesOrder;
    }

    public java.lang.String getNotes() {
        
        return notes;
    }

    public void setNotes(java.lang.String notes) {

        this.notes = notes;
    }
}
