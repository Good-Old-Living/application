package meru.app.service.account;

import meru.app.service.AppService;

public interface AccountService extends AppService {

    public static final String SERVICE_ACCOUNT = "Account Service";
    
    Object createUser(String json);
}
