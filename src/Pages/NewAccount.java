package Pages;


import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class NewAccount extends BasePage {
	
	final WebDriver driver;
		
	@FindBy (how = How.CLASS_NAME, using = "bodySmall")
	private WebElement copyBillingAddressToShipmentAddress;
	
	//Account information
	
	@FindBy (how = How.ID, using = "acc2")
	private WebElement accountName;
	
	@FindBy (how = How.ID, using = "acc3")
	private WebElement mainAccount;
	
	@FindBy (how = How.ID, using = "acc17country")
	private WebElement country;

	@FindBy (how = How.ID, using = "acc17street")
	private WebElement street;
	
	@FindBy (how = How.ID, using = "acc17city")
	private WebElement city;
	
	@FindBy (how = How.ID, using = "acc17state")
	private WebElement state;
	
	@FindBy (how = How.ID, using = "acc17zip")
	private WebElement zipCode;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSW8")
	private WebElement customerPriority;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcg")
	private WebElement lastModifyEmailDate;
	
	@FindBy (how = How.ID, using = "CF00Nc0000001pSW3")
	private WebElement contact;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWce")
	private WebElement documentType;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcd")
	private WebElement documentNumber;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWck")
	private WebElement primaryContactName;
	
//	@FindBy (how = How.ID, using = "")
//	private WebElement taxCondition;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcc")
	private WebElement billingAddressStore;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcm")
	private WebElement shippingAddressStore;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSW6")
	private WebElement status;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSWB")
	private WebElement sla;
	
	@FindBy (how = How.ID, using = "acc18country")
	private WebElement shipmentCountry;
	
	@FindBy (how = How.ID, using = "acc18street")
	private WebElement shipmentStreet;
	
	@FindBy (how = How.ID, using = "acc18city")
	private WebElement shipmentCity;
	
	@FindBy (how = How.ID, using = "acc18state")
	private WebElement shipmentState;
	
	@FindBy (how = How.ID, using = "acc18zip")
	private WebElement shipmentZipCode;
	
	@FindBy (how = How.ID, using = "acc10")
	private WebElement phone;
	
	@FindBy (how = How.ID, using = "CF00Nc0000001pSW2")
	private WebElement premises;
	
	//TA Custom Fields
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcj")
	private WebElement personalClubPoints;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWch")
	private WebElement nps;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcf")
	private WebElement fol;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWci")
	private WebElement personalClubCategory;
	
	@FindBy (how = How.ID, using = "00Nc0000001pWcl")
	private WebElement segment;
	
	//Billing Profile

	@FindBy (how = How.ID, using = "00Nc0000001pSVi")
	private WebElement billFrequency;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVc")
	private WebElement accountPaymentType;

	@FindBy (how = How.ID, using = "00Nc0000001pSVu")
	private WebElement enableAutopay;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVh")
	private WebElement billDeliveryMethod;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVj")
	private WebElement billingEmailAddress;
	
	@FindBy (how = How.ID, using = "CF00Nc0000001pSVg")
	private WebElement autoPaymentMethod;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVe")
	private WebElement autopaymentAmount;
	
	//Contact Preferences

	@FindBy (how = How.ID, using = "00Nc0000001pSVn_unselected")
	private WebElement contactPreferences;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVn_right_arrow")
	private WebElement rightArrow;
	
	//Directory Listing

	@FindBy (how = How.ID, using = "00Nc0000001pSVq")
	private WebElement directoryListed;
	
	//Loyalty
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVk")
	private WebElement cltv;
	
	@FindBy (how = How.ID, using = "00Nc0000001pSVm")
	private WebElement churn;
	
	//Save, Save and new, Cancel
	
	@FindBy (how = How.NAME, using = "save")
	private WebElement save;
	
	@FindBy (how = How.NAME, using = "save_new")
	private WebElement save_new;
	
	@FindBy (how = How.NAME, using = "cancel")
	private WebElement cancel;
	
	//Methods
	
	public NewAccount(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	public void createNewAcc(String name) {
		accountName.sendKeys(name);
//		mainAccount.sendKeys("Aaa Aaa");
		setSimpleDropdown(country, "Argentina");
		street.sendKeys("Congreso 3924");
		city.sendKeys("Ciudad Autónoma de Buenos Aires");
		state.sendKeys("Buenos Aires");
		zipCode.sendKeys("1429");
		setSimpleDropdown(customerPriority, "High");
		lastModifyEmailDate.sendKeys("20/07/2017");
//		contact.sendKeys("Aaa Aaa");
		setSimpleDropdown(documentType, "DNI");
		documentNumber.sendKeys("31879238");
		primaryContactName.sendKeys("Pedro Picapiedra");
		billingAddressStore.click();
		shippingAddressStore.click();
		setSimpleDropdown(status, "Active");
		setSimpleDropdown(sla, "Gold");
		setSimpleDropdown(shipmentCountry, "Argentina");
		shipmentStreet.sendKeys("Congreso 3924");
		shipmentCity.sendKeys("Capital Federal");
		setSimpleDropdown(shipmentState, "Ciudad Autónoma de Buenos Aires");
		shipmentZipCode.sendKeys("1429");
		phone.sendKeys("45678900");
		personalClubPoints.sendKeys("1234");
		setSimpleDropdown(nps, "1-DETRACTORS");
		fol.click();
		setSimpleDropdown(personalClubCategory, "Basico");
		setSimpleDropdown(segment, "Black");
		setSimpleDropdown(billFrequency, "Weekly");
		setSimpleDropdown(accountPaymentType, "Prepaid");
		enableAutopay.click();
		setSimpleDropdown(billDeliveryMethod, "eMail");
		billingEmailAddress.sendKeys("cuentadeprueba@nada.com");
		setSimpleDropdown(autopaymentAmount, "Full Balance");
		setElementFromList(contactPreferences, "eMail", rightArrow);
		save.click();
	}
	
	
	
	
	
	
	
}
