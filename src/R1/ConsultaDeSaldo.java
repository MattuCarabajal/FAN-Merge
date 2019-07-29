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
	private GestionDeClientes_Fw ges;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private LoginFw log;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.LoginSit();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
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
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
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
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148805_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_Crm_OC(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS148805";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		WebElement elemento =null;
		String saldo = null;
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
			saldo = elemento.getText();
		} catch (Exception e) {
			System.out.println("Verificar el estado de la card actual");
		}
	
		saldo = saldo.replaceAll("[$.,]", "");
		saldo = saldo.substring(0, saldo.length()-2);
		Assert.assertTrue(saldo.matches("[\\d]{1,5}"));
		int z = Integer.parseInt(saldo);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		datoVNuevo = datoVNuevo.substring(0, datoVNuevo.length()-6);
		int x = Integer.parseInt(datoVNuevo);
//		String datoVNuevo2 = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:CCBalance");
//		System.out.println("dato nuevo 2 " + datoVNuevo2);
//		datoVNuevo2 = datoVNuevo2.substring(0, datoVNuevo2.length()-6);
//		int y = Integer.parseInt(datoVNuevo2);
		Assert.assertTrue(x ==z);
		
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148807_OOCC_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_Crm_OC(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS148807";
		ges.BuscarCuenta("DNI", sDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"), 0);
		String saldo = driver.findElement(By.className("header-right")).findElement(By.cssSelector(".slds-text-heading_medium.expired-date.expired-pink")).getText();
		saldo  = saldo.replaceAll("[$.,]", "");
		saldo = saldo.substring(0 , saldo.length()-2);
		System.out.println(saldo);
		Assert.assertTrue(saldo.matches("[\\d]{1,7}"));
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		response = response.substring(0, response.length()-6);
		System.out.println(response);
		int a = Integer.parseInt(saldo);
		int b = Integer.parseInt(response);
		Assert.assertTrue(a == b);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148808_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_Crm_Telefónico(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS148808";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		WebElement elemento =null;
		String credito = null;
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
			credito = elemento.getText();
		} catch (Exception e) {
			System.out.println("Verificar el estado de la card actual");
		}
		credito = credito.replaceAll("[$.,]", "");
		credito = credito.substring(0, credito.length()-2);
		Assert.assertTrue(credito.matches("[\\d]{1,5}"));
		int z = Integer.parseInt(credito);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		datoVNuevo = datoVNuevo.substring(0, datoVNuevo.length()-6);
		int x = Integer.parseInt(datoVNuevo);
		//String datoVNuevo2 = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:CCBalance");
		//datoVNuevo2 = datoVNuevo2.substring(0, datoVNuevo2.length()-6);
		//int y = Integer.parseInt(datoVNuevo2);
		Assert.assertTrue(x ==z);
		
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "ConsultaSaldo")
	public void TS148810_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey){
		imagen = "TS148807";
		ges.BuscarCuenta("DNI", sDNI);
		mk.closeActiveTab();	
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"), 0);
		String saldo = driver.findElement(By.className("header-right")).findElement(By.cssSelector(".slds-text-heading_medium.expired-date.expired-pink")).getText();
		saldo  = saldo.replaceAll("[$.,]", "");
		saldo = saldo.substring(0 , saldo.length()-2);
		System.out.println(saldo);
		Assert.assertTrue(saldo.matches("[\\d]{1,7}"));
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		response = response.substring(0, response.length()-6);
		System.out.println(response);
		int a = Integer.parseInt(saldo);
		int b = Integer.parseInt(response);
		Assert.assertTrue(a == b);
	}
}