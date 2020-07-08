DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;


create table roles (id int not null primary key,name varchar(120) not null);
create table users (id int not null primary key,email varchar(120) not null,secret_key varchar(120) not null,name varchar(120) not null);

create table user_roles(user_id int not null,role_id int not null,foreign key(user_id) references users(id))