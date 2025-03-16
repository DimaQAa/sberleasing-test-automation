# Тестовое задание по автоматизации тестирования

В данном проекте реализованы автоматизированные тесты для API и UI согласно заданию.

## Структура проекта

- `src/test/java/com/example/tests/api/` - API-тесты для Petstore
- `src/test/java/com/example/tests/ui/` - UI-тесты для СберЛизинг

## Используемые технологии

- Java 17
- Maven
- JUnit 5
- Rest Assured (для API-тестов)
- Selenide (для UI-тестов)
- Allure (для отчетов)

## Запуск тестов

### Предварительные требования

- JDK 17
- Maven
- Google Chrome

### Запуск всех тестов

```bash
mvn clean test
```

### Запуск только API-тестов

```bash
mvn clean test -Dtest=UserApiTest
```

### Запуск только UI-тестов

```bash
mvn clean test -Dtest=SberLeasingUITest
```

### Генерация Allure-отчета

```bash
mvn allure:serve
```

## Возможные проблемы и их решение

### Проблема с ChromeDriver

Если при запуске UI-тестов возникает ошибка, связанная с несоответствием версий ChromeDriver и Chrome:

1. Скачайте подходящую версию ChromeDriver с [официального сайта](https://chromedriver.chromium.org/downloads) или [Chrome for Testing](https://googlechromelabs.github.io/chrome-for-testing/)
2. Распакуйте архив в удобное место
3. В классе `SberLeasingUITest` раскомментируйте строку:
   ```java
   // System.setProperty("webdriver.chrome.driver", "путь/к/chromedriver.exe");
   ```
   и укажите путь к скачанному chromedriver.exe

### Проблемы с локаторами элементов

Локаторы элементов на сайте СберЛизинг могут измениться. В этом случае необходимо обновить соответствующие селекторы в методах класса `SberLeasingUITest`.

## Описание тестов

### API-тесты (UserApiTest)

1. Создание пользователя (testCreateUser)
2. Обновление данных пользователя (testUpdateUser)
3. Получение информации о пользователе (testGetUser)
4. Удаление пользователя (testDeleteUser)

### UI-тесты (SberLeasingUITest)

1. Открытие Google
2. Поиск "СберЛизинг"
3. Переход на сайт СберЛизинг из результатов поиска
4. Переход в раздел подбора авто
5. Заполнение параметров подбора
6. Получение списка предложений
7. Выбор автомобиля из списка
8. Проверка соответствия марки выбранного автомобиля 