package R2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class HistorialDeRecargas extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	
	//@BeforeClass
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
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

	//@AfterClass(alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176705_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176705";
	}
	
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176713_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_IVR_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176713";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176717_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_ROL_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176717";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176727_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_TODOS_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176727";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176729_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Plan_Nacional_4G_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176729";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176731_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_1_GB_Renovacion_Cuota_de_Datos_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176731";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176733_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Promocion_Personal_Whastapp_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176733";
	}
	
	//----------------------------------------------- TELEFONICO   -------------------------------------------------------\\
	
	@Test (groups = {"PerfiTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176712_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS176712";
	}
	
	@Test (groups = {"PerfiTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176728_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_TODOS_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS176728";
		
	}
	@Test (groups = {"PerfiTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176734_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Promocion_Personal_Whastapp_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS176734";
	}
}