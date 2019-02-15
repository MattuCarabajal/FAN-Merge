package Tests;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.CustomerCare;

public class CustomerCare360Joaquin extends TestBase {
	
	CustomerCare Customer;
	
	private By btn_VerDetalles = By.cssSelector(".slds-button.slds-button--brand");
	private By campos_TarjetaHistorial = By.cssSelector(".slds-truncate.slds-th__action");
	private By tablaTarjetaHistorial = By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header");

	
	@BeforeClass(groups= {"CustomerCare", "DebitoAutomatico", "DetalleDeConsumos", "Vista360Layout"})
	public void init() {
		inicializarDriver();
		Customer = new CustomerCare(driver);
		login();
		IrA.CajonDeAplicaciones.ConsolaFAN();
	}
	
	//@AfterClass(groups= {"CustomerCare", "DebitoAutomatico", "DetalleDeConsumos", "Vista360Layout"})
	public void quit() {
		Customer.cerrarTodasLasPestanas();
		IrA.CajonDeAplicaciones.Ventas();
		cerrarTodo();
	}
	
	@BeforeMethod(groups= {"CustomerCare", "DebitoAutomatico", "DetalleDeConsumos", "Vista360Layout"})
	public void after() {
		Customer.cerrarTodasLasPestanas();
	}
	
	@Test(groups= {"CustomerCare", "DetalleDeConsumos"})
	public void TS38068_Consumption_Details_Definicion_de_Filtros_sobre_Calendario_Fecha_Desde_No_se_puede_ingresar_una_fecha_posterior_a_día_de_consulta() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Detalle de Consumos");
		
		WebElement selectorPeriodo = driver.findElement(By.xpath("//input[@ng-model='ptc.filterOption']"));
		selectorPeriodo.click();
		
		List<WebElement> opciones = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown--left li"));
		for (WebElement opcion : opciones) {
			if (opcion.getText().contains("Un rango personalizado")) {
				opcion.click();
				break;
			}
		}
		
		WebElement fechaInicio = driver.findElement(By.id("text-input-id-1"));
		waitFor.elementToBeClickable(fechaInicio);
		fechaInicio.click();
		
		List<WebElement> diasCalendario = driver.findElements(By.className("slds-day"));
		int ultimoDiaDelCalendario = diasCalendario.size() - 1;
		diasCalendario.get(ultimoDiaDelCalendario).click();
		
