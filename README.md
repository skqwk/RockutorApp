# Rockutor

### Описание
Микросервисное приложение для работы с документами

### Структура

- `editor` - сервис создания и редактирования документов
- `signer` - сервис для подписывания документов
- `auth` - сервис авторизации и аутентификации

### Порядок запуска сервисов в Docker:
1. `auth`
2. `editor`, `signer`, `jaeger`, `kafka` (`rockutor`)
3. `db_monitoring`
4. `krakend`
5. `graylog`

### Данные по настройке/эксплуатации:

| Сервис     | Адрес                  | Логин / Пароль |
|------------|------------------------|----------------|
| Graylog    | http://localhost:9000  | admin / admin  |
| Jaeger     | http://localhost:16686 | -              |     
| Grafana    | http://localhost:3000  | admin / admin  |
| Prometheus | http://localhost:9090  | -              |

### Примерная архитектура

![img.png](assets/architecture.png)

### Структура проекта

![img.png](assets/structure.png)
