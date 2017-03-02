package ru.test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {//метод инициализации с помощу которого мы не закрываем каждый раз браузер
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeMethod
    public void start() {
        if (driver != null) {
            return;
        }

        driver = new ChromeDriver();

       // wait = new WebDriverWait(driver, 10);// ожидание для все элементов

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));// останавливает все процессы уже после выполнения всех тестов
    }

    @AfterMethod
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
