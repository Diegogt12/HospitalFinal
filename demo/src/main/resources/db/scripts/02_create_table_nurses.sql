
create table nurses(
    nurse_dni varchar(10) primary key,
	name varchar(100) not null,
	nurse_id varchar(50) not null,
	hour_start time not null,
    hour_finish time not null
);

