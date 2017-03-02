package ru.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.test.TestBase;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Comp on 2/28/2017.
 */
public class TestAll extends TestBase {

    public LoginLiteCart loginLiteCart;

    @BeforeMethod
    public void initPageObjects() {
        loginLiteCart = PageFactory.initElements(driver, LoginLiteCart.class);

    }

    @Test
    public void LoginToLiteCart() {
        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        loginLiteCart.WaitTillElementIsDisplayed();
        Assert.assertTrue(loginLiteCart.IsOnLiteCartPage());
    }
    @Test
    public void ClickElementsByArray() {
        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        loginLiteCart.WaitTillElementIsDisplayed();
        List<WebElement> Element = driver.findElements(By.xpath("//*[@id='app-']/a/span[2]"));
        System.out.println("ElementsSize" + Element.size());
        for (int i = 0; i < Element.size(); i++) {
            Element = driver.findElements(By.xpath("//*[@id='app-']/a/span[2]"));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         Element.get(i).click();
        loginLiteCart.ElementIsPresent();
        }

    }

}












