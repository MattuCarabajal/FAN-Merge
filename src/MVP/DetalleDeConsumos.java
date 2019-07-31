package MVP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class DetalleDeConsumos extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private CustomerCare cc;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	String detalles;
	
	
	//@BeforeClass (groups= "PerfilOficina")
	public void initSIT() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.LoginSit();
		ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
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

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico", "MVP"}, dataProvider = "CuentaVista360")
	public void TS134802_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
		imagen = "TS134802";
		detalles = imagen + "-Vista 360 - DNI: " + sDNI + " - Nombre: " + sNombre;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Detalles de Consumo");
		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
		driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click")).get(1).findElement(By.cssSelector(".slds-button.slds-input__icon.slds-text-color--default")).click();
		WebElement periodosDeConsulta = driver.findElement(By.cssSelector("[class='slds-grid slds-wrap slds-grid--pull-padded slds-m-around--medium slds-p-around--medium negotationsfilter'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--2-of-8 slds-large-size--2-of-8'] [class='slds-dropdown slds-dropdown--left'] [class='slds-dropdown__list slds-dropdown--length-3']"));
		periodosDeConsulta.findElements(By.tagName("li")).get(1).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-p-top--medium'] tbody tr"), 0));
		List<WebElement> filas = driver.findElements(By.cssSelector("[class='slds-p-top--medium'] tbody tr"));
		cc.obligarclick(filas.get(0));
		filas = driver.findElements(By.cssSelector("[class='slds-p-top--medium'] tbody tr"));
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-p-top--medium'] tbody tr tr"), 3));
		List<WebElement> detalles = driver.findElements(By.cssSelector("[class='slds-p-top--medium'] tbody tr tr"));
		List<String> detallesReferencia = new ArrayList<String>(Arrays.asList("FECHA DE INICIO", "FECHA DE FIN", "PRODUCTO", "IMPORTE", "ORIGEN/DESTINO"));
		for (int i = 0; i < detalles.size(); i++) {
			Assert.assertTrue(detalles.get(i).getText().toLowerCase().contains(detallesReferencia.get(i).toLowerCase()));
		}
	}
}