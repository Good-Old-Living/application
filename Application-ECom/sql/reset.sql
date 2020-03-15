use account
source /scratch/dev/product/application/Application-Security/_generated/sql/schema_mysql.sql
source /scratch/dev/product/application/Application-Security/sql/seed-data.sql
use ecom
source /scratch/dev/product/application/Application-ECom/_generated/sql/schema_mysql.sql
source /scratch/dev/product/application/Application-ECom/sql/seed_data.sql



delete from sec_new_user;
delete from sec_user;
delete from bp_customer;
delete from bp_customer_address;
delete from sales_sales_order;
delete from sales_sales_order_line_item;