package Tests;

import Pages.BaseTest;
import Pages.Login;
import Pages.Register;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

public class Scenario2Test extends BaseTest {
    private Login login;
    private Register register;
    private String generatedEmail;

    @BeforeMethod
    public void beforeEachMethod(Method method){
        //super.beforeMethod(method); // Gọi log từ BaseTest
        login = new Login(driver);
        register = new Register(driver);
    }
    @Test
    public void S2_TC03(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String uniqueEmail = "demo@yyyq" + System.currentTimeMillis() % 10000 + ".vn";
            register.registerWith("demo", uniqueEmail, "Mrs", "123456","10", "8","2003","No","No","test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000","0987654321");
            generatedEmail = uniqueEmail;
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/delete_account']"))).click();
            WebElement deleteTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='title text-center' and @data-qa='account-deleted']/child::b")));
            String text = deleteTitle.getText();
            assertTrue("Account hasn't been delete", text.contains("ACCOUNT DELETED!"));
            System.out.println(text);
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test
    public void S2_TC04(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            login.loginWith(generatedEmail,"123456");
            WebElement erorr = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form']/div/div/div[1]/div/form/p")));
            String textError = erorr.getText();
            System.out.println(textError);
            Assert.assertTrue(textError.contains("Your email or password is incorrect!"), "Login success");
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }


}
