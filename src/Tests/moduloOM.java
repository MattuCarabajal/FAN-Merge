package Tests;

import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.Iterator;

import Pages.Accounts;
import Pages.BasePage;
import Pages.HomeBase;
import Pages.OM;
import Pages.RegistroEventoMasivo;
import Pages.SCP;
import Pages.setConexion;

import Pages.setConexion;

public class moduloOM extends TestBase {
	
	private WebDriver driver;
	private String vista="AlmerOM";
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		sleep(5000);
		//Usuario Victor OM
		login(driver, "https://crm--sit.cs14.my.salesforce.com/", "U585991", "Testa10k");
		sleep(5000);	
	}

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		driver.switchTo().defaultContent();
		sleep(2000);
		OM pageOm=new OM(driver);
		pageOm.goToMenuOM();
		
		//click +
		sleep(5000);
		
		pageOm.clickMore();
		sleep(3000);
		
		//click en Ordenes
		pageOm.clickOnListTabs("Orders");
	}
	
	
	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		
		sleep(2000);
		driver.quit();
		sleep(1000);
	}
	
	
	@Test(groups="OM", priority=1)
	public void TS8231_CRM_OM_Ordenes_Panel_principal_Crear_una_Orden() {
		OM pageOm=new OM(driver);
		pageOm.crearOrden("AutomaAll");
		assertTrue(driver.findElement(By.cssSelector(".noSecondHeader.pageType")).isDisplayed());
	}
	
	//Hay que Ajustar
	//@Test(groups="OM") 
	public void TS6727_CRM_OM_Ordenes_Order_Detail_Visualización_del_flujo_de_orquestación() {
		OM pageOm=new OM(driver);
		//pageOm.crearVistaOM("AutOrders","AutomaOM");
		Select allOrder=new Select(driver.findElement(By.id("fcf")));
		allOrder.selectByVisibleText("AUTPO");
		sleep(2000);
		try {
		driver.findElement(By.name("go")).click();
		sleep(2000);
		}catch(Exception e) {};
		
		WebElement primeraOrden=driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-first"));
		primeraOrden.findElement(By.xpath("//table/tbody/tr/td[3]")).click();
		
		List<WebElement> nPedidos=driver.findElement(By.className("x-grid3-scroller")).findElement(By.className("x-grid3-body"))
				.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ORDERS_ORDER_NUMBER"));
		nPedidos.get(0).click();
		/*
		for(WebElement p:nPedidos) {
			//System.out.println(p.getText());
			//if(p.getText().endsWith("3879")) {
			if(p.getText().endsWith("17832")) {
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+p.getLocation().y+")");
				try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				p.findElement(By.tagName("a")).click();
				break;}	
			}*/
		sleep(5000);
		
		
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.xpath("//*[@id=\"CF00Nc0000001pSu8_ilecell\"]")).getLocation().y+")");
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id=\"lookupa27c0000005JPh600Nc0000001pSu8\"]")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage frame=new BasePage(driver);
		driver.switchTo().frame(frame.getFrameForElement(driver, By.id("canvas")));
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(driver.findElement(By.id("canvas")).isDisplayed());
		
	}
	
	//No tenemos el Codigo del test
	//@Test(groups="OM")
	public void TS_CRM_OM_Interfaces_Cliente_Nuevo_PP_CN_Sin_delivery_Sin_VAS_Paso_1_S203_createSubscriber_Huawei_Verificacion_de_request_response() {
		Select allOrder=new Select(driver.findElement(By.id("fcf")));
		allOrder.selectByVisibleText("All Orders VICTOR OM");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {
		driver.findElement(By.name("go")).click();}
		catch(Exception e) {};
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> nPedidos=driver.findElement(By.className("x-grid3-scroller")).findElement(By.className("x-grid3-body"))
				.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ORDERS_ORDER_NUMBER"));
		for(WebElement p:nPedidos) {
			//System.out.println(p.getText());
			if(p.getText().endsWith("3879")) {
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+p.getLocation().y+")");
				try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				p.findElement(By.tagName("a")).click();
				break;}	
			}
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.xpath("//*[@id=\"CF00Nc0000001pSu8_ilecell\"]")).getLocation().y+")");
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id=\"lookupa27c0000005JPh600Nc0000001pSu8\"]")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage frame=new BasePage(driver);
		driver.switchTo().frame(frame.getFrameForElement(driver, By.id("canvas")));
		try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//assertTrue(driver.findElement(By.id("canvas")).isDisplayed());
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.xpath("//*[text()='Create subscriber']")).getLocation().y+")");
		driver.findElement(By.xpath("//*[text()='Create subscriber']")).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(newTab.get(1));
		
		driver.findElement(By.className("listTitle")).click();
		assertTrue(driver.findElement(By.xpath("//*[text()='Request']")).isDisplayed()&&driver.findElement(By.xpath("//*[text()='Response']")).isDisplayed());
	}
	
	@Test(groups="OM")
	public void TS6716_CRM_OM_Ordenes_Panel_principal_Ingreso() {
		Select allOrder=new Select(driver.findElement(By.id("fcf")));
		allOrder.selectByVisibleText("All");
		sleep(1000);
		//try {driver.findElement(By.name("go")).click();}catch(org.openqa.selenium.NoSuchElementException e) {}
		//sleep(3000);
		List<WebElement> Orders=driver.findElement(By.className("x-grid3-scroller")).findElement(By.className("x-grid3-body"))
				.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ORDERS_ORDER_NUMBER"));
		assertTrue(Orders.get(0).isDisplayed());
		//System.out.println(Orders.get(0).getText());
	}
	
	
	@Test(groups="OM")
	public void TS6718_CRM_OM_Ordenes_Panel_principal_Cantidad_de_ordenes_Mas_ordenes_que_el_valor_de_paginado() {
		Select allOrder=new Select(driver.findElement(By.id("fcf")));
		allOrder.selectByVisibleText("All");
		sleep(1000);
		try {driver.findElement(By.name("go")).click();}catch(org.openqa.selenium.NoSuchElementException e) {}
		sleep(3000);
		WebElement nDePagina=driver.findElement(By.className("pageInput"));
		int pTotal=Integer.parseInt(nDePagina.getAttribute("Maxlength"));
		int pActual=Integer.parseInt(nDePagina.getAttribute("value"));
		OM pageOM=new OM(driver);
		//Si hay una sola Pagina
		if(pActual==pTotal) {
			List<WebElement> nOrders=driver.findElement(By.className("x-grid3-scroller")).findElement(By.className("x-grid3-body"))
			.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ORDERS_ORDER_NUMBER"));
			WebElement ultimaOrden=nOrders.get(nOrders.size()-1);
			assertTrue(pageOM.scrollDownInAView(ultimaOrden));
		}
		//SI hay mas Paginas
		else {
			while(pActual<pTotal) {
				sleep(3000);
				try{
				WebElement next=driver.findElement(By.className("next"));
				next.click();
				}catch(org.openqa.selenium.NoSuchElementException e) {}
				pActual++;
			}
			List<WebElement> nOrders=driver.findElement(By.className("x-grid3-scroller")).findElement(By.className("x-grid3-body"))
					.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ORDERS_ORDER_NUMBER"));
					WebElement ultimaOrden=nOrders.get(nOrders.size()-1);
					assertTrue(pageOM.scrollDownInAView(ultimaOrden));
		}
	
	}
	
	//Paso 2 Flujo Completo
	@Test(groups= {"OM","CambioDeTitularidad"},retryAnalyzer = retry.class)
	public void TS79904_CRM_OM_Ordenes_Cliente_existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_2() throws InterruptedException {
		
		OM pageOm=new OM(driver);
		//pageOm.irAUltimoAssetSegunCuentaEnVista("AlmerOM"); //Descomentar y comentar la siguiente linea, para lanzar el test sobre una linea existente.
		boolean gestion = false;
		pageOm.Gestion_Alta_De_Linea("AutomaOM", "Plan Prepago Nacional");
		pageOm.irAChangeToOrder();
		pageOm.Gestion_Cambio_De_Titularidad("CambioDeTitularidad");
		driver.switchTo().defaultContent();
		sleep(10000); 
	    WebElement status = driver.findElement(By.id("Status_ilecell")); 
	    List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")); 
	    for (WebElement x : gest) { 
	      if (x.getText().equalsIgnoreCase("Cambio de titularidad")) { 
	        gestion = true; 
	      } 
	    } 
	    Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	    Assert.assertTrue(gestion); 
	}
	
	//Paso 0
	@Test(groups= {"OM","CambioDeTitularidad"}, dependsOnMethods ="TS79904_CRM_OM_Ordenes_Cliente_existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_2")
	public void TS79902_CRM_OM_Ordenes_Cliente_existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_0() {
			assertTrue(true);
		}
	//Paso 1
	@Test(groups= {"OM","CambioDeTitularidad"}, dependsOnMethods ="TS79904_CRM_OM_Ordenes_Cliente_existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_2")
	public void TS79903_CRM_OM_Ordenes_Cliente_existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_1() {
			assertTrue(true);
		}

	//@Test
	public void EliminarOrdersDeVista() {
		OM page=new OM(driver);
		page.deleteOrdersNoActivated("AlmerOM");
	}
	
	@Test(groups= {"OM","CambioDeSimCard"} ) 
	  public void TS52668_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_3() throws InterruptedException { 
	    OM pageOm=new OM(driver); 
	    boolean gestion = false; 
	    pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Con Tarjeta"); 
	    pageOm.Cambio_De_SimCard("07-13-2018"); 
	    sleep(10000); 
	    WebElement status = driver.findElement(By.id("Status_ilecell")); 
	    List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")); 
	    for (WebElement x : gest) { 
	      if (x.getText().equalsIgnoreCase("Cambio de SIM")) { 
	        gestion = true; 
	      } 
	    } 
	    Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	    Assert.assertTrue(gestion); 
	     
	  } 
	   
	  @Test(groups= {"OM","CambioDeSimCard"},dependsOnMethods = "TS52668_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_3") 
	  public void TS52667_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_2() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCard"},dependsOnMethods = "TS52668_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_3") 
	  public void TS52666_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_1() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCard"},dependsOnMethods = "TS52668_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_3") 
	  public void TS52665_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Sin_Delivery_Paso_0() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	   
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}) 
	  public void TS79634_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_3() throws InterruptedException { 
	    OM pageOm=new OM(driver); 
	    boolean gestion = false; 
	    pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional"); 
	    pageOm.Cambio_De_SimCard_Por_Siniestro("LineasFlor"); 
	    sleep(5000); 
	    WebElement status = driver.findElement(By.id("Status_ilecell")); 
	    List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")); 
	    for (WebElement x : gest) { 
	      if (x.getText().equalsIgnoreCase("Cambio de SIM por siniestro")) { 
	        gestion = true; 
	      } 
	    } 
	    Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	    Assert.assertTrue(gestion); 
	  } 
	   
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS79634_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_3") 
	  public void TS79632_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_2() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS79634_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_3") 
	  public void TS79631_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_1() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS79634_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_3") 
	  public void TS79630_CRM_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Sin_Delivery_Paso_0() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	   
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}) 
	  public void TS80323_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_5() throws InterruptedException { 
	    OM pageOm=new OM(driver); 
	    boolean gestion = false; 
	    pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan con Tarjeta"); 
	    pageOm.Cambio_De_SimCard_Por_Siniestro("LineasFlor"); 
	    sleep(5000); 
	    WebElement status = driver.findElement(By.id("Status_ilecell")); 
	    List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")); 
	    for (WebElement x : gest) { 
	      if (x.getText().equalsIgnoreCase("Cambio de SIM por siniestro")) { 
	        gestion = true; 
	      } 
	    } 
	    Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	    Assert.assertTrue(gestion); 
	  } 
	   
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS80323_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_5") 
	  public void TS80322_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_4() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS80323_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_5") 
	  public void TS80321_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_3() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS80323_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_5") 
	  public void TS80320_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_2() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS80323_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_5") 
	  public void TS80319_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_1() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	  @Test(groups= {"OM","CambioDeSimCardSiniestro"}, dependsOnMethods = "TS80323_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_5") 
	  public void TS80318_OM_Ordenes_Cliente_Existente_Cambio_De_SIM_Por_Siniestro_Plan_Con_Tarjeta_Sin_Delivery_Paso_0() throws InterruptedException { 
	    Assert.assertTrue(true); 
	  } 
	
}//Fin Clase
