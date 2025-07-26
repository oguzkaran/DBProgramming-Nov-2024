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

##### Triggers

>VTYS'lerde insert, delete ve update yapıldığında otomatik olarak devreye girmesini istediğimiz kod parçalarına trigger denir. Bir trigger her ne kadar bir fonksiyon olsa da veritabanı programcısı ya da veritabanına erişen uygulamalar tarafından çağrılamaz. 

**Anahtar Notlar:** Trigger kullanımında programcının dikkatli olması gerekir. Çünkü bir trigger tüm kullanıcıları etkileyebilir. Bu durumda programcının örneğin kendi test işlemlerinde de trigger'ı devre dışı bırakması gerekebilir. Ayrıca trigger kodları çok zaman almayacak şekilde yazılmalıdır. Çünkü bir trigger devredeyken, trigger'ın yazıldığı veritabanı elemanı için başka bir işlem araya giremez (transaction safe). Bu da, duruma göre veritabanına erişen uygulamaların yavaş hizmet almasına ve/veya hizmet vermesine yol açabilir.	
##### Database Transaction

>Transaction bir grup işin tek bir iş gibi yapılmasına verilen genel bir isimdir. Transaction genel olarak VTYS içerisindeki işleri temsil eder. Transaction'ın temelde iki tane amacı vardır. 
>- İşlerin güvenilir (reliable) bir biçimde yapılması ve gerektiğinde geri alınabilmesidir (roll back). Yapılan iş veya işlerin tutarlı (robust) bir biçimde veritabanına yansıtılmasıdır (commit).
>- Yapılmakta olan bir grup iş için başka işlerin araya girmesini engellemektir (atomic).

>VTYS'lerde transaction dendiğinde tipik olarak 4 kavramın kısaltmasından oluşan **ACID (Atomicity, Consistency, Isolation, Durability)** akla gelir.

>**Atomicity:** İşlerin kesilmeden yani araya başka işler girmeden yapılmasıdır
>**Consistency:** Verilerin geçerliliğinin (validation) garanti altına alınmasıdır. Örneğin bir veri bir tabloya kaydedilmişse yani yazılma işlemi commit edilmişse artık verinin tutarlılığı ve tabloda olması garanti altına alınmış olur. Yani herhangi bir problem oluşmamış demektir. Çünkü `problem oluşsaydı veri yazılmamış olurdu` anlaşılır.
>**Isolation:** Transaction ile yapılan işlerin başka işlerden ayrılması ya da başka bir deyişle soyutlanması demektir.
>**Durability:** Bir transaction commit edilmişse, herhangi bir başka hatanın oluşması durumunda bile o kalıcık bozulmaz. Hata, server'ın abnormal bir biçimde sonlanması (crash) dolayısıyla bile oluşsa commit edilmiş verinin bozulma ihtimali çok düşüktür.
>
>VTYS'lerde transaction iki şekilde yapılabilir: **implicit transaction, explicit transaction**
>- **implicit transaction:** Programcının ayrıca düşünmesi gerekmeyen transaction'dır. Örneğin, bir sorgu işlemi tamamlanana kadar o sorguya ilişkin tablolarda değişikliğe tol açacak işlemler (insert, delete, update) yapılamaz. Ya da örneğin `insert many` işleminde tüm veriler eklenene kadar o tablo ile ilgili herhangi bir işlem yapılamaz. `insert many` işleminde herhangi bir verinin insert edilmesinde problem oluşursa öncesinde yapılmış olan insert işlemleri otomatik olarak geri alınır (roll back). Bu iki örnek için VTYS programcısının transaction için herhangi bir işlem yapmasına gerek yoktur.
>- **explicit transaction:** Bir grup işin VTYS programcısı tarafından `transaction`ya da başka bir deyişle `transaction safe` olarak yapılmasıdır. VTYS'lerde bu işlemi gerçekleştirmek için çeşitli cümleler bulunur. 
>
>Genel olarak CRUD işlemlerine yönelik cümleler tek başına implicit transaction biçimindedir. Ancak örneğin insert işleminden sonra o veri için belirlenen identity değerinin elde edilmesi işleminin transaction safe yapılması programcının sorumluluğundadır yani explicit transaction olarak yapılmalıdır. Explicit transaction'ın tamamlanması için commit veya roll-back işleminin yapılmış olması gerekir. Aksi durumda transaction devam eder. Tipik  olarak trigger fonksiyonlar implicit transaction biçimindedir. VTYS programcısının ayrıca explicit transaction yapması gerekmez.

##### View

>VYYS'lerde view sanal tablolardır. Özel bazı durumlar dışında view içerisinde veri tutulmaz. View, bir sorguyu çalıştırarak bilgileri tablo biçiminde verir. View'lar `create view` cümlesi ile yaratılır. Bir view mantıksal olarak parametresiz, tablo döndüren fonksiyon gibidir. Bir view ile bazı şartlar sağladığında insert, delete ve update gibi işlemler de yapılabilmektedir. Bu tip view'lara **updatable view** denir. view içerisindeki sorguda `order by` yapılamaz. Gerekirse view ile elde edilen veriler sorgulanırken `order by` yapılabilir. Updatable bir view'da sorguya ilişkin koşulu sağlayan verinin oluşmaması için `with check option` olarak yaratılması gerekir. Bu durumda view ile yapılan insert, delete ve update gibi işlemleri ile elde edilen veri veya yeni eklenen veri koşula uymak zorundadır. Aksi durumda error oluşur.

##### Hataların İşlenmesi (Error Handling)

>Programlamada çalışma zamanında bazı hatalı durumlar oluşabilmektedir. Bunlara genel olarak **runtime error** veya **exception** terimleri kullanılır. VTYS dilleri yorumlayıcı (interpreter) ile çalıştıklarından sentaks ve semantik hatalar ile çalışma zamanında meydana gelen hatalı durumlar yine çalışma zamanında ele alınır. Sentaks ve semantik hatalar dışında kalan hataların oluşması durumunda programın nasıl devam edeceğine karar verilmesine ilişkin kodların yazılmasına **hataların işlenmesi (error handling)** denilmektedir. Böylesi bir error handle edilmezse ilgili program (script) abnormal bir biçimde sonlanır.

##### Cursor

>Cursor bir veri kümesini dolaşmak (iteration) için kullanılan bir araçtır. Cursor içerisindeki bir verinin elde edilmesine **fetching** denilmektedir. Cursor mantıksal olarak başlangıçta il verinin öncesini gösterir. Her adımda ilgili veri elde edilir (fetch), en son veriden sonra elde etme işlemi tamamlanmış olur. 