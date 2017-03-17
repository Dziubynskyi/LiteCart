package ru.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.test.TestBase;

import java.util.Set;

/**
 * Created by Comp on 3/17/2017.
 */
public class SwitchWindowsInBrowser extends TestBase {
    public LoginLiteCart loginLiteCart;

    @BeforeMethod
    public void initPageObjects() {
        loginLiteCart = PageFactory.initElements(driver, LoginLiteCart.class);

    }

    @Test
    public void SwitchWindowsInBrowser() {
        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        loginLiteCart.WaitTillElementIsDisplayed();
        driver.findElement(By.linkText("Countries")).click();
        driver.findElement(By.cssSelector("#content > div > a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Add New Country')]")));
        String mainWindow = driver.getWindowHandle();
        System.out.println("mainWindow : " + mainWindow);
        Set<String> oldWindows = driver.getWindowHandles();
        driver.findElement(By.cssSelector("tr:nth-child(2) > td > a")).click();
        String newWindow = wait.until((WebDriver driver) -> thereIsWindowOtherThan(oldWindows));
        driver.switchTo().window(newWindow);
        driver.close();
        driver.switchTo().window(mainWindow);


    }


    public String thereIsWindowOtherThan(Set<String> oldWindowHandles) {
        Set<String> newWindowHandles = driver.getWindowHandles();
        System.out.println("windowHandles = " + newWindowHandles.size());
        for (String newWindow : newWindowHandles) {
            if (!oldWindowHandles.contains(newWindow)) {
                System.out.println("new window:  = " + newWindow);
                return newWindow;
            }
        }
        return null;
    }
}
