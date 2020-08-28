package meru.erp.mdm.bp.lifecycle;

import app.erp.mdm.bp.Customer;
import app.erp.mdm.bp.CustomerWallet;
import app.erp.mdm.bp.CustomerWalletAmount;
import app.erp.mdm.bp.CustomerWalletHistory;
import meru.application.lifecycle.BusinessAppEntityLifeCycle;
import meru.exception.AppException;
import meru.persistence.EntityQuery;
import meru.sys.SystemCalendar;

public class CustomerWalletAmountLifeCycle extends BusinessAppEntityLifeCycle<CustomerWalletAmount> {

  private SystemCalendar mSystemCalendar = SystemCalendar.getInstance();

  @Override
  public void init() {

  }

  @Override
  public boolean preCreate(CustomerWalletAmount walletAmount) {

    return preModify(walletAmount);

  }

  @Override
  public boolean preModify(CustomerWalletAmount walletAmount) {

    addMoney(walletAmount.getCustomerId(),
             walletAmount.getAmount(),
             walletAmount.getDescription());
    return false;
  }

  public CustomerWallet getWallet(Long customerId) {
    EntityQuery<CustomerWallet> entityQuery = appEngine.createQuery(CustomerWallet.class);
    entityQuery.addQueryParameter("customerId",
                                  customerId);
    CustomerWallet wallet = appEngine.getFirst(entityQuery);

    if (wallet == null) {
      wallet = new CustomerWallet();
      wallet.setAmount(0);
      wallet.setCustomerId(customerId);
      wallet.setCreatedOn(mSystemCalendar.getCalendar());
      appEngine.save(wallet);
    }

    return wallet;
  }

  public void addMoney(Long customerId,
                       int amountToAdd) {
    addMoney(customerId,
             amountToAdd,
             null);
  }

  public void addMoney(Long customerId,
                       int amountToAdd,
                       String description) {

    //    if (amountToAdd <= 0) {
    //      throw new AppException("Invalid wallet amount : " + amountToAdd);
    //    }

    CustomerWallet wallet = getWallet(customerId);
    int amount = wallet.getAmount() + amountToAdd;

    CustomerWalletHistory walletHistory = createWalletHistory(wallet);
    walletHistory.setCurrAmount(amount);
    walletHistory.setAmountAdded(amountToAdd);
    walletHistory.setDescription(description);
    wallet.setAmount(amount);
    wallet.setUpdatedOn(mSystemCalendar.getCalendar());

    appEngine.saveEntity(walletHistory);
    appEngine.saveEntity(wallet);

    WalletTransaction walletTxn = new WalletTransaction(amountToAdd, amount);
    sendSMS("WalletLoaded.txt",
            customerId,
            walletTxn);
  }

  public boolean hasMoney(Long customerId) {
    CustomerWallet wallet = getWallet(customerId);
    int amount = wallet.getAmount();
    return amount > 0;

  }

  public void reduceMoney(Long customerId,
                          int amountToReduce,
                          String orderId) {

    CustomerWallet wallet = getWallet(customerId);
    int amount = wallet.getAmount() - amountToReduce;

    if (amount < 0) {
      throw new AppException("Insufficient balance in the wallet ");
    }

    CustomerWalletHistory walletHistory = createWalletHistory(wallet);
    walletHistory.setCurrAmount(amount);
    walletHistory.setAmountDeducted(amountToReduce);
    walletHistory.setDescription(orderId);
    wallet.setAmount(amount);
    wallet.setUpdatedOn(mSystemCalendar.getCalendar());

    appEngine.saveEntity(walletHistory);
    appEngine.saveEntity(wallet);

    WalletOrderTransaction walletTxn = new WalletOrderTransaction(amountToReduce, amount, orderId);
    sendSMS("WalletDeducted.txt",
            customerId,
            walletTxn);
  }

  private CustomerWalletHistory createWalletHistory(CustomerWallet wallet) {
    CustomerWalletHistory walletHistory = new CustomerWalletHistory();
    walletHistory.setCreatedOn(mSystemCalendar.getCalendar());
    walletHistory.setCustomerId(wallet.getCustomerId());
    walletHistory.setPrevAmount(wallet.getAmount());

    return walletHistory;
  }

  private void sendSMS(String templateFile,
                       Long customerId,
                       Object value) {

    Customer customer = appEngine.getEntity(Customer.class,
                                            customerId);

    if (customer.getMobile() != null) {

      try {
        sendSMS(templateFile,
                "91" + customer.getMobile(),
                value);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static class WalletTransaction {

    private int amount;
    private int balance;

    public WalletTransaction(int amount,
                             int balance) {
      this.amount = amount;
      this.balance = balance;
    }

    public int getAmount() {
      return amount;
    }

    public int getBalance() {
      return balance;
    }

  }

  public static class WalletOrderTransaction extends WalletTransaction {

    private String orderId;

    public WalletOrderTransaction(int amount,
                                  int balance,
                                  String orderId) {
      super(amount, balance);
      this.orderId = orderId;
    }

    public String getOrderId() {
      return orderId;
    }

  }
}
