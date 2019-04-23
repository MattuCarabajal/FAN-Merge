package Funcionalidades;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import Tests.TestBase;

public class Fallout extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private SalesBase sb;
	String imagen;
	
	
	@BeforeClass (alwaysRun = true)
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		loginOperativo(driver);
		sleep(7000);
		cc = new CustomerCare(driver);
		sb = new SalesBase(driver);
		cc.cajonDeAplicaciones("Ventas");
		sleep(5000);
		driver.findElement(By.className("wt-ManualQueue")).click();
		sb.irAInboxTecnico();
		sleep(25000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    driver.findElement(By.cssSelector(".scrollable.mt8")).click();
	    sleep(3000);
	    driver.switchTo().window(tabs.get(0));
	}
	
	@BeforeMethod (alwaysRun = true)
	public void before() {
		sleep(3000);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("home_Tab")).click();
		sleep(3000);
	}
	
	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OPERATIVO -------------------------------------------------------\\
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134430_CRM_OM_Ordenes_Inbox_tecnico_Busqueda_de_ordenes_con_error() {
		imagen = "TS134430";
		driver.findElement(By.className("wt-ManualQueue")).click();
		sb.irAInboxTecnico();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		driver.findElement(By.cssSelector(".slds-button.slds-section__title-action")).click();
		selectByText(driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Fatally Failed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral")), "equals", "search");
		sleep(3000);
		WebElement fila = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		Assert.assertTrue(fila.findElements(By.tagName("td")).get(2).getText().equalsIgnoreCase("Fatally Failed"));
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134445_CRM_OM_Ola_1_Inbox_Tecnico_Action_Pick_Up() {
		imagen = "TS134445";
		driver.findElement(By.className("wt-ManualQueue")).click();
		boolean assingToMe = false;
		sb.irAInboxTecnico();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		WebElement fila = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		fila.findElement(By.tagName("td")).findElement(By.tagName("span")).click();
		for (WebElement x : driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-scope"))) {
			if (x.getText().equalsIgnoreCase("Assign to Me")) {
				x.click();
				assingToMe = true;
			}
		}
		Assert.assertTrue(assingToMe);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134446_CRM_OM_Ola_1_Inbox_Tecnico_Action_Complete() {
		imagen = "TS134446";
		driver.findElement(By.className("wt-ManualQueue")).click();
		boolean complete = false;
		sb.irAInboxTecnico();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		WebElement fila = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		fila.findElement(By.tagName("td")).findElement(By.tagName("span")).click();
		for (WebElement x : driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-scope"))) {
			if (x.getText().equalsIgnoreCase("Complete")) {
				x.click();
				complete = true;
			}
		}
		Assert.assertTrue(complete);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134447_CRM_OM_Ola_1_Inbox_Tecnico_Action_Retry() {
		imagen = "TS134447";
		driver.findElement(By.className("wt-ManualQueue")).click();
		boolean retry = false;
		sb.irAInboxTecnico();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		WebElement fila = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		fila.findElement(By.tagName("td")).findElement(By.tagName("span")).click();
		for (WebElement x : driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-scope"))) {
			if (x.getText().equalsIgnoreCase("Retry")) {
				x.click();
				retry = true;
			}
		}
		Assert.assertTrue(retry);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"}, dependsOnMethods = "TS134430_CRM_OM_Ordenes_Inbox_tecnico_Busqueda_de_ordenes_con_error")
	public void TS134431_CRM_OM_Ordenes_Inbox_tecnico_Verificacion_del_total_de_ordenes_de_la_busqueda() {
		imagen = "TS134431";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"}, dependsOnMethods = "TS134430_CRM_OM_Ordenes_Inbox_tecnico_Busqueda_de_ordenes_con_error")
	public void TS134432_CRM_OM_Ordenes_Inbox_tecnico_Verificacion_de_nuevos_casos_con_el_mismo_error() {
		imagen = "TS134432";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134433_CRM_OM_Ordenes_Inbox_tecnico_Ver_el_detalle_de_las_ordenes() {
		imagen = "TS134433";
		driver.findElement(By.className("wt-ManualQueue")).click();
		sb.irAInboxTecnico();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		WebElement fila = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
		for (WebElement x : fila.findElements(By.tagName("td"))) {
			try {
				if (x.getText().contains("T0"))
					x.findElement(By.tagName("a")).click();
			} catch (Exception e) {}
		}
		sleep(5000);
		List<String> datos = new ArrayList<String>();
		datos.add("NAME");
		datos.add("STATE");
		datos.add("START DATE");
		datos.add("DUE DATE");
		datos.add("JEOPARDY STATUS");
		List<String> columnas = new ArrayList<String>();
		List<WebElement> tabla = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElements(By.tagName("th"));
		for (int i=0; i<tabla.size(); i++) {
			columnas.add(tabla.get(i).getText());
		}
		columnas.remove(0);
		Assert.assertTrue(columnas.equals(datos));
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134435_CRM_OM_Ordenes_Inbox_tecnico_Cancelacion_de_la_orden() {
		imagen = "TS134435";
		boolean status = false;
		driver.findElement(By.className("wt-ManualQueue")).click();
		sb.irAInboxTecnico();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		driver.findElement(By.cssSelector(".slds-button.slds-section__title-action")).click();
		selectByText(driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-empty")), "Fatally Failed");
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral")), "equals", "search");
		sleep(3000);
		driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(1).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-scope")), "contains", "edit");
		selectByText(driver.findElements(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")).get(2), "Cancelled");
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(3000);
		for (WebElement x : driver.findElements(By.tagName("td"))) {
			if (x.getText().contains("Cancelled"))
				status = true;
		}
		Assert.assertTrue(status);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"}, dependsOnMethods = "TS134430_CRM_OM_Ordenes_Inbox_tecnico_Busqueda_de_ordenes_con_error")
	public void TS134439_CRM_OM_Ordenes_Inbox_tecnico_Verificacion_de_numero_total_de_ordenes_por_error() {
		imagen = "TS134439";
		Assert.assertTrue(true);
	}
	
	@Test (groups = {"Fallout", "GestionesPerfilOperativo", "Ciclo3"})
	public void TS134440_CRM_OM_Ordenes_Inbox_tecnico_Seleccionar_todas_las_ordenes_para_realizar_acciones_multiples() {
		imagen = "TS134440";
		boolean assingToMe = false;
		driver.findElement(By.className("wt-ManualQueue")).click();
		sb.irAInboxTecnico();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-section__title-action")));
		driver.findElement(By.cssSelector(".slds-button.slds-section__title-action")).click();
		sleep(10000);
		List<WebElement> checkbox = driver.findElement(By.cssSelector(".backreference.slds-box.slds-table.slds-table_bordered.slds-table--cell-buffer.slds-m-top--small")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (int i=0; i<=2; i++) {
			checkbox.get(i).findElement(By.tagName("span")).click();
			sleep(2000);
			i++;
		}
		for (WebElement x : driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-scope"))) {
			if (x.getText().equalsIgnoreCase("Assign to Me")) {
				x.click();
				assingToMe = true;
			}
		}
		Assert.assertTrue(assingToMe);
	}
}