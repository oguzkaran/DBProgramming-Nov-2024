/*----------------------------------------------------------------------------------------------------------------------
	dpn24_flightdb 1.0.0
-----------------------------------------------------------------------------------------------------------------------*/

create database dpn24_flightdb;

use dpn24_flightdb;

create table countries (
    country_id integer primary key identity(1, 1),
    name nvarchar(250) not null,
    code char(3) not null
);

create table cities (
    city_id int primary key identity(1, 1),
    country_id integer foreign key references countries(country_id) not null,
    name nvarchar(250) not null
);

create table airports (
    code char(10) primary key,
    city_id int foreign key references cities(city_id) not null,
    name nvarchar(300) not null,
    open_date date default(getdate()) not null
    -- ...
);

create table flights (
    flight_id bigint primary key identity(1, 1),
    departure_airport_code char(10) foreign key references airports(code) not null,
    arrival_airport_code char(10) foreign key references airports(code) not null,
    date_time datetime not null
    -- ...
);
create table passengers (
    citizen_id char(30) primary key,
    first_name nvarchar(200) not null,
    last_name nvarchar(200) not null,
    email nvarchar(500) not null
    -- ...
);

create table passengers_to_flights (
    passenger_to_flight_id bigint primary key identity(1, 1),
    passenger_id char(30) foreign key references passengers(citizen_id) not null,
    flight_id bigint foreign key references flights(flight_id) not null,
    reservation_date_time datetime default(getdate()) not null,
    price real not null
);