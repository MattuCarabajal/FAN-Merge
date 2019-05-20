package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class DetalleDeConsumos extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private SalesBase sb;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeClass (groups = "PerfilAgente")
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges =  new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
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

	@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga")
	public void TS134782_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134782";
		detalles = imagen + "- Detalle de Consumos - DNI: "+sDNI;
		boolean sms = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'SMS']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("SMS"))
			sms = true;
		Assert.assertTrue(sms);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga")
	public void TS134783_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134783";
		detalles = imagen + "- Detalle de Consumo - DNI: "+sDNI;
		boolean recarga = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'Recarga']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("Recarga"))
			recarga = true;
		Assert.assertTrue(recarga);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga")
	public void TS134784_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134784";
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		boolean llamadaDeVoz = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'Llamada de voz']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("Llamada de voz"))
			llamadaDeVoz = true;
		Assert.assertTrue(llamadaDeVoz);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaProblemaRecarga")
	public void TS134785_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_Otros_consumos_FAN_Front_OOCC_134785(String sDNI, String sLinea) {
		imagen = "TS134785";
		detalles = imagen + "- Detalles de Consumos -DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(25000);
		cc.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click")).get(1).findElement(By.cssSelector(".slds-button.slds-input__icon.slds-text-color--default")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
		}
	}
	
	//----------------------------------------------- TELEFONICO --------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")
	public void TS134802_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
		imagen = "TS134802";
		detalles = imagen + "-Vista 360 - DNI: " + sDNI + " - Nombre: " + sNombre;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(10000);
		cc.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click")).get(1).findElement(By.cssSelector(".slds-button.slds-input__icon.slds-text-color--default")).click();
		WebElement periodosDeConsulta = driver.findElement(By.cssSelector("[class='slds-grid slds-wrap slds-grid--pull-padded slds-m-around--medium slds-p-around--medium negotationsfilter'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--2-of-8 slds-large-size--2-of-8'] [class='slds-dropdown slds-dropdown--left'] [class='slds-dropdown__list slds-dropdown--length-3']"));
		periodosDeConsulta.findElements(By.tagName("li")).get(1).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
		}
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaProblemaRecarga")
	public void TS134803_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_Telefonico(String sDNI, String sLinea ){
		imagen = "TS134803";
		detalles = imagen + " -Detalle de consumos - DNI: " + sDNI;
		boolean sms = false;
		ges.BuscarCuenta("DNI", sDNI);
//		sb.BuscarCuenta("DNI", sDNI);
//		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'SMS']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("SMS"))
			sms = true;
		Assert.assertTrue(sms);
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = "PerfilAgente", dataProvider = "CuentaProblemaRecarga")
	public void TS134825_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Agentes(String cDNI, String cLinea) {
		imagen = "TS134825";
		detalles = imagen + " -DetalleDeConsumos: " + cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		sleep(10000);
		cc.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click")).get(1).findElement(By.cssSelector(".slds-button.slds-input__icon.slds-text-color--default")).click();
		WebElement periodosDeConsulta = driver.findElement(By.cssSelector("[class='slds-grid slds-wrap slds-grid--pull-padded slds-m-around--medium slds-p-around--medium negotationsfilter'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--2-of-8 slds-large-size--2-of-8'] [class='slds-dropdown slds-dropdown--left'] [class='slds-dropdown__list slds-dropdown--length-3']"));
		periodosDeConsulta.findElements(By.tagName("li")).get(1).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
		}
	}
	
	@Test (groups = "PerfilAgente", dataProvider = "CuentaProblemaRecarga")
	public void TS134826_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134826";
		detalles = imagen + "Detalle de consumos -DNI:" + sDNI;
		boolean sms = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'SMS']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("SMS"))
			sms = true;
		Assert.assertTrue(sms);
	}
	
	@Test (groups = "PerfilAgente", dataProvider = "CuentaProblemaRecarga") 
	public void TS134827_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134827";
		detalles = imagen + "Detalle de consumos -DNI:" + sDNI;
		boolean recarga = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'Recarga']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("Recarga"))
			recarga = true;
		Assert.assertTrue(recarga);
	}
	
	@Test (groups = "PerfilAgente", dataProvider = "CuentaProblemaRecarga")
	public void TS134828_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134828";
		detalles = imagen + "Detalle de Consumos -DNI:" + sDNI+"-Linea: "+sLinea;
		boolean llamadaDeVoz = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-p-right--x-small.spacer")).findElement(By.tagName("span")).click();
		driver.findElements(By.id("text-input-02")).get(1).click();
		driver.findElement(By.xpath("//*[text() = 'Llamada de voz']")).click();
		WebElement filaTabla = driver.findElement(By.cssSelector(".slds-table.slds-table_striped.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		if (filaTabla.findElements(By.tagName("td")).get(1).getAttribute("title").equalsIgnoreCase("Llamada de voz"))
			llamadaDeVoz = true;
		Assert.assertTrue(llamadaDeVoz);
	}
	
	@Test (groups = "PerfilAgente", dataProvider = "CuentaProblemaRecarga")
	public void TS134829_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_Otros_consumos_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS134829";
		detalles = imagen + "Detalle de Consumos -DNI:" + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click")).get(1).findElement(By.cssSelector(".slds-button.slds-input__icon.slds-text-color--default")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(5000);
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		filas.get(0).click();
		filas = driver.findElements(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--medium'] tbody tr"));
		List<WebElement> detalles = filas.get(1).findElements(By.cssSelector(".slds-text-heading--label"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().equals(detallesReferencia.get(i)));
		}
	}
}