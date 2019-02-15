package Tests;

import static org.testng.Assert.assertTrue;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.HomeBase;
import Pages.setConexion;

public class PanelGuideSections extends TestBase {
	
	private WebDriver driver;
	private String cuentaName;

	@AfterClass(groups = "CustomerCare")
	public void tearDown2() {
		driver.switchTo().defaultContent();
		try{ for(WebElement e : driver.findElements(By.className("x-tab-strip-close"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		} } catch (org.openqa.selenium.StaleElementReferenceException e) {}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.findElement(By.id("tsidButton")).click();
		List<WebElement> options = driver.findElement(By.id("tsid-menuItems")).findElements(By.tagName("a"));

		for (WebElement option : options) {
			if(option.getText().toLowerCase().equals("Ventas".toLowerCase())){
				option.click();
				break;
			}
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.quit();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeClass(groups = "CustomerCare")
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@BeforeMethod(groups = "CustomerCare")
	public void setUp() throws Exception {
		HomeBase homePage = new HomeBase(driver);
	     if(driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
	    	 homePage.switchAppsMenu();
	    	 try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    	 homePage.selectAppFromMenuByName("Ventas");
	    	 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}    
	     }
	     homePage.switchAppsMenu();
	     try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     homePage.selectAppFromMenuByName("Consola FAN");
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}    
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			try{ for(WebElement e : driver.findElements(By.className("x-tab-strip-close"))) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
			} } catch (org.openqa.selenium.StaleElementReferenceException e) {}
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			goToLeftPanel(driver, "Cuentas");
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			WebElement frame0 = driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(frame0);
			Select field = new Select(driver.findElement(By.name("fcf")));
			try {field.selectByVisibleText("Todas Las cuentas");}
			catch (org.openqa.selenium.NoSuchElementException ExM) {field.selectByVisibleText("Todas las cuentas");}
			try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			//accountPage.selectAccountByName("Andres Tech");
			WebElement account = driver.findElement(By.cssSelector(".x-grid3-cell-inner.x-grid3-col-ACCOUNT_NAME"));
			cuentaName = account.findElement(By.tagName("span")).getText();
			System.out.println("Cuenta: "+cuentaName);
			account.findElement(By.tagName("span")).click();
	}
	
	@Test(groups = "CustomerCare")
	public void TS7139_predictiveSearch()
	{ 
		Accounts accountPage = new Accounts(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame(accountPage.getFrameForElement(driver, By.className("actions-content")));
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}   
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("actuali");
		List<WebElement> butons = driver.findElements(By.xpath("//*[text() = 'Actualizar Pago']"));
		assertTrue(butons.get(0).isDisplayed());
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = "CustomerCare")
	public void TS7134_sucessSearch()
	{
		
		Accounts accountPage = new Accounts(driver);
		 try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame(accountPage.getFrameForElement(driver, By.className("actions-content")));
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}   
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Pague su Factura");
	    try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	    List<WebElement> butons = driver.findElements(By.xpath("//*[text() = 'Pague su Factura']"));
		assertTrue(butons.get(0).isDisplayed());
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = "CustomerCare")
	public void TS7135_failSearch()
	{
		
		Accounts accountPage = new Accounts(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame(accountPage.getFrameForElement(driver, By.className("actions-content")));
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}   
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Paa");
		List<WebElement> butons = driver.findElements(By.xpath("//*[text() = 'Pague su Factura']"));
		if (butons.size()== 0)
			assertTrue(true);
		else
			assertTrue(false);
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = "CustomerCare")
	public void TS7140_placeholder()
	{
		Accounts accountPage = new Accounts(driver);
		
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
		driver.switchTo().defaultContent();
        driver.switchTo().frame(accountPage.getFrameForElement(driver, By.className("actions-content")));
		List<WebElement> butons = driver.findElements(By.xpath("//input[@placeholder='Buscar Gestion Ej: Cambio de Titularidad']"));
		assertTrue(butons.get(0).isDisplayed());
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = "CustomerCare")
	public void TS7141_resetSearch()
	{
		Accounts accountPage = new Accounts(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame(accountPage.getFrameForElement(driver, By.className("actions-content")));
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}   
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
	    driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Pague su factura");
		List<WebElement> butons = driver.findElements(By.xpath("//*[text() = 'Pague su Factura']"));
		assertTrue(butons.get(0).isDisplayed());
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> butons1 = driver.findElements(By.xpath("//*[text() = 'Actualizar Pago']"));
		if (butons1.size()== 0)
			assertTrue(true);
		else
			assertTrue(false);
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-valid.ng-dirty.ng-valid-parse.ng-touched.ng-not-empty")).clear();
		driver.findElement(By.className("actions-content")).findElement(By.tagName("input")).clear();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> butons2 = driver.findElements(By.xpath("//*[text() = 'Actualizar Pago']"));
		assertTrue(butons2.get(0).isDisplayed());
		driver.switchTo().defaultContent();
	 }

	
}
