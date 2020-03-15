package meru.application.security;


public enum UserType {

    EMAIL("E", "email") {
        public boolean satisfies(String userName) {
            return isEmail(userName);
        }
    },
    MOBILE("M", "mobile") {
        public boolean satisfies(String userName) {
            try {
                Long.parseLong(userName);
            } catch (Exception e) {
                return false;
            }

            return true;
        }
    },
    NAME("N", "name") {
        public boolean satisfies(String userName) {
            return true;
        }
    };

    private String type;
    private String attrName;

    private UserType(String type,
                     String attrName) {
        this.type = type;
        this.attrName = attrName;
    }

    public String getType() {
        return type;
    }

    public String getAttrName() {
        return attrName;
    }

    public boolean satisfies(String userName) {
        return false;
    }

    public static UserType getUserType(String userName) {

        for (UserType userType : values()) {
            if (userType.satisfies(userName)) {
                return userType;
            }
        }

        throw new RuntimeException("Unknown Type : " + userName);
    }

    public static final boolean isEmail(String userName) {
        return userName.contains("@");
    }

}
