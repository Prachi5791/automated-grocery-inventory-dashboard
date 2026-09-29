package com.prachi.inventory.selenium;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class ScreenshotOnFailureExtension
        implements BeforeTestExecutionCallback, AfterTestExecutionCallback, AfterEachCallback {

    private final Supplier<WebDriver> driverSupplier;

    

    public ScreenshotOnFailureExtension(
            Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void afterEach(ExtensionContext context) {

        WebDriver driver = driverSupplier.get();

        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        // Nothing required before the test.
    }

    @Override
    public void afterTestExecution(ExtensionContext context)
            throws Exception {

        if (context.getExecutionException().isEmpty()) {
            return;
        }

        WebDriver driver = driverSupplier.get();

        if (driver == null) {
            return;
        }

        if (!(driver instanceof TakesScreenshot)) {
            return;
        }

        saveScreenshot(driver, context);
    }

    private void saveScreenshot(
            WebDriver driver,
            ExtensionContext context) {

        try {
            Path screenshotDirectory =
                    Path.of("target", "screenshots");

            Files.createDirectories(screenshotDirectory);

            String testName = context.getDisplayName()
                    .replaceAll("[^a-zA-Z0-9-_]", "_");

            String timestamp = LocalDateTime.now()
                    .format(
                            DateTimeFormatter.ofPattern(
                                    "yyyyMMdd_HHmmss"
                            )
                    );

            Path destination = screenshotDirectory.resolve(
                    testName + "_" + timestamp + ".png"
            );

            Path source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE)
                    .toPath();

            Files.copy(
                    source,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Failure screenshot saved to: "
                            + destination.toAbsolutePath()
            );

        } catch (IOException e) {

            System.err.println(
                    "Could not save failure screenshot: "
                            + e.getMessage()
            );
        }
    }
}