// // // package com.prachi.inventory.selenium;

// // // import org.junit.jupiter.api.Test;
// // // import org.junit.jupiter.api.extension.RegisterExtension;
// // // import org.openqa.selenium.By;
// // // import org.openqa.selenium.WebElement;
// // // import org.openqa.selenium.support.ui.Select;
// // // import org.openqa.selenium.support.ui.WebDriverWait;

// // // import java.time.Duration;

// // // import static org.junit.jupiter.api.Assertions.assertEquals;
// // // import static org.junit.jupiter.api.Assertions.assertTrue;

// // // class StockTransactionTest extends BaseSeleniumTest {

// // //     @RegisterExtension
// // //     final ScreenshotOnFailureExtension screenshotExtension =
// // //             new ScreenshotOnFailureExtension(() -> driver);

// // //     @Test
// // //     void performStockInAndStockOut() {

// // //         WebDriverWait wait =
// // //                 new WebDriverWait(driver, Duration.ofSeconds(10));

// // //         // ---------------------------------------------------------
// // //         // STEP 1: Open inventory and record original Rice quantity
// // //         // ---------------------------------------------------------

// // //         driver.get(BASE_URL + "/items");

// // //         WebElement riceRow = wait.until(
// // //                 webDriver -> webDriver.findElement(
// // //                         By.xpath(
// // //                                 "//tr[td/strong[normalize-space()='rice']]"
// // //                         )
// // //                 )
// // //         );

// // //         int originalQuantity = Integer.parseInt(
// // //                 riceRow.findElements(By.tagName("td"))
// // //                         .get(3)
// // //                         .getText()
// // //                         .trim()
// // //         );

// // //         // ---------------------------------------------------------
// // //         // STEP 2: Open Transactions page
// // //         // ---------------------------------------------------------

// // //         driver.get(BASE_URL + "/transactions");

// // //         // ---------------------------------------------------------
// // //         // STEP 3: Stock In 5 units of Rice
// // //         // ---------------------------------------------------------

// // //         WebElement stockInForm = wait.until(
// // //                 webDriver -> webDriver.findElement(
// // //                         By.xpath(
// // //                                 "//form[contains(@action,"
// // //                                         + "'/transactions/stock-in')]"
// // //                         )
// // //                 )
// // //         );

// // //         Select stockInItem = new Select(
// // //                 stockInForm.findElement(By.name("itemId"))
// // //         );

// // //         String riceOptionText = stockInItem.getOptions()
// // //                 .stream()
// // //                 .filter(option ->
// // //                         option.getText()
// // //                                 .toLowerCase()
// // //                                 .startsWith("rice")
// // //                 )
// // //                 .findFirst()
// // //                 .orElseThrow(() ->
// // //                         new IllegalStateException(
// // //                                 "Rice was not found in Stock In item list"
// // //                         )
// // //                 )
// // //                 .getText();

// // //         stockInItem.selectByVisibleText(riceOptionText);

// // //         stockInForm.findElement(By.name("quantity"))
// // //                 .sendKeys("5");

// // //         stockInForm.findElement(By.name("note"))
// // //                 .sendKeys("Selenium TC04 stock in");

// // //         stockInForm.findElement(
// // //                 By.cssSelector("button.btn-in")
// // //         ).click();

// // //         // ---------------------------------------------------------
// // //         // STEP 4: Verify Stock In transaction
// // //         // ---------------------------------------------------------

// // //         wait.until(
// // //                 webDriver ->
// // //                         webDriver.getPageSource()
// // //                                 .contains("Selenium TC04 stock in")
// // //         );

// // //         assertTrue(
// // //                 driver.getPageSource()
// // //                         .contains("Selenium TC04 stock in"),
// // //                 "Stock In transaction was not recorded"
// // //         );

// // //         // ---------------------------------------------------------
// // //         // STEP 5: Stock Out 5 units of Rice
// // //         // ---------------------------------------------------------

// // //         WebElement stockOutForm = wait.until(
// // //                 webDriver -> webDriver.findElement(
// // //                         By.xpath(
// // //                                 "//form[contains(@action,"
// // //                                         + "'/transactions/stock-out')]"
// // //                         )
// // //                 )
// // //         );

