package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import Tests.GestionFlow;
import Tests.TestBase;

public class ABMdeServicios extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private PagePerfilTelefonico ppt;
	private GestionFlow gf;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		loginOOCC(driver);
		ges.irAConsolaFAN();	


	}
		
//	@BeforeClass (alwaysRun = true)
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		loginTelefonico(driver);
		ges.irAConsolaFAN();	

	}
	
//	@BeforeClass (alwaysRun = true)
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		ges = new GestionDeClientes_Fw(driver);
		loginAgente(driver);
		ges.irAConsolaFAN();	

	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
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

//	@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilOficina", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TS135738_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Voice_Mail_con_Clave_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135738";
		detalles = null;
		detalles = imagen+" - BajaServicio - DNI: "+sDNI+" -Linea: "+sLinea;		
//		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Voice Mail con Clave"));
		BasePage cambioFrameByID=new BasePage();
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta(sLinea);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();

		ges.irAGestionEnCardPorNumeroDeLinea("Alta/Baja de Servicios", sLinea);

//		cc.openrightpanel();
//		cc.closerightpanel();
//		cc.openleftpanel();
//		cc.closeleftpanel();
		driver.switchTo().defaultContent();
		cambioDeFrame(driver, By.cssSelector("[class='slds-button cpq-item-has-children'] svg"),0);
//		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
//		sOrder = sOrder.replace("Nro. Orden:", "");
//		sOrder = sOrder.replace(" ", "");
//		detalles +="-Orden:"+sOrder;
//		detalles +="-Servicio:VoiceMailConClave";
//		try {cc.closeleftpanel();}catch (Exception x) {}
//		try {driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();}catch (Exception x) {}
//		cambioDeFrame(driver, By.id("tab-default-1"),0);
//		ppt = new PagePerfilTelefonico(driver);
		ges.altaBajaServicio("baja", "Voice Mail con Clave");
		
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-col slds-align-middle cart-action slds-no-flex'] button")));
		driver.findElement(By.cssSelector("[class='slds-col slds-align-middle cart-action slds-no-flex'] button")).click();;
