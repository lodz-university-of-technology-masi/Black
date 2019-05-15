Uruchomienie serwera:
-

- Zainstaluj docker oraz docker-compose 
- W katalogu `backend` uruchom polecenie `docker-compose up --build` lub skrypt `start_postgres.sh`

- Uruchom aplikację serwerową (`SPRING_PROFILES_ACTIVE=local ./gradlew bootRun` lub za pomocą IDE)

Bazę danych można również uruchomić natywnie:
- Zainstaluj postgreSQL 10 (https://www.postgresql.org/download/) (dla Windows przy instalacji należy podać hasło "postgres") **Musi to być wersja 10!**
- Utwórz użytkownika `postgres` z hasłem `postgres` (tylko Linux)
- Uruchom skrypt tworzący użytkownika i bazę (`server/scripts/create_database.sh` lub `.bat`)

Logowanie
-
Aby się zalogować należy wysłać zapytanie
`POST /api/user/signin` zawierające dane `multipart/form-data` z loginem oraz hasłem np:
````
let data = new FormData()
data.append('username', 'moderator')
data.append('password', 'test')
http.post('/api/user/signin', data)
````
Wylogowanie następuje po wykonaniu `POST /api/user/signout`

API restowe
-
Dla wszystkich encji zaimplementowane zostały podstawowe metody pozwalające na ich pobieranie, dodawanie, usuwanie i modyfikację

- `GET /api/<nazwa_encji>/{id}` - Pobranie encji o danym `id` np `GET /api/tests/1`
- `DELETE /api/<nazwa_encji>/{id}` - Usunięcie encji o danym `id` 
- `PUT|PATCH /api/<nazwa_encji>/{id}` - Modyfikacja encji o danym `id`. W ciele zapytania musi znajdować się json zawierający pola encji.
- `POST /api/<nazwa_encji>/` - Dodanie nowej encji. W ciele zapytania musi znajdować się json zawierający pola encji. Zapytanie zwraca utworzoną encję.

Aktualnie zalogowanego użytkownika można pobrać za pomocą : `GET /api/users/current`

