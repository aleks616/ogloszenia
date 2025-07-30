# Ogłoszenia

Zadanie rekrutacyjne do firmy codepred, podstawowa aplikacja do zarządzania ogłoszeniami.

Stworzona w:

-Java 21

-Spring Boot 3.5.4

-Gradle

-MySQL

## Użycie
Działa na localhost:8084
```bash
./gradlew bootRun
```

### Struktura danych
![alt text](https://github.com/aleks616/ogloszenia/blob/main/src/main/resources/table.png "tabela ogloszenia")

- id - ustawiane automatycznie
- tresc ogloszenia
- data dodania - ustawiana automatycznie
- ilosc wyswietlen - inkrementowana przy każdym wyświetleniu przez GET, na początku 0

## Endpointy

GET, wyświetla dane ogłoszenia po id, jeśli ogłoszenie pod danym id nie istnieje, wyświetla błąd

```bash
/api/ogloszenie/{id}
```
POST, endpoint do dodawania ogłoszeń, przyjmuje treść ogłoszenia, nie przyjmuje wartości null

```bash
/api/dodajogloszenie
```
DELETE, usuwa ogłoszenie po jego id, jeśli takiego nie ma, zwraca błąd i jego opis

```bash
/api/usunogloszenie/{id}
```
PATCH, zmienia treść ogłoszenia po jego id, jeśli takiego nie ma, zwraca błąd i jego opis, wymaga treści ogłoszenia, nie przyjmuje treści o wartości null, wtedy zwraca błąd


```bash
/api/zmodyfikujogloszenie/{id}
```
---
## Testy i obsługa błędów
- Odpowiednie błędy przy ID < 1, dla funkcji używających id ogłoszenia jako argumentu

- Obsługa **IllegalArgumentException**, **NumberFormatException** i **EntityNotFoundException**, są wyświetlane razem z treścią błędu do każdej z metod (gdzie taki błąd jest możliwy), testy sprawdzające obsługę tych błędów.

- Test jednostkowy sprawdzający logikę zwiększania liczby wyświetleń

- Test integracyjny sprawdzający pełną ścieżkę dodania, wyświetlenia, modyfikacji i usunięcia ogłoszenia

## Struktura plików
- ogłoszenia.postman_collection-zrok.json - używałem zrok.io ponieważ korzystałem z postmana w wersji webowej która nie obsługuje localhost, to jest oryginalny wyeksportowany plik
- ogłoszenia.postman_collection-local.json - powyższy plik z zamienionym adresem zrok na localhost:8084
- ogloszeniadb.sql - dumb bazy danych mysql

### Informacje co do bazy danych
Użyłem mysql, w plikach w repo jest konfiguracja z pustym hasłem, należy je zamienić na prawdziwe







