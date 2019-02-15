package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;
import Tests.TestBase.IrA;


public class TechCareOla1A extends TestBase {
	
	private WebDriver driver;
	//private String Cuenta="Marco Polo";
	//private String sCuenta;
	//private String Linea="543416869777";
	
	@BeforeClass(alwaysRun=true)
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
	
	/** CASO REPETIDO
	 * Verifica que aparezca el inconveniente "no puede configurar" luego de diagnosticar el servicio.
	 */
	//@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=12, dataProvider="Tech")
	public void TS94226_CRM_Ola1_Technical_Care_CSR_SVA_Actualización_de_matriz_Servicio_Transferencia_de_llamadas_inconveniente_No_funciona_transferencia_de_llamadas_No_funciona_transferencia_de_llamadas_No_puede_configurar(String sCuenta, String sDni, String sLinea) {
		//------
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
	
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("Transferencia de Llamadas");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		assertEquals(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(1).getText().toLowerCase(),"no puede configurar");
	}

	/**
	 * Verifica que al diagnosticar servicio "voice mail con clave" se muestre la pregunta
	 * "cómo ingreso mi clave"
	 */
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94438_CRM_Ola1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Voice_Mail_con_Clave_e_inconveniente_cómo_ingreso_mi_clave(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("Voice Mail con Clave");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElement(By.cssSelector(".imgItemContainer.ng-scope")).getText().toLowerCase().contains("cómo ingreso mi clave"));
	}
	
	/**
	 * Veririca que se muestre la pregunta "no puedo llamar a un número en particular"
	 */
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS96292_CRM_Ola1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_Barrings_Configurables_por_el_usuario_e_inconveniente_No_puedo_llamar_a_un_numero_en_particular(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("Barrings Configurables por el Usuario");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElement(By.cssSelector(".imgItemContainer.ng-scope")).getText().toLowerCase().contains("no puedo llamar a un número en particular"));
	}
	
	/**
	 * Veririca que se muestre la pregunta "el artículo ofrecido soluciona su inconveniente"
	 */
	@Test(groups= {"TechnicalCare","MisServicios","Ola1"}, priority=4, dataProvider="Tech")
	public void TS94390_CRM_Ola1_Technical_Care_CSR_Mis_Servicios_Visualizacion_de_pregunta(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms entrante");
		sleep(5000);
		page.seleccionarRespuesta("no recibe de un número particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    boolean preguntaFinalDisponible=false;
	    List<WebElement> preguntaFinal=driver.findElements(By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding"));
	    for(WebElement pf:preguntaFinal) {
	    	if(pf.getText().toLowerCase().contains("el artículo ofrecido soluciona su inconveniente")&&pf.isDisplayed()) {
	    		preguntaFinalDisponible=true;
	    		break;
	    	}
	    }
	    assertTrue(preguntaFinalDisponible);
	}
	
	/**
	 * Verifica que al seleccionar NO continue a la siguienete Ventana
	 */
	@Test(groups= {"TechnicalCare","MisServicios","Ola1"}, priority=5, dataProvider="Tech")
	public void TS94392_CRM_Ola1_Technical_Care_CSR_Mis_Servicios_Visualizacion_Documento_Base_de_Conocimiento_Respuesta_NO(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000); 
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms entrante");
		sleep(5000);
		page.seleccionarRespuesta("no recibe de un número particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.BajaryContinuar();
	    sleep(5000);
	    assertTrue(driver.findElement(By.id("NetworkCategory_nextBtn")).isDisplayed());
	}
	
	/**
	 * Verifica que al seleccionar SI, se genere el ticket al final.
	 */
	@Test(groups= {"TechnicalCare","MisServicios","Ola1","filtrado"}, priority=5, dataProvider="Tech")
	public void TS94391_CRM_Ola1_Technical_Care_CSR_Mis_Servicios_Visualizacion_Documento_Base_de_Conocimiento_Respuesta_SI(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms entrante");
		sleep(5000);
		page.seleccionarRespuesta("no recibe de un número particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("si");
	    page.BajaryContinuar();
	    sleep(6000);
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"ClosedCaseKnowledgeBase\"]/div/p/p/strong/strong")).isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=10, dataProvider="Tech")
	public void TS94403_CRM_Ola1_Technical_Care_CSR_SVA_Validacion_Red_OK_Consulta_al_cliente_tiene_señal_NO(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms saliente");
		sleep(10000);//AQUI
		page.seleccionarRespuesta("sms emisión a algún destino en particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.BajaryContinuar();
	    sleep(4000);
	    page.BajaryContinuar();
	    sleep(4000);
	    page.buscarDireccion("Av. Congreso 3940, Buenos Aires, Argentina");
	    sleep(2000);
	    page.seleccionarRespuesta("no son las antenas");
	    sleep(4000);
	    try{page.seleccionarRespuesta("no");assertTrue(true);}
	    catch(org.openqa.selenium.NoSuchElementException a) {assertTrue(false);}   
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=9, dataProvider="Tech")
	public void TS94402_CRM_Ola1_Technical_Care_CSR_SVA_Validacion_Red_OK_Consulta_al_cliente_tiene_señal_SI(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms saliente");
		sleep(5000);
		page.seleccionarRespuesta("sms emisión a algún destino en particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.BajaryContinuar();
	    sleep(4000);
	    page.BajaryContinuar();
	    sleep(4000);
	    page.buscarDireccion("Av. Congreso 3940, Buenos Aires, Argentina");
	    sleep(2000);
	    page.seleccionarRespuesta("no son las antenas");
	    sleep(4000);
	    try{page.seleccionarRespuesta("si");assertTrue(true);}
	    catch(org.openqa.selenium.NoSuchElementException a) {assertTrue(false);}   
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94417_CRM_Ola1_Technical_Care_CSR_SVA_Validaciones_Visualizacion_MMS_Emisión_Cliente_informa_que_no_puede_enviar_Archivo_Imagen_Audio(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(20000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("mms");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		assertEquals(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(0).getText().toLowerCase(),"mms emisión cliente informa que no puede enviar archivo");
		assertEquals(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(1).getText().toLowerCase(),"mms emisión cliente informa que no puede enviar audio");
		assertEquals(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(2).getText().toLowerCase(),"mms emisión cliente informa que no puede enviar imagen");
	}

	@Test(groups= {"TechnicalCare","SVA","Ola1","filtrado"}, priority=8, dataProvider="Tech")
	public void TS94351_CRM_Ola1_Technical_Care_CSR_SVA_Verificacion_de_buscar_la_posicion_en_el_mapa(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms entrante");
		sleep(5000);
		page.seleccionarRespuesta("no recibe");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(5000);
	    page.clickContinuar();
	    sleep(4000);
	    page.buscarDireccion("José Martí 1439, Argentina");
	    sleep(5000);
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"busSearchMap\"]")).isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94412_CRM_Ola1_Technical_Care_CSR_SVA_Visualizacion_SMS_Emision_a_algun_destino(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(20000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms saliente");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
		sleep(5000);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
		sleep(5000);
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(1).getText().toLowerCase().contains("sms emisión a algún destino en particular"));
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94407_CRM_Ola1_Technical_Care_CSR_SVA_Visualizar_SMS_A_FIJO(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms saliente");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(0).getText().toLowerCase().contains("sms a fijo"));
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94466_CRM_Ola1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_SMS_entrante_e_inconveniente_No_recibe_de_un_número_particular(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms entrante");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(0).getText().toLowerCase().contains("no recibe de un número particular"));
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94469_CRM_Ola1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_SMS_saliente_e_inconveniente_SMS_a_fijo(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms saliente");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(0).getText().toLowerCase().contains("sms a fijo"));
	}
	
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=3, dataProvider="Tech")
	public void TS94462_CRM_Ola1_Technical_Care_CSR_SVA_Visualizacion_de_Servicio_e_inconveniente(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms saliente");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"HeadServiceDiagnosis\"]/div")).getText().toLowerCase().contains("sms saliente"));
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(0).isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1","filtrado"}, priority=7, dataProvider="Tech")
	public void TS94372_CRM_Ola1_Technical_Care_CSR_SVA_Verificacion_de_Cobertura_y_Señal_en_el_equipo(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms saliente");
		sleep(5000);
		page.seleccionarRespuesta("sms emisión a algún destino en particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.BajaryContinuar();
	    sleep(4000);
	    page.BajaryContinuar();
	    sleep(4000);
	    page.buscarDireccion("José Martí 1439, Argentina");
	    sleep(2000);
	    page.seleccionarRespuesta("fuera del area de cobertura");
	    sleep(5000);
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"OutOfCoverage\"]/div/p/p/strong")).isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=9, dataProvider="Tech")
	public void TS94329_CRM_Ola1_Technical_Care_CSR_SVA_Seleccion_del_dispositivo_CON_señal(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("InternetXDia");
		sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(4000);
	    page.clickContinuar();
	    sleep(4000);
	    page.buscarDireccion("Av. Congreso 3940, Buenos Aires, Argentina");
	    sleep(2000);
	    page.seleccionarRespuesta("no son las antenas");
	    sleep(4000);
	    try{page.seleccionarRespuesta("si");assertTrue(true);}
	    catch(org.openqa.selenium.NoSuchElementException a) {assertTrue(false);}   
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=10, dataProvider="Tech")
	public void TS94330_CRM_Ola1_Technical_Care_CSR_SVA_Seleccion_del_dispositivo_SIN_señal(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("InternetXDia");
		sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(5000);
	    page.seleccionarPreguntaFinal("Sí");
	    page.clickContinuar();
	    sleep(4000);
	    page.buscarDireccion("Av. Congreso 3940, Buenos Aires, Argentina");
	    sleep(2000);
	    page.seleccionarRespuesta("no son las antenas");
	    sleep(4000);
	    try{page.seleccionarRespuesta("no");assertTrue(true);}
	    catch(org.openqa.selenium.NoSuchElementException a) {assertTrue(false);}   
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=10, dataProvider="Tech")
	public void TS94317_CRM_Ola1_Technical_Care_CSR_SVA_Visualizar_pregunta_Respuesta_NO(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(10000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms saliente");
		sleep(5000);
		page.seleccionarRespuesta("sms emisión a algún destino en particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(4000);
	    page.seleccionarRespuesta("desregistrar");
	    page.clickContinuar();
	    sleep(5000);
	    page.clickContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("sí");
	    page.clickContinuar();
	    sleep(4000);
	    page.seleccionarPreguntaFinal("No, sigue con inconvenientes");
	    sleep(2000);
	    assertTrue(page.getOpcionesFinal(2).isDisplayed()&&page.getOpcionesFinal(3).isDisplayed());
	}
	
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=9, dataProvider="Tech")
	public void TS94316_CRM_Ola1_Technical_Care_CSR_SVA_Visualizar_pregunta_Respuesta_SI(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(20000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(10000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms saliente");
		sleep(5000);
		page.seleccionarRespuesta("sms emisión a algún destino en particular");
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(4000);
	    page.seleccionarRespuesta("desregistrar");
	    page.clickContinuar();
	    sleep(5000);
	    page.clickContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("sí");
	    page.clickContinuar();
	    sleep(4000);
	    page.seleccionarPreguntaFinal("Sí, funciona correctamente");
	    sleep(5000);
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"OperationalServiceMessage\"]/div")).isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1"}, priority=8, dataProvider="Tech")
	public void TS94350_CRM_Ola1_Technical_Care_CSR_SVA_Verificacion_del_ingreso_de_direccion(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("InternetXDia");
		sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(5000);
	    page.clickContinuar();
	    sleep(4000);
	    try {page.setDireccion("av congreso, argentina"); assertTrue(true);}
	    catch(org.openqa.selenium.NoSuchElementException noCampo) {assertTrue(false);}
	}
	
	
	@Test(groups= {"TechnicalCare","SVA","Ola1","filtrado"}, priority=6, dataProvider="Tech")
	public void TS94335_CRM_Ola1_Technical_Care_CSR_SVA_Verificacion_de_desregistro_de_la_linea(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(20000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("SMS","sms saliente");
		sleep(5000);
		page.seleccionarRespuesta("sms emisión a algún destino en particular");
		sleep(5000);
	    page.BajaryContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("no");
	    page.clickContinuar();
	    sleep(5000);
	    page.seleccionarRespuesta("desregistrar");
	    page.clickContinuar();
	    sleep(5000);
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"DeregisterSpeechMessage\"]/div")).isDisplayed());
	}
	
	@Test(groups= {"TechnicalCare","SVA","Ola1","filtrado"}, priority=3, dataProvider="Tech")
	public void TS94478_CRM_Ola1_Technical_Care_CSR_SVA_Validacion_SMS_saliente(String sCuenta, String sDni, String sLinea) {
		TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount(sCuenta);
		driver.switchTo().defaultContent();
		sleep(10000);
		
		page.clickOpcionEnAsset(sLinea, "mis servicios");
		sleep(7000);
		page.clickVerDetalle();
		sleep(5000);
		page.clickDiagnosticarServicio("sms","sms saliente");
		sleep(5000);
		Accounts accPage = new Accounts(driver);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(0).getText().toLowerCase().contains("sms a fijo"));
	    assertTrue(driver.findElements(By.cssSelector(".imgItemContainer.ng-scope")).get(1).getText().toLowerCase().contains("sms emisión a algún destino en particular"));
	}
}




