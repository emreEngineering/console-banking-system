# Konsol Bankacilik Sistemi

Java ile gelistirilmis, konsol uzerinden calisan basit bir bankacilik uygulamasi.

## Ozellikler

- Kullanici kaydi ve giris
- Para yatirma ve para cekme
- Hesaplar arasi para transferi
- Profil bilgisi guncelleme
- Dosya tabanli veri kaliciligi (`data/users.csv`)

## Gereksinimler

- Java 8 veya uzeri

## Calistirma

```bash
javac -d out src/*.java src/model/*.java src/service/*.java src/ui/*.java
java -cp out Main
```

## Proje Yapisi

- `src/`: Java kaynak kodlari
- `data/`: uygulama verileri
- `.gitignore`: takip edilmemesi gereken dosyalar

## Notlar

- IDE dosyalari (`.idea/`, `*.iml`) ve derleme ciktilari (`out/`, `*.class`) repoya dahil edilmez.
- Uygulama, kullanici verisini `data/users.csv` dosyasinda saklar.
