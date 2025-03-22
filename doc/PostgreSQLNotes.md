### C ve Sistem Programcıları Derneği
### PostgreSQL ile Veritabanı Programlama
### Eğitmen: Oğuz KARAN

>PostgreSQL free olarak kullanılabilen güçlü bir veritabanı yönetim sistemidir. PostgreSQL 
>**[https://www.enterprisedb.com/downloads/postgres-postgresql-downloads](https://www.oracle.com/tr/java/technologies/downloads/)**  bağlantısından ilgili işletim sistemine göre indirilip yüklenebilmektedir.  Ayrıca PostgreSQL `docker` platformu ile kullanılabilmektedir. Docker image'ı olarak PostgreSQL'in çekilebilmesi (pull) her host sistemde aşağıdaki gibi yapılabilir:

	`docker pull postgres:latest`

>PostgreSQL'in dili default olarak **plpgsql** olarak adlandırılır. Ancak PostgreSQL içerisinde belirli koşullar altında `Python, Perl, Ruby vb` diller de kullanılabilmektedir. Ayrıca C ve C++yazılmış kütüphanelere de erişim mümkündür.

>PostgreSQL'de iki türlü yorum satırı (comment line) bulunur:

```sql
-- Burası intepreter tarafından görülmeyecek
select * from airports; -- people tablosundaki tüm veriler çekildi

/*
	Burası da interpreter tarafından 
	görülmez
*/

```
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

create table passengers (  
    citizen_id char(30) primary key,  
    first_name varchar(200) not null,  
    last_name varchar(200) not null,  
    email varchar(500) not null  
    -- ...  
);  
  
create table passengers_to_flights (  
    passenger_to_flight_id bigserial primary key,  
    passenger_id char(30) references passengers(citizen_id) not null,  
    flight_id bigint references flights(flight_id) not null,  
    reservation_date_time timestamp default(current_timestamp) not null,  
    price real not null  
);
```

>Bir tabloya veri eklemek için **insert into** cümlesi kullanılır. insert into cümlesinin tipik bir kullanımı aşağıdaki gibidir:

```sql
insert into countries (name, code) values ('Turkey', 'TR');
insert into countries (name, code) values ('United States', 'US');
```

>insert into cümlesi ile birden fazla veri de eklenebilmektedir:

```sql
insert into countries (name, code) values ('Turkey', 'TR'), ('United States', 'US');
```

>Bir tablodaki verileri güncellemek için **update** cümlesi kullanılır. update cümlesinin tipik bir kullanımı aşağıdaki gibidir:

```sql
update cities set name=upper(name);
update countries set name=upper(name), code=lower(code);
```

>Buradaki cümleler herhangi bir koşul verilmediği için tablolardaki tüm veriler için güncelleme yapmak anlamına gelir. SQL'da koşul ifadelerini oluşturmak **where cümleciği (where clause)** kullanılabilir. where cümleciği bir koşul ifadesi (predicate) ile birlikte kullanılır:

```sql
update countries set code=upper(code) where country_id > 1;
```

>Burada country_id'si 1 değerinde büyük olan ülkelerin kodlarına ilişkin yazılar büyük harfe çevrilmiştir.

>Bir tabloadaki verileri silmek için **delete from** cümlesi kullanılır. delete from cümlesi de koşula bağlı olarak kullanılabilir. Eğer bir koşul verilmezse tüm veriler silinir. delete from cümlesinin tipik bir kullanımı aşağıdaki gibidir:

```sql
delete from cities where city_id > 9;
```

>Veriler üzerinde sorgulama işlemler **select cümlesi** ile gerçekleştirilir. select cümlesi her VTYS'de olduğu gibi PostgreSQL'de de oldukça karmaşık ve detaylıdır. select cümlesine ilişkin detaylar konular içerisinde ele alınacaktır. select cümlesi de koşula bağlı olarak kullanılabilmektedir. Koşul verilmezse tüm veriler elde edilir. select cümlesinde istenen alanlar bölümünde (projection) `*` karakteri tüm alanları getir anlamında (select all) kullanılır:

```sql
select * from cities;
select * from cities where country_id = 1;
```

select cümlesinde alanlar aralarında virgül ile ayrılacak şekilde de verilebilmektedir. Hatta bu alanlara **takma ad (alias)** da verilebilir:

```sql
select country_id, name from cities;
select country_id as cid, name as city_name from cities;
select country_id as "country id", name as "city name" from cities
```

>select cümlesinde ilgili alan adına tablo ismi ve nokta operatörü kullanılarak da erişilebilir:

```sql
select cities.country_id cid, cities.name cname from cities
select c.country_id cid, c.name cname from cities c
```

>Tabloya da bir takma as verip o takma ad ile de alanlara erişilebilir. 

**Anahtar Notlar:** Takma ad verilirken as atomu kullanılabilir ya da doğrudan takma ad da verilebilir.

##### Join İşlemleri

>join işlemleri ile birden fazla tablo (özellikle ilişkili) kullanılarak sorgulama yapılabilir. join işlemleri şunlardır: 
>1. inner join
>2. outer join
>	- left join
>	- right join
>	- full join
>3. self join
>
>Bunlar içerisinde inner join ve self join daha çok kullanılır. Şüphesiz diğerleri de çeşitli durumlarda kullanılabilmektedir.
>
> inner join işleminde birleştirilen alanlar `=` operatörü ile aşağıdaki gibi belirtilir. Bu durumda iki tablo inner join ile sorgulandığında eşleşen alanların ortak olanlara ilişkin veriler getirilir. join işlemlerinde birleştirilen tabloların teorik olarak ilişkilendirilmiş olması gerekmese de ilişkilerin belirlenmiş olduğu tablolar ile VTYS bu sorguları daha efektif olarak çalıştırır:

```sql
select a.code, a.name, a.open_date, ci.name, co.name  
from airports a inner join cities ci on a.city_id = ci.city_id  
inner join countries co on ci.country_id = co.country_id  
where a.code = 'LBS';
```

>Burada örnek olarak kodu`LBS` olan hava alanının çeşitli bilgileri diğer tablolar ile birleştirilerek elde edilmiştir. inner join ile yapılan sorgulamalar self join biçiminde de yapılabilir. Teorik olarak bakıldığında self join, inner join'den daha yavaştır. Ancak popüler VTYS'lerin bir çoğu yazılan sorguları optimize ettikleri (query optimization) için hız anlamında kayda değer bir fark olmamaktadır:

```sql
select a.code, a.name, a.open_date, ci.name, co.name  
from airports a, cities ci, countries co  
where a.city_id = ci.city_id and ci.country_id = co.country_id  
and a.code = 'LBS';
```

>Aşağıdaki sorguyu inceleyiniz

```sql
select f.flight_id, ad.name departure, c.name, co.name, aa.name arrival, ca.name, coa.name  
from flights f inner join airports ad on f.departure_airport_code = ad.code  
inner join airports aa on f.arrival_airport_code = aa.code  
inner join cities c on ad.city_id = c.city_id  
inner join cities ca on aa.city_id = ca.city_id  
inner join countries co on c.country_id = co.country_id  
inner join countries coa on ca.country_id = coa.country_id  
where f.date_time = '2024-08-25 00:00:00.000000';
```

>Aynı sorgu self join ile aşağıdaki gibi de yapılabilir:

```sql
select f.flight_id, ad.name departure, c.name, co.name, aa.name arrival, ca.name, coa.name  
from flights f, airports ad, airports aa, cities c, cities ca, countries co, countries coa  
where  
f.departure_airport_code = ad.code and f.arrival_airport_code = aa.code  
and ad.city_id = c.city_id and aa.city_id = ca.city_id and c.country_id = co.country_id  
and ca.country_id = coa.country_id  
and f.date_time = '2024-08-25 00:00:00.000000';
```

>Yukarıdaki veritabanına göre aşağıdaki basit sorulara ilişkin sorguları yazalım:

>**Soru:** flight_id'si bilinen bir uçuşun tarih zamanı ile birlikte kalkış ve varış hava alanlarının adlarını açılış tarihlerini ve şehir isimlerini ile  birlikte getiren sorgu?

```sql
select f.flight_id, f.date_time, ad.name departure, cd.name, aa.name arrival, ca.name  
from flights f inner join airports ad on f.departure_airport_code = ad.code  
inner join airports aa on f.arrival_airport_code = aa.code  
inner join cities cd on ad.city_id = cd.city_id  
inner join cities ca on aa.city_id = ca.city_id  
where f.flight_id = 5;
```

>**Soru:** Kalkış airport kodu bilinen uçuşların varış hava limanlarının isimlerini ve hangi ülkede olduklarını getiren sorgu?

>inner join:
```sql
select aa.name, coa.name  
from  
flights f inner join airports ad on f.departure_airport_code = ad.code  
inner join airports aa on f.arrival_airport_code = aa.code  
inner join cities ca on aa.city_id = ca.city_id  
inner join countries coa on coa.country_id = ca.country_id  
where f.departure_airport_code = 'KPN';
```
>self join

```sql
select aa.name, coa.name  
from  
flights f, airports ad, airports aa, cities ca, countries coa  
where f.departure_airport_code = ad.code and f.arrival_airport_code = aa.code  
and aa.city_id = ca.city_id and  coa.country_id = ca.country_id  
and f.departure_airport_code = 'KPN';
```

>**Soru:** Airport kodu bilinen hava limanından yapılan uçuşların kalkış zamanını ve varış hava limanı isimlerini getiren sorgu?

>inner join

```sql
select f.date_time, aa.name  
from  
airports ad inner join flights f on ad.code = f.departure_airport_code  
inner join airports aa on f.arrival_airport_code = aa.code  
where f.departure_airport_code = 'KPN';
```

>**Soru:** city_id'si bilinen bir şehirden yapılan belirli bir tarih-zamandaki uçuşların kalkış zamanı, kalkış havalimanı ismi, varış havalimanı ismi ve varış havalimanının ait olduğu şehir bilgilerini getiren sorgu?

>inner join

```sql
select f.date_time, ad.name, aa.name, ca.name  
from  
cities c inner join airports ad on c.city_id = ad.city_id  
inner join flights f on ad.code = f.departure_airport_code  
inner join airports aa on f.arrival_airport_code = aa.code  
inner join cities ca on aa.city_id = ca.city_id  
where c.city_id = '326' and f.date_time = '2024-08-25 00:00:00.000000';
```

>PostgreSQL'de akış oluşturmak için en azından bir **anonim blok (anonymous block)** içerisinde olmak gerekir. Standart SQL (yani CRUD cümleleri) bir blok içerisinde yazılmak zorunda değildir. Blok içerisindeki kodların iki tane tek tırnak arasında yazılması gerekir. Bu anlamda `$$` da tek tırnak anlamındadır. PostgreSQL'de test amaçlı bir takım çıktılar (output) oluşturmak için `raise deyimi (raise statement)` kullanılabilir. Biz burada raise deyimini `notice` ile birlikte kullanacağız. Bir blok içerisinde değişken bildirimleri declare bölümü içerisinde önce isim sonra tür bilgisi gelecek şekilde yapılır:
>
>`name varchar(256);`
>Değişkene bildirim noktasında değer verilebilir (initialization). Blok içerisinde akış `begin-end` arasında oluşturulur. Atama işlemi `=` operatörü ile yapılabildiği gibi `:=` operatörü ile de yapılabilmektedir. raise deyiminde `%` karakteri yer tutucu (placeholder) ya da diğer ismiyle format karakteri (format specifier) olarak kullanılabilmektedir. Bu durumda raise deyiminin yazısı içerisinde `%` karakteri çıkartılması istenirse `%%` biçiminde kullanılmalıdır.

```sql
do  
$$  
    declare  
        first_name varchar(256) = 'Oğuz';  
        last_name varchar(256);  
    begin  
        last_name = 'Karan';  
        raise notice 'Hello % %', first_name, last_name;  
    end  
$$;
```


```sql
do  
'  
    declare        
	    first_name varchar(256) = $$Oğuz$$;
	    last_name varchar(256);    
    begin        
	    last_name = $$Karan$$;        
	    raise notice $$Hello % %$$, first_name, last_name;    
	end
';
```

##### Fonksiyonlar

>Programlama dillerinde akış içerisinde gerektiğinde çalıştırılabilen alt programlar yazılabilir. PostgreSQL'de bir alt program çeşitli biçimlerde yazılabilir. Fonksiyon da alt program oluşturmanın bir yöntemidir. Fonksiyon `create function` veya `create or replace function` cümleleri ile yazılabilir. Bir fonksiyonun ismi, hangi alt programın çalıştırılacağını (call/invoke) belirtme için gereklidir. Bir fonksiyonun çağrılırken aldığı ve fonksiyon içerisinde kullanılabilen değişkenlerine **parametre değişkenleri (parameter variables)** denir. Fonksiyon çağrısı bittiğinde çağrılan noktaya bir değer ile geri dönmesine **geri dönüş değeri (return value)** denir. Bir fonksiyonun geri dönüş değeri bilgisi aslında fonksiyonun döndüğü değere ilişkin türü belirtir. Fonksiyon yazılırken blok için hangi dilde yazılacağı belirtilir. Çünkü PostgreSQL'de fonksiyon çeşitli diller (perl, ruby, python vb.) kullanılarak yazılabilmektedir. Ancak PostgreSQL'in resmi dili `plpgsql` olduğundan ağırlıklı olarak bu dilde yazılır. Biz de kursumuzda genel olarak `plpgsql` kullanacağız. Ancak dillere ilişkin bazı örnekleri vereceğiz. Fonksiyonun geri dönüş değeri varsa bu değer fonksiyon içerisinde **return deyimi (return statement)** ile oluşturulur. return deyimi bir ifade ile kullanıldığında o ifadenin değerine geri dönülmüş olur. Fonksiyon çağrılırken parametre değişkenleri için geçilen ifadelere **argümanlar (arguments)** denir. PostgreSQL'de pek çok hazır fonksiyon (built-in) bulunmaktadır. Veritabanı programcısı yapacağı bir iş için hazır fonksiyonlar varsa öncelikle o fonksiyonları tercih etmelidir. Fonksiyonların yazılması basit olsa bile hazır olanları kullanması verimi ve performansı genel olarak artırır. Aşağıdaki örnek amaçlı yazılmış fonksiyonu inceleyiniz:

```sql
create or replace function add_two_ints(a int, b int)  
returns int  
as  
$$  
    declare  
        total int;  
    begin  
        total = a + b;  
  
        return total;  
    end  
$$ language plpgsql;  
```

```sql
do  
$$  
    declare  
        x int = 10;  
        y int = 20;  
        sum int;  
    begin  
        sum = add_two_ints(x, y);  
  
        raise notice '% + % = %', x, y, sum;  
    end  
$$;
```

>Aşağıdaki örnek amaçlı yazılmış fonksiyonu inceleyiniz

```sql
create or replace function add_two_ints(a int, b int)  
returns int  
as  
$$  
    begin  
        return a + b;  
    end  
$$ language plpgsql;  
```

```sql
do  
$$  
    declare  
        x int = 10;  
        y int = 20;  
        sum int;  
    begin  
        sum = add_two_ints(x, y);  
  
        raise notice '% + % = %', x, y, sum;  
    end  
$$;
```

>PostgreSQL'de fonksiyon parametre değişkenlerine isim verilmeyip sadece türleri yazılabilir. Bu durumda fonksiyon içerisinde parametre değişkenlerine 1 değerinde başlamak üzere `$` atomu ile birliktge bir sayı verilir. 1 birinci parametreyi eder. Aşağıdaki örnek amaçlı yazılmış fonksiyonu inceleyiniz

```sql
create or replace function add_two_ints(int, int)  
returns int  
as  
$$  
    begin  
        return $1 + $2;  
    end  
$$ language plpgsql;  
```

```sql
do  
$$  
    declare  
        x int = 10;  
        y int = 20;  
        sum int;  
    begin  
        sum = add_two_ints(x, y);  
  
        raise notice '% + % = %', x, y, sum;  
    end  
$$;
```

##### Function Overloading

>PostgreSQL'de bir veritabanı içerisinde aynı isimde birden fazla fonksiyon yazılabilmektedir. Bu kavrama **function overloading** denir. Bir veritabanında birden fazla fonksiyonun aynı isimde olacak şekilde yaratılabilmesi için parametrik yapılarının farklı olması gerekir. Aslında PostgreSQL'de her fonksiyonun bir imzası (signature) vardır. İmza, fonksiyonun ismi ve parametrik yapısıdır. Kural şudur: **Bir veritabanı içerisinde aynı imzaya sahip birden  fazla fonksiyon yaratılamaz. Başka bir deyişle bir veritabanı içerisinde yaratılan fonksiyonların her birisinin imzası farklı olmalıdır.** Bu kurala aynı isimde fonksiyonların imzalarının farklı olması için parametrik yapılarının farklı olması gerekir. Bir fonksiyon çağrısında hangi fonksiyonun çağrılacağına karar verilmesi sürecine **function overload resolution** denir. Buna ilişkin kurallar örneklerle ele alınacaktır. Veritabanı programlamada genel olarak argümaların türleri ile karşılık geldikleri parametrelerin türlerinin aynı olduğu senaryolar karşımıza çıkmaktadır (best match). 

>Aşağıdaki demo fonksiyonlar overload edilmiştir

```sql
create or replace function add_two_ints(int, int)  
returns int  
as  
$$  
    begin  
        return $1 + $2;  
    end  
$$ language plpgsql;  
  
create or replace function add_two_ints(bigint, bigint)  
returns bigint  
as  
$$  
    begin  
        return $1 + $2;  
    end  
$$ language plpgsql;  
  
  
create or replace function add_two_ints(smallint, smallint)  
returns smallint  
as  
$$  
    begin  
        return $1 + $2;  
    end  
$$ language plpgsql;
```

```sql
do  
$$  
    declare  
        x int = 70000;  
        y int = 80000;  
        a bigint = 400000000;  
        b bigint = 500000000;  
        c smallint = 200;  
        d smallint = 200;  
    begin  
        raise notice '% + % = %', x, y, add_two_ints(x, y);  
        raise notice '% + % = %',a, b, add_two_ints(a, b);  
        raise notice '% + % = %', c, d, add_two_ints(c, d);  
    end  
$$;
```

##### Matematiksel İşlem Yapan Fonksiyonlar

>PostgreSQL'de matematiksel işlemler yapan pek çok hazır fonksiyon bulunmaktadır. Burada temel olanlar ve genel olarak kullanılanlar ele alınacaktır. 

###### sqrt Fonksiyonu
>Bu fonksiyon parametresi ile aldığı sayının kareköküne geri döner. Tipik olarak tüm nümerik türler için işlem yapabilmektedir. Şüphesiz tam sayılar için yine `double precison` türüne geri döner. Fonksiyona negatif bir argüman geçildiğince **çalışma zamanı hatası runtime error** oluşur.

```sql
do  
$$  
    begin  
       raise notice 'sqrt(%) = %', 2, sqrt(2);  
       raise notice 'Tekrar yapıyor musunuz?';  
    end  
$$;
```

```sql
do  
$$  
    begin  
       raise notice 'sqrt(%) = %', -2, sqrt(-2);  
       raise notice 'Tekrar yapıyor musunuz?';  
    end  
$$;
```

###### pow Fonksiyonu
Bu fonksiyon $$a^b$$ işlemini yapar.

```sql
do  
$$  
    begin  
       raise notice 'pow(%, %) = %', 2.34, 4.567, pow(2.34, 4.567);  
       raise notice 'Tekrar yapıyor musunuz?';  
    end  
$$;
```

>**Sınıf Çalışması:** mapdb isimli bir veritabanı oluşturunuz. Bu veritabanında nokta çiftlerinin koordinat bilgilerini tutan `coordinate_pairs` isimli bir tablo yaratınız. Tablo içerisindeki kaydın otomatik artan id bilgisi ile x1, y1, x2, y2 nokta değerleri `double preccision` olarak tutulacaktır. Buna göre Euclid uzaklığı bilgisini döndüren bir fonksiyon yazınız ve tüm kayıtlar için noktalarla birlikte aralarındaki uzaklığı da getiren sorguyu yazınız
>
>**Açıklamalar:** 
>- Euclid uzaklığı formulü şu şekildedir:
>$$d = \sqrt{(x1 - x2)^2 + (y1 - y2)^2}$$

```sql
create table coordinate_pairs (  
    coordinate_pair_id serial primary key,  
    x1 double precision not null,  
    y1 double precision not null,  
    x2 double precision not null,  
    y2 double precision not null  
);

create or replace function euclidean_distance(x1 double precision, y1 double precision, x2 double precision, y2 double precision)  
returns double precision  
as  
$$  
    begin  
       return sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));  
    end  
