create table orders
(
    id                 uuid not null
        primary key,
    content            varchar(255),
    cost               numeric(38, 2),
    courier_public_id  bigint,
    creation_date      timestamp(6),
    customer_public_id bigint,
    location_from      varchar(255),
    location_to        varchar(255),
    public_id          uuid not null,
    status             varchar(255),
    title              varchar(255)
);