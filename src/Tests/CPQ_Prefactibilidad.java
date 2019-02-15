package Tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.SalesPage;

public class CPQ_Prefactibilidad extends TestBase {

	protected SalesPage Page;
	
	@BeforeClass
	public void init() {
		inicializarDriver();
		Page = new SalesPage(driver);
		Page.login("SIT");
		Page.irASalesCPQ();
	}
	
	@AfterClass
	public void exit() {
		cerrarTodo();
	}
	
	@BeforeMethod
	public void before() {
		Page.comprobarPrefactibilidad();
	}
	
	@AfterMethod
	public void after() {
		Page.cerrarPrefactibilidad();
	}
	
	@Test
	public void TS11813_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_campos_obligatorios_en_la_carga_de_domicilio() {
		List<WebElement> campos = Page.obtenerCamposObligatorios();
		List<String> camposObligatorios = obtenerElAtributoDeLosElementos("id", campos); 
		
		Assert.assertTrue(camposObligatorios.contains("State"));
		Assert.assertTrue(camposObligatorios.contains("City"));
		Assert.assertTrue(camposObligatorios.contains("Street"));
		Assert.assertTrue(camposObligatorios.contains("StreetNumber"));
		Assert.assertTrue(camposObligatorios.contains("PostalCode"));
		Assert.assertTrue(camposObligatorios.contains("HomeType"));
		Assert.assertTrue(camposObligatorios.contains("ZoneType"));
	}
	
	@Test
	public void TS11814_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_combinatoria_CASABARRIO_PRIVADO() {
		Page.tipoDeCasa("Casa");
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("Lot");
		
		Assert.assertTrue(esObligatorio(campo));
	}
	
	@Test
	public void TS11815_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_combinatoria_LOCAL_PARQUE_INDUSTRIAL() {
		Page.tipoDeCasa("Local");
		Page.tipoDeZona("Parque industrial");
		WebElement campo = Page.obtenerCampo("Lot");
		
		Assert.assertTrue(esObligatorio(campo));
	}
	
	@Test
	public void TS11816_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_campo_EDIFICIO_igual_TIPO_DE_EDIFICIO_y_NUMERO_DE_PISO() {
		Page.tipoDeCasa("Edificio");
		WebElement campo1 = Page.obtenerCampo("BuildingType");
		WebElement campo2 = Page.obtenerCampo("BuildingNumber");
		
		Assert.assertTrue(esObligatorio(campo1));
		Assert.assertTrue(esObligatorio(campo2));
	}

	@Test
	public void TS11817_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_combinatoria_EDIFICIO_BARRIO_PRIVADO() {
		Page.tipoDeCasa("Edificio");
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("BuildingNumber");
		
		Assert.assertTrue(esObligatorio(campo));
	}
	