		Assert.assertTrue(fechaInicio.getAttribute("value").equals(""));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38164_360_View_UX_360_Card_Historiales_Visualizar_HISTORIAL_DE_PACKS() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		List<WebElement> historiales = driver.findElements(By.cssSelector(".slds-grid.slds-grid--align-spread.slds-grid--vertical-align-center"));
		for (WebElement historial : historiales) {
			if (historial.getText().contains("Historial de packs")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38166_360_View_UX_360_Card_Historiales_Visualizar_HISTORIAL_DE_AJUSTES() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		List<WebElement> historiales = driver.findElements(By.cssSelector(".slds-grid.slds-grid--align-spread.slds-grid--vertical-align-center"));
		for (WebElement historial : historiales) {
			if (historial.getText().contains("Historial de ajustes")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38169_360_View_UX_360_Card_Historiales_Visualizar_botón_Ver_Detalle_HISTORIAL_DE_RECARGAS_SOS() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Recargas S.O.S");
		
		Assert.assertTrue(tarjeta.findElement(btn_VerDetalles).isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38170_360_View_UX_360_Card_Historiales_Visualizar_botón_Ver_Detalle_HISTORIAL_DE_AJUSTES() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Ajustes");
		
		Assert.assertTrue(tarjeta.findElement(btn_VerDetalles).isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38172_360_View_UX_360_Card_Historiales_Campos_Historial_de_Packs() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Packs");
		
		List<WebElement> columnas = tarjeta.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		List<String> textoColumna = new ArrayList<String>();
		for (WebElement columna : columnas) {
			textoColumna.add(columna.getText());
		}
		
		Assert.assertTrue(textoColumna.contains("FECHA"));
		Assert.assertTrue(textoColumna.contains("VENCIMIENTO"));
		Assert.assertTrue(textoColumna.contains("NOMBRE DEL PACK"));
		Assert.assertTrue(textoColumna.contains("MONTO"));	
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38174_360_View_UX_360_Card_Historiales_Campos_Historial_de_Ajustes() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Ajustes");
		
		List<WebElement> columnas = tarjeta.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		List<String> textoColumna = new ArrayList<String>();
		for (WebElement columna : columnas) {
			textoColumna.add(columna.getText());
		}
		
		Assert.assertTrue(textoColumna.contains("FECHA"));
		Assert.assertTrue(textoColumna.contains("MOTIVO"));
		Assert.assertTrue(textoColumna.contains("MONTO"));
	}

	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38185_360_View_360_View_Historial_de_Packs_Desplegable_nombre_Historial_Packs() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Historial de Packs");

		WebElement selectorNombrePack = driver.findElement(By.id("text-input-03"));
		Assert.assertTrue(selectorNombrePack.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38186_360_View_360_View_Historial_de_Packs_Fecha_Desde_y_Hasta_no_superan_los_30_dias() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Historial de Packs");

		WebElement calendarioFechaFin = driver.findElement(By.id("text-input-id-2"));
		String valorViejo = calendarioFechaFin.getAttribute("value");
		calendarioFechaFin.click();
		
		List<WebElement> diasCalendario = driver.findElements(By.className("slds-day"));
		int ultimoDiaDelCalendario = diasCalendario.size() - 1;
		diasCalendario.get(ultimoDiaDelCalendario).click();
		String valorNuevo = calendarioFechaFin.getAttribute("value");
		
		Assert.assertTrue(valorViejo.equals(valorNuevo));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38187_360_View_360_View_Historial_de_Packs_Detalle_Aperturar_registro_Detalle() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Historial de Packs");

		WebElement botonConsultar = driver.findElement(By.xpath("//button[contains(.,'Consultar')]"));
		botonConsultar.click();
		
		List<WebElement> registrosHistorial = driver.findElements(By.cssSelector(".slds-input__icon--left.slds-icon.slds-icon--x-small.slds-input__icon"));
		registrosHistorial.get(0).click();

		List<WebElement> detalleRegistrosHistorial = driver.findElements(By.xpath("//div[@class='slds-grid']"));
		for (WebElement registro : detalleRegistrosHistorial) {
			if (registro.isDisplayed()) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38188_360_View_360_View_Historial_de_Packs_Detalle_Ordenamiento_columna_cierra_registros() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Historial de Packs");

		WebElement botonConsultar = driver.findElement(By.xpath("//button[contains(.,'Consultar')]"));
		botonConsultar.click();

		List<WebElement> registrosHistorial = driver.findElements(By.cssSelector(".slds-input__icon--left.slds-icon.slds-icon--x-small.slds-input__icon"));
		registrosHistorial.get(0).click();
		
		List<WebElement> columnasHistorial = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		columnasHistorial.get(3).click();
		
		Assert.assertTrue(false); // DESDE EL DEPLOY DE FASE 4 NO SE CIERRAN LOS REGISTROS ABIERTOS DESPUES DE ORDENAR
		List<WebElement> detalleRegistrosHistorial = driver.findElements(By.xpath("//div[@class='slds-grid']"));
		for (WebElement registro : detalleRegistrosHistorial) {
			if (registro.isDisplayed())
				Assert.assertTrue(false);
		}
		Assert.assertTrue(true);
	}
	

	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38189_360_View_Historial_de_Recargas_Pre_pago_Visualización_de_registros_y_criterios_de_ordenamiento_Ordenamiento_columna() {
		Customer.elegirCuenta("aaaaFernando Care"); 
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		Customer.irAHistorialDeRecargas();

		WebElement registroHistorial = driver.findElement(By.cssSelector(".slds-input__icon--left.slds-icon.slds-icon--x-small.slds-input__icon"));
		registroHistorial.click();
		
		List<WebElement> columnasHistorial = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		columnasHistorial.get(0).click();

		List<WebElement> detallesRegistroHistorial = driver.findElements(By.xpath("//div[@class='slds-grid']"));
		for (WebElement registro : detallesRegistroHistorial) {
			if (registro.isDisplayed())
				Assert.assertTrue(false);
		}
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"CustomerCare", "DebitoAutomatico"})
	public void TS38205_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_NO_adherida_a_Aut_Deb_Que_se_vea() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Débito automático");
		
		WebElement adhesion = driver.findElement(By.className("borderOverlay"));
		adhesion.click();
		
		List<WebElement> cuentas = driver.findElements(By.xpath("//label[@class='slds-checkbox__label']"));
		waitFor.visibilityOfAllElements(cuentas);
		
		Assert.assertTrue(cuentas.get(0).isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "DebitoAutomatico"})
	public void TS38231_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_con_Financiamiento_de_deuda() {
		Customer.elegirCuenta("aaaaCuenta FinancDeuda");
		Customer.irAGestion("Débito automático");
		
		WebElement adhesion = driver.findElement(By.className("borderOverlay"));
		adhesion.click();
		sleep(1000);
		
		WebElement inputs = driver.findElement(By.xpath("//span[@class='slds-checkbox']"));
		inputs.findElement(By.tagName("span")).click();
		sleep(500);
		
		WebElement msg = driver.findElement(By.cssSelector(".message.description"));
		
		Assert.assertTrue(msg.getText().contains("suspendida por financiación vigente"));
	}
	
	@Test(groups= {"CustomerCare", "DebitoAutomatico"})
	public void TS38233_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_activa_pero_con_servicios_inactivos() {
		Customer.elegirCuenta("aaaaCuenta Activa Serv Inact");
		Customer.irAGestion("Débito automático");
		
		WebElement msgError = driver.findElement(By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope h1"));
		waitFor.visibilityOfElement(msgError);

		Assert.assertTrue(msgError.getText().contains("Error"));
	}
	
	@Test(groups= {"CustomerCare", "DebitoAutomatico"})
	public void TS38234_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_Inactiva() {
		Customer.elegirCuenta("aaaaAndres Care");
		Customer.irAGestion("Débito automático");
		
		WebElement msgError = driver.findElement(By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope h1"));
		waitFor.visibilityOfElement(msgError);

		Assert.assertTrue(msgError.getText().contains("Error"));
	}
	
	@Test(groups= {"CustomerCare", "DebitoAutomatico"})
	public void TS38235_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_sin_servicios () {
		Customer.elegirCuenta("aaaaCuenta Activa S/Serv");
		Customer.irAGestion("Débito automático");
		
		WebElement msgError = driver.findElement(By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope h1"));
		waitFor.visibilityOfElement(msgError);

		Assert.assertTrue(msgError.getText().contains("Error"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38416_360_View_360_card_servicio_prepago_Header_Visualizar_campos() {
		Customer.elegirCuenta("aaaaFernando Care");
		
		WebElement headerServicioActivo = driver.findElement(By.cssSelector(".console-card.active .card-top"));
		String textoTarjeta = headerServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Fecha de activación"));
		Assert.assertTrue(textoTarjeta.contains("Línea"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38417_360_View_360_card_servicio_prepago_Información_de_la_card_Visualizar_campos() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement detallesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .details"));
		String textoTarjeta = detallesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Estado"));
		Assert.assertTrue(textoTarjeta.contains("Crédito recarga"));
		Assert.assertTrue(textoTarjeta.contains("Crédito promocional"));
		Assert.assertTrue(textoTarjeta.contains("Internet disponible"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38418_360_View_360_card_servicio_prepago_Acciones_Detalle_de_consumos() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Detalle de Consumos"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38419_360_View_360_card_servicio_prepago_Acciones_Historial_de_Recargas() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Historiales"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38421_360_View_360_card_servicio_prepago_Mis_Servicios() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Mis servicios"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38471_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Nombre_del_producto() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement nombreProducto = driver.findElement(By.cssSelector(".console-card.active .card-top .header-left h2"));
		
		Assert.assertTrue(nombreProducto.getText().length() > 0);
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38472_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Fecha_de_activación() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement fechaActivacion = driver.findElement(By.cssSelector(".console-card.active .card-top .header-left .slds-text-body_regular"));
		
		Assert.assertTrue(fechaActivacion.getText().length() > 0); 
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38473_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Estado() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement detallesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .details"));
		String textoTarjeta = detallesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Estado"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38474_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Numero_de_línea() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement numeroLinea = driver.findElement(By.cssSelector(".console-card.active .card-top .header-right div"));
		
		Assert.assertTrue(numeroLinea.getText().length() > 0);
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38475_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Crédito_de_Recarga() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement detallesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .details"));
		String textoTarjeta = detallesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Crédito recarga"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38476_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Internet_Disponible() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement detallesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .details"));
		String textoTarjeta = detallesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Internet disponible"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38477_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Acciones_Detalle_de_consumos() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
	
		Assert.assertTrue(textoTarjeta.contains("Detalle de Consumos"));
	}

	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38477_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Acciones_Recargas_y_Packs() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
	
		Assert.assertTrue(textoTarjeta.contains("Historiales"));
	}
	
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38479_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Acciones_Ahorrá() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Ahorrá"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38480_360_View_360_card_servicio_prepago_Persistencia_Visualizar_Acciones_Mis_Servicios() {
		Customer.elegirCuenta("aaaaFernando Care");

		WebElement accionesServicioActivo = driver.findElement(By.cssSelector(".console-card.active .actions"));
		String textoTarjeta = accionesServicioActivo.getText();
		
		Assert.assertTrue(textoTarjeta.contains("Mis servicios"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38628_360_View_360_View_Card_Pre_pago_Acción_sobre_Historiales_Visualizar_Ultimas_5_recargas_desde_el_dia_de_la_fecha() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Recargas");
		
		WebElement tabla = tarjeta.findElement(tablaTarjetaHistorial);
		List<WebElement> lineas = tabla.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		
		Assert.assertTrue(lineas.size() <= 5);
		for (WebElement l : lineas) {
			Assert.assertTrue(l.getText().length() != 0);
		}
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38629_360_View_360_View_Card_Pre_pago_Acción_sobre_Historiales_Visualizar_Ultimas_5_recargas_SOS_desde_el_dia_de_la_fecha() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Recargas S.O.S");
		
		WebElement tabla = tarjeta.findElement(tablaTarjetaHistorial);
		List<WebElement> lineas = tabla.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		
		Assert.assertTrue(lineas.size() <= 5);
		for (WebElement l : lineas) {
			Assert.assertTrue(l.getText().length() != 0);
		}
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38630_360_View_360_View_Card_Pre_pago_Acción_sobre_Historiales_Visualizar_Ultimas_5_compras_de_Packs_desde_el_dia_de_la_fecha() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Packs");
		
		WebElement tabla = tarjeta.findElement(tablaTarjetaHistorial);
		List<WebElement> lineas = tabla.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		
		Assert.assertTrue(lineas.size() <= 5);
		for (WebElement l : lineas) {
			Assert.assertTrue(l.getText().length() != 0);
		}
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38631_360_View_360_View_Card_Pre_pago_Acción_sobre_Historiales_Visualizar_Ultimos_5_ajustes_desde_el_dia_de_la_fecha() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Ajustes");
		
		WebElement tabla = tarjeta.findElement(tablaTarjetaHistorial);
		List<WebElement> lineas = tabla.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		
		Assert.assertTrue(lineas.size() <= 5);
		for (WebElement l : lineas) {
			Assert.assertTrue(l.getText().length() != 0);
		}
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS38637_360_View_360_View_Card_Pre_pago_Acción_sobre_Historiales_Ordenar_ajustes_por_Monto() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		// TODAVIA NO FUNCIONAN LOS HISTORIALES
		WebElement tarjeta = Customer.obtenerTarjetaHistorial("Historial de Ajustes");
		
		List<WebElement> camposOrdenables = tarjeta.findElements(campos_TarjetaHistorial);
		WebElement campo = null;
		for (WebElement c : camposOrdenables) {
			if (c.getText().contains("MONTO")) {
				campo = c;
				c.click();
				break;
			}
		}
		
		Assert.assertTrue(campo.findElement(By.cssSelector(".slds-icon.slds-icon--x-small.slds-icon-text-default.slds-is-sortable__icon")).isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS68996_360_View_360_View_Mis_servicios_Visualizar_numero_de_linea_asociada_al_asset() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAMisServicios();
		
		WebElement numeroLinea = driver.findElement(By.cssSelector(".lineNumber.via-slds b"));
		Assert.assertTrue(numeroLinea.isDisplayed());
	}

	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69025_360_View_360_View_Historiales_Datos_Visualizar_Numero_de_linea() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAHistoriales();
		
		WebElement numeroLinea = driver.findElement(By.cssSelector(".lineNumber.via-slds b"));
		Assert.assertTrue(numeroLinea.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69029_360_View_360_View_Especificacion_Ordenamiento_Visaulizar_flecha_ordenamiento_Historial_de_Packs() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Historial de Packs");
		
		WebElement botonConsultar = driver.findElement(By.xpath("//button[contains(.,'Consultar')]"));
		botonConsultar.click();
		
		List<WebElement> columnasHistorial = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		columnasHistorial.get(0).click();
		
		List<WebElement> flechasOrdenamiento = driver.findElements(By.cssSelector(".slds-icon-text-default.slds-is-sortable__icon"));
		
		Assert.assertTrue(flechasOrdenamiento.get(0).isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69057_360_View_Buscador_de_Gestiones_Buscar_una_gestion_ingresando_todas_las_letras_en_mayuscula() {
		Customer.elegirCuenta("aaaaFernando Care");
		
		Customer.buscarGestion("ACTUALIZAR PAGO");
		
		Assert.assertTrue(Customer.gestionesEncontradas.get(0).getText().contains("Actualizar Pago"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69058_360_View_Buscador_de_Gestiones_Buscar_una_gestion_ingresando_todas_las_letras_en_minuscula() {
		Customer.elegirCuenta("aaaaFernando Care");
		
		Customer.buscarGestion("actualizar pago");
		
		Assert.assertTrue(Customer.gestionesEncontradas.get(0).getText().contains("Actualizar Pago"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69059_360_View_Buscador_de_Gestiones_Buscar_una_gestion_que_tiene_tilde_ingresando_el_texto_a_buscar_sin_tildes() {
		Customer.elegirCuenta("aaaaFernando Care");
		
		Customer.buscarGestion("Debito automatico");

		Assert.assertTrue(Customer.gestionesEncontradas.get(0).getText().contains("Débito automático"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69096_360_View_360_VIEW_Ubicacion_de_Accion_Enlace_Acceso_TAB_Facturacion_Visualizar_boton_de_acceso_al_TAB_de_FACTURACION() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.panelIzquierdo();
		
		WebElement btn_Facturacion = driver.findElement(By.cssSelector(".profile-tags-header .slds-p-right--x-small"));
		
		Assert.assertTrue(btn_Facturacion.getText().contains("Facturación"));
		Assert.assertTrue(btn_Facturacion.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "DetalleDeConsumos"})
	public void TS69146_Consumption_Details_Criterios_de_Filtro_Temporal_Visualizar_calendario_en_filtro_Inicio() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Detalle de Consumos");
		
		WebElement selectorPeriodo = driver.findElement(By.xpath("//input[@ng-model='ptc.filterOption']"));
		selectorPeriodo.click();
		
		List<WebElement> opciones = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown--left li"));
		for (WebElement opcion : opciones) {
			if (opcion.getText().contains("Un rango personalizado")) {
				opcion.click();
				break;
			}
		}
		
		WebElement fechaInicio = driver.findElement(By.id("text-input-id-1"));
		fechaInicio.click();
		WebElement calendario = driver.findElement(By.xpath("//table[@class='slds-datepicker__month']"));
		
		Assert.assertTrue(calendario.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "DetalleDeConsumos"})
	public void TS69147_Consumption_Details_Criterios_de_Filtro_Temporal_Visualizar_fecha_mayor_a_la_actual_grisada_en_filtro_Inicio() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Detalle de Consumos");
		
		WebElement selectorPeriodo = driver.findElement(By.xpath("//input[@ng-model='ptc.filterOption']"));
		selectorPeriodo.click();
		
		List<WebElement> opciones = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown--left li"));
		for (WebElement opcion : opciones) {
			if (opcion.getText().contains("Un rango personalizado")) {
				opcion.click();
				break;
			}
		}
		
		WebElement fechaInicio = driver.findElement(By.id("text-input-id-1"));
		waitFor.elementToBeClickable(fechaInicio);
		fechaInicio.click();
		
		List<WebElement> dias = driver.findElements(By.cssSelector(".slds-datepicker__month td"));
		int indice = -1;
		for (WebElement dia : dias) {
			if (dia.getAttribute("class").contains("slds-is-today")) {
				indice = dias.indexOf(dia);
			}
		}
		
		if (indice < 0) {
			System.err.println("ERROR: No se encontró el día en el calendario");
			Assert.assertTrue(false);
		}
		
		WebElement fechaMayorActual = dias.get(indice + 1);

		Assert.assertTrue(fechaMayorActual.getAttribute("class").contains("slds-disabled-text"));
	}
	
	@Test(groups= {"CustomerCare", "DetalleDeConsumos"})
	public void TS69151_Consumption_Details_Criterios_de_Filtro_Temporal_Visualizar_calendario_en_filtro_Fin() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("Detalle de Consumos");
		
		WebElement selectorPeriodo = driver.findElement(By.xpath("//input[@ng-model='ptc.filterOption']"));
		selectorPeriodo.click();
		
		List<WebElement> opciones = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown--left li"));
		for (WebElement opcion : opciones) {
			if (opcion.getText().contains("Un rango personalizado")) {
				opcion.click();
				break;
			}
		}
		
		WebElement fechaFin = driver.findElement(By.id("text-input-id-2"));
		waitFor.elementToBeClickable(fechaFin);
		fechaFin.click();
		WebElement calendario = driver.findElement(By.xpath("//table[@class='slds-datepicker__month']"));
		
		Assert.assertTrue(calendario.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69191_360_View_360_View_Acceso_a_Gestiones_desde_el_Asset_Asset_Mobile_Prepago_Flyout_Acceso_Gestiones() {
		Customer.elegirCuenta("aaaaFernando Care");
		
		driver.findElement(By.cssSelector(".console-card.active .card-top")).click();

		List<WebElement> accionesFlyout = driver.findElements(By.cssSelector(".community-flyout-actions-card li"));
		for (WebElement accion : accionesFlyout) {
			if (accion.getText().contains("Gestiones")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69192_360_View_360_View_Acceso_a_Gestiones_desde_el_Asset_Asset_Mobile_Prepago_Flyout_Acceso_Gestiones_Titulo() {
		Customer.elegirCuenta("aaaaFernando Care");
		
		driver.findElement(By.cssSelector(".console-card.active .card-top")).click();

		List<WebElement> accionesFlyout = driver.findElements(By.cssSelector(".community-flyout-actions-card li"));
		for (WebElement accion : accionesFlyout) {
			if (accion.getText().contains("Gestiones")) {
				accion.click();
				break;
			}
		}
		
		WebElement pestaña = Customer.obtenerPestanaActiva();
		Assert.assertTrue(pestaña.getText().contains("Gestiones"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69163_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Visualizar_numero_de_linea() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		WebElement numeroLinea = driver.findElement(By.cssSelector(".slds-text-heading--large .lineNumber"));
		
		Assert.assertTrue(numeroLinea.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69176_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Visualizar_campos() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		List<WebElement> campos = driver.findElements(By.cssSelector(".slds-p-bottom--small .slds-th__action"));
		List<String> textos = new ArrayList<String>();
		for (WebElement c : campos)
			textos.add(c.getText());
		
		Assert.assertTrue(textos.contains("FECHA"));
		Assert.assertTrue(textos.contains("TIPO"));
		Assert.assertTrue(textos.contains("NÚMERO"));
		Assert.assertTrue(textos.contains("NOMBRE"));
		Assert.assertTrue(textos.contains("ESTADO"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69179_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Numero_Ordenar_ascendente() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		List<WebElement> campos = driver.findElements(By.cssSelector(".slds-p-bottom--small .slds-text-heading--label th"));
		WebElement numero = null;
		for (WebElement c : campos) {
			if (c.getText().contains("NÚMERO"))
				numero = c;
		}
		
		numero.click();
		sleep(500);
		if(!numero.getAttribute("class").contains("slds-is-sorted--asc")) {
			numero.click();
			sleep(500);
		}
		
		Assert.assertTrue(numero.getAttribute("class").contains("slds-is-sorted--asc"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69180_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Nombre_Ordenar_ascendente() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		List<WebElement> campos = driver.findElements(By.cssSelector(".slds-p-bottom--small .slds-text-heading--label th"));
		WebElement numero = null;
		for (WebElement c : campos) {
			if (c.getText().contains("NOMBRE"))
				numero = c;
		}
		
		numero.click();
		sleep(500);
		if(!numero.getAttribute("class").contains("slds-is-sorted--asc")) {
			numero.click();
			sleep(500);
		}
		
		Assert.assertTrue(numero.getAttribute("class").contains("slds-is-sorted--asc"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69184_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Nombre_Ordenar_descendente() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		List<WebElement> campos = driver.findElements(By.cssSelector(".slds-p-bottom--small .slds-text-heading--label th"));
		WebElement numero = null;
		for (WebElement c : campos) {
			if (c.getText().contains("NOMBRE"))
				numero = c;
		}
		
		numero.click();
		sleep(500);
		if(!numero.getAttribute("class").contains("slds-is-sorted--desc")) {
			numero.click();
			sleep(500);
		}
		
		Assert.assertTrue(numero.getAttribute("class").contains("slds-is-sorted--desc"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69185_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Numero_Ordenar_descendente() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		List<WebElement> campos = driver.findElements(By.cssSelector(".slds-p-bottom--small .slds-text-heading--label th"));
		WebElement numero = null;
		for (WebElement c : campos) {
			if (c.getText().contains("NÚMERO"))
				numero = c;
		}
		
		numero.click();
		sleep(500);
		if(!numero.getAttribute("class").contains("slds-is-sorted--desc")) {
			numero.click();
			sleep(500);
		}
		
		Assert.assertTrue(numero.getAttribute("class").contains("slds-is-sorted--desc"));
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69202_360_View_360_View_Ordenamiento_default_Vista_360_Gestiones_Visualizar_Columna_ordenada_por_default() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		WebElement flechaOrdenamiento = driver.findElement(By.cssSelector(".slds-p-bottom--small.slds-is-sorted svg"));
		
		Assert.assertTrue(flechaOrdenamiento.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69254_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Paginado() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		WebElement botonesPaginado = driver.findElement(By.cssSelector(".slds-form-element.slds-button-group.pull-right"));
		
		Assert.assertTrue(botonesPaginado.isDisplayed());
	}
	
	@Test(groups= {"CustomerCare", "Vista360Layout"})
	public void TS69255_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Paginado_Parametrizacion() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestiones();
		
		WebElement filasPorPaginas = driver.findElement(By.cssSelector(".slds-select"));
		
		Assert.assertTrue(filasPorPaginas.isDisplayed());
	}
	
	
	@Test
	public void Adrian_Test_Gestion_Exitosa() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("inconvenientes");
		sleep(8000);
		driver.findElement(By.id("CboConcepto")).click();
		driver.findElement(By.xpath("//*[text() = 'CREDITO PREPAGO']")).click();
		driver.findElement(By.id("CboItem")).click();
		driver.findElement(By.xpath("//*[text() = 'Consumos de datos']")).click();
		driver.findElement(By.id("CboMotivo")).click();
		driver.findElement(By.xpath("//*[text() = 'Beneficio vencido']")).click();
		List<WebElement> siClass = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e : siClass) {
			if(e.getText().toLowerCase().equals("si")) {
				e.click();
				break;
			}
		}
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step-AssetSelection_nextBtn")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-VerifyPreviousAdjustments_prevBtn")));
		List<WebElement> ajusteClass = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e: ajusteClass) {
			if(e.getText().toLowerCase().equals("si, ajustar")){
				e.click();
				break;
			}
		}
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();;
		sleep(5000);
		List<WebElement> calcularAjusteClass = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e: calcularAjusteClass) {
			if(e.getText().toLowerCase().equals("si")) {
				e.click();
				break;
			}
		}
		driver.findElement(By.id("Desde")).sendKeys("01-05-2018");
		driver.findElement(By.id("Hasta")).sendKeys("02-05-2018");
		driver.findElement(By.id("Unidad")).click();
		driver.findElement(By.xpath("//*[text() = 'Datos (Mb)']")).click();
		driver.findElement(By.id("CantidadDatosms")).sendKeys("100");
		driver.findElement(By.id("Step-AjusteNivelLinea_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Step-Summary_nextBtn")).click();
		sleep(5000);
		List<WebElement> confirmacionClass = driver.findElements(By.className("slds-form-element__control"));
		boolean a = false;
		for(WebElement e: confirmacionClass) {
			if(e.getText().toLowerCase().contains("tu gestión se realizó con éxito")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test
	public void Adrian_Test_Suspensiones() {
		Customer.elegirCuenta("aaaaFernando Care");
		Customer.irAGestion("suspensiones");
		sleep(3000);
		List<WebElement> suspensionOrHabilitacion = driver.findElements(By.cssSelector(ajustarClase("slds-form-element__label ng-binding ng-scope")));
		clickOnObjectByText(suspensionOrHabilitacion, "suspensi", "parcial");
		/*
		for(WebElement e: suspensionOrHabilitacion) {
			if(e.getText().toLowerCase().contains("suspensi")) {
				e.click();
				break;
			}
		}
		*/
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		sleep(5000);
		List<WebElement> lineaOrEquipo = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e: lineaOrEquipo) {
			if(e.getText().toLowerCase().equals("linea + equipo")) {
				e.click();
				break;
			}
		}
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		sleep(3000);
		List<WebElement> linea = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
		for(WebElement e: linea) {
			if(e.getText().toLowerCase().equals("l\u00EDnea: undefined")){
				e.click();
				break;
			}
		}
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		sleep(10000);
		List<WebElement> equipo = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e: equipo) {
			if(e.getText().toLowerCase().equals("imei")){
				e.click();
				break;
			}
		}
		driver.findElement(By.id("ManualIMEI")).sendKeys("360012345678123");
		driver.findElement(By.id("Step3.5A-DeviceForLine_nextBtn")).click();
		sleep(3000);
		List<WebElement> razon = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e: razon) {
			if(e.getText().toLowerCase().equals("robo")){
				e.click();
				break;
			}
		}
		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
		sleep(3000);
		List<WebElement> descSiniestro = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for(WebElement e: descSiniestro) {
			if(e.getText().toLowerCase().equals("argentina")){
				e.click();
				break;
			}
		}
		driver.findElement(By.id("State")).click();
		driver.findElement(By.xpath("//*[text() = 'Catamarca']")).click();
		driver.findElement(By.id("Partido")).sendKeys("Catamarca");
		driver.findElement(By.id("CityTypeAhead")).sendKeys("SAN FDO DE V DE CATAMARCA");
		for(WebElement e: descSiniestro) {
			if(e.getText().toLowerCase().equals("si")){
				e.click();
				break;
			}
		}
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(3000);
		List<WebElement> nullObject61 = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		for(WebElement e: nullObject61) {
			if(e.getText().toLowerCase().equals("continue")){
				e.click();
				break;
			}
		}
		sleep(3000);
		List<WebElement> confirmacionClass = driver.findElements(By.className("ta-care-omniscript-done"));
		boolean a = false;
		for(WebElement e: confirmacionClass) {
			if(e.getText().toLowerCase().contains("tu gestión se realizó con éxito")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
		sleep(15000);
		driver.quit();
	}
	
	
	
}
