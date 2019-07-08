package Funcionalidades;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.BeFan;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SCP;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import PagesPOM.VentaDePackFw;
import Tests.CBS_Mattu;
import Tests.MDW;
import Tests.TestBase;
import Pages.Marketing;

public class CambioDePlan extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private Marketing mk;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private SCP scp;
	String detalles;
	private String fecha = fechaCapro(25);
	
	
//	 @BeforeClass (alwaysRun = true)
	public void Sit03() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit03();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		mk = new Marketing(driver);

	}

	@BeforeClass(groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		contact = new ContactSearch(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		mk = new Marketing(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
		mk = new Marketing(driver);
		BeFan Botones = new BeFan(driver);
	}

//	 @BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
		mk = new Marketing(driver);
		BeFan Botones = new BeFan(driver);
	}

//	 @BeforeClass (groups = "PerfilAgente")
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		log.loginAgente();
		mk = new Marketing(driver);
		ges.irAConsolaFAN();
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}
	
	//@AfterMethod (alwaysRun=true)
	public void after() throws IOException{
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}
	
	//@AfterClass(alwaysRun = true)
	public void quit(){
		driver.quit();
		sleep(5000);
	}
	
	private void cambioDePlan(String tipoDNI, String DNI, String plan, String numLinea) {
		ges.BuscarCuenta(tipoDNI, DNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", numLinea);
		sleep(7000);
		cambioDeFrame(driver, By.id("OrderRequestDate"),0);
		sleep(10000);
		cambioDeFrame(driver, By.id("Request date_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OrderRequestDate")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		sleep(120000);
		cambioDeFrame(driver,By.id("TargetOffer_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ScrollWindow")));
		sleep(9000);
		List<WebElement> planes = driver.findElements(By.cssSelector(".slds-grid.slds-box.vlc-slds-selectableItem.arrowup"));
		for (WebElement p : planes){
			if (p.getText().toLowerCase().contains(plan.toLowerCase()))
				p.click();
		}
		driver.findElement(By.id("TargetOffer_nextBtn")).click();
		sleep(80000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Comparision_nextBtn")));
		driver.findElement(By.id("Comparision_nextBtn")).click();
		sleep(29000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		driver.findElement(By.id("Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AccountData_nextBtn")));
		sleep(5000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("nextBtn-label")));
		driver.findElement(By.id("nextBtn-label")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button_brand")));
		sleep(8500);
		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		ges.BuscarCuenta(tipoDNI, DNI);
		ges.compararMegasEnCardPorLinea(numLinea);
		
	
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143263_CRM_Pospago_SalesCPQ_Cambio_de_plan_con_Falla_S069() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143262_CRM_Pospago_SalesCPQ_Cambio_de_plan_con_Falla_S131() throws AWTException{

	}

	@Test (groups = {"PerfilOficina"} )
	public void TS_144313_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{
//		cambioDePlan("DNI","15851622","conexi\u00f3n control abono m");
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS159158_CRM_Pospago_SalesCPQ_Cambio_de_plan_Actualizar_Ciclo_de_Facturacion_Solo_en_la_Primera_Gestion() throws AWTException{		
		imagen = "TS159158";
		cambioDePlan("95850890", "DNI", "Plan Abono Fijo 4GB", "2932598342");
		ges.BuscarCuenta("DNI", "91020744");
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", "2932598839");
		sleep(20000);
		cambioDeFrame(driver, By.id("OrderRequestDate"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Request date_nextBtn")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		sleep(100000);
		for (WebElement x : driver.findElements(By.cssSelector(".slds-grid.slds-box.vlc-slds-selectableItem.arrowup"))) {
			if (x.getText().toLowerCase().contains("conexi\u00f3n control abono m"))
				x.click();
		}
		driver.findElement(By.id("TargetOffer_nextBtn")).click();
		sleep(60000);
		driver.findElement(By.id("Comparision_nextBtn")).click();
		sleep(60000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AccountData_nextBtn")));
		driver.findElement(By.id("AccountData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("nextBtn-label")));
		driver.findElement(By.id("nextBtn-label")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button_brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(10000);
		mk.closeActiveTab();
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector(".console-card.active.expired"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".console-card.active.expired")));
		WebElement cicloFacturacion = null;
		for (WebElement x : driver.findElement(By.cssSelector(".console-card.active.expired")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("ciclo de facturaci\u00f3n"))
				cicloFacturacion = x;
		}
		String cicloAnterior = cicloFacturacion.findElements(By.tagName("span")).get(2).getText();
		mk.closeActiveTab();
		driver.navigate().refresh();
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", "2932598840");
		sleep(20000);
		cambioDeFrame(driver, By.id("OrderRequestDate"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Request date_nextBtn")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		sleep(140000);
		for (WebElement x : driver.findElements(By.cssSelector(".slds-grid.slds-box.vlc-slds-selectableItem.arrowup"))) {
			if (x.getText().toLowerCase().contains("conexi\u00f3n control abono m"))
				x.click();
		}
		driver.findElement(By.id("TargetOffer_nextBtn")).click();
		sleep(120000);
		driver.findElement(By.id("Comparision_nextBtn")).click();
		sleep(120000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AccountData_nextBtn")));
		driver.findElement(By.id("AccountData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
		contact.tipoValidacion("documento");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("nextBtn-label")));
		driver.findElement(By.id("nextBtn-label")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button_brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(10000);
		mk.closeActiveTab();
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector(".console-card.active.expired"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".console-card.active.expired")));
		WebElement nuevoCicloFacturacion = null;
		for (WebElement x : driver.findElement(By.cssSelector(".console-card.active.expired")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("ciclo de facturaci\u00f3n"))
				nuevoCicloFacturacion = x;
		}
		String cicloPosterior = nuevoCicloFacturacion.findElements(By.tagName("span")).get(2).getText();
		Assert.assertTrue(cicloAnterior.equals(cicloPosterior));
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_156600_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_RedList_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_144340_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_Repro_a_APRO4() throws AWTException, IOException{
		
		this.cambioDePlan("DNI", "42377446", "Plan Abono Fijo 4GB","2932598814");
//		ges.BuscarCuenta("DNI", "42377445");
//		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", "2932598818");
//		ges.compararMegasEnCardPorLinea("2932598813");

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS156667_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_con_Gestion_en_Curso() throws AWTException{
		imagen = "TS156667";
		ges.BuscarCuenta("DNI", "38010576");
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", "2932598378");
		cambioDeFrame(driver, By.cssSelector(".message.description.ng-binding.ng-scope"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message.description.ng-binding.ng-scope")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("asset cannot be modified because a request has already been made to disconnect it"));
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS143266_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Inactiva() throws AWTException{
		imagen = "TS143266"; 
		ges.BuscarCuenta("DNI", "51365481");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='console-card active expired']")));
		driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-top']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions")));
		driver.findElement(By.xpath("//li//span[contains(text(), 'Cambio de Plan')]")).click();
		cambioDeFrame(driver, By.id("prompt-heading-id"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("prompt-heading-id")));
		Assert.assertTrue(driver.findElement(By.id("prompt-heading-id")).getText().toLowerCase().contains("l\u00ednea inactiva"));
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143269_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_suspendida_Fraude() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143265_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Suspendida_Siniestro() throws AWTException{

	}
	
	@Test (groups = {"PerfilAgente", "R1"})
	public void TS145168_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_a_APRO2() {
		imagen = "TS145168";
		cambioDePlan("DNI", "92010588", "conexi\u00f3n control abono s", "2932564197");
		
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente"} )
	public void TS_168782_CRM_Pospago_SalesCPQ_Cambio_de_plan_Telefonico_P_8_R_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{
		
	}
	
	@Test (groups = {"PerfilAgente", "R1"})
	public void TS169804_CRM_Pospago_SalesCPQ_Cambio_de_plan_Telefonico_P_y_R_de_Plan_con_Tarjeta_Repro_a_APRO2() {
		imagen = "TS169804";
		cambioDePlan("DNI", "92010588", "conexi\u00f3n control abono s", "2932564197");
		
	}
	
	//----------------------------------------------- Agente -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente"} )
	public void TS_168774_CRM_Pospago_SalesCPQ_Cambio_de_plan_Agente_DNI_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{
		imagen = "17954137"; 
		cambioDePlan("DNI", "17954140", "Plan con Tarjeta Repro", detalles);
		Assert.assertTrue(ges.compararMegasEnCardPorLinea("2932598828"));
		
	}
	
	@Test (groups = {"PerfilAgente", "R1"})
	public void TS169796_CRM_Pospago_SalesCPQ_Cambio_de_plan_Agente_DNI_de_Plan_con_Tarjeta_Repro_a_APRO2() {
		imagen = "TS169796";
		cambioDePlan("DNI", "92010588", "conexi\u00f3n control abono s", "2932564197");
		
	}
	
	
	
}
