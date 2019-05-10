package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ConsultaDeSaldo extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	LoginFw log;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginOOCC();
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		log.loginTelefonico();
		sleep(15000);
		cc.irAConsolaFAN();
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	//@BeforeClass (groups = "PerfilAgente")
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		log.loginAgente();
		sleep(15000);
		cc.irAConsolaFAN();
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	//------------------------------------------------------------------------------
	public boolean sleepCambioDeFrame (WebDriver driver, String typeOfBy, String elementSelector, double timeMax, double timeAcumulated) {
		if (timeMax < timeAcumulated) {
			return true;
		}
		try {
			switch(typeOfBy) {
				case "id":
					driver.switchTo().frame(cambioFrame(driver, By.id(elementSelector)));
					break;
				case "cssSelector":
					driver.switchTo().frame(cambioFrame(driver, By.cssSelector(elementSelector)));
					break;
				case "tagName":
					driver.switchTo().frame(cambioFrame(driver, By.tagName(elementSelector)));
					break;
				case "className":
					driver.switchTo().frame(cambioFrame(driver, By.className(elementSelector)));
					break;
				default:
					System.out.println("Error seleccionando el tipo de By.");
					break;
			}
			return true;
		} catch (Exception e) {
			try {Thread.sleep(200);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			return sleepCambioDeFrame(driver, typeOfBy, elementSelector, timeMax, timeAcumulated + 0.200);
		}
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134373_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_OOCC(String sDNI, String sLinea, String sAccountKey) {
		imagen ="TS134373";
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleepCambioDeFrame(driver, "className", "card-top", 10, 0);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 6));
		String card = driver.findElement(By.className("card-info")).findElement(By.className("uLdetails")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(2).getText();
		card = card.replaceAll("[$.,]", "");
		Integer creditoCard = Integer.parseInt(card);
		Assert.assertTrue(credito.equals(creditoCard));
	}
	
	@Test (groups = {"PerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134376_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_OOCC(String sDNI, String sLinea, String sAccountKey) {
		imagen ="TS134376";		
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleepCambioDeFrame(driver, "className", "card-top", 10, 0);
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "ConsultaDeSaldo", "Ciclo1" }, dataProvider = "ConsultaSaldo")
	public void TS134811_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134811";		
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleepCambioDeFrame(driver, "className", "card-top", 10, 0); //////
		sleep(5000);
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
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);	
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134814_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Agentes(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS134814";		
		detalles = imagen + "- Consulta de Saldo - DNI:" + sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleepCambioDeFrame(driver, "className", "card-top", 10, 0);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 6));
		String card = driver.findElement(By.className("card-info")).findElement(By.className("uLdetails")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(2).getText();
		card = card.replaceAll("[$.,]", "");
		Integer creditoCard = Integer.parseInt(card);
		Assert.assertTrue(credito.equals(creditoCard));
	}
	
	@Test (groups = {"PerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134815_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Agentes(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134815";		
		detalles = imagen + "Consulta de saldo -DNI:" + sDNI;
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);	
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}
	
}