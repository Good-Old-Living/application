drop table if exists core_client;
drop table if exists core_city_area;
drop table if exists core_country;
drop table if exists core_housing_complex;
drop table if exists core_city;
drop table if exists core_housing_complex_address;
drop table if exists core_state;
drop table if exists core_address;
drop table if exists core_sequence_id;
drop table if exists core_schedule_trigger;
drop table if exists core_schedule;
drop table if exists core_app_entity_state;
drop table if exists core_entity_feature_value;
drop table if exists core_entity_feature;
drop table if exists core_property;
drop table if exists core_app_hierarchical_entity;
drop table if exists core_property_group;
drop table if exists core_app_entity_sequence;
drop table if exists comm_send_s_m_s;
drop table if exists comm_alert;
drop table if exists comm_send_email;
drop table if exists comm_email;
drop table if exists sales_sales_order_line_item;
drop table if exists sales_sales_invoice;
drop table if exists sales_sales_order;
drop table if exists sales_payment_transaction;
drop table if exists store_product_notification;
drop table if exists store_shopping_cart;
drop table if exists store_shopping_cart_line_item;
drop table if exists bp_customer_wallet_amount;
drop table if exists bp_customer;
drop table if exists bp_business_partner_group;
drop table if exists bp_customer_address;
drop table if exists bp_razor_pay_payment;
drop table if exists bp_customer_wallet;
drop table if exists bp_customer_wallet_history;
drop table if exists bp_business_partner;
drop table if exists bp_customer_payment;
drop table if exists bp_customer_group;
drop table if exists mdm_product_category_feature;
drop table if exists mdm_product_line_item;
drop table if exists mdm_product_category;
drop table if exists mdm_product;
drop table if exists mdm_product_feature;
drop table if exists mdm_product_feature_value;
drop table if exists sec_role;
drop table if exists sec_resource_permission;
drop table if exists sec_resource_security;
drop table if exists sec_new_user;
drop table if exists sec_user_role;
drop table if exists sec_user;
drop table if exists bus_enterprise_address;
drop table if exists bus_enterprise;
drop table if exists mar_sales_order_complimentary_item;
drop table if exists mar_sales_offer;
drop table if exists subs_subscription_deviation;
drop table if exists subs_subscription_line_item;
drop table if exists subs_subscription;
drop table if exists fin_payment_method;
drop table if exists fin_tax;
drop table if exists purs_purchase_order_line_item;
drop table if exists purs_purchase_order;