// // //         Select stockOutItem = new Select(
// // //                 stockOutForm.findElement(By.name("itemId"))
// // //         );

// // //         stockOutItem.selectByVisibleText(riceOptionText);

// // //         stockOutForm.findElement(By.name("quantity"))
// // //                 .sendKeys("5");

// // //         stockOutForm.findElement(By.name("note"))
// // //                 .sendKeys("Selenium TC04 stock out");

// // //         stockOutForm.findElement(
// // //                 By.cssSelector("button.btn-out")
// // //         ).click();

// // //         // ---------------------------------------------------------
// // //         // STEP 6: Verify Stock Out transaction
// // //         // ---------------------------------------------------------

// // //         wait.until(
// // //                 webDriver ->
// // //                         webDriver.getPageSource()
// // //                                 .contains("Selenium TC04 stock out")
// // //         );

// // //         assertTrue(
// // //                 driver.getPageSource()
// // //                         .contains("Selenium TC04 stock out"),
// // //                 "Stock Out transaction was not recorded"
// // //         );

// // //         // ---------------------------------------------------------
// // //         // STEP 7: Return to Inventory
// // //         // ---------------------------------------------------------

// // //         driver.get(BASE_URL + "/items");

// // //         // ---------------------------------------------------------
// // //         // STEP 8: Verify Rice quantity returned to original value
// // //         // ---------------------------------------------------------

// // //         WebElement finalRiceRow = wait.until(
// // //                 webDriver -> webDriver.findElement(
// // //                         By.xpath(
// // //                                 "//tr[td/strong[normalize-space()='rice']]"
// // //                         )
// // //                 )
// // //         );

// // //         int finalQuantity = Integer.parseInt(
// // //                 finalRiceRow.findElements(By.tagName("td"))
// // //                         .get(3)
// // //                         .getText()
// // //                         .trim()
// // //         );

// // //         assertEquals(
// // //                 originalQuantity,
// // //                 finalQuantity,
// // //                 "Rice quantity did not return to the original value"
// // //         );
// // //     }
// // // }


// // package com.prachi.inventory.selenium;

// // import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.extension.RegisterExtension;
// // import org.openqa.selenium.By;
// // import org.openqa.selenium.WebElement;
// // import org.openqa.selenium.support.ui.Select;
// // import org.openqa.selenium.support.ui.WebDriverWait;

// // import java.time.Duration;

// // import static org.junit.jupiter.api.Assertions.assertEquals;
// // import static org.junit.jupiter.api.Assertions.assertTrue;

// // class StockTransactionTest extends BaseSeleniumTest {

// //     @RegisterExtension
// //     final ScreenshotOnFailureExtension screenshotExtension =
// //             new ScreenshotOnFailureExtension(() -> driver);

// //     @Test
// //     void performStockInAndStockOut() {

// //         WebDriverWait wait =
// //                 new WebDriverWait(driver, Duration.ofSeconds(10));

// //         // ---------------------------------------------------------
// //         // STEP 1: Open inventory and record original Rice quantity
// //         // ---------------------------------------------------------

// //         driver.get(BASE_URL + "/items");

// //         WebElement riceRow = wait.until(
// //                 webDriver -> webDriver.findElement(
// //                         By.xpath(
// //                                 "//tr[td/strong[normalize-space()='rice']]"
// //                         )
// //                 )
// //         );

// //         int originalQuantity = Integer.parseInt(
// //                 riceRow.findElements(By.tagName("td"))
// //                         .get(3)
// //                         .getText()
// //                         .trim()
// //         );

// //         // ---------------------------------------------------------
// //         // STEP 2: Open Transactions page
// //         // ---------------------------------------------------------

// //         driver.get(BASE_URL + "/transactions");

// //         // ---------------------------------------------------------
// //         // STEP 3: Locate Stock In form
// //         // ---------------------------------------------------------

// //         WebElement stockInForm = wait.until(
// //                 webDriver -> webDriver.findElement(
// //                         By.xpath(
// //                                 "//form[contains(@action,"
// //                                         + "'/transactions/stock-in')]"
// //                         )
// //                 )
// //         );

// //         Select stockInItem = new Select(
// //                 stockInForm.findElement(By.name("itemId"))
// //         );

