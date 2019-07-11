package Funcionalidades;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ConsultaDeSaldo extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private Marketing mk;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private LoginFw log;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilAgente")
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134373_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_OOCC(String sDNI, String sLinea, String sAccountKey) {
		imagen ="TS134373";
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 6));
		System.out.println(credito);
		String card = driver.findElement(By.className("card-info")).findElement(By.className("uLdetails")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(2).getText();
		card = card.replaceAll("[$.,]", "");
		Integer creditoCard = Integer.parseInt(card);
		System.out.println(creditoCard);
		Assert.assertTrue(credito.equals(creditoCard));
	}
	
	@Test (groups = {"PerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134376_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_OOCC(String sDNI, String sLinea, String sAccountKey) {
		imagen ="TS134376";		
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"), 0);
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalUsageAmount");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 5));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
	
	@Test (groups = {"PerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS148805_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_Crm_OC(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS148805";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		WebElement elemento =null;
		String nuevomega = null;
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card ']"));
		cards = ges.listaDeElementosPorText(cards, sLinea);
		if(cards.size()<=0) {
			System.out.println("numero de linea inexistente");
		}
		WebElement cardActiva= ges.getBuscarElementoPorText(cards, "Activo");
		try {
			cambioDeFrame(driver, By.cssSelector("[id='flecha'] i"), 0);
			ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='flecha'] i")));
			cardActiva.findElement(By.cssSelector("[id='flecha'] i")).click();	
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters']"), 0));
			List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-gutters']"));
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']"),0));
			elemento = ges.getBuscarElementoPorText(elementos, "Cr\u00e9dito Disponible").findElements(By.cssSelector("[class='slds-grid slds-gutters'] [class='slds-col slds-size_1-of-3'] [class='value']")).get(1);
			nuevomega = elemento.getText();
		} catch (Exception e) {
			System.out.println("Verificar el estado de la card actual");
		}
		System.out.println(nuevomega);
		Assert.assertTrue(nuevomega.matches("[$][\\d]{1,5}[,][\\d]{2}"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "ConsultaDeSaldo", "Ciclo1" }, dataProvider = "ConsultaSaldo")
	public void TS134811_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134811";		
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 6));
		String card = driver.findElement(By.className("card-info")).findElement(By.className("uLdetails")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(2).getText();
		card = card.replaceAll("[$.,]", "");
		Integer creditoCard = Integer.parseInt(card);
		Assert.assertTrue(credito.equals(creditoCard));
	}
	
	@Test (groups = {"PerfilTelefonico", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134813_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134813";
		detalles = imagen + " -Consulta de saldo - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("card-top"), 0);
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("header-right"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("header-right")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);
		System.out.println("Saldo en card :"+saldoEnCard);
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalUsageAmount");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 5));
		System.out.println("saldo en facturacion: "+ saldoFacturacion);
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
		
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134814_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Agentes(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS134814";		
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 6));
		String card = driver.findElement(By.className("card-info")).findElement(By.className("uLdetails")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(2).getText();
		card = card.replaceAll("[$.,]", "");
		System.out.println("credito en card:" + card);
		Integer creditoCard = Integer.parseInt(card);
		Assert.assertTrue(credito.equals(creditoCard));
	}
	
	@Test (groups = {"PerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134815_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Agentes(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134815";
		detalles = imagen + "Consulta de saldo -DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"), 0);
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalUsageAmount");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 5));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
}