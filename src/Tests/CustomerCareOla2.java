package Tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.setConexion;

public class CustomerCareOla2 extends TestBase {
	
	private WebDriver driver;
	private CustomerCare cc;
	
	
	@BeforeClass (alwaysRun = true, groups = {"CustomerCare", "Ola2", "Marcas"})
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		loginOfCom(driver);
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch(Exception e) {
			sleep(3000);
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(6000);
		}
	}
	
	@AfterClass (alwaysRun = true, groups = {"CustomerCare", "Ola2", "Marcas"})
	public void exit() {
		driver.quit();
		sleep(5000);
	}
	
	@BeforeMethod (alwaysRun = true, groups = {"CustomerCare", "Ola2", "Marcas"})
	public void before() {
		cc.cerrarTodasLasPestanas();
	}
	
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")
	public void TS100939_360_View_POSTPAGO_Visualizacion_Resumen_de_Facturacion_Verificacion_filtro_Fecha(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();		
		WebElement fechaHasta = cc.obtenerFechaHasta();
		Assert.assertTrue(fechaHasta.getAttribute("value").contentEquals(fechaDeHoy()));
	}
	//ojo con este caso
	@Test (groups = {"CustomerCare", "Ola2", "Marcas","filtrado"}, dataProvider = "CustomerCuentaActiva") //Rompe porque no existe la gestion Marcas, se paso a Ola 3 y no esta en funcionamiento
	public void TS100963_Marks_Management_Base_Conocimiento_Acceso_a_base_de_conocimiento(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("marcas");
		Assert.assertTrue(cc.verificarBaseConocimientoMarcas());
	}
	
	//@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque no existe la gestion Marcas, se paso a Ola 3 y no esta en funcionamiento
	public void TS100967_Marks_Management_Escenario_de_Casos_Existentes_Ingresar_comentarios(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("marcas");
		cc.SeleccionarClienteOCuenta("Cliente");
		cc.botonSiguiente().click();
		sleep(1000);	
		cc.seleccionarMarca(1);
		cc.botonConsultar().click();	
		Assert.assertTrue(cc.campoComentarios().isDisplayed());
	}
	
	//@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque no existe la gestion Marcas, se paso a Ola 3 y no esta en funcionamiento
	public void TS100971_Marks_Management_Marcas_Session_Guiada_Boton_en_Iniciar_gestiones(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("marcas");
		WebElement tab = cc.obtenerPestanaActiva();		
		Assert.assertTrue(tab.getText().contentEquals("Gesti�n de Marcas"));
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")
	public void TS100941_360_View_POSTPAGO_Visualizacion_Resumen_de_Facturacion_Campos_Fecha_no_editables(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		buscarYClick(driver.findElements(By.className("slds-text-body_regular")), "contains", "resumen de cuenta");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		String date1 = driver.findElement(By.id("text-input-id-1")).getAttribute("value");
		driver.findElement(By.id("text-input-id-1")).sendKeys("01/01/1000");
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getAttribute("value").equals(date1));
	}
	
	//@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Rompe porque no existe la gestion Marcas, se paso a Ola 3 y no esta en funcionamiento
	public void TS100965_Mark_Management_Resumen_Caso_Solicitud_Aplicacion_Marcas_Visualizar_mensaje(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAGestion("marcas");
		cc.SeleccionarClienteOCuenta("Cliente");
		driver.findElement(By.id("SelectAccount_nextBtn")).click();
		sleep(3000);
		Select mark = new Select (driver.findElement(By.id("MarksList")));
		mark.selectByVisibleText("No Cobro de Intereses");
		driver.findElement(By.id("NewMark_nextBtn")).click();
		sleep(3000);
		WebElement res = driver.findElement(By.cssSelector(".slds-card.ng-scope"));
		Assert.assertTrue(res.getText().toLowerCase().contains("tipo de accion:") && res.getText().toLowerCase().contains("marca:") && res.getText().toLowerCase().contains("vigencia:"));
		driver.findElement(By.id("Summary_nextBtn")).click();
		sleep(7000);
		boolean a = false;
		List <WebElement> gest = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : gest) {
			if (x.getText().toLowerCase().contains("se ha generado la gesti\u00f3n")) {
				a = true;
			}
		}
		String msg = driver.findElement(By.xpath("//*[@id=\"VlocityBPView\"]/div/div/header/h1")).getText();
		int i = 0;
		while(msg.charAt(i++) != '0') {}
		String caso = msg.substring(i-1, msg.length());
		caso = caso.substring(0, caso.lastIndexOf(" "));
		cc.buscarCaso(caso);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".feeditemaux.cxfeeditemaux.CreateRecordAuxBody")));
		WebElement vc = driver.findElement(By.cssSelector(".feeditemaux.cxfeeditemaux.CreateRecordAuxBody"));
		Assert.assertTrue(vc.getText().toLowerCase().contains("gesti\u00f3n de marcas") && a);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")
	public void TS100940_360_View_POSTPAGO_Visualizacion_Resumen_de_Facturacion_Verificacion_grisado_Fecha(String cCuenta) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		WebElement fh = driver.findElement(By.id("text-input-id-2"));
		Assert.assertTrue(fh.getAttribute("max-date").contains(dateFormat.format(date)));
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Los filtros de fecha no funcionan correctamente
	public void TS118727_360_View_POSTPAGO_Visualizacion_Resumen_de_Facturacion_Resumen_de_Cta_se_muestra_registros_de_los_ultimos_6_meses(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")
	public void TS118763_360_View_Resumen_Cta_Cte_Simple_Integracion_historial_de_pagos_S059_Obtener_historial_de_pagos(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(7000);
		WebElement comp = driver.findElements(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).get(0).findElement(By.tagName("p"));
		Assert.assertTrue(comp.getText().toLowerCase().contains("comprobantes"));
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Requiere actualizacion
	public void TS118732_360_View_POSTPAGO_Visualizacion_Resumen_de_Facturacion_Fecha_Desde_muestra_griseadas_las_fechas_anteriores_a_6_meses_de_la_fecha_actual(String cCuenta) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance(); 
        cal.setTime(date); 
        cal.add(Calendar.MONTH, -6);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		String mes = "";
		String fh = driver.findElement(By.id("text-input-id-1")).getAttribute("min-date");
		String[] meses = {"Jan","Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		for(int i=0; i<=11; i++) {
			if (fh.contains(meses[i])) {
				int parc = i+1;
				if(parc < 10)
					mes = "0"+parc;
				else
					mes = Integer.toString(parc);
			}
		}
		Assert.assertTrue((fh.split(" ")[3]+"-"+mes+"-"+fh.split(" ")[2]).contains(dateFormat.format(date)));
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96127_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_2_se_muestra_el_campo_Numero_de_Cupon_de_Telecom_Argentina_numero(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //
	public void TS96125_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_2_se_muestra_el_campo_Numero_de_Tarjeta_cheque_numero(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96129_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_2_se_muestra_el_campo_Tipo_Cupon_texto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96128_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_2_se_muestra_el_campo_Tipo_de_Comprobante_texto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96133_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_3_se_muestra_el_campo_Descripcion_de_Motivo_texto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96132_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_3_se_muestra_el_campo_Fecha_ATM_dd_mm_aaaa(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96131_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_3_se_muestra_el_campo_Usuario_Modificacion_texto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
	
	@Test (groups = {"CustomerCare", "Ola2", "Marcas"}, dataProvider = "CustomerCuentaActiva")  //Hay que preguntar como es el caso
	public void TS96130_360_View_POSTPAGO_UX_Visualizacion_Resumen_de_Facturacion_Verificar_que_mas_detalle_de_un_registro_especifico_de_pago_del_grupo_3_se_muestra_el_campo_Usuario_texto(String cCuenta) {
		cc.elegirCuenta(cCuenta);
		cc.irAFacturacion();
		cc.irAResumenDeCuenta();
		Assert.assertTrue(false);
	}
}