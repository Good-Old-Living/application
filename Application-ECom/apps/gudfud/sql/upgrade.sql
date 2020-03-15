
alter table mdm_product_category add column notes varchar(500) after availability;

delete from mdm_product_category where id in (1,101,102,103);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, notes, tax_id) values (1, NULL, 'Fruits & Vegetables', 'category', 'module', '/Fruits & Vegetables', 0, 'Y', 'Organic fruits and vegetables supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.', 1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id) values (101, 1, 'Organic Fruits', 'category', 'module', '/Fruits & Vegetables/Organic Fruits', 0, 'Y', 'Available only on Tuesdays depending on the availability', 'Organic fruits supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id) values (102, 1, 'Organic Vegetables', 'category', 'module', '/Fruits & Vegetables/Organic Vegetables', 1, 'Y', 'Available only on Tuesdays depending on the availability', 'Organic vvegetables supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);
insert into mdm_product_category(id, parent_id, name, type, kind, qualified_name, sort_order, is_active, availability, notes, tax_id) values (103, 1, 'Organic Leafy Greens', 'category', 'module', '/Fruits & Vegetables/Organic Leafy Greens', 2, 'Y', 'Available only on Tuesdays depending on the availability','Organic greens supply will be available only on Tuesdays. Booking will be open on Sunday and closed at Monday 12PM.',1);




