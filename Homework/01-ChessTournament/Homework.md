>Aşağıda hazırlanmış veritabanına ilişkin şemada **senaryoyu değiştirmeden** iyileştirme işlemini yapınız.

![ChessTournament](./chesstournament.png)

**Veritabanına İlişkin Analiz:**
- Tablo isimlendirmede tüm tabloların çoğul olması gerekir (Oğuz Karan)
- Otomatik belirlenen primary key alanlarının isimleri `xxx_id` biçiminde olmalıdır (Oğuz Karan)
- `players` tablosunda `name` ve `surname` okunabilirlik/algılanabilirlik açısından `first_name` ve `last_name` olarak değiştirilebilir (Umutcan Turan)
- `players`tablosundaki `adres, phone ve email` alanları `contact_information`adlı ayrı bir tabloda olabilir (Umutcan Turan)
- Primary key alanları olmayan tabloların primary key'leri otomatik artan olarak belirlenebilir. Ancak bunlar senaryoya göre composite key olabilirler (İrem Naz Sağır, Oğuz Karan)
- `clubs`tablosunda `organizer_id` tutulması anlamsız gibi görünüyor. Bu değer farklı anlam ifade etmiyorsa zaten olması `data integrity`açısından anlamsız. Bu alan farklı bir anlam ifade ediyorsa bile ismi bu şekilde olmamalıdır. Belkl senaryoya göre buradaki id'ye ilişkin organizer mantıksal olarak diğer organizer'larından daha öncelikli olabilir. Böyle ise bile bu değerin organizers tablosunda tutulması daha uygun olabilir. `clubs` ve `organizers` tablosu `many-to-many` ilişkisi ile oluşturulursa da senaryo değişmez (Umutcan Turan, Oğuz Karan)
- Alan isimleri genel olarak kullandığımız convention'a uygun olarak değiştirilecektir. (Umutcan Turan)
