package MVP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.*;

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
	
	
	//@BeforeClass
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
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

	//@AfterClass(alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
		for (WebElement canal : canales){
			if (canal.getText().equals("Otros")){
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
		esperarElemento(driver, By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"), 0);
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilOficina", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
	public void TS134790_CRM_Movil_Prepago_Historial_de_Recargas_Consultar_detalle_de_Recargas_Sin_Beneficios_Fan_FRONT_Telefonico(String sDNI, String sLinea) {
		imagen = "TS134790";
		detalles = null;
		detalles = imagen+"-HistorialDeRecargasTelefonico-DNI:"+sDNI;
		cbsm.Servicio_Recharge(sLinea,"10000000","0");
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
		
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "RecargasHistorias")
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
		List<WebElement> recargasRealizadas = driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr [data-label='Canal'] div"));
		for (WebElement recargaRealizada : recargasRealizadas) {
			String canal = recargaRealizada.getText();
			Assert.assertTrue(canales.contains(canal));
		}
	}
}