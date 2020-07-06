-- alter table mdm_product add column delivery_instructions varchar(100);

drop table bp_customer_group;
create table bp_customer_group (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    discount float not null,
    wallet_exempted varchar(1),
    CONSTRAINT UK_bp_customer_group_91108202 UNIQUE (name)
) ENGINE=InnoDB;

insert into core_property(id, prefix, name, type, value) values(81, 'app', 'order.walletThreshold', 'string', '50');
insert into core_property(id, prefix, name, type, value) values(1001, 'app', 'error.insufficent_balance', 'string', 'Insufficient balance in your wallet. Please add money to your wallet by paying via UPI (GPay, PhonePe, PayTM) to 9880960654 (Kiruthiga) and drop a message to the same number');

insert into bp_customer_group(id, name, discount, wallet_exempted) values(1, 'Regular', 0, 'Y');