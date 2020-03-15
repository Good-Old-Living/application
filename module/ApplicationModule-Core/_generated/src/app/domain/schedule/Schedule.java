package app.domain.schedule;

import app.domain.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="core_schedule")
public class Schedule extends AppEntity {


    @Column(name="name", nullable=false)
    private java.lang.String name;

    @Column(name="action_class", nullable=false)
    private java.lang.String actionClass;

    @Column(name="type", nullable=false)
    private java.lang.String type;

    @Column(name="start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar startTime;

    @Column(name="end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar endTime;

    @Column(name="trigger_on_start")
    private String triggerOnStart = "N";
    private transient boolean triggerOnStartBoolean;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="schedule_trigger_id", nullable=false)
    private app.domain.schedule.ScheduleTrigger scheduleTrigger;

    public java.lang.String getName() {
        
        return name;
    }

    public void setName(java.lang.String name) {

        this.name = name;
    }

    public java.lang.String getActionClass() {
        
        return actionClass;
    }

    public void setActionClass(java.lang.String actionClass) {

        this.actionClass = actionClass;
    }

    public java.lang.String getType() {
        
        return type;
    }

    public void setType(java.lang.String type) {

        this.type = type;
    }

    public java.util.Calendar getStartTime() {
        
        return startTime;
    }

    public void setStartTime(java.util.Calendar startTime) {

        this.startTime = startTime;
    }

    public java.util.Calendar getEndTime() {
        
        return endTime;
    }

    public void setEndTime(java.util.Calendar endTime) {

        this.endTime = endTime;
    }

    public java.lang.String getTriggerOnStart() {
        
        return triggerOnStart;
    }

    public void setTriggerOnStart(java.lang.String triggerOnStart) {

        this.triggerOnStart = triggerOnStart;
    }

    public boolean triggerOnStart() {

        return "Y".equals(triggerOnStart);
    }

    public Boolean getTriggerOnStartBoolean() {
        
        return triggerOnStart != null && triggerOnStart.equals("Y");
    }

    public void setTriggerOnStartBoolean(Boolean triggerOnStartBoolean) {

        triggerOnStart = triggerOnStartBoolean ? "Y" : "N";
    }

    public boolean triggerOnStartBoolean() {

        return "Y".equals(triggerOnStartBoolean);
    }

    public app.domain.schedule.ScheduleTrigger getScheduleTrigger() {

        if (scheduleTrigger == null) {
        }
        
        return scheduleTrigger;
    }

    public void setScheduleTrigger(app.domain.schedule.ScheduleTrigger scheduleTrigger) {

        this.scheduleTrigger = scheduleTrigger;
    }

    public String toString() {
        return name;
    }
}
