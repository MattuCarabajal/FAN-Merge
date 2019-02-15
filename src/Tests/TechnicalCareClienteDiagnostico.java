package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.Driver;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.Login;
import Pages.TechCareDiagnostic;
import Pages.customerInformation;
import Pages.setConexion;


public class TechnicalCareClienteDiagnostico extends TestBase  {
	String value;
	String gestion;
	private WebDriver driver;
 	
	@BeforeClass
	public void mainSteup() {

		this.driver = setConexion.setupEze();	

		login(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}
	@AfterTest
	public void tearDown() {
		value = "UP/Down";
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("1");
		page.selectfile("1");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue1(value);
		value = "Down/Down";
		page.selectfile("1");
		page.selectvalues(value);
		page.validvalue1(value);
		value = "UP/UP";
		page.selectfile("2");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue1(value);
	driver.close();
	}
	
	@Test
	public void TS6755_DiagnosticsFixedBroadbandUPUP() {
		value = "UP/UP";
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("1");
		page.selectfile("1");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue1(value);
		page.selectfile("2");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue2(value);
}
	
	@Test
	public void TS6756_DiagnosticsFixedBroadbandUPDOWN() {
		value = "UP/Down";
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("1");
		page.selectfile("1");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue1(value);
		page.selectfile("2");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue2(value);
}
	@Test
	public void TS6757_DiagnosticsFixedBroadbandDOWNDOWN() {
		value = "Down/Down";
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("1");
		page.selectfile("1");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue1(value);
		page.selectfile("2");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue2(value);
}
	@Test
	public void TS6759_DiagnosticsFixedBroadbandNOTSESION() {
		value = "Sin Sesión";
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("1");
		page.selectfile("1");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue1(value);
		page.selectfile("2");
		page.clearvalues();
		page.selectvalues(value);
		page.validvalue2(value);
}

	@Test
	//no terminado
	public void TS6411_DiagnosticsMobileTelephonyMobileServiceEnterDomicile() {
		gestion = "Asistencia Técnica";
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("2");
		page.selectaccounttech();
		page.SelectGestion(gestion);
		driver.switchTo().defaultContent();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page.SelectService();
	
	}
}
