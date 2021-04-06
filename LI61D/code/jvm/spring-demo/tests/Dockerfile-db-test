FROM postgres:13-alpine

USER postgres
WORKDIR /app

COPY ./tests/sql/create-schema.sql /docker-entrypoint-initdb.d/1_create.sql
COPY ./tests/sql/insert-test-data.sql /docker-entrypoint-initdb.d/2_insert-test-data.sql

COPY --chown=postgres:postgres ./tests/scripts/wait-for-postgres.sh ./bin/wait-for-postgres.sh
RUN chmod +x ./bin/wait-for-postgres.sh

EXPOSE 5432