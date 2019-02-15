package Tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.CustomerCare;
import Pages.SCP;
import Pages.setConexion;

public class SCPContextoSectorial extends TestBase {

	private WebDriver driver;
	private SCP scp;
	private static String downloadPath = "C:\\Users\\Florangel\\Downloads";
	
	
	@BeforeClass (alwaysRun = true)
	public void Init() throws Exception {
		driver = setConexion.setupEze();
		scp = new SCP(driver);
		loginSCPConPermisos(driver);
		sleep(5000);
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setUp() throws Exception {
		sleep(3000);
		scp.clickOnTabByName("cuentas");
		scp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		sleep(5000);
	}
	
	@AfterMethod (alwaysRun = true)
	public void after(){
		sleep(3000);
		driver.switchTo().defaultContent();
		sleep(5000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("home_Tab")).getLocation().y+")");
		driver.findElement(By.id("home_Tab")).click();
	}
	
	@AfterClass (alwaysRun = true)
	public void teardown() {
		driver.quit();
		sleep(5000);
	}
	
	
	@Test (groups = "SCP", priority = 3)
	public void TS112613_Cronograma_de_cuenta_Agregar_Vencimiento_Contrato_del_Servicio() {
		scp.moveToElementOnAccAndClick("cuartoTitulo", 1);
		List <WebElement> checkbox = driver.findElements(By.className("checkboxFiltroTimeLine"));
		checkbox.get(1).click();
		driver.findElement(By.id("j_id0:j_id91:j_id111")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.className("tl-message-full")).isDisplayed());		
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112626_Cronograma_de_cuenta_Filtros_Vencimiento_Contrato_del_Servicio(){
		scp.moveToElementOnAccAndClick("cuartoTitulo", 1);
		List <WebElement> checkbox = driver.findElements(By.className("checkboxFiltroTimeLine"));
		checkbox.get(1).click();
		driver.findElement(By.id("j_id0:j_id91:j_id111")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.className("tl-timenav-slider-background")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112627_Cronograma_de_Cuenta_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("cuartoTitulo", 1);
		List <WebElement> checkbox = driver.findElements(By.className("checkboxFiltroTimeLine"));
		checkbox.get(1).click();
		driver.findElement(By.id("j_id0:j_id91:j_id111")).click();
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.className("tl-message-full")).isDisplayed() && driver.findElement(By.className("tl-timenav-slider-background")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112594_Contexto_Sectorial_Ingreso_Desde_Acerca_del_cliente() {
		scp.moveToElementOnAccAndClick("primerTitulo", 1);
		Assert.assertTrue(driver.findElement(By.id("hidden-Con")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-M\u00e9t")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Pla")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Cad")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Ten")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Cas")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112595_Contexto_Sectorial_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("primerTitulo", 1);		
		Assert.assertTrue(driver.findElement(By.id("hidden-Con")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-M\u00e9t")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Pla")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Cad")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Ten")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-Cas")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112633_Estrategia_de_Crecimiento_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 5);
		boolean check=true;
	    String[] datosOp = {"t\u00edtulo", "descripci\u00f3n", "tipo", "origen", "negocio potencial"};
	    List<String> titleTabla = new ArrayList<String>();
	    WebElement oportunidad = driver.findElement(By.id("mainTable")).findElement(By.className("headerRow"));
	    List<WebElement> composicion= oportunidad.findElements(By.tagName("th"));	    
	    for(WebElement a : composicion) {
	      titleTabla.add(a.getText().toLowerCase());
	    }	    
	    for(String a:datosOp) {
	    	if(!(titleTabla.contains(a)))
	    		check=false;
	    }
	    Assert.assertTrue(check);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112678_Hitos_Relevantes_Nuevo_Hito_Relevante() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 3);
		WebElement element = driver.findElement(By.cssSelector(".data2Col.last")).findElement(By.cssSelector(".btn.btn-default.btn-sm"));
		element.click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.className("modal-content")).isDisplayed());
		sleep(2000);
		driver.findElement(By.className("modal-footer")).findElement(By.cssSelector(".btn.btn-default")).click();
		
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112742_Negocio_del_Cliente_Exportar_a_Excel() {
		scp.moveToElementOnAccAndClick("primerTitulo", 2);
		String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "exportar a excel");
		sleep(8000);
		usuario = usuario.replace(' ', '_');
		usuario = usuario.concat("-Negocio_del_Cliente.xls");
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112744_Negocio_del_cliente_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("primerTitulo", 2);
		Assert.assertTrue(driver.findElement(By.id("hidden-descripcionCliente")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-strategicContext")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-strategicIniciative")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-mainCompetitors")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hidden-csat")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112745_Negocio_del_Cliente_Principales_competidores_del_cliente() {
		scp.moveToElementOnAccAndClick("primerTitulo", 2);
		driver.findElement(By.id("hidden-mainCompetitors")).click();
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".hiddenTable.hidden-mainCompetitors")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112802_Share_of_Wallet_Exportar_a_Excel() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 1);
		String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "exportar a excel");
		sleep(8000);
		usuario = usuario.replace(' ', '_');
		usuario = usuario.concat("-Share_of_Wallet.xls");
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112804_Share_of_Wallet_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 1);	
		boolean check = true;
	    String[] datosOp = {"ytd", "a\u00f1o anterior", "a\u00f1o anterior -1"};
	    List<String> titleTabla = new ArrayList<String>();
	    WebElement oportunidad = driver.findElement(By.id("j_id0:Form:pageContent")).findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
	    List<WebElement> composicion= oportunidad.findElements(By.tagName("th"));	    
	    for(WebElement a : composicion) {
	      titleTabla.add(a.getText().toLowerCase());
	    }	    
	    for(String a : datosOp) {
	    	if(!(titleTabla.contains(a)))
	    		check = false;
	    }
	    Assert.assertTrue(check);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112703_Mosaico_de_Relacionamiento_General_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 4);
		boolean check = true;
	    String[] datosOp = {"rol", "actitud", "autoridad", "influencia", "relacionamiento con la competencia", "generaci\u00f3n"};
	    List<String> titleTabla = new ArrayList<String>();
	    WebElement oportunidad = driver.findElement(By.id("j_id0:j_id139")).findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
	    List<WebElement> composicion = oportunidad.findElements(By.tagName("th"));	    
	    for(WebElement a : composicion) {
	      titleTabla.add(a.getText().toLowerCase());
	    }	    
	    for(String a:datosOp) {
	    	if(!(titleTabla.contains(a)))
	    		check = false;
	    }
	    Assert.assertTrue(check);
	}
		
	@Test (groups = "SCP", priority = 3)
	public void TS112721_Mosaico_de_Relacionamiento_por_Oportunidad_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 3);
		boolean check = true;
	    String[] datosOp = {"nombre de la oportunidad", "importe", "probabilidad (%)"};
	    List <String> titleTabla = new ArrayList<String>();
	    WebElement oportunidad = driver.findElement(By.id("j_id0:pageContent")).findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"));
	    List<WebElement> composicion = oportunidad.findElements(By.tagName("th"));	    
		for (WebElement a : composicion) {
			titleTabla.add(a.getText().toLowerCase());
		}	    
		for (String a : datosOp) {
			if (!(titleTabla.contains(a)))
				check = false;
		}
	    Assert.assertTrue(check);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112753_Opportunity_Snapshot_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		boolean check = true;
		String[] datosOp = {"nombre de la oportunidad", "importe", "probabilidad (%)", "etapa"};
		List <String> titleTabla = new ArrayList<String>();
		WebElement oportunidad = driver.findElement(By.id("j_id0:pageContent")).findElement(By.id("mainTable_wrapper"));
		List <WebElement> composicion = oportunidad.findElements(By.tagName("th"));	    
		for (WebElement a : composicion) {
			titleTabla.add(a.getText().toLowerCase());
		}    
		for (String a : datosOp) {
			if (!(titleTabla.contains(a)))
				check = false;
		}
		Assert.assertTrue(check);	  
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112754_Opportunity_Snapshot_Ir_al_Snapshot() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir al snapshot");
		sleep(5000);
		WebElement oportunidad = driver.findElement(By.className("panel-body")).findElement(By.tagName("h2"));
		Assert.assertTrue(oportunidad.getText().contains("Oportunidad:"));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112756_Opportunity_Snapshot_Nombre_de_la_oportunidad() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		driver.findElement(By.xpath("//*[@id=\"mainTable\"]/tbody/tr[1]/td[2]/a")).click();
		sleep(5000);
		Assert.assertTrue(driver.findElement(By.id("bodyCell")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112763_Organigrama_y_mapa_de_influencia_Descargar_Imagen() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver organigrama / mapa de influencia");
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm.generateImg")), "contains", "descargar imagen");
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112766_Organigrama_y_mapa_de_Influencia_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		Assert.assertTrue(driver.findElement(By.className("jOrgChart")).isDisplayed());	
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112765_Organigrama_y_mapa_de_influencia_Guardar_cambios() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver organigrama / mapa de influencia");
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm.save")), "contains", "guardar cambios");
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112764_Organigrama_y_mapa_de_influencia_Guardar() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver organigrama / mapa de influencia");	
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm.save")), "contains", "guardar cambios");
	}
	
	@Test (groups = "SCP", priority = 4)
	public void TS112592_Contexto_Sectorial_Exportar_a_Excel() {
		scp.moveToElementOnAccAndClick("primerTitulo", 1);
		String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "exportar a excel");
		sleep(8000);
		usuario = usuario.replace(' ', '_');
		usuario = usuario.concat("-Contexto_Sectorial.xls");
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112751_Opportunity_Snapshot_Chatter_contextualizado_Leer_comentario_escrito_con_otro_usuario() {
		scp.Desloguear_Loguear("isabel");
		scp.Desloguear_Loguear_Comentar("permisos", "isabel", "comentario opportunity", "tercerTitulo", 4);
		scp.Desloguear_Loguear("permisos");
		sleep(5000);
		scp.clickOnTabByName("cuentas");
		sleep(7000);
		scp.clickEnCuentaPorNombre("Florencia Di Ci");
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		scp.validarcomentarioajeno("comentario opportunity");
	}
	
	@Test (groups = "SCP",priority = 3)
	public void TS112755_Opportunity_Snapshot_Ir_al_Snapshot_Ingreso() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		List <WebElement> snapshot = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		snapshot.get(0).click();
		sleep(10000);
		List <WebElement> element = driver.findElements(By.cssSelector(".pbSubheader.brandTertiaryBgr.first.tertiaryPalette"));
		Assert.assertTrue(element.get(0).getText().contains("Campos de la Oportunidad"));
		Assert.assertTrue(element.get(1).getText().contains("Soluciones Para el Sector"));
		Assert.assertTrue(element.get(2).getText().contains("Value Drivers"));
		Assert.assertTrue(element.get(3).getText().contains("Propuesta de Valor"));
		Assert.assertTrue(element.get(4).getText().contains("Mosaico de Relacionamiento por Oportunidad"));
		Assert.assertTrue(element.get(5).getText().contains("Criterios de Decisi\u00f3n por Oportunidad"));		
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112757_Opportunity_Snapshot_Search() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		String a = "dulce d\u00eda cafeter\u00eda";
		driver.findElement(By.xpath("//*[@id=\"mainTable_filter\"]/label/input")).sendKeys(a);
		WebElement element = driver.findElement(By.xpath("//*[@id=\"mainTable\"]/tbody/tr[1]/td[2]"));
		Assert.assertTrue(element.getText().toLowerCase().contains(a));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112759_Opportunity_Snapshot_Ver_video() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Assert.assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());
		driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112762_Organigrama_y_mapa_de_influencia_Chatter_contextualizado_Leer_comentario_escrito_con_otro_usuario() {
		scp.Desloguear_Loguear("isabel");
		scp.Desloguear_Loguear_Comentar("permisos", "isabel", "comentario opportunity", "primerTitulo", 3);
		scp.Desloguear_Loguear("permisos");
		sleep(5000);
		scp.clickOnTabByName("cuentas");
		sleep(7000);
		scp.clickEnCuentaPorNombre("Florencia Di Ci");
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		scp.validarcomentarioajeno("comentario opportunity");
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112768_Organigrama_y_mapa_de_influencia_Search() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		String a = "lucas";
		driver.findElement(By.xpath("//*[@id=\"mainTable_filter\"]/label/input")).sendKeys(a);
		sleep(3000);
		Assert.assertTrue(driver.findElement(By.className("odd")).getText().toLowerCase().contains(a));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112769_Organigrama_y_mapa_de_influencia_Ver_Video() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Assert.assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());
		driver.close();
	    driver.switchTo().window(tabs2.get(0));		
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112771_Organigrama_y_mapa_de_influencia_zoom() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		Assert.assertTrue(driver.findElement(By.id("zoomOut")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("zoomIn")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 4)
	public void TS112588_Contexto_Sectorial_Chatter_contextualizado_Leer_comentario_escrito_con_otro_usuario() {
		scp.Desloguear_Loguear("isabel");
		scp.Desloguear_Loguear_Comentar("permisos", "isabel", "comentario opportunity", "primerTitulo", 1);
		scp.Desloguear_Loguear("permisos");
		sleep(5000);
		scp.clickOnTabByName("cuentas");
		sleep(7000);
		scp.clickEnCuentaPorNombre("Florencia Di Ci");
		scp.moveToElementOnAccAndClick("primerTitulo", 1);
		scp.validarcomentarioajeno("comentario opportunity");
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112801_Share_of_Wallet_Chatter_contextualizado_Leer_comentario_escrito_con_otro_usuario() {
		scp.Desloguear_Loguear("isabel");
		scp.Desloguear_Loguear_Comentar("permisos", "isabel", "comentario opportunity", "segundoTitulo", 1);
		scp.Desloguear_Loguear("permisos");
		sleep(5000);
		scp.clickOnTabByName("cuentas");
		sleep(7000);
		scp.clickEnCuentaPorNombre("Florencia Di Ci");
		scp.moveToElementOnAccAndClick("segundoTitulo", 1);
		scp.validarcomentarioajeno("comentario opportunity");
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112805_Share_of_Wallet_Ver_Video() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 1);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Assert.assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());
		driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112803_Share_of_Wallet_Guardar() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 1);
		Actions action = new Actions(driver);
		List <WebElement> element = driver.findElements(By.className("inlineEditWrite"));
		String a = "250";
		action.doubleClick(element.get(0)).perform();
		driver.findElement(By.xpath("//*[@id=\"j_id0_Form_j_id122\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"j_id0_Form_j_id122\"]")).sendKeys(a);
		driver.findElement(By.xpath("//*[@id=\"j_id0_Form_j_id122\"]")).sendKeys("\uE007");
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "guardar");
		sleep(5000);		
		int cant = 0;
		while (cant < 2) {
			try {
				Assert.assertTrue(element.get(0).getText().contains(a));
				break;
			} catch(org.openqa.selenium.StaleElementReferenceException e) {}
			cant++;
		}
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112748_Negocio_del_Cliente_Ver_Video() {
		scp.moveToElementOnAccAndClick("primerTitulo", 2);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Assert.assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());
		driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112681_Hitos_Relevantes_Ver_Video() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 3);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Assert.assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());
		driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112679_Hitos_Relevantes_Nuevo_Hito_Relevante_Agregar() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 3);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "nuevo hito relevante");
		sleep(5000);
		String c = "nuevo test";
		List <WebElement> a = driver.findElements(By.className("resetHito"));
		a.get(0).sendKeys(c);
		a.get(1).click();
		CustomerCare cc = new CustomerCare(driver);
		cc.setSimpleDropdown(a.get(1), "Otro");
		sleep(2000);
		driver.findElement(By.className("dateFormat")).click();
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		sleep(5000);
		List <WebElement> b = driver.findElements(By.className("data2Col"));
		Assert.assertTrue(b.get(1).getText().contains(c));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112680_Hitos_Relevantes_Nuevo_Hito_Relevante_Cancelar() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 3);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "nuevo hito relevante");
		sleep(5000);
		String c = "cancelacion de hito";
		List <WebElement> a = driver.findElements(By.className("resetHito"));
		a.get(0).sendKeys(c);
		a.get(1).click();
		CustomerCare cc = new CustomerCare(driver);
		cc.setSimpleDropdown(a.get(1), "Otro");
		driver.findElement(By.className("dateFormat")).click();
		sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"myModalHito\"]/div[2]/div/div[3]/button[1]")).click();
		sleep(5000);
		List <WebElement> b = driver.findElements(By.className("data2Col"));
		Assert.assertTrue(!(b.get(1).getText().contains(c)));
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112677_Hitos_Relevantes_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 3);
		boolean check = true;
	    String[] datosOp = {"Descripci\u00f3n", "Fecha", "Categor\u00eda"};
	    List<String> titleTabla = new ArrayList<String>();
	    WebElement oportunidad = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id89:hitosRelevantes:j_id97\"]/div[2]/table/tbody/tr[2]/td/table/thead"));
	    List<WebElement> composicion= oportunidad.findElement(By.tagName("tr")).findElements(By.tagName("th"));	    
	    for(WebElement a : composicion) {
	      titleTabla.add(a.getText());
	    }	    
	    for(String a : datosOp) {
	    	if(!(titleTabla.contains(a)))
	    		check = false;
	    }
	    Assert.assertTrue(check);
	    Assert.assertTrue(driver.findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).getAttribute("value").equals("Borrar"));
	    
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112750_Opportunity_Snapshot_Chatter_contextualizado_Escribir_comentario() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		String a = "comentario de opportunity snapshot";
		scp.comentarycompartir(a);
		scp.validarcomentario(a);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112800_Share_of_Wallet_Chatter_contextualizado_Escribir_comentario() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 1);
		String a = "comentario de share of wallet";
		scp.comentarycompartir(a);
		scp.validarcomentario(a);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112761_Organigrama_y_mapa_de_influencia_Chatter_contextualizado_Escribir_comentario() {
		scp.moveToElementOnAccAndClick("primerTitulo", 3);
		String a = "comentario";
		scp.comentarycompartir(a);
		scp.validarcomentario(a);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112674_Hitos_Relevantes_Chatter_contextualizado_Escribir_comentario() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 3);
		String a = "comentario de hitos relevantes";
		scp.comentarycompartir(a);
		scp.validarcomentario(a);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112628_Estrategia_de_Crecimiento_Agregar_Negocio_Potencial() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 5);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "agregar negocio potencial");
		sleep(5000);
		String a = "nuevo negocio de prueba";
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id123:j_id219\"]")).sendKeys(a);
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();
		List <WebElement> tabla = driver.findElements(By.className("ScrollingDiv"));
		Assert.assertTrue(tabla.get(1).getText().contains("nuevo negocio de prueba"));
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112629_Estrategia_de_Crecimiento_Chatter_contextualizado_Escribir_comentario() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 5);
		String a = "comentario de estrategia de crecimiento";
		scp.comentarycompartir(a);
		scp.validarcomentario(a);
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112700_Mosaico_de_Relacionamiento_General_Descargar_imagen() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 4);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver mosaico ordenado por rol");
		sleep(5000);
		List <WebElement> b = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		boolean a = false;
		for (WebElement x : b) {
			if (x.getText().contains("Descargar Imagen"))
				a = true;
		}
		Assert.assertTrue(a);
	}
	
	//@Test (groups = "SCP", priority = 2)
	public void TS112716_Mosaico_de_Relacionamiento_General_Ver_Video() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 4);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
	    sleep(10000);
	    driver.switchTo().frame(cambioFrame(driver, By.className("ytp-cued-thumbnail-overlay")));
		Assert.assertTrue(driver.findElement(By.className("ytp-cued-thumbnail-overlay")).isDisplayed());
		sleep(3000);
		driver.close();
		sleep(3000);
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112707_Mosaico_de_Relacionamiento_General_Ordenar_por_Actitud() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 4);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver mosaico ordenado por actitud");
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".tablaMosaico.tablaUser")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector(".tablaMosaico.tablaTecnico")).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112711_Mosaico_de_Relacionamiento_General_Ordenar_por_ROL() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 4);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver mosaico ordenado por rol");
		sleep(10000);
		List <WebElement> a = driver.findElements(By.className("tablaMosaico"));
		Assert.assertTrue(a.get(0).isDisplayed() && a.get(1).isDisplayed());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112715_Mosaico_de_Relacionamiento_General_Ver_organigrama() {
		scp.moveToElementOnAccAndClick("segundoTitulo", 4);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver mosaico ordenado por rol");
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver organigrama");
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		WebElement element = driver.findElement(By.className("panel-heading"));
		Assert.assertTrue(element.getText().contains("Organigrama y Mapa de Influencia"));
		driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112758_Opportunity_Snapshot_Triangulo_Ordenador() throws ParseException {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		Assert.assertTrue(scp.Triangulo_Ordenador_Validador(driver, By.id("mainTable_wrapper"), 5, 2));	
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112561_Asignacion_de_Value_Drivers_a_Oportunidades_Exportar_a_Excel() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 1);
		String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "exportar a excel");
		sleep(8000);
		usuario = usuario.replace(' ', '_');
		usuario = usuario.concat("-Asignaci\u00f3n_de_Value_Drivers_a_Oportunidades.xls");
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112686_Matriz_de_Criterios_de_Decision_Exportar_a_Excel() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "exportar a excel");
		sleep(8000);
		usuario = usuario.replace(' ', '_');
		usuario = usuario.concat("-Criterios_de_Decisi\u00f3n_por_Oportunidad.xls");
		Assert.assertTrue(scp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");		
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112687_Matriz_de_Criterios_de_Decision_Guardar() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		List <WebElement> element1 = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		boolean a = false;
		for (WebElement x : element1) {
			if (x.getText().toLowerCase().contains("guardar")) {
				x.click();
				a = true;
				break;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "SCP",priority = 3)
	public void TS112688_Matriz_de_Criterios_de_Decision_Ir_al_mosaico() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir al mosaico");
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("panel-heading")).getText().contains("Mosaico de Relacionamiento"));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112689_Matriz_de_Criterios_de_Decision_Ir_al_Snapshot() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir al snapshot");
		sleep(10000);
		Assert.assertTrue(driver.findElement(By.className("panel-heading")).getText().contains("Opportunity Snapshot"));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112690_Matriz_de_Criterios_de_Decision_Ver_Graficos_de_Criterio() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ver gr\u00e1fico de criterios");
		sleep(10000);
		List <WebElement> element2 = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		boolean a = false;
		for (WebElement x : element2) {
			if (x.getText().contains("Modificar Criterios"))
				a = true;
		}
		Assert.assertTrue(a);	
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112691_Matriz_de_Criterios_de_Decision_Ver_Video() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		sleep(10000);
	    ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Assert.assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());
		driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
	
	@Test (groups = "SCP", priority = 2)
	public void TS112692_Matriz_de_Criterios_de_desicion_Ingreso_Desde_el_contacto() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		WebElement boton = driver.findElement(By.xpath("//*[@id=\"j_id0:j_id128:j_id140\"]"));
		boolean b = false, c = false;
		if (boton.getAttribute("value").contains("Agregar Criteria"))
			b = true;
		WebElement eliminar = driver.findElement(By.id("j_id0:j_id143:j_id158:0:j_id174"));
		if (eliminar.getAttribute("value").contains("Eliminar"))
			c = true;
		boolean check = true;
	    String[] datosOp = {"Criterio", "Consideraci\u00f3n del cliente", "Nuestra posici\u00f3n competitiva", "Competidores competitivos de pie", "Approach"};
	    List<String> titleTabla = new ArrayList<String>();
	    WebElement oportunidad = driver.findElement(By.id("j_id0:j_id143:j_id146"));
	    List<WebElement> composicion = oportunidad.findElement(By.tagName("tr")).findElements(By.tagName("th"));	    
	    for(WebElement a : composicion) {
	      titleTabla.add(a.getText());
	    }	    
	    for(String a : datosOp) {
	    	if(!(titleTabla.contains(a)))
	    		check=false;
	    }
	    Assert.assertTrue(b && c && check);
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112685_Matriz_de_Criterios_de_desicion_Eliminar() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		List <WebElement> elim = driver.findElements(By.xpath("//*[@id=\"j_id0:j_id143:j_id158:0:j_id174\"]"));
		boolean a = false;
		for (WebElement x : elim) {
			if (x.getAttribute("value").toLowerCase().contains("eliminar")) {
				a = true;
				break;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112682_Matriz_de_Criterios_de_Decision_Agregar_Criterio() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 2);
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default.btn-sm")), "contains", "ir a los criterios");
		sleep(10000);
		driver.findElement(By.xpath("//*[@id=\"j_id0:j_id128:j_id140\"]")).click();
		sleep(5000);
		WebElement ventana = driver.findElement(By.className("modal-header"));
		Assert.assertTrue(ventana.getText().contains("Evaluaci\u00f3n del Criterio"));
		buscarYClick(driver.findElements(By.cssSelector(".btn.btn-default")), "contains", "cerrar");
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112720_Mosaico_de_Relacionamiento_por_Oportunidad_Enviar() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 3);
		WebElement comp = driver.findElement(By.id("publishersharebutton"));
		Assert.assertTrue(comp.getAttribute("value").equalsIgnoreCase("Compartir"));
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112752_Opportunity_Snapshot_enviar() {
		scp.moveToElementOnAccAndClick("tercerTitulo", 4);
		WebElement comp = driver.findElement(By.id("publishersharebutton"));
		Assert.assertTrue(comp.getAttribute("value").equalsIgnoreCase("Compartir"));
	}
}