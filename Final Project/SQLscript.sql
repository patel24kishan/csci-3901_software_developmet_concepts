#*****************************Login Detail**************************
 #username kahodariya
 #password B00864907
#**********************************************************************

drop table if exists contactInformation; # to ensure any prexisting contactInformation table is deleted from Database
drop table if exists testDatabase; # to ensure any prexisting testDatabase table is deleted from Database

create table if not exists contactInformation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Mobile_ID varchar(80)  null,
    Contact_ID varchar (80) null,
    NumOfDays int, 
    Contact_Duration int,
    Test_ID varchar(10) ,
    TestDate int default 0,
    Result boolean 
	);

create table if not exists testDatabase (
    Mobile_ID varchar(80) null  default 'default_ID'primary key,
     TestHash varchar(10) default null ,
     TestResult  boolean ,
     TestDate int default 0
);