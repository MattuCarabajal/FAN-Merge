package Perfomance;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import DataProvider.ExcelUtils;

public class LoginPerfomance {
	
	private WebDriver driver;
	
	//**********************************************DataProvider*******************************************//
	
	@DataProvider
	public Object[][] login() throws Exception{
	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","login",1,1,4);
	 return (testObjArray);
	}
	
	//**********************************************Configuracion******************************************//
	@BeforeMethod
	public void setup() {
	System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    driver = new ChromeDriver(options);
	}
	
	
	//**********************************************TEST******************************************//
	@Test(invocationCount=3,dataProvider="login")
	public void login(String sUser, String sPassword, String seconds, String ambiente) {
		int Esperar=Integer.parseInt(seconds)/10;
		boolean Passed=true;
		driver.manage().timeouts().implicitlyWait(Esperar, TimeUnit.SECONDS);
		driver.get(ambiente);
		
		try {
		WebElement logininterno=driver.findElement(By.id("idp_section_buttons"));
		logininterno.click();
		WebElement User=driver.findElement(By.name("Ecom_User_ID"));
		WebElement Password=driver.findElement(By.name("Ecom_Password"));
		WebElement loginButton=driver.findElement(By.id("loginButton2"));
		User.sendKeys(sUser);
		Password.sendKeys(sPassword);
		loginButton.click();
		}catch(Exception e) {
			assertEquals("Inicio de Sesion: Passed", "Inicio de Sesion: FAIL");
			driver.quit();
		}
		long tiempoInicio=System.currentTimeMillis();
		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		List<WebElement> element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));
		List<WebElement> element2 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));
		
		long tiempoFinal=System.currentTimeMillis();
		
		System.out.println("Tiempo de Ejecucion: "+(tiempoFinal-tiempoInicio)+"ms.");
		System.out.println("Tiempo de Necesario: "+(Esperar*1000)+"ms.");
		
		if((tiempoFinal-tiempoInicio)>(Esperar*1000)) 
			Passed=false;
		driver.quit();
		assertTrue(Passed);
	}
	
	
	
	@Test(dataProvider="login")
	public void login2(String sUser, String sPassword, String seconds, String ambiente) {
		int Esperar=Integer.parseInt(seconds)/10;
		String resultado="Tiempo de carga suficiente";
		driver.manage().timeouts().implicitlyWait(Esperar, TimeUnit.SECONDS);
		driver.get(ambiente);
		
		try {
		WebElement logininterno=driver.findElement(By.id("idp_section_buttons"));
		logininterno.click();
		WebElement User=driver.findElement(By.name("Ecom_User_ID"));
		WebElement Password=driver.findElement(By.name("Ecom_Password"));
		WebElement loginButton=driver.findElement(By.id("loginButton2"));
		User.sendKeys(sUser);
		Password.sendKeys(sPassword);
		loginButton.click();
		}catch(Exception e) {
			assertEquals("Inicio de Sesion: Passed", "Inicio de Sesion: FAIL");
			driver.quit();
		}
		
		try{
		WebDriverWait wait = new WebDriverWait(driver, Esperar);
		List<WebElement> element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));
		List<WebElement> element2 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));
		}catch(org.openqa.selenium.TimeoutException a) {
			resultado="Tiempo Agotado";
		}
		driver.quit();
		assertEquals(resultado, "Tiempo de carga suficiente");
	}
}
