package app;
public class ApplicationModule_CoreEntityClassRegistry extends meru.app.registry.EntityClassRegistry {
    protected void populateClassMap() {
        super.populateClassMap();
        addResourceClass("SequenceId", app.domain.SequenceId.class);
        addResourceClass("BusinessApplication", app.domain.BusinessApplication.class);
        addResourceClass("ScheduleTrigger", app.domain.schedule.ScheduleTrigger.class);
        addResourceClass("Schedule", app.domain.schedule.Schedule.class);
        addResourceClass("AppEntityState", app.domain.AppEntityState.class);
        addResourceClass("EntityFeatureValue", app.domain.EntityFeatureValue.class);
        addResourceClass("EntityFeature", app.domain.EntityFeature.class);
        addResourceClass("RegisteredNewUser", app.domain.account.RegisteredNewUser.class);
        addResourceClass("Property", app.domain.Property.class);
        addResourceClass("AppHierarchicalEntity", app.domain.AppHierarchicalEntity.class);
        addResourceClass("PropertyGroup", app.domain.PropertyGroup.class);
        addResourceClass("AuditableEntity", app.domain.AuditableEntity.class);
        addResourceClass("AppEntitySequence", app.domain.AppEntitySequence.class);
        addResourceClass("AppEntity", app.domain.AppEntity.class);
        addResourceClass("SendSMS", app.domain.comm.SendSMS.class);
        addResourceClass("Alert", app.domain.comm.Alert.class);
        addResourceClass("SendEmail", app.domain.comm.SendEmail.class);
        addResourceClass("Email", app.domain.comm.Email.class);
        addResourceClass("AppHierarchicalBaseEntity", app.domain.AppHierarchicalBaseEntity.class);
    }
}
