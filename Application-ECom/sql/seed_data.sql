delete from sec_role;
delete from sec_user;

-- ecom
insert into sec_role (id, name, home) values(201, 'admin','/app');
insert into sec_role (id, name, home) values(202, 'customer',NULL);

-- password - Nakul2006
insert into sec_user (id, name, password, primary_role_id,state) values(-1, 'admin', '987e72af83c6bd51955c6adaacda9b65', "201", 'A');




delete from bus_enterprise;
delete from bus_enterprise_address;
delete from core_address;
insert into bus_enterprise(id, name, email, phone, tin) values (1,'Paaril.com','kiruthigaa@paaril.com','080-40959550','1');
insert into bus_enterprise_address(id, enterprise_id, address_id, is_primary) values (1,1,1,'Y');
insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values (1, 'D3-1002, L&T South City',1,1,18,1,'560076');




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

insert into core_property_group(id, name, value) values(91, 'product-type', 'Certified Organic');
insert into core_property_group(id, name, value) values(92, 'product-type', 'Organic');

insert into core_property_group(id, name, value) values(201, 'delivery-time-slot', '7 AM to 9 AM');
insert into core_property_group(id, name, value) values(202, 'delivery-time-slot', '11 AM to 1 PM');
insert into core_property_group(id, name, value) values(203, 'delivery-time-slot', '3 PM to 5 PM');
insert into core_property_group(id, name, value) values(204, 'delivery-time-slot', '7 PM to 9 PM');


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
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(125,101,'app-module-menu','Vegetable Price Upload','entity','app/module/mdm/catalog/VegetablePriceList.xhtml');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(401,104,'app-module-menu','Purchase Order','entity','app/module/purchase/PurchaseOrder.xhtml');

insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(501,105,'app-module-menu','Sales Order','entity','app/module/sales/SalesOrder.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(502,105,'app-module-menu','Sales Invoice','entity','app/module/sales/SalesInvoice.xhtml');
insert into core_app_hierarchical_entity(id, parent_id, type, name, kind, action) values(503,105,'app-module-menu','Veggies Summary','entity','app/module/sales/VeggiesSummary.xhtml');

delete from core_app_entity_sequence;
insert into core_app_entity_sequence(id, name, value) values(1, 'SalesOrder.Id',1);
insert into core_app_entity_sequence(id, name, value) values(2, 'InvoiceOrder.Id',1);
insert into core_app_entity_sequence(id, name, value) values(3, 'PaymentGateway.Id',1);
insert into core_app_entity_sequence(id, name, value) values(4, 'PurchaseOrder.Id',1);


delete from core_app_entity_state;

insert into core_app_entity_state(id, entity, code, status) values(1, "SalesOrder", 1, "New");
insert into core_app_entity_state(id, entity, code, status) values(2, "SalesOrder", 2, "In-Process");
insert into core_app_entity_state(id, entity, code, status) values(3, "SalesOrder", 3, "Shipped");
insert into core_app_entity_state(id, entity, code, status) values(4, "SalesOrder", 4, "Pending Delivery");
insert into core_app_entity_state(id, entity, code, status) values(5, "SalesOrder", 5, "Delivered");
insert into core_app_entity_state(id, entity, code, status) values(6, "SalesOrder", 6, "Cancelled By Customer");
insert into core_app_entity_state(id, entity, code, status) values(7, "SalesOrder", 7, "Cancelled");


delete from fin_payment_method;
insert into fin_payment_method(id, name, uid) values (1,'Cash/Card On Delivery', 'COD');
insert into fin_payment_method(id, name, uid) values (2,'Debit/Credit Card', 'CARD');
insert into fin_payment_method(id, name, uid) values (3,'Net Banking', 'NETBANK');


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


insert into core_address(id, address, area_id, city_id, state_id, country_id, pin_code) values(-1, 'Off Bannerghatta Road', 1, 1, 18, 1, 560076);

insert into core_housing_complex(id, name, address_id) values(1,'L&T South City', -1);

-- insert into core_city_area(id, name, city_id) values(21, 'Bellandur', 1);



delete from fin_tax;
insert into fin_tax(id, name, rate) values(1, 'GST - Nil', 0);
insert into fin_tax(id, name, rate) values(2, 'GST - 5%', 5);
insert into fin_tax(id, name, rate) values(3, 'GST - 12%', 12);
insert into fin_tax(id, name, rate) values(4, 'GST - 18%', 18);
insert into fin_tax(id, name, rate) values(5, 'GST - 28%', 28);

delete from mdm_product_category;
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, notes, tax_id) values (1, NULL, 'Fruits & Vegetables', 'category', 'module', '/Fruits & Vegetables', 0, 'Y', 'Organic fruits and vegetables supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2, NULL, 'Food Items', 'category', 'module', '/Food Items', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (3, NULL, 'Ready To Cook', 'category', 'module', '/Ready To Cook', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (4, NULL, 'Ready To Eat', 'category', 'module', '/Ready To Eat', 3, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (5, NULL, 'Dairy Products', 'category', 'module', '/Dairy Products', 4, 'Y', 1);

-- Fruits & Vegetables
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id) values (101, 1, 'Organic Fruits', 'category', 'module', '/Fruits & Vegetables/Organic Fruits', 0, 'Y', 'Available only on Tuesdays depending on the availability', 'Organic fruits supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id) values (102, 1, 'Organic Vegetables', 'category', 'module', '/Fruits & Vegetables/Organic Vegetables', 1, 'Y', 'Available only on Tuesdays depending on the availability', 'Organic vvegetables supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id) values (103, 1, 'Organic Leafy Greens', 'category', 'module', '/Fruits & Vegetables/Organic Leafy Greens', 2, 'Y', 'Available only on Tuesdays depending on the availability','Organic greens supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);



-- insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, tax_id) values ('FRVG11', 'FRVG1', 'Apple', 'category', 'module', '/Fruits & Vegetables/Organic Fruits/Banana Elaichi', 0, 'Y', 'Available only on Tuesdays and Fridays depending on the availability', 1);

-- insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, tax_id) values ('FRVG21', 'FRVG2', 'Onion, Tomato & Potato', 'category', 'module', '/Fruits & Vegetables/Organic Vegetables/Onion, Tomato & Potato', 0, 'Y', 'Available only on Tuesdays and Fridays depending on the availability', 1);

-- Staples
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2001, 2, 'Rice', 'category', 'module', '/Food Items/Rice', 0, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2002, 2, 'Millets', 'category', 'module', '/Food Items/Millets', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2003, 2, 'Pulses', 'category', 'module', '/Food Items/Pulses', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2004, 2, 'Flour & Sooji', 'category', 'module', '/Food Items/Flour & Sooji', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2005, 2, 'Spices & Masalas', 'category', 'module', '/Food Items/Spices & Masalas', 3, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2006, 2, 'Edible Oil', 'category', 'module', '/Food Items/Edible Oil', 4, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (2007, 2, 'Salt & Sugar Items', 'category', 'module', '/Food Items/Salt & Sugar Items', 5, 'Y', 1);


-- Ready To Cook
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (3001, 3, 'Idli & Dosa Batter', 'category', 'module', '/Ready To Cook/Idli & Dosa Batter', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (3002, 3, 'Pasta & Noodles', 'category', 'module', '/Ready To Cook/Pasta & Noodles', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (3003, 3, 'Poha & Vermicelli', 'category', 'module', '/Ready To Cook/Poha & Vermicelli', 3, 'Y', 1);

-- Ready To Eat
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (4001, 4, 'Cereals', 'category', 'module', '/Ready To Eat/Cereals', 1, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (4002, 4, 'Cookies and Snacks', 'category', 'module', '/Ready To Eat/Cookies and Snacks', 2, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (4003, 4, 'Dry Fruits', 'category', 'module', '/Ready To Eat/Dry Fruits', 2, 'Y', 1);

-- Millet Snacks
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (40021, 4002, 'Millet Cookies', 'category', 'module', '/Ready To Eat/Cookies and Snacks/Millet Cookies', 8, 'Y', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (40022, 4002, 'Millet Snacks', 'category', 'module', '/Ready To Eat/Cookies and Snacks/Millet Snacks', 1, 'Y', 1);

-- Dairy Produc
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, tax_id) values (5001, 5, 'Ghee, Butter & Paneer', 'category', 'module', '/Dairy Products/Ghee, Butter & Paneer', 0, 'Y', 2);

delete from core_schedule;
insert into core_schedule(id, name, action_class, type, start_time, end_time, trigger_on_start, schedule_trigger_id) values(1, 'Email Dispatch', 'meru.app.service.schedule.job.EmailScheduleJob', 'second', NULL, NULL, 'Y', 1);

delete from core_schedule_trigger;
insert into core_schedule_trigger(id, frequency) values(1, 3);



