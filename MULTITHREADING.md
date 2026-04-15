# FinTrack API - Многопоточность и Нагрузочное Тестирование

## Реализованные задачи

### 1. Асинхронная бизнес-операция через @Async / CompletableFuture

#### Описание
Реализована асинхронная операция генерации отчёта, которая:
- Мгновенно возвращает `taskId` клиенту
- Выполняется в фоне в отдельном потоке
- Позволяет проверять статус выполнения

#### Эндпоинты

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/api/async/report?months=3` | Запустить генерацию отчёта (возвращает taskId) |
| `GET` | `/api/async/status/{taskId}` | Проверить статус задачи |
| `GET` | `/api/async/counter` | Общее количество запущенных задач (Atomic счётчик) |

#### Пример использования

```bash
# 1. Запустить асинхронную задачу
curl -X POST "http://localhost:8080/api/async/report?months=2"
# Ответ: {"taskId":"task-1","message":"Task started. Check status at /api/async/status/{taskId}"}

# 2. Проверить статус (сразу после запуска)
curl http://localhost:8080/api/async/status/task-1
# Ответ: {"taskId":"task-1","status":"RUNNING","result":null,"startTime":1234567890,"endTime":null,"durationMs":null}

# 3. Проверить статус (после завершения)
curl http://localhost:8080/api/async/status/task-1
# Ответ: {"taskId":"task-1","status":"COMPLETED","result":"Report generated for 2 months. Total transactions: 300","startTime":1234567890,"endTime":1234569890,"durationMs":2000}
```

#### Реализованные классы
- `AsyncConfig.java` — конфигурация ThreadPoolTaskExecutor (4-8 потоков)
- `AsyncTaskService.java` — сервис с @Async методами и ConcurrentHashMap для хранения статусов
- `AsyncTaskController.java` — REST контроллер

---

### 2. Потокобезопасный счётчик (AtomicLong)

#### Описание
В `AsyncTaskService` реализован потокобезопасный счётчик запущенных задач через `AtomicLong`:
```java
private final AtomicLong taskCounter = new AtomicLong(0);
```

Использует CAS (Compare-And-Swap) операции для атомарности без блокировок.

#### Эндпоинт
```bash
curl http://localhost:8080/api/async/counter
# Ответ: {"totalTasksStarted":5}
```

---

### 3. Race Condition: демонстрация и решение

#### Описание
Демонстрация проблемы race condition при 50+ потоках и 4 подхода к решению:

| Подход | Описание | Результат |
|--------|----------|-----------|
| **Без синхронизации** | Обычная переменная `long` | ❌ Потеря обновлений (race condition) |
| **synchronized** | Блок `synchronized (lock)` | ✅ Корректный результат |
| **AtomicLong** | `AtomicLong.incrementAndGet()` | ✅ Корректный результат, выше производительность |
| **ReentrantLock** | Явная блокировка `lock.lock()/unlock()` | ✅ Корректный результат, гибче synchronized |

#### Эндпоинты

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/api/concurrency/race-condition` | Продемонстрировать race condition |
| `POST` | `/api/concurrency/synchronized` | Решение через synchronized |
| `POST` | `/api/concurrency/atomic` | Решение через AtomicLong |
| `POST` | `/api/concurrency/lock` | Решение через ReentrantLock |
| `POST` | `/api/concurrency/full-comparison` | **Полное сравнение всех 4 подходов** |

#### Пример использования

```bash
# Полное сравнение всех подходов
curl -X POST http://localhost:8080/api/concurrency/full-comparison
```

#### Пример ответа
```json
{
  "unsafe": {
    "expectedTotal": 50000,
    "actualResult": 37842,
    "lostUpdates": 12158,
    "hasRaceCondition": true,
    "timeMs": 45
  },
  "synchronized": {
    "expectedTotal": 50000,
    "actualResult": 50000,
    "lostUpdates": 0,
    "hasRaceCondition": false,
    "timeMs": 78
  },
  "atomic": {
    "expectedTotal": 50000,
    "actualResult": 50000,
    "lostUpdates": 0,
    "hasRaceCondition": false,
    "timeMs": 52
  },
  "lock": {
    "expectedTotal": 50000,
    "actualResult": 50000,
    "lostUpdates": 0,
    "hasRaceCondition": false,
    "timeMs": 85
  },
  "summary": {
    "raceConditionDemonstrated": true,
    "allSolutionsCorrect": true
  }
}
```

