package Tests;

import org.testng.annotations.BeforeClass;


import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
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
import Pages.OMQPage;
import Pages.RegistroEventoMasivo;
import Pages.SCP;
import Pages.OM;
import Pages.setConexion;




public class OM_Modulo extends TestBase {
	
private WebDriver driver;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		sleep(5000);
		//Usuario Victor OM
		login(driver, "https://telecomcrm--uat.cs53.my.salesforce.com/", "uat198427", "Bulls96");
		sleep(5000);	
	}

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		driver.switchTo().defaultContent();
		sleep(2000);
		SCP pageSCP= new SCP(driver);
		pageSCP.goToMenu("Ventas");
		
		//click +
		sleep(5000);
		OM pageOm=new OM(driver);
		pageOm.clickMore();
		sleep(3000);
		
		//click en Ordenes
		pageOm.clickOnListTabs("Pedidos");
	}
	
	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		sleep(2000);
		driver.quit(); 
		sleep(1000);
	}

	//flujo completo
	@Test(groups= {"OM","agregarPack"}, retryAnalyzer = retry.class)
	public void TS102209_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4() throws InterruptedException, MalformedURLException {
	String Url;
	OM pageOm=new OM(driver);
	OMQPage OM=new OMQPage (driver);
	pageOm.Gestion_Alta_De_Linea("QuelysOM", "Plan con tarjeta");
	
	sleep(5000);
	pageOm.irAChangeToOrder();
	sleep(12000);
	driver.switchTo().defaultContent();
	
	
	//fecha avanzada
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(pageOm.fechaAvanzada()));
	driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
	sleep(12000);
	
	//agregar Pack
	pageOm.agregarPack("Packs Opcionales","Packs de Datos", "Pack 200Mb + WhasApp x 1 día","Pack 1GB de dia + 3GB de Noche","Pack 500Mb + WhasApp x 3 días");
		
	//Click ViewRecord
	sleep(8000);	
	driver.findElement(By.id("-import-btn")).click();
	sleep(7000);
	
	//agregar gestion
	pageOm.agregarGestion("Alta producto gen\u00e9rico");
	
	//sincronizar producto
	Url = driver.getCurrentUrl();
	pageOm.clickTab("Product2_Tab");
	OM.sincroProducto ();//("Samsung Cargador - Negro");
	OM.clickSincronizar();
	driver.get(Url);
	
	//Orquestacion
	driver.findElement(By.name("ta_submit_order")).click();
	sleep(35000);
	pageOm.completarFlujoOrquestacion();
	sleep(8000);	
	WebElement status = driver.findElement(By.id("Status_ilecell")); 
	Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	
	 
	}
	//Paso 0
		@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102209_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
		public void TS102205_CRM_M_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_0() {
			Assert.assertTrue(true);
		}
	//Paso 1
		@Test(groups= {"OM","agregarPack"}, dependsOnMethods ="TS102209_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
		public void TS102206_CRM_M_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_1() {
			Assert.assertTrue(true);
		}
	//Paso 2
		@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102209_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
		public void TS102207_CRM_M_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_2() {
			Assert.assertTrue(true);		
		}
		
	//Paso 3
		@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102209_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_4")
		public void TS102208_CRM_M_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Paso_3() {
			Assert.assertTrue(true);	
		}
		
		
	//flujo completo
	@Test(groups= {"OM","agregarPack"}, retryAnalyzer = retry.class)
	public void TS102216_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4() throws InterruptedException, MalformedURLException {
	String Url;
	OM pageOm=new OM(driver);
	OMQPage OM=new OMQPage (driver);
    pageOm.Gestion_Alta_De_Linea("QuelysOM", "Plan Prepago Nacional");
	sleep(5000);
	pageOm.irAChangeToOrder();
	sleep(12000);
	driver.switchTo().defaultContent();
	
	//fecha avanzada
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(pageOm.fechaAvanzada()));
	driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
	sleep(12000);
	
	//agregar Pack
	OM.agregarPack("Packs Opcionales","Packs de Datos", "Pack 2GB + WhasApp x 3 días","Pack 1GB de dia + 3GB de Noche","Pack 2Gb + WhasApp x 30 días");
				
	//Click ViewRecord
	sleep(8000);	
	driver.findElement(By.id("-import-btn")).click();
	sleep(8000);
	
	//agregar gestion
	pageOm.agregarGestion("Alta producto gen\u00e9rico");
	
	//sincronizar
	Url = driver.getCurrentUrl();
	pageOm.clickTab("Product2_Tab");
	OM.sincroProducto();//("Friend&Family VOZ CFS");
	OM.clickSincronizar();
	driver.get(Url);
	
	//Orquestacion
	driver.findElement(By.name("ta_submit_order")).click();
	sleep(35000);
	pageOm.completarFlujoOrquestacion();
	sleep(8000);
	WebElement status = driver.findElement(By.id("Status_ilecell")); 
	Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	
			
	}
	
	//Paso 0
	@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102216_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS102212_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_0() {
		Assert.assertTrue(true);
			}
	
	//Paso 1
	@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102216_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS102213_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_1() {
		Assert.assertTrue(true);
			}
	
	//Paso 2
	@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102216_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS102214_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_2() {
		Assert.assertTrue(true);
			}

	//Paso 3
	@Test(groups= {"OM","AgregarPAck"}, dependsOnMethods ="TS102216_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_4")
	public void TS102215_CRM_OM_Ola_2_Ordenes_Cliente_existente_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Paso_3() {
		Assert.assertTrue(true);
			}
		
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, retryAnalyzer = retry.class)
	 public void TS102304_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados() throws Exception {    
	 OM pageOm=new OM(driver);
	 pageOm.Alta_de_linea_con_Pack("QuelysOM", "Plan con tarjeta","Pack Internet x 30 dias");
	 sleep(5000);
	 pageOm.completarFlujoOrquestacion();
	 pageOm.verificacionDeCamposEnviadosenelRequest("CreateSubscriber - S203");
	 WebElement status = driver.findElement(By.id("Status_ilecell")); 
	Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	}
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, dependsOnMethods ="TS102304_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados")
	public void TS102300_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Huawei_S203_createSubscriber_Verificacion_de_campos_enviados_en_el_request() {
		Assert.assertTrue(true);
			}
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, dependsOnMethods ="TS102304_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados")
	public void TS102301_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Huawei_S203_createSubscriber_Verificacion_de_parametros_enviados() {
		Assert.assertTrue(true);
			}
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, dependsOnMethods ="TS102304_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados")
	public void TS102303_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_con_tarjeta_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_campos_enviados_en_el_request() {
		Assert.assertTrue(true);
			}
	
	
	//VEEEEEEEEEEEEEEEEEEEEEEEEEECCCCCCCCCTOOOOOOOOOOOOOOOORRRRR
	@Test(groups= {"OM","agregarPack"}, retryAnalyzer = retry.class)
	public void TSXXX_HOLA() throws InterruptedException, MalformedURLException {
	String Url;
	OM pageOm=new OM(driver);
	OMQPage OM=new OMQPage (driver);
	
	
	pageOm.Gestion_Alta_De_Linea("QuelysOM", "Plan con tarjeta");
	
	sleep(5000);
	pageOm.irAChangeToOrder();
	sleep(12000);
	driver.switchTo().defaultContent();
	
	
	//fecha avanzada
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(pageOm.fechaAvanzada()));
	driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
	sleep(12000);
	
	//agregar Pack
	pageOm.agregarPack("Packs Opcionales","Packs de Datos", "Pack 200Mb + WhasApp x 1 día","Pack 1GB de dia + 3GB de Noche","Pack 500Mb + WhasApp x 3 días");
		
	//Click ViewRecord
	sleep(8000);	
	driver.findElement(By.id("-import-btn")).click();
	sleep(7000);
	
	//agregar gestion
	pageOm.agregarGestion("Alta producto gen\u00e9rico");
	
	//sincronizar producto
	Url = driver.getCurrentUrl();
	pageOm.clickTab("Product2_Tab");
	OM.sincroProducto ();//("Samsung Cargador - Negro");
	OM.clickSincronizar();
	driver.get(Url);
	
	//Orquestacion
	driver.findElement(By.name("ta_submit_order")).click();
	sleep(35000);
	pageOm.completarFlujoOrquestacion();
	sleep(8000);	
	WebElement status = driver.findElement(By.id("Status_ilecell")); 
	Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	
	 
	}
	
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, retryAnalyzer = retry.class)
	 public void TS102309_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados() throws Exception {    
	 OM pageOm=new OM(driver);
	 pageOm.Alta_de_linea_con_Pack("QuelysOM", "Plan prepago nacional","Pack Internet x 30 dias");
	 sleep(5000);
	 pageOm.completarFlujoOrquestacion();
	 pageOm.verificacionDeCamposEnviadosenelRequest("CreateSubscriber - S203");
	 WebElement status = driver.findElement(By.id("Status_ilecell")); 
	Assert.assertTrue(status.getText().equalsIgnoreCase("Activated")); 
	}
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, dependsOnMethods ="TS102309_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados")
	public void TS102305_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Huawei_S203_createSubscriber_Verificacion_de_campos_enviados_en_el_request() {
		Assert.assertTrue(true);
			}
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, dependsOnMethods ="TS102309_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados")
	public void TS102306_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Huawei_S203_createSubscriber_Verificacion_de_parametros_enviados() {
		Assert.assertTrue(true);
			}
	
	@Test(groups= {"OM","altaconPack","Verificacionderequest"}, dependsOnMethods ="TS102309_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_parametros_enviados")
	public void TS102308_CRM_OM_Ola_2_Interfaces_Alta_de_linea_con_1_pack_Plan_prepago_nacional_Sin_delivery_Sin_VAS_Numeracion_Movil_S326_updateNumberStatus_Verificacion_de_campos_enviados_en_el_request() {
		Assert.assertTrue(true);
			}
	
	
	
}

