### C ve Sistem Programcıları Derneği
### Genel Notlar
### Eğitmen: Oğuz KARAN

##### Veritabanı ve Veritabanı Yönetim Sistemi

>Bilgilerin ve verilerin saklanması ve geri alınması için organize edilmiş dosyalara **veritabanı (database)** denir. Veritabanları tek bir dosyada olabileceği gibi farklı dosyalar biçiminde de organize edilmiştir. Genellikle bu organizasyon veriye en hızlı ve/veya en iyi performans ile erişmek içindir. Veritabanlarının organizasyonuna yönelik pek çok model (paradigm) kullanılmaktadır. Bunlardan en çok kullanılanlarından bir **ilişkisel veritabanı (relational databases).** Ancak farklı uygulamalarda farklı modeller kullanılabilmektedir. Bu modellerin seçimi ve kullanımı yapılan uygulamaya göre en avantajlı olacak şekilde belirlenir. 
>
>Veritabanı işlemlerini yapan özel yazılımlara **veritabanı yönetim sistemleri (database management systems)** denir. Veritabanı işlemleri ticari uygulamalarda uygulamanın performansını ve verimi doğrudan ya da dolaylı olarak etkileyen en önemli elemanlardan biridir. Bu nedenle uygulama geliştiriciler veritabanı işlemlerini mümkün olduğunca hızlı (fast), güvenilir (safe/reliable), güvenli (secure) yapan araçlar kullanmak isterler. Bir yazılım ürününün veritabanı yönetim sistemi (VTYS) olabilmesi için bazı özelliklere sahip olması beklenir. Bunlardan tipik olan bazıları şunlardır:
>
>- Aşağı seviyeli dosya organizasyonu ile kullanıcının ilişkisini keserler. VTYS'lerde kullanıcıların, bilgilerin hangi dosyada ya da dosyalarda tutulduğunu bilmesi gerekmez. Yani adeta veritabanı kullanıcıya bir kara kutu gibi sunulur. Kullanıcı VTYS'ye ne yapacağını iletir ve ilgili işlemler VTYS tarafından yapılır. 
>- VTYS'ler genel olarak yüksek seviyeli deklaratif dillerle kullanıcı isteklerini alırlar ve ilgili işlemler yaparlar. Bu dillerden ilişkisel veritabanı modelini kullanan sistemlerde en yaygın olanı **SQL (Structured Query Language)** dilidir. 
>
>**Anahtar Notlar:** VTYS'lerde kullanılan dil her ne kadar SQL olarak adlandırılsa da VTYS'ye özgü dil kuralları ve özel adları olabilmektedir. Örneğin, PostgreSQL'de kullanılan genel olarak `plpgsql` dilidir. MSSQL'de kullanılan dil de genel olarak `T-SQL` dilidir. Oracle'da kullanılan dil `PL/SQL` dilidir. 
>
>- VTYS'ler genel olarak `client-server` çalışma modeline sahiptir. Birden fazla kullanıcı VTYS'ye istekte bulunup cevap alabilir. 
>- VTYS'lerin bir çoğu bir takım yardımcı araçları da içerirler. Örneğin, yönetici programlar, sorgulamaya ilişkin optimizasyon araçları vb. 
>- VTYS'ler belli düzeyde secure ve reliable olarak üretildiklerinden, bu sistemlerde bilgiler kolay kolay bozulmaz ve kolay kolay çalınamazlar. 
>
>Bazı VTYS'ler, yukarıdaki özelliklerin pek çoğunu barındırmasa da `SQL` kullanımına izin vermektedir. Bunları genel olarak kurulum da gerektirmez. Bunlar adeta bir veritabanı kütüphanesi gibi tek bir kütüphane dosyasından oluşmaktadırlar. Özellikle gömülü sistemlerde (embedded systems) tercih edildiklerinden dolayı bunlara **gömülü VTYS (embedded DBMS)** da denilmektedir. Bunların günümüzde yaygın olarak kullanılanlarından biri `SQLite` ürünüdür. SQLite hem Windows, hem Unix/Linux, hem Mac OS X hem de mobil işletim sistemlerinde de kullanılabilmektedir. Örneğin Android sistemlerinde pek çok bilgi SQLite kullanılarak saklanmaktadır. 

##### İlişkisel Veritabanları 

>İlişkisel modeli kullananan veritabanlarına **ilişkisel veritabanları (relational databases)** denir.
>İlişkisel veritabanları genel olarak tablolardan (tables), tablolar da satırlardan (records/tuple) ve sütunlardan (fields) oluşur. Tablolarda tekil olması garanti altında olan sütunlara **birincil anahtar (primary key)** denir. Veritabanı içerisindeki her tablonun birincil anahtara sahip olması tavsiye edilir. Birincil anahtar olmayan ancak tekil olan alanlar da olabilmektedir. Bu alanlara tabloda `unique` olarak belirlenir. İlişkisel veritabanlarında bilgiler birden fazla tabloda tutuluyor olabilir. Böylece bilgi birden fazla tablodan da alınabilmektedir. İdeal olarak tablolarda tekrarlı bilgi mümkün olduğunca bulunmamalıdır. Birden fazla tablo arasında geçiş yapabilmek için ortak alanlara ihtiyaç duyulur. Bu ortak alanlara **yabancı anahtar (foreign key)** denir. Bir yabancı anahtar ilişkili olduğu tablodaki bir birincil anahtara karşılık gelir. İlişkisel veritabanlarında alanların (field) türleri vardır. 

**Anahtar Notlar:** Bir değişken bellekte ne kadar yer ayrılacağını ve içerisinde tutulan değerin hangi formatta olacağını belirten kavrama **tür (type)** denir. 

>İlişkisel veritabanlarında veritabanı elemanlarının kullanılabilmesi için yaratılmış olması gerekir. Veritabanı elemanlarının yaratılması `CREATE` cümleleri ile yapılır. Veritabanı elemanlarının yaratılması (create), silinmesi (drop), güncellenmesi (alter) vb. cümlelere genel olarak **Data Definition Language (DDL)** denilmektedir. Verilerin eklenmesi (insert), silinmesi (delete), sorgulanması (select/query), güncellenmesi (update) vb. cümlelere **Data Manipulation Language (DML)** denir. 

>DML işlemleri için **CRUD** kısaltması kullanılmaktadır. CRUD işlemleri (CRUD operations) şunlardır:
>- **C**reate
>- **R**ead
>- **U**pdate
>- **D**elete
>Aslında CRUD DDL cümleleri için de kullanılabilir. CRUD işlemlerine ilişkin cümleler VTYS'ye göre değişiklik gösterebilir ve çoğunlukla çok kapsamlı cümleler kurulabilmektedir. Hatta Standart SQL'a ilişkin cümlelerin de detayları bulunur. Bunlar bir VTYS'ye özgü olarak ayrıca öğrenilmelidir. 
