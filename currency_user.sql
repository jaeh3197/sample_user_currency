create table currency.currency
(
    exchange_rate decimal(38, 2) null,
    created_at    datetime(6)    null,
    id            bigint auto_increment
        primary key,
    modified_at   datetime(6)    null,
    currency_name varchar(255)   null,
    symbol        varchar(255)   null
);

create table currency.user
(
    created_at  datetime(6)  null,
    id          bigint auto_increment
        primary key,
    modified_at datetime(6)  null,
    email       varchar(255) null,
    name        varchar(255) null
);

create table currency.user_currency
(
    amount_after_exchange decimal(38, 2) null,
    amount_in_krw         decimal(38, 2) null,
    created_at            datetime(6)    null,
    id                    bigint auto_increment
        primary key,
    modified_at           datetime(6)    null,
    to_currency_id        bigint         null,
    user_id               bigint         null,
    status                varchar(255)   null,
        foreign key (to_currency_id) references currency.currency (id),
        foreign key (user_id) references currency.user (id)
);

