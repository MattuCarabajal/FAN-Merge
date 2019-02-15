package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import Pages.BasePage;
import Pages.Order;
import Pages.OrdersTab;
import Pages.Ta_CPQ;
import Pages.Ta_CPQ_Validate;
import Pages.setConexion;

public class ta_CPQ_Validate extends ta_CPQ{
	
	private WebDriver driver;
	
	Ta_CPQ_Validate validatePage;
	private String ngValidRequired = "ng-valid-required";
	private String ngInvalidRequired = "ng-invalid-required";
	private String ngInvalidMaxLength= "ng-invalid-maxlength";
	private String ngValid = "ng-valid";
	private String province = "Buenos Aires";
	private String locality = "BALCARCE";
	private String street = "Av. Gonzalez Chavez";
	private String streetNumber = "485";
	private String postalCode = "7620";
	private String  typeZone = "Urbano";
	
	@BeforeClass(groups = {"Fase2"})
	public void Init() throws Exception
	{
		this.driver = setConexion.setupEze();
		 wait = new WebDriverWait(driver, 10);
		//try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//@Override
	@BeforeMethod(groups = {"Fase2"})
	public void setup() throws Exception {
		//super.setup();
		
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		if (!driver.findElement(By.id("tsidLabel")).getText().equals("Ventas")){
			driver.findElement(By.id("tsidLabel")).click();
			driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u41000000QWha\"]")).click();
		}
		OrdersTab page1 = new OrdersTab(driver);
		page1.goToOrdersTab();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		page1.goToRecentOrder();
		Order page2 = new Order(driver);
		page2.goToTaCPQ();
		try {Thread.sleep(12000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		
		Ta_CPQ cart = new Ta_CPQ(driver);
		WebElement buttonValidate = null;
		
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cart.deleteAddedProducts();
		List<WebElement> divsProducts = cart.getDivsProducts();
		for (WebElement divProduct: divsProducts) {
			if (cart.requiresPrefactibility(divProduct)){
				buttonValidate = driver.findElement(By.cssSelector(".slds-button.slds-button--neutral.add-button"));
				break;
			}
		}
		
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		buttonValidate.click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage pagina = new BasePage (driver);
		driver.switchTo().frame(pagina.getFrameForElement(driver, By.id("State")));
		
		validatePage = new Ta_CPQ_Validate (driver);

	}
	
	public boolean verifyInputCharLimit (WebElement input, String value, int repetitions) {
		for (int i=0;i<repetitions;i++){
			input.sendKeys(value);
		}
		return (input.getAttribute("value").length() == repetitions);
	}
	
	@AfterMethod(groups = {"Fase2"})
	public void returnToSales() {
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.get("https://crm--sit.cs14.my.salesforce.com/801c0000000Ec64");
	      }
	
	@AfterClass(groups = "Fase2")
	public void tearDown() {
		driver.close();
	}
	
	@Test(groups = {"Fase2"})
	/**
	 * TODO: no está funcionado el click sobre el botón "Siguiente" para continuar y ver el resultado
	 * de la prefactibilidad.
	 */
	public void TS11816_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_campo_EDIFICIO_igual_TIPO_DE_EDIFICIO_y_NUMERO_DE_PISO() {
			validatePage.completeRequiredFields(province, locality, street, streetNumber, postalCode, typeZone);
			validatePage.completeWithTypeHomeEdificio("4º", "B");
			try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+validatePage.getNextButton().getLocation().y+")");
		    validatePage.getNextButton().click();
		    try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			List<WebElement> prefactibilityResult  = driver.findElements(By.xpath("//label[@for=\"PrefeasibilityResult\"]"));
			Assert.assertTrue (prefactibilityResult.size() > 0);	
	}
	
