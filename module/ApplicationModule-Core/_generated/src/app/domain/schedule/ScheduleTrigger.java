package app.domain.schedule;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_schedule_trigger")
public class ScheduleTrigger extends AppEntity {


    @Column(name="frequency")
    private Integer frequency;

    @Column(name="at_time")
    private java.lang.String atTime;

    @Column(name="day")
    private java.lang.String day;

    public Integer getFrequency() {
        
        return frequency;
    }

    public void setFrequency(Integer frequency) {

        this.frequency = frequency;
    }

    public java.lang.String getAtTime() {
        
        return atTime;
    }

    public void setAtTime(java.lang.String atTime) {

        this.atTime = atTime;
    }

    public java.lang.String getDay() {
        
        return day;
    }

    public void setDay(java.lang.String day) {

        this.day = day;
    }
}
