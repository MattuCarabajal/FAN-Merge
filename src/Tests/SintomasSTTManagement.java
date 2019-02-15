package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.HomeBase;
import Pages.SintomasSSTManager;
import Pages.setConexion;

//page link: https://cs14.salesforce.com/a44?fcf=00Bc0000001LRmd
//consola FAN link : https://crm--sit.cs14.my.salesforce.com/console?tsid=02uc0000000D6Hd

public class SintomasSTTManagement extends TestBase {
	
	private WebDriver driver;
	private String symptomsListURL = "https://crm--sit.cs14.my.salesforce.com/console?tsid=02uc0000000D6Hd";
	private String validDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
			+ "Morbi bibendum erat sit amet metus molestie egestas. Phasellus facilisis tortor sapien, quis "
			+ "consequat orci dapibus vel. Praesent interdum consectetur neque tempor sagittis. "
			+ "Nulla facilisi volutpat.";
	
	private String invalidDescription = "Alorem ipsum dolor sit amet, consectetur adipiscing elit. "
			+ "Morbi bibendum erat sit amet metus molestie egestas. Phasellus facilisis tortor sapien, quis "
			+ "consequat orci dapibus vel. Praesent interdum consectetur neque tempor sagittis. "
			+ "Nulla facilisi volutpat.";
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		//TODO: add how to get to ABM de Motivo
		HomeBase homePage = new HomeBase(driver);
		if(!driver.findElement(By.id("tsidLabel")).getText().equals("Ventas")) {
	    	 homePage.switchAppsMenu();
	    	 try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    	 homePage.selectAppFromMenuByName("Ventas");
	    	 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}    
	     }
	     homePage.switchAppsMenu();
	     try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     homePage.selectAppFromMenuByName("Consola FAN");
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}        
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	@AfterClass(alwaysRun=true)
	public void tearDown() {

		HomeBase homePage = new HomeBase(driver);
		homePage.closeAllTabs(driver);
		homePage.switchAppsMenu();
		homePage.selectAppFromMenuByName("Ventas");
		driver.quit();
		sleep(2000);
	}
	
	@AfterMethod(alwaysRun=true)
	public void goToConsolaFAN() {
		HomeBase homePage = new HomeBase(driver);
		homePage.switchAppsMenu();
		//homePage.selectAppFromMenuByName("Ventas");
		homePage.selectAppFromMenuByName("Consola FAN");
	}
		//************FALLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO*****************
	//Uses both pages (Admin and user SST Symptoms ABM)
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS12605_SST_Sintomas_Consistencia(){
		HomeBase homePage = new HomeBase(driver);
		homePage.switchAppsMenu();
		//driver.findElement(By.id("tsidButton")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		homePage.selectAppFromMenuByName("Ventas");
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		homePage.selectMainTabByName("Síntomas de STT");
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		//this is How the page handles the selection
		sstManagerPage.selectToSeeByName("ABM de Síntomas STT");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.selectToSeeByName("All");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<String> symptomsRegInSSTView = sstManagerPage.getSymptomsRegisterNumbers();
		homePage.switchAppsMenu();
		homePage.selectAppFromMenuByName("Consola FAN");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<String> symptomsRegInSSTViewForAdmin = sstManagerPage.getSymptomsRegisterNumbersForAdmin();
		Assert.assertTrue((new HashSet(symptomsRegInSSTViewForAdmin)).equals((new HashSet(symptomsRegInSSTView))));
		//symptomsRegInSSTView.add("unStringDeMas"); //this proves HashSet.equals() is working correctly.
		//Assert.assertFalse((new HashSet(symptomsRegInSSTViewForAdmin)).equals((new HashSet(symptomsRegInSSTView))));
	}
	
	//Admin Symptoms ABM Page
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11561_Admin_Desactivacion_De_Sintoma(){
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement activeSymptom = sstManagerPage.getFirstActiveSymptom();
		boolean isActive = sstManagerPage.isSymptomActive(activeSymptom);
		Assert.assertTrue(isActive);//checks condition, that symptom is active.
		sstManagerPage.setSymptomState(activeSymptom, false);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertFalse(sstManagerPage.isSymptomActive(activeSymptom));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11558_Creacion_De_Sintoma_Descripcion_255(){
		String nombreSintomaNuevo = "TS11558 Sintoma nuevo.255";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, validDescription);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
	}

	//*********esta fue la que fallo
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11559_Creacion_De_Sintoma_Descripcion_256(){
		//the creation is allowed, it just cuts down what exceeds the 255 limit.
		String nombreSintomaNuevo = "TS11559 Sintoma nuevo.256";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, invalidDescription);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11557_Creacion_De_Sintoma_Descripcion_letra(){
		String nombreSintomaNuevo = "TS11557 Sintoma con desc 1 letra.";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, "a");//1 letter, should be allowed to create.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11556_Creacion_De_Sintoma_Descripcion_numero(){
		String nombreSintomaNuevo = "TS11557 Sintoma con desc 1 numero.";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, "9");//1 number, should be allowed to create.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11555_Creacion_De_Sintoma_Descripcion_simbolo(){
		String nombreSintomaNuevo = "TS11555 Sintoma con desc 1 simbolo.";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, "®");//1 symbol, should NOT be allowed to create.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertFalse(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was NOT found.

		//this isn't working.

		//this isn't implemented yet.

	}

	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11546_Creacion_De_Sintoma_Ejecucion_activado(){
		String nombreSintomaNuevo = "TS11546: Sintoma ACTIVADO";
		String activadoDescripcion = "Se creo activado.";
		boolean crearActivado = true; //this checks the active checkBox.
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.deleteAllSymptomsByName(nombreSintomaNuevo); //this should be done in the tearDown method. afterClass.
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, activadoDescripcion, crearActivado);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement newSymptom = sstManagerPage.getSymptomByName(nombreSintomaNuevo);
		Assert.assertTrue(newSymptom != null); //verifies that the symptom was found.
		Assert.assertTrue(sstManagerPage.isSymptomActive(newSymptom));
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11547_Creacion_De_Sintoma_Ejecucion_desactivado(){
		String nombreSintomaNuevo = "TS1157: Sintoma DESACTIVADO";
		String activadoDescripcion = "Se creo desactivado.";
		boolean crearActivado = false; //this doesn't check the activeCheckbox.
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.deleteAllSymptomsByName(nombreSintomaNuevo); //this should be done in the tearDown method. afterClass.
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, activadoDescripcion, crearActivado);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement newSymptom = sstManagerPage.getSymptomByName(nombreSintomaNuevo);
		Assert.assertTrue(newSymptom != null); //verifies that the symptom was found.
		Assert.assertFalse(sstManagerPage.isSymptomActive(newSymptom)); //verifies the symptom isn't active.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11549_Creacion_De_Sintoma_Fecha_De_Alta_verificacion(){
		String nombreSintomaNuevo = "TS11549: Fecha de alta verificacion.";
		String activadoDescripcion = "Se creo con fecha dd/mm/yyyy.";
		boolean crearActivado = false; //this doesn't check the activeCheckbox.
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.deleteAllSymptomsByName(nombreSintomaNuevo); //this should be done in the tearDown method. afterClass.
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, activadoDescripcion, crearActivado);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement newSymptom = sstManagerPage.getSymptomByName(nombreSintomaNuevo);
		Date newSymptomDateInPage = sstManagerPage.getSymptomDateByName(nombreSintomaNuevo);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		//System.out.println(dtf.format(now)); //current date as string.
		Date currentDate = null;
		try {
			currentDate = dateFormat.parse(dtf.format(now));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(newSymptom != null); //verifies that the symptom was found.
		Assert.assertTrue(newSymptomDateInPage.equals(currentDate));//verifies that the dates matches.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11551_Creacion_De_Sintoma_Fecha_De_Modificacion_verificacion(){
		//Condition : There must be only one symptom with this name.
		String nombreSintomaModificar = "TS11546: Sintoma ACTIVADO";
		TestUtils testUtils = new TestUtils();
		String fechaActualConHoras = testUtils.getCurrentDateWithHoursString();
		String activadoDescripcion = "Se modifico con fecha " + fechaActualConHoras;
		boolean crearActivado = false; //this doesn't check the activeCheckbox.
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToModifySymptomByName(nombreSintomaModificar);//selects this symptom to modify
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaModificar, activadoDescripcion, crearActivado);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Date newSymptomDateInPage = sstManagerPage.getSymptomModifiedDateByName(nombreSintomaModificar);
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date currentDate = null;
		String fechaActual = testUtils.getCurrentDateString();
		try {
			currentDate = dateFormat.parse(fechaActual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(currentDate.equals(newSymptomDateInPage)); //verifies that modif and current date are the same.
	}
	
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11554_Creacion_De_Sintoma_Nombre_Sintoma_letra(){
		String nombreSintomaNuevo = "s";
		String descripcionSintomaNuevo = "TS11554: 1 letra.";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.deleteAllSymptomsByName(nombreSintomaNuevo); //this should be done in the tearDown method. afterClass.
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, descripcionSintomaNuevo);//1 letter name, should be allowed to create.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11553_Creacion_De_Sintoma_Nombre_Sintoma_numero(){
		String nombreSintomaNuevo = "8";
		String descripcionSintomaNuevo = "TS11553: un numero (8).";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.deleteAllSymptomsByName(nombreSintomaNuevo); //this should be done in the tearDown method. afterClass.
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, descripcionSintomaNuevo);//1 number, should be allowed to create.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
	}
	
	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11552_Creacion_De_Sintoma_Nombre_Sintoma_simbolo(){
		String descripcionSintomaNuevo = "TS11552: nombre simbolo, no deberia poder crearse.";
		String nombreSintomaNuevo =  "®";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, descripcionSintomaNuevo);//1 symbol, should NOT be allowed to create.
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertFalse(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was NOT found.
		//this isn't implemented yet.
	}

	@Test(groups = {"Fase2","TechnicalCare","ABMAdministrador","Ola2"})
	public void TS11550_Creacion_De_Sintoma_Nombre_Usuario_verificacion(){
		String descripcionSintomaNuevo = "Verificacion usuario. Deber ser USIT.";
		String nombreSintomaNuevo =  "TS11550: Verificacion usuario.";
		String nombreDeUsuarioEsperado = "Daniel Sit";
		SintomasSSTManager sstManagerPage = new SintomasSSTManager(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.deleteAllSymptomsByName(nombreSintomaNuevo); //this should be done in the goToConsolaFAN afterMethod.
		sstManagerPage.goToCreateNewSymptom();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		sstManagerPage.fillAndSaveCustomSymptom(nombreSintomaNuevo, descripcionSintomaNuevo);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Síntomas de STT");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(sstManagerPage.getSymptomByName(nombreSintomaNuevo) != null); //verifies that the symptom was found.
		sstManagerPage.openSymptomByName(nombreSintomaNuevo);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String nombreEnSintomaAbierto = sstManagerPage.getCreatedByProperty();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(nombreEnSintomaAbierto.startsWith(nombreDeUsuarioEsperado)); //startsWith, because it has the date attached.
	}
	
}
