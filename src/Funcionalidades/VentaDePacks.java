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
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import PagesPOM.VentaDePackFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class VentaDePacks extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private LoginFw log;
	private VentaDePackFw vt;
	private Marketing mk;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		vt = new VentaDePackFw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		vt = new VentaDePackFw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() throws IOException, AWTException {
			driver = setConexion.setupEze();
			ges = new GestionDeClientes_Fw(driver);
			cc = new CustomerCare(driver);
			vt = new VentaDePackFw(driver);
			cbs = new CBS();
			cbsm = new CBS_Mattu();
			log = new LoginFw(driver);
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
	
	@Test (groups = "PerfilOficina", dataProvider = "ventaX1Dia" )
	public void TS123163_CRM_Movil_REPRO_Venta_de_pack_1000_min_a_Personal_y_1000_SMS_x_1_dia_Factura_de_Venta_TC_Presencial(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws KeyManagementException, NoSuchAlgorithmException{
		// Pack modificado Pack SMS y Minutos a Personal Ilimitados x 1 d�a
		imagen = "TS123163";
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Minutos");
		vt.packCombinado(sVentaPack);
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuotas);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		//cbsm.PagarTCPorServicio(sOrden);
		sleep(10000);
		if(activarFalsos == true) {
			cbsm.Servicio_NotificarPago(sOrden);
		}
		sleep(30000);
		boolean verificacion = false;
		for(int i= 0; i < 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr"), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				verificacion = true;
			}else {
				sleep(15000);
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(verificacion);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack));

	}
	
	@Test (groups = "PerfilOficina",priority=1, dataProvider = "ventaPack50ofic")
	public void TS139727_CRM_Movil_REPRO_Venta_de_pack_50_min_y_50_SMS_x_7_dias_Factura_de_Venta_Efectivo_OOCC(String sDNI, String sLinea, String sventaPack) throws AWTException {
		// Nombre nuevo = TS139727_CRM_Movil_REPRO_Venta_de_Pack_150_min_a_Personal_y_150_SMS_x_7_dias_OOCC
		//Pack modificado Pack 150 min a Personal y 150 SMS x 7 dias
		imagen = "TS139727";
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packCombinado(sventaPack);
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		cbsm.Servicio_NotificarPago(sOrden);
		//Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[1], orden.split("-")[0],driver));
		sleep(35000);
		boolean a = false;
		for(int i= 0; i <= 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				a = true;
			}else {
				sleep(15000);
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sventaPack));
	}
	
	@Test (groups = "PerfilOficina", dataProvider="PackOfCom")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_TC_OffCom(String sDNI, String sLinea, String sPackOfCom, String cBanco, String cTarjeta, String cPromo, String cCuotas) throws AWTException, KeyManagementException, NoSuchAlgorithmException{
		//Pack modificado = Pack 1 GB x 1 dia + whatsapp gratis
		imagen = "Venta De Pack Oficina";
		detalles = imagen + "- DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packDatos(sPackOfCom);		
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+="-Monto:"+orden.split("-")[2]+"-Prefactura:"+orden.split("-")[1];
		//cbsm.PagarTCPorServicio(sOrden);
		if(activarFalsos == true) {
			cbsm.Servicio_NotificarPago(sOrden);
			sleep(30000);
		}
		boolean a = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver,By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"),0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if (datos.equalsIgnoreCase("activada") || datos.equalsIgnoreCase("activated")) {
				a = true;
			} else {
				sleep(15000);
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackOfCom));
	}
	
	@Test (groups = "PerfilOficina", dataProvider="PackOfCom")
	public void TS123132_CRM_Movil_REPRO_Venta_de_pack_1000_SMS_x1_Dia_Factura_de_Venta_TD_Presencial_OOCC(String sDNI, String sLinea) {
		imagen = "TS123132";
		detalles = imagen + "- DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		//No exite el pack que se debe utilizar
	}
	
	@Test(groups = "PerfilOficina", dataProvider = "")
	public void TS135662_CRM_Movil_PRE_Venta_de_Pack_1_GB_x_7_dias_whatsapp_gratis_Presencial_OOCC(String sDNI, String sLinea){
		imagen = "TS123132";
		detalles = imagen + "- DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		//No se visualiza el medio de pago que se debe utilizar
	}
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico",priority=1, dataProvider="ventaPackInternacional30SMS")
	public void TS123133_CRM_Movil_REPRO_Venta_De_Pack_internacional_30_SMS_al_Resto_del_Mundo_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws InterruptedException, AWTException{
		//Pack modificado = Pack internacional 30 minutos LDI y 15 SMS int
		imagen = "TS123133";
		detalles = null;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packLDI(sVentaPack);		
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
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
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(20000);
		cc.buscarCaso(sOrden);
		boolean a = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver,By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"),0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if (datos.equalsIgnoreCase("activada") || datos.equalsIgnoreCase("activated")) {
				a = true;
			} else {
				sleep(15000);
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack));
	}
	
	@Test (groups = "PerfilTelefonico",priority=1, dataProvider="packUruguay")
	public void TS123143_CRM_Movil_REPRO_Venta_de_pack_100MB_Uruguay_Descuento_de_saldo_Telefonico(String sDNI, String sLinea, String packUruguay) throws InterruptedException, AWTException{
		//Pack Modificado = Pack Roaming 300MB Lim y USA
		imagen = "TS123143";
		detalles = null;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packRoaming(packUruguay);
		vt.tipoDePago("descuento de saldo");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		sleep(45000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), packUruguay));
	}
	
	@Test (groups = "PerfilTelefonico",priority=1, dataProvider="ventaPack50Tele")
	public void TS123157_CRM_Movil_REPRO_Venta_De_Pack_50_Min_Y_50_SMS_X_7_Dias_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sVentaPack, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws InterruptedException, AWTException{
		//Pack modificado Pack 150 min a Personal y 150 SMS x 7 dias
		imagen = "TS123157";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packCombinado(sVentaPack);	
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		sleep(15000);
		boolean a = false;
		for(int i= 0; i < 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				a = true;
			}else {
				sleep(15000);
				driver.navigate().refresh();
				i++;
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = "PerfilAgente",priority=1, dataProvider="ventaPack500min")
	public void TS123148_CRM_Movil_REPRO_Venta_de_pack_500_Min_todo_destino_Factura_de_Venta_TD_Presencial(String sDNI, String sLinea, String sVentaPack) throws InterruptedException, AWTException{
		imagen = "TS123148";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packRoaming(sVentaPack);
	}
	
	@Test (groups = "PerfilAgente",priority=1, dataProvider="ventaPackA40")
	public void TS123166_CRM_Movil_REPRO_Venta_de_pack_Adelanto_Personal_40_Descuento_de_saldo_Presencial(String sDNI, String sLinea, String sVentaPack) throws InterruptedException, AWTException{
		imagen = "TS123166";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packRoaming(sVentaPack);
		//pagePTelefo.PacksRoaming(sVentaPack);
	}
	
	@Test (groups = "PerfilAgente",priority=1, dataProvider="ventaPackM2M")
	public void TS135801_CRM_Movil_PREVenta_de_pack_Paquete_M2M_10_MB_Factura_de_Venta_Efectivo_Presencial_PuntMa_Alta_Agente(String sDNI, String sLinea, String sVentaPack) throws InterruptedException, AWTException{
		imagen = "TS135801";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packRoaming(sVentaPack);
		//pagePTelefo.PacksRoaming(sVentaPack);
	}
	
	@Test (groups = "PerfilAgente", dataProvider="PackAgente")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_efectivo_Agente(String sDNI, String sLinea, String sPackAgente) throws AWTException{
		imagen = "Venta_de_Pack";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packDatos(sPackAgente);
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
		cbsm.Servicio_NotificarPago(sOrden);
		//Assert.assertTrue(invoSer.PagoEnCaja("1006", accid, "1001", orden.split("-")[1], orden.split("-")[0],driver));
		sleep(30000);
		boolean a = false;
		for(int i= 0; i < 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				a = true;
			}else {
				driver.navigate().refresh();
				sleep(15000);
				i++;
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackAgente));
	}
	
	@Test (groups = "PerfilAgente", dataProvider="PackAgente30minAPersonal")
	public void TS123155_CRM_Movil_REPRO_Venta_de_Pack_300_min_a_Personal_x_7_dias_Agente(String sDNI, String sLinea , String sPackAgente){
		imagen = "Venta_de_Pack";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Minutos");
		vt.packDatos(sPackAgente);
		//No se visualiza que medio de pago se debe utilizar
	}
	
}