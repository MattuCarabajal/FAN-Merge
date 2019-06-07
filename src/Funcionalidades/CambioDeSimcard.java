package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.GestionFlow;
import Tests.TestBase;

public class CambioDeSimcard extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	String imagen;
	String detalles;
	
//	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	//@BeforeClass (groups = "PerfilAgente")
		public void initAgente() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges =  new GestionDeClientes_Fw(driver);
		cbsm = new CBS_Mattu();
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
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
	
	@Test(groups = { "GestionesPerfilOficina","CambioSimcardDer", "E2E","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS134381_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Voluntario_Ofcom_Presencial_Con_entega_de_pedido(String sDNI, String sLinea, String accid) throws AWTException	{
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:"+accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio de Simcard");
		cambioDeFrame(driver, By.id("SelectAsset0"), 0);
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
//		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cc.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
//		sleep(16000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
//		sleep(16000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
//		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		String invoice = cc.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
//		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
		cbsm.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		if(activarFalsos==true) {
			boolean esta = false;
			List<WebElement> campos = tabla.findElements(By.tagName("tr"));
			for(WebElement campo: campos) {
				if(campo.getText().toLowerCase().contains("tracking status")) {
					Assert.assertTrue(campo.getText().toLowerCase().contains("preparar pedido"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
		}
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test(groups = { "GestionesPerfilOficina","CambioDeSimcard", "E2E","Ciclo3" }, priority = 1, dataProvider = "SimCardSiniestroOfCom")
	public void TS134382_CRM_Movil_REPRO_Cambio_de_simcard_sin_costo_Siniestro_Ofcom_Presencial_Con_entega_de_pedido(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "134382";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:" + accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio de Simcard");
		cambioDeFrame(driver, By.id("SelectAsset0"), 0);
		driver.findElement(By.id("SelectAsset0")).findElement(By.cssSelector(".slds-radio.ng-scope")).click();
		driver.findElement(By.id("AssetSelection_nextBtn")).click();
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cc.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(16000);
		String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
		detalles += "-Orden:" + orden;
		System.out.println("Orden " + orden);
		orden = orden.substring(orden.length()-8);
		cc.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
		sleep(15000);
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cc.obtenerMontoyTNparaAlta(driver, orden);
		System.out.println(invoice);
		detalles+="-Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		CBS_Mattu invoSer = new CBS_Mattu();
		if(activarFalsos==true)
			invoSer.Servicio_NotificarEmisionFactura(orden);
		sleep(10000);
		driver.navigate().refresh();
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
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
//		try {
//			cc.cajonDeAplicaciones("Consola FAN");
//		} catch(Exception e) {
//			waitForClickeable(driver,By.id("tabBar"));
//			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
//			sleep(6000);
//		}
		driver.switchTo().defaultContent();
		sleep(6000);
		cc.obtenerMontoyTNparaAlta(driver, orden);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioDeSimcardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroOfCom") 
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
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCardDer", "E2E" }, priority = 1, dataProvider = "SimCardSiniestroAG") //NO APARECE EL MEDIO DE PAGO
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
//			cc.cajonDeAplicaciones("Consola FAN");
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
//			cc.cajonDeAplicaciones("Consola FAN");
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
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard", "E2E" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS158806_CRM_Movil_PRE_Cambio_de_Simcard_suspension_siniestro_OOCC_presencial(String sDNI, String sLinea, String accid) {
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:"+accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		esperarElemento(driver, By.cssSelector("select[id='DeliveryMethodSelection']"), 0);
		selectByText(driver.findElement(By.cssSelector("select[id='DeliveryMethodSelection']")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview"), 0);
		esperarElemento(driver, By.xpath("//p[contains(text(),'Nro. orden')]"), 0);
		String orden = driver.findElement(By.xpath("//p[contains(text(),'Nro. orden')]")).getText();
		orden = orden.substring(orden.length()-8, orden.length());
		System.out.println("Orden: " + orden);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary"), 0);
		esperarElemento(driver, By.xpath("//div[contains(text(),'Order')]"), 0);
		String confirmacionNroDeOrden = driver.findElement(By.xpath("//div[contains(text(),'Order')]")).getText();
		System.out.println(confirmacionNroDeOrden);
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		detalles += "-Orden:" + orden;
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
//		FALTA TERMINARLO / FLUJO INCOMPLETO
	}
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard", "E2E" }, priority = 1, dataProvider = "CambioSimCardOficina")
	public void TS159155_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_OOCC_presencial(String sDNI, String sLinea, String accid) {
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:"+accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		cambioDeFrame(driver, By.id("DeliveryMethodSelection"), 0);
		esperarElemento(driver, By.cssSelector("select[id='DeliveryMethodSelection']"), 0);
		selectByText(driver.findElement(By.cssSelector("select[id='DeliveryMethodSelection']")), "Presencial");
		clickBy(driver, By.id("DeliveryMethodConfiguration_nextBtn"), 0);
		cambioDeFrame(driver, By.id("InvoicePreview"), 0);
		esperarElemento(driver, By.xpath("//p[contains(text(),'Nro. orden')]"), 0);
		String orden = driver.findElement(By.xpath("//p[contains(text(),'Nro. orden')]")).getText();
		orden = orden.substring(orden.length()-8, orden.length());
		System.out.println("Orden: " + orden);
		clickBy(driver, By.id("InvoicePreview_nextBtn"), 0);
		cambioDeFrame(driver, By.id("OrderSumary"), 0);
		esperarElemento(driver, By.xpath("//div[contains(text(),'Order')]"), 0);
		String confirmacionNroDeOrden = driver.findElement(By.xpath("//div[contains(text(),'Order')]")).getText();
		System.out.println(confirmacionNroDeOrden);
		clickBy(driver, By.id("OrderSumary_nextBtn"), 0);
		detalles += "-Orden:" + orden;
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
//		FALTA TERMINARLO / FLUJO INCOMPLETO
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
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
//			cc.cajonDeAplicaciones("Consola FAN");
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
	
	@Test(groups = { "GestionesPerfilTelefonico","CambioSimCard","Ciclo 3", "E2E" }, priority = 1, dataProvider = "CambioSimCardTelef")
	public void TS159157_CRM_Movil_PRE_Cambio_de_Simcard_voluntario_Telefonico_store_pick_up(String sDNI, String sLinea, String accid, String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "134381";
		detalles = imagen + "-DNI:" + sDNI + "-Cuenta:" + accid;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Cambio SimCard");
		Assert.assertTrue(true);
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test(groups = { "GestionesPerfilAgente","Ciclo 3", "E2E", "CambioDeSimCard" }, priority = 1, dataProvider = "SimCardSiniestroAG")
	public void TS99020_CRM_Movil_REPRO_Cambio_de_SimCard_Presencial_Siniestro_DOC_Store_Pick_Up_TC_Agente(String sDNI, String sLinea,String sEntrega, String sProvincia, String sLocalidad, String sPuntodeVenta) throws AWTException {
		imagen = "99020";
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
		sleep(12000);
		pagePTelefo.getResumenOrdenCompra().click();
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		sleep(5000);
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
		sb.completarLogistica(sOrden, driver);
		sb.completarEntrega(sOrden, driver);
		CambiarPerfil("ofcom",driver);
//		try {
//			cc.cajonDeAplicaciones("Consola FAN");
//		} catch(Exception e) {
//			waitForClickeable(driver,By.id("tabBar"));
//			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
//			sleep(6000);
//		}
		
		driver.switchTo().defaultContent();
		sleep(6000);
		cCC.obtenerMontoyTNparaAlta(driver, sOrden);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		
		GestionFlow gf = new GestionFlow();
		String sIMSIFlow = gf.FlowIMSI(driver,sLinea);
		System.out.println("sIMSIFlow: " + sIMSIFlow);
	}
	
	@Test(groups = { "GestionesPerfilAgente","CambioSimCard", "E2E","Ciclo3" }, priority = 1, dataProvider = "CambioSimCardAgente")
	public void TS_Cambio_SimCard_Agente(String sDNI, String sLinea) throws AWTException {
		imagen = "TS_Cambio_SimCard_Agente";
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
		cCC.irAGestionEnCard("Cambio SimCard");
		sleep(10000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
		sleep(15000);
		Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
		metodoEntrega.selectByVisibleText("Presencial");
		cCC.obligarclick(driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")));
		sleep(16000);
		WebElement cereal = driver.findElement(By.id("ICCIDConfiguration"));
		System.out.println(cereal.getText());
		Assert.assertTrue(cereal.isDisplayed());
		driver.findElement(By.id("ICCDAssignment_nextBtn")).click();
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
					Assert.assertTrue(UnC.getText().toLowerCase().contains("entregado"));
					esta = true;
					break;
				}
			}
			Assert.assertTrue(esta);
		}
		sleep(2000);
		CambiarPerfil("logistica",driver);
		//cCC.obtenerMontoyTNparaAlta(driver, orden);
		sb.completarLogistica(orden, driver);
		sb.completarEntrega(orden, driver);
		CambiarPerfil("ofcom",driver);
		try {
//			cc.cajonDeAplicaciones("Consola FAN");
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
}