package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class customerInformation extends BasePage {
	
	final WebDriver driver;
	
	@FindBy (how = How.ID, using = "FirstName")
	private WebElement name;
	
	@FindBy (how = How.ID, using = "LastName")
	private WebElement lastName;
	
	@FindBy (how = How.ID, using = "DocumentType")
	private WebElement documentType;
	
	@FindBy (how = How.ID, using = "DocumentNumber")
	private WebElement document;
	
	@FindBy (how = How.ID, using = "Cuil")
	private WebElement cuil;
	
	@FindBy (how = How.ID, using = "Birthdate")
	private WebElement birthDate;
	
	@FindBy (how = How.ID, using = "Gender")
	private WebElement gender;
	
	@FindBy (how = How.ID, using = "Email")
	private WebElement email;
	
	@FindBy (how = How.ID, using = "MobilePhone")
	private WebElement mobilePhone;
	
	@FindBy (how = How.ID, using = "Phone")
	private WebElement phone;
	
	@FindBy (how = How.ID, using = "OtherPhone")
	private WebElement otherPhone;
	
	@FindBy (how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private List<WebElement> buttons;
	
	@FindBy (how = How.CLASS_NAME, using = "ng-binding")
	private List<WebElement> update;
	
	public customerInformation(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String isLastNameRequired() {
		return lastName.getAttribute("required").toString();
	}
	
	public String isFirstNameRequired() {
		return name.getAttribute("required").toString();
	}
	
	public String isEmailRequired() {
		return email.getAttribute("required").toString();
	}
	
	public String isDocumentRequired() {
		return document.getAttribute("required").toString();
	}
	
	public String isBirthDateRequired() {
		return birthDate.getAttribute("required").toString();
	}
	
	public String isGenderRequired() {
		return gender.getAttribute("required").toString();
	}
	
	public String isMobilePhoneRequired() {
		return mobilePhone.getAttribute("required").toString();
	}
	
	public void setFieldsWhichDontTriggerIdentityValidationProcess() {
		name.clear();
		name.sendKeys("Bbb");
		lastName.clear();
		lastName.sendKeys("Bbb");
//		setSimpleDropdown(documentType, "CUIL");
		setSimpleDropdown(gender, "Femenino");
		birthDate.clear();
		birthDate.sendKeys("11/06/1985");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void setDefaultValues() {
		name.clear();
		name.sendKeys("Fernandoasd");
		lastName.clear();
		lastName.sendKeys("Careeeeee");
//		setSimpleDropdown(documentType, "DNI");
//		document.clear();
//		document.sendKeys("12345678");
		email.clear();
		email.sendKeys("facundo.dilemme@live.com");
		setSimpleDropdown(gender, "Fernando");
		mobilePhone.clear();
		mobilePhone.sendKeys("1164853799");
		//phone.clear();
		//phone.sendKeys("1122332233");
		otherPhone.clear();
		otherPhone.sendKeys("1122332233");
		cuil.clear();
		cuil.sendKeys("23267493724");
		birthDate.clear();
		birthDate.sendKeys("04/05/2049");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void setTwoFieldsWhichDontTriggerIdentityValidationProcess() {
		setSimpleDropdown(gender, "Femenino");
		birthDate.clear();
		birthDate.sendKeys("11/06/1985");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void setThreeFieldsWhichTriggerIdentityValidationProcess() {
		setSimpleDropdown(gender, "Femenino");
		document.clear();
		document.sendKeys("32645423");
		birthDate.clear();
		birthDate.sendKeys("11/06/1985");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void modifyDocument(String number) {
		document.clear();
		document.sendKeys(number);
		getElementFromList(update, "Actualizar").click();
	}
	
	public Boolean isDocumentModifyable() {
		Boolean a = true;
		try { document.clear(); } catch (org.openqa.selenium.InvalidElementStateException e) { a = false; }
		return a;
	}
	
	public Boolean isBirthDateAValidDateFormat() {
		birthDate.clear();
		birthDate.sendKeys("11A06[]1985");
		getElementFromList(update, "Actualizar").click();
		try {
			driver.switchTo().alert().accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isYearPickerPresentInBirthDatePicker() {
		Boolean a = false;
		birthDate.clear();
		driver.findElement(By.className("datepicker--nav-title")).click();
		driver.findElement(By.className("datepicker--nav-title")).click();
		if(driver.findElements(By.cssSelector(".datepicker--cell.datepicker--cell-year")).size() != 0) {
			a = true;
		}
		return a;
	}
	
	public String getCurrentValue() {
		return name.getAttribute("value").toString();
	}
	
	public void modifyNameAndCancel() {
		name.clear();
		name.sendKeys("Test");
		getElementFromList(buttons, "Cancelar").click();
		driver.switchTo().alert().accept();
	}
	
	public void modifyMobilePhone() {
		mobilePhone.clear();
		mobilePhone.sendKeys("1159241458");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void modifyOtherPhone() {
		otherPhone.clear();
		otherPhone.sendKeys("45534451");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void modifyFirstName() {
		otherPhone.clear();
		otherPhone.sendKeys("Bbb");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void modifyBirthDate() {
		birthDate.clear();
		birthDate.sendKeys("11/06/1980");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void modifyEmail() {
		email.clear();
		email.sendKeys("lalalala@lala.com");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void modifyLastName() {
		lastName.clear();
		lastName.sendKeys("Bbb");
		getElementFromList(update, "Actualizar").click();
	}
	
	public void areNumbersAllowedInFirstNameAndLastName() {
		//Boolean a = false;
		name.clear();
		name.sendKeys("1234");
		lastName.clear();
		lastName.sendKeys("1234");
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"a1zc0000003WDyFAAW-3\"]/div[1]/div/child[2]/div/ng-form/div[2]/div[2]/div/div[2]/small[8]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"a1zc0000003WDyFAAW-3\"]/div[1]/div/child[3]/div/ng-form/div[2]/div[2]/div/div[2]/small[8]")).isDisplayed());
		/*List<WebElement> lista = driver.findElements(By.cssSelector(".description.ng-binding"));
		if((lista.get(6).getText().equals("El Nombre No Debe Tener Números.")) &&
		   (lista.get(13).getText().equals("El Apellido No Debe Tener Números."))){
			a = true;
			}	
		return a;*/
	}
	
	public void modifyDniBy(String doc) {
		document.clear();
		document.sendKeys(doc);
		getElementFromList(update, "Actualizar").click();
	}
	
	public Boolean areLettersAllowedInCuil() {
		
		Boolean a = false;
		cuil.clear();
		try {
		cuil.sendKeys("aaa");
	    } catch (NoSuchElementException e) {	
	    }	
		List<WebElement> lista = driver.findElements(By.cssSelector(".description.ng-binding"));

		if((lista.get(9).getText().equals("Required"))){
			a = true;
			}	
		return a;
	}
	public void modifyCuil() {
		cuil.clear();
		cuil.sendKeys("23267493724");
		getElementFromList(update, "Actualizar").click();
	}
	public boolean notchansgetopname() {
		boolean a = false;
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[text() ='Detalles']")).click();
		List<WebElement> frame1 = driver.findElements(By.tagName("iframe"));
		driver.switchTo().frame(frame1.get(2));
		List <WebElement> name = driver.findElements(By.className("topName"));
		if((name.get(0).getText().equals("Aaa Aaa"))){
			a = true;
		}
		return a;
	}

	public boolean validarcaractermovilalternativo() {
		boolean a = false;
		BasePage cambioFrameByID=new BasePage();
	    try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("profile-edit")));
	    driver.findElement(By.className("profile-edit")).click();
	    try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("LastName")));
	    List<WebElement> errores = driver.findElements(By.cssSelector(".vlc-slds-error-block.ng-scope"));
	    driver.findElement(By.id("MobilePhone")).sendKeys("a");
	    for(WebElement aa:errores) {
	    	if(aa.getText().equals("Longitud Mínima De 10 Solo Números."))
	    		return true;
	    }	    	    
	    return false;
	}

	public boolean validarcaracterespecialesNyA() {
		boolean a = false;
		BasePage cambioFrameByID=new BasePage();
	    try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("profile-edit")));
	    driver.findElement(By.className("profile-edit")).click();
	    try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("LastName")));
	    List<WebElement> errores = driver.findElements(By.cssSelector(".vlc-slds-error-block.ng-scope"));
	    driver.findElement(By.id("FirstName")).sendKeys("/*!#$");;
	    driver.findElement(By.id("LastName")).sendKeys("/*!#$");
	    if (errores.get(0).getText().equals("asd") && errores.get(1).getText().equals("asd")) {
	    	a = true;
	    }    
		return false;
	}
	
}
