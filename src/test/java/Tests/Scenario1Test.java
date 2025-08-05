package Tests;

import Pages.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;



public class Scenario1Test extends BaseTest {
    private Register register;
    private FillInfo fillInfo;
    private Logout logout;
    private String generatedEmail;
    private Login login;
    @BeforeMethod
    public void beforeEachMethod(Method method){
        //super.beforeMethod(method); // Gọi log từ BaseTest
        register = new Register(driver);
        fillInfo = new FillInfo(driver);
        logout = new Logout(driver);
        login = new Login(driver);
    }
    @Test (priority = 1)
    public void s1_TC01(){
        try {
            String uniqueEmail = "demo@testt" + System.currentTimeMillis() % 10000 + ".vn";
            register.registerWith("demo", uniqueEmail, "Mrs", "123456","10", "8","2003","No","No","test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000","0987654321");
            if (register.isRegisterSuccess()) {
                System.out.println("Register Successfully.");
            } else {
                System.out.println("Register failed.");
            }
            generatedEmail = uniqueEmail;
            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(priority = 2)
    public void s1_TC02(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            fillInfo.fillSignupInfo("demo", generatedEmail);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default' and @data-qa='signup-button']"))).click();
            WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form']/div/div/div[3]/div/form/p")));
            String texterorr = text.getText();
            System.out.println(texterorr);
            assertTrue(texterorr.contains("Email Address already exist!"), "Chưa có account tồn tại");
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(priority = 3)
    public void s1_TC03(){
        try {
            login.loginWith(generatedEmail,"123456");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[10]/a")));
            String textTitle = text.getText();
            System.out.println(textTitle);
            assertTrue(textTitle.contains("Logged in as"),"Login failed");
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(priority = 4)
    public void s1_TC04(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Logout']"))).click();
            login.loginWith(generatedEmail,"12345");
            WebElement erorr = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form']/div/div/div[1]/div/form/p")));
            String textError = erorr.getText();
            System.out.println(textError);
            assertTrue(textError.contains("Your email or password is incorrect!"), "Login success");
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(enabled = false)
    public void s1_TC07(){
        try {
            RestAssured.baseURI = "https://automationexercise.com";
            String email = "rdemo" + System.currentTimeMillis() + "@gdemo.com";

            Response response = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("name", "rdemo")
                    .formParam("email", email)
                    .formParam("password", "1234")
                    .formParam("title", "Mr")
                    .formParam("birth_date", "10")
                    .formParam("birth_month", "08")
                    .formParam("birth_year", "2000")
                    .formParam("firstname", "rde")
                    .formParam("lastname", "mo")
                    .formParam("company", "TestCompany")
                    .formParam("address1", "hem co dia chi")
                    .formParam("address2", "duong phu")
                    .formParam("country", "Vietnam")
                    .formParam("state", "HCM")
                    .formParam("city", "HCM")
                    .formParam("zipcode", "70000")
                    .formParam("mobile_number", "0987654321")
                    .when()
                    .post("/api/createAccount");
            String rawBody = response.getBody().asString();
            System.out.println("Raw body: " + rawBody);

            JSONObject json = new JSONObject(rawBody);

            assertEquals(response.getStatusCode(), 200);
            assertEquals(json.getInt("responseCode"), 201);
            assertTrue(json.getString("message").contains("User created!"));
        }catch (Exception e){
            fail("Error: " +e.getMessage());
        }
    }

}
