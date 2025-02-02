>Aşağıda hazırlanmış veritabanına ilişkin şemada **senaryoyu değiştirmeden** iyileştirme işlemilerini yapınız.

![ChessTournament](./carservicecenter.png)

**Veritabanına İlişkin Analiz:**

- Tablo isimleri ve primary key alan isimleri yeniden gözden geçirilmeli (Oğuz Karan)
- `customers` tablosundaki iletişim bilgileri `contact_info` gibi bir tablo ile ayrılabilir.
- `booking`tablosundaki `payment_received_yn` ismi `payment_received` olarak değiştirilebilir (Umutcan Turan)
- `manifacturer`tablosunda `manifacturer_name ve manifacturer_details`alanlarındaki `manifacturer_` kaldırılabilir. Benzer durum olan tablolar da ayrı incelenmelidir (Yusuf Enes Baran)