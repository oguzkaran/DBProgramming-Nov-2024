### C ve Sistem Programcıları Derneği
### SQL Server ile Veritabanı Programlama
### Eğitmen: Oğuz KARAN

>SqlServer Microsoft firmasının bir ürünü olup kısıtlı olarak free kullanılabilmektedir. Free ve kısıtlı olarak kullanılabilen sürümüne **Express Edition** denilmektedir. Bu sürümde hem data boyutu kısıtı bulunmaktadır hem de bazı araçlar ve komutlar desteklenmemektir. Görece küçük olan uygulamalarda tercih edilebilir. Sql Server'ın **Developer Edition** sürümü **Enterprise Edition** sürümü tamamen aynı özelliklere sahiptir ancak ticari olarak kullanılamaz. Genel olarak geliştiricilerin geliştirme aşamasında kullandıkları sürümdür. Enterprise edition lisanslı ve ücretlidir. Hatta sistemde cpu ya da çekirdek sayısına göre ücretlendirilmektedir. 
>
>SQL Server tipik olarak Windows işletim sistemine kurulabilmektedir. Ancak SqlServer 2019 sürümünden itibaren `docker` kullanılarak Unix/Linux ve Mac OS sistemlerinde de kullanılabilmektedir. 

##### SQL Server Docker Kurulumu

>Windows işletim sistemi dışında (MacOSX veya Unix/Linux) SQL Server, 2017 sürümünden itibaren Docker ile kullanılabilmektedir. Bundan önceki sürümler için çeşitli sanal makine programlarına Windows işletim sistemi kurularak kullanılabilirdi.

**Anahtar Notlar** Windows işletim sisteminde Docker’ın çalıştırılabilmesi için “Turn on/off Windows Features (Windows Özellikleri aç veya kapat)” bölümündeki HyperV özelliğinin aktif hale getirilmesi gerekir. Fakat bu özelliğin aktif hale getirilmesi başka programları da etkileyebilir. Buna dikkat edilmelidir.

