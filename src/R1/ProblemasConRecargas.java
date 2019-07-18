package R1;

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
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void Sit03() {
		driver = setConexion.setupEze();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.LoginSit03();
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
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
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
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaProblemaRecarga")
	public void TS148758_CRM_Movil_Mix_Problemas_con_Recarga_On_Line_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS148758";
		detalles = imagen + " -Problema con recargas - DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 7));
		System.out.println(datosInicial);
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
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 7));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 100000 == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaProblemaRecarga")
	public void TS148767_CRM_Movil_Mix_Problemas_con_Recarga_TC_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS148758";
		detalles = imagen + " -Problema con recargas - DNI: " + sDNI;
		String monto = "1000";
		String comprobante = "123";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 7));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Inconvenientes con Recargas", sLinea);
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "equals", "Tarjeta de Cr\u00e9dito");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CreditCardData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "equals", "SI");
		driver.findElement(By.id("CreditCardRefillAmount")).sendKeys(monto);
		driver.findElement(By.id("CreditCardRefillReceipt")).sendKeys(comprobante);
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
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 7));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 100000 == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "R1"} , dataProvider = "CuentaProblemaRecarga" )
	public void TS162880_CRM_Movil_Mix_Problemas_con_Recarga_Validaciones_sobre_el_servicio_Linea_suspendida_Crm_OC(String sDNI, String sLineas) {
		imagen = "TS162880";
		detalles = imagen + " -Problema con recargas - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", "57132590");
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		List<WebElement> cards = driver.findElements(By.cssSelector("[class*='console-card active']"));
		WebElement cardPorLinea= ges.getBuscarElementoPorText(ges.listaDeElementosPorText(cards, "3861453831"),"Suspendido");
		cardPorLinea.findElement(By.cssSelector("[id='flecha'] i")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='community-flyout-actions-card'] ul li"), 0));
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li"), 0));
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-button slds-button--neutral ']"), 0));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='community-flyout-actions-card'] ul li"));
		elementos.addAll(driver.findElements(By.cssSelector("[class='card-info-hybrid'] [class='actions'] li")));
		elementos.addAll(driver.findElements(By.cssSelector("[class='slds-button slds-button--neutral ']")));
		driver.findElement(By.xpath("//*[@class = 'community-flyout-actions-card']//ul//li//span[contains(text(), 'Inconvenientes con Recargas')]")).click();;
		cambioDeFrame(driver, By.cssSelector("[class = 'slds-icon slds-icon--large nds-icon nds-icon_large ta-care-omniscript-error-icon']"), 0);
		String primerMensaje = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] div header h1")).getText();
		String segundoMensaje = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] div header p label")).getText();
		Assert.assertTrue(primerMensaje.toLowerCase().contains("no se puede continuar la gesti\u00f3n"));
		Assert.assertTrue(segundoMensaje.toLowerCase().contains("en este momento la l\u00ednea cuenta con restricciones para poder hacer recargas"));
		
	}	
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaProblemaRecarga")
	public void TS148755_CRM_Movil_Mix_Problemas_con_Recarga_Tarjeta_Scratch_Caso_Nuevo_Inexistente_Crm_Telefonico(String sDNI, String sLinea) {
		imagen = "TS148755";
		detalles = imagen + " -Problema con recargas - DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 7));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCardPorNumeroDeLinea("Inconvenientes con Recargas", sLinea);
		cambioDeFrame(driver, By.id("RefillMethods_nextBtn"), 0);
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "equals", "Tarjeta Prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("PrepaidCardData_nextBtn")));
		driver.findElement(By.cssSelector("[id = 'BatchNumber']")).sendKeys("11120000009882");
		driver.findElement(By.cssSelector("[id = 'PIN']")).sendKeys("0638");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		try {
			ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("ExistingCase_nextBtn")));
			buscarYClick(driver.findElements(By.cssSelector("[class = 'imgItemContainer ng-scope']")), "equals", "Crear un caso nuevo");
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
		} catch (Exception e) {}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Summary_nextBtn")));
		List <WebElement> documentacion = driver.findElements(By.cssSelector("[class = 'slds-p-around--medium'] [class = 'slds-list--horizontal slds-wrap'] [class = 'slds-item--label slds-text-color--weak slds-truncate ng-binding ng-scope']"));
		List <String> documentacionValidacion = new ArrayList<String>(Arrays.asList("L\u00ednea:", "Monto:" , "N\u00famero de Serie:"));
		for (int i = 0; i < documentacion.size(); i++) {
			Assert.assertTrue(documentacion.get(i).getText().equals(documentacionValidacion.get(i)));
		}
		driver.findElement(By.id("Summary_nextBtn")).click();
		cambioDeFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-done-icon"), 0);
		WebElement verificacion = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] div header h1"));
		Assert.assertTrue(verificacion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 7));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 1500000 == datosFinal);		
	}
}