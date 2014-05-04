# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admins (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint uq_admins_1 unique (username),
  constraint pk_admins primary key (id))
;

create sequence admins_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admins;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admins_seq;

