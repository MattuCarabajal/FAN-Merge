package R2;

import java.io.IOException;
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
	
	@BeforeClass (groups= "PerfilOficina")
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
		
	//@BeforeClass (groups= "PerfilTelefonico")
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
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
	}

	//@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaSinSaldo")
	public void TS176150_CRM_Movil_PRE_No_Renovacion_de_cuota_Presencial_Descuento_de_saldo_sin_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS176150";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver,By.cssSelector("[class='card-top']"),0);
		ges.irAGestionEnCardPorNumeroDeLinea("Renovacion de Datos", sLinea);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		sleep(5000);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("Reseteo Internet por"))
				elemento.findElement(By.className("slds-checkbox")).click();
		}
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='message description ng-binding ng-scope']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='message description ng-binding ng-scope']")).getText().contains("Saldo Insuficiente"));
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RenovacionCuotaConSaldo")
	public void TS176168_CRM_Movil_PRE_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS176168";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver,By.cssSelector("[class='card-top']"),0);
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);		
		String saldoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldoInicial = Integer.parseInt(saldoViejo.substring(0, saldoViejo.length()-4));
		ges.irAGestionEnCardPorNumeroDeLinea("Renovacion de Datos", sLinea);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		sleep(5000);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer"), 0));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("Reseteo Internet por")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "descuento");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='ta-care-omniscript-done']")).getText().contains("La operaci\u00f3n termino exitosamente"));
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + 51200 == datosFinal);		
		String saldoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldoFinal = Integer.parseInt(saldoNuevo.substring(0, saldoNuevo.length()-4));
		Assert.assertTrue(saldoInicial - 4000 == saldoFinal);
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dependsOnMethods = "TS176150_CRM_Movil_PRE_No_Renovacion_de_cuota_Presencial_Descuento_de_saldo_sin_Credito")
	public void TS176217_CRM_Movil_REPRO_No_Renovacion_de_cuota_Presencial_Descuento_de_saldo_sin_Credito() {
		imagen = "TS176217";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dependsOnMethods = "TS176168_CRM_Movil_PRE_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito")
	public void TS176233_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito() {
		imagen = "TS176233";
		Assert.assertTrue(true);
	}
	
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dependsOnMethods = "TS176150_CRM_Movil_PRE_No_Renovacion_de_cuota_Presencial_Descuento_de_saldo_sin_Credito")
	public void TS176147_CRM_Movil_PRE_No_Renovacion_de_cuota_Telefonico_Descuento_de_saldo_sin_Credito() {
		imagen = "TS176147";
		Assert.assertTrue(true);
	}
		
	@Test (groups = {"PerfilOficina", "R2"}, dependsOnMethods = "TS176168_CRM_Movil_PRE_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito")
	public void TS176167_CRM_Movil_PRE_Renovacion_de_cuota_Telefonico_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito() {
		imagen = "TS176167";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dependsOnMethods = "TS176168_CRM_Movil_PRE_Renovacion_de_cuota_Presencial_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito")
	public void TS176232_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Reseteo_Internet_por_Dia_Limitrofe_Descuento_de_saldo_con_Credito() {
		imagen = "TS176232";
		Assert.assertTrue(true);
	}
}