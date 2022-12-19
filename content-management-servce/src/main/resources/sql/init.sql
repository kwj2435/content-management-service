create schema if not exists cms;
use cms;

create table product(
                        id bigint primary key auto_increment,
                        name varchar(200) not null,
                        category bigint not null,
                        status varchar(10) not null,
                        sales_started_at datetime null,
                        sales_finished_at datetime null,
                        created_at datetime not null,
                        updated_at datetime not null
);

create table category(
                         id bigint primary key auto_increment,
                         category_name varchar(100) not null,
                         created_at datetime not null,
                         updated_at datetime not null
);

insert into category(category_name, created_at, updated_at) values ('카테고리01', now(), now());
insert into category(category_name, created_at, updated_at) values ('카테고리02', now(), now());
insert into category(category_name, created_at, updated_at) values ('카테고리03', now(), now());

insert into product(name, category, status, sales_started_at, sales_finished_at, created_at, updated_at)
values('테스트상품01', 1, 'SALE', null, null, now(), now());
insert into product(name, category, status, sales_started_at, sales_finished_at, created_at, updated_at)
values('테스트상품02', 2, 'CLOSED', now(), null, now(), now());
insert into product(name, category, status, sales_started_at, sales_finished_at, created_at, updated_at)
values('테스트상품03', 3, 'SALE', null, null, now(), now());
insert into product(name, category, status, sales_started_at, sales_finished_at, created_at, updated_at)
values('테스트상품04', 1, 'CLOSED', now(), null, now(), now());
insert into product(name, category, status, sales_started_at, sales_finished_at, created_at, updated_at)
values('테스트상품05', 2, 'SALE', null, null, now(), now());