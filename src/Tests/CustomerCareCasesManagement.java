package Tests;


import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.CustomerCasesManager;
import Pages.setConexion;

public class CustomerCareCasesManagement extends TestBase {

	private WebDriver driver;

	@BeforeTest
	public void mainSteup() {
		this.driver = setConexion.setupEze();	
		login(driver);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		if (!driver.getCurrentUrl().toString().equals("https://cs14.salesforce.com/console")){
			driver.findElement(By.id("tsidLabel")).click();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.xpath("//a[@href=\"/console?tsid=02uc0000000D6Hd\"]")).click();
		}
		try {Thread.sleep(3500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		goToLeftPanel2(driver, "Casos");
	}
	
	@AfterTest
	public void tearDown() {
		driver.close();
	}
		
	@Test
	public void TS7095_DeleteCasesAdminRestrictedMessage() {
		String adminRestrictedMsg = "El primer error de validación encontrado fue \"Solo el administrador puede eliminar casos generados.\"";
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		CustomerCasesManager CCManagerPage = new CustomerCasesManager(driver);
		CCManagerPage.getCase("00001813").findElement(By.xpath("//a[@onclick=\"return confirmDelete();\"]")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		checkAlert();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();

		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		WebElement frameToSwitchTo = frames.get(0);
		for (WebElement frame: frames) {
			if(frame.getAttribute("id").equals("ext-comp-1005")) {
				frameToSwitchTo = frame;
			}
		}
		
		driver.switchTo().frame(frameToSwitchTo);
		List<WebElement> trList = driver.findElements(By.tagName("tr"));

		String errorMsgInPage = trList.get(1).findElement(By.tagName("td")).getText();

		Assert.assertTrue(errorMsgInPage.contains(adminRestrictedMsg));
	}
		
		
	
	public void checkAlert() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alert.accept();
	    } catch (Exception e) {
	        //exception handling
	    	System.out.println(e.getMessage());
	    }
	}
	
	
	
}