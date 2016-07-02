package com.axatrikx;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverHelper {

    public WebDriver initDriver(String browser) {
        WebDriver driver;
        if ("chrome".equalsIgnoreCase(browser)) {
            ClassLoader classLoader = getClass().getClassLoader();
            System.setProperty("webdriver.chrome.driver", classLoader.getResource("chromedriver.exe").getPath());
            driver = new ChromeDriver();
        } else {
            // we will use Firefox as default
            driver = new FirefoxDriver();
        }
        // maximize
        driver.manage().window().maximize();
        return driver;
    }
    
    // other methods
}
