package Tests;

import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CasePage;
import Pages.CustomerCare;
import Pages.setConexion;

public class CustomerCareFase2 extends TestBase {

	private CustomerCare cc;
	private BasePage bp;
	private Accounts ac;

	
	@AfterClass(groups = {"CustomerCare", "Vista360Layout", "CambiosDeCondiciónImpositiva", "Sugerencias", "DetalleDeConsumos", "CambioDeCiclo", "MovimientoDeCuentasDeFacturación", "AdministraciónDeCasos", "CostoDeCambios"})
	public void tearDown() {
		driver.quit();
		sleep(5000);
	}

	@BeforeClass(groups = {"CustomerCare", "Vista360Layout", "CambiosDeCondiciónImpositiva", "Sugerencias", "DetalleDeConsumos", "CambioDeCiclo", "MovimientoDeCuentasDeFacturación", "AdministraciónDeCasos", "CostoDeCambios"})
	public void init() throws Exception {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		bp = new BasePage(driver);
		ac = new Accounts(driver);
		login(driver);
		sleep(10000);
		String a = driver.findElement(By.id("tsidLabel")).getText();
		driver.findElement(By.id("tsidLabel")).click();
		if (a.equals("Ventas")) {
			driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		} else {
			driver.findElement(By.xpath("//a[@href=\'/home/home.jsp?tsid=02u41000000QWha\']")).click();
			sleep(4000);
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\'/console?tsid=02uc0000000D6Hd\']")).click();
		}
	}

	@BeforeMethod(groups = {"CustomerCare", "Vista360Layout", "CambiosDeCondiciónImpositiva", "Sugerencias", "DetalleDeConsumos", "CambioDeCiclo", "MovimientoDeCuentasDeFacturación", "AdministraciónDeCasos", "CostoDeCambios"})
	public void setup() {
		cc.cerrarultimapestana();
	}

	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7137_BusinessDataPanelQuickAccessButtonsAccount() {
		goToLeftPanel(driver, "Cuentas");
		cc.elegircuenta("aaaaAndres Care");
		cc.openleftpanel();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box-details")));
		driver.findElements(By.className("profile-box-details"));
		cc.validarDatos();
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS7138_BusinessDataPanelPicklistCommercialDataAccount() {
		goToLeftPanel(driver, "Cuentas");
		cc.elegircuenta("aaaaAndres Care");
		cc.openleftpanel();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box-details")));
		driver.findElement(By.className("account-select"));
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15962_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Boton_de_sesion_guiada() {
		goToLeftPanel(driver, "Cuentas");
		cc.elegircuenta("aaaaAndres Care");
		cc.openrightpanel();
		cc.ValidarBtnsGestion("Cambios de condición impositiva");
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS14567_Capacidades_de_Busqueda_Filtrar_Por_DNI() {
		cc.usarbuscadorsalesforce("30303030");
		cc.validarlabusqueda("aaaaAndres Care");
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS14565_View_Capacidades_de_Busqueda_Visualizar_Filtro_Salesforce() {
		cc.validarbuscadorsalesforce();
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS14570_Busqueda_de_Transacciones_Filtrar_Por_Numero_de_Caso() {
		driver.findElement(By.id("phSearchInput")).clear();
		sleep(3000);
		cc.usarbuscadorsalesforce("00003035");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".listRelatedObject.caseBlock")));
		WebElement element = driver.findElement(By.cssSelector(".listRelatedObject.caseBlock"));
		Assert.assertTrue(element.getText().contains("00003035"));
		driver.switchTo().defaultContent();
	}

	@Test(groups = {"CustomerCare", "Sugerencias"})
	public void TS12244_Positive_Feedback_Suggestions_Generic_Interaction_No_Follow_up_Required_Creacion_de_los_Casos_Crear_Caso() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("sugerencia");
		cc.crearsugerencia("Sugerencias", "Publicidad", "crear");
		List<WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("su gestión se finalizo correctamente.")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "Sugerencias"})
	public void TS12245_Positive_Feedback_Suggestions_Generic_Interaction_No_Follow_up_Required_Creacion_de_los_Casos_Crear_y_Cancelar_Gestion() {
		CasePage page1 = new CasePage(driver);
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("sugerencia");
		cc.crearsugerencia("Sugerencias", "Productos/Servicios", "cancel");
		cc.cerrarultimapestana();
		cc.elegircaso();
		page1.validarcasocerrado("", "", "Sugerencias", "nico");
	}
	
