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
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class HistorialDePacks extends TestBase {
	
	private WebDriver driver;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	private SalesBase sb;
	String detalles;
	
	//@BeforeClass (groups= "PerfilOficina")
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit02();
		//ges.irAConsolaFAN();
	}
	
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
			if (x.getText().toLowerCase().contains("internet 50 mb dia"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).getText();
		Assert.assertTrue(datoTabla.toLowerCase().contains("internet 50 mb dia"));
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
	
	//==============================================     PERFIL TELEFONICO    =================================================================================
	
//	@Test (groups = "PerfilTelefonico", dataProvider = "RecargasHistorias")
//	public void TS135437_CRM_Movil_Prepago_Historial_De_Packs_Fan_Front_Telefonico(String cDNI , String sLinea) {
//		boolean enc = false;
//		imagen = "TS135437";
//		detalles = imagen+"-HistorialDePacksTelefonico - DNI: "+cDNI;
//		ges.BuscarCuenta("DNI", cDNI);
//		ges.irAGestionEnCard("Historiales");
//		cc.seleccionDeHistorial("historial de packs");
//		cambioDeFrame(driver,By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']"),0);
//		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
//		cambioDeFrame(driver,By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']"),0);
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
//		WebElement tabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElement(By.tagName("table")).findElements(By.tagName("tbody")).get(1);
//		List<WebElement> recargas = tabla.findElements(By.tagName("tr"));
//		for(WebElement r : recargas){
//			if(r.getText().contains("$")){
//				enc = true;
//				break;
//			}
//		}
//		Assert.assertTrue(enc);
//	}
	

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
