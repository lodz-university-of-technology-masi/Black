export PGPASSWORD=postgres
psql -w -U postgres -c "CREATE DATABASE masi;"
psql -w -U postgres -d masi -f "./init_database.sql"
