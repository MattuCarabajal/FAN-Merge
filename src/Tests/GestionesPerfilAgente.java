package Tests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;

public class GestionesPerfilAgente extends TestBase{

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private Marketing mk;
	List <String> datosOrden =new ArrayList<String>();
	PagePerfilTelefonico ppt;
	String imagen;
	String detalles;
	
	@BeforeClass(alwaysRun=true)
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		sb = new SalesBase(driver);
		mk = new Marketing(driver);
		loginMerge(driver);
		//sleep(22000);
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
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(10000);
		goToLeftPanel2(driver, "Inicio");
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
		
		sleep(15000);
	}
	
	@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		datosOrden.add(detalles);
		guardarListaTxt(datosOrden);
		datosOrden.clear();
		tomarCaptura(driver,imagen);
	}
	
	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		//guardarListaTxt(datosOrden);
		driver.quit();
		sleep(5000);
	}
	
	@Test (groups = {"GestionesPerfilAgente","Recargas","E2E", "Ciclo1"}, dataProvider="RecargaTC")
	public void TS134322_CRM_Movil_REPRO_Recargas_Presencial_TC_Agente(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTipoDNI, String sDNITarjeta, String sTitular, String sPromo, String sCuotas) throws AWTException, KeyManagementException, NoSuchAlgorithmException {
		//Check All
		imagen = "TS134322";//00006559
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
//		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
//		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
//		//System.out.println("Saldo original: " + iMainBalance);
		
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
//		Assert.assertTrue(iMainBalance+monto == uiMainBalance);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
	}
	//       ESTE 1
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
	
	@Test (groups = {"GestionesPerfilAgente", "AnulacionDeVenta", "E2E","Ciclo4"}, dataProvider = "CuentaAnulacionDeVenta")
	public void Anulacion_De_Venta(String cDNI) {
		imagen = "Anulacion_De_Venta";
		detalles = null;
		detalles = imagen + "Anulacion de venta -DNI:" + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("anulacion de ordenes");
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
	
	@Test (groups = {"GestionesPerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS134814_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_credito_prepago_de_la_linea_FAN_Front_Agentes(String sDNI){
		imagen = "TS134814";
		detalles = null;
		detalles = imagen + "Consulta de Saldo -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.openleftpanel();
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		WebElement cred = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[2]/div[1]/ng-include/section[1]/div[2]/ul[2]/li[1]/span[3]"));
		Assert.assertTrue(!(cred.getText().isEmpty()));
	}
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga") 
	public void TS134827_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_FAN_Front_Agentes(String cDNI, String cLinea){
		imagen = "TS134827";
		detalles = null;
		detalles = imagen + "Detalle de consumos -DNI:" + cDNI;
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cCC.irADetalleDeConsumos();
		sleep(12000);
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
		WebElement plan = driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(0);
		System.out.println(plan.getText());
		System.out.println(plan.getAttribute("value"));
		Assert.assertTrue(plan.isDisplayed());
		}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga")
	public void TS134826_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_Agentes(String sDNI, String sLinea){
		CustomerCare cCC = new CustomerCare(driver);
		imagen = "TS134826";
		detalles = null;
		detalles = imagen + "Detalle de consumos -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
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
		WebElement plan = driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(2);
		System.out.println(plan.getText());
		//System.out.println(plan.getAttribute("value"));
		Assert.assertTrue(plan.isDisplayed());
		
		/*driver.switchTo().frame(cambioFrame(driver, By.id("advancerFilters")));
		WebElement dmso = driver.findElements(By.xpath("//*[@id='j_id0:j_id5']/div//div[2]/ng-include/div/div[2]/div[*]")).get(2).findElement(By.className("unit-div"));
		System.out.println(dmso.getText());
		Assert.assertTrue(dmso.isDisplayed());*/
	}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaModificacionDeDatos")
	public void TS134828_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134828";
		detalles = null;
		detalles = imagen + "Detalle de Consumos -DNI:" + sDNI+"-Linea: "+sLinea;
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
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
		List <WebElement> voz = driver.findElements(By.className("unit-div"));
			for(WebElement v : voz){
				System.out.println(v.getText());
				if(v.getText().toLowerCase().contains("minutos")){
				a=true;
				}
			}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaModificacionDeDatos")
	public void TS134829_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_Otros_consumos_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134829";
		detalles = null;
		detalles = imagen + "Detalle de Consumos -DNI:" + sDNI;
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
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
	
	@Test (groups = {"GestionesPerfilAgente", "ConsultaDeSaldo", "Ciclo1"}, dataProvider = "ConsultaSaldo")
	public void TS_134815_CRM_Movil_Prepago_Vista_360_Consulta_de_Saldo_Verificar_saldo_del_cliente_FAN_Front_Agentes(String sDNI) {
		imagen = "TS134815";
		detalles = null;
		detalles = imagen + "Consulta de saldo -DNI:" + sDNI;
		Marketing mk = new Marketing(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.openleftpanel();
		mk.closeActiveTab();
		//cc.irAFacturacion();
		sleep(5000);
		cc.irAFacturacion();
		/*driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		buscarYClick(driver.findElements(By.className("left-sidebar-section-header")), "equals", "facturaci\u00f3n");*/
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		boolean a = false;
		List <WebElement> saldo = driver.findElements(By.className("header-right"));
		for(WebElement x : saldo) {
			if(x.getText().toLowerCase().contains("saldo")) {
				a = true;
			}
			Assert.assertTrue(a);
		}
	}
	
	@Test(groups = {"GestionesPerfilAgente","Sales", "PreparacionNominacion","E2E","Ciclo1"}, dataProvider="DatosSalesNominacionNuevoAgente") 
	public void TS_CRM_Nominacion_Argentino_Agente(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail, String sProvincia, String sLocalidad, String sCalle, String sNumCa, String sCP) { 
		imagen = "TS_CRM_Nominacion_Argentino_Agente"+sDni;
		File directory = new File("Dni.jpg");
		detalles = null;
		detalles = imagen+"-Linea: "+sLinea;
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		SalesBase SB = new SalesBase(driver);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta")||UnaL.getText().toLowerCase().contains("plan prepago nacional")) {
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
		contact.tipoValidacion("documento");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
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
	@Test (groups = {"GestionesPerfilAgente", "Vista360","Ciclo2"}, dataProvider="CuentaVista360")
	public void TS134818_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea){
		imagen = "TS134818";
		detalles = null;
		detalles = imagen + "Vista 360-DNI:" + sDNI;
		BasePage cambioFrameByID=new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver); 
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(10000);
		boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement wTabla = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> lTabla = wTabla.findElements(By.tagName("tr"));
 		for(WebElement x : lTabla) {
 			if(x.getText().toLowerCase().contains("activo")){
 			System.out.println(x.getText());
 			a = true;
 			}
 		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionNuevoAgente")
	public void TS85100_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_nuevo_Presencial_DOC_Agente(String sLinea, String sDni, String sNombre, String sApellido, String sSexo, String sFnac, String sEmail) {
		imagen = "TS85100";
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
		contact.tipoValidacion("documento");
		File directory = new File("DniMal.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "no");
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("identidad no superada")) {
				error = true;
			}
		}
		Assert.assertTrue(error);
	}
	
	@Test(groups = {"GestionesPerfilAgente", "RenovacionDeCuota","E2E","Ciclo1"}, dataProvider="RenovacionCuotaConSaldo") 
	public void TS135402_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Internet_50_MB_Dia_Descuento_de_saldo_con_Credito(String sDNI, String sLinea){
		imagen = "TS135402";
		detalles = "Renovacion de cuota: "+imagen+"DNI: "+sDNI+"Linea: "+sLinea;
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		String datosInicial = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		
		//Check all
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
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
		List<WebElement> wCheckBox = driver.findElements(By.cssSelector(".slds-radio.ng-scope"));
		wCheckBox.get(1).click();
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		String mesj = driver.findElement(By.cssSelector(".slds-box.ng-scope")).getText();
		System.out.println(mesj);
		Assert.assertTrue(mesj.equalsIgnoreCase("La operaci\u00f3n termino exitosamente"));
		String datosFinal = cCBS.ObtenerUnidadLibre(cCBSM.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cCBS.ObtenerValorResponse(cCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance < uiMainBalance);
	}
		
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134821_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea){
		imagen = "TS134821";
		detalles = null;
		detalles = imagen + "Vista 360 -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAGestiones();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(5000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).getLocation().y+" )");
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		//System.out.println(nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).getText());
		boolean ingresar = false;
		if(nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).isDisplayed()) {
			nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).click();
			ingresar = true;
		}
		Assert.assertTrue(ingresar);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS134836_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_los_datos_del_cliente_completos_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS134836";
		detalles = null;
		detalles = imagen + " -ActualizarDatos: " + sDNI;
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
	
	@Test (groups = {"GestionesPerfilAgente", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS129334_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_campo_Correo_Electronico_Cliente_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS129334";
		detalles = null;
		detalles = imagen + " -ActualizarDatos: " + sDNI;
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
	
	@Test (groups = {"GestionesPerfilAgente", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS121103_CRM_Movil_REPRO_Modificacion_de_datos_No_Actualizar_datos_Cliente_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS121103";
		detalles = null;
		detalles = imagen+"-Modificacion de datos No modificar-DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilAgente", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS121102_CRM_Movil_REPRO_Modificacion_de_datos_Cliente_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS121102";
		detalles = null;
		detalles = imagen + " -ActualizarDatos: " + sDNI;
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
	
	@Test (groups = {"GestionesPerfilAgente", "ModificarDatos", "E2E", "Ciclo3"}, dependsOnMethods = "TS121103_CRM_Movil_REPRO_Modificacion_de_datos_No_Actualizar_datos_Cliente_FAN_Front_Agentes")
	public void TS121099_CRM_Movil_REPRO_No_Actualizar_datos_Cliente_Perfil_FAN_Front_Agentes() {
		imagen = "TS121099";
		detalles = null;
		detalles = imagen+"-Modificacion de datos No modifica";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "ModificarDatos", "E2E", "Ciclo3"}, dataProvider = "CuentaModificacionDeDatos")
	public void TS121098_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_Cliente_Perfil_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS121098";
		detalles = null;
		detalles = imagen + " -ActualizarDatos: " + sDNI;
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
		sleep(15000);
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
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E","ConsultaPorGestion", "Ciclo2"}, dataProvider = "CuentaVista360")
	public void TS134831_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_Cerrada_Informacion_brindada_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea) {
		imagen = "TS134831";
		detalles = null;
		detalles = imagen+"-Vista 360 -DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "34372815");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(18000);
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
				if (cell.getText().equals("16")) {
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
				if (cell.getText().equals("16")) {
					cell.click();
				}
			} catch (Exception e) {}
		}
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
		sleep(5000);
		boolean verificacion = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).getLocation().y+" )");
		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		List <WebElement> lista = nroCaso.findElements(By.tagName("tr"));
		for(WebElement x : lista) {
			if(x.getText().toLowerCase().contains("resuelta exitosa") || x.getText().toLowerCase().contains("realizada exitosa")) {
				System.out.println(x.getText());
				verificacion = true;
			}
		}
		Assert.assertTrue(verificacion);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TS135737_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Restriccion_Ident_de_Llamadas_Presencial_Agente(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135737";
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		//Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
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
		detalles +="-Servicio:RestriccionIdent.deLlamadas";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "Restriccion Ident. de Llamadas", driver);
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
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
	}
	
	@Test (groups = {"GestionesPerfilAgente","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS135754_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Transferencia_de_llamadas_Presencial_Agente(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135754";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Transferencia de Llamadas"));
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
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		//cc.openrightpanel();
		//cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:TransferenciaDeLlamadas";
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
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "Transferencia de Llamadas", driver);
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
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Transferencia de Llamadas"));
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Ajustes", "E2E", "Ciclo3"}, dataProvider = "CuentaAjustesPRE")
	public void TS135380_CRM_Movil_Prepago_Otros_Historiales_Historial_de_ajustes_Ordenamiento_por_Motivo_de_ajuste_FAN_Front_Agente(String sDNI, String sLinea) {
		imagen = "TS135380";
		boolean ajustePositivo = false;
		detalles = null;
		detalles = imagen+"-Ajuste-DNI:"+sDNI;
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
	
	@Test (groups = {"GestionesPerfilAgente", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnico")
	public void TS121364_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_reparaciones_de_equipos_Validacion_de_IMEI_Ok_Con_Garantia_Derivado_a_Servicio_Tecnico_Sin_Muleto_Terminal_reparada_y_entregada_Agente(String sDNI, String sLinea, String sIMEI) throws InterruptedException {
		imagen = "TS121364";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
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
		Assert.assertTrue(false); 
	}
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
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			waitForClickeable(driver,By.id("tabBar"));
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		
		driver.switchTo().defaultContent();
		sleep(6000);
		cCC.obtenerMontoyTNparaAlta(driver, sOrden);
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));
		
		GestionFlow gf = new GestionFlow();
		String sIMSIFlow = gf.FlowIMSI(driver,sLinea);
		System.out.println("sIMSIFlow: " + sIMSIFlow);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "ServicioTecnico","E2E", "Ciclo4"}, dataProvider = "serviciotecnico")
	public void TS121367_CRM_Movil_REPRO_Servicio_Tecnico_Realiza_configuraciones_de_equipos_Validacion_de_IMEI_Ok_Configuracion_Con_Garantia_Sin_Muleto_Reparar_ahora_Agente(String sDNI, String sLinea, String sIMEI, String sEmail, String sOpcion, String sSintoma) throws InterruptedException {
		imagen = "TS121367";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		searchAndClick(driver, "Servicio T\u00e9cnico");
		sleep(8000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		List<WebElement> clickOnButtons= driver.findElements(By.xpath(".//*[@class='borderOverlay']"));
		clickOnButtons.get(1).click();
		WebElement IMEI = driver.findElement(By.id("ImeiCode"));
		IMEI.click();
		IMEI.sendKeys(sIMEI);driver.findElement(By.id("ImeiInput_nextBtn")).click();
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
		sleep(8000);
		buscarYClick(driver.findElements(By.id("ClientInformation_nextBtn")),"equals", "continuar");
		sleep(8000);
		selectByText(driver.findElement(By.id("SelectOperationType")), sOpcion);
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

	@Test (groups = {"GestionesPerfilAgente", "DiagnosticoIncoveniente","E2E", "Ciclo3"}, dataProvider = "Diagnostico")
	public void TS119283_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_navegar_Antena_rojo_NO_BAM_Agente(String sDNI, String sLinea) throws Exception  {
		imagen = "TS119283";
		detalles = null;
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestionEnCard("Diagn\u00f3stico");// campo diagnostico no aparece en perfil agente
	    tech.clickDiagnosticarServicio("datos", "Datos", true);
	    tech.selectionInconvenient("No puedo navegar");
	    tech.continuar();
	    tech.seleccionarRespuesta("si");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga") 
	public void TS134825_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Agentes(String cDNI, String cLinea){
		imagen = "TS134825";
		detalles = null;
		detalles = imagen + " -DetalleDeConsumos: " + cDNI;
		CustomerCare cCC = new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
//		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
//		driver.findElement(By.className("card-top")).click();
//		sleep(15000);
		cCC.irADetalleDeConsumos();
		sleep(12000);
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
		List <WebElement> servicio = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.via-slds-table-pinned-header")).findElements(By.tagName("tr"));
		for(WebElement s : servicio) {
			if(s.isDisplayed()) {
				System.out.println("consumos realizados por el cliente");
			}
		}
	}
	
	@Test (groups = {"GestionesPerfilAgente","NumerosAmigos","E2E","Ciclo1"}, dataProvider="NumerosAmigosModificacion")
	public void TS139724_CRM_Movil_REPRO_FF_Modificacion_Agente(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		imagen = "TS139724";
		BasePage cambioFrame=new BasePage();
		CBS sCBS = new CBS();
		CBS_Mattu sCBSM = new CBS_Mattu();
		String sMainBalance = sCBS.ObtenerValorResponse(sCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
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
			Assert.assertTrue(sCBS.validarNumeroAmigos(sCBSM.Servicio_QueryCustomerInfo(sLinea), "voz",sNumeroVOZ));
		else
			Assert.assertTrue(sCBS.validarNumeroAmigos(sCBSM.Servicio_QueryCustomerInfo(sLinea), "sms",sNumeroSMS));
		datosOrden.add(cCC.obtenerOrden(driver, "N\u00fameros Gratis"));
		Assert.assertTrue(bAssert);
		sleep(5000);
		String orden = cc.obtenerOrden(driver, "Numero Gratis");
		datosOrden.add("Numeros amigos, orden numero: " + orden + " con numero de DNI: " + sDNI);
		sleep(10000);
		BasePage bBP = new BasePage();
		bBP.closeTabByName(driver, "N\u00fameros Gratis");
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		String uMainBalance = sCBS.ObtenerValorResponse(sCBSM.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance-2050000 >= uiMainBalance);
		Assert.assertTrue(mMarketing.verificarNumerosAmigos(driver, sNumeroVOZ, sNumeroSMS));
		//Verify when the page works
	}
	
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
	@Test (groups = {"GestionesPerfilAgente", "Modificar Datos", "E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDNI")
	public void TS129328_CRM_Movil_REPRO_Modificacion_de_datos_Actualizar_datos_campo_DNI_CUIT_Cliente_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS129328";
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
	
	@Test (groups = {"GestionesPerfilOficina", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TS135743_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Restriccion_Ident_de_Llamadas_Presencial_Agente(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135743";
		detalles = null;
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;
		GestionFlow gGF = new GestionFlow();
		Assert.assertTrue(gGF.FlowConsultaServicioInactivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
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
		detalles +="-Servicio:RestriccionIdent.deLlamadas";
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
		//driver.findElement(By.cssSelector(".slds-button.cpq-item-has-children")).click();//Plan con Tarjeta Repro button
		//ppt.getwPlanConTarjetaRepro().click();//Plan con Tarjeta Repro button
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "Restriccion Ident. de Llamadas", driver);
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
		Assert.assertTrue(gGF.FlowConsultaServicioActivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
	}
	
	
	@Test (groups = {"GestionesPerfilAgente","ModificarDatos","E2E", "Ciclo3"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS129330_CRM_Movil_REPRO_Modificacion_de_datos_No_Permite_Actualizar_datos_campo_DNI_Cliente_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS129330";
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
		if(driver.findElement(By.id("MobilePhone")).getAttribute("value").isEmpty()){
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
			if(x.getText().toLowerCase().equals("no se pueden modificar m\u00e1s de dos d\u00edgitos de su dni.")) {
				a =true;
				System.out.println(x.getText());
			}
		}
		Assert.assertTrue(a);
	}
	//98438 ejecutar de nuevo
	@Test (groups = {"GestionesPerfilAgente", "Nominacion", "Ciclo1"}, dataProvider="DatosNoNominacionExistenteAgente")
	public void TS134492_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Presencial_DOC(String sLinea, String sDni, String sSexo, String sFnac, String sEmail) {
		imagen = "TS134492";
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
		contact.searchContact2("DNI", sDni, "Masculino");
		waitForClickeable(driver,By.id("Contact_nextBtn"));
		driver.findElement(By.id("Contact_nextBtn")).click();
		//sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("DniMal.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "no");
		List<WebElement> errores = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope")); 
		boolean error = false;
		for (WebElement UnE: errores) {
			if (UnE.getText().toLowerCase().contains("identidad no superada")) {
				error = true;
			}
		}
		Assert.assertTrue(error);
	}
	
	@Test (groups= {"GestionesPerfilAgente", "Ciclo2", "Vista360"}, dataProvider = "documentacionVista360")
	public void TS134817_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_OOCC_Agentes(String sDNI){
		imagen = "TS134817";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		sleep(3000);
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "mis servicios");
		sleep(15000);
		//boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		WebElement verif = driver.findElement(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		if(verif.getText().toLowerCase().contains("servicios incluidos")) {
			//a = true;
		}
		//Assert.assertTrue(a);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")), "equals", "ver detalle");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds-card__header.slds-card__header.headerList")));
		boolean b = false;
		for(WebElement x : driver.findElements(By.cssSelector(".slds-card.slds-m-bottom--small"))) {
			if(x.getText().toLowerCase().contains("servicio") && x.getText().toLowerCase().contains("fecha") && x.getText().toLowerCase().contains("estado")) {
				b = true;
			}
		}
		Assert.assertTrue(b);
	}
	
	@Test (groups= {"GestionesPerfilAgente", "Ciclo2", "Vista360"}, dataProvider = "CuentaVista360")
	public void TS134819_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_Agentes(String sDNI, String sNombre, String sLinea){
		imagen = "TS134819";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+ sDNI+ "- Nombre: " + sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		WebElement nombre = driver.findElement(By.cssSelector(".slds-text-heading_large.title-card"));
		WebElement dni = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.account-detail-content"))) {
			if (x.getText().toLowerCase().contains(sDNI))
				dni = x;
		}
		Assert.assertTrue(nombre.getText().contains(sNombre) && dni.getText().contains(sDNI));
		WebElement tabla = driver.findElement(By.className("detail-card"));
		Assert.assertTrue(tabla.getText().toLowerCase().contains("atributo") && tabla.getText().toLowerCase().contains("nps"));
		WebElement tabla2 = driver.findElement(By.className("profile-box"));
		Assert.assertTrue(tabla2.getText().toLowerCase().contains("actualizar datos") && tabla2.getText().toLowerCase().contains("reseteo clave"));
		WebElement tabla3 = driver.findElement(By.className("left-sidebar-section-container"));
		Assert.assertTrue(tabla3.getText().contains("Correo Electr\u00f3nico") && tabla3.getText().contains("M\u00f3vil"));
		Assert.assertTrue(tabla3.getText().toLowerCase().contains("tel\u00e9fono alternativo") && tabla3.getText().toLowerCase().contains("club personal") && tabla3.getText().toLowerCase().contains("categor\u00eda"));
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134824_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
		imagen = "TS13824";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		boolean historial = false, recarga = false, renovacion = false, servicios = false, suscripciones = false, inconvenientes = false, diagnostico = false, numeros = false, cambioSim = false, cambioPlan = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		WebElement wFlyOutActionCard = driver.findElement(By.className("community-flyout-actions-card"));
		List<WebElement> wGestiones = wFlyOutActionCard.findElements(By.tagName("li"));
		for (WebElement wAux : wGestiones) {
			if (wAux.getText().toLowerCase().contains("historial de suspensiones"))
					historial = true;
			if(wAux.getText().toLowerCase().contains("recarga de cr\u00e9dito"))
					recarga = true;
			if(wAux.getText().toLowerCase().contains("renovacion de datos"))
					renovacion = true;	
			if(wAux.getText().toLowerCase().contains("alta/baja de servicios"))
					servicios = true;
			if(wAux.getText().toLowerCase().contains("suscripciones"))
					suscripciones = true;
			if(wAux.getText().toLowerCase().contains("inconvenientes de recargas"))
					inconvenientes = true;
			if(wAux.getText().toLowerCase().contains("diagn\u00f3stico"))
					diagnostico = true;
			if(wAux.getText().toLowerCase().contains("n\u00fameros gratis"))
					numeros = true;
			if(wAux.getText().toLowerCase().contains("cambio simcard"))
					cambioSim = true;
			if(wAux.getText().toLowerCase().contains("cambio de plan"))
					cambioPlan = true;
					
					
		}
	Assert.assertTrue(historial && recarga && renovacion && servicios && suscripciones && inconvenientes && diagnostico && numeros && cambioSim && cambioPlan);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134832_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
		imagen = "TS13832";
		detalles = null;
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		CustomerCare cCC=new CustomerCare(driver);
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cCC.irAGestiones();
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement table = driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		sleep(3000);
		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
		for (WebElement cell : tableRows) {
			try {
				if (cell.getText().equals("03")) {
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
				if (cell.getText().equals("28")) {
					cell.click();
					sleep(5000);
				}
			} catch (Exception e) {}
		}
		sleep(3000);
		driver.findElement(By.id("text-input-id-2")).click();
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
		sleep(3000);
		List<WebElement> tipo = driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"));
			for(WebElement t : tipo){
				if(t.getText().toLowerCase().equals("todos")){
					t.click();
				}
			}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
		sleep(9000);
		Boolean asd = true;
		List <WebElement> cuadro = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			for(WebElement c : cuadro){
				if(c.getText().toLowerCase().contains("Order")){
					asd = false;
				}
			}
		Assert.assertTrue(asd);
	}
	
	@Test (groups = {"GestionesPerfilAgente", "Vista360", "E2E", "Ciclo1"}, dataProvider = "CuentaVista360")
	public void TS134823_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
		imagen = "TS13823";
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
	
	@Test (groups = {"GestionesPerfilAgente","Sales", "PreparacionNominacion","E2E","Ciclo1"}, dataProvider="DatosNoNominacionExistenteTelefonico") 
	public void TS134493_CRM_Movil_REPRO_No_Nominatividad_No_Valida_Identidad_Cliente_Existente_Presencial_Preguntas_y_Respuestas(String sLinea, String sDni, String sSexo) {
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();		
		sleep(10000);
		driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		for (WebElement x: driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta") || x.getText().toLowerCase().contains("plan prepago nacional")) {
				x.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				break;
			}
		}
		sleep(10000);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sSexo);
		sleep(5000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("Contact_nextBtn")).getLocation().y+" )");
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
}