create table core_client (
    id bigint not null primary key auto_increment,
    app_id bigint not null,
    name varchar(100) not null,
    email varchar(100) not null,
    phone varchar(100) not null,
    fax varchar(100),
    tin varchar(100) not null,
    CONSTRAINT UK_core_client_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table core_city_area (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    city_id bigint not null
) ENGINE=InnoDB;

create table core_country (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    code varchar(10) not null
) ENGINE=InnoDB;

create table core_housing_complex (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    address_id bigint not null,
    CONSTRAINT UK_core_housing_complex_101215263 UNIQUE (name,address_id)
) ENGINE=InnoDB;

create table core_city (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    country_id bigint not null,
    state_id bigint not null
) ENGINE=InnoDB;

create table core_housing_complex_address (
    id bigint not null primary key auto_increment,
    housing_complex_id bigint not null,
    block varchar(100),
    number varchar(100) not null
) ENGINE=InnoDB;

create table core_state (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    country_id bigint not null,
    code varchar(10) not null
) ENGINE=InnoDB;

create table core_address (
    id bigint not null primary key auto_increment,
    address varchar(100) not null,
    landmark varchar(100),
    area_id bigint,
    city_id bigint not null,
    state_id bigint not null,
    country_id bigint not null,
    pin_code int not null,
    latlng varchar(100)
) ENGINE=InnoDB;

create table core_sequence_id (
    id bigint not null primary key auto_increment,
    entity_id bigint not null,
    name varchar(100) not null,
    value bigint not null,
    CONSTRAINT UK_core_sequence_id_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table core_schedule_trigger (
    id bigint not null primary key auto_increment,
    frequency int,
    at_time varchar(100),
    day varchar(100)
) ENGINE=InnoDB;

create table core_schedule (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    action_class varchar(100) not null,
    type varchar(100) not null,
    start_time timestamp null,
    end_time timestamp null,
    trigger_on_start varchar(1),
    schedule_trigger_id bigint not null,
    CONSTRAINT UK_core_schedule_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table core_app_entity_state (
    id bigint not null primary key auto_increment,
    entity varchar(100) not null,
    code int not null,
    status varchar(100) not null,
    CONSTRAINT UK_core_app_entity_state_659150602 UNIQUE (entity,code)
) ENGINE=InnoDB;

create table core_entity_feature_value (
    id bigint not null primary key auto_increment,
    entity_feature_id bigint not null,
    row_attribute_name varchar(100),
    column_attribute_name varchar(100),
    value varchar(100),
    sort_order int default 0
) ENGINE=InnoDB;

create table core_entity_feature (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    type_id bigint not null,
    owner_id bigint not null,
    owner_type varchar(100) not null,
    sort_order int default 0,
    values_id bigint
) ENGINE=InnoDB;

create table core_property (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    type varchar(100) not null,
    owner_id bigint,
    category varchar(100),
    prefix varchar(100),
    value varchar(2000) not null,
    CONSTRAINT UK_core_property__1911615833 UNIQUE (owner_id,category,prefix,name)
) ENGINE=InnoDB;

create table core_app_hierarchical_entity (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    qualified_name varchar(100),
    category varchar(100),
    parent_id bigint,
    type varchar(100) not null,
    kind varchar(100) not null,
    action varchar(100),
    CONSTRAINT UK_app_hierarchical_base_entity_1357597990 UNIQUE (parent_id,name)
) ENGINE=InnoDB;

create table core_property_group (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    value varchar(100) not null,
    CONSTRAINT UK_core_property_group_685338716 UNIQUE (name,value)
) ENGINE=InnoDB;

create table core_app_entity_sequence (
    id bigint not null primary key auto_increment,
    entity_id bigint,
    name varchar(100) not null,
    value bigint not null,
    CONSTRAINT UK_core_app_entity_sequence_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table comm_send_s_m_s (
    id bigint not null primary key auto_increment,
    number varchar(100) not null,
    message varchar(256) not null,
    sent_on timestamp not null,
    reference varchar(100) not null,
    delivered_on timestamp null,
    state varchar(100) not null
) ENGINE=InnoDB;

create table comm_alert (
    id bigint not null primary key,
    sender varchar(100) not null,
    receiver varchar(100) not null,
    category varchar(100) not null,
    type varchar(100) not null,
    description varchar(100) not null,
    target varchar(100) not null,
    received_on timestamp not null
) ENGINE=InnoDB;

create table comm_send_email (
    id bigint not null primary key auto_increment,
    tos varchar(500) not null,
    ccs varchar(500),
    bccs varchar(500),
    subject varchar(100) not null,
    message text not null,
    content_type varchar(100) not null,
    sent_on timestamp not null,
    reference varchar(100),
    delivered_on timestamp null,
    state varchar(100) not null
) ENGINE=InnoDB;

create table comm_email (
    id bigint not null primary key,
    sender varchar(100) not null,
    receiver varchar(100) not null,
    message varchar(1000) not null,
    subject varchar(200) not null,
    received_on timestamp not null,
    state int not null
) ENGINE=InnoDB;

create table sales_sales_order_line_item (
    id bigint not null primary key auto_increment,
    sales_order_id bigint not null,
    product_line_item_id bigint not null,
    quantity int not null,
    net_quantity float,
    total_price float not null,
    unit_mrp float not null,
    unit_price float not null,
    discount float,
    discount_type_id bigint,
    tax_rate float not null default 0,
    notes varchar(100),
    CONSTRAINT UK_sales_sales_order_line_item__1548591821 UNIQUE (sales_order_id,product_line_item_id)
) ENGINE=InnoDB;

create table sales_sales_invoice (
    id bigint not null primary key auto_increment,
    number varchar(100) not null,
    sales_order_id bigint not null,
    notes varchar(100),
    CONSTRAINT UK_sales_sales_invoice_1674106984 UNIQUE (number)
) ENGINE=InnoDB;

create table sales_sales_order (
    id bigint not null primary key auto_increment,
    order_id varchar(100) not null,
    customer_id varchar(100) not null,
    transaction_id varchar(100),
    session_id varchar(100) not null,
    delivery_address_id bigint not null,
    delivery_address_text varchar(500),
    payment_method_id bigint,
    created_on timestamp not null,
    amount float not null,
    amount_paid float,
    code varchar(100),
    state_id bigint not null,
    payment_received varchar(1) not null,
    delivery_instructions varchar(500),
    payment_mode_id bigint not null,
    payment_order_id varchar(100),
    payment_id varchar(100),
    payment_transaction_id bigint,
    CONSTRAINT UK_sales_sales_order_1151114186 UNIQUE (order_id)
) ENGINE=InnoDB;

create table sales_payment_transaction (
    id bigint not null primary key auto_increment,
    wallet_amount_deducted int,
    wallet_amount_added int
) ENGINE=InnoDB;

create table store_product_notification (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    product_line_item_id bigint not null,
    CONSTRAINT UK_store_product_notification_221529140 UNIQUE (customer_id,product_line_item_id)
) ENGINE=InnoDB;

create table store_shopping_cart (
    id bigint not null primary key auto_increment,
    customer_id bigint,
    session_id varchar(100) not null,
    created_time timestamp not null,
    items_id bigint,
    CONSTRAINT UK_store_shopping_cart_696693053 UNIQUE (customer_id)
) ENGINE=InnoDB;

create table store_shopping_cart_line_item (
    id bigint not null primary key auto_increment,
    shopping_cart_id bigint not null,
    product_line_item_id bigint not null,
    quantity int not null,
    total_price float not null,
    CONSTRAINT UK_store_shopping_cart_line_item_1726628601 UNIQUE (shopping_cart_id,product_line_item_id)
) ENGINE=InnoDB;

create table bp_customer_wallet_amount (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    amount int not null,
    description varchar(100),
    CONSTRAINT UK_bp_customer_wallet_amount__492871880 UNIQUE (customer_id)
) ENGINE=InnoDB;

create table bp_customer (
    id bigint not null primary key auto_increment,
    name varchar(100),
    email varchar(100),
    mobile varchar(100) not null,
    group_id bigint,
    user_id bigint not null,
    CONSTRAINT UK_bp_customer_1872440165 UNIQUE (user_id)
) ENGINE=InnoDB;

create table bp_business_partner_group (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    discount float not null,
    CONSTRAINT UK_bp_business_partner_group_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table bp_customer_address (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    name varchar(100) not null,
    mobile varchar(100) not null,
    alt_phone varchar(100),
    housing_complex_address_id bigint,
    address_id bigint,
    is_primary varchar(1)
) ENGINE=InnoDB;

create table bp_razor_pay_payment (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    transaction_id varchar(100) not null,
    order_id varchar(100) not null,
    amount int not null,
    status varchar(100) not null,
    created_on timestamp not null,
    CONSTRAINT UK_bp_razor_pay_payment_1624361938 UNIQUE (customer_id,transaction_id)
) ENGINE=InnoDB;

create table bp_customer_wallet (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    amount int not null,
    created_on timestamp not null,
    updated_on timestamp null,
    CONSTRAINT UK_bp_customer_wallet__492871880 UNIQUE (customer_id)
) ENGINE=InnoDB;

create table bp_customer_wallet_history (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    curr_amount int not null,
    prev_amount int not null,
    amount_deducted int,
    amount_added int,
    description varchar(100),
    created_on timestamp not null
) ENGINE=InnoDB;

create table bp_business_partner (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    contact_person varchar(100) not null,
    mobile int not null,
    landline int,
    email varchar(100),
    address_id bigint not null,
    CONSTRAINT UK_bp_business_partner_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table bp_customer_payment (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    transaction_id varchar(100) not null,
    amount int not null,
    status varchar(100) not null,
    checksum varchar(500) not null,
    created_on timestamp not null,
    CONSTRAINT UK_bp_customer_payment_1624361938 UNIQUE (customer_id,transaction_id)
) ENGINE=InnoDB;

create table bp_customer_group (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    discount float not null,
    wallet_exempted varchar(1),
    CONSTRAINT UK_bp_customer_group_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table mdm_product_category_feature (
    id bigint not null primary key auto_increment,
    category_id varchar(100) not null,
    name varchar(100) not null,
    type_id bigint not null,
    attributes varchar(2000),
    sort_order int not null default 0,
    CONSTRAINT UK_mdm_product_category_feature_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table mdm_product_line_item (
    id bigint not null primary key auto_increment,
    uid varchar(100),
    product_id bigint not null,
    code varchar(10) not null,
    search_terms varchar(5000) not null,
    description varchar(5000),
    category varchar(100) not null,
    quantity int not null,
    net_quantity int,
    unit_of_measure_id bigint not null,
    package_type_id bigint,
    mrp float not null,
    price float not null,
    savings float,
    discount float,
    discount_type_id bigint,
    is_default varchar(1),
    sort_order int,
    is_active varchar(1) not null default 'Y',
    in_stock varchar(1) not null default 'Y',
    is_offer varchar(1) default 'N',
    tags varchar(100),
    tn_image varchar(100),
    CONSTRAINT UK_mdm_product_line_item_90793676 UNIQUE (code),
    CONSTRAINT UK_mdm_product_line_item__1031836417 UNIQUE (product_id,quantity,unit_of_measure_id,package_type_id)
) ENGINE=InnoDB;

create table mdm_product_category (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    qualified_name varchar(100),
    category varchar(100),
    parent_id bigint,
    type varchar(100) not null,
    kind varchar(100) not null,
    action varchar(100),
    sort_order int default 0,
    is_active varchar(1) not null default 'Y',
    tags varchar(100),
    availability varchar(100),
    notes varchar(500),
    tax_id bigint,
    orderable varchar(1),
    CONSTRAINT UK_app_hierarchical_base_entity_1357597990 UNIQUE (parent_id,name),
    CONSTRAINT UK_mdm_product_category__1355873464 UNIQUE (qualified_name)
) ENGINE=InnoDB;

create table mdm_product (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    uid varchar(100),
    brand varchar(100),
    type_id bigint,
    product_category_id bigint not null,
    short_description varchar(100),
    description text,
    rating int,
    popularity float,
    is_active varchar(1) not null default 'Y',
    tags varchar(100),
    tn_image varchar(200),
    tax_id bigint,
    subscribable varchar(1),
    orderable varchar(1),
    delivery_instructions varchar(250),
    CONSTRAINT UK_mdm_product_1933564132 UNIQUE (product_category_id,name,brand)
) ENGINE=InnoDB;

create table mdm_product_feature (
    id bigint not null primary key auto_increment,
    product_id varchar(100) not null,
    product_category_feature_id varchar(100),
    name varchar(100) not null,
    type_id bigint not null,
    attributes varchar(2000),
    sort_order int not null default 0,
    CONSTRAINT UK_mdm_product_feature_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table mdm_product_feature_value (
    id bigint not null primary key auto_increment,
    owner_id varchar(100) not null,
    owner_type varchar(100) not null,
    row_attribute_name varchar(100),
    column_attribute_name varchar(100),
    value varchar(100),
    sort_order int default 0
) ENGINE=InnoDB;

create table sec_role (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    home varchar(100),
    CONSTRAINT UK_sec_role_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table sec_resource_permission (
    id bigint not null primary key auto_increment,
    resource_security_id bigint not null,
    role_id bigint not null
) ENGINE=InnoDB;

create table sec_resource_security (
    id bigint not null primary key auto_increment,
    resource_pattern varchar(100) not null,
    resource_type varchar(100) not null,
    access_type varchar(100),
    is_protected varchar(1) not null,
    permissions_id bigint
) ENGINE=InnoDB;

create table sec_new_user (
    id bigint not null primary key auto_increment,
    mobile varchar(100) not null,
    mobile_access_token varchar(100) not null,
    CONSTRAINT UK_sec_new_user_1639615937 UNIQUE (mobile)
) ENGINE=InnoDB;

create table sec_user_role (
    id bigint not null primary key auto_increment,
    user_id bigint not null,
    role_id bigint not null,
    CONSTRAINT UK_sec_user_role_346843723 UNIQUE (user_id,role_id)
) ENGINE=InnoDB;

create table sec_user (
    id bigint not null primary key auto_increment,
    mobile varchar(100) not null,
    password varchar(100) not null,
    primary_role_id bigint not null,
    state varchar(100) not null,
    info varchar(100),
    created_on date not null,
    mobile_access_token varchar(100),
    CONSTRAINT UK_sec_user_1639615937 UNIQUE (mobile)
) ENGINE=InnoDB;

create table bus_enterprise_address (
    id bigint not null primary key auto_increment,
    enterprise_id varchar(100) not null,
    address_id bigint not null,
    is_primary varchar(1),
    CONSTRAINT UK_bus_enterprise_address__1908266512 UNIQUE (enterprise_id,address_id)
) ENGINE=InnoDB;

create table bus_enterprise (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    email varchar(100) not null,
    phone varchar(100) not null,
    fax varchar(100),
    tin varchar(100) not null,
    CONSTRAINT UK_bus_enterprise_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table mar_sales_order_complimentary_item (
    id bigint not null primary key auto_increment,
    sales_offer_id varchar(100) not null,
    product_line_item_id bigint not null,
    mrp float not null,
    CONSTRAINT UK_mar_sales_order_complimentary_item_1855948993 UNIQUE (sales_offer_id,product_line_item_id)
) ENGINE=InnoDB;

create table mar_sales_offer (
    id bigint not null primary key auto_increment,
    title varchar(100) not null,
    start_time date not null,
    end_time date not null,
    product_category_id bigint,
    minimum_purchase bigint,
    customer_types varchar(100),
    discount float not null,
    discount_type_id bigint not null,
    sort_order int,
    is_complimentary_or varchar(1),
    complimentary_items_id bigint,
    CONSTRAINT UK_mar_sales_offer__1464826535 UNIQUE (title)
) ENGINE=InnoDB;

create table subs_subscription_deviation (
    id bigint not null primary key auto_increment,
    subscription_line_item_id bigint,
    quantity int not null,
    start_date date not null,
    end_date date not null
) ENGINE=InnoDB;

create table subs_subscription_line_item (
    id bigint not null primary key auto_increment,
    subscription_id bigint not null,
    product_line_item_id bigint not null,
    quantity int not null,
    frequency_id bigint not null,
    deviations_id bigint,
    start_date date not null,
    state_id bigint not null,
    CONSTRAINT UK_subs_subscription_line_item__903128018 UNIQUE (subscription_id,product_line_item_id)
) ENGINE=InnoDB;

create table subs_subscription (
    id bigint not null primary key auto_increment,
    customer_id bigint not null,
    delivery_address_id bigint not null,
    state_id bigint not null,
    line_items_id bigint,
    CONSTRAINT UK_subs_subscription_1723908697 UNIQUE (customer_id,delivery_address_id)
) ENGINE=InnoDB;

create table fin_payment_method (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    uid varchar(100) not null,
    CONSTRAINT UK_fin_payment_method_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table fin_tax (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    rate float not null,
    CONSTRAINT UK_fin_tax_91108202 UNIQUE (name)
) ENGINE=InnoDB;

create table purs_purchase_order_line_item (
    id bigint not null primary key auto_increment,
    purchase_order_id varchar(100) not null,
    quantity int not null,
    net_quantity float,
    total_price float not null,
    unit_mrp float not null,
    unit_price float not null,
    discount float,
    discount_type_id bigint,
    tax_rate float not null default 0,
    notes varchar(100)
) ENGINE=InnoDB;

create table purs_purchase_order (
    id bigint not null primary key auto_increment,
    order_id varchar(100),
    business_partner_id bigint not null,
    placed_on timestamp not null,
    state_id bigint not null,
    amount float not null,
    CONSTRAINT UK_purs_purchase_order_1151114186 UNIQUE (order_id)
) ENGINE=InnoDB;
