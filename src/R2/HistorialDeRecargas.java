package R2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class HistorialDeRecargas extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log;
	String detalles;
	
	
	//@BeforeClass
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();	
	}
	
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass(alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176705_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_con_Beneficios_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176705";
		
		// SE NECESITA UNA CUENTA CON BENEFICIOS
		
	}
		
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176713_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_IVR_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176713";
		boolean canal = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("Historial de recargas");
		cambioDeFrame(driver, By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium']"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '01']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.id("text-input-03")).click();
		for(WebElement x : driver.findElements(By.cssSelector("[class='slds-dropdown__list slds-dropdown--length-5'] li"))){
			if(x.getText().contains("IVR"))
				x.click();
		}
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tableHeader'] [class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		for(WebElement y : driver.findElements(By.cssSelector("[class='slds-truncate slds-p-vertical--medium']"))){
			if(y.getText().contains("IVR"))
				canal = true;
		}
		Assert.assertTrue(canal);	// CANAL IVR ????
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176717_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_ROL_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176717";
		boolean canal = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("Historial de recargas");
		cambioDeFrame(driver, By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium']"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '01']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.id("text-input-03")).click();
		sleep(500);
		for(WebElement x : driver.findElements(By.cssSelector("[class='slds-dropdown__list slds-dropdown--length-5'] li"))){
			if(x.getText().contains("ROL"))
				x.click();
		}
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tableHeader'] [class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium']")));
		for(WebElement y : driver.findElements(By.cssSelector("[class='slds-truncate slds-p-vertical--medium']"))){
			if(y.getText().contains("ROL"))
				canal = true;
		}
		Assert.assertTrue(canal);	  // CANAL ROL ????
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176727_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_TODOS_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176727";
		boolean canal = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("Historial de recargas");
		cambioDeFrame(driver, By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium']"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '01']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.id("text-input-03")).click();
		sleep(500);
		for(WebElement x : driver.findElements(By.cssSelector("[class='slds-dropdown__list slds-dropdown--length-5'] li"))){
			if(x.getText().contains("Todos"))
				x.click();
		}
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-truncate slds-p-vertical--medium']")));
		for(WebElement y : driver.findElements(By.cssSelector("[class='slds-truncate slds-p-vertical--medium']"))){
			//System.out.println(y.getText());
			if(y.getText().contains("SMS"))
				canal = true;
		}
		Assert.assertTrue(canal);
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176729_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Plan_Nacional_4G_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176729";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '03']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().contains("Pack Roaming 40 SMS Limitrofes y USA x 30 días"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		Assert.assertTrue(datoTabla.matches("[0-9]{2}[\\/]{1}[0-9]{2}[\\/]{1}[0-9]{4} [0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}"));
	
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176731_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_1_GB_Renovacion_Cuota_de_Datos_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176731";
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '03']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().contains("1 GB Renovacion Cuota de Datos."))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		Assert.assertTrue(datoTabla.matches("[0-9]{2}[\\/]{1}[0-9]{2}[\\/]{1}[0-9]{4} [0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}"));
	
	}
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176733_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Promocion_Personal_Whastapp_Crm_OC(String sDNI, String sLinea){
		imagen = "TS176733";
		boolean fecha = false, vencimiento = false, nombre = false, monto = false, detalle = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '03']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-input__icon--left slds-icon slds-icon--x-small nds-icon nds-icon_x-small slds-input__icon']")));
		driver.findElement(By.cssSelector("[class='slds-input__icon--left slds-icon slds-icon--x-small nds-icon nds-icon_x-small slds-input__icon']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ng-pristine ng-untouched ng-valid ng-empty slds-col slds-size_1-of-2 slds-medium-size_1-of-2 slds-large-size_1-of-2 slds-p-around_x-small'] dl")));
		for(WebElement y : driver.findElements(By.cssSelector("[class='ng-pristine ng-untouched ng-valid ng-empty slds-col slds-size_1-of-2 slds-medium-size_1-of-2 slds-large-size_1-of-2 slds-p-around_x-small'] dl"))){
			if(y.getText().contains("Servicio:"))
				detalle = true;
		}
		for(WebElement x : driver.findElements(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] [class='slds-text-heading--label'] th [class='slds-truncate slds-th__action']"))){
			if (x.getText().contains("FECHA"))
				fecha = true;
			if(x.getText().contains("VENCIMIENTO"))
				vencimiento = true;
			if(x.getText().contains("NOMBRE DEL PACK"))
				nombre = true;
			if(x.getText().contains("MONTO"))
				monto = true;
		}
		Assert.assertTrue(fecha && vencimiento && nombre && monto && detalle);
	}
	
	//----------------------------------------------- TELEFONICO   -------------------------------------------------------\\
	
	@Test (groups = {"PerfiTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176712_CRM_Movil_Pre_Historial_de_Recargas_Consultar_detalle_de_Recargas_por_Canal_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS176712";
		boolean pack1 = false, sms = false, otros = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de recargas");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '03']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().contains("Todos"))
				cc.obligarclick(x);
		}
		selectByText(driver.findElement(By.cssSelector("[class='slds-select ng-pristine ng-untouched ng-valid ng-not-empty']")),"20");
		sleep(1500);
		for(WebElement a : driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr td"))){
			if(a.getText().contains("Recarga Virtual"))
				pack1 = true;
			if(a.getText().contains("SMS"))
				sms = true;
			if(a.getText().contains("Otros"))
				otros = true;
		}
		Assert.assertTrue(pack1 && sms && otros );
	}
	
	@Test (groups = {"PerfiTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176728_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_TODOS_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS176728";
		boolean pack1 = false, pack2 = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '03']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-dropdown slds-dropdown--left resize-dropdowns']")));
		for (WebElement x : driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns")).findElements(By.tagName("li"))) {
			if (x.getText().contains("Todos"))
				cc.obligarclick(x);
		}
		selectByText(driver.findElement(By.cssSelector("[class='slds-select ng-pristine ng-untouched ng-valid ng-not-empty']")),"20");
		sleep(1500);
		for(WebElement a : driver.findElements(By.cssSelector("[class='slds-p-bottom--small slds-p-left--medium slds-p-right--medium'] tbody tr td"))){
			if(a.getText().contains("1 GB Renovacion Cuota de Datos."))
				pack1 = true;
			if(a.getText().contains("Pack Roaming 40 SMS Limitrofes y USA x 30 días"))
				pack2 = true;
		}
		Assert.assertTrue(pack1 && pack2);
		
	}
	@Test (groups = {"PerfiTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176734_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Promocion_Personal_Whastapp_Crm_Telefonico(String sDNI, String sLinea){
		imagen = "TS176734";
		boolean fecha = false, vencimiento = false, nombre = false, monto = false, detalle = false;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Historiales");
		cc.seleccionDeHistorial("historial de packs");
		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small"), 0);
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement mes = driver.findElement(By.id("month"));
		WebElement botonRew = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes.getText().toLowerCase().equals("julio"))
				botonRew.click();
		}	
		driver.findElement(By.xpath("//*[text() = '03']")).click();	
		driver.findElement(By.id("text-input-id-2")).click();
		sleep(500);
		WebElement mes2 = driver.findElement(By.id("month"));
		WebElement botonRew2 = driver.findElement(By.cssSelector("[class= 'slds-button slds-button--icon-container nds-button nds-button_icon-container']"));
		for(int i =0 ; i<=12; i++){
			if(!mes2.getText().toLowerCase().equals("julio"))
				botonRew2.click();
		}
		driver.findElement(By.xpath("//*[text() = '31']")).click();	
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='slds-input__icon--left slds-icon slds-icon--x-small nds-icon nds-icon_x-small slds-input__icon']")));
		driver.findElement(By.cssSelector("[class='slds-input__icon--left slds-icon slds-icon--x-small nds-icon nds-icon_x-small slds-input__icon']")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ng-pristine ng-untouched ng-valid ng-empty slds-col slds-size_1-of-2 slds-medium-size_1-of-2 slds-large-size_1-of-2 slds-p-around_x-small'] dl")));
		for(WebElement y : driver.findElements(By.cssSelector("[class='ng-pristine ng-untouched ng-valid ng-empty slds-col slds-size_1-of-2 slds-medium-size_1-of-2 slds-large-size_1-of-2 slds-p-around_x-small'] dl"))){
			if(y.getText().contains("Servicio:"))
				detalle = true;
		}
		for(WebElement x : driver.findElements(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] [class='slds-text-heading--label'] th [class='slds-truncate slds-th__action']"))){
			if (x.getText().contains("FECHA"))
				fecha = true;
			if(x.getText().contains("VENCIMIENTO"))
				vencimiento = true;
			if(x.getText().contains("NOMBRE DEL PACK"))
				nombre = true;
			if(x.getText().contains("MONTO"))
				monto = true;
		}
		Assert.assertTrue(fecha && vencimiento && nombre && monto && detalle);
		
	}
}