package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.TestBase;

public class DetalleDeConsumos extends TestBase {

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
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaTriviasYSuscripciones")
	public void TS134782_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_OOCC_134782(String sDNI, String sLinea) {
		imagen = "TS134782";
		detalles = null;
		detalles = imagen + "- Detalle de Consumos - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "equals", "detalles de consumo");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea))
				s.click();
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		sleep(5000);
		Boolean a = false;
		driver.switchTo().frame(cambioFrame(driver, By.className("unit-div")));
		List <WebElement> msg = driver.findElements(By.className("unit-div"));
		for(WebElement m : msg){
			if(m.getText().toLowerCase().contains("mensajes"))
				a=true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS134783_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_FAN_Front_OOCC_134783(String sDNI, String sLinea) {
		imagen = "TS134783";
		detalles = null;
		detalles = imagen + "- Detalle de Consumo - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(25000);
		cc.irADetalleDeConsumos();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea))
				s.click();	
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		sleep(5000);
	    driver.switchTo().frame(cambioFrame(driver, By.className("summary-container")));
		WebElement ConsumoDatos =  driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(0);;
		Assert.assertTrue(ConsumoDatos.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2"}, dataProvider = "CuentaProblemaRecarga")
	public void TS134784_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_FAN_Front_OOCC(String sDNI, String sLinea) {
		imagen = "TS134784";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cc.irADetalleDeConsumos();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-01")));
		driver.findElements(By.id("text-input-01")).get(0).click();
		try{
			driver.findElement(By.id("listbox-option-248")).click();
		} catch(Exception e){}
		Boolean a = false;
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals","consultar");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.className("unit-div")));
		List <WebElement> voz = driver.findElements(By.className("unit-div"));
		for(WebElement v : voz){
			if(v.getText().toLowerCase().contains("minutos"))
				a=true;
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2" }, dataProvider = "CuentaProblemaRecarga")
	public void TS134785_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_Otros_consumos_FAN_Front_OOCC_134785(String sDNI, String sLinea) {
		imagen = "TS134785";
		detalles = null;
		detalles = imagen + "- Detalles de Consumos -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
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
	
	@Test(groups = { "GestionesPerfilOficina", "DetalleDeConsumo", "Ciclo2" }, dataProvider = "CuentaVista360")
	public void TS134802_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
		imagen = "TS134802";
		detalles = null;
		detalles = imagen + "-Vista 360 - DNI: " + sDNI + " - Nombre: " + sNombre;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
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
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga")
	public void TS134803_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_Telefonico(String cDNI, String cLinea ){
		imagen = "TS134803";
		detalles = null;
		detalles = imagen + " -Detalle de consumos - DNI: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(cLinea))
				s.click();
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		WebElement plan = driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(2);
		Assert.assertTrue(plan.isDisplayed());
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test(groups = { "GestionesPerfilAgente", "DetalleDeConsumo", "Ciclo2" }, dataProvider = "CuentaProblemaRecarga")
	public void TS134825_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Agentes(String cDNI, String cLinea) {
		imagen = "TS134825";
		detalles = null;
		detalles = imagen + " -DetalleDeConsumos: " + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
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
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga")
	public void TS134826_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_SMS_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134826";
		detalles = null;
		detalles = imagen + "Detalle de consumos -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea))
				s.click();
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		sleep(5000);
		WebElement plan = driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(2);
		Assert.assertTrue(plan.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaProblemaRecarga") 
	public void TS134827_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Datos_FAN_Front_Agentes(String cDNI, String cLinea){
		imagen = "TS134827";
		detalles = null;
		detalles = imagen + "Detalle de consumos -DNI:" + cDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(cLinea))
				s.click();
		}
		driver.findElement(By.id("text-input-02")).click();
		List <WebElement> periodo = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
		periodo.get(1).click();
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")),"equals", "consultar");
		WebElement plan = driver.findElement(By.className("summary-container")).findElements(By.className("unit-div")).get(0);
		Assert.assertTrue(plan.isDisplayed());
	}
	
	@Test (groups = {"GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2"}, dataProvider="CuentaModificacionDeDatos")
	public void TS134828_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_consumo_Voz_FAN_Front_Agentes(String sDNI, String sLinea){
		imagen = "TS134828";
		detalles = null;
		detalles = imagen + "Detalle de Consumos -DNI:" + sDNI+"-Linea: "+sLinea;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irADetalleDeConsumos();
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter")));
		driver.findElement(By.id("text-input-01")).click();
		List <WebElement>serv = driver.findElement(By.cssSelector(".slds-dropdown__list.slds-dropdown--length-5")).findElements(By.tagName("li"));
		for(WebElement s : serv) {
			if(s.getText().contains(sLinea))
				s.click();
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
			if(v.getText().toLowerCase().contains("minutos")){
				a=true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test(groups = { "GestionesPerfilAgente", "DetalleDeConsumo","Ciclo2" }, dataProvider = "CuentaModificacionDeDatos")
	public void TS134829_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_detalle_de_Otros_consumos_FAN_Front_Agentes(String sDNI, String sLinea) {
		imagen = "TS134829";
		detalles = null;
		detalles = imagen + "Detalle de Consumos -DNI:" + sDNI;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(8000);
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