package Tests;


import static org.testng.Assert.assertTrue;

import java.util.List;

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

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;


public class TechnicalCareCSRMuletoA extends TestBase{
	
private WebDriver driver;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		goInitToConsolaFanF3(driver);
		     
	    CustomerCare cerrar = new CustomerCare(driver);
	    cerrar.cerrarultimapestana();
		
		//Selecciona Cuentas
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel2(driver, "Cuentas");
		
		   
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		//Selecciona la cuenta Adrian Tech de todas las Cuentas
				seleccionCuentaPorNombre(driver, "Adrian Techh");
		//selecciona el campo donde esta la busquedad del imput y Escribe
				searchAndClick(driver, "Gestionar Muleto");
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void after() {
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		CustomerCare cerrar = new CustomerCare(driver);
		cerrar.cerrarultimapestana();
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		CustomerCare cerrar = new CustomerCare(driver);
		//cerrar.cerrarultimapestaña();
		HomeBase homePage = new HomeBase(driver);
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		homePage.selectAppFromMenuByName("Ventas");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.quit();
		sleep(2000);
	}
	
//-------------------------------------------------- Metodos------------------------------------------------------------//	
	public void clickContinuar() {
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement BenBoton = driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
		WebElement continuar= driver.findElement(By.xpath("//*[text() = 'Continuar']"));
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
		continuar.click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		}
		catch(org.openqa.selenium.ElementNotVisibleException a) {
			List <WebElement> continuar2=driver.findElements(By.className("ng-binding"));
			for(WebElement c:continuar2) {
				if(c.getText().contains("Continuar")) {
					c.click();
					break;}}}		
	}
	
	
	public void llenarDatos() {
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select sGenero=new Select(driver.findElement(By.id("Gender")));
		sGenero.selectByVisibleText("Masculino");
		Select sDocumento=new Select(driver.findElement(By.id("DocumentType")));
		sDocumento.selectByVisibleText("DNI");
		driver.findElement(By.id("DocumentNumber")).sendKeys("36686926");
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
//----------------------------------------------------------- TESTs---------------------------------------------------//	
	
	/**En el campo accesorio escribe y luego verifica que se haya escrito
	 * @author Almer
	 * 
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51111_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_contenido_campo_Accesorios() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
	llenarDatos();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
		
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("AccesoriesField")).sendKeys("Esta es una prueba automatizada");
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		boolean check=false;
		WebElement campoAccesorios=driver.findElement(By.id("MuletoDelivered")).
				findElement(By.className("slds-form-element__control")).
				findElement(By.className("ng-binding")).
				findElement(By.tagName("p"));
			//System.out.println(campoAccesorios.getText());
		if(campoAccesorios.getText().contains("Esta es una prueba automatizada"))
				check=true;
		assertTrue(check);
	}
	
	/**
	 * Verifica que al introducir un numero de documento (que no tenga muleto para devolucion), despues de hacerle click en continuar
	 * apareza el mensaje  "No Posee Terminal Para Devolución"
	 * @author Almer
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51117_CRM_Fase_3_Technical_Care_CSR_Muleto_Visualizacion_mensaje_en_caso_de_No_poseer_Muleto() {
		
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Devolucion de Muleto
		driver.findElements(By.className("borderOverlay")).get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	//Click Numero de Documento
		driver.findElements(By.className("borderOverlay")).get(2).click();
		llenarDatos();
		clickContinuar();
		
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("alert-ok-button")).click();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean check=false;
		List<WebElement> a=driver.findElements(By.className("error"));
		for(WebElement b:a)
		//System.out.println(b.getText());
			if(b.getText().contains("No Posee Terminal Para Devolución."))
				check=true;
		
		assertTrue(check);
	}
	
	/**
	 * Ingresa a la vista 360 va a entrega de muleto, llena los campos, selecciona el muleto, y en el campo accesorio no escriber.
	 * se verifica que se pueda continuar sin escribir en el campo accesorio
	 * @author Almer
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51109_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_si_no_se_ingresa_un_texto_puede_continuar_con_la_gestion() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
	llenarDatos();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//driver.findElement(By.id("AccesoriesField")).sendKeys("Esta es una prueba automatizada");
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
			assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.TechCare-DownloadPDF-Btn")).isDisplayed());}
		catch(org.openqa.selenium.ElementNotVisibleException e){assertTrue(false);}		
	}

	/**
	 *Se Verifica que en Entrega de muleto se pueda seleccionar un  muleto(Dispositivos) de la lista.
	 * @author Almer
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51097_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_seleccion_de_uNO_del_listado() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
	llenarDatos();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		
		try {
			listadoDeTerminales.get(1).click();
			assertTrue(true);
		}
		catch(Exception e) {assertTrue(false);}
	}
	
	/**
	 * Se verifica que al seleccionar "Numero de Caso" se pueda ingresar el numero de caso.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51114_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_ingreso_del_NuMERO_de_Caso_para_Devolucion_de_muleto() {
		
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Devolucion de Muleto
		driver.findElements(By.className("borderOverlay")).get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	//Click Numero de caso
		driver.findElements(By.className("borderOverlay")).get(3).click();
		try{
		driver.findElement(By.id("InputCase")).sendKeys("00006139");
		assertTrue(true);}
		catch(Exception e) {assertTrue(false);}
	}
	
	/**
	 * Se verifica que al ingresar un dni que NO este en blacklist, se pueda continuar con la gestion.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51088_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_Blacklist_negativo() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
	llenarDatos();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
			clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			clickContinuar();
			assertTrue(true);
			}
		catch(Exception e) {assertTrue(false);}
	}
	
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51110_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_si_no_se_ingresa_un_texto_puede_continuar_con_la_gestion() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
	llenarDatos();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
			assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.TechCare-DownloadPDF-Btn")).isDisplayed());}
		catch(org.openqa.selenium.ElementNotVisibleException e){assertTrue(false);}		
	}
	
//---------------------------------------------------------- Fase 3.1------------------------------------------------------//

	/**
	 * Ingresa a devolucion de muleto, llena los datos segun el numero de caso, y verifica que se pueda seleccionar una opcion
	 * en el select "TIPO DE DEVOLUCIOM"
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51121_CRM_Fase_3_Technical_Care_CSR_Muleto_Seleccion_de_uN_dato_de_la_lista() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(1).click();
		clickContinuar();
		//Click Numero de caso
		driver.findElements(By.className("borderOverlay")).get(3).click();
		driver.findElement(By.id("InputCase")).sendKeys("00006139");
		sleep(1000);
		clickContinuar();
		sleep(4000);
		WebElement sTD=driver.findElement(By.id("DevolutionType"));
		Select tipoDevolucion=new Select(sTD);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+sTD.getLocation().y+")");
		sleep(500);
		try {
			tipoDevolucion.selectByIndex(1);
			assertTrue(true);
		}catch(Exception e) {System.out.println("No se puede seleccionar tipo de devolucion");assertTrue(false);}
	}
	/**
	 * Selecciona entrega de muleto, ingresa el dni, pasa las validaciones del cliente, selecciona un terminal y verifica que
	 * se pueda ingresar texto alfanumerico en el campo accesorios.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51102_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_el_ingreso_de_un_texto_ALFANuMERICO() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		llenarDatos();
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("AccesoriesField")).sendKeys("3sta 3s una pru3ba au7oma7izada");
		clickContinuar();
		//Verifico que la opcion "descargar", posterior a la pantalla de campo. este disponible.
		assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.TechCare-DownloadPDF-Btn")).isDisplayed());
	}
	/**
	 * Selecciona entrega de muleto, ingresa el dni, pasa las validaciones del cliente, selecciona un terminal y verifica que
	 * se pueda ingresar solo texto en el campo accesorios.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51103_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_el_ingreso_de_un_texto_de_LETRAS() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		llenarDatos();
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("AccesoriesField")).sendKeys("abcdefghijklmnñopqrstuvwxyz");
		clickContinuar();
		//Verifico que la opcion "descargar", posterior a la pantalla de campo. este disponible.
		assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.TechCare-DownloadPDF-Btn")).isDisplayed());
	}
	
	/**
	 * Selecciona entrega de muleto, ingresa el dni, pasa las validaciones del cliente, selecciona un terminal y verifica que
	 * se pueda ingresar solo Numeros en el campo accesorios.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51104_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_el_ingreso_de_un_texto_DE_NuMEROS() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		llenarDatos();
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("AccesoriesField")).sendKeys("0123456789");
		clickContinuar();
		//Verifico que la opcion "descargar", posterior a la pantalla de campo. este disponible.
		assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.TechCare-DownloadPDF-Btn")).isDisplayed());
	}
	
	/**
	 * Selecciona entrega de muleto, ingresa el dni, pasa las validaciones del cliente, selecciona un terminal y verifica que
	 * se pueda ingresar un texto menor o igual a 255 caracteres en el campo accesorios.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51106_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_el_ingreso_de_un_texto_limitado_a_255_caracteres() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		llenarDatos();
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("AccesoriesField")).sendKeys("Roberto is a veteran who is characterised by orderliness and a firm belief in the value of control. He runs his own hardware store accordingly. If a supplier sells him boxes with 100 screws each, he counts all the screws and files asssssssssssaaaaaaaaazaa");
		clickContinuar();
		//Verifico que la opcion "descargar", posterior a la pantalla de campo. este disponible.
		assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.TechCare-DownloadPDF-Btn")).isDisplayed());
	}
	
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51119_CRM_Fase_3_Technical_Care_CSR_Muleto_Visualizacion_datos_de_Terminal_en_devolucion_de_muleto() {
	
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(1).click();
		clickContinuar();
		//Click Numero de caso
		driver.findElements(By.className("borderOverlay")).get(3).click();
		driver.findElement(By.id("InputCase")).sendKeys("00009014");
		sleep(1000);
		clickContinuar();
		sleep(4000);
	}
	
	/**
	 * Se Verifica que el campo(Aceesorios) para ingresar texto, este disponible.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51101_CRM_Fase_3_Technical_Care_CSR_Muleto_Visualizacion_de_campo_para_ingresar_texto() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		llenarDatos();
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> listadoDeTerminales=driver.findElements(By.className("slds-radio--faux"));
		listadoDeTerminales.get(1).click();
		clickContinuar();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(driver.findElement(By.id("AccesoriesField")).isDisplayed());
	}
	
	/**
	 * Se Verifica que La pantalla Entrega-Devolucion de Muleto este disponble
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51138_CRM_Fase_3_Technical_Care_CSR_Muleto_Visualizacion_de_campo_para_ingresar_texto() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		assertTrue(driver.findElements(By.className("borderOverlay")).get(0).isDisplayed());//Entrega
		assertTrue(driver.findElements(By.className("borderOverlay")).get(1).isDisplayed());//Devolucion
	}
	
	/**
	 * Se Verifica que se pueda Ingresar DNI en la entrega de Muleto
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51074_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_de_ingreso_del_DNI_para_entrega_de_muleto() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		sleep(3000);
		llenarDatos();
		sleep(1000);
		clickContinuar();
		sleep(3000);
		System.out.println(driver.findElement(By.xpath("//*[@id=\"ClientInfoList\"]/div/p/p[10]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"ClientInfoList\"]/div/p/p[10]")).getText().endsWith("36686926"));
	}
	
	/**
	 * Se verifica que se puede seleccionar devolucion de muleto
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51072_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_de_la_seleccion_DEVOLuCION_DE_MuLETO() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(1).click();
		clickContinuar();
		sleep(3000);
		assertTrue(driver.findElements(By.className("borderOverlay")).get(2).isDisplayed());//Verifica que el campo siguiente a la pantalla este disponible
	}
	
	/**
	 * Se verifica que se puede seleccionar Entrega de muleto
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51071_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_de_la_seleccion_ENTREGA_DE_MuLETO() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		sleep(3000);
		assertTrue(driver.findElement(By.id("DocumentNumber")).isDisplayed());//Verifica que el campo Genero de la pantalla siguiente este disponible
	}
	
	/**
	 * Se verifica que en el listado de muleto aparezcan los campos
	 * -nmu -Descripcion -marca -cantidad en stock
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51096_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_informacion_del_listado_de_terminales_muletos() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		sleep(3000);
		llenarDatos();
		sleep(1000);
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(2000);
		WebElement campos=driver.findElement(By.xpath("//*[@id=\"TerminalsSelection\"]/div/ng-include/div/table/thead/tr"));
		//System.out.println(campos.getText());
		if(campos.getText().toLowerCase().contains("nmu")&&campos.getText().toLowerCase().contains("descripción")
				&&campos.getText().toLowerCase().contains("marca")&&campos.getText().toLowerCase().contains("cantidad en stock")) {
			assertTrue(true);
		}else
			assertTrue(false);
	}
	
	/**
	 * se verifica q en entrega de muleto, si el cliente no posee muelto pendiente para devolucion, se pueda continuar.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51091_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_Muleto_pendiente_de_entrega_NO() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		sleep(3000);
		llenarDatos();
		sleep(1000);
		clickContinuar();
		sleep(3000);
		WebElement NO=driver.findElement(By.xpath("//*[@id=\"ValidationResults\"]/div/p/p[3]"));
		boolean noposee=false;
		if(NO.getText().toLowerCase().endsWith("no"))
			noposee=true;
		clickContinuar();
		sleep(3000);
		assertTrue(driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--cell-buffer.vlc-slds-table")).isDisplayed()&&noposee);
	}
	
	/**
	 * Se verifica que en devolucion, no se posea un Muleto para devolucion
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51116_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_que_NO_POSEE_un_muleto_para_devolucion() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(1).click();
		clickContinuar();
		sleep(3000);
		driver.findElements(By.className("borderOverlay")).get(2).click();
		sleep(1000);
		llenarDatos();
		sleep(1000);
		clickContinuar();
		sleep(1000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(1000);
		boolean check=false;
		List<WebElement> a=driver.findElements(By.className("error"));
		for(WebElement b:a)
		//System.out.println(b.getText());
			if(b.getText().contains("No Posee Terminal Para Devolución."))
				check=true;
		
		assertTrue(check);
	}
	
	/**
	 * Se Verifica que no se pueda seleccionar mas de dos muletos en ka lista
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51098_CRM_Fase_3_Technical_Care_CSR_Muleto_Verificacion_que_NO_pueda_seleccionarse_mas_de_uN_muleto() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		sleep(3000);
		llenarDatos();
		sleep(1000);
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> Celda=driver.findElements(By.xpath("//*[@id=\"TerminalsSelection\"]/div/ng-include/div/table/tbody/tr"));
		Celda.get(0).findElement(By.className("slds-radio--faux")).click();
		sleep(100);
		Celda.get(1).findElement(By.className("slds-radio--faux")).click();
		//System.out.println(Celda.get(0).findElement(By.xpath("//th/label/input")).getAttribute("class"));
		assertTrue(Celda.get(0).findElement(By.xpath("//th/label/input")).getAttribute("class").endsWith("ng-touched"));
	}
	
	/**
	 * Se verifica que se muestre el listado de muletos.
	 */
	@Test(groups= {"Fase3","TechnicalCare","Muleto","Ola3"})
	public void TS51095_CRM_Fase_3_Technical_Care_CSR_Muleto_Visualizacion_estado_de_terminales() {
	//Cambio al Frame
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.className("borderOverlay")));
	//Click Entrega de Muleto
		driver.findElements(By.className("borderOverlay")).get(0).click();
		clickContinuar();
		sleep(3000);
		llenarDatos();
		sleep(1000);
		clickContinuar();
		sleep(3000);
		clickContinuar();
		sleep(3000);
		List <WebElement> Celda=driver.findElements(By.xpath("//*[@id=\"TerminalsSelection\"]/div/ng-include/div/table/tbody/tr"));
		assertTrue(!(Celda.isEmpty()));
	}
}
