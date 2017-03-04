package ru.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    //"//*[@id='app-']/a/span[2]"
    @Test
    public void ClickElementsByArray() {
        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        loginLiteCart.WaitTillElementIsDisplayed();
        List<WebElement> Element = driver.findElements(By.cssSelector("li#app-"));
        //Создаем лист веб элеменов
        System.out.println("ElementsSize = " + Element.size());
        //Узнаем размер листа
        for (int i = 0; i < Element.size(); i++) {
            Element = driver.findElements(By.cssSelector("li#app-"));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Element.get(i).click();
            //Нажиммаем ну каждый элемент в листе
            loginLiteCart.ElementIsPresent();
        }
    }

    @Test
    public void CheckStickers() {
        driver.navigate().to("http://localhost/litecart/en/");
        loginLiteCart.WaitWebSiteLoaded();
        List<WebElement> productsList = driver.findElements(By.cssSelector("a.link[title*=Duck]"));//
        //В цикле проверяем, какое количество стикеров имеет каждый товар
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).findElements(By.cssSelector("[class^=sticker]")).size() != 1) {
                //Если количесвто стикеров не равно 1
            }

        }
    }

}












