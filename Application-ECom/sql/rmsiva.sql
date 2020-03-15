use security;
delete from sec_new_user where email='sivarajs@gmail.com';
delete from sec_user where email='sivarajs@gmail.com';
use paaril;
delete from core_address where id = (select address_id from bp_customer_address where customer_id = (select id from bp_customer where email='sivarajs@gmail.com'));
delete from bp_customer_address where customer_id = (select id from bp_customer where email='sivarajs@gmail.com');
delete from bp_customer where email='sivarajs@gmail.com';