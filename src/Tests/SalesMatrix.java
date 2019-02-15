package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.HomeBase;
import Pages.SalesBase;
import Pages.setConexion;

public class SalesMatrix extends TestBase {
	
	private WebDriver driver;
	protected SalesBase sb;
	
		
	@BeforeClass (alwaysRun = true)
	public void init() throws Exception{
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		loginOfCom(driver);
		sleep(5000);
		HomeBase homePage = new HomeBase(driver);
		sleep(6000);
	    String a = driver.findElement(By.id("tsidLabel")).getText();
	    if (a.contains("Ventas")){}
	    else {
	    	homePage.switchAppsMenu();
	    	sleep(2000);
	    	homePage.selectAppFromMenuByName("Ventas");
	    	sleep(5000);            
	    }	    
	    sleep(8000);
	    driver.findElement(By.xpath("//a[@href=\"/home/showAllTabs.jsp\"]")).click();
	    sleep(5000);
	    List <WebElement> vcm = driver.findElements(By.cssSelector(".relatedListIcon.userDefinedImage"));
	    for (WebElement x : vcm) {
	    	if (x.getAttribute("title").toLowerCase().equals("vlocity calculation matrices")) {
	    		x.click();
	    		break;
	    	}
	    }
	    sleep(5000);
	    driver.findElement(By.name("go")).click();
	    sleep(5000);
	}
	
	//@AfterMethod (alwaysRun = true)
	public void goBack(){
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.findElement(By.className("ptBreadcrumb")).findElement(By.tagName("a")).click();
		sleep(5000);
	}

