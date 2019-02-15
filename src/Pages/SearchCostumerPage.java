package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchCostumerPage {
		
		WebDriver driver;
		
		@FindBy (how = How.ID,using = "ContactName")
		WebElement ContactName;
		
		@FindBy (how = How.ID,using = "PhoneNumber")
		WebElement PhoneNumber;
	
		@FindBy (how = How.ID,using = "	AccountName")
		WebElement 	AccountName;
		
		@FindBy (how = How.ID,using = "	DocumentType")
		WebElement 	DocumentType;
		
		@FindBy (how = How.ID,using = "	DocumentNumber")
		WebElement 	DocumentNumber;
		
		@FindBy (how = How.ID,using = "	Email")
		WebElement 	Email;
		
			
	}