$$ language plpgsql;

select x1, y1, x2, y2, euclidean_distance(x1, y1, x2, y2) as distance from coordinate_pairs;
```

###### random Fonksiyonu

>Bu fonksiyon `[0, 1)` aralığında rassal olarak belirlenmiş gerçek sayıya geri döner

```sql
do  
$$  
    begin  
       raise notice '%', random();  
    end  
$$;
```

>random fonksiyonu kullanılarak aşağıdaki gibi belirli aralıkta rassal sayılar üretebilen fonksiyonlar overload edilebilir

```sql
create or replace function random_value(origin int, bound int)  
returns int  
as  
$$  
    begin  
       return floor(random() * (bound - origin) + origin);  
    end  
$$ language plpgsql;  
  
create or replace function random_value(origin bigint, bound bigint)  
returns bigint  
as  
$$  
    begin  
       return floor(random() * (bound - origin) + origin);  
    end  
$$ language plpgsql;  
  
create or replace function random_value(origin double precision, bound double precision)  
returns double precision  
as  
$$  
    begin  
       return random() * (bound - origin) + origin;  
    end  
$$ language plpgsql;  

do  
$$  
    begin  
       raise notice '%', random_value(10, 21);  
       raise notice '%', random_value(4000000000, 10000000000);  
       raise notice '%', random_value(2.345, 2.346);  
    end  
