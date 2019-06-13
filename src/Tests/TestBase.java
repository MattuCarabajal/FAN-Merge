package Tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import Pages.Accounts;
import Pages.BasePage;
import Pages.BeFan;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.Login;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import DataProvider.ExcelUtils;

public class TestBase {
	
	protected static WebDriver driver;

	public static String urlAmbiente = "https://telecomcrm--uat02.cs45.my.salesforce.com/";
	// public static String urlAmbiente = "https://telecomcrm--sit02.cs91.my.salesforce.com";

	// viejo public static String urlSCP = "https://telecomcrm--uat.cs8.my.salesforce.com";
	public static String urlSCP = "https://telecomcrm--uat.cs53.my.salesforce.com";

	// public static String urlComunidad = "https://uat-autogestion-uat.cs53.force.com/clientes/s/";
	public static String urlCommunity = "https://sit-scrumcella.cs14.force.com/clientes/s/";

	public static String urlFlow = "https://webgestionmoviltesting/default.aspx";

	public static String urlBeFANUAT = "http://snapuat.telecom.com.ar/#/home";

	//public static String urlBeFAN = "https://befantest2.personal.corp/#/signin";
	 public static String urlBeFAN = "https://befanuat2.personal.corp/#/signin";
				
	public void leftDropdown(WebDriver driver, String selection) {
		driver.findElement(By.className("x-btn-mc")).click();
		switch(selection) {
		case "Cuentas":
			driver.findElement(By.id("ext-gen211")).click();;
			break; 
		}
	}
	
	public void goToLeftPanel(WebDriver driver, String selection) {
		WebElement element = driver.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(element, 245, 20).click().build().perform();
		switch(selection) {
		case "Cuentas":
			driver.findElement(By.id("nav-tab-0")).click();
			break;
		case "Casos":
			driver.findElement(By.id("nav-tab-1")).click();
			break;
		}
		sleepPrivado(5000);
	}
	
