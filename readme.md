https://dev.to/nhannguyenuri/setting-up-postgresql-in-docker-a-step-by-step-guide-3gc4

https://sky.pro/wiki/media/rabota-s-modulyami-maven-i-sborka-konkretnogo-modulya/

https://www.javathinking.com/blog/spring-boot-application-is-not-running-flyway-migrations-on-startup/

docker run --name service-catalog -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=service -p 5432:5432 -d postgres:16.1

docker run --name manager-app -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=service -p 5433:5432 -d postgres:16.1

mvn clean flyway:clean flyway:migrate spring-boot:run

docker exec -it service-catalog psql -U user -d service

docker exec -it manager-app psql -U user -d service