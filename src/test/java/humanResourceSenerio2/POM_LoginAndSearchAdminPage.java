package humanResourceSenerio2;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POM_LoginAndSearchAdminPage {
	WebDriver driver;
	POM_Utility_LoginPage loginPage;
	POM_Utility_AdminPage adminPage;
	
  @Test (priority=1)
  public void testLogin() throws InterruptedException {
	  
	  System.out.println("\nTest For Login Page: ");
	  loginPage.login("Admin", "admin123");
	  Thread.sleep(5000);
	  Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Test Fail!!!");
	  System.out.println("Test Case Pass, Successfull Login!!!");
	  
	  
  }
  
  @Test (priority=2)
  public void testLeftMenuOptionsCount() throws InterruptedException{
	  
	  System.out.println("\nTest For Count Left Menu Option: ");
	  adminPage.getMenuOptionsCount();
      adminPage.clickAdminMenu();  
	  
  }
  
  @Test (priority=3)
  public void testSearchByUsername() throws InterruptedException{
	  
	  System.out.println("\nTest For Search By UserName Admin: ");
	  adminPage.clickAdminMenu();
      adminPage.searchByUsername("Admin");
      adminPage.resetSearch();
      adminPage.refreshPage();
      
	  	  
  }
  
  
  
  @Test (priority=4)
  public void testSearchByUserRole() throws InterruptedException{
	  
	  System.out.println("\nTest For Search By UserRole Admin: ");
	  adminPage.clickAdminMenu();
      adminPage.selectUserRole();
      
      adminPage.resetSearch();
      adminPage.refreshPage();
	  
  }
  
  @Test (priority=4)
  public void testSearchByUserStatus() throws InterruptedException{
	  
	  System.out.println("\nTest For UserStatus Enable");
	  adminPage.clickAdminMenu();
      adminPage.selectStatus();
      
      adminPage.resetSearch();
  }
  @BeforeTest
  public void loginSetup() {
	  
	  driver = new ChromeDriver();
	  driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	  loginPage = new POM_Utility_LoginPage(driver);
	  adminPage = new POM_Utility_AdminPage(driver);
	  
  }

  @AfterTest
  public void afterTest() throws InterruptedException {
	  
	  Thread.sleep(5000);
	  driver.quit();
  }
}
