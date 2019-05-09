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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.Login;
import Pages.setConexion;

//Data provider
import DataProvider.ExcelUtils;


public class TestBase {
	protected static WebDriver driver;
		
		public static String urlAmbiente = "https://telecomcrm--uat02.cs45.my.salesforce.com/";
		//public static String urlAmbiente = "https://telecomcrm--SIT02.cs91.my.salesforce.com";
		
		// viejo public String urlSCP = "https://telecomcrm--uat.cs8.my.salesforce.com";
		public static String urlSCP = "https://telecomcrm--uat.cs53.my.salesforce.com";
		
		//public static String urlComunidad = "https://uat-autogestion-uat.cs53.force.com/clientes/s/";
		public static String urlCommunity = "https://sit-scrumcella.cs14.force.com/clientes/s/";
		
		public static String urlFlow	= "https://webgestionmoviltesting/default.aspx";
		
		public static String urlBeFAN = "http://snapuat.telecom.com.ar/#/home";
		
		public static boolean activarFalsos = true;
		
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
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void goToLeftPanel2(WebDriver driver, String selection) {
		
		driver.switchTo().defaultContent();
		try {
			driver.findElement(By.className("x-btn-split"));
		}catch(NoSuchElementException noSuchElemExcept) {
			List<WebElement> frames = driver.findElements(By.tagName("iframe"));
			for (WebElement frame : frames) {
				try {
					driver.findElement(By.className("x-btn-split"));
					break;
				}catch(NoSuchElementException noSuchElemExceptInside) {
					driver.switchTo().defaultContent();
					driver.switchTo().frame(frame);
				}
			}
		}
		WebElement dropDown = driver.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(dropDown, 245, 20).click().build().perform();
		List<WebElement> options = driver.findElements(By.tagName("li"));
		for(WebElement option : options) {
			if(option.findElement(By.tagName("span")).getText().toLowerCase().equals(selection.toLowerCase())) {
				option.findElement(By.tagName("a")).click();
				//System.out.println("Seleccionado"); //13/09/2017 working.
				break;
			}
		}
	}
	
