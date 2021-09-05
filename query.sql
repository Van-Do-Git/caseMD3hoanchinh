CREATE database CaseStudyModule3;
use CaseStudyModule3;
create table role
(
    id_role   int auto_increment primary key,
    name_role varchar(255) not null
);
create table users
(
    id_user  int auto_increment primary key,
    fullName varchar(255) not null,
    phone    varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    id_role  int          not null,
    foreign key (id_role) references role (id_role)
);

create table icon
(
    id_icon   int auto_increment primary key,
    link_icon varchar(255) not null
);

create table revenue_Categories
(
    id_rc   int auto_increment primary key,
    name_rc varchar(255) not null,
    id_icon int          not null,
    id_user int          not null,
    foreign key (id_icon) references icon (id_icon),
    foreign key (id_user) references users (id_user)
);
create table expenditure_Categories
(
    id_ec   int auto_increment primary key,
    name_ec varchar(255) not null,
    id_icon int          not null,
    id_user int          not null,
    foreign key (id_icon) references icon (id_icon),
    foreign key (id_user) references users (id_user)
);
create table revenue
(
    id_re    int auto_increment primary key,
    date_re  date         not null,
    money_re int          not null,
    note     varchar(255) not null,
    id_rc    int          not null,
    foreign key (id_rc) references revenue_Categories (id_rc)
);
create table expenditure
(
    id_ex    int auto_increment primary key,
    date_ex  date         not null,
    money_ex int          not null,
    note     varchar(255) not null,
    id_ec    int          not null,
    foreign key (id_ec) references expenditure_Categories (id_ec)
);

create table limited
(
    id_limited  int auto_increment primary key,
    id_user     int not null,
    limit_day   int default 0,
    limit_month int default 0,
    foreign key (id_user) references users (id_user)
);

alter table users
    add column status bit default 1;

insert into users(fullName, phone, username, password, id_role) VALUE ('van', '0123', 'xuanan', '1235', 1);
select *
from users
         join role on users.id_role = role.id_role
where username = ?
  and password = ?;

select *
from expenditure_categories
         join icon on expenditure_Categories.id_icon = icon.id_icon
where id_user = 12;

select *
from limited
where id_user = 12;

select *
from expenditure_categories
         join icon on expenditure_Categories.id_icon = icon.id_icon
where id_ec = ?;

UPDATE expenditure
SET date_ex= ?,
    money_ex= ?,
    note= ?,
    id_ec= ?
WHERE id_ex = ?;

select *
from role
         join users on role.id_role = users.id_role
         join limited on users.id_user = limited.id_user;

select *
from users
         join role on users.id_role = role.id_role
         join limited on users.id_user = limited.id_user
where username = ?
  and password = ?;

select *
from role
         join users on role.id_role = users.id_role;

update users
set status = ?
where id_user = ?;

# select eC.name_ec,sum(e.money_ex)\n" +
#                     "from expenditure e join expenditure_Categories eC on eC.id_rc = e.id_rc\n" +
#                     "where rC.id_user = ? and e.date_ex=?\n" +
#                     "group by rC.name_rc";;

select name_ec, sum(money_ex)
from expenditure
         join expenditure_Categories on expenditure.id_ec = expenditure_Categories.id_ec
where id_user = ?
  and date_ex = ?
group by name_ec;

SELECT eC.name_ec, sum(money_ex)
                    FROM expenditure e JOIN expenditure_Categories eC ON eC.id_ec = e.id_ec
                    WHERE id_user = 12 AND month(date_ex)=month(?)
                    GROUP BY eC.name_ec;