$$;
```

###### Yuvarlama İşlemi Yapan Önemli Fonksiyonlar

>Tam sayıya yuvarlama işlemi yapan bazı önemli fonksiyonlar şunlardır:
>- **floor:** Parmetresi ile aldığı gerçek (real) sayıdan küçük en büyük tamsayıya geri döner.
>- **round:** Bilimsel yuvarlama yapar. Sayının noktadan sonraki kısmı `>= 0.5` ise bir üst tamsayıya, `< 0.5` ise noktadan sonraki atılmış tamsayıya geri döner
>- **ceil:** Parametresi ile aldığı gerçek (real) sayıdan büyük en küçük tamsayıya geri döner.

```sql
do  
$$  
    declare  
        val double precision = 2.545;  
    begin  
       raise notice 'floor(%) = %', val, floor(val);  
       raise notice 'round(%) = %', val, round(val);  
       raise notice 'ceil(%) = %', val, ceil(val);  
    end  
$$
```

```sql
do  
$$  
    declare  
        val double precision = -2.545;  
    begin  
       raise notice 'floor(%) = %', val, floor(val);  
       raise notice 'round(%) = %', val, round(val);  
       raise notice 'ceil(%) = %', val, ceil(val);  
    end  
$$
```

##### Yazılarla İşlem Yapan Fonksiyonlar

>Neredeyse tüm uygulamalarda az ya da çok yazılarla işlem yapılır. Programlamada yazı kavramına **string** denir. PostgreSQL'de yazılar işlem yapan pek çok yararlı fonksiyon vardır (string function). Programcı yazı ile ilgili bir işlem için varsa buradaki fonksiyonları kullanmalı, yoksa yine buradaki fonksiyonları da kullanarak fonksiyonlarını yazmalıdır. Yazılar PostgreSQL'de **immutable**'dır. Yani yazı üzerinde işlem bir fonksiyon yazının orjinalini değiştiremez. Değişiklik yapan bir fonksiyon değişiklik yapılmış yeni yazıya geri döner. 


###### repeat Fonksiyonu

>Bu fonksiyon parametresi ile aldığı yazıyı iki parametresi ile aldığı değer kadar çoklar

```sql
do  
$$  
    declare  
        ch varchar(1) = '*';  
        str varchar(100) = 'Ankara';  
    begin  
       raise notice '%', repeat(ch, 3);  
       raise notice '%', repeat(str, 3);  
    end  
$$
```

###### length Fonksiyonu

>Bu fonksiyon yazının uzunluğunu (karakter sayısını) döndürür.

```sql
do  
$$  
    declare  
        str varchar(100) = 'Ankara';  
    begin  
       raise notice '%', length(str);  
    end  
$$
```

###### Yazıların Birleştirilmesi (concatenation)

>Yazıların birleştirilmesi işlemi concat fonksiyonu ile yapılabilir. Yazı birleştirmesi işlemi çok fazla karşılaşılan bir işlem olduğundan `||` operatörü ile de yazı birleştirmesi yapılabilir.

```sql
do  
$$  
    declare  
        first_name varchar(100) = 'Oğuz';  
        last_name varchar(100) = 'Karan';  
    begin  
       raise notice '%', concat(first_name, ' ', last_name);  
       raise notice '%', first_name || ' ' || last_name;  
    end  
$$
```

###### left ve right Fonksiyonları

>Bu fonksiyonlar sırasıyla soldan ve sağdan istenilen sayıda karakterin yazı olarak elde edilmesini sağlar

```sql
do  
$$  
    declare  
        str varchar(100) = 'Ankara';  
    begin  
       raise notice '%', left(str, 4);  
       raise notice '%', right(str, 4);  
    end  
$$
```

>**Sınıf Çalışması:** Parametresi ile aldığı bir yazının yine parametresi ile aldığı ilk count karakterini almak koşuluyla diğer karakterlerini üçüncü parametresi ile aldığı karakter ile değiştiren `hide_text_right` fonksiyonu yazınız. 

```sql
create or replace function hide_text_right(str varchar, count int, ch char(1))  
returns varchar  
as  
$$  
    begin  
        return left(str, count) || repeat(ch, length(str) - count);  
    end  
$$ language plpgsql;
```

```sql
select  
    hide_text_right(p.citizen_id, 2, '*') as citizen_id,  
    p.first_name || ' ' || p.last_name as fullname,  
    hide_text_right(email, 3, '*') as email,  
    f.date_time, f.departure_airport_code, f.arrival_airport_code,  
    pf.price  
from  
    flights f inner join passengers_to_flights pf on f.flight_id = pf.flight_id  
              inner join passengers p on pf.passenger_id = p.citizen_id;
```

###### initcap fonksiyonu

>Bu fonksiyon yazı içerisindeki whitespace karakterlere göre ilgili kelimeleri **upper camel case** durumuna getirir
>
>Özellikle programlamada isimlendirme konusunda br takım convention'lar bulunmaktadır. Bu convvention'ların bazıları çok kullanıldığından zamanında adlandırılmıştır. Bu convention'lardan bazıları şunlardır:
>**- Snake Case/Unix Style:** Bu isimlendirme biçiminde, kelimelerin tamamı küçük harf olarak yazılır. Birden fazla kelime için alttire ayraç olarak kullanılır. Örneğin `first_name`
>**- Lower Camel Case:**  Bu isimlendirme biçiminde, ilk kelimenin baş harfi küçük geri kalan kelimelerin baş harfleri büyük ve geri kalan tüm karakterler küçük harf yapılır. Herhangi bir ayraç kullanılmaz. Örneğin: `numberOfDevices`. Bu isimlendirme `camel case` olarak da bilinir.
>**- Upper Camel Case:** Bu isimlendirme biçiminde, tüm kelimelerin baş harfleri büyük geri kalan tüm karakterler küçük harf yapılır. Herhangi bir ayraç kullanılmaz. Örneğin: `NumberOfDevices`. Bu isimlendirme `pascal case` olarak da bilinir.


```sql
do  
$$  
    declare  
        first_name varchar(250) := 'mEHMET fATİH';  
        last_name varchar(250) := 'cOSKUN';  
    begin  
        raise notice '%', initcap(first_name) || ' '  || initcap(last_name);  
    end  
$$;
```

###### rtrim ve ltrim Fonsiyonları

>Bu fonksiyonlar yazının sırasıyla sonundaki ve başındaki whitespace karakterleri atarlar. 

```sql
do  
$$  
    declare  
        username varchar(250) := '      oguz karan    ';  
    begin  
        raise notice '(%), (%), (%)', rtrim(username), ltrim(username), ltrim(rtrim(username));  
    end  
$$;
```

###### upper ve lower Fonksiyonları

>Bu fonksiyonlar yazının tamamını sırasıyla büyük harf ya da küçük harf yaparlar. 

```sql
do  
$$  
    declare  
        username varchar(250) := 'oguzkaran';  
    begin  
        raise notice '%, %', upper(username), lower(username);  
    end  
$$;
```

>Bir sorgudan elde edilen tek bir bileşen, aynı zamanda tek bir kayıt da içeriyorsa elde edilen değer bir değişkene `select into` cümlesi ile verilebilir. Birden fazla kayıt içeriyorsa ilk edilenler alınır:

```sql  
do  
$$  
    declare  
        country_id int;  
        country_name varchar(250);  
    begin  
        select c.country_id, c.name as cid from countries c where code = 'TR' into country_id, country_name;  
        raise notice '%, %', country_id, country_name;  
    end  
$$;
```

##### out Parametreli Fonksiyonlar

>Bir fonksiyonun parametre değişkeni **out** olarak bildirilebilir. Bu aslında bir çeşit geri dönüş değeri gibidir. out parametreli fonksiyonlar içerisinde return deyimi kullanılmaz. Bu fonksiyonlar birden fazla değere geri dönebilmektedirler. out olarak bildirilmiş parametre değişkenlerine doğrudan atama yapılabilir. out parametreli bir fonksiyon sanki bir tabloya geri dönüyormuş gibi select cümlesi ile kullanılırlar. Bu fonksiyonlarda yine yazılacak dil belirtilir. Ancak geri dönüş değeri bilgisi yazılmaz. Bu fonksiyonlar çağrılırken out parametre değişkenlerine argüman geçilmez. Bu fonksiyonların çağrılması tablo döndüren fonksiyonlara benzese de yapı olarak farklı fonksiyonlardır. Tablo döndüren fonksiyonlar birden fazla kayıt döndürebiliken, bu fonksiyonlar bir tane kayıt döndürürler.

```sql
create or replace function get_name_and_code_by_country_id(int, out varchar(250), out char(10))  
as  
$$  
    begin  
        select c.code, c.name from countries c where c.country_id = $1 into $2, $3 ;  
    end;  
$$ language plpgsql;  
  
  
  
do  
$$  
    declare  
        country_code char(10);  
        country_name varchar(250);  
    begin  
        select * from get_name_and_code_by_country_id(1) into country_code, country_name;  
        raise notice '%, %', country_code, country_name;  
    end  
$$;
```

>out parametreli fonksiyonların out parametre değişkenlerine değer verilememesi durumunda (tipik olarak sorgudan bir bilgi elde edilmediğinde) içerisinde null değer bulunur. Yukarıdaki anonim blokta 1 numaralı country_id'ye sahip bir country yoksa elde edilen değerler null olurlar. Aslında bu durum out parametreli fonksiyon olmasından değil `select into` cümlesinden kaynaklanır. 

##### Tablo Döndüren Fonksiyonlar

>Genel olarak bir sorgunun sonucuna yani sorguya ilişkin alanlara (projection) geri dönen fonksiyonlardır. PostgreSQL'de tablo döndüren fonksiyonlarda geri dönüş değeri bilgisinde döndürülecek tabloya (projection) ilişkin alanların tür ve isimleri birlikte bildirilir ve bu alanlara uygun projection'a sahip sorgu cümleleri yazılır. Tablo döndüren fonksiyonlarda return deyimi ile birlikte `query` anahtar sözcüğü kullanılır.  Burada geri dönüş değerine ilişkin alanlar ile ilgili sorgunun projection'ına ilişkin alanların uyumuna genel olarak fonksiyon çağrısında bakılır. Genel olarak create veya alter gibi ddl cümlelerinde doğrudan bakılmaz.

```sql
create or replace function get_airport_and_country_name_by_departure_airport_code(char(10))  
returns table (departure_airport_name varchar(300), country_name varchar(250))  
as  
$$  
    begin  
        return query select aa.name, coa.name  
                from  
                    flights f inner join airports ad on f.departure_airport_code = ad.code  
                              inner join airports aa on f.arrival_airport_code = aa.code  
                              inner join cities ca on aa.city_id = ca.city_id  
                              inner join countries coa on coa.country_id = ca.country_id  
                where f.departure_airport_code = $1;  
    end;  
