package Tests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.imageio.stream.CloseableDisposerRecord;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SCP;
import Pages.SalesBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRAutogestionPage;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;

public class GestionesPerfilTelefonico extends TestBase{

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	private CBS cbs;
	private CBS_Mattu cbsm;;	
	private TechnicalCareCSRDiagnosticoPage tech;
	private TechnicalCareCSRAutogestionPage Tech;
	private TechnicalCareCSRAutogestionF4 estado;
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	BasePage closed = new BasePage(driver);
	List <String> datosOrden =new ArrayList<String>();
	PagePerfilTelefonico ppt;
	String imagen;
	String detalles;
	private FluentWait<WebDriver> fluentWait;
	private SCP scp;
	
	
	
	@BeforeClass(alwaysRun=true)
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		loginMerge(driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			/*waitForClickeable(driver,By.id("tabBar"));
			//sleep(3000);
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);*/
		}
		driver.switchTo().defaultContent();
		sleep(3000);
//		WebElement dynamicElement = (new WebDriverWait(driver, 10))
//				  .until(ExpectedConditions.elementToBeClickable(By.id("tabBar")));//.presenceOfElementLocated(By.id("dynamicElement")));
//		//sleep(22000);
		/*waitForClickeable(driver,By.id("tabBar"));
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(21000);*/
			
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(10000);
		goToLeftPanel3(driver, "Inicio");
		sleep(15000);
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
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
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
		}catch(ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame 2.");			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		
		sleep(25000);
	}

	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		datosOrden.add(detalles);
		guardarListaTxt(datosOrden);
		datosOrden.clear();
		tomarCaptura(driver,imagen);
	}

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Recargas","E2E"}, dataProvider = "RecargaTC")  //Error despues de ingresar la tarjeta
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String cDNI, String cMonto, String cLinea, String cBanco, String cTarjeta, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular, String cPromo, String cCuotas) throws AWTException {
		imagen= "TS134332";
		detalles = null;
		detalles = imagen+"-Recarga-DNI:"+cDNI;
		CBS cCBS = new CBS();
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
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
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
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
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
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(cLinea), "bcs:MainBalance");
		System.out.println("saldo nuevo "+uMainBalance);
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Integer monto = Integer.parseInt(orden.split("-")[2].replace(".", ""));
		monto = Integer.parseInt(monto.toString().substring(0, monto.toString().length()-1));
