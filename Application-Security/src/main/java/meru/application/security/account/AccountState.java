package meru.application.security.account;

public enum AccountState {
    New("N"),
    Active("A"),
    Suspended("S");
    
    private String code;
    
    private AccountState(String code) {
        this.code = code;
    }
    
    public String  getCode() {
        return code;
    }
}
