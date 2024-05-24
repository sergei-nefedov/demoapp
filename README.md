<h3>Демонстрационное RESTful приложение.</h3>

Реализовано с использованием Spring Boot, Spring Security, Maven, PostgreSQL, RestAssured. <br> Запуск осуществляется через pers.nefedov.demoapp.DemoappApplication. <br>
Для работы приложения необходимо запустить PostgreSQL в контейнере Docker с помощью команды  <br> <i>'docker run -d --name testapp_db -p 5432:5432 -e POSTGRES_USER=test_app -e  POSTGRES_PASSWORD=password -e POSTGRES_DB=testapp_db postgres:latest'</i> <br>

Описание эндпойнтов с примерами запросов реализовано с помощью Swagger, интерфейс которого находится по адресу http://localhost:8080/swagger-ui. <br>
Tестирование осуществляется при запущенном приложении через класс pers.nefedov.demoapp.controller.TransferTest. 
