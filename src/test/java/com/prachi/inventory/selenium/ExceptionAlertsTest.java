package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionAlertsTest extends BaseSeleniumTest {

    @RegisterExtension
    final ScreenshotOnFailureExtension screenshotExtension =
            new ScreenshotOnFailureExtension(() -> driver);

    @Test
    void verifyExceptionAlerts() {

        String alertItemName =
                "SeleniumAlertItem_" + System.currentTimeMillis();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        /*
         * Step 1: Create an item with zero quantity.
         * This should appear as an OUT OF STOCK alert.
         */
        driver.get(BASE_URL + "/items");

        driver.findElement(By.name("name"))
                .sendKeys(alertItemName);

        driver.findElement(By.name("category"))
                .sendKeys("Selenium Testing");

        driver.findElement(By.name("quantity"))
                .sendKeys("0");

        driver.findElement(By.name("unit"))
                .sendKeys("packet");

        driver.findElement(By.name("price"))
                .sendKeys("50");

        driver.findElement(By.name("reorderLevel"))
                .sendKeys("5");

        driver.findElement(
                By.cssSelector("#add-form button[type='submit']")
        ).click();

        /*
         * Step 2: Wait until the item is added successfully.
         */
        wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//strong[contains(normalize-space(),'"
                                        + alertItemName
                                        + "')]"
                        )
                )
        );

        /*
         * Step 3: Open the Alerts page.
         */
        driver.get(BASE_URL + "/alerts");

        /*
         * Step 4: Verify the Alerts page loaded.
         */
        assertTrue(
                driver.getTitle()
                        .contains("Alerts"),
                "Alerts page did not load"
        );

        assertTrue(
                driver.getPageSource()
                        .contains("Inventory Alerts"),
                "Inventory Alerts heading was not found"
        );

        /*
         * Step 5: Verify the newly created item
         * appears as an OUT OF STOCK alert.
         */
        WebElement alertItem = wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//div[contains(@class,'out-alert')]"
                                        + "//h3[normalize-space()='"
                                        + alertItemName
                                        + "']"
                        )
                )
        );

        assertTrue(
                alertItem.isDisplayed(),
                "Newly created out-of-stock item was not displayed in alerts"
        );

        /*
         * Step 6: Verify the OUT OF STOCK badge.
         */
        WebElement alertCard = alertItem.findElement(
                By.xpath("./ancestor::div[contains(@class,'out-alert')]")
        );

        assertTrue(
                alertCard.getText()
                        .contains("OUT OF STOCK"),
                "OUT OF STOCK badge was not displayed"
        );

        /*
         * Step 7: Verify the current quantity is 0.
         */
        assertTrue(
                alertCard.getText()
                        .contains("Current quantity:"),
                "Current quantity information was not displayed"
        );

        assertTrue(
                alertCard.getText()
                        .contains("0"),
                "Alert did not show quantity as 0"
        );
    }
}