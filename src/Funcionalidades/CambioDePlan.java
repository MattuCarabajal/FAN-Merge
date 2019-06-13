package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
import Pages.SCP;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import PagesPOM.VentaDePackFw;
import Tests.CBS_Mattu;
import Tests.MDW;
import Tests.TestBase;

public class CambioDePlan extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private SCP scp;
	String detalles;
	
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
		log.loginOOCC();
		ges.irAConsolaFAN();
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
		ges.irAConsolaFAN();
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}
	
	@AfterMethod (alwaysRun=true)
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
	
	@Test()
	public void asd(){
		imagen = "TS_CambioDePlan";
		detalles = imagen + "- TS_CambioDePlan - DNI: " + "";
		boolean enc = false;
		String fecha = "06-29-2019";
		ges.BuscarCuenta("DNI", "91020601");
		ges.irAGestionEnCard("Cambio de Plan");
		sleep(7000);
		cambioDeFrame(driver, By.id("OrderRequestDate"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OrderRequestDate")));
		driver.findElement(By.id("OrderRequestDate")).sendKeys(fecha);
		driver.findElement(By.id("Request date_nextBtn")).click();
		//ERROR CREAR ORDEN
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Failed_FDO_nextBtn")));
		driver.findElement(By.id("Failed_FDO_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(2500);
		driver.findElement(By.id("Failed_FDO_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("ScrollWindow")));
		List<WebElement> planes = driver.findElement(By.className("ScrollWindow")).findElements(By.tagName("div"));
		for(WebElement p : planes){
			if(p.getText().toLowerCase().contains("Plan Abono Fijo 3GB")){
				p.click();
			}
		}
		driver.findElement(By.id("TargetOffer_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Comparision_nextBtn")));
		
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	@Test (groups = {"PerfilOficina"} )
	public void TS_143263_CRM_Pospago_SalesCPQ_Cambio_de_plan_con_Falla_S069() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143262_CRM_Pospago_SalesCPQ_Cambio_de_plan_con_Falla_S131( ) throws AWTException{

	}

	@Test (groups = {"PerfilOficina"} )
	public void TS_144313_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_159158_CRM_Pospago_SalesCPQ_Cambio_de_plan_Actualizar_Ciclo_de_Facturacion_Solo_en_la_Primera_Gestion( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_156600_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_RedList_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_144340_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_Repro_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_156667_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_con_Gestion_en_Curso( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143266_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Inactiva( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143269_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_suspendida_Fraude( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143265_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Suspendida_Siniestro( ) throws AWTException{

	}
	
	//----------------------------------------------- Agente -------------------------------------------------------\\
	@Test (groups = {"PerfilAgente"} )
	public void TS_168774_CRM_Pospago_SalesCPQ_Cambio_de_plan_Agente_DNI_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilAgente"} )
	public void TS_168782_CRM_Pospago_SalesCPQ_Cambio_de_plan_Telefonico_P_8_R_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	@Test (groups = {"PerfilOficina"} )
	public void TS_143263_CRM_Pospago_SalesCPQ_Cambio_de_plan_con_Falla_S069() throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143262_CRM_Pospago_SalesCPQ_Cambio_de_plan_con_Falla_S131( ) throws AWTException{

	}

	@Test (groups = {"PerfilOficina"} )
	public void TS_144313_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_159158_CRM_Pospago_SalesCPQ_Cambio_de_plan_Actualizar_Ciclo_de_Facturacion_Solo_en_la_Primera_Gestion( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_156600_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_RedList_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_144340_CRM_Pospago_SalesCPQ_Cambio_de_plan_OOCC_DNI_de_Plan_con_Tarjeta_Repro_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_156667_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_con_Gestion_en_Curso( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143266_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Inactiva( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143269_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_suspendida_Fraude( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilOficina"} )
	public void TS_143265_CRM_Pospago_SalesCPQ_No_Cambio_de_plan_Linea_Suspendida_Siniestro( ) throws AWTException{

	}
	
	//----------------------------------------------- Agente -------------------------------------------------------\\
	@Test (groups = {"PerfilAgente"} )
	public void TS_168774_CRM_Pospago_SalesCPQ_Cambio_de_plan_Agente_DNI_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
	
	@Test (groups = {"PerfilAgente"} )
	public void TS_168782_CRM_Pospago_SalesCPQ_Cambio_de_plan_Telefonico_P_8_R_de_Plan_con_Tarjeta_a_APRO4( ) throws AWTException{

	}
}
