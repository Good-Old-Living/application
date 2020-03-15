delete from bus_enterprise;
delete from bus_enterprise_address;
delete from core_address;
insert into bus_enterprise(id, name, email, phone, tin) values (1,'GodFud','kiruthigaa@paaril.com','080-40959550','1');
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values (1, 'D3-1002, L&T South City',1,1,18,1,'560076');
insert into bus_enterprise_address(id, enterprise_id, address_id, is_primary) values (1,1,1,'Y');

delete from core_property;

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

insert into core_property(id, prefix, name, type, value) values(71, 'mail', 'order.email', 'string', 'kiruthigaa@paaril.com');
insert into core_property(id, prefix, name, type, value) values(72, 'mail', 'order.email.bcc', 'string', 'kirusiva28@gmail.com,sivarajs@gmail.com');

insert into core_property(id, prefix, name, type, value) values(90, 'sms', 'provider.url', 'string', 'https://smsapi.24x7sms.com/api_2.0/SendSMS.aspx?APIKEY=hDuiVf92kbx&SenderID=PAARIL&ServiceName=TEMPLATE_BASED&MobileNo={0}&Message={1}');



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


insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-1, 'Off Bannerghatta Road', 1, 1, 18, 1, 560076);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-2, 'RBI Layout', 1, 1, 18, 1, 560075);

insert into core_housing_complex(id, name, address_id) values(1,'L&T South City', -1);
insert into core_housing_complex(id, name, address_id) values(2,'Elita Promenade',-2);

-- insert into core_city_area(id, name, city_id) values(21, 'Bellandur', 1);

delete from fin_payment_method;
insert into fin_payment_method(id, name, uid) values (1,'Cash/Card On Delivery', 'COD');

delete from mdm_product_category;
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (1, NULL, 'Food Products', 'category', 'module', '/Food Products', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2, NULL, 'Dairy Products', 'category', 'module', '/Dairy Products', 4, 'Y', 1);

-- Food Items
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (101, 1, 'Sugar Items', 'category', 'module', '/Food Products/Sugar Items', 0, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (102, 1, 'Poultry Items', 'category', 'module', '/Food Products/Poultry Items', 1, 'Y', 1);

-- Dairy Produc
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (201, 2, 'Milk', 'category', 'module', '/Dairy Products/Milk', 0, 'Y', 2);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (202, 2, 'Ghee, Butter & Paneer', 'category', 'module', '/Dairy Products/Ghee, Butter & Paneer', 1, 'Y', 2);



delete from core_schedule;
insert into core_schedule(id, name, action_class, type, start_time, end_time, trigger_on_start, schedule_trigger_id) values(1, 'Email Dispatch', 'meru.app.service.schedule.job.EmailScheduleJob', 'second', NULL, NULL, 'Y', 1);

delete from core_schedule_trigger;
insert into core_schedule_trigger(id, frequency) values(1, 3);



