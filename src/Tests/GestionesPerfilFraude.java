package Tests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.OM;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.TechCare_Ola1;
import Pages.setConexion;

public class GestionesPerfilFraude extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	List<String> sOrders = new ArrayList<String>();
	String imagen;
	String detalles;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginFraude(driver);
		//sleep(22000);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			//sleep(3000);
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		driver.switchTo().defaultContent();
		sleep(6000);
		
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(3000);
		goToLeftPanel2(driver, "Inicio");
		sleep(13000);
		try {
			sb.cerrarPestaniaGestion(driver);
		} catch (Exception ex1) {}
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");}
			
		sleep(25000);
		driver.switchTo().defaultContent();
		
	}
	@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	@Test (groups = {"Habilitacion", "GestionesPerfilFraude","E2E", "Ciclo3"}, dataProvider="CuentaHabilitacion")
	public void TS98599_CRM_Movil_REPRO_Rehabilitacion_Administrativo_Fraude_DNI(String sDNI, String sLinea) {
		imagen = "TS98599";
		detalles = null;
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(sLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion backoffice");
		sleep(40000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step2-SelectAssetOrDocument_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step3_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "contains", "continue");
		sleep(15000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(8000);
		String orden = cc.obtenerOrden(driver, "Habilitaci\u00f3n administrativa");
		detalles+=" - Orden: "+orden;
		System.out.println(sOrders);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98477_CRM_Movil_REPRO_Suspension_por_Fraude_Linea_Comercial_Desconocimiento_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98477";
		detalles = null;
		detalles = imagen + " -Suspension - DNI: " + sDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(sLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion backoffice");
		sleep(40000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
		selectByText(driver.findElement(By.id("SelectSubFraud")),"Desconocimiento");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(15000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(40000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(10000);
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspension fraude"));
		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
		detalles += " - Orden: "+orden;
		sOrders.add("Suspencion, orden numero: " + orden + " con numero de DNI: " + sDNI);
		System.out.println(sOrders);
	}	
	
	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98487_CRM_Movil_REPRO_Suspension_por_Fraude_DNI_CUIT_Comercial_Irregular_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98487";
		detalles = null;
		detalles = imagen + " - Suspension - DNI: " + sDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(sLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion backoffice");
		sleep(40000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
		selectByText(driver.findElement(By.id("SelectSubFraud")),"Irregular");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(40000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(10000);
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspendida fraude"));
		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
		detalles+= " - Orden: "+orden;
		sOrders.add("Suspencion, orden numero: " + orden + " con numero de DNI: " + sDNI);
		System.out.println(sOrders);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98498_CRM_Movil_REPRO_Suspension_por_Fraude_Cuenta_de_facturacion_Comercial_Desconocimiento_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98498";
		detalles = null;
		detalles = imagen + " -Suspension - DNI: " + sDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(sLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion back");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		sleep(8000);
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectFraud")), "Comercial");
		selectByText(driver.findElement(By.id("SelectSubFraud")), "Desconocimiento");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "contains", "continue");
		sleep(15000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(8000);
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspension fraude"));
		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
		detalles+=" - orden: "+orden;
		System.out.println(sOrders);	
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension") 
	public void TS_98484_CRM_Movil_REPRO_Suspension_por_Fraude_DNI_CUIT_Comercial_Fraude_por_suscripcion_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98484";
		detalles = null;
		detalles = imagen + "-Suspension - DNI:" + sDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(sLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion back");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		sleep(8000);
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
		selectByText(driver.findElement(By.id("SelectSubFraud")),"Administrativo por suscripci\u00f3n");
		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		boolean a = false;
		List <WebElement> realiz = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : realiz) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		sleep(8000);
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspension fraude"));
		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
		detalles+=" - Orden: "+orden;
	}
	

	
	@Test (groups = {"Suspension", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS_98491_CRM_Movil_REPRO_Suspension_por_Fraude_Linea_Comercial_Desconocimiento_Administrativo(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98491";
		detalles = null;
		detalles = imagen + "- Suspension -DNI:" + cDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(cLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion back");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
		selectByText(driver.findElement(By.id("SelectSubFraud")),"Desconocimiento");
		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(15000);
		boolean a = false;
		List <WebElement> realiz = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : realiz) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		sleep(8000);
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBSM.obtenerStatusLinea(cLinea).equals("suspension fraude"));
		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
		detalles+=" - Orden: "+orden;
		
	}
}