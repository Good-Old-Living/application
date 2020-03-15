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
drop table if exists core_client;
drop table if exists core_city_area;
drop table if exists core_country;
drop table if exists core_housing_complex;
drop table if exists core_city;
drop table if exists core_housing_complex_address;
drop table if exists core_state;
drop table if exists core_address;

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
    block varchar(100) not null,
    number varchar(100) not null,
    housing_complex_id bigint not null,
    CONSTRAINT UK_core_housing_complex_address_1669423064 UNIQUE (block,number,housing_complex_id)
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