	/**
	 * Se verifica que el sistema muestra los campos obligatorios a completar 
	 * con el simbolo (*): Provincia, Localidad, Calle, Altura, Codigo Postal, 
	 * Tipo de Domicilio y Tipo de Zona.
	 * class should contain ng-invalid-required or ng-valid-required
	 */
	@Test(groups = {"Fase2"})
	public void TS11813_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_campos_obligatorios_en_la_carga_de_domicilio() {		
		Assert.assertTrue(validatePage.getProvinceInput().getAttribute("class").contains("ng-invalid-required"));
		Assert.assertTrue(validatePage.getLocalityInput().getAttribute("class").contains("ng-invalid-required"));
		Assert.assertTrue(validatePage.getStreetInput().getAttribute("class").contains("ng-invalid-required"));
		Assert.assertTrue(validatePage.getStreetNumberInput().getAttribute("class").contains("ng-invalid-required"));
		Assert.assertTrue(validatePage.getPostalCodeInput().getAttribute("class").contains("ng-invalid-required"));
		Assert.assertTrue(validatePage.getTypeZoneInput().getAttribute("class").contains("ng-invalid-required"));
		//Tipo de Domicilio es radio button, y el asterisco se muestra por separado en un span.
		List<WebElement> typeHomeAsteriskSpan = driver.findElements(By.xpath("//span[@class=\"vlc-asterix icon-v-asterix\"]"));
		Assert.assertTrue(typeHomeAsteriskSpan.size() > 0);		
	}
	
	/** Se verifica que el sistema solicita el ingreso de Lote al completar los campos 
	 * Tipo de Domicilio y Tipo de Zona con los valores CASA y BARRIO PRIVADO.
	 */
	@Test(groups = {"Fase2"})
	public void TS11814_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_combinatoria_CASABARRIO_PRIVADO() {
		validatePage.getTypeHomeCasa().click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement loteInput = driver.findElement(By.id("Lot"));
		Assert.assertTrue (loteInput.getAttribute("class").contains("ng-invalid-required"));
	}
	
	/** Se verifica que el sistema solicita el ingreso de TIPO DE EDIFICIO y 
	 * NUMERO DE PISO al completar el campo Tipo de Domicilio con el valor EDIFICIO.
	 * De acuerdo con Fabiana, el objetivo del caso es que al elegir esta combinacion
	 * aparezcan para completar los campos "Tipo de Edificio" y "Numero de Edificio",
	 * NO es obligatorio completarlos. 
	 */
	@Test(groups = {"Fase2"})
	public void TS11817_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_combinatoria_EDIFICIO_BARRIO_PRIVADO() {
		validatePage.getTypeHomeEdificio().click();
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		List<WebElement> buildingTypeSelect = driver.findElements(By.id("BuildingType"));
		List<WebElement> buildingNumberType = driver.findElements(By.id("BuildingNumber"));
		
		Assert.assertTrue (buildingTypeSelect.size() >0);
		Assert.assertTrue (buildingNumberType.size() >0);
	}
	
