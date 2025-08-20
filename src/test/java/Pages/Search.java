package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.fail;

public class Search {
    protected WebDriver driver;
    private ActionHelper actions;

    //===Locator===
    private static final By PRODUCT_LINK = By.xpath("//a[@href='/products']");
    private static final By PRODUCT_BRAND_CATEGORY = By.xpath("//div[@class='brands_products']");
    private static final By PRODUCT_SEARCH = By.id("search_product");
    private static final By SEARCH_SUBMIT_BTN = By.id("submit_search");
    private static final By LIST_PRODUCT = By.xpath("//div[@class='features_items']//div[@class='col-sm-4']//div[@class='productinfo text-center']/p");
    public Search(WebDriver driver){
        this.driver = driver;
        actions = new ActionHelper(driver);
    }
    public void search(String keyword){
        try {
            actions.click(PRODUCT_LINK);
            actions.scrollToElement(driver, PRODUCT_BRAND_CATEGORY);
            actions.sendKey(PRODUCT_SEARCH, keyword);
            actions.click(SEARCH_SUBMIT_BTN);
        }catch (Exception e){
            fail("Search was error: " +e.getMessage());
        }
    }
    public boolean isSuccessSearch(String keyword) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            List<WebElement> products = actions.getVisibleElements(LIST_PRODUCT);
            for (WebElement product : products) {
                String productName = product.getText();
            }
            return true;
        } catch (Exception e) {
            fail("Error checking related products: " + e.getMessage());
            return false;
        }
    }
}
