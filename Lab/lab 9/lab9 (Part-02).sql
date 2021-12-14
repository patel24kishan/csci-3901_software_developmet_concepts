use dhruvp;

create table ds_EVENT(
	Event_ID int primary key,
    Event_Date date,
    Event_Location varchar(20),
    Event_Time time
); 

create table ds_MENU(
	Menu_ID int primary key,
    Menu_description varchar(20),
    Menu_type varchar(20)
);

create table ds_DISH(
	Dish_ID int primary key,
    Dish_Name varchar(20),
    Prep_Time time
);

create table ds_DISH_Ingredient(
    Ingredient varchar(20),
    Dish_id int NOT NULL REFERENCES ds_DISH(Dish_ID) ON DELETE cascade
);

describe ds_DISH_Ingredient;

create table ds_WORK_SCHEDULE(
	Event_ID int,
    Emp_ID int,
	Start_Time time,
    End_Time time,
    Position time,
    foreign key(Event_ID) references ds_EVENT(Event_ID),
    foreign key(Emp_ID) references ds_STAFF(Emp_ID)
);

create table ds_STAFF(
	Emp_ID int primary key,
    Supervisor_ID int,
    Staff_Name varchar(20),
    Salary int,
    foreign key(Supervisor_ID) references ds_STAFF(Emp_ID)
);
 
create table ds_STAFF_Skill(
    Skill varchar(20),
    Emp_ID int NOT NULL REFERENCES ds_STAFF(Emp_ID) ON DELETE cascade
);

alter table ds_WORK_SCHEDULE add foreign key (Event_ID) references ds_EVENT(Event_ID);
alter table ds_WORK_SCHEDULE modify Event_ID int not null;

alter table ds_WORK_SCHEDULE add foreign key (Emp_ID) references ds_STAFF(Emp_ID);
alter table ds_WORK_SCHEDULE modify Emp_ID int not null;

alter table ds_EVENT add Menu_ID int;
alter table ds_EVENT add foreign key (Menu_ID) references ds_MENU(Menu_ID);
alter table ds_EVENT modify Menu_ID int not null;

create table ds_Contains(
	Menu_ID int,
    Dish_ID int
);

alter table ds_Contains add foreign key (Menu_ID) references ds_MENU(Menu_ID);
alter table ds_Contains modify Menu_ID int not null;

alter table ds_Contains add foreign key (Dish_ID) references ds_DISH(Dish_ID);
alter table ds_Contains modify Dish_ID int not null;
