create table couriers
(
    id             uuid not null
        primary key,
    is_available   boolean,
    user_public_id bigint,
    work_number    serial
);

create table shifts
(
    id         uuid not null
        primary key,
    end_date   timestamp(6),
    start_date timestamp(6)
);