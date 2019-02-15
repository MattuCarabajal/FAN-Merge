package Tests;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.Page;

import Pages.BasePage;
import Pages.OM;
import Pages.setConexion;

public class OMRuben extends TestBase {

	private WebDriver driver;
	private OM pageOm;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		this.driver = setConexion.setupEze();
		sleep(5000);
		// Usuario Victor OM
		login(driver, "https://crm--sit.cs14.my.salesforce.com/", "U585991", "Testa10k");
		sleep(5000);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		driver.switchTo().defaultContent();
		sleep(2000);
		BasePage bp = new BasePage(driver);
		bp.cajonDeAplicaciones("Sales");

		// click +
		sleep(5000);
		pageOm = new OM(driver);
		pageOm.clickMore();
		sleep(3000);

		// click en Ordenes
		pageOm.clickOnListTabs("Orders");
	}

	// @AfterClass(alwaysRun=true)
	public void tearDown() {
		sleep(2000);
		driver.quit();
		sleep(1000);
	}

	@Test(groups = "OM")
	public void TS6723_CRM_OM_Ordenes_Vista_Configuracion_Borrar_Vista() {

		// Crear Nueva Vista
		sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"filter_element\"]/div/span/span[2]/a[2]")).click();

		sleep(5000);

		// Completar el Formulario y Guardar
		driver.findElement(By.id("fname")).sendKeys("Vista Temporal de Ruben");
		driver.findElement(By.cssSelector(".btn.primary")).click();

		sleep(5000);

		// Seleccionar Vista
		Select vistaSelect = new Select(driver.findElement(By.name("fcf")));
		vistaSelect.selectByVisibleText("Vista Temporal de Ruben");
		sleep(2000);
		driver.findElement(By.className("filterLinks")).findElements(By.tagName("a")).get(1).click();

		sleep(5000);

		// Borrar Vista
		try {
			driver.findElement(By.name("delID")).click();
			sleep(5000);
		} catch (UnhandledAlertException f) {
			try {
				// Aceptar Alerta para Borrar Lista
				Alert confirmDelete = driver.switchTo().alert();
				confirmDelete.accept();
			} catch (NoAlertPresentException e) {
				e.printStackTrace();
			}
		}

		sleep(5000);

		// Chequear Si La Lista Se Borró
		vistaSelect = new Select(driver.findElement(By.name("fcf")));

		List<WebElement> elementosVistaSelect = vistaSelect.getOptions();

		Boolean vistaEncontrada = false;

		for (WebElement e : elementosVistaSelect) {
			if (e.getText().equalsIgnoreCase("Vista Temporal de Ruben")) {
				vistaEncontrada = true;
				break;
			}
		}

		Assert.assertFalse(vistaEncontrada);

	}

	@Test(groups = "OM")
	public void TS6724_CRM_OM_Ordenes_Vista_Log_in_con_vista_previamente_utilizada() {

		// Seleccionar Una Vista Random
		sleep(3000);
		Select vistaSelect = new Select(driver.findElement(By.name("fcf")));
		List<WebElement> elementosVistaSelect = vistaSelect.getOptions();
		OM pageOm = new OM(driver);
		String textoVistaRandom = pageOm.getRandomElementFromList(elementosVistaSelect).getText();
		vistaSelect.selectByVisibleText(textoVistaRandom);

		// Logout
		sleep(3000);
		omLogout(driver);

		// Login
		sleep(2000);
		omInternalLoginWithCredentials(driver, "U585991", "Testa10k");

		// Navegar hasta Ordenes
		sleep(5000);
		driver.switchTo().defaultContent();
		sleep(5000);
		BasePage bp = new BasePage(driver);
		bp.cajonDeAplicaciones("Sales");
		sleep(2000);
		pageOm.clickMore();
		sleep(3000);
		pageOm.clickOnListTabs("Orders");

		// Verificar si la misma Vista esta seleccionada
		sleep(5000);
		vistaSelect = new Select(driver.findElement(By.name("fcf")));
		Boolean mismaVista = vistaSelect.getFirstSelectedOption().getText().equalsIgnoreCase(textoVistaRandom);

		Assert.assertTrue(mismaVista);

	}

	/* El test TS6725 puede fallar si ambos usuarios seleccionen la misma vista */
	// 20180611 No hace logout, login entra con el mismo usuario

	@Test(groups = "OM")

	public void TS6725_CRM_OM_Ordenes_Vista_Log_in_con_vista_previamente_utilizada_por_otro_usuario() {

		// Seleccionar una Vista Random para un Primer Usuario
		sleep(3000);
		Select vistaSelect = new Select(driver.findElement(By.name("fcf")));
		List<WebElement> elementosVistaSelect = vistaSelect.getOptions();
		OM pageOm = new OM(driver);
		String vistaPrimerUsuario = pageOm.getRandomElementFromList(elementosVistaSelect).getText();
		vistaSelect.selectByVisibleText(vistaPrimerUsuario);

		// Logout del Primer Usuario
		sleep(3000);
		omLogout(driver);

		/* No hace logout y continua con el mismo usuario */

		// Login con otro Usuario
		sleep(2000);
		omInternalLoginWithCredentials(driver, "u589831", "Testa10k");

		// Navegar hasta Ordenes
		sleep(5000);
		driver.switchTo().defaultContent();
		sleep(5000);
		BasePage bp = new BasePage(driver);
		bp.cajonDeAplicaciones("Sales"); /* Valor Anterior: Ventas - Fallaba porque encuentras Sales */
		sleep(2000);
		pageOm.clickMore();
		sleep(3000);
		pageOm.clickOnListTabs("Orders");
		sleep(5000);
		// driver.findElement(By.cssSelector(".listRelatedObject.orderBlock.title")).click();

		// Verificar que la Vista no sea la del Primer Usuario
		vistaSelect = new Select(driver.findElement(By.name("fcf")));
		String vistaOtroUsuario = vistaSelect.getFirstSelectedOption().getText();

		System.out.println(vistaPrimerUsuario);
		System.out.println(vistaOtroUsuario);

		Assert.assertFalse(vistaPrimerUsuario.equals(vistaOtroUsuario));
	}

	@Test(groups = "OM")
	public void TS_52660_CRM_OM_Ordenes_Cliente_Existente_Baja_de_linea_Sin_VAS_Paso_0() {
		pageOm.selectVistaByVisibleText("RubenOM-Activated");
		sleep(6000);
		pageOm.getAccountList().get(0).click();
		pageOm.irAChangeToOrder();
		sleep(6000);
		// Ingresar Fecha Futura
		driver.findElement(By.id("RequestDate")).sendKeys(pageOm.getFechaAvanzadaFormateada_MM_dd_yyyy());
//		driver.findElement(By.id("RequestDate")).sendKeys("06-21-2018");
//		sleep(1000);
//		driver.findElement(By.xpath("//*[@id=\"a1zc0000003XcLmAAK-1\"]/div[2]/div[3]/button")).click();
//		sleep(2000);
//		pageOm.checkFutureDateRestriction();
		 
//		pageOm.crearOrdenConGestion("RubenOM", "Desconexi\u00f3n");
//		sleep(6000);
//		pageOm.getTaSubmitOrderButton().click();
	}

}
