package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ClientSearch extends BasePage {
	
	final WebDriver driver;
	
	@FindBy (how = How.ID, using = "ContactName")
	private WebElement name;
	
	@FindBy (how = How.ID, using = "PhoneNumber")
	private WebElement phoneLine;
	
	@FindBy (how = How.ID, using = "AccountName")
	private WebElement socialReason;
	
	@FindBy (how = How.ID, using = "DocumentType")
	private WebElement documentType;
	
	@FindBy (how = How.ID, using = "DocumentNumber")
	private WebElement documentNumber;
	
	@FindBy (how = How.ID, using = "Email")
	private WebElement email;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement cancel;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement saveForLater;

	@FindBy (how = How.ID, using = "SearchClientsDummy")
	private WebElement search;
	
	@FindBy (how = How.CLASS_NAME, using = "slds-tabs--scoped__link")
	private List<WebElement> clientsTabs;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-scope")
	private List<WebElement> clients;
	
public ClientSearch(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
}

public void searchClientBy(String field, String searchTerm) {
	switch(field) {
	case "name":
		name.sendKeys(searchTerm);
		break;
	case "phone":
		phoneLine.sendKeys(searchTerm);
		break;
	case "social reason":
		socialReason.sendKeys(searchTerm);
		break;
	case "dni":
		setSimpleDropdown(documentType, "DNI");
		documentNumber.sendKeys(searchTerm);
		break;
	case "cuit":
		setSimpleDropdown(documentType, "CUIT");
		documentNumber.sendKeys(searchTerm);
		break;
	case "email":
		email.sendKeys(searchTerm);
		break;
	}
	search.click();
}

public String getClient(String tab) {
	switch(tab) {
	case "active client":
		clientsTabs.get(0).click();
		break;
	case "inactive client":
		clientsTabs.get(1).click();
		break;
	}
	return clients.get(0).getText();
}

}
