 delete from core_property_group where id in(251,252,253);
 
insert into core_property_group(id, name, value) values(251, 'payment-mode', 'Cash On Delivery');
insert into core_property_group(id, name, value) values(252, 'payment-mode', 'GPay');
insert into core_property_group(id, name, value) values(253, 'payment-mode', 'Credit Cart/Debit Cart/Net Banking');
insert into core_property_group(id, name, value) values(254, 'payment-mode', 'Wallet');

alter table sales_sales_order add column payment_method_id bigint;

alter table sales_sales_order add column payment_mode_id bigint not null default 251;
alter table sales_sales_order add column payment_order_id varchar(100);
alter table sales_sales_order add column payment_id varchar(100);
alter table sales_sales_order add column payment_transaction_id bigint;

create table sales_payment_transaction (
    id bigint not null primary key auto_increment,
    sales_order_id bigint not null,
    wallet_amount_deducted int,
    wallet_amount_added int,
    CONSTRAINT UK_sales_payment_transaction__1250121668 UNIQUE (sales_order_id)
) ENGINE=InnoDB;




alter table sales_payment_transaction drop column sales_order_id;