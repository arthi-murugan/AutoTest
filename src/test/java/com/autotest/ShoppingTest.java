package com.autotest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

        // assert home page title
        String actualTitle = driver.getTitle();
        String expectedTitle = "Apotek på nätet. Köp läkemedel billigt och fraktfritt | apotea.se";
        Assert.assertEquals(actualTitle, expectedTitle);

        //Find search field
        WebElement searchField = driver.findElement(By.name("q"));

        // assert search field is displayed.
        Assert.assertEquals(true, searchField.isDisplayed());
        searchField.click();

        // Search for a product
        searchField.sendKeys("aco");
        searchField.sendKeys(Keys.ENTER);

        //Open a product
        driver.findElement(By.linkText("ACO Intimate Care Cleansing Wash 250 ml")).click();

        // assert product page title
        String productTitle = driver.getTitle();
        String expectedProductTitle = "Köp ACO Intimate Care Cleansing Wash 250 ml på apotea.se";
        Assert.assertEquals(productTitle, expectedProductTitle);

        // assert buy button displayed.
        WebElement buyButton = driver.findElement(By.className("purchase-button"));
        Assert.assertEquals(true, buyButton.isDisplayed());

        //add to shopping cart
        buyButton.click();

        //Increase quantity
        driver.findElement(By.xpath("//div[@class='buy-button buy-button-add']")).click();

        //Proceed to pay
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cart-head-product")));
        driver.findElement(By.id("cart-header-to-checkout")).click();

        // assert product content is available
        WebElement cartContent = driver.findElement(By.xpath("//div[@id='cart-content']"));
        Assert.assertEquals(true, cartContent.isDisplayed());

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

        // assert shipping method is selected.
        WebElement shippingMethod = driver.findElement(By.name("ShippingMethodID"));
        Assert.assertEquals(true, shippingMethod.isSelected());

        // assert sms notification is selected.
        WebElement sms = driver.findElement(By.id("sms"));
        Assert.assertEquals(true, sms.isSelected());

        // assert email notification is not selected
        WebElement email = driver.findElement(By.id("email"));
        Assert.assertNotEquals(true, email.isSelected());

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

