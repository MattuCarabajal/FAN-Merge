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

import Pages.CBS;
import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class RenovacionDeCuota extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private CustomerCare cc;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	@BeforeClass (groups= "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups= "PerfilAgente")
	public void initAgente() {
		driver = setConexion.setupEze();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
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
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176147_CRM_Movil_PRE_No_Renovacion_de_cuota_Telefonico_Descuento_de_saldo_sin_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176147";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176150_CRM_Movil_PRE_No_Renovacion_de_cuota_Presencial_Descuento_de_saldo_sin_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176150";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176167_CRM_Movil_PRE_Renovacion_de_cuota_Telefonico_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176167";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176168_CRM_Movil_PRE_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176168";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176217_CRM_Movil_REPRO_No_Renovacion_de_cuota_Presencial_Descuento_de_saldo_sin_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176217";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176232_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176232";
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176233_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid) {
		imagen="TS176233";
	}
}