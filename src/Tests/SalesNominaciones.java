package Tests;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.SalesBase;
import Pages.setConexion;

public class SalesNominaciones extends TestBase{

	private WebDriver driver;
	protected String perfil = "call";

	@BeforeClass(alwaysRun=true)
	public void Init2() {
		CustomerCare cc = new CustomerCare(driver);
		driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}		
			 loginOfCom(driver);  
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		HomeBase homePage = new HomeBase(driver);
		SalesBase SB = new SalesBase(driver);
		sleep(9000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(18000);
		sleep(10000);
		try{
			SB.cerrarPestaniaGestion(driver);}
		catch(Exception ex1) {
		}
		goToLeftPanel2(driver, "Inicio");
		sleep(5000);
		
	}
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame 2.");
			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		
		sleep(25000);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.id("SearchClientDocumentNumber")));
	}
	
	@AfterMethod(alwaysRun=true)
		public void deslogin(){
			sleep(2000);
			SalesBase SB = new SalesBase(driver);
			driver.switchTo().defaultContent();
			sleep(6000);
			SB.cerrarPestaniaGestion(driver);
			
			sleep(5000);

		}
	
	@AfterClass(alwaysRun=true)
	public void Exit() {
		driver.quit();
		sleep(2000);
	}
	
	@Test(groups={"Sales", "Nominacion","Ola1","filtrado"}, dataProvider="SalesCuentaBolsa")  //si 215 213 078 135 094 114 119 118 157
	  public void TS95215_Nominacion_Argentino_Nominar_personas_mayores_a_16_anios_cliente_mayor_de_edad_con_linea_existente_plan_repro(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(34).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(34).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(10000);
		perfil = "call";
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		boolean b = false;
		contact.searchContact2("DNI", sDni, sLinea);
		sleep(6000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		List<WebElement> vali = driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"));
		for(WebElement v : vali){
			if(v.getText().toLowerCase().contains("validaci\u00f3n de identidad")){
				b = true;
				System.out.println(v.getText());
			}
		}
		Assert.assertTrue(b);
	}
	
	@Test(groups = {"Sales", "Nominacion","Ola1"}, dataProvider="SalesCuentaBolsa") 
	public void TS95213_Nominacion_Argentino_Nominar_personas_mayores_a_16_anios_cliente_mayor_de_edad_sin_linea_existente_plan_repro(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(33).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(33).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(10000);
		perfil = "call";
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		boolean b = false;
		sleep(5000);
		contact.searchContact2("DNI", sDni, sLinea);
		sleep(6000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		List<WebElement> vali = driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"));
		for(WebElement v : vali){
			if(v.getText().toLowerCase().contains("validaci\u00f3n de identidad")){
				b = true;
				System.out.println(v.getText());
			}
		}
		Assert.assertTrue(b);
	}
	
	
	//@Test(groups = "Sales")//NO
	public void TS95214_Nominacion_Argentino_Nominar_personas_mayores_a_16_anios_cliente_menor_de_edad_sin_linea_existente_plan_repro(){
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		boolean b = false;
		contact.searchContact("DNI", "16000001", "femenino");
		sleep(6000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("menoredad@gmail.com");
		driver.findElement(By.id("FirstName")).sendKeys("menor");
		driver.findElement(By.id("LastName")).sendKeys("edad");
		driver.findElement(By.id("Birthdate")).sendKeys("04/07/2017");
		List<WebElement> erro = driver.findElements(By.cssSelector(".message.description.ng-binding.ng-scope"));
		for(WebElement e : erro){
			if(e.getText().toLowerCase().contains("fecha de nacimiento inv\u00e1lida")){
				e.isDisplayed();
				//System.out.println(e.getText());
				b=true;
			}
		}
		Assert.assertTrue(b);
	}
	
	//@Test(groups = "Sales")
	public void TS95076_Nominacion_Argentino_Validar_cantidad_de_lineas(){
		driver.navigate().back();
		SalesBase SB = new SalesBase(driver);
		sleep(3000);
		SB.BuscarCuenta("DNI", "10000019");
		WebElement lis = driver.findElement(By.id("tab-scoped-2")).findElement(By.tagName("div")).findElement(By.tagName("tbody"));
		System.out.println(lis.getSize());
	}
	@Test(groups ={ "Sales", "Nominacion","Ola1","filtrado"}, dataProvider="SalesCuentaBolsa")//si
	public void TS95078_Nominacion_Argentino_Validar_metodo_Ident_por_DNI(String sCuenta, String sDni, String sLinea){
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		SalesBase SB = new SalesBase(driver);
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(37).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(37).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(10000);
		perfil = "call";
		contact.searchContact2("DNI", sDni, sLinea);
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).click();
		sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"EmailSelectableItems\"]/div/ng-include/div/ng-form/div[1]/div[1]/input")).sendKeys("asdasd@gmail.com");
		driver.findElement(By.id("Contact_nextBtn")).click();
		sleep(7000);
		List <WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : valdni) {
			if (x.getText().toLowerCase().contains("validaci\u00f3n por documento de identidad")) {
				x.click();
				break;
			}
		}
		driver.findElement(By.id("ValidationMethod_nextBtn")).click();
		sleep(7000);
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(7000);
		boolean b = false;
		List<WebElement> vali = driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"));
		for(WebElement v : vali){
			if(v.getText().toLowerCase().contains("datos de la cuenta")){
				b = true;
				System.out.println(v.getText());
			}
		}
		Assert.assertTrue(b);
	}
	
	//@Test(groups = "Sales")
	public void TS76061_SalesCPQ_Nominacion_Argentino_Verificar_Formulario_De_Documentacion(){
		boolean a= false;
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact("DNI", "10000018", "femenino");
		sleep(6000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		SB.seleccionarMetodoValidacion("DOC");
	}
	
	@Test(groups={"Sales","Nominacion","Ola1"}, dataProvider="SalesCuentaBolsa")
	public void TS95135_Nominacion_Argentino_Verificar_solicitud_de_datos_para_la_nominacion(String sCuenta, String sDni, String sLinea) {
		SalesBase SB = new SalesBase(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(32).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(32).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(10000);
		perfil = "call";
		boolean a = false;
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("g\u00e9nero")) {
				a = true;
			}
		}
		Assert.assertTrue(driver.findElement(By.id("DocumentTypeSearch")).isEnabled());
		Assert.assertTrue(driver.findElement(By.id("DocumentInputSearch")).isEnabled());
		Assert.assertTrue(a);
	}
	
	//***************************************************************************************************************************
	@Test(groups = {"Sales", "Nominacion","Ola1","filtrado"}, dataProvider="SalesCuentaBolsa") 
	public void TS95140_Nominacion_Argentino_Verificar_creacion_de_la_cuenta(String sCuenta, String sDni, String sLinea) { 
		SalesBase SB = new SalesBase(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(31).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(31).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("DNI", sDni, sLinea);
		if(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().contains("email"))
			driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		BasePage bp = new BasePage(driver);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		SB.Crear_DomicilioLegal("Buenos Aires", "aba", "falsa", "", "1000", "", "", "1549");
		sleep(38000);
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		System.out.println("tam ="+element.size());
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				//System.out.println(x.getText());
			}
		}
		Assert.assertTrue(a);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		
		
	}
	
	//@Test(groups = "Sales")
	public void TS95415_Nominacion_General_Verificar_envio_de_SMS_Nomi_Exitosa() {
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact("DNI", "10000019", "masculino");
		contact.ingresarMail("asdads@gmail.com", "si");
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		List <WebElement> sms = driver.findElements(By.cssSelector(".slds-form-element__control.ng-scope"));
		boolean a = false;
		for (WebElement x : sms) {
			if (x.getText().contains("SMS")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	
	
	@Test(groups={"Sales","Nominacion","Ola1"}, dataProvider="SalesCuentaBolsa")
	public void TS95075_SalesCPQ_Nominacion_Argentino_Verificar_Datos_Para_Nominar_Cliente_Existente(String sCuenta, String sDni, String sLinea){
		ContactSearch contact = new ContactSearch(driver);
		SalesBase SB = new SalesBase(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(30).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(30).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		contact.searchContact2("DNI", sDni, sLinea);
		sleep(6000);
		assertTrue(!driver.findElement(By.id("FirstName")).getAttribute("value").isEmpty());
		
	}
	//***********************************************************************************************************************
	@Test(groups={"Sales","Nominacion","Ola1","filtrado"},dataProvider="SalesPasaporteBolsa")
	public void TS95094_SalesCPQ_Nominacion_Extranjero_Verificar_Confirmacion_Exitosa(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(41).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(41).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		contact.searchContact2("Pasaporte", sDni , sLinea);
		sleep(6000);
		if (driver.findElement(By.id("FirstName")).getAttribute("value").isEmpty()) {
			driver.findElement(By.id("FirstName")).sendKeys("Malan");
			driver.findElement(By.id("LastName")).sendKeys("Faretto");
			driver.findElement(By.id("Birthdate")).sendKeys("30/06/1980");
		}
		if(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().contains("email"))
			driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		
		driver.findElement(By.id("PermanencyDueDate")).sendKeys("30/08/2018");
		sleep(3000);
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		try {CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		}catch(Exception ex1) {}
		sleep(5000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		BasePage bp = new BasePage(driver);
		sleep(5000);
		bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
		SB.Crear_DomicilioLegal("Buenos Aires", "aba", "falsa", "", "1000", "", "", "1549");
		sleep(10000);
		directory = new File("form.pdf");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		
		contact.subirformulario(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(45000);
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		Assert.assertTrue(a);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
	}
	
	@Test(groups={"Sales","Nominacion","Ola1","filtrado"}, dataProvider="SalesPasaporteBolsa")
	public void TS95114_SalesCPQ_Nominacion_Extranjero_Verificar_Datos_Nominar_Cliente_Extranjero(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(28).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(28).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		assertTrue((driver.findElement(By.id("DocumentTypeSearch")).isEnabled()));
		assertTrue((driver.findElement(By.cssSelector(".slds-select_container.vlc-control-wrapper.vlc-slds__border.vlc-slds__border--primary")).findElement(By.tagName("label")).getText().contains("TIPO DE DOCUMENTO")));
		assertTrue((driver.findElement(By.id("DocumentInputSearch")).isEnabled())&&(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.ng-pristine.ng-scope.ng-valid-pattern.ng-invalid.ng-invalid-required.ng-valid-minlength.ng-valid-maxlength")).findElement(By.tagName("label")).getText().contains("DOCUMENTO")));
		assertTrue(driver.findElement(By.id("GenderSearch|0")).isEnabled()&&(driver.findElement(By.id("GenderSearch|0")).findElement(By.tagName("label")).getText().contains("G\u00e9nero")));
	}
	
	@Test(groups={"Sales","Nominacion","Ola1"}, dataProvider="SalesPasaporteBolsa")
	public void TS95118_SalesCPQ_Nominacion_Extranjero_Verificar_Formato_De_Fecha_PlazoPermanencia(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(27).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(27).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		contact.searchContact2("Pasaporte", sDni, sLinea);
		sleep(6000);
		if (driver.findElement(By.id("FirstName")).getAttribute("value").isEmpty()) {
			driver.findElement(By.id("FirstName")).sendKeys("Malan");
			driver.findElement(By.id("LastName")).sendKeys("Faretto");
			driver.findElement(By.id("Birthdate")).sendKeys("30/08/1980");
		}
		if(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().contains("email"))
			driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		
		
		driver.findElement(By.id("PermanencyDueDate")).sendKeys("30/06/2021");
		assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().contains("La permanencia no puede ser mayor a 2 a\u00f1os a partir de la fecha o menor a la fecha actual"));
		sleep(1000);
		driver.findElement(By.id("PermanencyDueDate")).clear();
		driver.findElement(By.id("PermanencyDueDate")).sendKeys("30/08/2018");
		//driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(7000);
		assertTrue(driver.findElement(By.id("ValidationMethod_nextBtn")).isEnabled());
	}
	
	@Test(groups={"Sales","Nominacion","Ola1"}, dataProvider="SalesPasaporteBolsa")  //**************************** VER ***********************************//
	public void TS95116_SalesCPQ_Nominacion_Extranjero_Verificar_Blacklist_Cliente_Existente(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		sleep(5000);
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(26).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(26).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		contact.searchContact2("Pasaporte", "312313214", "femenino");
		//contact.searchContact("Pasaporte", "312313214","");
		sleep(10000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		driver.findElement(By.id("PermanencyDueDate")).sendKeys("30/08/2018");
		//driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		Assert.assertTrue(driver.findElement(By.id("Contact_nextBtn")).isDisplayed());
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		try {CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));}
		catch(Exception ex1) {}
		sleep(7000);
		Assert.assertFalse(driver.findElement(By.id("ValidationMethod_nextBtn")).isDisplayed());
		/*contact.tipoValidacion("documento");
		contact.subirArchivo("C:\\Users\\Sofia Chardin\\Desktop\\DNI.jpg", "si");
		//CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		Assert.assertTrue(false);*/
		
	}
	@Test(groups={"Sales","Nominacion","Ola1","filtrado"}, dataProvider="SalesCuentaBolsa")
	public void TS95288_SalesCPQ_Nominacion_Argentino_Verificar_Flujo_De_Nominacion_Arg_Telefonico(String sCuenta, String sDni, String sLinea){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		//try {
			SB.DesloguearLoguear("call", 4);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			HomeBase homePage = new HomeBase(driver);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    /*String a = driver.findElement(By.id("tsidLabel")).getText(); 
		    if (a.contains("Ventas")){}
		    else {
		    	homePage.switchAppsMenu();
		    	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    	homePage.selectAppFromMenuByName("Ventas");
		    	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}            
		    }*/
		    driver.findElement(By.xpath("//a[@href=\'https://crm--sit--c.cs14.visual.force.com/apex/taClientSearch']")).click();		
		    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
			SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
			WebElement cli = driver.findElement(By.id("tab-scoped-1"));
			if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
				cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
			}
			sleep(3000);
			WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(25).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
			System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(25).findElements(By.tagName("td")).get(1).getText());
			cua.click();
			sleep(13000);
			perfil = "call";
			contact.searchContact2("DNI", sDni , sLinea);
			sleep(8000);		
			driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
			CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
			sleep(7000);
			sleep(5000);
			contact.tipoValidacion("documento");
			sleep(18000);
			File directory = new File("Dni.jpg");
			contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
			BasePage bp = new BasePage(driver);
			//driver.findElement(By.id("FormUpload_nextBtn")).click();
			sleep(8000);
			bp.setSimpleDropdown(driver.findElement(By.id("ImpositiveCondition")), "IVA Consumidor Final");
			SB.Crear_DomicilioLegal("Buenos Aires", "aba", "falsa", "", "1000", "", "", "1549");
			sleep(20000);
			//contact.subirformulario("C:\\Users\\florangel\\Downloads\\form.pdf", "si");
			//sleep(8000);
			SB.continuar();
			sleep(30000);
			System.out.println(driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.id("TextBlock2")).findElement(By.className("ng-binding")).findElements(By.tagName("p")).get(2).getText());
			assertTrue(driver.findElements(By.id("TextBlock2")).get(1).findElements(By.tagName("p")).get(3).getText().toLowerCase().contains("nominaci\u00f3n exitosa"));
			SB.DesloguearLoguear("nominaciones", 4);
		/*}catch(Exception ex1) {
			SB.DesloguearLoguear("nominaciones", 4);
			assertTrue(false);
		}*/
	}
	
	
	//@Test(groups = "Sales")//si
	public void TS95119_SalesCPQ_Nominacion_Extranjero_Verificar_Documento_Adjunto_Pasaporte(){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact2("Pasaporte", "1324567", "femenino");
		sleep(6000);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys("30/06/2018");
		//driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		driver.findElement(By.id("MethodSelectionPassport|0")).findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		driver.findElement(By.id("ValidationMethod_nextBtn")).click();
		sleep(5000);
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(1000);
		driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("documento de identidad superada");
	}
	
	//@Test(groups = "Sales")
	public void TS95138_SalesCPQ_Nominacion_Argentino_Verificar_Formulario_De_Documentacion(){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact("DNI", "10000018", "femenino");
		sleep(6000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		SB.seleccionarMetodoValidacion("DOC");
		sleep(5000);
		assertTrue(driver.findElement(By.id("FileDocumentImage")).isEnabled());
	}
	
	
	@Test(groups={"Sales","Nominacion","Ola1"},dataProvider="SalesPasaporteBolsa")
	public void TS95157_SalesCPQ_Nominacion_Extranjero_Verificar_Solicitud_De_Ingreso_Pasaporte_Cliente_Nuevo(String sCuenta, String sDni, String sLinea){
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		Random aleatorio = new Random(System.currentTimeMillis());
		String NyA = sCuenta;
		SalesBase SB = new SalesBase(driver);
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(24).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(24).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		aleatorio.setSeed(System.currentTimeMillis());
		int intAletorio = aleatorio.nextInt(899999999)+100000000;
		contact.searchContact2("Pasaporte", Integer.toString(intAletorio), sLinea);
		sleep(6000);
		driver.findElement(By.id("PermanencyDueDate")).sendKeys("30/06/2019");
		driver.findElement(By.id("FirstName")).sendKeys("Malan");
		driver.findElement(By.id("LastName")).sendKeys("Faretto");
		driver.findElement(By.id("Birthdate")).sendKeys("30/06/1980");
		//driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		contact.tipoValidacion("documento");
		sleep(5000);
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(1000);
		driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("documento de identidad superada");
	}
	
	//@Test(groups = "Sales")
	public void TS94977_SalesCPQ_Nominacion_Argentino_Verificar_Formulario_De_Documentacion_Adjunto(){
		SalesBase SB = new SalesBase(driver);
		CustomerCare CC = new CustomerCare(driver);
		ContactSearch contact = new ContactSearch(driver);
		contact.searchContact("DNI", "10000018", "femenino");
		sleep(6000);
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("algoaqui@yahoo.com.ar");
		CC.obligarclick(driver.findElement(By.id("Contact_nextBtn")));
		sleep(5000);
		SB.seleccionarMetodoValidacion("DOC");
		sleep(5000);
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(1000);
		driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().toLowerCase().contains("documento de identidad superada");
	}
	
	@Test(groups={"Sales","Nominacion","Ola1","filtrado"}, dataProvider="SalesPasaporteBolsa")
	public void TS95156_SalesCPQ_Nominacion_Extranjero_Verificar_Campo_Fecha_De_Permanencia_Cliente_Nuevo(String sCuenta, String sDni, String sLinea){
		ContactSearch contact = new ContactSearch(driver);
		String NyA = sCuenta;
		SalesBase SB = new SalesBase(driver);
		SB.BuscarAvanzada(NyA.split(" ")[0], NyA.split(" ")[1], "", "", "");
		WebElement cli = driver.findElement(By.id("tab-scoped-1"));
		if (cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElement(By.tagName("div")).getText().equals("Cliente Wholesale")) {
			cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		}
		sleep(3000);
		WebElement cua = driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(40).findElements(By.tagName("td")).get(6).findElement(By.tagName("svg"));
		System.out.println("1: "+driver.findElement(By.id("tab-scoped-1")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(40).findElements(By.tagName("td")).get(1).getText());
		cua.click();
		sleep(13000);
		perfil = "call";
		contact.searchContact2("Pasaporte", sDni, sLinea);
		sleep(6000);
		assertTrue(driver.findElement(By.id("PermanencyDueDate")).isEnabled());
		assertTrue(driver.findElements(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--right")).get(2).findElement(By.tagName("label")).getText().toLowerCase().contains("plazo de permanencia"));
	}
	
}

	

	
	
	
		
