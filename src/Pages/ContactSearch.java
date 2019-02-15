package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import Tests.TestBase;

public class ContactSearch extends BasePage {

	final WebDriver driver;

	@FindBy(how = How.ID, using = "SearchClientDocumentType")
	private WebElement documentType;
	
	@FindBy(how = How.ID, using = "DocumentTypeSearch")
	private WebElement documentType2;

	@FindBy(how = How.ID, using = "SearchClientDocumentNumber")
	private WebElement document;

	@FindBy(how = How.CSS, using = ".slds-radio--faux.ng-scope")
	private List<WebElement> gender;
	
	@FindBy(how = How.ID, using = "FirstName")
	private WebElement nombre;
	
	@FindBy(how = How.ID, using = "LastName")
	private WebElement apellido;
	
	@FindBy(how = How.ID, using = "Birthdate")
	private WebElement fNac;

	@FindBy(how = How.ID, using = "ContactInfo_nextBtn")
	private WebElement next;

	@FindBy(how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement cancel;

	public ContactSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void searchContact(String docType, String docValue, String genero) {
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(documentType, docType);
		document.click();
		document.sendKeys(docValue);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (genero) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		if(!genero.equals(""))
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	public void searchContact2(String docType, String docValue, String genero) {
		TestBase TB = new TestBase();
		TB.waitForClickeable(driver,By.id("DocumentInputSearch"));
		//try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(documentType2, docType);
		driver.findElement(By.id("DocumentInputSearch")).click();
		driver.findElement(By.id("DocumentInputSearch")).sendKeys(docValue);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		if(!genero.equals(""))
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	
	public void DNI(String DNI) {
		document.sendKeys(DNI);
	}

	public void sex(String genero) {
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
	}

	public void ingresarMail(String mail, String continuar) {
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id=\"EmailSelectableItems\"]/div/ng-include/div/ng-form/div[1]/div[1]/input")).sendKeys(mail);
		switch (continuar) {
		case "si":
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("Contact_nextBtn")).click();
			break; 
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void tipoValidacion(String tipoValidacion) {
		TestBase TB = new TestBase();
		TB.waitForClickeable(driver, By.id("MethodSelection_prevBtn"));
		switch (tipoValidacion) {
		case "documento":
			List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
			for (WebElement x : valdni) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por documento de identidad")) {
					x.click();
					break;
				}
			}
			break;
		case "preguntas y respuestas":
			List<WebElement> valqa = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
			for (WebElement x : valqa) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas")) {
					x.click();
					break;
				}
			}
			break;
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void tipoValidacion2(String tipoValidacion) {
		switch (tipoValidacion) {
		case "documento":
			List<WebElement> valdni = driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope"));
			for (WebElement x : valdni) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por documento de identidad")) {
					x.click();
					break;
				}
			}
			break;
		case "preguntas y respuestas":
			List<WebElement> valqa = driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope"));
			for (WebElement x : valqa) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas")) {
					x.click();
					break;
				}
			}
			break;
		}
		driver.findElement(By.id("ValidationMethod_nextBtn")).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void subirArchivo(String uploadPath, String continuar) {
		driver.findElement(By.id("FileDocumentImage")).sendKeys(uploadPath);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("DocumentMethod_nextBtn")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void subirformulario(String uploadPath, String continuar) {
		driver.findElement(By.id("UploadSignedForm")).sendKeys(uploadPath);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("PDFForm_nextBtn")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	public void Llenar_Contacto(String sNom, String sAp, String sFN ) {
		TestBase TB = new TestBase();
		TB.waitFor(driver,By.id("FirstName"));
		nombre.sendKeys(sNom);
		apellido.sendKeys(sAp);
		fNac.sendKeys(sFN);
		sleep(2000);
	}
	public void subir_cupos(String uploadPath, String continuar){
		driver.findElement(By.id("fileinput")).sendKeys(uploadPath);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.name("BTN-Importar")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void subir_regiones(String uploadPath, String continuar){
		driver.findElement(By.id("fileinput")).sendKeys(uploadPath);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.cssSelector(".btn.btn-primary.btn-sm.btn-block.btn-continuar")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
}
