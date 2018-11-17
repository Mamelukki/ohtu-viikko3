package ohtu;

import java.io.File;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Tester {

    private static WebDriver driver;
    private static WebElement element;

    public static void main(String[] args) {
        File pathBinary = new File("/home/salmisar/firefox/firefox");
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        driver = new FirefoxDriver(firefoxBinary, firefoxProfile);

        driver.get("http://localhost:4567");

        sleep(2);

        // right username, right password
        login("pekka", "akkep");
        logout();
        
        // right username, wrong password
        login("pekka", "wrong");
        backToHome();
        
        // nonexistent username
        login("nonexistent", "password");
        backToHome();
        
        // register 
        register("new", "password");
        continueToMainPage();
        
        // logout after register
        logout();

        driver.quit();
    }

    private static void login(String username, String password) {
        element = driver.findElement(By.linkText("login"));
        element.click();
        sleep(2);
        Random r = new Random();
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        sleep(2);
        element.submit();
    }

    private static void logout() {
        sleep(2);
        element = driver.findElement(By.linkText("logout"));
        element.click();
        sleep(2);
    }
    
    private static void backToHome() {
        sleep(2);
        element = driver.findElement(By.linkText("back to home"));
        element.click();
        sleep(2);
    }
    
    private static void continueToMainPage() {
        sleep(2);
        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();
        sleep(2);
    }

    private static void register(String username, String password) {
        element = driver.findElement(By.linkText("register new user"));
        element.click();
        sleep(2);
        Random r = new Random();
        element = driver.findElement(By.name("username"));
        element.sendKeys(username + r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(password);
        element = driver.findElement(By.name("signup"));
        sleep(2);
        element.submit();
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
