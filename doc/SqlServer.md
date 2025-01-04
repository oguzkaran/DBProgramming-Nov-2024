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

