use security;
-- delete from sec_user where name <> 'admin';
delete from sec_user where name is NULL;

use app;
delete from bp_business_partner;
delete from bp_business_partner_address;


use account;
-- delete from sec_user where name <> 'admin';
delete from sec_user where name not in ('admin');

use ecom;
delete from bp_business_partner;
delete from bp_business_partner_address;


