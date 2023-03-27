# parcel_delivery_app
Запуск приложения(draft version, проект дорабатывается):
1. Запустить docker-compose.yaml для старта необходимых контейнеров (PostgresDB, RabbitMQ, Grafana, Prometeus);
2. Стартануть все микросервисы из IDE. (контейнеризация будет сделана позже)
3. В корне проекта приложена коллекция эндпоинтов для Postman (Swagger реализован пока не до конца).
4. Также можно запустить симуляцию загрузки очереди и spring batch большим количеством заказов.

Solution design находится в корне проекта (parcel-solution-design.drawio).
