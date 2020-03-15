package meru.app.service.schedule;

import java.util.logging.Logger;

import meru.app.engine.AppEngine;
import meru.app.service.ServiceManager;
import meru.logger.LoggerFactory;
import meru.transaction.TransactionManager;

public abstract class AppScheduleJob {

    protected static Logger logger = LoggerFactory.getLogger(AppScheduleJob.class.getName());
    
    protected AppEngine appEngine;
    protected ServiceManager serviceManager;
    private TransactionManager transactionManager;

    void init(AppEngine appEngine, ServiceManager serviceManager) {
        this.appEngine = appEngine;
        this.serviceManager = serviceManager;
        transactionManager = TransactionManager.getInstance();
    }

    public void perform() {
        
        try {

            transactionManager.begin();
            execute();
            transactionManager.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transactionManager.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    protected abstract void execute();

}
