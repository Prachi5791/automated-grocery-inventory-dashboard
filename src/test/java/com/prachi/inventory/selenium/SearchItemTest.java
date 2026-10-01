package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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
        driver.findElement(By.name("keyword"))
                .sendKeys(searchItem);

        // 3. Submit search form
        driver.findElement(
                By.cssSelector(
                        "form.search-section button[type='submit']"
                )
        ).click();

        // 4. Define locator for the search result
        By riceResult = By.xpath(
                "//strong[contains(" +
                        "translate(normalize-space(), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                        "'abcdefghijklmnopqrstuvwxyz'), " +
                        "'rice')]"
        );

        // 5. Wait until the search result is displayed
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        boolean resultDisplayed = wait.until(webDriver -> {
            try {
                return webDriver.findElement(riceResult).isDisplayed();
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        // 6. Verify the searched item is displayed
        assertTrue(
                resultDisplayed,
                "Searched item 'rice' was not displayed"
        );
    }
}