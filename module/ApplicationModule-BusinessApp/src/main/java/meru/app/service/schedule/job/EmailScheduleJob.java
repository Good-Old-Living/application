package meru.app.service.schedule.job;

import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import meru.app.service.ServiceManager;
import meru.app.service.schedule.AppScheduleJob;
import meru.comm.mail.MailBox;
import meru.comm.mail.MailEnvelop;
import meru.messaging.MessageState;
import meru.persistence.EntityQuery;
import meru.sys.SystemCalendar;
import meru.transaction.TransactionManager;
import app.domain.comm.SendEmail;

public class EmailScheduleJob extends AppScheduleJob {

  private TransactionManager mTransactionManager = TransactionManager.getInstance();

  @Override
  public synchronized void execute() {

    EntityQuery<SendEmail> entityQuery = appEngine.createQuery(SendEmail.class);
    entityQuery.addQueryParameter("state",
                                  MessageState.NEW.getState());

    List<SendEmail> emails = appEngine.get(entityQuery);
    MailBox mailBox = serviceManager.getService(ServiceManager.MAIL_BOX_NAME,
                                                MailBox.class);
    if (emails != null) {
      String state = null;
      for (SendEmail emailMsg : emails) {

        MailEnvelop envelop = new MailEnvelop(emailMsg.getTos(),
                                              emailMsg.getSubject(),
                                              emailMsg.getMessage());

        envelop.setCCs(emailMsg.getCcs());
        envelop.setBCCs(emailMsg.getBccs());
        envelop.setContentType("text/html");

        try {
          mailBox.drop(envelop);
          state = MessageState.DELIVERED.getState();
        } catch (Exception e) {
          e.printStackTrace();

          try {
            Thread.currentThread().wait(1000);
            mailBox.drop(envelop);
            state = MessageState.DELIVERED.getState();
          } catch (Exception ex) {
            ex.printStackTrace();
            state = MessageState.FAILED.getState();
          }

        }

        emailMsg.setDeliveredOn(SystemCalendar.getInstance().getUTCCalendar());
        emailMsg.setState(state);

        appEngine.save(emailMsg);

      }

      try {
        mTransactionManager.commit();
      } catch (SecurityException | IllegalStateException | RollbackException
          | HeuristicMixedException | HeuristicRollbackException
          | SystemException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }
}
