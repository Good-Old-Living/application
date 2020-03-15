package meru.app.service.account;

import meru.messaging.MessageSender;

public class AccountServiceImpl implements AccountService {

    private String appRoot;
    private MessageSender mMessageSender;

    public AccountServiceImpl(MessageSender messageSender) {
        mMessageSender = messageSender;
        appRoot= "http://localhost:8080/account/";
    }

    @Override
    public Object createUser(String json) {

        mMessageSender.send(appRoot+"e/User",
                            json,
                            "application/json");

        return null;
    }

    @Override
    public String getName() {
        return SERVICE_ACCOUNT;
    }

    @Override
    public void start() throws Exception {
        
    }

    @Override
    public void stop() throws Exception {
    }

}
