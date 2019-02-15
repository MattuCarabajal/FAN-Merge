package Tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;



public class PanelServiceActive extends TestBase {
	
	private WebDriver driver;
	
	
	@AfterClass(groups= "CustomerCare")
	public void tearDown2() {
		driver.quit();	
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	

		@BeforeClass(groups= "CustomerCare")
		public void init() throws Exception
		
		{
			this.driver = setConexion.setupEze();
			login(driver);
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
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
		     try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				CustomerCare page = new CustomerCare(driver);
				page.cerrarultimapestana();
				page.elegircuenta("aaaaFernando Care");
		}	
		

		
	
	@Test(groups= "CustomerCare")
	public void TS7130_filterFuncionality(){
		//CustomerCare page = new CustomerCare(driver);
		
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		 BasePage cambioFrameByID=new BasePage();
		 driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		 List<WebElement> servicios = driver.findElements(By.cssSelector(".console-card.active"));
		 if(servicios.size()>0) {
		     if(servicios.size()>1) {
		    	 driver.findElement(By.cssSelector(".form-inline.ng-pristine.ng-valid")).findElement(By.tagName("input")).sendKeys(servicios.get(0).findElement(By.className("slds-text-heading_medium")).getText());
		    	 servicios = driver.findElements(By.cssSelector(".console-card.active"));
		    	 Assert.assertTrue(servicios.size()==1);
		     }else {
		    	 driver.findElement(By.cssSelector(".form-inline.ng-pristine.ng-valid")).findElement(By.tagName("input")).sendKeys("esto no debe aparecer");
		    	 try {servicios = driver.findElements(By.cssSelector(".console-card.active"));
		    	 	assertTrue(servicios.size()<1);
		    	 }catch (NoSuchElementException noSuchElemExcept) {assertTrue(true);}
		     }
		     assertTrue(true);
		}
		 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		 driver.findElement(By.cssSelector(".form-inline.ng-valid.ng-dirty.ng-valid-parse")).findElement(By.tagName("input")).clear();
		 driver.switchTo().defaultContent();
		
	}
	
	@Test(groups= "CustomerCare")
	public void TS7129_showFilter()
	{
	
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		 BasePage cambioFrameByID=new BasePage();
		 driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		 Assert.assertTrue(driver.findElement(By.cssSelector(".form-inline.ng-pristine.ng-valid")).findElement(By.tagName("input")).isDisplayed());
		 try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			
		 driver.switchTo().defaultContent();
	}
	
	@Test(groups= "CustomerCare")
	public void TS7131_showCardService()
	{
		
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		 BasePage cambioFrameByID=new BasePage();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".console-card.active")));
	     List<WebElement> servicios = driver.findElements(By.cssSelector(".console-card.active"));
	    
	     if(servicios.size()>1)
			Assert.assertNotEquals(servicios.get(0).findElement(By.className("slds-text-heading_medium")).getText(),servicios.get(1).findElement(By.className("slds-text-heading_medium")).getText());
	     try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			
	     driver.switchTo().defaultContent();
	}
	
	@Test(groups= "CustomerCare")
	public void TS7133_validationScroll(){
		CustomerCare page = new CustomerCare(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.usarpanelcentral("aaaaFernando Care");
		 BasePage cambioFrameByID=new BasePage();
		 driver.switchTo().defaultContent();
	     driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".hasMotif.accountTab.detailPage.sfdcBody.brandQuaternaryBgr.ext-webkit.ext-chrome")));
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		Boolean VertscrollStatus = (Boolean) javascript.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
		assertTrue(VertscrollStatus);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
	}
}
	
	



	
	


