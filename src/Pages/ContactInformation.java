package Pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ContactInformation extends BasePage {
	
final WebDriver driver;
	
	@FindBy (how = How.ID, using = "FirstName")
	private WebElement name;
	
	@FindBy (how = How.ID, using = "LastName")
	private WebElement lastName;
	
	@FindBy (how = How.ID, using = "Birthdate")
	private WebElement birthDate;
	
	@FindBy (how = How.CSS, using = ".slds-checkbox--faux")
	private WebElement email;
	
	@FindBy (how = How.CLASS_NAME, using = "slds-checkbox--faux")
	private WebElement doesNotPossesEmail;
	
	@FindBy (how = How.ID, using = "UpdateContact")
	private WebElement UpdateContact;
	
	@FindBy (how = How.ID, using = "Contact_prevBtn")
	private WebElement PrevBtn;
	
	@FindBy (how = How.ID, using = "Contact_nextBtn")
	private WebElement NextBtn;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement cancel;
	
	@FindBy (how= How.CSS, using = ".slds-input.form-control.ng-pristine.ng-empty.ng-invalid.ng-invalid-required.ng-touched")
	private WebElement emailinput;
	
public ContactInformation(WebDriver driver){
		this.driver = driver;
        PageFactory.initElements(driver, this);
}

public void setContactInformation(String nombre, String apellido, String fechaDeNacimiento) {
	name.sendKeys(nombre);
	lastName.sendKeys(apellido);
	birthDate.sendKeys(fechaDeNacimiento);

		//doesNotPossesEmail.click();
}
public void nextPage() {
	NextBtn.click();
}

}
