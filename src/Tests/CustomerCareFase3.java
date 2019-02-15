package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.setConexion;

public class CustomerCareFase3 extends TestBase{
	
	private CustomerCare cc;
	private Accounts ac;
	
	
	@BeforeClass (groups = {"CustomerCare", "DebitoAutomatico", "Vista360Layout", "DetalleDeConsumos"})
	 public void init() throws Exception {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		ac = new Accounts(driver);
		login(driver);
		sleep(5000);
		String a = driver.findElement(By.id("tsidLabel")).getText();
		driver.findElement(By.id("tsidLabel")).click();
		if(a.equals("Ventas")) {
			driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		}else {
			driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02u41000000QWha\']")).click();
			sleep(4000);
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		}
	 }
	
	@BeforeMethod (groups = {"CustomerCare", "DebitoAutomatico", "Vista360Layout", "DetalleDeConsumos"})
	public void setup(){
		try {driver.switchTo().alert().accept();} catch (org.openqa.selenium.NoAlertPresentException e) {}
		driver.switchTo().defaultContent();
		cc.cerrarultimapestana();
	}
	
	@AfterClass (groups = {"CustomerCare", "DebitoAutomatico", "Vista360Layout", "DetalleDeConsumos"})
	public void tearDown() {
		driver.quit();
		sleep(5000);
	}
	
	
	//@Test (groups = "CustomerCare")  BUG
	public void TS38096_Credit_Card_Payments_Desvincular_TC_registrada_Visualizar_opción_Desvincular() {
		
	}
		
