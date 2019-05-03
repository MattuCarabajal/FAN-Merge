package Funcionalidades;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class HistorialDeRecargas extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void init() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
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
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch(NoSuchElementException e) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Elemento no encontrado en ningun frame 2.");			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if (UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
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
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134787_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_FAN_Front_OOCC(String sDNI) {
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
	public void TS134788_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_Sin_Beneficios_FAN_Front_OOCC(String sDNI){
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
	
	@Test (groups= {"GestionesPerfilOficina", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134470_CRM_Movil_Prepago_Historial_De_Recargas_SOS_S440_FAN_Front_OOCC(String cDNI, String cLinea) {
		imagen = "TS134470";
		detalles = null;
		detalles = imagen + "-Historial de recargas - DNI: "+cDNI;
		boolean enc = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		for(int i=0;i<=2;i++) {
			cCBSM.Servicio_Recharge(cLinea,"25000000", "0");
			sleep(1000);
		}
		cCBSM.Servicio_Loan(cLinea,"15000000");
		sleep(1000);
		cCBSM.Servicio_Recharge(cLinea,"25000000", "0");
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
	
	@Test (groups = {"GestionesPerfilOficina", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "HistoriaRecarga")
	public void TS134747_CRM_Movil_Prepago_Historial_de_Recargas_S141_FAN_Front_OOCC(String sDNI, String sLinea){
		imagen = "TS134747";
		detalles = null;
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_Recharge(sLinea,monto , "0");
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
		boolean verif = false;
		Integer recarga = Integer.parseInt(monto.substring(0, 4));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		String sMonto = verificacion.findElements(By.tagName("td")).get(5).getText();
		sMonto = sMonto.replaceAll("[$,]", "");
		Integer sMontoV2 = Integer.parseInt(sMonto);
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia)) {
				verif = true;
			}
		}
		Assert.assertTrue(verif);
		Assert.assertTrue(recarga.equals(sMontoV2));
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
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		String montoTotalOriginal = driver.findElement(By.className("tableHeader")).findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")).findElement(By.tagName("span")).findElement(By.tagName("b")).getText();
		montoTotalOriginal = montoTotalOriginal.replaceAll("[$.,]", "");
		Integer montoTotalDeRecarga = Integer.parseInt(montoTotalOriginal);
		System.out.println(montoTotalDeRecarga);
		String montoARecargar = "30000000";
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_Recharge(sLinea,montoARecargar , "0");
		Integer recarga = Integer.parseInt(montoARecargar.substring(0, 4));
		System.out.println(recarga);
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		String montoTotalNuevo = driver.findElement(By.className("tableHeader")).findElement(By.className("slds-text-heading--medium")).findElement(By.tagName("b")).getText();
		montoTotalNuevo = montoTotalNuevo.replaceAll("[$.,]", "");
		Integer montoTRecarga = Integer.parseInt(montoTotalNuevo);
		System.out.println(montoTRecarga);
		Assert.assertTrue(montoTRecarga == (montoTotalDeRecarga + recarga));
		
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "HistoriaRecarga")
	public void TS134840_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Fecha_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134840";
		detalles = null;
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_Recharge(sLinea,monto , "0");
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
		sleep(3000);
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia)) {
				verif = true;
			}
		}
		Assert.assertTrue(verif);
		
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
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
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
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134791_CRM_Movil_Prepago_Historial_de_Recargas_Monto_total_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134791";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		CustomerCare cc = new CustomerCare(driver);
		//cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAHistoriales();
		sleep(5000);
		WebElement historialDeRecargas = null;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button_brand")));
		for (WebElement x : driver.findElements(By.className("slds-card"))) {
			if (x.getText().toLowerCase().contains("historial de recargas")) {
				historialDeRecargas = x;
			}
		}
		historialDeRecargas.findElement(By.cssSelector(".slds-button.slds-button_brand")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		String montoTotalOriginal = driver.findElement(By.className("tableHeader")).findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")).findElement(By.tagName("span")).findElement(By.tagName("b")).getText();
		montoTotalOriginal = montoTotalOriginal.replaceAll("[$.,]", "");
		Integer montoTotalDeRecarga = Integer.parseInt(montoTotalOriginal);
		System.out.println(montoTotalDeRecarga);
		String montoARecargar = "30000000";
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_Recharge(sLinea,montoARecargar, "0");
		Integer recarga = Integer.parseInt(montoARecargar.substring(0, 4));
		System.out.println(recarga);
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		String montoTotalNuevo = driver.findElement(By.className("tableHeader")).findElement(By.className("slds-text-heading--medium")).findElement(By.tagName("b")).getText();
		montoTotalNuevo = montoTotalNuevo.replaceAll("[$.,]", "");
		Integer montoTRecarga = Integer.parseInt(montoTotalNuevo);
		System.out.println(montoTRecarga);
		Assert.assertTrue(montoTRecarga == (montoTotalDeRecarga + recarga));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "HistorialDeRecargas", "E2E", "Ciclo2"}, dataProvider = "RecargasHistorias")
	public void TS134792_CRM_Movil_Prepago_Historial_de_Recargas_S141_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134792";
		detalles = null;
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String monto = "30000000";
		String dia = fechaDeHoy();
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_Recharge(sLinea,monto , "0");
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
		boolean verif = false;
		Integer recarga = Integer.parseInt(monto.substring(0, 4));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		String sMonto = verificacion.findElements(By.tagName("td")).get(5).getText();
		sMonto = sMonto.replaceAll("[$,]", "");
		Integer sMontoV2 = Integer.parseInt(sMonto);
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia)) {
				verif = true;
			}
		}
		Assert.assertTrue(verif);
		Assert.assertTrue(recarga.equals(sMontoV2));
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134793_CRM_Movil_Prepago_Historial_De_Recargas_SOS_S440_FAN_Front_Telefonico(String cDNI,String sLinea) {
		imagen = "TS134793";
		detalles = null;
		detalles = imagen + " -Historial de recargas - DNI: " + cDNI;
		boolean enc = false;
		CBS_Mattu cCBSM = new CBS_Mattu();
		for(int i=0;i<=2;i++) {
			cCBSM.Servicio_Recharge(sLinea,"25000000" , "0");
			sleep(1000);
		}
		cCBSM.Servicio_Loan(sLinea,"15000000");
		sleep(1000);
		cCBSM.Servicio_Recharge(sLinea,"25000000", "0");
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
	
	@Test (groups= {"GestionesPerfilTelefonico", "HistorialDeRecargas", "Ciclo2"},  dataProvider = "HistoriaRecarga")
	public void TS134839_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Fecha_Fan_FRONT_Telefonico(String sDNI, String sLinea){
		imagen = "TS134839";
		detalles = null;
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		CBS_Mattu cCBSM = new CBS_Mattu();
		cCBSM.Servicio_Recharge(sLinea,monto,"0");
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
		sleep(3000);
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia)) {
				verif = true;
			}
		}
		Assert.assertTrue(verif);
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
		Assert.assertTrue(false);
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
}