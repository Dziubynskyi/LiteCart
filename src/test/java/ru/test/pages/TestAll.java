package ru.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.test.TestBase;

import java.util.ArrayList;
import java.util.Collections;
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

    @Test
    public void CheckPageAndPrice() { // тест проверяет что стоимости товара на первой странице совпадают со второй
        driver.navigate().to("http://localhost/litecart/en/");
        String NameOnFirstPage = driver.findElement(By.xpath("//*[@id=\"box-campaigns\"]/div/ul/li/a[1]/div[2]")).getText();
        String CampaignPrice1 = driver.findElement(By.xpath("//*[@id=\"box-campaigns\"]/div/ul/li/a[1]/div[4]/strong")).getText();
        String RegularPrice1 = driver.findElement(By.cssSelector("#box-campaigns > div > ul > li > a.link > div.price-wrapper > s")).getText();

        driver.navigate().to("https://demo.litecart.net/en/this-n-that-c-2/yellow-duck-p-1");
        String NameOnSecondPage = driver.findElement(By.cssSelector("h1.title")).getText();
        String CampaignPrice2 = driver.findElement(By.className("campaign-price")).getText();
        String RegularPrice2 = driver.findElement(By.className("regular-price")).getText();

        if ((NameOnFirstPage.equals(NameOnSecondPage)) != true) {
            System.out.println("Name of duck not same");
        }
        if ((CampaignPrice1.equals(CampaignPrice2)) != true) {
            System.out.println("CampaignPrice not same");
        }
        if ((RegularPrice1.equals(RegularPrice2)) != true) {
            System.out.println("RegularPrice not same");
        }
    }

    @Test
    public void countriesAlphabeticallySorted() {
        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        loginLiteCart.WaitTillElementIsDisplayed();
        Assert.assertTrue(loginLiteCart.IsOnLiteCartPage());
        driver.findElement(By.linkText("Countries")).click();
        // Вытягиваем список линков на страны
        List<WebElement> countriesList = driver.findElements(By.cssSelector("td:nth-child(5) > a"));

        // Создаем два пустых арейЛиста для стран
        List<String> countryNames = new ArrayList<String>();
        List<String> sortedCountryNames = new ArrayList<String>();

        for (int i = 0; i < countriesList.size(); i++) {
            //String temp = countriesList.get(i).getText(); вытянуть страны через текст
            String temp = countriesList.get(i).getAttribute("textContent");// вытянуть страны через атрибут
            countryNames.add(temp); // кладем список стран уже в формате стринг в ерей лист
            sortedCountryNames.add(temp); // кладем список стран уже в формате стринг в ерей лист который будем сортировать и сравнивать с countryNames
        }

        Collections.sort(sortedCountryNames); // cортируем страны

        // в массиве сравниваем отсортированные страны и нет
        for (int i = 0; i < countryNames.size(); i++) {
            if (!countryNames.get(i).equals(sortedCountryNames.get(i))) {
                System.out.println("Countries are not alphabetically sorted. Country # " + i + " is on wrong place.");
            }
        }
    }

    @Test
    public void CheckGeoZones() {
        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        loginLiteCart.WaitTillElementIsDisplayed();
        Assert.assertTrue(loginLiteCart.IsOnLiteCartPage());
        driver.findElement(By.linkText("Geo Zones")).click();
        driver.findElement(By.cssSelector("td:nth-child(3) > a")).click();
        List<WebElement> ZonesOfCanada = driver.findElements(By.cssSelector("td:nth-child(3) > select > option[selected]"));// локатор который выбирает только выбранные значения в выпадающем списке
        List<String> CanadaZonesNames = new ArrayList<String>();
        List<String> sortedCanadaZonesNames = new ArrayList<String>();

        for (int i = 0; i < ZonesOfCanada.size(); i++) {
            String temp = ZonesOfCanada.get(i).getText();
            CanadaZonesNames.add(temp);
            sortedCanadaZonesNames.add(temp);
        }
        Collections.sort(sortedCanadaZonesNames);
        for (int i = 0; i < CanadaZonesNames.size(); i++) {
            if (!CanadaZonesNames.get(i).equals(sortedCanadaZonesNames.get(i))) {
                System.out.println("ZonesOfCanada are not alphabetically sorted. Zone # " + i + " is on wrong place.");
            }
        }
        driver.navigate().back();
        driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(3) > a")).click();
        List<WebElement> ZonesOfUnitetStites = driver.findElements(By.cssSelector("td:nth-child(3) > select > option[selected]"));// локатор который выбирает только выбранные значения в выпадающем списке

        List<String> ZonesOfUnitetStates = new ArrayList<String>();
        List<String> sortedZonesOfUnitetStites = new ArrayList<String>();
        for (int i = 0; i < ZonesOfUnitetStites.size(); i++) {
            String temp = ZonesOfUnitetStites.get(i).getText();
            ZonesOfUnitetStates.add(temp);
            sortedZonesOfUnitetStites.add(temp);
        }
        Collections.sort(sortedZonesOfUnitetStites);
        for (int i = 0; i < ZonesOfUnitetStates.size(); i++) {
            if (!ZonesOfUnitetStates.get(i).equals(sortedZonesOfUnitetStites.get(i))) {
                System.out.println("ZonesOfUnitedStates are not alphabetically sorted. Zone # " + i + " is on wrong place.");
            }
        }
        for (int i = 0; i < CanadaZonesNames.size(); i++) {
            System.out.println(CanadaZonesNames.get(i));
        }
        System.out.println("*********************************************************************************");

        for (int i = 0; i < ZonesOfUnitetStates.size(); i++) {
            System.out.println(ZonesOfUnitetStates.get(i));
        }
    }
}










