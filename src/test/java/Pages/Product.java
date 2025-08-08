package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.fail;

public class Product {
    protected WebDriver driver;
    private ScrollUtil scrollUtil;
    private String imgText;
    private String textPrice;

    public Product(WebDriver driver){
        this.driver = driver;
        scrollUtil = new ScrollUtil(driver);
    }
    public void productDetail(String product){

        try {
            //Cuộn đến vị trí list product
            scrollUtil.scrollToElement(driver, By.xpath("//div[@class='features_items']"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            //Lấy giá tiền
            WebElement price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'single-products')]//p[contains(text(), '"+ product + "')]/../h2")));
            textPrice = price.getText();
            System.out.println(textPrice);
            //Lấy attribute link hình
            WebElement img = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'single-products')]//p[text()='"+ product + "']/../..//img")));
            imgText = img.getAttribute("src");
            System.out.println(imgText);
            //Click vào button View detail
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='productinfo text-center']/p[text()='"+ product + "']/../../..//a[@href='/product_details/1' and contains(., 'View Product')]"))).click();
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    public boolean isMatchProduct(String product){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'product-information')]/h2")));
            String textProductName = productName.getText();
            System.out.println(textProductName);

            WebElement priceDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'product-information')]/span/span[not(label)]")));
            String textPriceDetail = priceDetail.getText();
            System.out.println(textPriceDetail);

            WebElement imgDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'view-product')]/img")));
            String textImgDetail = imgDetail.getAttribute("src");
            System.out.println(textImgDetail);
            // So sánh img src
            boolean isImgSrcMatch = imgText.equals(textImgDetail);
            boolean isPriceMatch = textPrice.equals(textPriceDetail);
            boolean isProductMatch = product.equals(textProductName);
            return isImgSrcMatch && isPriceMatch && isProductMatch;

        }catch (Exception e){
            fail("Error " +e.getMessage());
            return false;
        }
    }
}
