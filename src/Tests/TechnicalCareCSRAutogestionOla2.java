package Tests;

import static org.testng.Assert.assertTrue;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRAutogestionPage;
import Pages.setConexion;


public class TechnicalCareCSRAutogestionOla2 extends TestBase{
	
	private WebDriver driver;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws InterruptedException{
		this.driver = setConexion.setupEze();
	    sleep(5000);
	    login(driver);
	    sleep(5000);
	    HomeBase homePage = new HomeBase(driver);
	    Accounts accountPage = new Accounts(driver);
	    if(driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
	    homePage.switchAppsMenu();
	    sleep(2000);
	    homePage.selectAppFromMenuByName("Ventas");
	    sleep(5000);
	       }
	    homePage.switchAppsMenu();
	    sleep(2000);
	    homePage.selectAppFromMenuByName("Consola FAN");
	    sleep(5000);
		goToLeftPanel2(driver, "Cuentas");
		sleep(2000);  
		driver.switchTo().defaultContent();
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".topNav.primaryPalette")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		try {field.selectByVisibleText("Todas Las cuentas");}
		catch (org.openqa.selenium.NoSuchElementException ExM) {field.selectByVisibleText("Todas las cuentas");}
		

	 	 CustomerCare cerrar = new CustomerCare(driver);
	 	 cerrar.cerrarultimapestana();		
	 	 sleep(4000);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		//Selecciona la cuenta Adrian Tech de todas las Cuentas
 		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		driver.switchTo().defaultContent();
		sleep(3000);
		//page.selectAccount((buscarCampoExcel(3, "Cuenta Activa c/ linea y serv", 1)));
		page.selectAccount ("Adrian Tech");
		driver.switchTo().defaultContent();
		sleep(3000);
		//selecciona el campo donde esta la busquedad escribe y busca
		searchAndClick(driver, "Diagnóstico de Autogestión");
		driver.switchTo().defaultContent();
		sleep(3000);
	}
	
	//@AfterMethod(alwaysRun=true)
		public void after() {
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent(); 
		CustomerCare cerrar = new CustomerCare(driver);
	    cerrar.cerrarultimapestana();
	    driver.switchTo().defaultContent(); 
	}
	
	//@AfterClass(alwaysRun=true)
		public void tearDown() {
			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			CustomerCare cerrar = new CustomerCare(driver);
			cerrar.cerrarultimapestana();
			HomeBase homePage = new HomeBase(driver);
			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			homePage.selectAppFromMenuByName("Ventas");
			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.quit();
		}
	
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73882_CRM_Fase4_TechnicalCare_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Mi_cuenta_corresponde_a_un_servicio_de_Telecom() throws InterruptedException {
		sleep(5000);
		BasePage cambioFrameByID=new BasePage();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
				TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
				tech.listadoDeSeleccion("WEB","Mi cuenta", "Información Incorrecta");
				tech.clickOnButtons();
				sleep(4000);
				tech.verificarCaso();
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
				tech.getCaseBody().click();		
				sleep(5000);
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
				List<WebElement>menu=tech.getOptionContainer();
				menu.get(4).click();
				assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
		}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73795_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_111_corresponde_a_Telecom() throws InterruptedException{
		sleep (3000);
			BasePage cambioFrameByID=new BasePage();
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
				TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
				tech.listadoDeSeleccion("Asteriscos TP","*111", "Información Incorrecta");
				tech.clickOnButtons();
				sleep(4000);
				tech.verificarCaso();
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
				tech.getCaseBody().click();		
				sleep(5000);
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
				List<WebElement>menu=tech.getOptionContainer();
				menu.get(4).click();
				assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));						
	}
			
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73813_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_112_accede_al_112_fija_corresponde_a_TP_Telecom() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Asteriscos TP","*112 (accede al 112 fija)", "Información Incorrecta");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
			
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73797_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_150_saldo_corresponde_a_TP_Telecom() throws InterruptedException {
		
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP","*150 (saldo)", "Información Incorrecta");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	
		}
		
			
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion" , "Ola2"})
	public void TS73806_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_151_recargas_corresponde_a_TP_Telecom() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Asteriscos TP","*151 (recargas)", "Información Incorrecta");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73798_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_152_packs_nros_amigos_corresponde_a_TP_Telecom() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Asteriscos TP","*152 (packs, nros amigos)", "Información Incorrecta");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
			
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73817_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_211_Tienda_Personal_corresponde_a_TP_Telecom() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Asteriscos TP","*211 (Tienda Personal)", "Información Incorrecta");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73816_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_211_Ventas_corresponde_a_TP_Telecom() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Asteriscos TP","*211 (Ventas)", "Información Incorrecta");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73828_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_222_ACA_corresponde_a_un_servicio_de_Terceros() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Otros Asteriscos","*222 (ACA)", "Llamada falló");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73830_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_242643_Banco_Piano_corresponde_a_un_servicio_de_Terceros() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Otros Asteriscos","*242643 (Banco Piano)", "Llamada falló");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
		
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion" ,"Ola2"})
	public void TS73804_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_2447_Chip_corresponde_a_TP_Telecom() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
			tech.listadoDeSeleccion("Asteriscos TP","*2447 (Chip)", "Información Incorrecta");
			tech.clickOnButtons();
			sleep(4000);
			tech.verificarCaso();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
			tech.getCaseBody().click();		
			sleep(5000);
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
			List<WebElement>menu=tech.getOptionContainer();
			menu.get(4).click();
			assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
			
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73831_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_2463_corresponde_a_un_servicio_de_Terceros() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos", "*2463", "Llamada falló");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73832_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_2484_Citi_corresponde_a_un_servicio_de_Terceros() throws InterruptedException {	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","*2484 (Citi)", "Llamada falló");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73800_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_25225_Black_corresponde_a_TP()throws InterruptedException {	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP","*25225 (Black)", "Información Incorrecta");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}

	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73801_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_25283_Clave_corresponde_a_TP()throws InterruptedException {	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP","*25283 (Clave)", "Información Incorrecta");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73833_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_272_Buquebus_corresponde_a_un_servicio_de_Terceros()throws InterruptedException {	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","*272 (Buquebus)", "Tono ocupado");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73834_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_27638_Arnet_corresponde_a_un_servicio_de_Terceros()throws InterruptedException {	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","*27638 (Arnet)", "Tono ocupado");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion" , "Ola2"})
	public void TS73826_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_288_788_asistencia_en_ruta_corresponde_a_un_servicio_de_Terceros()throws InterruptedException {	
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","*288/*788 (788 asistencia en ruta)", "Tono ocupado");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	
	}


	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73835_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_347_corresponde_a_un_servicio_de_Terceross()throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","*347", "Tono ocupado");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73836_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_380_corresponde_a_un_servicio_de_Terceros()throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","*380", "Tono ocupado");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73818_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_531_Tienda_Planes_corresponde_a_TP() throws InterruptedException {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*531 (Tienda Planes)", "Información Incorrecta");
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73805_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_333_recarga_delivery_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*333 (recarga delivery)", "Información Incorrecta");//  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73820_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_534_EMail_Marketing_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*534 (E-Mail Marketing)", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73821_RM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_539_Home_Personal_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*539 (Home Personal)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73824_RM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_554_Facebook_Personal_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*554 (Facebook Personal)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73808_RM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_555_contestador_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*555 (contestador)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73824_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_554_Facebook_Personal_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*554 (Facebook Personal)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	

	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73808_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_555_contestador_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*555 (contestador)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73837_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_746_Banco_Rio_super_linea_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos", "*746 (Banco Río, super línea)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73803_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_7526_PLAN_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*7526 (PLAN)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73812_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_762_ROAMERS_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*762 (ROAMERS)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73807_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_767_SOS_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*767 (SOS)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73802_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_77666_Promo_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*77666 (Promo)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73838_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_8472_847_super_linea_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos", "*8472/*847", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Terceros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73796_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_878_Saldo_Virtual_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*878 (Saldo Virtual)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73810_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_88988_SAEC_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*88988 (SAEC)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73811_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_910_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*910", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73809_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_99999_PBP_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*99999 (PBP)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73799_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_CLUB_2582_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*CLUB (*2582)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73821_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_539_Home_Personal_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*539 (Home Personal)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73825_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_Otros_corresponde_a_TP() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "Otros", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS75922_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0554_Facebok_Personal_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("0800", "0800-444-0554 (Facebook Personal)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS75540_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_4100_Ventas_desde_la_Web_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("0800", "0800-444-4100 (Asistencia Tienda Online)", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73900_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Centros_de_Atencion_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Centros de Atención", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73901_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Club_Personal_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Club Personal", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73902_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Compra_Packs_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Compra Packs", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73897_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Mi_Fracturacion_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Mi Facturación", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73898_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Mi_Linea_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Mi Línea", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73899_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Mis_Consumos_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Mis Consumos", "Información Incorrecta");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}

	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
		public void TS73993_CRM_Fase_4_Technical_Care_CSR_AutoGestion_0800_Otros_Visualizacion_de_campo() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("0800","Otros","Tono ocupado" );
		assertTrue(tech.verificarOpciones(tech.getServiceSelection(), "Otros"));
		
			
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
		public void TS73990_CRM_Fase_4_Technical_Care_CSR_AutoGestion_Asteriscos_Otros_Visualizacion_de_campo() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP","Otros","Tono ocupado" );
		assertTrue(tech.verificarOpciones(tech.getServiceSelection(), "Otros"));
		
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
		public void TS73992_CRM_Fase_4_Technical_Care_CSR_AutoGestion_Nros_Emergencia_Otros_Visualizacion_de_campo() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Nros. emergencia","Otros","Tono ocupado" );
		assertTrue(tech.verificarOpciones(tech.getServiceSelection(), "Otros"));
	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
		public void TS73991_CRM_Fase_4_Technical_Care_CSR_AutoGestion_Otros_Asteriscos_Otros_Visualizacion_de_campo() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Otros Asteriscos","Otros","Tono ocupado" );
		assertTrue(tech.verificarOpciones(tech.getServiceSelection(), "Otros"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73903_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_APP_servicio_Otros_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("APP", "Otros", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73923_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Mi_Personal_SIM_servicio_Comprar_Packs_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Mi Personal (SIM)", "Comprar Packs", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73928_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Mi_Personal_SIM_servicio_Otros_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Mi Personal (SIM)", "Otros", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73927_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Mi_Personal_SIM_servicio_Promociones_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Mi Personal (SIM)", "Promociones", "Tono ocupado");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73916_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_ABM_Números_Amigos_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (ABM Números Amigos)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73920_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_Consulta_de_Recargas_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (Consulta de Recargas)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73919_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_Consulta_de_Saldo_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (Consulta de Saldo)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73915_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_Consulta_Puntos_Club_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (Consulta Puntos Club)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73917_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_Consulta_Activación_de_Packs_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (Consulta, Activación de Packs)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73921_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_Detalle_de_Consumo_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (Detalle de Consumo)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73914_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_111_Consulta_Promociones_y_Beneficios_corresponde_a_un_servicio_de_Telecom() throws Exception {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*111# (Consulta Promociones y Beneficios)", "Otros");  
		tech.clickOnButtons();
		sleep(4000);
		tech.verificarCaso();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
		tech.getCaseBody().click();		
		sleep(5000);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionLabel")));
		List<WebElement>menu=tech.getOptionContainer();
		menu.get(4).click();
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73911_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_150_saldo_corresponde_a_un_servicio_de_Telecom() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*150# (saldo)", "No Interactúa");  
		tech.clickOnButtons();
		tech.verificarNumDeGestion();
		tech.ServiceOwner();
		sleep(3000);
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS73910_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_156_roaming_corresponde_a_un_servicio_de_Telecom() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("USSD", "*156# (roaming)", "No Interactúa");  
		tech.clickOnButtons();
		tech.verificarNumDeGestion();
		tech.ServiceOwner();
		sleep(3000);
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_del_servicio_100_corresponde_a_un_servicio_de_Telecom() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Nros. emergencia", "100", "Tono ocupado");  
		tech.clickOnButtons();
		tech.verificarNumDeGestion();
		tech.ServiceOwner();
		sleep(3000);
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_del_servicio_101_corresponde_a_un_servicio_de_Telecom() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Nros. emergencia", "101", "Tono ocupado");  
		tech.clickOnButtons();
		tech.verificarNumDeGestion();
		tech.ServiceOwner();
		sleep(3000);
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_del_servicio_102_corresponde_a_un_servicio_de_Telecom() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Nros. emergencia", "102", "Tono ocupado");  
		tech.clickOnButtons();
		tech.verificarNumDeGestion();
		tech.ServiceOwner();
		sleep(3000);
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
		
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion", "Ola2"})
	public void TS_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_del_servicio_103_corresponde_a_un_servicio_de_Telecom() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Nros. emergencia", "103", "Tono ocupado");  
		tech.clickOnButtons();
		tech.verificarNumDeGestion();
		tech.ServiceOwner();
		sleep(3000);
		assertTrue(tech.getVerificar().getText().equalsIgnoreCase("Telecom"));
	}
	
	@Test(groups= {"TechnicalCare", "Autogestion", "Ola2"})
	public void TS_CSR_Autogestión_Asteriscos_Verificar_Opcion_SI_cree_y_cierre_el_caso() throws Exception {
		TechnicalCareCSRAutogestionPage tech = new TechnicalCareCSRAutogestionPage(driver);
		tech.listadoDeSeleccion("Asteriscos TP", "*111", "Tono ocupado");  
		tech.selectionInconvenient("Sí");
		tech.verificarCaso();	
		sleep(3000);
		tech.cerrarCaso("Resuelta Masiva", "Instructions not clear");
	}

}


