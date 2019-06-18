package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
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
	private String fecha = "12-20-2020";
	
	
//	 @BeforeClass (alwaysRun = true)
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit02();
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
	
	private void cambioDePlan() {
		String fecha = fechaCapro(10);
		ges.BuscarCuenta("Pasaporte", "925475893");
		sleep(3000);
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
		System.out.println("Cliclo Repro: "+cicloAnterior);
		mk.closeActiveTab();
		driver.navigate().refresh();
		ges.irAGestionEnCard("Cambio de Plan");
		sleep(7000);
		cambioDeFrame(driver, By.id("OrderRequestDate"),0);
		sleep(10000);
		cambioDeFrame(driver, By.id("Request date_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OrderRequestDate")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		sleep(30000);
		cambioDeFrame(driver,By.id("TargetOffer_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ScrollWindow")));
		sleep(3000);
		List<WebElement> planes = driver.findElements(By.cssSelector(".slds-grid.slds-box.vlc-slds-selectableItem.arrowup"));
		for(WebElement p : planes){
			if(p.getText().toLowerCase().contains("plan abono fijo 3gb")){
				p.click();
			}
		}
		driver.findElement(By.id("TargetOffer_nextBtn")).click();
		sleep(35000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Comparision_nextBtn")));
		driver.findElement(By.id("Comparision_nextBtn")).click();
		sleep(25000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		driver.findElement(By.id("Summary_nextBtn")).click();
//		selectByText(driver.findElement(By.id("state-BillingAddress")), "BUENOS AIRES");
//		driver.findElement(By.cssSelector(".slds-input.ng-not-empty.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required.ng-touched")).clear();
//		driver.findElement(By.cssSelector(".slds-input.ng-not-empty.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required.ng-touched")).sendKeys("PUNTA ALTA ALTA SOLIER");
//		driver.findElement(By.cssSelector(".slds-input.ng-not-empty.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required.ng-touched")).sendKeys(Keys.ENTER);
//		selectByText(driver.findElement(By.id("zoneType-BillingAddress")), "URBANA");
//		driver.findElements(By.cssSelector(".slds-input.ng-not-empty.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required.ng-touched")).get(1).clear();
//		driver.findElements(By.cssSelector(".slds-input.ng-not-empty.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required.ng-touched")).get(1).sendKeys("Falsa");
//		driver.findElement(By.id("streetNumber-BillingAddress")).clear();
//		driver.findElement(By.id("streetNumber-BillingAddress")).sendKeys("1234");
//		driver.findElement(By.id("postalCode-BillingAddress")).clear();
//		driver.findElement(By.id("postalCode-BillingAddress")).sendKeys("4321");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AccountData_nextBtn")));
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
		sleep(15000);
		driver.navigate().refresh();
		sleep(5000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector(".console-card.active.expired"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".console-card.active.expired")));
		WebElement cicloFacturacionPost = null;
		for (WebElement x : driver.findElement(By.cssSelector(".console-card.active.expired")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("ciclo de facturaci\u00f3n"))
				cicloFacturacionPost = x;
		}
		String cicloPosterior = cicloFacturacionPost.findElements(By.tagName("span")).get(2).getText();
		System.out.println("Cliclo Repro: "+cicloPosterior);
		Assert.assertTrue(cicloPosterior!=cicloAnterior);	
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
		
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS159158_CRM_Pospago_SalesCPQ_Cambio_de_plan_Actualizar_Ciclo_de_Facturacion_Solo_en_la_Primera_Gestion() throws AWTException{
		imagen = "TS159158";
		ges.BuscarCuenta("DNI", "5115412");
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", "2932598531");
//		sleep(140000);
//		cambioDeFrame(driver, By.id("OrderRequestDate"), 0);
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Request date_nextBtn")));
//		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
//		driver.findElement(By.id("Request date_nextBtn")).click();
//		sleep(140000);
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-grid.slds-box.vlc-slds-selectableItem.arrowup"))) {
//			if (x.getText().toLowerCase().contains("conexi\u00f3n control abono xs"))
//				x.click();
//		}
//		driver.findElement(By.id("TargetOffer_nextBtn")).click();
//		sleep(120000);
//		driver.findElement(By.id("Comparision_nextBtn")).click();
//		sleep(120000);
//		driver.findElement(By.id("Summary_nextBtn")).click();		
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AccountData_nextBtn")));
//		driver.findElement(By.id("AccountData_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_nextBtn")));
//		contact.tipoValidacion("documento");
//		File directory = new File("Dni.jpg");
//		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("nextBtn-label")));
//		driver.findElement(By.id("nextBtn-label")).click();
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button_brand")));
//		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
//		sleep(10000);
//		driver.navigate().refresh();
//		mk.closeActiveTab();
//		cc.irAFacturacion();
//		cambioDeFrame(driver, By.cssSelector(".console-card.active.expired"), 0);
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".console-card.active.expired")));
//		WebElement cicloFacturacion = null;
//		for (WebElement x : driver.findElement(By.cssSelector(".console-card.active.expired")).findElements(By.tagName("li"))) {
//			if (x.getText().toLowerCase().contains("ciclo de facturaci\u00f3n"))
//				cicloFacturacion = x;
//		}
//		String cicloAnterior = cicloFacturacion.findElements(By.tagName("span")).get(2).getText();
//		mk.closeActiveTab();
//		driver.navigate().refresh();
		
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_156600_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_RedList_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_144340_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_Repro_a_APRO4() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS156667_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_con_Gestion_en_Curso() throws AWTException{
		imagen = "TS156667";
		ges.BuscarCuenta("DNI", "15647523");
		ges.irAGestionEnCardPorNumeroDeLinea("Cambio de Plan", "2932598510");
		cambioDeFrame(driver, By.cssSelector(".message.description.ng-binding.ng-scope"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message.description.ng-binding.ng-scope")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("asset cannot be modified because a request has already been made to disconnect it"));
	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143266_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Inactiva() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143269_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_suspendida_Fraude() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143265_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Suspendida_Siniestro() throws AWTException{

	}
	
	//----------------------------------------------- Agente -------------------------------------------------------\\
	@Test (groups = {"PerfilAgente"} )
	public void TS_168774_CRM_Pospago_SalesCPQ_Cambio_de_plan_Agente_DNI_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{

	}
	
	@Test (groups = {"PerfilAgente"} )
	public void TS_168782_CRM_Pospago_SalesCPQ_Cambio_de_plan_Telefonico_P_8_R_de_Plan_con_Tarjeta_a_APRO4() throws AWTException{

	}
	
	
}
