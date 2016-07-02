package com.axatrikx.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.axatrikx.WebDriverHelper;

public class SearchPage {

    private static final String URL = "http://amazon.com";
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void beforeClass(String browser) {
        driver = new WebDriverHelper().initDriver(browser);
    }

    @BeforeMethod
    public void navigateToHomePage() {
        driver.get(URL);
    }

    @Test
    @Parameters("title")
    public void verifyNavigation(String actualTitle) {
        driver.get(URL);
        String title = driver.getTitle();
        // Verify title
        Assert.assertEquals(actualTitle, title, "Incorrect Title");
    }

    @Test (dependsOnMethods = { "verifyNavigation" })
    public void testSearchOnPage() {
        // navigate to page
        String searchTerm = "selenium webdriver";
        performSearch(searchTerm);
        String title = driver.getTitle();

        // Verify title
        Assert.assertTrue(title.contains(searchTerm),
                "Result page title doesnt contain search term (" + searchTerm + ")");

        // Verify if 16 results are present
        int resCount = driver.findElements(By.xpath("//li[contains(@class,'s-result-item')]")).size();
        Assert.assertEquals(resCount, 16, "Incorrect number of results");
    }

    @Test(dataProvider = "searchTerms")
    public void testSearchesOnPage(String searchTerm, Boolean isValid) {
        performSearch(searchTerm);
        String title = driver.getTitle();

        // Verify title
        Assert.assertTrue(title.contains(searchTerm),
                "Result page title doesnt contain search term (" + searchTerm + ")");

        if (isValid) {
            // Verify if at least 16 results are present
            int resCount = driver.findElements(By.xpath("//li[contains(@class,'s-result-item')]")).size();
            Assert.assertTrue(resCount >= 16, "Incorrect number of results");
        } else {
            String noResultText = driver.findElement(By.id("noResultsTitle")).getText();
            Assert.assertEquals(noResultText, "Your search \"" + searchTerm + "\" did not match any products.",
                    "Incorrect error message");
        }
    }

    @DataProvider(name = "searchTerms")
    public static Object[][] getSearchTerms() {
        return new Object[][] { { "Selenium WebDriver", true }, { "Cucumber", true }, { "Game of Thrones", true },
                { "Chuck Palahniuk", true }, { "~~~~~~~~~~~~~~~~", false } };
    }

    private void performSearch(String searchTerm) {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchTerm + Keys.ENTER);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
