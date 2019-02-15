package Tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;

public class TechnicalCareCSRAutogestion extends TestBase {
	private WebDriver driver;
	private String cuentaNombre;
	private int bandera;
	
	public void buscarCaso(String nCaso) {
		driver.switchTo().defaultContent();
		sleep(1000);
		WebElement Buscador=driver.findElement(By.xpath("//input[@id='phSearchInput']"));
		Buscador.sendKeys(nCaso);
		sleep(2000);
		try {
		driver.findElement(By.className("autoCompleteRowLink")).click();
		sleep(2000);
		Buscador.clear();}
		catch(org.openqa.selenium.NoSuchElementException e) {
			sleep(7000);
		Buscador.submit();
		sleep(1000);
		Buscador.clear();
		sleep(2000);
		BasePage cambioFrameByID=new BasePage();
		int i=0;
		
		while(i<3) {
			try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("searchResultsWarningMessageBox")));
			if(driver.findElement(By.id("searchResultsWarningMessageBox")).isDisplayed()) {
				driver.navigate().refresh();
				sleep(2000);
				i++;
				//System.out.println(i);
				}
			}
			catch (java.lang.NullPointerException a){
				sleep(3000);
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("searchPageHolderDiv")));
				i=4;
				System.out.println("Segundo Catch");
			}
		}
		sleep(2000);
		WebElement Caso=driver.findElement(By.cssSelector(".listRelatedObject.caseBlock")).
				findElement(By.cssSelector(".bPageBlock.brandSecondaryBrd.secondaryPalette")).
				findElement(By.className("pbBody")).findElement(By.className("list")).
				findElements(By.tagName("tr")).get(1).
				findElement(By.tagName("th")).findElement(By.tagName("a"));
		Caso.click();
		}
		sleep(2000);
	}
	
	@BeforeClass(alwaysRun=true) 
	public void init() throws Exception
	{
		bandera =0;
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		HomeBase homePage = new HomeBase(driver);
	     if(driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
	    	 homePage.switchAppsMenu();
	    	 try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    	 homePage.selectAppFromMenuByName("Ventas");
	    	 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}    
	     }
	     homePage.switchAppsMenu();
	     try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     homePage.selectAppFromMenuByName("Consola FAN");
	     try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	     CustomerCare cerrar = new CustomerCare(driver);
	     cerrar.cerrarultimapestana();
	     goToLeftPanel2(driver, "Cuentas");
	     try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeMethod(alwaysRun=true)  
	public void setUp() throws Exception {
	     Accounts accountPage = new Accounts(driver);
	     driver.switchTo().defaultContent();
	     accountPage.accountSelect("Vista Tech");
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     WebElement account = driver.findElement(By.cssSelector(".x-grid3-cell-inner.x-grid3-col-ACCOUNT_NAME"));
		 cuentaNombre = account.findElement(By.tagName("span")).getText();
		 account.findElement(By.tagName("span")).click();
		 try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
		
	}
	
	//@AfterMethod(alwaysRun=true) 
	 public void afterMethod() {
		driver.switchTo().defaultContent();
		List<WebElement> ctas = driver.findElement(By.cssSelector(".x-tab-strip.x-tab-strip-top")).findElements(By.tagName("li"));
		ctas.remove(0);
		for (WebElement cta : ctas) {
			if (cta.findElement(By.className("tabText")).getText().equals(cuentaNombre)) {
				Actions action = new Actions(driver);
				action.moveToElement(cta);
				action.moveToElement(cta.findElement(By.className("x-tab-strip-close"))).click().build().perform();
				break;
			}
				
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		  }
	
	//@AfterClass(alwaysRun=true) 
	public void tearDown2() {
		driver.switchTo().defaultContent();
		try{ for(WebElement e : driver.findElements(By.className("x-tab-strip-close"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		} } catch (org.openqa.selenium.StaleElementReferenceException e) {}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.findElement(By.id("tsidButton")).click();
		List<WebElement> options = driver.findElement(By.id("tsid-menuItems")).findElements(By.tagName("a"));

		for (WebElement option : options) {
			if(option.getText().toLowerCase().equals("Ventas".toLowerCase())){
				option.click();
				break;
			}
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.quit();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test(groups = {"Fase3","TechnicalCare", "Autogestion","Ola2"})
	public void TS51149_Autogestion_Verificacion_De_Que_Exista_La_Opcion_De_Autogestion() {
		 driver.switchTo().defaultContent();
		 Accounts accountPage = new Accounts(driver);
		 driver.switchTo().frame(accountPage.getFrameForElement(driver, By.className("actions-content")));
		 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}   
		 driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
		 driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("autogesti");
		List<WebElement> butons = driver.findElements(By.xpath("//*[text() = 'Diagn\u00f3stico de Autogesti\u00f3n']"));
		assertTrue(butons.get(0).isDisplayed());
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"Fase3","TechnicalCare", "Autogestion","Ola2"})
	public void TS51150_Autogestion_Verificacion_De_La_Existencia_Interfaz_De_Autogestion() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		assertTrue(driver.findElement(By.id("SelfManagementStep_nextBtn")).isDisplayed());
	}
	
	/*@Test(groups = "Fase3")
	public void Caso_Nico() {
		Accounts accountPage = new Accounts(driver);
		accountPage.closeAccountServiceTabByName("Servicios");
		accountPage.findAndClickButton("facturación");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.cssSelector(".form-control.services-input-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("000");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}*/
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51151_Autogestion_Verificacion_Del_Canal() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		assertTrue(driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding")).getText().toLowerCase().equals("canal"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51152_Autogestion_Verificacion_Del_Servicio() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		assertTrue(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")).get(1).getText().toLowerCase().equals("servicio"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51153_Autogestion_Verificacion_De_Lista_Canal_Y_Servicios() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByIndex(1);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByIndex(1);
	    assertTrue(!listSelect.getFirstSelectedOption().getText().isEmpty());
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51154_Autogestion_Verificacion_Del_Servicio_Asterisco_111() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*111");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*111"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51155_Autogestion_Verificacion_Del_Servicio_Asterisco_878() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*878 (Saldo Virtual)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*878"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51156_Autogestion_Verificacion_Del_Servicio_Asterisco_150() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*150 (saldo)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*150"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51157_Autogestion_Verificacion_Del_Servicio_Asterisco_152() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*152 (packs, nros amigos)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*152 (packs, nros amigos)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51158_Autogestion_Verificacion_Del_Servicio_Asterisco_2582() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*CLUB (*2582)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*club (*2582)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51159_Autogestion_Verificacion_Del_Servicio_Asterisco_25225() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*25225 (Black)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*25225 (black)"));
	}

	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51160_Autogestion_Verificacion_Del_Servicio_Asterisco_25283() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*25283 (Clave)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*25283 (clave)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51161_Autogestion_Verificacion_Del_Servicio_Asterisco_77666() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*77666 (Promo)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*77666 (promo)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51163_Autogestion_Verificacion_Del_Servicio_Asterisco_7526() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*7526 (PLAN)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*7526 (plan)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51164_Autogestion_Verificacion_Del_Servicio_Asterisco_2447() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*2447 (Chip)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*2447 (chip)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51165_Autogestion_Verificacion_Del_Servicio_Asterisco_333() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*333 (recarga delivery)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*333 (recarga delivery)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51166_Autogestion_Verificacion_Del_Servicio_Asterisco_151() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*151 (recargas)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*151 (recargas)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51167_Autogestion_Verificacion_Del_Servicio_Asterisco_767() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*767 (SOS)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*767 (sos)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51168_Autogestion_Verificacion_Del_Servicio_Asterisco_555() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*555 (contestador)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*555 (contestador)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51169_Autogestion_Verificacion_Del_Servicio_Asterisco_99999() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*99999 (PBP)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*99999 (pbp)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51170_Autogestion_Verificacion_Del_Servicio_Asterisco_88988() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*88988 (SAEC)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*88988 (saec)"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51171_Autogestion_Verificacion_Del_Servicio_Asterisco_910() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*910");
	    assertTrue(listSelect.getFirstSelectedOption().getText().toLowerCase().contains("*910"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51172_Autogestion_Asterisco_Verificacion_De_Opciones_De_Inconvenientes() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*150 (saldo)");
		assertTrue(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")).get(2).getText().toLowerCase().equals("motivo del inconveniente"));
		listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
		listSelect.selectByIndex(1);
		assertTrue(!listSelect.getFirstSelectedOption().getText().isEmpty());
	}
	
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51173_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_111() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"otros","la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta","inconv con derivaci\u00f3n a representante"};
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*111");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    List<String> motNoms = new ArrayList<String>();
	    for (WebElement UnMot : motivos) {
	    	motNoms.add(UnMot.getText().toLowerCase());
	    }
	    for (String uno : todos) {
			assertTrue(motNoms.contains(uno));
		}

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51174_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_878() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"otros","la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta"};
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*878 (Saldo Virtual)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51175_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_150() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"otros","la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta"};
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*150 (saldo)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51176_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_152() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","inconv. compra de packs","inconv alta nro. amigos","inconv datos de nr. amigos","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*152 (packs, nros amigos)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51177_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_2582() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","inconv con derivaci\u00f3n a representante","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*CLUB (*2582)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51179_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_25225() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","inconv con derivaci\u00f3n a representante","informa sistema fuera de servicio","informacion incorrecta"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*25225 (Black)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51180_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_25283() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","no valida contraseña","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*25283 (Clave)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51181_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_77666() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*77666 (Promo)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51182_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_7526() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*7526 (PLAN)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51183_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_2447() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*2447 (Chip)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51184_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_333() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","inconv recarga delivery","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*333 (recarga delivery)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51185_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_151() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","inconv quema de pin saldo","inconv quema de pin sms","inconv recarga con tc","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*151 (recargas)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51186_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_767() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","inconv con recarga sos","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*767 (SOS)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51187_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_555() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*555 (contestador)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51188_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_99999() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","inconv con derivaci\u00f3n a representante","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*99999 (PBP)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51189_Autogestion_Verificacion_Lista_De_Inconvenientes_Asterisco_88988() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","inconv con derivaci\u00f3n a representante","informacion incorrecta","otros ( para completar campo)"};//falta otros************
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*88988 (SAEC)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51190_Autogestion_Verificacion_Lista_De_Inconvenientes_910() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"otros","la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","informacion incorrecta","inconv con derivaci\u00f3n a representante"};
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*910");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    List<String> motNoms = new ArrayList<String>();
	    for (WebElement UnMot : motivos) {
	    	motNoms.add(UnMot.getText().toLowerCase());
	    }
	    for (String uno : todos) {
			assertTrue(motNoms.contains(uno));
		}

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51191_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_288() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*288/*788 (788 asistencia en ruta)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*288"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51195_Autogestion_Verificacion_Lista_De_Inconvenientes_288() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*288/*788 (788 asistencia en ruta)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51196_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_788() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*288/*788 (788 asistencia en ruta)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*788"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51198_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_120() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*120 (cambio de numeracion)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*120"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51201_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_222() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*222 (ACA)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51202_Autogestion_Verificacion_De_Inconveniente_Asterisco_237() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*237 (Aerolineas Argentinas)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*237"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51203_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_237() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*237 (Aerolineas Argentinas)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51204_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_242643() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*242643 (Banco Piano)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*242643"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51205_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_242643() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*242643 (Banco Piano)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51206_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_2463() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*2463");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*2463"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51207_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_2463() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*2463");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51208_Autogestion_Verificacion_De_Inconveniente_Asterisco_2484() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*2484 (Citi)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*2484"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51209_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_2484() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*2484 (Citi)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51210_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_272() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*272 (Buquebus)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*272"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51211_Autogestion_Verificacion_De_Inconveniente_Asterisco_272() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*272 (Buquebus)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51212_Autogestion_Verificacion_De_Inconveniente_Asterisco_27638() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*27638 (Arnet)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*27638"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51213_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_27638() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*27638 (Arnet)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51214_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_347() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*347");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*347"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51215_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_347() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*347");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51216_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_380() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*380");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*380"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51217_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_380() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*380");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51218_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_746() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*746 (Banco Rio, super linea)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*746"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51219_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_746() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*746 (Banco Rio, super linea)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51220_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_8742() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*8742");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*8742"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51221_Autogestion_Verificacion_De_Lista_De_Opciones_Asterisco_8472() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*8472/*847");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51222_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_874() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*8472/*847");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*847"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51223_Autogestion_Verificacion_De_La_Seleccion_Canal_Asterisco_Y_Servicio_746() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("*746 (Banco Rio, super linea)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("*746"));
	}
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51224_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_100() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("100");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("100"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51225_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_746() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		try {listSelect.selectByVisibleText("746");}
		catch(org.openqa.selenium.NoSuchElementException exne) {assertTrue(true);}
	    assertFalse(listSelect.getFirstSelectedOption().getText().contains("746"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51226_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_101() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("101");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("101"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51227_Autogestion_Verificacion_De_Lista_De_Opciones_Canal_Nros_De_Emergencia_Y_Servicio_101() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("101");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51228_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_102() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("102");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("102"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51229_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_102() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("102");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51230_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_103() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("103");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("103"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51231_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_103() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("103");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51232_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_106() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("106");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("106"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51233_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_106() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("106");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51234_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_107() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("107");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("107"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51235_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_107() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("107");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51236_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_131() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("131");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("131"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51237_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_131() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("131");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51238_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_136() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("136");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("136"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51239_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_136() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("136");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51240_Autogestion_Verificacion_De_La_Seleccion_Canal_Nros_De_Emergencia_Y_Servicio_911() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("911");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("911"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51241_Autogestion_Verificacion_De_Lista_De_Inconvenientes_Canal_Nros_De_Emergencia_Y_Servicio_911() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio"};
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("911");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51242_Autogestion_Verificacion_De_La_Seleccion_Canal_0800_Y_Servicio_08004440800() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("0800");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("0800-444-0800 (AC)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("0800-444-0800"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51244_Autogestion_Verificacion_De_La_Seleccion_Canal_0800_Y_Servicio_08004444434() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("0800");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("0800-444-4434 (Placas)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("0800-444-4434"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51245_Autogestion_Verificacion_Lista_De_Inconvenientes_Canal_0800_Servicio_08004444434() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"informacion incorrecta","la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado","la llamada se cae","informa sistema fuera de servicio","inconv con derivaci\u00f3n a representante","no valida contraseña","otros ( para completar este campo)"};//falta ,"otros ( para completar este campo)"
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("0800");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("0800-444-4434 (BAM)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51246_Autogestion_Verificacion_De_La_Seleccion_Canal_0800_Y_Servicio_08000800888422() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("0800");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("0800-888-4422 (Atenci\u00f3n Pymes)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("0800-888-4422"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51248_Autogestion_Verificacion_De_La_Seleccion_Canal_0800_Y_Servicio_08008887382() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("0800");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("0800-888-7382 (Activaciones)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("0800-888-7382"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51252_Autogestion_Verificacion_Lista_De_Inconvenientes_Canal_Web_Servicio_Servicios() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"modulo caído","sitio caído","informaci\u00f3n incompleta","informacion incorrecta","inconveniente para alta de fol","incon.  alta de nros. amigos","inconveniente para alta da","no envía configuraci\u00f3n","no informa pin y puk","inconveniente con informe de pago", "incon.con compra de packs","otros"};//falta ,"otros"
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Servicios");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51254_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Mi_Cuenta() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Mi cuenta");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Mi cuenta"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51257_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Club_Personal() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Club Personal - Web");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Club Personal"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51259_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Planes() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Planes");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Planes"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51261_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Packs() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Packs");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Packs"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51263_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Promociones() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Promociones");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Promociones"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51266_Autogestion_Verificacion_Lista_De_Inconvenientes_Canal_Web_Servicio_Tienda_On_Line() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"modulo caído","sitio caído","informaci\u00f3n incompleta","informacion incorrecta","inconveniente para alta de fol","incon.  alta de nros. amigos","inconveniente para alta da","no envía configuraci\u00f3n","no informa pin y puk","inconveniente con informe de pago", "incon.con compra de packs","otros"};//falta ,"otros"
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Tienda On lIne");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51268_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_App_Musica_Y_Mas() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Apps, Musica y +");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Apps, Musica"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51275_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Internet_Movil() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Internet Movil");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Internet Movil"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51277_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Internacional() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Internacional");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Internacional"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51279_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Personal_Black() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Personal Black");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Personal Black"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51280_Autogestion_Verificacion_Lista_De_Inconvenientes_Canal_Web_Servicio_Personal_Black() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"modulo caído","sitio caído","informaci\u00f3n incompleta","informacion incorrecta","inconveniente para alta de fol","incon.  alta de nros. amigos","inconveniente para alta da","no envía configuraci\u00f3n","no informa pin y puk","inconveniente con informe de pago", "incon.con compra de packs","otros"};//falta ,"otros"
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Personal Black");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51281_Autogestion_Verificacion_La_Seleccion_Canal_Web_Y_Servicio_Contacto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Contacto (CHAT)");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Contacto"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51282_Autogestion_Verificacion_Lista_De_Inconvenientes_Canal_Web_Servicio_Contacto_Chat() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"modulo caído","sitio caído","informaci\u00f3n incompleta","inconveniente para alta de fol","incon.  alta de nros. amigos","inconv con derivaci\u00f3n a representante","inconveniente para alta da","no envía configuraci\u00f3n","no informa pin y puk","inconveniente con informe de pago","incon.con compra de packs","informacion incorrecta","otros"};//falta ,"informacion incorrecta","otros"
		try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WEB");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Contacto (CHAT)");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51283_Autogestion_Verificacion_La_Seleccion_Canal_App_Y_Servicio_Mi_Facturacion() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Mi Facturacion");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Mi Facturacion"));
	}
	
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51284_Autogestion_Verificacion_Lista_De_Inconvenientes_Canal_App_Servicio_Mi_Facturacion() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"abre aplicaci\u00f3n y cierra automáticamente","informaci\u00f3n incompleta","informacion incorrecta","inconvenientes con informa pago","inconv. compra de packs","otros"};//falta "otros"
		try {Thread.sleep(14000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Mi Facturacion");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));

	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51285_Autogestion_Verificacion_La_Seleccion_Canal_App_Y_Servicio_Mi_Linea() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Mi Linea");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Mi Linea"));
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51286_Autogestion_APP_Mi_Linea_Visualizacion_De_Lista_De_Inconvenientes() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		String[] todos = {"otros","inconvenientes con informa pago","informacion incorrecta","informaci\u00f3n incompleta","inconv. compra de packs","abre aplicaci\u00f3n y cierra automáticamente"};
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Mi Linea");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
	    List<WebElement> motivos = listSelect.getOptions();
	    List<String> motNoms = new ArrayList<String>();
	    for (WebElement UnMot : motivos) {
	    	motNoms.add(UnMot.getText().toLowerCase());
	    }
	    for (String uno : todos) {
			assertTrue(motNoms.contains(uno));
		}
	    
	}
	
	@Test(groups = {"TechnicalCare", "Autogestion","Ola2"})
	public void TS51291_Autogestion_Verificacion_La_Seleccion_Canal_App_Y_Servicio_Club_Personal() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Club Personal");
	    assertTrue(listSelect.getFirstSelectedOption().getText().contains("Club Personal"));
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73990_Autogestion_Asteriscos_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Asteriscos TP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73991_Autogestion_Otros_Asteriscos_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Otros Asteriscos");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73992_Autogestion_nros_Emergencia_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Nros. emergencia");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73993_Autogestion_0800_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("0800");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73994_Autogestion_WEB_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WAP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73995_Autogestion_APP_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73996_Autogestion_WAP_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WAP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73997_Autogestion_USSD_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("USSD");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73998_Autogestion_Mi_Personal_Otros_Visualizacion_De_Campo() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("Mi Personal (SIM)");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS74001_Autogestion_APP_Otros_Verificacion_De_Caso_Valido() {
		Accounts accountPage = new Accounts(driver);
		String nCaso = new String();
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("APP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		driver.findElement(By.id("Other")).sendKeys("Prueba automatizada");
		listSelect = new Select(driver.findElement(By.id("MotiveSelection")));
		listSelect.selectByIndex(1);
		driver.findElement(By.id("SelfManagementStep_nextBtn")).click();
		sleep(5000);
		try {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("KnowledgeBaseResults_nextBtn")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		sleep(4000);
		nCaso = driver.findElement(By.id("CreatedClosedCaseText")).findElement(By.tagName("strong")).getText();
		}catch(java.lang.NullPointerException ex1) {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SimilCaseInformation")));
			nCaso =driver.findElement(By.id("SimilCaseInformation")).findElement(By.tagName("strong")).getText();
		}
		CustomerCare cCP = new CustomerCare(driver);
		driver.switchTo().defaultContent();
		cCP.elegircaso();
		buscarCaso(nCaso);
		sleep(4000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("cas15_ileinner")));
		assertTrue(driver.findElement(By.id("cas15_ileinner")).getText().toLowerCase().equals("prueba automatizada"));
	}
	
	@Test(groups = {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS74002_Autogestion_WAP_Otros_Verificacion_La_No_Visualizacion_Del_Campo_Otros() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("autogesti\u00f3n");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SelfManagementStep_nextBtn")));
		Select listSelect = new Select(driver.findElement(By.id("ChannelSelection")));
		listSelect.selectByVisibleText("WAP");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		listSelect = new Select(driver.findElement(By.id("ServiceSelection")));
		listSelect.selectByVisibleText("Otros");
		sleep(1000);
		assertTrue(driver.findElement(By.id("Other")).isDisplayed());
		listSelect.selectByIndex(2);
		sleep(1000);
		try{driver.findElement(By.id("Other")).isDisplayed();
			assertTrue(false);
		}catch (org.openqa.selenium.NoSuchElementException ex1) {assertTrue(true);}
	}
	
	/*@Test(groups = {"TechnicalCare","Muleto"})
	public void TS51071_Muleto_Verificacion_De_La_Seleccion_Entrega_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(driver.findElement(By.id("ProcessingType_nextBtn")).isDisplayed());
		
	}
	
	@Test(groups = {"TechnicalCare","Muleto"})
	public void TS51072_Muleto_Verificacion_De_La_Seleccion_Devolucion_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(driver.findElement(By.id("ProcessingType_nextBtn")).isDisplayed());
		
	}*/
	
	@Test(groups = {"TechnicalCare","Muleto","Ola3"})
	public void TS51073_Muleto_Visualizacion_Campo_DNI_Para_Entrega_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		assertTrue(listSelect.getFirstSelectedOption().getText().equals("DNI"));
		assertTrue(driver.findElement(By.id("DocumentNumber")).isDisplayed());
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51075_Muleto_Visualizacion_De_DNI_Para_Entrega_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		assertTrue(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).isEnabled());
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51077_Muleto_Verificacion_DNI_Supere_La_Validacion_De_Fraude() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		sleep(5000);
		WebElement validDNI = driver.findElement(By.id("ValidationResults")).findElements(By.tagName("p")).get(2);
		assertTrue(validDNI.getText().toLowerCase().equals("dni en blacklist de fraude: no"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ClientInformation21_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ClientInformation21_nextBtn")).click();
		sleep(7000);
		assertTrue(driver.findElement(By.id("TerminalsSelection")).isEnabled());
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51082_Muleto_Verificar_Gestion_Sin_Devolucion_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		sleep(5000);
		WebElement validDNI = driver.findElement(By.id("ValidationResults")).findElements(By.tagName("p")).get(3);
		assertTrue(validDNI.getText().toLowerCase().equals("cliente posee muleto pendiente de entrega: no"));
		
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ClientInformation21_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ClientInformation21_nextBtn")).click();
		sleep(7000);
		assertTrue(driver.findElement(By.id("TerminalsSelection")).isEnabled());
		
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51083_Muleto_Visualizacion_Los_Datos_Del_Cliente() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		sleep(5000);
		assertTrue(driver.findElement(By.id("ClientInfoHeader")).getText().toLowerCase().contains("datos del cliente"));
		List<WebElement> datos = driver.findElement(By.id("ClientInfoList")).findElements(By.tagName("p"));
		List<WebElement> titulos = new ArrayList<WebElement>();
		List<String> contenidos = new ArrayList<String>();
		for (WebElement unD : datos) {
			contenidos.add(unD.getText().split(":")[1]);
		}
		 for (String unC : contenidos) {
			 assertFalse(unC.isEmpty() || unC.equals(" "));
		 } 
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51084_Muleto_Visualizacion_Lista_De_Datos_Del_Cliente() {
		Accounts accountPage = new Accounts(driver);
		String[] todos = {"nombre:","apellido:","razon social:","tel\u00e9fono m\u00f3vil:","tel\u00e9fono de contacto:","email:","segmento:"};
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		sleep(5000);
		List<WebElement> datos = driver.findElement(By.id("ClientInfoList")).findElements(By.tagName("p"));
		List<WebElement> titulos = new ArrayList<WebElement>();
		List<String> contenidos = new ArrayList<String>();
		for (WebElement unD : datos) {
			titulos.add(unD.findElement(By.tagName("span")));
			System.out.println(unD.getText());
			contenidos.add(unD.getText().split(":")[1]);
		}
		 assertTrue(verificarContenidoLista(todos,titulos));
		 for (String unC : contenidos) {
			 assertFalse(unC.isEmpty() || unC.equals(" "));
		 }
		 
		 
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51085_Muleto_Visualizacion_Validacion_De_Blacklist_De_Fraude_Si() {
		Accounts accountPage = new Accounts(driver);
		String[] todos = {"nombre:","apellido:","razon social:","tel\u00e9fono m\u00f3vil:","tel\u00e9fono de contacto:","email:","segmento:"};
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		sleep(5000);
		WebElement validDNI = driver.findElement(By.id("ValidationResults")).findElements(By.tagName("p")).get(2);
		assertTrue(validDNI.getText().toLowerCase().equals("dni en blacklist de fraude: si"));
	}
	
	@Test(groups = {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51087_Muleto_Visualizacion_Validacion_De_Blacklist_De_Fraude_No() {
		Accounts accountPage = new Accounts(driver);
		String[] todos = {"nombre:","apellido:","razon social:","tel\u00e9fono m\u00f3vil:","tel\u00e9fono de contacto:","email:","segmento:"};
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("Gender"));
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		listSelect = new Select (driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Femenino");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		sleep(5000);
		WebElement validDNI = driver.findElement(By.id("ValidationResults")).findElements(By.tagName("p")).get(2);
		assertTrue(validDNI.getText().toLowerCase().equals("dni en blacklist de fraude: no"));
	}
	
	@Test(groups = {"TechnicalCare","Muleto","Ola3"})
	public void TS51112_Muleto_Verificacion_Ingreso_Del_DNI_Para_Entrega_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		assertTrue(driver.findElement(By.id("DocumentNumber")).getAttribute("value").equals("37431150"));
	}
	
	/*@Test(groups = {"TechnicalCare","Muleto"})
	public void TS51081_Muleto_Verificacion_De_Gestion_Con_Devolucion_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select listSelect = new Select(driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Masculino");
		listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37373737");
	}*/
	
	@Test(groups = {"TechnicalCare","Muleto","Ola3"})
	public void TS51107_Muleto_Verificacion_Del_Ingreso_De_Un_Texto_No_Mayor_A_255_Caracteres() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElement(By.className("borderOverlay")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select listSelect = new Select(driver.findElement(By.id("Gender")));
		listSelect.selectByVisibleText("Masculino");
		listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		driver.findElement(By.id("DocumentNumber")).sendKeys("37373737");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ClientIdentification_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ClientIdentification_nextBtn")).click();
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ClientInformation21_nextBtn")).getLocation().y+")");
			driver.findElement(By.id("ClientInformation21_nextBtn")).click();
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.className("slds-radio--faux")).click();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("TerminalsAvailable_nextBtn")).getLocation().y+")");
			driver.findElement(By.id("TerminalsAvailable_nextBtn")).click();
		}catch (org.openqa.selenium.NoSuchElementException ex1) {((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("NewClient_nextBtn")).getLocation().y+")");
			driver.findElement(By.id("NewClient_nextBtn")).click();}
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("AccesoriesField")).sendKeys("Roberto is a veteran who is characterised by orderliness and a firm belief in the value of control. He runs his own hardware store accordingly. If a supplier sells him boxes with 100 screws each, he counts all the screws and files a complaint if just a single one is missing. He feels that the world around his isle of neatness has gone mad");
		assertTrue(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-area.ng-scope.ng-valid-minlength.ng-valid-required.ng-dirty.ng-valid-parse.ng-invalid.ng-invalid-maxlength")).findElement(By.cssSelector(".error.ng-scope")).getText().toLowerCase().equals("longitud máxima de 255"));
	}
	
	@Test(groups = {"TechnicalCare","Muleto","Ola3"})
	public void TS51113_Muleto_Verificacion_Ingreso_Del_DNI_Para_Devolucion_De_Muleto() {
		Accounts accountPage = new Accounts(driver);
		accountPage.findAndClickButton("muleto");
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("ProcessingType_nextBtn")).getLocation().y+")");
		driver.findElement(By.id("ProcessingType_nextBtn")).click();
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("VlocityBP")));
		driver.findElements(By.className("borderOverlay")).get(2).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select listSelect = new Select(driver.findElement(By.id("DocumentType")));
		listSelect.selectByVisibleText("DNI");
		assertTrue(listSelect.getFirstSelectedOption().getText().equals("DNI"));
		driver.findElement(By.id("DocumentNumber")).sendKeys("37431150");
		assertTrue(driver.findElement(By.id("DocumentNumber")).getAttribute("value").equals("37431150"));
		
	}
	
	
}
