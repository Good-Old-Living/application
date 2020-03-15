
delete from core_property_group;
insert into core_property_group(id, name, value) values(1, 'attribute-feature-type', 'Text');
insert into core_property_group(id, name, value) values(2, 'attribute-feature-type', 'List');
insert into core_property_group(id, name, value) values(3, 'attribute-feature-type', 'Map');
insert into core_property_group(id, name, value) values(4, 'attribute-feature-type', 'Table');

insert into core_property_group(id, name, value) values(11, 'unit-of-measure', 'gm');
insert into core_property_group(id, name, value) values(12, 'unit-of-measure', 'kg');
insert into core_property_group(id, name, value) values(13, 'unit-of-measure', 'ltr');
insert into core_property_group(id, name, value) values(14, 'unit-of-measure', 'ml');
insert into core_property_group(id, name, value) values(15, 'unit-of-measure', 'pack');
insert into core_property_group(id, name, value) values(16, 'unit-of-measure', 'bunch');
insert into core_property_group(id, name, value) values(17, 'unit-of-measure', 'pc');

insert into core_property_group(id, name, value) values(31, 'package-type', 'Pouch');
insert into core_property_group(id, name, value) values(32, 'package-type', 'Bottle');
insert into core_property_group(id, name, value) values(33, 'package-type', 'Jar');
insert into core_property_group(id, name, value) values(34, 'package-type', 'Tin');
insert into core_property_group(id, name, value) values(35, 'package-type', 'Pack');
insert into core_property_group(id, name, value) values(36, 'package-type', 'Can');

insert into core_property_group(id, name, value) values(71, 'discount-type', '%');


insert into core_property_group(id, name, value) values(81, 'subscription-frequency', 'Daily');
insert into core_property_group(id, name, value) values(82, 'subscription-frequency', 'Weekly');
insert into core_property_group(id, name, value) values(83, 'subscription-frequency', 'Bi-Weekly');
insert into core_property_group(id, name, value) values(84, 'subscription-frequency', 'Monthly');

delete from core_property_group where id in (91,92,93);
insert into core_property_group(id, name, value) values(91, 'product-type', 'Organic Certified');
insert into core_property_group(id, name, value) values(92, 'product-type', 'Organic');
insert into core_property_group(id, name, value) values(93, 'product-type', 'Natural');


delete from core_app_hierarchical_entity;

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(1, NULL,'app-setup-menu','General Setup','module');
-- insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(2,1, NULL,'app-setup-menu','Security','module');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(21, 1,'app-setup-menu','App Configuration','entity','app/setup/AppConfig.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(22, 1,'app-setup-menu','Email','entity','app/setup/Email.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(23, 1,'app-setup-menu','SMS','entity','app/setup/SMS.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(24, 1,'app-setup-menu','Payment Gateway','entity','app/setup/PaymentGateway.xhtml');


-- insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(31,1, 2,'app-setup-menu','Role','entity','app/security/Role.xhtml');
-- insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(32,1, 2,'app-setup-menu','User','entity','app/security/User.xhtml');


insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(101,NULL,'app-module-menu','Master Data Management','module');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(102,NULL,'app-module-menu','Financial Management','module');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(103,NULL,'app-module-menu','Inventory Management','module');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(104,NULL,'app-module-menu','Purchase Management','module');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind) values(105,NULL,'app-module-menu','Sales Management','module');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(121,101,'app-module-menu','Product Category','entity','app/module/mdm/catalog/ProductCategory.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(122,101,'app-module-menu','Product','entity','app/module/mdm/catalog/Product.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(123,101,'app-module-menu','Business Partner','entity','app/module/mdm/bp/bp.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(124,101,'app-module-menu','Apartment','entity','app/module/mdm/apartment.xhtml');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(401,104,'app-module-menu','Purchase Order','entity','app/module/purchase/PurchaseOrder.xhtml');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(501,105,'app-module-menu','Sales Order','entity','app/module/sales/SalesOrder.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(502,105,'app-module-menu','Sales Invoice','entity','app/module/sales/SalesInvoice.xhtml');

delete from core_app_entity_sequence;
insert into core_app_entity_sequence(id, name, value) values(1, 'SalesOrder.Id',1);
insert into core_app_entity_sequence(id, name, value) values(2, 'InvoiceOrder.Id',1);
insert into core_app_entity_sequence(id, name, value) values(3, 'PaymentGateway.Id',1);
insert into core_app_entity_sequence(id, name, value) values(4, 'PurchaseOrder.Id',1);


delete from core_app_entity_state;

insert into core_app_entity_state(id, entity, code, status) values(1, "SalesOrder", 1, "New");
insert into core_app_entity_state(id, entity, code, status) values(2, "SalesOrder", 2, "In-Process");
insert into core_app_entity_state(id, entity, code, status) values(5, "SalesOrder", 6, "Delivered");
insert into core_app_entity_state(id, entity, code, status) values(6, "SalesOrder", 10, "Cancelled By Customer");
insert into core_app_entity_state(id, entity, code, status) values(7, "SalesOrder", 11, "Cancelled");

insert into core_app_entity_state(id, entity, code, status) values(21, "Subscription", 1, "Active");
insert into core_app_entity_state(id, entity, code, status) values(22, "Subscription", 2, "Stopped");

delete from core_country;
insert into core_country(id, name, code) values(1, 'India', 'in');

delete from core_state;
insert into core_state(id, name, country_id, code) values(18, 'Karnataka', 1, 'KA');

delete from core_city;
insert into core_city(id, name, country_id, state_id) values(1, 'Bangalore', 1, 18);

delete from core_city_area;

insert into core_city_area(id, name, city_id) values(1, 'Arekere Mico Layout', 1);
insert into core_city_area(id, name, city_id) values(11, 'JP Nagar 1st Phase', 1);
insert into core_city_area(id, name, city_id) values(12, 'JP Nagar 2nd Phase', 1);
insert into core_city_area(id, name, city_id) values(13, 'JP Nagar 3rd Phase', 1);
insert into core_city_area(id, name, city_id) values(14, 'JP Nagar 4th Phase', 1);
insert into core_city_area(id, name, city_id) values(15, 'JP Nagar 5th Phase', 1);
insert into core_city_area(id, name, city_id) values(16, 'JP Nagar 6th Phase', 1);
insert into core_city_area(id, name, city_id) values(17, 'JP Nagar 7th Phase', 1);
insert into core_city_area(id, name, city_id) values(18, 'JP Nagar 8th Phase', 1);


delete from fin_tax;
insert into fin_tax(id, name, rate) values(1, 'GST - Nil', 0);
insert into fin_tax(id, name, rate) values(2, 'GST - 5%', 5);
insert into fin_tax(id, name, rate) values(3, 'GST - 12%', 12);
insert into fin_tax(id, name, rate) values(4, 'GST - 18%', 18);
insert into fin_tax(id, name, rate) values(5, 'GST - 28%', 28);


delete from core_schedule;
insert into core_schedule(id, name, action_class, type, start_time, end_time, trigger_on_start, schedule_trigger_id) values(1, 'Email Dispatch', 'meru.app.service.schedule.job.EmailScheduleJob', 'second', NULL, NULL, 'Y', 1);

delete from core_schedule_trigger;
insert into core_schedule_trigger(id, frequency) values(1, 3);


