package Tests;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.Login;
import Pages.ManejoCaja;
import Pages.setConexion;

public class GestionFlow extends TestBase {
	
	private WebDriver driver;
	
	//Befores & Afters ===========================================
	
	@BeforeClass(alwaysRun=true)
		public void readySteady() throws Exception {
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginflow(driver);
		driver.switchTo().defaultContent();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		}
		
		//@AfterClass(alwaysRun=true)
		public void tearDown() {
			driver.close();
		}
	
	
	@Test (groups = {"Flow","E2E"})
	public boolean FlowConsultaServicioInactivo (WebDriver driver, String sLinea, String sServicio) throws AWTException {
	TestBase ts = new TestBase();
	ts.abrirPestaniaNueva(driver);
	sleep(5000);
	ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	driver.switchTo().window(tabs2.get(1));
	loginflow(driver);
	WebElement consul = driver.findElement(By.cssSelector(".nav-link.dropdown-toggle.linksMenu"));
		if(consul.getText().toLowerCase().equals("consultas")){
			consul.click();
		}
	driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado.itemClick")).get(0).click();
	sleep(5000);
	driver.switchTo().frame(cambioFrame(driver, By.id("frameizquierdo")));
	driver.findElement(By.name("btnConsultar")).click();
	sleep(5000);
	WebElement txt = driver.findElement(By.id("txtCampo"));
	txt.click();
	txt.sendKeys(sLinea);  
	driver.findElement(By.name("btnConsultar")).click();
	sleep(15000);
	driver.switchTo().frame(cambioFrame(driver, By.id("framederecho"))); 
	List<WebElement> box = driver.findElements(By.cssSelector(".box.efecto3"));
		for(WebElement b : box){
			if(b.getText().toLowerCase().contains("servicios activos")){
				b.click();
				break;
			}
		}
	sleep(12000);
	driver.switchTo().frame(cambioFrame(driver, By.id("laAyuda")));
	ArrayList<String> txt1 = new ArrayList<String>();
	String txt2 = "INACTIVO - "+sServicio;
	driver.findElement(By.id("aVerInactivasServiciosActivos")).click();
	sleep(3000);
	List<WebElement> serv = driver.findElement(By.id("laAyuda")).findElements(By.tagName("font"));
			for(WebElement e: serv){
				if(e.getAttribute("color").equals("red")){
					txt1.add(e.getText());
				}
			}
			boolean bAssert = txt1.contains(txt2);
			if (bAssert) {
				System.out.println(sServicio+"Activo Correctamente");
			}
			driver.switchTo().defaultContent();
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.id("aNavUsuario"))).click(driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado")).get(2)).perform();
			sleep(5000);
			driver.close();
			sleep(2000);
		    driver.switchTo().window(tabs2.get(0));
		    sleep(1500);
			return bAssert;
	}	
	
	
	@Test (groups = {"Flow","E2E"})
	public boolean FlowConsultaServicioActivo (WebDriver driver, String sLinea, String sServicio) throws AWTException{
	TestBase ts = new TestBase();
	ts.abrirPestaniaNueva(driver);
	sleep(5000);
	ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	driver.switchTo().window(tabs2.get(1));
	try {
		loginflow(driver);
	}
	catch(Exception eE) {
		//Always empty
	}
	sleep(5000);
	WebElement consul = driver.findElement(By.cssSelector(".nav-link.dropdown-toggle.linksMenu"));
		if(consul.getText().toLowerCase().equals("consultas")){
		consul.click();
		}
	driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado.itemClick")).get(0).click();
	sleep(5000);
	driver.switchTo().frame(cambioFrame(driver, By.id("frameizquierdo")));
	driver.findElement(By.name("btnConsultar")).click();
	sleep(5000);
	WebElement txt = driver.findElement(By.id("txtCampo"));
	txt.click();
	txt.sendKeys(sLinea);  
	driver.findElement(By.name("btnConsultar")).click();
	sleep(7500);
	driver.switchTo().frame(cambioFrame(driver, By.id("framederecho"))); 
	List<WebElement> box = driver.findElements(By.cssSelector(".box.efecto3"));
		for(WebElement b : box){
			if(b.getText().toLowerCase().contains("servicios activos")){
				b.click();
				break;
			}
		}
	sleep(8000);
	driver.switchTo().frame(cambioFrame(driver, By.id("laAyuda")));
	ArrayList<String> txt1 = new ArrayList<String>();
	String txt2 = (sServicio);
	List<WebElement> serv = driver.findElement(By.id("laAyuda")).findElements(By.tagName("font"));
		for(WebElement e: serv){
			if(e.getAttribute("color").equals("#5065BC")){
				txt1.add(e.getText());
			}
		}
		boolean bAssert = txt1.contains(txt2);
		if (bAssert) {
			System.out.println(sServicio+"Activo Correctamente");
		}
		driver.switchTo().defaultContent();
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.id("aNavUsuario"))).click(driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado")).get(2)).perform();
		sleep(5000);
		driver.close();
		sleep(2000);
	    driver.switchTo().window(tabs2.get(0));
	    sleep(1500);
		return bAssert;
}
	
	
	
	@Test (groups = {"Flow","E2E"})
	public boolean FlowServiciosActivos (WebDriver driver, String sLinea) throws AWTException{
	TestBase ts = new TestBase();
	ts.abrirPestaniaNueva(driver);
	sleep(5000);
	ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	loginflow(driver);
	sleep(5000);
	WebElement consul = driver.findElement(By.cssSelector(".nav-link.dropdown-toggle.linksMenu"));
		if(consul.getText().toLowerCase().equals("consultas")){
		consul.click();
		}
	driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado.itemClick")).get(0).click();
	sleep(5000);
	driver.switchTo().frame(cambioFrame(driver, By.id("frameizquierdo")));
	driver.findElement(By.name("btnConsultar")).click();
	sleep(5000);
	WebElement txt = driver.findElement(By.id("txtCampo"));
	txt.click();
	txt.sendKeys(sLinea);  
	driver.findElement(By.name("btnConsultar")).click();
	sleep(15000);
	driver.switchTo().frame(cambioFrame(driver, By.id("framederecho"))); 
	List<WebElement> box = driver.findElements(By.cssSelector(".box.efecto3"));
		for(WebElement b : box){
			if(b.getText().toLowerCase().contains("servicios activos")){
				b.click();
				break;
			}
		}
	sleep(8000);
	driver.switchTo().frame(cambioFrame(driver, By.id("laAyuda")));
	ArrayList<String> txt1 = new ArrayList<String>();
	ArrayList<String> txt2 = new ArrayList<String>();
	txt2.add("Llamada en Espera");
	txt2.add("Conferencia Multipartita");
	txt2.add("Identificaci\u00f3n de Llamadas");
	txt2.add("Transferencias de Llamadas");
	txt2.add("Notificaci\u00f3n de mensajes de voz y Recepci\u00f3n de SMS");
	txt2.add("Mensaje Personal - Env\u00edo y Recepci\u00f3n de SMS");
	txt2.add("Servicio de voz y datos - GPRS Activo");
	txt2.add("Video Call");
	txt2.add("Contestador Personal");
	txt2.add("Discado Directo Internacional con Roaming Int.(solo Con transferencia internacional)");
	txt2.add("Servicio MMS");
	//txt2.add("Servicio 4G Nunca Registrado");
	List<WebElement> serv = driver.findElement(By.id("laAyuda")).findElements(By.tagName("font"));
		for(WebElement e: serv){
			if(e.getAttribute("color").equals("#5065BC")){
				txt1.add(e.getText());
			}
		}
	System.out.println("Servicios Activos"+txt1);	
	boolean bAssert = txt1.containsAll(txt2);
	driver.switchTo().defaultContent();
	Actions action = new Actions(driver);
	action.moveToElement(driver.findElement(By.id("aNavUsuario"))).click(driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado")).get(2)).perform();
	sleep(5000);
	driver.close();
	sleep(2000);
	driver.switchTo().window(tabs2.get(0));
	sleep(2000);
	return bAssert;
	}
		
	
	@Test (groups = {"Flow","E2E"})
	public String FlowIMSI (WebDriver driver, String sLinea) throws AWTException{
	TestBase ts = new TestBase();
	ts.abrirPestaniaNueva(driver);
	sleep(5000);
	ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	loginflow(driver);
	sleep(5000);
	WebElement consul = driver.findElement(By.cssSelector(".nav-link.dropdown-toggle.linksMenu"));
		if(consul.getText().toLowerCase().equals("consultas")){
		consul.click();
		}
	driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado.itemClick")).get(0).click();
	sleep(5000);
	driver.switchTo().frame(cambioFrame(driver, By.id("frameizquierdo")));
	driver.findElement(By.name("btnConsultar")).click();
	sleep(5000);
	WebElement txt = driver.findElement(By.id("txtCampo"));
	txt.click();
	txt.sendKeys(sLinea);  
	driver.findElement(By.name("btnConsultar")).click();
	sleep(7500);
	driver.switchTo().frame(cambioFrame(driver, By.id("framederecho"))); 
	List<WebElement> box = driver.findElements(By.cssSelector(".box.efecto3"));
		for(WebElement b : box){
			if(b.getText().toLowerCase().contains("imsi:")){
				b.click();
				break;
			}
		}
	sleep(8000);
	String nimsi;
	driver.switchTo().frame(cambioFrame(driver, By.id("laAyuda")));
	System.out.println(driver.findElement(By.id("laAyuda")).getText());
	nimsi = driver.findElement(By.id("laAyuda")).getText();
	nimsi = nimsi.split(" ")[2];
	System.out.println(nimsi);
	driver.switchTo().defaultContent();
	Actions action = new Actions(driver);
	action.moveToElement(driver.findElement(By.id("aNavUsuario"))).click(driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado")).get(2)).perform();
	sleep(5000);
	driver.close();
	driver.switchTo().window(tabs2.get(0));
	return(nimsi);
	}
	
	
	public String miraComoTeTraigoElEstadoPapa(WebDriver driver, String numeroLinea) throws AWTException {
		String EstadoTemporal = "No se pudo obtener un estado";
		TestBase ts = new TestBase();
		ts.abrirPestaniaNueva(driver);
		sleep(5000);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		loginflow(driver);
		WebElement consul = driver.findElement(By.cssSelector(".nav-link.dropdown-toggle.linksMenu"));
			if(consul.getText().toLowerCase().equals("consultas")){
				consul.click();
			}
		sleep(500);
		driver.findElements(By.cssSelector(".dropdown-item.navbarItemPersonalizado.itemClick")).get(0).click();
		sleep(5000);
		driver.findElement(By.id("txtCampo")).sendKeys(numeroLinea);
		driver.findElement(By.name("btnConsultar")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("framederecho"))); 
		EstadoTemporal = driver.findElements(By.cssSelector(".box.efecto3")).get(0).getText();
		
		switch(EstadoTemporal) {
		case "Estado":
			EstadoTemporal = "Preactiva";
		case "L\u00ednea Tarjeta FAN / Abono Fijo Activa (Llamadas Entrantes y Salientes)":
			EstadoTemporal = "Activa";
		case "L\u00ednea Tarjeta FAN / Abono Fijo Bloqueada x Siniestro / Mora2":
			EstadoTemporal = "Suspendida";
		}
		
		if (EstadoTemporal.equals("Preactiva") || EstadoTemporal.equals("Activa") || EstadoTemporal.equals("Suspendida")) {
			
		} else {
			EstadoTemporal = "No se pudo obtener un estado";
		}
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		return EstadoTemporal;
	}
	
}