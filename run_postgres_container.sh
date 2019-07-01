docker run -p 5432:5432 --net=kalki_nw --rm --name kalki-postgres  \
-e POSTGRES_USER=kalkiuser -e POSTGRES_PASSWORD=kalkipass -e POSTGRES_DB=kalkidb -d postgres

