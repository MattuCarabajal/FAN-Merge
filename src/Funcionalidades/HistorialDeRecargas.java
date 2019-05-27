package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class HistorialDeRecargas extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		sleep(5000);
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	//@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134787_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_FAN_Front_OOCC(String sDNI,String sLinea) {
		imagen = "TS134787";
		detalles = imagen + " - Historial de recargas - DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		boolean enc = false;
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03") ,0);
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Con Beneficios']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		cambioDeFrame(driver, By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"), 0);
		sleep(7000);
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		List<WebElement> recargas = tabla.findElements(By.tagName("td"));
		for(WebElement r : recargas){
			if(r.getText().contains("Con Beneficios")){
				System.out.println(r.getText());
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134788_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_Sin_Beneficios_FAN_Front_OOCC(String sDNI, String sLinea){
		imagen = "TS134788";
		detalles = imagen + "- Historial de recargas - DNI: " + sDNI;
		cbsm.Servicio_Recharge(sLinea,"25000000","0");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"),0);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> canales = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement canal : canales){
			if(canal.getText().equals("Otros")){
				canal.click();
				break;
			}	
		}
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Sin Beneficios']")).click();
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		List<WebElement> detallesUltimaRecarga = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr")).get(0).findElements(By.tagName("td"));
		String fecha = fechaDeHoy();
		String fechaDeRecarga = detallesUltimaRecarga.get(0).getText();
		boolean coincideLaFecha = fechaDeRecarga.contains(fecha);
		String beneficios = detallesUltimaRecarga.get(3).getText();
		boolean sinBeneficios = beneficios.equalsIgnoreCase("sin beneficios");
		String montoDeRecarga = "$25,00";
		boolean montoCorrecto = montoDeRecarga.equals(detallesUltimaRecarga.get(5).getText());
		Assert.assertTrue(coincideLaFecha && sinBeneficios && montoCorrecto);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134470_CRM_Movil_Prepago_Historial_De_Recargas_SOS_S440_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134470";
		detalles = imagen + "-Historial de recargas - DNI: " + sDNI;
		boolean enc = false;
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(899999)+10000000;
		for(int i=0;i<=2;i++) {
			cbsm.Servicio_Recharge(sLinea,"25000000", "0");
			sleep(1000);
		}
		cbsm.Servicio_Loan(sLinea,Integer.toString(intAleatorio));
		sleep(1000);
		cbsm.Servicio_Recharge(sLinea,"25000000", "0");
		sleep(1000);
		String monto = Integer.toString(intAleatorio);
		monto = monto.substring(0,4);
		System.out.println(monto);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas s.o.s");
		sleepFindBy(driver, By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"), 0);
		cambioDeFrame(driver,By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"),0);
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		cambioDeFrame(driver,By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
		for(WebElement r : recargas){
			if(r.getText().replaceAll("[$.,]","").contains(monto)){
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}

	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134473_CRM_Movil_Prepago_Historial_De_Packs_Fan_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134473";
		detalles = imagen + "- Historial de packs - DNI: " + sDNI;
		boolean enc = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver,By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"),0);
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		cambioDeFrame(driver,By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
		for(WebElement r : recargas){
			if(r.getText().contains("$")){
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134747_CRM_Movil_Prepago_Historial_de_Recargas_S141_FAN_Front_OOCC(String sDNI, String sLinea){
		imagen = "TS134747";
		detalles = imagen + "-Historial De Recarga-DNI: " + sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		cbsm.Servicio_Recharge(sLinea,monto , "0");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver,By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium']"),0);
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(conf.isDisplayed());
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		WebElement conf_1 = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
		Assert.assertTrue(conf_1.isDisplayed());
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-truncate slds-th__action']")));
		boolean a = false;
		List <WebElement> fecha = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));		
		for(WebElement x : fecha) {
			if(x.getText().toLowerCase().contains("fecha"))
				a= true;
		}
		Assert.assertTrue(a);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-grid slds-col']")));
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
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia))
				verif = true;
		}
		Assert.assertTrue(verif);
		Assert.assertTrue(recarga.equals(sMontoV2));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134786_CRM_Movil_Prepago_Historial_de_Recargas_Monto_total_FAN_Front_OOCC(String sDNI, String sLinea) { 
		imagen = "TS134786";
		detalles = imagen + "-Historial De Recarga-DNI :" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");;
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver,By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"),0);
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-bottom--small slds-p-left--medium']")));
		String montoTotalOriginal = driver.findElement(By.className("tableHeader")).findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")).findElement(By.tagName("span")).findElement(By.tagName("b")).getText();
		montoTotalOriginal = montoTotalOriginal.replaceAll("[$.,]", "");
		Integer montoTotalDeRecarga = Integer.parseInt(montoTotalOriginal);
		System.out.println(montoTotalDeRecarga);
		String montoARecargar = "30000000";
		cbsm.Servicio_Recharge(sLinea,montoARecargar , "0");
		Integer recarga = Integer.parseInt(montoARecargar.substring(0, 4));
		System.out.println(recarga);
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(10000);
		String montoTotalNuevo = driver.findElement(By.className("tableHeader")).findElement(By.className("slds-text-heading--medium")).findElement(By.tagName("b")).getText();
		montoTotalNuevo = montoTotalNuevo.replaceAll("[$.,]", "");
		Integer montoTRecarga = Integer.parseInt(montoTotalNuevo);
		System.out.println(montoTRecarga);
		Assert.assertTrue(montoTRecarga == (montoTotalDeRecarga + recarga));	
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134838_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_IVR_Fan_FRONT_OOCC(String sDNI , String sLinea) {
		imagen = "TS134838";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();		
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")).isDisplayed());
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		Assert.assertTrue(tabla.isDisplayed());
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134840_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Fecha_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134840";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		cbsm.Servicio_Recharge(sLinea,monto , "H");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia))
				verif = true;
		}
		Assert.assertTrue(verif);		
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134841_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_SMS_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134847";
		detalles = imagen + "-Historial De Recarga-DNI: " + sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		cbsm.Servicio_Recharge(sLinea,monto , "2");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> sms = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : sms){
			if(s.getText().equals("SMS"))
				s.click();
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia) && x.findElements(By.tagName("td")).get(2).getText().equals("SMS"))
				verif = true;
		}
		Assert.assertTrue(verif);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS134842_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_ROL_Fan_FRONT_OOCC(String sDNI, String sLinea) {
		imagen = "TS134842";
		detalles = imagen + "-Historial De Recarga-DNI :" + sDNI;
		cbsm.Servicio_Recharge(sLinea,"25000000", "E");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> canales = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement canal : canales){
			if(canal.getText().equals("Recarga Online")){
				canal.click();
				break;
			}	
		}
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		List<WebElement> detallesUltimaRecarga = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr")).get(0).findElements(By.tagName("td"));
		String fecha = fechaDeHoy();
		String fechaDeRecarga = detallesUltimaRecarga.get(0).getText();
		boolean coincideLaFecha = fechaDeRecarga.contains(fecha);
		String elCanal = detallesUltimaRecarga.get(2).getText();
		boolean rol = elCanal.equalsIgnoreCase("Recarga Online");
		String montoDeRecarga = "$25,00";
		String ultimaRecarga = detallesUltimaRecarga.get(5).getText();
		boolean montoCorrecto = montoDeRecarga.equals(ultimaRecarga);
		Assert.assertTrue(coincideLaFecha && rol && montoCorrecto);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135346_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_TODOS_Fan_FRONT_OOCC(String sDNI , String sLinea) {
		imagen = "TS135346";
		detalles = imagen + " - Historial de recargas - DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		ArrayList<String> canales = new ArrayList<String>(Arrays.asList("Recarga Online", "Otros", "*111#", "SMS", "Atenci\u00f3n al cliente / Mi Personal"));
		List<WebElement> recargasRealizadas = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr"));
		for (WebElement recargaRealizada : recargasRealizadas) {
			String canal = recargaRealizada.findElements(By.tagName("td")).get(2).getText();
			Assert.assertTrue(canales.contains(canal));
		}
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135361_CRM_Movil_Prepago_Otros_Historiales_Historial_de_ajustes_FAN_Front_OOCC_S138(String sDNI , String sLinea){
		imagen = "TS135361";
		detalles = imagen + "- Historial de packs - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de ajustes");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		boolean enc = false;
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
		for(WebElement r : recargas){
			if(r.getText().contains("$")){
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135468_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_TODOS_FAN_Front_OOCC(String sDNI , String sLinea){
		imagen = "TS135468";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("todos"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		Assert.assertTrue(datoTabla.matches("[0-9]{2}[\\/]{1}[0-9]{2}[\\/]{1}[0-9]{4} [0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135472_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Internacional_FAN_Front_OOCC(String sDNI , String sLinea){
		imagen = "TS135472";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("bam pack 10gb x 30 dias"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(datoTabla.toLowerCase().contains("bam pack 10gb x 30 dias"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135474_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Familia_FAN_Front_OOCC(String sDNI , String sLinea){
		imagen = "TS135474";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("internet 100 mb dia"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(datoTabla.toLowerCase().contains("internet 100 mb dia"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135476_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Internet_50_Mb_FAN_Front_OOCC(String sDNI , String sLinea){
		imagen = "TS135476";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("internet 50 mb dia nacional"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(datoTabla.toLowerCase().contains("internet 50 mb dia nacional"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "RecargasHistorias")
	public void TS135483_CRM_Movil_Prepago_Historial_de_Packs_Seleccion_de_Fechas_FAN_Front_OOCC(String sDNI , String sLinea) throws ParseException{
		imagen = "TS135483";
		boolean verificarFecha = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		String desde = driver.findElement(By.id("text-input-id-1")).getAttribute("value");
		String hasta = driver.findElement(By.id("text-input-id-2")).getAttribute("value");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDesde = sdf.parse(desde);
		Date fechaHasta = sdf.parse(hasta);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		datoTabla = datoTabla.substring(0, datoTabla.indexOf(" "));
		Date fechaTabla = sdf.parse(datoTabla);
		if (fechaTabla.compareTo(fechaDesde) >= 0 && fechaTabla.compareTo(fechaHasta) <= 0)
			verificarFecha = true;
		Assert.assertTrue(verificarFecha);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134789_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_Fan_FRONT_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134789";
		detalles = imagen+"-HistorialDeRecargasTelefonico-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);;
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
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134790_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_Sin_Beneficios_Fan_FRONT_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134790";
		detalles = null;
		detalles = imagen+"-HistorialDeRecargasTelefonico-DNI:"+sDNI;
		cbsm.Servicio_Recharge(sLinea,"10000000","0");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Sin Beneficios']")).click();
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		List<WebElement> detallesUltimaRecarga = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr")).get(0).findElements(By.tagName("td"));
		String fecha = fechaDeHoy();
		String fechaDeRecarga = detallesUltimaRecarga.get(0).getText();
		boolean coincideLaFecha = fechaDeRecarga.contains(fecha);
		String beneficios = detallesUltimaRecarga.get(3).getText();
		boolean sinBeneficios = beneficios.equalsIgnoreCase("sin beneficios");
		String montoDeRecarga = "$10,00";
		boolean montoCorrecto = montoDeRecarga.equals(detallesUltimaRecarga.get(5).getText());
		Assert.assertTrue(coincideLaFecha && sinBeneficios && montoCorrecto);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134791_CRM_Movil_Prepago_Historial_de_Recargas_Monto_total_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134791";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-p-bottom--small slds-p-left--medium']")));
		String montoTotalOriginal = driver.findElement(By.className("tableHeader")).findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small.slds-p-left--medium")).findElement(By.tagName("span")).findElement(By.tagName("b")).getText();
		montoTotalOriginal = montoTotalOriginal.replaceAll("[$.,]", "");
		Integer montoTotalDeRecarga = Integer.parseInt(montoTotalOriginal);
		String montoARecargar = "30000000";
		cbsm.Servicio_Recharge(sLinea,montoARecargar, "0");
		Integer recarga = Integer.parseInt(montoARecargar.substring(0, 4));
		sleep(3000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(10000);
		String montoTotalNuevo = driver.findElement(By.className("tableHeader")).findElement(By.className("slds-text-heading--medium")).findElement(By.tagName("b")).getText();
		montoTotalNuevo = montoTotalNuevo.replaceAll("[$.,]", "");
		Integer montoTRecarga = Integer.parseInt(montoTotalNuevo);
		Assert.assertTrue(montoTRecarga == (montoTotalDeRecarga + recarga));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134792_CRM_Movil_Prepago_Historial_de_Recargas_S141_FAN_Front_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134792";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String monto = "10000000";
		String dia = fechaDeHoy();
		cbsm.Servicio_Recharge(sLinea,monto , "0");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver,By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium']"),0);
		WebElement conf = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"));
		Assert.assertTrue(conf.isDisplayed());
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		WebElement conf_1 = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium"));
		Assert.assertTrue(conf_1.isDisplayed());
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-truncate slds-th__action']")));
		boolean a = false;
		List <WebElement> fecha = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));		
		for(WebElement x : fecha) {
			if(x.getText().toLowerCase().contains("fecha"))
				a= true;
		}
		Assert.assertTrue(a);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-grid slds-col']")));
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
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134793_CRM_Movil_Prepago_Historial_De_Recargas_SOS_S440_FAN_Front_Telefonico(String cDNI,String sLinea) {
		imagen = "TS134793";
		detalles = imagen + " -Historial de recargas - DNI: " + cDNI;
		boolean enc = false;
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(899999)+10000000;
		for(int i=0;i<=2;i++) {
			cbsm.Servicio_Recharge(sLinea,"10000000" , "0");
			sleep(1000);
		}
		cbsm.Servicio_Loan(sLinea,Integer.toString(intAleatorio));
		sleep(1000);
		cbsm.Servicio_Recharge(sLinea,"25000000", "0");
		sleep(1000);
		String monto = Integer.toString(intAleatorio);
		monto = monto.substring(0,4);
		ges.BuscarCuenta("DNI", cDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas s.o.s");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
		for(WebElement r : recargas){
			if(r.getText().replaceAll("[$.,]","").contains(monto)){
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
		
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134839_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Fecha_Fan_FRONT_Telefonico(String sDNI, String sLinea){
		imagen = "TS134839";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "30000000";
		cbsm.Servicio_Recharge(sLinea,monto,"0");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia))
				verif = true;
		}
		Assert.assertTrue(verif);	
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134844_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_IVR_Fan_FRONT_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134844";
		boolean histDeRecargas = false, histDePacks = false, histDeRecargasSOS = false, histDeAjustes = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button_brand"), 0);
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
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();		
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")).isDisplayed());
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		Assert.assertTrue(tabla.isDisplayed());
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134845_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_SMS_Fan_FRONT_Telefonico(String sDNI, String sLinea){
		imagen = "TS134845";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "10000000";
		cbsm.Servicio_Recharge(sLinea,monto,"2");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> sms = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : sms){
			if(s.getText().equals("SMS"))
				s.click();
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia) && x.getText().contains("SMS"))
				verif = true;
		}
		Assert.assertTrue(verif);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134846_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_ROL_Fan_FRONT_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134846";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		cbsm.Servicio_Recharge(sLinea,"10000000", "E");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> canales = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement canal : canales){
			if(canal.getText().equals("Recarga Online")){
				canal.click();
				break;
			}	
		}
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		List<WebElement> detallesUltimaRecarga = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr")).get(0).findElements(By.tagName("td"));
		String fecha = fechaDeHoy();
		String fechaDeRecarga = detallesUltimaRecarga.get(0).getText();
		boolean coincideLaFecha = fechaDeRecarga.contains(fecha);
		String elCanal = detallesUltimaRecarga.get(2).getText();
		boolean rol = elCanal.equalsIgnoreCase("Recarga Online");
		String montoDeRecarga = "$10,00";
		String ultimaRecarga = detallesUltimaRecarga.get(5).getText();
		boolean montoCorrecto = montoDeRecarga.equals(ultimaRecarga);
		Assert.assertTrue(coincideLaFecha && rol && montoCorrecto);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS134847_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_Atencion_al_cliente_Fan_FRONT_Telefonico(String sDNI, String sLinea){
		imagen = "TS134847";
		detalles = imagen+"-Historial De Recarga-DNI:"+sDNI;
		String dia = fechaDeHoy();
		String monto = "10000000";
		cbsm.Servicio_Recharge(sLinea,monto,"J");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.verificacionDeHistorial("Historial de packs");
		cc.verificacionDeHistorial("Historial de ajustes");
		cc.verificacionDeHistorial("Historial de recargas");
		cc.verificacionDeHistorial("Historial de recargas S.O.S");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		List<WebElement> atencion = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement a : atencion){
			if(a.getText().contains("Atenci\u00f3n al cliente"))
				a.click();
		}
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();	
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		boolean verif = false;
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).getLocation().y+" )");
		WebElement verificacion = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		for(WebElement x : verificacion.findElements(By.tagName("tr"))) {
			if(x.findElements(By.tagName("td")).get(0).getText().contains(dia) && x.getText().contains("Atenci\u00f3n al cliente"))
				verif = true;
		}
		Assert.assertTrue(verif);
		boolean a = false;
		List <WebElement> fecha = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));		
		for(WebElement x : fecha) {
			if(x.getText().toLowerCase().equals("canal")) {
				x.click();
				ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
				Assert.assertTrue(driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).isDisplayed());
				a= true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135347_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_TODOS_Fan_FRONT_Telefonico(String sDNI, String cLinea) {
		imagen = "TS135347";
		detalles = imagen + " -Historial de recargas - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.id("text-input-03"), 0);
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		ArrayList<String> canales = new ArrayList<String>(Arrays.asList("Recarga Online", "Otros", "*111#", "SMS", "Atenci\u00f3n al cliente / Mi Personal"));
		List<WebElement> recargasRealizadas = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr"));
		for (WebElement recargaRealizada : recargasRealizadas) {
			String canal = recargaRealizada.findElements(By.tagName("td")).get(2).getText();
			Assert.assertTrue(canales.contains(canal));
		}
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135437_CRM_Movil_Prepago_Historial_De_Packs_Fan_Front_Telefonico(String cDNI , String sLinea) {
		boolean enc = false;
		imagen = "TS135437";
		detalles = imagen+"-HistorialDePacksTelefonico - DNI: "+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver,By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"),0);
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		cambioDeFrame(driver,By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
		for(WebElement r : recargas){
			if(r.getText().contains("$")){
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135467_CRM_Movil_Prepago_Historial_de_Packs_Fan_Front_Telefonico(String cDNI , String sLinea) {
		boolean enc = false;
		imagen = "TS135467";
		detalles = imagen+"-HistorialDePacksTelefonico - DNI:"+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver,By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"),0);
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		cambioDeFrame(driver,By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
		for(WebElement r : recargas){
			if(r.getText().contains("$")){
				enc = true;
				break;
			}
		}
		Assert.assertTrue(enc);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135469_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_TODOS_FAN_Front_Telefonico(String sDNI , String sLinea){
		imagen = "TS135469";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("todos"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		Assert.assertTrue(datoTabla.matches("[0-9]{2}[\\/]{1}[0-9]{2}[\\/]{1}[0-9]{4} [0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135475_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Familia_FAN_Front_Telefonico(String sDNI , String sLinea){
		imagen = "TS135475";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("internet 100 mb dia"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(datoTabla.toLowerCase().contains("internet 100 mb dia"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135477_CRM_Movil_Prepago_Historial_de_Packs_Nombre_del_Pack_Plan_Internet_40_Mb_FAN_Front_Telefonico(String sDNI , String sLinea){
		imagen = "TS135477";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));;
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().toLowerCase().contains("1 gb renovacion cuota de datos"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(datoTabla.toLowerCase().contains("1 gb renovacion cuota de datos"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
	public void TS135484_CRM_Movil_Prepago_Historial_de_Packs_Seleccion_de_Fechas_FAN_Front_Telefonico(String sDNI , String sLinea) throws ParseException{
		imagen = "TS135484";
		boolean verificarFecha = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		String desde = driver.findElement(By.id("text-input-id-1")).getAttribute("value");
		String hasta = driver.findElement(By.id("text-input-id-2")).getAttribute("value");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDesde = sdf.parse(desde);
		Date fechaHasta = sdf.parse(hasta);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")));
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		datoTabla = datoTabla.substring(0, datoTabla.indexOf(" "));
		Date fechaTabla = sdf.parse(datoTabla);
		if (fechaTabla.compareTo(fechaDesde) >= 0 && fechaTabla.compareTo(fechaHasta) <= 0)
			verificarFecha = true;
		Assert.assertTrue(verificarFecha);
	}
}