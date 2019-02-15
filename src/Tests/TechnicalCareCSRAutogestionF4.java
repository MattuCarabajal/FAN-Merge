package Tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;

public class TechnicalCareCSRAutogestionF4 extends TestBase{
	
	private WebDriver driver;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginMarcela(driver);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goInitToConsolaFanF3(driver);
	    try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     //Alerta Aparece Ocasionalmente
	       try {
				driver.switchTo().alert().accept();
				driver.switchTo().defaultContent();
			}catch(org.openqa.selenium.NoAlertPresentException e) {}

       CustomerCare cerrar = new CustomerCare(driver);
       cerrar.cerrarultimapestana();		
       sleep(4000);
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		//Selecciona la cuenta Adrian Tech de todas las Cuentas
		seleccionCuentaPorNombre(driver, "Adrian Techh");
		sleep(3000);
		driver.switchTo().defaultContent();
		//selecciona el campo donde esta la busquedad escribe y busca
		searchAndClick(driver, "Diagnóstico de Autogestión");
		sleep(1000);
	}
	
	@AfterMethod(alwaysRun=true)
	public void after() {
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent(); 
		CustomerCare cerrar = new CustomerCare(driver);
	    cerrar.cerrarultimapestana();
	    driver.switchTo().defaultContent(); 
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
		HomeBase homePage = new HomeBase(driver);
		sleep(1000);
		homePage.selectAppFromMenuByName("Ventas");
		sleep(2000);
		driver.quit();
		sleep(2000);
	}
//--------------------------------------Metodo Seleccion-------------------------------------------------------------//
	
	public void elegirOpciones(String Canal, String Servicio, String MotivoDelIncoveniente) {
		sleep(5000);
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText(Canal);
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText(Servicio);
		Select sMotivo=new Select(driver.findElement(By.id("MotiveSelection")));
		sMotivo.selectByVisibleText(MotivoDelIncoveniente);
		sleep(2000);
	}
	
	public void continuar() {
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement BenBoton = driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
		sleep(500);
		try {
		driver.findElement(By.id("SelfManagementStep_nextBtn")).click();
		}
		catch(org.openqa.selenium.ElementNotVisibleException e) {
			driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();
		}
		sleep(3000);
	}
	
	public void saltarPantallas() {
		sleep(3000);
		if(driver.findElement(By.cssSelector(".imgItemContainer.ng-scope")).isDisplayed()) {
			sleep(1000);
			driver.findElements(By.className("borderOverlay")).get(1).click();
			continuar();
			sleep(3000);
		}
	}
	
	public String getCaso() {
		String nCaso="";
		if(driver.findElement(By.xpath("//*[@id=\"CreatedClosedCaseText\"]/div/p/p/strong[1]")).isDisplayed())
			nCaso=driver.findElement(By.xpath("//*[@id=\"CreatedClosedCaseText\"]/div/p/p/strong[1]")).getText();
		else {
			if(driver.findElement(By.xpath("//*[@id=\"SimilCaseInformation\"]/div/p/p[3]/strong[1]")).isDisplayed())
				nCaso=driver.findElement(By.xpath("//*[@id=\"SimilCaseInformation\"]/div/p/p[3]/strong[1]")).getText();
			else
				System.out.println("No se encuentra el numero de caso\nNumero encontrado: "+nCaso);
		}
		return nCaso;
	}
	
	public void buscarCaso(String nCaso) {
		driver.switchTo().defaultContent();
		sleep(1000);
		WebElement Buscador=driver.findElement(By.xpath("//input[@id='phSearchInput']"));
		Buscador.sendKeys(nCaso);
		sleep(2000);
		try {
		driver.findElement(By.className("autoCompleteRowLink")).click();
		sleep(2000);
		Buscador.clear();}
		catch(org.openqa.selenium.NoSuchElementException e) {
			sleep(7000);
		Buscador.submit();
		sleep(1000);
		Buscador.clear();
		sleep(2000);
		BasePage cambioFrameByID=new BasePage();
		int i=0;
		
		while(i<3) {
			try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("searchResultsWarningMessageBox")));
			if(driver.findElement(By.id("searchResultsWarningMessageBox")).isDisplayed()) {
				driver.navigate().refresh();
				sleep(2000);
				i++;
				//System.out.println(i);
				}
			}
			catch (java.lang.NullPointerException a){
				sleep(3000);
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("searchPageHolderDiv")));
				i=4;
				System.out.println("Segundo Catch");
			}
		}
		sleep(2000);
		WebElement Caso=driver.findElement(By.cssSelector(".listRelatedObject.caseBlock")).
				findElement(By.cssSelector(".bPageBlock.brandSecondaryBrd.secondaryPalette")).
				findElement(By.className("pbBody")).findElement(By.className("list")).
				findElements(By.tagName("tr")).get(1).
				findElement(By.tagName("th")).findElement(By.tagName("a"));
		Caso.click();
		}
		sleep(2000);
	}
	
	public void irDetalleDeCaso() {
		sleep(2000);
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("optionContainer")));
		sleep(1000);
		List<WebElement> opciones = driver.findElement(By.className("optionContainer")).findElements(By.className("optionLabel"));
		for(WebElement op:opciones) {
			//System.out.println(op.getText());
			if(op.getText().toLowerCase().startsWith("detalles de vista de caso"))
				op.click();
		}
	}
	
	public boolean verificarServiceOwner(String servicio) {
		sleep(3000);
		//System.out.println(driver.findElement(By.xpath("//*[@id=\"00Nc0000002Ja0S_ileinner\"]")).getText());
		if(driver.findElement(By.xpath("//*[@id=\"00Nc0000002Ja0S_ileinner\"]")).getText().contains(servicio))
			return true;
		else
			return false;
	}
	
