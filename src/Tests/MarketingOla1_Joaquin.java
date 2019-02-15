package Tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Marketing;
import Pages.setConexion;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class MarketingOla1_Joaquin extends TestBase {
	private WebDriver driver;
	protected Marketing Page;
	
	@BeforeClass(alwaysRun = true, groups = {"Marketing", "Ola1"})
	public void init() {
		driver = setConexion.setupEze();
		Page = new Marketing(driver);
		loginMarketing(driver);
		cambiarListaLightningAVistaClasica(driver);
		try {
			Page.cajonDeAplicaciones("Consola FAN");
		}catch(Exception ex) {
			sleep(3000);
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
		
	}
	
	@BeforeMethod(alwaysRun = true, groups = {"Marketing", "Ola1"})
	public void before() {
		Page.cerrarTodasLasPestanas();
	}
	
	@AfterClass(alwaysRun = true, groups = {"Marketing", "Ola1"})
	public void exit() {
		Page.cerrarTodasLasPestanas();
		//Page.cajonDeAplicaciones("Ventas");
		driver.close();
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaSinServicio")
	public void TS90242_Sucripcion_CP_No_Validacion_de_Mail(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.clubPersonal("alta");
		
		Assert.assertTrue(Page.verificarMensajeDeErrorEmail());
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98022_Visualizar_botones_ABM_del_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		
		List<WebElement> botones = Page.obtenerBotonesClubPersonal();
		for (WebElement boton : botones) {
			Assert.assertTrue(boton.getText().contains("Alta") || boton.getText().contains("Baja") || boton.getText().contains("Modificación"));
		}
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaConFraude")
	public void TS98025_Visualizar_error_Fraude_Alta_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.clubPersonal("alta");
		
		Assert.assertTrue(Page.verificarMensajeDeErrorCuentaFraude());
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaConFraude")
	public void TS98028_Generar_Caso_error_Fraude_Alta_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.clubPersonal("alta");
		
		String numeroCaso = null;
		if (Page.verificarMensajeDeErrorCuentaFraude()) {
			numeroCaso = Page.obtenerNumeroCasoCuentaFraude();
		}
		else Assert.assertTrue(false);
		
		Page.cerrarTodasLasPestanas();
		Page.irACasos();
		Assert.assertTrue(Page.corroborarCasoCerrado(numeroCaso));
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98029_Visualizar_cuentas_Customer_Alta_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.clubPersonal("alta");
		
		Assert.assertTrue(Page.visualizarCuentasConsumerUsuarioCP());
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98030_Visualizar_cuentas_Business_Alta_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.clubPersonal("alta");
		
		Assert.assertTrue(Page.visualizarCuentasBusinessUsuarioCP());
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98039_Visualizacion_de_cuentas_seleccionadas_Alta_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.estadoAltaBaja("alta");
		Page.seleccionarCuenta("consumerAccounts");
		Page.botonSiguiente().click();

		Assert.assertTrue(Page.visualizarCuentasSeleccionadasConsumerCP());
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98040_No_visualizacion_de_cuentas_sin_seleccionar_Alta_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.estadoAltaBaja("alta");
		Page.seleccionarCuenta("consumerAccounts");
		Page.botonSiguiente().click();
		
		Assert.assertTrue(!Page.visualizarCuentasSeleccionadasBusinessCP());
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98063_Verificar_creacion_de_caso_cerrado_Notificacion_Baja_CP(String nombreCuenta) {
		Page.elegirCuenta(nombreCuenta);
		Page.irAGestionMarketing();
		Page.estadoAltaBaja("baja");
		Page.seleccionarCuenta("businessAccounts");
		Page.seleccionarMotivo(1);
		Page.botonSiguiente().click();
		sleep(1000);
		Page.botonSiguiente().click();
		sleep(1500);
		
	    String numeroCaso = Page.obtenerNumeroCasoAltaOBaja(); 
	    Page.cerrarTodasLasPestanas();
		Page.irACasos();
		
		Assert.assertTrue(Page.corroborarCasoCerrado(numeroCaso));
	}
	
	@Test(groups = {"Marketing", "Ola1", "GestionDelSocioDeClubPersonal"}, dataProvider="MarketingCuentaNormal")
	public void TS98064_Visualizar_mensaje_al_cerrar_el_caso_Notificacion_Baja_CP(String nombreCuenta) throws IOException {
		Page.seleccionarCuentaMarketing(nombreCuenta, "Vista Marketing");
		Page.irAGestionMarketing();
		Page.estadoAltaBaja("baja");
		Page.seleccionarCuenta("businessAccounts");
		Page.seleccionarMotivo(1);
		Page.botonSiguiente().click();
		sleep(1000);
		Page.botonSiguiente().click();
		sleep(1500);
		
		Assert.assertTrue(Page.obtenerTextoCasoGeneradoAltaOBaja().contains("Su gestión ha sido procesada, el número es:"));
	}
}