// //         // Find Rice option and store its item ID.
// //         WebElement riceOption = stockInItem.getOptions()
// //                 .stream()
// //                 .filter(option ->
// //                         option.getText()
// //                                 .toLowerCase()
// //                                 .startsWith("rice")
// //                 )
// //                 .findFirst()
// //                 .orElseThrow(() ->
// //                         new IllegalStateException(
// //                                 "Rice was not found in Stock In item list"
// //                         )
// //                 );

// //         String riceItemId = riceOption.getAttribute("value");

// //         // Select Rice using its stable database ID.
// //         stockInItem.selectByValue(riceItemId);

// //         // ---------------------------------------------------------
// //         // STEP 4: Stock In 5 units
// //         // ---------------------------------------------------------

// //         stockInForm.findElement(By.name("quantity"))
// //                 .sendKeys("5");

// //         stockInForm.findElement(By.name("note"))
// //                 .sendKeys("Selenium TC04 stock in");

// //         stockInForm.findElement(
// //                 By.cssSelector("button.btn-in")
// //         ).click();

// //         // ---------------------------------------------------------
// //         // STEP 5: Verify Stock In transaction
// //         // ---------------------------------------------------------

// //         wait.until(
// //                 webDriver ->
// //                         webDriver.getPageSource()
// //                                 .contains("Selenium TC04 stock in")
// //         );

// //         assertTrue(
// //                 driver.getPageSource()
// //                         .contains("Selenium TC04 stock in"),
// //                 "Stock In transaction was not recorded"
// //         );

// //         // ---------------------------------------------------------
// //         // STEP 6: Locate Stock Out form
// //         // ---------------------------------------------------------

// //         WebElement stockOutForm = wait.until(
// //                 webDriver -> webDriver.findElement(
// //                         By.xpath(
// //                                 "//form[contains(@action,"
// //                                         + "'/transactions/stock-out')]"
// //                         )
// //                 )
// //         );

// //         Select stockOutItem = new Select(
// //                 stockOutForm.findElement(By.name("itemId"))
// //         );

// //         // Select the same Rice item using its stable ID.
// //         stockOutItem.selectByValue(riceItemId);

// //         // ---------------------------------------------------------
// //         // STEP 7: Stock Out 5 units
// //         // ---------------------------------------------------------

// //         stockOutForm.findElement(By.name("quantity"))
// //                 .sendKeys("5");

// //         stockOutForm.findElement(By.name("note"))
// //                 .sendKeys("Selenium TC04 stock out");

// //         stockOutForm.findElement(
// //                 By.cssSelector("button.btn-out")
// //         ).click();

// //         // ---------------------------------------------------------
// //         // STEP 8: Verify Stock Out transaction
// //         // ---------------------------------------------------------

// //         wait.until(
// //                 webDriver ->
// //                         webDriver.getPageSource()
// //                                 .contains("Selenium TC04 stock out")
// //         );

// //         assertTrue(
// //                 driver.getPageSource()
// //                         .contains("Selenium TC04 stock out"),
// //                 "Stock Out transaction was not recorded"
// //         );

// //         // ---------------------------------------------------------
// //         // STEP 9: Return to Inventory
// //         // ---------------------------------------------------------

// //         driver.get(BASE_URL + "/items");

// //         // ---------------------------------------------------------
// //         // STEP 10: Verify quantity returned to original value
// //         // ---------------------------------------------------------

// //         WebElement finalRiceRow = wait.until(
// //                 webDriver -> webDriver.findElement(
// //                         By.xpath(
// //                                 "//tr[td/strong[normalize-space()='rice']]"
// //                         )
// //                 )
// //         );

// //         int finalQuantity = Integer.parseInt(
// //                 finalRiceRow.findElements(By.tagName("td"))
// //                         .get(3)
// //                         .getText()
// //                         .trim()
// //         );

// //         assertEquals(
// //                 originalQuantity,
// //                 finalQuantity,
// //                 "Rice quantity did not return to the original value"
// //         );
// //     }
// // }

// package com.prachi.inventory.selenium;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.RegisterExtension;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.Select;
// import org.openqa.selenium.support.ui.WebDriverWait;

// import java.time.Duration;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// class StockTransactionTest extends BaseSeleniumTest {

//     @RegisterExtension
//     final ScreenshotOnFailureExtension screenshotExtension =
//             new ScreenshotOnFailureExtension(() -> driver);