	@Test
	public void TS11833_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Calle_es_menorigual_25_caracteres() {
		WebElement campo = Page.obtenerCampo("Street");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11834_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Calle_NO_mayor_25_caracteres() {
		WebElement campo = Page.obtenerCampo("Street");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11836_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_es_menorigual_7_caracteres() {
		WebElement campo = Page.obtenerCampo("StreetNumber");
		campo.sendKeys("1111111");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11837_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_NO_mayor_7_caracteres() {
		WebElement campo = Page.obtenerCampo("StreetNumber");
		campo.sendKeys("11111111");
		
		Assert.assertTrue(!esValido(campo));		
	}
	
	@Test
	public void TS11838_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Codigo_Postal_es_menorigual_4_digitos(){
		WebElement campo = Page.obtenerCampo("PostalCode");
		campo.sendKeys("1111");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11840_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Comentario_de_Domicilio_es_menorigual_255_caracteres() {
		WebElement campo = Page.obtenerCampo("Comments");
		for (int i = 0; i < 255; i++) {
			campo.sendKeys("a");
		}
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11841_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Comentario_de_Domicilio_NO_mayor_255_caracteres() {
		WebElement campo = Page.obtenerCampo("Comments");
		for (int i = 0; i < 256; i++) {
			campo.sendKeys("a");
		}
		
		Assert.assertTrue(!esValido(campo));	
	}

	@Test
	public void TS11849_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_CalleRio_de_atras_es_menorigual_25_caracteres() {
		WebElement campo = Page.obtenerCampo("Backstreet");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11850_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_CalleRio_de_atras_NO_mayor_25_caracteres() {
		WebElement campo = Page.obtenerCampo("Backstreet");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11853_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_1_es_menorigual_25_caracteres(){
		WebElement campo = Page.obtenerCampo("SidestreetA");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11854_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_1_NO_mayor_25_caracteres() {
		WebElement campo = Page.obtenerCampo("SidestreetA");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11857_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_2_es_mayor_25_caracteres() {
		WebElement campo = Page.obtenerCampo("SidestreetB");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11858_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Entre_CalleRio_2_NO_mayor_25_caracteres() {
		WebElement campo = Page.obtenerCampo("SidestreetB");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11861_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Descripcion_de_Zona_Cerrada_es_menorigual_25_caracteres() {
		WebElement campo = Page.obtenerCampo("Neighborhood");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11872_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_ingreso_de_numeros_positivos_en_el_campo_Numero_de_Piso(){
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("FloorNumber");
		campo.sendKeys("15");
		
		Assert.assertTrue(esValido(campo));			
	}

	@Test 
	public void TS11873_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_ingreso_de_numeros_negativos_en_el_campo_Numero_de_Piso(){
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("FloorNumber");
		campo.sendKeys("-7");
		
		Assert.assertTrue(esValido(campo));		
	}

	@Test
	public void TS11874_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_ingreso_de_numeros_decimales_en_el_campo_Numero_de_Piso() {
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("FloorNumber");
		campo.sendKeys("3.14");
		
		Assert.assertTrue(esValido(campo));
	}
	
	@Test
	public void TS11876_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Departamento_menorigual_5_caracteres() {
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("Department");
		campo.sendKeys("aaaaa");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11877_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Departamento_NO_mayor_5_caracteres(){
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("Department");
		campo.sendKeys("aaaaaa");
		
		Assert.assertTrue(!esValido(campo));	
	}

	@Test
	public void TS11878_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_EscaleraAla_es_menorigual_2_caracteres() {
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("Stair/Wing");
		campo.sendKeys("11");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11879_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_EscaleraAla_NO_mayor_2_caracteres() {
		Page.tipoDeCasa("Edificio");
		WebElement campo = Page.obtenerCampo("Stair/Wing");
		campo.sendKeys("111");
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11891_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Nombre_del_Establecimiento_es_menorigual_30_caracteres() {
		WebElement campo = Page.obtenerCampo("EstablishmentName");
		for (int i = 0; i < 30; i++) {
			campo.sendKeys("a");
		}
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11892_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Nombre_del_Establecimiento_NO_mayor_30_caracteres() {
		WebElement campo = Page.obtenerCampo("EstablishmentName");
		for (int i = 0; i < 31; i++) {
			campo.sendKeys("a");
		}
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11893_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Lote_es_menorigual_5_caracteres(){
		Page.tipoDeCasa("Casa");
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("Lot");
		campo.sendKeys("11111");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11894_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Lote_NO_mayor_5_caracteres() {
		Page.tipoDeCasa("Casa");
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("Lot");
		campo.sendKeys("111111");
		
		Assert.assertTrue(!esValido(campo));	
	}

	@Test
	public void TS11895_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Manzana_es_menorigual_5_caracteres() {
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("Block");
		campo.sendKeys("11111");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11896_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Manzana_NO_mayor_5_caracteres() {
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("Block");
		campo.sendKeys("111111");
		
		Assert.assertTrue(!esValido(campo));
	}

	@Test
	public void TS11897_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Calle_Interna_es_menorigual_25_caracteres(){
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("InnerStreet");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(esValido(campo));
	}
	
	@Test
	public void TS11898_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Calle_Interna_NO_mayor_25_caracteres() {
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("InnerStreet");
		campo.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		Assert.assertTrue(!esValido(campo));		
	}

	@Test
	public void TS11899_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_Interna_es_menorigual_7_caracteres() {
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("InnerStreetNumber");
		campo.sendKeys("1111111");
		
		Assert.assertTrue(esValido(campo));
	}

	@Test
	public void TS11900_CRM_Fase_2_SalesCPQ_Configuracion_DomicilioDeInstalacion_Verificar_longitud_del_campo_Altura_Interna_NO_mayor_7_caracteres() {
		Page.tipoDeZona("Barrio privado");
		WebElement campo = Page.obtenerCampo("InnerStreetNumber");
		campo.sendKeys("11111111");
		
		Assert.assertTrue(!esValido(campo));		
	}

}
