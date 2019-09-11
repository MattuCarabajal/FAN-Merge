package R2;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.Marketing;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class OtrosHistoriales extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private Marketing mk;
	String detalles;
	

	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
			driver = setConexion.setupEze();
			ges = new GestionDeClientes_Fw(driver);
			cc = new CustomerCare(driver);
			mk = new Marketing(driver);
			log = new LoginFw(driver);
			log.loginTelefonico();
			ges.irAConsolaFAN();
		}
		
	@BeforeClass (groups = "PerfilAgente")
	public void initAgente() {
			driver = setConexion.setupEze();
			ges = new GestionDeClientes_Fw(driver);
			cc = new CustomerCare(driver);
			mk = new Marketing(driver);
			log = new LoginFw(driver);
			log.loginAgente();
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

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = {"PerfilOficina", "R2"}, dataProvider = "RecargasHistorias")
	public void TS144788_CRM_Movil_Pre_Historial_de_Suspenciones_y_Habilitaciones_Crm_OC(String sDNI , String sLinea){
		imagen="TS144788";
		Boolean historial = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
			for(WebElement x : driver.findElements(By.cssSelector("[class = 'card-info'] [class= 'actions'] li a [class= 'slds-text-body_regular']"))){
				if(x.getText().contains("Historial de Suspensiones"))
					x.click();
				System.out.println(x.getText());
			}
		cambioDeFrame(driver, By.cssSelector("[class='slds-size--2-of-9 slds-medium-size--2-of-9 slds-large-size--2-of-9 slds-m-top--small'] button"),0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr"), 0));
		for(WebElement y : driver.findElements(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr td"))){
				if(y.getText().contains(sLinea))
				historial = true;
		}
		Assert.assertTrue(historial);
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "R2"}, dataProvider = "RecargasHistorias")
	public void TS144789_CRM_Movil_Pre_Historial_de_Suspenciones_y_Habilitaciones_Crm_Telefonico(String sDNI , String sLinea){
		imagen="TS144789";
		Boolean historial = false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
			for(WebElement x : driver.findElements(By.cssSelector("[class = 'card-info'] [class= 'actions'] li a [class= 'slds-text-body_regular']"))){
				if(x.getText().contains("Historial de Suspensiones"))
					x.click();
				System.out.println(x.getText());
			}
		cambioDeFrame(driver, By.cssSelector("[class='slds-size--2-of-9 slds-medium-size--2-of-9 slds-large-size--2-of-9 slds-m-top--small'] button"),0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr"), 0));
		for(WebElement y : driver.findElements(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr td"))){
				if(y.getText().contains(sLinea))
				historial = true;
		}
		Assert.assertTrue(historial);
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "RecargasHistorias")
	public void TS144790_CRM_Movil_Pre_Historial_de_Suspenciones_y_Habilitaciones_Crm_Agente(String sDNI , String sLinea){
		imagen="TS144790";
		Boolean historial = false, inicio = false, impacto = false, procedimiento = false, motivo =false, submotivo = false, servicio =false;
		ges.BuscarCuenta("DNI", sDNI);
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
			for(WebElement x : driver.findElements(By.cssSelector("[class = 'card-info'] [class= 'actions'] li a [class= 'slds-text-body_regular']"))){
				if(x.getText().contains("Historial de Suspensiones"))
					x.click();
			}
		cambioDeFrame(driver, By.cssSelector("[class='slds-size--2-of-9 slds-medium-size--2-of-9 slds-large-size--2-of-9 slds-m-top--small'] button"),0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr"), 0));
		for(WebElement y : driver.findElements(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody tr td"))){
				if(y.getText().contains(sLinea))
				historial = true;
		}
		Assert.assertTrue(historial);
		
		for(WebElement z : driver.findElements(By.cssSelector("[class='slds-table slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] thead th"))){
			if(z.getText().contains("FECHA DE PEDIDO"))
				inicio = true;
			if(z.getText().contains("FECHA DE IMPACTO"))
				impacto = true;
			if(z.getText().contains("PROCEDIMIENTO"))
				procedimiento = true;
			if(z.getText().contains("MOTIVO"))
				motivo = true;
			if(z.getText().contains("SUBMOTIVO"))
				submotivo = true;
			if(z.getText().contains("SERVICIO"))
				servicio = true;
	}
		Assert.assertTrue( inicio && impacto && procedimiento && motivo && submotivo && servicio);
	}
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176741_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_TODOS_Crm_Agente(String sDNI , String sLinea){
		imagen="TS176741";
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
			if (x.getText().toLowerCase().contains("todos"))
				cc.obligarclick(x);
		}
		String datoTabla = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-p-left--medium.slds-p-right--medium")).findElements(By.tagName("tbody")).get(1).findElement(By.tagName("tr")).findElement(By.tagName("td")).getText();
		Assert.assertTrue(datoTabla.matches("[0-9]{2}[\\/]{1}[0-9]{2}[\\/]{1}[0-9]{4} [0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}"));
	}
	
	@Test (groups = {"PerfilAgente", "R2"}, dataProvider = "RecargasHistorias")
	public void TS176742_CRM_Movil_Pre_Historial_de_Packs_Nombre_del_Pack_Plan_Internet_50_Mb_Crm_Agente(String sDNI , String sLinea){
		imagen="TS176742";
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
	
}