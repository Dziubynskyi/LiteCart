package ru.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.test.TestBase;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 Задание 14. Проверьте, что ссылки открываются в новом окне
 Сделайте сценарий, который проверяет, что ссылки на странице редактирования страны открываются в новом окне.
 Сценарий должен состоять из следующих частей:
 1) зайти в админку
 2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
 3) открыть на редактирование какую-нибудь страну или начать создание новой
 4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.
 Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank". Но в этом упражнении требуется именно кликнуть по ссылке, чтобы она открылась в новом окне, потом переключиться
 в новое окно, закрыть его, вернуться обратно, и повторить эти действия для всех таких ссылок.
 Не забудьте, что новое окно открывается не мгновенно, поэтому требуется ожидание открытия окна.
 Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
 -----
 Уложите созданный файл, содержащий сценарий, в ранее созданный репозиторий. В качестве ответа на задание отправьте ссылку на свой репозиторий и указание, какой именно файл содержит нужный сценарий.
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
        String mainWindow = driver.getWindowHandle();// находим индификатор действуещего окна
        System.out.println("mainWindow : " + mainWindow);
        Set<String> oldWindows = driver.getWindowHandles();//находим  индификаторы всех открытых окон
        List<WebElement> linksList = driver.findElements(By.xpath(".//form[@method='post']/table//a[@target='_blank']"));   //получить все ссылочкные элементы на странице

        for (int i = 0; i < linksList.size(); i++) { // в цикле проходим все ссылки + переключаем на них драйвер + потом снова переключаемя на главную старницу
            linksList.get(i).click();
            String newWindow = wait.until((WebDriver driver) -> thereIsWindowOtherThan(oldWindows));// ждем пока индификтор появился и сохраняем его в переменную
            driver.switchTo().window(newWindow);
            WebElement element = wait.until(presenceOfElementLocated(By.xpath("//h1")));
            driver.close();
            driver.switchTo().window(mainWindow);// переключаемся к старому окну (в котором и была ссылка которая открывает новое окно)
        }






    }


    public String thereIsWindowOtherThan(Set<String> oldWindowHandles) {// получает все старые открытые окна , находим новые, удаляем старые, проверяем осталось что-то или нет из старых. Если да значит все хорошо - индификатор нового окна найдет
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
