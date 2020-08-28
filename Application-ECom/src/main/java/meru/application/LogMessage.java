package meru.application;

import java.util.logging.Level;

import app.domain.AppEntity;

public class LogMessage extends AppEntity {

  private String sessionId;
  private String customerId;
  private String type;
  private String message;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Level getLogLevel() {

    Level level = Level.INFO;

    if (type != null) {

      switch (type) {
        case "warning":
          level = Level.WARNING;
          break;
        case "severe":
          level = Level.SEVERE;
          break;
      }
    }

    return level;
  }

  public String getFormattedMessage() {
    return String.format("Customer Id : %s, Session Id : %s, %s",
                         customerId,
                         sessionId,
                         message);

  }
}
