package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CreateNewClient extends BasePage {
	
	final WebDriver driver;
	
	@FindBy (how = How.ID, using = "ContactName")
	private WebElement clientName;
	
	@FindBy (how = How.ID, using = "ImpositiveCondition")
	private WebElement impositiveCondition;
	
	@FindBy (how = How.ID, using = "IdPhone")
	private WebElement identifyingPhone;
	
	@FindBy (how = How.CLASS_NAME, using = "slds-checkbox--faux")
	private List<WebElement> checkboxes;
	
	@FindBy (how = How.ID, using = "Street")
	private WebElement street;
	
	@FindBy (how = How.ID, using = "StreetNumber")
	private WebElement streetNumber;
	
	@FindBy (how = How.ID, using = "FloorNumber")
	private WebElement floorNumber;
	
	@FindBy (how = How.ID, using = "Department")
	private WebElement Apartment;
	
	@FindBy (how = How.ID, using = "PostalCode")
	private WebElement zipCode;
	
	@FindBy (how = How.ID, using = "State")
	private WebElement state;
	
	@FindBy (how = How.ID, using = "City")
	private WebElement city;
	
	@FindBy (how = How.ID, using = "BillingStreet")
	private WebElement billingStreet;
	
	@FindBy (how = How.ID, using = "BillingStreetNumber")
	private WebElement billingStreetNumber;
	
	@FindBy (how = How.ID, using = "BillingFloor")
	private WebElement billingFloor;
	
	@FindBy (how = How.ID, using = "BillingDepartment")
	private WebElement billingApartment;
	
	@FindBy (how = How.ID, using = "BillingPostalCode")
	private WebElement billingZipCode;
	
	@FindBy (how = How.ID, using = "BillingState")
	private WebElement billingState;
	
	@FindBy (how = How.ID, using = "BillingCity")
	private WebElement billingCity;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement cancel;
	
public CreateNewClient(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
}

public void createClient() {
	clientName.sendKeys("Aaa Aaa");
	setSimpleDropdown(impositiveCondition, "IVA Consumidor final");
	identifyingPhone.sendKeys("01145534091");
	street.sendKeys("Congreso");
	streetNumber.sendKeys("3924");
	zipCode.sendKeys("1427");
	setSimpleDropdown(state, "Ciudad Autónoma de Buenos Aires");
//	setSimpleDropdown(city, "Capital Federal");
	checkboxes.get(2).click();
	buttons.get(1).click();
}

public void checkFloor()
{
	floorNumber.sendKeys("123");
}

}
