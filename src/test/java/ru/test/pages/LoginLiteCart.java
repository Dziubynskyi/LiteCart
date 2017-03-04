package ru.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Comp on 2/28/2017.
 */
public class LoginLiteCart extends Page {
    @FindBy(name = "username")
    public WebElement UserName;

    @FindBy(name = "password")
    public WebElement Password;

    @FindBy(name = "login")
    public WebElement LoginButton;

    @FindBy( xpath = "//*[@id='chart-sales-monthly']")
    public WebElement IsOnManePageButton;

    @FindBy( xpath = "//h1")
    public WebElement Teg;

    @FindBy(xpath = "//*[@id='logotype-wrapper']/a/img")
    public WebElement Web;




    public LoginLiteCart(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    public void SelectLogin(String Login) {
        setElementText(UserName, Login);
    }

    public void SelectPassword(String Pass) {
        setElementText(Password, Pass);
    }
    public void PressLoginButton() {
        clickElement(LoginButton);
    }
    public boolean IsOnLiteCartPage() {
      return  exists(IsOnManePageButton);

    }
    public void WaitTillElementIsDisplayed(){
       waitUntilIsLoadedCustomTime(IsOnManePageButton,40);
    }

    public boolean ElementIsPresent() {
        return exists(Teg);
    }

    public void WaitWebSiteLoaded() {
        waitUntilIsLoaded(Web);
    }
}


