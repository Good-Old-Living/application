package meru.ecom.store;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class OrderCutOffTime {

  private Calendar dateTime;

  public OrderCutOffTime(Calendar dateTime) {
    this.dateTime = dateTime;
  }

  public OrderCutOffTime(String timeValue) {

    constructCalendar(timeValue);
    
  }

  
  private void constructCalendar(String timeValue) {
    
    /*
    Everyday 12PM
    Everyday 5PM
    Everyday 3AM
    Nextday 5AM
   */
    
    String[] tokens = timeValue.split("[ ]");
    
    String frequency = tokens[0];
    String timeStr = tokens[1];
    String time = timeStr.substring(0, timeStr.length()-2);
    String amPM = timeStr.substring(timeStr.length()-2);
    
    LocalDateTime timeNow = LocalDateTime.now();    

    ZoneId istTZ = ZoneId.of("Asia/Kolkata");
    ZonedDateTime currentISTime = timeNow.atZone(istTZ); 
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss a z");
    System.out.println(formatter.format(currentISTime.withZoneSameInstant(ZoneId.of("America/New_York"))));
  }

  public static void main(String[] args) {
    new OrderCutOffTime("Nextday 5AM");
  }
}
