import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander Tyamakov on 13.07.2021
 */

public class SberTest1 {
    WebDriver driver;
    String baseUrl;

    @Before
    public void beforeTest () {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        baseUrl = "https://www.sberbank.ru/ru/person";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        WebDriverWait wait=new WebDriverWait(driver, 20);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }
    @Test
    public void testInsuranse () {
        driver.findElement(By.xpath("//div[@class = 'kitt-cookie-warning__content']//button")).click();
        driver.findElement(By.xpath("//a[@aria-label = 'Страхование']")).click();
        driver.findElement(By.xpath("//a[@data-cga_click_top_menu = 'Страхование_Все страховые программы_type_important']")).click();
        driver.findElement(By.xpath("//h3[contains(text(),'Страхование путешественников')]")).click();

        Wait<WebDriver> wait = new WebDriverWait(driver,5,1000);
        WebElement title = driver.findElement(By.xpath("//h1[@data-test-id = 'PageTeaserDict_header']"));
        Assert.assertEquals("Страхование путешественников",title.getText());

        WebElement sendBtn = driver.findElement(By.xpath("//a[@data-test-id = 'PageTeaserDict_button']//span[contains(text(),'Оформить онлайн')]"));
        wait.until(ExpectedConditions.visibilityOf(sendBtn)).click();
        driver.findElement(By.xpath("//h3[contains(text(),'Минимальная')]")).click();
        WebElement confirm = driver.findElement(By.xpath("//button[contains(text(),'Оформить')]"));
        wait.until(ExpectedConditions.visibilityOf(confirm)).click();

        fillField(By.xpath("//input[@id = 'surname_vzr_ins_0']"),"Иванов");
        fillField(By.xpath("//input[@id = 'name_vzr_ins_0']"),"Иван");
        fillField(By.xpath("//input[@id = 'birthDate_vzr_ins_0']"),"01.01.1990");
        driver.findElement(By.xpath("//label[contains(text(),'Женский')]")).click();
        fillField(By.xpath("//input[@id = 'person_lastName']"),"Иванов");
        fillField(By.xpath("//input[@id = 'person_firstName']"),"Иван");
        fillField(By.xpath("//input[@id = 'person_middleName']"),"Иванович");
        fillField(By.xpath("//input[@id = 'person_birthDate']"),"01.01.1990");
        fillField(By.xpath("//input[@id = 'documentIssue']"),"МВД");
        fillField(By.xpath("//input[@id = 'documentDate']"),"01.01.2010");
        fillField(By.xpath("//input[@id = 'passportNumber']"),"656477");
        fillField(By.xpath("//input[@id = 'passportSeries']"),"4565");

        Assert.assertEquals("Иванов",driver.findElement(By.xpath("//input[@id = 'surname_vzr_ins_0']")).getAttribute("value"));
        Assert.assertEquals("Иван",driver.findElement(By.xpath("//input[@id = 'name_vzr_ins_0']")).getAttribute("value"));
        Assert.assertEquals("01.01.1990",driver.findElement(By.xpath("//input[@id = 'birthDate_vzr_ins_0']")).getAttribute("value"));
        Assert.assertEquals("Иванов",driver.findElement(By.xpath("//input[@id = 'person_lastName']")).getAttribute("value"));
        Assert.assertEquals("Иван",driver.findElement(By.xpath("//input[@id = 'person_firstName']")).getAttribute("value"));
        Assert.assertEquals("Иванович",driver.findElement(By.xpath("//input[@id = 'person_middleName']")).getAttribute("value"));
        Assert.assertEquals("01.01.1990",driver.findElement(By.xpath("//input[@id = 'person_birthDate']")).getAttribute("value"));
        Assert.assertEquals("4565",driver.findElement(By.xpath("//input[@id = 'passportSeries']")).getAttribute("value"));
        Assert.assertEquals("656477",driver.findElement(By.xpath("//input[@id = 'passportNumber']")).getAttribute("value"));
        Assert.assertEquals("01.01.2010",driver.findElement(By.xpath("//input[@id = 'documentDate']")).getAttribute("value"));
        Assert.assertEquals("МВД",driver.findElement(By.xpath("//input[@id = 'documentIssue']")).getAttribute("value"));

        driver.findElement(By.xpath("//button[contains(text(),'Продолжить')]")).click();

        Assert.assertEquals("При заполнении данных произошла ошибка",
                driver.findElement(By.xpath("//div[@class = 'alert-form alert-form-error']")).getText());
        Assert.assertEquals("Поле не заполнено.",
                driver.findElement(By.xpath("//*[contains(@class,'phone')]/validation-message")).getText());
        Assert.assertEquals("Поле не заполнено.",
                driver.findElement(By.xpath("//*[contains(@name,'email')]/span/validation-message")).getText());
        Assert.assertEquals("Поле не заполнено.",
                driver.findElement(By.xpath("//*[contains(@name,'repeatEmail')]/span/validation-message")).getText());


    }
    public void fillField(By locator, String value){
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    @After
    public void afterTest () {
        driver.quit();
    }
}