	public void goToLeftPanel2(WebDriver driver, String selection) {
		cambioDeFrame(driver, By.className("x-btn-split"), 0);
		WebElement dropDown = driver.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);
		builder.moveToElement(dropDown, 245, 20).click().build().perform();
		List<WebElement> options = driver.findElements(By.tagName("li"));
		for(WebElement option : options) {
			if(option.findElement(By.tagName("span")).getText().toLowerCase().equals(selection.toLowerCase())) {
				option.findElement(By.tagName("a")).click();
				break;
			}
		}
	}
	
	public void goToLeftPanel3(WebDriver driver, String selection) {
		cambioDeFrame(driver, By.className("x-btn-split"), 0);
		driver.findElement(By.className("x-btn-split")).click();
		List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'x-menu-list-item')]"));
		for(WebElement option : options) {
			if(option.findElement(By.tagName("span")).getText().toLowerCase().equals(selection.toLowerCase())) {
				option.findElement(By.tagName("a")).click();
				//System.out.println("Seleccionado"); //13/09/2017 working.
				break;
			}
		}
	}
	
	public void goToLeftPanel4(WebDriver driver, String selection) {
		cambioDeFrame(driver, By.className("x-btn-split"), 0);
		WebElement element = driver.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(element, 245, 20).click().build().perform();
		List<WebElement> optiones = driver.findElements(By.tagName("li"));
		for(WebElement option : optiones) {
			if(option.findElement(By.tagName("span")).getText().toLowerCase().equals(selection.toLowerCase())) {
				option.findElement(By.tagName("a")).click();
				break;
			}
		}	
	}
	
	public void login(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(4000);
	    Login page0 = new Login(driver);
	    page0.ingresar();
		//}else{
			//driver.findElement(By.id("chooser")).click();
	//	}
	}
	
	public void loginDani(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(4000);
	    Login page0 = new Login(driver);
	    page0.ingresarDani();
		//}else{
			//driver.findElement(By.id("chooser")).click();
	//	}
	}
	
	public void loginMarketing(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(5000);
	    Login lLogin = new Login(driver);
	    lLogin.ingresarDani();
	}
	
	public void loginSCPAdmin(WebDriver driver) {
	     driver.get("https://telecomcrm--uat.cs8.my.salesforce.com");
	     sleepPrivado(6000);
	     Login page0 = new Login(driver);
	     page0.ingresarAdminSCP();
	   }
	   
	public void loginSCPconTodo(WebDriver driver) {
	     driver.get(urlSCP);
	     sleepPrivado(6000);
	     Login page0 = new Login(driver);
	     page0.ingresarSCPconTodo();
	   }

	public void loginSCPUsuario(WebDriver driver) {
		driver.get(urlSCP);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarUsuarioSCP();
	}

	public void loginSCPAdminServices(WebDriver driver) {
		driver.get(urlSCP);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarSCPAdminServices();
	}

	public void loginSCPConPermisos(WebDriver driver) {
		driver.get(urlSCP);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarSCPConPermisos();
	}

	public void omInternalLoginWithCredentials(WebDriver driver, String userName, String password) {
		driver.navigate().to(urlAmbiente);
		driver.findElement(By.xpath("//*[@id=\"idp_hint\"]/button")).click();
	    sleepPrivado(3000);
//		driver.findElement(By.name("Ecom_User_ID")).sendKeys(userName);
//		driver.findElement(By.name("Ecom_Password")).sendKeys(password);
//		driver.findElement(By.id("loginButton2")).click();
	}
	
	public void omLogout(WebDriver driver) {
		driver.findElement(By.id("userNav")).click();
		sleepPrivado(2000);
		driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a")).get(3).click();
	}
	     
	public void login1(WebDriver driver) {
		driver.get("https://goo.gl/ETjDYJ");
	    sleepPrivado(1000);
	    Login page0 = new Login(driver);
	    page0.ingresar();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}	
	
	public void IngresarCred(WebDriver driver) {
	    Login page0 = new Login(driver);
	    page0.ingresar();
		sleepPrivado(5000);
	}
	    
	public void waitFor(WebDriver driver, By element) {
		(new WebDriverWait(driver, 25)).until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	public void waitForClickeable(WebDriver driver, By element) {
		(new WebDriverWait(driver, 25)).until(ExpectedConditions.elementToBeClickable(element));
	}

	public void clickLeftPanel(WebDriver driver) {
		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		for (WebElement btn : buttons) {
			if(btn.getAttribute("id").equals("ext-gen33")) {
				btn.click();
				break;
			}
		}
	}
	
	public void loginsales(WebDriver driver, String tipo) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		switch (tipo) {
		case "agente":
			page0.ingresarAndres();
			break;
		case "callcenter":
			page0.ingresarElena();
			break;
		case "vendedor":
			page0.ingresarFrancisco();
			break;
		case "logistica":
			page0.ingresarNicolas();
			break;
		case "entregas":
			page0.ingresarMarcela();
			break;
		}
	}

	/**
	 * Ingresa con los datos de la cuenta Andres Para el Modulo Sales tiene
	 * vinculado el perfil de Agente y Atenci�n a clientes
	 */
	// public void loginAgente(WebDriver driver) {
	// driver.get(urlAmbiente);
	// //try {Thread.sleep(6000);} catch (InterruptedException ex)
	// {Thread.currentThread().interrupt();}
	// waitForClickeable(driver, By.id("idp_section_buttons"));
	// Login page0 = new Login(driver);
	// page0.ingresarAndres();
	// }
	//
	public void loginGeneral(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarGeneral();
	}

	/**
	 * Ingresa con los datos de la cuenta Elena Para el Modulo Sales tiene vinculado
	 * el perfil de Call center
	 */
	// public void loginTelefonico(WebDriver driver) {
	// driver.get(urlAmbiente);
	// try {Thread.sleep(6000);} catch (InterruptedException ex)
	// {Thread.currentThread().interrupt();}
	// Login page0 = new Login(driver);
	// page0.ingresarElena();
	// }
	
	public void loginLogistica(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarLogistica();
	}

	public void loginEntrega(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarEntrega();
	}

	/**
	 * Ingresa con los datos de la cuenta Francisco Para el Modulo Sales tiene
	 * vinculado el perfil de Vendedor Oficina Comercial
	 */
	public void loginFranciso(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarFrancisco();
	}

	public void loginOfCom(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarNominaciones();
	}

	public void loginBackOffice(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarBackOffice();
	}

	public void loginflow(WebDriver driver) {
		driver.get(urlFlow);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarflow();
	}

	public void loginOperativo(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(5000);
		Login page0 = new Login(driver);
		page0.ingresarOperativo();
	}

	public void loginAdminFuncional(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarLautaro();
	}

	/**
	 * Ingresa con los datos de la cuenta Nicolas. Para el Modulo Sales tiene
	 * vinculado el perfil de Logistica
	 */
	public void loginNicolas(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarNicolas();
	}

	/**
	 * Ingresa con los datos de la cuenta de Marcela Para el Modulo Sales tiene
	 * vinculado el perfil de Entregas y Configuraciones
	 */
	public void loginMarcela(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarMarcela();
	}

	public void loginFabiana(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarFabiana();
	}

	public void loginVictor(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarVictor();
	}

	public void loginBeFAN(WebDriver driver) {
		driver.get(urlBeFAN);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarBeFAN();
	}

	public void loginBeFANConfigurador(WebDriver driver) {
		driver.get(urlBeFAN);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarBeFANConfigurador();
	}

	public void loginFraude(WebDriver driver) {
		driver.get(urlAmbiente);
		waitForClickeable(driver, By.id("idp_section_buttons"));
		Login page0 = new Login(driver);
		page0.ingresarFraude();
	}

	// Ingreso a Merge
	public void loginOOCC(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarOOCC();
	}

	public void loginTelefonico(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarTelefonico();
	}

	public void loginAgente(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarAgente();
	}

	public void loginFANFront(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(6000);
		Login page0 = new Login(driver);
		page0.ingresarFANFront();
	}

	public void elegirmodulo(String modulo) {
		sleepPrivado(4000);
		String a = driver.findElement(By.id("tsidLabel")).getText();
		driver.findElement(By.id("tsidLabel")).click();
		List<WebElement> mdls = driver.findElements(By.cssSelector(".menuButtonMenuLink"));
		sleepPrivado(8000);
		if (a.equals("Ventas")) {
			for (WebElement e : mdls) {
				if (e.getText().toLowerCase().equals(modulo)) {
					e.click();
				}
				break;
			}
		} else {
			for (WebElement e : mdls) {
				if (e.getText().toLowerCase().equals("ventas")) {
					e.click();
				}
				break;
			}
			sleepPrivado(10000);
			driver.findElement(By.id("tsidLabel")).click();
			for (WebElement e : mdls) {
				if (e.getText().toLowerCase().equals(modulo)) {
					e.click();
				}
				break;
			}
		}
	}
		
	public boolean verificarContenidoLista(String[] consultar, List<WebElement> Lista) {
		List<String> titleTabla = new ArrayList<String>();
		for (WebElement a : Lista) {
			titleTabla.add(a.getText().toLowerCase());
			System.out.println(a.getText());
		}
		for (String a : consultar) {
			if (!(titleTabla.contains(a))) {
				System.out.println("fallo en " + a);
				return false;
			}
		}
		return true;
	}

	/**
	 * Desarrollado para ir a consola Fan en F3. (primero va ventas y luego retorna
	 * a consola fan)
	 * 
	 * @param driver
	 * @author Almer Fase 3
	 */
	public void goInitToConsolaFanF3(WebDriver driver) {
		HomeBase homePage = new HomeBase(driver);
		sleepPrivado(3000);
		if (driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
			homePage.switchAppsMenu();
			sleepPrivado(2000);
			homePage.selectAppFromMenuByName("Ventas");
			sleepPrivado(5000);
		}
		homePage.switchAppsMenu();
		sleepPrivado(2000);
		homePage.selectAppFromMenuByName("Consola FAN");
		sleepPrivado(3000);
		try {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		} catch (org.openqa.selenium.NoAlertPresentException e) {}
	}
		
	/**
	 * Selecciona una Cuenta segun el Nombre
	 * 
	 * @param driver
	 * @author Almer Fase 3
	 */
	public void seleccionCuentaPorNombre(WebDriver driver, String nombreCuenta) {
		sleepPrivado(3000);
		goToLeftPanel2(driver, "Cuentas");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText("Vista Tech");
		sleepPrivado(5000);
		List<WebElement> accounts = driver.findElements(By.xpath("//*[text() = '" + nombreCuenta + "']"));
		accounts.get(0).click();
		sleepPrivado(3000);
		driver.switchTo().defaultContent();
	}

	/**
	 * Selecciona una Cuenta segun el Nombre
	 * 
	 * @param driver
	 * @author Almer Fase 3
	 */
	public void searchAndClick(WebDriver driver, String busqueda) {
		Accounts view = new Accounts(driver);
		BasePage searchImput = new BasePage();
		sleepPrivado(7000);
		view.deployEastPanel();
		List<WebElement> frame1 = driver.findElements(By.tagName("iframe"));
		int indexFrame = searchImput.getIndexFrame(driver, By.xpath("/html/body/div/div[1]/ng-include/div/div[1]/ng-include/div/div[2]/input"));
		driver.switchTo().frame(frame1.get(indexFrame));
		WebElement elemento = driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div/div[1]/ng-include/div/div[2]/input"));
		elemento.sendKeys(busqueda);
		sleepPrivado(3000);
		driver.findElement(By.xpath("//*[text() = '" + busqueda + "']")).click();
		driver.switchTo().defaultContent();
		sleepPrivado(2000);
	}

	public static void inicializarDriver() {
		driver = setConexion.setupEze();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void cerrarTodo() {
		driver.close();
	}
	
	public static void login() {
		//driver.get("https://crm--sit.cs14.my.salesforce.com/");
		//waitFor(By.xpath("//button[@class='button mb24 secondary wide']"));
		//driver.findElement(By.xpath("//button[@class='button mb24 secondary wide']")).click();
		driver.get("https://tuidsrvtest.telecom.com.ar:8443/nidp/app/login?id=IDM_NamePassword&sid=0&option=credential&sid=0&target=https%3A%2F%2Ftuidsrvtest.telecom.com.ar%3A8443%2Fnidp%2Fsaml2%2Fidpsend%3FSAMLRequest%3DjZNtb6owFMe%252FiuE9WB58gAwNiOyyzcEERfbGYCmKSqttgc1PP6Z3y%252B6b5Z6kSU%252FOw%252F%252B0%252FfVu%252FFYeOzWirCDYFGQJCB2EIckKvDWFReSKQ2E8umNpeTwZVsV3eI7OFWK809ZhZlwDplBRbJCUFczAaYmYwaERWrMnQ5GAcaKEE0iOQsdiDFHeCk0IZlWJaIhoXUC0mD%252BZwo7zEzO6XUhLUQy9SIJM1qTyXWLpEbGcUIgkSMoxIyYADgRXUxtZj4SO0w5U4JRfz%252FDViFdFxmjN25jE0RG1xZ8NpJQaQ01Tu7jITt3P%252BZVuu2MIZ%252BMiM8NvtXYGoeM5prBWJlZrsVU4iq%252FOpsMmuVjNzPle4DMMaDZ93CJO5Nw%252FkOfDKZPzHF0WsI%252FtvQfDnftq7zeibfui1uPzZaasEnXpDvk9kQdbSvnpwUly61zF%252BkUnMFELmvW2jyKAzT7FAad59rLcupGLDvpa4d59NfCUd%252F5Sb%252BLFTEtk1d0Dp9gAFTEvOITuLHn0c9H2YxXG6C1YDhprFl1ca1NPFScIavGgNvNYfz739b0zqUMShCt%252FveaYNBSfs2Ja9adsAYKnSmm0OEwUZ%252BW86dZ2kheBdlg95E0SuPtd%252FG7PVziDbvrS3hZjFfIw4ynmpqAAeSDKsgj6kdwzQM9QNElR%252B69CJ%252FiLhF3gG2i%252F8bO5JTHjTxQFYuCH7bMsv4BtE4QbnsZVnP7g8ve26ReMwug%252F0bvr%252FtAZ3bx%252FP8XoAw%253D%253D%26RelayState%3D%252F%26SigAlg%3Dhttp%253A%252F%252Fwww.w3.org%252F2000%252F09%252Fxmldsig%2523rsa-sha1%26Signature%3DTt2v5J4iLGiCWAZy1OvWbgjc93hORZDfCHxkk2WSnVWIXVucRmjzDalrxjE6ND96lDjYxV8nzcuDMAReZKFOf%252B9uS9EOUhC%252FpMRgrdrdz4dMQ5x4Y5XC%252F9W1VWMGDtL%252FwxIXNbSj5UH8T4zGLtCEB6IvDBi6w5dfNxJgtae71L8cJ2wf1b%252BOWJ7yAUZVCN3ZUUkrrYg6UJPCt8CqNo%252FGfwyeN8wtb4T25%252FDhJzhIpmWmjLJFgZTZqcvYpE7NKkwWv6FZ8oO4iRti94O2829xVNV5oXDJ03E9MdoL9JnltlmHSV97WUYb8OKz9mqqnucfZMloUlGmIhp2wGLCEU8%252BZM4lLjt%252F5Z0FD0icF02v9eFvi3gg9lg4%252F7Xu7%252BhnHrw2bH83mCsqNQlJW3eguz8BRYgRFEG8naVRX7MrmiPejZ2146l9GssJxh0naB4YgQDjhb01gYBqiLBliFIWDvrgGF%252BY2heuYVG3dE80MTYsc14zD9G49S5ib0g679P4m5HEx2D2eZEkxRVgGDb58ej5igd%252FWpQ3JAkyq6%252FqU595jyhWa8HlCH9UeyMXSwtgakHABbewF%252Bug%252F6jN%252F61XYhpmb9Wa5fugZNKC5E9jSo2FZUrJnTx0ShCnRwITUcwKM75wLI6ra33eLuMmwv3t6H6%252BFhKiDiGmnMyqBPzhFrwgzVg%253D%26id%3DSalesforceSIT");
		driver.findElement(By.xpath("//input[@name='Ecom_User_ID']")).sendKeys("u589831");
		driver.findElement(By.xpath("//input[@name='Ecom_Password']")).sendKeys("Testa10k");
		driver.findElement(By.xpath("//input[@name='Ecom_Password']")).submit();
	}
		
	public static class IrA {
		
		public static class CajonDeAplicaciones {
			
			public static void ConsolaFAN() {
				driver.switchTo().defaultContent();
				driver.findElement(By.xpath("//span[@id='tsidLabel']")).click();
				driver.findElement(By.xpath("//a[contains(.,'Consola FAN')]")).click();
				try {
					(new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".x-window.x-window-plain.x-window-dlg")));
					driver.findElement(By.tagName("button")).click();
					driver.findElement(By.xpath("//span[@id='tsidLabel']")).click();
					driver.findElement(By.xpath("//a[contains(.,'Consola FAN')]")).click();
				} catch (TimeoutException e) {}
				
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				try {driver.switchTo().alert().accept();} catch (org.openqa.selenium.NoAlertPresentException e) {}
			}
			
			public static void Ventas() {
				HomeBase homePage = new HomeBase(driver);
			    String a = driver.findElement(By.id("tsidLabel")).getText();
				if (a.contains("Ventas")){}
			    else {
			    	homePage.switchAppsMenu();
			    	try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			    	homePage.selectAppFromMenuByName("Ventas");
			    	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}            
			    }
				//driver.switchTo().defaultContent();
				//driver.findElement(By.xpath("//span[@id='tsidLabel']")).click();
				//driver.findElement(By.xpath("//a[contains(.,'Ventas')]")).click();
			}
		}
	}
		
	public static WebDriverWait dynamicWait() {
		WebDriverWait ed = new WebDriverWait(driver, 7);
		return ed;
	}

	public static class waitFor {
		public static void visibilityOfAllElements(List<WebElement> elems) {
			dynamicWait().until(ExpectedConditions.visibilityOfAllElements(elems));
		}

		public static void visibilityOfElement(WebElement elem) {
			dynamicWait().until(ExpectedConditions.visibilityOf(elem));
		}

		public static void elementToBeSelected(WebElement elem) {
			dynamicWait().until(ExpectedConditions.elementToBeSelected(elem));
		}

		public static void attributeContains(WebElement elem, String atrib, String valor) {
			dynamicWait().until(ExpectedConditions.attributeContains(elem, atrib, valor));
		}

		public static void elementToBeClickable(WebElement elem) {
			dynamicWait().until(ExpectedConditions.elementToBeClickable(elem));
		}
	}
	
	private void sleepPrivado(int miliseconds) {
		try {Thread.sleep(miliseconds);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void sleep(int miliseconds) {
		sleepPrivado(miliseconds + 5000);
	}

	public List<String> obtenerElAtributoDeLosElementos(String atributo, List<WebElement> elementos) {
		List<String> valores = new ArrayList<String>();
		for (WebElement elem : elementos) {
			valores.add(elem.getAttribute(atributo));
		}
		return valores;
	}

	public Boolean esObligatorio(WebElement campo) {
		return campo.getAttribute("class").contains("ng-invalid-required");
	}

	public Boolean esValido(WebElement campo) {
		return (!campo.getAttribute("class").contains("invalid"));
	}

	public String obtenerValorDelCampo(WebElement campo) {
		return campo.getAttribute("value");
	}

	public String fechaDeHoy() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return (dateFormat.format(date));
	}

	public void selectByText(WebElement element, String data) {
		Select select = new Select(element);
		select.selectByVisibleText(data);
	}
	
	public int getIndexFrame(WebDriver driver, By byForElement) { // working correctly
		int index = 0;
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(byForElement).getText();
				driver.findElement(byForElement).isDisplayed();
				driver.switchTo().defaultContent();
				return index;
			} catch (NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();
			}
		}
		return -1;
	}
	
	public WebElement cambioFrame(WebDriver driver, By byForElement) {
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		try {
			return frames.get(getIndexFrame(driver, byForElement));
		} catch (ArrayIndexOutOfBoundsException iobExcept) {
			return null;
		}
	}
	
	// Metodo para obtener el dato deseado del excel indicando la hoja o pesta;a donde se encuentra (se agrupa por modulo)
	public String buscarCampoExcel(int hoja, String desc, int columna) throws IOException {
		String campo = null;
		File archivo = new File("Cuentas.xlsx");
		FileInputStream file = new FileInputStream(archivo);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(hoja);
		Iterator<Row> rows = sheet.rowIterator();
		System.out.println("Aquiiiii");
		System.out.println(rows.next().getCell(0).getStringCellValue());
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			// System.out.println(row.getCell(0).getStringCellValue());
			if (row.getCell(0).getStringCellValue().toLowerCase().contains(desc.toLowerCase())) {
				try {
					campo = row.getCell(columna).getStringCellValue();
				} catch (java.lang.IllegalStateException ex1) {
					campo = Double.toString(row.getCell(columna).getNumericCellValue());
					if (campo.contains("E")) {
						campo = Double.toString(row.getCell(columna).getNumericCellValue());
						campo = campo.substring(0, campo.indexOf("E")).replace(".", "");
					}
				}
				break;
			}
		}
		workbook.close();
		return campo;
	}
	
	private String dataProviderCuentas() {
		if (urlAmbiente.contains("sit".toLowerCase())) {
			return "CuentasSIT.xlsx";
		} else if (urlAmbiente.contains("uat")) {
			return "CuentasUAT.xlsx";
		}
		System.out.println("Error de URL!");
		return null;
	}
	
	public String dataProviderE2E() {
		String sDataProviderE2E;
		if (urlAmbiente.contains("sit02")) {
			sDataProviderE2E = "E2ESIT.xlsx";
		} else {
			if (urlAmbiente.contains("uat02")) {
				sDataProviderE2E = "E2EUAT.xlsx";
			} else {
				System.out.println("Error de URL!");
				sDataProviderE2E = null;
			}
		}
		return sDataProviderE2E;
	}

	//========================================== DATA PROVIDER - REGRESION COMPACTA ==========================================\\

	public String dataProviderCompact() {
		if (urlAmbiente.contains("sit02")) {
			return "MicroRegresionSIT.xlsx";
		}
		else if (urlAmbiente.contains("uat")) {
			return "MicroRegresionUAT.xlsx";
		}
		System.out.println("Error de URL!");
		return null;
	}
	
	public void login(WebDriver driver, String Ambiente, String User, String Password ) {
		Login page0 = new Login(driver);
		driver.get(Ambiente);
		sleepPrivado(4000);
	    page0.ingresar(User, Password);
	}
	
	public void buscarYClick(List<WebElement> elements, String match, String texto) {
		sleepPrivado(2000);
		switch (match) {
		case "contains":
			for (WebElement x : elements) {
				if (x.getText().toLowerCase().contains(texto.toLowerCase())) {
					x.click();
					break;
				}
			}
			break;
		case "equals":
			for (WebElement x : elements) {
				if (x.getText().equalsIgnoreCase(texto)) {
					x.click();
					break;
				}
			}
			break;
		}
		sleepPrivado(2000);
	}
	
	public void cambiarListaLightningAVistaClasica(WebDriver driver) {
		try {
			clickBy(driver, By.cssSelector(".bare.branding-userProfile-button.slds-button.uiButton.forceHeaderButton.oneUserProfileCardTrigger"), 0);
			sleepPrivado(1000);
			WebElement wSalesforceClassic = driver.findElement(By.className("profile-card-footer"));
			wSalesforceClassic.findElement(By.tagName("a")).click();
			sleepPrivado(2000);
		} catch (Exception e) {}
	}
	
	public void guardarListaTxt(List<String> datosOrden) throws IOException {
		File archivo = new File("DatosOrdenes.txt");
		FileWriter ArchiSa = new FileWriter(archivo.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(ArchiSa);
		PrintWriter wr = new PrintWriter(bw);
		for (String UnD : datosOrden) {
			wr.append(UnD + "\r\n");
		}
		wr.close();
		bw.close();
		ArchiSa.close();
	}
	
	public void loginCBS(WebDriver driver) {
		driver.get(urlAmbiente);
		sleepPrivado(5000);
		Login lLogin = new Login(driver);
		lLogin.ingresarCBS();
		driver.get("https://10.75.39.140:8081/main.action?ssLogin=true&BMEWebToken=be935f78-f517-441c-a299-c5a1ba3f1f411b7c8915-7f90-4b1d-bee6-15837afe7b05");
	}
	
	public void CambiarPerfil(String perfil, WebDriver driver) {
		/*sleep(2000);
		System.out.println("llegue aqui");
		driver.switchTo().defaultContent();
		driver.findElement(By.id("userNavButton")).click();
		sleep(6000);
		driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a")).get(2).click();
		sleep(6000);
		//driver.findElement(By.id("userDropdown")).click();
		//sleep(3000);
		driver.findElement(By.id("app_logout")).click();
		sleep(5000);*/
//		driver.findElement(By.cssSelector(".menuButton.menuButtonRounded")).click();
//		driver.findElement(By.xpath("//*[text() = 'Finalizar sesi�n']")).click();
//		sleep(5000);
		driver.get(urlAmbiente);
		driver.findElement(By.id("userNav")).click();
		sleepPrivado(2000);
		System.out.println(driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a")).get(4).getText());
		driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a")).get(4).click();
		//driver.findElement(By.id("cancel_idp_hint")).click();
		 switch(perfil.toLowerCase()){
		 case "agente":
			 loginAgente(driver);
			 break;
		 case "telefonico":
			 loginTelefonico(driver);  
			 break;
		 case "ofcom":
			 loginOfCom(driver);
			 break;
		 case "logistica":
			 loginLogistica(driver);
			 break;
		 case "entrega":
			 loginEntrega(driver);
			 break;
		 case "om":
			 login(driver,urlAmbiente, "U585991", "Testa10k");
			 break;
		 case "backoffice":
			 login(driver, urlAmbiente, "uat518122", "Testa10k");
			 break;
		 case "fraude":
			 loginFraude(driver);
		 	break;
		 case "logisticayentrega":
		 	loginLogisticaYEntrega(driver);
		 	break;
		 case "fanfront":
			 loginFANFront(driver);
		 }
		 sleepPrivado(10000);
	}
	
	public void tomarCaptura(WebDriver driver, String imageName) {
	      //Directorio donde quedaran las imagenes guardadas
		File directory;
		if(urlAmbiente.contains("sit"))
			directory = new File("imagenesSIT");
		else
			directory = new File("imagenesUAT");
	    try {
	    	if (directory.isDirectory()) {
	    		//Toma la captura de imagen
	            File imagen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            //Mueve el archivo a la carga especificada con el respectivo nombre
	            FileUtils.copyFile(imagen, new File(directory.getAbsolutePath()   + "\\" + imageName + ".png"));
	         } else {
	            //Se lanza la excepcion cuando no encuentre el directorio
	            throw new IOException("ERROR : La ruta especificada no es un directorio!");
	         }
	      } catch (IOException e) {
	         //Impresion de Excepciones
	         e.printStackTrace();
	      }
   }
	
	public void abrirPestaniaNueva(WebDriver driver) throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_T);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_T);
	}
	
	public void loginCommunity(WebDriver driver) {
		driver.get(urlCommunity);
		sleepPrivado(5000);
	    Login lLogin = new Login(driver);
	    lLogin.ingresarComunidad();
	}
	
	public void guardarLineasNominadas(String data) throws IOException {
		File archivo = new File("DatosNominacion.txt");
		// if (!archivo.exists())
		// FileWriter ArchiSa=new FileWriter(archivo,true);
		// archivo.delete();
		// Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
		FileWriter ArchiSa = new FileWriter(archivo.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(ArchiSa);
		PrintWriter wr = new PrintWriter(bw);
		wr.append(data + "\r\n");
		wr.close();
		bw.close();
		ArchiSa.close();
	}
	
	public String obtenerChargeCode() {
		WebElement box = driver.findElements(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).get(2);
		box.click();
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", box);
		WebElement configure = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown_right.cpq-item-actions-dropdown")).get(2);
		buscarYClick(configure.findElements(By.tagName("a")), "contains", "configure");
		WebElement chargeCode = null;
		for (WebElement x : driver.findElements(By.className("slds-form-element"))) {
			if (x.getText().toLowerCase().contains("charge code")) {
				chargeCode = x;
				break;
			}
		}
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", chargeCode);
		return chargeCode.findElement(By.tagName("input")).getAttribute("value");
	}
	
	public void loginLogisticaYEntrega(WebDriver driver) {
		driver.get(urlAmbiente);
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    Login page0 = new Login(driver);
	    page0.ingresarLogisticaYEntrega();
	}
	
	//====================================================== METODOS JOSE ======================================================\\
	
	private WebElement frameConElElemento (WebDriver driver, By byForElement) {
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		driver.switchTo().defaultContent();
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(byForElement);
				driver.switchTo().defaultContent();
				return frame;
			} catch (NoSuchElementException e) {
				driver.switchTo().defaultContent();
			}
		}
		return null;
	}
	
	public void cambioDeFrame(WebDriver driver, By byForElement, double timeAcumulated) {
		if (10 > timeAcumulated) {
			try {
				WebElement myFrame = frameConElElemento(driver, byForElement);
				driver.switchTo().frame(myFrame);
			} catch (Exception e1) {
				sleepPrivado(250);
				cambioDeFrame(driver, byForElement, timeAcumulated + 0.250);
			}
		}
	}
	
	public WebElement esperarElemento(WebDriver driver, By byForElement, double timeAcumulated) {
		if (10 > timeAcumulated) {
			try {
				return driver.findElement(byForElement);
			} catch (Exception e) {
				sleepPrivado(250);
				return esperarElemento(driver, byForElement, timeAcumulated + 0.250);
			}
		}
		System.out.println("ELEMENTO NO ENCONTRADO! METODO: esperarElemento.");
		return null;
	}

	public void clickBy(WebDriver driver, By byForElement, double timeAcumulated) {
		if (15 > timeAcumulated) {
			try {
				driver.findElement(byForElement).click();
			} catch (Exception e) {
				try {Thread.sleep(250);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				clickBy(driver, byForElement, timeAcumulated + 0.250);
			}
		} else { driver.findElement(byForElement).click(); }
	}
	
	public void elementosRequeridos(WebDriver driver, By byForElements, int numberOfElements, double timeAcumulated) {
		if (10 > timeAcumulated) {
			if (driver.findElements(byForElements).size() < numberOfElements) {
				sleepPrivado(250);
				elementosRequeridos(driver, byForElements, numberOfElements, timeAcumulated + 0.250);
			}
		} else {System.out.println("NO ENCONTRO EL NUMERO DE ELEMENTOS MINIMO INDICADO PARA LA LISTA");}
	}
	
	public String getTextBy(WebDriver driver, By byForElement, double timeAcumulated) {
		if (10 > timeAcumulated) {
			try {
				if (driver.findElement(byForElement).getText().length() > 0) {
					return driver.findElement(byForElement).getText();
				}
			} catch (Exception e) {}
				sleepPrivado(250);
				return getTextBy(driver, byForElement, timeAcumulated + 0.250);
		}
		System.out.println("NO SE ENCONTRO TEXTO EN EL ELEMENTO INDICADO.");
		return null;
	}
	
	public void sendKeysBy(WebDriver driver, By byForElement, String text, double timeAcumulated) {
		if (10 > timeAcumulated) {
			try {
				driver.findElement(byForElement).sendKeys(text);
			} catch (Exception e) {
				sleepPrivado(250);
				sendKeysBy(driver, byForElement, text, timeAcumulated + 0.250);
			}
		} else { driver.findElement(byForElement).sendKeys(text); }
	}
	
	public void lineasPreactivadas(String nombreArchivo, String estado) {
		String ambiente = driver.getCurrentUrl();
		BeFan Botones = new BeFan(driver);
		Botones.andaAlMenu("sims", "gestion");
		Botones.SGSeleccionEstado(estado.substring(0,1).toUpperCase() + estado.substring(1).toLowerCase());
		waitForClickeableAndDropdownValuesToLoad(driver, By.name("vendedores"),By.xpath("/html/body/div[1]/div[2]/div/section/div[1]/div[3]/select"), 10);
		if (ambiente.toLowerCase().contains("sit02")) {
			selectByText(driver.findElement(By.name("vendedores")), "BAS-VJP-BAHIA BLANCA - VJP Punta Alta");
		} else if (ambiente.toLowerCase().contains("uat02")){
			selectByText(driver.findElement(By.name("vendedores")), "PREACTIVE MAS FUERTE QUE TENGO UNA TOALLA - Punto Telecom");
		}
		driver.findElement(By.cssSelector("[class*='col-lg-2'] input")).sendKeys(nombreArchivo);
		Botones.SGClickBuscar();
		Botones.SGClickVerDetalle(1);
		sleep(10000);
		List<WebElement> lineasNominadas = driver.findElements(By.xpath("//div[@class='modal-body']//table//tbody//tr//td[2]"));
		ArrayList<String> lineas = new ArrayList<String>();
		for (WebElement m : lineasNominadas) {
			if (!m.getText().equals("") || m != null) {
				lineas.add(m.getText());
			}
		}
	}
	
	//===================================================== DATA PROVIDER =====================================================\\
	
	@DataProvider
	public Object[][] Nominacion() throws Exception{
		return ExcelUtils.getTableArray("Cuentas.xlsx","Tech",1,1,3);
	}
	
	@DataProvider
	public Object[][] rNuevaNomina() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,14,"NuevaNomina");
	}
	
	@DataProvider
	public Object[][] rExistenteNomina() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,4,"ExistenteNomina");
	}
	
	@DataProvider
	public Object[][] rRecargasTC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,12,"RecargasTC");
	}
	
	@DataProvider
	public Object[][] rSmsDescuento() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,3,"PackSmsDescuento");
	}
	
	@DataProvider
	public Object[][] rDatosEfectivo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,3,"PackDatosEfectivo");
	}
	
	@DataProvider
	public Object[][] rMinutosTC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,12,"PackMinutosTC");
	}
	
	@DataProvider
	public Object[][] rDescuentoTelef() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(), "DatosRegresion", 1, 1, 3,"RenovacionDescuentoTelef");
	}
	
	@DataProvider
	public Object[][] rEfectivoOOCC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,3,"RenovacionEfectivoOOCC");
	}
	
	@DataProvider
	public Object[][] rTCAgente() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"DatosRegresion",1,1,12,"RenovacionTCAgente");
	}

	@DataProvider
	public Object[][] Tech() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Tech", 1, 1, 3);
	}

	@DataProvider
	public Object[][] SalesCuentaInactiva() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 3, "Cuenta Inactiva");
	}

	@DataProvider
	public Object[][] CustomerCuentaActiva() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Customer", 1, 1, 1);
	}

	@DataProvider
	public Object[][] SalesCuentaActiva() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 3, "Cuenta Activa");
	}

	@DataProvider
	public Object[][] SalesContactoSinCuenta() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 2, "Contacto sin cuenta");
	}

	@DataProvider
	public Object[][] SalesBlacklist() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 2, "Blacklist");
	}

	@DataProvider
	public Object[][] SalesCuentaConGestiones() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 3, "Cuenta con gestiones");
	}

	@DataProvider
	public Object[][] SalesCuentaBolsa() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 3, "Cuenta Bolsa");
	}

	@DataProvider
	public Object[][] OMAltaLinea() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 7, "AltaLinea");
	}

	@DataProvider
	public Object[][] OMAltaCompleta() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 10, "AltaLineaC");
	}

	@DataProvider
	public Object[][] OMCambioSim() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 9, "CambioSim");
	}

	@DataProvider
	public Object[][] OMCambioDeSimSiniestro() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 9, "CambioDeSimSiniestro");
	}

	@DataProvider
	public Object[][] OMCambioDeNumero() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 7, "CambioDeNumero");
	}

	@DataProvider
	public Object[][] OMNominacion() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 3, "Nominacion");
	}

	@DataProvider
	public Object[][] SalesPasaporteBolsa() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Sales", 1, 1, 3, "Pasaporte Bolsa");
	}

	@DataProvider
	public Object[][] MarketingCuentaNormal() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Marketing", 1, 1, 1, "Cuenta Normal");
	}

	@DataProvider
	public Object[][] MarketingCuentaConMora() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Marketing", 1, 1, 1, "Cuenta c/ Mora");
	}

	@DataProvider
	public Object[][] MarketingCuentaConFraude() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Marketing", 1, 1, 1, "Cuenta c/ Fraude");
	}
	
	@DataProvider
	public Object[][] MarketingCuentaSinServicio() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Marketing", 1, 1, 1, "Cuenta sin Servicio");
	}
	
	@DataProvider
	public Object[][] MarketingCuentaAtributosYExclusiones() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "Marketing", 1, 1, 1, "Cuenta Atributos y Exclusiones");
	}
	
	@DataProvider
	public Object[][] OMCambioTitularidad() throws Exception {
		return ExcelUtils.getTableArray("Cuentas.xlsx", "OM", 1, 1, 6, "Cambio de Titularidad");
	}
	
	@DataProvider
	public Object[][] DatosSalesNominacion() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "Altas y Nominacion", 1, 1, 12, "Nominacion");
	}

	@DataProvider
	public Object[][] DatosSalesNominacionNuevoOfCom() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 14, "NominacionNuevoOfCom");
	}

	@DataProvider
	public Object[][] NominacionExistenteOfCom() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 4, "NominacionExistenteOfCom");
	}

	@DataProvider
	public Object[][] DatosNoNominacionNuevoAgente() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 7, "NoNominaNuevoAgente");
	}

	@DataProvider
	public Object[][] DatosSalesNominacionNuevoAgente() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 14, "NominacionNuevoAgente");
	}

	@DataProvider
	public Object[][] DatosSalesNominacionNuevoPasaporteOfCom() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 15, "NominacionNuevoPasaporteOfCom");
	}

	@DataProvider
	public Object[][] DatosNoNominaNuevoEdadOfCom() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 4, "NoNominaNuevoEdadOfCom");
	}

	@DataProvider
	public Object[][] DatosNoNominacionNuevoTelefonico() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(), "nominacion", 1, 1, 7, "NoNominaNuevoTelefonico");
	}
	
	@DataProvider
	public Object[][] DatosAltaLineaAgente() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,16,"Alta Linea Agente");
	}

	@DataProvider
	public Object[][] DatosSalesAltaLineaEquipo() throws Exception{ //para verificar, por las dudas no se borro.
		return ExcelUtils.getTableArray("Cuentas.xlsx","PreparacionDatos",1,1,9,"Alta Linea Equip New AG");
	}
	
	@DataProvider
	public Object[][] PerfilCuentaSeiscientos() throws Exception{
		return ExcelUtils.getTableArray("Cuentas.xlsx","PerfilGestiones",1,1,4,"Cuenta Seiscientos");
	}
	
	@DataProvider
	public Object[][] RecargaTC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"recargas",1,1,12,"Recargas TC");
	}
	
	@DataProvider
	public Object[][] DatosAltaLineaOfCom() throws Exception{//verofocadp
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,15,"Alta Linea OFCOM");
	}
	
	@DataProvider
	public Object[][] AltaLineaNuevoAgentePresencial() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,12,"Alta Linea Nuevo Agente Presencial");
	}
	
	@DataProvider
	public Object[][] AltaLineaExistenteOfComPresencial() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,4,"Alta Linea Existente OfCom Presencial");
	}
	
	@DataProvider
	public Object[][] RenovacionCuotaConSaldo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"renovacion",1,1,3,"Renovacion Cuota Con Saldo");
	}
	
	@DataProvider
	public Object[][] RenovacionCuotaSinSaldo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"renovacion",1,1,3,"Renovacion Cuota Sin Saldo");
	}
	
	@DataProvider
	public Object[][] RenovacionCuotaSinSaldoConTC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"renovacion",1,1,14,"Renovacion Cuota TC");
	}
	
	@DataProvider
	public Object[][] RenovacionCuotaconSaldoConTC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"renovacion",1,1,14,"Renovacion Cuota Con TC Con Saldo");
	}
	
	@DataProvider
	public Object[][] NumerosAmigos() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,4,"Numeros Amigos");
	}
	
	@DataProvider
	public Object[][] AltaServicios() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"Alta Servicio");
	}
	
	@DataProvider
	public Object[][] BajaServicios() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"Baja Servicio");
	}
	
	@DataProvider
	public Object[][] VentaPacks() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"Venta de pack saldo");
	}
	
	@DataProvider
	public Object[][] packUruguay() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"packUruguay");
	}
	
	@DataProvider
	public Object[][] ventaPack50Tele() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,14,"Pack 50min Tel");
	}
	
	@DataProvider // no esta en el data provider
	public Object[][] ventaPack50ofic() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"Pack 50 min Oficina");
	}
	
	@DataProvider
	public Object[][] ventaX1Dia() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,14,"packDe1DiaPersonal");
	}
	
	@DataProvider
	public Object[][] ventaPackInternacional30SMS() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,14,"packTelefoInternacional30SMS");
	}
	
	@DataProvider
	public Object[][] ventaPack500min() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"Pack500min");
	}
	
	@DataProvider
	public Object[][] ventaPackA40() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"PackAdela40");
	}
	
	@DataProvider
	public Object[][] ventaPackM2M() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"Pack M2M 10");
	}
	
	@DataProvider
	public Object[][] PackOfCom() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,7,"packOfCom");
	}
	
	@DataProvider
	public Object[][] PackAgente() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"venta de pack",1,1,3,"packAgente");
	}
	
	@DataProvider
	public Object[][] CuentaSuspension() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,5,"SuspensionOficina");
	}
	
	@DataProvider
	public Object[][] CuentaAjustesPRE() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"Ajustes PRE");
	}
	
	@DataProvider
	public Object[][] CuentaAjustesREPRO() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"Ajustes REPRO");
	}
	
	@DataProvider
	public Object[][] CuentaProblemaRecarga() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,2,"ProblemaRecargas");
	}
	
	@DataProvider
	public Object[][] CuentaProblemaRecargaAYD() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,4,"ProblemaRecargaAyD");
	}
	
	@DataProvider
	public Object[][] CuentaProblemaRecargaQuemada() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,4,"ProblemaRecargaQuemada");
	}
	
	@DataProvider
	public Object[][] ProblemaRecargaPrepaga() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,4,"ProblemaRecargaPrepaga");
	}
	
	@DataProvider
	public Object[][] CuentaHabilitacion() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,2,"Habilitacion");
	}
	
	@DataProvider
	public Object[][] RecargaEfectivo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"recargas",1,1,3,"Recarga Efectivo");
	}
	
	@DataProvider
	public Object[][] CambioSimCardTelef() throws Exception{
		return  ExcelUtils.getTableArray(dataProviderE2E(),"cambio de simcard",1,1,7,"SimCard Telef");
	}
	
	@DataProvider
	public Object[][] CambioSimCardAgente() throws Exception{
		return  ExcelUtils.getTableArray(dataProviderE2E(),"cambio de simcard",1,1,2,"Cambio SimCard Agente");
	}
	
	@DataProvider
	public Object[][] CambioSimCardOficina() throws Exception{
		return  ExcelUtils.getTableArray(dataProviderE2E(),"cambio de simcard",1,1,3,"Cambio SimCard Oficina");
	}
	
	@DataProvider
	public Object[][] SimCardSiniestroAG() throws Exception{
		return  ExcelUtils.getTableArray(dataProviderE2E(),"cambio de simcard",1,1,6,"SimCard Siniestro Agente");
	}
	
	@DataProvider
	public Object[][] SimCardSiniestroOfCom() throws Exception{
		return  ExcelUtils.getTableArray(dataProviderE2E(),"cambio de simcard",1,1,2,"SimCard Siniestro OfCom");
	}
	
	@DataProvider
	public Object[][] DatosAltaEquipoExiste() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,5,"Alta Linea Equipo Existe");
	}
	
	@DataProvider
	public Object[][] AltaLineaNuevoEquipoTC() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,22,"Alta Linea Equip New AG Credito");
	}
	
	@DataProvider
	public Object[][] VentaNuevoEquipoOfCom() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,12,"Venta Equipo New OfCom");
	}
	
	@DataProvider
	public Object[][] DatosAltaAgenteCredito() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,17,"Alta Linea AG TC");
	}
	
	@DataProvider
	public Object[][] PerfilCuentaTomRiddleConLinea() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"recargas",1,1,14,"Recargas TC");
	}
	
	@DataProvider
	public Object[][] AltaLineaExistenteOfComTD() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,9,"Alta Linea Existe OfCom Debito");
	}
	
	@DataProvider
	public Object[][] AltaEquipoExisteSPU() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,8,"Linea Equipo Existe SPU");
	}
	
	@DataProvider
	public Object[][] CuentaTriviasYSuscripciones() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni",1,1,2,"Trivias Y Suscripciones");
	}
	
	@DataProvider
	public Object[][] CuentaReintegros() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni",1,1,1,"Reintegros");
	}
	
	@DataProvider
	public Object[][] CuentaModificacionDeDatos() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"Modificacion De Datos");
	}
	
	@DataProvider
	public Object[][] CuentaAnulacionDeVenta() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni",1,1,1,"Anulacion De Venta");
	}
	
	@DataProvider
	public Object[][] CuentaReseteoClave() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni",1,1,1,"Reseteo De Clave");
	}
	
	@DataProvider(name = "SerialInexistente")
	public Object[][] SerialInexistente() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,10,"SerialInexistente");
	}
	
	@DataProvider(name = "SerialConDepositoErroneo")
	public Object[][] SerialConDepositoErroneo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialConDepositoErroneo");
	}
	
	@DataProvider(name = "SerialConFormatoInvalido")
	public Object[][] SerialConFormatoInvalido() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,7,"SerialConFormatoInvalido");
	}
	
	@DataProvider(name = "SerialBalido")
	public Object[][] SerialBalido() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,8,"SerialBalido");
	}
	
	@DataProvider(name = "DosSerialesValidos")
	public Object[][] DosSerialesValidos() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,8,"DosSerialesValidos");
	}
	
	@DataProvider(name = "ArchivoVacio")
	public Object[][] ArchivoVacio() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,7,"ArchivoVacio");
	}
	
	@DataProvider(name = "SerialNoMCVM")
	public Object[][] SerialNoMCVM() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialNoMCVM");
	}
	
	@DataProvider(name = "SerialesNoValidos")
	public Object[][] SerialesNoValidos() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,7,"SerialesNoValidos");
	}
	
	@DataProvider(name = "SerialValidoEterno")
	public Object[][] SerialValidoEterno() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialValidoEterno");
	}
	
	@DataProvider
	public Object[][] AltaLineaNuevoconEquipo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,13,"Linea Nueva Equipo AG");
	}
	
	@DataProvider
	public Object[][] AltaLineaEquipoOfCom() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,8,"Linea Equipo Nuevo OfCom");
	}
	
	@DataProvider
	public Object[][] NumerosAmigosModificacion() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,4,"Amigos Modificacion");
	}
	
	@DataProvider
	public Object[][] NumerosAmigosBaja() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,3,"Amigos Baja");
	}
	
	@DataProvider
	public Object[][] RecargaTD() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"recargas",1,1,12,"Recargas TD");
	}
	
	@DataProvider
	public Object[][] VentaExisteEquipoAgTd() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"alta de linea",1,1,9,"Venta Equipo Existe AG Debito");
	}
	
	@DataProvider
	public Object[][] NumerosAmigosLetras() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,2,"Amigos Letras");
	}
	
	@DataProvider
	public Object[][] ConsultaSaldo() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,3,"SaldoConsulta");
	}
	
	@DataProvider
	public Object[][] ProductosyServicios() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni",1,1,1,"Productos y Servicios");
	}
	
	@DataProvider
	public Object[][] validaDocumentacion() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,6,"DocumentacionValida");
	}
	
	@DataProvider
	public Object[][] invalidaDocumentacion() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,6,"DocumentacionInvalida");
	}
	
	@DataProvider
	public Object[][] documentacionVista360() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,1,"VistaDocumentacion");
	}
		
	@DataProvider
	public Object[][] RecargasHistorias() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"RecargaHistoria");
	}
	
	@DataProvider
	public Object[][] DatosSalesNominacionPyRNuevoOfCom() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"nominacion",1,1,12,"NominacionNuevoPyROfCom");
	} 
	
	@DataProvider
	public Object[][] NumerosAmigosNoPersonalAlta() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,4,"No Personal Alta Amigos");
	}
	
	@DataProvider
	public Object[][] NumerosAmigosNoPersonalModificacion() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,4,"No Personal Modificacion Amigos");
	}
	
	@DataProvider
	public Object[][] NumerosAmigosNoPersonalBaja() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"numeros amigos",1,1,3,"No Personal Baja Amigos");
	}
	
	@DataProvider
	public Object[][] serviciotecnicoR() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"servicio tecnico",1,1,7,"Servicio Tecnico Reparacion");
	}
	
	@DataProvider
	public Object[][] serviciotecnicoC() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"servicio tecnico",1,1,7,"Servicio Tecnico Configuracion");
	}
	
	@DataProvider
	public Object[][] Diagnostico() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"DiagnInconveniente");
	}
	
	@DataProvider
	public Object[][] BaseDeConocimiento() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"DatosConocimientos");
	}
	
	@DataProvider
	public Object[][] LineasNominadas() throws Exception {
		return ExcelUtils.getTableArray(dataProviderE2E(),"ListaLineas",1,1,1,"LineaNominada");
	}
	
	@DataProvider
	public Object[][] CuentaModificacionDeDNI() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"dni linea",1,1,2,"Modificacion De DNI");
	}	
	
	@DataProvider
	public Object[][] DatosNoNominacionNuevoTelefonicoPasaporte() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"nominacion",1,1,8,"NoNominacionNuevoTelefonicoPasaporte");
	}
	
	@DataProvider
	public Object[][] DatosNoNominacionExistenteAgente() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,5,"NoNominacionExistenteAgente");
	}
	
	@DataProvider
	public Object[][] GestionRegionesCreacion() throws Exception{
		return  ExcelUtils.getTableArray(dataProviderE2E(),"BeFAN",1,1,1,"Creacion Regiones");
	}
	
	@DataProvider
	public Object[][] DatosNominacionExistente5Lineas() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"nominacion",1,1,2,"NominacionExistente5Lineas");
	}
	
	@DataProvider
	public Object[][] DatosNoNominacionNuevoFraudeTelef() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,2,"NoNominacionNuevoFraudeTelef");
	}
	
	@DataProvider
	public Object[][] DatosNoNominacionExistenteFraudeOfcom() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,2,"NoNominacionExistenteFraudeOfcom");
	}
	
	@DataProvider
	public Object[][] DatosNoNominacionExistenteTelefonico() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,3,"NoNominacionExistenteTelefonico");
	}

	@DataProvider
	public Object[][] CuentaVista360() throws Exception{
		return ExcelUtils.getTableArray(dataProviderE2E(),"clientes",1,1,3,"Vista 360");
	}
	
	//=============================================== Metodos Victor =========================================================\\
	
	public void loginBeFANVictor(WebDriver driver, String perfil) {
		driver.get(urlBeFAN);
	    Login page0 = new Login(driver);
		waitForClickeable(driver, By.id("containerTxtMail"));
	    page0.ingresarBeFANVictor(perfil, urlBeFAN);
	}
	
	public Boolean waitForVisible(WebDriver driver, By element, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			System.out.println("Encontro el elemento " + element.toString());
			return true;
		}
		catch (TimeoutException ex) {
			System.out.println("No encontro el elemento " + element.toString());
			return false;
		}
	}
	
	public void sleepFindBy(WebDriver driver, By element, String texto, int timeout) {
		List <WebElement> tigesito = driver.findElements(element);
		for(WebElement x : tigesito) {
			if (x.getText().toLowerCase().equals(texto.toLowerCase())) {
					new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(element));
			}
		}
	}
	
	public String teTraigoLaFecha() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
		return time;
	}
	
	public String teTraigoRandomStrings(String NumeroAleatorio) {
		//Inicializo variables
		String papito = "";
		char[] papito3 = {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'};
		//Guardo cada caracter del string recibido (NumeroAleatorio) y lo guardo en un array de chars llamado papito3
		NumeroAleatorio.getChars(0, 13, papito3, 0);
		//Genero en el array de chars de strings (papito4) los valores alterados del array de chars de numeros (papito3)
		for(int i=0;i<14;i++) {
			switch(papito3[i]) {
			case '0':
				papito = papito + 'a';
				break;
			case '1':
				papito = papito + 'b';
				break;
			case '2':
				papito = papito + 'c';
				break;
			case '3':
				papito = papito + 'd';
				break;
			case '4':
				papito = papito + 'e';
				break;
			case '5':
				papito = papito + 'f';
				break;
			case '6':
				papito = papito + 'g';
				break;
			case '7':
				papito = papito + 'h';
				break;
			case '8':
				papito = papito + 'i';
				break;
			case '9':
				papito = papito + 'j';
				break;
			}
		}
		System.out.println(papito);
		return papito;
	}
	
	public void waitForClickeableAndDropdownValuesToLoad(WebDriver driver, By element, By elementHijo, int timeout) {
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.and(ExpectedConditions.elementToBeClickable(element),ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, elementHijo)));
	}
	
	public void waitForClickeableWithTextAndThenClick(WebDriver driver, By element, String texto, int timeout) {
		List <WebElement> tigesito = driver.findElements(element);
		for(WebElement x : tigesito) {
			if (x.getText().toLowerCase().equals(texto.toLowerCase())) {
				(new WebDriverWait(driver, timeout)).until(ExpectedConditions.elementToBeClickable(x));
				x.click();
			}
		}
	}

}