$$ language plpgsql;
```

```sql
create or replace function get_flight_info_by_flight_id(bigint)  
returns table (flight_id bigint, date_time timestamp, departure_airport_name varchar(300),   
               departure_city_name varchar(250), arrival_airport_name varchar(300), arrival_city_name varchar(250))  
as  
$$  
    begin  
        return query select f.flight_id, f.date_time, ad.name departure, cd.name, aa.name arrival, ca.name  
                     from flights f inner join airports ad on f.departure_airport_code = ad.code  
                                    inner join airports aa on f.arrival_airport_code = aa.code  
                                    inner join cities cd on ad.city_id = cd.city_id  
                                    inner join cities ca on aa.city_id = ca.city_id  
                     where f.flight_id = $1;  
    end;  
$$ language plpgsql;
```

##### if Deyimi

>if deyimi hemen hemen tüm programlama dillerinde ve akış içerisinde koşula bağlı olarak akışın yönlendirilmesini sağlayan önemli bir kontrol deyimidir. if deyiminin genel biçimi şu şekildedir:

```sql
if <koşul ifadesi> then 
	<deyim>
[else
	<deyim>
]
end if;
```

>if deyiminde herhangi bir `begin-end` aralığına ihtiyaç yoktur. if deyiminde else kısmı olmak zorunda değildir. Bu durumda koşul gerçekleştiğinde varsa else anahtar sözcüğüne kadar yoksa end if'e kadar doğru kısmına ilişkin kodlar yazılır. else kısmı ise end if'e kadar olan kodlardır. 


```sql
do  
$$  
    declare  
        value int;  
    begin  
        value = random() * (101 + 100) - 100;  
        if value > 0 then  
            raise notice '% is positive', value;  
        else  
            raise notice '% is not positive', value;  
        end if;  
    end;  
$$
```

>Aşağıdaki örnekte if deyiminin else kısmında başka bir if deyimi yazılmıştır. Bu şekilde yazılması başka ayrık kontroller de söz konusu olduğunda karmaşık olabilmektedir

```sql
do  
$$  
    declare  
        value int;  
    begin  
        value = random() * (101 + 100) - 100;  
        if value > 0 then  
            raise notice '% is positive', value;  
        else  
            if value = 0 then  
                raise notice 'zero';  
            else  
                raise notice '% is negative', value;  
            end if;  
        end if;  
    end;  
$$
```

>`elseif` anahtar sözcüğü yukarıdaki demo örnek aşağıdaki gibi daha az karmaşık dolayısıyla daha okunabilir biçimde yazılabililr

```sql
do  
$$  
    declare  
        value int;  
    begin  
        value = random() * (101 + 100) - 100;  
        if value > 0 then  
            raise notice '% is positive', value;  
        elseif value = 0 then  
            raise notice 'zero';  
        else  
            raise notice '% is negative', value;  
        end if;  
    end;  
$$
```
>`elseif` anahtar sözcüğü aslında daha eski olan `elsif`anahtar sözcüğü de kullanılabilir

```sql
do  
$$  
    declare  
        value int;  
    begin  
        value = random() * (101 + 100) - 100;  
        if value > 0 then  
            raise notice '% is positive', value;  
        elsif value = 0 then  
            raise notice 'zero';  
        else  
            raise notice '% is negative', value;  
        end if;  
    end;  
$$
```

>**Sınıf Çalışması:** Aşağıdaki tabloları hazırlayınız ve ilgili soruları yanıtlayınız
>**Tablolar:**
>- **people**
>	- citizen_id char(11)
>	- first_name varchar(300)
>	- last_name varchar(300)
>	- age int
>- **people_younger**
>	- citizen_id char(11)
>	- first_name varchar(300)
>	- last_name varchar(300)
>	- age int
>- **people_older**
>	- citizen_id char(11)
>	- first_name varchar(300)
>	- last_name varchar(300)
>	- age int
>**Sorular:**
>1. Parametresi ile aldığı person bilgilerine göre yaşı 18 ile 65 arasında olanları `people` tablosuna, yaşı 18'den küçük olanları `people_younger` tablosuna, yaşı 65'den büyük olanlar `people_older` tablosuna ekleyen `insert_person` isimli void bir fonksiyonu yazınız.
>2. Parametresi ile aldığı yaş bilgisine göre o yaştaki kişileri tablo olarak döndüren `get_people_by_age`fonksiyonunu yazınız
>
>**Not:** Bu örnek ileride yaş bilgisi doğum tarihi ile hesaplanacak şekilde yapılacaktır.
>
>**Çözüm:**
>
```sql
create table people (  
    citizen_id char(11) primary key,  
    first_name varchar(300) not null,  
    last_name varchar(300) not null,  
    age int not null  
);  
  
create table people_younger (  
    citizen_id char(11) primary key,  
    first_name varchar(300) not null,  
    last_name varchar(300) not null,  
    age int not null  
);  
  
create table people_older (  
    citizen_id char(11) primary key,  
    first_name varchar(300) not null,  
    last_name varchar(300) not null,  
    age int not null  
);  
```


```sql
create or replace function insert_person(char(11), varchar(300), varchar(300), int)  
returns void  
as  
$$  
    begin  
        if $4 < 18 then  
            insert into people_younger (citizen_id, first_name, last_name, age) values ($1, $2, $3, $4);  
        elseif $4 < 65 then  
            insert into people (citizen_id, first_name, last_name, age) values ($1, $2, $3, $4);  
        else  
            insert into people_older (citizen_id, first_name, last_name, age) values ($1, $2, $3, $4);  
        end if;  
    end;  
$$ language plpgsql;


create or replace function get_people_by_age(int)  
returns table (citizen_id char(11), first_name varchar(300), last_name varchar(300), age int)  
as  
$$  
    begin  
        if $1 < 18 then  
            return query select * from people_younger p where p.age = $1;  
        end if;  
  
        if $1 < 65 then  
            return query select * from people p where p.age = $1;  
        end if;  
  
        return query select * from people_older p where p.age = $1;  
    end  
$$ language plpgsql;
```

>Bir değişkenin değerinin null olup olmadığı **is null** veya **is not null** koşul ifadeleri ile kontrol edilebilir:

```sql
if name is null then
	-- ...
endif;

if name is not null then
	-- ...
endif;
```

>**Sınıf Çalışması:** Aşağıdaki tabloyu hazırlayınız ve ilgili soruları yanıtlayınız
>employees
>	- citizen_id char(11) p.k.
>	- first_name varchar(250) not null
>	- middle_name varchar(250)
>	- last_name varchar(250) not null
>	- marital_status int
>**Sorular:**
>1. Parametresi ile aldığı int türden medeni durum bilgisine göre yazı olarak `Evli, Bekar, Boşanmış veya Belirsiz` yazılarından birisine geri dönen `get_marital_status_text_tr` fonksiyonunu yazınız. Burada sıfır Evli, 1 Bekar, 2, Boşanmış ve diğer değerler de Belirsiz anlamında kullanılacaktır.
>2. Parametresi ile aldığı 3 tane yazıyı aralarına space karakteri koyarak, ancak `null` olanları yazıya eklemeyecek şekilde toplam yazıya dönen `get_full_text` fonksiyonunu yazınız.
>3. Parametresi ile aldığı `citizen_id` bilgisine göre ismin tamamını (full_name), marital_status_text bilgisini tablo olarak döndüren `get_employee_by_citizen_id`fonksiyonunu yazınız.


```sql
create table employees (  
    citizen_id char(11) primary key,  
    first_name varchar(250) not null,  
    middle_name varchar(250),  
    last_name varchar(250) not null,  
    marital_status int  
);  
  
create or replace function get_marital_status_text_tr(status int)  
returns varchar(20)  
as  
$$  
    declare  
        status_str varchar(20);  
    begin  
        if status = 0 then  
            status_str = 'Evli';  
        elseif status = 1 then  
            status_str = 'Bekar';  
        elseif status = 2 then  
            status_str = 'Boşanmış';  
        else  
            status_str = 'Belirsiz';  
        end if;  
  
        return status_str;  
    end;  
$$ language plpgsql;  
  
create or replace function get_full_text(varchar, varchar, varchar)  
returns varchar  
as  
$$  
    declare  
        full_text varchar = '';  
    begin  
        if $1 is not null then  
            full_text = $1;  
        end if;  
  
        if $2 is not null then  
            if full_text <> '' then  
                full_text = full_text || ' ';  
            end if;  
            full_text = full_text || $2;  
        end if;  
  
        if $3 is not null then  
            if full_text <> '' then  
                full_text = full_text || ' ';  
            end if;  
            full_text = full_text || $3;  
        end if;  
  
        return full_text;  
    end;  
$$ language plpgsql;  
  
create or replace function get_employee_by_citizen_id(char)  
returns table (full_name varchar, marital_status_text varchar)  
as  
$$  
    begin  
        return query            select get_full_text(first_name, middle_name, last_name), get_marital_status_text_tr(marital_status)  
            from employees where citizen_id =$1;  
    end;  
$$ language plpgsql;  
  
-- Simple test codes  
do  
$$  
    begin  
        raise notice 'Status:%', get_marital_status_text_tr(0);  
        raise notice 'Status:%', get_marital_status_text_tr(1);  
        raise notice 'Status:%', get_marital_status_text_tr(2);  
        raise notice 'Status:%', get_marital_status_text_tr(3);  
        raise notice 'Status:%', get_marital_status_text_tr(4);  
    end;  
$$;  
  
  
do  
$$  
    begin  
        raise notice '(%)', get_full_text('Oğuz', null, 'Karan');  
        raise notice '(%)', get_full_text('Ali', 'Vefa', 'Serçe');  
    end;  
$$;  
  
select * from employees;  
  
select * from get_employee_by_citizen_id('f048425c-58e5-4694-94ec-7ae8dc4fbeae');  
select * from get_employee_by_citizen_id('f2a22590-a77a-4f23-8b3f-6ec665371369');
```

>Yukarıdaki örnekte `marital_status` bilgileri ayrı tablolarda tutulduğunda fonksiyonlar aşağıdaki gibi yazılabilir. Dikkat edilirse bu yaklaşımda `get_marital_status_text_tr` fonksiyonuna ihtiyaç yoktur

```sql
create table marital_status (  
    marital_status_id serial primary key,  
    text varchar(20) not null  
);  
  
insert into marital_status (text) values ('Married'), ('Single'), ('Divorced'), ('Unknown');  
  
create table marital_status_tr (  
    marital_status_tr_id serial primary key,  
    marital_status_id int references marital_status(marital_status_id) not null,  
    text_tr varchar(20) not null  
);  
  
insert into marital_status_tr (marital_status_id, text_tr) values (1, 'Evli'), (2, 'Bekar'), (3, 'Boşanmış'), (4, 'Belirsiz');  
  
create table employees (  
    citizen_id char(40) primary key,  
    first_name varchar(250) not null,  
    middle_name varchar(250),  
    last_name varchar(250) not null,  
    marital_status int references marital_status(marital_status_id) not null  
);  
  
