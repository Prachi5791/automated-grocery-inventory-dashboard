package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class InventoryViewTest extends BaseSeleniumTest {

    @RegisterExtension
    final ScreenshotOnFailureExtension screenshotExtension =
            new ScreenshotOnFailureExtension(() -> driver);

    @Test
    void verifyInventoryPageLoads() {

        // Step 1: Open inventory page
        driver.get(BASE_URL + "/items");

        // Step 2: Verify page title
        assertEquals(
                "GroceryFlow | Inventory Dashboard",
                driver.getTitle()
        );

       

        // Step 3: Verify inventory heading is displayed
        assertTrue(
                driver.getPageSource().contains("Inventory"),
                "Inventory heading/content was not found"
        );

        // Step 4: Verify inventory table exists
        assertTrue(
                driver.getPageSource().contains("<table"),
                "Inventory table was not found"
        );

        // Step 5: Verify the page URL
        assertTrue(
                driver.getCurrentUrl().contains("/items"),
                "User is not on the inventory page"
        );
    }
}