	//@AfterClass (alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
	
	
	@Test (groups = {"Sales", "Ventas"})
	public void TS38033_Ventas_Entregas_General_Verificar_Plazo_de_Envio_para_ModoRetiroSuc_ZonaAmba() {
		sb.selectMatrix("s", "shippingtimeconfiguration");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".rowsTbody.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".rowsTbody.ng-scope"));
		List <WebElement> fila = element.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		if (fila.get(2).getText().toLowerCase().contains("retiro")) {
			a = true;
		}
		if (fila.get(2).getText().toLowerCase().contains("amba")) {
			b = true;
		}
		if (fila.get(2).getText().toLowerCase().contains("2")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "Ventas"})
	public void TS38034_Ventas_Entregas_General_Verificar_Plazo_de_Envio_para_ModoRetiroSuc_ZonaCiudadPrinInt() {
		sb.selectMatrix("s", "shippingtimeconfiguration");	
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".rowsTbody.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".rowsTbody.ng-scope"));
		List <WebElement> fila = element.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		if (fila.get(3).getText().toLowerCase().contains("retiro")) {
			a = true;
		}
		if (fila.get(3).getText().toLowerCase().contains("ciudades principales")) {
			b = true;
		}
		if (fila.get(3).getText().toLowerCase().contains("3")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "Ventas"})
	public void TS38035_Ventas_Entregas_General_Verificar_Plazo_de_Envio_para_ModoEnvEst_ZonaAmba() {
		sb.selectMatrix("s", "shippingtimeconfiguration");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".rowsTbody.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".rowsTbody.ng-scope"));
		List <WebElement> fila = element.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		if (fila.get(4).getText().toLowerCase().contains("standard")) {
			a = true;
		}
		if (fila.get(4).getText().toLowerCase().contains("amba")) {
			b = true;
		}
		if (fila.get(4).getText().toLowerCase().contains("5")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "Ventas"})
	public void TS38036_Ventas_Entregas_General_Verificar_Plazo_de_Envio_para_ModoEnvEst_ZonaCiudadPrinInt() {
		sb.selectMatrix("s", "shippingtimeconfiguration");	
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".rowsTbody.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".rowsTbody.ng-scope"));
		List <WebElement> fila = element.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		if (fila.get(5).getText().toLowerCase().contains("standard")) {
			a = true;
		}
		if (fila.get(5).getText().toLowerCase().contains("ciudades principales")) {
			b = true;
		}
		if (fila.get(5).getText().toLowerCase().contains("8")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "Ventas"})
	public void TS38037_Ventas_Entregas_General_Verificar_Plazo_de_Envio_para_ModoEnvExp_ZonaAmba() {
		sb.selectMatrix("s", "shippingtimeconfiguration");	
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".rowsTbody.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".rowsTbody.ng-scope"));
		List <WebElement> fila = element.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		if (fila.get(0).getText().toLowerCase().contains("express")) {
			a = true;
		}
		if (fila.get(0).getText().toLowerCase().contains("amba")) {
			b = true;
		}
		if (fila.get(0).getText().toLowerCase().contains("1")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "Ventas"})
	public void TS38038_Ventas_Entregas_General_Verificar_Plazo_de_Envio_para_ModoEnvExp_ZonaCiudadPrinInt() {
		sb.selectMatrix("s", "shippingtimeconfiguration");	
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".rowsTbody.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".rowsTbody.ng-scope"));
		List <WebElement> fila = element.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		if (fila.get(1).getText().toLowerCase().contains("express")) {
			a = true;
		}
		if (fila.get(1).getText().toLowerCase().contains("ciudades principales")) {
			b = true;
		}
		if (fila.get(1).getText().toLowerCase().contains("2")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "AltaDeCuenta","Ola1"}, priority=5)
	public void TS95194_Alta_Cuenta_Validaciones_Verificar_creacion_de_matriz_de_validacion_de_identidad() {
		sb.selectMatrix("m", "manageableidentityvalidation");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".table.pricingMatrixTable")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".table.pricingMatrixTable")).isDisplayed());
	}
	
	@Test (groups = {"Sales", "AltaDeLinea","Ola1"}, priority=0)
	public void TS95293_Ventas_Seriales_Verificar_instancia_de_Factura_en_la_matriz_StockMovementOperation() {
		sb.selectMatrix("s", "stockmovementoperation");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".table.pricingMatrixTable")));
		WebElement tabla = driver.findElement(By.id("rowsTbody"));
		List <WebElement> fila = tabla.findElements(By.tagName("tr"));
		boolean a = false, b = false, c = false;
		for (WebElement x : fila) {
			if (x.findElements(By.tagName("td")).get(2).getText().contains("StorePickUp")) {
				a = true;
			}
			if (x.findElements(By.tagName("td")).get(1).getText().contains("FACTURA")) {
				b = true;
			}
			if (x.findElements(By.tagName("td")).get(5).getText().contains("VICLIE")) {
				c = true;
			}
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "AltaDeLinea","Ola1"}, priority=0)
	public void TS95294_Ventas_Seriales_Verificar_instancia_de_Entrega_eliminada_en_la_matriz_StockMovementOperation() {
		sb.selectMatrix("s", "stockmovementoperation");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".table.pricingMatrixTable")));
		WebElement tabla = driver.findElement(By.id("rowsTbody"));
		List <WebElement> fila = tabla.findElements(By.tagName("tr"));
		for (WebElement x : fila) {
			if (x.findElements(By.tagName("td")).get(2).getText().contains("StorePickUp") && x.findElements(By.tagName("td")).get(1).getText().contains("ENTREGA")) {
				Assert.assertTrue(false);
			}
		}
	}
	
	@Test (groups = {"Sales", "AltaDeLinea", "Ola1"}, priority=0)
	public void TS94963_Verificar_que_se_configuren_criterios_Stock() {
		sb.selectMatrix("s", "stockmovementoperation");
		driver.switchTo().frame(cambioFrame(driver, By.className("edit-columns")));
		WebElement fila = driver.findElement(By.className("edit-columns"));
		boolean a = false, b = false, c = false;
		if (fila.getText().toLowerCase().contains("operationtype")) {
			a = true;
		}
		if (fila.getText().toLowerCase().contains("instance")) {
			b = true;
		}
		if (fila.getText().toLowerCase().contains("deliverymode")) {
			c = true;
		}
		Assert.assertTrue(a && b && c);
	}
	
	@Test (groups = {"Sales", "AltaDeLinea", "Ola1","filtrado"}, priority=0)
	public void TS95246_Ventas_General_Anulacion_de_reserva_de_venta_de_productos_tangibles_Modo_presencial() {
		sb.selectMatrix("s", "stockmovementoperation");
		driver.switchTo().frame(cambioFrame(driver, By.className("edit-columns")));
		WebElement tabla = driver.findElement(By.id("rowsTbody"));
		List <WebElement> fila = tabla.findElements(By.tagName("tr"));
		boolean a = false, b = false;
		for (WebElement x : fila) {
			if (x.findElements(By.tagName("td")).get(1).getText().contains("ANULARESERVA")) {
				a = true;
			}
			if (x.findElements(By.tagName("td")).get(2).getText().contains("Presencial")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
}
