package Tests;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.NewThreshold;
import Pages.ParametrizationThresholds;
import Pages.setConexion;

public class parametricThresholds extends TestBase {

	private WebDriver driver;
	

	@AfterClass
	public void tearDown2() {
		driver.close();		
	}

	@BeforeMethod
	public void tearDown() {
		driver.get("https://cs14.salesforce.com/home/home.jsp?tsid=02u41000000QWha");
	}

	@BeforeClass
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test
	public void TS6365_newThreshold() {
		ParametrizationThresholds page0 = new ParametrizationThresholds(driver);
		page0.clickOnNewThreshold();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		NewThreshold page1 = new NewThreshold(driver);
		page1.createNewThreshold("Linea", "Rojo", "10", "0");
		driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[2]/a")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> abc = driver.findElements(By.className("listItemPad")); 
		abc.get(11).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-last")).getText().contains("Down"));
		List<WebElement> deleteButtons = driver.findElements(By.xpath("//*[text() = 'Eliminar']"));
		deleteButtons.get(5).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test
	public void TS6367_deleteThreshold() {
		ParametrizationThresholds page0 = new ParametrizationThresholds(driver);
		page0.clickOnNewThreshold();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		NewThreshold page1 = new NewThreshold(driver);
		page1.createNewThreshold("Linea", "Rojo", "10", "0");
		driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[2]/a")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> abc = driver.findElements(By.className("listItemPad")); 
		abc.get(11).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-last")).getText().contains("Down"));
		List<WebElement> deleteButtons = driver.findElements(By.xpath("//*[text() = 'Eliminar']"));
		Integer a = deleteButtons.size();
		deleteButtons.get(5).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> deleteButtons2 = driver.findElements(By.xpath("//*[text() = 'Eliminar']"));
		Integer b = deleteButtons2.size();
		Assert.assertTrue((a-b == 1));
	}
	
	@Test
	public void TS6370_modifyThreshold() {
		ParametrizationThresholds page0 = new ParametrizationThresholds(driver);
		page0.clickOnNewThreshold();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		NewThreshold page1 = new NewThreshold(driver);
		page1.createNewThreshold("Linea", "Rojo", "10", "0");
		driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[2]/a")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> abc = driver.findElements(By.className("listItemPad")); 
		abc.get(11).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-last")).getText().contains("Down"));
		List<WebElement> modifyButtons = driver.findElements(By.xpath("//*[text() = 'Modificar']"));
		modifyButtons.get(5).click();
		page1.modifyThreshold();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-last")).getText().contains("Up"));
		List<WebElement> deleteButtons = driver.findElements(By.xpath("//*[text() = 'Eliminar']"));
		deleteButtons.get(5).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
}
