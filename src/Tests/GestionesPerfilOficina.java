package Tests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CBS;
import Pages.CalculoImpuestos;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.OM;
import Pages.PagePerfilTelefonico;
import Pages.SCP;
import Pages.SalesBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRAutogestionPage;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;

public class GestionesPerfilOficina extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private Marketing mk;
	private PagePerfilTelefonico ppt;
	private SCP scp;
	List<String> sOrders = new ArrayList<String>();
	String imagen;
	String detalles;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws IOException, AWTException {
		CalculoImpuestos CI = new CalculoImpuestos();
		CBS_Mattu cCBSM = new CBS_Mattu();
		GestionFlow gGF = new GestionFlow();
		//System.out.println(gGF.FlowIMSI(driver, "2932449333"));
		
		
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		mk = new Marketing(driver);
		loginMerge(driver);
		sleep(22000);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			/*sleep(3000);
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);*/
		}
		
		driver.switchTo().defaultContent();
		sleep(6000);
		
		//Assert.assertTrue(cCBSM.Imprimir(driver,"20181227000000108255", "1000000026310001"));
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(3000);
		goToLeftPanel4(driver, "Inicio");
		sleep(10000);
		try {
			sb.cerrarPestaniaGestion(driver);
		} catch (Exception ex1) {}
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");
			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		
		sleep(10000);
		
	}

	@AfterMethod(alwaysRun=true)
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
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigos")
	public void TS100602_CRM_Movil_REPRO_FF_Alta_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100602";
		detalles = null;
		detalles = imagen+"-Numeros Amigos-DNI:"+sDNI;
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		waitFor(driver,By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope"));
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		//waitForClickeable(driver, By.className("card-top"));
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		sleep(15000);
		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		Assert.assertTrue(bAssert);
		sleep(5000);
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		if (iIndice == 0)
			Assert.assertTrue(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "voz", sNumeroVOZ));
		else
			Assert.assertTrue(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "sms", sNumeroSMS));
		sOrders.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		String orden = cc.obtenerOrden(driver, "Numero Gratis");
		detalles +="-Orden:"+orden;
		sOrders.add("Numeros amigos, orden numero: " + orden + " con numero de DNI: " + sDNI);
		sleep(10000);
		BasePage bBP = new BasePage();
		bBP.closeTabByName(driver, "N\u00fameros Gratis");
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		Assert.assertTrue(mMarketing.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		Assert.assertTrue(cc.corroborarEstadoCaso(orden, "Activated"));
		//Verify when the page works
	}
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E","Ciclo1"}, dataProvider="NumerosAmigosModificacion")
	public void TS100604_CRM_Movil_REPRO_FF_Modificacion_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100604";
		detalles = null;
		detalles = imagen+"-Numeros Amigos-DNI:"+sDNI;
		BasePage cambioFrame=new BasePage();
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(15000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).clear();
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).clear();
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		sleep(5000);
		List<WebElement> opcs = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for(WebElement UnaO: opcs) {
			if(UnaO.getText().equalsIgnoreCase("si")) {
				UnaO.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		sleep(20000);
		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		if (iIndice == 0)
			Assert.assertTrue(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "voz",sNumeroVOZ));
		else
			Assert.assertTrue(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "sms",sNumeroSMS));
		sOrders.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		Assert.assertTrue(bAssert);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Numero Gratis");
		sOrders.add("Numeros amigos, orden numero: " + orden + " con numero de DNI: " + sDNI);
		sleep(10000);
		BasePage bBP = new BasePage();
		bBP.closeTabByName(driver, "N\u00fameros Gratis");
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance-2050000 >= uiMainBalance);
		Assert.assertTrue(mMarketing.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		//Verify when the page works
	}
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E","Ciclo1"}, dataProvider="NumerosAmigosBaja")
	public void TS100605_CRM_Movil_REPRO_FF_Baja_Presencial(String sDNI, String sLinea, String sVOZorSMS) throws AWTException {
		imagen = "TS100605";
		detalles = null;
		detalles = imagen+"-Numeros Amigos-DNI:"+sDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(15000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).click();
		Robot r = new Robot();    
		for(int i = 0; i<10;i++) {
			r.keyPress(KeyEvent.VK_BACK_SPACE); 
			r.keyRelease(KeyEvent.VK_BACK_SPACE);
		}
		sleep(2000);
		cCC.obligarclick(driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")));
		sleep(5000);
		List<WebElement> opcs = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for(WebElement UnaO: opcs) {
			if(UnaO.getText().equalsIgnoreCase("si")) {
				UnaO.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		sleep(15000);
		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		if (Integer.parseInt(sVOZorSMS) == 0)
			Assert.assertFalse(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "voz",""));
		else
			Assert.assertFalse(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "sms",""));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance-2050000 >= uiMainBalance);
		sOrders.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		Assert.assertTrue(bAssert);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Numero Gratis");
		detalles +="-Orden:"+orden;
		sleep(10000);
		BasePage bBP = new BasePage();
		bBP.closeTabByName(driver, "N\u00fameros Gratis");
		sOrders.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Assert.assertTrue(wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).getText().isEmpty());
		//Verify when the page works
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E","Ciclo1"}, dataProvider = "RecargaEfectivo")
	public void TS134318_CRM_Movil_REPRO_Recargas_Presencial_Efectivo_Ofcom(String cDNI, String cMonto, String cLinea) throws AWTException {
		sleep(6000);
		CBS_Mattu invoSer = new CBS_Mattu();
		imagen = "TS134318"+cDNI;
		detalles = null;
		detalles = imagen+"-Recarga-DNI:"+cDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		//String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		//Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		if(cMonto.length() >= 4) {
			cMonto = cMonto.substring(0, cMonto.length()-1);
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI); //modificacion de dni 95905123
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(24000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(cLinea,driver); //modificacion de linea 2932523159
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(18000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(cMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(15000);
		String sOrden = cc.obtenerOrden3(driver);
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		String msj = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText(); 
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(msj.toLowerCase().contains("se ha enviado correctamente la factura a huawei. dirigirse a caja para realizar el pago de la misma"));
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.switchTo().defaultContent();
		sb.cerrarPestaniaGestion(driver);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		//String orden = cc.obtenerOrdenMontoyTN(driver, "Recarga");
		System.out.println("orden = "+orden);
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + cDNI +", Monto:"+orden.split("-")[2]);
		Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[2], orden.split("-")[1],driver));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		System.out.println("saldo nuevo "+uMainBalance);
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Integer monto = Integer.parseInt(orden.split("-")[2].replace(".", ""));
		monto = Integer.parseInt(monto.toString().substring(0, monto.toString().length()-1));
		//System.out.println("monto inicial "+iMainBalance);
		System.out.println("monto recarga "+monto);
		System.out.println("monto uifinal "+uiMainBalance);
		//monto = iMainBalance+monto;
		System.out.println("Sumatoria :"+monto);
		Assert.assertTrue(monto == uiMainBalance);
		CalculoImpuestos CI = new CalculoImpuestos();
		System.out.println(CI.determinarCategoriaIVA(driver));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E","Ciclo1"}, dataProvider = "RecargaTC")
	public void TS134330_CRM_Movil_REPRO_Recargas_Presencial_TC_Ofcom_Financiacion(String cDNI, String cMonto, String cLinea, String cBanco, String cTarjeta,String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular, String cPromo,String cCuotas) throws AWTException, KeyManagementException, NoSuchAlgorithmException {
		imagen = "TS134330";
		detalles = null;
		detalles = imagen+"-Recarga - DNI:"+cDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		System.out.println("monto "+iMainBalance);
		if(cMonto.length() >= 4) {
			cMonto = cMonto.substring(0, cMonto.length()-1);
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(cLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(cMonto);
		sleep(15000);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(15000);
		String sOrden = cc.obtenerOrden3(driver);
		//driver.switchTo().frame(cambioFrame(driver, By.id("InvoicePreview_nextBtn")));
		driver.findElement(By.xpath("//*[@id=\"InvoicePreview_nextBtn\"]")).click();
		sleep(20000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(20000);
		//driver.switchTo().frame(cambioFrame(driver, By.id("BankingEntity-0")));
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		sleep(5000);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		sleep(5000);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		sleep(5000);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		//String sOrden = cc.obtenerOrden3(driver);
		buscarYClick(driver.findElements(By.id("InvoicePreview_nextBtn")), "equals", "siguiente");
		sleep(20000);
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
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + cDNI +", Monto:"+orden.split("-")[2]);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.PagarTCPorServicio(sOrden);
		sleep(5000);
		if(activarFalsos == true) {
			cCBSM.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		sleep(10000);
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		System.out.println("saldo nuevo "+uMainBalance);
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Integer monto = Integer.parseInt(orden.split("-")[2].replace(".", ""));
		monto = Integer.parseInt(monto.toString().substring(0, monto.toString().length()-1));
		monto = iMainBalance+monto;
		Assert.assertTrue(monto == uiMainBalance);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134338_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_con_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134338";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		//Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIconRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "DDI con Roaming Internacional"));
	}

	
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134355_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_con_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134355";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIconRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		ppt = new PagePerfilTelefonico(driver);
		//Dada la estructura de la funcion es lo mismo porque al dar de baja uno, da de alta el otro para poder continuar
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test(groups = {"Sales", "Nominacion","E2E","Ciclo1"}, dataProvider="DatosSalesNominacionNuevoOfCom") 
	public void TS_85094_CRM_Movil_REPRO_Nominatividad_Cliente_Nuevo_Presencial_DOC_OfCom(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) { 
		imagen = "85094-Nominacion"+sDni;
		detalles = null;
		detalles = imagen +"-Linea: "+sLinea;
		sleep(1000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		SalesBase SB = new SalesBase(driver);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-1")); 	
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")||UnaL.getText().toLowerCase().contains("plan prepago nacional")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		BasePage bp = new BasePage(driver);
		sleep(6000);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		SB.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(32000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		System.out.println("cont="+element.get(0).getText());
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		Assert.assertTrue(a);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaSuspension") 
	public void gestionSuspension(String cDNI,String cLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "gestionSuspension";
		detalles = null;
		detalles = imagen+"- DNI:"+cDNI+" - Linea: "+cLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		cc.irAGestion("suspensiones");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+cLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(10000);
		selectByText(driver.findElement(By.id("State")),cProvincia);
		sleep(10000);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(cCiudad);
		sleep(10000);
		driver.findElement(By.id("Partido")).sendKeys(cPartido);
		sleep(7000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(15000);
		boolean a = false;
		List <WebElement> elem = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : elem) {
			if(x.getText().toLowerCase().contains("tu solicitud est\u00e1 siendo procesada.")) {
				a = true;
			}			
		}
		Assert.assertTrue(a);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Suspensi\u00f3n de Linea");
		sOrders.add("Suspension, orden numero: " + orden + ", DNI: " + cDNI);
		//System.out.println(sOrders);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecarga")
	public void problemaRecargaOnline(String sDNI, String sLinea) {
		imagen= "problemaRecargaOnline";
		detalles = null;
		detalles = imagen+" - DNI:"+sDNI+" - Linea: "+sLinea;
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, (saldo.length()) - 1));
		imagen = "problemaRecargaOnline";
		detalles = null;
		detalles = imagen+"-Problema Con Recargas-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")),"contains", "recarga online");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("RefillDate")).sendKeys("23-07-2018");
		sleep(8000);
		driver.findElement(By.id("RefillAmount")).sendKeys("123");
		sleep(8000);
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
		driver.findElement(By.id("ExistingCase_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains","si");
		WebElement img = driver.findElement(By.id("FileAttach"));
		File directory = new File("Dni.jpg");
		img.sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(8000);
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(8000);
		boolean a = false;
		boolean b = false;
		List <WebElement> conf = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : conf) {
			if(x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, (saldo2.length()) - 1));
		if(saldo1+123000 == saldo3) {
			b = true;
		}
		Assert.assertTrue(b);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles+=" - Orden: "+orden;
		sOrders.add("Recargas, orden numero: " + orden + " con DNI: " + sDNI );
		System.out.println(sOrders);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecarga") //Error al intentar impactar la recarga
	public void poblemaRecargaCredito(String cDNI, String sLinea) {
		imagen = "problemaRecargaCredito";
		detalles = null;
		detalles = imagen+" - DNI: "+cDNI+" - Linea: "+sLinea;
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, (saldo.length()) - 1));
		System.out.println(saldo1);
		imagen = "poblemaRecargaCredito";
		detalles = null;
		detalles = imagen+"-Problema Con Recargas-DNI:"+cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")),"contains", "tarjeta de cr\u00e9dito");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "si");
		driver.findElement(By.id("CreditCardRefillAmount")).sendKeys("123");
		driver.findElement(By.id("CreditCardRefillReceipt")).sendKeys("123");
		driver.findElement(By.id("CreditCardData_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(8000);
		boolean a = false;
		boolean b = false;
		List <WebElement> conf = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : conf) {
			if(x.getText().toLowerCase().contains("recarga realizada con \u00e9xito!")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, (saldo2.length()) - 1));
		System.out.println(saldo3);
		if((saldo1+123000 == saldo3)) {
			b = true;
		}
		Assert.assertTrue(b);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Problemas con Recargas");
		detalles+=" - Orden: "+orden;
		sOrders.add("Recargas, orden numero: " + orden + " con DNI: " + cDNI );
		System.out.println(sOrders);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E","Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS103596_CRM_Movil_REPRO_Ajuste_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS103596";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Datos (Mb)");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + (123 * 1024) == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos","E2E","Ciclo3"}) //No se puede modificar el DNI 2 veces en un mes
	public void GestionActualizacionDatos() {
		imagen = "GestionActualizacionDatos";
		OM om = new OM(driver);
		//boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.cssSelector(".slds-form-element__label--toggleText.ng-binding")).click();
		sleep(3000);
		driver.findElement(By.id("ContactFirstName")).sendKeys("nestor alberto");
		driver.findElement(By.id("ContactLastName")).sendKeys("papa");
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		String nroDNI = driver.findElement(By.id("DocumentNumber")).getAttribute("value");
		String old2 = nroDNI.substring(0, nroDNI.length()-2);
		driver.findElement(By.id("DocumentNumber")).clear();
		String ultimos2 = om.getRandomNumber(2);
		driver.findElement(By.id("DocumentNumber")).sendKeys(old2 + ultimos2);
		String asd = driver.findElement(By.id("DocumentNumber")).getAttribute("value");
		System.out.println(asd);
		/*driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("se realizaron correctamente las modificaciones")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);*/		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ProblemasConRecargas","E2E","Ciclo3"}, dataProvider = "ProblemaRecargaPrepaga")  //Se necesitan nuevos numeros de tarjeta, solo se pueden usar 1 vez
	public void GestionProblemasConRecargasTarjetaPrepaga(String cDNI,String sLinea, String cBatch, String cPin) {
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, 5));
		System.out.println(saldo1);
		imagen = "GestionProblemasConRecargasTarjetaPrepaga";
		detalles = null;
		detalles = imagen+"-Problema Con Recargas-DNI:"+cDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(cBatch);
		driver.findElement(By.id("PIN")).sendKeys(cPin);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		boolean b = false;
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, 5));
		System.out.println(saldo3);
		if((saldo1+500 == saldo3)) {
			b = true;
		}
		Assert.assertTrue(b);
		String orden = cc.obtenerOrden(driver, "Problema con recarga con tarjeta prepaga");
		detalles+=" - Orden: "+orden;
		Assert.assertTrue(cc.verificarOrden(orden));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E","Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void Gestion_Ajustes_Credito_Pospago(String sDNI, String sLinea) {
		imagen = "Gestion_Ajustes_Credito_Pospago";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO POSPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Minutos/SMS");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "cuenta: ");
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nota de d\u00e9bito");
		driver.findElement(By.id("MontoLibre")).sendKeys("123");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Ajuste Minutos");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98438_CRM_Movil_REPRO_Suspension_por_Siniestro_Hurto_Linea_Titular_Presencial(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98438";
		detalles = null;
		detalles = imagen + " - Suspension - DNI: " +cDNI+" - Linea: "+cLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		cc.irAGestion("suspensiones");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+cLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "hurto");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(10000);
		selectByText(driver.findElement(By.id("State")),cProvincia);
		sleep(4000);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(cCiudad);
		sleep(4000);
		driver.findElement(By.id("Partido")).sendKeys(cPartido);
		sleep(4000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(40000);
		boolean a = false;
		List <WebElement> elem = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : elem) {
			if(x.getText().toLowerCase().contains("tu solicitud est\u00e1 siendo procesada.")) {
				a = true;
			}			
		}
		Assert.assertTrue(a);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Suspensi\u00f3n de Linea");
		detalles+= " - Orden: "+orden;
		sOrders.add("Suspension, orden numero: " + orden + " con numero de DNI: " + cDNI);
		System.out.println(sOrders);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
	public void TS98442_CRM_Movil_REPRO_Suspension_por_Siniestro_Extravio_Linea_Titular_Presencial(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
		imagen = "TS98442";
		detalles = null;
		detalles = imagen + " -Suspension - DNI: " + cDNI +" - Linea: "+cLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		cc.irAGestion("suspensiones");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+cLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "extrav\u00edo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(10000);
		selectByText(driver.findElement(By.id("State")),cProvincia);
		sleep(10000);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(cCiudad);
		sleep(10000);
		driver.findElement(By.id("Partido")).sendKeys(cPartido);
		sleep(7000);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
		sleep(15000);
		boolean a = false;
		List <WebElement> elem = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : elem) {
			if(x.getText().toLowerCase().contains("tu solicitud est\u00e1 siendo procesada.")) {
				a = true;
			}			
		}
		Assert.assertTrue(a);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Suspensi\u00f3n de Linea");
		sOrders.add("Suspencion, orden numero: " + orden + " con numero de DNI: " + cDNI);
		System.out.println(sOrders);
	}	
	
	
	
	
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E","Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS112434_CRM_Movil_PRE_Ajuste_Credito_Minutos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112434";
		detalles = null;
		detalles = imagen + " -Ajustes - DNI: " + sDNI;
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		System.out.println(datosInicial);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (10 * 3600) == datosFinal);
		System.out.println(datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E", "Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS112435_CRM_Movil_PRE_Ajuste_Credito_SMS_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112435";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "SMS Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "SMS");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "SMS Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		Assert.assertTrue(datosInicial + 123 == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E", "Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS103599_CRM_Movil_REPRO_Se_crea_caso_de_ajuste_menor_a_500_pesos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS103599";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("49999");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 4999 == datosFinal);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E", "Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS112452_CRM_Movil_PRE_Ajuste_Nota_de_Credito_Derivacion_a_rango_superior_1900_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112452";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta repro");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("200000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles += "-Orden: " + orden;
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E", "Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS135706_CRM_Movil_PRE_Ajuste_Nota_de_Credito_FAN_Front_OOCC_Punta_Alta(String sDNI, String sLinea) {
		imagen = "TS135706";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO POSPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Minutos/SMS");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "cuenta:");
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nota de cr\u00e9dito");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElements(By.className("slds-cell-shrink")).get(0).click();
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E","Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS135707_CRM_Movil_PRE_Ajuste_Nota_de_Debito_FAN_Front_OOCC_Bariloche(String sDNI, String sLinea) {
		imagen = "TS135707";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO POSPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Minutos/SMS");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "cuenta:");
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nota de d\u00e9bito");
		sleep(2000);
		driver.findElement(By.id("MontoLibre")).sendKeys("123");
		selectByText(driver.findElement(By.id("SelectItemAjusteLibre")), "Abono b\u00e1sico movil");
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		String nroCaso = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
		detalles+=" - Caso: "+nroCaso;
		System.out.println(nroCaso);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	
	@Test (groups = {"Rehabilitacion", "GestionesPerfilOficina","E2E", "Ciclo3"}, dataProvider="CuentaHabilitacion")
	public void TS98599_CRM_Movil_REPRO_Rehabilitacion_Administrativo_Fraude_DNI(String cDNI) {
		imagen = "TS98599";
		detalles = null;
		detalles = imagen + " - Rehabilitacion - DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		cc.irAGestion("suspensiones y reconexion back");
		sleep(40000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "nombre: ");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "contains", "continue");
		sleep(15000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(8000);
		String orden = cc.obtenerOrden(driver, "Habilitaci\u00f3n administrativa");
		detalles+=" - Orden: "+orden;
		System.out.println(sOrders);
	}
	
	@Test (groups = {"Habilitacion", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaHabilitacion")
	public void TS98590_CRM_Movil_REPRO_Rehabilitacion_por_Siniestro_Presencial_Robo_Linea(String sDNI, String sLinea) {
		imagen = "TS98590";
		detalles = null;
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(8000);
		cc.irAGestion("suspensiones");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center")), "contains", "validaci\u00f3n por documento de identidad");
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		sleep(8000);
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		driver.findElement(By.id("DocumentMethod_nextBtn")).click();
		sleep(8000);
		//driver.findElement(By.id("ValidationResult_nextBtn")).click();
		//sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains","linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu solicitud est\u00e1 siendo procesada.")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(8000);
		String orden = cc.obtenerOrden(driver, "Reconexi\u00f3n de Linea");
		detalles+=" - Orden: "+orden;
		System.out.println(sOrders);
	}
	
	//@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E"}, dataProvider = "CuentaAjustesPRE")  //Diferido
	public void TS112438_CRM_Movil_PRE_Ajuste_Cargos_aun_no_facturados_FAN_Front_OOCC(String cDNI) {
		imagen = "TS112438";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CARGOS AUN NO FACTURADOS");
		selectByText(driver.findElement(By.id("CboTipo")), "Otros cargos no facturados");
		selectByText(driver.findElement(By.id("CboItem")), "Cargo de reconexi\u00f3n");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS135708_CRM_Movil_REPRO_Ajuste_Credito_Minutos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS135708";
		detalles = null;
		detalles = imagen + " -Ajustes - DNI: " + sDNI;
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		System.out.println(datosInicial);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Segundos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (10 * 3600) == datosFinal);
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div")).findElement(By.tagName("strong")).getText();
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E","Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS129317_CRM_Movil_REPRO_Ajuste_RAV_Unidades_Libres_a_Pesos_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129317";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		WebElement monto = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list)
			if (x.getText().toLowerCase().contains("monto"))
				monto = x;
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 78.00"));		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E", "Ciclo3"},dataProvider = "CuentaAjustesPRE")
	public void TS135705_CRM_Movil_PRE_Ajuste_RAV_Unidades_Libres_a_Pesos_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS135705";
		detalles = null;
		detalles = imagen + " - Ajustes - DNI: " + sDNI;
		WebElement monto = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Voz");
		driver.findElement(By.id("CantidadVoz")).sendKeys("100000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list)
			if (x.getText().toLowerCase().contains("monto")) {
				monto = x;
		}
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 78.00"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E","Ciclo3"},dataProvider = "CuentaAjustesREPRO")
	public void TS129320_CRM_Movil_REPRO_Escalamiento_segun_RAV_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129320";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		boolean gest = false;
		WebElement monto = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("50100");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		WebElement fact = driver.findElement(By.cssSelector(".slds-card.ng-scope")).findElements(By.cssSelector(".slds-card__header.slds-grid")).get(1);
		List <WebElement> list = fact.findElements(By.tagName("li"));
		for (WebElement x : list) {
			if (x.getText().toLowerCase().contains("monto"))
				monto = x;
		}
		Assert.assertTrue(monto.getText().equalsIgnoreCase("Monto: 501"));
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			detalles+=" - Orden: "+orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles += "-Orden: " + orden;
			sOrders.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina","RenovacionDeCuota","E2E","Ciclo1"}, dataProvider="RenovacionCuotaSinSaldo")
	public void TS135396_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_Efectivo_sin_Credito(String sDNI, String sLinea) throws AWTException {
		imagen = "TS135396";
		//Check all
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+"- DNI: "+sDNI+" - Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("factura")){
				UnP.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-orden:"+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		sleep(12000);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(30000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		//Assert.assertTrue(msj.toLowerCase().contains("se ha enviado correctamente la factura a huawei. dirigirse a caja para realizar el pago de la misma"));
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		CBS_Mattu invoSer = new CBS_Mattu();
		Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[2], orden.split("-")[1],driver));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	//Arreglar luego porque no debe ser asi
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS112598_CRM_Movil_PRE_Pago_con_Tarjeta_de_debito_Reintegro_con_Efectivo_1000(String cDNI) {
		imagen = "TS112598";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		Marketing mk = new Marketing(driver);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "solicitud de reintegros");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "tarjeta de d\u00e9bito");
		selectByText(driver.findElement(By.id("selectReason")), "Pago duplicado");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("100000");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("la gesti\u00f3n se deriv\u00f3 correctamente")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Solicitud de Reintegros");
			detalles+=" - Orden: "+orden;
			sOrders.add("Solicitud de Reintegros, orden numero: " + orden + " con numero de DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles+=" - Orden: "+orden;
			orden = orden.substring(orden.lastIndexOf(" ")+1, orden.lastIndexOf("."));
			sOrders.add("Solicitud de Reintegros, numero de orden: " + orden + " de cuenta con DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Solicitud de Reintegros"));
		}
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecargaAYD") //Lote: 11120000001688 PIN: 02222
	public void TS135714_CRM_Movil_PRE_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible(String cDNI,String sLinea, String cSerie, String cPIN){
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, 5));
		System.out.println(saldo1);
		imagen = "TS135714";
		detalles = null;
		detalles = imagen + " -Problema con recargas - DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009352");
		driver.findElement(By.id("PIN")).sendKeys("0589");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(15000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		boolean b = false;
		boolean a = false;
		List <WebElement> prob = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prob) {
			if(x.getText().toLowerCase().contains("recarga realizada con \u00e9xito")) {
				b = true;
			}
		}
		Assert.assertTrue(b);
		String saldo2 = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo3 = Integer.parseInt(saldo2.substring(0, 5));
		if(saldo1+500 == saldo3) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecargaQuemada")
	public void TS104347_CRM_Movil_REPRO_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Quemada(String cDNI,String sLinea, String cSerie, String cPIN){
		CBS_Mattu verifM = new CBS_Mattu();
		CBS verif = new CBS();
		String saldo = verif.ObtenerValorResponse(verifM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer saldo1 = Integer.parseInt(saldo.substring(0, 5));
		System.out.println(saldo1);
		imagen = "TS104347";
		detalles = null;
		detalles = imagen + " -Problema con recargas - DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")), "contains", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("BatchNumber")).sendKeys(cSerie);
		driver.findElement(By.id("PIN")).sendKeys(cPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		boolean b = false;
		for(WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if(x.getText().toLowerCase().contains("la tarjeta ya fue utilizada para una recarga")) {
				b = true;
			}
		}
		Assert.assertTrue(b);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Reintegros", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS112597_CRM_Movil_PRE_Pago_con_Tarjeta_de_debito_Reintegro_con_Efectivo_Menos_de_1000(String cDNI) {
		imagen = "TS112597";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + cDNI;
		Marketing mk = new Marketing(driver);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "solicitud de reintegros");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "tarjeta de d\u00e9bito");
		selectByText(driver.findElement(By.id("selectReason")), "Pago duplicado");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys("90000");
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("la gesti\u00f3n se deriv\u00f3 correctamente")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Solicitud de Reintegros");
			detalles+=" - Orden: "+orden;
			sOrders.add("Solicitud de Reintegros, orden numero: " + orden + " con numero de DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			detalles+=" - Orden: "+orden;
			orden = orden.substring(orden.lastIndexOf(" ")+1, orden.lastIndexOf("."));
			sOrders.add("Solicitud de Reintegros, numero de orden: " + orden + " de cuenta con DNI: " + cDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Solicitud de Reintegros"));
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "TriviasYSuscripciones", "E2E","Ciclo3"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS119032_CRM_Movil_REPRO_Suscripciones_Baja_de_suscripciones_sin_BlackList_Presencial(String cDNI,String cLinea) {
		imagen = "TS119032";
		detalles = null;
		detalles = imagen +" -Trivias y suscripciones - DNI: " +cDNI;
		boolean gest = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_RealizarAltaSuscripcion(cLinea,"178");
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.seleccionarCardPornumeroLinea(cLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty")).findElement(By.className("slds-checkbox")).click();
		driver.findElements(By.cssSelector(".slds-button.slds-button--brand")).get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionOptions_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		} catch (Exception e) {}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "TriviasYSuscripciones", "E2E","Ciclo3"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS110893_CRM_Movil_REPRO_Suscripciones_Baja_de_una_suscripcion_con_BlackList_con_ajuste_Presencial(String sDNI, String sLinea) {
		imagen="TS110893";
		detalles = null;
		detalles = imagen + "- Trivias y suscripciones - DNI:" + sDNI;
		boolean gest = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_RealizarAltaSuscripcion(sLinea, "178");
		WebElement blackList = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty")).findElement(By.className("slds-checkbox")).click();
		driver.findElements(By.cssSelector(".slds-button.slds-button--brand")).get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionOptions_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		List <WebElement> bl = driver.findElements(By.className("slds-form-element__control"));
		for (WebElement x : bl) {
			if (x.getText().toLowerCase().contains("quer\u00e9s agregar al cliente a la blacklist")) {
				blackList = x;
			}
		}
		buscarYClick(blackList.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		} catch (Exception e) {}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		sleep(10000);
		List <WebElement> msj = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "TriviasYSuscripciones", "E2E","Ciclo3"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS110877_CRM_Movil_REPRO_Suscripciones_Baja_de_una_suscripcion_sin_BlackList_con_ajuste_Presencial(String sDNI, String sLinea) {
		imagen = "TS110877";
		detalles = null;
		detalles = imagen + "- Trivias y suscripciones - DNI: "+sDNI;
		boolean gest = false;
		CBS_Mattu cCBSM = new CBS_Mattu();		
		WebElement blackList = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".addedValueServices-row.ng-pristine.ng-untouched.ng-valid.ng-empty")).findElement(By.className("slds-checkbox")).click();
		driver.findElements(By.cssSelector(".slds-button.slds-button--brand")).get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionOptions_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nunca me suscrib\u00ed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		List <WebElement> bl = driver.findElements(By.className("slds-form-element__control"));
		for (WebElement x : bl) {
			if (x.getText().toLowerCase().contains("quer\u00e9s agregar al cliente a la blacklist")) {
				blackList = x;
			}
		}
		buscarYClick(blackList.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("UnsubscriptionOptions_nextBtn")).click();
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
			driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")).get(1).click();
		} catch (Exception e) {}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.switchTo().frame(cambioFrame(driver, By.id("UnsubscriptionSummary_nextBtn")));
		driver.findElement(By.id("UnsubscriptionSummary_nextBtn")).click();
		sleep(10000);
		cCBSM.Servicio_RealizarAltaSuscripcion(sLinea, "178");
		List <WebElement> msj = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : msj) {
			if (x.getText().toLowerCase().contains("tu caso se resolvi\u00f3 con \u00e9xito")) {
				gest = true;
			}
		}
		Assert.assertTrue(gest);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E"}, dataProvider = "RecargaTD")
	public void TS134320_CRM_Movil_REPRO_Recargas_Presencial_TD_Ofcom(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws AWTException {
		if(sMonto.length() >= 4) {
			sMonto = sMonto.substring(0, sMonto.length()-1);
		}
		imagen= "TS134320";
		detalles = null;
		detalles = imagen+"-Recarga-DNI:"+sDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		String sAccid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+ sAccid);
		detalles +="-Cuenta:"+sAccid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		sleep(15000);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(15000);
		CustomerCare cCC = new CustomerCare(driver);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles +="-Orden:"+sOrden;
		driver.findElement(By.xpath("//*[@id=\"InvoicePreview_nextBtn\"]")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de debito");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BankingEntity-0")));
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		sleep(5000);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		sleep(5000);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		buscarYClick(driver.findElements(By.id("InvoicePreview_nextBtn")), "equals", "siguiente");
		sleep(20000);
		List <WebElement> wExis = driver.findElements(By.id("GeneralMessageDesing"));
		boolean bAssert = false;
		for(WebElement wAux : wExis) {
			if(wAux.getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito")) {
				bAssert = true;
			}
			Assert.assertTrue(bAssert);
		}
		String orden = cCC.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = " + orden);
		detalles+="-Monto:"+orden.split("-")[2]+"-Prefactura:"+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+ sAccid +", DNI: " + sDNI + ", Monto:" + sOrden.split("-")[2]);
		CBS_Mattu cInvoSer = new CBS_Mattu();
		Assert.assertTrue(cInvoSer.PagoEnCaja("1006", sAccid , "4001", sOrden.split("-")[2], sOrden.split("-")[1],driver));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		System.out.println("saldo nuevo "+uMainBalance);
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Integer monto = Integer.parseInt(orden.split("-")[2].replace(".", ""));
		monto = Integer.parseInt(monto.toString().substring(0, monto.toString().length()-2));
		Assert.assertTrue(iMainBalance+monto == uiMainBalance);
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	
	
	@Test (groups = {"GestionesPerfilOficina","VentaDePacks","E2E","Ciclo1"}, dataProvider="PackOfCom")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_TC_OffCom(String sDNI, String sLinea, String sPackOfCom, String cBanco, String cTarjeta, String cPromo, String cCuotas) throws AWTException, KeyManagementException, NoSuchAlgorithmException{
		imagen = "Venta De Pack Oficina";
		detalles = null;
		detalles = imagen + "- DNI: "+sDNI+" - Linea: "+sLinea;
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack();
		pagePTelefo.closerightpanel();
		sleep(4000);
		pagePTelefo.agregarPack(sPackOfCom);		
		pagePTelefo.tipoDePago("en factura de venta");
		pagePTelefo.getTipodepago().click();
		sleep(12000);
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(12000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+=" -Orden: "+sOrden;
		pagePTelefo.getMediodePago().click();
		sleep(15000);
		pagePTelefo.getOrdenSeRealizoConExito().click();
		sleep(15000);
		driver.navigate().refresh();
		sleep(10000);
		String orden = cCC.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+="-Monto:"+orden.split("-")[2]+"-Prefactura:"+orden.split("-")[1];
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.PagarTCPorServicio(sOrden);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, sOrden);
		System.out.println(invoice);
		sleep(10000);
		if(activarFalsos == true) {
			cCBSM.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		sleep(10000);
		CBS cCBS = new CBS();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sPackOfCom));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
		// ================ ESTE VA  1 ============
	@Test(groups = { "GestionesPerfilOficina", "CambioSimCard","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TSCambioSimCardOficina(String sDNI, String sLinea) throws AWTException {
		imagen = "TSCambioSimCardOficina";
		detalles = null;
		detalles = imagen + "- DNI: " + sDNI+ "- Linea: "+sLinea;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("Cambio SimCard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(16000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		System.out.println("Orden " + orden);
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
			invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
			
		}
		CambiarPerfil("logisticayentrega",driver);
		sb.completarLogistica(orden, driver);
		//CambiarPerfil("entrega",driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			//sleep(3000);
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		
		driver.switchTo().defaultContent();
		sleep(6000);
		cCC.obtenerMontoyTNparaAlta(driver, orden);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
		
	@Test (groups = {"GestionesPerfilOficina","RenovacionDeCuota","E2E", "Ciclo1"}, dataProvider="RenovacionCuotaSinSaldoConTC")
	public void TS135397_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_TC_sin_Credito(String sDNI, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws AWTException, KeyManagementException, NoSuchAlgorithmException {
		imagen = "TS135397";
		//Check all
		detalles = "Renocavion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles += "-Cuenta: "+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("factura")){
				UnP.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(15000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BankingEntity-0")));
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		sleep(5000);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		sleep(5000);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		sleep(5000);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(25000);
		String msj = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText(); 
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(msj.toLowerCase().contains("se ha enviado correctamente la factura a huawei. dirigirse a caja para realizar el pago de la misma"));
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(8000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		cCBSM.PagarTCPorServicio(sOrden);
		sleep(5000);
		if(activarFalsos == true) {
			cCBSM.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		sleep(10000);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "AnulacionDeVenta", "E2E","Ciclo4"}, dataProvider = "CuentaAnulacionDeVenta")
	public void Anulacion_De_Venta(String sDNI) {
		imagen = "Anulacion_De_Venta";
		detalles = null;
		detalles = imagen + " - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("anulaci\u00f3n de ordenes");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral")), "equals", "anulaci\u00f3n de venta");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("AnnulmentReasonSelect")));
		selectByText(driver.findElement(By.id("AnnulmentReasonSelect")), "Arrepentimiento");
		driver.findElement(By.id("AnnulmentReason_nextBtn")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table")));
		String gestion = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table")).findElements(By.tagName("tr")).get(4).getText();
		Assert.assertTrue(gestion.contains("Estado") && (gestion.contains("Cancelada") || gestion.contains("Cancelled")));
	}
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E","Ciclo1"}, dataProvider="NumerosAmigosLetras")
	public void TXGPO0001_CRM_Movil_REPRO_FF_Alta_Presencial_Ingreso_Letras(String sDNI, String sLinea) {
		imagen = "TXGPO0001";
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys("A");
		wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys("B");
		wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Assert.assertFalse(wNumerosAmigos.get(0).findElement(By.tagName("input")).getText().equals("A"));
		Assert.assertFalse(wNumerosAmigos.get(1).findElement(By.tagName("input")).getText().equals("B"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS135346_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_TODOS_Fan_FRONT_OOCC(String sDNI) {
		imagen = "TS135346";
		detalles = null;
		detalles = imagen + " - Historial de recargas - DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAHistoriales();
		WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de recargas"))
				historialDeRecargas = x;
		}
		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed()) {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS_134373_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_OOCC(String sDNI){
		imagen ="TS134373";
		detalles = null;
		detalles = imagen + "- Consulta de Saldo - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement cred = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[2]/div[1]/ng-include/section[1]/div[2]/ul[2]/li[1]/span[3]"));
		Assert.assertTrue(!(cred.getText().isEmpty()));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS_134376_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_OOCC(String sDNI) {
		imagen="TS134376";
		detalles = null;
		detalles = imagen + "- Consulta de Saldo - DNI:" +sDNI;
		Marketing mk = new Marketing(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.openleftpanel();
		mk.closeActiveTab();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		boolean a = false;
		List <WebElement> saldo = driver.findElements(By.className("header-right"));
		for(WebElement x : saldo) {
			if(x.getText().toLowerCase().contains("balance")) {
				a = true;
			}
			Assert.assertTrue(a);
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS134783_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_FAN_Front_OOCC_134783(String sDNI, String sLinea) {
		CustomerCare cCC = new CustomerCare(driver);
		imagen = "TS134783";
		detalles = null;
		detalles = imagen + "- Detalle de Consumo - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cCC.irADetalleDeConsumos();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea)){
				s.click();
			}	
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.className("summary-container")));
		WebElement ConsumoDatos =  driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(0);;
		System.out.println(ConsumoDatos.getText());
		Assert.assertTrue(ConsumoDatos.isDisplayed());
		}
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS134782_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_OOCC_134782(String sDNI, String sLinea) {
		imagen = "TS134782";
		detalles = null;
		detalles = imagen + "- Detalle de Consumos - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "detalles de consumo");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea)){
				s.click();
			}	
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		sleep(5000);
		Boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.className("unit-div")));
		List <WebElement> msg = driver.findElements(By.className("unit-div"));
			for(WebElement m : msg){
				System.out.println(m.getText());
				if(m.getText().toLowerCase().contains("mensajes")){
				a=true;
				}
			}
		Assert.assertTrue(a);
		}
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS134784_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134784";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-01")));
		driver.findElements(By.id("text-input-01")).get(0).click();
		try{
			driver.findElement(By.id("listbox-option-248")).click();
			}
		catch (Exception e){}
		Boolean a = false;
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals","consultar");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.className("unit-div")));
		List <WebElement> voz = driver.findElements(By.className("unit-div"));
			for(WebElement v : voz){
				System.out.println(v.getText());
				if(v.getText().toLowerCase().contains("minutos")){
				a=true;
				}
			}
		Assert.assertTrue(a);
		}
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS134785_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_Otros_consumos_FAN_Front_OOCC_134785(String sDNI, String sLinea) {
		CustomerCare cCC = new CustomerCare(driver);
		imagen = "TS134785";
		detalles = null;
		detalles = imagen + "- Detalles de Consumos -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cCC.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea)){
				s.click();
			}	
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		sleep(5000);
		Boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.className("unit-div")));
		List <WebElement> otros = driver.findElements(By.className("unit-div"));
			for(WebElement o : otros){
				System.out.println(o.getText());
				if(o.getText().toLowerCase().contains("otros")){
				a=true;
				}
			}
		Assert.assertTrue(a);
		
	}
	
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134470_CRM_Movil_Prepago_Historial_De_Recargas_SOS_S440_FAN_Front_OOCC(String cDNI, String cLinea) {
		imagen = "TS134470";
		detalles = null;
		detalles = imagen + "-Historial de recargas - DNI: "+cDNI;
		boolean enc = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		for(int i=0;i<=2;i++) {
			cCBSM.Servicio_Recharge(cLinea,"25000000");
			sleep(1000);
		}
		cCBSM.Servicio_Loan(cLinea,"15000000");
		sleep(1000);
		cCBSM.Servicio_Recharge(cLinea,"25000000");
		sleep(1000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		CustomerCare cc = new CustomerCare(driver);
		cc.irAHistoriales();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds")));
		List <WebElement> historiales = driver.findElements(By.cssSelector(".slds-m-around_small.ta-fan-slds"));
		for (WebElement UnH: historiales) {
			System.out.println(UnH.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText());
			if(UnH.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText().equals("Historial de recargas S.O.S")) {
				enc = true;
				driver.findElements(By.cssSelector(".slds-button.slds-button_brand")).get(1).click();
				sleep(5000);
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-around--medium")));
				driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
				sleep(5000);
				Assert.assertTrue(true);
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "RecargasHistorias")
	public void TS134473_CRM_Movil_Prepago_Historial_De_Packs_Fan_Front_OOCC(String cDNI) {
		imagen = "TS134473";
		detalles = null;
		detalles = imagen + "- Historial de packs - DNI: "+cDNI;
		boolean enc = false;
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.irAHistoriales();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds")));
		List <WebElement> historiales = driver.findElements(By.className("slds-card"));
		for (WebElement UnH: historiales) {
			System.out.println(UnH.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText());
			if(UnH.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText().equals("Historial de packs")) {
				enc = true;
				driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
				sleep(5000);
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
				driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
				sleep(5000);
				Assert.assertTrue(true);
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	

	@Test(groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion")
	public void TS135496_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail ){
		imagen = "TS135496";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> solapas = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(solapas.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test(groups={"Sales","GestionDeClientes", "Ciclo1"}, dataProvider = "validaDocumentacion")
	public void TS135503_CRM_Movil_REPRO_Busqueda_Apellido(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135503";
		detalles = null;
		detalles = imagen + "- Gestion de Clientes - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("",sApellido,"","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> solapas = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(solapas.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test(groups={"Sales","GestionDeClientes", "Ciclo1"}, dataProvider = "validaDocumentacion")
	public void TS135509_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135509";
		detalles = null;
		detalles = imagen + "- Gestion de Clientes - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> solapas = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(solapas.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosSalesNominacionExistenteOfCom")
	public void TS85097_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_DOC_OfCom(String sLinea, String sDni) {
		imagen = "TS85097";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		sleep(2000);
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, "Masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(30000);
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ReseteoDeClave", "Ciclo2"}, dataProvider = "CuentaReseteoClave")
	public void TS95980_CRM_Movil_REPRO_Reseteo_de_Clave_Presencial(String sDNI) {
		imagen = "TS95980";
		detalles = null;
		detalles = imagen + "-ReseteoDeClave - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		buscarYClick(driver.findElements(By.className("profile-edit")),"contains", "reseteo clave");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step 1_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step 1_nextBtn")).click();
		sleep(5000);
		WebElement msj = driver.findElement(By.className("ta-care-omniscript-done"));
		Assert.assertTrue(msj.getText().contains("Su n\u00famero de confirmaci\u00f3n es: "));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ReseteoDeClave", "Ciclo2"}, dataProvider = "CuentaReseteoClave")
	public void TS95982_CRM_Movil_REPRO_No_Reseteo_de_Clave_Presencial(String sDNI) {
		imagen = "TS95982";
		detalles = null;
		detalles = imagen + "-ReseteoDeClave - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "30998801");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		buscarYClick(driver.findElements(By.className("profile-edit")),"contains", "reseteo clave");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step 1_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("Step 1_nextBtn")).click();
		sleep(5000);
		WebElement msj = driver.findElement(By.className("ta-care-omniscript-done"));
		Assert.assertTrue(msj.getText().contains("Su n\u00famero de confirmaci\u00f3n es: "));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS130056_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Efectivo_con_Credito(String sDNI, String sLinea) throws AWTException {
		imagen="TS130056";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDNI+" - Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles+="-Cuenta: "+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("200 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}sleep(2000);
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("factura")){
				UnP.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		sleep(8000);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		//String msj = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText(); 
		//String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		//Assert.assertTrue(msj.toLowerCase().contains("se ha enviado correctamente la factura a huawei. dirigirse a caja para realizar el pago de la misma"));
		//Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(8000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDNI +", Monto:"+orden.split("-")[2]);
		CBS_Mattu invoSer = new CBS_Mattu();
		Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[2], orden.split("-")[1],driver));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println("Datos Inicial "+datosInicial);
		System.out.println("Datos final "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS130069_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea) {
		imagen = "TS130069";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDNI+" - Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		System.out.println("datos inicial:"+datosInicial);
		System.out.println("Saldo Inicial:"+iMainBalance);
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles += "-Cuenta: "+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("200 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}sleep(2000);
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("saldo")){
				UnP.click();
				break;
			}
		}		
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(18000);
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		System.out.println("Saldo final:"+uiMainBalance);
		Assert.assertTrue(iMainBalance > uiMainBalance);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println("Datos final:"+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		
	}
		
	@Test (groups = {"GestionesPerfilOficina","RenovacionDeCuota","E2E", "Ciclo1"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS135395_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_Efectivo_con_Credito(String sDNI, String sLinea) throws AWTException {
		imagen = "TS135395";
		//Check all
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDNI+" - Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}sleep(2000);
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("factura")){
				UnP.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(15000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(18000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		sleep(8000);
		cCC.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
		sleep(30000);
		String msj = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText(); 
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(msj.toLowerCase().contains("se ha enviado correctamente la factura a huawei. dirigirse a caja para realizar el pago de la misma"));
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(12000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDNI +", Monto:"+orden.split("-")[2]);
		CBS_Mattu invoSer = new CBS_Mattu();
		Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[2], orden.split("-")[1],driver));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135497_CRM_Movil_REPRO_Busqueda_DNI_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135497";
		detalles = null;
		detalles = imagen+"-Gestion de clientes - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion")
	public void TS135499_CRM_Movil_REPRO_Busqueda_Libreta_de_enrolamiento_Numero_de_Documento(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail){
		imagen = "TS135499";
		detalles = null;
		detalles = imagen+"- Gestion de clientes - Libreta de enrolamiento: "+sLibreta;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135500_CRM_Movil_REPRO_Busqueda_Libreta_dE_enrolamiento_Numero_de_Documento_no_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135500";
		detalles = null;
		detalles = imagen + "- Gestion de clientes - Libreta de enrolamiento: " + sLibreta;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("Libreta de Enrolamiento", sLibreta);
		boolean libreta = false;
		for (WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if (x.getText().toLowerCase().contains("no hay ning\u00fan cliente con este tipo y n\u00famero de documento."))
				libreta = true;
		}
		Assert.assertTrue(libreta);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion")
	public void TS135501_CRM_Movil_REPRO_Busqueda_Nombre(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135501";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada(sNombre,"","","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135502_CRM_Movil_REPRO_Busqueda_Nombre_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135502";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada(sNombre,"","","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135504_CRM_Movil_REPRO_Busqueda_Apellido_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135504";
		detalles = null;
		detalles = imagen+"- Gestion de clinetes - Apellido: "+sApellido;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("",sApellido,"","","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion")
	public void TS135505_CRM_Movil_REPRO_Busqueda_Razon_Social(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135505";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Razon social: "+sRazon;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","",sRazon,"","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135506_CRM_Movil_REPRO_Busqueda_Razon_social_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135506";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Razon social: "+sRazon;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","",sRazon,"","");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> vacio = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(vacio.get(0).findElement(By.tagName("a")).getText().isEmpty());
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "validaDocumentacion")
	public void TS135507_CRM_Movil_REPRO_Busqueda_Email(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135507";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Email: "+sEmail;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","","","",sEmail);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> activo = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		assertTrue(activo.get(0).findElement(By.tagName("a")).getText().equals("Clientes Activos"));
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135508_CRM_Movil_REPRO_Busqueda_Email_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135508";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Email: "+sEmail;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("","","","",sEmail);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("slds-form-element__control"))){
			if(x.getText().toLowerCase().equals("no hay ning\u00fan cliente con este tipo y n\u00famero de documento. busc\u00e1 con otro dato o cre\u00e1 un nuevo cliente.")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"},dataProvider = "invalidaDocumentacion")
	public void TS135510_CRM_Movil_REPRO_Busqueda_Numero_de_Cuenta_No_existente(String sDNI, String sNumeroDeCuenta, String sNombre, String sApellido, String sLibreta, String sRazon, String sEmail) {
		imagen = "TS135510";
		detalles = null;
		detalles = imagen+" - Gestion de clientes - Numero de cuenta: "+sNumeroDeCuenta;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarAvanzada("", "", "", sNumeroDeCuenta, "");
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> vacio = driver.findElement(By.className("slds-tabs--scoped__nav")).findElements(By.tagName("li"));
		Assert.assertTrue(vacio.get(0).findElement(By.tagName("a")).getText().isEmpty());
	}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"})
	public void TS135495_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_DNI() {
		imagen = "TS135495";
		detalles = null;
		detalles = imagen+" - Gestion de clientes";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "DNI");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean a = false;
			if(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("dni")) {
				a = true;
			}
			Assert.assertTrue(a);
		}
	
	@Test (groups={"Sales","GestionDeClientes", "Ciclo1"})
	public void TS135498_CRM_Movil_REPRO_Busqueda_Tipo_de_documento_Libreta_de_enrolamiento() {
		imagen = "TS135498";
		detalles = null;
		detalles = imagen+" - Gestion de clientes";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		selectByText(driver.findElement(By.id("SearchClientDocumentType")), "Libreta de Enrolamiento");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean a = false;
			if(driver.findElement(By.id("SearchClientDocumentType")).getText().toLowerCase().contains("libreta de enrolamiento")) {
				a = true;
			}
			Assert.assertTrue(a);
	}
	
	@Test (groups= {"GestionPerfilOficina", "Ciclo2", "Vista360"}, dataProvider = "documentacionVista360")
	public void TS_134379_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_OOCC(String sDNI) {
		imagen = "TS134379";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		List <WebElement> servicios = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement s : servicios) {
			if (s.getText().toLowerCase().contains("activo")) {
				System.out.println(s.getText());
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"},dataProvider = "DatosSalesNominacionNuevoPasaporteOfCom")
	public void TS128436_CRM_Movil_REPRO_Nominatividad_Presencial_DOC_Pasaporte_OfCom(String sLinea, String sPasaporte, String sNombre, String sApellido, String sSexo, String sPermanencia, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) {
		imagen = "TS128436";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sPasaporte + " -Linea: "+sLinea;
		boolean nominacion = false;
		SalesBase SB = new SalesBase(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		sleep(2000);
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("Pasaporte", sPasaporte, "Masculino");
		contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys(sPermanencia);
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(7000);
		BasePage bp = new BasePage(driver);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		SB.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(38000);
		directory = new File("form.pdf");
		driver.findElement(By.id("UploadSignedForm")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(2000);
		driver.findElement(By.id("PDFForm_nextBtn")).click();
		sleep(30000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominaNuevoEdadOfCom")
	public void TS130071_CRM_Movil_REPRO_No_Nominatividad_Presencial_DOC_Edad_OfCom(String sLinea, String sDni, String sSexo,String sFnac) {
		imagen = "TS130071";
		detalles = null;
		detalles = imagen + "No nominatividad- dni: "+sDni+" -Linea: "+sLinea;
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		sleep(2000);
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		driver.findElement(By.id("Birthdate")).sendKeys(sFnac);
		sleep(3000);
		WebElement error = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		Assert.assertTrue(error.getText().toLowerCase().contains("fecha de nacimiento inv\u00e1lida"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360Version2")
	public void TS134495_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil, String sNps, String sTelAlternativo, String sPuntosClub, String sCategoria) {
		imagen = "TS134495";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		//Post: Se comparan los elementos de la primera tabla(Datos del Cliente) que se visualiza con los datos del dataProvider
		WebElement nombre = driver.findElement(By.cssSelector(".slds-text-heading_large.title-card"));
		WebElement nps = driver.findElement(By.className("detail-card")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2);
		WebElement acceso = driver.findElement(By.className("profile-links-wrapper"));
		WebElement dni = null;
		//System.out.println(nombre.getText()); System.out.println(nps.getText()); System.out.println(acceso.getText());
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.account-detail-content"))) {
			if (x.getText().toLowerCase().contains(sDNI))
				dni = x;
		}
		Assert.assertTrue(nombre.getText().contains(sNombre) && dni.getText().contains(sDNI) && acceso.isDisplayed() && (nps.getText().contains(sNps) || nps.isDisplayed()));
		//Post: Se comparan los elementos de la segunda tabla que se visuliza (Datos Personales) con los datos del dataProvider
		WebElement mail = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
		WebElement movil = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1);
		WebElement telefonoAlternativo = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2);
		WebElement puntosClubPersonal = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(3);
		WebElement categoria = driver.findElements(By.className("left-sidebar-section-container")).get(0).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(4);
		//Imprimir los elemetos (Descomentar si es necesario para verificar que traiga los elemetos)
		//System.out.println(mail.getText()); System.out.println(movil.getText()); System.out.println(telefonoAlternativo.getText()); System.out.println(puntosClubPersonal.getText()); System.out.println(categoria.getText());
		Assert.assertTrue(mail.getText().contains(sEmail) && (movil.getText().contains(sMovil) || movil.isDisplayed()) && (telefonoAlternativo.getText().contains(sTelAlternativo) || telefonoAlternativo.isDisplayed()));
		Assert.assertTrue(puntosClubPersonal.getText().contains(sPuntosClub) || puntosClubPersonal.isDisplayed());
		Assert.assertTrue(categoria.getText().contains(sCategoria) || categoria.isDisplayed());
		//Post: Se comparan los elementos de la tercera tabla que se visualiza (segmentacion) con los datos del dataProvider
		WebElement segmentoYAtriburo = driver.findElements(By.className("left-sidebar-section-container")).get(1).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("td"));
		//System.out.println(segmentoYAtriburo.getText());
		Assert.assertTrue(segmentoYAtriburo.getText().contains("Alta reciente - Masivo"));
		
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134745_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134745";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
		boolean detalles = false, historiales = false, misServicios = false, gestiones = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement plan = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(0);
		WebElement FechaActivacion = driver.findElement(By.cssSelector(".slds-text-body_regular.expired-title"));
		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
		System.out.println(plan.getText()); System.out.println(FechaActivacion.getText()); System.out.println(linea.getText());
		assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
				creditoRecarga = true;
			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
				creditoPromocional = true;
			if (x.getText().toLowerCase().contains("estado"))
				estado = true;
			if (x.getText().toLowerCase().contains("internet disponible"))
				internetDisponible = true;
		}
		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
			if(y.getText().toLowerCase().contains("detalles de consumo"))
				detalles = true;
			if(y.getText().toLowerCase().contains("historiales"))
				historiales = true;
			if(y.getText().toLowerCase().contains("mis servicios"))
				misServicios = true;
			if(y.getText().toLowerCase().contains("gestiones"))
				gestiones = true;
		}
		Assert.assertTrue(detalles && historiales && misServicios && gestiones);
	}
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", "Ciclo1"}, dataProvider="CuentaVista360")
	public void TS134380_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_OOCC(String sDNI,String sNombre, String sLinea){
		imagen = "TS134380";
		//Check all
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI;
		CustomerCare cCC = new CustomerCare(driver);
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement wTabla = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> lTabla = wTabla.findElements(By.tagName("tr"));
 		for(WebElement x : lTabla) {
 			System.out.println(x.getText());
 		}
		Assert.assertTrue(wTabla.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134496_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Perfil_FAN_Front_OOCC(String sDNI,String sLinea, String sNombre) {
		imagen = "TS134496";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
		assertTrue(linea.getText().contains(sLinea));
//		driver.switchTo().defaultContent();
//		List<WebElement> pestanas = driver.findElements(By.className("x-tab-strip-closable"));
//		pestanas.addAll(driver.findElements(By.cssSelector(".x-tab-strip-closable.x-tab-strip-active")));
//		for (WebElement x : pestanas) {
//			System.out.println(x.getText());
//			if (x.getText().toLowerCase().contains(sNombre))
//				cuenta = true;
//			break;
//		}
//		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
//		Assert.assertTrue(driver.findElement(By.cssSelector(".console-card.active.expired")).isDisplayed());
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS134834_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_los_datos_del_cliente_completos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134834";
		detalles = null;
		detalles = imagen + " -ActualizarDatos - DNI: " + sDNI+ " - Linea: "+sLinea;
		String nuevoNombre = "Otro";
		String nuevoApellido = "Apellido";
		String nuevoNacimiento = "10/10/1982";
		String nuevoMail = "maildetest@gmail.com";
		String nuevoPhone = "3574409239";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		String nombre = driver.findElement(By.id("FirstName")).getAttribute("value");
		String apellido = driver.findElement(By.id("LastName")).getAttribute("value");
		String fechaNacimiento = driver.findElement(By.id("Birthdate")).getAttribute("value");
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		String phone = driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nuevoNombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(nuevoApellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(nuevoNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(nuevoPhone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.className("ng-binding")).getText().equalsIgnoreCase("Las modificaciones se realizaron con \u00e9xito!"));
		String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		orden = orden.substring(orden.length()-9, orden.length()-1);
		detalles +="-Orden:"+orden;	
		mk.closeActiveTab();
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:Email").equals(nuevoMail));
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:FirstName").equalsIgnoreCase(nuevoNombre));
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:LastName").equalsIgnoreCase(nuevoApellido));
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:Birthday").contains("19821010"));
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("FirstName")).getAttribute("value").equals(nuevoNombre));
		Assert.assertTrue(driver.findElement(By.id("LastName")).getAttribute("value").equals(nuevoApellido));
		Assert.assertTrue(driver.findElement(By.id("Birthdate")).getAttribute("value").equals(nuevoNacimiento));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		Assert.assertTrue(driver.findElement(By.id("MobilePhone")).getAttribute("value").equals(nuevoPhone));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(apellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(fechaNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(phone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(7000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS129335_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_campo_Correo_Electronico_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129335";
		detalles = null;
		detalles = imagen + " -ActualizarDatos - DNI: "+sDNI+" - Linea: "+sLinea;
		String nuevoMail = "maildetest@gmail.com";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.className("ng-binding")).getText().equalsIgnoreCase("Las modificaciones se realizaron con \u00e9xito!"));
		String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		orden = orden.substring(orden.length()-9, orden.length()-1);
		detalles +="-Orden:"+orden;	
		mk.closeActiveTab();
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:Email").equals(nuevoMail));
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(7000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS129329_CRM_Movil_REPRO_Modificacion_de_datos_No_Permite_Actualizar_datos_campo_DNI_CUIT_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129329";
		detalles = null;
		detalles = imagen+"- Modificacion de datos No modifica - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS103660_CRM_Movil_REPRO_No_Actualizar_datos_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS103660";
		detalles = null;
		detalles = imagen+"-Modificacion de datos No modifica - DNI: "+sDNI;
		boolean cancelar = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		if (driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).isDisplayed()) {
			driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
			sleep(3000);
			driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.ng-binding")).click();
			cancelar = true;
		}
		Assert.assertTrue(cancelar);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS103659_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS103659";
		detalles = null;
		detalles = imagen + " -ActualizarDatos - DNI: " + sDNI;
		String nuevoMail = "maildetest@gmail.com";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.className("ng-binding")).getText().equalsIgnoreCase("Las modificaciones se realizaron con \u00e9xito!"));
		String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		orden = orden.substring(orden.length()-9, orden.length()-1);
		detalles +="-Orden:"+orden;	
		mk.closeActiveTab();
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:Email").equals(nuevoMail));
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(7000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS84237_CRM_Movil_REPRO_Modificacion_de_datos_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS84237";
		detalles = null;
		detalles = imagen + " -ActualizarDatos - DNI: " + sDNI;
		String nuevoNombre = "Otro";
		String nuevoApellido = "Apellido";
		String nuevoNacimiento = "10/10/1982";
		String nuevoMail = "maildetest@gmail.com";
		String nuevoPhone = "3574409239";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		String nombre = driver.findElement(By.id("FirstName")).getAttribute("value");
		String apellido = driver.findElement(By.id("LastName")).getAttribute("value");
		String fechaNacimiento = driver.findElement(By.id("Birthdate")).getAttribute("value");
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		String phone = driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nuevoNombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(nuevoApellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(nuevoNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(nuevoPhone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.className("ng-binding")).getText().equalsIgnoreCase("Las modificaciones se realizaron con \u00e9xito!"));
		String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		orden = orden.substring(orden.length()-9, orden.length()-1);
		detalles +="-Orden:"+orden;	
		mk.closeActiveTab();
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:Email").equals(nuevoMail));
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:FirstName").equalsIgnoreCase(nuevoNombre));
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:LastName").equalsIgnoreCase(nuevoApellido));
		assertTrue(cCBS.ObtenerValorResponse(cCBSM.Servicio_QueryCustomerInfo(sLinea), "bcc:Birthday").contains("19821010"));
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("FirstName")).getAttribute("value").equals(nuevoNombre));
		Assert.assertTrue(driver.findElement(By.id("LastName")).getAttribute("value").equals(nuevoApellido));
		Assert.assertTrue(driver.findElement(By.id("Birthdate")).getAttribute("value").equals(nuevoNacimiento));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		Assert.assertTrue(driver.findElement(By.id("MobilePhone")).getAttribute("value").equals(nuevoPhone));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(apellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(fechaNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(phone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(7000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS_134787_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_FAN_Front_OOCC(String sDNI) {
		imagen = "TS134787";
		detalles = null;
		detalles = imagen + " - Historial de recargas - DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(12000);
		cc.irAHistoriales();
		WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de recargas"))
				historialDeRecargas = x;
		}
		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Con Beneficios']")).click();
		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed()) {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS_134788_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_Sin_Beneficios_FAN_Front_OOCC(String sDNI){
		imagen = "TS134788";
		detalles = null;
		detalles = imagen + "- Historial de recargas - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAHistoriales();
		WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de recargas"))
				historialDeRecargas = x;
		}
		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Sin Beneficios']")).click();
		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed()) {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
	}
	
	
	@Test (groups = {"GestionesPerfilOficina","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "HistoriaRecarga")
	public void TS135468_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_TODOS_FAN_Front_OOCC(String sDNI, String sLinea){
		imagen = "TS135468";
		detalles = null;
		detalles = imagen + " - Historial de packs - DNI: " + sDNI+ " - Linea: "+sLinea;
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.irAHistoriales();
		WebElement historialDeAjustes = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de packs"))
				historialDeAjustes = x;
		}
		historialDeAjustes.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("14")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("14")) {
					cell.click();
				}
			} catch (Exception e) {}
		}	
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		driver.findElement(By.id("text-input-03")).click();
		sleep(2000);
		List<WebElement> todos = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
			for(WebElement t : todos){
				if(t.getText().equals("Todos")){
					t.click();
				}	
			}
		Assert.assertTrue(enc);
	}

	@Test (groups = {"GestionesPerfilOficina","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "RecargasHistorias")
	public void TS135361_CRM_Movil_Prepago_Otros_Historiales_Historial_de_ajustes_FAN_Front_OOCC_S138(String sDNI){
		imagen = "TS135361";
		detalles = null;
		detalles = imagen + "- Historial de packs - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.irAHistoriales();
		sleep(8000);WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de ajustes")){
				historialDeRecargas = x;
				historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
			}
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.via-slds-table-pinned-header")).isDisplayed());
	
	}
	
	@Test (groups = {"GestionesPerfilOficina", "VentaDePacks", "Ciclo1"},priority=1, dataProvider = "ventaPack50ofic")
	public void TS139727_CRM_Movil_REPRO_Venta_de_pack_50_min_y_50_SMS_x_7_dias_Factura_de_Venta_Efectivo_OOCC(String sDNI, String sLinea, String sventaPack) throws AWTException {
		imagen = "TS139727";
		detalles = null;
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID=new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack();
		pagePTelefo.closerightpanel();
		pagePTelefo.PackCombinado(sventaPack);
		
		//String chargeCode = null;
//		try {
//			//chargeCode = 
//					pagePTelefo.PackCombinado(sventaPack);
//			pagePTelefo.PackCombinado(sventaPack);
//		}
//		catch (Exception eE) {
//			driver.navigate().refresh();
//			sleep(10000);
//			mk.closeTabByName(driver, "Comprar SMS");
//			cCC.seleccionarCardPornumeroLinea(sLinea, driver);
//			pagePTelefo.comprarPack("comprar sms");
//			//cc.closerightpanel();
//			//chargeCode = 
//					pagePTelefo.PackCombinado(sventaPack);
//			pagePTelefo.PackCombinado(sventaPack);
		//}
		pagePTelefo.tipoDePago("en factura de venta");
		sleep(12000);
		pagePTelefo.getTipodepago().click();
		sleep(12000);
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		sleep(8000);
		pagePTelefo.getMediodePago().click();
		sleep(45000);
		pagePTelefo.getOrdenSeRealizoConExito().click();
		sleep(10000);
		String orden = cCC.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		CBS_Mattu invoSer = new CBS_Mattu();
		Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[1], orden.split("-")[0],driver));
		sleep(10000);
		driver.navigate().refresh();
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sventaPack));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));	
		System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrden + "Cuenta: "+ accid + "Fin");
		detalles += "-Charge Code: ";// + chargeCode;
	}
	
	@Test (groups = {"GestionPerfilOficina", "VentaDePacks", "Ciclo1"}, dataProvider = "ventaX1Dia" )
	public void TS123163_CRM_Movil_REPRO_Venta_de_pack_1000_min_a_Personal_y_1000_SMS_x_1_dia_Factura_de_Venta_TC_Presencial(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws KeyManagementException, NoSuchAlgorithmException{
		imagen = "TS123163";
		detalles = null;
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID=new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		//cCC.closerightpanel();
		pagePTelefo.comprarPack();
		//String chargeCode = 
				pagePTelefo.PackCombinado(sVentaPack);
		pagePTelefo.tipoDePago("en factura de venta");
		sleep(12000);
		pagePTelefo.getTipodepago().click();
		sleep(12000);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuotas);
		pagePTelefo.getMediodePago().click();
		sleep(45000);
		pagePTelefo.getOrdenSeRealizoConExito().click();// No se puede procesr (Ups, hay problemas para procesar su pago.)
		sleep(10000);
		String orden = cCC.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.PagarTCPorServicio(sOrden);
		sleep(10000);
		if(activarFalsos == true) {
			cCBSM.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		CBS cCBS = new CBS();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sVentaPack));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));	
		System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrden + "Cuenta: "+ accid + "Fin");
		detalles += "-Charge Code: " ;//+ chargeCode;
		//Blocked
	}
	
	@Test (groups = {"GestionesPerfilOficina","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "RecargasHistorias")
	public void TS135483_CRM_Movil_Prepago_Historial_de_Packs_Seleccion_de_Fechas_FAN_Front_OOCC(String sDNI){
		imagen = "TS135483";
		detalles = null;
		detalles = imagen + " - Historial de packs - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.irAHistoriales();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds")));
		driver.findElements(By.className("slds-card"));
		System.out.println(driver.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText());
		driver.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText().equals("Historial de packs");
		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("09")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("07")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		sleep(5000);
		WebElement visu = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"));
		Assert.assertTrue(visu.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigosNoPersonalModificacion")
	public void TS_CRM_Movil_REPRO_FF_No_Modificacion_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS";
		detalles = null;
		detalles = imagen+"- Numeros Amigos - DNI: "+sDNI+" - Linea: "+sLinea;
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				//Delete next line after TS100601 and TS100602 works
				Assert.assertFalse(wNumerosAmigos.get(0).findElement(By.tagName("input")).getText().isEmpty());
				wNumerosAmigos.get(0).findElement(By.tagName("input")).clear();
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				//Delete next line after TS100601 and TS100602 works
				Assert.assertFalse(wNumerosAmigos.get(1).findElement(By.tagName("input")).getText().isEmpty());
				wNumerosAmigos.get(1).findElement(By.tagName("input")).clear();
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		
		switch (iIndice) {
			case 0:
				Assert.assertTrue(driver.findElements(By.cssSelector("[class='vlc-slds-error-block']")).get(0).findElement(By.cssSelector("[class='error']")).getText().equalsIgnoreCase("La linea no pertenece a Telecom, verifica el n\u00famero."));
				break;
			case 1:
				Assert.assertTrue(driver.findElements(By.cssSelector("[class='vlc-slds-error-block']")).get(1).findElement(By.cssSelector("[class='error']")).getText().equalsIgnoreCase("La linea no pertenece a Telecom, verifica el n\u00famero."));
				break;
			default:
				Assert.assertTrue(false);
		}
		//Verify when the page works
	}
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigosNoPersonalAlta")
	public void TS100607_CRM_Movil_REPRO_FF_No_Alta_Amigo_No_Personal_Presencial(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100607";
		detalles = null;
		detalles = imagen+"- Numeros Amigos - DNI:"+sDNI+" - Linea: "+sLinea;
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		
		switch (iIndice) {
			case 0:
				Assert.assertTrue(driver.findElements(By.cssSelector("[class='vlc-slds-error-block']")).get(0).findElement(By.cssSelector("[class='error']")).getText().equalsIgnoreCase("La linea no pertenece a Telecom, verifica el n\u00famero."));
				break;
			case 1:
				Assert.assertTrue(driver.findElements(By.cssSelector("[class='vlc-slds-error-block']")).get(1).findElement(By.cssSelector("[class='error']")).getText().equalsIgnoreCase("La linea no pertenece a Telecom, verifica el n\u00famero."));
				break;
			default:
				Assert.assertTrue(false);
		}
	}
	
	@Test (groups = {"GestionesPerfilOficina","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigosNoPersonalBaja")
	public void TS100612_CRM_Movil_REPRO_FF_No_Baja_Presencial(String sDNI, String sLinea, String sVOZorSMS) throws AWTException {
		imagen = "TS100612";
		detalles = null;
		detalles = imagen+"- Numeros Amigos - DNI:"+sDNI+" - Linea: "+sLinea;
		CBS_Mattu cCBSM = new CBS_Mattu();
		CBS cCBS = new CBS();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		
		BasePage cambioFrame=new BasePage();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).click();
		Robot r = new Robot();    
		for(int i = 0; i<10;i++) {
			r.keyPress(KeyEvent.VK_BACK_SPACE); 
			r.keyRelease(KeyEvent.VK_BACK_SPACE);
		}
		//sleep(5000);
		waitForClickeable(driver,By.cssSelector(".OSradioButton.ng-scope.only-buttom"));
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		//sleep(5000);
		waitForClickeable(driver,By.cssSelector(".slds-radio.ng-scope"));
		List<WebElement> opcs = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		for(WebElement UnaO: opcs) {
			if(UnaO.getText().equalsIgnoreCase("no")) {
				UnaO.click();
				break;
			}
		}
		cCC.obligarclick(driver.findElement(By.id("ChargeConfirmation_nextBtn")));
		waitFor(driver,By.id("ErrorTB"));
		//sleep(20000);
		Assert.assertTrue(driver.findElement(By.id("ErrorTB")).getText().toLowerCase().contains("no fue posible realizar el cambio"));
		Assert.assertTrue(driver.findElement(By.id("ErrorTB")).getText().toLowerCase().contains("orden cancelada a pedido del cliente"));
		Assert.assertTrue(sMainBalance.equals(cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance")));
	}

	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", "Ciclo 1"}, dataProvider="CuentaVista360")
	public void TS134371_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_FAN_Front_OOCC(String sDNI, String sNombre, String sLinea){
		imagen = "TS134371";
		detalles = null;
		detalles = imagen+"- Vista 360 - DNI: "+sDNI;
		String day = fechaDeHoy();
		String dia = day.substring(0, 2);
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("26")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.switchTo().defaultContent();
		WebElement fecha = driver.findElement(By.id("text-input-id-2"));
		sleep(3000);
		List<WebElement> tableRows2 = fecha.findElements(By.xpath("//tr//td"));
		for (WebElement x: tableRows2) {
		try {
		if (x.getText().equals(dia)) {
		x.click();
		}
		} catch (Exception e) {}
		}
		/*driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("06")) {
					cell.click();
				}
			} catch (Exception e) {}
		}*/
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(12000);
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		nroCaso.findElements(By.tagName("td")).get(2).click();
		System.out.println(nroCaso);
		sleep(15000);
		WebElement estado = null;
		driver.switchTo().frame(cambioFrame(driver, By.name("ta_clone")));
		for (WebElement x : driver.findElements(By.className("detailList"))) {
			if (x.getText().toLowerCase().contains("n\u00famero de pedido"))
				estado = x;
		}
		for (WebElement x : estado.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;
		}
		if (estado.getText().toLowerCase().contains("activada") || (estado.getText().toLowerCase().contains("iniciada")))
			gestion = true;
		Assert.assertTrue(gestion);
	}


	@Test (groups = {"GestionesPerfilOficina", "Vista360", "E2E","ConsultaPorGestion", "Ciclo2"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS134370_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_OOCC(String sDNI , String sLinea) {
		imagen = "TS134370";
		detalles = null;
		detalles = imagen+"- Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("06")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_2) {
			try {
				if (cell.getText().equals("31")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table_3 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows_3 = table_3.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows_3) {
			try {
				if (cell.getText().equals("06")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		sleep(5000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"))) {
			if(x.getText().toLowerCase().equals("order")) {
				a = true;
			}
		}
		Assert.assertFalse(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "RenovacionDeCuota", "E2E", "Ciclo1"}, dataProvider = "RenovacionCuotaSinSaldo")
	public void TS135512_CRM_Movil_REPRO_No_Renovacion_de_cuota_Pack_de_datos_Activo_Presencial(String sDNI, String sLinea) {
		imagen = "TS135512";
		detalles = null;
		detalles = imagen + "- No renovacion de cuota - DNI:" + sDNI+ "-Linea: "+sLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Renovacion de Datos");
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-checkbox--faux")));
		driver.findElements(By.className("slds-checkbox--faux")).get(2).click();
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "descuento de saldo");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(3000);
		WebElement msj = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-radio-control.ng-scope.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required"));
		msj = msj.findElement(By.className("vlc-slds-error-block"));
		Assert.assertTrue(msj.getText().contains("El Saldo No Es Suficiente Para Comprar El Pack"));
		Assert.assertTrue(false);//investigar si debe ser asi o si debe negar la activacion del pack si ya esta activo
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134349_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_Plazo_vencido_Asistencia_registrada_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134349";
		detalles = null;
		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a"));
		System.out.println(nroCaso.getText());
		nroCaso.click();
		sleep(8000);
		WebElement fechaYHora = null;
			driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().toLowerCase().contains("owned by me"))
				fechaYHora = x;
		}
		fechaYHora = fechaYHora.findElement(By.tagName("tbody"));
		for (WebElement x : fechaYHora.findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("fecha/hora de cierre"))
				fechaYHora = x;
			System.out.println(x.getText());
		}
		Assert.assertTrue(fechaYHora.getText().contains("Fecha/Hora de cierre"));
		Assert.assertTrue(fechaYHora.findElements(By.tagName("td")).get(3).getText().matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnicoC")
	public void TS121362_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_configuraciones_de_equipos_Validacion_de_IMEI_Ok_Sin_Garantia_Sin_Muleto_Reparar_ahora_No_acepta_presupuesto_ofCom(String sDNI, String sIMEI, String sEmail, String sLinea, String sOpcion, String sOperacion, String sSintoma) throws InterruptedException {
		imagen = "TS121362";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);
		driver.findElement(By.id("ImeiInput_nextBtn")).click();
		sleep(15000);
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver,By.id("PopulateRepairs")));
			driver.findElement(By.cssSelector(".vlc-slds-remote-action__content.ng-scope")).findElement(By.xpath("//*[@id=\"PopulateRepairs\"]/div/p[3]"));
			List<WebElement> butt=driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
			butt.get(1).click();
		}
		catch (Exception e) {
		Assert.assertTrue(false);
	}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("vlc-control-wrapper")).getLocation().y+" )");
		driver.findElement(By.id("AlternativeEmail")).click();
		driver.findElement(By.id("AlternativeEmail")).sendKeys(sEmail);
		driver.findElement(By.id("AlternativePhone")).click();
		driver.findElement(By.id("AlternativePhone")).sendKeys(sLinea);
		sleep(8000);
		sOpcion=sOpcion.toLowerCase();
		boolean flag=false;
		for(WebElement opt:driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"))) {
			if(opt.getText().toLowerCase().contains(sOpcion)) {
				opt.click();
				flag=true;
				break;
			}
		}
		if(!flag) System.out.println("Opcion no disponible");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("ClientInformation_nextBtn")),"equals", "continuar");
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectOperationType")), sOperacion);
		sleep(8000);
		driver.findElement(By.className("vlc-control-wrapper")).click();
		List<WebElement> sint = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical")).findElements(By.tagName("li"));
		for(WebElement sintoma : sint) {
			if(sintoma.getText().contains(sSintoma));
			sintoma.click();
			break;
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("SymptomExplanation_nextBtn")), "equals", "continuar");
	}
	
	
	
	@Test (groups = {"GestionesPerfilOficina", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnicoR")
	public void TS121372_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_reparaciones_de_equipos_Busqueda_de_cliente_Reparacion_Sin_Muleto_Equipo_en_destruccion_total_Con_seguro_de_proteccion_total_No_se_pudo_realizar_la_reparacion_OfCom(String sDNI, String sIMEI, String sEmail, String sLinea, String sOpcion, String sOperacion, String sSintoma) throws InterruptedException {
		imagen = "TS121372";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI+" - Linea: "+sLinea;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);
		driver.findElement(By.id("ImeiInput_nextBtn")).click();
		sleep(15000);
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver,By.id("PopulateRepairs")));
			driver.findElement(By.cssSelector(".vlc-slds-remote-action__content.ng-scope")).findElement(By.xpath("//*[@id=\"PopulateRepairs\"]/div/p[3]"));
			List<WebElement> butt=driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
			butt.get(1).click();
		}
		catch (Exception e) {
	}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("vlc-control-wrapper")).getLocation().y+" )");
		driver.findElement(By.id("AlternativeEmail")).click();
		driver.findElement(By.id("AlternativeEmail")).sendKeys(sEmail);
		driver.findElement(By.id("AlternativePhone")).click();
		driver.findElement(By.id("AlternativePhone")).sendKeys(sLinea);
		sleep(8000);
		sOpcion=sOpcion.toLowerCase();
		boolean flag=false;
		for(WebElement opt:driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"))) {
			if(opt.getText().toLowerCase().contains(sOpcion)) {
				opt.click();
				flag=true;
				break;
			}
		}
		if(!flag) System.out.println("Opcion no disponible");
		sleep(5000);
		buscarYClick(driver.findElements(By.id("ClientInformation_nextBtn")),"equals", "continuar");
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-has-input-has-icon--right"));
		selectByText(driver.findElement(By.id("SelectOperationType")), sOperacion);
		sleep(8000);
		driver.findElement(By.id("SelectSymptomType")).click();
		sleep(5000);
		List<WebElement> sint = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical")).findElements(By.tagName("li"));
		for(WebElement sintoma : sint) {
			System.out.println(sintoma.getText());
			if(sintoma.getText().equalsIgnoreCase(sSintoma)) {
				System.out.println("entreeeeeee");
				sintoma.click();
				break;
			}
	}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("SymptomExplanation_nextBtn")), "equals", "continuar");
		sleep(8000);
		boolean precio = false;
		List<WebElement> soluciones = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--cell-buffer.techCare-PriceListSelection.ng-scope")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement sol: soluciones) {
			if(sol.getText().contains("$0")) {
				sol.findElement(By.tagName("td")).findElement(By.tagName("td")).getText();
				System.out.println(sol);
				precio = true;
				}
			}
					
		for(WebElement sol:soluciones) {
			if(sol.getText().contains("Irreparable")) {
				System.out.println(sol.getText());
				sleep(8000);
				sol.findElement(By.tagName("td")).findElement(By.className("slds-checkbox")).click();
			}
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("1stSolutionList_nextBtn")), "equals", "continuar");
		List<WebElement> costo = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--cell-bufferslds-m-bottom--x-large.ta-table-list.ng-scope")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement cost:costo) {
			if(cost.getText().contains("$0"));{
				System.out.println(cost.getText());
				precio = true;
				Assert.assertTrue(precio);
				}
			}
		
		sleep(8000);
		List<WebElement> presup = driver.findElement(By.className("slds-form-element__control")).findElement(By.tagName("label")).findElements(By.tagName("div"));
		for(WebElement opt:presup) {
			if(opt.getText().toLowerCase().contains("Si")) {
				System.out.println(opt.getText());
				opt.findElement(By.tagName("label")).findElement(By.id("RadioBudgetAcceptance")).click();
				break;
			}
		}
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.TechCare-createOrder-Btn")), "equals", "crear orden");
		
	}
		
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS111871_CRM_Movil_REPRO_Diagnostico_SVA_Configuracion_Disponible_Presencial_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Desregistrar_OfCom(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111871";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: "+sDNI+" - Linea: "+sLinea;
		boolean desregistrar = false;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAProductosyServicios();
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
	    tech.selectionInconvenient("SMS a fijo");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tech.categoriaRed("Desregistrar");
	    Assert.assertTrue(desregistrar);
	}

	@Test(groups = {"Sales", "Nominacion","E2E","Ciclo1"}, dataProvider="DatosSalesNominacionPyRNuevoOfCom") 
	public void TS85099_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_Preguntas_Y_Respuestas_Ofcom(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) { 
		imagen = "TS85099-Nominacion"+sDni;
		detalles = null;
		detalles = imagen +"-Linea: "+sLinea;
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		SalesBase SB = new SalesBase(driver);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		sleep(1500);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")||UnaL.getText().toLowerCase().contains("plan prepago nacional")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(13000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("preguntas y respuestas");
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn")));
		sleep(5000);
		BasePage bp = new BasePage(driver);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		SB.Crear_DomicilioLegal(sProvincia, sLocalidad, sCalle, "", sNumCa, "", "", sCP);
		sleep(38000);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		System.out.println("cont="+element.get(0).getText());
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		Assert.assertTrue(a);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		invoSer.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS100978_CRM_REPRO_BDC_Customer_Care_Suspensiones_y_Rehabilitaciones_Perfil_OOCC_Acceso_a_base_de_conocimiento(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS100978";
		detalles = null;
		detalles = imagen + "-Base de Conocimiento - DNI: " +sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.id("knowledge2HomePage_kbOneTab")));
		Assert.assertTrue(driver.findElement(By.id("knowledge2HomePage_kbOneTab")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS125107_CRM_REPRO_BDC_Customer_Care_Problemas_con_recargas_Tarjetas_Prepagas_Verificar_acceso_a_BC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS125107";
		detalles = null;
		detalles = imagen + "- Base Conocimiento - DNI: "+sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.id("knowledge2HomePage_kbOneTab")));
		Assert.assertTrue(driver.findElement(By.id("knowledge2HomePage_kbOneTab")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS130755_CRM_REPRO_BDC_Customer_Care_Problemas_con_Recargas_PerfilTelefonico_Articulo_de_Medios_de_Recargas(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS125107";
		detalles = null;
		detalles = imagen + "- Base de Conocimiento - DNI: "+sDNI+" - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("vlc-slds-knowledge-list-item")));
		Assert.assertTrue(driver.findElement(By.className("vlc-slds-knowledge-list-item")).getText().contains("Problemas con recargas Online"));
	}
	
	@Test (groups = {"GestionPerfilOficina", "BasedeConocimiento", "Ciclo3"}, dataProvider = "BaseDeConocimiento")
	public void TS124899_CRM_REPRO_BDC_Technical_Care_CSR_Suscripciones_Base_de_Conocimiento(String sDNI, String sLinea) {
		imagen = "TS124899";
		detalles = null;
		detalles = imagen+"-Base de Conocimiento -DNI: "+sDNI+" -Linea: "+sLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.className("community-flyout-actions-card")));
		Assert.assertTrue(driver.findElement(By.className("community-flyout-actions-card")).getText().contains("Suscripciones"));
		sleep(3000);
		cc.irAGestionEnCard("Suscripciones");
		sleep(7000);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("scc_widget_knowledgeOne_button")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".zen-unit.zen-lastUnit.kb-center")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".zen-unit.zen-lastUnit.kb-center")).isDisplayed());	
	}
	
	@Test (groups = {"GestionPerfilOficina", "BasedeConocimiento", "Ciclo3"}, dataProvider = "BaseDeConocimiento")
	public void TS125103_CRM_REPRO_BDC_Customer_Care_Suspensiones_y_Rehabilitaciones_Valoracion_positiva_de_un_articulo(String sDNI, String sLinea) {
		imagen = "TS125103";
		detalles = null;
		detalles = imagen+"- Base de Conocimiento - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("scc_widget_knowledgeOne_button")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".zen-unit.zen-lastUnit.kb-center")));
		driver.findElement(By.id("knowledgeSearchInput_kbOneTab")).sendKeys("Suspensiones y Reconexiones - Requisitos");
		driver.findElement(By.cssSelector(".knowledgeSearchBoxButton.knowledgeSearchButton.leftColumn")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("articleList_kbOneTab")));
		driver.findElement(By.xpath("//*[@id=\"kA0c0000000DD1B_kbOneTab\"]/div/p[1]/a")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".ptBody.secondaryPalette.brandSecondaryBrd")));
		boolean a = false;
		for(WebElement x : driver.findElements(By.className("voteUDInlineUpCount"))) {
			if(x.getText().toLowerCase().contains("2")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionPerfilOficina", "BasedeConocimiento", "Ciclo3"}, dataProvider = "BaseDeConocimiento")
	public void TS124900_CRM_REPRO_BDC_Technical_Care_CSR_Inconvenientes_con_Servicios_Varios_Base_de_Conocimiento(String sDNI, String sLinea) {
		imagen = "TS124900";
		detalles = null;
		detalles = imagen+"-Base de Conocimiento:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(5000);
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.id("knowledge2HomePage_kbOneTab")));
		Assert.assertTrue(driver.findElement(By.id("knowledge2HomePage_kbOneTab")).isDisplayed());
	}
	@Test (groups = {"GestionesPerfilOficina", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TC135738_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Voice_Mail_con_Clave_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135738";
		detalles = null;
		detalles = imagen+" - BajaServicio - DNI: "+sDNI+" -Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Voice Mail con Clave"));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		sb.BuscarCuenta("DNI",sDNI);
		//sb.BuscarCuenta(sLinea);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		//cc.openrightpanel();
		//cc.closerightpanel();
		//cc.openleftpanel();
		//cc.closeleftpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:VoiceMailConClave";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "CONTESTADOR", "Voice Mail con Clave", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Voice Mail con Clave"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS135739_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135739";
		detalles = null;
		detalles = imagen+"- BajaServicio - DNI: "+sDNI+" - Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TC135744_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Voice_Mail_con_Clave_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135744";
		detalles = null;
		detalles = imagen+"- AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		//Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Voice Mail con Clave"));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		sb.BuscarCuenta("DNI",sDNI);
		//sb.BuscarCuenta(sLinea);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		//cc.openrightpanel();
		//cc.closerightpanel();
		//cc.openleftpanel();
		//cc.closeleftpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:VoiceMailConClave";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicioContestador("Alta", "Servicios basicos general movil", "Contestador", "Voice Mail con Clave", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Voice Mail con Clave"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS135745_CRM_Movil_REPRO_Alta_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135745";
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		//Al dar de baja DDI con roaming internacional se activa el boton para dar de alta el DDI sin roaming internacional
		ppt.altaBajaServicio("baja", "Servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
		
	@Test (groups = {"GestionesPerfilOficina", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS125098_CRM_REPRO_BDC_Customer_Care_Problemas_con_recargas_Valoracion_negativa_en_BC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS125098";
		detalles = null;
		detalles = imagen + "-Base de Conocimiento - DNI: "+sDNI+ " - Nombre: "+sNombre;
		boolean downVote = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().defaultContent();
		buscarYClick(driver.findElements(By.className("x-btn-text")), "contains", "knowledge");
		driver.switchTo().frame(cambioFrame(driver, By.xpath("//*[@id=\"knowledgeSearchInput_kbOneTab\"]")));
		driver.findElement(By.xpath("//*[@id=\"knowledgeSearchInput_kbOneTab\"]")).sendKeys("problemas con recargas");
		driver.findElement(By.xpath("//*[@id=\"knowledgeSearchInput_kbOneTab\"]")).sendKeys(Keys.ENTER);
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".articleListContainer.loaded")));
		driver.findElement(By.cssSelector(".articleListContainer.loaded")).findElement(By.className("articleEntry")).findElement(By.tagName("a")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("articleRendererBorderRight")));
		WebElement negativo = driver.findElement(By.className("articleRendererBorderRight")).findElements(By.tagName("td")).get(1).findElements(By.tagName("a")).get(1);
		if (negativo.getAttribute("title").contains("Haga clic si no le gusta este art\u00edculo de knowledge"))
			downVote = true;
		Assert.assertTrue(downVote);
	}
		// =========================== ESTE VA 2 ============================
	@Test(groups = { "GestionesPerfilOficina","CambioDeSimcard", "E2E","Ciclo3" }, priority = 1, dataProvider = "SimCardSiniestroOfCom")
	public void TS99030_CRM_Movil_REPRO_Cambio_de_SimCard_Presencial_Siniestro_Ofcom(String sDNI, String sLinea) throws AWTException {
		imagen = "99030";
		detalles = null;
		detalles = imagen + "-DNI:" + sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		sleep(8000);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(16000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
		invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
		}
		sleep(2000);
		CambiarPerfil("logistica",driver);
		sb.completarLogistica(orden, driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		
		driver.switchTo().defaultContent();
		sleep(6000);
		cCC.obtenerMontoyTNparaAlta(driver, orden);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		
		GestionFlow gf = new GestionFlow();
		String sIMSIFlow = gf.FlowIMSI(driver,sLinea);
		System.out.println("sIMSIFlow: " + sIMSIFlow);
	}
	
	
	//@Test(groups = { "GestionesPerfilOficina","CambioSimcardDer", "E2E","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS134381_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Voluntario_Ofcom_Presencial_Con_entega_de_pedido(String sDNI, String sLinea) throws AWTException	{
		imagen = "134381";
		detalles = null;
		detalles = imagen + "-DNI:" + sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		sleep(8000);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(30000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(16000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
		invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
		}
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga") 
	public void TS104346_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_On_Line_Ofcom(String sDNI, String sLinea) {
		imagen = "TS104346";
		detalles = null;
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("OnlineRefillData_nextBtn")));
		driver.findElement(By.id("RefillDate")).sendKeys("11-08-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("111");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(5000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(5000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("FileAttach")).sendKeys("C:\\Users\\Sofia Chardin\\Desktop\\DNI.jpg");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga") 
	public void TS104351_CRM_Movil_Repro_Problemas_con_Recarga_On_line_Sin_comprobante_En_espera_del_cliente_Ofcom(String sDNI, String sLinea) {
		imagen = "TS104351";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("11-08-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("111");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(5000);		
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(5000);
		} catch (Exception e) {}	
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("La gesti\u00f3n fue derivada"));
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecargaAYD") 
	public void TS104353_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible_Ofcom(String sDNI, String sLinea, String sSerie, String sPIN) {
		imagen = "TS104353";
		detalles = null;
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		driver.findElements(By.className("borderOverlay")).get(0).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys(sSerie);
		driver.findElement(By.id("PIN")).sendKeys(sPIN);
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(5000);
		WebElement estado = driver.findElement(By.id("PrepaidCardStatusLabel"));
		Assert.assertTrue(estado.getText().toLowerCase().contains("activa"));
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(5000);
		WebElement gestion = driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.tagName("header")).findElement(By.tagName("h1"));
		Assert.assertTrue(gestion.getText().contains("Recarga realizada con \u00e9xito"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 5000000 == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "HistoriaRecarga")
	public void TS134840_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Fecha_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134840";
		detalles = null;
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAHistoriales();
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(5000);
		WebElement fecha = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("tr")).findElement(By.tagName("th"));
		System.out.println(fecha.getText());
		Assert.assertTrue(fecha.getText().contains("FECHA"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "HistoriaRecarga")
	public void TS134747_CRM_Movil_Prepago_Historial_de_Recargas_S141_FAN_Front_OOCC(String sDNI, String sLinea){
		imagen = "TS134747";
		detalles = null;
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAHistoriales();
		sleep(3000);
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		sleep(3000);
		cc.seleccionDeHistorial("historial de recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(conf.isDisplayed());
		try {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			} catch (Exception e) {
				driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			}
		sleep(3000);
		WebElement conf_1 = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
		Assert.assertTrue(conf_1.isDisplayed());
		sleep(3000);
		boolean a = false;
		List <WebElement> fecha = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));		
		for(WebElement x : fecha) {
			if(x.getText().toLowerCase().contains("fecha")) {
				a= true;
			}
		}
		Assert.assertTrue(a);
		sleep(3000);
		WebElement paginas = driver.findElement(By.cssSelector(".slds-grid.slds-col"));
		Assert.assertTrue(paginas.getText().contains("Filas"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS119262_CRM_Movil_REPRO_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Sin_Locacion_NO_recupera_locacion_Geo_Fuera_de_area_de_cobertura_OfCom(String sDNI, String sLinea) throws Exception  {
		boolean caso = false;
		imagen = "TS119262";
		detalles = null;
		detalles = imagen + " -Diagnostico: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo llamar desde otro pa\u00eds");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
		tech.categoriaRed("Desregistrar");
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("BlackListValidationOk_nextBtn")), "equals", "continuar");
		tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(10000);
		WebElement gesti = driver.findElement(By.xpath("//*[@id='OutOfCoverageMessage']/div/p/p[2]/span/strong"));
		String Ncaso = gesti.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		caso = true;
		assertTrue(caso);
	}
	
	
	@Test (groups = {"GestionesPerfilOficina", "ModificarDatos", "E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDNI")
	public void TS129325_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_campo_DNI_CUIT_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129325";
		detalles = null;
		detalles = imagen+"-Modificacion de datos - DNI:"+sDNI;
		String nuevoDNI = "22222070";
		String nuevoMail = "maildetest@gmail.com";
		String numeroTelefono = "1533546987";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		driver.findElement(By.id("DocumentNumber")).getAttribute("value");
		driver.findElement(By.id("Email")).getAttribute("value");
		driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(numeroTelefono);
		driver.findElement(By.id("DocumentNumber")).clear();
		driver.findElement(By.id("DocumentNumber")).sendKeys(nuevoDNI);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.className("ng-binding")).getText().equalsIgnoreCase("Las modificaciones se realizaron con \u00e9xito!"));
		String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		orden = orden.substring(orden.length()-9, orden.length()-1);		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS129472_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Pagos_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS129472";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).findElement(By.className("slds-text-heading--label"));
		List<String> verificacion = new ArrayList<String>();
		verificacion.add("FECHA DE EMISI\u00d3N");
		verificacion.add("FECHA DE VENCIMIENTO");
		verificacion.add("TIPO");
		verificacion.add("NRO. DE COMPROBANTE");
		verificacion.add("MONTO");
		List<String> columnas = new ArrayList<String>();
		List<WebElement> datos = tabla.findElements(By.tagName("a"));
		for (int i=0; i<datos.size(); i++) {
			columnas.add(datos.get(i).getText());
		}
		Assert.assertTrue(verificacion.equals(columnas));
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilOficina","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecarga")
	public void TS104352_CRM_Movil_REPRO_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Pin_Borrado(String sDNI, String sLinea) {
		imagen = "TS104352";
		boolean gestion = false, error = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009319");
		driver.findElement(By.id("PIN")).sendKeys("0386");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(10000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(10000);
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("recarga realizada con \u00e9xito"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 5000 == datosFinal);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009319");
		driver.findElement(By.id("PIN")).sendKeys("0386");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("La tarjeta ya fue utilizada para una recarga"))
				error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"GestionesPerfilOficina","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "RecargasHistorias")
	public void TS135476_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Internet_50_Mb_FAN_Front_OOCC(String sDNI){
		imagen = "TS135476";
		detalles = null;
		detalles = imagen+"-HistorialDePacksTelefonico - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.irAHistoriales();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds")));
		driver.findElements(By.className("slds-card"));
		System.out.println(driver.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText());
		driver.findElement(By.cssSelector(".slds-card__header.slds-grid")).getText().equals("Historial de packs");
		driver.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(8000);
		driver.findElement(By.id("text-input-03")).click();
		List <WebElement>NomPack = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		String pack = "Internet 50 MB Dia";
		for(WebElement Pack : NomPack) {
			if(Pack.getText().equalsIgnoreCase(pack)) {
				System.out.println(Pack.getText());
				Pack.click();
				break;
			}
		}
		assertTrue(pack.equals("Internet 50 MB Dia"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","ResumenDeCuenta","E2E", "Ciclo4"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS129471_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Comprobantes_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS129471";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		boolean a = false;
		for(WebElement x : driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"))) {
			if(x.getText().toLowerCase().contains("comprobantes")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina","ModificarDatos","E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS84240_CRM_Movil_REPRO_Modificacion_de_datos_No_Actualizar_datos_Cliente_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS84240";
		detalles = null;
		detalles = imagen + " -ActualizarDatos - DNI: " + sDNI+ " - Linea: "+sLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		if (driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
			driver.findElement(By.id("MobilePhone")).clear();
			driver.findElement(By.id("MobilePhone")).sendKeys("5214864852");
		}
		driver.findElement(By.id("FirstName")).getAttribute("value");
		driver.findElement(By.id("LastName")).getAttribute("value");
		driver.findElement(By.id("Birthdate")).getAttribute("value");
		driver.findElement(By.id("Email")).getAttribute("value");
		driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("OtherPhone")).getAttribute("value");
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("OtherPhone")).clear();
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-modal__content.slds-p-around--medium")));
		WebElement error = driver.findElement(By.cssSelector(".slds-modal__content.slds-p-around--medium"));
		Assert.assertTrue(error.getText().equalsIgnoreCase("Error: Por favor corrija los campos con errores"));
		driver.findElement(By.id("alert-ok-button")).click();
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "ConsultaSaldo")
	public void TS129462_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Validaciones_de_logica_de_filtros_Fecha_FAN_Front_OOCC(String sDNI) {
		imagen="TS129462";
		detalles = null;
		detalles = imagen + "- Resumen de Cuenta - DNI:" +sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.openleftpanel();
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		cc.irAResumenDeCuentaCorriente();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		for (WebElement cell : table.findElements(By.xpath("//tr//td"))) {
			try {
				if (cell.getText().equals("29"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		for (WebElement cell : table2.findElements(By.xpath("//tr//td"))) {
			try {
				if (cell.getText().equals("10"))
					cell.click();
			} catch (Exception e) {}
		}
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-horizontal--small.slds-size--1-of-2.slds-medium-size--1-of-4.slds-large-size--1-of-4")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS129476_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Factura_de_Venta_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil){
		imagen = "TS129476";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(5000);
		cc.irAResumenDeCuentaCorriente();
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		WebElement saldo = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont"));
		saldo.findElement(By.tagName("b"));
		System.out.println(saldo.getText());
		sleep(3000);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
			List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
				for (WebElement cell : tableRows) {
					try {
						if (cell.getText().equals("01")) {
							cell.click();
						}
					}catch(Exception e) {}
				}
				driver.findElement(By.id("text-input-id-2")).click();
				WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
				sleep(3000);
				List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
				for (WebElement cell : tableRows_2) {
					try {
						if (cell.getText().equals("01")) {
						cell.click();
						}
					}catch(Exception e) {}
				}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		boolean conf = false;
		List <WebElement> tablas = driver.findElements(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		for (WebElement x : tablas) {
			if(x.getText().toLowerCase().contains("comprobantes")) {
				conf = true;
			}
		}
		Assert.assertTrue(conf);
		List<WebElement> Tabla = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement x:Tabla) {
			if (x.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("fv")) {
				sleep(10000);
				x.findElements(By.tagName("td")).get(5).click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("boton")));
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		comprobante.findElement(By.tagName("object"));
		System.out.println(comprobante.getText());
		Assert.assertTrue(comprobante.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes","E2E", "Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS112437_CRM_Movil_PRE_Ajuste_Credito_MB_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS112437";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosInicial = Integer.parseInt(datoViejo);
		System.out.println(datosInicial);
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Datos (Mb)");
		driver.findElement(By.id("CantidadDatosms")).sendKeys("123");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		String datoNuevo = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Integer datosFinal = Integer.parseInt(datoNuevo);
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + (123 * 1024) == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105428_CRM_Movil_Repro_Autogestion_USSD_No_Interactua_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105428";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("USSD", "Otro", "No Interact\u00faa");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105431_CRM_Movil_Repro_Autogestion_WAP_Sitio_Caido_No_carga_informacion_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105431";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("WAP", "Otro", "Sitio Ca\u00eddo/ No carga informaci\u00f3n");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105831_Nros_Emergencia_Informa_Sistema_Fuera_de_Servicio_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105831";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("Nros. Emergencia", "Otro", "Informa Sistema Fuera de Servicio");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}

	@Test (groups = {"GestionesPerfilOficina","Autogestion","E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS105418_CRM_Movil_Repro_Autogestion_0800_Inconv_con_derivacion_a_representante_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105418";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(15000);
		tech.listadoDeSeleccion("0800", "Otros", "Incov con derivaci\u00f3n a representante");
		sleep(4000);
		tech.verificarNumDeGestion();
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test(groups = { "GestionesPerfilOficina","CambioDeSimcard", "E2E","Ciclo3" }, priority = 1, dataProvider = "SimCardSiniestroOfCom")
	public void TS134382_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Siniestro_Ofcom_Presencial_Con_entega_de_pedido(String sDNI, String sLinea) throws AWTException {
		imagen = "134382";
		detalles = null;
		detalles = imagen + "-DNI:" + sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		sleep(8000);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(16000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
			invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
		}
		sleep(2000);
		CambiarPerfil("logistica",driver);
		sb.completarLogistica(orden, driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		
		driver.switchTo().defaultContent();
		sleep(6000);
		cCC.obtenerMontoyTNparaAlta(driver, orden);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		
		GestionFlow gf = new GestionFlow();
		String sIMSIFlow = gf.FlowIMSI(driver,sLinea);
	}
	
	//@Test(groups = { "GestionesPerfilTelefonico","CambioDeSimcardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroOfCom") 
	public void TS134406_OF_COM_CRM_Movil_REPRO_Cambio_de_simcard_con_costo_Siniestro_Ofcom_Store_pickUp_Con_entega_de_pedido_pago_en_Efectivo(String sDNI, String sLinea){
		imagen = "134406";
		detalles = null;
		detalles = imagen + "-DNI:" + sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		sleep(8000);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles+=" - Orden: "+orden;
		orden = orden.substring(orden.length()-8);
		System.out.println("Orden " + orden);
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);		
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, orden);
		detalles+=" - Cuenta: "+accid+"Invoice: "+invoice.split("-")[0];
		System.out.println(invoice);
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
			invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
			
		}
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	//@Test(groups = { "GestionesPerfilTelefonico","CambioSimCardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroAG") //NO APARECE EL MEDIO DE PAGO
	public void TS134407_CRM_Movil_REPRO_Cambio_de_simcard_con_costo_Siniestro_Ofcom_Store_pickUp_Con_entega_de_pedido_pago_con_TD(String sDNI, String sLinea,String cEntrega, String cProvincia, String cLocalidad, String cPuntodeVenta, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI,String cDNITarjeta, String cTitular){
		imagen = "134407";
		detalles = null;
		detalles = imagen + "-DNI:" + sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		sleep(8000);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		pagePTelefo.mododeEntrega(driver, cEntrega, cProvincia, cLocalidad, cPuntodeVenta);
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles+=" - Orden: "+orden;
		orden = orden.substring(orden.length()-8);
		System.out.println("Orden " + orden);
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);		
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, orden);
		detalles+=" - Cuenta: "+accid+"Invoice: "+invoice.split("-")[0];
		System.out.println(invoice);
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
			invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement UnC: campos) {
				if(UnC.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(UnC.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
			
		}
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360") 
	public void TS134368_OFCOM_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134368";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
		detalles = null;
		detalles = imagen + "- Detalle de Consumo - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "detalle de consumos");
		sleep(9500);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-02")));
		System.out.println(driver.findElement(By.id("text-input-02")).getAttribute("value"));
		driver.findElement(By.id("text-input-02")).click();
		List<WebElement> pres = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		for(WebElement p : pres){
			if(p.getText().toLowerCase().equals("los \u00faltimos 15 d\u00edas")){
			cc.obligarclick(p);
			}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(7000);
		boolean a = false;
		List<WebElement> filtro = driver.findElements(By.cssSelector(".slds-text-heading--small"));
		for(WebElement f : filtro){
			if(f.getText().toLowerCase().equals("filtros avanzados")){
				a = true;
			}
		}	
		Assert.assertTrue(a);
		Select pagina = new Select (driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")));
		pagina.selectByVisibleText("30");
		sleep(7500);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "HistoriaRecarga")
	public void TS134786_CRM_Movil_Prepago_Historial_de_Recargas_Monto_total_FAN_Front_OOCC(String sDNI, String sLinea) { 
		imagen = "TS134786";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAHistoriales();
		sleep(3000);
		WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de recargas")) {
				historialDeRecargas = x;
			}
		}
		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.className("tableHeader")));
		boolean a = false;
		WebElement conf = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium"));
			if(conf.getText().toLowerCase().contains("monto total de recargas: ")) {
				//System.out.println(conf);
				a = true;
			
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134335_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_Llamadas_WiFi_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134335";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:Llamadas WiFi/Servicio de Red Voz sobre WIFI";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134339_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134339";
		detalles = null;
		detalles = imagen+"- BajaServicio - DNI: "+sDNI+" - Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134352_CRM_Movil_PRE_Alta_Servicio_sin_costo_Llamadas_WiFi_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134352";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:Llamadas WiFi/Servicio de Red Voz sobre WIFI";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134356_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134356";
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		try {
			driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(15000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Alta", "Servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Recargas","E2E", "Ciclo1"}, dataProvider="RecargaTC")
	public void TS134319_CRM_Movil_REPRO_Recargas_Presencial_TC_Ofcom(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular, String sPromo, String sCuotas) throws AWTException, KeyManagementException, NoSuchAlgorithmException {
		//Check All
		imagen = "TS134319";
		detalles = null;
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		if(sMonto.length() >= 4) {
			sMonto = sMonto.substring(0, sMonto.length()-1);
		}
		if(sVenceMes.length() >= 2) {
			sVenceMes = sVenceMes.substring(0, sVenceMes.length()-1);
		}
		if(sVenceAno.length() >= 5) {
			sVenceAno = sVenceAno.substring(0, sVenceAno.length()-1);
		}
		if(sCodSeg.length() >= 5) {
			sCodSeg = sCodSeg.substring(0, sCodSeg.length()-1);
		}
		
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		//System.out.println("Saldo original: " + iMainBalance);
		
		BasePage cambioFrame=new BasePage();
		sleep(5000);
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles += "-Cuenta:" + accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(12000);
		cCC.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(1000);
		driver.findElement(By.id("BankingEntity-0")).click();
		sleep(2000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuotas);
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
		String orden = cCC.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+="-Monto:"+orden.split("-")[2]+"-Prefactura:"+orden.split("-")[1];
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.PagarTCPorServicio(sOrden);
		sleep(5000);
		if(activarFalsos == true) {
			cCBSM.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		sleep(10000);
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		System.out.println("saldo nuevo "+uMainBalance);
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Integer monto = Integer.parseInt(orden.split("-")[2].replace(".", ""));
		monto = Integer.parseInt(monto.toString().substring(0, monto.toString().length()-2));
		Assert.assertTrue(iMainBalance+monto == uiMainBalance);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", ""}, dataProvider="CuentaVista360")
	public void TS134746_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_OOCC(String sDNI,String sNombre, String sLinea) {
		imagen = "TS134746";
		detalles = imagen+" - Vista360 - DNI: "+sDNI+" - Linea: "+sLinea;
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		WebElement wFlyoutCard = driver.findElement(By.name("Flyout Grid Actions"));
		List<WebElement> wWebList = wFlyoutCard.findElements(By.tagName("span"));
		List<String> sTextList = new ArrayList<String>();
		sTextList.add("Recarga de cr\u00e9dito");
		sTextList.add("Renovacion de Datos");
		sTextList.add("Alta/Baja de Servicios");
		sTextList.add("Suscripciones");
		sTextList.add("Problemas con Recargas");
		sTextList.add("Historial de Suspensiones");
		sTextList.add("Diagn\u00f3stico");
		sTextList.add("N\u00fameros Gratis");
		sTextList.add("Cambio SimCard");
		ppt =  new PagePerfilTelefonico(driver);
		Assert.assertTrue(ppt.forEach(wWebList, sTextList));
		//Verify info
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Mensajes')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'MB')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'KB')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Minutos')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Segundos')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
		//Verify buttons
		WebElement wRightSideCard = driver.findElement(By.xpath("//*[@class='items-card ng-not-empty ng-valid']"));
		wWebList = wRightSideCard.findElements(By.xpath("//button[@class='slds-button slds-button--neutral slds-truncate']"));
		sTextList.clear();
		sTextList.add("Comprar SMS");
		sTextList.add("Comprar Internet");
		sTextList.add("Comprar Minutos");
		Assert.assertTrue(ppt.forEach(wWebList, sTextList));
	}
	
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134838_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_IVR_Fan_FRONT_OOCC(String sDNI) {
		imagen = "TS134838";
		boolean histDeRecargas = false, histDePacks = false, histDeRecargasSOS = false, histDeAjustes = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAHistoriales();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-card__header.slds-grid"))) {
			if (x.getText().contains("Historial de recargas"))
				histDeRecargas = true;
			if (x.getText().contains("Historial de packs"))
				histDePacks = true;
			if (x.getText().contains("Historial de recargas S.O.S"))
				histDeRecargasSOS = true;
			if (x.getText().contains("Historial de ajustes"))
				histDeAjustes = true;
		}
		Assert.assertTrue(histDeRecargas && histDePacks && histDeRecargasSOS && histDeAjustes);
		WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de recargas"))
				historialDeRecargas = x;
		}
		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.id("text-input-03")).click();		
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")).isDisplayed());
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		Assert.assertTrue(tabla.isDisplayed());
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS125372_CRM_Movil_Repro_Problemas_con_Recarga_Presencial_TC(String sDNI, String sLinea) {
		imagen = "TS125372";
		boolean gestion = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		driver.findElements(By.className("borderOverlay")).get(1).click();
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("RefillDate")).sendKeys("01-12-2018");
		driver.findElement(By.id("RefillAmount")).sendKeys("5000");
		driver.findElement(By.id("ReceiptCode")).sendKeys("123");
		driver.findElement(By.id("OnlineRefillData_nextBtn")).click();
		sleep(7000);
		try {
			driver.findElement(By.xpath("//*[@id=\"SessionCase|0\"]/div/div[1]/label[2]/span/div/div")).click();
			driver.findElement(By.id("ExistingCase_nextBtn")).click();
			sleep(10000);
		} catch (Exception e) {}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("FileAttach")).sendKeys("C:\\Users\\Nicolas\\Desktop\\descarga.jpg");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-done-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("Recarga realizada con \u00e9xito"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS136535_CRM_Movil_REPRO_Ajuste_General_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS136535";
		detalles = null;
		detalles = imagen + " -Ajustes-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("55000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("El caso fue derivado para autorizaci\u00f3n"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 5550 == datosFinal);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98434_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98434";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("State")).sendKeys(sProvincia);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98436_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_No_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98436";
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("suspensiones");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("State")).sendKeys(sProvincia);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("DNI")).sendKeys(sDNI);
		driver.findElement(By.id("FirstName")).sendKeys("Cinco");
		driver.findElement(By.id("LastName")).sendKeys("Newton");
		driver.findElement(By.id("Phone")).sendKeys("2944675270");
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119210_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119210";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		boolean caso= false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tech.categoriaRed("No son las antenas (Verde)");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageOkNetMessage")));
		WebElement gesti = driver.findElement(By.id("CoverageOkNetMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		String Ncaso = gesti.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		caso = true;
		assertTrue(caso);
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119201_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_recibir_llamadas_Sin_Locacion_Envia_configuraciones(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119201";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(8000);
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso = driver.findElement(By.xpath("//*[@id='OutOfCoverageMessage']/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
		
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119186_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_NO_recupera_locacion_Geo_rojo(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119186";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(8000);
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		sleep(8000);
		WebElement evento = driver.findElement(By.id("MassiveIncidentLookUp"));
		evento.click();
		evento.sendKeys("Evento Masivo");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso = driver.findElement(By.xpath("//*[@id='MassiveIncidentMessage']/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Pendiente evento masivo"));
	}
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134841_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_SMS_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134847";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAHistoriales();
		sleep(3000);
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		sleep(3000);
		cc.seleccionDeHistorial("historial de recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		WebElement canal = driver.findElement(By.id("text-input-03"));
		System.out.println(canal.getText());
		Assert.assertTrue(canal.isDisplayed());
		sleep(7000);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> sms = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : sms){
			if(s.getText().equals("SMS")){
				s.click();
			}	
		}
		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed()) {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134842_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_ROL_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134842";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irAHistoriales();
		sleep(3000);
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		sleep(3000);
		cc.seleccionDeHistorial("historial de recargas");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		WebElement canal = driver.findElement(By.id("text-input-03"));
		System.out.println(canal.getText());
		Assert.assertTrue(canal.isDisplayed());
		sleep(7000);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> recarga = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement r : recarga){
			if(r.getText().equals("Recarga Online")){
				r.click();
			}	
		}
		if (driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).isDisplayed()) {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "RecargasHistorias")
	public void TS135472_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Internacional_FAN_Front_OOCC(String sDNI){
		imagen = "TS135472";
		detalles = imagen+"-Historial De Packs -DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAHistoriales();
		sleep(3000);
		cc.seleccionDeHistorial("historial de packs");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(5000);
		driver.findElement(By.id("text-input-03")).click();
		//Falta la opcion en el Nombre del pack: Plan Internacional --- Se requiere actualizar cuando exista el Pack Internacional
		List<WebElement> todos = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement t : todos){
			if(t.getText().equals("Reseteo 200 MB por Dia")){
				t.click();
			}	
		}
	}
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "RecargasHistorias")
	public void TS135474_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Familia_FAN_Front_OOCC(String sDNI){
		imagen = "TS135474";
		detalles = imagen+"-Historial De Packs -DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAHistoriales();
		sleep(3000);
		cc.seleccionDeHistorial("historial de packs");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(5000);
		driver.findElement(By.id("text-input-03")).click();
		//Falta la opcion en el Nombre del pack: Plan Familia --- Se requiere actualizar cuando exista el Pack Familia
		List<WebElement> todos = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement t : todos){
			if(t.getText().equals("Pack 100MB Uruguay")){
				t.click();
			}	
		}
		
	}
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"},  dataProvider = "Diagnostico")
	public void TS105466_CRM_CRM_Movil_Repro_Autogestion_WEB_Incon_con_Compra_de_packs_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105466";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(15000);
		tech.listadoDeSeleccion("WEB", "Packs", "Incon.con Compra de packs");
		sleep(4000);
		tech.verificarNumDeGestion();
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Informada"));
		//Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS136536_CRM_Movil_REPRO_Escalamiento_segun_RAV_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS136536";
		detalles = null;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO PREPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Consumos de datos");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "plan con tarjeta");
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Desde")).sendKeys("01-07-2018");
		driver.findElement(By.id("Hasta")).sendKeys("30-07-2018");
		selectByText(driver.findElement(By.id("Unidad")), "Credito");
		driver.findElement(By.id("CantidadMonto")).sendKeys("200000");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		WebElement nroCaso = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().toLowerCase().contains("el caso fue derivado para autorizaci\u00f3n")) {
				gestion = true;
				nroCaso = x;
			}
		}
		Assert.assertTrue(gestion);
		String caso = nroCaso.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		caso = caso.substring(caso.indexOf("0"), caso.length());
		System.out.println(caso);
		CambiarPerfil("backoffice", driver);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(15000);
		cc.cerrarTodasLasPestanas();
		goToLeftPanel2(driver, "Casos");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".topNav.primaryPalette")));
		selectByText(driver.findElement(By.cssSelector(".topNav.primaryPalette")).findElement(By.name("fcf")), "BO Centralizado");
		WebElement filaDelCaso = null;
		WebElement tabla = driver.findElement(By.className("x-grid3-body"));
		for (WebElement x : tabla.findElements(By.tagName("tr"))) {
			if (x.getText().contains(caso))
				filaDelCaso = x;
		}
		filaDelCaso.findElement(By.tagName("input")).click();
		driver.findElement(By.cssSelector(".linkBar.brandSecondaryBrd")).findElement(By.name("accept")).click();
		sleep(5000);
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.name("edit")));
		WebElement aprobar = null;
		for (WebElement x : driver.findElements(By.className("actionColumn"))) {
			if (x.getText().contains("Aprobar/rechazar"))
				aprobar = x;
		}
		for (WebElement x : aprobar.findElements(By.tagName("a"))) {
			if (x.getText().contains("Aprobar/rechazar"))
				x.click();
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.name("goNext")));
		driver.findElement(By.name("goNext")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("extraStatusDiv_A")));
		Assert.assertTrue(driver.findElement(By.className("extraStatusDiv_A")).getText().equalsIgnoreCase("Aprobado"));
		String datoNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoNuevo.substring(0, 5));
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 2000 == datosFinal);
		detalles = imagen + " -Ajustes-DNI: " + sDNI + ", Caso numero: " + caso;
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS_119162_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas(String sDNI, String sLinea){
		boolean caso = false;
		imagen = "TS119262";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("S\u00ed");
		buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageResult|0")));
		List<WebElement> cobertura = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
			for(WebElement c : cobertura){
				if(c.getText().toLowerCase().contains("no son las antenas")){
					c.click();
					break;
				}
			}
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageOkNetMessage")));
		WebElement gesti = driver.findElement(By.id("CoverageOkNetMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		String Ncaso = gesti.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		caso = true;
		assertTrue(caso);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105449_CRM_Movil_Repro_Autogestion_0800_Informa_Sistema_Fuera_de_Servicio_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105449";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: " + sDNI;
		boolean estado = false;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("diagnostico de autogestion");
		sleep(5000);
		tech.listadoDeSeleccion("0800", "0800-444-0531 (Tienda Planes)", "Informa Sistema Fuera de Servicio");
		sleep(7000);
		String caso = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).getText();
		caso = caso.substring(caso.indexOf("0"), caso.length());
		System.out.println(caso);
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.name("close")));
		WebElement tabla = null;
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			if (x.getText().contains("Propietario del caso"))
				tabla = x;
		}
		for (WebElement x : tabla.findElements(By.tagName("tr"))) {
			if (x.getText().contains("Estado"))
				tabla = x;
		}
		if (tabla.findElements(By.tagName("td")).get(3).getText().equalsIgnoreCase("Realizada exitosa"))
			estado = true;
		Assert.assertTrue(estado);
	}
	@Test (groups = {"GestionesPerfilOficina", "Resumen de Cuenta Corriente", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS129461_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Verificacion_de_vista_de_Resumen_de_Cuenta_Corriente_FAN_Front_OOCC(String sDNI){
		imagen = "TS129461";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "resumen de cuenta");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".boton3.secondaryFontWhite")));
		WebElement saldo = driver.findElement(By.className("secondaryFontNormal"));
		WebElement fechas = driver.findElement(By.cssSelector(".slds-text-heading--small.secondaryFont"));
		WebElement boton = driver.findElement(By.cssSelector(".boton3.secondaryFontWhite"));
		Assert.assertTrue(saldo.isDisplayed() && fechas.isDisplayed() && boton.isDisplayed());
		boton.click();
		sleep(5000);
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(comprobante.getText().toLowerCase().contains("comprobantes"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosNominacionExistente5Lineas")
	public void TS85104_CRM_Movil_REPRO_No_Nominatividad_Numero_Limite_De_Lineas_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85104";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		sleep(2000);
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, "Masculino");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(10000);
		Assert.assertFalse(driver.findElement(By.id("MethodSelection_nextBtn")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominacionExistenteFraudeOfcom")
	public void TS85105_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85105";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		sleep(2000);
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		sleep(5000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, "Masculino");
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("el dni figura en lista de fraude"));
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DiagnosticoInconvenientes", }, dataProvider = "Diagnostico")
	public void TS105453_CRM_Movil_Repro_Autogestion_0800_La_Linea_esta_muda_No_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105453";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		searchAndClick(driver, "Diagn\u00f3stico de Autogesti\u00f3n");
		tech.listadoDeSeleccion("0800", "0800-444-4100 (Asistencia Tienda Online)", "La l\u00ednea esta muda");
		sleep(4000);
		System.out.println(tech.verificarNumDeGestion());
		//driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/td[3]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Informada"));	
	}
	@Test (groups = {"GestionesPerfilOficina","ProblemaRecarga","E2E","Ciclo3"}, dataProvider="CuentaProblemaRecarga")
	public void TS104350_CRM_Movil_REPRO_Problemas_con_Recarga_Presencial_Tarjeta_Scratch_Caso_Nuevo_Inactiva(String sDNI, String sLinea) {
		imagen = "TS104350";
		boolean gestion = false, error = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(8000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009527");
		driver.findElement(By.id("PIN")).sendKeys("0872");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(5000);
		WebElement msj =driver.findElement(By.xpath("//*[@class='vlc-slds-inline-control__label' and contains(text(), 'Estado de la tarjeta: ')]"));
		System.out.println(msj.getText());
		Assert.assertTrue(msj.findElement(By.xpath("//*[@id='PrepaidCardStatusLabel']//label/strong")).getText().equalsIgnoreCase("Activa")); 
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS124406_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS124406";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cuenta = driver.findElement(By.className("header-left")).getText();
		System.out.println(cuenta);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(15000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		boolean saldo = false, comprobantes = false, pagos= false;
		WebElement sald = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont")).findElement(By.tagName("b"));
		sald.isDisplayed();
		saldo=true;
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large"));
		comprobante.isDisplayed();
		comprobantes= true;
		WebElement pago = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table"));
		pago.isDisplayed();
		pagos= true;
		Assert.assertTrue(saldo && comprobantes && pagos);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS124411_CRM_MovilREPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Detalle_ampliado_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS124411";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[@class='slds-p-bottom--small']/table/tbody/tr/td/div/a/i"));
		detaFactu.click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		Assert.assertTrue(driver.findElements(By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).get(0).isDisplayed());
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS124414_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Descarga_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS124414";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[@class='slds-p-bottom--small']/table/tbody/tr/td/div/a/i"));
		detaFactu.click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-float--right")));
		WebElement imprimir = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton"));
		WebElement descargar = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton2"));
		Assert.assertTrue(imprimir.isDisplayed() && descargar.isDisplayed());
		sleep(5000);
		descargar.click();
		sleep(5000);
		String downloadPath = "C:\\Users\\Quelys\\Downloads";
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+downloadPath);
			System.out.println("Descargo Archivo");
			} catch (IOException ee) {
			ee.printStackTrace();
			}
		Assert.assertTrue(scp.isFileDownloaded(downloadPath,"B00785"), "Failed to download Expected document");
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS124415_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Impresion_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS124415";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[@class='slds-p-bottom--small']/table/tbody/tr/td/div/a/i"));
		detaFactu.click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-float--right")));
		WebElement imprimir = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton"));
		WebElement descargar = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton2"));
		Assert.assertTrue(imprimir.isDisplayed() && descargar.isDisplayed());
		imprimir.click();
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		Assert.assertTrue(tabs2.size()>1);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS129473_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Pago_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS129473";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElements(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).get(1).getLocation().y+" )");
		sleep(5000);
		WebElement detapagos = driver.findElement(By.xpath("//*[@class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-bottom--small'] //b[contains(text(), 'Pagos')] /.. /.. /.. //*[@class='slds-p-bottom--small'] //tr[3] //td[5] //a"));
		detapagos.click();
		sleep(15000);
		Assert.assertTrue(driver.findElement(By.className("secondaryFont")).getText().contains(cuenta));
		
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS129475_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Nota_de_Credito_Debito_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS129475";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta corriente");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detaFactu = driver.findElement(By.xpath("//*[contains(text(),'NCBR')]/../following-sibling::*//div/a"));
		detaFactu.click();
		sleep(5000);
		//Assert.assertTrue(///Bug... no muestra la informacion de la Nota de Credito);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119275_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_ANTENA_ROJO_Evento_Masivo_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119275";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"MassiveIncidentMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119279_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_CONCILIAR_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119279";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		cCC.obligarclick(driver.findElements(By.className("borderOverlay")).get(0));
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		sleep(30000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"IncorrectCategoriesMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		driver.findElement(By.xpath("//*[@id='Case_body']/table/tbody/tr[2]/th/a")).click();		
		sleep(5000);
		boolean a = false;
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ep")));
		List <WebElement> texto = driver.findElements(By.className("dataCol"));
		for(WebElement x : texto) {
			if(x.getText().toLowerCase().contains("en proceso")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119283_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_navegar_Antena_rojo_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119283";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(8000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"MassiveIncidentMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119267_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_Sin_conciliar_ni_desregistrar_Envio_de_configuracion(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119267";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		//buscarYClick(driver.findElements(By.className("borderOverlay")), "contains", "enviar");
		cCC.obligarclick(driver.findElements(By.className("borderOverlay")).get(2));
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"ConfigurationCaseMessage\"]/div/p/p/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119286_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_Navega_con_lentitud_Fuera_del_area_de_cobertura_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119286";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "Navega lento");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"OutOfCoverageMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119183_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_Servicio_con_suspencion(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119183";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(15000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		String caso = driver.findElement(By.xpath("//*[@id='OutOfCoverageMessage']/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","DiagnosticoInconvenientes"}, dataProvider = "Diagnostico")
	public void TS119178_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_Evento_Masivo(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119178";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(15000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		page.seleccionarPreguntaFinal("No, sigue con inconvenientes");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		String caso = driver.findElement(By.xpath("//*[@id='OutOfCoverageMessage']/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta Masiva", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119266_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_sin_rellamado_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119266";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(8000);
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		String cuota = driver.findElement(By.xpath("//*[@id=\"AvailableDataQuotaDisplay\"]/div/p/div/p[2]/span")).getText();
		String saldo = driver.findElement(By.xpath("//*[@id=\"AvailableDataBalanceDisplay\"]/div/p/div/p[2]/span")).getText();
		System.out.println("Saldo: " + saldo);
		System.out.println("Cuota: " + cuota);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		Tech.categoriaRed("No son las antenas (Verde)");
		try {
			buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		}
		catch (Exception x) {		
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SignalValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("SignalValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		List <WebElement> opcion = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
		for(WebElement x : opcion ) {
			if(x.getText().toLowerCase().equals("si, enviar configuraci\u00f3n")) {
				x.click();
			}
		}
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"MobileConfigSendingMessage\"]/div/p/h1/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119221_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas_Sin_Locacion_Evento_Masivo(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119221";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select(driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo realizar ni recibir llamadas");
		//selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		String saldo = driver.findElement(By.xpath("//*[@id=\"AvailableVoiceBalanceDisplay\"]/div/p/div/p[2]/span")).getText();
		System.out.println("El saldo es: " +saldo);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		String informacion = driver.findElement(By.xpath("//*[@id=\"CommercialInfo\"]/div/p/div")).getText();
		System.out.println(informacion);
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("AddressSection_nextBtn")));
		Tech.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		WebElement evento = driver.findElement(By.id("MassiveIncidentLookUp"));
		evento.click();
		evento.sendKeys("Evento Masivo");
		driver.findElement(By.id("AddressSection_nextBtn")).click();
		sleep(8000);
		String caso = driver.findElement(By.xpath("//*[@id=\"MassiveIncidentMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
}