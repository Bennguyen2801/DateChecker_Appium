package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import com.google.firebase.firestore.util.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class SampleTest {

    private AndroidDriver driver;

    @Before
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Galaxy Nexus")
                .setPlatformName("android")
                .setAppPackage("com.demo.datechecker")
                .setAppActivity("com.demo.datechecker.MainActivity")
                .setAutomationName("UiAutomator2");
        driver = new AndroidDriver(getUrl(), options);
    }

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // Ensure the test fails if URL is incorrect
        }
    }

    @Test
    public void testValidDate() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("22");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("06");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2024");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        WebElement result = driver.findElement(By.id("com.demo.datechecker:id/resultTextView"));
        assertNotNull(result);
        assertEquals("22/06/2024 is a valid date.", result.getText());
    }

    @Test
    public void testInvalidDay() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("32");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("06");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2024");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        String pageSource = driver.getPageSource();
        assertNotNull("Page source should not be null", pageSource);

        assertTrue("Toast message should contain 'Day must be an integer between 1 and 31.'",
                pageSource.contains("Day must be an integer between 1 and 31."));
    }

    @Test
    public void testInvalidMonth() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("22");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("13");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2024");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        String pageSource = driver.getPageSource();
        assertNotNull("Page source should not be null", pageSource);

        assertTrue("Toast message should contain 'Day must be an integer between 1 and 31.'",
                pageSource.contains("Month must be an integer between 1 and 12."));
    }

    @Test
    public void testInvalidYear() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("22");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("06");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("800");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        String pageSource = driver.getPageSource();
        assertNotNull("Page source should not be null", pageSource);

        assertTrue("Toast message should contain 'Day must be an integer between 1 and 31.'",
                pageSource.contains("Year must be an integer between 1000 and 3000."));
    }

    @Test
    public void testLeapYear() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("29");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("02");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2024");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        WebElement result = driver.findElement(By.id("com.demo.datechecker:id/resultTextView"));
        assertNotNull(result);
        assertEquals("29/02/2024 is a valid date.", result.getText());
    }

    @Test
    public void testNonLeapYear() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("29");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("02");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2023");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        WebElement result = driver.findElement(By.id("com.demo.datechecker:id/resultTextView"));
        assertNotNull(result);
        assertEquals("29/02/2023 is not a valid date.", result.getText());
    }

    @Test
    public void testEmptyDay() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("06");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2024");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        String pageSource = driver.getPageSource();
        assertNotNull("Page source should not be null", pageSource);

        assertTrue("Toast message should contain 'Day must be an integer between 1 and 31.'",
                pageSource.contains("All inputs must be integer numbers."));
    }

    @Test
    public void testEmptyMonth() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("22");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("2024");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        String pageSource = driver.getPageSource();
        assertNotNull("Page source should not be null", pageSource);

        assertTrue("Toast message should contain 'Day must be an integer between 1 and 31.'",
                pageSource.contains("All inputs must be integer numbers."));
    }

    @Test
    public void testEmptyYear() {
        WebElement dayInput = driver.findElement(By.id("com.demo.datechecker:id/dayInput"));
        dayInput.sendKeys("22");

        WebElement monthInput = driver.findElement(By.id("com.demo.datechecker:id/monthInput"));
        monthInput.sendKeys("06");

        WebElement yearInput = driver.findElement(By.id("com.demo.datechecker:id/yearInput"));
        yearInput.sendKeys("");

        WebElement checkDateButton = driver.findElement(By.id("com.demo.datechecker:id/checkDateButton"));
        checkDateButton.click();

        String pageSource = driver.getPageSource();
        assertNotNull("Page source should not be null", pageSource);

        assertTrue("Toast message should contain 'Day must be an integer between 1 and 31.'",
                pageSource.contains("All inputs must be integer numbers."));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
