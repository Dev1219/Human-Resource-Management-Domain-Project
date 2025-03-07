package humanResourceSenerio2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class POM_Utility_LoginPage {
	
	WebDriver driver;
	@FindBy(xpath="//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")WebElement usrName;
	@FindBy(xpath="//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input")WebElement passWord;
	@FindBy(xpath="//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")WebElement clickLogin;
	
	public POM_Utility_LoginPage(WebDriver d) {
		
		driver = d;
		PageFactory.initElements(driver, this);
	
	}
	
	public void setUserName(String un) {
		usrName.sendKeys(un);
	}
	
	public void setPassword(String pw) {
		passWord.sendKeys(pw);
	}
	
	public void clickLoginBtn(){
		clickLogin.click();
		
	}
	
	//Method to perform Login
	
	public void login(String username, String password) {
		
		setUserName(username);
		setPassword(password);
		clickLoginBtn();
		
		
	}

}