	@Test(groups = {"CustomerCare", "Sugerencias"})
	public void TS12302_Positive_Feedback_Suggestions_Generic_Interaction_No_Follow_up_Required_Detalle_de_Atributos_Feedback_Positivo_Generar_Gestion_Subcategoria_Atencion_Ejecutivos() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("sugerencia");
		cc.crearsugerencia("Sugerencias", "Atención Ejecutivos", "crear");
		List<WebElement> element = driver.findElements(By.className("ta-care-omniscript-done"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("su gestión se finalizo correctamente.")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15962_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Boton_de_sesion_guiada() {
		cc.elegircuenta("aaaaFernando Care");
		cc.ValidarBtnsGestion("Cambios de condi");
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15966_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_1_Escenario_1() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		cc.validarCheckBox();
		List<WebElement> dni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope"));
		for (WebElement x : dni) {
			if (x.getText().toLowerCase().contains("dni --> cuit")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15976_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_2_Seleccion_DNI_a_CUIT() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15977_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_2_Sin_seleccion_DNI_a_CUIT() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		cc.validarError();
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15974_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_2_Visualizar_DNI_a_CUIT() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		WebElement billings = driver.findElement(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding"));
		boolean a = false;
		if (billings.isDisplayed()) {
			a = true;
			assertTrue(a);
		}
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15993_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_4_Valores_IVA_No_ejecutivo() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.setSimpleDropdown((driver.findElement(By.id("NewCUITType"))), "20");
		cc.setSimpleDropdown((driver.findElement(By.id("NewCheckDigit"))), "1");
		cc.clickSiguiente(driver.findElement(By.id("Step_5_DNI_To_CUIT_nextBtn")));
		sleep(5000);
		driver.findElement(By.id("IVACondition")).click();
		sleep(2000);
		Select ivaSelect = new Select(driver.findElement(By.id("IVACondition")));
		Assert.assertTrue(bp.getSelectOptions(ivaSelect).contains("IVA Exento / No Alcanzado"));
		Assert.assertTrue(bp.getSelectOptions(ivaSelect).contains("IVA Responsable Inscripto"));
		Assert.assertTrue(bp.getSelectOptions(ivaSelect).contains("IVA Sujeto No Categorizado"));
		Assert.assertTrue(bp.getSelectOptions(ivaSelect).contains("IVA Monotributista"));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15999_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_4_Visualizar_Exencion_IVA() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.setSimpleDropdown((driver.findElement(By.id("NewCUITType"))), "20");
		cc.setSimpleDropdown((driver.findElement(By.id("NewCheckDigit"))), "1");
		cc.clickSiguiente(driver.findElement(By.id("Step_5_DNI_To_CUIT_nextBtn")));
		sleep(5000);
		cc.ElementPresent(driver.findElement(By.cssSelector(".taxConditionChanges")));
		driver.switchTo().defaultContent();
	}

	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS16015_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_4_Visualizar_Percepcion_IIBB() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.setSimpleDropdown((driver.findElement(By.id("NewCUITType"))), "20");
		cc.setSimpleDropdown((driver.findElement(By.id("NewCheckDigit"))), "1");
		cc.clickSiguiente(driver.findElement(By.id("Step_5_DNI_To_CUIT_nextBtn")));
		sleep(5000);
		cc.ElementPresent(driver.findElement(By.id("IIBBCondition")));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS16017_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_4_Visualizar_Picklist_Jurisdicciones_Percepcion_IIBB() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.setSimpleDropdown((driver.findElement(By.id("NewCUITType"))), "20");
		cc.setSimpleDropdown((driver.findElement(By.id("NewCheckDigit"))), "1");
		cc.clickSiguiente(driver.findElement(By.id("Step_5_DNI_To_CUIT_nextBtn")));
		sleep(5000);
		cc.ElementPresent(driver.findElement(By.id("IIBBExemptionJurisdiction")));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15965_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Validaciones_negativas() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.validarError();
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS16052_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_6_Confirmacion() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.setSimpleDropdown((driver.findElement(By.id("NewCUITType"))), "20");
		cc.setSimpleDropdown((driver.findElement(By.id("NewCheckDigit"))), "1");
		cc.clickSiguiente(driver.findElement(By.id("Step_5_DNI_To_CUIT_nextBtn")));
		sleep(5000);
		cc.setSimpleDropdown(driver.findElement(By.id("IVACondition")), "IVA Monotributista");
		driver.findElement(By.id("IVAExemptionDateTo")).sendKeys("24/12/2017");
		driver.findElement(By.id("IVAExemptionPercentage")).sendKeys("10");
		cc.setSimpleDropdown(driver.findElement(By.id("IIBBCondition")), "IIBB Inscripto Local");
		cc.setSimpleDropdown(driver.findElement(By.id("IIBBExemptionJurisdiction")), "CABA");
		driver.findElement(By.id("IIBBExemptionDateTo")).sendKeys("25/12/2017");
		driver.findElement(By.id("IIBBExemptionPercentage")).sendKeys("10");
		sleep(2000);
		driver.findElement(By.id("RespaldatoryDocumentationFile")).sendKeys("C:\\descarga.jpg");
		sleep(2000);
		cc.clickSiguiente(driver.findElement(By.id("Step_6_Select_New_Tax_Condition_DNI_To_CUIT_nextBtn")));
		sleep(3000);
		cc.clickSiguiente(driver.findElement(By.id("Step_8_Summary_nextBtn")));
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("ng-binding")));
		List<WebElement> nombre = driver.findElements(By.className("ng-binding"));
		Assert.assertTrue(nombre.get(0).getText().contains("Los datos se actualizaron correctamente. Su número de gestión es:"));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS16054_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Paso_6_Caso_creado() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-radio--faux.ng-scope")));
		driver.findElement(By.cssSelector(".slds-radio--faux.ng-scope")).click();
		cc.clickSiguiente(driver.findElement(By.id("Step_2_Select_Tax_Condition_To_Modify_nextBtn")));
		sleep(10000);
		cc.billings(driver.findElements(By.cssSelector(".slds-form-element__label.tax-condition-billing-accounts-name.ng-binding")));
		cc.clickSiguiente(driver.findElement(By.id("Step_3_Select_Billing_Accounts_nextBtn")));
		sleep(3000);
		cc.setSimpleDropdown((driver.findElement(By.id("NewCUITType"))), "20");
		cc.setSimpleDropdown((driver.findElement(By.id("NewCheckDigit"))), "1");
		cc.clickSiguiente(driver.findElement(By.id("Step_5_DNI_To_CUIT_nextBtn")));
		sleep(5000);
		cc.setSimpleDropdown(driver.findElement(By.id("IVACondition")), "IVA Monotributista");
		driver.findElement(By.id("IVAExemptionDateTo")).sendKeys("24/12/2017");
		driver.findElement(By.id("IVAExemptionPercentage")).sendKeys("10");
		cc.setSimpleDropdown(driver.findElement(By.id("IIBBCondition")), "IIBB Inscripto Local");
		cc.setSimpleDropdown(driver.findElement(By.id("IIBBExemptionJurisdiction")), "CABA");
		driver.findElement(By.id("IIBBExemptionDateTo")).sendKeys("25/12/2017");
		driver.findElement(By.id("IIBBExemptionPercentage")).sendKeys("10");
		sleep(2000);
		driver.findElement(By.id("RespaldatoryDocumentationFile")).sendKeys("C:\\descarga.jpg");
		sleep(2000);
		cc.clickSiguiente(driver.findElement(By.id("Step_6_Select_New_Tax_Condition_DNI_To_CUIT_nextBtn")));
		sleep(3000);
		cc.clickSiguiente(driver.findElement(By.id("Step_8_Summary_nextBtn")));
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("ng-binding")));
		List<WebElement> nombre = driver.findElements(By.className("ng-binding"));
		Assert.assertTrue(nombre.get(0).getText().contains("Los datos se actualizaron correctamente."));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-media.slds-media--timeline.slds-timeline__media--standard-case")));
		WebElement x = driver.findElement(By.cssSelector(".slds-media.slds-media--timeline.slds-timeline__media--standard-case"));
		Assert.assertTrue(x.getText().toLowerCase().contains("cambio de condición impositiva"));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS15905_Consumption_Details_Definicion_de_Filtros_Filtro_Lista_de_Servicios_Servicio_que_lo_tiene_el_cliente() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-01")));
		sleep(15000);
		sleep(15000);
		driver.findElement(By.id("text-input-01")).click();
		sleep(4000);
		List<WebElement> servicios = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown--left")).get(0).findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label"));
		Assert.assertTrue(servicios.size() > 0);
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS15906_Consumption_Details_Definicion_de_Filtros_Filtro_Lista_de_Servicios_Servicio_que_no_tiene_el_cliente() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("detalle de consumo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("text-input-01")));
		sleep(15000);
		driver.findElement(By.id("text-input-01")).click();
		sleep(4000);
		List<WebElement> servicios = driver.findElements(By.cssSelector(".slds-dropdown.slds-dropdown--left")).get(0).findElements(By.cssSelector(".slds-lookup__item-action.slds-lookup__item-action--label"));
		Assert.assertTrue(servicios.size() != 0);
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS15964_Tax_Condition_Changes_Sesion_Guiada_Para_Cambios_en_Condicion_Impositiva_Guardar_para_Despues_Sesion_Guiada() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambios de condi");
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")));
		List<WebElement> guardar = driver.findElements(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		guardar.get(1).click();
		sleep(2000);
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-box.vlc-slds-save_for_later")));
		cc.ElementPresent(driver.findElement(By.cssSelector(".slds-grid.slds-box.vlc-slds-save_for_later")));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS15954_360_View_Ver_Equipo_Creador_en_Case_Visualizar_campo_Equipo_del_Creador() {
		cc.elegircuenta("aaaaFernando Care");
		cc.elegircaso();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".x-grid3-hd-inner.x-grid3-hd-00Nc0000001iLah")).isDisplayed());
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16069_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_1_Seleccion_Billing_accounts() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambio de ciclo");
		sleep(25000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BillingCycle_nextBtn")));
		List<WebElement> checkbox = driver.findElements(By.className("slds-checkbox--faux"));
		checkbox.get(0).click();
		checkbox.get(1).click();
		checkbox.get(2).click();
		Assert.assertTrue(cc.ElementPresent(driver.findElement(By.id("BillingCycle_nextBtn"))));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16062_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_1_Visualizar_Billing_accounts() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambio de ciclo");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-checkbox--faux")));
		List<WebElement> cuenta = driver.findElements(By.className("slds-checkbox--faux"));
		Assert.assertTrue(cuenta.size() > 0);
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16077_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_2_Valores_Picklist_Ciclo_de_facturacion() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("cambio de ciclo");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BillingCycle_nextBtn")));
		List<WebElement> checkbox = driver.findElements(By.className("slds-checkbox--faux"));
		checkbox.get(0).click();
		checkbox.get(1).click();
		checkbox.get(2).click();
		driver.findElement(By.id("BillingCycle_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("BillingCycleSelect")).click();
		Select dias = new Select(driver.findElement(By.id("BillingCycleSelect")));
		Assert.assertTrue(bp.getSelectOptions(dias).contains("1"));
		Assert.assertTrue(bp.getSelectOptions(dias).contains("7"));
		Assert.assertTrue(bp.getSelectOptions(dias).contains("14"));
		Assert.assertTrue(bp.getSelectOptions(dias).contains("21"));
		driver.switchTo().defaultContent();
	}

	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS16056_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Funcionamiento_Boton_Sesion_Guiada() {
		cc.elegircuenta("aaaaAndres Care");
		cc.SelectGestion("Cambio de ciclo");
		sleep(30000);
		cc.ValidarCambioDeCiclo();
	}
	
	@Test(groups = {"CustomerCare", "CambiosDeCondiciónImpositiva"})
	public void TS16055_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion__Boton_Sesion_Guiada() {
		cc.elegircuenta("aaaaAndres Care");
		cc.ValidarBtnsGestion("Cambio de ciclo");
	}
	
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12252_Billing_Group_User_Line_Movements_Paso_0_Error_por_cliente_inactivo() {
		goToLeftPanel(driver, "Cuentas");
		cc.elegircuenta("aaaaAndres Care");
		cc.SelectGestion("Movimientos de cuenta de facturaci");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(element.getText().contains("El cliente no está activo"));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12251_Billing_Group_User_Line_Movements_Paso_0_Error_por_fraude_Cliente_inactivo() {
		cc.elegircuenta("aaaaAndres Care");
		cc.SelectGestion("Movimientos de cuenta de facturaci");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(element.getText().contains("El cliente no está activo"));
	}
	
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12262_Billing_Group_User_Line_Movements_Paso_1_Billing_Account_suspendida_por_fraude_No_se_visualiza() {
		cc.elegircuenta("aaaaAndres Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("el cliente tiene fraude") && x.getText().toLowerCase().contains("el cliente no está activo")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12261_Billing_Group_User_Line_Movements_Paso_1_Mover_Bundle_Se_mueven_todos_los_servicios() {
		Assert.assertTrue(false);
		/*goToLeftPanel(driver, "Cuentas");
		cc.editarcuenta("aaaaFernando Care", "no", "active");
		cc.editarcuenta("aaaaaaaaFernando Care Billing 1", "no", "active");
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("Movimientos de cuenta de facturaci");
		sleep(10000);
		driver.findElement(By.id("Validaciones_nextBtn")).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.className("slds-checkbox--faux"));
		element.get(0).click();
		driver.findElement(By.id("BillingAccountFrom_nextBtn")).click();
		sleep(10000);
		List <WebElement> x = driver.findElements(By.className("slds-radio--faux"));
		x.get(1).click();
		driver.findElement(By.id("BillingAccountToStep_nextBtn")).click();
		sleep(5000);
		driver.findElement(By.id("Summary_nextBtn")).click();
		cc.serviciocambiadecuenta("Arnet 10 MB (Prueba)", "aaaaaaaaFernando Care Billing 2");
		cc.SelectGestion("Movimientos de cuenta de facturacion");
		cc.validarerrorpaso1("servicio cambia de cuenta billing");*/
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS15953_Billing_Cycle_Changes_Rastreo_de_los_Cambios_del_Inicio_del_Ciclo_de_Facturacion_Visualizar_Datos_Anteriores_Ciclo_Facturacion() {
		cc.elegircuenta("aaaaFernando Care");
		cc.usarpanelcentral("Detalles");
		cc.validarhistorialdecuentas();
	}

	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16061_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_0_Caso_Cliente_activo() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");		
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
		List <WebElement> error = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		error.get(1).click();
		sleep(5000);
		List <WebElement> error2 = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		int cant = 0;
		while (cant < 2) {
			try {
				error2.get(1).click();
			} catch(org.openqa.selenium.StaleElementReferenceException e) {
				cant++;
			}
		}
		sleep(5000);
		Assert.assertTrue(false);
		//Assert.assertTrue(driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).isDisplayed());
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS15959_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_0_Caso_fraude() {
		cc.elegircuenta("aaaaRaul Care");
		cc.SelectGestion("ciclo");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".message.description.ng-binding.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		Assert.assertTrue(element.getText().contains("En este momento no se puede efectuar este tipo de gestión porque su cuenta está en un estado de fraude"));
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16060_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_0_Validaciones_Cliente_activo() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");		
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".message.description.ng-binding.ng-scope")));
		WebElement element = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		Assert.assertTrue(element.getText().toLowerCase().contains("en este momento no se puede efectuar este tipo de gestión porque su cuenta está en estado inactiva."));
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16057_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_0_Validaciones_correctas() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");		
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
		List <WebElement> error = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		error.get(1).click();
		sleep(5000);
		List <WebElement> error2 = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		int cant = 0;
		while (cant < 2) {
			try {
				error2.get(1).click();
			} catch(org.openqa.selenium.StaleElementReferenceException e) {
				cant++;
			}
		}
		sleep(5000);
		driver.findElement(By.id("CostReview_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")));
		boolean a = false;
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("en este formulario podrás cambiar la fecha en la cual se te empieza a facturar cada mes")) {
				a = true;
			}
		}
		Assert.assertTrue(false);
		//Assert.assertTrue(a);
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16065_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_1_Ciclo_Billing_accounts() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
		List <WebElement> error = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		error.get(1).click();
		sleep(5000);
		List <WebElement> error2 = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		int cant = 0;
		while (cant < 2) {
			try {
				error2.get(1).click();
			} catch(org.openqa.selenium.StaleElementReferenceException e) {
				cant++;
			}
		}
		sleep(5000);
		driver.findElement(By.id("CostReview_nextBtn")).click();
		sleep(10000);
		List <WebElement> accounts = driver.findElements(By.className("slds-checkbox__label"));
		boolean a = false, b = false;
		for (WebElement x : accounts) {
			if (x.getText().contains("aaaaFernando Care")) {
				a = true;
			}
			if (x.getText().contains("aaaaFernando Care Billing 1")) {
				b = true;
			}
		}
		Assert.assertTrue(false);
		//Assert.assertTrue(a && b);
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16064_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_1_Funcionamiento_Boton_Servicios_Billing_accounts() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
		List <WebElement> error = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		error.get(1).click();
		sleep(5000);
		List <WebElement> error2 = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		int cant = 0;
		while (cant < 2) {
			try {
				error2.get(1).click();
			} catch(org.openqa.selenium.StaleElementReferenceException e) {
				cant++;
			}
		}
		sleep(5000);
		driver.findElement(By.id("CostReview_nextBtn")).click();
		sleep(10000);
		List<WebElement> element = driver.findElements(By.id("tree0-node1__label"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("ver servicios contratados")) {
				a = true;
			}
		}
		Assert.assertTrue(false);
		//Assert.assertTrue(a);
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16078_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_2_Mandatorio_Picklist_Ciclo_de_facturacion() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")));
		List <WebElement> error = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		error.get(1).click();
		sleep(5000);
		List <WebElement> error2 = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope"));
		int cant = 0;
		while (cant < 2) {
			try {
				error2.get(1).click();
			} catch(org.openqa.selenium.StaleElementReferenceException e) {
				cant++;
			}
		}
		sleep(5000);
		driver.findElement(By.id("CostReview_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BillingCycle_nextBtn")));
		List<WebElement> checkbox = driver.findElements(By.className("slds-checkbox--faux"));
		checkbox.get(0).click();
		checkbox.get(1).click();
		driver.findElement(By.id("BillingCycle_nextBtn")).click();
		sleep(10000);
		driver.findElement(By.id("NewBillingCycle_nextBtn")).click();
		sleep(2000);
		Assert.assertTrue(false);
		//Assert.assertTrue(x.getText().toLowerCase().contains("error: por favor complete todos los campos requeridos"));
		driver.findElement(By.id("alert-ok-button")).click();
		driver.switchTo().defaultContent();
	}
	
	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16080_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_Inicio_Ciclo_Facturacion_Paso3_Visualizar_Datos_Antiguos_Resumen() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");
		cc.clickContinueError();
		cc.clickContinueError();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(bp.getFrameForElement(driver, By.id("BillingCycle_nextBtn")));
		String parcial = driver.findElement(By.id("tree0-node1")).findElements(By.cssSelector(".slds-form-element__label.slds-truncate.ng-binding")).get(1).getText();
		parcial = parcial.substring(parcial.length() - 2, parcial.length());
		if (parcial.contains(" ")) {
			parcial.substring(1);
		}
		System.out.println("parcial" + parcial);
		cc.ciclodefacturacionpaso2();
		sleep(4000);
		if (parcial.equals("1")) {
			bp.setSimpleDropdown(driver.findElement(By.id("BillingCycleSelect")), "7");
		} else {
			bp.setSimpleDropdown(driver.findElement(By.id("BillingCycleSelect")), "1");
		}
		sleep(4000);
		((JavascriptExecutor) driver).executeScript(
		"window.scrollTo(0," + driver.findElement(By.id("NewBillingCycle_nextBtn")).getLocation().y + ")");
		driver.findElement(By.id("NewBillingCycle_nextBtn")).click();
		sleep(8000);
		String actual = driver.findElement(By.id("SelectableItems2")).findElements(By.cssSelector("slds-text-align--left.slds-m-around--x-small.ng-binding.ng-scope")).get(1).getText();
		actual = actual.substring(actual.length() - 2, actual.length());
		if (actual.contains(" ")) {
			actual.substring(1);
		}
		System.out.println("actual" + actual);
		assertTrue(actual.equals(parcial));
		// error, se deben esperar 2 dias para relaizar la prueba
	}

	@Test(groups = {"CustomerCare", "CambioDeCiclo"})
	public void TS16081_Billing_Cycle_Changes_Sesion_Guiada_para_Cambios_de_Inicio_de_Ciclo_de_Facturacion_Paso_3_Visualizar_Datos_Nuevos_Resumen() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("ciclo");
		cc.clickContinueError();
		cc.clickContinueError();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(bp.getFrameForElement(driver, By.id("BillingCycle_nextBtn")));
		cc.billings(driver.findElements(By.className("slds-checkbox--faux")));
		sleep(5000);
		String parcial = driver.findElement(By.id("tree0-node1")).findElements(By.cssSelector(".slds-form-element__label.slds-truncate.ng-binding")).get(1).getText();
		parcial = parcial.substring(parcial.length() - 2, parcial.length());
		if (parcial.contains(" ")) {
			parcial.substring(1);
		}
		System.out.println("parcial" + parcial);
		cc.ciclodefacturacionpaso2();
		if (parcial.equals("1")) {
			bp.setSimpleDropdown(driver.findElement(By.id("BillingCycleSelect")), "7");
		} else {
			bp.setSimpleDropdown(driver.findElement(By.id("BillingCycleSelect")), "1");
		}
		sleep(2000);
		((JavascriptExecutor) driver).executeScript(
		"window.scrollTo(0," + driver.findElement(By.id("BillingCycle_nextBtn")).getLocation().y + ")");
		driver.findElement(By.id("BillingCycle_nextBtn")).click();
	}
		
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12254_Billing_Group_User_Line_Movements_Paso_1_Seleccion_de_Billing_Account_sin_seleccionar_servicios() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		driver.findElement(By.id("Validaciones_nextBtn")).click();
		sleep(20000);
		driver.findElement(By.id("BillingAccountFrom_nextBtn")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-modal__header.slds-theme--info.slds-theme--alert-texture.slds-theme--error")).isDisplayed());
		driver.findElement(By.id("alert-ok-button")).click();
		driver.switchTo().defaultContent();
	}
		
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12255_Billing_Group_User_Line_Movements_Paso_1_Seleccion_de_Billing_Account_y_un_servicio() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		driver.findElement(By.id("Validaciones_nextBtn")).click();
		sleep(20000);
		List <WebElement> element = driver.findElements(By.className("slds-checkbox--faux"));
		element.get(1).click();
		driver.findElement(By.id("BillingAccountFrom_nextBtn")).click();
		sleep(7000);
		List <WebElement> x = driver.findElements(By.cssSelector(".slds-size--11-of-12.vlc-slds-flex-grow.vlc-slds-underline--gradient"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("paso 2: seleccionar billing account de destino")) {
				Assert.assertTrue(a.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}
		
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12260_Billing_Group_User_Line_Movements_Paso_1_Visualizacion_Bundle() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		driver.findElement(By.id("Validaciones_nextBtn")).click();
		sleep(20000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-tree__container.ng-scope")).isDisplayed());
		driver.switchTo().defaultContent();
	}
		
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12268_Billing_Group_User_Line_Movements_Paso_2_Billing_Account_suspendida_por_fraude_No_se_visualiza() {
		cc.elegircuenta("aaaaAndres Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-1.ng-scope"));
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("el cliente tiene fraude") && x.getText().toLowerCase().contains("el cliente no está activo")) {
				Assert.assertTrue(x.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}
		
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12264_Billing_Group_User_Line_Movements_Paso_2_Expansion_de_servicios() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		driver.findElement(By.id("Validaciones_nextBtn")).click();
		sleep(20000);
		List <WebElement> element = driver.findElements(By.className("slds-checkbox--faux"));
		element.get(1).click();
		driver.findElement(By.id("BillingAccountFrom_nextBtn")).click();
		sleep(7000);
		List <WebElement> x = driver.findElements(By.className("slds-radio__label"));
		for (WebElement a : x) {
			if (a.getText().toLowerCase().contains("aaaaaaaaFernando Care Billing 1")) {
				a.click();
			}
		}
		List <WebElement> a = driver.findElements(By.className("slds-form-element__control"));
		for (WebElement b : a) {
			if (b.getText().toLowerCase().contains("fernando baf care")) {
				Assert.assertTrue(b.isDisplayed());
			}
		}
		driver.switchTo().defaultContent();
	}	
	
	@Test(groups = {"CustomerCare", "MovimientoDeCuentasDeFacturación"})
	public void TS12273_Billing_Group_User_Line_Movements_Cancelacion_de_omniscript_Caso_con_estado_cancelado() {
		cc.elegircuenta("aaaaFernando Care");
		cc.SelectGestion("movimiento");
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Validaciones_nextBtn")));
		driver.findElement(By.id("Validaciones_nextBtn")).click();
		sleep(20000);
		List <WebElement> element = driver.findElements(By.className("slds-checkbox--faux"));
		element.get(1).click();
		driver.findElement(By.id("BillingAccountFrom_nextBtn")).click();
		sleep(7000);
		driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("alert-ok-button")));
		driver.findElement(By.id("alert-ok-button")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-text-heading--large")));
		WebElement x = driver.findElement(By.className("slds-text-heading--large"));
		Assert.assertTrue(x.getText().toLowerCase().contains("el proceso se canceló"));
		driver.switchTo().defaultContent();
	}
		
	@Test(groups = {"CustomerCare", "AdministraciónDeCasos"})
	public void TS14601_Case_Management__Casos_Ordernados_Por_Tipos_Vista_Todos_Los_Casos_Abiertos(){
		 cc.cerrarultimapestana();
	     driver.switchTo().defaultContent();
	     goToLeftPanel2(driver, "Casos");
	     ac.accountSelect("Todos Los Casos Abiertos");
	     sleep(5000);
	     assertTrue(driver.findElement(By.cssSelector(".x-panel.x-grid-panel")).isDisplayed());
	}
		
	@Test(groups = { "CustomerCare", "CostoDeCambios" })
	public void TS15850_Cost_For_Changes_Sesion_Guiada_Visualizar_Leyenda_Cargo_Gestion_Consumidor_Final_Costo_IVA() {
		goToLeftPanel2(driver, "Cuentas");
		driver.switchTo().defaultContent();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".topNav.primaryPalette")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		try {
			field.selectByVisibleText("Todas Las cuentas");
		} catch (org.openqa.selenium.NoSuchElementException ExM) {
			field.selectByVisibleText("Todas las cuentas");
		}
		sleep(9000);
		cc.cerrarultimapestana();
		sleep(5000);
		cc.elegircuenta("aaaaFernando Care");
		sleep(9000);
		ac.findAndClickButton("Cambios de condición impositiva");
		sleep(8000);
		assertTrue(false);
		// bug reportado, arreglar cuando se pueda visualizar
	}
		
	@Test(groups = {"CustomerCare", "CostoDeCambios"})
	public void TS15851_Cost_For_Changes_Sesion_Guiada_Visualizar_Leyenda_Cargo_Gestion_Empresas_Costo_Impuestos(){
		 goToLeftPanel2(driver, "Cuentas");
	     driver.switchTo().defaultContent();
	     driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".topNav.primaryPalette")));
	     Select field = new Select(driver.findElement(By.name("fcf")));
	     try {field.selectByVisibleText("Todas Las cuentas");}
	     catch (org.openqa.selenium.NoSuchElementException ExM) {field.selectByVisibleText("Todas las cuentas");}
	     sleep(9000);
	     cc.cerrarultimapestana();
		 sleep(5000); 
		 cc.elegircuenta("Empresa Care");
		 sleep(9000); 
		 ac.findAndClickButton("Cambios de condición impositiva");
		 sleep(8000); 
		 assertTrue(false);
		 //bug reportado, arreglar cuando se pueda visualizar
	}
		
	@Test(groups = {"CustomerCare", "Vista360Layout"})
	public void TS14569_360_View_360_View_Capacidades_De_Busqueda_Filtrar_Por_Billing_Account(){
		driver.switchTo().defaultContent();
		driver.findElement(By.id("phSearchInput")).clear();
		sleep(3000);
		driver.findElement(By.id("phSearchInput")).sendKeys("aaaaFernando Care Billin");
		sleep(10000);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("phSearchInput:group0:option0")).click();
		sleep(10000);
		try {driver.switchTo().alert().accept();}catch(org.openqa.selenium.NoAlertPresentException e) {}
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(ac.getFrameForElement(driver, By.id("topButtonRow")));
		assertTrue(driver.findElement(By.className("mainTitle")).getText().equals("Detalle de Cuenta"));
	}
		
	@Test(groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS15940_Consumption_Details_Mostrar_Informacion_Sobre_El_Tiempo_De_Actualizacion_Ultima_Actualizacion_Dentro_Del_Dia(){
		 goToLeftPanel2(driver, "Cuentas");
	     driver.switchTo().defaultContent();
	     driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".topNav.primaryPalette")));
	     Select field = new Select(driver.findElement(By.name("fcf")));
	     try {field.selectByVisibleText("Todas Las cuentas");}
	     catch (org.openqa.selenium.NoSuchElementException ExM) {field.selectByVisibleText("Todas las cuentas");}
	     sleep(9000);
	     cc.cerrarultimapestana();
		 sleep(5000); 
		 cc.elegircuenta("aaaaFernando Care");
		 ac.findAndClickButton("Detalle de Consumos");
		 sleep(8000);
		 driver.switchTo().defaultContent();
		 driver.switchTo().frame(ac.getFrameForElement(driver, By.id("text-input-01")));
		 driver.findElement(By.id("text-input-01")).click();
		 driver.findElement(By.cssSelector(".slds-dropdown.slds-dropdown--left")).findElement(By.tagName("span")).click();
		 driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		 driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
		 sleep(5000); 
		 //driver.switchTo().defaultContent();
		 //driver.switchTo().frame(ac.getFrameForElement(driver, By.className(".slds-text-title")));
		 String fecha = driver.findElements(By.className("slds-text-title")).get(2).getText();
		 fecha = fecha.substring(25, fecha.length()-2);
		 System.out.println("fecha: "+fecha);
		 Calendar Factual = Calendar.getInstance();
		 System.out.println("fecha act: "+(Integer.toString(Factual.get(Calendar.DATE))+"/"+Integer.toString(Factual.get(Calendar.MONTH))+"/"+Integer.toString(Factual.get(Calendar.YEAR))));
		 assertTrue(fecha.split(" ")[0].equals(Integer.toString(Factual.get(Calendar.DATE))+"/"+Integer.toString(Factual.get(Calendar.MONTH))+"/"+Integer.toString(Factual.get(Calendar.YEAR)))); 
	}
		
	@Test(groups = {"CustomerCare", "AdministraciónDeCasos"})
	public void TS15960_360_View_Ver_Equipo_Creador_En_Case_Usuario_Cambia_De_Equipo_No_Se_Modifica_El_Campo_Equipo_Del_Creador() throws ParseException{
		 Date date1 = new Date();
	     driver.switchTo().defaultContent();
	     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	     try {
			date1 = sdf.parse("24/11/2017");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     goToLeftPanel2(driver, "Casos");
	     ac.accountSelect("Mis Casos");
	     driver.switchTo().defaultContent();
	     driver.switchTo().frame(ac.getFrameForElement(driver, By.className("x-grid3-body")));
	     List<WebElement> TodosCasos = driver.findElement(By.className("x-grid3-body")).findElements(By.className("x-grid3-row"));
	     TodosCasos.remove(0);
	     for (WebElement UnC : TodosCasos) {
	    	 String fecha = UnC.findElements(By.tagName("td")).get(12).findElement(By.tagName("div")).getText();
	    	 fecha = fecha.split(" ")[0];
	    	 if (date1.compareTo(sdf.parse(fecha))>0) {
	    		 System.out.println("Equipo: "+UnC.findElements(By.tagName("td")).get(10).findElement(By.tagName("div")).getText());
	    		 assertTrue(!UnC.findElements(By.tagName("td")).get(10).findElement(By.tagName("div")).getText().equals("Cubo magico team"));
	    		 break;
	    	 }   	 
	     }	     
	}	
	
	@Test(groups = {"CustomerCare", "AdministraciónDeCasos"})
	public void TS15961_360_View_Ver_Equipo_Creador_En_Case_Usuario_Cambia_De_Equipo_Nuevo_Caso_Se_Modifica_El_Campo_Equipo_Del_Creador() throws ParseException{
		cc.elegircaso();
		cc.crearCaso("Fernandoasd Careeeeee");
		List <WebElement> ec = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite"));
		String equipoCreador = ec.get(11).getText();
		Actions action = new Actions(driver);
		action.moveToElement(ec.get(11)).doubleClick().build().perform();
		driver.findElement(By.xpath("//*[@id=\"00Nc0000001iLah\"]")).sendKeys("cambio de equipo");
		List <WebElement> save = driver.findElements(By.name("inlineEditSave"));
		for (WebElement x : save) {
			if (x.getAttribute("value").contains("Guardar")) {
				x.click();
				break;
			}
		}
		sleep(5000);
		List <WebElement> nec = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite"));
		String NequipoCreador = nec.get(11).getText();
		Assert.assertTrue(equipoCreador != NequipoCreador);
	}
}
