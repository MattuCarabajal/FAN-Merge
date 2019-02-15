package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SalesPage extends BasePage {
	
	public SalesPage(WebDriver driv) {
		setupNuevaPage(driv);
	}
	
	public void irAGestionDeClientes() {
		driver.findElement(By.xpath("//a[contains(.,'Gestión de Clientes')]")).click();
	}
	
	public void irASalesCPQ() {
		cajonDeAplicaciones("Ventas");
		irAGestionDeClientes();
		buscarClientePor("DNI", "32323232");
		accionesClienteActivo("Catalogo");	
	}
	
	// ********************** GESTION DE CLIENTES **********************

	public void buscarClientePor(String por, String numero) {
		switch (por) {
			case "DNI":
				Select selectorDocumento = new Select(driver.findElement(By.xpath("//select[@id='SearchClientDocumentType']")));
				selectorDocumento.selectByVisibleText("DNI");
				
				WebElement inputDNI = driver.findElement(By.xpath("//input[@id='SearchClientDocumentNumber']"));
				inputDNI.sendKeys(numero);
				
				WebElement botonBuscarCliente = driver.findElement(By.xpath("//div[@id='SearchClientsDummy']/p"));
				botonBuscarCliente.click();
				break;
		}
	}
	
	public void accionesClienteActivo(String accion) {
		switch(accion) {
			case "Catalogo":
				driver.findElement(By.xpath("//div[@id='tab-scoped-1']//a[@title='Catalogo']")).click();
				break;
		}
		System.out.println("sleep");
		sleep(20000);
		System.out.println("20");
	}
	
	
	// ****************************** CPQ ******************************
	
	public void comprobarPrefactibilidad() {
		waitForVisibilityOfElementLocated(By.cssSelector(".taGetPrefeasibility"));
		driver.findElement(By.cssSelector(".taGetPrefeasibility")).click();
		sleep(1500);
		driver.switchTo().frame(driver.findElement(By.cssSelector(".slds-modal.slds-fade-in-open.installationAddressModal iframe")));
	}
	
	
	// ************************ PREFACTIBILIDAD ************************
	
	public List<WebElement> obtenerCamposObligatorios() {
		return driver.findElements(By.xpath("//input[contains(@class,'ng-invalid-required')]|//select[contains(@class,'ng-invalid-required')]"));
	}
	
	public void cerrarPrefactibilidad() {
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[contains(.,'Cerrar')]")).click();
		sleep(500);
	}
	
	public void tipoDeCasa(String tipo) {
		driver.findElement(By.xpath("//label/span[contains(.,'" + tipo + "')]")).click();
	}
	
	public void tipoDeZona(String tipo) {
		Select selectorZona = new Select(driver.findElement(By.xpath("//select[@id='ZoneType']")));
		selectorZona.selectByVisibleText(tipo);
	}
	
	public WebElement obtenerCampo(String nombre) {
		return driver.findElement(By.xpath("//input[@id='" + nombre + "']|//select[@id='" + nombre + "']|//textarea[@id='" + nombre + "']"));
	}
	
	
}
