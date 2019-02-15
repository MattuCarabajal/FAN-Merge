package Tests;

import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.ContactSearch;
import Pages.HomeBase;
import Pages.OM;
import Pages.OMQPage;
import Pages.SalesBase;
import Pages.setConexion;

public class GestionesOM extends TestBase {
	

	private WebDriver driver;
	
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
	
	@Test(groups="OM", priority=1, dataProvider="OMAltaLinea")
	public void AltaLinea_Datos(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi) throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Parametros(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi);
	}
	
	@Test(groups="OM", priority=1, dataProvider="OMAltaLinea")
	public void AltaLinea_Servicios_Datos(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi, String sServicios) throws InterruptedException {
		OM pageOm=new OM(driver);
		if(sServicios.toLowerCase().equals("nada")){
			pageOm.Gestion_Alta_De_Linea_Parametros(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi);
		}
		if(sServicios.toLowerCase().equals("todo")){
			pageOm.Gestion_Alta_De_Linea_Con_Todos_Los_Servicios(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi,"servicios basicos general movil");
		}
	}
	
	@Test(groups="OM", priority=1, dataProvider="OMAltaCompleta")
	public void AltaLinea_Completa_Datos(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi, String sToN, String sCantPac, String sAmiVoz, String sAmiSms) throws InterruptedException {
		OM pageOm=new OM(driver);
		int iCantPac = Integer.parseInt(sCantPac);
		pageOm.Gestion_Alta_De_Linea_Completa(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi, sToN, iCantPac, sAmiVoz, sAmiSms);
	}
	
	@Test(groups="OM", priority=1, dataProvider="OMCambioSim")
	public void TS_CRM_Cambio_De_SimCard_Datos(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi, String sIccid2, String sImsi2, String sKi2) throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Parametros(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi);
		pageOm.Cambio_De_SimCard_Parametros(sIccid2,sImsi2,sKi2);
	}
	
	@Test(groups="OM", priority=1, dataProvider="OMCambioDeSimSiniestro")
	public void TS_CRM_Cambio_De_SimCard_Por_Siniestro_Datos(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi, String sIccid2, String sImsi2, String sKi2) throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Parametros(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi);
		pageOm.Cambio_De_SimCard_Por_Siniestro_Parametros(sIccid2,sImsi2,sKi2);
	}
	
	@Test(groups="OM", priority=1, dataProvider="OMCambioDeNumero")
	public void TS_CRM_Cambio_De_Numero_Datos(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi,String sMsisdn) throws InterruptedException {
		OM pageOm=new OM(driver);
		boolean gestion = false;
		pageOm.Gestion_Alta_De_Linea_Parametros(sCuenta, sPlan, sLinea, sIccid, sImsi, sKi);
		pageOm.Gestion_Cambio_de_Numero_Parametros(sMsisdn);
		sleep(5000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Cambio de n\\u00famero")) {
				gestion = true;
			}
		}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
	}
	
	@Test(groups="OM", priority=1)
	public void AltaLinea() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional");
	}
	@Test(groups="OM", priority=1)
	public void AltaLinea_1_Servicio() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Con_1_Servicio("FlorOM", "Plan Prepago Nacional","Llamada en espera");
	}
	
	@Test(groups="OM", priority=1)
	public void AltaLinea_Numeros_Amigos() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Con_Amigos("FlorOM", "Plan Prepago Nacional","Friends&Family","7586","5428");
	}
	
	@Test(groups="OM", priority=1)
	public void AltaLinea_Varios_Packs() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Con_Varios_Packs("FlorOM", "Plan Prepago Nacional","Packs Opcionales",10);
	}
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Baja_De_Servicio() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Con_1_Servicio("FlorOM", "Plan Prepago Nacional","Llamada en espera");
		System.out.println("No tiene sentido");
		pageOm.Baja_De_Servicio("Llamada en espera");
	}
	
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Cambio_De_SimCard() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Con Tarjeta");
		pageOm.Cambio_De_SimCard("07-14-2018");
	}
	
	@Test(groups="OM", priority=1, dataProvider="OMNominacion") 
	public void TS_CRM_Gestion_Nominacion(String sCuenta, String sDni, String sLinea) throws Exception {
		OM pageOm=new OM(driver);
		SalesBase sb = new SalesBase(driver);
		sb.DesloguearLoguear("nominaciones", 4);
		pageOm.Gestion_Nominacion(sCuenta, sDni, sLinea);
		sb.DesloguearLoguear("OM", 4);
	}
	
	
	@Test(groups="GestionOM", dataProvider="OMCambioTitularidad") 
	public void TS_CRM_CambioDeTitularidad(String sCuenta, String sPlan, String sLinea, String sIccid, String sImsi, String sKi) throws InterruptedException {
		
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea_Parametros("AutomaOM", sPlan, sLinea, sIccid, sImsi, sKi);
		pageOm.irAChangeToOrder();
		pageOm.Gestion_Cambio_De_Titularidad(sCuenta);
		driver.switchTo().defaultContent();
		sleep(5000);
		System.out.println(driver.findElement(By.id("accid_ileinner")).findElement(By.tagName("a")).getText());
	}
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Alta_De_Servicio() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Con Tarjeta");
		pageOm.Gestion_Alta_De_Servicio("LineasFlor", "Llamada en espera");
	}
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Venta_De_Packs() throws InterruptedException {
		OM pageOm=new OM(driver);
		pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Con Tarjeta");
		pageOm.Venta_De_Packs("Packs Opcionales", 1);
	}
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Cambio_De_SimCard_Por_Siniestro() throws InterruptedException {
		OM pageOm=new OM(driver);
		boolean gestion = false;
		pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Con Tarjeta");
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
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Rehabilitacion_Por_Siniestro() throws InterruptedException {
		OM pageOm=new OM(driver);
		boolean gestion = false;
		pageOm.Gestion_Alta_De_Linea("FlorOM", "Plan Con Tarjeta");
		pageOm.Rehabilitacion_Por_Siniestro();
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
	
	@Test(groups="OM", priority=1)
	public void AltaDeServicio() throws InterruptedException {
		boolean gestion = false;
		OM om = new OM(driver);
		om.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional");
		om.irAChangeToOrder();
		sleep(15000);
		driver.findElement(By.id("RequestDate")).sendKeys("12-09-2019");
		driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
		sleep(10000);
		driver.findElement(By.xpath("//*[@id=\"tab-default-1\"]/div[1]/ng-include/div/div/div/div[3]/div[1]/div[1]/button/span[2]")).click();
		sleep(10000);
		driver.findElements(By.cssSelector(".slds-button.slds-button_icon-small")).get(1).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"tab-default-1\"]/div[1]/ng-include/div/div/div/div[4]/div[2]/div/ng-include/div/div[2]/ng-include/div/div[3]/div/div[3]/div/div/ng-include/div/div[2]/ng-include/div/div[3]/div/div[2]/div[11]/button")).click();
		sleep(7000);
		driver.findElement(By.xpath("//*[@id=\"tab-default-1\"]/div[1]/ng-include/div/div/div/div[4]/div[2]/div/ng-include/div/div[2]/ng-include/div/div[3]/div/div[3]/div/div/ng-include/div/div[2]/ng-include/div/div[5]/div/div[2]/div[11]/button")).click();
		sleep(7000);
		driver.findElement(By.xpath("//*[@id=\"tab-default-1\"]/div[1]/ng-include/div/div/div/div[4]/div[2]/div/ng-include/div/div[2]/ng-include/div/div[3]/div/div[3]/div/div/ng-include/div/div[2]/ng-include/div/div[7]/div/div[2]/div[11]/button")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button_neutral")), "contains", "view record");
		sleep(5000);
		om.agregarGestion("Alta o Baja SVA");
		sleep(3000);
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(10000);
		om.completarFlujoOrquestacion();
		sleep(10000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Alta o Baja SVA")) {
				gestion = true;
			}
		}
		
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
	}
	
	
	@Test(groups="OM", priority=1)
	public void TS_CRM_Cambio_De_Numero() throws InterruptedException {
		OM pageOm=new OM(driver);
		boolean gestion = false;
		pageOm.Gestion_Alta_De_Linea("AlOM", "Plan Con Tarjeta");
		pageOm.Gestion_Cambio_de_Numero("AlanOM", "07-07-2018");
		sleep(5000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Cambio de n\\u00famero")) {
				gestion = true;
			}
		}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
	}

	@Test(groups="OM", priority=1)
	public void BajaDeLineaOM(String Cuenta, String Plan) throws InterruptedException {
		boolean gestion = false;
		OM Pom = new OM(driver);
		Pom.Gestion_Alta_De_Linea(Cuenta, Plan);
		Pom.irAChangeToOrder();
		sleep(15000);
		driver.switchTo().defaultContent(); 
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(Pom.fechaAvanzada()));
		driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
		sleep(10000);
		driver.findElement(By.xpath(".//*[@id='tab-default-1']/div/ng-include//div[10]//button")).click();
		sleep(2000);		
		buscarYClick(driver.findElements(By.cssSelector(".slds-dropdown__item.cpq-item-actions-dropdown__item")), "contains", "delete");
		sleep(5000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--destructive")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button_neutral")), "contains", "view record");
		sleep(5000);
		Pom.agregarGestion("Desconexi\u00f3n");
		sleep(3000);
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(10000);
		Pom.completarFlujoOrquestacion();
		sleep(10000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Desconexi\u00f3n")) {
				gestion = true;
			}
		}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
	}
	
	@Test(groups="OM", priority=1)
	public void ConciliacionOM() throws InterruptedException {
		OM om = new OM(driver);
		boolean gestion = false;
		om.Gestion_Alta_De_Linea("FlorOM", "Plan Prepago Nacional");
		om.Conciliacion();
		sleep(5000);
		WebElement status = driver.findElement(By.id("Status_ilecell"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".dataCol.inlineEditWrite"));
		for (WebElement x : gest) {
			if (x.getText().equalsIgnoreCase("Conciliate")) {
				gestion = true;
			}
		}
		Assert.assertTrue(status.getText().equalsIgnoreCase("Activated"));
		Assert.assertTrue(gestion);
		
		
	}
	
	@Test(groups="OM", priority=1)
	public void suspencionPorSiniestro(String sCuenta, String sPlan, String sTipoSiniestro) throws InterruptedException {
		
		//Suspension por siniestro por hurto = STCH
		//Suspension por siniestro por robo = STCR
		//Suspension por siniestro por extravio = STCE
		
		OM oOM = new OM(driver);
		oOM.Gestion_Alta_De_Linea(sCuenta, sPlan);
		
		sleep(5000);
		oOM.irAChangeToOrder();
		
		sleep(10000);
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(oOM.fechaAvanzada()));
		driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
		
		sleep(12000);
		List<WebElement> wTopRightButtons = driver.findElements(By.id("-import-btn"));
		for (WebElement wAux : wTopRightButtons){
			if (wAux.getAttribute("title").equalsIgnoreCase("View Record")) {
				wAux.click();
			}
		}
		
		sleep(5000);
		driver.findElement(By.id("topButtonRow")).findElement(By.name("edit")).click();
		
		Select sSelectDropdown = new Select(driver.findElement(By.id("00Nc0000002IvyM")));
		sSelectDropdown.selectByVisibleText("Suspension");
		
		driver.findElement(By.id("topButtonRow")).findElement(By.name("save")).click();
		
		sleep(5000);
		oOM.CambiarProductos("Suspend-Siniestro", "Change", "Suspend");
		String sOrderURL = driver.getCurrentUrl();
		
		List<WebElement> wProductos = driver.findElement(By.cssSelector(".listRelatedObject.orderBlock")).findElement(By.className("pbBody")).findElements(By.tagName("tr")); 
        for (WebElement wAux : wProductos) {
        	System.out.println(wAux.findElement(By.tagName("th")).getText());
        	if(wAux.findElement(By.tagName("th")).getText().contains("Simcard")) {
        		wAux.findElement(By.tagName("th")).findElement(By.tagName("a")).click();
        		System.out.println("Hice click");
        		sleep(5000);
        		break;
        	}
        }
		String sID = driver.getCurrentUrl().substring(40);
		
		//sleep(10000);
		//driver.findElement(By.id("Order_ileinner")).click();
		
		sleep(5000);
		driver.get("https://workbench.developerforce.com/login.php?startUrl=%2Fquery.php");
		Select sEnviroment = new Select(driver.findElement(By.id("oauth_env"))); 
        sEnviroment.selectByVisibleText("Sandbox");
		driver.findElement(By.id("termsAccepted")).click();
		driver.findElement(By.id("loginBtn")).click();
		
		sleep(5000);
		String s1 = "SELECT Id From OrderItem WHERE Id = '";
		driver.findElement(By.id("soql_query_textarea")).sendKeys(s1 + sID + "'");
		driver.findElement(By.name("querySubmit")).click();
		sleep(5000);
		driver.findElement(By.id("query_results")).findElement(By.tagName("a")).click();
		sleep(5000);
		List<WebElement> wTopButtons = driver.findElements(By.tagName("input"));
		for (WebElement wAux : wTopButtons) {
			if (wAux.getAttribute("value").equalsIgnoreCase("Update")) {
				wAux.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.name("Codigo_Movimiento_Inventario__c")).sendKeys(sTipoSiniestro);
		driver.findElement(By.name("action")).click();
		
		sleep(5000);
		driver.get(sOrderURL);
		sleep(5000);
		WebElement wTopButtonRow = driver.findElement(By.id("topButtonRow"));
		List<WebElement> wTopButtonRowButtons = wTopButtonRow.findElements(By.tagName("input"));
		for (WebElement wAux : wTopButtonRowButtons) {
			if (wAux.getAttribute("value").equalsIgnoreCase("TA Submit Order")) {
				wAux.click();
			}
		}
		
		sleep(10000);
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(45000);
		try {
			oOM.cambiarVentanaNavegador(1);
			sleep(2000);
			driver.findElement(By.id("idlist")).click();
			sleep(5000);
			oOM.cambiarVentanaNavegador(0);
		}
		catch(java.lang.IndexOutOfBoundsException ex1) {
			//Empty
		}
		sleep(12000);
		oOM.completarFlujoOrquestacion();
		sleep(5000);
	}
	
	@Test (groups="OM", priority=1)
	public void suspencionPorFraude(String sCuenta, String sPlan) throws InterruptedException {
		OM oOM = new OM(driver);
		oOM.Gestion_Alta_De_Linea(sCuenta, sPlan);
		
		sleep(5000);
		oOM.irAChangeToOrder();
		
		sleep(10000);
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(oOM.fechaAvanzada()));
		//driver.findElement(By.id("RequestDate")).sendKeys("07-15-2019");
		driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
		
		sleep(12000);
		List<WebElement> wTopRightButtons = driver.findElements(By.id("-import-btn"));
		for (WebElement wAux : wTopRightButtons){
			if (wAux.getAttribute("title").equalsIgnoreCase("View Record")) {
				wAux.click();
			}
		}
		
		sleep(5000);
		driver.findElement(By.id("topButtonRow")).findElement(By.name("edit")).click();
		
		Select sSelectDropdown = new Select(driver.findElement(By.id("00Nc0000002IvyM")));
		sSelectDropdown.selectByVisibleText("Suspension");
		
		driver.findElement(By.id("topButtonRow")).findElement(By.name("save")).click();
		
		sleep(5000);
		oOM.CambiarProductos("Suspend-Fraude", "Change", "Suspend");
		
		//sleep(10000);
		//driver.findElement(By.id("Order_ileinner")).click();
		
		WebElement wTopButtonRow = driver.findElement(By.id("topButtonRow"));
		List<WebElement> wTopButtonRowButtons = wTopButtonRow.findElements(By.tagName("input"));
		for (WebElement wAux : wTopButtonRowButtons) {
			if (wAux.getAttribute("value").equalsIgnoreCase("TA Submit Order")) {
				wAux.click();
			}
		}
		
		sleep(10000);
		oOM.completarFlujoOrquestacion();
	}
	
	@Test(groups="OM", priority=1)
	public void rehabilitacionPorSiniestro(String sNumeroOrden, String sTipoSiniestro) throws InterruptedException {
		
		//Rehabilitacion por siniestro por hurto = RTCH
		//Rehabilitacion por siniestro por robo = RTCR
		//Rehabilitacion por siniestro por extravio = RTCE
		
		OM oOM = new OM(driver);
		oOM.buscarOrdenPorNumero(sNumeroOrden);
		
		sleep(5000);
		oOM.irAChangeToOrder();
		
		sleep(10000);
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(oOM.fechaAvanzada()));
		driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
		
		sleep(12000);
		List<WebElement> wTopRightButtons = driver.findElements(By.id("-import-btn"));
		for (WebElement wAux : wTopRightButtons){
			if (wAux.getAttribute("title").equalsIgnoreCase("View Record")) {
				wAux.click();
			}
		}
		
		sleep(5000);
		driver.findElement(By.id("topButtonRow")).findElement(By.name("edit")).click();
		
		Select sSelectDropdown = new Select(driver.findElement(By.id("00Nc0000002IvyM")));
		sSelectDropdown.selectByVisibleText("Resume");
		
		driver.findElement(By.id("topButtonRow")).findElement(By.name("save")).click();
		
		sleep(5000);
		oOM.CambiarProductos("Resume-Siniestro", "Change", "Resume");
		String sOrderURL = driver.getCurrentUrl();
		
		List<WebElement> wProductos = driver.findElement(By.cssSelector(".listRelatedObject.orderBlock")).findElement(By.className("pbBody")).findElements(By.tagName("tr")); 
        for (WebElement wAux : wProductos) {
        	System.out.println(wAux.findElement(By.tagName("th")).getText());
        	if(wAux.findElement(By.tagName("th")).getText().contains("Simcard")) {
        		wAux.findElement(By.tagName("th")).findElement(By.tagName("a")).click();
        		System.out.println("Hice click");
        		sleep(5000);
        		break;
        	}
        }
		String sID = driver.getCurrentUrl().substring(40);
		
		//sleep(10000);
		//driver.findElement(By.id("Order_ileinner")).click();
		
		sleep(5000);
		driver.get("https://workbench.developerforce.com/login.php?startUrl=%2Fquery.php");
		Select sEnviroment = new Select(driver.findElement(By.id("oauth_env"))); 
        sEnviroment.selectByVisibleText("Sandbox");
		driver.findElement(By.id("termsAccepted")).click();
		driver.findElement(By.id("loginBtn")).click();
		
		sleep(5000);
		String s1 = "SELECT Id From OrderItem WHERE Id = '";
		driver.findElement(By.id("soql_query_textarea")).sendKeys(s1 + sID + "'");
		driver.findElement(By.name("querySubmit")).click();
		sleep(5000);
		driver.findElement(By.id("query_results")).findElement(By.tagName("a")).click();
		sleep(5000);
		List<WebElement> wTopButtons = driver.findElements(By.tagName("input"));
		for (WebElement wAux : wTopButtons) {
			if (wAux.getAttribute("value").equalsIgnoreCase("Update")) {
				wAux.click();
				break;
			}
		}
		sleep(5000);
		driver.findElement(By.name("Codigo_Movimiento_Inventario__c")).sendKeys(sTipoSiniestro);
		driver.findElement(By.name("action")).click();
		
		sleep(5000);
		driver.get(sOrderURL);
		sleep(5000);
		WebElement wTopButtonRow = driver.findElement(By.id("topButtonRow"));
		List<WebElement> wTopButtonRowButtons = wTopButtonRow.findElements(By.tagName("input"));
		for (WebElement wAux : wTopButtonRowButtons) {
			if (wAux.getAttribute("value").equalsIgnoreCase("TA Submit Order")) {
				wAux.click();
			}
		}
		
		sleep(10000);
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(45000);
		try {
			oOM.cambiarVentanaNavegador(1);
			sleep(2000);
			driver.findElement(By.id("idlist")).click();
			sleep(5000);
			oOM.cambiarVentanaNavegador(0);
		}
		catch(java.lang.IndexOutOfBoundsException ex1) {
			//Empty
		}
		sleep(12000);
		oOM.completarFlujoOrquestacion();
		sleep(5000);
	}
	
	@Test(groups="OM", priority=1)
	public void rehabilitacionPorFraude(String sNumeroOrden, String sTipoSiniestro) {
		OM oOM = new OM(driver);
		oOM.buscarOrdenPorNumero(sNumeroOrden);
		
		sleep(5000);
		oOM.irAChangeToOrder();
		
		sleep(10000);
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		driver.findElement(By.id("RequestDate")).sendKeys(dateFormat.format(oOM.fechaAvanzada()));
		//driver.findElement(By.id("RequestDate")).sendKeys("07-15-2019");
		driver.findElement(By.cssSelector(".form-control.btn.btn-primary.ng-binding")).click();
		
		sleep(12000);
		List<WebElement> wTopRightButtons = driver.findElements(By.id("-import-btn"));
		for (WebElement wAux : wTopRightButtons){
			if (wAux.getAttribute("title").equalsIgnoreCase("View Record")) {
				wAux.click();
			}
		}
		
		sleep(5000);
		driver.findElement(By.id("topButtonRow")).findElement(By.name("edit")).click();
		
		Select sSelectDropdown = new Select(driver.findElement(By.id("00Nc0000002IvyM")));
		sSelectDropdown.selectByVisibleText("Resume");
		
		driver.findElement(By.id("topButtonRow")).findElement(By.name("save")).click();
		
		sleep(5000);
		oOM.CambiarProductos("Resume-Fraude", "Change", "Resume");
		
		//sleep(10000);
		//driver.findElement(By.id("Order_ileinner")).click();
		
		WebElement wTopButtonRow = driver.findElement(By.id("topButtonRow"));
		List<WebElement> wTopButtonRowButtons = wTopButtonRow.findElements(By.tagName("input"));
		for (WebElement wAux : wTopButtonRowButtons) {
			if (wAux.getAttribute("value").equalsIgnoreCase("TA Submit Order")) {
				wAux.click();
			}
		}
		
		sleep(10000);
		oOM.completarFlujoOrquestacion();
	}
	
	@Test
	public void OpenPage() throws InterruptedException{
		suspencionPorSiniestro("MattuOM", "Plan Prepago Nacional", "STCH");
		String sNumeroDeOrden = driver.findElement(By.id("OrderNumber_ileinner")).getText();
		rehabilitacionPorSiniestro(sNumeroDeOrden, "RTCH");
	}
}