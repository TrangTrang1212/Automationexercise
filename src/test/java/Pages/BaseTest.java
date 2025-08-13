package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

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
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        System.out.println("===== Running on browser: " + browser.toUpperCase() + " =====");

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver-win64\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_setting_values.notifications", 2);
                prefs.put("profile.default_content_setting_values.geolocation", 2);
                prefs.put("autofill.profile_enabled", false);

                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("--disable-features=PasswordManagerEnabled");
                chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                chromeOptions.addArguments("--disable-features=PasswordCheck");
                chromeOptions.addArguments("--incognito");
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                chromeOptions.setExperimentalOption("useAutomationExtension", false);

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Drivers\\geckodriver-v0.35.0-win64\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.get("https://automationexercise.com/");
        System.out.println("Browser setup completed: " + browser);
    }

    @BeforeMethod
    public void beforeMethod(java.lang.reflect.Method method) {
        System.out.println("=== Starting Test Case: " + method.getName() + " | Browser: " + driverName() + " ===");
    }

    @AfterMethod
    public void tearDownMethod() {
        try {
            Logout logoutPage = new Logout(driver);
            Register registerPage = new Register(driver);
            Login login = new Login(driver);

            if (registerPage.isRegisterSuccess() || login.isLoginSuccess()) {
                logoutPage.logout();
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
