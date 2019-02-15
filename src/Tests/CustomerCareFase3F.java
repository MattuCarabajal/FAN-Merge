package Tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.setConexion;

public class CustomerCareFase3F  extends TestBase {
	
	private Accounts ac;
	private HomeBase hb;
	private CustomerCare cc;
	
	
	@AfterClass(groups = {"CustomerCare", "Vista360Layout"})
	public void tearDown2() {
		driver.quit();	
	}
	
	@BeforeClass(groups = {"CustomerCare", "Vista360Layout"})
	public void init() throws Exception {
		driver = setConexion.setupEze();
		ac = new Accounts(driver);
		hb = new HomeBase(driver);
		cc = new CustomerCare(driver);
		login(driver);
		sleep(5000);
		if (driver.findElement(By.id("tsidLabel")).getText().equals("Consola FAN")) {
			hb.switchAppsMenu();
			sleep(2000);
			hb.selectAppFromMenuByName("Ventas");
			sleep(5000);
		}
		hb.switchAppsMenu();
		sleep(2000);
		hb.selectAppFromMenuByName("Consola FAN");
		sleep(10000);
		goToLeftPanel2(driver, "Cuentas");
		driver.switchTo().defaultContent();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".topNav.primaryPalette")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		try {
			field.selectByVisibleText("Todas Las cuentas");
		} catch (org.openqa.selenium.NoSuchElementException ExM) {
			field.selectByVisibleText("Todas las cuentas");
		}
	}
	
	@BeforeMethod(groups = {"CustomerCare", "Vista360Layout"})
	public void setUp() throws Exception {
		sleep(6000);
		cc.cerrarultimapestana();
		sleep(12000);
		cc.elegircuenta("aaaaFernando Care");
		sleep(14000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		sleep(9000);
	}
	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37501_Historial_De_Recargas_PrePago_Monto_Total_De_Recargas_Visualizar_Monto_Total_De_Recargas() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElements(By.tagName("td")).get(3).findElement(By.tagName("b")).getText().isEmpty())
						assertTrue(false);
				}
				break;
			}
		}
		ac.closeAccountServiceTabByName("Historiales");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37508_Historial_De_Recargas_PrePago_Ordenamiento_Y_Paginado_De_Grilla_Ordenar_Por_Beneficios() {
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElement(By.tagName("a")).getText().toLowerCase().equals("beneficios")) {
						enc = true;
						assertTrue(UnaT.findElement(By.tagName("a")).getAttribute("ng-click").equals("sortBy(field)"));
						break;
					}
				}
				break;
			}
		}
		assertTrue(enc);
		ac.closeAccountServiceTabByName("Historiales");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37504_Historial_De_Recargas_PrePago_Ordenamiento_Y_Paginado_De_Grilla_Ordenar_Por_Canal() {
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElement(By.tagName("a")).getText().toLowerCase().equals("canal")) {
						enc = true;
						assertTrue(UnaT.findElement(By.tagName("a")).getAttribute("ng-click").equals("sortBy(field)"));
						break;
					}
				}
				break;
			}
		}
		assertTrue(enc);
		ac.closeAccountServiceTabByName("Historiales");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37505_Historial_De_Recargas_PrePago_Ordenamiento_Y_Paginado_De_Grilla_Ordenar_Por_Descripcion() {
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElement(By.tagName("a")).getText().toLowerCase().equals("descripción")) {
						enc = true;
						assertTrue(UnaT.findElement(By.tagName("a")).getAttribute("ng-click").equals("sortBy(field)"));
						break;
					}
				}
				break;
			}
		}
		assertTrue(enc);
		ac.closeAccountServiceTabByName("Historiales");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37503_Historial_De_Recargas_PrePago_Ordenamiento_Y_Paginado_De_Grilla_Ordenar_Por_Fecha_Y_Hora() {
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElement(By.tagName("a")).getText().toLowerCase().equals("fecha")) {
						enc = true;
						assertTrue(UnaT.findElement(By.tagName("a")).getAttribute("ng-click").equals("sortBy(field)"));
						break;
					}
				}
				break;
			}
		}
		assertTrue(enc);
		ac.closeAccountServiceTabByName("Historiales");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37506_Historial_De_Recargas_PrePago_Ordenamiento_Y_Paginado_De_Grilla_Ordenar_Por_Monto() {
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElement(By.tagName("a")).getText().toLowerCase().equals("monto")) {
						enc = true;
						assertTrue(UnaT.findElement(By.tagName("a")).getAttribute("ng-click").equals("sortBy(field)"));
						break;
					}
				}
				break;
			}
		}
		assertTrue(enc);
		ac.closeAccountServiceTabByName("Historiales");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS37507_Historial_De_Recargas_PrePago_Ordenamiento_Y_Paginado_De_Grilla_Ordenar_Por_Vencimiento() {
		boolean enc = false;
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		driver.findElement(By.className("card-info")).findElements(By.tagName("li")).get(1).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".via-slds.slds-m-around--small.ng-scope")));
		List<WebElement> historiales = driver.findElements(By.cssSelector(".via-slds.slds-m-around--small.ng-scope"));
		for (WebElement UnH : historiales) {
			if (UnH.findElement(By.cssSelector(".slds-text-heading--large.slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-bottom--small")).findElement(By.tagName("p")).getText().toLowerCase().equals("historial de recargas")) {
				List<WebElement> tuplas = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				for (WebElement UnaT : tuplas) {
					if (UnaT.findElement(By.tagName("a")).getText().toLowerCase().equals("vencimiento")) {
						enc = true;
						assertTrue(UnaT.findElement(By.tagName("a")).getAttribute("ng-click").equals("sortBy(field)"));
						break;
					}
				}
				break;
			}
		}
		assertTrue(enc);
		ac.closeAccountServiceTabByName("Historiales");
	}
}