package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Cart {
    protected WebDriver driver;
    private ScrollUtil scrollUtil;
    private String textProductName;
    private String textPriceDetail;
    public Cart(WebDriver driver){
        this.driver = driver;
        scrollUtil = new ScrollUtil(driver);
    }
    public void addToCart(String qty){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement qtyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantity")));
            qtyField.clear();
            qtyField.sendKeys(qty);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default cart']"))).click();
            WebElement textAdded = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[@class='modal-title w-100']")));
            String text = textAdded.getText();
            System.out.println(text);

            WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'product-information')]/h2")));
            textProductName = productName.getText();

            WebElement priceDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'product-information')]/span/span[not(label)]")));
            textPriceDetail = priceDetail.getText();
        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }
    public boolean isAddSuccess(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement textAdded = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[@class='modal-title w-100']")));
            String text = textAdded.getText();
            return text.contains("Added!");
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }
    public boolean isViewCartSuccess_01(String qty){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view_cart']/u"))).click();

            WebElement cartName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/product_details/')]")));
            String textCartName = cartName.getText();

            WebElement cartPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='cart_price']/p")));
            String textCartPrice = cartPrice.getText();

            WebElement cartQty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='cart_quantity']/button")));
            String textCartQty = cartQty.getText();

            //So sánh
            boolean isNameMatch = textProductName.equals(textCartName);
            boolean isQtyMatch = qty.equals(textCartQty);
            boolean isPriceMatch = textPriceDetail.equals(textCartPrice);

            return isNameMatch && isQtyMatch && isPriceMatch;
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }

}
