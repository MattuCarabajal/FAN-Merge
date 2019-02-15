package Tests;
import org.testng.annotations.Test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import Pages.AccountType;
import Pages.Login;
import Pages.NewAccount;
import Pages.setConexion;

public class newAccount extends TestBase {
	
private WebDriver driver;
String accountName = "Aaa Aaa";

@BeforeTest
public void mainSteup() {
	this.driver = setConexion.setupEze();	
	login(driver);
}

@AfterTest
public void tearDown2() {
	driver.close();
	
}

@AfterMethod
public void tearDown() {
	driver.switchTo().defaultContent();
	driver.findElement(By.id("navigatortab__scc-pt-0")).click();
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.findElement(By.id("ext-gen67")).click();
	List<WebElement> frame6 = driver.findElements(By.tagName("iframe"));
	driver.switchTo().frame(frame6.get(1));
	driver.findElement(By.name("delete")).click(); 
	 for (String handle : driver.getWindowHandles()) {	 
	    driver.switchTo().window(handle);}
	List<WebElement> buttons = driver.findElements(By.cssSelector(".x-btn-text"));
	buttons.get(2).click();
	try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.get("https://cs14.salesforce.com/home/home.jsp?tsid=02u41000000QWha");
}

@BeforeMethod
public void setUp() throws Exception {
	try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	System.out.println(driver.getCurrentUrl().toString());
	if (!driver.getCurrentUrl().toString().equals("https://cs14.salesforce.com/console")){
		driver.findElement(By.id("tsidLabel")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//a[@href=\"/console?tsid=02uc0000000D6Hd\"]")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> mainTabs = driver.findElements(By.className("x-tab-strip-close"));
		for (WebElement e : mainTabs) {
			try {((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);} catch (org.openqa.selenium.StaleElementReferenceException b) {}
			}
		List<WebElement> mainTabs1 = driver.findElements(By.className("x-tab-strip-close"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", mainTabs1.get(1));
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goToLeftPanel(driver, "Cuentas");
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement frame1 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame1);
	}
}

@Test	
public void createNewAccount() {
	driver.findElement(By.name("new")).click();
	driver.switchTo().defaultContent();
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	List<WebElement> frame2 = driver.findElements(By.tagName("iframe"));
	driver.switchTo().frame(frame2.get(1));
	AccountType tipo = new AccountType(driver);
	tipo.setType("Consumer");
	List<WebElement> frame3 = driver.findElements(By.tagName("iframe"));
	driver.switchTo().frame(frame3.get(1));
	NewAccount account = new NewAccount(driver);
	account.createNewAcc(accountName);
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.switchTo().defaultContent();
	goToLeftPanel(driver, "Cuentas");
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	WebElement frame4 = driver.findElement(By.tagName("iframe"));
	driver.switchTo().frame(frame4);
	Select field = new Select(driver.findElement(By.name("fcf")));
	field.selectByVisibleText("Todas las cuentas");
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.navigate().refresh();
	WebElement frame5 = driver.findElement(By.tagName("iframe"));
	driver.switchTo().frame(frame5);
	List<WebElement> accounts = driver.findElements(By.cssSelector(".x-grid3-cell-inner.x-grid3-col-ACCOUNT_NAME"));
	Assert.assertEquals(accountName, accounts.get(0).getText());
}

}
