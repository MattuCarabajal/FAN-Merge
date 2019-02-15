package Tests;

import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.net.SimpleSocketServer;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.jna.platform.win32.OaIdl.DATE;
import com.sun.xml.internal.ws.api.server.Container;

import Pages.Accounts;
import Pages.BasePage;
import Pages.Marketing;
import Pages.OM;
import Pages.OMQPage;
import Pages.setConexion;

public class OMerO extends TestBase {

	private WebDriver driver;
	protected OM om;
	protected BasePage bp;
	protected OMQPage omq;

	@BeforeClass (alwaysRun = true, groups = "OM")
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		login(driver, "https://crm--sit.cs14.my.salesforce.com/", "U585991", "Testa10k");
		sleep(5000);
		om = new OM(driver);
		bp = new BasePage(driver);
		omq = new OMQPage(driver);
	}
	
	@BeforeMethod (alwaysRun = true, groups = "OM")
	public void before() {
		bp.cajonDeAplicaciones("Sales");
		sleep(5000);
		driver.findElement(By.id("Order_Tab")).click();
		sleep(3000);
	}
	
	//@AfterClass (alwaysRun = true, groups = "OM")
	public void quit() {
		driver.quit();
		sleep(5000);
	}
//==============================================================  VISTA  ========================================================
	@Test (groups = "OM")
	public void TS6721_OM_Ordenes_Vista_Configuracion(){
		driver.findElement(By.className("fFooter")).click();
		sleep(5000);
		driver.findElement(By.id("fname")).sendKeys("Automatizacion");
		Select env = new Select (driver.findElement(By.id("colselector_select_1")));
		env.selectByVisibleText("Order Number");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(2000);
		env.selectByVisibleText("Order Start Date");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(2000);
		env.selectByVisibleText("Order Amount");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(2000);
		env.selectByVisibleText("Contract Number");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(3000);	
		Select field = new Select (driver.findElement(By.id("fcol1")));
		field.selectByVisibleText("Account Name");
		Select oper = new Select (driver.findElement(By.id("fop1")));
		oper.selectByVisibleText("equals");
		driver.findElement(By.id("fval1")).sendKeys("AutomaOM");
		WebElement a = driver.findElement(By.cssSelector(".pbButtonb")).findElement(By.tagName("input"));
		a.click();
		sleep(5000);
		List<WebElement> acc = driver.findElement(By.id("ext-gen15")).findElement(By.tagName("table")).findElement(By.tagName("thead")).findElements(By.tagName("td"));
			if(acc.get(0).getText().contains("Action"))
				acc.remove(0);
		System.out.println(acc.size());		
		Assert.assertTrue(acc.get(2).getText().contains("Account Name"));
		Assert.assertTrue(acc.get(3).getText().contains("Status"));
		Boolean chk = false;
		WebElement llsstt = driver.findElement(By.className("x-grid3-body")).findElement(By.tagName("div")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		List<WebElement> list = llsstt.findElements(By.tagName("td"));
			for(WebElement l : list){
				list.get(2).equals("AutomaOM");
				chk = true;
			}
		Assert.assertTrue(chk);	
		sleep(3000);
		WebElement dele = driver.findElement(By.className("filterLinks")).findElements(By.tagName("a")).get(1);
		dele.click();
		try {driver.switchTo().alert().accept();}catch(org.openqa.selenium.NoAlertPresentException e) {}
	}
	
//========================================================================================================================================================================
	
	@Test (groups = "OM")
	public void TS6722_OM_Ordenes_Vista_Configuracion_Cargar_Vista(){
		driver.findElement(By.className("fFooter")).click();
		sleep(5000);
		driver.findElement(By.id("fname")).sendKeys("CambiodeVista");
		Select env = new Select (driver.findElement(By.id("colselector_select_1")));
		env.selectByVisibleText("Order Number");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(2000);
		env.selectByVisibleText("Order Start Date");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(2000);
		env.selectByVisibleText("Order Amount");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(2000);
		env.selectByVisibleText("Contract Number");
		driver.findElement(By.id("colselector_select_0_left")).click();
		sleep(3000);	
		Select field = new Select (driver.findElement(By.id("fcol1")));
		field.selectByVisibleText("Account Name");
		Select oper = new Select (driver.findElement(By.id("fop1")));
		oper.selectByVisibleText("equals");
		driver.findElement(By.id("fval1")).sendKeys("AutomaOM");
		WebElement a = driver.findElement(By.cssSelector(".pbButtonb")).findElement(By.tagName("input"));
		a.click();
		sleep(5000);
		om.selectVistaByVisibleText("All");
		sleep(5000);
		om.selectVistaByVisibleText("CambiodeVista");
		sleep(5000);
		List<WebElement> acc = driver.findElement(By.id("ext-gen15")).findElement(By.tagName("table")).findElement(By.tagName("thead")).findElements(By.tagName("td"));
			if(acc.get(0).getText().contains("Action"))
				acc.remove(0);
		System.out.println(acc.size());		
		Assert.assertTrue(acc.get(2).getText().contains("Account Name"));
		Assert.assertTrue(acc.get(3).getText().contains("Status"));
	}
//========================================================  CAMBIO DE NUMERO  ============================================================================
	
	@Test (groups = {"OM","CambioDeNumero"})
	public void TS79682CRM_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_5() throws InterruptedException{
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea("AlOM", "Plan Prepago Nacional");
		pageOm.Gestion_Cambio_de_Numero("AlanOM", "07-10-2018");
		sleep(15000);
		boolean gestion = false;
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		WebElement gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")).get(12);
		if (gest.getText().toLowerCase().contains("cambio de n\u00famero")) {
				gestion = true;
			}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
		
	}      
		
			
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS79682CRM_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_5")
	public void TS79681_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_4(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS79682CRM_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_5")
	public void TS79680_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_3(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS79682CRM_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_5")
	public void TS79678_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_2(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS79682CRM_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_5")
	public void TS79677_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_1(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS79682CRM_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_5")
	public void TS79676_OM_Ordenes_Cliente_existente_Cambio_de_numero_Sin_delivery_Paso_0(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
//==============================================================================================================================================
	
	
	@Test (groups = {"OM","CambioDeNumero"})
	public void TS80246_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_5() throws InterruptedException{
		OM pageOm=new OM(driver);
		boolean gestion = false;
		pageOm.Gestion_Alta_De_Linea("AlOM", "Plan con tarjeta");
		pageOm.Gestion_Cambio_de_Numero("AlanOM", "07-09-2018");
		sleep(15000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		WebElement gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")).get(12);
		if (gest.getText().toLowerCase().contains("cambio de n\u00famero")) {
				gestion = true;
			}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
		sleep(3000);
	}      
		
			
	@Test (groups = {"OM","CambioDeNumero"},dependsOnMethods="TS80246_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_5")
	public void TS80245_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_4(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS80246_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_5")
	public void TS80244_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_3(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS80246_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_5")
	public void TS80243_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_2(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS80246_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_5")
	public void TS80242_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_1(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	@Test (groups = {"OM","CambioDeNumero"}, dependsOnMethods="TS80246_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_5")
	public void TS80241_OM_Ordenes_Cliente_existente_Cambio_de_numero_Plan_con_tarjeta_Sin_delivery_Paso_0(){
		Assert.assertTrue(true);
		sleep(3000);
	}
	
	
//==============================================            ALTA DE LINEA         ==================================================================	
	
	@Test(groups = {"OM","AltadeLinea"})                  // FALTAN NOMINAR
	public void TS51856_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4() throws InterruptedException{
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea("AlOM", "Plan Prepago Nacional");
		driver.navigate().back();
		sleep(5000);
		driver.findElement(By.xpath("//*[@id='topButtonRow']/input[6]")).click();
		sleep(10000);
		boolean chiqui = false;
		while (chiqui == false) {

			try {
				driver.findElement(By.id("zoomOut")).click();
			} catch (Exception ex1) {
				chiqui = true;
				driver.findElement(By.id("zoomIn")).click();
				break;
			}
		}
		pageOm.ordenCajasVerdes("CreateSubscriber - S203", "Env\u00edo de Activaci\u00f3n de Servicios de la Red", "updateNumberStatus - S326");
	}
	
	@Test(groups = {"OM","AltadeLinea"}, dependsOnMethods="TS51856_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS51856_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_0(){
		Assert.assertTrue(true);
	
	}
	
	@Test(groups = {"OM","AltadeLinea"}, dependsOnMethods="TS51856_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS51857_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_1(){
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"OM","AltadeLinea"}, dependsOnMethods="TS51856_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS51858_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_2(){
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"OM","AltadeLinea"}, dependsOnMethods="TS51856_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_4")
	public void TS51859_Ordenes_Cliente_Nuevo_Alta_de_linea_Sin_delivery_Sin_VAS_Paso_2(){
		Assert.assertTrue(true);
	}
	
//===================================================== CAMBIO DE TITULARIDAD ========================================================================	

	@Test(groups = {"OM","CambiodeTitularidad"})
	public void TS127336_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_2() throws InterruptedException{
		om.Gestion_Alta_De_Linea("CambiodeTitularidad", "Plan prepago nacional");
		om.irAChangeToOrder();
		om.Gestion_Cambio_De_Titularidad("CambiodeTitularidad");
		boolean gestion = false;
		sleep(15000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		WebElement gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")).get(12);
		if (gest.getText().toLowerCase().contains("cambio de titularidad")) {
				gestion = true;
			}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
		sleep(3000);
	}
	
	@Test(groups = {"OM","CambiodeTitularidad"}, dependsOnMethods="TS127336_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_2")
	public void TS127335_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_1(){
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"OM","CambiodeTitularidad"}, dependsOnMethods="TS127336_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_2")
	public void TS127334_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_prepago_nacional_Paso_0(){
		Assert.assertTrue(true);
	}

	
//===================================================================================================================================================
	
	
	@Test(groups = {"OM","CambiodeTitularidad"})
	public void TS127349_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_con_tarjeta_Paso_2() throws InterruptedException{
		om.Gestion_Alta_De_Linea("CambiodeTitularidad", "Plan con tarjeta");
		om.irAChangeToOrder();
		om.Gestion_Cambio_De_Titularidad("CambiodeTitularidad");
		boolean gestion = false;
		sleep(15000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		WebElement gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite")).get(12);
		if (gest.getText().toLowerCase().contains("cambio de titularidad")) {
				gestion = true;
			}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
		sleep(3000);
	}
	
	@Test(groups = {"OM","CambiodeTitularidad"}, dependsOnMethods="TS127349_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_con_tarjeta_Paso_2")
	public void TS127348_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_con_tarjeta_Paso_1(){
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"OM","CambiodeTitularidad"}, dependsOnMethods="TS127349_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_con_tarjeta_Paso_2")
	public void TS127347_Ordenes_Cliente_Existente_Cambio_de_titularidad_Plan_con_tarjeta_Paso_0(){
		Assert.assertTrue(true);
	}
//========================================================== TIMESTAMP  =================================================================================	
	
	@Test(groups = {"OM"})
	public void TS11483_Ordenes_Tareas_Timestamp_Completed_Automatica() throws InterruptedException{
		
		//Alta de linea
		om.crearOrden("AlOM");
		assertTrue(driver.findElement(By.cssSelector(".noSecondHeader.pageType")).isDisplayed());
		om.agregarGestion("Venta");
		sleep(2000);
		omq.getCPQ().click();
		sleep(5000);
		omq.colocarPlan1("Plan Prepago Nacional");
		omq.configuracion();
		om.AgregarDomicilio();
		sleep(5000);
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(15000);
		try {System.out.println(driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
			driver.switchTo().alert().dismiss();
			driver.switchTo().defaultContent();
			driver.findElement(By.name("ta_submit_order")).click();
		} catch (org.openqa.selenium.NoAlertPresentException e) {
			driver.switchTo().defaultContent();
		}
		sleep(45000);
		 try { 
		      om.cambiarVentanaNavegador(1); 
		      sleep(2000); 
		      driver.findElement(By.id("idlist")).click(); 
		      sleep(5000); 
		      om.cambiarVentanaNavegador(0); 
		    }catch(java.lang.IndexOutOfBoundsException ex1) {} 
		sleep(12000);
		
		//  Orquestacion
		
		boolean chiqui = false;
		while (chiqui == false) {

			try {
				driver.findElement(By.id("zoomOut")).click();
			} catch (Exception ex1) {
				chiqui = true;
				driver.findElement(By.id("zoomIn")).click();
				break;
			}

		}
		sleep(10000);
		List<WebElement> cajas = driver.findElements(By.cssSelector(".item-label-container.item-header.item-failed"));
		cajas.addAll(driver.findElements(By.cssSelector(".item-label-container.item-header.item-fatally-failed")));
		cajas.addAll(driver.findElements(By.cssSelector(".item-label-container.item-header.item-running")));
		int i = 1;
		while (cajas.size() > 0) {
			for (WebElement UnaC : cajas) {
				UnaC.click();
				sleep(5000);
				om.cambiarVentanaNavegador(i);
				//i++;
				sleep(5000);
				List<WebElement> botones = driver
						.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-scope"));
				for (WebElement UnB : botones) {
					if (UnB.getText().equals("Complete")) {
						UnB.click();
						sleep(4000);
						System.out.println("Hizo click");
						break;
					}
				}
				WebElement comp = driver.findElements(By.cssSelector(".ng-scope.ng-isolate-scope")).get(13);
				String a = null;
				System.out.println(comp.getText());
					if(comp.getText().toLowerCase().equals("fatally failed")||comp.getText().toLowerCase().equals("running")){
						sleep(5000);
						driver.navigate().refresh();
						sleep(25000);
						List<WebElement> hora = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--cell-buffer")).findElements(By.tagName("tr"));
							for(WebElement hor : hora){
								if(hor.findElement(By.tagName("th")).getText().equals("Completed")){
									a=hor.findElement(By.tagName("td")).getText();
									
								}
							}
						a = a.substring(0, 15);
						System.out.println(a);
						Date date = new Date();
						Calendar cal = Calendar.getInstance(); 
				        cal.setTime(date); 
				        if(Integer.parseInt(a.substring(a.length()-2,a.length()))!=date.getMinutes()){
				         cal.add(Calendar.MINUTE, -1);
				        }
				        date = cal.getTime();
				        DateFormat dateFormat = new SimpleDateFormat();
				        if(date.getMonth()<10){
				        	dateFormat = new SimpleDateFormat("dd/M/yyyy HH:mm");
				        }else{
				        	dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				        }
				        System.out.println(dateFormat.format(date));
				        Assert.assertTrue(dateFormat.format(date).equals(a));   
					}	
				sleep(10000);
				om.cambiarVentanaNavegador(0);
				sleep(10000);
				om.closeAllOtherTabs();
				sleep(35000);
				
			}
		
			cajas = driver.findElements(By.cssSelector(".item-label-container.item-header.item-failed"));
			cajas.addAll(driver.findElements(By.cssSelector(".item-label-container.item-header.item-fatally-failed")));
			cajas.addAll(driver.findElements(By.cssSelector(".item-label-container.item-header.item-running")));
		}
		om.closeAllOtherTabs();
		sleep(5000);
		driver.findElement(By.className("submit-button")).click();
		sleep(6000);
		om.cambiarVentanaNavegador(1);
		sleep(5000);
		om.closeAllOtherTabs();
		Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("Activated"));
		
      }
		
	}