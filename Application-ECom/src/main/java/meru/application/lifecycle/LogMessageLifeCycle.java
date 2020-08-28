package meru.application.lifecycle;

import java.util.logging.Logger;

import meru.application.LogMessage;
import meru.logger.LoggerFactory;

public class LogMessageLifeCycle extends BusinessAppEntityLifeCycle<LogMessage> {

  private Logger logger = LoggerFactory.getLogger("Generic");

  @Override
  public boolean preCreate(LogMessage logMessage) {

    try {
      logger.log(logMessage.getLogLevel(),
                 logMessage.getFormattedMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
