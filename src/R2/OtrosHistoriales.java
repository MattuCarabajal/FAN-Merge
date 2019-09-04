package R2;


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
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class OtrosHistoriales extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	

	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		log.loginOOCC();
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
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS144788_CRM_Movil_Pre_Historial_de_Suspenciones_y_Habilitaciones_Crm_OC(String sDNI , String sLinea){
		imagen="TS144788";
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS144789_CRM_Movil_Pre_Historial_de_Suspenciones_y_Habilitaciones_Crm_Telefonico(String sDNI , String sLinea){
		imagen="TS144789";
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "RecargasHistorias")
	public void TS144790_CRM_Movil_Pre_Historial_de_Suspenciones_y_Habilitaciones_Crm_Agente(String sDNI , String sLinea){
		imagen="TS144790";
	}
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176741_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_TODOS_Crm_Agente(String sDNI , String sLinea){
		imagen="TS176741";
	}
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176742_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Plan_Internet_50_Mb_Crm_Agente(String sDNI , String sLinea){
		imagen="TS176742";
	}
	
}