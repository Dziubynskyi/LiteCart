package ru.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.test.TestBase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

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
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //это тут нужно. Выключил мешает внизу. Работает с вкл
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
        List<WebElement> ZonesOfCanada = driver.findElements(By.cssSelector("td:nth-child(3) > select > option[selected]"));// локатор который выбирает только выбранное значения (одно) в выпадающем списке
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
        List<WebElement> ZonesOfUnitetStites = driver.findElements(By.cssSelector("td:nth-child(3) > select > option[selected]"));// локатор который выбирает только выбранное значения в выпадающем списке

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
    @Test
    public void CreateNewUser() {
        String email = "Dziybynskyi+1294@gmail.com";
        String password = "3254632546";

        driver.navigate().to("http://localhost/litecart/en/");
        loginLiteCart.WaitWebSiteLoaded();
        driver.findElement(By.cssSelector("tr:nth-child(5) > td > a")).click();
        driver.findElement(By.name("tax_id")).sendKeys("336542097");
        driver.findElement(By.name("company")).sendKeys("black rock");
        driver.findElement(By.name("firstname")).sendKeys("Yuriy");
        driver.findElement(By.name("lastname")).sendKeys("Barsky");
        driver.findElement(By.name("address1")).sendKeys("Dror 56");
        driver.findElement(By.name("postcode")).sendKeys("76485");
        driver.findElement(By.name("city")).sendKeys("Tel-Aviv");
        WebElement elementCountry = driver.findElement(By.cssSelector("td:nth-child(1) > select"));// локатор для дробдаун всегда СЕЛЕКТ
        selectValueInDropdownbyText(elementCountry, "United States");
        WebElement elementZone = driver.findElement(By.cssSelector("td:nth-child(2) > select"));// локатор для дробдаун всегда СЕЛЕКТ

        WebElement element = wait.until(presenceOfElementLocated(By.cssSelector("td:nth-child(2) > select")));

        selectValueInDropdownbyText(element, "Georgia");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys("+10974767847");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);
        driver.findElement(By.cssSelector("td > button")).click();

        wait.until(ExpectedConditions.stalenessOf(element));// явное ожидание пока элемент исчезнет


        driver.findElement(By.cssSelector("li:nth-child(4) > a")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        driver.findElement(By.cssSelector("li:nth-child(4) > a")).click();

    }


    @Test
    public void AddNewProduct() { // нуже новый путь на картинку
        String PathTofile = "dog.jpg";
        File PathTest = new File(PathTofile);
        String AbsolutePath = PathTest.getAbsolutePath();
        System.out.println(AbsolutePath);

        driver.get("http://localhost/litecart/admin/login.php");
        loginLiteCart.SelectLogin("admin");
        loginLiteCart.SelectPassword("admin");
        loginLiteCart.PressLoginButton();
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.cssSelector("#content > div:nth-child(2) > a:nth-child(2)")).click();
        driver.findElement(By.name("name[en]")).sendKeys("BobDuck");
        driver.findElement(By.name("code")).sendKeys("34256");
        driver.findElement(By.cssSelector("tr:nth-child(10) > td > input[type='date']")).sendKeys("12032017");
        driver.findElement(By.cssSelector("tr:nth-child(11) > td > input[type='date']")).sendKeys("30032017");
        driver.findElement(By.cssSelector("td > input[type='file']")).sendKeys(AbsolutePath);// type='file' в локаторе позволяет выбрать картинку нужную
        driver.findElement(By.cssSelector("#content > form > div > ul > li:nth-child(2) > a")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("div.trumbowyg-editor")));
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("It is best dog!");

        By tabPrices = By.xpath("//a[contains(text(), 'Prices')]"); // такой вид записи локаторов через By
        driver.findElement(tabPrices).click();

        WebDriverWait waitNew = new WebDriverWait(driver, 5);
        waitNew.until(ExpectedConditions.visibilityOfElementLocated(By.className("content")));

        WebElement priceField = driver.findElement(By.cssSelector("#tab-prices > table:nth-child(2) > tbody > tr > td > input[type='number']"));
        setElementText(priceField, "19.90");
        driver.findElement(By.name("save")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("td:nth-child(3) > a")));

    }

    @Test
    public void AddProductToCart() throws InterruptedException {
        driver.navigate().to("http://localhost/litecart/en/");
        driver.findElement(By.cssSelector("#box-most-popular > div > ul > li:nth-child(1) > a.link")).click();
        if (true == IsElementDrobDownPresent()) { // условие проверяет что если нам попалась утка с дробдаун  нужно выбарть значение small в ней (утки хаотично попадаются не увсех есть дробдауны)
            WebElement DrobDown = driver.findElement(By.cssSelector("tr:nth-child(1) > td > select"));
            selectValueInDropdownbyText(DrobDown, "Small");
            WebElement QuantityB = driver.findElement(By.cssSelector("#cart > a.content > span.quantity"));
            String QuantityBefore = QuantityB.getText();
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(driver -> !driver.findElement(By.cssSelector("#cart > a.content > span.quantity")).getText().equals(QuantityBefore));
        } else {
            WebElement QuantityB = driver.findElement(By.cssSelector("#cart > a.content > span.quantity"));
            String QuantityBefore = QuantityB.getText();
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(driver -> !driver.findElement(By.cssSelector("#cart > a.content > span.quantity")).getText().equals(QuantityBefore));
        }
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Most Popular')]")));
        driver.findElement(By.cssSelector("#box-most-popular > div > ul > li:nth-child(2) > a.link")).click();
        if (true == IsElementDrobDownPresent()) {
            WebElement DrobDown = driver.findElement(By.cssSelector("tr:nth-child(1) > td > select"));
            selectValueInDropdownbyText(DrobDown, "Small");
            WebElement QuantityB = driver.findElement(By.cssSelector("#cart > a.content > span.quantity"));
            String QuantityBefore = QuantityB.getText();
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(driver -> !driver.findElement(By.cssSelector("#cart > a.content > span.quantity")).getText().equals(QuantityBefore));
        } else {
            WebElement QuantityB = driver.findElement(By.cssSelector("#cart > a.content > span.quantity"));
            String QuantityBefore = QuantityB.getText();
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(driver -> !driver.findElement(By.cssSelector("#cart > a.content > span.quantity")).getText().equals(QuantityBefore));
        }
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Most Popular')]")));
        driver.findElement(By.cssSelector(" #box-most-popular > div > ul > li:nth-child(3) > a.link")).click();
        if (true == IsElementDrobDownPresent()) {
            WebElement DrobDown = driver.findElement(By.cssSelector("tr:nth-child(1) > td > select"));
            selectValueInDropdownbyText(DrobDown, "Small");
            WebElement QuantityB = driver.findElement(By.cssSelector("#cart > a.content > span.quantity"));
            String QuantityBefore = QuantityB.getText();
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(driver -> !driver.findElement(By.cssSelector("#cart > a.content > span.quantity")).getText().equals(QuantityBefore));
        } else {
            WebElement QuantityB = driver.findElement(By.cssSelector("#cart > a.content > span.quantity"));
            String QuantityBefore = QuantityB.getText();
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(driver -> !driver.findElement(By.cssSelector("#cart > a.content > span.quantity")).getText().equals(QuantityBefore));
        }
        driver.findElement(By.linkText("Checkout »")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Customer Details')]")));

        WebElement PriseB1 = driver.findElement(By.cssSelector("td:nth-child(2) > strong"));
        String PriseBefore1 = PriseB1.getText();
        driver.findElement(By.name("remove_cart_item")).click();
        wait.until(driver -> !driver.findElement(By.cssSelector("td:nth-child(2) > strong")).getText().equals(PriseBefore1));

        WebElement PriseB2 = driver.findElement(By.cssSelector("td:nth-child(2) > strong"));
        String PriseBefore2 = PriseB2.getText();
        driver.findElement(By.name("remove_cart_item")).click();
        //wait.until(driver -> !driver.findElement(By.cssSelector("td:nth-child(2) > strong")).getText().equals(PriseBefore2));
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.cssSelector("td:nth-child(2) > strong"));
        driver.findElement(By.name("remove_cart_item")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout-cart-wrapper > p:nth-child(2) > a")));

    }


    public void setElementText(WebElement element, String text) {
        element.click();
        element.clear();
        //Log.info("entering text '" + text + "' into element " + element);
        element.sendKeys(text);
        // Assert.assertEquals(element.getAttribute("value"), text);
    }

    public void selectValueInDropdownbyText(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

    public boolean verifyElementIsPresent(WebElement element) {
        try {
            element.getTagName();
            return true;
        } catch (NoSuchElementException e) {
            //  Log.info("---------------------------------");
            //  Log.info("element " + element + " can not be found by  element.getTagName()");
            //   Log.info("---------------------------------");
            return false;
        }
    }
    public void selectValueInDropdownbyIndex(WebElement dropdown, int index) {
        Select select = new Select(dropdown);
        select.deselectByIndex(index);
    }

    public boolean IsElementDrobDownPresent() {
        return driver.findElements(By.cssSelector("tr:nth-child(1) > td > select")).size() > 0;
    }

}










