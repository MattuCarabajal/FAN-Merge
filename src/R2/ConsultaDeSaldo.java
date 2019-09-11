package R2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
	
	
	//@BeforeClass (alwaysRun = true)
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
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initAgente() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
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
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ConsultaSaldo")
	public void TS134373_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_Crm_OC(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134373";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String creditoCard = driver.findElement(By.className("card-info")).findElement(By.className("uLdetails")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(2).getText();
		System.out.println(creditoCard);
		Assert.assertTrue(creditoCard.contains("$"));
	}
	
	//------------------------------------------- TELEFONICO  ------------------------------------------
	@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "ConsultaSaldo")
	public void TS147792_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_Crm_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS147792";
		ges.BuscarCuenta("DNI", sDNI);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.className("card-top"), 0);
		String saldo = driver.findElement(By.cssSelector("[class='card-info'] [class='header-right'] [class='slds-text-heading_medium expired-date']")).getText();
		saldo  = saldo.replaceAll("[$.,]", "");
		saldo = saldo.substring(0 , saldo.length()-2);
		String response = cbs.ObtenerValorResponse(cbsm.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		response = response.substring(0, response.length()-6);
		System.out.println(response);
		int saldoEnFacturacion = Integer.parseInt(saldo);
		int saldoResponse = Integer.parseInt(response);
		Assert.assertTrue(saldoEnFacturacion == saldoResponse);
	}
	
	//--------------------------------------------   AGENTE -------------------------------------------
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "ConsultaSaldo")
	public void TS147793_CRM_Movil_Mix_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_Crm_Agente(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS147793";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String saldo = driver.findElement(By.cssSelector("[class='card-info-hybrid'] [class='details'] li span:nth-child(3)")).getText();
		saldo  = saldo.replaceAll("[$.,]", "");
		saldo = saldo.substring(0 , saldo.length()-2);
		int z = Integer.parseInt(saldo);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		datoVNuevo = datoVNuevo.substring(0, datoVNuevo.length()-6);
		int x = Integer.parseInt(datoVNuevo);
		String datoVNuevo2 = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:CCBalance");
		datoVNuevo2 = datoVNuevo2.substring(0, datoVNuevo2.length()-6);
		int y = Integer.parseInt(datoVNuevo2);
		Assert.assertTrue(x + y == z);
	}
}