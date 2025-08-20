package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ActionHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    public ActionHelper(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    public void clicks(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    public String getText(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
    public String getText_fast(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).getText();
    }
    public String getAttribute(By locator,String attribute){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
    }
    public void sendKey(By locator, String text){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
    public void selectDropdownByValueWithWait(By locator, String value) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select dropdown = new Select(element);
            dropdown.selectByValue(value);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            dropdown = new Select(element);
            WebElement selectedOption = dropdown.getFirstSelectedOption();
    }
    public List<WebElement> getVisibleElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    public WebElement getVisibleElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public String getChildText(WebElement parent, By childLocator) {
        return parent.findElement(childLocator).getText().trim();
    }
    public void acceptAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void dismissAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
    }
    public String getAlertText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        return alert.getText();
    }
    public void sendKeysToAlert(String text) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        alert.accept();
    }
    public void scrollToElement(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator); // Tìm phần tử theo locator
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element); // Cuộn đến phần tử
    }
    public void scrollToPage(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}