	/**
	 * Se verifica que el sistema requiere el ingreso de valor en el campo Lote 
	 * luego de ingresar LOCAL y PARQUE INDUSTRIAL en Tipo de Domicilio y 
	 * Tipo de Zona respectivamente.
	 * */
	@Test(groups = {"Fase2"})
	public void TS11815_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_combinatoria_LOCAL_PARQUE_INDUSTRIAL() {
		validatePage.getTypeHomeLocal().click();
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(6); //Parque industrial
		
		WebElement loteInput = driver.findElement(By.id("Lot"));
		Assert.assertTrue (loteInput.getAttribute("class").contains("ng-invalid-required"));
		
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de numeros decimales
	 *  en el campo Numero de Piso
	 */
	@Test(groups = {"Fase2"})
	public void TS11874_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_ingreso_de_numeros_decimales_en_el_campo_Numero_de_Piso() {
		validatePage.getTypeHomeEdificio().click();
		WebElement floorInput = driver.findElement (By.id("FloorNumber"));
		floorInput.sendKeys("3.14");
		Assert.assertEquals(floorInput.getAttribute("value"), "3.14");
		Assert.assertTrue(floorInput.getAttribute("class").contains("ng-valid-required"));
	}
	
	/** Se verifica que el sistema permite el ingreso de numeros negativos 
	 * en el campo Numero de Piso
	 */
	@Test(groups = {"Fase2"}) 
	public void TS11873_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_ingreso_de_numeros_negativos_en_el_campo_Numero_de_Piso(){
		validatePage.getTypeHomeEdificio().click();
		WebElement floorInput = driver.findElement (By.id("FloorNumber"));
		floorInput.sendKeys("-7");
		Assert.assertEquals(floorInput.getAttribute("value"), "-7");
		Assert.assertTrue(floorInput.getAttribute("class").contains("ng-valid-required"));
		
	}
	
	/** Se verifica que el sistema permite el ingreso de numeros positivos 
	 * en el campo Numero de Piso.
	 */
	@Test(groups = {"Fase2"})
	public void TS11872_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_ingreso_de_numeros_positivos_en_el_campo_Numero_de_Piso(){
		validatePage.getTypeHomeEdificio().click();
		WebElement floorInput = driver.findElement (By.id("FloorNumber"));
		floorInput.sendKeys("15");
		Assert.assertEquals(floorInput.getAttribute("value"), "15");
		Assert.assertTrue(floorInput.getAttribute("class").contains("ng-valid-required"));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta 7 caracteres 
	 * en el campo Altura.
	 */
	@Test(groups = {"Fase2"})
	public void TS11836_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_es_menorigual_7_caracteres() {
		for (int i=0; i<7; i++) {
			validatePage.getStreetNumberInput().sendKeys("1");
		}
		Assert.assertEquals(validatePage.getStreetNumberInput().getAttribute("value"), "1111111");
		Assert.assertTrue(validatePage.getStreetNumberInput().getAttribute("class").contains("ng-valid-required"));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta
	 *  7 caracteres en el campo Altura Interna.
	 */
	@Test(groups = {"Fase2"})
	public void TS11899_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_Interna_es_menorigual_7_caracteres() {
		validatePage.getTypeHomeCasa();
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement innerStreetNumberInput = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("InnerStreetNumber"))));
		for (int i=0; i<7; i++) {
			innerStreetNumberInput.sendKeys("1");
		}
		Assert.assertEquals(innerStreetNumberInput.getAttribute("value"), "1111111");
		Assert.assertTrue(innerStreetNumberInput.getAttribute("class").contains("ng-valid-required"));				
	}
	
