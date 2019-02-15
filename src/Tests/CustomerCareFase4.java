package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;

public class CustomerCareFase4 extends TestBase{

	private CustomerCare cc;

	
	@BeforeClass (groups = {"CustomerCare", "Vista360Layout", "DetalleDeConsumos", "ActualizarDatos", "DebitoAutomatico"})
	public void init() {
		inicializarDriver();
		cc = new CustomerCare(driver);
		login();
		IrA.CajonDeAplicaciones.ConsolaFAN();
	}
	
	@AfterClass (groups = {"CustomerCare", "Vista360Layout", "DetalleDeConsumos", "ActualizarDatos", "DebitoAutomatico"})
	public void quit() {
		cc.cerrarTodasLasPestanas();
		IrA.CajonDeAplicaciones.Ventas();
		cerrarTodo();
	}
	
	@BeforeMethod (groups = {"CustomerCare", "Vista360Layout", "DetalleDeConsumos", "ActualizarDatos", "DebitoAutomatico"})
	public void after() {
		cc.cerrarTodasLasPestanas();
	}
	
	//@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS15955_360_View_Ver_Equipo_Creador_en_Case_Caso_Creado_Cerrar_Caso_Campo_Equipo_del_Creador_no_cambia_valor() {
		WebElement selector = driver.findElement(By.cssSelector(".x-btn-small.x-btn-icon-small-left"));
		WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(btnSplit, 245, 20).click().build().perform();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.className("piped")));
		driver.findElement(By.name("newCase")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("bottomButtonRow")));
		List <WebElement> dc = driver.findElements(By.name("save"));
		for (WebElement x : dc) {
			if (x.getAttribute("value").contains("¿Desea continuar?")) {
				x.click();
				break;
			}
		}
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.xpath("//*[@id=\"cas3\"]")));
		driver.findElement(By.xpath("//*[@id=\"cas3\"]")).sendKeys("Fernandoasd Careeeeee");
		driver.findElement(By.xpath("//*[@id=\"cas7\"]")).click();
		driver.findElement(By.xpath("//*[text() = 'Nuevo']")).click();
		driver.switchTo().frame(cambioFrame(driver, By.id("topButtonRow")));
		List <WebElement> save = driver.findElements(By.name("save"));
		for (WebElement x : save) {
			if (x.getAttribute("value").contains("Guardar")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		WebElement cc = driver.findElement(By.cssSelector(".preamblecontainer.displayblock"));
		Assert.assertTrue(cc.getText().toLowerCase().contains("creó este caso"));
		List <WebElement> ec = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite"));
		String equipoCreador = ec.get(11).getText();
		List <WebElement> elim = driver.findElements(By.name("close"));
		for (WebElement x : elim) {
			if (x.getAttribute("value").contains("Cerrar caso")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.findElement(By.xpath("//*[@id=\"cas7\"]")).click();
		driver.findElement(By.xpath("//*[text() = 'Cerrado']")).click();
		driver.findElement(By.xpath("//*[@id=\"cas6\"]")).click();
		sleep(2000);
		driver.findElement(By.xpath("//*[text() = 'Complex functionality']")).click();
		List <WebElement> cerrar = driver.findElements(By.className("btn"));
		for (WebElement x : cerrar) {
			if (x.getAttribute("value").contains("Guardar")) {
				x.click();
				break;
			}
		}
		List <WebElement> casoCerrado = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite"));
		Assert.assertTrue(casoCerrado.get(11).getText().equals(equipoCreador));
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS37166_360_View_UX_360_Card_Historiales_Visualizar_HISTORIAL_DE_AJUSTES() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAHistoriales();
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-p-around--large.slds-text-body--regular.labelFont"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("historial de ajustes")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS37230_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_con_Mora() {
		cc.elegirCuenta("aaaaCuenta ConMora");
		cc.irAGestion("débito auto");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		sleep(5000);
		driver.findElement(By.className("slds-checkbox--faux")).click();
		sleep(7000);
		WebElement error = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		Assert.assertTrue(error.getText().toLowerCase().contains("la cuenta de facturación aaaacuenta conmora está suspendida por mora"));
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS37469_360_View_Vista_360_de_facturación_clientes_individuos_Persistencia_Visualizar_Convenios_de_Pago() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".icon.icon-v-modify-contract")));
		List <WebElement> element = driver.findElements(By.className("slds-text-body_regular"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Convenios de pago")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69033_360_View_360_View_Historiales_Formulario_Historiales_Ver_detalle_Historial_de_Recargas_Verificar_nombre_Historico() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAHistoriales();		
		List <WebElement> hist = driver.findElements(By.cssSelector(".slds-p-around--large.slds-text-body--regular.labelFont"));
		for (WebElement x : hist) {
			if (x.getText().toLowerCase().contains("historial de recargas")) {
				x.click();
				break;
			}
		}
		WebElement element = cc.obtenerPestanaActiva();
		Assert.assertTrue(element.getText().equals("Historiales"));
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69034_360_View_360_View_Historiales_Formulario_Historiales_Ver_detalle_Historial_de_Packs_Verificar_nombre_Historico() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAHistoriales();		
		List <WebElement> hist = driver.findElements(By.cssSelector(".slds-p-around--large.slds-text-body--regular.labelFont"));
		for (WebElement x : hist) {
			if (x.getText().toLowerCase().contains("historial de packs")) {
				x.click();
				break;
			}
		}
		WebElement element = cc.obtenerPestanaActiva();
		Assert.assertTrue(element.getText().equals("Historiales"));
	}
	
	@Test (groups = {"CustomerCare", "ActualizarDatos"})
	public void TS69038_Profile_Changes_Perfil_del_cliente_Modificacion_DNI_CUIL_Verificar_Imposibilidad_de_modificar_DNI_y_CUIL_al_mismo_tiempo(){
		cc.elegirCuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-edit")));
		List <WebElement> act = driver.findElements(By.className("profile-edit"));
		act.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("LastName")));
		WebElement dni = driver.findElement(By.id("DocumentType"));
		WebElement cuil = driver.findElement(By.id("Cuil"));
		Assert.assertTrue(dni.getAttribute("disabled").equals("true"));
		Assert.assertTrue(cuil.getAttribute("required").equals("true"));
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69098_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_encabezado() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		List <WebElement> title = driver.findElements(By.className("big_title"));
		boolean a = false;
		for (WebElement x : title) {
			if (x.getText().equals("Detalle de Consumos")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69099_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_Servicio() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Servicio")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69100_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_menu_desplegable_del_filtro_Servicio() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		List <WebElement> filtro = driver.findElements(By.id("text-input-01"));
		Assert.assertTrue(filtro.get(0).getAttribute("ng-model").contains("ptc.filterServiceOption"));
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69101_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_Periodo() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false, b = false;
		for (WebElement x : element) {
			if (x.getText().equals("Periodo")) {
				a = true;
			}
		}
		List <WebElement> cons = driver.findElements(By.id("text-input-02"));
		for (WebElement x : cons) {
			if (x.getAttribute("placeholder").equals("Los \u00faltimos 15 d\u00edas")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69102_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_menu_desplegable_del_filtro_Periodo() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		List <WebElement> filtro = driver.findElements(By.id("text-input-02"));
		filtro.get(0).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 3 d\u00edas']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Los \u00faltimos 15 d\u00edas']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69104_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_calendario_en_filtro_Inicio() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		driver.findElement(By.id("text-input-id-1")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69106_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_calendario_en_filtro_Fin() {
		TestBase TB = new TestBase();
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		TB.waitFor(driver, By.id("text-input-id-2"));
		driver.findElement(By.id("text-input-id-2")).click();
		TB.waitFor(driver, By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69107_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_boton_Consultar() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69108_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_desplegable_Filtros_Avanzados() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Filtros avanzados")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69110_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Numero_de_origen_o_destino() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false, b = false;
		for (WebElement x : element) {
			if (x.getText().equals("N\u00famero de origen o destino")) {
				a = true;
			}
		}
		List <WebElement> search = driver.findElements(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty"));
		if (search.get(1).getAttribute("placeholder").equals("Buscar")) {
			b = true;			
		}		
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69111_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Tipo_de_consumo() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false, b = false;
		for (WebElement x : element) {
			if (x.getText().equals("Tipo de consumo")) {
				a = true;
			}
		}
		List <WebElement> cons = driver.findElements(By.id("text-input-02"));
		for (WebElement x : cons) {
			if (x.getAttribute("placeholder").equals("Todos los consumos")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69112_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Con_o_sin_cargo() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		sleep(3000);
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false, b = false;
		for (WebElement x : element) {
			if (x.getText().equals("Con o sin cargo")) {
				a = true;
			}
		}
		List <WebElement> checkbox = driver.findElements(By.id("text-input-01"));
		for (WebElement x : checkbox) {
			if (x.getAttribute("placeholder").equals("Con y sin cargo")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69113_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Producto() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Producto")) {
				a = true;
			}
		}		
		Assert.assertTrue(a);
		Assert.assertTrue(driver.findElement(By.id("text-input-03")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69138_Consumption_Details_Detalle_de_Consumo_Filtro_x_Producto_Visualizar_filtro_Producto_desplegado() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Número de origen o destino")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69149_Consumption_Details_Criterios_de_Filtro_Temporal_Visualizar_filtro_Fin() {
		cc.elegirCuenta("aaaaFernando Care");
		cc.irAGestion("detalle de consu");
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();		
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Fin")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69164_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Visualizar_todas_las_ordenes() {
		cc.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		waitFor(driver, By.cssSelector(".icon.icon-v-troubleshoot-line"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".icon.icon-v-troubleshoot-line"));
		gest.get(1).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		Assert.assertTrue(driver.findElement(By.className("slds-m-around--medium")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69165_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Visualizar_todos_los_casos() {
		cc.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		waitFor(driver, By.cssSelector(".icon.icon-v-troubleshoot-line"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".icon.icon-v-troubleshoot-line"));
		gest.get(1).click();
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(3000);
		WebElement tipo = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[3]/div[1]/div/table/tbody/tr[1]/td[2]"));
		boolean a = false;
		if (tipo.getText().contains("Case")) {
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69168_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Fitlro_Fecha_Verificar_Calendario() {
		cc.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		waitFor(driver, By.cssSelector(".icon.icon-v-troubleshoot-line"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".icon.icon-v-troubleshoot-line"));
		gest.get(1).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("text-input-id-2")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69251_360_View_Validacion_de_nominacion_en_Vista_360_Asset_no_nominado_Visualiza_todas_sus_acciones_disponibles() {
		cc.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		List <WebElement> gestiones = driver.findElements(By.className("slds-text-body_regular"));
		boolean a = false, b = false, c = false, d = false;
		for (WebElement x : gestiones) {
			if (x.getText().equals("Detalle de Consumos")) {
				a = true;
			}
			if (x.getText().equals("Historiales")) {
				b = true;
			}
			if (x.getText().equals("Ahorrá")) {
				c = true;
			}
			if (x.getText().equals("Mis servicios")) {
				d = true;
			}
		}
		Assert.assertTrue(a && b && c && d);
		Assert.assertTrue(driver.findElement(By.cssSelector(".console-flyout.active.flyout")).isDisplayed());
	}
}