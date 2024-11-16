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
> **Anahtar Notlar:** VTYS'lerde kullanılan dil her ne kadar SQL olarak adlandırılsa da VTYS'ye özgü dil kuralları ve özel adları olabilmektedir. Örneğin, PostgreSQL'de kullanılan genel olarak `plpgsql` dilidir. MSSQL'de kullanılan dil de genel olarak `T-SQL` dilidir. Oracle'da kullanılan dil `PL/SQL` dilidir. 
>- VTYS'ler genel olarak `client-server` çalışma modeline sahiptir. Birden fazla kullanıcı VTYS'ye istekte bulunup cevap alabilir. 
>- VTYS'lerin bir çoğu bir takım yardımcı araçları da içerirler. Örneğin, yönetici programlar, sorgulamaya ilişkin optimizasyon araçları vb. 
>- VTYS'ler belli düzeyde secure ve reliable olarak üretildiklerinden, bu sistemlerde bilgiler kolay kolay bozulmaz ve kolay kolay çalınamazlar. 

