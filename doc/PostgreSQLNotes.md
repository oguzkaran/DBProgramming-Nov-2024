### C ve Sistem Programcıları Derneği
### PostgreSQL ile Veritabanı Programlama
### Eğitmen: Oğuz KARAN

>PostgreSQL free olarak kullanılabilen güçlü bir veritabanı yönetim sistemidir. PostgreSQL 
>**[https://www.enterprisedb.com/downloads/postgres-postgresql-downloads](https://www.oracle.com/tr/java/technologies/downloads/)**  bağlantısından ilgili işletim sistemine göre indirilip yüklenebilmektedir. 

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

###### sqrt Fonk>>siyonu
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
>- **ceil:** Parmetresi ile aldığı gerçek (real) sayıdan büyük en küçük tamsayıya geri döner.

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



