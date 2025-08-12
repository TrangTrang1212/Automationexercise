package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.fail;

public class Category {
    protected WebDriver driver;
    private ScrollUtil scrollUtil;
    public Category(WebDriver driver){
        this.driver = driver;
        scrollUtil = new ScrollUtil(driver);
    }

    public void categories(String ca, String sub){
        try {
            scrollUtil.scrollToElement(driver, By.xpath("//div[@class='left-sidebar']"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#" + ca + "' and @data-toggle='collapse']"))).click();
            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class = 'fa fa-plus']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='" + sub + " ']"))).click();
        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }
    public void brands(String brand){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/products']"))).click();
            scrollUtil.scrollToElement(driver, By.xpath("//div[@class='brands_products']"));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='" + brand + "']"))).click();
        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }
    public boolean isSuccessCategory(String ca, String sub){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement textTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class = 'title text-center']")));
            String title = textTitle.getText();
            String expectedTitle = String.format("%s - %s Products", ca, sub).toUpperCase();;
            //System.out.println("Actual title: " + title);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
            return title.contains(expectedTitle);
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }
    public boolean isSuccessBrand(String brand){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement textTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class = 'title text-center']")));
            String title = textTitle.getText();
            String expectedTitle = String.format("Brand - %s Products", brand).toUpperCase();;
            //System.out.println("Actual title: " + title);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
            return title.contains(expectedTitle);
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }

}
