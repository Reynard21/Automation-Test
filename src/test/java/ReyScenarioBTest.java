import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ReyScenarioBTest {

    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "https://www.periplus.com/";

    @BeforeMethod
    public void setUpChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testShoppingCartScenario() {
        // Case 1: Chrome launched & Entered Periplus's site
        driver.get(baseUrl);

        // Case 2: Rey goes sign in to his account
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-signin-text")));
        signInButton.click();

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("astrovolt0@gmail.com");

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("astrovolt0");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-login")));
        loginButton.click();

        // Case 3: Rey goes searching for books
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_name")));
        searchBar.click();
        searchBar.sendKeys("Rey Piano");

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("btnn")));
        searchButton.click();

        // Case 4: Rey selects the book
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[img[@class='default-img']]")));
        productLink.click();

        // Case 5: Rey adds the book to the cart
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'btn-add-to-cart')]")));
        addToCartButton.click();

        // Case 6: Rey closes the modal to proceed to the checkout cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Notification-Modal")));
        WebElement closeModalButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-modal-close")));
        closeModalButton.click();

        // Case 7: Rey is finally at the checkout cart
        driver.get(baseUrl + "checkout/cart");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterMethod
    public void closeChrome() {
        if (driver != null) {
            driver.quit();
        }
    }
}