package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchItemTest extends BaseSeleniumTest {

    @RegisterExtension
    final ScreenshotOnFailureExtension screenshotExtension =
            new ScreenshotOnFailureExtension(() -> driver);

    @Test
    void searchInventoryItem() {

        String searchItem = "rice";

        // 1. Open inventory page
        driver.get(BASE_URL + "/items");

        // 2. Enter item name in search field
        WebElement searchInput =
                driver.findElement(By.name("keyword"));

        searchInput.sendKeys(searchItem);

        // 3. Submit search form
        driver.findElement(
                By.cssSelector(
                        "form.search-section button[type='submit']"
                )
        ).click();

        // 4. Wait for search result
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement result = wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//strong[contains(" +
                                        "translate(normalize-space(), " +
                                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                                        "'abcdefghijklmnopqrstuvwxyz'), " +
                                        "'rice')]"
                        )
                )
        );

        // 5. Verify the searched item is displayed
        assertTrue(
                result.isDisplayed(),
                "Searched item 'rice' was not displayed"
        );
    }
}