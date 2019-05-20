package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
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
	private SalesBase sb;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (alwaysRun = true)
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod(alwaysRun=true)
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

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E","Ciclo1"}, dataProvider = "RecargaEfectivo")
	public void TS134318_CRM_Movil_REPRO_Recargas_Presencial_Efectivo_Ofcom(String sDNI, String sMonto, String sLinea) {
		imagen = "TS134318";
		detalles = imagen + "-Recarga-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("InvoicePreview_nextBtn")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Recargas","E2E", "Ciclo1"}, dataProvider="RecargaTC")
	public void TS134319_CRM_Movil_REPRO_Recargas_Presencial_TC_Ofcom(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134319";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("InvoicePreview_nextBtn")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E"}, dataProvider = "RecargaTD")
	public void TS134320_CRM_Movil_REPRO_Recargas_Presencial_TD_Ofcom(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("InvoicePreview_nextBtn")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de debito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 900 == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E","Ciclo1"}, dataProvider = "RecargaTC")
	public void TS134330_CRM_Movil_REPRO_Recargas_Presencial_TC_Ofcom_Financiacion(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134330";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("InvoicePreview_nextBtn")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "Recargas","E2E"}, dataProvider = "RecargaTC")  //Error despues de ingresar la tarjeta
	//public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String cDNI, String cMonto, String cLinea, String cBanco, String cTarjeta, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular, String cPromo, String cCuotas) throws AWTException {
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String cDNI, String cMonto, String cLinea, String cBanco, String cTarjeta, String cPromo, String cCuota, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTitular) throws AWTException {
	imagen= "TS134332";
		detalles = null;
		detalles = imagen+"-Recarga-DNI:"+cDNI;
	/*	CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		System.out.println("monto "+iMainBalance);
		if(cMonto.length() >= 4) {
			cMonto = cMonto.substring(0, cMonto.length()-1);
		}
		if(cVenceMes.length() >= 2) {
			cVenceMes = cVenceMes.substring(0, cVenceMes.length()-1);
		}
		if(cVenceAno.length() >= 5) {
			cVenceAno = cVenceAno.substring(0, cVenceAno.length()-1);
		}
		if(cCodSeg.length() >= 5) {
			cCodSeg = cCodSeg.substring(0, cCodSeg.length()-1);
		}*/
	
		
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(cMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(20000);
		String sOrden = cc.obtenerOrden3(driver);
		detalles +="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		sleep(1000);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), cCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNI);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);				
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		buscarYClick(driver.findElements(By.id("InvoicePreview_nextBtn")), "equals", "siguiente");
		List <WebElement> exis = driver.findElements(By.id("GeneralMessageDesing"));
		boolean a = false;
		for(WebElement x : exis) {
			if(x.getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
			Assert.assertTrue(a);
		}
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+="-Monto:"+orden.split("-")[2]+"-Prefactura:"+orden.split("-")[1];
		//CBS_Mattu invoSer = new CBS_Mattu();
		//invoSer.PagoEnCaja("1003", accid, "2001", orden.split("-")[2], orden.split("-")[1],driver);
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		//String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		//System.out.println("saldo nuevo "+uMainBalance);
		//Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Integer monto = Integer.parseInt(orden.split("-")[2].replace(".", ""));
		monto = Integer.parseInt(monto.toString().substring(0, monto.toString().length()-1));
//		monto = iMainBalance+monto;
		//Assert.assertTrue(monto == uiMainBalance);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAgente","Recargas","E2E", "Ciclo1"}, dataProvider="RecargaTC")
	public void TS134322_CRM_Movil_REPRO_Recargas_Presencial_TC_Agente(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134322";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("InvoicePreview_nextBtn")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
	}
}