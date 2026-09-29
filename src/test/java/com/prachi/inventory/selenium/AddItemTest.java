package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddItemTest extends BaseSeleniumTest {

    @RegisterExtension
    final ScreenshotOnFailureExtension screenshotExtension =
            new ScreenshotOnFailureExtension(() -> driver);

    @Test
    void addNewInventoryItem() {

        String itemName =
                "SeleniumTestItem_" + System.currentTimeMillis();

        // 1. Open inventory page
        driver.get(BASE_URL + "/items");

        // 2. Fill Item Name
        driver.findElement(By.name("name"))
                .sendKeys(itemName);

        // 3. Fill Category
        driver.findElement(By.name("category"))
                .sendKeys("Selenium Testing");

        // 4. Fill Quantity
        driver.findElement(By.name("quantity"))
                .sendKeys("20");

        // 5. Fill Unit
        driver.findElement(By.name("unit"))
                .sendKeys("packet");

        // 6. Fill Price
        driver.findElement(By.name("price"))
                .sendKeys("50");

        // 7. Fill Reorder Level
        driver.findElement(By.name("reorderLevel"))
                .sendKeys("5");

        // 8. Submit ONLY the Add Item form
        driver.findElement(
                By.cssSelector("#add-form button[type='submit']")
        ).click();

        // 9. Wait until the newly added item appears
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement addedItem = wait.until(
                webDriver -> webDriver.findElement(
                        By.xpath(
                                "//strong[contains(normalize-space(),'"
                                        + itemName
                                        + "')]"
                        )
                )
        );

        // 10. Verify item is displayed
        assertTrue(
                addedItem.isDisplayed(),
                "Newly added item was not displayed in inventory"
        );
    }
}