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
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups= "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initAgente() {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod (alwaysRun = true)
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
	

	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "validaDocumentacion")
	public void TS178016_CRM_Movil_Pre_Busqueda_Apellido_OOCC(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS178016";
		sb.BuscarAvanzada("", sApellido, "", "", "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "validaDocumentacion")
	public void TS178038_CRM_Movil_Repro_Busqueda_Apellido_OOCC(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS178038";
		sb.BuscarAvanzada("", sApellido, "", "", "");
		Assert.assertTrue(ges.estaEnClientes(sDNI));
	}
	
	//----------------------------------------------- AGENTE  -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "validaDocumentacion")
	public void TS178030_CRM_Movil_Repro_Busqueda_Tipo_de_documento_DNI_Agente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sRazon, String sEmail) {
		imagen = "TS178030";
		sb.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']")));
		WebElement cliente = driver.findElement(By.cssSelector("[class='slds-tabs--scoped__content'] tbody [class='searchClient-body slds-hint-parent ng-scope']"));
		String dni = cliente.findElements(By.tagName("td")).get(3).getText();
		Assert.assertTrue(sDNI.equals(dni));
	}
}