# 🚀 Инструкция по запуску FinTrack

## Быстрый старт

### 1️⃣ Запуск базы данных PostgreSQL

Убедитесь, что PostgreSQL запущен и создайте базу данных:

```sql
CREATE DATABASE fintrack;
```

### 2️⃣ Заполнение базы данных тестовыми данными

Выполните SQL-скрипт из корня проекта:

```bash
psql -U postgres -d fintrack -f database_seed.sql
```

Или через pgAdmin/DBeaver импортируйте файл `database_seed.sql`

**Что будет создано:**
- 4 пользователя
- 8 счетов
- 10 категорий
- 8 тегов
- 25 транзакций с ManyToMany связями

### 3️⃣ Настройка Backend

Отредактируйте `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fintrack
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ
```

### 4️⃣ Запуск Backend (Spring Boot)

```bash
# Из корня проекта
./mvnw spring-boot:run

# Или если используете Windows
mvnw.cmd spring-boot:run
```

Backend запустится на `http://localhost:8080`

**Проверьте работу:**
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Health: http://localhost:8080/api/users

### 5️⃣ Запуск Frontend (React)

Откройте новый терминал:

```bash
cd frontend
npm install  # Если еще не установлены зависимости
npm run dev
```

Frontend запустится на `http://localhost:5173`

---

## 🎯 Что можно протестировать

### Dashboard (/)
- Общая статистика: пользователи, счета, категории, теги, транзакции
- Финансовая аналитика: общий баланс, доходы, расходы
- Последние транзакции

### Users (/users)
- ✅ CRUD операции
- ✅ Поиск по username/email/fullName
- ✅ Отображение связи OneToMany (количество счетов)

### Accounts (/accounts)
- ✅ CRUD операции
- ✅ Карточки с балансом и валютой
- ✅ Связь с пользователями
- ✅ Отображение количества транзакций (OneToMany)

### Categories (/categories)
- ✅ CRUD операции
- ✅ Поиск по названию
- ✅ Отображение количества транзакций (OneToMany)

### Tags (/tags)
- ✅ CRUD операции
- ✅ Выбор цвета для каждого тега
- ✅ Используются в ManyToMany с транзакциями

### Transactions (/transactions)
- ✅ Полный CRUD
- ✅ Фильтрация по типу (Income/Expense)
- ✅ Поиск по описанию
- ✅ **ManyToMany**: выбор нескольких тегов для транзакции
- ✅ Цветовая индикация доходов/расходов
- ✅ Отображение связанных тегов с цветами

### Async Tasks (/async)
- ✅ Запуск асинхронных задач генерации отчетов
- ✅ Real-time мониторинг статуса задачи
- ✅ Отображение времени выполнения
- ✅ **AtomicLong**: потокобезопасный счетчик запущенных задач
- ✅ **@Async + CompletableFuture**: неблокирующие операции

### Concurrency Demo (/concurrency)
- ✅ **Race Condition**: демонстрация проблемы (50 потоков, потерянные обновления)
- ✅ **Synchronized**: решение через synchronized блок
- ✅ **AtomicLong**: решение через атомарные операции
- ✅ **ReentrantLock**: решение через явные блокировки
- ✅ **Full Comparison**: сравнение всех 4 подходов
- ✅ Визуализация результатов и производительности

---

## 🧪 Нагрузочное тестирование (JMeter)

Если установлен JMeter:

```bash
jmeter -n -t jmeter/fintrack_load_test.jmx -l results.jtl -e -o report
```

Тест включает:
- Async Operations Test: 10 потоков × 20 итераций
- Concurrency Demo Test: 5 потоков × 10 итераций  
- Task Status Check Test: 10 потоков × 30 итераций

---

## 📊 Демонстрация выполнения требований

### ✅ 1. Асинхронная бизнес-операция (@Async / CompletableFuture)
- **Где**: `/async` страница
- **Что**: Генерация отчетов асинхронно
- **Возвращает**: ID задачи
- **Проверка статуса**: GET `/api/async/status/{taskId}`
- **Код**: `AsyncTaskService.java`, `AsyncTaskController.java`

### ✅ 2. Потокобезопасный счётчик (synchronized / Atomic)
- **Где**: `/async` страница (счетчик задач)
- **Что**: `AtomicLong taskCounter` в `AsyncTaskService`
- **Демонстрация**: Счетчик "Total Tasks Started" обновляется потокобезопасно

### ✅ 3. Race condition (ExecutorService 50+ потоков) и решение
- **Где**: `/concurrency` страница
- **Что**: 
  - Race Condition: 50 потоков, небезопасный счетчик → потерянные обновления
  - Synchronized: решение через `synchronized` блок
  - AtomicLong: решение через `AtomicLong.incrementAndGet()`
  - ReentrantLock: решение через `ReentrantLock`
- **Код**: `RaceConditionDemoService.java`, `RaceConditionDemoController.java`

### ✅ 4. Нагрузочное тестирование JMeter
- **Где**: `jmeter/fintrack_load_test.jmx`
- **Что**: 3 группы тестов для async операций и concurrency
- **Результаты**: CSV файлы с метриками производительности

---

## 🎨 Дополнительные возможности фронтенда

### Современный UI/UX
- Responsive дизайн (работает на мобильных)
- TailwindCSS для стилизации
- Lucide React иконки
- Модальные окна для форм
- Loading states и error handling

### Real-time обновления
- React Query для кэширования и автообновления
- Статус async задач обновляется каждую секунду
- Счетчик задач обновляется каждые 2 секунды

### Визуализация данных
- Цветовые индикаторы для доходов/расходов
- Кастомные цвета для тегов
- Карточки для счетов и категорий
- Статистика на Dashboard

---

## 🔧 Troubleshooting

### Backend не запускается
- Проверьте, что PostgreSQL запущен
- Проверьте credentials в `application.properties`
- Убедитесь, что база данных `fintrack` создана

### Frontend не подключается к API
- Убедитесь, что backend запущен на порту 8080
- Проверьте CORS настройки в `CorsConfig.java`
- Откройте DevTools браузера и проверьте Network tab

### Ошибки при сборке frontend
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run build
```

---

## 📝 API Endpoints (краткая справка)

### CRUD операции
- Users: `/api/users`
- Accounts: `/api/accounts`
- Categories: `/api/categories`
- Tags: `/api/tags`
- Transactions: `/api/transactions`

### Async операции
- POST `/api/async/report?months=3` - Запустить задачу
- GET `/api/async/status/{taskId}` - Статус задачи
- GET `/api/async/counter` - Счетчик задач

### Concurrency демо
- POST `/api/concurrency/race-condition` - Race condition
- POST `/api/concurrency/synchronized` - Synchronized
- POST `/api/concurrency/atomic` - AtomicLong
- POST `/api/concurrency/lock` - ReentrantLock
- POST `/api/concurrency/full-comparison` - Полное сравнение

---

## ✨ Готово!

Откройте браузер: **http://localhost:5173**

Наслаждайтесь полнофункциональным приложением! 🎉
