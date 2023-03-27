# parcel_delivery_app
Запуск приложения(draft version, проект дорабатывается):
1. Запустить docker-compose.yaml для старта необходимых контейнеров (PostgresDB, RabbitMQ, Grafana, Prometeus);
2. Стартануть все микросервисы из IDE: eureka-server, api-gateway, ms-identities, ms-orders, ms-couriers, ms-batches, bank-service, simulation-service. (контейнеризация будет сделана позже)
3. В корне проекта приложена коллекция эндпоинтов для Postman (Swagger реализован пока не до конца. Так как аутентефикация и авторизация привязана к Gateway, возникли трудности с корректным пробросом open-api-doc через gateway.).
4. Также можно запустить симуляцию загрузки очереди и spring batch большим количеством заказов.

Solution design находится в корне проекта (parcel-solution-design.drawio).
