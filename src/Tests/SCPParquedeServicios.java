package Tests;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.Login;
import Pages.SCP;
import Pages.setConexion;

import static org.testng.Assert.assertTrue;

import java.awt.Button;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
public class SCPParquedeServicios extends TestBase{
	private WebDriver driver;
	String categoria = "Servicio automatizado";
	String servicio = "Prueba automatizada";
	String color = "Red";
	private static String downloadPath = "C:\\Users\\Florangel\\Downloads";
	
	@BeforeClass(groups= "SCP")
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginSCPConPermisos(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
		
	@BeforeMethod(groups= "SCP")
	public void setup(){
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("tsidLabel")).getLocation().y+")");
		String a = driver.findElement(By.id("tsidLabel")).getText();
		driver.findElement(By.id("tsidLabel")).click();
		List <WebElement> mdls = driver.findElements(By.cssSelector(".menuButtonMenuLink"));
		if(a.equals("Ventas"))
		{			try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

			for(WebElement e: mdls){
				if(e.getText().toLowerCase().equals("scp")){
					e.click();
					}break;}
		}else{
			for(WebElement e: mdls){
				if(e.getText().toLowerCase().equals("ventas")){
					e.click();
					}break;}
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("tsidLabel")).click();
			for(WebElement e: mdls){
				if(e.getText().toLowerCase().equals("scp")){
					e.click();
					}break;}
		}
	}
	
	//@AfterMethod(groups= "SCP")
	public void after(){
		driver.switchTo().defaultContent();
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("home_Tab")).getLocation().y+")");
		driver.findElement(By.id("home_Tab")).click();
	}
	
   //@AfterClass(groups= "SCP")
	public void tearDown() {
		driver.quit();
		sleep(4000);
	}
	
	@Test(groups= "SCP", priority=6)
	public void TS112781_Parque_de_Servicios_Agregar_Nuevo_Servicio(){
	
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		page.moveToElementOnAccAndClick("segundoTitulo", 2);
		page.nuevoservicio(categoria, servicio, color);
		Assert.assertTrue(page.validarservicionuevo(categoria, servicio, color));
		
	}
	
	@Test(groups= "SCP", priority=6)
	public void TS112782_Parque_de_Servicios_Borrar(){
	
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("segundoTitulo", 2);
		page.nuevoservicioEspecifico(categoria, servicio, color);
		Assert.assertTrue(page.validarservicioborrado(categoria, servicio, color));
	}
	
		
	@Test(groups= "SCP", priority=6)
	public void TS112785_Parque_de_Servicios_Exportar_a_Excel(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("segundoTitulo",2);
		 String usuario = driver.findElements(By.cssSelector(".nav.navbar-nav.navbar-right")).get(1).findElement(By.tagName("a")).getText();  
	      List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));  
	      for (WebElement UnS : servicioList) {  
	        if (UnS.getText().toLowerCase().contains("export to excel")||UnS.getText().toLowerCase().contains("exportar a excel")) {  
	          UnS.click();  
	          break;  
	        }  
	      }  
	      try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	      usuario=usuario.replace(' ', '_');  
	      usuario=usuario.concat("-Plan_de_Acci\u00f3n.xls");  
	      assertTrue(page.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");  
	}
		@Test(groups= "SCP", priority=6)
		public void TS112786_Parque_de_Servicios_Guardar(){
			SCP page = new SCP(driver);
			page.clickOnTabByName("cuentas");
			page.clickOnFirstAccRe();
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			page.moveToElementOnAccAndClick("segundoTitulo",2);
			page.servicioguardar();
		}
		
	@Test(groups= "SCP", priority=6)
	public void TS112783_Parque_de_Servicios_Chatter_contextualizado_Escribir_comentario(){
			SCP page = new SCP(driver);
			page.clickOnTabByName("cuentas");
			page.clickOnFirstAccRe();
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			page.moveToElementOnAccAndClick("segundoTitulo",2);
			page.comentarycompartir("Esto es un comentario");
			page.validarcomentario("Esto es un comentario");
	}
	@Test(groups= "SCP", priority=6)
	public void TS112789_Plan_de_Accion_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("cuartoTitulo", 2);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	
	@Test(groups= "SCP", priority=3)
	public void TS112727_Negocio_del_cliente_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("primerTitulo", 1);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	
	@Test(groups= "SCP", priority=3)
	public void TS112587_Contexto_sectorial_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("primerTitulo", 1);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	
	@Test(groups= "SCP", priority=3)
	public void TS112718_Mosaico_de_Relacionamiento_por_Oportunidad_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("tercerTitulo", 3);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	
	@Test(groups= "SCP", priority=3)
	public void TS112683_Matriz_de_Criterios_de_Decision_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("tercerTitulo", 2);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	@Test(groups= "SCP", priority=3)
	public void TS112614_Cronograma_de_Cuenta_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("tercerTitulo", 1);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	
	@Test(groups= "SCP", priority=3)
	public void TS112698_Mosaico_de_Relacionamiento_General_Chatter_contextualizado_Escribir_comentario(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickOnFirstAccRe();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.moveToElementOnAccAndClick("tercerTitulo", 4);
		page.comentarycompartir("Esto es un comentario");
		page.validarcomentario("Esto es un comentario");
	}
	
	@Test(groups = "SCP", priority=3) 
    public void TS112630_Estrategia_de_Crecimiento_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2);    
      try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
   pcp.clickOnTabByName("cuentas");
   try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
   try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
   pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
   try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
   pcp.moveToElementOnAccAndClick("tercerTitulo", 5);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	@Test(groups = "SCP", priority=3) 
    public void TS112615_Cronograma_de_Cuenta_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2);    
      try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("cuartoTitulo", 1);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	@Test(groups = "SCP", priority=3) 
    public void TS112675_Hitos_Relevantes_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("segundoTitulo", 3);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	@Test(groups = "SCP", priority=3) 
    public void TS112684_Matriz_de_Criterios_de_Decision_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("tercerTitulo", 2);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	@Test(groups = "SCP", priority=3) 
    public void TS112699_Mosaico_de_Relacionamiento_General_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("segundoTitulo", 4);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	@Test(groups = "SCP", priority=3) 
    public void TS112719_Mosaico_de_Relacionamiento_por_Oportunidad_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("tercerTitulo", 3);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	
	@Test(groups = "SCP", priority=3) 
    public void TS112728_Negocio_del_cliente_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("primerTitulo", 2);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }
	
	@Test(groups = "SCP", priority=3) 
    public void TS112784_Parque_de_Servicios_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
      SCP pcp = new SCP(driver); 
      java.util.Date fecha = new Date();
      System.out.println (fecha);
      pcp.Desloguear_Loguear("isabel");
	  pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickOnTabByName("cuentas");
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
	  try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  pcp.moveToElementOnAccAndClick("segundoTitulo", 2);
      pcp.validarcomentarioajeno(fecha.toString());
      pcp.Desloguear_Loguear("permisos");
 }	
	@Test(groups = "SCP", priority=6) 
	public void TS112645_Estructura_de_las_oportunidades_Bloques_Estado_de_Proyecto_DELTA_Oportunidad(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.ValidarEstadosDELTA("oportunidad");
	}
	@Test(groups = "SCP", priority=6) 
	public void TS112646_Estructura_de_las_oportunidades_Bloques_Estado_de_Proyecto_DELTA_Proyectos(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.ValidarEstadosDELTA("proyectos");
	}
	
	@Test(groups = "SCP", priority=6) 
	public void TS112640_Estructura_de_las_oportunidades_Bloques_Competidores(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.validarcompetidores();
	}
	@Test(groups = "SCP", priority=6) 
