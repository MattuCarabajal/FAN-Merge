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
import org.testng.annotations.AfterClass;
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
	
	
	//@BeforeClass (groups = "PerfilOficina")
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
	
	@BeforeClass (groups = "PerfilAgente")
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
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider = "ventaX1Dia" )
	public void TS123163_CRM_Movil_REPRO_Venta_de_pack_1000_min_a_Personal_y_1000_SMS_x_1_dia_Factura_de_Venta_TC_Presencial(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws KeyManagementException, NoSuchAlgorithmException{
		// Pack modificado Pack SMS y Minutos a Personal Ilimitados x 1 d�a
		imagen = "TS123163";//No esta impactando el Pack
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(verificacion);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack , dia));

	}
	
	@Test (groups = "PerfilOficina",priority=1, dataProvider = "ventaPack50ofic")
	public void TS139727_CRM_Movil_REPRO_Venta_de_pack_50_min_y_50_SMS_x_7_dias_Factura_de_Venta_Efectivo_OOCC(String sDNI, String sLinea, String sventaPack) throws AWTException {
		// Nombre nuevo = TS139727_CRM_Movil_REPRO_Venta_de_Pack_150_min_a_Personal_y_150_SMS_x_7_dias_OOCC
		//Pack modificado Pack 150 min a Personal y 150 SMS x 7 dias
		imagen = "TS139727";
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sventaPack, dia));
	}
	
	@Test (groups = "PerfilOficina", dataProvider="PackOfCom")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_TC_OffCom(String sDNI, String sLinea, String sPackOfCom, String cBanco, String cTarjeta, String cPromo, String cCuotas) throws AWTException, KeyManagementException, NoSuchAlgorithmException{
		//Pack modificado = Pack 1 GB x 1 dia + whatsapp gratis
		imagen = "Venta De Pack 1 GB x 1 dia";
		detalles = imagen + "- DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackOfCom, dia));
	}

	@Test(groups = "PerfilOficina", dataProvider = "PackOOCC1GBx7Dias")
	public void TS135662_CRM_Movil_PRE_Venta_de_Pack_1_GB_x_7_dias_whatsapp_gratis_Presencial_OOCC_TD(String sDNI, String sLinea, String sPack1GBx7Dias, String cBanco, String cTarjeta, String cPromo, String cCuotas){
		imagen = "TS135662";
		detalles = imagen + "- DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packDatos(sPack1GBx7Dias);		
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de debito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack2(cbsm.Servicio_QueryFreeUnit(sLinea), sPack1GBx7Dias , dia));
	}
	
	@Test(groups = "PerfilOficina", dataProvider = "PackOOCCInternetx7Dias")
	public void TS135703_CRM_Movil_REPRO_Venta_de_Pack_Pack_internet_x_7_dias_OOCC_EFE(String sDNI, String sLinea, String sPackInternetx7Dias) {
		imagen = "TS135703";
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packDatos(sPackInternetx7Dias);
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
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackInternetx7Dias, dia));
	}
	
	@Test(groups = "PerfilOficina", dataProvider = "PackOOCC1GBYSMSYVOZ")
	public void TS135656_CRM_Movil_REPRO_Venta_de_Pack_1_GB_sms_voz_y_whatsapp_gratis_x_7_dias_Presencial_OOCC_TC(String sDNI, String sLinea, String sPack1GBsmsYvoz, String cBanco, String cTarjeta, String cPromo, String cCuotas) {
		imagen = "TS135656";
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packCombinado(sPack1GBsmsYvoz);
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
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack1GBsmsYvoz, dia));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "ventaPack50ofic")
	public void TS135657_CRM_Movil_REPRO_Venta_de_Pack_150_min_a_Personal_y_150_SMS_x_7_dias_Presencial_OOCC_Descuento_de_Saldo(String sDNI, String sLinea, String sventaPack) {
		imagen = "TS135657";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packCombinado(sventaPack);
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
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sventaPack, dia));
	}
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "PackAPRO")
	public void TS156780_CRM_Movil_Mix_Venta_de_oferta_OOCC_Pack_Roaming_Descuento_de_saldo(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular){
		imagen = "TS156780";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCardPorNumeroDeLinea("Comprar Minutos", sLinea);
		vt.packRoaming(sVentaPack);
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
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
	}
	

	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico",priority=1, dataProvider="ventaPackInternacional30SMS")
	public void TS123133_CRM_Movil_REPRO_Venta_De_Pack_internacional_30_SMS_al_Resto_del_Mundo_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws InterruptedException, AWTException{
		//Pack modificado = Pack internacional 30 minutos LDI y 15 SMS int
		imagen = "TS123133";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		cc.buscarCasoParaPedidos(sOrden);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
	}
	
	@Test (groups = "PerfilTelefonico",priority=1, dataProvider="packUruguay")
	public void TS123143_CRM_Movil_REPRO_Venta_de_pack_100MB_Uruguay_Descuento_de_saldo_Telefonico(String sDNI, String sLinea, String packUruguay) throws InterruptedException, AWTException{
		//Pack Modificado = Pack Roaming 300MB Lim y USA
		imagen = "TS123143";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), packUruguay, dia));
	}
	
	@Test (groups = "PerfilTelefonico",priority=1, dataProvider="ventaPack50Tele")
	public void TS135671_CRM_Movil_REPRO_Venta_de_Pack_150_min_a_Personal_y_150_SMS_x_7_dias_Telefonico_TC(String sDNI, String sLinea, String sVentaPack, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws InterruptedException, AWTException{
		imagen = "TS135671";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		cc.buscarCasoParaPedidos(sOrden);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="PackTel200MB")
	public void TS135669_CRM_Movil_REPRO_Venta_de_Pack_200_Mb_x_1_dia_whatsapp_gratis_Telefonico_Descuento_de_Saldo(String sDNI, String sLinea, String sPack200MB) {
		imagen = "TS135669";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packDatos(sPack200MB);
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
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack200MB, dia));
	}
	
	@Test (groups = {"PerfilTelefonico", "R1"}, dataProvider = "PackAPRO")
	public void TS156768_CRM_Movil_Mix_Venta_de_oferta_Telefonico_Pack_Roaming_TDC(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular){
		imagen = "TS156768";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCardPorNumeroDeLinea("Comprar Minutos", sLinea);
		vt.packRoaming(sVentaPack);	
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
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		cc.buscarCasoParaPedidos(sOrden);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
	}
	
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = "PerfilAgente", dataProvider="PackAgente")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_efectivo_Agente(String sDNI, String sLinea, String sPackAgente) throws AWTException{
		imagen = "Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
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
		cc.buscarCasoParaPedidos(sOrden);
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(10000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackAgente, dia));
	}
	
	@Test (groups = "PerfilAgente", dataProvider="PackAgente30minAPersonal")
	public void TS123155_CRM_Movil_REPRO_Venta_de_Pack_300_min_a_Personal_x_7_dias_Agente_Efectivo(String sDNI, String sLinea , String sPackAgente30MinPersonal){
		imagen = "TS123155";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Minutos");
		vt.packMinutos(sPackAgente30MinPersonal);
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector("[class = 'slds-form-element__label ng-binding']")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		cc.buscarCasoParaPedidos(sOrden);
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(10000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackAgente30MinPersonal, dia));
	}
	
	@Test (groups = "PerfilAgente", dataProvider="PackAgenteRoaming1GB")
	public void TS135672_CRM_Movil_REPRO_Venta_de_Pack_Roaming_1GB_Lim_y_USA_Agente_TC(String sDNI, String sLinea, String sPackAgenteRoaming, String cBanco, String cTarjeta, String cPromo, String cCuotas){
		imagen = "TS135672";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packRoaming(sPackAgenteRoaming);
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
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(10000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackAgenteRoaming, dia));
	}
	
	@Test (groups = "PerfilAgente", dataProvider="PackAgenteSmsYMinutosIlimitados")
	public void TS135661_CRM_Movil_REPRO_Venta_de_Pack_SMS_y_Minutos_a_Personal_Ilimitados_x_7_dias_Agente_Descuento_de_Saldo(String sDNI, String sLinea, String sPackIlimitadoX7Dias) {
		imagen = "TS135661";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packCombinado(sPackIlimitadoX7Dias);
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
		//No se impacta el pack,por lo cual no funciona la validacion del pack activo
		//Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPackIlimitadoX7Dias));
	}

	@Test(groups = "PerfilAgente", dataProvider = "PackAgente40MinutosX3Dias")
	public void TS135668_CRM_Movil_REPRO_Venta_de_Pack_40_minutos_a_cualquier_compania_x_3_dias_Agente_TD(String sDNI, String sLinea, String sPack40Minutos, String cBanco, String cTarjeta, String cPromo, String cCuotas){
		imagen = "TS135668";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packMinutos(sPack40Minutos);		
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de debito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(30000);
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
			}
		}
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack40Minutos , dia) && a);
	}
	
	@Test (groups = { "PerfilAgente", "R1" }, dataProvider = "PackAPRO")
	public void TS156753_CRM_Movil_Mix_Venta_de_oferta_Agente_Pack_Roaming_Efectivo(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular){
		imagen = "TS156753";
		detalles = imagen+"- Venta de pack - DNI: "+sDNI+" - Linea: "+sLinea;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCardPorNumeroDeLinea("Comprar Minutos", sLinea);
		vt.packRoaming(sVentaPack);	
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
		sleep(7000);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
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
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
	}
}