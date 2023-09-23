--liquibase formatted sql

-- changeset mbuyanov:1
create table account
(
    id BIGSERIAL primary key ,
    name varchar(50) unique not null ,
    pin varchar(100) not null ,
    balance BigInt default 0
);
