package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Ta_CPQ_Validate extends BasePage {
	
final WebDriver driver;

@FindBy (how = How.ID, using = "State")
private WebElement provinceInput;

@FindBy (how = How.ID, using = "City")
private WebElement localityInput;

@FindBy (how = How.ID, using = "Street")
private WebElement streetInput;

@FindBy (how = How.ID, using = "StreetNumber")
private WebElement streetNumberInput;

@FindBy (how = How.ID, using = "PostalCode")
private WebElement postalCodeInput;

@FindBy (how = How.XPATH, using = "//span[@class=\"slds-form-element__label ng-binding\"][text()=\"Casa\"]")
private WebElement typeHomeCasa;

@FindBy (how = How.XPATH, using = "//span[@class=\"slds-form-element__label ng-binding\"][text()=\"Local\"]")
private WebElement typeHomeLocal;

@FindBy (how = How.XPATH, using = "//span[@class=\"slds-form-element__label ng-binding\"][text()=\"Edificio\"]")
private WebElement typeHomeEdificio;

@FindBy (how = How.ID, using = "ZoneType")
private WebElement typeZoneInput;

@FindBy (how = How.ID, using = "AddressInput_nextBtn")
private WebElement nextButton;

@FindBy (how= How.ID, using = "Comments")
private WebElement commentsTextArea;

@FindBy (how= How.ID, using = "SidestreetA")
private WebElement betweenStreet1;

@FindBy (how= How.ID, using = "SidestreetB")
private WebElement betweenStreet2;

@FindBy (how= How.ID, using = "Backstreet")
private WebElement backStreet;

@FindBy (how= How.ID, using = "Neighborhood")
private WebElement neighbourhood; //it's neighbourhood, bitches!

@FindBy (how = How.ID, using = "EstablishmentName")
private WebElement establishmentName;

public Ta_CPQ_Validate(WebDriver driver){
	this.driver = driver;
    PageFactory.initElements(driver, this);
}

public void completeRequiredFields(String province, String locality, String street, String streetNumber, String postalCode,  String typeZone) {
	setSimpleDropdown (provinceInput, province);
	setSimpleDropdown (localityInput, locality);
	streetInput.sendKeys(street);
	streetNumberInput.sendKeys(streetNumber);
	postalCodeInput.sendKeys(postalCode);
	setSimpleDropdown(typeZoneInput, typeZone);
	
}

public void completeWithTypeHomeEdificio (String floorNumber, String department) {
	typeHomeEdificio.click();
	try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	
	WebElement floorNumberInput = driver.findElement(By.id("FloorNumber"));
	WebElement departmentInput = driver.findElement(By.id("Department"));
	
	
	floorNumberInput.sendKeys(floorNumber);
	departmentInput.sendKeys(department);
}

public WebElement getProvinceInput() {
	return provinceInput;
}

public void setProvinceInput(WebElement provinceInput) {
	this.provinceInput = provinceInput;
}

public WebElement getLocalityInput() {
	return localityInput;
}

public void setLocalityInput(WebElement localityInput) {
	this.localityInput = localityInput;
}

public WebElement getStreetInput() {
	return streetInput;
}

public void setStreetInput(WebElement streetInput) {
	this.streetInput = streetInput;
}

public WebElement getStreetNumberInput() {
	return streetNumberInput;
}

public void setStreetNumberInput(WebElement streetNumberInput) {
	this.streetNumberInput = streetNumberInput;
}

public WebElement getPostalCodeInput() {
	return postalCodeInput;
}

public void setPostalCodeInput(WebElement postalCodeInput) {
	this.postalCodeInput = postalCodeInput;
}

public WebElement getTypeHomeCasa() {
	return typeHomeCasa;
}

public void setTypeHomeCasa(WebElement typeHomeCasa) {
	this.typeHomeCasa = typeHomeCasa;
}

public WebElement getTypeHomeLocal() {
	return typeHomeLocal;
}

public void setTypeHomeLocal(WebElement typeHomeLocal) {
	this.typeHomeLocal = typeHomeLocal;
}

public WebElement getTypeHomeEdificio() {
	return typeHomeEdificio;
}

public void setTypeHomeEdificio(WebElement typeHomeEdificio) {
	this.typeHomeEdificio = typeHomeEdificio;
}

public WebElement getTypeZoneInput() {
	return typeZoneInput;
}

public void setTypeZoneInput(WebElement typeZoneInput) {
	this.typeZoneInput = typeZoneInput;
}

public WebElement getNextButton() {
	return nextButton;
}

public void setNextButton(WebElement nextButton) {
	this.nextButton = nextButton;
}

public WebElement getCommentsTextArea() {
	return commentsTextArea;
}

public void setCommentsTextArea(WebElement commentsTextArea) {
	this.commentsTextArea = commentsTextArea;
}

public WebElement getBetweenStreet1() {
	return betweenStreet1;
}

public void setBetweenStreet1(WebElement betweenStreet1) {
	this.betweenStreet1 = betweenStreet1;
}

public WebElement getBetweenStreet2() {
	return betweenStreet2;
}

public void setBetweenStreet2(WebElement betweenStreet2) {
	this.betweenStreet2 = betweenStreet2;
}

public WebElement getBackStreet() {
	return backStreet;
}

public void setBackStreet(WebElement backStreet) {
	this.backStreet = backStreet;
}

public WebElement getNeighbourhood() {
	return neighbourhood;
}

public void setNeighbourhood(WebElement neighborhood) {
	this.neighbourhood = neighborhood;
}

public WebElement getEstablishmentName() {
	return establishmentName;
}

public void setEstablishmentName(WebElement establishmentName) {
	this.establishmentName = establishmentName;
}




}



