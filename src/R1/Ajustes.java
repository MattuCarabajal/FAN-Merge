package R1;

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

public class Ajustes extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	String detalles;
	
	
	//@BeforeClass (alwaysRun = true)
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.LoginSit();
		ges.irConsolaFanSit02();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbs = new CBS();
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
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160879_CRM_Movil_Mix_Ajuste_Credito_Datos_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS160879";
		ges.BuscarCuenta("DNI", sDNI);
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		System.out.println(datosInicial);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2019");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2019");
		selectByText(driver.findElement(By.id("Unidad")), "Datos (Mb)");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("100");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='Step-Confirmation']")));
		Assert.assertTrue(driver.findElement(By.id("Step-Confirmation")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"));
		String nroCaso = driver.findElement(By.cssSelector("[id='Step-Confirmation'] p strong")).getText();
		cc.buscarOrdenDiag(nroCaso + "*");
		cambioDeFrame(driver, By.id("searchAllSummaryView"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("searchAllSummaryView")));
		WebElement gestion = driver.findElement(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Case_body'] tbody td:nth-child(4)"));
		Assert.assertTrue(gestion.getText().equalsIgnoreCase("Realizada exitosa"));
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (100 * 1024) == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160832_CRM_Movil_Mix_Ajuste_Credito_Minutos_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS160832";
		ges.BuscarCuenta("DNI", sDNI);
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		System.out.println(datosInicial);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2019");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2019");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='Step-Confirmation']")));
		Assert.assertTrue(driver.findElement(By.id("Step-Confirmation")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"));
		String nroCaso = driver.findElement(By.cssSelector("[id='Step-Confirmation'] p strong")).getText();
		cc.buscarOrdenDiag(nroCaso + "*");
		cambioDeFrame(driver, By.id("searchAllSummaryView"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("searchAllSummaryView")));
		WebElement gestion = driver.findElement(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Case_body'] tbody td:nth-child(4)"));
		Assert.assertTrue(gestion.getText().equalsIgnoreCase("Realizada exitosa"));
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (10 * 3600) == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160833_CRM_Movil_Mix_Ajuste_Credito_SMS_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS160833";
		ges.BuscarCuenta("DNI", sDNI);
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "SMS Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		System.out.println(datosInicial);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2019");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2019");
		selectByText(driver.findElement(By.id("Unidad")), "SMS");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='Step-Confirmation']")));
		Assert.assertTrue(driver.findElement(By.id("Step-Confirmation")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"));
		String nroCaso = driver.findElement(By.cssSelector("[id='Step-Confirmation'] p strong")).getText();
		cc.buscarOrdenDiag(nroCaso + "*");
		cambioDeFrame(driver, By.id("searchAllSummaryView"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("searchAllSummaryView")));
		WebElement gestion = driver.findElement(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Case_body'] tbody td:nth-child(4)"));
		Assert.assertTrue(gestion.getText().equalsIgnoreCase("Realizada exitosa"));
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "SMS Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 123 == datosFinal);
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160831_CRM_Movil_Mix_Ajuste_Nota_de_Debito_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS160831";
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "FACTURA EMITIDA");
		selectByText(driver.findElement(By.id("CboTipo")), "Cargos fijos facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Abono/proporcional");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SelectBillingAccount_nextBtn")));
		driver.findElement(By.cssSelector("[class='slds-radio__label'] [class='slds-form-element__label ng-binding']")).click();
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nota de d\u00e9bito");
		driver.findElement(By.id("MontoLibre")).sendKeys("20000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='Step-Confirmation']")));
		Assert.assertTrue(driver.findElement(By.id("Step-Confirmation")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"));
		String nroCaso = driver.findElement(By.cssSelector("[id='Step-Confirmation'] p strong")).getText();
		cc.buscarOrdenDiag(nroCaso + "*");
		cambioDeFrame(driver, By.id("searchAllSummaryView"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("searchAllSummaryView")));
		WebElement gestion = driver.findElement(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Case_body'] tbody td:nth-child(4)"));
		Assert.assertTrue(gestion.getText().equalsIgnoreCase("Realizada exitosa"));
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160830_CRM_Movil_Mix_Ajuste_Nota_de_Credito_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS160830";
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "FACTURA EMITIDA");
		selectByText(driver.findElement(By.id("CboTipo")), "Cargos fijos facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Abono/proporcional");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SelectBillingAccount_nextBtn")));
		driver.findElement(By.cssSelector("[class='slds-radio__label'] [class='slds-form-element__label ng-binding']")).click();
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelCuenta_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nota de cr\u00e9dito");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("MontoLibre")).sendKeys("20000");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id='Step-Confirmation']")));
		Assert.assertTrue(driver.findElement(By.id("Step-Confirmation")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"));
		String nroCaso = driver.findElement(By.cssSelector("[id='Step-Confirmation'] p strong")).getText();
		cc.buscarOrdenDiag(nroCaso + "*");
		cambioDeFrame(driver, By.id("searchAllSummaryView"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("searchAllSummaryView")));
		WebElement gestion = driver.findElement(By.cssSelector("[class='bPageBlock brandSecondaryBrd secondaryPalette'] [id='Case_body'] tbody td:nth-child(4)"));
		Assert.assertTrue(gestion.getText().equalsIgnoreCase("Realizada exitosa"));
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160801_CRM_Movil_Mix_Ajuste_General_Derivacion_manual_Crm_OC(String sDNI, String sLinea){
		imagen = "TS160801";
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Informacion incorrecta");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono m");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding ng-scope']")), "equals", "agregar unidades");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SummaryDerivateToBO_nextBtn")));
		driver.findElement(By.id("SummaryDerivateToBO_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("la gesti\u00f3n se deriv\u00f3 al area equipo back office centralizado"));
		String caso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header p label")).getText();
		String nCaso = cc.getNumeros(caso);
		System.out.println(cc.getNumeros(caso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(nCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
		sleep(2000);
		String estadoDelCaso = driver.findElement(By.cssSelector("[class = 'listRelatedObject caseBlock'] [class = 'bPageBlock brandSecondaryBrd secondaryPalette'] [class = 'pbBody'] table tbody tr [class = ' dataCell  cellCol3 ']")).getText();
		Assert.assertTrue(estadoDelCaso.equalsIgnoreCase("derivada"));	
		
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160897_CRM_Movil_Mix_Ajuste_Backoffice_modifica_cantidades_Crm_OC(String sDNI, String sLinea) {
		imagen = "TS160897";
//		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea, "bcs:MainBalance");
//		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, datoViejo.length()-4));
//		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Informacion incorrecta");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("04-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("24-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("60000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, datoNuevo.length()-4));
		System.out.println(datosFinal);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("NewAdjustment")));
		String orden = driver.findElement(By.xpath("//*[@id='txtSuccessConfirmation']//p//strong")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		//////////////////////////////////////////////  FALTA LA VERIFICACION BO   ///////////////////////////////////////////
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("globalHeaderNameMink")));
		driver.findElement(By.id("globalHeaderNameMink")).click();
		driver.findElement(By.xpath("//*[@class='zen-select zen-open']//li//a[text()='Finalizar sesi\u00f3n']")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("userDropdown")));
		driver.findElement(By.xpath("//*[@id='userDropdown']//img")).click();
		driver.findElement(By.id("logout")).click();
		ges.cambiarPerfil("uat518122");
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(18000);
		cc.cerrarTodasLasPestanas();
		cc.buscarCaso(orden+"*");
		driver.switchTo().frame(cambioFrame(driver, By.name("edit")));
		WebElement list = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table"));
		list.findElements(By.tagName("td")).get(1).findElements(By.tagName("a")).get(1).click();
		sleep(5000);
		selectByText(driver.findElement(By.id("newOwn_mlktp")), "Usuario");
		driver.findElement(By.id("newOwn")).sendKeys("Marcelo Aletta");
		driver.findElement(By.name("save")).click();
		sleep(5000);
		driver.findElements(By.className("actionLink")).get(2).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("goNext")));
		driver.findElement(By.name("goNext")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("extraStatusDiv_A")));
		Assert.assertTrue(driver.findElement(By.className("extraStatusDiv_A")).getText().equalsIgnoreCase("Aprobado"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160902_CRM_Movil_Mix_Se_crea_caso_de_ajuste_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS160902";
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Informacion incorrecta");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("2000000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		clickBy(driver, By.id("Step-Summary_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//h1")).getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"));
		String orden = driver.findElement(By.xpath("//*[@class='ta-care-omniscript-done']//header//label")).getText();
		orden = orden.substring(orden.lastIndexOf(" ")+1, orden.length());
		System.out.println(orden);
		Assert.assertTrue(orden.matches("\\d{8}"));
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160916_CRM_Movil_Mix_Ajuste_RAV_Unidades_Libres_a_Pesos_Genera_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS160916";
		WebElement monto = null;
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono m");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AjusteNivelLinea_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list)
			if (x.getText().toLowerCase().contains("monto"))
				monto = x;
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 78.00"));
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "CuentaAjustesMIX")
	public void TS160884_CRM_Movil_Mix_Ajuste_Credito_Monto_1500_Aprobador_Front_Sin_revisor_Exepcion_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS160884";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, datoViejo.length()-4));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", sDNI);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
		cc.irAGestion("inconvenientes con cargos tasados");
		cambioDeFrame(driver, By.id("Step-TipodeAjuste_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Recarga delivery");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("150000");
		cc.obligarclick(driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step-Summary_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("Step-Summary_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("NewAdjustment")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='txtSuccessConfirmation']//h1")).getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, datoNuevo.length()-4));
		System.out.println("datofinal" + datosFinal);
		Assert.assertTrue(datosInicial + 150000 == datosFinal);
	}
}