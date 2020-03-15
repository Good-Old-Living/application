package meru.app.service.schedule;

import java.util.List;
import java.util.logging.Logger;

import meru.app.service.AbstractAppService;
import meru.logger.LoggerFactory;
import meru.persistence.EntityQuery;
import meru.scheduler.Job;
import meru.scheduler.JobExecutionContext;
import meru.scheduler.Scheduler;
import meru.scheduler.Trigger;
import meru.scheduler.provider.quartz.QuartzScheduler;
import meru.scheduler.provider.quartz.QuartzTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;

import app.domain.schedule.Schedule;
import app.domain.schedule.ScheduleTrigger;

public class SchedulerService extends AbstractAppService {

    private Logger logger = LoggerFactory.getLogger(SchedulerService.class.getName());

    public static final String NAME = "Scheduler Service";

    private Scheduler scheduler;

    public SchedulerService() throws Exception {
        scheduler = createScheduler();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void start() throws Exception {
        startScheduler();
    }

    @Override
    public void stop() {

    }

    private void startScheduler() throws Exception {
        
        String scheduleEnabled = serviceManager.getAppProperty("app.schedule.enabled");
        
        if (scheduleEnabled != null && "false".equals(scheduleEnabled)) {
            return;
        }

        EntityQuery<Schedule> entityQuery = appEngine.createQuery(Schedule.class);
        List<Schedule> scheduleList = appEngine.get(entityQuery);
        
        for (Schedule schedule : scheduleList) {

            logger.info("Starting schedule job : " + schedule.getName());

            AppScheduleJob scheduleJob = createAppScheduleAction(schedule.getActionClass());
            Job job = createJob(scheduleJob);
            Trigger trigger = createTrigger(schedule);
            scheduler.submitJob(job,
                                trigger);
        }
    }

    private AppScheduleJob createAppScheduleAction(String actionClass) throws Exception {
        AppScheduleJob scheduleAction = (AppScheduleJob) Class.forName(actionClass)
                                                              .newInstance();
        scheduleAction.init(appEngine,
                            serviceManager);
        return scheduleAction;
    }

    private Scheduler createScheduler() throws SchedulerException {
        return new QuartzScheduler("App Scheduler");
    }

    private Trigger createTrigger(Schedule schedule) {

        ScheduleBuilder<?> scheduleBuilder = null;
        ScheduleTrigger scheduleTrigger = schedule.getScheduleTrigger();
        String type = schedule.getType();

        if (type.equals("second")) {
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                                                   .withIntervalInSeconds(scheduleTrigger.getFrequency())
                                                   .repeatForever();
        } else if (type.equals("minute")) {

        } else if (type.equals("hour")) {

        } else if (type.equals("day")) {
            scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleTrigger.getAtTime());
        }

        org.quartz.Trigger trigger = TriggerBuilder.newTrigger()
                                                   .withIdentity(schedule.getName()
                                                                         + "-Trigger",
                                                                 "group1")
                                                   .withSchedule(scheduleBuilder)
                                                   .startNow()
                                                   .build();
        return new QuartzTrigger(trigger);
    }

    private Job createJob(final AppScheduleJob scheduleJob) {
        return new Job() {

            private static final long serialVersionUID = 1L;
            private AppScheduleJob mScheduleJob;

            {
                mScheduleJob = scheduleJob;
            }

            @Override
            public void execute(JobExecutionContext executionContext) {
                mScheduleJob.perform();
            }

        };

    }
}
