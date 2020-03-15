delete from bus_enterprise;
delete from bus_enterprise_address;
delete from core_address;
insert into bus_enterprise(id, name, email, phone, tin) values (1,'Paaril','kiruthigaa@paaril.com','080-40959550','1');
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

insert into core_property(id, prefix, name, type, value) values(80, 'sms', 'order.mobile', 'string', '919880960654');


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
insert into core_city_area(id, name, city_id) values(2, 'Arekere Mico Layout 2nd Stage', 1);
insert into core_city_area(id, name, city_id) values(3, 'Arekere', 1);

insert into core_city_area(id, name, city_id) values(11, 'JP Nagar 1st Phase', 1);
insert into core_city_area(id, name, city_id) values(12, 'JP Nagar 2nd Phase', 1);
insert into core_city_area(id, name, city_id) values(13, 'JP Nagar 3rd Phase', 1);
insert into core_city_area(id, name, city_id) values(14, 'JP Nagar 4th Phase', 1);
insert into core_city_area(id, name, city_id) values(15, 'JP Nagar 5th Phase', 1);
insert into core_city_area(id, name, city_id) values(16, 'JP Nagar 6th Phase', 1);
insert into core_city_area(id, name, city_id) values(17, 'JP Nagar 7th Phase', 1);
insert into core_city_area(id, name, city_id) values(18, 'JP Nagar 8th Phase', 1);

insert into core_city_area(id, name, city_id) values(101, 'Ballendur', 1);


insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-1, 'Off Bannerghatta Road', 1, 1, 18, 1, 560076);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-2, 'RBI Layout', 17, 1, 18, 1, 560075);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-3, 'Bannerghatta Road', 3, 1, 18, 1, 560076);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-4, 'Bannerghatta Road', 3, 1, 18, 1, 560076);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-5, 'Off Bannerghatta Road', 1, 1, 18, 1, 560076);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-6, 'RBI Layout', 17, 1, 18, 1, 560075);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-7, 'RBI Layout', 17, 1, 18, 1, 560075);
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-101, 'Trinity Meadows Road', 101, 1, 18, 1, 560103);

insert into core_housing_complex(id, name, address_id) values(1,'L&T South City', -1);
insert into core_housing_complex(id, name, address_id) values(2,'Elita Promenade',-2);
insert into core_housing_complex(id, name, address_id) values(3,'Mahindra Windchimes',-3);
insert into core_housing_complex(id, name, address_id) values(4,'Mantri Paradise',-4);
insert into core_housing_complex(id, name, address_id) values(5,'SJR Luxuria',-5);
insert into core_housing_complex(id, name, address_id) values(6,'Brigade Millenium ',-6);
insert into core_housing_complex(id, name, address_id) values(7,'Brigade Gardenia ',-7);

insert into core_housing_complex(id, name, address_id) values(101,'Kristal Brookite',-101);
insert into core_housing_complex(id, name, address_id) values(102,'Kristal Halite I',-101);
insert into core_housing_complex(id, name, address_id) values(103,'Kristal Halite II',-101);
insert into core_housing_complex(id, name, address_id) values(104,'Kristal Tiara',-101);


-- insert into core_city_area(id, name, city_id) values(21, 'Bellandur', 1);

delete from fin_payment_method;
insert into fin_payment_method(id, name, uid) values (1,'Cash/Card On Delivery', 'COD');

delete from mdm_product_category;
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, notes, tax_id, orderable) values (1, NULL, 'Fruits & Vegetables', 'category', 'module', '/Fruits & Vegetables', 0, 'Y', 'Organic fruits and vegetables supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2, NULL, 'Grocery & Staples', 'category', 'module', '/Grocery & Staples', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (3, NULL, 'Ready To Cook', 'category', 'module', '/Ready To Cook', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (4, NULL, 'Breakfast & Snacks', 'category', 'module', '/Breakfast & Snacks', 3, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (5, NULL, 'Dairy & Eggs', 'category', 'module', '/Dairy & Eggs', 4, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (6, NULL, 'Beverages', 'category', 'module', '/Beverages', 5, 'Y', 1);

-- Fruits & Vegetables
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id, orderable) values (101, 1, 'Organic Fruits', 'category', 'module', '/Fruits & Vegetables/Organic Fruits', 0, 'Y', 'Available only on Tuesdays depending on the availability', 'Organic fruits supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id, orderable) values (102, 1, 'Organic Vegetables', 'category', 'module', '/Fruits & Vegetables/Organic Vegetables', 1, 'Y', 'Available only on Tuesdays depending on the availability', 'Organic vvegetables supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id, orderable) values (103, 1, 'Organic Leafy Greens', 'category', 'module', '/Fruits & Vegetables/Organic Leafy Greens', 2, 'Y', 'Available only on Tuesdays depending on the availability','Organic greens supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);



-- insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, tax_id, orderable) values ('FRVG11', 'FRVG1', 'Apple', 'category', 'module', '/Fruits & Vegetables/Organic Fruits/Banana Elaichi', 0, 'Y', 'Available only on Tuesdays and Fridays depending on the availability', 1);

-- insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, tax_id, orderable) values ('FRVG21', 'FRVG2', 'Onion, Tomato & Potato', 'category', 'module', '/Fruits & Vegetables/Organic Vegetables/Onion, Tomato & Potato', 0, 'Y', 'Available only on Tuesdays and Fridays depending on the availability', 1);

-- Food Items
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2001, 2, 'Rice', 'category', 'module', '/Grocery & Staples/Rice', 0, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2002, 2, 'Millets', 'category', 'module', '/Grocery & Staples/Millets', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2003, 2, 'Pulses', 'category', 'module', '/Grocery & Staples/Pulses', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2004, 2, 'Flour & Sooji', 'category', 'module', '/Grocery & Staples/Flour & Sooji', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2005, 2, 'Spices & Masalas', 'category', 'module', '/Grocery & Staples/Spices & Masalas', 3, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2006, 2, 'Edible Oil', 'category', 'module', '/Grocery & Staples/Edible Oil', 4, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2007, 2, 'Salt & Sweeteners', 'category', 'module', '/Grocery & Staples/Salt & Sweeteners', 5, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (2008, 2, 'Sauces, Ketchup and Spreads', 'category', 'module', '/Grocery & Staples/Sauces, Ketchup and Spreads', 5, 'Y', 1);


-- Ready To Cook
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (3001, 3, 'Idli & Dosa Batter', 'category', 'module', '/Ready To Cook/Idli & Dosa Batter', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (3002, 3, 'Pasta & Noodles', 'category', 'module', '/Ready To Cook/Pasta & Noodles', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (3003, 3, 'Poha & Vermicelli', 'category', 'module', '/Ready To Cook/Poha & Vermicelli', 3, 'Y', 1);

-- Ready To Eat
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (4001, 4, 'Cereals', 'category', 'module', '/Breakfast & Snacks/Cereals', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (4002, 4, 'Cookies & Snacks', 'category', 'module', '/Breakfast & Snacks/Cookies & Snacks', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (4003, 4, 'Dry Fruits', 'category', 'module', '/Breakfast & Snacks/Dry Fruits', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (4004, 4, 'Breads', 'category', 'module', '/Breakfast & Snacks/Breads', 2, 'Y', 1, 'Y');

-- Millet Snacks
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (40021, 4002, 'Millet Cookies', 'category', 'module', '/Breakfast & Snacks/Cookies & Snacks/Millet Cookies', 8, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (40022, 4002, 'Millet Snacks', 'category', 'module', '/Breakfast & Snacks/Cookies & Snacks/Millet Snacks', 1, 'Y', 1);

-- Dairy Products
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (5000, 5, 'Eggs', 'category', 'module', '/Dairy & Eggs/Eggs', 0, 'Y', 2, 'Y');
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (5001, 5, 'Ghee, Butter & Paneer', 'category', 'module', '/Dairy & Eggs/Ghee, Butter & Paneer', 1, 'Y', 2);

-- Beverages
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (6001, 6, 'Coffee', 'category', 'module', '/Beverages/Coffee', 0, 'Y', 2);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id, orderable) values (6002, 6, 'Tea', 'category', 'module', '/Beverages/Tea', 0, 'Y', 2);


delete from core_schedule;
-- insert into core_schedule(id, name, action_class, type, start_time, end_time, trigger_on_start, schedule_trigger_id) values(1, 'Email Dispatch', 'meru.app.service.schedule.job.EmailScheduleJob', 'second', NULL, NULL, 'Y', 1);
insert into core_schedule(id, name, action_class, type, start_time, end_time, trigger_on_start, schedule_trigger_id) values(2, 'Product Delivery CutOff', 'meru.app.service.schedule.job.EmailScheduleJob', 'hour', NULL, NULL, 'Y', 2);

delete from core_schedule_trigger;
-- insert into core_schedule_trigger(id, frequency) values(1, 3);
 insert into core_schedule_trigger(id, frequency) values(2, 1);


delete from mdm_delivery_cutoff;
insert into mdm_delivery_cutoff(id, name, startTime, endTime, delivery, handler) values(1, 'Batter', '6 AM', '6 PM', '7 PM to 9 PM');
insert into mdm_delivery_cutoff(id, name, startTime, endTime, delivery, handler) values(2, 'Batter', '6 PM', '12 AM', 'Tomorrow 7 AM to 8 AM');
insert into mdm_delivery_cutoff(id, name, startTime, endTime, delivery, handler) values(2, 'Batter', '12 AM', '6 AM', '7 AM to 8 AM');

