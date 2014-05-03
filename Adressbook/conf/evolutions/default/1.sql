# --- !Ups

create table addressbook (
  id                        bigint(20) not null auto_increment,
  name                      varchar(50),
  age                       int,
  tel                       varchar(50),
  address                   varchar(128),
  primary key (id))
;



# --- !Downs

drop table if exists addressbook;
