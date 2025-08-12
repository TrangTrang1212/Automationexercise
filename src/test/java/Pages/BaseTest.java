package Pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;

    protected String driverName() {
        return driver.getClass().getSimpleName().replace("Driver", "");
    }

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
        // Disable Selenium logs
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        System.out.println("===== Running on browser: " + browser.toUpperCase() + " =====");

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver-win64\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();

                // Chặn save password + password leak detection
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_setting_values.notifications", 2); // Block notifications
                prefs.put("profile.default_content_setting_values.geolocation", 2);    // Block location
                prefs.put("autofill.profile_enabled", false); // Disable autofill

                chromeOptions.setExperimentalOption("prefs", prefs);

                // Chặn thông báo "Change your password"
                chromeOptions.addArguments("--disable-features=PasswordManagerEnabled");
                chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                chromeOptions.addArguments("--disable-features=PasswordCheck");

                // Chạy incognito để không dùng mật khẩu đã lưu
                chromeOptions.addArguments("--incognito");

                // Loại bỏ "Chrome is being controlled by automated software"
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                chromeOptions.setExperimentalOption("useAutomationExtension", false);

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                System.setProperty("webdriver.firefox.driver", "C:\\Drivers\\geckodriver-v0.35.0-win64\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.get("https://automationexercise.com/");
        driver.manage().window().maximize();
        System.out.println("Browser setup completed: " + browser);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("=== Starting Test Case: " + method.getName() + " | Browser: " + driverName() + " ===");
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destFile = new File("screenshots/" + result.getName() + ".png");
                FileUtils.copyFile(srcFile, destFile);
                System.out.println("Screenshot captured for failed test: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error capturing screenshot: " + e.getMessage());
            }
        }

        try {
            Logout logoutPage = new Logout(driver);
            Register registerPage = new Register(driver);
            Login login = new Login(driver);

            if (registerPage.isRegisterSuccess() || login.isLoginSuccess()) {
                logoutPage.logout();
               // System.out.println("Successfully logged out after test.");
            } else {
                System.out.println("Logout skipped due to failed registration or login.");
            }
        } catch (Exception e) {
            System.out.println("Error during logout: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}
