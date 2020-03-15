package app.erp.mdm.catalog;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="mdm_vegetable_price_list")
public class VegetablePriceList {


    @Id
    @Column(name="id", nullable=false)
    private java.lang.String id;

    @Column(name="supplier", nullable=false)
    private java.lang.String supplier;

    @Column(name="csv_file", nullable=false)
    private java.lang.String csvFile;

    public java.lang.String getId() {
        
        return id;
    }

    public void setId(java.lang.String id) {

        this.id = id;
    }

    public java.lang.String getSupplier() {
        
        return supplier;
    }

    public void setSupplier(java.lang.String supplier) {

        this.supplier = supplier;
    }

    public java.lang.String getCsvFile() {
        
        return csvFile;
    }

    public void setCsvFile(java.lang.String csvFile) {

        this.csvFile = csvFile;
    }
}
