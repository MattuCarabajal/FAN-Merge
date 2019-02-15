package Pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class AccountType extends BasePage {
	
	final WebDriver driver;
		
	@FindBy (how = How.ID, using = "p3")
	private WebElement selectType;
	
	@FindBy (how = How.NAME, using = "save")
	private WebElement doYouWishToContinue;
	
	@FindBy (how = How.NAME, using = "cancel")
	private WebElement cancel;
	
	//Methods
	
	public AccountType(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setType(String value) {
		setSimpleDropdown(selectType, value);
		List<WebElement> elements = driver.findElements(By.className("btn"));
		elements.get(0).click();

		//doYouWishToContinue.click();
	}
	
}
