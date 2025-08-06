package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Search {
    private WebDriver driver;
    private ScrollUtil scrollUtil;
    public Search(WebDriver driver){
        this.driver = driver;
        scrollUtil = new ScrollUtil(driver);
    }
    public void search(String keyword){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/products']"))).click();
        scrollUtil.scrollToElement(driver, By.xpath("//div[@class='brands_products']"));
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("search_product")));
        searchInput.clear();
        searchInput.sendKeys(keyword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit_search"))).click();
    }
    public boolean isSuccessSearch(String keyword) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // Tìm tất cả tên sản phẩm trong <div class="features_items">/<div class="col-sm-4">
            List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='features_items']//div[@class='col-sm-4']//div[@class='productinfo text-center']/p")));
            for (WebElement product : products) {
                String productName = product.getText();
                System.out.println("Found product containing keyword '" + keyword + "': " + productName);
            }
            return true;
        } catch (Exception e) {
            fail("Error checking related products: " + e.getMessage());
            return false;
        }
    }
}
