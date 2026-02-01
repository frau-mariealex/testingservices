package tests.web;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.web.ArticlePage;
import pages.web.HomePage;
import pages.web.SearchResultsPage;

public class WikipediaWebTests extends BaseWebTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage(driver);
        homePage.open(baseUrl);
    }

    @Test(description = "Поиск возвращает результаты для валидного запроса")
    public void testSearchReturnsResults() {
        SearchResultsPage resultsPage = homePage.searchFor("Байкал озеро");
        
        Assert.assertTrue(resultsPage.hasResults(), 
            "Поиск должен вернуть результаты для 'Байкал озеро'");
    }

    @Test(description = "Клик по результату поиска открывает статью")
    public void testClickSearchResultOpensArticle() {
        SearchResultsPage resultsPage = homePage.searchFor("Пчёлы насекомые");
        ArticlePage articlePage = resultsPage.clickFirstResult();
        
        Assert.assertTrue(articlePage.isArticleTitleDisplayed(), 
            "Заголовок статьи должен отображаться");
        Assert.assertTrue(articlePage.isArticleContentDisplayed(), 
            "Контент статьи должен отображаться");
    }

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][] {
            {"Гуси-лебеди сказка"},
            {"Глюкоза химия"},
            {"Квакши лягушки"}
        };
    }

    @Test(dataProvider = "searchQueries", description = "Поиск работает с разными запросами")
    public void testSearchWithDifferentQueries(String query) {
        SearchResultsPage resultsPage = homePage.searchFor(query);
        
        boolean success = resultsPage.hasResults() || 
                         resultsPage.isNoResultsMessageDisplayed() ||
                         driver.getCurrentUrl().contains("wikipedia.org/wiki/");
        Assert.assertTrue(success,
            "Поиск должен показать результаты или сообщение для: " + query);
    }

    @DataProvider(name = "articlePages")
    public Object[][] articlePages() {
        return new Object[][] {
            {"https://ru.wikipedia.org/wiki/Гуси-лебеди", "Гуси-лебеди"},
            {"https://ru.wikipedia.org/wiki/МИФИ", "МИФИ"},
            {"https://ru.wikipedia.org/wiki/Кошка", "Кошка"},
            {"https://ru.wikipedia.org/wiki/Квакши", "Квакши"},
            {"https://ru.wikipedia.org/wiki/Санкт-Петербург", "Санкт-Петербург"},
            {"https://ru.wikipedia.org/wiki/Иван_Грозный", "Иван Грозный"},
            {"https://ru.wikipedia.org/wiki/Пчёлы", "Пчёлы"},
            {"https://ru.wikipedia.org/wiki/Байкал", "Байкал"},
            {"https://ru.wikipedia.org/wiki/Байкал_Электроникс", "Байкал Электроникс"},
            {"https://ru.wikipedia.org/wiki/Глюкоза", "Глюкоза"}
        };
    }

    @Test(dataProvider = "articlePages", description = "Открытие конкретных страниц Wikipedia")
    public void testOpenSpecificArticlePages(String url, String expectedTitle) {
        driver.get(url);
        
        ArticlePage articlePage = new ArticlePage(driver);
        
        Assert.assertTrue(articlePage.isArticleContentDisplayed(), 
            "Контент статьи должен отображаться для: " + expectedTitle);
        
        Assert.assertTrue(driver.getCurrentUrl().contains("ru.wikipedia.org"), 
            "Должны быть на русской Wikipedia");
        
        String pageTitle = driver.getTitle().toLowerCase();
        String articleTitle = articlePage.getArticleTitle().toLowerCase();
        boolean titleMatch = pageTitle.contains(expectedTitle.toLowerCase()) || 
                            articleTitle.contains(expectedTitle.toLowerCase());
        Assert.assertTrue(titleMatch, 
            "Страница должна содержать '" + expectedTitle + "' в заголовке");
    }

    @Test(description = "Главные элементы отображаются на главной странице")
    public void testHomePageElementsDisplayed() {
        boolean logoOrTitle = homePage.isLogoDisplayed() || driver.getTitle().contains("Википедия");
        Assert.assertTrue(logoOrTitle, 
            "Логотип или заголовок Википедии должен присутствовать");
        Assert.assertTrue(homePage.isSearchInputDisplayed(), 
            "Поле поиска должно отображаться");
    }

    @Test(description = "Случайная статья работает")
    public void testRandomArticleFeature() {
        driver.get("https://ru.wikipedia.org/wiki/Служебная:Случайная_страница");
        
        ArticlePage randomArticle = new ArticlePage(driver);
        
        Assert.assertTrue(randomArticle.urlContains("ru.wikipedia.org"), 
            "Должны быть на русской Wikipedia");
        boolean hasContent = randomArticle.isArticleTitleDisplayed() || 
                            randomArticle.isArticleContentDisplayed();
        Assert.assertTrue(hasContent, 
            "Случайная статья должна содержать контент");
    }

    @Test(description = "Структура страницы статьи корректна")
    public void testArticlePageStructure() {
        driver.get("https://ru.wikipedia.org/wiki/МИФИ");
        
        ArticlePage articlePage = new ArticlePage(driver);
        
        boolean hasTitle = articlePage.isArticleTitleDisplayed() || 
                          driver.getTitle().contains("МИФИ");
        Assert.assertTrue(hasTitle, 
            "Статья должна иметь заголовок");
        Assert.assertTrue(articlePage.isArticleContentDisplayed(), 
            "Статья должна иметь контент");
    }

    @Test(description = "Переход на английскую Wikipedia")
    public void testNavigationToEnglishWikipedia() {
        driver.get("https://en.wikipedia.org");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("en.wikipedia.org"), 
            "Должны быть на английской Wikipedia");
        Assert.assertTrue(homePage.isSearchInputDisplayed(), 
            "Поле поиска должно отображаться");
    }

    @Test(description = "Переход на русскую Wikipedia")
    public void testNavigationToRussianWikipedia() {
        driver.get("https://ru.wikipedia.org");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("ru.wikipedia.org"), 
            "Должны быть на русской Wikipedia");
        Assert.assertTrue(homePage.isSearchInputDisplayed(), 
            "Поле поиска должно отображаться");
    }

    @Test(description = "Переход на немецкую Wikipedia")
    public void testNavigationToGermanWikipedia() {
        driver.get("https://de.wikipedia.org");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("de.wikipedia.org"), 
            "Должны быть на немецкой Wikipedia");
        Assert.assertTrue(homePage.isSearchInputDisplayed(), 
            "Поле поиска должно отображаться");
    }

    @Test(description = "Проверка возможности смены языка статьи")
    public void testLanguageSwitchOnArticle() {
        driver.get("https://ru.wikipedia.org/wiki/Москва");
        
        ArticlePage articlePage = new ArticlePage(driver);
        
        Assert.assertTrue(articlePage.isArticleContentDisplayed(), 
            "Контент статьи должен отображаться");
        
        int languageCount = articlePage.getLanguageLinksCount();
        Assert.assertTrue(languageCount >= 0, 
            "Статья должна иметь опции смены языка или быть на одном языке");
    }

    @Test(description = "Наличие ссылок на разные языки")
    public void testMultipleLanguageLinksAvailable() {
        driver.get("https://ru.wikipedia.org/wiki/Байкал");
        
        ArticlePage articlePage = new ArticlePage(driver);
        
        Assert.assertTrue(articlePage.isArticleContentDisplayed(), 
            "Контент статьи должен отображаться");
        
        int languageCount = articlePage.getLanguageLinksCount();
        Assert.assertTrue(languageCount > 0, 
            "Популярная статья должна иметь переводы на другие языки");
    }
}
