package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Driver;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.DeliveryMethod;
import Pages.LineAssignment;
import Pages.SalesBase;
import Pages.Ta_CPQ;
import Pages.ValidationMethodSelection;
import Pages.setConexion;
import Pages.BasePage;
import Pages.ContactInformation;
import Tests.ValidationByDni;


public class newClient extends TestBase {
	protected String perfil = "agente";
	protected  WebDriverWait wait;
	private WebDriver driver;
	String plan="Plan con tarjeta";
	String Name = "lolaasd";
	String LastName = "velasd";
	String DateOfBirthday = "07/06/1987";
	String DNI = "DNI";
	String[] DocValue = {"10000000","3569874563","365","ssss","37452658"};
	int i = 0;
	private String validationType = "document";
	
	//@AfterClass(groups="Fase2")
	public void tearDown2() {
		//driver.close();
	}
	
	@BeforeClass(groups="Fase2")
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		 wait = new WebDriverWait(driver, 10);
		//try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 switch(perfil){
		 case "dani":
			login(driver);
			break;
		 case "agente":
			 loginAndres(driver);
			 break;
		 case "call":
			 loginTelefonico(driver);
			 break;
		 case "venta":
			 loginFranciso(driver);
			 break;
		 case "logistica":
			 loginNicolas(driver);
			 break;
		 case "entregas":
			 loginMarcela(driver);
			 break;
		 case "fabiana":
			 loginFabiana(driver);
			 break;
		 }
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	//@AfterMethod
	public void tearDown() throws Exception {
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.get("https://crm--sit.cs14.my.salesforce.com/home/home.jsp?tsid=02u41000000QWha/");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeMethod(groups="Fase2")
	public void setup() throws Exception {
		BasePage Bp= new BasePage();
		CustomerCare CC = new CustomerCare(driver);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		if (!driver.findElement(By.id("tsidLabel")).getText().equals("Ventas")){
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u41000000QWha\"]")).click();
		}
		driver.findElement(By.id("sidebarDiv")).findElement(By.tagName("a")).click();
		sleep(8000);
		SalesBase SB = new SalesBase(driver);
		SB.BuscarCuenta("DNI", "32323232");
		SB.acciondecontacto("catalogo");
	  /*if(i==0) {
		  i++;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		ContactSearch conts = new ContactSearch(driver);
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAletorio = aleatorio.nextInt(8999999)+1000000;
		Bp.setSimpleDropdown(driver.findElement(By.id("SearchClientDocumentType")),"DNI");
		driver.findElement(By.id("SearchClientDocumentNumber")).sendKeys(Integer.toString(intAletorio));
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(5000);
		List <WebElement> cc = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for (WebElement x : cc) {
			if (x.getText().toLowerCase().contains("+ crear nuevo cliente")) {
				x.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		//conts.searchContact(DNI, DocValue[4], "femenino");
		//conts.sex("femenino");
		//driver.findElement(By.id("ContactSearch_nextBtn")).click();
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		if(driver.findElement(By.id("FirstName")).getAttribute("value").isEmpty()) {
			ContactInformation page = new ContactInformation(driver);
			page.setContactInformation(Name, LastName, DateOfBirthday);
		}
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement BenBoton = driver.findElement(By.id("Contact_nextBtn"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
		BenBoton.click();*/
		sleep(12000);
		Ta_CPQ page3 = new Ta_CPQ(driver);
		SB.agregarplan(plan);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SB.continuar();
		//page3.clickOnSalesConfig();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		LineAssignment page4 = new LineAssignment(driver);
		page4.clickOnNext();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  //}
	}
	
	@Test(groups="Fase2")
	public void TS15412_CRM_Fase_2_SalesCPQ_Alta_de_linea_Verificar_default_de_modalidad_entrega_para_Canal_Ecommerce_Telefonico()
	{
		DeliveryMethod page5 = new DeliveryMethod(driver);
		Assert.assertEquals("Presencial", page5.getCurrentValueForDeliveryMethod());
		assertTrue(driver.findElement(By.id("Department")).getAttribute("value").isEmpty());
	}
	
	@Test(groups="Fase2")
	public void TS15414_CRM_Fase_2_SalesCPQ_Alta_de_linea_Verificar_default_de_modalidad_entrega_para_Canal_Presencial_Agentes()
	{
		driver.findElement(By.cssSelector(".slds-checkbox--faux")).click();
		assertFalse(driver.findElement(By.id("IdPhone")).isDisplayed());
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test(groups="Fase2")
	public void TS15413_CRM_Fase_2_SalesCPQ_Alta_de_linea_Verificar_default_de_modalidad_entrega_para_Canal_Presencial_Oficinas_comerciales()
	{
		driver.findElement(By.id("IdPhone")).sendKeys("1234568745");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	
	@Test(groups="Fase2")
	public void TS15409_CRM_Fase_2_SalesCPQ_Alta_de_linea_Verificar_LOV_de_modalidad_entrega_para_canal_ecommerce_telefonico()
	{
		BasePage page = new BasePage();
		WebElement CI = driver.findElement(By.id("ImpositiveCondition"));
		page.setSimpleDropdown(CI, "-- Clear --");
		driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-select-control.ng-scope.ng-dirty.ng-valid-parse.ng-invalid.ng-invalid-required"));
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test(groups="Fase2")
	public void TS15411CRM_Fase_2_SalesCPQ_Alta_de_linea_Verificar_LOV_de_modalidad_entrega_para_Canal_Presencial_Agentes(){
		BasePage page = new BasePage();
		WebElement prov = driver.findElement(By.id("State"));
		page.setSimpleDropdown(prov, "Buenos Aires");
		driver.findElement(By.id("CityTypeAhead")).sendKeys("Almagro");
		driver.findElement(By.id("LegalStreetTypeAhead")).sendKeys("Gascon");
		driver.findElement(By.id("StreetNumber")).sendKeys("700");
		//Se debe validar unchecked de copiar datos del domicilio legal???? 
		assertTrue(driver.findElement(By.id("BillingState")).isDisplayed());		
		assertTrue(driver.findElement(By.id("BillingCityTypeAhead")).isDisplayed());
		assertTrue(driver.findElement(By.id("BillingStreetTypeAheade")).isDisplayed());
		assertTrue(driver.findElement(By.id("BillingStreetNumber")).isDisplayed());
		assertTrue(driver.findElement(By.id("BillingFloor")).isDisplayed());
		assertTrue(driver.findElement(By.id("BillingDepartment")).isDisplayed());
		assertTrue(driver.findElement(By.id("BillingPostalCodeTypeAhead")).isDisplayed());
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test(groups="Fase2")
	public void TS15410_CRM_Fase_2_SalesCPQ_Alta_de_linea_Verificar_LOV_de_modalidad_entrega_para_Canal_Presencial_Oficinas_comerciales()
	{
		String a;
		a = driver.findElement(By.id("ContactName")).getText();
		assertEquals(a, "lolaasd velasd");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	
	
}
