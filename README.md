TASK MANAGEMENT SYSTEM

Описание проекта:

Проект представляет собой приложение на Spring Boot и Java 17 с использованием PostgreSQL в качестве базы данных. 
Настроена аутентификация и авторизация по email и паролю.
Аутентификация реализована с помощью JWT токена.
Проект настроен для запуска в Docker с использованием Docker Compose.


Содержание проекта:

Dockerfile: описание образа Docker для Spring Boot приложения.
docker-compose.yml: файл для запуска сервисов приложения и базы данных PostgreSQL.
.env: файл с переменными окружения.


Шаги для запуска проекта:

1. Сначала нужно клонировать репозиторий с проектом на локальный компьютер:
git clone https://github.com/AmanEfer/crm.git

2. Создать файл '.env'
В нем прописать переменные:
SPRING_DATASOURCE_USERNAME=ваш логин в postres
SPRING_DATASOURCE_PASSWORD=ваш пароль в postres
JWT_SECRET=ваш секретный ключ
JWT_EXPIRATION=время действия токена(например 3600000)

3. Запустить приложение в Docker с помощью docker-compose.yml