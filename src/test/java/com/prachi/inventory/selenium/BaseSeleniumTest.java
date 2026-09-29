package com.prachi.inventory.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseSeleniumTest {

    protected WebDriver driver;

    protected static final String BASE_URL =
            "http://localhost:8082/automated-grocery-inventory-dashboard";

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--window-size=1440,1000");

        driver = new ChromeDriver(options);
    }
}