package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Cart {
    protected WebDriver driver;
    private ScrollUtil scrollUtil;
    private String textProductName;
    private String textPriceDetail;
    private Product productPage;
    public Cart(WebDriver driver){
        this.driver = driver;
        scrollUtil = new ScrollUtil(driver);
        productPage = new Product(driver);
    }

    public String getTextProductName() {
        return textProductName;
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
            //System.out.println(text);

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
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='cartModal']//a[@href='/view_cart']"))).click();

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
    public boolean isViewCartSuccess_02(String expectedName, String expectedPrice, String expectedQty) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            List<WebElement> rows = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//tr[contains(@id,'product')]")
                    )
            );

            for (WebElement row : rows) {
                String cartName = row.findElement(By.xpath(".//a[contains(@href, '/product_details/')]")).getText();
                String cartPrice = row.findElement(By.xpath(".//td[@class='cart_price']/p")).getText();
                String cartQty = row.findElement(By.xpath(".//td[@class='cart_quantity']/button")).getText();

                boolean isNameMatch = expectedName.equals(cartName);
                boolean isQtyMatch = expectedQty.equals(cartQty);
                boolean isPriceMatch = expectedPrice.equals(cartPrice);

                if (isNameMatch && isQtyMatch && isPriceMatch) {
                    return true; // Tìm thấy sản phẩm đúng với dữ liệu mong đợi
                }
            }
            return false;
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
            return false;
        }
    }
    public String[] addMultipleProduct(String[] products, String[] qtys) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String[] prices = new String[products.length];

            for (int i = 0; i < products.length; i++) {
                String productName = products[i];
                String qty = qtys[i];

                // Mở chi tiết sản phẩm & lấy giá
                productPage.productDetail(productName);
                prices[i] = productPage.getPrice();
                // Thêm vào giỏ hàng
                addToCart(qty);
                if (i < products.length - 1) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue Shopping']"))).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
                }
            }
            // Mở giỏ hàng
            List<WebElement> viewCartLinks = driver.findElements(By.xpath("//div[@id='cartModal']//a[@href='/view_cart']"));
            if (!viewCartLinks.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(viewCartLinks.get(0))).click();
            } else {
                driver.get("https://automationexercise.com/view_cart");
            }
            // Verify từng sản phẩm
            return prices;

        } catch (Exception e) {
            throw new RuntimeException("Error khi add nhiều sản phẩm: " + e.getMessage(), e);
        }
    }


}