#### Реализованные классы
- `RaceConditionDemoService.java` — сервис с 4 вариантами реализации
- `RaceConditionDemoController.java` — REST контроллер

---

### 4. Нагрузочное тестирование JMeter

#### Файл плана
`jmeter/fintrack_load_test.jmx`

#### Сценарии тестирования

| Thread Group | Потоки | Итерации | Эндпоинт | Цель |
|--------------|--------|----------|----------|------|
| Async Operations | 20 | 100 | `POST /api/async/report` | Тест асинхронных операций |
| Concurrency Demo | 10 | 50 | `POST /api/concurrency/full-comparison` | Тест многопоточных операций |
| Task Status Check | 30 | 200 | `GET /api/async/status/{taskId}` | Тест проверки статуса |

#### Запуск тестирования

**Вариант 1: Через GUI**
```bash
# 1. Откройте JMeter
jmeter -n -t jmeter/fintrack_load_test.jmx

# 2. Или с GUI
jmeter
# File -> Open -> jmeter/fintrack_load_test.jmx
# Нажмите зелёную кнопку "Start"
```

**Вариант 2: Без GUI (рекомендуется для нагрузочного тестирования)**
```bash
jmeter -n -t jmeter/fintrack_load_test.jmx -l results/jmeter_results.jtl -e -o results/html_report
```

#### Ожидаемые результаты

| Эндпоинт | Ожидаемое среднее время | Ожидаемый 95th percentile |
|----------|-------------------------|---------------------------|
| `/api/async/report` | 5-15 мс | < 50 мс |
| `/api/async/status/{id}` | 2-5 мс | < 10 мс |
| `/api/concurrency/full-comparison` | 200-500 мс | < 1000 мс |

---

## Запуск приложения

### Предварительные требования
- Java 21+
- Gradle (встроен в проект через gradlew)

### Без базы данных (H2 in-memory для тестов)

Создайте файл `src/main/resources/application.properties`:
```properties
# H2 in-memory database для демонстрации
spring.datasource.url=jdbc:h2:mem:fintrack
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```

### Запуск
```bash
./gradlew bootRun
```

### Swagger UI
После запуска откройте: http://localhost:8080/swagger-ui.html

---

## Структура реализованных файлов

```
src/main/java/com/fintrack/api/
├── ApiApplication.java                    # + @EnableAsync
├── config/
│   └── AsyncConfig.java                   # Конфигурация ThreadPoolTaskExecutor
├── controller/
│   ├── AsyncTaskController.java           # Эндпоинты асинхронных операций
│   └── RaceConditionDemoController.java   # Эндпоинты concurrency demo
├── dto/
│   └── TaskStatusDto.java                 # DTO статуса задачи
└── service/
    ├── AsyncTaskService.java              # Асинхронный сервис + Atomic счётчик
    └── RaceConditionDemoService.java      # Демонстрация race condition и решения

jmeter/
└── fintrack_load_test.jmx                 # План нагрузочного тестирования JMeter
```

---

## Технологии

- **@Async + CompletableFuture** — асинхронное выполнение
- **ThreadPoolTaskExecutor** — пул потоков (4 ядра, 8 максимум)
- **ConcurrentHashMap** — потокобезопасное хранение статусов задач
- **AtomicLong** — lock-free потокобезопасный счётчик (CAS операции)
- **synchronized** — классическая синхронизация
- **ReentrantLock** — явная блокировка с try-finally
- **ExecutorService** — управление пулом из 50 потоков для демо
- **JMeter 5.6+** — нагрузочное тестирование
