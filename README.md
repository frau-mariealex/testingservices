# Automation Tests Project

Проект автоматизированного тестирования веб-сайта Wikipedia и мобильного приложения Wikipedia для Android.

## Содержание

- [Описание проекта](#описание-проекта)
- [Технологический стек](#технологический-стек)
- [Структура проекта](#структура-проекта)
- [Требования к окружению](#требования-к-окружению)
- [Установка и настройка](#установка-и-настройка)
- [Запуск веб-тестов](#запуск-веб-тестов)
- [Запуск мобильных тестов](#запуск-мобильных-тестов)
- [Тестовые сценарии](#тестовые-сценарии)

## Описание проекта

Данный проект содержит автоматизированные UI-тесты для:
1. **Веб-сайт Wikipedia** (ru.wikipedia.org) - тестирование поиска, навигации, переключения языков
2. **Мобильное приложение Wikipedia** (Android) - тестирование поиска, просмотра статей, прокрутки

## Технологический стек

| Технология | Версия | Назначение |
|------------|--------|------------|
| Java | 11+ | Язык программирования |
| Maven | 3.6+ | Система сборки |
| Selenium WebDriver | 4.15.0 | Автоматизация веб-тестов |
| Appium | 9.0.0 | Автоматизация мобильных тестов |
| TestNG | 7.8.0 | Фреймворк для тестирования |
| WebDriverManager | 5.6.2 | Автоматическое управление драйверами |

## Структура проекта

```
.
├── pom.xml                              # Maven конфигурация
├── README.md                            # Документация
└── src/
    └── test/
        ├── java/
        │   ├── pages/
        │   │   ├── web/                 # Page Object для веб-сайта
        │   │   │   ├── BasePage.java
        │   │   │   ├── HomePage.java
        │   │   │   ├── SearchResultsPage.java
        │   │   │   └── ArticlePage.java
        │   │   └── mobile/              # Page Object для мобильного приложения
        │   │       ├── BasePage.java
        │   │       ├── MainScreen.java
        │   │       ├── SearchScreen.java
        │   │       ├── ArticleScreen.java
        │   │       └── SettingsScreen.java
        │   ├── tests/
        │   │   ├── web/                 # Веб-тесты
        │   │   │   ├── BaseWebTest.java
        │   │   │   └── WikipediaWebTests.java
        │   │   └── mobile/              # Мобильные тесты
        │   │       ├── BaseMobileTest.java
        │   │       └── WikipediaMobileTests.java
        │   └── utils/                   # Утилиты
        │       ├── ConfigReader.java
        │       └── DriverFactory.java
        └── resources/
            ├── config.properties        # Конфигурация
            ├── testng.xml               # TestNG suite (все тесты)
            ├── testng-web.xml           # TestNG suite (только веб)
            └── testng-mobile.xml        # TestNG suite (только мобильные)
```

## Требования к окружению

### Для веб-тестов:
- JDK 11 или выше
- Maven 3.6+
- Google Chrome (последняя версия) или Firefox

### Для мобильных тестов:
- JDK 11 или выше
- Maven 3.6+
- Android SDK (Platform Tools)
- Android Emulator или реальное устройство Android
- Appium Server 2.x
- Приложение Wikipedia APK

## Установка и настройка

**Требования:** JDK 11+ и Maven 3.6+ должны быть установлены.

### 1. Клонирование репозитория
```bash
https://github.com/frau-mariealex/testingservices.git
cd testingservices
```

### 2. Установка зависимостей
```bash
mvn clean install -DskipTests
```

### 3. Настройка для мобильных тестов

#### Установка Appium
```bash
# Установка Appium через npm
npm install -g appium

# Установка драйвера UiAutomator2
appium driver install uiautomator2

# Проверка установки
appium -v
```

#### Настройка Android Emulator
1. Установите Android Studio
2. Откройте AVD Manager
3. Создайте эмулятор с API Level 30+ (Android 11+)
4. Запустите эмулятор

#### Установка Wikipedia APK
```bash
# Установка APK на эмулятор/устройство
adb install wikipedia.apk
```
APK можно скачать:
- Google Play: https://play.google.com/store/apps/details?id=org.wikipedia
- APKPure: https://apkpure.com/wikipedia/org.wikipedia
- F-Droid: https://f-droid.org/packages/org.wikipedia/

### 6. Конфигурация

Отредактируйте файл `src/test/resources/config.properties`:

```properties
# Web тесты
web.base.url=https://ru.wikipedia.org
web.browser=chrome
web.implicit.wait=10
web.explicit.wait=15

# Mobile тесты
appium.server.url=http://127.0.0.1:4723
appium.platform.name=Android
appium.platform.version=14.0  
appium.device.name=emulator-5554
appium.app.package=org.wikipedia
appium.app.activity=org.wikipedia.main.MainActivity
appium.automation.name=UiAutomator2
appium.no.reset=false
```

## Запуск веб-тестов

### Запуск всех веб-тестов
```bash
mvn test -Pweb
```

### Запуск через TestNG XML
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-web.xml
```

### Запуск конкретного теста
```bash
mvn test -Dtest=WikipediaWebTests#testSearchReturnsResults
```

### Проверяемые веб-сценарии:
1. Поиск статьи и проверка результатов
2. Открытие статьи из результатов поиска
3. Навигация по языковым версиям (EN, RU, DE)
4. Проверка элементов главной страницы
5. Функция случайной статьи
6. Открытие конкретных статей (Гуси-лебеди, МИФИ, Кошка, Квакши, Санкт-Петербург, Иван Грозный, Пчёлы, Байкал, Байкал Электроникс, Глюкоза)
7. Проверка возможности смены языка статьи

## Запуск мобильных тестов

### 1. Запустите Appium Server
```bash
appium
```

### 2. Запустите Android Emulator
```bash
emulator -avd <имя_эмулятора>
```

### 3. Запуск мобильных тестов
```bash
mvn test -Pmobile
```

### Запуск через TestNG XML
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-mobile.xml
```

### Проверяемые мобильные сценарии:
1. Поиск статьи по ключевому слову
2. Проверка заголовка статьи
3. Прокрутка статьи
4. Проверка наличия языковых опций
5. Навигация назад

## Запуск всех тестов

```bash
mvn test
```

## Тестовые сценарии

### Веб-тесты (12 методов, 22 теста с DataProvider)

| Тест | Описание |
|------|----------|
| testSearchReturnsResults | Поиск возвращает результаты |
| testClickSearchResultOpensArticle | Клик по результату открывает статью |
| testSearchWithDifferentQueries | Поиск с разными запросами (DataProvider, 3 теста) |
| testOpenSpecificArticlePages | Открытие конкретных статей (DataProvider, 10 тестов: Гуси-лебеди, МИФИ, Кошка, Квакши, Санкт-Петербург, Иван Грозный, Пчёлы, Байкал, Байкал Электроникс, Глюкоза) |
| testNavigationToEnglishWikipedia | Переход на английскую Wikipedia |
| testNavigationToRussianWikipedia | Переход на русскую Wikipedia |
| testNavigationToGermanWikipedia | Переход на немецкую Wikipedia |
| testLanguageSwitchOnArticle | Проверка возможности смены языка статьи |
| testHomePageElementsDisplayed | Отображение элементов главной страницы |
| testMultipleLanguageLinksAvailable | Наличие ссылок на разные языки |
| testRandomArticleFeature | Функция случайной статьи |
| testArticlePageStructure | Структура страницы статьи |

### Мобильные тесты (11 методов, 14 тестов с DataProvider)

| Тест | Описание |
|------|----------|
| testSearchArticleByKeyword | Поиск статьи по ключевому слову |
| testOpenArticleFromSearch | Открытие статьи из результатов поиска |
| testSearchWithVariousQueries | Поиск с разными запросами (DataProvider, 3 теста) |
| testArticleTitleMatchesSearch | Проверка заголовка статьи |
| testArticleUIElements | Проверка UI элементов статьи |
| testScrollArticle | Прокрутка статьи вниз и вверх |
| testScrollToSpecificContent | Прокрутка до определённого контента |
| testMainScreenDisplayed | Отображение главного экрана |
| testNavigateToSettings | Навигация в настройки |
| testArticleHasLanguageOption | Наличие опции смены языка |
| testBackNavigation | Навигация назад |

## Отчёты

После выполнения тестов отчёты доступны в:
- `target/surefire-reports/` - стандартные отчёты TestNG
- `target/surefire-reports/emailable-report.html` - HTML отчёт

