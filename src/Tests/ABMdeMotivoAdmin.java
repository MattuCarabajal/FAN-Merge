package Tests;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.ContactMotiveManager;
import Pages.ContactMotivesManager;
import Pages.HomeBase;
import Pages.setConexion;


public class ABMdeMotivoAdmin extends TestBase {
	
	private WebDriver driver;
	
	private String motiveName = "motivo Nuevo para Tests"; // needed for 12587 and 12589 (ADD and DEL motive)
	private String descripcion = "Descripcion para el test.";
	private String servicio = "Internet 1GB";
	private int bandera = 1;
	
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.quit();
		sleep(1000);
	}

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		//TODO: add how to get to ABM de Motivo
		
		HomeBase homePage = new HomeBase(driver);
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    String a = driver.findElement(By.id("tsidLabel")).getText();
	    if (a.contains("Ventas")){}
	    else {
		     homePage.switchAppsMenu();
		     try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		     homePage.selectAppFromMenuByName("Ventas");
		     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}            
	    }
	    
	    //click +
	    try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    driver.findElement(By.xpath("//a[@href=\"/home/showAllTabs.jsp\"]")).click();
	    
	    try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    WebElement BenBoton = driver.findElement(By.xpath("//a[@href=\"/a41/o\"]"));
	    if (bandera ==1) 
	    	BenBoton = driver.findElement(By.xpath("//a[@href=\"/a44/o\"]"));
	    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
	    BenBoton.click();
	    try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	        
	    
	    driver.findElement(By.id("filter_element")).findElement(By.tagName("input")).click();
	    try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//priority forces the tests order execution, and groupsDependency, guarantees the other ones finished
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11560_ABM_de_Sintomas_De_STT_Activacion_De_Sintoma(){
		ContactMotivesManager cMMPage = new ContactMotivesManager(driver);
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 Random aleatorio = new Random(System.currentTimeMillis());
		     aleatorio.setSeed(System.currentTimeMillis());
		  int intAleatorio = aleatorio.nextInt(8999)+1000;
		String motivoN="Motivo"+Integer.toString(intAleatorio);
		cMMPage.crearMotivoDeContacto(motivoN);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cMMPage.activarMotivoDesdeDetalle();
		sleep(5000);
		driver.findElement(By.className("ptBreadcrumb")).findElement(By.tagName("a")).click();
		cMMPage.seleccionarListado("All");
		sleep(5000);
		List<WebElement> columna = driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-first")).findElements(By.tagName("td"));
		Assert.assertTrue(columna.get(5).findElement(By.tagName("span")).getText().equals(motivoN));
		Assert.assertTrue(columna.get(7).findElement(By.tagName("img")).getAttribute("title").equals("Seleccionado"));
		bandera = 2;
		cMMPage.eliminarPrimerSintoma();
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS12584_ABM_de_Motivo_Ingreso(){
		ContactMotivesManager cMMPage = new ContactMotivesManager(driver);
		cMMPage.getMotivesWrapper();//This should be enough.
		cMMPage.getMotiveByName("No me funciona internet");//Could Change, but this has to be a real current MotiveName.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS12587_ABM_de_Motivo_Agregar_Nuevo_Motivo() {
		driver.findElement(By.name("new")).click();
		ContactMotiveManager contactMMPage = new ContactMotiveManager(driver);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contactMMPage.getContactMotiveName().sendKeys(motiveName);
		contactMMPage.getDescripcion().sendKeys(descripcion);
		//driver.findElements(By.cssSelector(".dataCol.col02")).get(1).findElement(By.tagName("textarea")).sendKeys(descripcion);
		contactMMPage.getActivoCheck().click();
		contactMMPage.getServicio().sendKeys(servicio);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contactMMPage.save();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertEquals(driver.findElement(By.id("Name_ileinner")).getText(), motiveName); //checks name input with current.
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertEquals(driver.findElement(By.id("00Nc0000001pWdl_ileinner")).getText(), descripcion); //checks descripcion input with current.
		driver.findElement(By.className("ptBreadcrumb")).findElement(By.tagName("a")).click(); //goes back to main page for ABM.
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(1500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactMotivesManager contactsMMPage = new ContactMotivesManager(driver);
		//index 4 is the motiveName
		Assert.assertNotEquals(contactsMMPage.getMotiveByName(motiveName),null); //this should be enough.
		//Assert.assertEquals(cMMPage.getMotiveByName(motiveName).findElements(By.className("x-grid3-col")).get(4).getText(), motiveName);
		//this motive gets deleted in another test.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS12590_ABM_de_Motivo_Modificar_Motivo() {
		TS12587_ABM_de_Motivo_Agregar_Nuevo_Motivo();
		ContactMotivesManager contactsMMPage = new ContactMotivesManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contactsMMPage.modifyMotiveByName(motiveName);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		motiveName = "Nombre Motivo MODIFICADO.";
		descripcion = "Descripcion Motivo MODIFICADA.";
		servicio = "Internet 2GB";
		//here all is modified.
		ContactMotiveManager contactMMPage = new ContactMotiveManager(driver);
		contactMMPage.clearValues(); //clears textboxes only.
		contactMMPage.getContactMotiveName().sendKeys(motiveName);
		contactMMPage.getDescripcion().sendKeys(descripcion);
		contactMMPage.getActivoCheck().click();
		contactMMPage.getAsociableAIncidente().click();
		contactMMPage.getServicio().sendKeys(servicio);
		contactMMPage.save();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		contactsMMPage.modifyMotiveByName(motiveName); //opens modify, but just will assertEquals.
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//only asserts Texts, maybe checks should be checked as well.
		Assert.assertEquals(contactMMPage.getContactMotiveName().getAttribute("value"), motiveName);
		Assert.assertEquals(contactMMPage.getDescripcion().getAttribute("value"), descripcion);
		Assert.assertEquals(contactMMPage.getServicio().getAttribute("value"), servicio);
		contactMMPage.cancel();
	}
	
	
	/*@Test(priority = 4, groups ="b", dependsOnGroups = "a")
	public void TS12589_ABM_de_Motivo_Asociar_Motivo_A_Incidente_Masivo() {
		//expected main page for ABM of motives.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//The Motive Name here is: Nombre Motivo MODIFICADO.
		ContactMotivesManager cMMPage = new ContactMotivesManager(driver);
		cMMPage.openMotiveByName(motiveName);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactMotive CMPage = new ContactMotive(driver);
		CMPage.newCase();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//Could use AccountType
		WebElement registerTypeSelect = driver.findElement(By.id("p3"));
		CMPage.setSimpleDropdown(registerTypeSelect, "Incidente Masivo");
		driver.findElement(By.name("save")).click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.name("save")).click();

	}*/
	
	//@Test(priority = 5, groups ="c", dependsOnGroups = "b") //change to this when TS12589-MassiveIncident works.
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS12589_ABM_de_Motivo_Quitar_Motivo() {
		//expected main page for ABM of motives.
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactMotivesManager cMMPage = new ContactMotivesManager(driver);
		//index 4 is the motiveName
		Assert.assertEquals(cMMPage.getMotiveByName(motiveName).findElements(By.className("x-grid3-col")).get(4).getText(), motiveName);
		cMMPage.deleteMotiveByName(motiveName); //here its deleted
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		Assert.assertEquals(cMMPage.getMotiveByName(motiveName), null); //The motive doesnt exist anymore :( 
	}	
}
