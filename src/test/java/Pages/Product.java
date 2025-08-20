package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.testng.AssertJUnit.fail;

public class Product {
    protected WebDriver driver;
    private String imgText;
    private String textPrice;
    private ActionHelper action;

    // ====== Dynamic Locators ======
    private By getPricePDP(String productName){
        return By.xpath("//div[contains(@class, 'single-products')]//p[contains(text(), '"+ productName + "')]/../h2");
    }
    private By getImgPDP(String productName){
        return By.xpath("//div[contains(@class, 'single-products')]//p[text()='"+ productName + "']/../..//img");
    }
    private By getViewDetailButton(String productName){
        return By.xpath("//div[@class='productinfo text-center']/p[text()='" + productName + "']/../../..//a[contains(@href, '/product_details/') and contains(., 'View Product')]");
    }
    //Locators
    private static final By FEATURES_ITEM = By.xpath("//div[@class='features_items']");
    private static final By PRODUCT_NAME_PLP = By.xpath("//div[contains(@class, 'product-information')]/h2");
    private static final By PRODUCT_PRICE_PLP = By.xpath("//div[contains(@class, 'product-information')]/span/span[not(label)]");
    private static final By PRODUCT_IMG_PLP = By.xpath("//div[contains(@class, 'view-product')]/img");

    public Product(WebDriver driver){
        this.driver = driver;
        action = new ActionHelper(driver);
    }

    public void saveProductInfo(String productName){
        textPrice = action.getText(getPricePDP(productName));
        imgText = action.getAttribute(getImgPDP(productName),"src");
    }
    public void productDetail(String productName){
        try {
            action.scrollToElement(driver, FEATURES_ITEM);
            saveProductInfo(productName);
            action.click(getViewDetailButton(productName));
        }catch (Exception e){
            fail("productDetail was error " +e.getMessage());
        }
    }
    public String getPrice() {
        return textPrice;
    }
    public boolean isMatchProduct(String product){
        try {
            String textProductName = action.getText(PRODUCT_NAME_PLP);
            String textPriceDetail = action.getText(PRODUCT_PRICE_PLP);
            String textImgDetail =action.getAttribute(PRODUCT_IMG_PLP,"src");

            boolean isImgSrcMatch = imgText.equals(textImgDetail);
            boolean isPriceMatch = textPrice.equals(textPriceDetail);
            boolean isProductMatch = product.equals(textProductName);

            return isImgSrcMatch && isPriceMatch && isProductMatch;
        }catch (Exception e){
            fail("isMatchProduct was error " +e.getMessage());
            return false;
        }
    }
}