//     @Test
//     void performStockInAndStockOut() {

//         WebDriverWait wait =
//                 new WebDriverWait(driver, Duration.ofSeconds(10));

//         // ---------------------------------------------------------
//         // STEP 1: Open inventory and record original Rice quantity
//         // ---------------------------------------------------------

//         driver.get(BASE_URL + "/items");

//         WebElement riceRow = wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//tr[td/strong[normalize-space()='rice']]"
//                         )
//                 )
//         );

//         int originalQuantity = Integer.parseInt(
//                 riceRow.findElements(By.tagName("td"))
//                         .get(3)
//                         .getText()
//                         .trim()
//         );

//         // ---------------------------------------------------------
//         // STEP 2: Open Transactions page
//         // ---------------------------------------------------------

//         driver.get(BASE_URL + "/transactions");

//         // ---------------------------------------------------------
//         // STEP 3: Locate Rice ID from Stock In form
//         // ---------------------------------------------------------

//         WebElement stockInSelectElement = wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//form[contains(@action,"
//                                         + "'/transactions/stock-in')]"
//                                         + "//select[@name='itemId']"
//                         )
//                 )
//         );

//         Select stockInItem =
//                 new Select(stockInSelectElement);

//         WebElement riceOption = stockInItem.getOptions()
//                 .stream()
//                 .filter(option ->
//                         option.getText()
//                                 .toLowerCase()
//                                 .startsWith("rice")
//                 )
//                 .findFirst()
//                 .orElseThrow(() ->
//                         new IllegalStateException(
//                                 "Rice was not found in Stock In item list"
//                         )
//                 );

//         String riceItemId =
//                 riceOption.getAttribute("value");

//         stockInItem.selectByValue(riceItemId);

//         // ---------------------------------------------------------
//         // STEP 4: Stock In 5 units
//         // ---------------------------------------------------------

//         wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//form[contains(@action,"
//                                         + "'/transactions/stock-in')]"
//                                         + "//input[@name='quantity']"
//                         )
//                 )
//         ).sendKeys("5");

//         wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//form[contains(@action,"
//                                         + "'/transactions/stock-in')]"
//                                         + "//textarea[@name='note']"
//                         )
//                 )
//         ).sendKeys("Selenium TC04 stock in");

//         wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.cssSelector("button.btn-in")
//                 )
//         ).click();

//         // ---------------------------------------------------------
//         // STEP 5: Verify Stock In transaction
//         // ---------------------------------------------------------

//         wait.until(
//                 webDriver ->
//                         webDriver.getPageSource()
//                                 .contains("Selenium TC04 stock in")
//         );

//         assertTrue(
//                 driver.getPageSource()
//                         .contains("Selenium TC04 stock in"),
//                 "Stock In transaction was not recorded"
//         );

//         // ---------------------------------------------------------
//         // STEP 6: Locate Stock Out item selector FRESH
//         // ---------------------------------------------------------

//         By stockOutSelectLocator =
//         By.xpath(
//                 "//form[contains(@action,"
//                         + "'/transactions/stock-out')]"
//                         + "//select[@name='itemId']"
//         );

//         wait.until(webDriver -> {
//         try {
//                 WebElement element =
//                         webDriver.findElement(stockOutSelectLocator);

//                 Select select = new Select(element);

//                 select.selectByValue(riceItemId);

//                 return true;

//         } catch (org.openqa.selenium.StaleElementReferenceException e) {
//                 return false;
//         }
//         });

        

//         // ---------------------------------------------------------
//         // STEP 7: Stock Out 5 units
//         // ---------------------------------------------------------

//         wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//form[contains(@action,"
//                                         + "'/transactions/stock-out')]"
//                                         + "//input[@name='quantity']"
//                         )
//                 )
//         ).sendKeys("5");

//         wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//form[contains(@action,"
//                                         + "'/transactions/stock-out')]"
//                                         + "//textarea[@name='note']"
//                         )
//                 )
//         ).sendKeys("Selenium TC04 stock out");

//         wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.cssSelector("button.btn-out")
//                 )
//         ).click();

//         // ---------------------------------------------------------
//         // STEP 8: Verify Stock Out transaction
//         // ---------------------------------------------------------