	//@Test (groups = "CustomerCare")  BUG
	public void TS38101_Credit_Card_Payments_Desvincular_TC_registrada_Iniciar_pago_desde_asset() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("facturación");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".icon.icon-v-campaign")));
		WebElement element = driver.findElement(By.className("card-info")).findElement(By.cssSelector(".icon.icon-v-campaign"));
		element.click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.id("preloadedAmount")));
		Assert.assertTrue(driver.findElement(By.id("preloadedAmount")).isDisplayed());
		
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38195_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_0_Cliente_Activo_Billing_account_activa_con_servicios_activos() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		WebElement element = driver.findElement(By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding"));
		Assert.assertTrue(element.getText().toLowerCase().contains("¿qué gestión te gustaría hacer?"));
	}
		
	//@Test (groups = "CustomerCare")		BUG
	public void TS38196_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_0_Cliente_Activo_Billing_account_activa_sin_servicios_activos() {
		cc.elegircuenta("aaaaAndres Care");
		cc.SelectGestion("débito");
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope"));
		Assert.assertTrue(element.getText().toLowerCase().contains("no es posible realizar la gestión porque no tiene cuentas/servicios activos."));
	}
		
	//@Test (groups = "CustomerCare")		BUG
	public void TS38197_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_0_Cliente_Activo_Billing_account_inactiva() {
		cc.elegircuenta("aaaaAndres Care");
		Accounts x = new Accounts(driver);
		x.findAndClickButton("débito");
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope"));
		Assert.assertTrue(element.getText().toLowerCase().contains("no es posible realizar la gestión porque no tiene cuentas/servicios activos."));
	}
	
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38199_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_1_Visualizar_botones() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito");
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".captionOut.slds-form-element__label.ng-binding")));
		List <WebElement> botones = driver.findElements(By.cssSelector(".captionOut.slds-form-element__label.ng-binding"));
		Assert.assertTrue(botones.get(0).getText().contains("Adhesión"));
		Assert.assertTrue(botones.get(1).getText().contains("Actualizar datos"));
		Assert.assertTrue(botones.get(2).getText().contains("Stop debit"));
		Assert.assertTrue(botones.get(3).getText().contains("Baja"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38407_360_View_Vista_360_de_facturación_clientes_individuos_Billing_Account_Activa() {
		CustomerCare cc = new CustomerCare(driver);
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("Facturación");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-text-body_regular.account-number")));
		WebElement tarjetaCuenta = driver.findElement(By.cssSelector(".slds-text-body_regular.account-number"));
		Assert.assertTrue(tarjetaCuenta.isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38423_360_View_Buscador_para_vistas_de_facturación_Buscar_por_Número_de_cuenta() {
		cc.elegircuenta("aaaaFernando Care");
		cc.irAFacturacion();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		String a = "001c000001";
		driver.findElement(By.cssSelector(".form-control.services-input-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(a);
		sleep(2000);
		WebElement cuenta = driver.findElement(By.cssSelector(".slds-text-body_regular.account-number"));
		Assert.assertTrue(cuenta.getText().toLowerCase().contains(a));
	}	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38424_360_View_Buscador_para_vistas_de_facturación_Buscar_por_Nombre_de_cuenta() {
		cc.elegircuenta("aaaaFernando Care");
		cc.irAFacturacion();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		String a = "calle de billing 1";
		driver.findElement(By.cssSelector(".form-control.services-input-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(a);
		sleep(2000);
		WebElement cuenta = driver.findElement(By.className("slds-text-heading_medium"));
		Assert.assertTrue(cuenta.getText().toLowerCase().contains(a));		
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38425_360_View_Buscador_para_vistas_de_facturación_Buscar_por_Ciclo_de_facturación() {
		cc.elegircuenta("aaaaFernando Care");
		ac.closeAccountServiceTabByName("Servicios");
		ac.findAndClickButton("facturación");
		sleep(20000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		String a = "14";
		driver.findElement(By.cssSelector(".form-control.services-input-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(a);
		sleep(2000);
		List <WebElement> ciclo = driver.findElements(By.cssSelector(".slds-text-body_regular.value.paddingTop"));
		Assert.assertTrue(ciclo.get(2).getText().contains(a));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38428_360_View_Buscador_para_vistas_de_facturación_Buscar_por_Medio_de_pago() {
		cc.elegircuenta("aaaaFernando Care");
		ac.closeAccountServiceTabByName("Servicios");
		cc.irAFacturacion();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".console-card.active")));
		String a = "factura física";
		driver.findElement(By.cssSelector(".form-control.services-input-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(a);
		sleep(2000);
		List <WebElement> ciclo = driver.findElements(By.cssSelector(".slds-text-body_regular.value.paddingTop"));
		Assert.assertTrue(ciclo.get(4).getText().toLowerCase().contains(a));
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38431_360_View_Buscador_para_vistas_de_facturación_Botón_refrescar() {
		cc.elegircuenta("aaaaFernando Care");
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".btn.btn-primary"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("refrescar")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38183_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Campo_cancelacion_sin_cargo() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(2000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("cancelación sin cargo")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38184_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Campo_Fecha_de_pago() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small")).click();
		sleep(2000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("fecha de pago")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38175_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Desde_antigüedad_6_meses() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		WebElement element = driver.findElement(By.id("text-input-id-1"));
		String a = element.getText();
		driver.findElement(By.id("text-input-id-1")).click();
		cc.setSimpleDropdown(driver.findElement(By.id("select-01")), "2016");
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[4]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getText().equals(a));		
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38176_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Desde_calendario_grisado() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("text-input-id-1")));
		WebElement element = driver.findElement(By.id("text-input-id-1"));
		String a = element.getText();
		driver.findElement(By.id("text-input-id-1")).click();
		cc.setSimpleDropdown(driver.findElement(By.id("select-01")), "2018");
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[4]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getText().equals(a));		
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38177_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Desde_Consulta_exitosa() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[4]/span")).click();
		sleep(2000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);	
		WebElement element = driver.findElement(By.cssSelector(".slds-text-align--left.slds-text-color--error"));
		Assert.assertTrue(element.getText().toLowerCase().contains("debe ingresar una fecha de fin"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38182_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Desde_y_Hasta_consulta_exitosa() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.xpath("//*[@id=\"week-0\"]/td[7]/span")).click();
		sleep(2000);
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"week-0\"]/td[6]/span")).click();
		sleep(2000);
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38181_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Desde_y_Hasta_no_superan_los_90_dias() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement element = driver.findElement(By.xpath("//*[@id=\"divExterno\"]/label/div/div[2]/div/div[1]/div[1]/button"));
		for (int i=0; i<3; i++) {
			element.click();
		}
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[4]/span")).click();
		sleep(2000);
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/div[2]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[4]/span")).click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.id("text-input-id-2")).getText().isEmpty());
	}
			
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38178_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Hasta_no_mayor_a_fecha_actual() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("text-input-id-1")));
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[4]/span")).click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.id("text-input-id-2")).getText().isEmpty());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38180_360_View_Historial_de_Recargas_Pre_pago_Recarga_SOS_Fecha_Hasta_consulta_exitosa() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("historial de recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector("text-input-id-1")));
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[3]/span")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-m-top--x-large")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38450_360_View_Historial_de_Recargas_Pre_pago_Grilla_Colapsar_registro() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		driver.findElement(By.cssSelector(".slds-truncate.slds-p-vertical--medium")).click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.className("nghided")).isDisplayed());		
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38448_360_View_Historial_de_Recargas_Pre_pago_Grilla_Columna_Beneficios() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active.expired")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		for (WebElement x : a) {
			if (x.getText().toLowerCase().contains("beneficios")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38442_360_View_Historial_de_Recargas_Pre_pago_Grilla_Columna_Canal() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		for (WebElement x : a) {
			if (x.getText().toLowerCase().contains("canal")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38443_360_View_Historial_de_Recargas_Pre_pago_Grilla_Columna_Descripción() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		for (WebElement x : a) {
			if (x.getText().toLowerCase().contains("descripción")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38444_360_View_Historial_de_Recargas_Pre_pago_Grilla_Columna_Fecha_y_Hora() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		for (WebElement x : a) {
			if (x.getText().toLowerCase().contains("fecha y hora")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38446_360_View_Historial_de_Recargas_Pre_pago_Grilla_Column_Monto() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		for (WebElement x : a) {
			if (x.getText().toLowerCase().contains("monto")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38447_360_View_Historial_de_Recargas_Pre_pago_Grilla_Columna_Vencimiento() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		for (WebElement x : a) {
			if (x.getText().toLowerCase().contains("vencimiento")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38449_360_View_Historial_de_Recargas_Pre_pago_Grilla_Expandir_registro() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		driver.findElement(By.cssSelector(".slds-truncate.slds-p-vertical--medium")).click();
		driver.findElement(By.cssSelector(".slds-truncate.slds-p-vertical--medium")).click();
		sleep(2000);
		try {
			Assert.assertFalse(driver.findElement(By.className("nghided")).isDisplayed());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.assertTrue(true);
		}
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38409_360_View_Vista_360_de_facturación_clientes_individuos_Header_Cards_Visualizar_campos() {
		cc.elegircuenta("aaaaFernando Care");
		ac.closeAccountServiceTabByName("Servicios");
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> element = driver.findElements(By.className("card-top"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("cuenta"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("ciclo de facturación"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38500_360_View_Historial_de_Recargas_Pre_pago_Filtros_Botón_CONSULTAR() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(0).isDisplayed());		
	}	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38496_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Canal_IVR() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'IVR']")).click();
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(1).getText().equals("IVR"));		
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38493_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Canal_Rol() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'ROL']")).click();
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(1).getText().equals("ROL"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38495_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Canal_SMS() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'SMS']")).click();
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(1).getText().equals("SMS"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38497_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Canal_TODOS() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Todos']")).click();
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38494_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Canal_WEB() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-03")));
		driver.findElement(By.id("text-input-03")).click();
		driver.findElement(By.xpath("//*[text() = 'Web']")).click();
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(1).getText().equals("Web"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38498_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Con_beneficios() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-04")));
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Con Beneficio']")).click();
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(5).getText().equals("Con Beneficio"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38499_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_SIN_beneficios() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-04")));
		driver.findElement(By.id("text-input-04")).click();
		driver.findElement(By.xpath("//*[text() = 'Sin Beneficio']")).click();
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		Assert.assertTrue(x.get(5).getText().equals("Sin Beneficio"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38481_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("text-input-id-2")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38489_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Fecha_Hasta_y_Fecha_desde_dentro_de_90_días() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.xpath("//*[@id=\"divExterno\"]/label/div/div[2]/div/div[1]/div[1]/button")).click();;	
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[4]/span")).click();
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/div[2]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[4]/span")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small")).isDisplayed());		
	}	
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38490_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Fecha_Hasta_y_Fecha_desde_fuera_de_90_días() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		WebElement x = driver.findElement(By.id("text-input-id-2"));
		String a = x.getAttribute("value");
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement b = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/div[2]/div/label/div/div[2]/div/div[1]/div[2]/button"));
		for (int i=0; i<3; i++) {
			b.click();
		}
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[3]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-2")).getAttribute("value").equals(a));
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38485_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Fecha_igual_a_fecha_actual() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/div[2]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[3]/span")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-size--1-of-1.slds-medium-size--1-of-1.slds-large-size--1-of-1.slds-p-bottom--small")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38486_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Fecha_mayor_a_fecha_actual() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		WebElement b = driver.findElement(By.id("text-input-id-1"));
		String a = b.getAttribute("value");
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement x = driver.findElement(By.xpath("//*[@id=\"divExterno\"]/label/div/div[2]/div/div[1]/div[2]/button"));
		for (int i=0; i<3; i++) {
			x.click();
		}
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[4]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getAttribute("value").equals(a));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38484_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Fecha_menor_a_fecha_actual() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		WebElement b = driver.findElement(By.id("text-input-id-2"));
		String a = b.getAttribute("value");
		driver.findElement(By.id("text-input-id-2")).click();
		WebElement x = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div/div[2]/div[2]/div/label/div/div[2]/div/div[1]/div[1]/button"));
		for (int i=0; i<4; i++) {
			x.click();
		}
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[3]/span")).click();
		Assert.assertTrue(!(driver.findElement(By.id("text-input-id-2")).getAttribute("value").equals(a)));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38482_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Seleccionar_Fecha_desde_supera_los_6_meses_de_antiguedad() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		WebElement b = driver.findElement(By.id("text-input-id-1"));
		String a = b.getAttribute("value");
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement x = driver.findElement(By.xpath("//*[@id=\"divExterno\"]/label/div/div[2]/div/div[1]/div[1]/button"));
		for (int i=0; i<6; i++) {
			x.click();
		}
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[4]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getAttribute("value").equals(a));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38483_360_View_Historial_de_Recargas_Pre_pago_Filtros_Filtro_Fecha_Visualizar_grisado_Fecha_desde_supera_los_6_meses_de_antiguedad() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		WebElement b = driver.findElement(By.id("text-input-id-1"));
		String a = b.getAttribute("value");
		driver.findElement(By.id("text-input-id-1")).click();
		WebElement x = driver.findElement(By.xpath("//*[@id=\"divExterno\"]/label/div/div[2]/div/div[1]/div[1]/button"));
		for (int i=0; i<6; i++) {
			x.click();
		}
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[4]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getAttribute("value").equals(a));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38515_360_View_Historial_de_Recargas_Pre_pago_Grilla_Expandir_registro_Asset_Prepago_Expandir_nuevo_registro_El_anterior_queda_abierto() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		x.get(0).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-truncate.slds-p-around--small")).isDisplayed());
		x.get(7).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-truncate.slds-p-around--small")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38514_360_View_Historial_de_Recargas_Pre_pago_Grilla_Expandir_registro_Asset_Prepago_Beneficios_de_Unidades_Visualizar_datos() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		a.get(5).click();
		a.get(5).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		x.get(5).click();
		Assert.assertTrue(driver.findElement(By.className("slds-box--small")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38513_360_View_Historial_de_Recargas_Pre_pago_Grilla_Expandir_registro_Asset_Prepago_Beneficios_de_Credito_Visualizar_datos() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		a.get(5).click();
		a.get(5).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		x.get(5).click();
		Assert.assertTrue(driver.findElement(By.className("slds-box--small")).getText().toLowerCase().contains("beneficios de crédito"));
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38516_360_View_Historial_de_Recargas_Pre_pago_Grilla_Expandir_registro_Asset_Prepago_Expande_registro_que_No_tiene_beneficios() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		a.get(5).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		x.get(5).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-truncate.slds-p-around--small")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38512_360_View_Historial_de_Recargas_Pre_pago_Grilla_Expandir_registro_Asset_Prepago_Expandir_registro() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".console-card.active")));
		List <WebElement> historiales = driver.findElements(By.className("slds-text-body_regular"));
		for (WebElement x : historiales) {
			if (x.getText().toLowerCase().contains("historiales")) {
				x.click();
				break;
			}
		}
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--brand"));
		element.get(0).click();
		sleep(10000);
		ac.closeAccountServiceTabByName("Historiales");
		ac.closeAccountServiceTabByName("Servicios");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-id-1")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-truncate.slds-p-vertical--medium")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-truncate.slds-th__action"));
		a.get(5).click();
		a.get(5).click();
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium"));
		x.get(5).click();
		Assert.assertTrue(driver.findElement(By.className("slds-box--small")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38408_360_View_Vista_360_de_facturación_clientes_individuos_Billing_Account_Inactiva() {
		cc.elegircuenta("aaaaFernando Care Billing 2");
		cc.irAFacturacion();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".btn.btn-primary")));
		driver.findElement(By.cssSelector(".icon.icon-v-campaign")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope")).getText().toLowerCase().contains("en este momento no se puede efectuar este tipo de gestión porque su cuenta se encuentra inactiva"));
	}
		
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS38067_360_View_360_View_Acciones_Vlocity_Limitante_Verifica_que_en_el_buscador_figuran_todas_las_acciones_a_pesar_de_que_no_figuren_por_defecto() {
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.className("actions-content")));
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("facturación");
		sleep(5000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("facturación"));
		sleep(3000);
		cc.cerrarultimapestana();
		cc.elegircuenta("aaaaFernando Care");
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.className("actions-content")));
		driver.findElement(By.className("actions-content")).findElement(By.tagName("input")).sendKeys("cambio de");
		sleep(5000);
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		Assert.assertTrue(a.get(0).getText().toLowerCase().contains("cambio de ciclo"));	
	}
		
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38069_Consumption_Details_Definicion_de_Filtros_sobre_Calendario_Fecha_Hasta_No_se_puede_ingresar_una_fecha_posterior_a_día_de_consulta() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[1]/ng-include/div/div[2]/div[5]/div/label/div/div[2]/div/div[1]/div[2]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-3\"]/td[4]/span")).click();
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).getAttribute("value").isEmpty());
	}
		
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38071_Consumption_Details_Definicion_de_Filtros_sobre_Calendario_Fecha_Hasta_dentro_de_rango_de_15_días_respecto_a_Fecha_Desde() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		sleep(3000);
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[1]/ng-include/div/div[2]/div[5]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[3]/span")).click();
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[1]/ng-include/div/div[2]/div[6]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-2\"]/td[3]/span")).click();
		Assert.assertTrue(!(driver.findElement(By.id("text-input-id-2")).getAttribute("value").isEmpty()));
	}
	
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38070_Consumption_Details_Definicion_de_Filtros_sobre_Calendario_Ingresar_Fecha_Hasta_posterior_a_Fecha_Desde_con_más_de_15_días_de_diferencia() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		sleep(3000);
		driver.findElement(By.id("text-input-id-1")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[1]/ng-include/div/div[2]/div[5]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-1\"]/td[3]/span")).click();
		driver.findElement(By.id("text-input-id-2")).click();
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id5\"]/div/div/ng-include/div/div[1]/ng-include/div/div[2]/div[6]/div/label/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"week-4\"]/td[6]/span")).click();
		Assert.assertTrue((driver.findElement(By.id("text-input-id-2")).getAttribute("value").isEmpty()));
	}
		
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38339_Consumption_Details_Detalle_consumos_Pre_pago_apertura_de_detalle_Consumo_Prepago_Ver_detalle() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--right.slds-picklist__input")).findElement(By.id("text-input-01")).click();
		driver.findElement(By.xpath("//*[text() = 'Prueba - 123456789']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-card.slds-p-around--large.np-card")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38345_Consumption_Details_Styling_detalle_de_consumos_Mostrado_de_campos_Prepago_Visualizar_columnas() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--right.slds-picklist__input")).findElement(By.id("text-input-01")).click();
		driver.findElement(By.xpath("//*[text() = 'Prueba - 123456789']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(3000);
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--label"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("fecha inicio"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("tipo"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("costo"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("origen/destino"));
		Assert.assertTrue(element.get(0).getText().toLowerCase().contains("credito disponible"));
	}
		
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS38340_Consumption_Details_Detalle_consumos_Pre_pago_apertura_de_detalle_Consumo_Prepago_Visualizar_campos() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--brand")));
		driver.findElement(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--right.slds-picklist__input")).findElement(By.id("text-input-01")).click();
		driver.findElement(By.xpath("//*[text() = 'Prueba - 123456789']")).click();
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		sleep(3000);
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium.card-table-row-icon"));
		for (int i=0; i<6; i++) {
			Assert.assertTrue(element.get(i).isDisplayed());
		}
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38193_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_0_Cuenta_con_Fraude() {
		cc.elegircuenta("aaaaRaul Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(element.getText().toLowerCase().contains("no puede continuar con esta operación"));
	}
	
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38194_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_0_Cuenta_sin_Fraude() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38200_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_1_Funcionamiento_botones() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).isDisplayed());
		driver.findElement(By.id("AvailableBillingAccounts_prevBtn")).click();
		sleep(5000);
		element.get(1).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).isDisplayed());
		driver.findElement(By.id("AvailableBillingAccounts_prevBtn")).click();
		sleep(5000);
		element.get(2).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).isDisplayed());
		driver.findElement(By.id("AvailableBillingAccounts_prevBtn")).click();
		sleep(5000);
		element.get(3).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).isDisplayed());
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38433_360_View_360_Card_Servicio_Prepago_Flyout_Acciones_Productos_Activos() {
		cc.elegircuenta("aaaaFernando Care");		
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
	    driver.findElement(By.className("card-top")).click();
	    sleep(5000);
	    WebElement element = driver.findElement(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions"));
	    Assert.assertTrue(element.getText().toLowerCase().contains("productos activos"));	    
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS38437_360_View_360_Card_Servicio_Prepago_FlyoutAccionesSuscripciones() {
		cc.elegircuenta("aaaaFernando Care");		
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
	    driver.findElement(By.className("card-top")).click();
	    sleep(5000);
	    WebElement element = driver.findElement(By.cssSelector(".slds-small-size--3-of-12.slds-medium-size--3-of-12.slds-large-size--3-of-12.flyout-actions"));
	    Assert.assertTrue(element.getText().toLowerCase().contains("suscripciones"));
	}	
	
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38223_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Seleccion_multiple_de_BA() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		List <WebElement> x = driver.findElements(By.className("slds-checkbox--faux"));
		x.get(0).click();
		x.get(1).click();
		sleep(3000);
		try {
			Assert.assertFalse(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).isDisplayed());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.assertTrue(true);
		}
	}	
	
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38219_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Seleccion_simple_de_BA() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		List <WebElement> x = driver.findElements(By.className("slds-checkbox--faux"));
		x.get(0).click();
		sleep(3000);
		try {
			Assert.assertFalse(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).isDisplayed());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.assertTrue(true);
		}
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38208_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Baja_Cuenta_NO_adherida_a_Aut_Deb_Que_NO_se_vea() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(3).click();
		sleep(7000);
		WebElement x = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-message.ng-scope.ng-invalid.ng-invalid-vlc-val-error.ng-dirty"));
		Assert.assertTrue(x.getText().toLowerCase().contains("no posee cuentas de facturación con débito automático"));
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38206_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Modificación_Cuenta_NO_adherida_a_Aut_Deb_Que_NO_se_vea() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(1).click();
		sleep(7000);
		WebElement x = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-message.ng-scope.ng-invalid.ng-invalid-vlc-val-error.ng-dirty"));
		Assert.assertTrue(x.getText().toLowerCase().contains("no posee cuentas de facturación con débito automático"));
	}
	
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38207_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Stop_Debit_Cuenta_NO_adherida_a_Aut_Deb_Que_NO_se_vea() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(2).click();
		sleep(7000);
		WebElement x = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-message.ng-scope.ng-invalid.ng-invalid-vlc-val-error.ng-dirty"));
		Assert.assertTrue(x.getText().toLowerCase().contains("no posee cuentas de facturación con débito automático"));
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38244_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_3_Adhesión_Formato_Tarjetas_Credito() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		sleep(7000);
		List <WebElement> x = driver.findElements(By.className("slds-checkbox--faux"));
		x.get(0).click();
		x.get(1).click();
		driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.className("vlc-slds-item--addbutton-Long")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-modal__content.slds-p-around--medium.vloc-modal-content.slds-is-relative")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope"));
		a.get(2).click();
		WebElement b = driver.findElement(By.id("CardNumber__c"));
		Assert.assertTrue(b.getAttribute("placeholder").equals("____-____-____-____"));
	}	
	
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38229_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_2_Adhesión_Cuenta_con_Fraude() {
		cc.elegircuenta("aaaaRaul Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(element.getText().toLowerCase().contains("no puede continuar con esta operación"));
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38256_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_3_Adhesión_Adhiere_tarjeta_que_no_estaba_adherida() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		sleep(7000);
		List <WebElement> x = driver.findElements(By.className("slds-checkbox--faux"));
		x.get(0).click();
		driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.className("vlc-slds-item--addbutton-Long")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-modal__content.slds-p-around--medium.vloc-modal-content.slds-is-relative")));
		List <WebElement> checkbox = driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope"));
		checkbox.get(2).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding")));
		List <WebElement> b = driver.findElements(By.id("CardNumber__c"));
		b.get(1).sendKeys("4071111111111111");
		List <WebElement> c = driver.findElements(By.id("DueDate"));
		c.get(1).sendKeys("122019");
		List <WebElement> d = driver.findElements(By.id("SecurityCode__c"));
		d.get(1).sendKeys("123");
		List <WebElement> e = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding"));
		e.get(1).click();
		sleep(5000);
		WebElement f = driver.findElement(By.cssSelector(".slds-size--12-of-12.slds-medium-size--12-of-12.slds-large-size--12-of-12.vlc-slds-edit-block--Long-cards.ng-scope"));
		Assert.assertTrue(f.getText().toLowerCase().contains("tarjeta de crédito") && f.getText().toUpperCase().contains("AMERICAN EXPRESS"));
	}
		
	@Test(groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS38255_Automatic_Debit_Subscriptions_Sesión_guiada_Débito_Automático_Inicial_Paso_3_Adhesión_Numero_de_TC_Ingresa_0000_No_Reconoce_Empresa() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("débito auto");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element__label.vlc-slds-inline-control__label.ng-binding")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		sleep(7000);
		List <WebElement> x = driver.findElements(By.className("slds-checkbox--faux"));
		x.get(0).click();
		driver.findElement(By.id("AvailableBillingAccounts_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.className("vlc-slds-item--addbutton-Long")).click();
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-modal__content.slds-p-around--medium.vloc-modal-content.slds-is-relative")));
		List <WebElement> a = driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope"));
		a.get(2).click();
		sleep(3000);  
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding")));
		List <WebElement> b = driver.findElements(By.id("CardNumber__c"));
		b.get(1).sendKeys("0000111111111111");
		List <WebElement> c = driver.findElements(By.id("EmptyBranding"));
		Assert.assertTrue(c.get(1).isDisplayed());
	}
}