>Docker ile SQL Server kullanımının adımları şöyledir:
>
>1.       Docker Yüklenmesi: Docker lisans gerektirmeden kullanılabilen bir üründür. Notların yazıldığı tarihte aşağıdaki link’den download edilebilir:
>
>[https://hub.docker.com/editions/community/docker-ce-desktop-mac?tab=description_](https://hub.docker.com/editions/community/docker-ce-desktop-mac?tab=description) _(12 Temmuz 2023)
>_https://docs.docker.com/desktop/install/windows-install/_](https://docs.docker.com/desktop/install/windows-install/) _(12 Temmuz 2023)
>
>2.       Docker çalıştırılır.

>3.       Docker default olarak _2GB_ bellek alanı kullanır. Ancak SQL Server en az _3.25GB_ belleğe ihtiyaç duyduğundan Docker bellek alanı artırılmalıdır. Ne kadar artırılacağı çalışılan sisteme (host) göre değişebilir. Bu işlemler için:
>
>Bu işlem ayarlar (settings) menüsünden yapılabilir.

>**Not**: Bu menü programın sürümüne göre değişebilmektedir.

>4.       Bir terminal açılarak (Windows’ da command prompt) SQL Server aşağıdaki gibi download edilir:

  >                              **_docker pull mcr.microsoft.com/mssql/server:2022-latest_**

>5.       Kurulmuş olan docker image için aşağıdaki komut ile container yaratılabilir:

**_docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Csystem-1993" -p 1433:1433 --name mysqlserver -d mcr.microsoft.com/mssql/server:2022-latest_**

M1 işlemcileri ve Unix/Linux ortamları için:

**_docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Csystem-1993" -p 1433:1433 --name mysql -d mcr.microsoft.com/azure-sql-edge_**

>Burada şifre belirlemede zorunluluklar (password policy) için mesajlar alınabilir. Buradaki cümleler docker üzerinde yeni container yaratmak için kullanılır. Ayrıca bu komutla birlikte sqlserver çalışır (up) duruma geçer.

>Container'ların tamamını görüntülemek için

             docker ps -a

>komutu kullanılabilir.

>Bir container'ı silmek için

                docker rm container ismi veya id numarası

>komutu kullanılabilir.

>Çalışan bir container' ı durdurmak için

	`docker stop <container ismi veya id si>`

komutu kullanılabilir.

>Var olan bir container'ı çalıştırmak için

	`docker start <container ismi veya id si>`
	
>komutu kullanılabilir.         

>6.       İstenirse çalışıyor durumda (up) olan container’lar aşağıdaki gibi elde edilebilir.

	`docker ps`

	`docker ps -a`
ile çalışsın çalımasın tüm container'lar elde edilebilir.

>7.       Şu andan itibaren mssql server bağlantısı için herhangi bir client program kullanılabilmektedir. İstenirse komut satırından kullanım için **_sqlcmd_** programı da kullanılabilmektedir:

>SqlServer'ın kurulum dosyaları Windows ortamı için
>
>**[https://www.microsoft.com/en/sql-server/sql-server-downloads](https://www.microsoft.com/en/sql-server/sql-server-downloads)**
>
>bağlantısından indirilebilir. Kurulum yapıldığında Sqlserver bir `Windows Service` olarak çalışır. 
>Windows'a kurulumda SqlServer erişimi için iki seçenek söz konusudur
>1. Windows Authentication: Kurulum sırasında işletim sisteminin SqlServer kullanımına izin verilen kullanıcıları için super user (admin) erişimidir. Uzaktan bu şekilde bağlantı mümkün değildir. Docker container olarak kurulan Sql server için bu şekilde erişim mümkün değildir. 
>2. Sql Server Authentication: Bir kullanıcı (login) ile bağlantı sağlanabilen yöntemdir. Tipik olarak buradaki super use `sa` isimli kullanıcıdır.

Sql Server için tipik kullanılan client program (IDE) **Sql Server Management Studio (SSMS)** programıdır. Bu programın yeni versiyonu **Azure Data Studio** olarak isimlendirilmiştir. Eski versiyona benzeyen özellikle bazı noktaları oldukça farklıdır. SSMS yalnızca Windows sistemlerine kurulabilmektedir. Azure Studio ise Unix/Linux ve Mac OS sistemlerine de kurulabilmektedir. Başka bir işletim sisteminden Sql Server erişimi için genel kullanılan client programlar (Dbeaver, Datagrip vb.) tercih edilebilir. 

>Sql Server'ın dili **T-SQL/Transact-SQL** olarak adlandırılır. 

>Sql Server'da iki türlü yorum satırı (comment line) bulunur:

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

>Buradaki cümleler herhangi bir koşul verilmediği için tablolardaki tüm veriler için güncelleme yapmak anlamına gelir. SQL'da koşul ifadelerini oluşturmak için **where cümleciği (where clause)** kullanılabilir. where cümleciği bir koşul ifadesi (predicate) ile birlikte kullanılır:

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
**Anahtar Notlar:** Sql Server'da takma adlar standart olarak  `"` içerisinde yazılamazlar.

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
where f.date_time = '2024-08-25 00:00:00';
```

>Aynı sorgu self join ile aşağıdaki gibi de yapılabilir:

```sql
select f.flight_id, ad.name departure, c.name, co.name, aa.name arrival, ca.name, coa.name  
from flights f, airports ad, airports aa, cities c, cities ca, countries co, countries coa  
where  
f.departure_airport_code = ad.code and f.arrival_airport_code = aa.code  
and ad.city_id = c.city_id and aa.city_id = ca.city_id and c.country_id = co.country_id  
and ca.country_id = coa.country_id  
and f.date_time = '2024-08-25 00:00:00';
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
where c.city_id = '326' and f.date_time = '2024-08-25 00:00:00';
```

>T-SQL'de kodların yazıldığı dosyanın tamamı bir akış belirtir. Sql Server bir output oluşturmak için çeşitli yöntemler olsa da tipik olarak select cümlesi ile de yapılabilmektedir. T-SQL'de  değişken bildirimi **declare** anahtar sözcüğü kullanılarak yapılır. Değişken isimleri `@` atomu ile başlatılmalıdır. İsimden sonra tür bilgisi yazılır. İstenirse bildirim noktasında değer verilebilir (initialization). Değişkenin değerini değiştirebilmek için set anahtar sözcüğü kullanılır.

```sql
declare @sensor_name nvarchar(256)
declare @company_name nvarchar(256) = 'CSD'

set @sensor_name = 'Rain sensor'

select @company_name, @sensor_name
```


##### Fonksiyonlar

>Programlama dillerinde akış içerisinde gerektiğinde çalıştırılabilen alt programlar yazılabilir. T-SQL'de bir alt program çeşitli biçimlerde yazılabilir. Fonksiyon da alt program oluşturmanın bir yöntemidir. Fonksiyon `create function` cümlesi ile yazılabilir. Bir fonksiyonun ismi, hangi alt programın çalıştırılacağını (call/invoke) belirtmek için gereklidir. Bir fonksiyonun çağrılırken aldığı ve fonksiyon içerisinde kullanılabilen değişkenlerine **parametre değişkenleri (parameter variables)** denir. Fonksiyon çağrısı bittiğinde çağrılan noktaya bir değer ile geri dönmesine **geri dönüş değeri (return value)** denir. Bir fonksiyonun geri dönüş değeri bilgisi aslında fonksiyonun döndüğü değere ilişkin türü belirtir. Fonksiyonun geri dönüş değeri bu değer fonksiyon içerisinde **return deyimi (return statement)** ile oluşturulur. return deyimi bir ifade ile kullanıldığında o ifadenin değerine geri dönülmüş olur. Fonksiyon çağrılırken parametre değişkenleri için geçilen ifadelere **argümanlar (arguments)** denir. T-SQL'de pek çok hazır fonksiyon (built-in) bulunmaktadır. Veritabanı programcısı yapacağı bir iş için hazır fonksiyonlar varsa öncelikle o fonksiyonları tercih etmelidir. Fonksiyonların yazılması basit olsa bile hazır olanları kullanması verimi ve performansı genel olarak artırır. 
>
>T-SQL'de fonksiyonlar 4 çeşittir:
>1. Scaler-valued functions
>2. Table-valued functions
>3. Aggregate functions
>4. System functions

##### Scale-Valued Functions

>En temel fonksiyonlardır. Bu fonksiyonlar tipik olarak scaler bir değere geri dönerler. 

**Anahtar Notlar:** Sql Server'da `şema (schema)` bir veritabanı elemanı vardır. Programcı herhangi şema yaratmazsa `dbo` isimli şema otomatik yaratılır. Scaler-valued bir fonksiyon özel bazı durumlar dışında şema ismi nokta operatörü kullanılarak çağrılır

>Aşağıdaki örnek amaçlı yazılmış fonksiyonu inceleyiniz:

```sql
create function add_two_ints(@a int, @b int)
returns int
as
begin
	return @a + @b
end
  
```

```sql
declare @a int = 10
declare @b int = 20

select dbo.add_two_ints(@a, @b)
```

**Anahtar Notlar:** T-SQL'de function overloading yapılamaz. Bu durumda aynı veritabanı içerisindeki tüm fonksiyon unique isimleri olmalıdır.

##### Matematiksel İşlem Yapan Fonksiyonlar

>Sql Server'da matematiksel işlemler yapan pek çok hazır fonksiyon bulunmaktadır. Burada temel olanlar ve genel olarak kullanılanlar ele alınacaktır. 

###### sqrt Fonksiyonu
>Bu fonksiyon parametresi ile aldığı sayının kareköküne geri döner. Tipik olarak tüm nümerik türler için işlem yapabilmektedir. Şüphesiz tam sayılar için yine `double precison` türüne geri döner. Fonksiyona negatif bir argüman geçildiğince **çalışma zamanı hatası runtime error** oluşur.

```sql
declare @a real = 2.3
declare @b real = -5.4

select sqrt(@a), sqrt(@b)
```

###### power Fonksiyonu
Bu fonksiyon $$a^b$$ işlemini yapar.

```sql
declare @a real = 2.3
declare @b real = -5.4

select power(@a, @b)
```

>**Sınıf Çalışması:** mapdb isimli bir veritabanı oluşturunuz. Bu veritabanında nokta çiftlerinin koordinat bilgilerini tutan `coordinate_pairs` isimli bir tablo yaratınız. Tablo içerisindeki kaydın otomatik artan id bilgisi ile x1, y1, x2, y2 nokta değerleri `double preccision` olarak tutulacaktır. Buna göre Euclid uzaklığı bilgisini döndüren bir fonksiyon yazınız ve tüm kayıtlar için noktalarla birlikte aralarındaki uzaklığı da getiren sorguyu yazınız
>
>**Açıklamalar:** 
>- Euclid uzaklığı formulü şu şekildedir:
>$$d = \sqrt{(x1 - x2)^2 + (y1 - y2)^2}$$

```sql
create database mapdb;

use mapdb

create table coordinate_pairs (  
    coordinate_pair_id int primary key identity(1, 1),  
    x1 real not null,  
    y1 real not null,  
    x2 real not null,  
    y2 real not null  
);

create function euclidean_distance(@x1 real, @y1 real, @x2 real, @y2 real)
returns real
as
begin
	return sqrt(power(@x1 - @x2, 2) + power(@y1 - @y2, 2))
end

select x1, y1, x2, y2, dbo.euclidean_distance(x1, y1, x2, y2) as distance from coordinate_pairs;
```

###### rand Fonksiyonu

>Bu fonksiyon `[0, 1)` aralığında rassal olarak belirlenmiş gerçek sayıya geri döner

```sql
select rand()
```

>random fonksiyonu kullanılarak aşağıdaki gibi belirli aralıkta rassal sayılar üretebilen fonksiyonlar overload edilebilir. T-SQL'de fonksiyon içerisinde yan etkisi (side effect) olan bir işlem yapılamaz. rand fonksiyonu global düzeyde bir takım değerleri değiştirdiğinden yan etkisi olan bir fonksiyondur. Dolayısıyla bir fonksiyon içerisinde çağrılamaz. Aşağıdaki fonksiyon T-SQL'de create edilemez.

```sql
create function random_int_value(@origin int, @bound int)
returns int
as
begin
	return floor(rand() * (@bound - @origin) + @origin)
end
```

###### Yuvarlama İşlemi Yapan Önemli Fonksiyonlar

>Tam sayıya yuvarlama işlemi yapan bazı önemli fonksiyonlar şunlardır:
>- **floor:** Parmetresi ile aldığı gerçek (real) sayıdan küçük en büyük tamsayıya geri döner.
>- **round:** Bilimsel yuvarlama yapar. Sayının noktadan sonraki kısmı `>= 0.5` ise bir üst tamsayıya, `< 0.5` ise noktadan sonraki atılmış tamsayıya geri döner. Bu fonksiyonun parametreleri ileride ele alınacaktır.
>- **ceiling:** Parametresi ile aldığı gerçek (real) sayıdan büyük en küçük tamsayıya geri döner.

```sql
declare @val real = 3.4

select floor(@val), ceiling(@val)
set @val = -3.4
select floor(@val), ceiling(@val)
```

##### Yazılarla İşlem Yapan Fonksiyonlar

>Neredeyse tüm uygulamalarda az ya da çok yazılarla işlem yapılır. Programlamada yazı kavramına **string** denir. SqlServer'da yazılar işlem yapan pek çok yararlı fonksiyon vardır (string function). Programcı yazı ile ilgili bir işlem için varsa buradaki fonksiyonları kullanmalı, yoksa yine buradaki fonksiyonları da kullanarak fonksiyonlarını yazmalıdır. Yazılar Sql Server'da **immutable**'dır. Yani yazı üzerinde işlem bir fonksiyon yazının orjinalini değiştiremez. Değişiklik yapan bir fonksiyon değişiklik yapılmış yeni yazıya geri döner. 

###### replicate Fonksiyonu

>Bu fonksiyon yazıyı çoğaltmak için kullanılır

```sql
declare @str nvarchar(250) = 'ankara'
declare @str_replicate nvarchar(250)

set @str_replicate =  replicate(@str, 5)

select @str, @str_replicate
```

###### len Fonksiyonu

>Parametresi ile aldığı yazının  karakter sayısına (uzunluğu) geri döner

```sql
declare @str nvarchar(250) = 'ankara'

select len(@str)
```
###### Yazıların Birleştirilmesi (concatenation)

>Yazıların birleştirilmesi işlemi concat fonksiyonu ile yapılabilir. Yazı birleştirmesi işlemi çok fazla karşılaşılan bir işlem olduğundan `+` operatörü ile de yazı birleştirmesi yapılabilir

```sql
declare @first_name nvarchar(250) = 'Oğuz'
declare @last_name nvarchar(250) = 'Karan'
declare @full_name nvarchar(500)

set @full_name = concat(@first_name, ' ', @last_name)
select @full_name
```

```sql
declare @first_name nvarchar(250) = 'Oğuz'
declare @last_name nvarchar(250) = 'Karan'
declare @full_name nvarchar(500)

set @full_name = @first_name + ' ' + @last_name
select @full_name
```

>concat_ws fonksiyonu ile verilen bir ayraç (delimiter/seperator) ile birleştirme yapılır. Bu fonksiyon Sql Server 2017 ile eklenmiştir

```sql
declare @first_name nvarchar(250) = 'Oğuz'
declare @last_name nvarchar(250) = 'Karan'
declare @full_name nvarchar(500)

set @full_name = concat_ws('-', @first_name, @last_name)
select @full_name
```

###### left ve right fonksiyonları 

>Bu fonksiyonlar sırasıyla soldan ve sağdan istenilen sayıda karakterin yazı olarak elde edilmesini sağlar

```sql
declare @str nvarchar(250) = 'Ankara'

select left(@str, 4), right(@str, 4)

```

>**Sınıf Çalışması:** Parametresi ile aldığı bir yazının yine parametresi ile aldığı ilk count karakterini almak koşuluyla diğer karakterlerini üçüncü parametresi ile aldığı karakter ile değiştiren `hide_text_right` fonksiyonu yazınız. 

```sql
create function hide_text_right(@str nvarchar(max), @count int, @ch char(1))
returns nvarchar(max)
begin
	return left(@str, @count) + replicate(@ch, len(@str) - @count)
end
```

```sql
select 
dbo.hide_text_right(p.citizen_id, 2, '*') as citizen_id,
p.first_name + ' ' + p.last_name as fullname,
dbo.hide_text_right(email, 3, '*') as email,
f.date_time, f.departure_airport_code, f.arrival_airport_code,
pf.price
from 
flights f inner join passengers_to_flights pf on f.flight_id = pf.flight_id
inner join passengers p on pf.passenger_id = p.citizen_id
```

###### str Fonksiyonu 

>Bu fonksiyon nümerik bir değerin yazısal karşılığını döndürür

```sql
select 
dbo.hide_text_right(p.citizen_id, 2, '*') as citizen_id,
p.first_name + ' ' + p.last_name as fullname,
dbo.hide_text_right(email, 3, '*') as email,
f.date_time, f.departure_airport_code, f.arrival_airport_code,
dbo.hide_text_right(str(pf.price, 4), 1, 'X') as price
from 
flights f inner join passengers_to_flights pf on f.flight_id = pf.flight_id
inner join passengers p on pf.passenger_id = p.citizen_id
```

###### substring Fonksiyonu

>Bu fonksiyon yazının belirli bir parçasını elde etmek için kullanılır

```sql
declare @str nvarchar(200) = 'ankara'

select substring(@str, 3, len(@str)), substring(@str, 3, 3)
```

###### rtrim, ltrim ve trim Fonksiyonları

>Bu fonksiyonlar sırasıyla sağdan, soldan ve sağdan-soldan boşluk karakterini atarlar. trim fonksiyonu SqlServer 2017 ile eklenmiştir. İkinci parametreye argüman geçilirse geçilen argümana ilişkin karakterleri atar

```sql
declare @str nvarchar(200) = '       C ve Sistem Programcıları Derneği                   '

select '(' + rtrim(@str) + ')', '(' + ltrim(@str) + ')', '(' + trim(@str) + ')'
```

>SqlServer 2017 öncesinde trim işlemi için aşağıdaki fonksiyon yazılabilir

```sql
create function csd_trim(@str nvarchar(max))
returns nvarchar(max)
as
begin
	return ltrim(rtrim(@str))
end
```

```sql
declare @str nvarchar(200) = '       C ve Sistem Programcıları Derneği                   '

select '(' + rtrim(@str) + ')', '(' + ltrim(@str) + ')', '(' + dbo.csd_trim(@str) + ')'
```

###### upper ve lower fonksiyonları

>Bu fonksiyonları yazının tüm harflerini sırasıyla büyük harfe ve küçük harfe çevirir. Bu fonksiyonlar çalışılan dile göre çevirme işlemlerini yaparlar. Bu durumun detayları ileride ele alınacaktır

```sql
create function capitalize(@str nvarchar(max))
returns nvarchar(max)
as
begin
	return upper(left(@str, 1)) + lower(substring(@str, 2, len(@str)))
end
```

```sql
select 
dbo.hide_text_right(p.citizen_id, 2, '*') as citizen_id,
dbo.capitalize(p.first_name) + ' ' + dbo.capitalize(p.last_name) as fullname,
dbo.hide_text_right(email, 3, '*') as email,
f.date_time, f.departure_airport_code, f.arrival_airport_code,
dbo.hide_text_right(str(pf.price, 4), 1, 'X') as price
from 
flights f inner join passengers_to_flights pf on f.flight_id = pf.flight_id
inner join passengers p on pf.passenger_id = p.citizen_id
```

>Sql Server'daki diğer string fonksiyonları konular içerisinde kullanılacaktır.

>**Sınıf Çalışması:** Aşağıda açıklanan **csd_initcap** isimli fonksiyonu yazınız
>
>**Açıklamalar:**
>- Fonksiyon parametresi ile aldığı bir yazıyı ilk karakteri büyük geri kalan karakterleri küçük olacak şekilde yapacaktır. Örneğin `bUGÜN HAVA ÇOK GÜZEL` yazısı `Bugün hava çok güzel` biçiminde elde edilecektir.
>- Fonksiyondan elde edilen yazıda alfabetik olmayan karakterler aynen korunacaktır



>Bir sorgudan elde edilen tek bir bileşen, aynı zamanda tek bir kayıt da içeriyorsa elde edilen değer bir değişkene aşağıdaki gibi verilebilir:

```sql
declare @country_id int = (select country_id from countries where code = 'TR')  
  
select @country_id
```

>Önceliklendirme açısından parantez zorunludur. Aksi durumda error oluşur.

##### Tablo Döndüren Fonksiyonlar (Table-Valued Functions)

>Genel olarak bir sorgunun sonucuna yani sorguya ilişkin alanlara (projection) geri dönen fonksiyonlardır. Bu fonksiyonların geri dönüş değerleri için doğrudan `returns table` yazılır. Sorgudan elde edilen projection'a ilişkin alanlara geri dönülür.

```sql
create function get_airport_and_country_name_by_departure_airport_code(@departure_airport_code nchar (10))  
returns table  
as  
    return (select aa.name as arrival_airport_name, coa.name country_name
            from  
                flights f inner join airports ad on f.departure_airport_code = ad.code  
                          inner join airports aa on f.arrival_airport_code = aa.code  
                          inner join cities ca on aa.city_id = ca.city_id  
                          inner join countries coa on coa.country_id = ca.country_id  
            where f.departure_airport_code = @departure_airport_code)

```

>Burada projection'a alanlar aynı isme sahip olursa geri dönüş değeri olarak herhangi bir isimlendirme yapılmadığında alias olarak sorguda verilmelidir.

```sql
create function get_flight_info_by_flight_id(@flight_id bigint)  
returns table  
return (select f.flight_id, f.date_time, ad.name departure_airport, cd.name as departure_city, aa.name arrival_airport, ca.name as arrival_city  
        from flights f inner join airports ad on f.departure_airport_code = ad.code  
                       inner join airports aa on f.arrival_airport_code = aa.code  
                       inner join cities cd on ad.city_id = cd.city_id  
                       inner join cities ca on aa.city_id = ca.city_id  
        where f.flight_id = @flight_id)
```

##### if Deyimi

>if deyimi hemen hemen tüm programlama dillerinde ve akış içerisinde koşula bağlı olarak akışın yönlendirilmesini sağlayan önemli bir kontrol deyimidir. if deyiminin genel biçimi şu şekildedir:

```sql
if <koşul ifadesi>
	<deyim>
else
	<deyim>
```

>Aşağıdaki demo örneği inceleyiniz

```sql
declare @origin int = -10  
declare @bound int = 11  
declare @val int = floor(rand() * (@bound - @origin + 1) + @origin)  
  
select @val  
  
if @val > 0  
    select 'Positive'  
else  
    select 'Not positive'
```

>Aşağıdaki demo örnekte if deyiminin else kısmında bir if deyimi yazılmıştır. Bu şekilde yazılması başka ayrık kontroller de söz konusu olduğunda karmaşık olabilmektedir


```sql
declare @origin int = -10  
declare @bound int = 11  
declare @val int = floor(rand() * (@bound - @origin + 1) + @origin)  
  
select @val  
  
if @val > 0  
    select 'Positive'  
else  
    if @val = 0  
        select 'Zero'  
    else  
        select 'Not positive'
```

>Yukarıdaki örnek aşağıdaki gibi daha okunabilir/algılamnabilir olarak yazılabilir

```sql
declare @origin int = -10  
declare @bound int = 11  
declare @val int = floor(rand() * (@bound - @origin + 1) + @origin)  
  
select @val  
  
if @val > 0  
    select 'Positive'  
else if @val = 0  
    select 'Zero'  
else  
    select 'Not positive'
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

**Çözüm:**

```sql
create database dpn24_companydb  
  
use dpn24_companydb  
  
create table employees (  
    citizen_id char(11) primary key,  
    first_name nvarchar(250) not null,  
    middle_name nvarchar(250),  
    last_name nvarchar(250) not null,  
    marital_status int  
)  
  
create function get_marital_status_text_tr(@status int)  
returns nvarchar(20)  
as  begin  
    declare @status_str nvarchar(20)  
   if @status = 0  
         set @status_str =  'Evli'  
    else if @status = 1  
        set @status_str =  'Bekar'  
    else if @status = 2  
        set @status_str =  N'Boşanmış'  
    else  
        set @status_str =  'Belirsiz'  
  
    return @status_str  
end  
  
create function get_full_text(@first nvarchar(250), @second nvarchar(250), @third nvarchar(max))  
returns nvarchar(max)  
as  
begin  
    declare @full_text nvarchar(max) = '';  
  
    if @first is not null  
        set @full_text = @first;  
  
    if @second is not null  
    begin        
		if @full_text <> ''  
            set @full_text = @full_text + ' ';  
        set @full_text = @full_text + @second;  
    end  
  
    if @third is not null  
    begin        
		if @full_text <> ''  
            set @full_text = @full_text + ' ';  
        set @full_text = @full_text + @third;  
    end  
  
    return @full_text;  
end  
  
create function get_employee_by_citizen_id(@citizen_id char(11))  
returns table  
as  
    return (select dbo.get_full_text(first_name, middle_name, last_name) as fullname,  
                   dbo.get_marital_status_text_tr(marital_status) as marital_status  
                            from employees where citizen_id = @citizen_id)  
    
-- Simplw test codes  
select * from employees;  
select * from get_employee_by_citizen_id('f048425c-58e5-4694-94ec-7ae8dc4fbeae');  
select * from get_employee_by_citizen_id('f2a22590-a77a-4f23-8b3f-6ec665371369');
```

##### case İfadesel Deyimi

>**case ifadesel deyimi (case expression)** tipik olarak hem deyim hem de ifade biçiminde kullanılabilmektedir. 

```sql
declare @origin int = -10  
declare @bound int = 11  
declare @val int = floor(rand() * (@bound - @origin + 1) + @origin)  
declare @message nvarchar(100)  
select @val  
  
set @message = case  
        when @val > 0 then 'Positive'  
        when @val = 0 then 'Zero'  
        else 'Negative'  
    end  
  
select @message
```

>case expression eşitlik karşılaştırmasında da kullanılabilir

```sql
declare @origin int = -10  
declare @bound int = 11  
declare @val int = floor(rand() * (@bound - @origin + 1) + @origin)  
declare @message nvarchar(100)  
select @val  
  
set @message = case @val % 2  
        when 0 then 'Even'  
        else 'Odd'  
    end  
  
select @message
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

**Çözüm:**

```sql
create database dpn24_companydb  
  
use dpn24_companydb  
  
create table employees (  
    citizen_id char(11) primary key,  
    first_name nvarchar(250) not null,  
    middle_name nvarchar(250),  
    last_name nvarchar(250) not null,  
    marital_status int  
)  
  
create function get_marital_status_text_tr(@status int)  
    returns nvarchar(20)  
as  
begin  
    return case @status  
               when 0 then 'Evli'  
               when 1 then 'Bekar'  
               when 2 then N'Boşanmış'  
               else 'Belirsiz'  
        end  
end  
  
create function get_full_text(@first nvarchar(250), @second nvarchar(250), @third nvarchar(250))  
    returns nvarchar(max)  
as  
begin  
    declare @full_text varchar = '';  
  
    if @first is not null  
        set @full_text = @first;  
  
    if @second is not null  
        begin        if @full_text <> ''  
            set @full_text = @full_text + ' ';  
        set @full_text = @full_text + @second;  
        end  
  
    if @third is not null  
        begin        if @full_text <> ''  
            set @full_text = @full_text + ' ';  
        set @full_text = @full_text + @third;  
        end  
  
    return @full_text;  
end  
  
create function get_employee_by_citizen_id(@citizen_id char(11))  
    returns table  
        as        return (select dbo.get_full_text(first_name, middle_name, last_name) as fullname,  
                       dbo.get_marital_status_text_tr(marital_status) as marital_status  
                from employees where citizen_id = @citizen_id)  
  
-- Simplw test codes  
select * from employees;  
select * from get_employee_by_citizen_id('f048425c-58e5-4694-94ec-7ae8dc4fbeae');  
select * from get_employee_by_citizen_id('f2a22590-a77a-4f23-8b3f-6ec665371369');
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
create database dpn24_tennisclubdb  
  
use dpn24_tennisclubdb  
  
create table court_status (  
    court_status_id int primary key identity(1, 1),  
    description nvarchar(50) not null  
)  
  
insert into court_status (description) values ('Available'), ('Not Available'), ('Reserved')  
  
create table court_types (  
    court_type_id int primary key identity(1, 1),  
    description nvarchar(50) not null  
)  
  
insert into court_types (description) values ('Open'), ('Closed'), ('Open or Closed')  
  
create table courts (  
    court_id int primary key identity(1, 1),  
    name nvarchar(250) not null,  
    court_status_id int foreign key references court_status(court_status_id),  
    court_type_id int foreign key references court_types(court_type_id)  
)
  
create function get_court_details()  
returns table  
as  
return (select c.name court_name, cs.description court_status, ct.description court_type  
        from  
        court_status cs inner join courts c on cs.court_status_id = c.court_status_id  
        inner join court_types ct on c.court_type_id = ct.court_type_id)  
  
  
create function get_court_details_tr()  
returns table  
as  
return (select c.name court_name,  
                case c.court_status_id  
                    when 1 then 'Uygun'  
                    when 2 then N'Uygun değil'  
                    else 'Rezerve'  
                end court_status,  
                case c.court_type_id  
                   when 1 then N'Açık'  
                   when 2 then N'Kapalı'  
                   else N'Açık veya Kapalı'  
                 end court_type  
        from  
        courts c)  
  
  
-- Simple test codes  
select * from get_court_details()  
select * from get_court_details_tr()
```

##### Tarih-Zaman Fonksiyonları

>Tarih-zaman işlemleri neredeyse her uygulamada kullanılmaktadır. SqlServer'da tarih, zaman ve tarih-zaman türleri birbirinden ayrıdır. Bunlar sırasıyla **date, time** ve **datetime** biçimindedir. Ayrıca **datetime2, smalldatetime, datetimeoffset** türleri de bulunmaktadır.

###### getdate, sysdatetime, getutcdate ve sysutcdatetime Fonksiyonları

>`getdate ve sysdatetime` fonksiyonları o an çalışılan sistemin tarih-zaman bilgisini elde etmek için kullanılırlar. `sysdatetime` datetime2 türünden değer döndürdüğü için nanosaniyeler mertebesinde `getdate` fonksiyonuna göre daha hassastır.  `getutcdate ve sysutcdatetime` fonksiyonları o an çalışılan sistemin tarih-zaman bilgisini `UTC (Universal Time Clock)` olarak verirler. `sysutcdatetime` datetime2 türünden değer döndürdüğü için nanosaniyeler mertebesinde `getutcdate` fonksiyonuna göre daha hassastır. 

```sql
select getdate(), sysdatetime(), getutcdate(), sysutcdatetime()
```

>Bu fonksiyonlar çeşitli domain'lerde ilgili tarih-zaman alan bilgilerinin default değerleri için de kullanılabilir.

```sql
create table airports (  
    code char(10) primary key,  
    city_id int foreign key references cities(city_id) not null,  
    name nvarchar(300) not null,  
    open_date date default(getdate()) not null  
    -- ...  
);  
```

###### datepart Fonksiyonu

>Bu fonksiyon ile bir tarih, zaman ya da tarih-zamana ilişkin bileşenler elde edilebilir. Burada `weekday`, `1 Pazar, 2 Pazartesi, ..., 7 Cumartesi` olacak şekildedir.
>
```sql
declare @now datetime = getdate()  
  
select datepart(day, @now), datepart(month, @now), datepart(year, @now),  
       datepart(hour, @now), datepart(minute, @now), datepart(second, @now),  
       datepart(dayofyear, @now),datepart(weekday, @now)
   
```

Ayrıca tarih zamana ilişkin bazı parçaları daha kolay elde etmek için bazı fonksiyonlar vardır. Örneğin `month` ve `year`fonksiyonları ilgili tarih zamana ilişkin ay ve yıl bilgisini elde etmek için kullanılabilir:

```sql
declare @now datetime = getdate()  
  
select month(@now), year(@now), datepart(month, @now), datepart(year, @now)
```

###### datediff Fonksiyonu

>Bu fonksiyon iki tarih-zaman arasındaki farkı istenilen birimde döndürür. Birimler tamsayı biçimindedir. 


```sql
declare @big_earthquake datetime = '2023-02-06 04:00:00'  
declare @now datetime = sysdatetime()  
declare @years real  
  
set @years = datediff(day, @big_earthquake, @now) / 365.0  
  
select @years
```

###### datefromparts ve datetimefromparts Fonksiyonları

>Bu fonksiyon ile tarih-zamana ilişlkin bileşenlerden tarih-zaman bilgisi oluşuturulur

```sql
declare @birth_date date = datefromparts(1976, 9, 10)  
  
select datediff(day, @birth_date, getdate()) / 365.0
```

```sql
declare @birth_date date = datetimefromparts(1976, 9, 10, 14, 0, 0, 0)  
  
select datediff(day, @birth_date, getdate()) / 365.0
```

###### eomonth Fonksiyonu

>Bu fonksiyon ilgili tarihe ilişkin ayın son gününün tarih bilgisine geri döner

```sql
select eomonth(getdate()), eomonth(datefromparts(2024, 2, 1)), eomonth(datefromparts(2025, 2, 1))
```


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
>**Çözüm:**

```sql
  
create table students (  
    citizen_id    char(40) primary key,  
    first_name    nvarchar(100) not null,  
    middle_name   nvarchar(100),  
    last_name     nvarchar(100) not null,  
    birth_date    date          not null,  
    register_date date default (getdate())  
)  
  
drop function get_birth_date_message_tr  
  
create function get_birth_date_message_tr(@birth_date date)  
returns nvarchar(256)  
as  
begin  
    declare @today date = getdate()  
    declare @birth_day date = datefromparts(datepart(year, @today), datepart(month, @birth_date), datepart(day, @birth_date))  
    declare @age real = datediff(day, @birth_date, @today) / 365.0  
    declare @message nvarchar(256)  
  
    set @message = case  
        when @today > @birth_day then N'Geçmiş doğum gününüz kutlu olsun.'  
        when @today < @birth_day then N'Doğum gününüz şimdiden kutlu olsun.'  
        else N'Doğum gününüz kutlu olsun.'  
    end  
  
    return @message + cast (@age as nvarchar(10))  
end  
  

create function get_students_by_register_month_and_year(@month int, @year int)  
returns table  
as  
return (  
    select * from students where datepart(month, register_date) = @month and datepart(year, register_date) = @year  
)
```

SSSSSSSSSSSSSSSSSSS

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

##### Stored Procedures

>Anımsanacağı gibi Sql Server'da fonksiyonlar içersinde yan-etkiye (side effect) sahip yani değiştirme eğiliminde olan işlemler yapılamaz. Örneğin insert, delete ve update cümleleri fonksiyonlar içerisinde kullanılamaz. Yine örneğin `rand` gibi global bazı değişkenlerin değerlerini değiştiren fonksiyonlar da yine bir fonksiyon içerisinde çağrılamazlar. Böylesi işlemler için ismine **stored procedure (SP)** denilen alt programlar kullanılır. SP'ler yapı olarak fonksiyonlara benzese de kendine has özellikleri bulunan alt programlardır. Bu özellikler konu içerisinde ele alınacaktır. 
>
>Bir SP **create procedure** cümlesi ile yaratılır. SP'nin geri dönüş değeri kavramı yoktur. Sql Server'da bir convention olarak SP isimleri `sp_` ile başlatılır. Sql Server'da hazır olarak (built-in) bulunan SP'ler için bu convention'a uyulmuştur. Bir SP **exec** veya **execute** cümlelerinden biri ile çalıştırılır. exec yapıldığında argümanlar parametre değişken isimleri ile verilebilirler. Bu şekilde argüman geçilmesine **isimli argümanlar (named arguments)** da denilmektedir. İsimli argüman kullanılacaksa tüm argümanlar isimli olarak verilmelidir. İsimli argüman kullanımında argümanların sırasının önemi yoktur. İsimli argüman verilmeyecekse bu durumda argüman sırası şüphesiz önemlidir. 


>Aşağıdaki tabloları ve SP'leri inceleyiniz

```sql
create database dpn24_shoppingdb  
  
use dpn24_shoppingdb  
  
create table clients (  
    username nvarchar(100) primary key,  
    email nvarchar(300) unique not null,  
    first_name nvarchar(100) not null,  
    middle_name nvarchar(100),  
    last_name nvarchar(100) not null,  
    birth_date date not null,  
    register_date date default(getdate()) not null  
)  
  
create table client_logins (  
    client_login_id bigint primary key identity(1, 1),  
    username nvarchar(100) foreign key references clients(username) not null,  
    date_time datetime default(getdate()) not null,  
    success bit not null  
)  
  
create procedure sp_insert_client(@username nvarchar(100), @email nvarchar(300), @first_name nvarchar(100), @middle_name nvarchar(100), @last_name nvarchar(100), @birth_date date)  
as  
begin  
    insert into clients (username, email, first_name, middle_name, last_name, birth_date) values (@username, @email, @first_name, @middle_name, @last_name, @birth_date)  
end  
  
exec sp_insert_client 'oguzkaran', 'oguzkaran@csystem.org', N'Oğuz', null, 'Karan', '1976-09-10'  
  
execute sp_insert_client @birth_date ='1989-07-29', @email = 'yasar@gulec.com', @first_name = N'Yaşar', @middle_name = N'Uğur', @last_name = N'Güleç', @username = 'yasar'
```


>**Sınıf Çalışması:** Aşağıdaki tabloları hazırlayınız ve ilgili soruları yanıtlayınız
>**Tablolar:**
>- **people**
>	- citizen_id char(11)
>	- first_name nvarchar(300)
>	- last_name nvarchar(300)
>	- birth_date date
>- **people_younger**
>	- citizen_id char(11)
>	- first_name nvarchar(300)
>	- last_name nvarchar(300)
>	- birth_date date
>- **people_older**
>	- citizen_id char(11)
>	- first_name nvarchar(300)
>	- last_name nvarchar(300)
>	- birth_date date
>**Sorular:**
>- Parametresi ile aldığı person bilgilerine göre yaşı 18 ile 65 arasında olanları `people` tablosuna, yaşı 18'den küçük olanları `people_younger` tablosuna, yaşı 65'den büyük olanlar `people_older` tablosuna ekleyen `sp_insert_person` isimli SP'yi yazınız.

>**Çözüm:**

```sql
use testdb;  
  
create table people (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
  
create table people_younger (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
create table people_older (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
  
create procedure sp_insert_person(@citizen_id char(11), @first_name nvarchar(300), @last_name nvarchar(300), @birth_date date)  
as  
begin  
    declare @age real = datediff(day, @birth_date, getdate()) / 365.0  
  
    if @age < 18  
        insert into people_younger values(@citizen_id, @first_name, @last_name, @birth_date)  
    else if @age < 65  
        insert into people values(@citizen_id, @first_name, @last_name, @birth_date)  
    else  
        insert into people_older values(@citizen_id, @first_name, @last_name, @birth_date)  
 end
```


##### Aggregate Fonksiyonlar

>Bu fonksiyonlar sorgu içerisindeki bilgilere göre kümülatif biçimde işlem yaparak sonuç elde etmek için kullanılırlar. Bu fonksiyonlar özellikle gruplamada da çok sık kullanılmaktadır. Gruplama işlemi ve `group by` operatörü ileride ele alınacaktır.

>Aşağıdaki aggregate fonksiyonlar `dpn24_veterinerian_hospital_db` veritabanının `1.0.0` versiyonu ile örneklenmiştir.

###### avg Fonksiyonu 

>Bu fonksiyon parametresi ile aldığı alana ilişkin değerlerin ortalamasını hesaplar

>Aşağıdaki örnekte hayvanların yaşlarının ortalamasını hesaplayan bir fonksiyon yazılmıştır
>
```sql
create function get_age_average(@reference_date date)  
returns real  
as  
begin  
    declare @average real = (select avg(datediff(day, birth_date, @reference_date) / 365.0)  from animals)  
  
    return @average  
end
```

###### sum Fonksiyonu
>Bu fonksiyon parametresi ile aldığı alana ilişkin değerlerin ı hesaplar.

>Aşağıdaki örnekte hayvanların yaşları toplamını hesaplayan bir fonksiyon yazılmıştır
```sql
create function get_age_sum(@reference_date date)  
returns real  
as  
begin  
    declare @sum real = (select sum(datediff(day, birth_date, @reference_date) / 365.0)  from animals)  
  
    return @sum  
end
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
create function get_min_age(@reference_date date)  
    returns real  
as  
begin  
    declare @sum real = (select min(datediff(day, birth_date, @reference_date) / 365.0)  from animals)  
  
    return @sum  
end  
  
create function get_max_age(@reference_date date)  
    returns real  
as  
begin  
    declare @sum real = (select max(datediff(day, birth_date, @reference_date) / 365.0)  from animals)  
  
    return @sum  
end
```

>Aşağıdaki örnekte en büyük yaş ile en küçük yaşın ortalaması elde edilen sorgu yazılmıştır

```sql
select (max(datediff(day, birth_date, getdate()) / 365.0) + min(datediff(day, birth_date, getdate()) / 365.0)) / 2 from animals
```


>**Sınıf Çalışması:** `dpn24_veterinerian_hospital_db` veritabanının `1.0.0` versiyonunda diploma_no'su bir veteriner için ödenen ücretlerin ortalamasını döndüren `get_prices_avg_by_diploma_no` fonksiyonunu yazınız

**Çözüm:**
```sql
create function get_prices_avg_by_diploma_no(@diploma_no bigint)  
returns real  
as  
begin  
    declare @avg real = (select avg(vtap.price) from  
                        veterinarian_to_animals vta inner join veterinarian_to_animal_prices vtap on vta.veterinarian_to_animal_id = vtap.veterinarian_to_animal_id  
                        where vta.diploma_no = @diploma_no)  
    return @avg  
end
```

##### @@identity Değişkeni

>Sql Server'da bir grup built-in global düzeyde erişilebilen değişken bulunmaktadır. Bu değişkenlerin isimleri genel olarak `@@` ile başlatılır. **@@identity** değişkeni kullanıldığı noktada en son otomatik olarak üretilen değeri verir. Şüphesiz en son üretilen değer ile bu değişkenin kullanıldığı arada başka bir değerin üretilmesinin araya girmemesi için bu işlemin atomik olarak yani kesilmeden yapılması gerekir. Bunun `transaction safe` bir akış oluşturulmalıdır. Transaction kavramı transaction safe akış oluşturulması ileride ele alınacaktır. 

```sql
create table sensors (  
    sensor_id int primary key identity(1, 1),  
    name nvarchar(250) not null,  
    host nvarchar(100) not null  
)  
  
create table ports (  
    port_id bigint primary key identity(1, 1),  
    sensor_id int foreign key references sensors(sensor_id) not null,  
    number int check(0 < number and number < 65536) not null  
)  
  
create procedure sp_insert_sensor_with_port(@name nvarchar(250), @host nvarchar(100), @port int)  
as  
begin  
    -- İleride transaction safe yapılacak  
    insert into sensors (name, host) values (@name, @host)  
  
    declare @sensor_id int = @@identity  
  
    insert into ports (sensor_id, number) values (@sensor_id, @port)  
end  
  
exec sp_insert_sensor_with_port 'rain', 'csystem.org/sensors/rain', 4500
```

##### group by Clause ve having Operatörü

>`group by clause` özellikle aggregate fonksiyonlarla birlikte gruplama işlemi için çok sık karşımıza çıkan sorgulardandır. Gruplarken aynı zamanda gruplamaya yönelik koşul da belirtilecekse `having` operatörü kullanılır. 
>
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
    credits int not null  
)  
  
  
create table grades (  
    grade_id int primary key identity(1, 1),  
    description nvarchar(2) not null,  
    value real not null  
)  
  
insert into grades (description, value) values ('AA', 4.0), ('BA', 3.5), ('BB', 3.0), ('CB', 2.5), ('CC', 2.0), ('DC', 1.5), ('DD', 1.0), ('FF', 0.0), ('NA', 0.0), ('P', -1)  

create table enrolls (  
    enroll_id bigint primary key identity(1, 1),  
    student_id int foreign key references students(student_id),  
    lecture_code char(7) foreign key references lectures(lecture_code),  
    grade_id int foreign key references grades(grade_id)  
)  
  
create function get_full_text(@first nvarchar(250), @second nvarchar(250), @third nvarchar(250))  
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

##### Triggers

>Trigger'lar SQL Server'da iki gruba ayrılır: **instead of trigger, after trigger.** instead of trigger'lar insert, delete veya update işlemlerinden hangisine ilişkinse, o işlem yapılır yapılmaz henüz veritabanına  yansıtılmadan devreye girer. Tipik olarak programcı trigger içerisinde işlemin veritabanına yansıtılıp yansıtılmayacağını belirleyen kodları yazar. Bir trigger'da duruma göre işlemin yapılması yani veritabanına yansıtılması	reddedilebilir. Şüphesiz bu tarz işlemler SP kullanarak da yapılabilir. Bu anlamda hangisinin tercih edileceği domain'e	(yani senaryoya) bağlıdır. After trigger, insert, delete veya update işlemi veritabanına yansıdıktan sonra devreye girer. instead of trigger'larda işlemin veritabanına yansıtılması trigger içerisinde yapılmalıdır. Aksi durumda ilgili işlem	veritabanına yansıtılmaz. After trigger'larda ise veritabanına yansımış işlemin geri alınması trigger içerisinde yapılmalıdır. Aksi durumda ilgili işlem veritabanına yansımış olarak kalır.  

>Bir trigger `create trigger` cümlesi ile yaratılır. Bir trigger içerisinde yapılan işleme ilişkin verilere `inserted` ismi ile erişilir. update trigger için `update` fonksiyonu ile hangi alanın update edildiği sorgulanarak işlem yapılabilir. 

>Aşağıdaki demo örneği inceleyiniz

```sql
use testdb  
  
create table devices (  
    device_id int primary key identity (1, 1),  
    name nvarchar(300) not null,  
    host nvarchar(300) not null,  
    port int not null,  
    latitude real,  
    longitude real,  
);  
  
  
create trigger t_insert_device_instead_of on devices  
instead of insert  
as  
begin  
    declare @port int = (select port from inserted)  
  
    if 1024 <= @port and @port <= 65535  
        insert into devices (name, host, port, latitude, longitude) (select name, host, port, latitude, longitude from inserted)  
end  
  
create trigger t_update_device_instead_of on devices  
instead of update  
as  
begin  
    declare @port int = (select port from inserted)  
      
    if update(port) and 1024 <= @port and @port <= 65535  
        update devices set port = @port where device_id = (select device_id from inserted)  
  
end  
  
insert into devices (name, host, port, latitude, longitude) values ('Rain Sensor', 'csystem.org/devices/rain', 125, 34.5, -120.5);  
insert into devices (name, host, port, latitude, longitude) values ('Humidity Sensor', 'csystem.org/devices/humidity', 345, 34.5, -120.5);  
insert into devices (name, host, port, latitude, longitude) values ('Weather Sensor', 'csystem.org/devices/weather', 3434, 34.5, -120.5);  
  
select * from devices;  
  
update devices set port = 346 where device_id = 1;  
update devices set port = 6767 where device_id = 1;
```

>Aşağıdaki demo örneği inceleyiniz

```sql
create table devices (  
    device_id int primary key identity (1, 1),  
    name nvarchar(300) not null,  
    host nvarchar(300) not null,  
    port int not null,  
    latitude real,  
    longitude real,  
);  
  
  
create trigger t_insert_device_after on devices  
after insert  
as  
begin  
    declare @port int = (select port from inserted)  
  
    if 1024 > @port or @port > 65535  
    begin  
        declare @device_id int = (select device_id from inserted)  
  
        delete from devices where device_id = @device_id  
    end  
end  
  
insert into devices (name, host, port, latitude, longitude) values ('Rain Sensor', 'csystem.org/devices/rain', 1250, 34.5, -120.5);  
insert into devices (name, host, port, latitude, longitude) values ('Humidity Sensor', 'csystem.org/devices/humidity', 345, 34.5, -120.5);  
insert into devices (name, host, port, latitude, longitude) values ('Weather Sensor', 'csystem.org/devices/weather', 3434, 34.5, -120.5);
```

>**Sınıf Çalışması:** Aşağıdaki tabloları hazırlayınız ve ilgili soruyu yanıtlayınız
>**Tablolar:**
>- **people**
>	- citizen_id char(11)
>	- first_name nvarchar(300)
>	- last_name nvarchar(300)
>	- birth_date date
>- **people_younger**
>	- citizen_id char(11)
>	- first_name nvarchar(300)
>	- last_name nvarchar(300)
>	- birth_date date
>- **people_older**
>	- citizen_id char(11)
>	- first_name nvarchar(300)
>	- last_name nvarchar(300)
>	- birth_date date
>**Sorular:**
>- insert işlemin yaşı 18'den büyük 65'den küçük olanları `people` tablosuna, 65'den büyük olanları `people_older` tablosuna ve 18'den küçük olanları `people_younger` tablosuna ekleyen instead of trigger'ları yazınız.
>- Doğum tarihi bilgisinin güncellenmesi durumuna göre kontrol edip gerektiğinde ilgili tabloya veriyi aktaran instead of trigger'ları yazınız

```sql
use peopledb;  
  
create table people (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
create table people_younger (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
create table people_older (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
create trigger t_insert_people_instead_of on people  
instead of insert  
as  
begin  
    declare @birth_date date = (select birth_date from inserted)  
    declare @age real = datediff(day, @birth_date, getdate()) / 365.  
  
    if @age < 18  
        insert into people_younger (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
    else if @age < 65  
        insert into people (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
    else  
        insert into people_older (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
end  
  
create trigger t_insert_people_younger_instead_of on people_younger  
instead of insert  
as  
begin  
    declare @birth_date date = (select birth_date from inserted)  
    declare @age real = datediff(day, @birth_date, getdate()) / 365.  
  
    if @age < 18  
        insert into people_younger (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
    else if @age < 65  
        insert into people (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
    else  
        insert into people_older (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
end  
  
create trigger t_insert_people_older_instead_of on people_older  
instead of insert  
as  
begin  
    declare @birth_date date = (select birth_date from inserted)  
    declare @age real = datediff(day, @birth_date, getdate()) / 365.  
  
    if @age < 18  
        insert into people_younger (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
    else if @age < 65  
        insert into people (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
    else  
        insert into people_older (citizen_id, first_name, last_name, birth_date) (select citizen_id, first_name, last_name, birth_date from inserted)  
end  
  
insert into people (citizen_id, first_name, last_name, birth_date) values ('12345678904', 'John', 'Doe', '2013-01-01')  
insert into people (citizen_id, first_name, last_name, birth_date) values ('12345678901', 'Jack', 'Doe', '1976-01-01')  
insert into people (citizen_id, first_name, last_name, birth_date) values ('12345678904', 'Mary', 'Doe', '1934-01-01')
```



##### Explicit Transaction

>Bir transaction `begin tran` veya `begin transaction` cümlesi ile başlatılır. Artık bu noktadan sonra transaction'ın tamamlanması için `commit tran veya commit transaction` ya da `rollback tran veya rollback transaction` yapılması gerekir. Transaction'a istenirse bir isim de verilebilir. Bu durum ileride ele alınacaktır.
>
>Akış içerisinde herhangi bir error oluştuğunda `@@error` isimli bir global değişkene hataya ilişkin kod bilgisi atanır. İşlemde herhangi bir hata oluşmamışsa bu değişkenin içerisinde sıfır değeri bulunur. Bu durumda programcı transaction içerisinde yapılan bir işlemden sonra bu değişkenin değerini kontrol ederek akışa yön verir. 
>
>SQL Server'da explicit transaction yönetimi çok kompleks söz konusu değilse `goto` deyimi kullanılarak kolay bir biçimde yapılabilir. goto deyimi çok sık kullanılmasa da transaction işlemlerinde duruma göre tercih edilebilmektedir. goto deyimi bir etiket (label) bekler. Bu etiketin, önceden tanımlanmış olması gerekir. Etiket ismi değişken isimlendirme uygun herhangi bir isim olabilir. Tanımlama işlemi `:` atomu ile yapılır. Bu deyim ile akış ilgili etiketin bulunduğu koda dallanır (branch). 

```sql
create table sensors (  
    sensor_id int primary key identity(1, 1),  
    name nvarchar(250) not null,  
    host nvarchar(100) not null  
)  
  
create table ports (  
    sensor_id int foreign key references sensors(sensor_id) not null,  
    number int check(0 < number and number < 65536) not null,  
    constraint sensor_port_pk primary key(sensor_id, number)  
)  
  
create procedure sp_insert_sensor_with_port(@name nvarchar(250), @host nvarchar(100), @port int)  
as  
begin  
    declare @status int = 0  
  
    begin tran--saction  
    insert into sensors (name, host) values (@name, @host)  
  
    declare @sensor_id int = @@identity  
  
    insert into ports (sensor_id, number) values (@sensor_id, @port)  
    set @status = @@error  
  
    if @status <> 0
        goto END_TRANSACTION  
  
    commit tran--saction  
END_TRANSACTION:  
    if @status <> 0  
        rollback tran--saction  
end  
  
exec sp_insert_sensor_with_port 'rain', 'csystem.org/sensors/rain', 4500  
exec sp_insert_sensor_with_port 'weather', 'csystem.org/sensors/weather', 450  
exec sp_insert_sensor_with_port 'humidity', 'csystem.org/sensors/humidity', -450  
exec sp_insert_sensor_with_port 'traffic', 'csystem.org/sensors/traffic', 450  
  
select * from sensors;  
select * from ports;
```

##### Kesişim, Birleşim ve Fark İşlemleri

>Belirli koşullar altında birden fazla sorgu kesişim (intersect), birleşim (union) ve fark (except) işlemlerine sokulabilir. Bu işlemler genel olarak Matematik'teki küme işlemleri ile eşdeğerdir. Bu işlemlerde sorgulardan elde edilen alanların karşılıklı olarak türleri aynı olmak zorundadır. Bu işlemler implicit transaction biçimindedir. Birleşim işlemi iki türlü yapılabilir: union, union all. Union işleminde aynı  olan bir kayıttan bir tane elde edilirken, union all işleminde tümü elde edilir. Bu işlemlerde sorgu sayısının ve sorgunun yapılış biçiminin önemi yoktur. Bu işlemlerde iki verinin aynı olması için karşılıklı alanların değerlerinin aynı olması gerekir.

>Aşağıdaki örneği inceleyiniz

```sql
use testdb;  
  
create table people (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
  
create table people_younger (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
create table people_older (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
  
create procedure sp_insert_person(@citizen_id char(11), @first_name nvarchar(300), @last_name nvarchar(300), @birth_date date)  
as  
begin  
    declare @age real = datediff(day, @birth_date, getdate()) / 365.0  
  
    if @age < 18  
        insert into people_younger values(@citizen_id, @first_name, @last_name, @birth_date)  
    else if @age < 65  
        insert into people values(@citizen_id, @first_name, @last_name, @birth_date)  
    else  
        insert into people_older values(@citizen_id, @first_name, @last_name, @birth_date)  
end  
  
    drop function get_age  
  
create function get_age(@birth_date date, @reference_date date)  
returns real  
as  
begin  
    return datediff(day, @birth_date, @reference_date) / 365.  
end  
  
-- union  
create function get_all_people()  
returns table  
as  
return  
(select first_name + ' ' + last_name as full_name, birth_date, dbo.get_age(birth_date, getdate()) as age from people  
union  
select first_name + ' ' + last_name, birth_date, dbo.get_age(birth_date, getdate()) as age from people_older  
union  
select first_name + ' ' + last_name, birth_date, dbo.get_age(birth_date, getdate()) as age from people_younger)  
  
--union all  
create function get_all()  
returns table  
    asreturn  
(select first_name + ' ' + last_name as full_name, birth_date, dbo.get_age(birth_date, getdate()) as age from people  
 union all  
 select first_name + ' ' + last_name as full_name, birth_date, dbo.get_age(birth_date, getdate()) as age from people_older  
 union all  
 select first_name + ' ' + last_name as full_name, birth_date, dbo.get_age(birth_date, getdate()) as age from people_younger)  
  
  
--intersect  
create function get_same_people()  
returns table  
as  
return  
(select first_name + ' ' + last_name as full_name, birth_date from people  
intersect  
select first_name + ' ' + last_name as full_name, birth_date  from people_older  
intersect  
select first_name + ' ' + last_name as full_name, birth_date from people_younger)  
  
select * from get_all_people()  
select * from get_same_people()  
select * from get_all()  
  
-- execept  
select first_name + ' ' + last_name as full_name, birth_date from people  
except  
select first_name + ' ' + last_name as full_name, birth_date  from people_older  
except  
select first_name + ' ' + last_name as full_name, birth_date from people_younger
```

##### View

>Aşağıdaki view'ları inceleyiniz. Aşağıdaki örnekte `v_all_people` view'u updatable değildir. `v_babies` view'u updatable bir view'dur. `with chack option` seçeneği ile yaratılmıştır. Bu view'da citizen_id alanı elde edilmeseydi, view'un kendisi updatable olmasına karşın ilgili tabloda `citizen_id` alanı primary key olduğundan boş geçilemez. Dolayısıyla bu view'a veri insert edilemez. Bu view'dan elde edilen alanlar kullanılarak update ve delete işlemleri yapılabilir. Benzer şekilde `v_children `view'unda `datediff(day, birth_date, getdate()) / 365.0 < 18 ` koşulu yazılmasaydı tablonun içerisinde domain anlamında 18 yaşından küçüklerin bulunması zorunluluğu delinebilirdir. Çünkü bu durumda 2 yaşından büyük her kişi bu view üzerinden tabloya eklenebilirdi

```sql
use testdb;  
  
create table people (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
  
create table people_younger (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
)  
  
create table people_older (  
    citizen_id char(11) primary key,  
    first_name nvarchar(300),  
    last_name nvarchar(300),  
    birth_date date  
) 
  
create procedure sp_insert_person(@citizen_id char(11), @first_name nvarchar(300), @last_name nvarchar(300), @birth_date date)  
as  
begin  
    declare @age real = datediff(day, @birth_date, getdate()) / 365.0  
  
    if @age < 18  
        insert into people_younger values(@citizen_id, @first_name, @last_name, @birth_date)  
    else if @age < 65  
        insert into people values(@citizen_id, @first_name, @last_name, @birth_date)  
    else  
        insert into people_older values(@citizen_id, @first_name, @last_name, @birth_date)  
end  
  
create view v_all_people  
as  
select first_name + ' ' + last_name as full_name, birth_date from people  
union  
select first_name + ' ' + last_name as full_name, birth_date  from people_older  
union  
select first_name + ' ' + last_name as full_name, birth_date from people_younger 
  
select * from v_all_people 
  
create view v_babies  
as  
select citizen_id, first_name, last_name, birth_date from people_younger where datediff(day, birth_date, getdate()) / 365.0 < 2  
with check option  

  
create view v_children  
as  
select citizen_id, first_name, last_name, birth_date from people_younger  
where datediff(day, birth_date, getdate()) / 365.0 >= 2 and datediff(day, birth_date, getdate()) / 365.0 < 18  
with check option  
```

>**Sınıf Çalışması:** Aşağıdaki tabloları oluşturunuz ve soruları yanıtlayınız
>customers
>	- customer_id (p.k)
>	- first_name
>	- middle_name
>	- last_name
>	- address
>phones
>	- phone_number (p.k)
>	- customer_id (f.k.)
>phone_invoices
>	- phone_invoice_id (identity)
>	- phone_number (f.k.)
>	- invoice_date
>	- last_pay_date
>	- paid_date (nullable)
>	- total
>**Sorular:**
>1. Fatura ödeme merkezinin ödenmemiş tüm faturalara ilişkin bilgilerinin elde edildiği view'u yazınız. View içerisinde müşteri ismi full_name alanı olarak elde edilecektir. full_name alanı içerisindeki bilgilerin yalnızca ilk iki harfleri görünecektir. Örneğin `İlker Deveci` ismi için `İl*** De****` şeklinde ya da örneğin `Mehmet Ali Yeşilkaya` ismi için `Me**** Al* Ye*******` şeklinde görünecektir. 
>2. Ödenmiş faturaları elde eden bir view yazınız.
>3. Fatura ödenmemişse fatura ödeme merkezinin ödeme tamamlandığında view ile update yapabileceği bir SP yazınız. 
>

>**Çözüm:**

```sql
create database invoicedb;  
  
use invoicedb  
  
create table customers (  
    customer_id int identity primary key,  
    first_name varchar(50) not null,  
    middle_name varchar(50),  
    last_name varchar(50) not null,  
    address varchar(100) not null  
)  
  
create table phones (  
    phone_number varchar(15) primary key,  
    customer_id int not null foreign key references customers(customer_id)  
)  
  
create table phone_invoices (  
    phone_invoice_id int identity primary key,  
    phone_number varchar(15) not null foreign key references phones(phone_number),  
    invoice_date date not null,  
    last_pay_date date not null,  
    paid_date date,  
    total money not null  
)  
  
create function hide_text_right(@str nvarchar(max), @count int, @ch char(1))  
    returns nvarchar(max)  
begin  
    if @str is null  
        return null  
    return left(@str, @count) + replicate(@ch, len(@str) - @count)  
end  
  
  
create function get_full_text(@first nvarchar(250), @second nvarchar(250), @third nvarchar(max))  
    returns nvarchar(max)  
as  
begin  
    declare @full_text nvarchar(max) = '';  
  
    if @first is not null  
        set @full_text = @first;  
  
    if @second is not null  
        begin            if @full_text <> ''  
                set @full_text = @full_text + ' ';  
            set @full_text = @full_text + @second;  
        end  
  
    if @third is not null  
        begin            if @full_text <> ''  
                set @full_text = @full_text + ' ';  
            set @full_text = @full_text + @third;  
        end  
  
    return @full_text;  
end  
  
  
-- 1  
create view v_unpaid_invoices  
as  
select  
    pi.phone_number,  
    dbo.get_full_text(dbo.hide_text_right(c.first_name, 2, '*'), dbo.hide_text_right(c.middle_name, 2, '*'), dbo.hide_text_right(c.last_name, 2, '*')) as full_name,  
    pi.invoice_date,  
    pi.last_pay_date  
from  
phone_invoices pi inner join phones p on pi.phone_number = p.phone_number  
inner join customers c on c.customer_id = p.customer_id where pi.paid_date is null  
  
-- 2  
create view v_paid_invoices  
as  
select  
    pi.phone_number,  
    dbo.get_full_text(dbo.hide_text_right(c.first_name, 2, '*'), dbo.hide_text_right(c.middle_name, 2, '*'), dbo.hide_text_right(c.last_name, 2, '*')) as full_name,  
    pi.invoice_date,  
    pi.last_pay_date  
from  
    phone_invoices pi inner join phones p on pi.phone_number = p.phone_number  
                      inner join customers c on c.customer_id = p.customer_id where pi.paid_date is not null  
  
  
--3  
create view v_updatable_unpaid_invoices  
as  
    select phone_number, invoice_date, paid_date from phone_invoices where paid_date is null 
with check option 
  
  
create procedure sp_pay_invoice (@phone_number varchar(15), @invoice_date date)  
as  
begin  
    update v_updatable_unpaid_invoices set paid_date = getdate() where phone_number = @phone_number and invoice_date = @invoice_date  
end  
  
  
-- data and test  
  
insert into customers (first_name, middle_name, last_name, address) values ('John', 'Ali', 'Doe', '123 Elm St')  
insert into customers (first_name, last_name, address) values ('Jane', 'Johnson', '456 Oak St')  
  
insert into phones (phone_number, customer_id) values ('123-456-7890', 1)  
insert into phones (phone_number, customer_id) values ('123-456-7895', 2)  
  
insert into phone_invoices (phone_number, invoice_date, last_pay_date, paid_date, total) values ('123-456-7890', '2025-01-01', '2025-02-01', null, 100.00)  
insert into phone_invoices (phone_number, invoice_date, last_pay_date, paid_date, total) values ('123-456-7895', '2025-01-01', '2025-02-01', null, 200.00)  
insert into phone_invoices (phone_number, invoice_date, last_pay_date, paid_date, total) values ('123-456-7890', '2025-02-01', '2025-03-01', '2025-02-15', 100.00)  
  
select * from customers  
  
select * from phones  
select * from phone_invoices  
  
select * from v_unpaid_invoices  
select * from v_paid_invoices  
  
select * from v_updatable_unpaid_invoices  
  
update v_updatable_unpaid_invoices set paid_date = getdate() where phone_number = '123-456-7890' and invoice_date = '2025-01-01'  
  
exec sp_pay_invoice @phone_number = '123-456-7895', @invoice_date = '2025-01-01'
```


##### Hataların İşlenmesi (Error Handling)

>Aşağıdaki demo örnekte payda için üretilen rassal değer sıfır olduğunda `runtime error` oluşur

```sql
declare @a int = rand() * 20 - 10  
declare @b int = rand() * 20 - 10  
declare @result int  
  
select @a, @b  
  
set @result = @a / @b  
  
select @result
```

>Akış error bakımında ele alınacaksa (error handling) `begin try ve end try` arasında yazılmalıdır. `begin try ve end try` aralığını `begin catch ve end catch` izlemelidir. Bu durumda error oluşursa akış `begin catch end catch` kodlarına bir daha geri dönmemek üzere (non resumptive) dallanır. Her hangi bir error oluşmazsa akış `begin catch ve end catch` atlanarak eder.

```sql
begin try  
    declare @a int = rand() * 20 - 10  
    declare @b int = rand() * 20 - 10  
    declare @result int  
  
    select @a, @b  
  
    set @result = @a / @b  
  
    select @result  
end try  
begin catch  
    select 'Denominator can not be zero' as error_message  
end catch  
  
select 'Continue' as continue_message
```

>`sys.messages` view'unda, kayıtlı olan error'lara ilişkin bazı önemli bilgiler elde edilebilmektedir. Bu view'dan elde edilen alanlar şunlardır:
>**message_id:** Error'a ilişkin tekil (unique) bir bilgidir.
>
>**language_id:** Error'lara yönelik mesajlar pek çok dilde saklanmaktadır. Bu alan dile özgü bir id değeridir. İngilizce dilinin id'si `1033`, Türkçe dilinin id'si `1055` olarak belilenmiştir. 
>
>**severity:** Mesajın önem derecesine ilişkin numara bilgisidir. Aşağıdaki tabloda severity numaralarına ilişkin detaylar açıklanmıştır.

| Numara     | Anlamı                                                | Yakalama Durumu |
| ---------- | ----------------------------------------------------- | --------------- |
| `[0, 9]`   | Bilgilendirme mesajı ve yalnızca durum bilgisi        | Yakalanamaz     |
| `10`       | Bilgilendirme mesajı                                  | Yakalanamaz     |
| `[11, 16]` | Programcı tarafından handle edilebilirm hatalar       | Yakalanabilir   |
| `[17, 19]` | Yazılımsal hatalar                                    | Yakalanamaz     |
| `[20, 24]` | Sistemsel problemler ve ölümcül hatalar (fatal error) | Yakalanamaz     |

>**is_event_logged:** Oluşan error'un log'lanıp log'lanmayacağına ilişkin predicate bilgidir
>
>**text:** Error'un ilgili dildeki mesajını içeren alandır.

>Aşağıdaki sorguda programcının  handle edebileceği error'lar Türkçe mesajları ile elde edilmektedir

```sql
select * from sys.messages where severity between 11 and 16 and language_id = 1055
```

>Error'a ilişkin çeşitli bilgiler ve değerler şu fonksiyonlar ile elde edilebilir: 
>- **error_number**
>- **error_procedure**
>- **error_severity**
>- **error_line**
>- **error_state**
>- **error_message**

```sql
begin try  
    declare @a int = rand() * 20 - 10  
    declare @b int = rand() * 20 - 10  
    declare @result int  
  
    select @a, @b  
  
    set @result = @a / @b  
  
    select @result  
end try  
begin catch  
    select error_number(), error_message(), error_severity(), error_state(), error_line(), error_procedure()
end catch  
  
select 'Continue' as continue_message
```

>**Sınıf Çalışması:** Aşağıdaki belirtilen `staff` tablosunda insert işlemi yapılırken oluşan error'lara ilişkin procedure ismi, error number, error message, error severity, error line ve oluşma zamanı bilgilerini `staff_errors`tablosuna ekleyen `sp_insert_staff` isimli SP'yi yazınız:

>staff
>	- staff_id (identity)
>	- citizen_id (unique)
>	- first_name
>	- last_name
>	- birth_date
>	- phone

>**Çözüm:**

```sql
create table staff (  
    staff_id int primary key identity(1, 1),  
    citizen_id char(11) unique not null,  
    first_name nvarchar(300) not null,  
    last_name nvarchar(300) not null,  
    birth_date date not null,  
    phone char(15) not null,  
)  
  
create table staff_errors (  
    staff_error_id int primary key identity(1, 1),  
    procedure_name nvarchar(100) not null,  
    number int not null,  
    message nvarchar(max) not null,  
    severity int not null,  
    line int not null,  
    date_time datetime default(sysdatetime()) not null  
)  
  
create procedure sp_insert_staff(@citizen_id char(11), @first_name nvarchar(300), @last_name nvarchar(300), @birth_date date, @phone char(15))  
as  
begin  
    begin  try        insert into staff (citizen_id, first_name, last_name, birth_date, phone) values (@citizen_id, @first_name, @last_name, @birth_date, @phone)  
    end try  
    begin catch        insert into staff_errors (procedure_name, number, message, severity, line) values (error_procedure(),error_number(), error_message(), error_severity(), error_line())  
    end catch  
end  
  
exec sp_insert_staff @citizen_id = '12345678901', @first_name = 'John', @last_name = 'Doe', @birth_date = '1980-01-01', @phone = '1234567890'  
exec sp_insert_staff @citizen_id = '12345678903', @first_name = 'Jane', @last_name = 'Doe', @birth_date = '1980-01-01', @phone = '1234567890'  
  
select * from staff  
select * from staff_errors
```

>Error mesajlarına `sys.addmessage`SP'si ile programı kendi tanımladığı error'ları ekleyebilir. Bu durumda eklenene error'a ilişkin mesaj numarası `(50000, 2147483647]` aralığında olması gerekir. Bu aralık dışında verilen numaralar eklenemez, error oluşur.

```sql
exec  sys.sp_addmessage @msgnum = 50001, @severity = 11, @msgtext = 'CSD Error Message', @with_log = true  
  
select * from sys.messages where message_id = 50001;z
```

>`raiserror` deyimi ile error fırlatılabilir. raiseerror ile ya `[11, 16]` severity numaraları fırlatılabilir. raiserror ile fırlatılan erro'un numarası genel olarak 50000'dir. 


>Aşağıdaki SP'yi ve ilgili kodları inceleyiniz

```sql
create procedure sp_divide_two_ints(@a int, @b int, @result int out)  
as  
begin  
    if @b = 0  
        raiserror('Division by zero is not allowed.', 11, 1)  
    set @result = @a / @b  
end  
  
  
begin try  
    declare @result int  
    declare @a int = rand() * 10 - 5  
    declare @b int = rand() * 10 - 5  
  
    select @result, @a, @b  
    exec sp_divide_two_ints @a, @b, @result out  
  
    select @result  
end try  
begin catch  
    select error_number(), error_message(), error_severity(), error_state(), error_line(), error_procedure()  
end catch
```


>raiserror işlemi yan etkisi (side effect) bir işlem olduğundan fonksiyon içeriside kullanılamaz. Aşağıdaki fonksiyon tanımlaması geçersizdir.

```sql
  
  
create function divide_two_ints(@a int, @b int)  
returns int  
as  
begin  
    if @b = 0  
        raiserror('Division by zero is not allowed.', 11, 1)  
    return @a / @b  
end
```

>SQL Server 2012  ile birlikte `throw` ile 16 severity numaralı herhangi bir error fırlatılabilir. throw deyimi ile fırlatılan error'un numarasının `(50000, 2147483647]` aralığında olması gerekir.

```sql
create procedure sp_divide_two_ints(@a int, @b int, @result int out)  
as  
begin  
    if @b = 0  
        throw 50001, 'Division by zero is not allowed.', 1  
    set @result = @a / @b  
end  
  
  
begin try  
    declare @result int  
    declare @a int = rand() * 10 - 5  
    declare @b int = rand() * 10 - 5  
  
    select @result, @a, @b  
    exec sp_divide_two_ints @a, @b, @result out  
  
    select @result  
end try  
begin catch  
    select error_number(), error_message(), error_severity(), error_state(), error_line(), error_procedure()  
end catch
```

>İç içe try deyimleri doğrudan ya da dolaylı olarak olabilmektedir. Bu durumda içteki begin-try, end-try içerisinde bir error oluştuğunda dıştaki begin-catch bu error'u yakalayamaz. Eğer içteki error'un dıştaki begin-catch'de yakalanması istenityorsa yeniden fırlatılması (re-throw) gerekir.


```sql
create procedure sp_divide_two_ints(@a int, @b int, @result int out)  
as  
begin  
    if @b = 0  
        throw 50001, 'Division by zero is not allowed.', 1  
    set @result = @a / @b  
end  
  
create procedure sp_select_result(@min int, @bound int)  
as  
begin  
    begin try        declare @a int = rand() * (@bound - @min) + @min  
        declare @b int = rand() * (@bound - @min) + @min  
        declare @result int  
  
        select @a, @b  
  
        if @a = @b  
            throw 50002, 'The two numbers are equal.', 1  
  
        exec sp_divide_two_ints @a, @b, @result out  
  
        select @result  
    end try  
    begin catch        select error_number(), error_message(), error_severity(), error_state(), error_line(), error_procedure()  
        if error_number() = 50002  
            throw  
    end catchend  
  
create procedure sp_do_select  
as  
begin  
    begin try        declare @min int = -5  
        declare @bound int = 6  
  
        exec sp_select_result @min, @bound  
    end try  
    begin catch        select 'main:', error_number(), error_message(), error_severity(), error_state(), error_line(), error_procedure()  
    end catch  
end  
  
  
exec sp_do_select
```

##### Döngü Deyimleri

>