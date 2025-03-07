package humanResourceSenerio2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class POM_Utility_AdminPage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	

	@FindBy(xpath = "/html/body/div/div[1]/div[1]/aside/nav/div[2]/ul//a")
    List<WebElement> menuOptions;

    @FindBy(xpath = "/html/body/div/div[1]/div[1]/aside/nav/div[2]/ul/li[1]/a")
    WebElement adminMenu;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input")
    WebElement usernameSearchField;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
    WebElement searchButton;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
    WebElement resetButton;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[1]/div[2]/i")
    WebElement userRoleDropdown;
    
    @FindBy(xpath=  "/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div[2]/div[2]/span")
    WebElement selectRoleAdmin;
    
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div[1]/div[2]/i")
    WebElement statusDropdown;
    
    @FindBy(xpath= "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div[2]/div[2]/span")
    WebElement selectStatusEnable;

    @FindBy(xpath = "//span[@class='oxd-text oxd-text--span']")
    WebElement recordsFound;
    
    @FindBy(xpath="/html/body/div/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div/div/div[2]")
    List<WebElement> recordUNRowAdmin;
    
    @FindBy(xpath="/html/body/div/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div/div/div[3]")
    List<WebElement> recordroleAdmin;
    
    @FindBy(xpath="/html/body/div/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div/div/div[5]")
    List<WebElement> recordStatusEnable;

    public POM_Utility_AdminPage(WebDriver d) {
        this.driver = d;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public int getMenuOptionsCount() {
    	System.out.println("\nMenu Option is: " + menuOptions.size());
        return menuOptions.size();
        
    }

    public void clickAdminMenu() {
        adminMenu.click();
    }

    public void searchByUsername(String username) {
        usernameSearchField.sendKeys(username);
        searchButton.click();
        try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("\nUsername Admin User Are: "+recordUNRowAdmin.size());
    }

    public void selectUserRole() {
        
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(selectRoleAdmin)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("\nUser Role Admin User Are: "+recordroleAdmin.size());
        
        
    }

    public void selectStatus() {
    	
    	wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(selectStatusEnable)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("\nUser Status Enable User Are: "+recordStatusEnable.size());
    }

    public int getRecordsFound() {
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='orangehrm-paper-container']//table")));
         List<WebElement> rows = driver.findElements(By.xpath("//div[@class='orangehrm-paper-container']//table//tbody/tr"));
         return rows.size();
    }

    public void resetSearch() {
       
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }
    
    public void refreshPage() {
    	driver.navigate().refresh();
    }
}

