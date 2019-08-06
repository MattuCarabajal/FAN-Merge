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
	
	
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	@Test (groups = {"PerfilOficina", "R1"} )
	public void TS160902_CRM_Movil_Mix_Se_crea_caso_de_ajuste_Crm_Telefónico(){
		imagen = "TS160902";
		
		ges.BuscarCuenta("DNI", "42377438");
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
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "conexi\u00f3n control abono m");
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
	
	@Test (groups = {"PerfilOficina", "R1"} )
	public void TS160916_CRM_Movil_Mix_Ajuste_RAV_Unidades_Libres_a_Pesos_Genera_Crm_Telefonico(){
	imagen = "TS160916";
	WebElement monto = null;
	ges.BuscarCuenta("DNI", "42377438");
	ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='x-panel view_context x-border-panel']")));
	cc.irAGestion("inconvenientes con cargos tasados");
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
	
	@Test (groups = {"PerfilOficina", "R1"} )
	public void TS160884_CRM_Movil_Mix_Ajuste_Credito_Monto_1500_Aprobador_Front_Sin_revisor_Exepcion_Crm_Telefonico(){
		imagen = "TS160884";
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber("2932598789"), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, datoViejo.length()-4));
		System.out.println(datosInicial);
		ges.BuscarCuenta("DNI", "38010123");
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
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber("2932598789"), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, datoNuevo.length()-4));
		System.out.println("datofinal" + datosFinal);
		Assert.assertTrue(datosInicial + 150000 == datosFinal);
		
		
	}
}