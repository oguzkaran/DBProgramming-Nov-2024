### C ve Sistem Programcıları Derneği
### PostgreSQL ile Veritabanı Programlama
### Eğitmen: Oğuz KARAN

>PostgreSQL free olarak kullanılabilen güçlü bir veritabanı yönetim sistemidir. PostgreSQL 
>**[https://www.enterprisedb.com/downloads/postgres-postgresql-downloads](https://www.oracle.com/tr/java/technologies/downloads/)**  bağlantısından ilgili işletim sistemine göre indirilip yüklenebilmektedir. 

>Aşağıdaki demo örnekte bir uçuş veritabanı tasarlanmıştır

```sql

create database dpn24_flightdb;

create table countries (

	country_id serial primary key,
	
	name character varying(250) not null,
	
	code character(3) not null

);

  
create table cities (

	city_id serial primary key,
	
	country_id integer references countries(country_id) not null,
	
	name varchar(250) not null

);

  

create table airports (

	code char(10) primary key,
	
	city_id int references cities(city_id) not null,
	
	name varchar(300) not null,
	
	open_date date default(current_date) not null
	
	-- ...
);

  

create table flights (

	flight_id bigserial primary key,
	
	departure_airport_code char(10) references airports(code) not null,
	
	arrival_airport_code char(10) references airports(code) not null,
	
	date_time timestamp not null
	
	-- ...

);
```