	public void goToLeftPanel3(WebDriver driver, String selection) {
		
		driver.switchTo().defaultContent();
		try {
			driver.findElement(By.className("x-btn-split"));
		}catch(NoSuchElementException noSuchElemExcept) {
			List<WebElement> frames = driver.findElements(By.tagName("iframe"));
			for (WebElement frame : frames) {
				try {
					driver.findElement(By.className("x-btn-split"));
					break;
				}catch(NoSuchElementException noSuchElemExceptInside) {
					driver.switchTo().defaultContent();
					driver.switchTo().frame(frame);
				}
			}
		}
		WebElement dropDown = driver.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		

		driver.findElement(By.className("x-btn-split")).click();
		//WebElement wMenu = driver.findElement(By.xpath("//li[contains(@class,'x-menu-list-item')]"));
		
		
		List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'x-menu-list-item')]"));
		for(WebElement option : options) {
			if(option.findElement(By.tagName("span")).getText().toLowerCase().equals(selection.toLowerCase())) {
				CustomerCare cc = new CustomerCare(driver);
				option.findElement(By.tagName("a")).click();
				//System.out.println("Seleccionado"); //13/09/2017 working.
				break;
			}
		}
	}
	
	public void goToLeftPanel4(WebDriver driver, String selection) {
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
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//if(driver.findElement(By.id("idcard")).isDisplayed())
		//{
	    Login page0 = new Login(driver);
	    page0.ingresar();
		//}else{
			//driver.findElement(By.id("chooser")).click();
	//	}
	}
	public void loginDani(WebDriver driver) {
		driver.get(urlAmbiente);
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//if(driver.findElement(By.id("idcard")).isDisplayed())
		//{
	    Login page0 = new Login(driver);
	    page0.ingresarDani();
		//}else{
			//driver.findElement(By.id("chooser")).click();
	//	}
	}
	
	public void loginMarketing(WebDriver driver) {
		driver.get(urlAmbiente);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    Login lLogin = new Login(driver);
	    lLogin.ingresarDani();
	}
	
	public void loginSCPAdmin(WebDriver driver) {
	     driver.get("https://telecomcrm--uat.cs8.my.salesforce.com");
	     try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	       Login page0 = new Login(driver);
	       page0.ingresarAdminSCP();
	   }
	   
	public void loginSCPconTodo(WebDriver driver) {
	     driver.get(urlSCP);
	     try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	       Login page0 = new Login(driver);
	       page0.ingresarSCPconTodo();
	   }
	   
	     public void loginSCPUsuario(WebDriver driver) {
	       driver.get(urlSCP);
	       try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	         Login page0 = new Login(driver);
	         page0.ingresarUsuarioSCP();
	     }
	     
	     
	     public void loginSCPAdminServices(WebDriver driver) {
		       driver.get(urlSCP);
		       try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		         Login page0 = new Login(driver);
		         page0.ingresarSCPAdminServices();
		     }
	     public void loginSCPConPermisos(WebDriver driver) {
		       driver.get(urlSCP);
		       try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		         Login page0 = new Login(driver);
		         page0.ingresarSCPConPermisos();
		     }
	     
	public void omInternalLoginWithCredentials(WebDriver driver, String userName, String password) {
		driver.navigate().to(urlAmbiente);
		driver.findElement(By.xpath("//*[@id=\"idp_hint\"]/button")).click();
		sleep(3000);
//		driver.findElement(By.name("Ecom_User_ID")).sendKeys(userName);
//		driver.findElement(By.name("Ecom_Password")).sendKeys(password);
//		driver.findElement(By.id("loginButton2")).click();
	}
	
	public void omLogout(WebDriver driver) {
		driver.findElement(By.id("userNav")).click();
		sleep(2000);
		driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a")).get(3).click();
	}
	     
	public void login1(WebDriver driver) {
		driver.get("https://goo.gl/ETjDYJ");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//if(driver.findElement(By.id("idcard")).isDisplayed())
		//{
	    Login page0 = new Login(driver);
	    page0.ingresar();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

	}	
	public void IngresarCred(WebDriver driver) {
		
	    Login page0 = new Login(driver);
	    page0.ingresar();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

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
	
		//}else{
		//	driver.findElement(By.id("chooser")).click();
		//}

	    
/*public void waitFor(WebDriver driver, By element) {
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))

				  .until(ExpectedConditions.presenceOfElementLocated(element));}

	/*public void waitFor2(WebDriver driver, By element) {
		WebElement myDynamicElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(element));
	}
=======

				  .until(ExpectedConditions.presenceOfElementLocated(element));
*/
	
	

	
	//Sales Fase 3
		
	
		public void loginsales(WebDriver driver, String tipo){
	
		driver.get(urlAmbiente);
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    Login page0 = new Login(driver);
	    switch (tipo){
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
		/**Ingresa con los datos de la cuenta Andres
		 * Para el Modulo Sales tiene vinculado el perfil de Agente y Atenciï¿½n a clientes		 */
//		public void loginAgente(WebDriver driver) {
//			driver.get(urlAmbiente);
//			//try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
//			waitForClickeable(driver, By.id("idp_section_buttons"));
//		    Login page0 = new Login(driver);
//		    page0.ingresarAndres();
//		}
//		
		public void loginGeneral(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarGeneral();
		}
		/**Ingresa con los datos de la cuenta Elena
		 * Para el Modulo Sales tiene vinculado el perfil de Call center		 */
//		public void loginTelefonico(WebDriver driver) {
//			driver.get(urlAmbiente);
//			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
//		    Login page0 = new Login(driver);
//		    page0.ingresarElena();
//		}
	
		public void loginLogistica(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarLogistica();
		}
		public void loginEntrega(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarEntrega();
		}
		/**Ingresa con los datos de la cuenta Francisco
		 * Para el Modulo Sales tiene vinculado el perfil de Vendedor Oficina Comercial		 */
		public void loginFranciso(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarFrancisco();
		}
		
		public void loginOfCom(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarNominaciones();
		}
		
		public void loginBackOffice(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarBackOffice();
		}
		
		public void loginflow(WebDriver driver){
			driver.get(urlFlow);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarflow();
		}
		
		public void loginOperativo(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			Login page0 = new Login(driver);
		    page0.ingresarOperativo();
		}
		
		public void loginAdminFuncional(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarLautaro();
		}
		
		
		/**Ingresa con los datos de la cuenta Nicolas.
		 * Para el Modulo Sales tiene vinculado el perfil de Logistica	 */
		public void loginNicolas(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarNicolas();
		}
		
		/**Ingresa con los datos de la cuenta de Marcela
		 * Para el Modulo Sales tiene vinculado el perfil de Entregas y Configuraciones	 */
		public void loginMarcela(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarMarcela();
		}
		public void loginFabiana(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarFabiana();
		}
		public void loginVictor(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarVictor();
		}
		
		public void loginBeFAN(WebDriver driver) {
			driver.get(urlBeFAN);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarBeFAN();
		}
		
		public void loginBeFANConfigurador(WebDriver driver) {
			driver.get(urlBeFAN);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarBeFANConfigurador();
		}
		
		public void loginFraude(WebDriver driver) {
			driver.get(urlAmbiente);
			//try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			waitForClickeable(driver, By.id("idp_section_buttons"));
		    Login page0 = new Login(driver);
		    page0.ingresarFraude();
		}
		
		//Ingreso a Merge
		public void loginOOCC(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarOOCC();
		}
		
		public void loginTelefonico(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarTelefonico();
		}
		
		public void loginAgente(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarAgente();
		}
		
		public void loginFANFront(WebDriver driver) {
			driver.get(urlAmbiente);
			try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    Login page0 = new Login(driver);
		    page0.ingresarFANFront();
		}
		
		public void elegirmodulo(String modulo){
			try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			String a = driver.findElement(By.id("tsidLabel")).getText();
			driver.findElement(By.id("tsidLabel")).click();
			
			List <WebElement> mdls = driver.findElements(By.cssSelector(".menuButtonMenuLink"));
			try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

			if(a.equals("Ventas"))
			{			try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

				for(WebElement e: mdls){
					if(e.getText().toLowerCase().equals(modulo)){
						e.click();
						}break;}
			}else{
				for(WebElement e: mdls){
					if(e.getText().toLowerCase().equals("ventas")){
						e.click();
						}break;}
				try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				driver.findElement(By.id("tsidLabel")).click();
				for(WebElement e: mdls){
					if(e.getText().toLowerCase().equals(modulo)){
						e.click();
						}break;}
			}
		}
		
		
		public boolean verificarContenidoLista(String[] consultar,List <WebElement> Lista) {
		 
		     List<String> titleTabla = new ArrayList<String>();
		     for(WebElement a : Lista) {
		       titleTabla.add(a.getText().toLowerCase());
		       System.out.println(a.getText());//Para Verificar que este imprimiendo el texto que buscamos
		     }     
		     for(String a:consultar) {
		      if(!(titleTabla.contains(a))) {
		    	  System.out.println("fallo en "+a);
		    	  return false;
		      }
		     }
		     return true;
		}
		/**
		 * Desarrollado para ir a consola Fan en F3. (primero va ventas y luego retorna a consola fan)
		 * @param driver
		 * @author Almer Fase 3
		 */
		public void goInitToConsolaFanF3(WebDriver driver) {
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
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
		       try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		       try {
					driver.switchTo().alert().accept();
					driver.switchTo().defaultContent();
				}
				catch(org.openqa.selenium.NoAlertPresentException e) {}
		}
		
		/**
		 * Selecciona una Cuenta segun el Nombre
		 * @param driver
		 * @author Almer Fase 3
		 */
		public void seleccionCuentaPorNombre(WebDriver driver, String nombreCuenta) {
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			goToLeftPanel2(driver, "Cuentas");
			WebElement frame0 = driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(frame0);
			Select field = new Select(driver.findElement(By.name("fcf")));
			field.selectByVisibleText("Vista Tech");
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			List<WebElement> accounts = driver.findElements(By.xpath("//*[text() = '"+nombreCuenta+"']"));
			accounts.get(0).click();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.switchTo().defaultContent();
		}
		
		/**
		 * Selecciona una Cuenta segun el Nombre
		 * @param driver
		 * @author Almer Fase 3
		 */
		public void searchAndClick(WebDriver driver, String busqueda) {
			
			Accounts view=new Accounts(driver);
			try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			view.deployEastPanel();
			
			BasePage searchImput=new BasePage();
			List<WebElement> frame1 = driver.findElements(By.tagName("iframe"));
			int indexFrame = searchImput.getIndexFrame(driver, By.xpath("/html/body/div/div[1]/ng-include/div/div[1]/ng-include/div/div[2]/input"));
			driver.switchTo().frame(frame1.get(indexFrame));
			WebElement elemento = driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div/div[1]/ng-include/div/div[2]/input"));
			//Escribe en campo de busqueda
			elemento.sendKeys(busqueda);
			
			//Click en el resultado buscado
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			WebElement resultado= driver.findElement(By.xpath("//*[text() = '"+busqueda+"']"));
			resultado.click();
			driver.switchTo().defaultContent();
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			
			
		}
		
		
	/////////////////
	
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
		
	public void sleep(int miliseconds) {
		if(urlAmbiente.contains("sit"))
			try {Thread.sleep(miliseconds+5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		else
			try {Thread.sleep(miliseconds+5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
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
		sleep(300);
		return (!campo.getAttribute("class").contains("invalid"));
	}
	
	public String obtenerValorDelCampo(WebElement campo) {
		return campo.getAttribute("value");
	}
	
	public String fechaDeHoy() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return(dateFormat.format(date));
	}
	
	public void selectByText(WebElement element, String data){
		Select select = new Select(element);
		select.selectByVisibleText(data);
		sleep(2000);
	}
	
	public int getIndexFrame(WebDriver driver, By byForElement) { //working correctly
		//TODO: Do the same for a WebElement instead of a By.
		int index = 0;
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for(WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);

				driver.findElement(byForElement).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(byForElement).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().defaultContent();
				return index;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();
			}
		}
		return -1; //if this is called, the element wasnt found.
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
	
	//Metodo para obtener el dato deseado del excel indicando la hoja o pesta;a donde se encuentra (se agrupa por modulo)
	public String buscarCampoExcel(int hoja, String desc, int columna) throws IOException
	{
		String Campo = null;
		 File archivo = new File("Cuentas.xlsx");
		 FileInputStream file = new FileInputStream(archivo);
	     XSSFWorkbook workbook = new XSSFWorkbook(file); 
	     XSSFSheet sheet = workbook.getSheetAt(hoja);
	     Iterator<Row> rows = sheet.rowIterator();
	    // rows.next();
	     System.out.println("Aquiiiii");
	     System.out.println(rows.next().getCell(0).getStringCellValue());
	     while (rows.hasNext()) {
	    	 
		    XSSFRow row = (XSSFRow) rows.next();
		   // System.out.println(row.getCell(0).getStringCellValue());
		    if (row.getCell(0).getStringCellValue().toLowerCase().contains(desc.toLowerCase())){
		    	try {Campo = row.getCell(columna).getStringCellValue();}
		    	catch (java.lang.IllegalStateException ex1) 
		    	{  
		    		Campo = Double.toString(row.getCell(columna).getNumericCellValue());
		    		if(Campo.contains("E")) 
		    		{	
		    			Campo = Double.toString(row.getCell(columna).getNumericCellValue());
		    			Campo = Campo.substring(0, Campo.indexOf("E")).replace(".","" );
		    		}
		    	}
		    	break;
		    }
		 }
		return (Campo);
	}
	
	private String dataProviderCuentas() {
		String sDataProviderCuentas;
		
		if (urlAmbiente.contains("sit".toUpperCase())) {
			sDataProviderCuentas = "CuentasSIT.xlsx";
		}
		else {
			if (urlAmbiente.contains("uat")) {
				sDataProviderCuentas = "CuentasUAT.xlsx";
			}
			else {
				System.out.println("Error de URL!");
				sDataProviderCuentas = null;
			}
		}
		
		return sDataProviderCuentas;
	}
	
	private String dataProviderE2E() {
		String sDataProviderE2E;
		
		if (urlAmbiente.contains("SIT")) {
			sDataProviderE2E = "E2ESIT.xlsx";
		}
		else {
			if (urlAmbiente.contains("uat")) {
				sDataProviderE2E = "E2EUAT.xlsx";
			}
			else {
				System.out.println("Error de URL!");
				sDataProviderE2E = null;
			}
		}
		
		return sDataProviderE2E;
	}
	
	@DataProvider
	public Object[][] Tech() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Tech",1,1,3);

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] SalesCuentaInactiva() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,3,"Cuenta Inactiva");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] CustomerCuentaActiva() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Customer",1,1,1);

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] SalesCuentaActiva() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,3,"Cuenta Activa");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] SalesContactoSinCuenta() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,2,"Contacto sin cuenta");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] SalesBlacklist() throws Exception{

		 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,2,"Blacklist");
		 return (testObjArray);

	}
	
	
	@DataProvider
	public Object[][] SalesCuentaConGestiones() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,3,"Cuenta con gestiones");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] SalesCuentaBolsa() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,3,"Cuenta Bolsa");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] OMAltaLinea() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,7,"AltaLinea");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] OMAltaCompleta() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,10,"AltaLineaC");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] OMCambioSim() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,9,"CambioSim");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] OMCambioDeSimSiniestro() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,9,"CambioDeSimSiniestro");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] OMCambioDeNumero() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,7,"CambioDeNumero");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] OMNominacion() throws Exception{

		 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,3,"Nominacion");

		 return (testObjArray);

		}
	
	@DataProvider
	public Object[][] SalesPasaporteBolsa() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Sales",1,1,3,"Pasaporte Bolsa");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] MarketingCuentaNormal() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Marketing",1,1,1,"Cuenta Normal");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] MarketingCuentaConMora() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Marketing",1,1,1,"Cuenta c/ Mora");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] MarketingCuentaConFraude() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Marketing",1,1,1,"Cuenta c/ Fraude");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] MarketingCuentaSinServicio() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Marketing",1,1,1,"Cuenta sin Servicio");

	 return (testObjArray);

	}
	/**
	 * Inicia session en Login interno
	 * @param driver
	 * @param Ambiente
	 * @param User
	 * @param Password
	 */
	public void login(WebDriver driver, String Ambiente, String User, String Password ) {
		driver.get(Ambiente);
		sleep(4000);
	    Login page0 = new Login(driver);
	    page0.ingresar(User, Password);
	}
	
	@DataProvider
	public Object[][] MarketingCuentaAtributosYExclusiones() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","Marketing",1,1,1,"Cuenta Atributos y Exclusiones");

	 return (testObjArray);

	}
	
	public void buscarYClick(List <WebElement> elements, String match, String texto) {
		sleep(2000);
		switch (match) {
		case "contains":
			for (WebElement x : elements) {
				if (x.getText().toLowerCase().contains(texto)) {
					x.click();
					break;
				}
			}
			break;
		case "equals":
			for (WebElement x : elements) {
				if (x.getText().toLowerCase().equals(texto)) {
					x.click();
					break;
				}
			}
			break;
		}
		sleep(2000);
	}
	
	public void cambiarListaLightningAVistaClasica(WebDriver driver) {
		try {
			sleep(2000);
			driver.findElement(By.cssSelector(".bare.branding-userProfile-button.slds-button.uiButton.forceHeaderButton.oneUserProfileCardTrigger")).click();
			sleep(1000);
			WebElement wSalesforceClassic = driver.findElement(By.className("profile-card-footer"));
			wSalesforceClassic.findElement(By.tagName("a")).click();
			sleep(2000);
		}
		catch(Exception ex) {
			//Always Empty
		}
	}
	
	@DataProvider
	public Object[][] OMCambioTitularidad() throws Exception{

		Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","OM",1,1,6,"Cambio de Titularidad"); 

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] DatosSalesNominacion() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,12,"Nominacion");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] DatosSalesNominacionNuevoOfCom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,12,"NominacionNuevoOfCom");

	 return (testObjArray);

	}
	
	
	@DataProvider
	public Object[][] DatosSalesNominacionExistenteOfCom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,2,"NominacionExistenteOfCom");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] DatosNoNominacionNuevoAgente() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,7,"NoNominaNuevoAgente");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] DatosSalesNominacionNuevoAgente() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,12,"NominacionNuevoAgente");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] DatosSalesNominacionNuevoPasaporteOfCom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,13,"NominacionNuevoPasaporteOfCom");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] DatosNoNominaNuevoEdadOfCom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,4,"NoNominaNuevoEdadOfCom");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] DatosNoNominacionNuevoTelefonico() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,7,"NoNominaNuevoTelefonico");

	 return (testObjArray);

	}
	//
	
	@DataProvider
	public Object[][] DatosAltaLineaAgente() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,16,"Alta Linea Agente");

	 return (testObjArray);

	}

	@DataProvider
	public Object[][] DatosSalesAltaLineaEquipo() throws Exception{ //para verificar, por las dudas no se borro.

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","PreparacionDatos",1,1,9,"Alta Linea Equip New AG");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] PerfilCuentaSeiscientos() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray("Cuentas.xlsx","PerfilGestiones",1,1,4,"Cuenta Seiscientos");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] RecargaTC() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,14,"Recargas TC");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] DatosAltaLineaOfCom() throws Exception{//verofocadp

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,9,"Alta Linea OFCOM");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] AltaLineaNuevoAgentePresencial() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,12,"Alta Linea Nuevo Agente Presencial");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] AltaLineaExistenteOfComPresencial() throws Exception{//verificado

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,4,"Alta Linea Existente OfCom Presencial");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] RenovacionCuotaConSaldo() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"Renovacion Cuota Con Saldo");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] RenovacionCuotaSinSaldo() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"Renovacion Cuota Sin Saldo");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] RenovacionCuotaSinSaldoConTC() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,13,"Renovacion Cuota TC");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] RenovacionCuotaconSaldoConTC() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,13,"Renovacion Cuota Con TC Con Saldo");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] NumerosAmigos() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"Numeros Amigos");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] AltaServicios() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"Alta Servicio");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] BajaServicios() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"Baja Servicio");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] VentaPacks() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"Venta de pack saldo");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] packUruguay() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"packUruguay");

	 return (testObjArray);

	}
	
	
	@DataProvider
	public Object [][] ventaPack50Tele() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,14,"Pack 50min Tel");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ventaPack50ofic() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"Pack 50 min Oficina");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ventaX1Dia() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,14,"packDe1DiaPersonal");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ventaPackInternacional30SMS() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,14,"packTelefoInternacional30SMS");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ventaPack500min() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"Pack500min");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ventaPackA40() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"PackAdela40");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ventaPackM2M() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"Pack M2M 10");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] PackOfCom() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,7,"packOfCom");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] PackAgente() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"packAgente");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaSuspension() throws Exception {
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,5,"SuspensionOficina");
		
		return(testObjArray);
		
	}
	
	
	@DataProvider
	public Object [][] CuentaAjustesPRE() throws Exception {
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"Ajustes PRE");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] CuentaAjustesREPRO() throws Exception {
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"Ajustes REPRO");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] CuentaProblemaRecarga() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"ProblemaRecargas");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] CuentaProblemaRecargaAYD() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"ProblemaRecargaAyD");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] CuentaProblemaRecargaQuemada() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"ProblemaRecargaQuemada");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] ProblemaRecargaPrepaga() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"ProblemaRecargaPrepaga");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object [][] CuentaHabilitacion() throws Exception {
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"Habilitacion");
		
		return (testObjArray);
	} 
	
	public void guardarListaTxt(List<String> datosOrden) throws IOException {
		File archivo=new File("DatosOrdenes.txt");
		/*if (!archivo.exists())
			FileWriter ArchiSa=new FileWriter(archivo,true);
			//archivo.delete();*/
		//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
		FileWriter ArchiSa=new FileWriter(archivo.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(ArchiSa);
		PrintWriter wr = new PrintWriter(bw); 
		for(String UnD: datosOrden) {
			wr.append(UnD+"\r\n");
		}
		wr.close();
		bw.close();
		ArchiSa.close();
	}
	
	public void loginCBS(WebDriver driver) {
		driver.get(urlAmbiente);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
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
//		driver.findElement(By.xpath("//*[text() = 'Finalizar sesión']")).click();
//		sleep(5000);
		driver.get(urlAmbiente);
		driver.findElement(By.id("userNav")).click();
		sleep(2000);
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
		 sleep(10000);
	}
	
	@DataProvider
	public Object[][] RecargaEfectivo() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,3,"Recarga Efectivo");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] CambioSimCardTelef() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,6,"SimCard Telef");
		
		return (testObjArray);
	}
	
	
	@DataProvider
	public Object[][] CambioSimCardAgente() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"Cambio SimCard Agente");
		
		return (testObjArray);
	}
	@DataProvider
	public Object[][] CambioSimCardOficina() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"Cambio SimCard Oficina");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object[][] SimCardSiniestroAG() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,6,"SimCard Siniestro Agente");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object[][] SimCardSiniestroOfCom() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"SimCard Siniestro OfCom");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object[][] SimCardSiniestroTelef() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,6,"SimCard Siniestro Telef");
		
		return (testObjArray);
	}
	
	@DataProvider
	public Object[][] DatosAltaEquipoExiste() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,5,"Alta Linea Equipo Existe");

	 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] AltaLineaNuevoEquipoTC() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,22,"Alta Linea Equip New AG Credito");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] VentaNuevoEquipoOfCom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,12,"Venta Equipo New OfCom");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] DatosAltaAgenteCredito() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,17,"Alta Linea AG TC");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] PerfilCuentaTomRiddleConLinea() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,14,"Recargas TC");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] AltaLineaExistenteOfComTD() throws Exception{//verificado

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,9,"Alta Linea Existe OfCom Debito");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] AltaEquipoExisteSPU() throws Exception{//verificado
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,8,"Linea Equipo Existe SPU");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaTriviasYSuscripciones() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2ESinPago",1,1,2,"Trivias Y Suscripciones");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaReintegros() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2ESinPago",1,1,1,"Reintegros");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaModificacionDeDatos() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"Modificacion De Datos");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaAnulacionDeVenta() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,1,"Anulacion De Venta");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaReseteoClave() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,1,"Reseteo De Clave");

		 return (testObjArray);
	}
	
	@DataProvider(name = "SerialInexistente")
	public Object[][] SerialInexistente() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,10,"SerialInexistente");

	 return (testObjArray);

	}
	
	@DataProvider(name = "SerialConDepositoErroneo")
	public Object[][] SerialConDepositoErroneo() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialConDepositoErroneo");

	 return (testObjArray);

	}
	
	@DataProvider(name = "SerialConFormatoInvalido")
	public Object[][] SerialConFormatoInvalido() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialConFormatoInvalido");

	 return (testObjArray);

	}
	
	@DataProvider(name = "SerialBalido")
	public Object[][] SerialBalido() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,8,"SerialBalido");

	 return (testObjArray);

	}
	
	@DataProvider(name = "DosSerialesValidos")
	public Object[][] DosSerialesValidos() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,8,"DosSerialesValidos");

	 return (testObjArray);

	}
	
	@DataProvider(name = "ArchivoVacio")
	public Object[][] ArchivoVacio() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"ArchivoVacio");

	 return (testObjArray);

	}
	
	@DataProvider(name = "SerialNoMCVM")
	public Object[][] SerialNoMCVM() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialNoMCVM");

	 return (testObjArray);

	}
	
	@DataProvider(name = "SerialesNoValidos")
	public Object[][] SerialesNoValidos() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialesNoValidos");

	 return (testObjArray);

	}
	
	@DataProvider(name = "SerialValidoEterno")
	public Object[][] SerialValidoEterno() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"SerialValidoEterno");

	 return (testObjArray);

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
	
	@DataProvider
	public Object[][] AltaLineaNuevoconEquipo() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,13,"Linea Nueva Equipo AG");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] AltaLineaEquipoOfCom() throws Exception{//verofocadp

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,8,"Linea Equipo Nuevo OfCom");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] NumerosAmigosModificacion() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"Amigos Modificacion");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] NumerosAmigosBaja() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,3,"Amigos Baja");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] RecargaTD() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,12,"Recargas TD");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] VentaExisteEquipoAgTd() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,9,"Venta Equipo Existe AG Debito");

	 return (testObjArray);

	}
	
	public void abrirPestaniaNueva(WebDriver driver) throws AWTException
	{
		Robot r = new Robot();       
		r.keyPress(KeyEvent.VK_CONTROL); 
		r.keyPress(KeyEvent.VK_T); 
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_T);
	}
	
	@DataProvider
	public Object[][] NumerosAmigosLetras() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"Amigos Letras");

	 return (testObjArray);

	}
	
	@DataProvider
	public Object[][] ConsultaSaldo() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,3,"SaldoConsulta");
		
		return (testObjArray);
		
	}
	
	@DataProvider
	public Object[][] ProductosyServicios() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,1,"Productos y Servicios");
		
		return (testObjArray);
		
	}
	
	public void loginCommunity(WebDriver driver) {
		driver.get(urlCommunity);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    Login lLogin = new Login(driver);
	    lLogin.ingresarComunidad();
	}
	
	@DataProvider
	public Object[][] validaDocumentacion() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"DocumentacionValida");
		
		return (testObjArray);
		
	}
	
	@DataProvider
	public Object[][] invalidaDocumentacion() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"DocumentacionInvalida");
		
		return (testObjArray);
		
	}
	
	@DataProvider
	public Object[][] documentacionVista360() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,1,"VistaDocumentacion");
		
		return (testObjArray);
		
	}
	
	@DataProvider
	public Object[][] CuentaVista360() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,3,"Vista 360");
		
		return (testObjArray); 
		
	}
	
	@DataProvider
	public Object[][] CuentaVista360Version2() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,9,"Vista_2 360");
		
		return (testObjArray); 
		
	}
	
	@DataProvider
	public Object[][] RecargasHistorias() throws Exception{
		
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"RecargaHistoria");
		
		return (testObjArray);
		
	}
	@DataProvider
	public Object[][] DatosSalesNominacionPyRNuevoOfCom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,12,"NominacionNuevoPyROfCom");

	 return (testObjArray);
	} 
	
	@DataProvider
	public Object[][] NumerosAmigosNoPersonalAlta() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"No Personal Alta Amigos");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] NumerosAmigosNoPersonalModificacion() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,4,"No Personal Modificacion Amigos");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] NumerosAmigosNoPersonalBaja() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,3,"No Personal Baja Amigos");

		 return (testObjArray);
	}
		
	@DataProvider
	public Object[][] DiagnosticoInc() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"E2EconPago",1,1,2,"DiagnInconveniente");
		
		return (testObjArray);
	}
	
	public Object[][] serviciotecnicoR() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"Servicio Tecnico Reparacion");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] serviciotecnicoC() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,7,"Servicio Tecnico Configuracion");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] Diagnostico() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"DiagnInconveniente");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] BaseDeConocimiento() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"DatosConocimientos");

		 return (testObjArray);
	}
	
	
	public void guardarLineasNominadas(String data) throws IOException {
		File archivo=new File("DatosNominacion.txt");
		/*if (!archivo.exists())
			FileWriter ArchiSa=new FileWriter(archivo,true);
			//archivo.delete();*/
		//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
		FileWriter ArchiSa=new FileWriter(archivo.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(ArchiSa);
		PrintWriter wr = new PrintWriter(bw); 
		wr.append(data+"\r\n");
		wr.close();
		bw.close();
		ArchiSa.close();
	}
	
	@DataProvider
	public Object[][] LineasNominadas() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"ListaLineas",1,1,1,"LineaNominada");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] CuentaModificacionDeDNI() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"E2EsinPago",1,1,2,"Modificacion De DNI");

		 return (testObjArray);
	}
	
	public String obtenerChargeCode() {
		WebElement box = driver.findElements(By.cssSelector(".slds-button.slds-button_icon-border-filled.cpq-item-actions-dropdown-button")).get(2);
		box.click();
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", box);
		WebElement configure = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown_right.cpq-item-actions-dropdown")).get(2);
		buscarYClick(configure.findElements(By.tagName("a")), "contains", "configure");
		WebElement chargeCode = null;
		for (WebElement x : driver.findElements(By.className("slds-form-element"))) {
			if (x.getText().toLowerCase().contains("charge code"))
				chargeCode = x;
		}
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", chargeCode);
		return chargeCode.findElement(By.tagName("input")).getAttribute("value");
	}
	
	@DataProvider
	public Object[][] DatosNoNominacionNuevoTelefonicoPasaporte() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,8,"NoNominacionNuevoTelefonicoPasaporte");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] DatosNoNominacionExistenteAgente() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,5,"NoNominacionExistenteAgente");

		 return (testObjArray);
	}
	
	@DataProvider
	public Object[][] GestionRegionesCreacion() throws Exception{
		
		Object[][] testObjArray =  ExcelUtils.getTableArray(dataProviderE2E(),"BeFAN",1,1,1,"Creacion Regiones");
		
		return (testObjArray);
	}
	@DataProvider
	public Object[][] DatosNominacionExistente5Lineas() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,2,"NominacionExistente5Lineas");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] DatosNoNominacionNuevoFraudeTelef() throws Exception{
		
		 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,2,"NoNominacionNuevoFraudeTelef");

		 return (testObjArray);
	}
	@DataProvider
	public Object[][] DatosNoNominacionExistenteFraudeOfcom() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,2,"NoNominacionExistenteFraudeOfcom");

	 return (testObjArray);

	}
	@DataProvider
	public Object[][] DatosNoNominacionExistenteTelefonico() throws Exception{

	 Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"Altas y Nominacion",1,1,3,"NoNominacionExistenteTelefonico");

	 return (testObjArray);

	}
	
	public void loginLogisticaYEntrega(WebDriver driver) {
		driver.get(urlAmbiente);
		try {Thread.sleep(6000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    Login page0 = new Login(driver);
	    page0.ingresarLogisticaYEntrega();
	}
}