public void TS112648_Estructura_de_las_oportunidades_Bloques_Informacion_Adicional_de_Ventas(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.validarinfoventas();
	}
	
	@Test(groups = "SCP", priority=6) 
	public void TS112652_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad_Campos_no_editables(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.IngresarAlProductos("Accseo IRIS");
		page.ModificarProducto("modificar","cantidad", "5,00", "guardar");
		page.VerificarCampoModificado("cantidad", "5,00");
	}
	
	@Test(groups = "SCP", priority=6) 
	public void TS112658_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad_Cargo_Unica_Vez_Modificar(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.IngresarAlProductos("Accseo IRIS");
		page.ModificarProducto("modificar","cargo unico", "600,00", "guardar");
		page.VerificarCampoModificado("cargo unico", "600,00");
	}
	
	@Test(groups = "SCP", priority=3) 
	public void TS1112659_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad_Cargos_Totales_por_Mes(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.IngresarAlProductos("Accseo IRIS");
		page.ModificarProducto("modificar","cantidad", "8", "guardar");
		String a = page.CargosTotalesPorMes();
		page.ModificarProducto("modificar","cantidad", "5", "guardar");
		String b = page.CargosTotalesPorMes();
		Assert.assertFalse(a.equals(b));
	}
	
	@Test(groups = "SCP", priority=6) 
	public void TS112661_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad_Plazo_Modificar(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.IngresarAlProductos("Accseo IRIS");
		page.ModificarProducto("modificar","plazo", "50", "guardar");
		page.VerificarCampoModificado("plazo", "50");
	}
	
	@Test(groups = "SCP", priority=3) 
	public void TS1112662_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad_Precio_total_contrato(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.IngresarAlProductos("Accseo IRIS");
		page.ModificarProducto("modificar","cargo unico", "900,00", "guardar");
		page.VerificarCampoModificado("cargo unico", "900,00");
		page.ValidarMontoContrato();
		
	}
	
	@Test(groups = "SCP", priority=6) 
	public void TS112663_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad_Total_mes_por_plazo(){
		SCP page = new SCP(driver);
		page.clickOnTabByName("cuentas");
		page.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		page.IngresarAlProductos("Accseo IRIS");
		String antes = page.SacarTotalMesXPlazo();
		page.ModificarProducto("modificar","cargo unico", "500", "guardar");
		page.VerificarCampoModificado("cargo unico", "500,00");
		page.ValidarTotalMesXPlazo(antes);
		
	}
}
