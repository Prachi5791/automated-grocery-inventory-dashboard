package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockTransactionTest extends BaseSeleniumTest {

    @RegisterExtension
    final ScreenshotOnFailureExtension screenshotExtension =
            new ScreenshotOnFailureExtension(() -> driver);

    @Test
    void performStockInAndStockOut() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        // ---------------------------------------------------------
        // STEP 1: Open inventory and record original Rice quantity
        // ---------------------------------------------------------

        driver.get(BASE_URL + "/items");

        WebElement riceRow = wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//tr[td/strong[normalize-space()='rice']]"
                        )
                )
        );

        int originalQuantity = Integer.parseInt(
                riceRow.findElements(By.tagName("td"))
                        .get(3)
                        .getText()
                        .trim()
        );

        // ---------------------------------------------------------
        // STEP 2: Open Transactions page
        // ---------------------------------------------------------

        driver.get(BASE_URL + "/transactions");

        // ---------------------------------------------------------
        // STEP 3: Locate Rice ID from Stock In form
        // ---------------------------------------------------------

        String riceItemId = wait.until(webDriver -> {
            try {
                WebElement selectElement = webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-in')]"
                                        + "//select[@name='itemId']"
                        )
                );

                Select select = new Select(selectElement);

                return select.getOptions()
                        .stream()
                        .filter(option ->
                                option.getText()
                                        .toLowerCase()
                                        .startsWith("rice")
                        )
                        .findFirst()
                        .map(option -> option.getAttribute("value"))
                        .orElse(null);

            } catch (StaleElementReferenceException e) {
                return null;
            }
        });

        assertTrue(
                riceItemId != null && !riceItemId.isBlank(),
                "Rice was not found in Stock In item list"
        );

        // ---------------------------------------------------------
        // STEP 4: Stock In 5 units
        // ---------------------------------------------------------

        wait.until(webDriver -> {
            try {
                WebElement selectElement = webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-in')]"
                                        + "//select[@name='itemId']"
                        )
                );

                Select select = new Select(selectElement);
                select.selectByValue(riceItemId);

                return true;

            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-in')]"
                                        + "//input[@name='quantity']"
                        )
                )
        ).sendKeys("5");

        wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-in')]"
                                        + "//textarea[@name='note']"
                        )
                )
        ).sendKeys("Selenium TC04 stock in");

        wait.until(
                webDriver -> webDriver.findElement(
                        By.cssSelector("button.btn-in")
                )
        ).click();

        // ---------------------------------------------------------
        // STEP 5: Verify Stock In transaction
        // ---------------------------------------------------------

        wait.until(
                webDriver ->
                        webDriver.getPageSource()
                                .contains("Selenium TC04 stock in")
        );

        assertTrue(
                driver.getPageSource()
                        .contains("Selenium TC04 stock in"),
                "Stock In transaction was not recorded"
        );

        // ---------------------------------------------------------
        // STEP 6: Locate Stock Out item selector FRESH
        // ---------------------------------------------------------

        wait.until(webDriver -> {
            try {
                WebElement selectElement = webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-out')]"
                                        + "//select[@name='itemId']"
                        )
                );

                Select select = new Select(selectElement);

                select.selectByValue(riceItemId);

                return true;

            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        // ---------------------------------------------------------
        // STEP 7: Stock Out 5 units
        // ---------------------------------------------------------

        wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-out')]"
                                        + "//input[@name='quantity']"
                        )
                )
        ).sendKeys("5");

        wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//form[contains(@action,"
                                        + "'/transactions/stock-out')]"
                                        + "//textarea[@name='note']"
                        )
                )
        ).sendKeys("Selenium TC04 stock out");

        wait.until(
                webDriver -> webDriver.findElement(
                        By.cssSelector("button.btn-out")
                )
        ).click();

        // ---------------------------------------------------------
        // STEP 8: Verify Stock Out transaction
        // ---------------------------------------------------------

        wait.until(
                webDriver ->
                        webDriver.getPageSource()
                                .contains("Selenium TC04 stock out")
        );

        assertTrue(
                driver.getPageSource()
                        .contains("Selenium TC04 stock out"),
                "Stock Out transaction was not recorded"
        );

        // ---------------------------------------------------------
        // STEP 9: Return to Inventory
        // ---------------------------------------------------------

        driver.get(BASE_URL + "/items");

        // ---------------------------------------------------------
        // STEP 10: Verify quantity returned to original value
        // ---------------------------------------------------------

        WebElement finalRiceRow = wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//tr[td/strong[normalize-space()='rice']]"
                        )
                )
        );

        int finalQuantity = Integer.parseInt(
                finalRiceRow.findElements(By.tagName("td"))
                        .get(3)
                        .getText()
                        .trim()
        );

        assertEquals(
                originalQuantity,
                finalQuantity,
                "Rice quantity did not return to the original value"
        );
    }
}