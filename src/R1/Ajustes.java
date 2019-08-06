package R1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
	
	@Test
	public void TS160879_CRM_Movil_Mix_Ajuste_Credito_Datos_Crm_OC() {
		imagen = "TS160879";
		ges.BuscarCuenta("DNI", "42377438");
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit("2932598655"), "Datos Libres");
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
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit("2932598655"), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (100 * 1024) == datosFinal);
	}
	
	@Test
	public void TS160832_CRM_Movil_Mix_Ajuste_Credito_Minutos_Crm_OC() {
		imagen = "TS160832";
		ges.BuscarCuenta("DNI", "42377438");
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit("2932598655"), "Segundos Libres");
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
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit("2932598655"), "Segundos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (10 * 3600) == datosFinal);
	}
	
	@Test
	public void TS160833_CRM_Movil_Mix_Ajuste_Credito_SMS_Crm_OC() {
		imagen = "TS160833";
		ges.BuscarCuenta("DNI", "42377438");
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit("2932598655"), "SMS Libres");
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
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit("2932598655"), "SMS Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 123 == datosFinal);
	}
	
	@Test
	public void TS160831_CRM_Movil_Mix_Ajuste_Nota_de_Debito_Crm_OC() {
		imagen = "TS160831";
		ges.BuscarCuenta("DNI", "42377438");
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
	
	@Test
	public void TS160830_CRM_Movil_Mix_Ajuste_Nota_de_Credito_Crm_OC() {
		imagen = "TS160830";
		ges.BuscarCuenta("DNI", "42377438");
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
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	
}