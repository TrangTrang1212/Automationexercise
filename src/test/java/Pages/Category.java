package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Category {
    protected WebDriver driver;
    private Cart cartPage;
    private ActionHelper action;
    // ====== Dynamic Locators ======
    private By getCategorySelection (String ca){
        return By.xpath("//a[@href='#" + ca + "' and @data-toggle='collapse']");
    }
    private By getSubCategorySelection(String sub){
        return By.xpath("//a[text()='" + sub + " ']");
    }
    private By getBrandSelection(String brand){
        return By.xpath("//a[text()='" + brand + "']");
    }
    private static final By CATEGORY_SCROLL = By.xpath("//div[@class='left-sidebar']");
    private static final By BRAND_SCROLL = By.xpath("//div[@class='brands_products']");
    private static final By PRODUCT_LINK = By.xpath("//a[@href='/products']");
    private static final By TITLE_TEXT = By.xpath("//h2[@class = 'title text-center']");

    public Category(WebDriver driver){
        this.driver = driver;
        cartPage = new Cart(driver);
        action = new ActionHelper(driver);
    }
    public void categories(String ca, String sub){
        try {
            action.scrollToElement(driver, CATEGORY_SCROLL);
            action.click(getCategorySelection(ca));
            action.click(getSubCategorySelection(sub));
        }catch (Exception e){
            Assert.fail("Categories was error: " +e.getMessage());
        }
    }
    public void brands(String brand){
        try {
            action.click(PRODUCT_LINK);
            action.scrollToElement(driver, BRAND_SCROLL);
            action.click(getBrandSelection(brand));
        }catch (Exception e){
            Assert.fail("Brands was error: " +e.getMessage());
        }
    }
    public boolean isSuccessCategory(String ca, String sub){
        try {
            String title = action.getText(TITLE_TEXT);
            String expectedTitle = String.format("%s - %s Products", ca, sub).toUpperCase();
            cartPage.navigateToHome();
            return title.equalsIgnoreCase(expectedTitle);
        }catch (Exception e){
            Assert.fail("isSuccessCategory was error: " +e.getMessage());
            return false;
        }
    }
    public boolean isSuccessBrand(String brand){
        try {
            String title = action.getText(TITLE_TEXT);
            String expectedTitle = String.format("Brand - %s Products", brand).toUpperCase();
            cartPage.navigateToHome();
            return title.equalsIgnoreCase(expectedTitle);
        }catch (Exception e){
            Assert.fail("isSuccessCategory was error: " +e.getMessage());
            return false;
        }
    }
}
