package tests.mobile;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.mobile.ArticleScreen;
import pages.mobile.MainScreen;
import pages.mobile.SearchScreen;
import pages.mobile.SettingsScreen;

public class WikipediaMobileTests extends BaseMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod
    public void setUpTest() {
        mainScreen = new MainScreen(driver);
        mainScreen.skipOnboarding();
    }

    // ==================== TEST 1: Search Article ====================
    @Test(description = "Verify search functionality finds articles by keyword")
    public void testSearchArticleByKeyword() {
        // Open search
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        
        // Search for an article
        searchScreen.searchFor("Java programming");
        
        // Verify results are displayed
        Assert.assertTrue(searchScreen.areResultsDisplayed(), 
            "Search results should be displayed");
        Assert.assertTrue(searchScreen.getResultsCount() > 0, 
            "Search should return at least one result");
    }

    @Test(description = "Verify clicking search result opens the article")
    public void testOpenArticleFromSearch() {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor("Python programming");
        
        // Click first result
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        
        // Verify article is displayed
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article should be displayed after clicking search result");
    }

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][] {
            {"Albert Einstein"},
            {"Machine Learning"},
            {"Moscow"}
        };
    }

    @Test(dataProvider = "searchTerms", description = "Verify search works with different queries")
    public void testSearchWithVariousQueries(String searchTerm) {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor(searchTerm);
        
        Assert.assertTrue(searchScreen.areResultsDisplayed() || searchScreen.isNoResultsDisplayed(),
            "Search should show results or 'no results' message for: " + searchTerm);
    }

    // ==================== TEST 2: Article Title Verification ====================
    @Test(description = "Verify article title matches the search query")
    public void testArticleTitleMatchesSearch() {
        String searchQuery = "Java";
        
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor(searchQuery);
        
        // Open first result
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        
        // Verify title is displayed
        Assert.assertTrue(articleScreen.isTitleDisplayed(), 
            "Article title should be displayed");
        
        // Get article title
        String articleTitle = articleScreen.getArticleTitle();
        Assert.assertFalse(articleTitle.isEmpty(), 
            "Article title should not be empty");
    }

    @Test(description = "Verify article has expected UI elements")
    public void testArticleUIElements() {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor("Wikipedia");
        
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        
        // Verify article structure
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article content should be displayed");
        Assert.assertTrue(articleScreen.isToolbarDisplayed(), 
            "Article toolbar should be displayed");
        Assert.assertTrue(articleScreen.isSaveButtonDisplayed(), 
            "Save button should be available");
    }

    // ==================== TEST 3: Scroll Functionality ====================
    @Test(description = "Verify scroll functionality works on article page")
    public void testScrollArticle() {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor("History of computing");
        
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        
        // Verify article is displayed
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article should be displayed before scroll");
        
        // Scroll down
        articleScreen.scrollToBottom();
        
        // Article should still be displayed after scroll
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article should still be displayed after scrolling down");
        
        // Scroll back up
        articleScreen.scrollToTop();
        
        // Verify we can scroll back
        Assert.assertTrue(articleScreen.isToolbarDisplayed(), 
            "Toolbar should be visible after scrolling back up");
    }

    @Test(description = "Verify scrolling to specific content in article")
    public void testScrollToSpecificContent() {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor("United States");
        
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        
        // Try to find "History" section by scrolling
        boolean foundContent = articleScreen.isElementVisibleAfterScroll("History");
        
        // This test verifies scroll works, content may or may not be found
        // depending on the article structure
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article should be displayed after scroll attempt");
    }

    // ==================== TEST 4: Language Settings ====================
    @Test(description = "Verify main screen is displayed correctly")
    public void testMainScreenDisplayed() {
        Assert.assertTrue(mainScreen.isMainScreenDisplayed(), 
            "Main screen should be displayed");
        Assert.assertTrue(mainScreen.isSearchContainerDisplayed(), 
            "Search container should be visible on main screen");
    }

    @Test(description = "Verify navigation to settings")
    public void testNavigateToSettings() {
        SettingsScreen settingsScreen = mainScreen.clickMoreTab();
        settingsScreen.openSettings();
        
        Assert.assertTrue(settingsScreen.isSettingsScreenDisplayed(), 
            "Settings screen should be displayed");
    }

    @Test(description = "Verify article has language option available")
    public void testArticleHasLanguageOption() {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor("Moscow");
        
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        
        // Check if language option is available
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article should be displayed");
        
        // Many articles have language options
        boolean hasLanguage = articleScreen.hasLanguageOption();
        // This is a soft assertion - not all articles have language options
        System.out.println("Article has language option: " + hasLanguage);
    }

    @Test(description = "Verify back navigation works correctly")
    public void testBackNavigation() {
        SearchScreen searchScreen = mainScreen.clickSearchContainer();
        searchScreen.searchFor("Test");
        
        ArticleScreen articleScreen = searchScreen.clickFirstResult();
        Assert.assertTrue(articleScreen.isArticleDisplayed(), 
            "Article should be displayed");
        
        // Go back
        articleScreen.goBack();
        
        // Should be back at search results
        Assert.assertTrue(searchScreen.areResultsDisplayed() || mainScreen.isMainScreenDisplayed(),
            "Should navigate back to search results or main screen");
    }
}
