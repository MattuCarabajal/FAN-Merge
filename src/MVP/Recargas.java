package MVP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class Recargas extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (alwaysRun = true)
		public void initAgente() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	//@AfterMethod(alwaysRun=true)
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargaEfectivo")
	public void TS134318_CRM_Movil_REPRO_Recargas_Presencial_Efectivo_Ofcom(String sDNI, String sMonto, String sLinea) {
		imagen = "TS134318";
		detalles = imagen + "-Recarga-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		System.out.println("caso numero :"+caso);
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		cbsm.Servicio_NotificarEmisionFactura(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider="RecargaTC")
	public void TS134319_CRM_Movil_REPRO_Recargas_Presencial_TC_Ofcom(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134319";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		cbsm.Servicio_NotificarEmisionFactura(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargaTD")
	public void TS134320_CRM_Movil_REPRO_Recargas_Presencial_TD_Ofcom(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de debito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 900 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargaTC")  
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen= "TS134332";
		detalles = imagen+"-Recarga-DNI:"+sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDNI);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);				
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "MVP"}, dataProvider="RecargaTC")
	public void TS134322_CRM_Movil_REPRO_Recargas_Presencial_TC_Agente(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134322";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
}