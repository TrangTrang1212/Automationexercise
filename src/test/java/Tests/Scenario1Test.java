package Tests;

import DTO.AccountInfo;
import Pages.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;



public class Scenario1Test extends BaseTest {
    private Register register;
    private Logout logout;
    private String generatedEmail;
    private Login login;

    private static final String PASSWORD_VALID = "123456";
    private static final String PASSWORD_INVALID = "12345";
    @BeforeMethod
    public void beforeEachMethod(Method method){
        register = new Register(driver);
        logout = new Logout(driver);
        login = new Login(driver);
    }
    @Test(priority = 1)
    public void registerNewAccount(){
        generatedEmail = DataHelper.generateUniqueEmail();
        register.goToSignUpForm("demo", generatedEmail);
        AccountInfo accountInfo = new AccountInfo("Mrs", PASSWORD_VALID, "10", "8", "2003", "Yes", "No", "test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000", "0987654321");
        register.completeAccountInfo(accountInfo);
        assertTrue(register.isRegisterSuccess(), "Account creation failed!");
        ExtentReportListener.logStepWithScreenshot(driver, "ACCOUNT CREATED! success");
    }
    @Test(priority = 2)
    public void registerExistAccount(){
        logout.logout();
        register.goToSignUpForm("demo",generatedEmail);
        assertTrue(register.isRegisterFailed(), "Account hadn't been exist");
        ExtentReportListener.logStepWithScreenshot(driver, "Email already exist! message displayed");
    }
    @Test(priority = 3)
    public void loginWithCorrectPassword(){
        login.loginWith(generatedEmail,PASSWORD_VALID);
        assertTrue(login.isLoginSuccess(),"Login failed");
        ExtentReportListener.logStepWithScreenshot(driver, "Login successful with correct password");
        logout.logoutButton();
    }
    @Test(priority = 4)
    public void loginWithIncorrectPassword(){
        login.loginWith(generatedEmail,PASSWORD_INVALID);
        assertTrue(login.isLoginFailed(), "Login success");
        ExtentReportListener.logStepWithScreenshot(driver, "Incorrect password message displayed");
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
