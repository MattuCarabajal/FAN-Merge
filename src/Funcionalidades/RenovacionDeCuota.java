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
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class RenovacionDeCuota extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private SalesBase sb;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private CustomerCare cc;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	//@BeforeClass (groups= "PerfilOficina")
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log.LoginSit02();
		//ges.irAConsolaFAN();
	}
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	//@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (alwaysRun = true)
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		log = new LoginFw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		sleep(5000);
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
	
	@Test (groups = {"GestionesPerfilOficina", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS130056_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Efectivo_con_Credito(String sDNI, String sLinea, String accid) throws AWTException {
		imagen="TS130056";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDNI+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDNI);
		detalles+="-Cuenta: "+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Factura de Venta");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SelectPaymentMethodsStep_nextBtn")));
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDNI +", Monto:"+orden.split("-")[2]);
		cbsm.Servicio_NotificarPago(orden.split("-")[0]);
		driver.navigate().refresh();
		sleep(5000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres Adicionales");
		System.out.println("Datos Inicial "+datosInicial);
		System.out.println("Datos final "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS130069_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS130069";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDNI+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		detalles += "-Cuenta: "+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(5000);
		String mesj = driver.findElement(By.cssSelector("[class='slds-box ng-scope'] header")).getText();
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		System.out.println("Saldo final:"+uiMainBalance);
		Assert.assertTrue(iMainBalance < uiMainBalance);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println("Datos final:"+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));	
	}
	
	@Test (groups = {"GestionesPerfilOficina","RenovacionDeCuota","E2E", "Ciclo1"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS135395_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_Efectivo_con_Credito(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "TS135395";
		//Check all
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDNI+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;		
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		cc.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		//sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDNI +", Monto:"+orden.split("-")[2]);
		//Assert.assertTrue(cbsm.PagoEnCaja("1006", accid, "1001", orden.split("-")[2], orden.split("-")[1],driver));
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(5000);
		driver.navigate().refresh();
		sleep(20000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println(datosInicial);
		System.out.println(datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);	
	}
	
//	@Test (groups = {"GestionesPerfilOficina","RenovacionDeCuota","E2E","Ciclo1"}, dataProvider="RenovacionCuotaSinSaldo")
//	public void TS135396_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_Efectivo_sin_Credito(String sDNI, String sLinea , String accid) throws AWTException {
//		imagen = "TS135396";
//		//Check all
//		detalles = null;
//		detalles = "Renocavion de cuota: "+imagen+"- DNI: "+sDNI+" - Linea: "+sLinea;
//		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		ges.BuscarCuenta("DNI", sDNI);
//		System.out.println("id "+accid);
//		detalles +="-Cuenta:"+accid;
//		ges.irAGestionEnCard("Renovacion de Datos");
//		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
//		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
//		for(WebElement UnE:elementos) {
//			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
//				UnE.findElement(By.className("slds-checkbox")).click();
//			}
//		}
//		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
//		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
//		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
//		String sOrden = cc.obtenerOrden2(driver);
//		detalles += "-orden:"+sOrden;
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
//		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
//		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
//		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
//		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
//		String orden = cc.obtenerTNyMonto2(driver, sOrden);
//		System.out.println("orden = "+orden);
//		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
//		cbsm.Servicio_NotificarPago(sOrden);
//		//Assert.assertTrue(cbsm.PagoEnCaja("1006", accid, "1001", orden.split("-")[2], orden.split("-")[1],driver));
//		driver.navigate().refresh();
//		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		System.out.println(datosInicial);
//		System.out.println(datosFinal);
//		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
//		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
//		boolean condition = false;
//		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
//		for (WebElement fila : tabla) {
//			if (fila.getText().contains("Estado")) {
//				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
//				System.out.println(datos);
//				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
//				break;
//			}
//		}
//		Assert.assertTrue(condition);	
//		//Arreglar luego porque no debe ser asi		
//	}
//	
//	@Test (groups = {"GestionesPerfilOficina","RenovacionDeCuota","E2E", "Ciclo1"}, dataProvider="RenovacionCuotaSinSaldoConTC")
//	public void TS135397_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_TC_sin_Credito(String sDNI, String sLinea,String accid, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular) throws AWTException, KeyManagementException, NoSuchAlgorithmException {
//		imagen = "TS135397";
//		//Check all
//		detalles = "Renocavion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
//		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		ges.BuscarCuenta("DNI", sDNI);
//		System.out.println("id "+accid);
//		detalles += "-Cuenta: "+accid;
//		ges.irAGestionEnCard("Renovacion de Datos");
//		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
//		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
//		for(WebElement UnE:elementos) {
//			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
//				UnE.findElement(By.className("slds-checkbox")).click();
//			}
//		}
//		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
//		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
//		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
//		String sOrden = cc.obtenerOrden2(driver);
//		detalles += "-Orden:" + sOrden;
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
//		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
//		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
//		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
//		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
//		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
//		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing"))); 
//		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
//		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
//		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
//		String orden = cc.obtenerTNyMonto2(driver, sOrden);
//		System.out.println("orden = "+orden);
//		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
//		cbsm.PagarTCPorServicio(sOrden);
//		sleep(5000);
//		if(activarFalsos == true) {
//			cbsm.Servicio_NotificarPago(sOrden);
//			sleep(20000);
//		}
//		driver.navigate().refresh();
//		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
//		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
//		boolean condition = false;
//		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
//		for (WebElement fila : tabla) {
//			if (fila.getText().contains("Estado")) {
//				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
//				System.out.println(datos);
//				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
//				break;
//			}
//		}
//		Assert.assertTrue(condition);	
//	}
//		
//	@Test (groups = {"GestionesPerfilOficina", "RenovacionDeCuota", "E2E", "Ciclo1"}, dataProvider = "RenovacionCuotaSinSaldo")
//	public void TS135512_CRM_Movil_REPRO_No_Renovacion_de_cuota_Pack_de_datos_Activo_Presencial(String sDNI, String sLinea) {
//		imagen = "TS135512";
//		detalles = null;
//		detalles = imagen + "- No renovacion de cuota - DNI:" + sDNI+ "-Linea: "+sLinea;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Renovacion de Datos");
//		cambioDeFrame(driver, By.className("slds-checkbox--faux"), 0);
//		driver.findElements(By.className("slds-checkbox--faux")).get(2).click();
//		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "descuento de saldo");
//		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("alert-ok-button")));
//		driver.findElement(By.id("alert-ok-button")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-radio-control.ng-scope.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required")));
//		WebElement msj = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-radio-control.ng-scope.ng-dirty.ng-valid-parse.ng-valid.ng-valid-required"));
//		msj = msj.findElement(By.className("vlc-slds-error-block"));
//		Assert.assertTrue(msj.getText().toLowerCase().contains("el saldo no es suficiente para comprar el pack"));
//		//Assert.assertTrue(false);//investigar si debe ser asi o si debe negar la activacion del pack si ya esta activo
//	}
//	
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito";
		detalles = null;
		detalles = "Renovacion de cuota "+imagen+"-DNI:"+sDNI;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDNI);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("50 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(10000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		cc.obligarclick(driver.findElement(By.id("AltaHuawei_nextBtn")));
		sleep(12000);
		String sOrder = cc.obtenerOrden(driver, "Reseteo de Cuota");
		System.out.println("Orden"+sOrder);
		detalles += "-Orden:"+sOrder+"- Linea"+sLinea;
		System.out.println("Order: " + sOrder + " Fin");
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaconSaldoConTC")
	public void TS130065_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Reseteo_200_MB_por_Dia_TC_con_Credito(String sDNI, String sLinea, String accid, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "TS130065";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
//		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//		for(WebElement elemento:elementos) {
//			if(elemento.getText().contains("200 MB")) {
//				elemento.findElement(By.className("slds-checkbox")).click();
//				break;
//			}
//		}
//		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
//		sleep(10000);
//		List<WebElement> pagos = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
//		for (WebElement pago : pagos) {
//			if (pago.getText().toLowerCase().contains("factura")){
//				pago.click();
//				break;
//			}
//		}
//		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(5000);
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "factura");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(10000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		
		sleep(15000);
		String sOrden = cc.obtenerOrden2(driver);
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
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaSinSaldo")
	public void TS130067_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Sin_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS130067";
		detalles = null;
		detalles = imagen+"-Renovacion de cuota-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().equalsIgnoreCase("saldo insuficiente"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS130068_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Reseteo_200_MB_por_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea , String accid) {
		imagen = "TS130068";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("200 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "descuento de saldo");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-box.ng-scope")));
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));
		sleep(20000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println(datosInicial);
		System.out.println(datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaconSaldoConTC")
	public void TS135399_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Internet_50_MB_Dia_TC_con_Credito(String sDNI, String sLinea, String accid, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "TS135399";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
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
		sleep(8000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println(datosInicial);
		System.out.println(datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);
	}
	
//	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaSinSaldoConTC")
//	public void TS135400_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Internet_50_MB_Dia_TC_sin_Credito(String sDNI, String sLinea,String accid ,String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
//		imagen = "135400";
//		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
//		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		ges.BuscarCuenta("DNI", sDNI);
//		System.out.println("id "+accid);
//		detalles +="-Cuenta:"+accid;
//		ges.irAGestionEnCard("Renovacion de Datos");
//		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
//		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
//		for(WebElement UnE:elementos) {
//			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
//				UnE.findElement(By.className("slds-checkbox")).click();
//			}
//		}
//		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
//		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
//		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
//		String sOrden = cc.obtenerOrden2(driver);
//		detalles += "-Orden:" + sOrden;
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
//		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
//		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
//		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
//		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
//		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
//		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
//		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
//		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
//		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
//		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
//		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
//		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
//		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
//		sleep(8000);
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
//		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
//		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
//		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
//		sleep(15000);
//		String orden = cc.obtenerTNyMonto2(driver, sOrden);
//		System.out.println("orden = "+orden);
//		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
//		sleep(5000);
//		driver.navigate().refresh();
//		sleep(8000);
//		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
//		System.out.println(datosInicial);
//		System.out.println(datosFinal);
//		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
//		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
//		boolean condition = false;
//		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
//		for (WebElement fila : tabla) {
//			if (fila.getText().contains("Estado")) {
//				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
//				System.out.println(datos);
//				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
//				break;
//			}
//		}
//		Assert.assertTrue(condition);	
//	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS135401_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Internet_50_MB_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea , String accid) {
		imagen = "TS135401";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println(datosInicial);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "descuento de saldo");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-box.ng-scope")));
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));
		sleep(10000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println(datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaconSaldoConTC")
	public void TS135407_CRM_Movil_REPRO_Renovacion_de_cuota_Telefonico_Rastreo_Internet_por_Dia_Limitrofe_TC_con_Credito(String sDNI, String sLinea,String accid , String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "135407";
		detalles = null;
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDNI);
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("Lim\u00edtrofe")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
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
		sleep(8000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(15000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial))<=Integer.parseInt(datosFinal));
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		sleep(5000);
		driver.navigate().refresh();
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);	
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test(groups = {"GestionesPerfilAgente", "RenovacionDeCuota","E2E","Ciclo1"}, dataProvider="RenovacionCuotaConSaldo") 
	public void TS135402_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea, String accid){
		imagen = "TS135402";
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
//		ges.BuscarCuenta("DNI", sDNI);
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Renovacion de Datos");
		sleep(8000);
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> wCheckBox = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		wCheckBox.get(1).click();
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		sleep(8000);
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
	}
}