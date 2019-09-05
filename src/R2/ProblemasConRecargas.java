package R2;

import java.io.File;
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

public class ProblemasConRecargas extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private Marketing mk;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
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
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		mk = new Marketing(driver);
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
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ProblemaRecargaPrepaga")
	public void TS147740_CRM_Movil_Mix_Problemas_con_Recarga_On_Line_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS147740";
	}

	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ProblemaRecargaPrepaga")
	public void TS147749_CRM_Movil_Mix_Problemas_con_Recarga_TC_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS147749";
	}

	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ProblemaRecargaPrepaga")
	public void TS178664_CRM_Movil_Pre_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Quemada_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS178664";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ProblemaRecargaPrepaga")
	public void TS178665_CRM_Movil_Pre_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Vencida_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS178665";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "ProblemaRecargaPrepaga")
	public void TS178666_CRM_Movil_Pre_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Inexistente_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS178666";
	}
	
	
}
		