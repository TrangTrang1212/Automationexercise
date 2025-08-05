package Pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;
    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
        //Tắt warring
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        System.out.println("===== Đang chạy trên trình duyệt: " + browser.toUpperCase() + " =====");
        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver-win64\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false); // tắt đề xuất lưu mật khẩu
                prefs.put("profile.password_manager_enabled", false); // tắt trình quản lý mật khẩu
                prefs.put("profile.default_content_setting_values.notifications", 2); // tắt notification popup
                prefs.put("profile.default_content_setting_values.geolocation", 2); // tắt location popup
                prefs.put("autofill.profile_enabled", false); // 🔥 TẮT autofill (popup "Save address?")

                chromeOptions.setExperimentalOption("prefs", prefs);

                // Ẩn popup "Chrome is being controlled by automated test software"
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
                throw new IllegalArgumentException("Trình duyệt không được hỗ trợ: " + browser);
        }

        driver.get("https://automationexercise.com/");
        driver.manage().window().maximize();
        System.out.println("Done Setup on the " +browser);
    }
    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        // Chụp ảnh màn hình khi test thất bại
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destFile = new File("screenshots/" + result.getName() + ".png");
                FileUtils.copyFile(srcFile, destFile);
                System.out.println("Chụp ảnh màn hình khi test thất bại: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Lỗi khi chụp ảnh màn hình: " + e.getMessage());
            }
        }
        // Đăng xuất nếu đang đăng ký, đăng nhập thành công.
        try {
            Logout logoutPage = new Logout(driver);
            Register registerPage = new Register(driver);
            Login login = new Login(driver);
            if (registerPage.isRegisterSuccess()||login.isLoginSuccess()) {
                logoutPage.logout();
                System.out.println("Logout after test case.");
            } else {
                System.out.println("Don't logout because failed register.");
            }
        } catch (Exception e) {
            System.out.println("Error logout: " + e.getMessage());
        }
    }
    @AfterClass
    public void tearDown(){
        if (driver!= null){
            driver.quit();
            System.out.println("Close Browser");
        }
    }


}