//         wait.until(
//                 webDriver ->
//                         webDriver.getPageSource()
//                                 .contains("Selenium TC04 stock out")
//         );

//         assertTrue(
//                 driver.getPageSource()
//                         .contains("Selenium TC04 stock out"),
//                 "Stock Out transaction was not recorded"
//         );

//         // ---------------------------------------------------------
//         // STEP 9: Return to Inventory
//         // ---------------------------------------------------------

//         driver.get(BASE_URL + "/items");

//         // ---------------------------------------------------------
//         // STEP 10: Verify quantity returned to original value
//         // ---------------------------------------------------------

//         WebElement finalRiceRow = wait.until(
//                 webDriver -> webDriver.findElement(
//                         By.xpath(
//                                 "//tr[td/strong[normalize-space()='rice']]"
//                         )
//                 )
//         );

//         int finalQuantity = Integer.parseInt(
//                 finalRiceRow.findElements(By.tagName("td"))
//                         .get(3)
//                         .getText()
//                         .trim()
//         );

//         assertEquals(
//                 originalQuantity,
//                 finalQuantity,
//                 "Rice quantity did not return to the original value"
//         );
//     }
// }

// package com.prachi.inventory.selenium;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.RegisterExtension;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.Select;
// import org.openqa.selenium.support.ui.WebDriverWait;

// import java.time.Duration;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// class StockTransactionTest extends BaseSeleniumTest {

//     @RegisterExtension
//     final ScreenshotOnFailureExtension screenshotExtension =
//             new ScreenshotOnFailureExtension(() -> driver);

//     @Test
//     void performStockInAndStockOut() {

//         WebDriverWait wait =
//                 new WebDriverWait(driver, Duration.ofSeconds(10));

//         // ============================================================
//         // STEP 1: Open Inventory page
//         // ============================================================

//         driver.get(BASE_URL + "/items");

//         // ============================================================
//         // STEP 2: Read original Rice quantity
//         // Quantity is the 4th <td> in the Rice row.
//         // ============================================================

//         By riceQuantityLocator = By.xpath(
//                 "//tr[td/strong[normalize-space()='rice']]/td[4]"
//         );

//         String originalQuantityText = wait.until(
//                 webDriver -> {
//                     try {
//                         WebElement quantityCell =
//                                 webDriver.findElement(riceQuantityLocator);

//                         String text = quantityCell.getText().trim();

//                         if (text.matches("-?\\d+")) {
//                             return text;
//                         }

//                         return null;

//                     } catch (org.openqa.selenium.StaleElementReferenceException e) {
//                         return null;
//                     }
//                 }
//         );

//         int originalQuantity =
//                 Integer.parseInt(originalQuantityText);

//         System.out.println(
//                 "Original Rice quantity = " + originalQuantity
//         );

//         // ============================================================
//         // STEP 3: Open Transactions page
//         // ============================================================

//         driver.get(BASE_URL + "/transactions");

//         // ============================================================
//         // STEP 4: Find Rice in Stock In dropdown
//         // ============================================================

//         By stockInSelectLocator = By.xpath(
//                 "//form[contains(@action,'/transactions/stock-in')]"
//                         + "//select[@name='itemId']"
//         );

//         String riceItemId = wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement selectElement =
//                                 webDriver.findElement(stockInSelectLocator);

//                         Select select =
//                                 new Select(selectElement);

//                         return select.getOptions()
//                                 .stream()
//                                 .filter(option ->
//                                         option.getText()
//                                                 .trim()
//                                                 .toLowerCase()
//                                                 .startsWith("rice")
//                                 )
//                                 .map(option ->
//                                         option.getAttribute("value")
//                                 )
//                                 .findFirst()
//                                 .orElse(null);

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return null;
//                     }
//                 }
//         );

//         assertTrue(
//                 riceItemId != null && !riceItemId.isBlank(),
//                 "Rice was not found in Stock In item list"
//         );

//         System.out.println(
//                 "Rice item ID = " + riceItemId
//         );

//         // ============================================================
//         // STEP 5: Perform Stock In +5
//         // ============================================================

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement selectElement =
//                                 webDriver.findElement(stockInSelectLocator);

//                         Select select =
//                                 new Select(selectElement);

