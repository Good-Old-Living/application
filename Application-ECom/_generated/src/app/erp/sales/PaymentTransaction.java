package app.erp.sales;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sales_payment_transaction")
public class PaymentTransaction extends AppEntity {


    @Column(name="wallet_amount_deducted")
    private Integer walletAmountDeducted;

    @Column(name="wallet_amount_added")
    private Integer walletAmountAdded;

    public Integer getWalletAmountDeducted() {
        
        return walletAmountDeducted;
    }

    public void setWalletAmountDeducted(Integer walletAmountDeducted) {

        this.walletAmountDeducted = walletAmountDeducted;
    }

    public Integer getWalletAmountAdded() {
        
        return walletAmountAdded;
    }

    public void setWalletAmountAdded(Integer walletAmountAdded) {

        this.walletAmountAdded = walletAmountAdded;
    }
}
