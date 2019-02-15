package Tests;


import java.util.List;

import org.openqa.selenium.By;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.setConexion;


public class SmokeTest extends TestBase  {

	
	private WebDriver driver;
	
	@AfterTest(groups= {"Smoke"})
	public void tearDown() {
			driver.close();
	}
	
	@AfterMethod(groups={"Smoke"})
	public void home(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.get("https://crm--sit.cs14.my.salesforce.com/home/home.jsp?");
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String a = driver.findElement(By.id("tsidLabel")).getText();
		if(!a.equals("Ventas")){
		driver.findElement(By.id("tsidLabel")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02u41000000QWha\']")).click();}
	}

	
	@Test(groups={"Smoke"})
	public void TS1_Login(){
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		System.out.println("Login = OK");
	}
	
	@Test(groups={"Smoke", "Sales"})
	public void TS2_Modulo_Ventas(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String a = driver.findElement(By.id("tsidLabel")).getText();
		if(a.equals("Ventas")){
		System.out.println("Modulo Ventas = OK");}
		else{driver.findElement(By.id("tsidLabel")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02u41000000QWha\']")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String b = driver.findElement(By.id("tsidLabel")).getText();
		Assert.assertTrue(b.equals("Ventas"));
		Assert.assertTrue(
				driver.findElement(By.id("Contact_Tab")).isEnabled()&&
				driver.findElement(By.id("Account_Tab")).isEnabled()&&
				driver.findElement(By.id("Lead_Tab")).isEnabled()&&
				driver.findElement(By.id("Opportunity_Tab")).isEnabled()&&
				driver.findElement(By.id("Forecasting3_Tab")).isEnabled()&&
				driver.findElement(By.id("report_Tab")).isEnabled());
		System.out.println("Modulo Ventas = OK");}
	}
	
	@Test(groups={"Smoke", "ConsolaFAN"})
	public void TS3_Consola_FAN(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String a = driver.findElement(By.id("tsidLabel")).getText();
		if(a.equals("Consola FAN")){
		System.out.println("Modulo Consola FAN = OK");}
		else{driver.findElement(By.id("tsidLabel")).click();
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.className("sd_custom_logo")).isEnabled());
		System.out.println("Modulo Consola FAN = OK");}
	}
	
	@Test(groups={"Smoke", "SCP"})
	public void TS4_SCP(){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String a = driver.findElement(By.id("tsidLabel")).getText();		
		if(a.equals("SCP")){
		System.out.println("Modulo SCP= OK");}
		else{driver.findElement(By.id("tsidLabel")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02uc000000093iX\']")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String b = driver.findElement(By.id("tsidLabel")).getText();
		Assert.assertTrue(b.equals("SCP"));
		Assert.assertTrue(driver.findElement(By.className("wt-Strategic-Client-Plan")).isEnabled());
		System.out.println("Modulo SCP = OK");}		
	}
	
	@Test (groups= {"Smoke", "CustomerCare"})
	public void TS5_Cuentas_Consola_FAN() {
		TS3_Consola_FAN();		
		WebElement selector = driver.findElement(By.cssSelector(".x-btn-small.x-btn-icon-small-left"));
		WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(btnSplit, 245, 20).click().build().perform();
		List <WebElement> desplegable = driver.findElements(By.cssSelector(".x-menu-item.accountMru.standardObject.sd-nav-menu-item"));
		for (WebElement op : desplegable) {
			if (op.getText().equalsIgnoreCase("Cuentas")) op.click();
		}
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> boton = driver.findElements(By.className("btn"));
		for (WebElement x : boton) {
			if (x.getText().contains("Nueva cuenta")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}	
	}
	
	@Test (groups= {"Smoke", "CustomerCare"})
	public void TS6_Casos_Consola_FAN() {
		TS3_Consola_FAN();		
		WebElement selector = driver.findElement(By.cssSelector(".x-btn-small.x-btn-icon-small-left"));
		WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(btnSplit, 245, 20).click().build().perform();
		List <WebElement> casos = driver.findElements(By.className("x-menu-item-text"));
		for (WebElement x : casos) {
			if (x.getText().equals("Casos")) {
				x.click();
			}
		}
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> boton = driver.findElements(By.className("btn"));
		for (WebElement x : boton) {
			if (x.getText().contains("Nuevo caso")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}
	
	@Test (groups= {"Smoke", "CustomerCare"})
	public void TS7_Cuentas_Con_Precondiciones_Customer_Care() {
		TS3_Consola_FAN();		
		WebElement selector = driver.findElement(By.cssSelector(".x-btn-small.x-btn-icon-small-left"));
		WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(btnSplit, 245, 20).click().build().perform();
		List <WebElement> desplegable = driver.findElements(By.cssSelector(".x-menu-item.accountMru.standardObject.sd-nav-menu-item"));
		for (WebElement op : desplegable) {
			if (op.getText().equalsIgnoreCase("Cuentas")) op.click();
		}
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement marcoCuentas = driver.findElement(By.cssSelector(".x-plain-body.sd_nav_tabpanel_body.x-tab-panel-body-top iframe"));
		driver.switchTo().frame(marcoCuentas);
		WebElement selectCuentas = driver.findElement(By.name("fcf"));
		Select field = new Select(selectCuentas);
		if (!field.getFirstSelectedOption().getText().equalsIgnoreCase("Todas las cuentas"))
			field.selectByVisibleText("Todas las cuentas");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List <WebElement> cuenta = driver.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACCOUNT_NAME"));
		boolean fernando = false;
		boolean andres = false;
		boolean raul = false;
		boolean FCBilling1 = false;
		boolean FCBilling2 = false;
		boolean cuentaActServInact = false;
		boolean cuentaActSinServ = false;
		for (WebElement x : cuenta) {		
			if (x.getText().equals("aaaaFernando Care")) {
				fernando = true;				
			}
			if (x.getText().equals("aaaaAndres Care")) {
				andres = true;				
			}
			if (x.getText().equals("aaaaRaul Care")) {
				raul = true;
			}
			if (x.getText().equals("aaaaFernando Care Billing 1")) {
				FCBilling1 = true;
			}
			if (x.getText().equals("aaaaFernando Care Billing 2")) {
				FCBilling2 = true;
			}
			if (x.getText().equals("aaaaCuenta Activa Serv Inact")) {
				cuentaActServInact = true;
			}
			if (x.getText().equals("aaaaCuenta Activa S/Serv")) {
				cuentaActSinServ = true;
			}
		}
		Assert.assertTrue(fernando && andres && raul && FCBilling1 && FCBilling2 && cuentaActServInact && cuentaActSinServ);
	}
}
