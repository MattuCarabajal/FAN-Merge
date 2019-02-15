package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.awt.Button;
import java.util.List;

import org.openqa.selenium.*;

import Pages.BasePage;
import Pages.Bill;
import Pages.BillSimulation;
import Pages.DeliveryMethod;
import Pages.LineAssignment;
import Pages.Login;
import Pages.Order;
import Pages.OrdersTab;
import Pages.PaymentMethod;
import Pages.SerialInput;
import Pages.Ta_CPQ;
import Pages.setConexion;
import org.testng.Assert;



public class consoleFAN extends TestBase{
	private WebDriver driver;
	
	@AfterClass
	public void tearDown() {
		//driver.close();
	}
	
	@BeforeClass
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	@BeforeMethod
	public void setup() throws Exception {		
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//if (!driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")){
		if (!driver.getCurrentUrl().contains("console")) {
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\"/console?tsid=02uc0000000D6Hd\"]")).click();

		}
		goToLeftPanel(driver, "Cuentas");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		waitFor(driver, (By.name("fcf")));	
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText("Todas Las cuentas");
		
		waitFor(driver, (By.xpath("//*[text() = 'Adrian Tech']")));	
		
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> accounts = driver.findElements(By.xpath("//span[text()=\"Andres Care\"]/parent::a"));
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", accounts.get(0));
		
		//Actions moveAndClick = new Actions(driver);
		//moveAndClick.moveToElement(accounts.get(0)).click().doubleClick().build().perform();;
		//accounts.get(0).click();
		//System.out.println(accounts.get(0).getAttribute("href"));
		//driver.switchTo().defaultContent();
	}
	
	@Test
	public void TS6843_CRM_Fase_1_SalesCPQ_Alta_Linea_Vista_360_Ingresar_a_la_venta_desde_la_vista_360() {
		
		//try {Thread.sleep(18000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		//driver.switchTo().frame(BasePage.getIndexFrame(driver,By.xpath("//span[text()=\"Nueva Venta\"]")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		List<WebElement> buttonNewSale = driver.findElements(By.xpath("//span[text()=\"Nueva Venta\"]"));
		
		Assert.assertTrue (buttonNewSale.size() > 0);
		}
	
	
	@Test(groups = { "Fase2" })
	public void TS11948_CRM_Fase_2_SalesCPQ_Alta_Linea_Nueva_Venta_Verificar_acceso_a_Nueva_Venta_desde_vista_360() {
		consoleFAN runner =  new consoleFAN();
		runner.TS6843_CRM_Fase_1_SalesCPQ_Alta_Linea_Vista_360_Ingresar_a_la_venta_desde_la_vista_360();
	}
	
	
}