//		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
//		ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
//		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
//		sleep(15000);
//		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
//		driver.navigate().refresh();
//		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Voice Mail con Clave"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TS135744_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Voice_Mail_con_Clave_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135744";
		detalles = null;
		detalles = imagen+"- AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
		//Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Voice Mail con Clave"));
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
		detalles +="-Servicio:VoiceMailConClave";
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
		ppt.altaBajaServicioContestador("Alta", "Servicios basicos general movil", "Contestador", "Voice Mail con Clave", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Voice Mail con Clave"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134355_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_con_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134355";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
//		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(2000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		
		
		
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Servicio:DDIconRoamingInternacional";
		detalles +="-Orden:"+sOrder;
		try {cc.closeleftpanel();}catch (Exception x) {	}
		cambioDeFrame(driver, By.id("tab-default-1"),0);
		

		ppt = new PagePerfilTelefonico(driver);
		//Dada la estructura de la funcion es lo mismo porque al dar de baja uno, da de alta el otro para poder continuar
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134335_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_Llamadas_WiFi_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134335";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;		
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
		BasePage cambioFrameByID=new BasePage();
		sleep(30000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(18000);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:Llamadas WiFi/Servicio de Red Voz sobre WIFI";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134338_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_con_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134338";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;	
		//Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
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
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIconRoamingInternacional";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134339_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134339";
		detalles = null;
		detalles = imagen+"- BajaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
//		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(2000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134352_CRM_Movil_PRE_Alta_Servicio_sin_costo_Llamadas_WiFi_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134352";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
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
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:Llamadas WiFi/Servicio de Red Voz sobre WIFI";
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
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134356_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134356";
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
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
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
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
		ppt.altaBajaServicio("Alta", "Servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS135739_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135739";
		detalles = null;
		detalles = imagen+"- BajaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
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
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS135745_CRM_Movil_REPRO_Alta_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Presencial_OfCom(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135745";
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
//		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(2000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		
		
		
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
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
		//Al dar de baja DDI con roaming internacional se activa el boton para dar de alta el DDI sin roaming internacional
		ppt.altaBajaServicio("baja", "Servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilTelefonico", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TS135753_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Voice_Mail_con_Clave_y_Transferencia_de_Llamada_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135753";
		detalles = null;
		detalles = imagen+"-AltaServicio - DNI:"+sDNI;		
		//Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Voice Mail con Clave"));
		//Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Transferencia de Llamadas"));
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
		detalles +="-Servicio:VoiceMailConClave&TransferenciadeLlamadas";
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
		ppt.altaBajaServicioContestador("Alta", "Servicios basicos general movil", "Contestador", "Voice Mail con Clave", driver);
		ppt.altaBajaServicioContestador("Alta", "servicios basicos general movil", "nada","Transferencia de Llamadas", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Voice Mail con Clave"));
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Transferencia de Llamadas"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134342_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_Llamadas_WiFi_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134342";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;		
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
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
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:Llamadas WiFi/Servicio de Red Voz sobre WIFI";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3","ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134345_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_con_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134345";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;		
		//Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
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
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIconRoamingInternacional";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="BajaServicios")
	public void TS134346_CRM_Movil_PRE_Baja_de_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134346";
		detalles = null;
		detalles = imagen+"- BajaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
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
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
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
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134359_CRM_Movil_PRE_Alta_Servicio_sin_costo_Llamadas_WiFi_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134359";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
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
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:Llamadas WiFi/Servicio de Red Voz sobre WIFI";
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
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Servicio de Red Voz sobre WIFI"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134362_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_con_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134362";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
		//Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(4000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIconRoamingInternacional";
		try {
			cc.closeleftpanel();
		}
		catch (Exception x) {
			//Always empty
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("tab-default-1")));
		sleep(10000);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "DDI", "DDI con Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "DDI con Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilOficina","E2E","Ciclo3", "ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS134363_CRM_Movil_PRE_Alta_Servicio_sin_costo_DDI_sin_Roaming_Internacional_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS134363";
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional sin Roaming Int."));
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
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		sleep(35000);
		cc.openrightpanel();
		cc.closerightpanel();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")));
		String sOrder = driver.findElement(By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider")).getText();
		sOrder = sOrder.replace("Nro. Orden:", "");
		sOrder = sOrder.replace(" ", "");
		detalles +="-Orden:"+sOrder;
		detalles +="-Servicio:DDIsinRoamingInternacional";
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
		ppt.altaBajaServicio("Alta", "Servicios basicos general movil", "DDI", "DDI sin Roaming Internacional", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "DDI sin Roaming Internacional"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TS135848_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Contestador_Personal_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135848";
		detalles = null;
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;		
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Contestador Personal"));
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
		detalles +="-Servicio:ContestadorPersonal";
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
		ppt.altaBajaServicio("Baja", "Servicios basicos general movil", "Contestador", "Contestador Personal", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar
		//ppt.getwAltaBajaContinuar().click();//Continuar
		sleep(20000);
		WebElement wMessageBox = driver.findElement(By.xpath("//*[@id='TextBlock1']/div/p/p[2]"));
		System.out.println("wMessage.getText: " + wMessageBox.getText().toLowerCase());
		Assert.assertTrue(wMessageBox.getText().toLowerCase().contains("la orden " + sOrder + " se realiz\u00f3 con \u00e9xito!".toLowerCase()));
		sleep(15000);
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Contestador Personal"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TS169651_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Llamadas_WiFi_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS169651";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
		//Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='searchClient-body slds-hint-parent ng-scope'] td ")));
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(4000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
		
		try {cc.closeleftpanel();}catch (Exception x) {}
		try {driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();}catch (Exception x) {}
		cambioDeFrame(driver, By.id("tab-default-1"),0);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Alta", "servicios basicos general movil", "", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar

		
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TS169650_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Llamadas_WiFi_Telefonico(String sDNI, String sLinea) throws AWTException{
		imagen = "TS169650";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
		//Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Discado Directo Internacional con Roaming Int."));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(4000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
		
		try {cc.closeleftpanel();}catch (Exception x) {}
		try {driver.findElement(By.id("ext-comp-1039__scc-st-10")).click();}catch (Exception x) {}
		cambioDeFrame(driver, By.id("tab-default-1"),0);
		ppt = new PagePerfilTelefonico(driver);
		ppt.altaBajaServicio("Baja", "servicios basicos general movil", "", "Llamadas WiFi", driver);
		driver.findElement(By.cssSelector(".slds-button.slds-m-left--large.slds-button--brand.ta-button-brand")).click();//Continuar

		
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"GestionesPerfilAgente", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "BajaServicios")
	public void TS135737_CRM_Movil_REPRO_Baja_de_Servicio_sin_costo_Restriccion_Ident_de_Llamadas_Presencial_Agente(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135737";
		detalles = imagen+"-BajaServicio-DNI:"+sDNI;		
		//Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
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
		sOrders.add("Baja de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
	}
	
	@Test (groups = {"GestionesPerfilOficina", "ABMDeServicios", "E2E", "Ciclo3"}, dataProvider = "AltaServicios")
	public void TS135743_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Restriccion_Ident_de_Llamadas_Presencial_Agente(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135743";
		detalles = null;
		detalles = imagen+" - AltaServicio - DNI: "+sDNI+" - Linea: "+sLinea;		
//		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"),0);
		sb.BuscarCuenta("DNI",sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"),0);
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(2000);
		cc.irAGestionEnCard("Alta/Baja de Servicios");
		try{cc.openrightpanel();}catch(Exception e) {}
		try{ges.cerrarPanelDerecho();}catch(Exception e) {}
		cambioDeFrame(driver, By.cssSelector(".slds-text-body--small.slds-page-header__info.taDevider"),0);
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
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Restricci\u00f3n de la Identificaci\u00f3n de Llamadas"));
	}
	
	@Test (groups = {"GestionesPerfilAgente","E2E","Ciclo3","ABMDeServicios"}, dataProvider="AltaServicios")
	public void TS135754_CRM_Movil_REPRO_Alta_Servicio_sin_costo_Transferencia_de_llamadas_Presencial_Agente(String sDNI, String sLinea) throws AWTException{
		imagen = "TS135754";
		detalles = null;
		detalles = imagen+"-AltaServicio-DNI:"+sDNI;		
		Assert.assertTrue(gf.FlowConsultaServicioInactivo(driver, sLinea, "Transferencia de Llamadas"));
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
		sOrders.add("Alta de Servicio, orden numero: " + sOrder + ", DNI: " + sDNI);
		driver.navigate().refresh();
		Assert.assertTrue(cc.corroborarEstadoCaso(sOrder, "Activada"));
		sleep(20000);
		Assert.assertTrue(gf.FlowConsultaServicioActivo(driver, sLinea, "Transferencia de Llamadas"));
	}
}