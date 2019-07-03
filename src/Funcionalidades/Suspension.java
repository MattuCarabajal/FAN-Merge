package Funcionalidades;

import java.awt.AWTException;
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
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Suspension extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginOOCC(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
		
	//@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginTelefonico(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
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
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98438_CRM_Movil_REPRO_Suspension_por_Siniestro_Hurto_Linea_Titular_Presencial(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98438";
		detalles = null;
		detalles = imagen + " - Suspension - DNI: " +cDNI+" - Linea: "+cLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		cc.irAGestion("suspensiones");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+cLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "hurto");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(10000);
		selectByText(driver.findElement(By.id("State")),cProvincia);
		sleep(4000);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(cCiudad);
		sleep(4000);
		driver.findElement(By.id("Partido")).sendKeys(cPartido);
		sleep(4000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(40000);
		boolean a = false;
		List <WebElement> elem = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : elem) {
			if(x.getText().toLowerCase().contains("tu solicitud est\u00e1 siendo procesada.")) {
				a = true;
			}			
		}
		Assert.assertTrue(a);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Suspensi\u00f3n de Linea");
		detalles+= " - Orden: "+orden;
		sOrders.add("Suspension, orden numero: " + orden + " con numero de DNI: " + cDNI);
		System.out.println(sOrders);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98442_CRM_Movil_REPRO_Suspension_por_Siniestro_Extravio_Linea_Titular_Presencial(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98442";
		detalles = null;
		detalles = imagen + " -Suspension - DNI: " + cDNI +" - Linea: "+cLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		cc.irAGestion("suspensiones");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+cLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "extrav\u00edo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(10000);
		selectByText(driver.findElement(By.id("State")),cProvincia);
		sleep(10000);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(cCiudad);
		sleep(10000);
		driver.findElement(By.id("Partido")).sendKeys(cPartido);
		sleep(7000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(15000);
		boolean a = false;
		List <WebElement> elem = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : elem) {
			if(x.getText().toLowerCase().contains("tu solicitud est\u00e1 siendo procesada.")) {
				a = true;
			}			
		}
		Assert.assertTrue(a);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Suspensi\u00f3n de Linea");
		sOrders.add("Suspencion, orden numero: " + orden + " con numero de DNI: " + cDNI);
		System.out.println(sOrders);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98434_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98434";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("State")).sendKeys(sProvincia);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98436_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_No_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98436";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("State")).sendKeys(sProvincia);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("DNI")).sendKeys(sDNI);
		driver.findElement(By.id("FirstName")).sendKeys("Cinco");
		driver.findElement(By.id("LastName")).sendKeys("Newton");
		driver.findElement(By.id("Phone")).sendKeys("2944675270");
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98435_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98435";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("State")).sendKeys(sProvincia);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98437_CRM_Movil_Prepago_Suspension_por_Siniestro_Robo_Linea_No_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98437";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("State")).sendKeys(sProvincia);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("DNI")).sendKeys(sDNI);
		driver.findElement(By.id("FirstName")).sendKeys("Cinco");
		driver.findElement(By.id("LastName")).sendKeys("Newton");
		driver.findElement(By.id("Phone")).sendKeys("2944675270");
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	//----------------------------------------------- FRAUDE -------------------------------------------------------\\
	
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
	public void TS98484_CRM_Movil_REPRO_Suspension_por_Fraude_DNI_CUIT_Comercial_Fraude_por_suscripcion_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
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
	public void TS98491_CRM_Movil_REPRO_Suspension_por_Fraude_Linea_Comercial_Desconocimiento_Administrativo(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
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
}