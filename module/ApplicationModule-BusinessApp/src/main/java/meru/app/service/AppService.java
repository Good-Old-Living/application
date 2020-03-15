package meru.app.service;


public interface AppService {

    String getName();
    void start() throws Exception;
    void stop() throws Exception;
}
