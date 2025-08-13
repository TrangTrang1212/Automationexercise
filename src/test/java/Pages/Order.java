package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Order {
    protected WebDriver driver;
    private Product productPage;
    private Login login;
    private Cart cart;
    private ScrollUtil scrollUtil;
    private FillInfo fillInfo;
    public Order(WebDriver driver){
        this.driver = driver;
        login = new Login(driver);
        productPage = new Product(driver);
        scrollUtil = new ScrollUtil(driver);
        fillInfo = new FillInfo(driver);
    }
    public void checkout(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-default check_out']"))).click();
            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']/u"))).click();
        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }
    public boolean isCheckOutProcess(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement textCheckout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='active']")));
            String checkoutText = textCheckout.getText();
            System.out.println(checkoutText);
            return checkoutText.contains("Checkout");

        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }
    public boolean checkViewMatchOrder() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            //cartView
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view_cart']"))).click();
            List<WebElement> cartViews = driver.findElements(By.xpath("//table[contains(@class,'cart_info')]//tr[contains(@class,'cart_item')]"));
            String[] nameCart = new String[cartViews.size()];
            String[] categoryCart = new String[cartViews.size()];
            String[] priceCart = new String[cartViews.size()];
            String[] qtyCart = new String[cartViews.size()];
            String[] totalProductCart = new String[cartViews.size()];
            for (int i = 0; i < cartViews.size(); i++) {
                WebElement product = cartViews.get(i);
                nameCart[i] = product.findElement(By.xpath(".//a[contains(@href, '/product_details/')]")).getText().trim();
                categoryCart[i] = product.findElement(By.xpath("//td[@class='cart_description']/p")).getText().trim();
                priceCart[i] = product.findElement(By.xpath("//td[@class='cart_price']/p")).getText().trim();
                qtyCart[i] = product.findElement(By.xpath("//td[@class='cart_quantity']/button")).getText().trim();
                totalProductCart[i] = product.findElement(By.xpath("//p[@class='cart_total_price']")).getText().trim();
            }
            checkout();
            scrollUtil.scrollToElement(driver, By.className("heading"));
            List<WebElement> orderViews = driver.findElements(By.xpath("//table[contains(@class,'cart_info')]//tr[contains(@class,'cart_item')]"));
            String[] nameOrder = new String[orderViews.size()];
            String[] categoryOrder = new String[orderViews.size()];
            String[] priceOrder = new String[orderViews.size()];
            String[] qtyOrder = new String[orderViews.size()];
            String[] totalProductOrder = new String[orderViews.size()];
            for (int i = 0; i < orderViews.size(); i++) {
                WebElement product = orderViews.get(i);
                nameOrder[i] = product.findElement(By.xpath(".//a[contains(@href, '/product_details/')]")).getText().trim();
                categoryOrder[i] = product.findElement(By.xpath(".//td[@class='cart_description']/p")).getText().trim();
                priceOrder[i] = product.findElement(By.xpath("//td[@class='cart_price']/p")).getText().trim();
                qtyOrder[i] = product.findElement(By.xpath("//td[@class='cart_quantity']/button")).getText().trim();
                totalProductOrder[i] = product.findElement(By.xpath("//p[@class='cart_total_price']")).getText().trim();

            }
            boolean isNameMatch = Arrays.equals(nameCart, nameOrder);
            boolean isCategoryMatch = Arrays.equals(categoryCart, categoryOrder);
            boolean isPriceMatch = Arrays.equals(priceCart, priceOrder);
            boolean isQtyMatch = Arrays.equals(qtyCart, qtyOrder);
            boolean isTotalProductMatch = Arrays.equals(totalProductCart, totalProductOrder);
            return isNameMatch && isCategoryMatch && isPriceMatch && isQtyMatch && isTotalProductMatch;
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
            return false;
        }
    }
    public void placeOrder(String nameCard, String numberCard, String cvc, String expiration, String year){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-default check_out']"))).click();
            fillInfo.fillPayment(nameCard, numberCard,cvc, expiration,year);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("submit"))).click();

        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }
    public boolean isPlaceOrderSuccessfully(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement titleText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='title text-center']/b")));
            String textSuccess = titleText.getText();
            return textSuccess.contains("ORDER PLACED!");
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }
}