//                         select.selectByValue(riceItemId);

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         By stockInQuantityLocator = By.xpath(
//                 "//form[contains(@action,'/transactions/stock-in')]"
//                         + "//input[@name='quantity']"
//         );

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement quantityInput =
//                                 webDriver.findElement(
//                                         stockInQuantityLocator
//                                 );

//                         quantityInput.clear();
//                         quantityInput.sendKeys("5");

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         By stockInNoteLocator = By.xpath(
//                 "//form[contains(@action,'/transactions/stock-in')]"
//                         + "//textarea[@name='note']"
//         );

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement noteInput =
//                                 webDriver.findElement(
//                                         stockInNoteLocator
//                                 );

//                         noteInput.clear();
//                         noteInput.sendKeys(
//                                 "Selenium TC04 stock in"
//                         );

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         By stockInButtonLocator =
//                 By.cssSelector("button.btn-in");

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement button =
//                                 webDriver.findElement(
//                                         stockInButtonLocator
//                                 );

//                         button.click();

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         // ============================================================
//         // STEP 6: Verify Stock In transaction
//         // ============================================================

//         wait.until(
//                 webDriver ->
//                         webDriver.getPageSource()
//                                 .contains("Selenium TC04 stock in")
//         );

//         assertTrue(
//                 driver.getPageSource()
//                         .contains("Selenium TC04 stock in"),
//                 "Stock In transaction was not recorded"
//         );

//         System.out.println(
//                 "Stock In +5 transaction recorded successfully."
//         );

//         // ============================================================
//         // STEP 7: Perform Stock Out -5
//         // ============================================================

//         By stockOutSelectLocator = By.xpath(
//                 "//form[contains(@action,'/transactions/stock-out')]"
//                         + "//select[@name='itemId']"
//         );

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement selectElement =
//                                 webDriver.findElement(
//                                         stockOutSelectLocator
//                                 );

//                         Select select =
//                                 new Select(selectElement);

//                         select.selectByValue(riceItemId);

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         By stockOutQuantityLocator = By.xpath(
//                 "//form[contains(@action,'/transactions/stock-out')]"
//                         + "//input[@name='quantity']"
//         );

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement quantityInput =
//                                 webDriver.findElement(
//                                         stockOutQuantityLocator
//                                 );

//                         quantityInput.clear();
//                         quantityInput.sendKeys("5");

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         By stockOutNoteLocator = By.xpath(
//                 "//form[contains(@action,'/transactions/stock-out')]"
//                         + "//textarea[@name='note']"
//         );

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement noteInput =
//                                 webDriver.findElement(
//                                         stockOutNoteLocator
//                                 );

//                         noteInput.clear();
//                         noteInput.sendKeys(
//                                 "Selenium TC04 stock out"
//                         );

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         By stockOutButtonLocator =
//                 By.cssSelector("button.btn-out");

//         wait.until(
//                 webDriver -> {

//                     try {

//                         WebElement button =
//                                 webDriver.findElement(
//                                         stockOutButtonLocator
//                                 );

//                         button.click();

//                         return true;

//                     } catch (
//                             org.openqa.selenium.StaleElementReferenceException e
//                     ) {
//                         return false;
//                     }
//                 }
//         );

//         // ============================================================
//         // STEP 8: Verify Stock Out transaction
//         // ============================================================

//         wait.until(
//                 webDriver ->
//                         webDriver.getPageSource()
//                                 .contains("Selenium TC04 stock out")
//         );

//         assertTrue(
//                 driver.getPageSource()
//                         .contains("Selenium TC04 stock out"),
//                 "Stock Out transaction was not recorded"
//         );

//         System.out.println(
//                 "Stock Out -5 transaction recorded successfully."
//         );

//         // ============================================================
// // STEP 10: Return to Inventory page
// // ============================================================

// driver.get(BASE_URL + "/items");

// // ============================================================
// // STEP 11: Wait until the inventory page shows the expected
// // final quantity for Rice.
// // ============================================================

// By riceQuantityLocator1 = By.xpath(
//         "//tr[td/strong[normalize-space()='rice']]/td[4]"
// );

// boolean quantityRestored = wait.until(
//         webDriver -> {
//             try {
//                 WebElement quantityCell =
//                         webDriver.findElement(riceQuantityLocator1);

//                 String text = quantityCell.getText().trim();

//                 if (!text.matches("-?\\d+")) {
//                     return false;
//                 }

//                 int displayedQuantity =
//                         Integer.parseInt(text);

//                 System.out.println(
//                         "Rice quantity currently displayed = "
//                                 + displayedQuantity
//                 );

//                 return displayedQuantity == originalQuantity;

//             } catch (
//                     org.openqa.selenium.StaleElementReferenceException e
//             ) {
//                 return false;
//             }
//         }
// );

// assertTrue(
//         quantityRestored,
//         "Rice quantity did not return to the original value"
// );

// String finalQuantityText =
//         driver.findElement(riceQuantityLocator1)
//                 .getText()
//                 .trim();

// int finalQuantity =
//         Integer.parseInt(finalQuantityText);

// System.out.println(
//         "Final Rice quantity = " + finalQuantity
// );

// assertEquals(
//         originalQuantity,
//         finalQuantity,
//         "Rice quantity did not return to the original value"
// );

// System.out.println(
//         "TC04 PASSED: Rice quantity returned to "
//                 + originalQuantity
// );}}
package com.prachi.inventory.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
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

        // ============================================================
        // STEP 1: Open Inventory page
        // ============================================================

        driver.get(BASE_URL + "/items");

        // ============================================================
        // STEP 2: Read original Rice quantity
        // Quantity is the 4th <td> in the Rice row.
        // ============================================================

        By riceQuantityLocator = By.xpath(
                "//tr[td/strong[normalize-space()='rice']]/td[4]"
        );

        String originalQuantityText = wait.until(
                webDriver -> {
                    try {
                        WebElement quantityCell =
                                webDriver.findElement(riceQuantityLocator);

                        String text = quantityCell.getText().trim();

                        if (text.matches("-?\\d+")) {
                            return text;
                        }

                        return null;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return null;
                    }
                }
        );

        int originalQuantity =
                Integer.parseInt(originalQuantityText);

        System.out.println(
                "Original Rice quantity = " + originalQuantity
        );

        // ============================================================
        // STEP 3: Open Transactions page
        // ============================================================

        driver.get(BASE_URL + "/transactions");

        // ============================================================
        // STEP 4: Find Rice in Stock In dropdown
        // ============================================================

        By stockInSelectLocator = By.xpath(
                "//form[contains(@action,'/transactions/stock-in')]"
                        + "//select[@name='itemId']"
        );

        String riceItemId = wait.until(
                webDriver -> {
                    try {

                        WebElement selectElement =
                                webDriver.findElement(
                                        stockInSelectLocator
                                );

                        Select select =
                                new Select(selectElement);

                        return select.getOptions()
                                .stream()
                                .filter(option ->
                                        option.getText()
                                                .trim()
                                                .toLowerCase()
                                                .startsWith("rice")
                                )
                                .map(option ->
                                        option.getAttribute("value")
                                )
                                .findFirst()
                                .orElse(null);

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return null;
                    }
                }
        );

        assertTrue(
                riceItemId != null && !riceItemId.isBlank(),
                "Rice was not found in Stock In item list"
        );

        System.out.println(
                "Rice item ID = " + riceItemId
        );

        // ============================================================
        // STEP 5: Select Rice for Stock In
        // ============================================================

        wait.until(
                webDriver -> {
                    try {

                        WebElement selectElement =
                                webDriver.findElement(
                                        stockInSelectLocator
                                );

                        Select select =
                                new Select(selectElement);

                        select.selectByValue(riceItemId);

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 6: Enter Stock In quantity
        // ============================================================

        By stockInQuantityLocator = By.xpath(
                "//form[contains(@action,'/transactions/stock-in')]"
                        + "//input[@name='quantity']"
        );

        wait.until(
                webDriver -> {
                    try {

                        WebElement quantityInput =
                                webDriver.findElement(
                                        stockInQuantityLocator
                                );

                        quantityInput.clear();
                        quantityInput.sendKeys("5");

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 7: Enter Stock In note
        // ============================================================

        By stockInNoteLocator = By.xpath(
                "//form[contains(@action,'/transactions/stock-in')]"
                        + "//textarea[@name='note']"
        );

        wait.until(
                webDriver -> {
                    try {

                        WebElement noteInput =
                                webDriver.findElement(
                                        stockInNoteLocator
                                );

                        noteInput.clear();
                        noteInput.sendKeys(
                                "Selenium TC04 stock in"
                        );

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 8: Submit Stock In
        // ============================================================

        By stockInButtonLocator =
                By.cssSelector("button.btn-in");

        wait.until(
                webDriver -> {
                    try {

                        WebElement button =
                                webDriver.findElement(
                                        stockInButtonLocator
                                );

                        button.click();

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 9: Verify Stock In transaction
        // ============================================================

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

        System.out.println(
                "Stock In +5 transaction recorded successfully."
        );

        // ============================================================
        // STEP 10: Select Rice for Stock Out
        // ============================================================

        By stockOutSelectLocator = By.xpath(
                "//form[contains(@action,'/transactions/stock-out')]"
                        + "//select[@name='itemId']"
        );

        wait.until(
                webDriver -> {
                    try {

                        WebElement selectElement =
                                webDriver.findElement(
                                        stockOutSelectLocator
                                );

                        Select select =
                                new Select(selectElement);

                        select.selectByValue(riceItemId);

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 11: Enter Stock Out quantity
        // ============================================================

        By stockOutQuantityLocator = By.xpath(
                "//form[contains(@action,'/transactions/stock-out')]"
                        + "//input[@name='quantity']"
        );

        wait.until(
                webDriver -> {
                    try {

                        WebElement quantityInput =
                                webDriver.findElement(
                                        stockOutQuantityLocator
                                );

                        quantityInput.clear();
                        quantityInput.sendKeys("5");

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 12: Enter Stock Out note
        // ============================================================

        By stockOutNoteLocator = By.xpath(
                "//form[contains(@action,'/transactions/stock-out')]"
                        + "//textarea[@name='note']"
        );

        wait.until(
                webDriver -> {
                    try {

                        WebElement noteInput =
                                webDriver.findElement(
                                        stockOutNoteLocator
                                );

                        noteInput.clear();
                        noteInput.sendKeys(
                                "Selenium TC04 stock out"
                        );

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 13: Submit Stock Out
        // ============================================================

        By stockOutButtonLocator =
                By.cssSelector("button.btn-out");

        wait.until(
                webDriver -> {
                    try {

                        WebElement button =
                                webDriver.findElement(
                                        stockOutButtonLocator
                                );

                        button.click();

                        return true;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 14: Verify Stock Out transaction
        // ============================================================

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

        System.out.println(
                "Stock Out -5 transaction recorded successfully."
        );

        // ============================================================
        // STEP 15: Open Inventory page again
        // ============================================================

        driver.get(BASE_URL + "/items");

        // Force a fresh browser request.
        driver.navigate().refresh();

        // ============================================================
        // STEP 16: Wait for the Rice row to be present
        // ============================================================

        wait.until(
                webDriver -> {
                    try {
                        return webDriver.findElement(
                                By.xpath(
                                        "//tr[td/strong[normalize-space()='rice']]"
                                )
                        ).isDisplayed();

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return false;
                    }
                }
        );

        // ============================================================
        // STEP 17: Read final Rice quantity
        // ============================================================

        String finalQuantityText = wait.until(
                webDriver -> {
                    try {

                        WebElement quantityCell =
                                webDriver.findElement(
                                        riceQuantityLocator
                                );

                        String text =
                                quantityCell.getText().trim();

                        if (!text.matches("-?\\d+")) {
                            return null;
                        }

                        System.out.println(
                                "Rice quantity currently displayed = "
                                        + text
                        );

                        return text;

                    } catch (
                            org.openqa.selenium.StaleElementReferenceException e
                    ) {
                        return null;
                    }
                }
        );

        int finalQuantity =
                Integer.parseInt(finalQuantityText);

        System.out.println(
                "Final Rice quantity = " + finalQuantity
        );

        // ============================================================
        // STEP 18: Verify quantity returned to original value
        // ============================================================

        assertEquals(
                originalQuantity,
                finalQuantity,
                "Rice quantity did not return to the original value"
        );

        System.out.println(
                "TC04 PASSED: Rice quantity returned to "
                        + originalQuantity
        );
    }
}