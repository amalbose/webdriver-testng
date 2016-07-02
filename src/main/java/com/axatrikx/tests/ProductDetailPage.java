package com.axatrikx.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.axatrikx.WebDriverHelper;

public class ProductDetailPage {
    private static final String URL = "http://amazon.com";
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void beforeClass(String browser) {
        driver = new WebDriverHelper().initDriver(browser);
    }

    @Test
    public void verifyProductDetails() {
        driver.get(URL);
        // perform search
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("selenium webdriver" + Keys.ENTER);
        // click on the required result
        String product = "Selenium WebDriver Practical Guide";
        driver.findElement(By.xpath("//h2[text()='" + product + "']")).click();

        // verify title
        String productTitle = driver.findElement(By.id("productTitle")).getText();
        Assert.assertEquals(product, productTitle, "Incorrect product title");
        Assert.assertTrue(driver.findElement(By.id("reviewStarsLinkedCustomerReviews")).isDisplayed(),
                "Rating is not shown");
    }
}
