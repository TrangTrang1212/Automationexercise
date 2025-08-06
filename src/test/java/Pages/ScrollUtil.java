package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollUtil {
    private WebDriver driver;
    public ScrollUtil(WebDriver driver){
        this.driver = driver;
    }
    public void scrollToElement(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator); // Tìm phần tử theo locator
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element); // Cuộn đến phần tử
    }
    //ScrollUtil.scrollToElement(driver, By.id("elementId"));
    public void scrollToPage(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}
