package meru.erp;

public enum DiscountType {
    MONEY,
    PERCENT {
        public float getValue(float value) {
            return value / 100;
        }
    };

    public float getValue(float value) {
        return value;
    }
}
