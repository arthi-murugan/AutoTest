package com.autotest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShoppingTest {

    private WebDriver driver;
    private String baseUrl;
    private WebElement element;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        baseUrl = "https://www.apotea.se/";
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testShoppingFlow() throws Exception {
        // Open Website
        driver.get(baseUrl);
        driver.findElement(By.id("c-p-bn")).click();

        //Find search field and search for product
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.click();
        searchField.sendKeys("aco");
        searchField.sendKeys(Keys.ENTER);

        //Open a product and add to shopping cart
        driver.findElement(By.linkText("ACO Intimate Care Cleansing Wash 250 ml")).click();
        driver.findElement(By.className("purchase-button")).click();

        //Increase quantity
        driver.findElement(By.xpath("//div[@class='buy-button buy-button-add']")).click();

        //Proceed to pay
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cart-head-product")));
        driver.findElement(By.id("cart-header-to-checkout")).click();

        //Enter Email and postal code
        WebElement emailField = driver.findElement(By.id("Email"));
        emailField.click();
        emailField.sendKeys("test@example.com");
        //emailField.sendKeys(Keys.ENTER);
        WebElement deliveryField = driver.findElement(By.id("DeliveryZip"));
        deliveryField.click();
        deliveryField.sendKeys("14156");

        //Continue and enter mobile number
        driver.findElement(By.xpath("//div[@class='btn btn-success pointer']")).click();
        WebElement mobileField = driver.findElement(By.id("Mobile"));
        mobileField.sendKeys("0734800688");
        mobileField.sendKeys(Keys.ENTER);

        //Proceed to checkout
        driver.findElement(By.id("checkout-payment-validate-1")).click();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }
}
