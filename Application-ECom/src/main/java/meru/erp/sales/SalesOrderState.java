package meru.erp.sales;

import meru.exception.AppException;

public enum SalesOrderState {

                             New(1,
                                 "New"),
                             InProcess(2,
                                       "In-Process"),
                             PendingPayment(3,
                                            "Panding Payment"),
                             PendingDelivery(4,
                                             "Panding Delivery"),
                             Delivered(5,
                                       "Delivered"),
                             CustomerCancelled(10,
                                               "Cancelled By Customer"),
                             Cancelled(11,
                                       "Cancelled");

  private int code;
  private String state;

  private SalesOrderState(int code,
                          String state) {
    this.code = code;
    this.state = state;
  }

  public int getCode() {
    return code;
  }

  public String toString() {
    return state;
  }

  public static SalesOrderState getOrderState(String state) {

    if (state == null) {
      return New;
    }

    for (SalesOrderState orderState : values()) {
      if (orderState.state.equals(state)) {
        return orderState;
      }
    }

    throw new IllegalArgumentException("Unknown OrderState: " + state);
  }

  public static SalesOrderState getOrderState(int code) {

    for (SalesOrderState orderState : values()) {
      if (orderState.code == code) {
        return orderState;
      }
    }

    throw new IllegalArgumentException("Unknown OrderState code: " + code);
  }

  public boolean isTransitionAllowed(SalesOrderState toState) {
    boolean allowed = false;
    switch (this) {
      case New:
      case InProcess:
        allowed = toState == CustomerCancelled || toState == Cancelled;
        break;
      default:
        // Nothing
    }

    if (!allowed) {
      throw new AppException("SalesOrder state can not be chnaged to " + toState + " from " + state);
    }

    return true;
  }
}
