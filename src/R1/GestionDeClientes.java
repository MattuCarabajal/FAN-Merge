package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class GestionDeClientes extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private List<String> sOrders = new ArrayList<String>();
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private String imagen;
	String detalles;
	
	
	@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		sb = new SalesBase(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina, R1"}, dataProvider = "invalidaDocumentacion")
	public void TS150642_CRM_Movil_MIX_Busqueda_DNI_Numero_de_Documento_no_existente_OOCC(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "TS150642";
		sb.BuscarCuenta("DNI", sDNI);
		String message = "no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.";
		String messageFound = getTextBy(driver, By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-text-block vlc-slds-rte ng-pristine ng-valid ng-scope']"), 0);
		Assert.assertTrue(message.equalsIgnoreCase(messageFound));
	}
	
	@Test (groups = {"PerfilOficina, R1"}, dataProvider = "validaDocumentacion")
	public void TS150654_CRM_Movil_MIX_Busqueda_Numero_de_Cuenta_OOCC(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "150654";
		detalles = imagen + "- Gestion de Clientes - DNI: " + sDNI;
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
		
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico, R1"}, dataProvider = "validaDocumentacion")
	public void TS150641_CRM_Movil_MIX_Busqueda_DNI_Numero_de_Documento_Telefonico(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail){
		imagen = "150641";
		sb.BuscarCuenta("DNI", sDNI);
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
}