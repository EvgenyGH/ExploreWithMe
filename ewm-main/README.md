# _Explore with me (EWM)_
## Основной сервис (EWM-MAIN)

#### _Описание_
Основной сервис — содержит всё необходимое для работы продукта.  
Запускается на 8080 порту.
Запускается только после запуска сервиса [ewm-stats](./../ewm-stats/README.md).  
[API (swagger)](./ewm-main-service-spec.json)

#### _Используемые технологии_

Spring-Boot(2.7.1), jpa, postgresql(42.5.0), h2(2.1.214), java-core(jdk 11), lombok(1.18.24),
docker(20.10.17), docker-compose(2.10.2).

#### _Состав проекта_

Проект состоит из сервиса и базы данных postgres:

Общие ER проекта::
![ER](./../misc/ER/ER.png)

#### _Запуск проекта_

Для запуска проекта потребуется docker(20.10.17) и docker-compose(2.10.2).  
Запуск из командной строки:  
_`docker-compose -p ewm up`_
Т.к. проект не работает без сервиса статистики, docker-compose сначала запускает
сервис статистики, а потом основной сервис.

Порядок запуска в ручном режиме:
1. Запустить сервис статистики.  
   1.1. Установить переменные среды.  
   1.2. Запустить базу данных сервиса статистики.  
   1.3. Запустить сервис статистики.
2. Запуск основного сервиса.  
   2.1. Установить переменные среды.  
   2.2. Запустить базу данных основного сервиса.  
   2.3. Запустить основной сервис.

_Переменные среды для сервисов и их значения по
умолчанию в docker-compose:_
1. Сервис статистики:
    - DB_USER=ewm
    - DB_PASSWORD=pwd
    - DB_HOST=db-stats
    - DB_PORT=5432
    - DB_NAME=ewm-stats
    - STATS_SERVER_PORT=9090

2. База данных сервиса статистики:
    - POSTGRES_PASSWORD=pwd
    - POSTGRES_USER=ewm
    - POSTGRES_DB=ewm-stats

3. Основной сервис:
    - DB_USER=ewm
    - DB_PASSWORD=pwd
    - DB_HOST=db-main
    - DB_PORT=5432
    - DB_NAME=ewm
    - STATS_SERVER_PORT=9090
    - STATS_SERVER_HOST=ewm-stats
    - MAIN_SERVER_PORT=8080

4. База данных основного сервиса:
    - POSTGRES_PASSWORD=pwd
    - POSTGRES_USER=ewm
    - POSTGRES_DB=ewm-stats
