package HumanResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LoginTest {
	
	 WebDriver driver;
	 ExtentReports extent;
	 ExtentTest test;
	    
	    String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	    String excelPath = System.getProperty("user.dir") + "\\ExcellFile\\LoginData.xlsx";

	    @BeforeTest
	    public void setup() {
	        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\Reports\\ExtentReport.html");
	        extent = new ExtentReports();
	        extent.attachReporter(reporter);

	        driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.manage().window().maximize();
	    }
	    
	    @BeforeMethod
	    public void startTest(Method method) {
	        test = extent.createTest(method.getName());
	    }

	    @Test(dataProvider = "getLoginData")
	    public void loginTest(String username, String password) throws InterruptedException {
	        driver.get(url);

	        try {
	            WebElement userName = driver.findElement(By.name("username"));
	            WebElement passWord = driver.findElement(By.name("password"));
	            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

	            userName.clear();
	            userName.sendKeys(username);
	            passWord.clear();
	            passWord.sendKeys(password);
	            loginButton.click();

	            Thread.sleep(3000); 

	            // Capture Screenshot for Each Login Attempt
	            ScreenshotUtility.captureScreenshot(driver, "LoginAttempt_" + username);

	            try {
	                WebElement dashboard = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
	                Assert.assertTrue(dashboard.isDisplayed(), "Login successful for valid credentials");
	                
	             // Log the message for successful login
	                test.log(Status.PASS, "Login successful: " + username);


	                // Print the message for successful login
	                System.out.println("\nLogin successful: " + username);

	                // Logout after successful login
	                driver.findElement(By.xpath("//i[contains(@class, 'oxd-userdropdown-icon')]")).click();
	                driver.findElement(By.xpath("//a[text()='Logout']")).click();
	            } catch (Exception e) {
	                WebElement errorMsg = driver.findElement(By.xpath("//p[contains(@class, 'oxd-text oxd-text--p')]"));
	                Assert.assertTrue(errorMsg.isDisplayed(), "Error message should appear for invalid credentials");
	                
	                test.log(Status.FAIL, "Login failed as expected: " + username);

	                // Print the message for expected login failure
	                System.out.println("\nLogin failed as expected: " + username);
	                throw new AssertionError("Login should not be successful for invalid credentials!");
	            }
	        } catch (Exception e) {
	        	test.log(Status.SKIP, "Test Skipped: " + e.getMessage());
	            System.out.println("Test encountered an error: " + e.getMessage());
	            throw e;
	        }
	    }

	    @DataProvider
	    public Object[][] getLoginData() throws IOException {
	        FileInputStream fis = new FileInputStream(excelPath);
	        XSSFWorkbook wb = new XSSFWorkbook(fis);
	        XSSFSheet sheet = wb.getSheet("LoginData");

	        int rowCount = sheet.getPhysicalNumberOfRows();
	        Object[][] data = new Object[rowCount][2];

	        for (int i = 0; i < rowCount; i++) {
	            data[i][0] = sheet.getRow(i).getCell(0).getStringCellValue(); // Username
	            data[i][1] = sheet.getRow(i).getCell(1).getStringCellValue(); // Password
	        }

	        wb.close();
	        fis.close();
	        return data;
	    }

	    @AfterMethod
	    public void tearDown(ITestResult result) {
	    	if (result.getStatus() == ITestResult.FAILURE) {
	            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
	            ScreenshotUtility.captureScreenshot(driver, "Failure_" + result.getName());
	        } else if (result.getStatus() == ITestResult.SUCCESS) {
	            test.log(Status.PASS, "Test Passed");
	        } else {
	            test.log(Status.SKIP, "Test Skipped");
	        }
	        extent.flush();
	    }

	    @AfterTest
	    public void cleanup() {
	    	if (driver != null) {
	            driver.quit();
	    }
}
}