create or replace function get_full_text(varchar, varchar, varchar)  
returns varchar  
as  
$$  
    declare  
        full_text varchar = '';  
    begin  
        if $1 is not null then  
            full_text = $1;  
        end if;  
  
        if $2 is not null then  
            if full_text <> '' then  
                full_text = full_text || ' ';  
            end if;  
            full_text = full_text || $2;  
        end if;  
  
        if $3 is not null then  
            if full_text <> '' then  
                full_text = full_text || ' ';  
            end if;  
            full_text = full_text || $3;  
        end if;  
  
        return full_text;  
    end;  
$$ language plpgsql;  
  
create or replace function get_employee_by_citizen_id_tr(char)  
returns table (full_name varchar, marital_status_text varchar)  
as  
$$  
    begin  
        return query            select get_full_text(e.first_name, e.middle_name, e.last_name), mst.text_tr  
            from employees e inner join marital_status ms on ms.marital_status_id = e.marital_status  
            inner join marital_status_tr mst on ms.marital_status_id = mst.marital_status_id  
            where citizen_id = $1;  
    end;  
$$ language plpgsql;  
  
  
create or replace function get_employee_by_citizen_id_en(char)  
    returns table (full_name varchar, marital_status_text varchar)  
as  
$$  
begin  
    return query        select get_full_text(e.first_name, e.middle_name, e.last_name), ms.text  
        from employees e inner join marital_status ms on ms.marital_status_id = e.marital_status  
        where citizen_id = $1;  
end;  
$$ language plpgsql;  
  
-- Simple test codes  
  
do  
$$  
    begin  
        raise notice '(%)', get_full_text('Oğuz', null, 'Karan');  
        raise notice '(%)', get_full_text('Ali', 'Vefa', 'Serçe');  
    end;  
$$;  
  
select * from employees;  
  
select * from get_employee_by_citizen_id_tr('f048425c-58e5-4694-94ec-7ae8dc4fbeae');  
select * from get_employee_by_citizen_id_tr('815478f4-df97-4d8d-97e9-56a0084bffae');  
  
select * from get_employee_by_citizen_id_en('f048425c-58e5-4694-94ec-7ae8dc4fbeae');  
select * from get_employee_by_citizen_id_en('815478f4-df97-4d8d-97e9-56a0084bffae');
```

###### case İfadesel Deyimi

>**case ifadesel deyimi (case expression)** tipik olarak hem deyim hem de ifade biçiminde kullanılabilmektedir. 

>Aşağıdaki demo örneği inceleyiniz. Burada case expression, statement olarak kullanılmıştır

```sql
do $$  
    declare  
        origin int = -10;  
        bound int = 11;  
        val int = floor(random() * (bound - origin) + origin);
    begin  
        raise notice 'Value: %', val;  
        case  
            when val > 0 then raise notice '% is positive', val;  
            when val = 0 then raise notice 'Zero';  
            else raise notice '% is negative', val;  
        end case;  
    end;  
$$
```

>case expression'ın expression olarak kullanımında when ve else kısımlarında noktalı virgül kullanımı geçersizdir. Aynı zamanda bu kullanımda `end case` bitirilmesi de geçersizdir. Tipik olarak end ile bitirilmesi gerekir.

>Aşağıdaki demo örneği inceleyiniz. Burada case expression, expression olarak kullanılmıştır

```sql
do $$  
    declare  
        origin int = -10;  
        bound int = 11;  
        val int = floor(random() * (bound - origin) + origin);  
        message varchar(100);  
    begin  
        raise notice 'Value: %', val;  
        message = case  
            when val > 0 then 'positive'  
            when val = 0 then 'zero'  
            else 'negative'  
        end;  
  
        raise notice '%', message;  
    end;  
$$
```

>case expression eşitlik karşılaştırması yapacak biçiminde de kullanılabilir. 

>Aşağıdaki demo örneği inceleyiniz

```sql
do $$  
    declare  
        origin int = 1;  
        bound int = 4;  
        val int = floor(random() * (bound - origin) + origin);  
        message varchar(100);  
    begin  
        raise notice 'Value: %', val;  
        message = case val  
			  when 1 then 'one'  
			  when 2 then 'two'  
			  else 'three'  
        end;  
  
        raise notice '%', message;  
    end;  
$$
```

>**Sınıf Çalışması:** Aşağıdaki tabloyu hazırlayınız ve ilgili soruları yanıtlayınız
>employees
>	- citizen_id char(11) p.k.
>	- first_name varchar(250) not null
>	- middle_name varchar(250)
>	- last_name varchar(250) not null
>	- marital_status int
>**Sorular:**
>1. Parametresi ile aldığı int türden medeni durum bilgisine göre yazı olarak `Evli, Bekar, Boşanmış veya Belirsiz` yazılarından birisine geri dönen `get_marital_status_text_tr` fonksiyonunu yazınız. Burada sıfır Evli, 1 Bekar, 2, Boşanmış ve diğer değerler de Belirsiz anlamında kullanılacaktır.
>2. Parametresi ile aldığı 3 tane yazıyı aralarına space karakteri koyarak, ancak `null` olanları yazıya eklemeyecek şekilde toplam yazıya dönen `get_full_text` fonksiyonunu yazınız.
>3. Parametresi ile aldığı `citizen_id` bilgisine göre ismin tamamını (full_name), marital_status_text bilgisini tablo olarak döndüren `get_employee_by_citizen_id`fonksiyonunu yazınız.

```sql
create table employees (  
	citizen_id char(11) primary key,  
	first_name varchar(250) not null,  
	middle_name varchar(250),  
	last_name varchar(250) not null,  
	marital_status int  
);  
  
create or replace function get_marital_status_text_tr(status int)  
    returns varchar(20)  
as  
$$  
begin  
    return case status  
        when 0 then'Evli'  
        when 1 then 'Bekar'  
        when 2 then 'Boşanmış'  
        else 'Belirsiz'  
        end;  
end;  
$$ language plpgsql;  
  
create or replace function get_full_text(varchar, varchar, varchar)  
    returns varchar  
as  
$$  
declare  
    full_text varchar = '';  
begin  
    if $1 is not null then  
        full_text = $1;  
    end if;  
  
    if $2 is not null then  
        if full_text <> '' then  
            full_text = full_text || ' ';  
        end if;  
        full_text = full_text || $2;  
    end if;  
  
    if $3 is not null then  
        if full_text <> '' then  
            full_text = full_text || ' ';  
        end if;  
        full_text = full_text || $3;  
    end if;  
  
    return full_text;  
end;  
$$ language plpgsql;  
  
create or replace function get_employee_by_citizen_id(char)  
    returns table (full_name varchar, marital_status_text varchar)  
as  
$$  
begin  
    return query select get_full_text(first_name, middle_name, last_name), get_marital_status_text_tr(marital_status)  
                 from employees where citizen_id =$1;  
end;  
$$ language plpgsql;  
  
-- Simple test codes  do  
$$  
    begin  
        raise notice 'Status:%', get_marital_status_text_tr(0);  
        raise notice 'Status:%', get_marital_status_text_tr(1);  
        raise notice 'Status:%', get_marital_status_text_tr(2);  
        raise notice 'Status:%', get_marital_status_text_tr(3);  
        raise notice 'Status:%', get_marital_status_text_tr(4);  
    end;  
$$;  
  
  
do  
$$  
    begin  
        raise notice '(%)', get_full_text('Oğuz', null, 'Karan');  
        raise notice '(%)', get_full_text('Ali', 'Vefa', 'Serçe');  
    end;  
$$;  
  
select * from employees;  
  
select * from get_employee_by_citizen_id('f048425c-58e5-4694-94ec-7ae8dc4fbeae');  
select * from get_employee_by_citizen_id('f2a22590-a77a-4f23-8b3f-6ec665371369');
```

>Aşağıdaki demo örnekte case expression sorgu içerisinde kullanılmıştır

```sql
select get_full_text(first_name, middle_name, last_name),  
    case marital_status  
       when 0 then'Evli'  
       when 1 then 'Bekar'  
       when 2 then 'Boşanmış'  
       else 'Belirsiz'  
    end as marital_status  
    from employees where citizen_id ='f048425c-58e5-4694-94ec-7ae8dc4fbeae';
```

>**Sınıf Çalışması:** Aşağıdaki tabloları yaratınız ve ilgili soruları yanıtlayınız
>**Tablolar:**
>- court_status
>	- court_status_id
>	- description (Available, Not Available, Reserved)
>- court_types
>	- court_type_id
>	- description (Open, Closed, OpenOrClosed)
>- courts
>	- court_id
>	- name
>	- court_type_id
>	- court_status_id
>**Sorular:**
>1. Tüm kortların bilgilerini detaylı olarak döndüren `get_court_details` fonksiyonunu yazınız
>2. Tüm kortların bilgilerine ilişkin court_type ve court_status alanlarını Türkçe olarak döndüren `get_court_details_tr` fonksiyonunu yazınız
>**Çözüm:**

```sql
create table court_status (  
    court_status_id serial primary key,  
    description varchar(50) not null  
);  
  
insert into court_status (description) values ('Available'), ('Not Available'), ('Reserved');  
  
create table court_types (  
    court_type_id serial primary key,  
    description varchar(50) not null  
);  
  
insert into court_types (description) values ('Open'), ('Closed'), ('Open or Closed');  
  
create table courts (  
    court_id serial primary key,  
    name varchar(250) not null,  
    court_status_id int references court_status(court_status_id),  
    court_type_id int references court_types(court_type_id)  
);  
  
create or replace function get_court_details()  
    returns table (court_name varchar, court_status varchar, court_type varchar)  
as $$  
begin  
    return query select c.name court_name, cs.description court_status, ct.description court_type  
            from  
                court_status cs inner join courts c on cs.court_status_id = c.court_status_id  
                                inner join court_types ct on c.court_type_id = ct.court_type_id;  
end  
$$ language plpgsql;  
  
create or replace function get_court_details_tr()  
    returns table (court_name varchar, court_status varchar, court_type varchar)  
as $$  
    begin  
    return query select c.name,  
                   case c.court_status_id  
                       when 1 then cast ('Uygun' as varchar)  
                       when 2 then cast ('Uygun değil' as varchar)  
                       else cast('Rezerve' as varchar)  
                       end,  
                   case c.court_type_id  
                       when 1 then cast ('Açık' as varchar)  
                       when 2 then cast ('Kapalı' as varchar)  
                       else cast ('Açık veya Kapalı' as varchar)  
                       end  
            from  courts c;  
    end  
$$ language plpgsql;  
  
