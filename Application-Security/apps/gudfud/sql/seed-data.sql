delete from sec_role;
delete from sec_user;
delete from core_property;

-- ecom
insert into sec_role (id, name, home) values(201, 'admin','/app');
insert into sec_role (id, name, home) values(202, 'customer',NULL);

-- password - Nakul2006
insert into sec_user (id, name, password, primary_role_id,state) values(-1, 'admin', '987e72af83c6bd51955c6adaacda9b65', "201", 'A');

delete from core_property;
-- insert into core_property(id, prefix, name, type, value) values(51, 'mail', 'smtp.host', 'string', 'smtpout.asia.secureserver.net');
insert into core_property(id, prefix, name, type, value) values(50, 'mail', 'smtp.host', 'string', 'smtp.paaril.com');
insert into core_property(id, prefix, name, type, value) values(52, 'mail', 'smtp.port', 'int', '587');
insert into core_property(id, prefix, name, type, value) values(53, 'mail', 'smtp.auth', 'boolean', 'true');
insert into core_property(id, prefix, name, type, value) values(54, 'mail', 'smtp.connectiontimeout', 'int', '60000');
insert into core_property(id, prefix, name, type, value) values(55, 'mail', 'smtp.timeout', 'int', '60000');
insert into core_property(id, prefix, name, type, value) values(56, 'mail', 'smtp.starttls.enable', 'boolean', 'true');
-- insert into core_property(id, prefix, name, type, value) values(56, 'mail', 'smtp.ssl.enable', 'boolean', 'true');
insert into core_property(id, prefix, name, type, value) values(57, 'mail', 'smtp.socketFactory.port', 'int', '587');

insert into core_property(id, prefix, name, type, value) values(58, 'mail', 'user.name', 'string', 'Paaril Admin');
insert into core_property(id, prefix, name, type, value) values(59, 'mail', 'user.email', 'string', 'kiruthigaa@paaril.com');
insert into core_property(id, prefix, name, type, value) values(60, 'mail', 'user.password', 'string', 'Nakul2006');

insert into core_property(id, prefix, name, type, value) values(90, 'sms', 'provider.url', 'string', 'https://smsapi.24x7sms.com/api_2.0/SendSMS.aspx?APIKEY=hDuiVf92kbx&SenderID=PAARIL&ServiceName=TEMPLATE_BASED&MobileNo={0}&Message={1}');


delete from core_app_hierarchical_entity;

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(1,NULL, 'app-setup-menu','General','module');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(2,NULL, 'app-setup-menu','Property Group','module');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(11, 1, 'app-setup-menu','Email','entity','app/setup/config/Email.xhtml');


delete from core_schedule;
insert into core_schedule(id, name, action_class, type, start_time, end_time, trigger_on_start, schedule_trigger_id) values(1, 'Email Dispatch', 'meru.app.service.schedule.job.EmailScheduleJob', 'second', NULL, NULL, 'Y', 1);

delete from core_schedule_trigger;
insert into core_schedule_trigger(id, frequency) values(1, 3);