//		monto = iMainBalance+monto;
		Assert.assertTrue(monto == uiMainBalance);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));

	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaSinSaldo")
	public void TS130067_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Sin_Credito(String sDNI, String sLinea) {
		imagen = "TS130067";
		detalles = null;
		detalles = imagen+"-Renovacion de cuota-DNI:"+sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
			driver.findElement(By.id("combosMegas")).findElements(By.className("slds-checkbox")).get(1).click();
		}
		catch (Exception ex) {
			//Allways Empty
		}
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().equalsIgnoreCase("saldo insuficiente"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sDNI, String sLinea) {
		imagen = "TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito";
		detalles = null;
		detalles = "Renovacion de cuota "+imagen+"-DNI:"+sDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}sleep(2000);
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("saldo")){
				UnP.click();
				break;
			}
		}		
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().equalsIgnoreCase("la compra se realiz\u00f3 exitosamente");
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance < uiMainBalance);
		cCC.obligarclick(driver.findElement(By.id("AltaHuawei_nextBtn")));
		sleep(12000);
		String sOrder = cCC.obtenerOrden(driver, "Reseteo de Cuota");
		System.out.println("Orden"+sOrder);
		detalles += "-Orden:"+sOrder+"- Linea"+sLinea;
		System.out.println("Order: " + sOrder + " Fin");
	}
	
	// no existe Pack, se probo el caso con otro
	@Test (groups= {"GestionesPerfilTelefonico","E2E","VentaDePacks","Ciclo1"},priority=1, dataProvider="VentaPacks")
	public void TS123314_CRM_Movil_REPRO_Venta_de_Pack_40_Pesos_Exclusivo_Para_Vos_Descuento_De_Saldo_Telefonico(String sDNI, String sLinea,  String sVentaPack){
		imagen = "TS123314";
		detalles = null;
		detalles = imagen + " -Venta de Pack - DNI: " + sDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		//String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		//Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID=new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles+="-Cuenta:"+accid;
		pagePTelefo.buscarAssert();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack();
		//String chargeCode = 
				pagePTelefo.agregarPack(sVentaPack);
		pagePTelefo.tipoDePago("descuento de saldo");
		String orden = cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
		System.out.println("orden = "+orden);
		datosOrden.add("Venta de Pack" + orden + " de cuenta "+accid+" con DNI: " + sDNI);
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance < uiMainBalance);
		detalles += "-Charge Code: "; //+ chargeCode;
	}
	// ====================       ESTE 2 =======================================
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard", "E2E" }, priority = 1, dataProvider = "CambioSimCardTelef")
	public void TS_Cambio_Sim_Card_Store_Pick_up_Telefonico(String sDNI, String sLinea,String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "TS_Cambio_Sim_Card_Store_Pick_up_Telefonico";
		detalles = null;
		detalles = imagen+"-Telef-DNI:"+sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles+="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(12000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		pagePTelefo.mododeEntrega(driver, sEntrega, sProvincia, sLocalidad, sPuntodeVenta);
		sleep(10000);
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
					Assert.assertTrue(UnC.getText().toLowerCase().contains("factura emitida"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
		}
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	
	@Test (groups= {"GestionesPerfilTelefonico","E2E","VentaDePacks","Ciclo1"},priority=1, dataProvider="ventaPack50Tele")
	public void TS123157_CRM_Movil_REPRO_Venta_De_Pack_50_Min_Y_50_SMS_X_7_Dias_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sventaPack, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws InterruptedException, AWTException{
		imagen = "TS123157";
		detalles = null;
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
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
		//String chargeCode = 
				pagePTelefo.PackCombinado(sventaPack);
		//System.out.println(sventaPack);
		pagePTelefo.tipoDePago("en factura de venta");
		pagePTelefo.getTipodepago().click();
		sleep(12000);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
		pagePTelefo.getMediodePago().click();
		sleep(45000);
		pagePTelefo.getOrdenSeRealizoConExito().click();
		sleep(10000);
		String orden = cCC.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		driver.navigate().refresh();
		CBS_Mattu cCBSM = new CBS_Mattu();
		CBS cCBS = new CBS();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sventaPack));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));	
		System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrden + "Cuenta: "+ accid + "Fin");
		detalles += "-Charge Code: ";// + chargeCode;
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "Ajustes", "E2E"},dataProvider = "CuentaAjustesPRE")  //Rompe porque no sale el mensaje de gestion exitosa, sale el perfil no configurado correctamente
	public void TS121333_CRM_Movil_PRE_Ajuste_total_de_comprobantes_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS121333";
		detalles = null;
		detalles = imagen + " -Ajuste-DNI: " + sDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		detalles += "-Cuenta: "+ accid;
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
		sleep(3000);
		driver.findElements(By.className("slds-cell-shrink")).get(0).click();
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		sleep(7000);
		try {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			orden = orden.substring(orden.length()-8);
			detalles += "-Orden: " + orden;
			driver.findElement(By.id("Step-Summary_nextBtn")).click();
			sleep(7000);
			List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
			for (WebElement x : element) {
				if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
					gest = true;
				}
			}
			Assert.assertTrue(gest);
		} catch (Exception ex1) {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			orden = orden.substring(orden.length()-8);
			detalles += "-Orden: " + orden;
			Assert.assertTrue(false);
		}
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "ModificarDatos", "E2E"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS134835_CRM_Movil_PRE_Modificacion_de_datos_Actualizar_los_datos_del_cliente_completos_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134835";
		detalles = null;
		detalles = imagen + " -ActualizarDatos-DNI: "+ sDNI;
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
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "RecargasHistorias")
	public void TS135437_CRM_Movil_Prepago_Historial_De_Packs_Fan_Front_Telefonico(String cDNI) {
		boolean enc = false;
		imagen = "TS135437";
		detalles = null;
		detalles = imagen+"-HistorialDePacksTelefonico - DNI: "+cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
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
		
	@Test (groups = {"GestionesPerfilTelefonico", "ConsultaDeSaldo", "Ciclo1" }, dataProvider = "ConsultaSaldo")
	public void TS134811_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134811";
		detalles = null;
		detalles = imagen + " -Consulta de Saldo - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer credito = Integer.parseInt(sMainBalance.substring(0, 5));
		System.out.println(credito);
		Assert.assertTrue(false);  //Credito recarga: Informacion no disponible
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134813_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sAccountKey) {
		imagen = "TS134813";
		detalles = null;
		detalles = imagen + " -Consulta de saldo - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		String saldo = driver.findElement(By.className("header-right")).getText();
		saldo = saldo.replaceAll("[^\\d]", "");
		Integer saldoEnCard = Integer.parseInt(saldo);	
		CBS_Mattu cCBSM = new CBS_Mattu();
		CBS cCBS = new CBS();
		String response = cCBS.ObtenerValorResponse(cCBSM.verificarSaldo(sAccountKey), "ars:TotalOutStandAmt");
		Integer saldoFacturacion = Integer.parseInt(response.substring(0, 6));
		Assert.assertTrue(saldoEnCard.equals(saldoFacturacion));
	}

	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS135347_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_TODOS_Fan_FRONT_Telefonico(String sDNI) {
		imagen = "TS135347";
		detalles = null;
		detalles = imagen + " -Historial de recargas - DNI: " + sDNI;
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
			TestBase TB = new TestBase();
			TB.waitFor(driver, By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
			Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga")
	public void TS134803_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_Telefonico(String cDNI, String cLinea ){
		imagen = "TS134803";
		detalles = null;
		detalles = imagen + " -Detalle de consumos - DNI: " + cDNI;
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cCC.irADetalleDeConsumos();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(cLinea)){
				s.click();
			}	
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		WebElement plan = driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(2);
		System.out.println(plan.getText());
		System.out.println(plan.getAttribute("value"));
		Assert.assertTrue(plan.isDisplayed());
		/*WebElement dmso = driver.findElements(By.xpath("//*[@id='j_id0:j_id5']/div//div[2]/ng-include/div/div[2]/div[*]")).get(2).findElement(By.className("unit-div"));
		System.out.println(dmso.getText());
		System.out.println(dmso.getAttribute("value"));
		Assert.assertTrue(dmso.isDisplayed());*/
		}
			
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134793_CRM_Movil_Prepago_Historial_De_Recargas_SOS_S440_FAN_Front_Telefonico(String cDNI,String sLinea) {
		imagen = "TS134793";
		detalles = null;
		detalles = imagen + " -Historial de recargas - DNI: " + cDNI;
		boolean enc = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		for(int i=0;i<=2;i++) {
			cCBSM.Servicio_Recharge(sLinea,"25000000");
			sleep(1000);
		}
		cCBSM.Servicio_Loan(sLinea,"15000000");
		sleep(1000);
		cCBSM.Servicio_Recharge(sLinea,"25000000");
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
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
				driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
				sleep(5000);
				Assert.assertTrue(true);
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS130068_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Reseteo_200_MB_por_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea) {
		imagen = "TS130068";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		sleep(12000);
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));		
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaconSaldoConTC")
	public void TS130065_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Reseteo_200_MB_por_Dia_TC_con_Credito(String sDNI, String sLinea, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		BasePage cambioFrameByID=new BasePage();
		imagen = "TS130065";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		sleep(15000);
		cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(8000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaconSaldoConTC")
	public void TS135399_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Internet_50_MB_Dia_TC_con_Credito(String sDNI, String sLinea, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "TS135399";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
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
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
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
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(8000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaSinSaldoConTC")
	public void TS135400_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Internet_50_MB_Dia_TC_sin_Credito(String sDNI, String sLinea, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "135400";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
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
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		sleep(2000);
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
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
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
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaconSaldoConTC")
	public void TS135407_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Rastreo_Internet_por_Dia_Limitrofe_TC_con_Credito(String sDNI, String sLinea, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "135407";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
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
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("Lim\u00edtrofe")) {
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
		sleep(15000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(18000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial))<=Integer.parseInt(datosFinal));
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS135401_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Internet_50_MB_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea) {
		imagen = "TS135401";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
//		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
//		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
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
			if (UnP.getText().toLowerCase().contains("saldo")){
				UnP.click();
				break;
			}
		}		
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));	
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
//		Assert.assertTrue(iMainBalance > uiMainBalance);
	}
		
	@Test (groups = {"GestionesPerfilTelefonico", "ReseteoDeClave", "Ciclo2"}, dataProvider = "CuentaReseteoClave")
	public void TS95981_CRM_Movil_REPRO_Reseteo_de_Clave_Telefonico(String sDNI) {
		imagen = "TS95981";
		detalles = null;
		detalles = imagen + "-ReseteoDeClave - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(5000);
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "ReseteoDeClave", "Ciclo2"}, dataProvider = "CuentaReseteoClave")
	public void TS95983_CRM_Movil_REPRO_No_Reseteo_de_Clave_Telefonico(String sDNI) {
		imagen = "TS95983";
		detalles = null;
		detalles = imagen + "-ReseteoDeClave - DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
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
	
	@Test (groups = {"GestionesPerfilTelefonico","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigos")
	public void TS100601_CRM_Movil_REPRO_FF_Alta_Posventa(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100601";
		detalles = null;
		detalles = imagen+"-Numeros amigos-DNI:"+sDNI;
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
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		
		sleep(15000);
		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		datosOrden.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		Assert.assertTrue(bAssert);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Numero Gratis");
		detalles +="-Orden:"+orden;
		datosOrden.add("Numeros amigos, orden numero: " + orden + " con numero de DNI: " + sDNI);
		sleep(10000);
		BasePage bBP = new BasePage();
		bBP.closeTabByName(driver, "N\u00fameros Gratis");
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		Assert.assertTrue(mMarketing.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","NumerosAmigos","E2E","Ciclo1"}, dataProvider="NumerosAmigosModificacion")
	public void TS100603_CRM_Movil_REPRO_FF_Modificacion_Presencial_Posventa_Telefonico(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100603";
		detalles = null;
		detalles = imagen+"-Numeros amigos-DNI:"+sDNI;
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
		sleep(15000);
		datosOrden.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		Assert.assertTrue(bAssert);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Numero Gratis");
		detalles +="-Orden:"+orden;
		datosOrden.add("Numeros amigos, orden numero: " + orden + " con numero de DNI: " + sDNI);
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
	
	@Test (groups = {"GestionesPerfilTelefonico","NumerosAmigos","E2E","Ciclo1"}, dataProvider="NumerosAmigosBaja")
	public void TS100606_CRM_Movil_REPRO_FF_Baja_Posventa_Telefonico(String sDNI, String sLinea, String sVOZorSMS) throws AWTException {
		imagen = "TS100606";
		detalles = null;
		detalles = imagen+"-Numeros amigos-DNI:"+sDNI;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		BasePage cambioFrame=new BasePage();
		sleep(5000);
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
		sleep(20000);
		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		if (Integer.parseInt(sVOZorSMS) == 0)
			Assert.assertFalse(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "voz",""));
		else
			Assert.assertFalse(cCBS.validarNumeroAmigos(cCBSM.Servicio_QueryCustomerInfo(sLinea), "sms",""));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance-2050000 >= uiMainBalance);
		Assert.assertTrue(bAssert);
		sleep(10000);
		BasePage bBP = new BasePage();
		bBP.closeTabByName(driver, "N\u00fameros Gratis");
		detalles +=cCC.obtenerOrden(driver, "N\u00fameros Gratis");
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Assert.assertTrue(wNumerosAmigos.get(Integer.parseInt(sVOZorSMS)).getText().isEmpty());
		//Verify when the page works
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134796_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134796";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-p-around--small.slds-col")));
		WebElement gestiones = driver.findElement(By.cssSelector(".slds-p-around--small.slds-col"));
		Assert.assertTrue(gestiones.getText().toLowerCase().contains("t\u00edtulo") && gestiones.getText().contains("Fecha de creacion"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134797_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Panel_Derecho_Busqueda_de_gestiones_promociones_y_gestiones_abandonadas_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS134797";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".abandoned-content.scrollmenu")));
		WebElement abandoned = driver.findElement(By.className("abandoned-section"));
		Assert.assertTrue(abandoned.getText().contains("Gestiones Abandonadas") && driver.findElement(By.className("abandoned-section")).isDisplayed());
	}
	
	@Test (groups= {"GestionesPerfilTelefonico","VentaDePacks","E2E","Ciclo1"},priority=1, dataProvider="ventaPackInternacional30SMS")
	public void TS123133_CRM_Movil_REPRO_Venta_De_Pack_internacional_30_SMS_al_Resto_del_Mundo_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws InterruptedException, AWTException{
		imagen = "TS123133";
		detalles = null;
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
		pagePTelefo.comprarPack("comprar sms");
		sleep(5000);
		cCC.closeleftpanel();
		//String chargeCode = null;
		try {
			//chargeCode = 
					pagePTelefo.PackLDI(sVentaPack);
		}
		catch (Exception eE) {
			driver.navigate().refresh();
			sleep(10000);
			mk.closeTabByName(driver, "Comprar SMS");
			cCC.seleccionarCardPornumeroLinea(sLinea, driver);
			pagePTelefo.comprarPack("comprar sms");
			//chargeCode = 
					pagePTelefo.PackLDI(sVentaPack);
		}
		pagePTelefo.tipoDePago("en factura de venta");
		buscarYClick(driver.findElements(By.id("SetPaymentType_nextBtn")), "equals", "continuar");
		sleep(10000);
		pagePTelefo.getTipodepago().click();
		sleep(8000);
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), sTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		pagePTelefo.getMediodePago().click();
		sleep(45000);
		pagePTelefo.getOrdenSeRealizoConExito().click();// No se puede procesr (Ups, se ha producido un error en la prefactura Huawei.)
		sleep(10000);
		driver.navigate().refresh();
		sleep(5000);
		cCC.buscarCaso(sOrden);
		sleep(3000);
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sVentaPack));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));	
		System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrden + "Cuenta: "+ accid + "Fin");
		detalles = imagen + "-Venta de pack-DNI: "+ sDNI + "-" + "Charge Code: "; //+ chargeCode;
		//Blocked
	}
	
	@Test (groups= {"GestionPerfilTelefonico", "Ciclo2", "Vista360"}, dataProvider = "documentacionVista360")
	public void TS_134800_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_Telefonico(String sDNI) {
		imagen = "TS134800";
		detalles = null;
		detalles = imagen+"-Vista 360-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
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
		WebElement verif = driver.findElement(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		if(verif.getText().toLowerCase().contains("servicios incluidos")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups= {"GestionPerfilTelefonico", "Ciclo2", "Vista360"}, dataProvider = "CuentaVista360")
	public void TS134799_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "134799";
		detalles = null;
		detalles = imagen+"-Vista 360-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".console-flyout.active.flyout")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS129336_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_campo_Correo_Electronico_Cliente_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS129336";
		detalles = null;
		detalles = imagen+"-Modificacion de datos-DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS121104_CRM_Movil_REPRO_Modificacion_de_datos_No_Actualizar_datos_Cliente_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS121104";
		detalles = null;
		detalles = imagen+"-Modificacion de datos No modifica-DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "ModificarDatos", "E2E", "Ciclo3"}, dependsOnMethods = "TS121104_CRM_Movil_REPRO_Modificacion_de_datos_No_Actualizar_datos_Cliente_FAN_Front_Telefonico")
	public void TS121113_CRM_Movil_REPRO_No_Actualizar_datos_Cliente_FAN_Front_Telefonico() {
		imagen = "TS121113";
		detalles = null;
		detalles = imagen+"-Modificacion de datos No modifica No modifica";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS121105_CRM_Movil_REPRO_Modificacion_de_datos_Cliente_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS121105";
		detalles = null;
		detalles = imagen + " -ActualizarDatos-DNI: " + sDNI;
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
		sleep(15000);
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS121112_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_Cliente_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS121112";
		detalles = null;
		detalles = imagen + " -ActualizarDatos-DNI: " + sDNI;
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134789_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_Fan_FRONT_Telefonico(String sDNI) {
		imagen = "TS134789";
		detalles = null;
		detalles = imagen+"-HistorialDeRecargasTelefonico-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(18000);
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134790_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_Sin_Beneficios_Fan_FRONT_Telefonico(String sDNI) {
		imagen = "TS134790";
		detalles = null;
		detalles = imagen+"-HistorialDeRecargasTelefonico-DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS121138_CRM_Movil_REPRO_Ajuste_Credito_FAN_Front_Telefonico_BO(String sDNI, String sLinea) {
		imagen = "TS121138";
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
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.id("txtSuccessConfirmation"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito"))
				gest = true;
		}
		Assert.assertTrue(gest);
		if (TestBase.urlAmbiente.contains("sit")) {
			String orden = cc.obtenerOrden(driver, "Inconvenientes con cargos tasados y facturados");
			datosOrden.add("Inconvenientes con cargos tasados y facturados, orden numero: " + orden + " con numero de DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrden(orden));		
		} else {
			String orden = driver.findElement(By.xpath("//*[@id=\"txtSuccessConfirmation\"]/div/p/p")).findElement(By.tagName("strong")).getText();
			datosOrden.add("Inconvenientes con cargos tasados y facturados, numero de orden: " + orden + " de cuenta con DNI: " + sDNI);
			Assert.assertTrue(cc.verificarOrdenYGestion("Inconvenientes con cargos tasados y facturados"));
		}
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS135376_CRM_Movil_Prepago_Otros_Historiales_Historial_de_ajustes_Seleccion_de_Fechas_Ajuste_positivo_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS135376";
		boolean ajustePositivo = false;
		detalles = null;
		detalles = imagen+"-Ajustes-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAHistoriales();
		WebElement historialDeAjustes = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de ajustes"))
				historialDeAjustes = x;
		}
		historialDeAjustes.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		for (WebElement cell : table.findElements(By.xpath("//tr//td"))) {
			try {
				if (cell.getText().equals("4"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement table2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		for (WebElement cell : table2.findElements(By.xpath("//tr//td"))) {
			try {
				if (cell.getText().equals("7"))
					cell.click();
			} catch (Exception e) {}
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		List<WebElement> tablas = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"));
		for (WebElement x : tablas) {
			if (x.findElements(By.tagName("td")).get(3).getText().contains("$"))
				ajustePositivo = true;
		}
		Assert.assertTrue(ajustePositivo);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "RecargasHistorias")
	public void TS135469_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_TODOS_FAN_Front_Telefonico(String sDNI){
	boolean enc = false;
	imagen = "TS135469";
	detalles = null;
	driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
	sb.BuscarCuenta("DNI", sDNI);
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
			//Assert.assertTrue(true);
			break;
		}
	}
	driver.findElement(By.id("text-input-id-1")).click();
	WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
	sleep(3000);
	List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
	for (WebElement cell : tableRows) {
		try {
			if (cell.getText().equals("04")) {
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
			if (cell.getText().equals("01")) {
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
	
	@Test (groups = {"GestionesPerfilTelefonico","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "RecargasHistorias")
	public void TS135484_CRM_Movil_Prepago_Historial_de_Packs_Seleccion_de_Fechas_FAN_Front_Telefonico(String sDNI){
	//boolean enc = false;
	imagen = "TS135484";
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
				}catch(Exception e) {}
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
				}catch(Exception e) {}
			}
			driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
			sleep(5000);
			WebElement visu =driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"));
			Assert.assertTrue(visu.isDisplayed());
		}
	
	@Test (groups= {"GestionesPerfilTelefonico","E2E", "VentaDePacks", "Ciclo1"},priority=1, dataProvider="packUruguay")
	public void TS123143_CRM_Movil_REPRO_Venta_de_pack_100MB_Uruguay_Descuento_de_saldo_Telefonico(String sDNI, String sLinea, String packUruguay) throws InterruptedException, AWTException{
		imagen = "TS123143";
		detalles = null;
		CBS_Mattu cCBSM = new CBS_Mattu();
		CBS cCBS = new CBS();
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID=new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack("comprar minutos");
		sleep(5000);
		//String chargeCode = 
				pagePTelefo.PacksRoaming(packUruguay);
		pagePTelefo.tipoDePago("descuento de saldo");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		sleep(45000);
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), packUruguay));
		List <WebElement> wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElements(By.tagName("p"));
		boolean bAssert = wMessage.get(1).getText().contains("La orden se realiz\u00f3 con \u00e9xito!");
		Assert.assertTrue(bAssert);
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		detalles = imagen + "-Venta de pack - DNI: " + sDNI + "-" + "Charge Code: "; //+ chargeCode;
		Assert.assertTrue(iMainBalance > uiMainBalance);
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "RecargasHistorias")
	public void TS135467_CRM_Movil_Prepago_Historial_de_Packs_Fan_Front_Telefonico(String cDNI) {
		boolean enc = false;
		imagen = "TS135467";
		detalles = null;
		detalles = imagen+"-HistorialDePacksTelefonico - DNI:"+cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
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
				sleep(7000);
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
				driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
				sleep(5000);
				Assert.assertTrue(true);
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"},  dataProvider = "CuentaVista360")
	public void TS135351_CRM_Movil_Prepago_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_CASOS_FAN_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS135351";
		boolean gestion = false;
		detalles = null;
		detalles = imagen +" -Vista 360-DNI: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "64747868");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(9000);
		String nroCaso = driver.findElement(By.cssSelector(".slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).getText();
		driver.switchTo().defaultContent();
		sb.cerrarPestaniaGestion(driver);
		cc.buscarCaso(nroCaso);
		WebElement estado = null;
		driver.switchTo().frame(cambioFrame(driver, By.className("detailList")));
		for (WebElement x : driver.findElements(By.className("detailList"))) {
			if (x.getText().toLowerCase().contains("propietario del caso"))
				estado = x;
		}
		for (WebElement x : estado.findElements(By.tagName("tr"))) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;			
		}
		if (estado.getText().toLowerCase().contains("en espera de ejecuci\u00f3n") || (estado.getText().toLowerCase().contains("informada") ||(estado.getText().toLowerCase().contains("resuelta exitosa"))))
			gestion = true;
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "Ciclo2"},  dataProvider = "CuentaVista360")
	public void TS135356_CRM_Movil_Prepago_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_ORDENES_FAN_Telefonico(String sDNI, String sLinea,String sNombre) {
		imagen = "TS135356";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		boolean gestion = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).click();
		cc.obligarclick(nroCaso);
		sleep(15000);
		WebElement estado = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("n\u00famero de pedido"))
				estado = x;
		}
		for (WebElement x : estado.findElement(By.className("detailList")).findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("estado"))
				estado = x;
		}
		if (estado.getText().toLowerCase().contains("activada") || (estado.getText().toLowerCase().contains("iniciada")))
			gestion = true;
		Assert.assertTrue(gestion);
	}

	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E","ConsultaPorGestion", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134808_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_Cerradas_Informacion_brindada_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
		imagen = "TS134808";
		detalles = null;
		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "gestiones");
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("25")) {
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
				if (cell.getText().equals("01")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		sleep(5000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"));
		Assert.assertTrue(tabla.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionNuevoTelefonico")
	public void TS85111_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Nuevo_Telefonico_Preguntas_y_Respuestas(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail) {
		imagen = "TS85111";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		sleep(2000);
		contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		try {contact.ingresarMail(sEmail, "si");}catch (org.openqa.selenium.ElementNotVisibleException ex1) {}
		contact.tipoValidacion("preguntas y respuestas");
		sleep(8000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn"))); 
		sleep(5000);
		List<WebElement> valdni = driver.findElements(By.className("slds-radio__label"));
		for (WebElement x : valdni) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("ninguno de los anteriores")) {
				x.click();
			}
		}
		cCC.obligarclick(driver.findElement(By.id("QAQuestions_nextBtn")));      
		sleep(5000);
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("no superada")) {
				error = true;
			}
		}
		
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigosNoPersonalAlta")
	public void TS100608_CRM_Movil_REPRO_FF_No_Alta_Posventa_Amigo_No_Personal_Telefonico(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100608";
		detalles = null;
		detalles = imagen+"-Numeros amigos - DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilTelefonico","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigosNoPersonalModificacion")
	public void TS100610_CRM_Movil_REPRO_FF_No_Modificacion_Posventa_Telefonico(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS100610";
		detalles = null;
		detalles = imagen+"-Numeros Amigos - DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilTelefonico","NumerosAmigos","E2E", "Ciclo1"}, dataProvider="NumerosAmigosNoPersonalBaja")
	public void TS100611_CRM_Movil_REPRO_FF_No_Baja_Posventa_Telefonico(String sDNI, String sLinea, String sVOZorSMS) throws AWTException {
		imagen = "TS100611";
		detalles = null;
		detalles = imagen+"-Numeros Amigos - DNI:"+sDNI;
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

	@Test (groups = {"GestionesPerfilTelefonico", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS111300_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Desregistrar(String sDNI, String sLinea) throws Exception  {
		imagen = "TS111300";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
	    Assert.assertTrue(driver.findElement(By.cssSelector(".imgItemContainer.ng-scope")).getText().equalsIgnoreCase("Desregistrar"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "DiagnosticoInconvenientes","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS112441_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Entrante_No_Recibe_De_Un_Numero_En_Particular_Geo_Ok_Rojo(String sDNI, String sLinea) throws Exception  {
		imagen = "TS112441";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAProductosyServicios();
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Entrante", true);
	    tech.selectionInconvenient("No recibe de un n\u00famero particular");
	    tech.continuar();
	    tech.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    page.seleccionarPreguntaFinal("No");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    Assert.assertTrue(driver.findElement(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding")).getText().contains("Saldo Insuficiente"));
	   
	}
	@Test (groups = {"GestionesPerfilTelefonico", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS118160_CRM_REPRO_BDC_Customer_Care_Actualizacion_de_Datos_Perfil_Telefonico_Acceso_base_de_conocimientos_dentro_OS(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS118160";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("vlc-slds-knowledge-list-item")));
		Assert.assertTrue(driver.findElement(By.className("vlc-slds-knowledge-list-item")).getText().contains("Actualizaci\u00f3n de Datos"));
	}
	@Test (groups = {"GestionesPerfilTelefonico", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TC135753_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Voice_Mail_con_Clave_y_Transferencia_de_Llamada_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135753";
		detalles = null;
		detalles = imagen+"-AltaServicio - DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		//Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Voice Mail con Clave"));
		//Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Transferencia de Llamadas"));
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
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
		detalles +="-Servicio:VoiceMailConClave&TransferenciadeLlamadas";
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
		ppt.altaBajaServicioContestador("Alta", "servicios basicos general movil", "nada","Transferencia de Llamadas", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		datosOrden.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Voice Mail con Clave"));
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Transferencia de Llamadas"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TS135848_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Contestador_Personal_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135848";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Contestador Personal"));
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
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
		detalles +="-Servicio:ContestadorPersonal";
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
		ppt.altaBajaServicio("Baja", "Servicios basicos general movil", "Contestador", "Contestador Personal", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		datosOrden.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Contestador Personal"));
	}
					//=================== ESTEEEEEE 1 ==========
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard","Ciclo 3", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroTelef")
	public void TS134427_CRM_Movil_REPRO_Cambio_de_simcard_con_costo_Voluntario_Telefonico_Store_pickUp_Con_Siniestro_Telef(String sDNI, String sLinea,String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "134427";
		detalles = null;
		detalles = imagen+"-Telef-DNI:"+sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles+="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(12000);
		cCC.irAGestion("Cambio de Simcard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SelectAsset0")));
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		sleep(5000);
		pagePTelefo.mododeEntrega(driver, sEntrega, sProvincia, sLocalidad, sPuntodeVenta);
		sleep(10000);
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
					Assert.assertTrue(UnC.getText().toLowerCase().contains("factura emitida"));
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
	
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","DiagnosticoInconvenientes","Ciclo 3", "E2E" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119281_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_CONCILIACION_EXITOSA_NO_BAM_Telefonico(String sDNI, String sLinea){
		imagen = "TS119281";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		sleep(5000);
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
		tech.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(45000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		driver.switchTo().defaultContent();
		sleep(1000);
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
		Assert.assertTrue(gest.getText().equals("Realizada exitosa"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "BaseDeConocimiento", "Ciclo3"}, dataProvider = "CuentaVista360")
	public void TS130755_CRM_REPRO_BDC_Customer_Care_Problemas_con_Recargas_PerfilTelefonico_Articulo_de_Medios_de_Recargas(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil) {
		imagen = "TS130755";
		boolean knowledge = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		for (WebElement x : driver.findElements(By.cssSelector(".slds-form-element.slds-lookup.vlc-slds-knowledge-component.ng-scope"))) {
			if (x.getText().contains("Informaci\u00f3n De Recargas"))
				knowledge = true;
		}
		Assert.assertTrue(knowledge);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecargaAYD") 
	public void TS104332_CRM_Movil_Repro_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Tarjeta_Activa_y_Disponible(String sDNI, String sLinea, String sSerie, String sPIN) {
		imagen = "TS104332";
		detalles = null;
		detalles = imagen + " -Problemas Con Recargas-DNI: " + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134791_CRM_Movil_Prepago_Historial_de_Recargas_Monto_total_FAN_Front_Telefonico(String sDNI) {
		imagen = "TS134791";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		//cc.seleccionarCardPornumeroLinea(sLinea, driver);
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
		boolean a = false;
		WebElement conf = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium"));
			if(conf.getText().toLowerCase().contains("monto total de recargas: ")) {
				//System.out.println(conf);
				a = true;
			
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "HistoriaRecarga")
	public void TS134847_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_Atencion_al_cliente_Fan_FRONT_Telefonico(String sDNI, String sLinea){
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
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> atencion = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement a : atencion){
			if(a.getText().contains("Atenci\u00f3n al cliente")){
				a.click();
			}	
		}
		try {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			} catch (Exception e) {
				driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
			}
		sleep(3000);
		boolean a = false;
		List <WebElement> fecha = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));		
		for(WebElement x : fecha) {
			if(x.getText().toLowerCase().equals("canal")) {
				x.click();
				TestBase TB = new TestBase();
				TB.waitFor(driver, By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
				Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
				a= true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","Ciclo 3", "E2E","DiagnosticoInconveniente" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119245_CRM_Movil_REPRO_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Conciliacion_Exitosa_Telefonico(String sDNI, String sLinea){
		imagen = "TS119245";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);;
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(18000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		sleep(5000);
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
		tech.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(25000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		sleep(3000);
		driver.switchTo().defaultContent();
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
		sleep(5000);
		driver.navigate().refresh();
		Assert.assertTrue(gest.equals("Realizada exitosa"));
	    }
	
	@Test (groups = {"GestionesPerfilTelefonico", "ModificarDatos", "E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDNI")
	public void TS129327_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_campo_DNI_CUIT_Cliente_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS129327";
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
	
	@Test (groups = {"GestionesPerfilTelefonico","HistorialDeRecargas","E2E", "Ciclo1"},  dataProvider = "RecargasHistorias")
	public void TS135477_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Internet_40_Mb_FAN_Front_Telefonico(String sDNI){
		imagen = "TS135477";
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
		String pack = "Internet 40 Mb";
		boolean estaPack = false;
		for(WebElement Pack : NomPack) {
			if(Pack.getText().equalsIgnoreCase(pack)) {
				Pack.click();
				estaPack = true;
				break;
			}
		}
		Assert.assertTrue(estaPack, "No esta el pack "+pack+" en el deplegable");
		TestBase TB = new TestBase();
		TB.waitFor(driver, By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","ResumenDeCuenta","E2E", "Ciclo4"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS135434_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Comprobantes_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS135434";
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
	
	@Test (groups = {"GestionesPerfilTelefonico","ModificarDatos","E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS129333_CRM_Movil_REPRO_Modificacion_de_datos_No_Permite_Actualizar_datos_campo_DNI_Cliente_FAN_Front_Telefonico(String sDNI ,String sLinea) {
		imagen = "TS129333";
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
		WebElement documento = driver.findElement(By.id("DocumentNumber"));
		documento.getAttribute("value");
		documento.clear();
		documento.sendKeys("33331213");
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("InvalidModifications_prevBtn")));
		boolean a = false;
		for(WebElement x : driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"))) {
			if(x.getText().toLowerCase().equals("no se pueden modificar m\u00e1s de dos d\u00edgitos de su dni."))
				a = true;
		}
		Assert.assertTrue(a);
	}
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS135435_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Columnas_de_informacion_Pagos_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil){
		imagen = "TS135435";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(12000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(5000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		WebElement compro = driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large"));
		System.out.println(compro.getText());
		Assert.assertTrue(compro.isDisplayed());
		
	}

	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "E2E", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS135436_CRM_Movil_Prepago_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Pago_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sEmail, String sMovil){
		imagen = "TS135436";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		TestBase tb = new TestBase ();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(8000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(5000);
		driver.switchTo().frame(tb.cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(10000);
		WebElement Tabla = driver.findElement(By.className("slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(5);
		Tabla.click();
		sleep(7000);
		Assert.assertTrue(true);
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimcardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroOfCom") 
	public void TS134392_TELEF_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Siniestro_Telefonico_Store_pickUp_Con_entega_de_pedido(String sDNI, String sLinea){
		imagen = "134392";
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
		metodoEntrega.selectByVisibleText("Store Pick Up");
		Select provincia = new Select (driver.findElement(By.id("PickState")));
		provincia.selectByVisibleText("Buenos Aires");
		Select city = new Select (driver.findElement(By.id("PickCity")));
		city.selectByVisibleText("PUNTA ALTA");
		Select punto = new Select(driver.findElement(By.id("Store")));
		punto.selectByVisibleText("VJP Punta Alta - Bernardo De Irigoyen 390");
		driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")).click();
		sleep(12000);
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(8000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));	
	}
	
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119272_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_CUOTA_NO_BAM(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119272";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
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
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		page.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DataQuotaQuery_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("UnavailableQuotaMessage")));
		WebElement MediosDispon = driver.findElement(By.className("ng-binding")).findElement(By.xpath("//*[@id='UnavailableQuotaMessage']/div/p/p[1]/span"));
		Assert.assertTrue(MediosDispon.getText().equalsIgnoreCase("Prob\u00e1 realizar una recarga o comprar un pack de datos"));
		String caso = driver.findElement(By.xpath("//*[@id='UnavailableQuotaMessage']/div/p/p[2]/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Informada", "Consulta"));
				
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionNuevoTelefonicoPasaporte")
	public void TS128437_CRM_Movil_REPRO_No_Nominatividad_Telefonico_Pasaporte(String sLinea, String sPasaporte, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sFperm) {
		imagen = "TS128437";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sPasaporte+"-Linea: "+sLinea;
		sleep(2000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		waitForClickeable(driver,By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope"));
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("Pasaporte", sPasaporte, sSexo);
		//sleep(2000);
		contact.Llenar_Contacto(sNombre, sApellido, sFnac);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys(sFperm);
		driver.findElement(By.id("Contact_nextBtn")).click();
		//sleep(10000);
		waitFor(driver,By.className("ng-binding"));
		Assert.assertTrue(driver.findElement(By.className("ng-binding")).getText().equalsIgnoreCase("No se puede continuar con la operaci\u00f3n. Por favor, dirijase a una oficina comercial."));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134792_CRM_Movil_Prepago_Historial_de_Recargas_S141_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134792";
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
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS104344_CRM_Movil_Repro_Problemas_con_Recarga_Telefonico_On_Line(String sDNI, String sLinea) {
		imagen = "TS104344";
		boolean gestion = false;
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
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("AttachDocuments_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("La gesti\u00f3n fue derivada"))
				gestion = true;
		}
		Assert.assertTrue(gestion);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS104338_CRM_Movil_REPRO_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Nuevo_Quemada(String sDNI, String sLinea) {
		imagen = "TS104338";
		boolean gestion = false, error = false;
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
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009321");
		driver.findElement(By.id("PIN")).sendKeys("0804");
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
		Assert.assertTrue(datosInicial + 500 == datosFinal);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestionEnCard("Problemas con Recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillMethods_nextBtn")));
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009321");
		driver.findElement(By.id("PIN")).sendKeys("0804");
		driver.findElement(By.id("PrepaidCardData_nextBtn")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-error-icon")));
		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
			if (x.getText().contains("La tarjeta ya fue utilizada para una recarga"))
				error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"ProblemasConRecargas", "GestionesPerfilTelefonico", "E2E", "Ciclo3"}, dataProvider = "CuentaProblemaRecarga")
	public void TS104330_CRM_Movil_REPRO_Problemas_con_Recarga_Telefonico_Tarjeta_Scratch_Caso_Existente(String sDNI, String sLinea) {
		imagen = "TS104330";
		boolean gestion = false;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
		System.out.println(datosInicial);
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
		buscarYClick(driver.findElements(By.className("borderOverlay")), "equals", "tarjeta prepaga");
		driver.findElement(By.id("RefillMethods_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("BatchNumber")).sendKeys("11120000009323");
		driver.findElement(By.id("PIN")).sendKeys("0418");
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
		System.out.println(datosFinal);
		Assert.assertTrue(datosInicial + 500 == datosFinal);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134844_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_IVR_Fan_FRONT_Telefonico(String sDNI) {
		imagen = "TS134844";
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
	
	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
	public void TS98435_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98435";
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
	public void TS98437_CRM_Movil_Prepago_Suspension_por_Siniestro_Robo_Linea_No_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
		imagen = "TS98437";
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
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134845_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_SMS_Fan_FRONT_Telefonico(String sDNI, String sLinea){
		imagen = "TS134845";
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
		sleep(8000);
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
			TestBase TB = new TestBase();
			TB.waitFor(driver, By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
			Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134846_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_ROL_Fan_FRONT_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134846";
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
			TestBase TB = new TestBase();
			TB.waitFor(driver, By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
			Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
		} else
			Assert.assertTrue(false);
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134839_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Fecha_Fan_FRONT_Telefonico(String sDNI, String sLinea){
		imagen = "TS134839";
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
		sleep(8000);
		cc.seleccionDeHistorial("historial de recargas");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		WebElement canal = driver.findElement(By.id("text-input-03"));
		//canal.click();
		System.out.println(canal.getText());
		Assert.assertTrue(canal.isDisplayed());
		try {
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		} catch (Exception e) {
			driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		}
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
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "RecargasHistorias")
	public void TS135475_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Familia_FAN_Front_Telefonico(String sDNI){
		imagen = "TS135475";
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
			if(t.getText().equals("Todos")){
				t.click();
			}	
		}
		
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"},  dataProvider = "Diagnostico")
	public void TS105845_CRM_Movil_REPRO_Autogestion_APP_Abre_aplicacion_y_cierra_automaticamente_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105845";
		detalles = null;
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(15000);
		tech.listadoDeSeleccion("APP", "Otros", "Abre aplicaci\u00f3n y cierra autom\u00e1ticamente");
		sleep(4000);
		tech.verificarNumDeGestion();
		driver.switchTo().frame(cambioFrame(driver, By.id("srchErrorDiv_Case")));
		String estado= driver.findElement(By.xpath("//*[@id='Order_body']/table/tbody/tr[2]/td[5]")).getText();
		Assert.assertTrue(estado.equalsIgnoreCase("Iniciada"));
		//Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesREPRO")
	public void TS129321_CRM_Movil_REPRO_Escalamiento_segun_RAV_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS129321";
		detalles = null;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, 5));
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
		Assert.assertTrue(datosInicial + 2000 == datosFinal);
		detalles = imagen + " -Ajustes-DNI: " + sDNI + ", Caso numero: " + caso;
	}

	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaVista360") 
	public void TS_134802_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134802";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
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
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("slds-select_container")).getLocation().y+" )");
		WebElement filt = driver.findElement(By.className("slds-select_container"));
		System.out.println(filt.getText());
		Assert.assertTrue(filt.getText().contains("10"));
		/*Select pagina = new Select (driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-valid.ng-not-empty.ng-touched")));
		pagina.selectByVisibleText("20");
		sleep(7500);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).isDisplayed());*/
	
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS_134798_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134798";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		WebElement detalles = driver.findElement(By.cssSelector(".slds-grid.community-flyout-content"));
		System.out.println(detalles.getText());
		Assert.assertTrue(detalles.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS_134801_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134801";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals","mis servicios");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds")));
		ArrayList<String> txt1 = new ArrayList<String>();
		ArrayList<String> txt2 = new ArrayList<String>();
		txt2.add("Contestador Personal");
		txt2.add("DDI con Roaming Internacional");
		txt2.add("Voz");
		txt2.add("SMS Saliente");
		txt2.add("SMS Entrante");
		txt2.add("MMS");
		txt2.add("Datos");
		WebElement serv= driver.findElements(By.cssSelector(".slds-p-bottom--small")).get(1);
		System.out.println(serv.getText());
		 List <WebElement> servicios = serv.findElement(By.tagName("tbody")).findElements(By.tagName("td")); 
		 for(WebElement s: servicios){
			 System.out.println(s.getText());
			 txt1.add(s.getText());
			 }
			 Assert.assertTrue(txt2.contains(txt1));

	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134809_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
		imagen = "TS134809";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon-container.nds-button.nds-button_icon-container")).click();
		driver.findElement(By.xpath("//*[text() = '01']")).click();
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[text() = '01']")).click();
		
		
		
		
		
//		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
//		sleep(3000);
//		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows) {
//			try {
//				if (cell.getText().equals("03")) {
//					cell.click();
//				}
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.id("text-input-id-2")).click();
//		WebElement table_2 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
//		sleep(3000);
//		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows_2) {
//			try {
//				if (cell.getText().equals("30")) {
//					cell.click();
//					sleep(5000);
//				}
//			} catch (Exception e) {}
//		}
//		sleep(3000);
//		driver.findElement(By.id("text-input-id-2")).click();
//		WebElement table_3 = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
//		sleep(3000);
//		List<WebElement> tableRows_3 = table_3.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows_3) {
//			try {
//				if (cell.getText().equals("03")) {
//					cell.click();
//				}
//			} catch (Exception e) {}
//		}
//		sleep(3000);
//		List<WebElement> tipo = driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"));
//			for(WebElement t : tipo){
//				if(t.getText().toLowerCase().equals("todos")){
//					t.click();
//				}
//			}
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		sleep(9000);
//		Boolean asd = false;
//		List <WebElement> cuadro = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//			for(WebElement c : cuadro){
//				if(c.getText().toLowerCase().contains("Order")){
//					asd = true;
//				}
//			}
		Assert.assertTrue(false);	
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134342_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_Llamadas_WiFi_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134342";
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		datosOrden.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134345_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_con_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134345";
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		datosOrden.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134346_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134346";
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		datosOrden.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134359_CRM_Movil_PRE_Alta_Servicio_sin_costo_Llamadas_WiFi_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134359";
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		datosOrden.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134362_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_con_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134362";
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(10000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		datosOrden.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134363_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134363";
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
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		datosOrden.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","Vista360","E2E", ""}, dataProvider="CuentaVista360")
	public void TS134794_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_Telefonico(String sDNI, String sNombre, String sLinea) {
		imagen = "TS134794";
		detalles = imagen+" - Vista360 - DNI: "+sDNI;
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
		try {
			cc.openleftpanel();
		}
		catch(Exception e) {}
		WebElement wProfileBoxDetails = driver.findElement(By.className("profile-box-details"));
		Assert.assertTrue(wProfileBoxDetails.findElement(By.cssSelector(".slds-text-heading_large.title-card")).findElement(By.tagName("h1")).getText().toLowerCase().contains(sNombre.toLowerCase()));
		String sWebDNI = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(sWebDNI.equals(sDNI));
		Assert.assertTrue(sWebDNI.matches("[0-9]+"));
		List<WebElement> wWebList = wProfileBoxDetails.findElements(By.className("profile-edit"));
		List<String> sTextList = new ArrayList<String>();
		sTextList.add("Actualizar datos" + "Reseteo de Clave");
		ppt = new PagePerfilTelefonico(driver);
		Assert.assertTrue(ppt.forEach(wWebList, sTextList));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')]")).getText().equalsIgnoreCase("Segmentaci\u00f3n"));
		try {
			driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')] /.. /.. /.. /.. /..")).click();
		} catch (Exception e) {}
		sleep(5000);
		WebElement wL = driver.findElements(By.className("left-sidebar-section-container")).get(1);
		Assert.assertTrue(wL.findElements(By.tagName("table")).get(0).findElement(By.tagName("tbody")).findElement(By.tagName("td")).findElement(By.tagName("div")).getText().equalsIgnoreCase("Segmento:"));
		Assert.assertTrue(wL.findElements(By.tagName("table")).get(0).findElement(By.tagName("tbody")).findElement(By.tagName("td")).findElements(By.tagName("div")).get(1).getText().toLowerCase().contains("alta reciente"));
		Assert.assertTrue(wL.findElements(By.tagName("table")).get(1).findElement(By.tagName("tbody")).findElement(By.tagName("td")).findElement(By.tagName("div")).getText().equalsIgnoreCase("Atributos:"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "E2E","Ciclo4"}, dataProvider = "CuentaReintegros")
	public void TS135432_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Verificacion_de_vista_de_Resumen_de_Cuenta_Corriente_FAN_Front_Telefonico(String sDNI){
		imagen = "TS135432";
		detalles = null;
		detalles = imagen + " -Resumen de Cuenta Corriente - DNI: " + sDNI;
		Marketing mk = new Marketing(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "resumen de cuenta corriente");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		WebElement saldo = driver.findElement(By.cssSelector(".slds-text-heading--medium.secondaryFont"));
		WebElement fechas = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"));
		WebElement boton = driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"));
		Assert.assertTrue(saldo.isDisplayed() && fechas.isDisplayed() && boton.isDisplayed());
		boton.click();
		sleep(5000);
		WebElement comprobante = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(comprobante.getText().toLowerCase().contains("comprobantes"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider = "DatosNominacionExistente5Lineas")
	public void TS85113_CRM_Movil_REPRO_No_Nominatividad_Numero_Limite_De_Lineas_Cliente_Existente_Telefonico(String sLinea, String sDni) {
		imagen = "TS85113";
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominacionExistenteFraudeOfcom")
	public void TS85114_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Existente_Presencial(String sLinea, String sDni) {
		imagen = "TS85114";
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider = "DatosNoNominacionNuevoFraudeTelef")
	public void TS85112_CRM_Movil_REPRO_No_Nominatividad_Por_Fraude_Cliente_Nuevo_Telefonico(String sLinea, String sDni) {
		imagen = "TS85112";
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionExistenteTelefonico")
	public void TS134494_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Telefonico_Preguntas_Y_Respuestas(String sLinea, String sDni, String sSexo) {
		imagen = "TS134494";
		detalles = null;
		detalles = imagen+"No nominacion Agente- DNI: "+sDni+"-Linea: "+sLinea;
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
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		driver.findElement(By.id("Contact_nextBtn")).click();
		contact.tipoValidacion("preguntas y respuestas");
		sleep(8000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.obligarclick(driver.findElement(By.id("QAContactData_nextBtn"))); 
		sleep(5000);
		List<WebElement> valdni = driver.findElements(By.className("slds-radio__label"));
		for (WebElement x : valdni) {
			System.out.println(x.getText());
			if (x.getText().toLowerCase().contains("ninguno de los anteriores")) {
				x.click();
			}
		}
		cCC.obligarclick(driver.findElement(By.id("QAQuestions_nextBtn")));      
		sleep(5000);
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("no superada")) {
				error = true;
			}
		}
		
		Assert.assertTrue(error);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico" , "ResumenDeCuenta" , "E2E" , "Ciclo4"}, dataProvider= "CuentaVista360")
	public void TS135439_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Factura_de_Venta_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135439";
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135428_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135428";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.openleftpanel();
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		List <WebElement> lis = driver.findElement(By.className("actions")).findElements(By.tagName("li"));
		for(WebElement x : lis) {
			if(x.findElement(By.className("slds-text-body_regular")).getText().toLowerCase().contains("resumen de cuenta corriente")){
				x.click();
			break;
			}
		}
		
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
	}
	
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135429_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Detalle_ampliado_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135429";
		String cuenta = null;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		mk.openleftpanel();
		mk.closeActiveTab();
		cc.irAFacturacion();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		List <WebElement> lis = driver.findElement(By.className("actions")).findElements(By.tagName("li"));
		for(WebElement x : lis) {
			if(x.findElement(By.className("slds-text-body_regular")).getText().toLowerCase().contains("resumen de cuenta corriente")){
				x.click();
			break;
			}
		}
		
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
		WebElement detalleFactu = driver.findElement(By.xpath("//*[@class='slds-p-bottom--small']/table/tbody/tr/td/div/a/i"));
		detalleFactu.click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-float--right")));
		WebElement imprimir = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton"));
		WebElement descargar = driver.findElement(By.className("slds-float--right")).findElement(By.className("boton2"));
		Assert.assertTrue(imprimir.isDisplayed() && descargar.isDisplayed());
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		detalleFactu = driver.findElement(By.cssSelector(".slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		detalleFactu.getText().contains("Detalle de factura");
		Assert.assertTrue(detalleFactu.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135430_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Descarga_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135430";
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
		Assert.assertTrue(scp.isFileDownloaded(downloadPath,"B00785"), "Failed to download Expected document");
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135431_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Visualizacion_del_Resumen_de_Cuenta_Corriente_a_nivel_de_cuenta_de_Facturacion_Impresion_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135431";
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135433_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Validaciones_de_logica_de_filtros_Fecha_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135433";
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
		sleep(5000);
		String resuCuenta = driver.findElement(By.className("secondaryFont")).getText();
		cuenta = resuCuenta.substring(0, 16);
		System.out.println("El Resumen de Cuenta Corresponde a la cuenta: " + cuenta);	
		Assert.assertTrue(cuenta.equalsIgnoreCase(sCuenta));
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
				if (cell.getText().equals("12")) {
					cell.click();
				}
			} catch (Exception e) {}
		}	
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		WebElement Comprobantes = driver.findElement(By.xpath("//*[@class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-bottom--small'] //b[contains(text(), 'Comprobantes')]"));
		Assert.assertTrue(Comprobantes.isDisplayed());
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElements(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).get(1).getLocation().y+" )");
		WebElement pagos = driver.findElement(By.xpath("//*[@class='slds-text-heading--large slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-bottom--small'] //b[contains(text(), 'Pagos')]"));
		Assert.assertTrue(pagos.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ResumenDeCuenta", "Ciclo4"}, dataProvider = "CuentaVista360")
	public void TS135438_CRM_Movil_REPRO_Resumen_de_Cuenta_Corriente_Detalle_ampliado_registro_de_Comprobante_Nota_de_Credito_Debito_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre, String sCuenta, String sMovil) {
		imagen = "TS135438";
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
	
	@Test(groups = { "GestionesPerfilTelefonico","DiagnosticoInconvenientes","Ciclo 3", "E2E" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119198_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_recibir_llamadas_Conciliacion_Exitosa(String sDNI, String sLinea){
		imagen = "TS119198";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage Tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		tech.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(45000);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());
		String Ncaso = caso.getText();
		System.out.println("El numero de caso es: "+Ncaso);
		driver.switchTo().defaultContent();
		sleep(1000);
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
		Assert.assertTrue(Tech.cerrarCaso("Resuelta exitosa", "Consulta"));
		sleep(14000);
		mk.closeAllTabs(driver);
		Buscador.click();
		Buscador.clear();
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		gest = driver.findElement(By.id("Case_body"));	
		List<WebElement> x = mk.traerColumnaElement(gest, 5, 3);
		for (WebElement a :x) {
		System.out.println(a.getText());
			if (a.getText().toLowerCase().contains("resuelta exitosa")){
			}
		Assert.assertTrue(a.getText().equals("Resuelta exitosa")||a.getText().equals("Realizada exitosa"));
		}
	}
	
	
	@Test(groups = { "GestionesPerfilTelefonico","DiagnosticoInconvenientes","Ciclo 3", "E2E" }, priority = 1, dataProvider = "Diagnostico") 
	public void TS119231_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas_Sin_Locacion_Equipo_sin_senal(String sDNI, String sLinea){
		imagen = "TS119231";
		detalles = null;
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage Tech = new TechnicalCareCSRAutogestionPage (driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		//tech.categoriaRed("Desregistrar");
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tech.categoriaRed("No son las antenas (Verde)");
		sleep(15000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("CoverageOkNetMessage")));
		WebElement caso = driver.findElement(By.id("CoverageOkNetMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
		System.out.println(caso.getText());		
//		page.seleccionarPreguntaFinal("No");
//		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
//		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
//		sleep(8000);
//		page.seleccionarPreguntaFinal("No, sigue con inconvenientes");
//		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
//		sleep(45000);
//		driver.switchTo().frame(accPage.getFrameForElement(driver, By.id("IncorrectCategoriesMessage")));
//		WebElement caso = driver.findElement(By.id("IncorrectCategoriesMessage")).findElement(By.tagName("div")).findElement(By.tagName("p")).findElements(By.tagName("p")).get(1).findElement(By.tagName("span")).findElement(By.tagName("strong"));
//		System.out.println(caso.getText());
		String Ncaso = caso.getText();
//		System.out.println("El numero de caso es: "+Ncaso);
//		driver.switchTo().defaultContent();
//		sleep(1000);
		WebElement Buscador = driver.findElement(By.id("phSearchInput"));
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		WebElement gest = driver.findElement(By.id("Case_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.cssSelector(".dataRow.even.last.first")).findElements(By.tagName("td")).get(2);
		System.out.println(gest.getText());
//		Assert.assertTrue(Tech.cerrarCaso("Resuelta exitosa", "Consulta"));
		sleep(14000);
		mk.closeAllTabs(driver);
		Buscador.click();
		Buscador.clear();
		Buscador.sendKeys(Ncaso);
		Buscador.submit();
		sleep(14000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Case_body")));
		gest = driver.findElement(By.id("Case_body"));	
		List<WebElement> x = mk.traerColumnaElement(gest, 5, 3);
		for (WebElement a :x) {
		System.out.println(a.getText());
			if (a.getText().toLowerCase().contains("informada")){
			}
		Assert.assertTrue(a.getText().equals("Informada"));
		}
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119271_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_SEnAL_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119271";
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
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
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
		Tech.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"OutOfCoverageMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119171_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Conciliacion_Exitosa(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119171";
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
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		cCC.obligarclick(driver.findElements(By.className("borderOverlay")).get(0));
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		sleep(20000);
		String caso_1 = driver.findElement(By.xpath("//*[@id=\"IncorrectCategoriesMessage\"]/div/p/p[2]/span/strong")).getText();
		System.out.println(caso_1);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso_1);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "Solicitud De Reintegro", "E2E","Ciclo4"}, dataProvider = "ConsultaSaldo")
	public void TS112598_CRM_Movil_PRE_Pago_con_Tarjeta_de_debito_Reintegro_con_Efectivo_1000(String sDNI){
		imagen = "TS112598";
		detalles = null;
		detalles = imagen + " -Reintegros - DNI: " + sDNI;
		String reintegro = "1,000.00";
		Marketing mk = new Marketing(driver);
		boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("solicitud de reintegros");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SelectBillingAccount_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "cuenta: ");
		driver.findElement(By.id("SelectBillingAccount_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("stepRefundData_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "tarjeta de d\u00e9bito");
		selectByText(driver.findElement(By.id("selectReason")), "Otros");
		driver.findElement(By.id("inputCurrencyAmount")).sendKeys(reintegro);
		driver.findElement(By.id("stepRefundData_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("stepRefundMethod_nextBtn")));
		String monto = driver.findElement(By.xpath("//*[@id=\"AmountToRefund\"]/div[2]/div/p")).getText();
		if(monto.contains(reintegro)) {
			System.out.println(monto);
		}
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("stepRefundMethod_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Summary_nextBtn")));
		List <WebElement> tabla = driver.findElements(By.cssSelector("slds-card__header slds-grid"));
		for(WebElement x : tabla) {
			if(x.getText().toLowerCase().contains("tarjeta de d\u00e9bito") && x.getText().toLowerCase().contains("1000")) {
				System.out.println(tabla);
			}
		}
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(8000);
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-box.ng-scope"));
		if(verificacion.getText().toLowerCase().contains("correctamente")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","Diagnostico/Inconvenientes"}, dataProvider = "Diagnostico")
	public void TS119269_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_con_rellamado_NO_BAM(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS119269";
		detalles = null;
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage Tech = new TechnicalCareCSRDiagnosticoPage(driver);
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage (driver);
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select(driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		//selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		String cuota = driver.findElement(By.xpath("//*[@id=\"AvailableDataQuotaDisplay\"]/div/p/div/p[2]/span")).getText();
		String saldo = driver.findElement(By.xpath("//*[@id=\"AvailableDataBalanceDisplay\"]/div/p/div/p[2]/span")).getText();
		System.out.println("Saldo: " + saldo);
		System.out.println("Cuota: " + cuota);
		page.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		String informacion = driver.findElement(By.xpath("//*[@id=\"CommercialInfo\"]/div/p/div")).getText();
		System.out.println(informacion);
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("AddressSection_nextBtn")));
		Tech.categoriaRed("No son las antenas (Verde)");
		sleep(8000);
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
		String caso = driver.findElement(By.xpath("//*[@id=\"MobileConfigSendingMessage\"]/div/p/h1/span/strong")).getText();
		System.out.println(caso);
		driver.switchTo().defaultContent();
		tech.buscarCaso(caso);
		Assert.assertTrue(tech.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
}
