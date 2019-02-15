package Tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.hamcrest.core.Is;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.Page;

import Pages.AccountType;
import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;


public class TechnicalCareCSRDiagnosticoOla1 extends TestBase{
	
private WebDriver driver;
	
 	@BeforeClass(groups= {"TechnicalCare", "SVA", "Ola1"})
 	public void init() throws InterruptedException{
	this.driver = setConexion.setupEze();
    sleep(5000);
    login(driver);
    sleep(5000);
    HomeBase homePage = new HomeBase(driver);
    Accounts accountPage = new Accounts(driver);
    try {
    	if(driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
    	    homePage.switchAppsMenu();
    	    sleep(2000);
    	    homePage.selectAppFromMenuByName("Ventas");
    	    sleep(5000);
    	       }
    	    homePage.switchAppsMenu();
    	    sleep(2000);
    	    homePage.selectAppFromMenuByName("Consola FAN");
	}catch(Exception ex) {
		sleep(3000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(6000);
	}
    
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
		sleep(3000);
		driver.switchTo().defaultContent();
		sleep(3000);
		//page.selectAccount((  (3, "Cuenta Activa c/ linea y serv", 1)));
		//page.selectAccount ("Marco Polo");
		//driver.switchTo().defaultContent();
		//sleep(3000);
 	
	}
 	 	
 		//@AfterMethod(alwaysRun=true)
 		public void after() {
 			CustomerCare cerrar = new CustomerCare(driver);
 		    cerrar.cerrarultimapestana();
 		    sleep(2000);
 		}
 		
 		//@AfterClass(alwaysRun=true)
 		public void tearDown() {
 			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
 			CustomerCare cerrar = new CustomerCare(driver);
 			cerrar.cerrarultimapestana();
 			/*HomeBase homePage = new HomeBase(driver);
 			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
 			homePage.selectAppFromMenuByName("Ventas");
 			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}*/
 			driver.quit();
 		}
 		
	
	
	

	@Test (groups= {"TechnicalCare", "SVA", "Ola1","filtrado"},priority=2, dataProvider="Tech")
	public void TS94226_CRM_Ola_1_Technical_Care_CSR_SVA_Actualización_de_matriz_Servicio_Transferencia_de_llamadas_inconveniente_No_funciona_transferencia_de_llamadas_No_funciona_transferencia_de_llamadas_No_puede_configurar(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
    tech.verDetalles();
    tech.clickDiagnosticarServicio("Transferencia de Llamadas");
    sleep (4000);
    tech.selectionInconvenient("No puede configurar");
    assertTrue(tech.validarInconveniente("No puede configurar"));
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=2, dataProvider="Tech")
	public void TS94368_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_lista_de_servicios_AGRUPADOR(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(4000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Entrante", false);
    sleep(3000);
    assertTrue(tech.validarOpcionesXSubServicio("SMS Entrante"));
    
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech") //listo
	public void TS94439_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Transferencia_de_llamadas_e_inconveniente_No_funciona_transferencia_de_llamadas_No_puede_configurar(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(3000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("Transferencia de Llamadas");
    tech.selectionInconvenient("No funciona transferencia de llamadas");
    assertTrue(tech.validarInconveniente("No funciona transferencia de llamadas"));
    
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech")//listo
	public void TS94440_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Llamada_en_espera_e_inconveniente_No_funciona_llamada_en_espera(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("Llamada en espera"); 
    tech.selectionInconvenient("No funciona llamada en espera");
    assertTrue(tech.validarInconveniente("No funciona llamada en espera"));
    
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech")//listo
	public void TS94441_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Llamada_tripartita_e_inconveniente_No_funciona_Llamada_tripartita(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("Llamada Tripartita");
    tech.selectionInconvenient("No funciona Llamada tripartita");
    sleep(8000);
    assertTrue(tech.validarInconveniente("No funciona Llamada tripartita"));
    
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech")//listo
	public void TS94459_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Conferencia_tripartita_e_inconveniente_No_funciona_Conferencia_Tripartita(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("Llamada Tripartita");
    tech.selectionInconvenient("No funciona Llamada tripartita");
    sleep(8000);
    assertTrue(tech.validarInconveniente("No funciona Llamada tripartita"));
	}
	
	@Test(groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech") //Listo
	public void TS94464_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Mensajes_Multimedia_Personal_MMS_e_inconveniente_MMS_Emisión_Cliente_informa_que_no_puede_enviar_Archivo_Imagen_Audio(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("MMS");
    tech.selectionInconvenient("MMS Emisión Cliente informa que no puede enviar Imagen");
    sleep(8000);
    assertTrue(tech.validarInconveniente("MMS Emisión Cliente informa que no puede enviar Imagen"));
    
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech")
	public void TS94467_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_SMS_saliente_e_inconveniente_SMS_Emisión_a_algun_destino_en_particular(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", false);
    sleep(8000);
    assertTrue(tech.validarOpcionesXSubServicio("SMS Saliente"));

    
    }
	@Test (groups= {"TechnicalCare", "SVA", "Ola1","filtrado"},priority=3, dataProvider="Tech") //Listo
	public void TS94276_CRM_Ola_1_Technical_Care_CSR_SVA_Validacion_SMS_entrante_no_recibe_ningun_numero(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(4000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Entrante", true);
    tech.selectionInconvenient("No recibe de un número particular");
    assertTrue(tech.validarInconveniente("No recibe de un número particular"));
	
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech") //Listo
	public void TS94277_CRM_Ola_1_Technical_Care_CSR_SVA_Validacion_SMS_saliente_no_emite_a_ningun_numero(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(4000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
    tech.selectionInconvenient("SMS a fijo");
    assertTrue(tech.validarInconveniente("SMS a fijo"));
	
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech") //Listo
	public void TS94278_CRM_Ola_1_Technical_Care_CSR_SVA_Validacion_SMS_saliente_no_emite_a_algun_destino(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
    tech.selectionInconvenient("SMS Emisión a algún destino en particular");
    assertTrue(tech.validarInconveniente("SMS Emisión a algún destino en particular"));
	
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=2, dataProvider="Tech")
	public void TS94309_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_buscador_para_servicios_agrupados(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.buscarServicio("SMS");
    assertTrue(tech.validarOpcionesXServicio("SMS"));
   	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=2, dataProvider="Tech") 
	public void TS94310_CRM_Ola_1_Technical_Care_CSR_SVA_Verificacion_del_funcionamiento_del_buscador_para_servicios_agrupados(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.buscarServicio("VOZ");
    assertTrue(tech.validarOpcionesXServicio("VOZ"));
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=2, dataProvider="Tech")
	public void TS94311_CRM_Ola_1_Technical_Care_CSR_SVA_Verificacion_de_lista_de_servicios_agrupados(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", false);
    assertTrue(tech.validarOpcionesXServicio("SMS Saliente"));
    
	}
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=2, dataProvider="Tech")
	public void TS94312_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_del_agrupador_estado_ACTIVO(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", false);
    assertTrue(tech.validarEstado("SMS"));
    
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=11, dataProvider="Tech")
	public void TS94315_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizar_pregunta_al_final_del_proceso_SVA(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Entrante", true);
    tech.selectionInconvenient("No recibe de un número particular");
    tech.continuar();
    tech.seleccionarRespuesta("no");
    page.seleccionarPreguntaFinal("Sí");
    tech.clickContinuar();
    sleep(3000);
    tech.categoriaRed("Desregistrar");
    tech.clickContinuar();
    tech.speech();
    tech.categoriaRed("Sí");
    tech.clickContinua();
    assertTrue( tech.serviciofunciona("No"));
	}
	
	
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech")
	public void TS94413_CRM_Ola_1_Technical_Care_CSR_SVA_Validaciones_SMS_Emisión_a_ningún_destino_INCONSISTENCIA_LOCACION_NO(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
    sleep (4000);
    tech.selectionInconvenient("SMS Emisión a algún destino en particular");
    tech.continuar();
    tech.seleccionarRespuesta("no");
    tech.clickContinuar();
    page.seleccionarPreguntaFinal("Sí");
    tech.clickContinuar();
    tech.categoriaRed("Desregistrar");
    tech.clickContinuar();
    assertTrue(tech.validarSpeech());
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=7, dataProvider="Tech")
	public void TS94339_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_de_mensaje_solicitando_realizar_un_consumo(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(5000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
    tech.selectionInconvenient("SMS Emisión a algún destino en particular");
    tech.continuar();
    tech.seleccionarRespuesta("no");
    tech.clickContinuar();
    page.seleccionarPreguntaFinal("Sí");
    tech.categoriaRed("Desregistrar");
    tech.clickContinuar();
    tech.speech();
    tech.categoriaRed("No");
    assertTrue(tech.validarInconveniente("No"));
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1"},priority=3, dataProvider="Tech")
		public void TS94343_CRM_Ola_1_Technical_Care_CSR_SVA_Seleccion_OBLIGATORIA_de_inconveniente(String sCuenta, String sDni, String sLinea) throws Exception {
	    TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	    TechCare_Ola1 page=new TechCare_Ola1(driver);
		page.selectAccount (sCuenta);
		driver.switchTo().defaultContent();
		sleep(3000);
		tech.clickOpcionEnAsset(sLinea, "mis servicios");
		tech.verDetalles();
	    tech.clickDiagnosticarServicio("sms", "SMS Saliente", false);
	    assertTrue(tech.validarEstado("SMS"));
	}
	
	@Test (groups= {"TechnicalCare", "SVA", "Ola1","filtrado"},priority=2, dataProvider="Tech")
	public void TS94393_CRM_Ola_1_Technical_Care_CSR_Mis_Servicios_Identificacion_de_los_Servicios_opcionales_y_obligatorios(String sCuenta, String sDni, String sLinea) throws Exception {
    TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
    TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Entrante", false);
    assertTrue(tech.validarEstado("SMS"));
	}
	

	@Test (groups= {"TechnicalCare", "SVA", "Ola1"}, priority=1, dataProvider="Tech")
	public void TS94369_CRM_Ola_1_Technical_Care_CSR_SVA_Visualizacion_servicio_ACTIVO(String sCuenta, String sDni, String sLinea) throws Exception {
    TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
    TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	sleep(8000);
	driver.switchTo().defaultContent();
	sleep(8000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	sleep(8000);
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Entrante", false);
    assertTrue(tech.validarEstado("SMS"));
	}
	

	@Test (groups= {"TechnicalCare", "SVA", "Ola1","filtrado"},priority=8, dataProvider="Tech")
	public void TS94352_CRM_Ola_1_Technical_Care_CSR_SVA_Verificacion_de_la_posicion_en_el_mapa(String sCuenta, String sDni, String sLinea) throws Exception {
	TechnicalCareCSRDiagnosticoPage tech = new TechnicalCareCSRDiagnosticoPage(driver);
	TechCare_Ola1 page=new TechCare_Ola1(driver);
	page.selectAccount (sCuenta);
	driver.switchTo().defaultContent();
	sleep(3000);
	tech.clickOpcionEnAsset(sLinea, "mis servicios");
	tech.verDetalles();
    tech.clickDiagnosticarServicio("sms", "SMS Saliente", true);
    tech.selectionInconvenient("SMS Emisión a algún destino en particular");
    tech.continuar();
    sleep(5000);
    tech.Verificacion_de_la_posicion_en_el_mapa("no", "Sí", "Av. Cabildo", "No son las antenas", "Si", "Test-X Play", "Sí");
   assertTrue(tech.reclamo());

   
}
	
	
}
