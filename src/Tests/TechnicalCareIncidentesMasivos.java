package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.HomeBase;
import Pages.setConexion;

public class TechnicalCareIncidentesMasivos extends TestBase {
	private WebDriver driver;
	
	@BeforeClass
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeMethod
	public void setUp() throws Exception {
	 HomeBase homePage = new HomeBase(driver);
     homePage.switchAppsMenu();
     try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
     homePage.selectAppFromMenuByName("Consola FAN");
     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}            
     goToLeftPanel2(driver, "Casos");
     try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
     driver.findElement(By.name("newCase")).click();
     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}   
     Select field = new Select(driver.findElement(By.id("p3")));
 	 field.selectByVisibleText("Incidente Masivo");
     Accounts accountPage = new Accounts(driver);
     driver.switchTo().defaultContent();
     driver.findElement(By.name("save")).click();
 }
	
	@Test(groups = "Fase2")
	public void TS16329_SST_Creación_Eventos_Masivos_Tipo_Obligatorio() {
		Accounts accPage = new Accounts(driver);

	}

}
