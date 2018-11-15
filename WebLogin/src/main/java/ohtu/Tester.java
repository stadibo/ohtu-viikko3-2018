package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {
        loginWorks();
        correctUserWrongPass();
        noSuchUser();
        createNewUser();
        logOutAfterCreate();
    }

    public static void loginWorks() {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        sleep(1);

        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(1);

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));

        sleep(2);
        element.submit();

        sleep(1);

        driver.quit();
    }

    public static void correctUserWrongPass() {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(1);

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("hakka");
        element = driver.findElement(By.name("login"));

        sleep(2);
        element.submit();

        sleep(1);

        driver.quit();
    }

    public static void noSuchUser() {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(1);

        element = driver.findElement(By.name("username"));
        element.sendKeys("naaah");
        element = driver.findElement(By.name("password"));
        element.sendKeys("jeeesh");
        element = driver.findElement(By.name("login"));

        sleep(2);
        element.submit();

        sleep(1);

        driver.quit();
    }

    public static void createNewUser() {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();

        sleep(1);

        Random r = new Random();
        element = driver.findElement(By.name("username"));
        element.sendKeys("jepsky" + r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("jeeesh");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("jeeesh");
        element = driver.findElement(By.name("signup"));

        sleep(2);
        element.submit();

        sleep(1);

        driver.quit();
    }

    public static void logOutAfterCreate() {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();

        sleep(1);

        Random r = new Random();
        element = driver.findElement(By.name("username"));
        element.sendKeys("jepsky" + r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("jeeesh");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("jeeesh");
        element = driver.findElement(By.name("signup"));

        sleep(2);
        element.submit();
        
        sleep(1);
        
        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();

        sleep(1);

        element = driver.findElement(By.linkText("logout"));
        element.click();
        
        sleep(1);

        driver.quit();
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
