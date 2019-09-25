package R1;

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
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		vt = new VentaDePackFw(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
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
	public void initTelefonico() {
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
	public void initAgente() {
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
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
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
	
	@Test (groups = {"PerfilOficina", "R1"}, dataProvider = "PackAPRO")
	public void TS156780_CRM_Movil_Mix_Venta_de_oferta_OOCC_Pack_Roaming_Descuento_de_saldo(String sDNI, String sLinea, String sVentaPack, String sBanco, String sTarjeta, String sPromo, String sCuotas, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular){
		imagen = "TS156780";
		detalles = imagen+"-Venta de pack-DNI:"+sDNI;
		String dia = fecha();
		dia = dia.replaceAll("[/]", "");
		ges.BuscarCuenta("DNI", sDNI);
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 4));
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
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 4));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
		sleep(3000);
		Assert.assertTrue(ges.verificacionDeSMS(sLinea));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
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
		sleep(15000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(5000);
		int datoViejo = Integer.parseInt(ges.obtenerDatoSMSViejo());
		System.out.println(datoViejo);
		sleep(10000);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(15000);
		boolean a = false;
		for (int i= 0; i < 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if (datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				a = true;
			} else {
				sleep(15000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack , dia));
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", sDNI);
		int datoNuevo = Integer.parseInt(ges.obtenerDatoSMSNuevo(sLinea));
		System.out.println(datoNuevo);
		Assert.assertTrue(datoViejo + 40 == datoNuevo);
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R1"}, dataProvider = "PackAPRO")
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
		sleep(15000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(5000);
		int datoViejo = Integer.parseInt(ges.obtenerDatoSMSViejo());
		System.out.println(datoViejo);
		cbsm.Servicio_NotificarPago(sOrden);
		cc.buscarCasoParaPedidos(sOrden);
		sleep(7000);
		boolean a = false;
		for (int i= 0; i <= 10 ; i++) {
			cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
			ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
			WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
			String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
			if (datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
				a = true;
			} else {
				sleep(15000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sVentaPack, dia));
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", sDNI);
		int datoNuevo = Integer.parseInt(ges.obtenerDatoSMSNuevo(sLinea));
		System.out.println(datoNuevo);
		Assert.assertTrue(datoViejo + 40 == datoNuevo);
	}
}