-- Simple test codes  
select * from get_court_details();  
select * from get_court_details_tr();
```

##### Tarih-Zaman Fonksiyonları

>Tarih-zaman işlemleri neredeyse her uygulamada kullanılmaktadır. PostgreSQL'de tarih, zaman ve tarih-zaman türleri birbirinden ayrıdır. Bunlar sırasıyla **date, time** ve **timestamp** biçimindedir. Ayrıca zaman aralığı kavramı temsil eden **interval** türü de vardır.

###### current_date, current_time ve current_timestamp Fonksiyonları

>Bu fonksiyonlar sırasıyla PostgreSQL server uygulamasının çalıştığı sistemdeki o anki tarih, zaman ve tarih-zaman bilgilerini elde etmek için kullanılır.

```sql
do $$  
    begin  
       raise notice 'Current Date: %', current_date;  
       raise notice 'Current Time: %', current_time;  
       raise notice 'Current Datetime: %', current_timestamp;  
    end  
$$
```

###### date_part Fonksiyonu

>Bu fonksiyon ile bir tarih, zaman ya da tarih-zamana ilişkin bileşenler elde edilebilir. Burada `dow`, `0 (sıfır) Pazar, 1 Pazartesi, ..., 6 Cumartesi` olacak şekildedir.


```sql
do $$  
    declare  
        today date = current_date;  
    begin  
       raise notice 'Day: %', date_part('day', today);  
       raise notice 'Mon: %', date_part('mon', today);  
       raise notice 'Year: %', date_part('year', today);  
       raise notice 'Day of Week: %', date_part('dow', today);  
       raise notice 'Day of Year: %', date_part('doy', today);  
       raise notice 'Century: %', date_part('century', today);  
       raise notice 'Decade: %', date_part('decade', today);  
    end  
$$
```

```sql
do $$  
    declare  
        today timestamp = current_timestamp;  
    begin  
       raise notice 'Day: %', date_part('day', today);  
       raise notice 'Mon: %', date_part('mon', today);  
       raise notice 'Year: %', date_part('year', today);  
       raise notice 'Hour: %', date_part('hour', today);  
       raise notice 'Minute: %', date_part('minute', today);  
       raise notice 'Second: %', date_part('second', today);  
       raise notice 'Day of Week: %', date_part('dow', today);  
       raise notice 'Day of Year: %', date_part('doy', today);  
       raise notice 'Century: %', date_part('century', today);  
       raise notice 'Decade: %', date_part('decade', today);  
    end  
$$
```

>date türü ile time'a ilişkin bilgiler istendiğinde geceyarısı (midnight) zamanı olarak verilir. time türü ile date'e ilişkin bilgiler alınmaya çalışıldığında çalışma zamanı hatası (runtime error) oluşur. 

###### to_date ve to_timestamp Fonksiyonları

>Bu fonksiyonlar yazıyı sırasıyla date ve timestamp türüne çevirmek için kullanılır. Bu fonksiyon tarihe ilişkin yazının format bilgisini de alır. Format bilgisinde `D -> Day, M -> Month, Y -> Year, HH24 -> 24 Hour, HH12 -> 12 Hour, MI -> Minute, SS -> Second`biçimindedir. Diğer format bilgileri fonksiyonların dokümantasyonundan elde edilebilir.

```sql
do $$  
    declare  
        birth_date date;  
        birth_date_str char(10);  
        date_format_str char(10);  
    begin  
        date_format_str = 'DD/MM/YYYY';  
        birth_date_str = '10/09/1976';  
        birth_date = to_date(birth_date_str, date_format_str);  
  
        raise notice 'Day of week:%', date_part('dow', birth_date);  
    end  
$$
```

```sql
do $$  
    declare  
        date_time timestamp;  
        date_time_str char(30);  
        date_time_format_str char(30);  
    begin  
        date_time_format_str = 'DD/MM/YYYY HH24:MI:SS';  
        date_time_str = '09/03/2025 21:44:00';  
        date_time = to_timestamp(date_time_str, date_time_format_str);  
  
        raise notice 'Date:%', date_time;  
    end  
$$
```


###### age Fonksiyonu

>Bu fonksiyon, o anki tarih ile parametresi ile aldığı tarih arasındaki farkı interval olarak döndürür.

```sql
do $$  
    declare  
        birth_date date;  
        birth_date_str char(10);  
        date_format_str char(10);  
    begin  
        date_format_str = 'DD/FF/YYYY';  
        birth_date_str = '10/09/1976';  
        birth_date = to_date(birth_date_str, date_format_str);  
  
        raise notice 'Age:%', age(birth_date);  
    end  
$$
```

###### date_trunc Fonksiyonu

>Bu fonksiyon aldığı tarih, zaman ya da tarih zaman bilgisini aldığı anahtara göre default değerleri olacak şekilde yeni bir değere geri döner. Format, `yıl, ay, gün, saat, dakika, saniye, ...` sırasıyladır. Örneğin anahtar değeri olarak hour alırsa saatin sağında bulunan değerleri sıfırlar ya da örneğin day alırsa zaman bilgisini sıfırlamış olur. 

```sql
do $$  
    declare  
       now timestamp = current_timestamp;  
    begin  
        raise notice '%', now;  
        raise notice '%', date_trunc('year', now);  
        raise notice '%', date_trunc('mon', now);  
        raise notice '%', date_trunc('day', now);  
        raise notice '%', date_trunc('hour', now);  
        raise notice '%', date_trunc('minute', now);  
        raise notice '%', date_trunc('second', now);  
    end  
$$
```

##### Bir Tarihe İlişkin Ayın Son Gününün Bulunması

>PostgreSQL'de maalesef bir tarihe ilişkin ayın son gününü tarih olarak veren bir fonksiyon hazır olarak yoktur. Bu durumda gerektiğinde programcı bunu kendi yazmalıdır. Aşağıdaki fonksiyon ile ayın sonu günü elde edilebilir:

```sql
create or replace function end_of_month(date)  
returns date  
as  
$$  
    begin  
        return date_trunc('month', $1) + '1 Month - 1 day';  
    end  
$$ language plpgsql;
```

```sql
do $$  
    declare  
       today date = current_date;  
    begin  
        raise notice '%', today;  
        raise notice '%', end_of_month(today);  
    end  
$$
```

>Bu fonksiyon ilgili tarihe ilişkin ayın ilk günü elde edilmiş ve 1 ay sonrasının 1 gün öncesine ilişkin tarih elde edilerek aynı son günü bulunmuştur.

###### Tarih, Zaman ve Tarih Zaman Karşılaştırması

>Bu karşılaştırmalar tipik olarak `<, >, <=, >=, =, <>` gibi karşılaştırma operatörleri ile yapılabilir:

```sql
  
do $$  
    declare  
        birth_date date = to_date('10/09/1976', 'DD/MM/YYYY');  
        today date = current_date;  
        birth_day date;  
        age interval;  
    begin  
        birth_day := to_date(cast(date_part('day', birth_date) as varchar) || '/' || cast(date_part('month', birth_date) as varchar) || '/' || cast(date_part('year', today) as varchar), 'DD/MM/YYYY') ;  
        age = age(birth_date);  
  
        if birth_day > today then  
            raise notice 'Doğum gününüz şimdiden kutlu olsun. Yeni yaşınız:%', age;  
        elseif birth_day = today then  
            raise notice 'Doğum gününüz kutlu olsun. Yeni yaşınız:%', age;  
        else  
            raise notice 'Geçmiş Doğum gününüz kutlu olsun. Yeni yaşınız:%', age;  
        end if;  
    end  
$$
```


###### make_date, make_timestamp ve extract Fonksiyonkları

>make_date ve make_timestamp fonksiyonları parametreleri ile aldıkları bileşenlerden sırasıyla date ve timestamp karşılıklarını döndürürler. extract fonksiyonu date_part fonksiyonu ile hemen hemen aynı işi yapar.

```sql
  
do $$  
    declare  
        birth_date date = to_date('10/09/1976', 'DD/MM/YYYY');  
        today date = current_date;  
        birth_day date;  
        age interval;  
    begin  
        birth_day := make_date(cast(extract(year from today) as int), cast(extract(mon from birth_date) as int), cast(extract(day from birth_date) as int));  
        age = age(birth_date);  
  
        if birth_day > today then  
            raise notice 'Doğum gününüz şimdiden kutlu olsun. Yeni yaşınız:%', age;  
        elseif birth_day = today then  
            raise notice 'Doğum gününüz kutlu olsun. Yeni yaşınız:%', age;  
        else  
            raise notice 'Geçmiş Doğum gününüz kutlu olsun. Yeni yaşınız:%', age;  
        end if;  
    end  
$$
```

SSSSSSSSSSSSSSSS
>**Sınıf Çalışması:** Aşağıdaki tabloya göre ilgili soruları yanıtlayınız
>- students
>	- citizen_id char(40)
>	- first_name
>	- middle_name
>	- last_name
>	- birth_date
>	- register_date default(getdate())
>- **Sorular:**
>1. Parametresi ile aldığı doğum tarihi bilgisine göre aşağıdaki mesajardan birini döndüren `get_birth_date_message_tr` fonksiyonunu yazınız
>	- Doğum günü geçmişse **Geçmiş doğum gününüz kutlu olsun. Yeni yaşınız: 49**
>	- Doğum günü gelmemişse **Doğum gününüz şimdiden kutlu olsun. Yeni yaşınız: 49**
>	- Doğum günü fonksiyonun çağrıldığı günü **Doğum gününüz kutlu olsun. Yeni yaşınız: 49**
>2. Parametresi ile aldığı ay ve yıl bilgisine göre o ay ve o yıl içerisinde kayıt olmuş olan öğrencileri tablo olarak döndüren `get_students_by_register_month_and_year` fonksiyonunu yazınız.

>**Sınıf Çalışması:** dpn24_bankappdb veritabanının 1.0.0 versiyonu için aşağıdaki soruları yanıtlayınız
>- Parametresi ile aldığı müşteri numarasına göre müşterinin aşağıdaki bilgilerini tablo olarak döndüren 
    `get_customer_by_number` fonksiyonunu yazınız
    Bilgiler:
        - Adı soyadı
        - Kart numarası: İlk ve son 4 hanesi görünecek diğerleri X olarak görünecektir
        - security_code: İlk karakter görünecek geri kalanlar X biçiminde görünecektir
        - Kartın son kullanma tarihi
        - Kart türünün yazısal karşılığı
        - Kart sahibinin personel olup olmadığı, personel ise "Personel" değilse "Personel değil" biçiminde
        - Kartın son kullanma tarihinin geçip geçmediği, geçmişse "Son kullanma tarihi geçmiş" geçmemişse "Son kullanma tarihi geçmemiş"
>
>- Parametresi ile aldığı kart tür id'sine göre yurt dışında yaşayan müşterilere ilişkin aşağıdaki bilgileri tablo biçiminde döndüren `get_non_local_customers_by_card_type_id` fonksiyonunu yazınız
    Bilgiler:
        - Adı soyadı
        - Kart numarası: İlk ve son 4 hanesi görünecek diğerleri X olarak görünecektir
        - security_code: İlk karakter görünecek geri kalanlar X biçiminde görünecektir
        - Kartın son kullanma tarihi
        - Kart türünün yazısal karşılığı
        - Kart sahibinin personel olup olmadığı, personel ise "Personel" değilse "Personel değil" biçiminde
        - Kartın son kullanma tarihinin geçip geçmediği, geçmişse "Son kullanma tarihi geçmiş" geçmemişse 
        "Son kullanma tarihi geçmemiş"

>- Parametresi ile aldığı kart tür id'sine göre yurt içinde yaşayan kart süreleri dolmuş müşterileri tablo biçiminde döndüren `get_local_customers_by_card_type_id` fonksiyonunu yazınız
    Bilgiler:
        - Adı soyadı
        - Kart numarası: İlk ve son 4 hanesi görünecek diğerleri X olarak görünecektir
        - security_code: İlk karakter görünecek geri kalanlar X biçiminde görünecektir
        - Kartın son kullanma tarihi
        - Kart türünün yazısal karşılığı
        - Kart sahibinin personel olup olmadığı, personel ise "Personel" değilse "Personel değil" biçiminde            

##### Otomatik Belirlenen Değerin Elde Edilmesi

>PostgreSQL'de otomatik belirlenen değerlerin elde edilmesi için `currval` fonksiyonu kullanılır. Bu fonksiyon `register class` ismini alır. Programcı register class için herhangi bir belirleme yapmamışsa bu isim şu formatta oluşturulur:
>
```sql
<tablo ismi>_<alan adı>_seq
```

Örneğin students isimli bir tablonun otomatik artan alan adı student_id ise register class ismi default olarak şu şekilde oluşturulur:

```sql
students_student_id_seq
```

```sql
create table students (  
    student_id serial primary key,  
    name varchar(50),  
    birth_date date  
);  
  
