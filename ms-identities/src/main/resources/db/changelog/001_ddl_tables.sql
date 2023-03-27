create table users
(
    id        uuid         not null
        primary key,
    email     varchar(50)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    password  varchar(120),
    public_id serial,
    role      varchar(255) not null,
    username  varchar(20)
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique
);