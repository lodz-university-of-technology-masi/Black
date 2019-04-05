Uruchomienie serwera:
-
- Zainstaluj postgreSQL (https://www.postgresql.org/download/) (dla Windows przy instalacji należy podać hasło "postgres")
- Utwórz użytkownika `postgres` z hasłem `postgres` (tylko Linux)
- Uruchom skrypt tworzący użytkownika i bazę (`server/scripts/create_database.sh` lub `.bat`)
- Uruchom aplikację serwerową (`SPRING_PROFILES_ACTIVE=local ./gradlew bootRun` lub za pomocą IDE)

UWAGA: Aktualnie każde uruchomienie serwera czyści bazę i wgrywa jej schemat od nowa.
Żeby to zmienić trzeba wykomentować linijkę  `spring.datasource.initialization-mode=always` w `application-local.properties`.
