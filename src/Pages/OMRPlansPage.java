package Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class OMRPlansPage extends BasePage {
	final WebDriver driver;
	static FluentWait<WebDriver> fluentWait;

	/* ELEMENTS */
	@FindBy(css = ".slds-button.cpq-item-has-children")
	private WebElement planButton;

	//Servicios Telefonia Movil
	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc000000578J0AAI')]//button")
	private WebElement serviciosTelefoniaMovil;
	
	//Servicios Basicos General Movil
	@FindBy(xpath = "//*[contains(text(),'Servicios Basicos General Movil')]//../parent::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_icon-small')]")
	private WebElement serviciosBasicosGeneralMovil;
	
	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc000000578KIAAY<01tc0000005M7ySAAS')]//button")
	private WebElement sbgmContestador;

	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc000000578KIAAY<01tc0000005JSuAAAW')]//button")
	private WebElement sbgmDDI;

	//Servicios Internet por Dia
	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc0000005M7yJAAS')]//button")
	private WebElement serviciosInternetPorDia;
	
	//Friends&Family
	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc000000578JtAAI')]//button")
	private WebElement friendsAndFamily;
	
	//Packs Opcionales
	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc0000005fMVtAAM')]//button")
	private WebElement packsOpcionales;
	
	//Renovacion de Cuota
	@FindBy(xpath = "//div[contains(concat(' ',normalize-space(@class),' '),'cpq-item-base-product-name cpq-item-product-group js-cpq-cart-product-hierarchy-path-01tc000000578LBAAY<01tc000000578KOAAY')]//button")
	private WebElement renovacionDeCuota;
	
	
	public OMRPlansPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(45, TimeUnit.SECONDS)
			.pollingEvery(3, TimeUnit.SECONDS)
			.ignoring(org.openqa.selenium.NoSuchElementException.class);
//			.fluentWait.ignoring(org.openqa.selenium.ElementNotVisibleException.class);
	}
		
	public WebElement getPlanButton() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(planButton));
		System.out.println("getPlanButton");
		return planButton;
	}
	
	public WebElement getServiciosTelefoniaMovil() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(serviciosTelefoniaMovil));
		System.out.println("getServiciosTelefoniaMovil");
		return serviciosTelefoniaMovil;
	}

	public WebElement getServiciosBasicosGeneralMovil() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(serviciosBasicosGeneralMovil));
		System.out.println("getServiciosBasicosGeneralMovil");
		return serviciosBasicosGeneralMovil;
	}

	public WebElement getSBGMContestador() {
		fluentWait.until(ExpectedConditions.visibilityOf(sbgmContestador));
		System.out.println("getSBGMContestador");
		return sbgmContestador;
	}

	public WebElement getSBGMDDI() {
		fluentWait.until(ExpectedConditions.visibilityOf(sbgmDDI));
		System.out.println("getSBGMDDI");
		return sbgmDDI;
	}

	public WebElement getServiciosInternetPorDia() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(serviciosInternetPorDia));
		System.out.println("getServiciosInternetPorDia");
		return serviciosInternetPorDia;
	}

	public WebElement getFriendsAndFamily() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(friendsAndFamily));
		System.out.println("getFriendsAndFamily");
		return friendsAndFamily;
	}

	public WebElement getPacksOpcionales() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(packsOpcionales));
		System.out.println("getPacksOpcionales");
		return packsOpcionales;
	}

	public WebElement getRenovacionDeCuota() {
		fluentWait.until(ExpectedConditions.elementToBeClickable(renovacionDeCuota));
		System.out.println("getRenovacionDeCuota");
		return renovacionDeCuota;
	}
	
	
	
	private WebElement findAddToCartButtonByServiceName(String service) {
		String addToCartButtonXpath = "//*[contains(text(),'" + service + "')]//../parent::*//../following-sibling::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_neutral') and contains(text(),'Add to Cart')]";
		System.out.println("findAddToCartButtonByServiceName");
		return driver.findElement(By.xpath(addToCartButtonXpath));
//		WebElement addToCartButton = fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(addToCartButtonXpath)))); 
//		return addToCartButton; 
	}

	private WebElement findShowActionsButtonByServiceName(String service) {
		String showActionsButtonXpath = "//*[contains(text(),'" + service + "')]//../parent::*//../following-sibling::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_icon-border-filled cpq-item-actions-dropdown-button')]";
		System.out.println("findShowActionsButtonByServiceName");
		return driver.findElement(By.xpath(showActionsButtonXpath));
//		WebElement showActionsButton = fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(showActionsButtonXpath))));
//		return showActionsButton;
	}
	
	private WebElement findDeleteServiceButton(WebElement showActionsButton) {
		System.out.println("findDeleteServiceButton");
		return showActionsButton.findElement(By.xpath("//../child::*//span[contains(.,'Delete')]"));
//		WebElement deleteServiceButton = fluentWait.until(ExpectedConditions.elementToBeClickable(
//				showActionsButton.findElement(By.xpath("//../child::*//span[contains(.,'Delete')]"))));	
//		return deleteServiceButton;	
	}
	
	//*[contains(text(),'espera')]//../parent::*//../following-sibling::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_icon-border-filled cpq-item-actions-dropdown-button')]//../following-sibling::*//span[contains(.,'Delete')]
	//	WebElement deleteServiceButton = driver.findElement(By.xpath("//*[contains(text(),'Llamada en espera')]//../parent::*//../following-sibling::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_icon-border-filled cpq-item-actions-dropdown-button')]//../following-sibling::*//span[contains(.,'Delete')]"));
	//*[contains(text(),'Llamada en espera')]//../parent::*//../following-sibling::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_icon-border-filled cpq-item-actions-dropdown-button')]//../following-sibling::*//span[contains(.,'Delete')]

	
	public void addServiceToCartByName(String service) {
		sleep(5000);
		findAddToCartButtonByServiceName(service).click();
	}
		
	public void deleteService(String service) {
		sleep(5000);
		CustomerCare page = new CustomerCare(driver);
		WebElement showActionsButton = findShowActionsButtonByServiceName(service);
		page.obligarclick(showActionsButton);
		System.out.println("Click showActionsButton");
		sleep(10000);
		WebElement deleteServiceButton = findDeleteServiceButton(showActionsButton);
		page.obligarclick(deleteServiceButton);
		System.out.println("Click deleteServiceButton");
		sleep(2000);
		WebElement confirmDeleteButton = driver.findElement(By.xpath("//button[contains(text(),'Delete')]"));
		System.out.println("confirmDeleteButton");
		confirmDeleteButton.click();
		System.out.println("Click confirmDeleteButton");
//		WebElement confirmDeleteButton = fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Delete')]"))));
//		confirmDeleteButton.click();
	}
	
	//By.cssSelector(".slds-button.slds-button--destructive")
	
	public void configureService(String service) {
		WebElement showActionsButton = findShowActionsButtonByServiceName(service);
		showActionsButton.click();
		WebElement configureServiceButton = fluentWait.until(ExpectedConditions.elementToBeClickable(showActionsButton.findElement(By.xpath("//../following-sibling::*//*[contains(.,'Configure')]"))));
		configureServiceButton.click();
	}
	
	public void borrarServicio(String service) {
		sleep(5000);
		CustomerCare page = new CustomerCare(driver);
		WebElement showActionsButton = findShowActionsButtonByServiceName(service);
		page.obligarclick(showActionsButton);
		System.out.println("Click showActionsButton");
		sleep(10000);
		List<WebElement> opc = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown_right.cpq-item-actions-dropdown"));
		for(WebElement UnaO: opc) {
			if(UnaO.getText().toLowerCase().contains("delete")) {
				List<WebElement> opc2 = UnaO.findElements(By.tagName("a"));
				for(WebElement OtraO: opc2) {
					if(OtraO.getText().toLowerCase().contains("delete")) {
						page.obligarclick(OtraO);
						break;
					}
				}
			}
		}
		//WebElement deleteServiceButton = findDeleteServiceButton(showActionsButton);
		//page.obligarclick(deleteServiceButton);
		System.out.println("Click deleteServiceButton");
		sleep(2000);
		WebElement confirmDeleteButton = driver.findElement(By.xpath("//button[contains(text(),'Delete')]"));
		System.out.println("confirmDeleteButton");
		confirmDeleteButton.click();
		System.out.println("Click confirmDeleteButton");
//		WebElement confirmDeleteButton = fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Delete')]"))));
//		confirmDeleteButton.click();
	}
	
	//*[contains(text(),'Llamada en espera')]//../parent::*//../following-sibling::*//*[contains(concat(' ',normalize-space(@class),' '),'slds-button slds-button_icon-border-filled cpq-item-actions-dropdown-button')]//../following-sibling::*//span[contains(.,'Delete')]
}
