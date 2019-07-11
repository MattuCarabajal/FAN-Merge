package Funcionalidades;

import java.awt.AWTException;
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
import Pages.SalesBase;
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
	public void Sit03() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit03();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		//cc.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
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
	public void initTelefonico() throws IOException, AWTException {
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
		public void initAgente() throws IOException, AWTException {
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
	public void setup() throws Exception {
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
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargaEfectivo")
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
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	@Test (groups = "PerfilOficina", dataProvider="RecargaTC")
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
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargaTD")
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
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargaTC")
	public void TS134330_CRM_Movil_REPRO_Recargas_Presencial_TC_Ofcom_Financiacion(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134330";
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
	@Test (groups = "PerfilOficina, R1", dataProvider="RecargaEfectivo")
	public void TS146849_CRM_Movil_APRO2_Recarga_OOCC_Efectivo(String sDNI, String sMonto, String sLinea){
		imagen="146849";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String credInicial = cc.consutarSaldoEnCard(sLinea);
		credInicial = credInicial.replace("$", "");
		credInicial = credInicial.substring(0, 3);
		int x = Integer.parseInt(credInicial);
		System.out.println(x);
		cc.irAGestion("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("AssetSelection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "conexi\u00f3n control abono s2 - "+sLinea);
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(7500);
		driver.navigate().refresh();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String credFinal = cc.consutarSaldoEnCard(sLinea);
		credFinal = credFinal.replace("$", "");
		credFinal = credFinal.substring(0, 3);
		int z = Integer.parseInt(credFinal);
		System.out.println(z);
		int s = Integer.parseInt(sMonto);
		Assert.assertTrue(x + s == z);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");	
		
	}
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargaTC")  
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) throws AWTException {
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
	
	@Test (groups = "PerfilTelefonico, R1", dataProvider = "RecargaTC")  
	public void TS146857_CRM_Movil_APRO2_Recarga_Telefonico_TDC(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) throws AWTException {
		imagen= "146857";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String credInicial = cc.consutarSaldoEnCard(sLinea);
		credInicial = credInicial.replace("$", "");
		credInicial = credInicial.substring(0, 3);
		int x = Integer.parseInt(credInicial);
		System.out.println(x);
		cc.irAGestion("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("AssetSelection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "conexi\u00f3n control abono s2 - "+sLinea);
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
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
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(7500);
		driver.navigate().refresh();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String credFinal = cc.consutarSaldoEnCard(sLinea);
		credFinal = credFinal.replace("$", "");
		credFinal = credFinal.substring(0, 3);
		int z = Integer.parseInt(credFinal);
		System.out.println(z);
		int s = Integer.parseInt(sMonto);
		Assert.assertTrue(x + s == z);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");	
	}
	

	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = "PerfilAgente", dataProvider="RecargaTC")
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
	
	@Test (groups = "PerfilAgente", dataProvider="RecargaEfectivo")
	public void TS_Recarga_de_Cerdito_Efectivo_Agente(String sDNI, String sMonto, String sLinea) {
//		imagen = "TS134322";
//		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		System.out.println(datosInicial);
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
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + Integer.parseInt(sMonto) * 100 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}
	
	@Test (groups = "PerfilAgente", dataProvider="RecargaTD")
	public void TS_Recarga_de_Credito_TDD_Agente(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
//		imagen = "";
//		detalles = imagen + "-Recarga-DNI:" + sDNI;
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
	
	@Test (groups = "PerfilAgente", dataProvider="RecargaEfectivo")
	public void TS_Recarga_Limites_Maximo_y_Minimo(String sDNI, String sMonto, String sLinea) {
//		imagen = "TS134322";
//		detalles = imagen + "-Recarga-DNI:" + sDNI;
		int minimo = 5;
		int maximo = 500;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		// Prueba limite inferior no permitido
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(minimo-1+"");
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='alert-container']//p")).getText().equalsIgnoreCase("Error : Por favor corrija los campos con errores"));
		driver.findElement(By.xpath("//div[@id='alert-container']//button")).click();
		// Prueba limite superior no permitido
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).clear();
		driver.findElement(By.id("RefillAmount")).sendKeys(maximo+1+"");
		clickBy(driver, By.id("AmountSelectionStep_nextBtn"), 0);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='alert-container']//p")).getText().equalsIgnoreCase("Error : Por favor corrija los campos con errores"));
		driver.findElement(By.xpath("//div[@id='alert-container']//button")).click();
		// Prueba limite inferior permitido
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).clear();
		driver.findElement(By.id("RefillAmount")).sendKeys(minimo+"");
		clickBy(driver, By.id("AmountSelectionStep_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-form-element__control'] p p")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-form-element__control'] p p")).getText().contains("Nro. orden"));
		driver.findElement(By.id("InvoicePreview_prevBtn")).click();
		// Prueba limite superior permitido
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).clear();
		driver.findElement(By.id("RefillAmount")).sendKeys(maximo+"");
		clickBy(driver, By.id("AmountSelectionStep_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-form-element__control'] p p")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-form-element__control'] p p")).getText().contains("Nro. orden"));
		}
	
	@Test(groups = "PerfilAgente , R1" , dataProvider="RecargaTD")
	public void TS146855_CRM_Movil_APRO2_Recarga_Agente_TDD(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular){
		imagen="146855";
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String credInicial = cc.consutarSaldoEnCard(sLinea);
		credInicial = credInicial.replace("$", "");
		credInicial = credInicial.substring(0, 3);
		int x = Integer.parseInt(credInicial);
		System.out.println(x);
		cc.irAGestion("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("AssetSelection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AssetSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "conexi\u00f3n control abono s2 - "+sLinea);
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
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
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(7500);
		driver.navigate().refresh();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String credFinal = cc.consutarSaldoEnCard(sLinea);
		credFinal = credFinal.replace("$", "");
		credFinal = credFinal.substring(0, 3);
		int z = Integer.parseInt(credFinal);
		System.out.println(z);
		int s = Integer.parseInt(sMonto);
		Assert.assertTrue(x + s == z);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");	
		//String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		//Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		//Assert.assertTrue(datosInicial + 900 == datosFinal);	
		
	}
}