insert into students (name, birth_date) values ('Alice', '1990-01-01');  
  
create or replace function insert_student(varchar(50), birth_date date)  
returns int  
as 
$$  
begin  
    -- Transaction safe yapılacak  
    insert into students (name, birth_date) values ($1, $2);
     
    return currval('students_student_id_seq');  
end  
$$ language plpgsql;
```

```sql
do $$  
    begin  
       raise notice '%', insert_student('Oğuz', '1976-09-10');  
    end  
$$
```

##### Stored Procedures

>PostgreSQL'de `stored procedure (SP)`, `11` sürümü ile eklenmiştir. SP'lerin geri dönüş değeri kavramı yoktur. void da yazılmaz. Ancak, SP içerisinde return deyimi tek başına sonlandırmak amaçlı istenirse kullanılabilir. SP'ler parametresiz de olabilirler. SP'ler kullanılacak language bilgisi fonksiyonun sonunda verilmez, fonksiyonun bloğu başlamadan verilebilir. Bir SP `create procedure` veya `create or replace procedure` cümlesi ile yaratılır. SP'ler `inout` parametreli olabilirler. Fonksiyon ile SP'lerin farkları bölüm sonunda ayrıca ele alınacaktır. Bir SP `call` komutu ile çalıştırılır. 

```sql
create table people (  
    citizen_id char(11) primary key,  
    first_name varchar(300) not null,  
    last_name varchar(300) not null,  
    birth_date date not null  
);  
  
create table people_younger (  
    citizen_id char(11) primary key,  
    first_name varchar(300) not null,  
    last_name varchar(300) not null,  
    birth_date date not null  
);  
  
create table people_older (  
    citizen_id char(11) primary key,  
    first_name varchar(300) not null,  
    last_name varchar(300) not null,  
    birth_date date not null  
);  
  
create or replace procedure sp_insert_person(char(11), varchar(300), varchar(300), date)  
language plpgsql  
as  
$$  
declare  
    age double precision;  
begin  
    age = extract(epoch from age($4))::double precision / (60. * 60 * 24 * 365);  
  
    if age < 18 then  
        insert into people_younger (citizen_id, first_name, last_name, birth_date) values ($1, $2, $3, $4);  
    elseif age < 65 then  
        insert into people (citizen_id, first_name, last_name, birth_date) values ($1, $2, $3, $4);  
    else  
        insert into people_older (citizen_id, first_name, last_name, birth_date) values ($1, $2, $3, $4);  
    end if;  
end;  
$$;
```

>Aşağıdaki örneği inceleyiniz

```sql
create table categories (  
    category_id serial primary key,  
    description varchar(100) not null  
);  
  
insert into categories (description) values ('Food'), ('Drinks'), ('Electronics');  
  
create table products (  
    code varchar(100) primary key,  
    category_id int references categories(category_id) not null,  
    name varchar(500) not null,  
    unit_price double precision not null  
);  
  
create table payments (  
    payment_id serial primary key,  
    date timestamp default (current_timestamp) not null  
);  
  
create table payments_to_products (  
    payment_to_product_id serial primary key,  
    payment_id int references payments(payment_id) not null,  
    code varchar(100) references products(code) not null,  
    amount int not null,  
    unit_price double precision not null  
);  
  
create procedure sp_insert_product(varchar(100), int, varchar(500), double precision)  
language plpgsql  
as $$  
begin  
    insert into products (code, category_id, name, unit_price) values ($1, $2, $3, $4);  
end;  
$$;  
  
create or replace procedure sp_pay_first(varchar(100), int, double precision)  
language plpgsql  
as $$  
declare  
    payment_id int;  
begin  
    -- Must be transaction safe  
    insert into payments default values;  
    payment_id = currval('payments_payment_id_seq');  
    insert into payments_to_products (payment_id, code, amount, unit_price) values (payment_id, $1, $2, $3);  
end;  
$$;  
  
create or replace procedure sp_pay(int, varchar(100), int, double precision)  
    language plpgsql  
as $$  
begin  
    insert into payments_to_products (payment_id, code, amount, unit_price) values ($1, $2, $3, $4);  
end;  
$$;  
  
  
call sp_insert_product('LPT-100', 1, 'Laptop', 1000.0);  
call sp_pay_first('LPT-100', 23, 10.0);
```

>SP ve fonksiyon arasındaki temel farklar şu şekilde özetlenebilir:

| Procedure                                                         | Fonksiyon                                             |
| ----------------------------------------------------------------- | ----------------------------------------------------- |
| Geri Dönüş Değeri Kavramı Yoktur                                  | Geri Dönüş Değeri Kavramı vardır                      |
| CALL komutu ile çalıştırılır                                      | Select veya blok içerisinde çağrılır                  |
| commit ve rollback gibi transaction yönetim işlemleri yapılabilir | Transaction'a doğrudan müdahale edilemez              |
| language bildirimi fonksiyon bloğundan önce yapılabilir           | language bildirimi fonksiyon bloğunun sonunda yapılır |
| PostgreSQL 11 ile eklenmiştir                                     | İlk sürümden beri vardır                              |

##### Aggregate Fonksiyonlar

>Bu fonksiyonlar sorgu içerisindeki bilgilere göre kümülatif biçimde işlem yaparak sonuç elde etmek için kullanılırlar. Bu fonksiyonlar özellikle gruplamada da çok sık kullanılmaktadır. Gruplama işlemi ve `group by` operatörü ileride ele alınacaktır.

>Aşağıdaki aggregate fonksiyonlar `dpn24_veterinerian_hospital_db` veritabanının `1.0.0` versiyonu ile örneklenmiştir.

###### avg Fonksiyonu 

>Bu fonksiyon parametresi ile aldığı alana ilişkin değerlerin ortalamasını hesaplar

>Aşağıdaki örnekte hayvanların yaşlarının ortalamasını hesaplayan bir fonksiyon yazılmıştır
>
```sql
create or replace function get_age_average()  
returns double precision  
as $$  
declare  
    average real;  
begin  
    select avg(extract(epoch from age(birth_date))::double precision / (60. * 60 * 24 * 365)) from animals into average;  
  
    return average;  
end  
$$ language plpgsql;
```

###### sum Fonksiyonu
>Bu fonksiyon parametresi ile aldığı alana ilişkin değerlerin ı hesaplar.

>Aşağıdaki örnekte hayvanların yaşları toplamını hesaplayan bir fonksiyon yazılmıştır
```sql
create or replace function get_age_sum()  
returns double precision  
as $$  
declare  
    average real;  
begin  
    select sum(extract(epoch from age(birth_date))::double precision / (60. * 60 * 24 * 365)) from animals into average;  
  
    return average;  
end  
$$ language plpgsql;
```

##### count Fonksiyonu

>Bu fonksiyon ile sorgunun kayıt (record) sayısı elde edilebilir. Tipik olarak bu fonksiyon argüman olarak `*`değerini alır. 

>Aşağıdaki örnekte hayvanların sayısını hesaplayan sorgu yazılmıştır

```sql
select count(*) from animals;
```

>Aşağıdaki örnekte veterine gelen ve tedavi diploma_no'su verilen bir veterinere gelen hasta sayını hesaplayan sorgu yazılmıştır

```sql
select count(*) from veterinarian_to_animals where diploma_no = 67;
```

###### min ve max Fonksiyonları

>Bu fonksiyonlar sırasıyla aldıkları alana ilişkin en küçük ve en büyük değeri hesaplarlar

>Aşağıdaki örnekte hayvanlar içerisindeki en büyük ve en küçük yaşı veren fonksiyonlar yazılmıştır

```sql
create or replace function get_min_age()  
    returns double precision  
as $$  
declare  
    average real;  
begin  
    select min(extract(epoch from age(birth_date))::double precision / (60. * 60 * 24 * 365)) from animals into average;  
  
    return average;  
end  
$$ language plpgsql;  
  
create or replace function get_max_age()  
    returns double precision  
as $$  
declare  
    average real;  
begin  
    select max(extract(epoch from age(birth_date))::double precision / (60. * 60 * 24 * 365)) from animals into average;  
  
    return average;  
end  
$$ language plpgsql;
```

>Aşağıdaki örnekte en büyük yaş ile en küçük yaşın ortalaması elde edilen sorgu yazılmıştır

```sql
create or replace function get_age(date)  
returns double precision as  
$$  
    begin  
        return extract(epoch from age($1))::double precision / (60. * 60 * 24 * 365);  
    end;  
$$ language plpgsql;  
  
select (max(get_age(birth_date)) + min(get_age(birth_date)) / 2) from animals
```


>**Sınıf Çalışması:** `dpn24_veterinerian_hospital_db` veritabanının `1.0.0` versiyonunda diploma_no'su bir veteriner için ödenen ücretlerin ortalamasını döndüren `get_prices_avg_by_diploma_no` fonksiyonunu yazınız

**Çözüm:**
```sql
create or replace function get_prices_avg_by_diploma_no(bigint)  
    returns double precision  
as $$  
declare  
        average double precision;  
begin  
    select avg(vtap.price) from  
    veterinarian_to_animals vta inner join veterinarian_to_animal_prices vtap on vta.veterinarian_to_animal_id = vtap.veterinarian_to_animal_id  
    where vta.diploma_no = $1 into average;  
    return average;  
