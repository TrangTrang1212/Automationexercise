package Pages;

import DTO.PaymentInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Order {
    protected WebDriver driver;
    private FillInfo fillInfo;
    private ActionHelper actions;

    //=====Locator=====
    private static final By PROCESS_TO_CHECKOUT_BTN = By.xpath("//a[@class='btn btn-default check_out']");
    private static final By CHECKOUT_TEXT = By.xpath("//li[@class='active']");
    private static final String CHECKOUT_PAGE = "Checkout";
    private static final By VIEW_CART_LINK = By.xpath("//a[@href='/view_cart']");
    private static final By CART_ITEM_LIST = By.xpath("//div[@class='table-responsive cart_info']/table[@class='table table-condensed']");
    private static final By PRODUCT_NAME_CART = By.xpath(".//a[contains(@href, '/product_details/')]");
    private static final By PRODUCT_CATEGORY_CART = By.xpath("//td[@class='cart_description']/p");
    private static final By PRODUCT_PRICE_CART = By.xpath("//td[@class='cart_price']/p");
    private static final By PRODUCT_QTY_CART = By.xpath("//td[@class='cart_quantity']/button");
    private static final By PRODUCT_TOTAL_CART = By.xpath("//p[@class='cart_total_price']");
    private static final By VIEW_ITEM_CHECKOUT = By.id("cart_info");
    private static final By SUBMIT_BTN = By.id("submit");
    private static final By PLACE_ORDER_SUCCESS = By.xpath("//h2[@class='title text-center']/b");
    private static final String PLACE_ORDER_MESSAGE = "ORDER PLACED!";

    public Order(WebDriver driver){
        this.driver = driver;
        fillInfo = new FillInfo(driver);
        actions = new ActionHelper(driver);
    }
    public void clickProcessToCheckoutBTN(){
        try {
            actions.click(PROCESS_TO_CHECKOUT_BTN);
        }catch (Exception e){
            fail("clickProcessToCheckoutBTN was error: " +e.getMessage());
        }
    }
    public boolean isCheckOutProcess(){
        try {
            String checkoutText = actions.getText(CHECKOUT_TEXT);
            return checkoutText.equalsIgnoreCase(CHECKOUT_PAGE);
        }catch (Exception e){
            fail("isCheckOutProcess was error: " +e.getMessage());
            return false;
        }
    }
    public boolean checkViewMatchOrder() {
        try {
            //cartView
            actions.click(VIEW_CART_LINK);
            List<WebElement> cartViews = actions.getVisibleElements(CART_ITEM_LIST);
            String[] nameCart = new String[cartViews.size()];
            String[] categoryCart = new String[cartViews.size()];
            String[] priceCart = new String[cartViews.size()];
            String[] qtyCart = new String[cartViews.size()];
            String[] totalProductCart = new String[cartViews.size()];
            for (int i = 0; i < cartViews.size(); i++) {
                WebElement product = cartViews.get(i);
                nameCart[i] = actions.getChildText(product, PRODUCT_NAME_CART);
                categoryCart[i] = actions.getChildText(product, PRODUCT_CATEGORY_CART);
                priceCart[i] = actions.getChildText(product, PRODUCT_PRICE_CART);
                qtyCart[i] = actions.getChildText(product, PRODUCT_QTY_CART);
                totalProductCart[i] = actions.getChildText(product, PRODUCT_TOTAL_CART);
            }
            clickProcessToCheckoutBTN();
            actions.scrollToElement(driver, VIEW_ITEM_CHECKOUT);
            List<WebElement> orderViews = actions.getVisibleElements(CART_ITEM_LIST);
            String[] nameOrder = new String[orderViews.size()];
            String[] categoryOrder = new String[orderViews.size()];
            String[] priceOrder = new String[orderViews.size()];
            String[] qtyOrder = new String[orderViews.size()];
            String[] totalProductOrder = new String[orderViews.size()];
            for (int i = 0; i < orderViews.size(); i++) {
                WebElement product = orderViews.get(i);
                nameOrder[i] = actions.getChildText(product, PRODUCT_NAME_CART);
                categoryOrder[i] = actions.getChildText(product, PRODUCT_CATEGORY_CART);
                priceOrder[i] = actions.getChildText(product, PRODUCT_PRICE_CART);
                qtyOrder[i] = actions.getChildText(product, PRODUCT_QTY_CART);
                totalProductOrder[i] = actions.getChildText(product, PRODUCT_TOTAL_CART);
            }
            boolean isNameMatch = Arrays.equals(nameCart, nameOrder);
            boolean isCategoryMatch = Arrays.equals(categoryCart, categoryOrder);
            boolean isPriceMatch = Arrays.equals(priceCart, priceOrder);
            boolean isQtyMatch = Arrays.equals(qtyCart, qtyOrder);
            boolean isTotalProductMatch = Arrays.equals(totalProductCart, totalProductOrder);
            return isNameMatch && isCategoryMatch && isPriceMatch && isQtyMatch && isTotalProductMatch;
        } catch (Exception e) {
            fail("checkViewMatchOrder was error: " + e.getMessage());
            return false;
        }
    }
    public void placeOrder(PaymentInfo paymentInfo){
        try {
            actions.click(PROCESS_TO_CHECKOUT_BTN);
            fillInfo.fillPayment(paymentInfo);
            actions.click(SUBMIT_BTN);
        }catch (Exception e){
            fail("placeOrder was error: " +e.getMessage());
        }
    }
    public boolean isPlaceOrderSuccessfully(){
        try {
            String textSuccess = actions.getText(PLACE_ORDER_SUCCESS);
            return textSuccess.contains(PLACE_ORDER_MESSAGE);
        }catch (Exception e){
            fail("isPlaceOrderSuccessfully was error: " +e.getMessage());
            return false;
        }
    }
}
