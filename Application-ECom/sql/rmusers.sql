use account;
delete from sec_new_user;
delete from sec_user;
delete from sec_new_user;

use ecom;
delete from bp_customer;
delete from core_address;
delete from bp_customer_address;
delete from store_shopping_cart;
delete from store_shopping_cart_line_item;
delete from sales_sales_order;
delete from sales_sales_order_line_item;


delete from core_app_entity_sequence;
insert into core_app_entity_sequence(id, name, value) values(1, 'SalesOrder.Id',1);
insert into core_app_entity_sequence(id, name, value) values(2, 'InvoiceOrder.Id',1);
insert into core_app_entity_sequence(id, name, value) values(3, 'PaymentGateway.Id',1);
insert into core_app_entity_sequence(id, name, value) values(4, 'PurchaseOrder.Id',1);
