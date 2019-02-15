package Tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.TechCareDiagnostic;
import Pages.setConexion;


public class RestauradorConfiguracionUmbrales extends TestBase  {
	String value;
	String gestion;
	private WebDriver driver;
 	
	@BeforeClass
	public void mainSteup() {
		this.driver = setConexion.setupEze();	
		login(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}
//	@AfterTest
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
	public void RestaurarValorPuertoDeConexion() {
		TechCareDiagnostic page = new TechCareDiagnostic(driver);
		page.selectpage("1");//sets Parametrizacion Umbrales page to driver
		page.selectfile("1");//selects P letter, for Puerto de Conexion
		page.clearvalues();
		String udValue = "UP/Down";
		String ddValue = "Down/Down";
		List<String> redValues = Arrays.asList(udValue, ddValue);
		page.selectManyValues(redValues);
		String uuValue = "UP/UP";
		page.selectfile("2");
		page.clearvalues();
		page.selectvalues(uuValue);
	}
	
}
