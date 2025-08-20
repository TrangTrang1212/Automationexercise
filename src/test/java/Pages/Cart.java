package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Cart {
    protected WebDriver driver;
    private Product productPage;
    private ActionHelper action;
    private static final By QTY_FIELD = By.id("quantity");
    private static final By ADD_TO_CART_BTN = By.xpath("//button[@class='btn btn-default cart']");
    private static final By PRODUCT_NAME = By.xpath("//div[contains(@class, 'product-information')]/h2");
    private static final By PRODUCT_PRICE = By.xpath("//div[contains(@class, 'product-information')]/span/span[not(label)]");
    private static final By ALERT_ADDED = By.xpath("//h4[@class='modal-title w-100']");
    private static final By CART_ROWS = By.xpath("//tr[contains(@id,'product')]");
    private static final By CART_PRODUCT_NAME = By.xpath(".//a[contains(@href, '/product_details/')]");
    private static final By CART_PRODUCT_PRICE = By.xpath(".//td[@class='cart_price']/p");
    private static final By CART_PRODUCT_QTY = By.xpath(".//td[@class='cart_quantity']/button");
    private static final By CONTINUE_SHOPPING_BUTTON = By.xpath("//button[text()='Continue Shopping']");
    private static final By HOME_LINK = By.xpath("//a[@href='/']");
    private static final By CART_MODAL = By.xpath("//div[@id='cartModal']//a[@href='/view_cart']");
    public Cart(WebDriver driver){
        this.driver = driver;
        productPage = new Product(driver);
        action = new ActionHelper(driver);
    }
    public void clickContinueShopping() {
        action.click(CONTINUE_SHOPPING_BUTTON);
    }

    public void navigateToHome() {
        action.click(HOME_LINK);
    }
    public void clickAddToCart(){
        action.click(ADD_TO_CART_BTN);
    }
    public void saveProductDetail(){
        action.getText(PRODUCT_NAME);
        action.getText(PRODUCT_PRICE);
    }
    public void addToCart(String qty){
        try {
            action.sendKey(QTY_FIELD, qty);
            clickAddToCart();
            saveProductDetail();
        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }
    public boolean isAddSuccess(){
        try {
            String textAdded = action.getText(ALERT_ADDED);
            return textAdded.contains("Added!");
        }catch (Exception e){
            fail("isAddSuccess was error: " +e.getMessage());
            return false;
        }
    }
    public boolean verifyCartItem(String expectedName, String expectedPrice, String expectedQty) {
        try {
            List<WebElement> rows = action.getVisibleElements(CART_ROWS);

            for (WebElement row : rows) {
                String cartName = row.findElement(CART_PRODUCT_NAME).getText();
                String cartPrice = row.findElement(CART_PRODUCT_PRICE).getText();
                String cartQty = row.findElement(CART_PRODUCT_QTY).getText();

                boolean isNameMatch = expectedName.equals(cartName);
                boolean isQtyMatch = expectedQty.equals(cartQty);
                boolean isPriceMatch = expectedPrice.equals(cartPrice);

                if (isNameMatch && isQtyMatch && isPriceMatch) {
                    return true;
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
                    clickContinueShopping();
                    navigateToHome();
                }
            }
            List<WebElement> viewCartLinks = driver.findElements(CART_MODAL);
            if (!viewCartLinks.isEmpty()) {
                action.clicks(viewCartLinks.get(0));
            } else {
                driver.get("https://automationexercise.com/view_cart");
            }
            return prices;

        } catch (Exception e) {
            throw new RuntimeException("Error khi add nhiều sản phẩm: " + e.getMessage(), e);
        }
    }


}
