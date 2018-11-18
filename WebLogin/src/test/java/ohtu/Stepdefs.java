package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import java.util.Random;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Stepdefs {

    File pathBinary = new File("/home/salmisar/firefox/firefox");
    FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
    FirefoxProfile firefoxProfile = new FirefoxProfile();
    WebDriver driver = new FirefoxDriver(firefoxBinary, firefoxProfile);
    String baseUrl = "http://localhost:4567";
    Random random = new Random();

    @Given("^login is selected$")
    public void login_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();
    }

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_and_password_are_given(String username, String password) throws Throwable {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    @Then("^system will respond \"([^\"]*)\"$")
    public void system_will_respond(String pageContent) throws Throwable {
        assertTrue(driver.getPageSource().contains(pageContent));
    }

    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_correct_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @Then("^user is logged in$")
    public void user_is_logged_in() throws Throwable {
        pageHasContent("Ohtu Application main page");
    }

    @Then("^user is not logged in and error message is given$")
    public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }

    @When("^nonexistent username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void nonexistent_username_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @Given("^command new user is selected$")
    public void command_new_user_is_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
    }

    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
    public void a_valid_username_and_password_and_matching_password_confirmation_are_entered(String username, String password) throws Throwable {
        registerWith(username + random.nextInt(10000), password, password);
    }

    @Then("^a new user is created$")
    public void a_new_user_is_created() throws Throwable {
        pageHasContent("Welcome to Ohtu Application!");
    }

    @When("^too short username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
    public void too_short_username_and_password_and_matching_password_confirmation_are_entered(String username, String password) throws Throwable {
        registerWith(username + random.nextInt(10), password, password);
    }

    @When("^a valid username \"([^\"]*)\" and too short password \"([^\"]*)\" and matching password confirmation are entered$")
    public void a_valid_username_and_too_short_password_and_matching_password_confirmation_are_entered(String username, String password) throws Throwable {
        registerWith(username + random.nextInt(10000), password, password);
    }

    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and not matching password confirmation \"([^\"]*)\" are entered$")
    public void a_valid_username_and_password_and_not_matching_password_confirmation_are_entered(String username, String password1, String password2) throws Throwable {
        registerWith(username + random.nextInt(10000), password1, password2);
    }

    @Then("^user is not created and error \"([^\"]*)\" is reported$")
    public void user_is_not_created_and_error_is_reported(String error) throws Throwable {
        pageHasContent(error);
        pageHasContent("Create username and give password");
    }
    
    @Given("^user with username \"([^\"]*)\" with password \"([^\"]*)\" is successfully created$")
    public void user_with_username_with_password_is_successfully_created(String username, String password) throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
        registerWith(username, password, password);
        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();
        element = driver.findElement(By.linkText("logout"));
        element.click();
    }

    @Given("^user with username \"([^\"]*)\" and password \"([^\"]*)\" is tried to be created$")
    public void user_with_username_and_password_is_tried_to_be_created(String username, String password) throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
        registerWith(username, password, password);
        element = driver.findElement(By.linkText("back to home"));
        element.click();
    }

    @When("^invalid username \"([^\"]*)\" and invalid password \"([^\"]*)\" are given$")
    public void invalid_username_and_invalid_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    private void registerWith(String username, String password1, String password2) {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password1);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(password2);
        element = driver.findElement(By.name("signup"));
        element.submit();
    }
}
