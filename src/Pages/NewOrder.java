package Pages;


import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewOrder {
	
	WebDriver driver;
	
	@FindBy (how = How.ID, using ="Order_Tab")
	private WebElement Order;
	
	@FindBy (how = How.NAME , using ="new")
	private WebElement NewOrder;
	
	@FindBy (how = How.ID, using ="accid")
	private WebElement OrderAccountName;
	
	@FindBy (how = How.ID, using ="EffectiveDate")
	private WebElement OrderDate;
	
	@FindBy (how = How.ID, using ="CustomerAuthorizedBy")
	private WebElement OrderCustomerAutorized;
	
	@FindBy (how = How.ID, using ="accid_lkwgt")
	private WebElement FindAccount;
	
	@FindBy (how = How.ID, using ="CustomerAuthorizedBy_lkwgt")
	private WebElement FindCustomerAutorized;
	
	@FindBy (how = How.ID, using ="CompanyAuthorizedBy_lkwgt")
	private WebElement FindCompanyAuthorized;
	
	@FindBy (how = How.ID, using ="CompanyAuthorizedBy")
	private WebElement OrderCompanyAuthorized;
	
	@FindBy (how = How.ID, using ="BillingAddressstreet")
	private WebElement BillingAddressstreet;
	
	@FindBy (how = How.ID, using ="BillingAddressstate")
	private WebElement BillingAddressstate;
	
	@FindBy (how = How.ID, using ="BillingAddresszip")
	private WebElement BillingAddresszip;
	
	@FindBy (how = How.ID, using ="Description")
	private WebElement Description;
	
	@FindBy (how = How.ID, using ="Contract")
	private WebElement Contract;
	
	@FindBy (how = How.ID, using ="Contract_lkwgt")
	private WebElement FindContrat;
	
	@FindBy (how = How.ID, using ="ShippingAddressstreet")
	private WebElement ShippingAddressstreet;
	
	@FindBy (how = How.ID, using ="ShippingAddressstate")
	private WebElement ShippingAddressstate;
	
	@FindBy (how = How.ID, using ="ShippingAddresscity")
	private WebElement ShippingAddresscity;
	
	@FindBy (how = How.ID, using ="ShippingAddresszip")
	private WebElement ShippingAddresszip;
	
	@FindBy (how = How.NAME, using ="save")
	private WebElement save;
	
	@FindBy (how = How.NAME, using ="cancel")
	private WebElement cancel;
	
	public void NewAccount(WebDriver driver){
		
		this.driver = driver;

        PageFactory.initElements(driver, this);
	}
	
	public void LoadPage(String AccountName, String Costumer, String Company,
			String BStreet, String Desc, String Contr, String BState, String BZip,String SCity,
			String SStreet, String SState, String SZip)
	{
		
		OrderAccountName.sendKeys(AccountName);
		OrderDate.click();
		OrderCustomerAutorized.sendKeys(Costumer);
		FindAccount.click();
		FindCustomerAutorized.click();
		FindCompanyAuthorized.click();
		OrderCompanyAuthorized.sendKeys(Company);
		Description.sendKeys(Desc);
		Contract.sendKeys(Contr);
		FindContrat.click();
		BillingAddressstreet.sendKeys(BStreet);
		BillingAddressstate.sendKeys(BState);
		BillingAddresszip.sendKeys(BZip);
		ShippingAddressstreet.sendKeys(SStreet);
		ShippingAddressstate.sendKeys(SState);
		ShippingAddresscity.sendKeys(SCity);
		ShippingAddresszip.sendKeys(SZip);
		save.click();
		cancel.click();
	}

}
