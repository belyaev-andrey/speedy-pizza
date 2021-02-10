-- begin PIZZA_PIZZA
create table PIZZA_PIZZA (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    DIAMETER integer not null,
    CALORIES integer,
    PRICE decimal(19, 2) not null,
    --
    primary key (ID)
)^
-- end PIZZA_PIZZA
-- begin PIZZA_ORDER_POSITION
create table PIZZA_ORDER_POSITION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ORDER_ID uuid,
    PIZZA_ID uuid,
    AMOUNT integer,
    --
    primary key (ID)
)^
-- end PIZZA_ORDER_POSITION
-- begin PIZZA_DELIVERY
create table PIZZA_DELIVERY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PHONE_NUM varchar(255) not null,
    EMAIL varchar(255),
    CITY varchar(255) not null,
    STREET varchar(255),
    HOUSE_NUMBER varchar(255),
    APARTMENT_NUMBER varchar(255),
    DELIVERED_BY_ID uuid,
    DELIVERED_AT timestamp,
    ORDER_ID uuid not null,
    --
    primary key (ID)
)^
-- end PIZZA_DELIVERY
-- begin PIZZA_ORDER
create table PIZZA_ORDER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ORDER_NO varchar(255) not null,
    CREATED timestamp not null,
    STATUS integer not null,
    TOTAL_AMOUNT decimal(19, 2),
    --
    primary key (ID)
)^
-- end PIZZA_ORDER
-- begin PIZZA_INGREDIENT
create table PIZZA_INGREDIENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    ALLERGIC boolean,
    --
    primary key (ID)
)^
-- end PIZZA_INGREDIENT
-- begin PIZZA_PIZZA_RECIPE
create table PIZZA_PIZZA_RECIPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PIZZA_ID uuid not null,
    INGREDIENT_ID uuid not null,
    AMOUNT integer not null,
    --
    primary key (ID)
)^
-- end PIZZA_PIZZA_RECIPE