	/**
	 * Se verifica que el sistema NO permite el ingreso de mas de 7 caracteres 
	 * para el campo Altura Interna.
	 */
	@Test(groups = {"Fase2"})
	public void TS11900_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_Interna_NO_mayor_7_caracteres() {
		validatePage.getTypeHomeCasa();
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement innerStreetNumberInput = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("InnerStreetNumber"))));
		for (int i=0; i<8; i++) {
			innerStreetNumberInput.sendKeys("1");
		}
		Assert.assertEquals(innerStreetNumberInput.getAttribute("value"), "11111111");
		Assert.assertTrue(innerStreetNumberInput.getAttribute("class").contains("ng-invalid-maxlength"));
	}
	
	@Test(groups = {"Fase2"})
	public void TS11837_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_NO_mayor_7_caracteres() {
		for (int i=0; i<8; i++) {
			validatePage.getStreetNumberInput().sendKeys("1");
		}
		Assert.assertEquals(validatePage.getStreetNumberInput().getAttribute("value").length(), 8);
		Assert.assertTrue(validatePage.getStreetNumberInput().getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso
	 *  de hasta 25 caracteres de en el campo Calle Interna.
	 */
	@Test(groups = {"Fase2"})
	public void TS11897_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Calle_Interna_es_menorigual_25_caracteres(){
		validatePage.getTypeHomeCasa();
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement innerStreetInput = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("InnerStreet"))));
		for (int i=0; i<25; i++) {
			innerStreetInput.sendKeys("a");
		}
		Assert.assertEquals(innerStreetInput.getAttribute("value").length(), 25);
		Assert.assertTrue(innerStreetInput.getAttribute("class").contains(ngValid));
	}
	
	/**
	 * Se verifica que el sistema NO permite el ingreso de mas de 25 caracteres 
	 * para el campo Calle Interna.
	 */
	@Test(groups = {"Fase2"})
	public void TS11898_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Calle_Interna_NO_mayor_25_caracteres() {
		validatePage.getTypeHomeCasa().click();
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement innerStreetInput = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("InnerStreet"))));
		for (int i=0; i<26; i++) {
			innerStreetInput.sendKeys("a");
		}
		Assert.assertEquals(innerStreetInput.getAttribute("value").length(), 26);
		Assert.assertTrue(innerStreetInput.getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	/** 
	 * Se verifica que el sistema permite el ingreso de hasta 4 digitos 
	 * para el campo Codigo Postal.
	 */
	@Test(groups = {"Fase2"})
	public void TS11838_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Codigo_Postal_es_menorigual_4_digitos(){
		for (int i=0; i<4; i++) {
			validatePage.getPostalCodeInput().sendKeys("1");
		}
		Assert.assertEquals(validatePage.getPostalCodeInput().getAttribute("value").length(), 4);
		Assert.assertTrue(validatePage.getPostalCodeInput().getAttribute("class").contains(ngValid));
	}
	
	
	/*
	 * Se verifica que el sistema permite el ingreso de 
	 * hasta 255 caracteres en el campo Comentario de Domicilio.
	 */
	@Test(groups = {"Fase2"})
	public void TS11840_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Comentario_de_Domicilio_es_menorigual_255_caracteres() {
		for (int i=0; i<255; i++) {
			validatePage.getCommentsTextArea().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getCommentsTextArea().getAttribute("value").length(), 255);
		Assert.assertTrue(validatePage.getCommentsTextArea().getAttribute("class").contains(ngValid));
	}
	
	@Test(groups = {"Fase2"})
	public void TS11841_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Comentario_de_Domicilio_NO_mayor_255_caracteres() {
		for (int i=0; i<256; i++) {
			validatePage.getCommentsTextArea().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getCommentsTextArea().getAttribute("value").length(), 256);
		Assert.assertTrue(validatePage.getCommentsTextArea().getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta 5 caracteres 
	 * de texto libre en el campo Departamento.
	 */
	@Test(groups = {"Fase2"})
	public void TS11876_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Departamento_menorigual_5_caracteres() {
		validatePage.getTypeHomeEdificio().click();
		WebElement departmentInput = driver.findElement(By.id("Department"));
		for (int i=0; i<5; i++) {
			departmentInput.sendKeys("a");
		}
		Assert.assertEquals(departmentInput.getAttribute("value").length(), 5);
		Assert.assertTrue(departmentInput.getAttribute("class").contains(ngValid));
		
	}
	
	@Test(groups = {"Fase2"})
	public void TS11877_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Departamento_NO_mayor_5_caracteres(){
		validatePage.getTypeHomeEdificio().click();
		WebElement departmentInput = driver.findElement(By.id("Department"));
		for (int i=0; i<6; i++) {
			departmentInput.sendKeys("a");
		}
		Assert.assertEquals(departmentInput.getAttribute("value").length(), 6);
		Assert.assertTrue(departmentInput.getAttribute("class").contains(ngInvalidMaxLength));
		
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de 
	 * hasta 25 caracteres para el campo Descripcion de Calle.
	 */
	@Test(groups = {"Fase2"}) 
	public void TS11833_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Calle_es_menorigual_25_caracteres() {
		for (int i=0; i<25; i++) {
			validatePage.getStreetInput().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getStreetInput().getAttribute("value").length(), 25);
		Assert.assertTrue(validatePage.getStreetInput().getAttribute("class").contains(ngValid));
		
	}
	
	/**
	 * Se verifica que el sistema NO permite el ingreso de mas de 25 caracteres
	 *  en el campo Descripcion de Calle.
	 */
	@Test(groups = {"Fase2"})
	public void TS11834_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Calle_NO_mayor_25_caracteres() {
		for (int i=0; i<26; i++) {
			validatePage.getStreetInput().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getStreetInput().getAttribute("value").length(), 26);
		Assert.assertTrue(validatePage.getStreetInput().getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta 25 caracteres 
	 * para el campo Descripcion de Calle/Rio de atras.
	 */
	@Test(groups = {"Fase2"})
	public void TS11849_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_CalleRio_de_atras_es_menorigual_25_caracteres() {
		for (int i=0; i<25;i ++) {
			validatePage.getBackStreet().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getBackStreet().getAttribute("value").length(), 25);
		Assert.assertTrue(validatePage.getBackStreet().getAttribute("class").contains(ngValid));
		
	}

	@Test(groups = {"Fase2"})
	public void TS11850_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_CalleRio_de_atras_NO_mayor_25_caracteres() {
		for (int i=0; i<26;i ++) {
			validatePage.getBackStreet().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getBackStreet().getAttribute("value").length(), 26);
		Assert.assertTrue(validatePage.getBackStreet().getAttribute("class").contains(ngInvalidMaxLength));
		
	}
	
	@Test(groups = {"Fase2"})
	public void TS11853_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_1_es_menorigual_25_caracteres(){
		for (int i=0; i<25;i ++) {
			validatePage.getBetweenStreet1().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getBetweenStreet1().getAttribute("value").length(), 25);
		Assert.assertTrue(validatePage.getBetweenStreet1().getAttribute("class").contains(ngValid));
		
	}
	
	@Test(groups = {"Fase2"})
	public void TS11854_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_1_NO_mayor_25_caracteres() {
		for (int i=0; i<26;i ++) {
			validatePage.getBetweenStreet1().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getBetweenStreet1().getAttribute("value").length(), 26);
		Assert.assertTrue(validatePage.getBetweenStreet1().getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	@Test(groups = {"Fase2"})
	public void TS11857_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_2_es_mayor_25_caracteres() {
		for (int i=0; i<25;i ++) {
			validatePage.getBetweenStreet2().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getBetweenStreet2().getAttribute("value").length(), 25);
		Assert.assertTrue(validatePage.getBetweenStreet2().getAttribute("class").contains(ngValid));
		
	}
	
	@Test(groups = {"Fase2"})
	public void TS11858_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_2_NO_mayor_25_caracteres() {
		for (int i=0; i<26;i ++) {
			validatePage.getBetweenStreet2().sendKeys("a");
		}
		Assert.assertEquals(validatePage.getBetweenStreet2().getAttribute("value").length(), 26);
		Assert.assertTrue(validatePage.getBetweenStreet2().getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	//POR FAVOR
	//PROBAR LOS ULTIMOS TEST CASES DE ENTRECALLES RIO 1 Y 2

	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta 25 caracteres para el campo 
	 * Descripcion de Tipo de Domicilio con los valores CASA, LOCAL o EDIFICIO.
	 */
	@Test(groups = {"Fase2"})
	public void TS11844_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Tipo_de_Domicilio_es_menorigual_25_caracteres_y_posibles_valores() {
		//tomar los datos del test 11834
		
	}
	
	
	
	public void TSXX() {
		Assert.assertTrue(verifyInputCharLimit(validatePage.getBetweenStreet2(), "a",26));
		Assert.assertEquals(validatePage.getBetweenStreet2().getAttribute("value").length(), 26);
		Assert.assertTrue(validatePage.getBetweenStreet2().getAttribute("class").contains(ngInvalidMaxLength));
	}

	public void TS11829_CRM_Fase_2_SalesCPQ_Configuaracion_DomicilioDeInstalacion_Verificar_que_el_campo_Localidad_sea_un_Desplegable() {
		
	}
	
	/*
	 * Se verifica que el sistema permite el ingreso de hasta 
	 * 25 caracteres para el campo Descripcion de Zona Cerrada
	 */
	@Test(groups = {"Fase2"})
	public void TS11861_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Zona_Cerrada_es_menorigual_25_caracteres() {
		Assert.assertTrue(verifyInputCharLimit(validatePage.getNeighbourhood(), "a", 25));
		Assert.assertTrue(validatePage.getNeighbourhood().getAttribute("class").contains(ngValid));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta
	 *  2 caracteres en el campo Escalera/Ala
	 */
	@Test(groups = {"Fase2"})
	public void TS11878_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_EscaleraAla_es_menorigual_2_caracteres() {
		validatePage.getTypeHomeEdificio().click();
		WebElement stairWing = driver.findElement(By.id("Stair/Wing"));
		Assert.assertTrue(verifyInputCharLimit(stairWing, "1", 2));
		Assert.assertTrue(stairWing.getAttribute("class").contains(ngValid));
	}
	
	/**
	 * Se verifica que el sistema NO permite el ingreso de mas de 
	 * 5 caracteres para el campo Departamento
	 */
	@Test(groups = {"Fase2"})
	public void TS11879_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_EscaleraAla_NO_mayor_5_caracteres() {
		validatePage.getTypeHomeEdificio().click();
		WebElement department = driver.findElement(By.id("Department"));
		Assert.assertTrue(verifyInputCharLimit(department, "1", 6));
		Assert.assertTrue(department.getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta 5 caracteres
	 *  de texto libre en el campo Lote.
	 */
	@Test(groups = {"Fase2"})
	public void TS11893_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Lote_es_menorigual_5_caracteres(){
		validatePage.getTypeHomeCasa().click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement loteInput = driver.findElement(By.id("Lot"));
		Assert.assertTrue(verifyInputCharLimit(loteInput, "1", 5));
		Assert.assertTrue(loteInput.getAttribute("class").contains(ngValid));
	}
	
	/**Se verifica que el sistema NO permite el ingreso de mas de 5 caracteres para el campo Lote*/
	@Test(groups = {"Fase2"})
	public void TS11894_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Lote_NO_mayor_5_caracteres() {
		validatePage.getTypeHomeCasa().click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado
		
		WebElement loteInput = driver.findElement(By.id("Lot"));
		Assert.assertTrue(verifyInputCharLimit(loteInput, "1", 6));
		Assert.assertTrue(loteInput.getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	/**
	 * Se verifica que el sistema permite el ingreso de hasta 5 caracteres de en el campo Manzana
	 */
	@Test(groups = {"Fase2"})
	public void TS11895_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Manzana_es_menorigual_5_caracteres() {
		validatePage.getTypeHomeCasa().click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado

		WebElement blockInput = driver.findElement(By.id("Block"));
		Assert.assertTrue(verifyInputCharLimit(blockInput, "1", 5));
		Assert.assertTrue(blockInput.getAttribute("class").contains(ngValid));
		
	}

	/**
	 * Se verifica que el sistema NO permite el ingreso de mas de 5 caracteres 
	 * para el campo Manzana.
	 */
	@Test(groups = {"Fase2"})
	public void TS11896_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Manzana_NO_mayor_5_caracteres() {
		validatePage.getTypeHomeCasa().click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select typeZone = new Select (validatePage.getTypeZoneInput());
		typeZone.selectByIndex(3); //Barrio privado

		WebElement blockInput = driver.findElement(By.id("Block"));
		Assert.assertTrue(verifyInputCharLimit(blockInput, "1", 6));
		Assert.assertTrue(blockInput.getAttribute("class").contains(ngInvalidMaxLength));
	}
	
	/**
	 *Se verifica que el sistema permite el ingreso de hasta 30 caracteres 
	 *en el campo Nombre del Establecimiento 
	 */
	@Test(groups = {"Fase2"})
	public void TS11891_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Nombre_del_Establecimiento_es_menorigual_30_caracteres() {
		Assert.assertTrue(verifyInputCharLimit(validatePage.getEstablishmentName(), "a", 30));
		Assert.assertTrue(validatePage.getEstablishmentName().getAttribute("class").contains(ngValid));
	}
	
	/**
	 * Se verifica que el sistema NO permite el ingreso de mas de 30 caracteres
	 *  para el campo Nombre del Establecimiento
	 */
	@Test(groups = {"Fase2"})
	public void TS11892_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Nombre_del_Establecimiento_NO_mayor_30_caracteres() {
		Assert.assertTrue(verifyInputCharLimit(validatePage.getEstablishmentName(), "a", 31));
		Assert.assertTrue(validatePage.getEstablishmentName().getAttribute("class").contains(ngInvalidMaxLength));
	}
	

	
	

}
