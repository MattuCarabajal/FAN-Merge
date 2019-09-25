package R2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CBS;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class ProblemasConRecargas extends TestBase {

	private WebDriver driver;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.loginOOCC();
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
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS147740_CRM_Movil_Mix_Problemas_con_Recarga_On_Line_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS147740";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, datoViejo.length()-6));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Inconvenientes con Recargas", sLinea);
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "equals", "Recarga Online");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("OnlineRefillData_nextBtn")));
		driver.findElement(By.id("RefillDate")).sendKeys("02-07-2019");
		driver.findElement(By.id("OnlineRefillAmount")).sendKeys("1000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		try {
			ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ExistingCase_nextBtn")));
			buscarYClick(driver.findElements(By.cssSelector("[class = 'imgItemContainer ng-scope']")), "equals", "Crear un caso nuevo");
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
		} catch (Exception e) {}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("AttachDocuments_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileAttach")).sendKeys(new File(directory.getAbsolutePath()).toString());
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		driver.findElement(By.id("Summary_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-done-icon"), 0);
		WebElement verificacion = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] div header h1"));
		Assert.assertTrue(verificacion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, datoNuevo.length()-6));
		Assert.assertTrue(datosInicial + 10 == datosFinal);
	}

	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS147749_CRM_Movil_Mix_Problemas_con_Recarga_TC_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS147749";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, datoViejo.length()-6));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Inconvenientes con Recargas", sLinea);
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "equals", "Tarjeta de Cr\u00e9dito");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CreditCardData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "equals", "SI");
		driver.findElement(By.id("CreditCardRefillAmount")).sendKeys("1000");
		driver.findElement(By.id("CreditCardRefillReceipt")).sendKeys("123");
		driver.findElement(By.id("CreditCardData_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		List <WebElement> documentacion = driver.findElements(By.cssSelector("[class = 'slds-p-around--medium'] [class = 'slds-list--horizontal slds-wrap'] [class = 'slds-item--label slds-text-color--weak slds-truncate ng-binding ng-scope']"));
		List <String> documentacionValidacion = new ArrayList<String>(Arrays.asList("L\u00ednea:", "Monto:" , "Comprobante:"));
		for (int i = 0; i < documentacion.size(); i++) {
			Assert.assertTrue(documentacion.get(i).getText().equals(documentacionValidacion.get(i)));
		}
		driver.findElement(By.id("Summary_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-done-icon"), 0);
		WebElement verificacion = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] div header h1"));
		Assert.assertTrue(verificacion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, datoNuevo.length()-6));
		Assert.assertTrue(datosInicial + 10 == datosFinal);
	}

	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaProblemaRecargaQuemada")
	public void TS178664_CRM_Movil_Pre_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Quemada_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS178664";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PrepaidCardData_nextBtn")));
		driver.findElement(By.id("BatchNumber")).sendKeys(sBatch);
		driver.findElement(By.id("PIN")).sendKeys(sPin);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector("[class='ta-care-omniscript-done']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='ta-care-omniscript-done']")).getText().toLowerCase().contains("la tarjeta ya fue utilizada para una recarga"));
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaProblemaRecargaVencida")
	public void TS178665_CRM_Movil_Pre_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Vencida_Crm_OC(String sDNI, String sLinea, String sBatch, String sPin) {
		imagen = "TS178665";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PrepaidCardData_nextBtn")));
		driver.findElement(By.id("BatchNumber")).sendKeys(sBatch);
		driver.findElement(By.id("PIN")).sendKeys(sPin);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector("[class='ta-care-omniscript-done']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='ta-care-omniscript-done']")).getText().toLowerCase().contains("la tarjeta expir\u00f3"));
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS178666_CRM_Movil_Pre_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Inexistente_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS178666";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Inconvenientes con Recargas");
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PrepaidCardData_nextBtn")));
		driver.findElement(By.id("BatchNumber")).sendKeys("001852952914952");
		driver.findElement(By.id("PIN")).sendKeys("0123");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector("[class='ta-care-omniscript-done']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='ta-care-omniscript-done']")).getText().contains("Error al Obtener Estado de la Tarjeta"));
	}
}