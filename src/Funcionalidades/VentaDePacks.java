package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CBS;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class VentaDePacks extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private LoginFw log;
	private Marketing mk;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
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
		
	//@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginTelefonico(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	//@BeforeClass (alwaysRun = true)
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginAgente(driver);
		sleep(15000);
		cc.irAConsolaFAN();	
		driver.switchTo().defaultContent();
		sleep(6000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
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
	
	@Test (groups = {"GestionPerfilOficina", "VentaDePacks", "Ciclo1"}, dataProvider = "ventaX1Dia" )
	public void TS123163_CRM_Movil_REPRO_Venta_de_pack_1000_min_a_Personal_y_1000_SMS_x_1_dia_Factura_de_Venta_TC_Presencial(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws KeyManagementException, NoSuchAlgorithmException{
		imagen = "TS123163";
		detalles = null;
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		ges.BuscarCuenta("DNI", sDNI);
		//System.out.println("id "+accid);
		//detalles +="-Cuenta:"+accid;
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Compra de Pack");
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
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
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
		//System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrden + "Cuenta: "+ accid + "Fin");
		detalles += "-Charge Code: " ;//+ chargeCode;
		//Blocked
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
//			pagePTelefo.PackCombinado(sventaPack);
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
//			pagePTelefo.PackCombinado(sventaPack);
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
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
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
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups= {"GestionesPerfilAgente","VentadePacks","E2E","Ciclo1"},priority=1, dataProvider="ventaPack500min")
	public void TS123148_CRM_Movil_REPRO_Venta_de_pack_500_Min_todo_destino_Factura_de_Venta_TD_Presencial(String sDNI, String sLinea, String sVentaPack) throws InterruptedException, AWTException{
		imagen = "TS123148";
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
		pagePTelefo.PacksRoaming(sVentaPack);
	}
	
	@Test (groups= {"GestionesPerfilAgente","VentadePacks","E2E","Ciclo1"},priority=1, dataProvider="ventaPackA40")
	public void TS123166_CRM_Movil_REPRO_Venta_de_pack_Adelanto_Personal_40_Descuento_de_saldo_Presencial(String sDNI, String sLinea, String sVentaPack) throws InterruptedException, AWTException{
		imagen = "TS123166";
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
		sleep(5000);
		//pagePTelefo.PacksRoaming(sVentaPack);
	}
	
	@Test (groups= {"GestionesPerfilAgente","VentadePacks","E2E","Ciclo1"},priority=1, dataProvider="ventaPackM2M")
	public void TS135801_CRM_Movil_PREVenta_de_pack_Paquete_M2M_10_MB_Factura_de_Venta_Efectivo_Presencial_PuntMa_Alta_Agente(String sDNI, String sLinea, String sVentaPack) throws InterruptedException, AWTException{
		imagen = "TS135801";
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
		sleep(5000);
		//pagePTelefo.PacksRoaming(sVentaPack);
	}
	
	@Test (groups = {"GestionesPerfilAgente","VentaDePacks","E2E","Ciclo1"}, dataProvider="PackAgente")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_efectivo_Agente(String sDNI, String sLinea, String sPackAgente) throws AWTException{
		imagen = "Venta_de_Pack";
		detalles = null;
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		SalesBase sale = new SalesBase(driver);
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		sleep(5000);
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack();
		sleep(8000);
		pagePTelefo.closerightpanel();
		sleep(8000);
		pagePTelefo.agregarPack(sPackAgente);
		pagePTelefo.tipoDePago("en factura de venta");
		pagePTelefo.getTipodepago().click();
		sleep(12000);
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		sleep(12000);		
		pagePTelefo.getMediodePago().click();
		sleep(15000);
		pagePTelefo.getOrdenSeRealizoConExito().click();
		sleep(15000);
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, sOrden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		sleep(10000);
		System.out.println("Operacion: Compra de Pack- Cuenta: "+accid+" Invoice: "+invoice.split("-")[1] + "\tAmmount: " +invoice.split("-")[0]);
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBSM.PagoEnCaja("1005", accid, "1001", invoice.split("-")[0], invoice.split("-")[1],driver));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		CBS cCBS = new CBS();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sPackAgente));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
}