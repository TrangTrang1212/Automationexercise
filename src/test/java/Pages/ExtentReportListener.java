package Pages;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String browserName = "Not specified";

    @Override
    public void onStart(ISuite suite) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        ExtentSparkReporter reporter = new ExtentSparkReporter("extent-reports/ExtentReport_" + timeStamp + ".html");
        reporter.config().setDocumentTitle("Automation Report");
        reporter.config().setReportName("TestNG Selenium Report");
        reporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Host Name", "Localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
    }

    @Override
    public void onFinish(ISuite suite) {
        extent.flush();
    }

    @Override
    public void onStart(ITestContext context) {
        String browserParam = context.getCurrentXmlTest().getParameter("browser");
        if (browserParam != null && !browserParam.isEmpty()) {
            browserName = browserParam;
            extent.setSystemInfo("Browser", browserName);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testNameWithBrowser = result.getMethod().getMethodName() + " [" + browserName + "]";
        ExtentTest extentTest = extent.createTest(testNameWithBrowser, result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "❌ Test Failed: " + result.getMethod().getMethodName());
        test.get().log(Status.FAIL, result.getThrowable());
        attachScreenshotBase64(result, "📸 Screenshot on Failure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⚠️ Test Skipped: " + result.getMethod().getMethodName());
    }

    public static void logStepWithScreenshot(WebDriver driver, String stepDescription) {
        String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.get().log(Status.PASS, stepDescription)
                .addScreenCaptureFromBase64String(base64Screenshot, stepDescription);
    }

    private void attachScreenshotBase64(ITestResult result, String title) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).driver;
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.get().addScreenCaptureFromBase64String(base64Screenshot, title);
        }
    }
}