//------------------------------------- Autogestion Fase4------------------------------------------------------------//
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73819_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_533_Pauta_On_Line_corresponde_a_TP() {
		String caso;
		elegirOpciones("Asteriscos TP","*533 (Pauta On Line)","Inconv Recarga delivery");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));
	}
	
	//Revisar
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73822_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_567_SMS_mas_Push_corresponde_a_TP() {
		String caso;
		elegirOpciones("Asteriscos TP","*567 (SMS + Push)","Inconv Recarga delivery");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73815_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_servicio_asterisco_762_ROAMERS_corresponde_a_TP() {
		String caso;
		elegirOpciones("Asteriscos TP","*762 (ROAMERS)","Inconv Recarga delivery");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73870_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_0800_888_7382_Repro_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-7382 (Repro)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73865_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0531_Tienda_Planes_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0531 (Tienda Planes)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73864_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0533_Pautas_Online_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0533 (Pautas Online)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73863_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0534_EMail_Marketing_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0534 (E-Mail Marketing)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}

	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73862_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0536_Pauta_Online_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0536 (Pauta Online)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73861_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_00538_SMS_Push_Arnet_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0538 (SMS Push Arnet)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73880_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0539_Home_Personal_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0539 (Home Personal)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73878_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0561_Facebook_Arnet_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0561 (Facebook Arnet)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73879_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0567_SMS_Push_Personal_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0567 (SMS Push Personal)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73867_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_0800_0800_Clientes_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-0800 (0800 Clientes)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73859_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_4929_Adultos_Mayores_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-4929 (Adultos Mayores)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73860_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_444_6673_Nordelta_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-444-6673 (Nordelta)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73868_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_555_0018_Ventas_desde_la_Web_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-555-0018 (Ventas desde la Web)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	//aqui
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73858_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_555_0800_0035_GPON_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-555-0035 (GPON)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73874_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_555_0112_Tarjetas_de_Telecom_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-555-0112 (Tarjetas de Telecom)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73857_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_555_4532_Medios_Digitales_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-555-4532 (Medios Digitales)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73873_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_555_9999_Cliente_Arnet_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-555-9999 (Cliente Arnet)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73875_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_777_7246_Pago_con_TC_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-777-7246 (Pago con TC)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73876_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_7770328_Fax_Server_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-7770328 (Fax Server)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73871_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_888_0112_Cliente_112_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-0112 (Cliente 112)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73855_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_888_888_0800_Executive_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-0800 (Executive)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73854_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_888_888_1010_Convergente_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-1010 (Convergente)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73869_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_888_1010_Convergente4422_CUIT_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-4422 (CUIT)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73853_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_888_7382_Activaciones_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-7382 (Activaciones)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73856_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_0800_servicio_0800_888_8872_Medios_Impresos_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("0800","0800-888-8872 (Medios Impresos)","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73839_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Otros_Asteriscos_servicio_asterisco_Otros_corresponde_a_un_servicio_de_Terceros() {
		String caso;
		elegirOpciones("Otros Asteriscos","Otros","Llamada fallo");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Terceros"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73888_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Servicios_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Servicios","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73886_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Promociones_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Promociones","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73884_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Planes_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Planes","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73893_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Personal_Black_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Personal Black","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73885_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Packs_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Packs","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73892_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Internacional_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Internacional","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73894_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_Web_servicio_Contacto_CHAT_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WEB","Contacto (CHAT)","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73904_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_WAP_servicio_Estado_de_la_cuenta_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WAP","Estado de la cuenta","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73908_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_WAP_servicio_Email_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("WAP","Email","Información Incompleta");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	//Pertenece a Terceros por eso falla
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73922_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_Otros_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("USSD","Otros","Tono ocupado");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73912_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_724_IPago_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("USSD","*724# (IPago)","No Interactúa");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
	
	@Test(groups= {"Fase4","TechnicalCare", "Autogestion","Ola2"})
	public void TS73913_CRM_Fase_4_Technical_Care_CSR_Autogestion_Verificacion_de_que_la_autogestion_del_Canal_USSD_servicio_234_Nominatividad_corresponde_a_un_servicio_de_Telecom() {
		String caso;
		elegirOpciones("USSD","*234# Nominatividad","No Interactúa");
		continuar();
		saltarPantallas();
		sleep(4000);
		caso=getCaso();
		buscarCaso(caso);
		irDetalleDeCaso();
		assertTrue(verificarServiceOwner("Telecom"));	
	}
}