end  
$$ language plpgsql;
```

##### group by Clause ve having Operatörü

>`group by clause` özellikle aggregate fonksiyonlarla birlikte gruplama işlemi için çok sık karşımıza çıkan sorgulardandır. Gruplarken aynı zamanda gruplamaya yönelik koşul da belirtilecekse `having` operatörü kullanılır. 

>Aşağıdaki örneği ve ilgili sorguları inceleyiniz
>**Örnek:** Aşağıdaki tabloları `dpn24_school_db` veritabanında oluşturunuz ve ilgili soruları yanıtlayınız
>**Tablolar:**
>- students
>	- student_id int identity
>	- citizen_number char(11)
>	- first_name nvarchar(100)
>	- middle_name nvarchar(100)
>	- last_name nvarchar(100)
>	- birth_date date
>	- address nvarchar(max)
>- lectures
>	- lecture_code char(7)
>	- name nvarchar(100)
>	- credits int
>- grades
>	- grade_id int identity
>	- description nvarchar(2)
>	- value real
>- enrolls
>	- enroll_id bigint identity
>	- student_id foreign key
>	- lecture_code foreign key
>	- grade_id foreign key
>
>Harf notları ve karşılık gelen değerler şu şekilde olabilir
> - AA, 4.0
> - BA, 3.5
> - BB, 3.0
> - CB, 2.5
> - CC, 2.0
> - DC, 1.5
> - DD, 1.0
> - FF, 0.0
> - NA, -1
> - P, -1
> **Sorular:**
> 1. Parametresi ile aldığı ders kodu için öğrencilerin sayısını notlara göre gruplayarak getiren sorguya geri dönen `get_histogram_data_by_lecture_code` tablo döndüren fonksiyonunu yazınız.
> 2. Her bir ders için öğrenci sayılarını veren sorguya geri dönen `get_all_lectures_students_count` tablo döndüren fonksiyonunu yazınız
> 3. Kredi toplamları parametresi ile aldığı değerden büyük olan öğrencilerin bilgilerine geri dönen `get_students_by_total_credits_greater` tablo döndüren fonksiyonunu yazınız.
> 4. Dersi, parametresi ile aldığı sayıdan fazla olan sayıda öğrencinin aldığı dersleri döndüren `get_lectures_by_registered_students_count_greater` tablo döndüren fonksiyonu yazınız.
> 5. Dersi, parametresi ile aldığı sayıdan fazla olan sayıda öğrencinin aldığı derslerin ağırlıklı not ortalamalarını döndüren `get_lectures_grade_averages_by_registered_students_count_greater` tablo döndüren fonksiyonu yazınız. 
> 	**Açıklama:** Ağırlıklı not ortalamasını hesaplarken bir ders için, dersin kredisi ile alınan nota ilişkin değer çarpılır. Ortalama bu çarpımlarını ortalaması hesaplanır
> 6. Bir dersin açılabilmesi için belli sayıda öğrencinin olması gerektiği bir durumda açılması için gereken minimum öğrenci sayısını parametre olarak alan ve açılabilen dersleri getiren `get_open_lectures_by_minimum_count` fonksiyonunu yazınız.

>**Çözümler:**

```sql
create database dpn24_school_db  
  
use dpn24_school_db  
  
create table students(  
    student_id int primary key identity(1, 1),  
    citizen_number char(40) unique not null,  
    first_name nvarchar(100) not null,  
    middle_name nvarchar(100),  
    last_name nvarchar(100) not null,  
    birth_date date not null,  
    address nvarchar(max)  
)  
  
create table lectures(  
    lecture_code char(7) primary key,  
    name nvarchar(100) not null,  
    credits int not null,  
)  
  
  
create table grades (  
    grade_id int primary key identity(1, 1),  
    description nvarchar(2) not null,  
    value real not null  
)  
  
insert into grades (description, value) values ('AA', 4.0), ('BA', 3.5), ('BB', 3.0), ('CB', 2.5), ('CC', 2.0), ('DC', 1.5), ('DD', 1.0), ('FF', 0.0), ('NA', 0.0), ('P', -1)  
drop table enrolls  
  
  
create table enrolls (  
    enroll_id bigint primary key identity(1, 1),  
    student_id int foreign key references students(student_id),  
    lecture_code char(7) foreign key references lectures(lecture_code),  
    grade_id int foreign key references grades(grade_id)  
)  
  
create function get_full_text(@first nvarchar(250), @second nvarchar(250), @third nvarchar(max))  
returns nvarchar(max)  
as  
begin  
    declare @full_text nvarchar(max) = '';  
  
    if @first is not null  
        set @full_text = @first;  
  
    if @second is not null  
        begin                   if @full_text <> ''  
                set @full_text = @full_text + ' ';  
            set @full_text = @full_text + @second;  
        end  
  
    if @third is not null  
        begin                   if @full_text <> ''  
                set @full_text = @full_text + ' ';  
            set @full_text = @full_text + @third;  
        end  
  
    return @full_text;  
end  
  
-- 1  
create function get_histogram_data_by_lecture_code(@lecture_code char(7))  
returns table  
as  
return (  
    select g.description, count(*) as count from  
    enrolls e inner join grades g on g.grade_id = e.grade_id  
    where e.lecture_code = @lecture_code  
    group by g.description  
)  
  
-- 2  
create function get_all_lectures_students_count()  
returns table  
as  
return (  
    select lec.lecture_code, lec.name, count(*) as count  
    from  
    lectures lec inner join enrolls e on lec.lecture_code = e.lecture_code  
    group by lec.name, lec.lecture_code  
)  
  
-- 3  
create function get_students_by_total_credits_greater(@credits int)  
returns table  
as  
return (  
    select s.citizen_number,  
           dbo.get_full_text(s.first_name, s.middle_name, s.last_name) as full_name,  
           sum(lec.credits) as total_credits  
    from  
    lectures lec inner join enrolls e on lec.lecture_code = e.lecture_code  
    inner join students s on s.student_id = e.student_id  
    group by s.citizen_number, dbo.get_full_text(s.first_name, s.middle_name, s.last_name)  
    having sum(lec.credits) > @credits  
)  
  
-- 4  
create function get_lectures_by_registered_students_count_greater(@count int)  
returns table  
as  
return (  
    select lec.lecture_code, lec.name, count(*) as count  
    from  
    lectures lec inner join enrolls e on lec.lecture_code = e.lecture_code  
    group by lec.lecture_code, lec.name having count(*) > @count  
)  
  
-- 5  
create function get_lectures_grade_averages_by_registered_students_count_greater(@count int)  
returns table  
as  
return (  
    select lec.lecture_code, lec.name, avg(lec.credits * g.value) as average  
    from  
    lectures lec inner join enrolls e on lec.lecture_code = e.lecture_code  
    inner join grades g on e.grade_id = g.grade_id  
    group by lec.lecture_code, lec.name having count(*) > @count  
)  
  
-- 6  
create function get_open_lectures_by_minimum_count(@minCount int)  
returns table  
as  
return (  
    select lec.lecture_code, lec.name, lec.credits, count(*) as count  
    from  
    lectures lec inner join enrolls e on lec.lecture_code = e.lecture_code  
    group by lec.lecture_code, lec.name, lec.credits having count(*) > @minCount  
)

```


>**Sınıf Çalışması:** Aşağıdaki tablolara göre ilgili soruları yanıtlayınız. 
>
>**Sorular:**
>1. Kaç tane ürün vardır?
>2. Stokta bulunan ürünler kaç tanedir?
>3. Stokta bulunan ürünlerin toplam sayısı?
>4. Stokta bulunan ürünlerin toplam maliyetleri ve toplam satış fiyatları?
>5. Stokta bulunan ürünlerin hepsi satıldığında kar-zarar durumuna ilişkin değer?
>6. En pahalı ürün ile en ucuz ürünün satış fiyatları?
>7. En fazla toplam fiyata sahip ürün ile en az toplam fiyata sahip ürünlerin toplam fiyatları?
>8. Stokta bulunan ürünleri kategorilerine göre gruplayarak kar-zarar durumunu veren sorgu?
>9. Stokta bulunan ürünleri kategorilerine göre gruplayarak en çok kar getiren ürünlerin toplam fiyatlarını veren sorgu
>10. Stokta bulunan ürünleri kategorilerileri ve kategori id'lerine göre gruplayarak en çok kar getiren ürünlerin toplam fiyatlarını veren sorgu
>11. Stokta bulunan ürünleri kategorilerine ve kategori id'lerine göre gruplayarak en çok kar getiren ürünlerden maksimum karları, bilinen bir değerden büyük olanların toplam fiyatlarını getiren sorgu

```sql
create table product_categories (  
    product_category_id int primary key identity(1, 1),  
    description nvarchar(300) not null  
)  
  
insert into product_categories (description) values ('Wear'), ('Electronics'), ('Auto'), ('Vegetables')  
  
create table products (  
    code varchar(50) primary key,  
    product_category_id int foreign key references product_categories(product_category_id) not null,  
    name nvarchar(300) not null,  
    stock real not null,  
    cost money default(0.0) not null,  
    unit_price money default(0.0) not null  
)  
  
-- 1  
select count(*) from products;  
  
-- 2  
select count(*) from products where stock > 0  
  
-- 3  
select sum(stock) from products where stock > 0;  
  
-- 4  
select sum(stock * cost) total_cost, sum(stock * products.unit_price) from products where stock > 0  
  
-- 5  
select sum(stock * (unit_price - cost)) total from products where stock > 0  
  
-- 6  
select min(unit_price), max(unit_price) from products;  
  
-- 7  
select min(stock * unit_price), max(stock * unit_price) from products where stock > 0  
  
-- 8  
select pc.description, sum(p.stock * (p.unit_price - p.cost)) total  
from products p inner join product_categories pc on pc.product_category_id = p.product_category_id  
where stock > 0  
group by pc.description  
  
-- 9  
select pc.description, max(p.stock * (p.unit_price - p.cost)) as maximum  
from products p inner join product_categories pc on pc.product_category_id = p.product_category_id  
where stock > 0  
group by pc.description  
  
-- 10  
select pc.product_category_id,  pc.description, max(p.stock * (p.unit_price - p.cost)) as maximum  
from products p inner join product_categories pc on pc.product_category_id = p.product_category_id  
where stock > 0  
group by pc.description, pc.product_category_id  
  
-- 11  
create function get_maximum_total_greater(@threshold money)  
returns table  
as  
return (select pc.product_category_id,  pc.description, max(p.stock * (p.unit_price - p.cost)) as maximum  
    from products p inner join product_categories pc on pc.product_category_id = p.product_category_id  
    where stock > 0  
    group by pc.description, pc.product_category_id having max(p.stock * (p.unit_price - p.cost)) >= @threshold  
)  
  
create function get_maximum_total_less(@threshold money)  
returns table  
as  
return (select pc.product_category_id,  pc.description, max(p.stock * (p.unit_price - p.cost)) as maximum  
        from products p inner join product_categories pc on pc.product_category_id = p.product_category_id  
        where stock > 0  
        group by pc.description, pc.product_category_id having max(p.stock * (p.unit_price - p.cost)) < @threshold  
)
```

