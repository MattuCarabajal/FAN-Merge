package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class Vista360 extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private Marketing mk;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS145701_CRM_Movil_Mix_Card_de_Facturacion_Datos_informativos_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS145701";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		Assert.assertTrue(!driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-top'] div h2")).getText().isEmpty());
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='slds-text-body_regular account-number']")).getText().contains("L\u00edmite de Cr\u00e9dito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='header-right']")).getText().contains("Saldo"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Cuenta"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Modalidad de Recepci\u00f3n de Factura"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("\u00daltimo Vencimiento"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Adherido a D\u00e9bito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Ciclo de Facturaci\u00f3n"));
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148788_CRM_Movil_Mix_Vista_360_Distribucion_de_paneles_Perfil_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS148788";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='profile-box']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='left-sidebar-section-header'] [class='icon-v-right-caret']")));		
		Assert.assertTrue(false);  //No estan los segmentos ni los botones de "Anadir mas"
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148726_CRM_Movil_Mix_Vista_360_Consulta_por_gestiones_Gestiones_Cerradas_Informacion_brindada_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS148726";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		driver.findElement(By.xpath("//*[@class='console-card active']//*[@class='card-info-hybrid']//*[@class='actions']//span[text()='Gestiones']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")));		
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr"), 0));
		List<WebElement> datosTabla = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr"));
		Assert.assertTrue(datosTabla.size() >= 1);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS145700_CRM_Movil_Mix_Card_de_Facturacion_Datos_informativos_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS145700";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		Assert.assertTrue(!driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-top'] div h2")).getText().isEmpty());
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='slds-text-body_regular account-number']")).getText().contains("L\u00edmite de Cr\u00e9dito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='header-right']")).getText().contains("Saldo"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Cuenta"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Modalidad de Recepci\u00f3n de Factura"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("\u00daltimo Vencimiento"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Adherido a D\u00e9bito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Ciclo de Facturaci\u00f3n"));
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148798_CRM_Movil_Mix_Vista_360_Card_de_servicios_Desplegable_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS148798";
		boolean histSusp = false, suscrip = false, renovDatos = false, detPlan = false, numGratis = false, diag = false, inconvRecarg = false, abmServ = false, internet = false, minutos = false, sms = false, credDisp = false, credProm = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top']")).getText().contains("Conexi\u00f3n Control Abono"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-info-hybrid'] [class='details'] [class='imagre']")).getText().contains("Activo"));
		driver.findElement(By.cssSelector("[class='console-card active'] [id='flecha'] i")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='community-flyout-actions-card']")));
		for (WebElement x : driver.findElements(By.cssSelector("[class='community-flyout-actions-card'] ul li"))) {
			if (x.getText().equalsIgnoreCase("Historial de Suspensiones"))
				histSusp = true;
			if (x.getText().equalsIgnoreCase("Suscripciones"))
				suscrip = true;
			if (x.getText().equalsIgnoreCase("Renovacion de Datos"))
				renovDatos = true;
			if (x.getText().equalsIgnoreCase("Detalle del Plan"))
				detPlan = true;
			if (x.getText().equalsIgnoreCase("N\u00fameros Gratis"))
				numGratis = true;
			if (x.getText().equalsIgnoreCase("Diagn\u00f3stico"))
				diag = true;
			if (x.getText().equalsIgnoreCase("Inconvenientes con Recargas"))
				inconvRecarg = true;
			if (x.getText().equalsIgnoreCase("Alta/Baja de Servicios"))
				abmServ = true;
		}
		Assert.assertTrue(histSusp && suscrip && renovDatos && detPlan && numGratis && diag && inconvRecarg && abmServ);
		for (WebElement x : driver.findElements(By.cssSelector("[class='community-flyout-grid-items-card'] [class='slds-grid slds-gutters']"))) {
			if (x.getText().contains("Internet (Mb)"))
				internet = true;
			if (x.getText().contains("Minutos (minutos:segundos)"))
				minutos = true;
			if (x.getText().contains("SMS (cantidad)"))
				sms = true;
			if (x.getText().contains("Cr\u00e9dito Disponible"))
				credDisp = true;
			if (x.getText().contains("Cr\u00e9dito Promocional"))
				credProm = true;
		}
		Assert.assertTrue(internet && minutos && sms && credDisp && credProm);
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148803_CRM_Movil_Mix_Vista_360_Producto_Activo_del_cliente_Datos_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS148803";
		boolean gest = false, prodServ = false, cuenta = false, estado = false, credDisp = false, credProm = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top']")).getText().contains("Conexi\u00f3n Control Abono"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top'] [class='slds-text-body_regular activation-date slds-p-bottom--xx-small']")).getText().matches("Act: [0-9]{2}/[0-9]{2}/[0-9]{4}"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top'] [class='slds-text-body_regular line-number slds-p-bottom--xx-small']")).getText().matches("Linea: [0-9]{10}"));
		for (WebElement x : driver.findElements(By.cssSelector("[class='console-card active'] [class='card-info-hybrid'] [class='details']"))) {
			if (x.getText().contains("Cuenta:"))
				cuenta = true;
			if (x.getText().contains("Estado:"))
				estado = true;
			if (x.getText().contains("Cr\u00e9dito Disponible:"))
				credDisp = true;
			if (x.getText().contains("Cr\u00e9dito Promocional:"))
				credProm = true;
		}
		Assert.assertTrue(cuenta && estado && credDisp && credProm);
		for (WebElement x : driver.findElements(By.cssSelector("[class='console-card active'] [class='card-info-hybrid'] [class='actions'] li"))) {
			if (x.getText().equalsIgnoreCase("Gestiones"))
				gest = true;
			if (x.getText().equalsIgnoreCase("Productos y Servicios"))
				prodServ = true;
		}
		Assert.assertTrue(gest && prodServ);
	}
	
	@Test (groups = {"PerfilAgente", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148747_CRM_Movil_Mix_Vista_360_Productos_y_Servicios_Visualizacion_del_estado_de_los_servicios_activos_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS148747";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		driver.findElement(By.xpath("//*[@class='console-card active']//*[@class='card-info-hybrid']//*[@class='actions']//span[text()='Productos y Servicios']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--brand']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--brand']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--x-large'] thead")).getText().contains("NOMBRE"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--x-large'] thead")).getText().contains("FECHA DE ESTADO"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--x-large'] thead")).getText().contains("ESTADO"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-button slds-button--brand']")).getText().contains("Ver detalle"));
	}
}