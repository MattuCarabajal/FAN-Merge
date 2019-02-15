
package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.SCP;
import Pages.setConexion;

public class SCPPP extends TestBase {
	
private WebDriver driver;
	
	@BeforeClass(groups = "SCP")
	public void init() throws Exception	{
	this.driver = setConexion.setupEze();
	try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	loginSCPconTodo(driver);
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeMethod(groups = "SCP")
	public void setup() {
		
		SCP pScp = new SCP(driver);
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		pScp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	
	//@AfterClass(groups = "SCP")
	public void teardown() {
		driver.quit();
		sleep(10000);
	}
	
/*	@Test(groups = "SCP")
	public void Test_Crear_Cuenta_Borrar_Cuenta(){
		//Creacion
		driver.findElement(By.id("createNewLabel")).click();
		sleep(2000);
		driver.findElement(By.id("createNewMenu")).findElements(By.tagName("a")).get(0).click();
		sleep(5000);
		Select segment= new Select (driver.findElement(By.id("00N4100000c3bHf")));
		segment.selectByVisibleText("Negocio Corporativo");	
		driver.findElement(By.id("acc2")).sendKeys("CuentaTest");
		Select tipo = new Select (driver.findElement(By.id("acc6")));
		tipo.selectByVisibleText("Cuenta Cabecera");
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(8999999)+1000000;
		driver.findElement(By.id("00N4100000c3bHN")).sendKeys(Integer.toString(intAleatorio));
		WebElement guardar = driver.findElement(By.id("bottomButtonRow")).findElements(By.tagName("input")).get(0);
		guardar.click();
		sleep(10000);
		//Eliminacion
		List<WebElement> cuentass = driver.findElements(By.cssSelector(".recentItemModule.sidebarModule"));
			for(WebElement c : cuentass){
			c.getText().equals("CuentaTest");
			c.click();
			}
		sleep(5000);
		WebElement delet = driver.findElement(By.id("topButtonRow")).findElements(By.tagName("input")).get(3);
		System.out.println(delet.getText());
		delet.click();
		try {driver.switchTo().alert().accept();}catch(org.openqa.selenium.NoAlertPresentException e) {}
	}
	
	@Test (groups = "SCP")
	public void Test_Oportunidad(){
		SCP prueba = new SCP(driver); 
		prueba.Desloguear_Loguear("Maximiliano");
		sleep(10000);
		prueba.Crear_Oportunidad("testop", "test", "14/03/2018", "15/03/2018","Contacto", "17/03/2018", "CONTACTO INICIAL");
	} */
	
	@Test(groups = "SCP", priority=2)
	public void TS110244_Estructura_del_cliente_GGCC_Campos() {
	
		 ArrayList<String> camp1 = new ArrayList<String>();
		 ArrayList<String> txt2 = new ArrayList<String>();
		 txt2.add("CUIT");
		 txt2.add("Numero de Holding");
		 txt2.add("Segmento");
		 txt2.add("Region");
		 txt2.add("Territorio");
		 txt2.add("Domicilio de recepci\u00f3n de notificaciones");
		 List<WebElement> campos = driver.findElements(By.className("labelCol"));
		 System.out.println(campos.size());
		 for(WebElement c: campos){
			 camp1.add(c.getText());
			 }
			 Assert.assertTrue(camp1.containsAll(txt2));
	}
	
	@Test(groups = "SCP", priority=2) 
	public void TS110245_Estructura_del_cliente_GGCC_Campos_Region	() {
		SCP pScp = new SCP(driver);
		//pScp.Desloguear_Loguear("fabiana");
		//WebElement reg = driver.findElement(By.id("00N4100000c3bHn_ilecell"));
		WebElement reg = driver.findElement(By.className("detailList")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(5);
		Actions action = new Actions(driver);   
		action.moveToElement(reg).doubleClick().perform();
		waitFor(driver, By.id("00N4100000c3bHn"));
		Select dropdown = new Select (driver.findElements(By.cssSelector(".inlineEditDiv.inlineEditGroup")).get(0).findElement(By.tagName("span")).findElement(By.tagName("select")));
		dropdown.selectByVisibleText("Gobierno");	
		WebElement cerra = driver.findElement(By.id("InlineEditDialog_buttons"));
		cerra.click();
		
	}
	@Test(groups = "SCP", priority=2)
	public void TS110246_Estructura_del_cliente_GGCC_Campos_Territorio() {
	WebElement reg = driver.findElement(By.className("detailList")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(5);
	Actions action = new Actions(driver);   
	action.moveToElement(reg).doubleClick().perform();
	waitFor(driver, By.id("00N4100000c3bHn"));
	Select regio = new Select (driver.findElements(By.cssSelector(".inlineEditDiv.inlineEditGroup")).get(0).findElement(By.tagName("span")).findElement(By.tagName("select")));
	regio.selectByVisibleText("Privado");	
	Select terr = new Select (driver.findElements(By.cssSelector(".inlineEditDiv.inlineEditGroup")).get(1).findElement(By.tagName("span")).findElement(By.tagName("select")));
	terr.selectByVisibleText("--Ninguno--");
	Assert.assertEquals(terr.getFirstSelectedOption().getText(),"--Ninguno--");
	terr.selectByVisibleText("Industria");
	Assert.assertEquals(terr.getFirstSelectedOption().getText(),"Industria");
	terr.selectByVisibleText("Finanzas");
	Assert.assertEquals(terr.getFirstSelectedOption().getText(),"Finanzas");
	terr.selectByVisibleText("Servicios");
	Assert.assertEquals(terr.getFirstSelectedOption().getText(),"Servicios");
	terr.selectByVisibleText("Privado litoral");
	Assert.assertEquals(terr.getFirstSelectedOption().getText(),"Privado litoral");
	terr.selectByVisibleText("Privado mediterraneo");
	Assert.assertEquals(terr.getFirstSelectedOption().getText(),"Privado mediterraneo");
	regio.selectByVisibleText("Gobierno");
	sleep(3000);
	Select terri = new Select (driver.findElements(By.cssSelector(".inlineEditDiv.inlineEditGroup")).get(1).findElement(By.tagName("span")).findElement(By.tagName("select")));
	terri.selectByVisibleText("--Ninguno--");
	Assert.assertEquals(terri.getFirstSelectedOption().getText(),"--Ninguno--");
	terri.selectByVisibleText("Gobierno amba 1");
	Assert.assertEquals(terri.getFirstSelectedOption().getText(),"Gobierno amba 1");
	terri.selectByVisibleText("Gobierno amba 2");
	Assert.assertEquals(terri.getFirstSelectedOption().getText(),"Gobierno amba 2");
	terri.selectByVisibleText("Gobierno litoral");
	Assert.assertEquals(terri.getFirstSelectedOption().getText(),"Gobierno litoral");
	terri.selectByVisibleText("Gobierno mediterraneo");
	Assert.assertEquals(terri.getFirstSelectedOption().getText(),"Gobierno mediterraneo");
	WebElement cerra = driver.findElement(By.id("InlineEditDialog_buttons"));
	cerra.click();
	}
	
	@Test(groups = "SCP", priority=2) 
	public void TS110247_Estructura_del_cliente_GGCC_Campos_Territorio() {
		SCP pScp = new SCP(driver);
		driver.findElement(By.id("tabContainer")).findElement(By.id("tabBar")).findElements(By.tagName("li")).get(1).click();
		pScp.clickEnCuentaPorNombre("PruebaWH");
		 ArrayList<String> camp1 = new ArrayList<String>();
		 ArrayList<String> txt2 = new ArrayList<String>();
		 txt2.add("CUIT");
		 txt2.add("Raz\u00f3n Social");  // falta
		 txt2.add("Numero de clientes"); //falta 
		 txt2.add("Numero de Holding");
		 txt2.add("Categor\u00eda WH");
		 txt2.add("Domicilio de recepci\u00f3n de notificaciones");
		 List<WebElement> campos = driver.findElements(By.className("labelCol"));
		 System.out.println(campos.size());
		 for(WebElement c: campos){
			 camp1.add(c.getText());
			 }
			 Assert.assertTrue(camp1.containsAll(txt2));

	}
	
	@Test(groups = "SCP", priority=3)
	public void TS_112767_Organigrama_y_mapa_de_influencia_Modificar_mapa_de_influencias() {
	SCP prueba = new SCP(driver); 
	prueba.moveToElementOnAccAndClick("primerTitulo", 3);
	WebElement pag = driver.findElement(By.cssSelector(".btn.btn-default.btn-sm.myBtn.influenceBtn"));
	pag.click();
	WebElement msg = driver.findElement(By.className("messageText"));
	waitFor(driver, By.className("messageText"));
	System.out.println(msg.getText());
	Assert.assertTrue((msg.getText().equals("Para agregar o para quitar una Influencia: 1) Arrastrar la caja del contacto influyente 2) Soltarla sobre la caja del contacto influenciado 3) Clickear el bot\u00f3n de \"Guardar cambios\" antes de abandonar la p\u00e1gina!")));
	}
	
	
	@Test(groups = "SCP", priority=3) 
	public void TS112792_Plan_de_accion_Eliminar_tareas() { 
		SCP prueba = new SCP(driver); 
		prueba.Desloguear_Loguear("Maximiliano");
		sleep(18000);
		driver.findElement(By.id("mru0010j00000EYKW0")).click();
		sleep(10000);
	    prueba.moveToElementOnAccAndClick("cuartoTitulo", 2);
	    WebElement tabla= driver.findElement(By.id("mainTable_wrapper")).findElement(By.className("odd")).findElements(By.tagName("td")).get(1);
	    String lala = tabla.getText();
	    System.out.println(lala);
	    WebElement box = driver.findElement(By.id("mainTable")).findElement(By.className("odd")).findElement(By.tagName("td")).findElement(By.tagName("input"));	    
	    box.click();
	    boolean bot= false;
	    sleep(3000);
	    List<WebElement> btn = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
	     for(WebElement b :btn) {
	    	if (b.getText().contains("Eliminar Tareas")) {  
		   bot = true;  
	    		b.click();
		        break;}}
	    sleep(15000);
	    WebElement busc = driver.findElement(By.id("mainTable_filter")).findElement(By.tagName("input"));
	    busc.sendKeys(lala);
	    WebElement resul = driver.findElement(By.className("odd"));
	    Assert.assertEquals(resul.getText(), "No matching records found");
	    
	}
	
	@Test(groups = "SCP", priority=3) 
	public void TS112794_Plan_de_accion_Plan_de_accion_Fusionar_tareas() {
		SCP prueba = new SCP(driver); 
		sleep(15000);
		driver.findElement(By.id("mru0010j00000EYKW0")).click();
		sleep(3000);
	    prueba.moveToElementOnAccAndClick("cuartoTitulo", 2);
	    java.util.Date fechaCompleta = new Date();
	    boolean bien = false;
	    String fech =  fechaCompleta.getDate()+"/"+(fechaCompleta.getMonth()+1);
	    int hora = fechaCompleta.getHours();
	    int min	=	fechaCompleta.getMinutes();
	    	if((fechaCompleta.getDate()<10) && (fechaCompleta.getMonth()<10)) {
	    		fech= "0"+fechaCompleta.getDate()+"/0"+(fechaCompleta.getMonth()+1);
	    	}else {
	    		if(fechaCompleta.getDate()<10) {
	    			fech= "0"+fechaCompleta.getDate()+"/"+(fechaCompleta.getMonth()+1);
	    		}else {
	    			if (fechaCompleta.getMonth()<10)
	    			fech= fechaCompleta.getDate()+"/0"+(fechaCompleta.getMonth()+1);
	    		}
	    	}
	    	
	    System.out.println(fech);
	    List<WebElement> box = driver.findElement(By.id("mainTable")).findElements(By.className("odd"));
	    box.get(0).findElement(By.tagName("td")).findElement(By.tagName("input")).click();
	    box.get(1).findElement(By.tagName("td")).findElement(By.tagName("input")).click();
	    sleep(1500);
	    String texto1=  box.get(0).findElements(By.tagName("td")).get(3).findElement(By.tagName("span")).getText();
	    String texto2=  box.get(1).findElements(By.tagName("td")).get(3).findElement(By.tagName("span")).getText();
	 	 texto1 = texto1 +" "+texto2;
	 	Boolean bot = false;
	 	List<WebElement> btn = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
	     for(WebElement b :btn) {
	    	if (b.getText().contains("Fusionar tareas")) {  
		   bot = true;  
	    		b.click();
		        break;}}
	     sleep(10000);
	     WebElement busc = driver.findElement(By.id("mainTable_filter")).findElement(By.tagName("input"));
		 busc.sendKeys(fech); 
		List<WebElement> tabla= driver.findElement(By.id("mainTable_wrapper")).findElements(By.className("odd"));
		tabla.addAll(driver.findElement(By.id("mainTable_wrapper")).findElements(By.className("even")));//.findElements(By.tagName("td"));
		System.out.println(hora);
		System.out.println(min);
		for(WebElement UnaL : tabla) {
			if (UnaL.findElements(By.tagName("td")).get(3).getText().equals(texto1)||UnaL.findElements(By.tagName("td")).get(3).getText().equals(texto1+" null")){
				System.out.println("entre 1");
				if(Integer.parseInt(UnaL.findElements(By.tagName("td")).get(1).getText().split(" ")[1].split(":")[0])>hora) {
					bien = true;
					System.out.println("entre 2");
				}else {
					if (Integer.parseInt(UnaL.findElements(By.tagName("td")).get(1).getText().split(" ")[1].split(":")[0])==hora && Integer.parseInt(UnaL.findElements(By.tagName("td")).get(1).getText().split(" ")[1].split(":")[1])>=min) {
						bien = true;
					System.out.println("entre 3");}
				}
			}
		}
		 System.out.println(texto1);
		 Assert.assertTrue(bien);   
	}	
	
	@Test(groups = "SCP", priority=3) 
	public void TS112795_Plan_de_accion_Guardar() {
		SCP prueba = new SCP(driver); 
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 2);
	    WebElement tabla= driver.findElement(By.id("mainTable_wrapper")).findElement(By.className("odd")).findElements(By.tagName("td")).get(3);
	    Actions action = new Actions(driver);   
		action.moveToElement(tabla).doubleClick().perform();
		WebElement vent = driver.findElement(By.id("InlineEditDialog")).findElement(By.tagName("textarea"));
		waitFor(driver, By.id("InlineEditDialog"));
		sleep(5000);
		vent.sendKeys("TestGuardar");
		Boolean bot = false;
		WebElement acep = driver.findElement(By.id("InlineEditDialog_buttons")).findElements(By.tagName("input")).get(0);
		System.out.println(acep.getText());
		acep.click();
		String lala = tabla.getText();
	    List<WebElement> btn = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
	   for(WebElement b :btn) {
	    	if (b.getText().toLowerCase().contains("guardar")) {  
		   bot = true;  
		        b.click(); 
		        break;}}
	   sleep(15000);
	   WebElement busc = driver.findElement(By.id("mainTable_filter")).findElement(By.tagName("input"));
	    busc.sendKeys(lala);
	    Assert.assertTrue(lala.equals("TestGuardar"));
	   	busc.clear();
	    busc.submit();
	    sleep(5000);	
	    driver.navigate().refresh();
	    sleep(5000);
	    WebElement tablaa= driver.findElement(By.id("mainTable_wrapper")).findElement(By.className("odd")).findElements(By.tagName("td")).get(3);
	    action.moveToElement(tablaa).doubleClick().perform();
	    WebElement asjd = driver.findElement(By.id("mainTable_wrapper")).findElements(By.tagName("td")).get(3); 
	    sleep(5000);
	    WebElement buscc = driver.findElement(By.id("mainTable_filter")).findElement(By.tagName("input"));
		sleep(3000);
	    buscc.sendKeys(" Servicio: Prueba automatizada");
		WebElement acep2 = driver.findElement(By.id("InlineEditDialog_buttons")).findElements(By.tagName("input")).get(0);
		acep2.equals("Aceptar");
		acep2.click();
		List<WebElement> btnn = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for(WebElement b :btnn) {
	    	if (b.getText().toLowerCase().contains("guardar")) {  
		   bot = true;  
		        b.click(); 
		        break;}}
	}
	
	@Test(groups = "SCP", priority=3) 
	public void TS112796_Plan_de_Accion_Ingreso_Desde_el_contacto() {
		SCP prueba = new SCP(driver); 
	    prueba.moveToElementOnAccAndClick("cuartoTitulo", 2);
		 ArrayList<String> colu = new ArrayList<String>();
		 ArrayList<String> txt2 = new ArrayList<String>();
		 txt2.add(": activate to sort column descending");
		 txt2.add("Fecha de Creaci\u00f3n");
		 txt2.add("Tema");
		 txt2.add("Descripci\u00f3n");
		 txt2.add("Fecha Limite");
		 txt2.add("Completado");
		 txt2.add("Estado de la tarea");
		 txt2.add("Prioridad");
		 txt2.add("Asignado a");
		 List<WebElement> col = driver.findElement(By.id("mainTable")).findElement(By.tagName("tr")).findElements(By.tagName("th")); 
		 System.out.println(col.get(0).getAttribute("aria-label"));
		 colu.add(col.get(0).getAttribute("aria-label"));
		 col.remove(0);
		 System.out.println(col.size());
		 for(WebElement c: col){
			 colu.add(c.getText());
			 System.out.println(c.getText());
			  }
		 for(String a:colu) {
		        if(!(txt2.contains(a))) {
		        	System.out.println(a);
		         Assert.assertTrue(false);
		       }}
			 Assert.assertTrue(true);
		
	}
	@Test(groups = "SCP", priority=3) 
	public void TS112797_Plan_de_accion_Search() {
		SCP prueba = new SCP(driver); 
	    prueba.moveToElementOnAccAndClick("cuartoTitulo", 2);
	    WebElement busc = driver.findElement(By.id("mainTable_filter")).findElement(By.tagName("input"));
	    WebElement tabla= driver.findElement(By.id("mainTable_wrapper")).findElement(By.className("odd")).findElements(By.tagName("td")).get(1);
	    String lala = tabla.getText();
	    busc.sendKeys(lala);
	    Assert.assertTrue(lala.contains(tabla.getText()));
	}
	
	@Test(groups = "SCP", priority=3) 
	public void TS112798_Plan_de_accion_Triangulo_ordenador() throws ParseException {
		SCP prueba = new SCP(driver); 
	    prueba.moveToElementOnAccAndClick("cuartoTitulo", 2);
	    prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 7, 2);
	    List<WebElement> tod= driver.findElement(By.id("mainTable_wrapper")).findElements(By.className("odd"));
	    tod.addAll(driver.findElement(By.id("mainTable_wrapper")).findElements(By.className("even")));
	    ArrayList<String> todo = new ArrayList<String>();
		ArrayList<String> fechas = new ArrayList<String>();
		todo.add("tod");
			for(WebElement t : tod) {
				fechas.add(t.findElements(By.tagName("td")).get(1).getText().split(" ")[0]);
			}
			for(String f :fechas) {
				System.out.println(f);
			}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		String fech = new String();
	    String fecha2= new String();
	    Date date1 = new Date();
	    Date date2 = new Date();  
	    	for(int i = 0; i<=fechas.size()-1;i++) {
	    		fech=fechas.get(i);
	    		fecha2=fechas.get(i+1);
	    		date1 = sdf.parse(fech);
	    		date2 = sdf.parse(fecha2);
	    			if(date1.compareTo(date2)>0) {
	    				System.out.println(date2+" es menor que "+date1);
	    				Assert.assertTrue(false);
	    			}
	    	}
	    Assert.assertTrue(true);
		}
	}
