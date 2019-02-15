package Tests;

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

import Pages.SCP;
import Pages.setConexion;

public class test_SCP_Base extends TestBase {
	
	private WebDriver driver;
	private SCP scp;
	private String Cuenta;

	
	@BeforeClass (alwaysRun = true)
	public void Init() throws Exception {
		driver = setConexion.setupEze();
		loginSCPConPermisos(driver);
		scp = new SCP(driver);
		sleep(5000);
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setUp() throws Exception {
		sleep(4000);
		scp.clickOnTabByName("cuentas");
		scp.listTypeAcc("Todas las cuentas");
		scp.clickEnCuentaPorNombre("cuenta bien hecha scp");
		Cuenta = driver.findElement(By.className("topName")).getText();
		scp.moveToElementOnAccAndClick("tercerTitulo", 1);
	}
	
	@AfterMethod (alwaysRun = true)
	public void afterMethod() {
		driver.switchTo().defaultContent();
		scp.goTop();
		sleep(1000);
	}
	
	@AfterClass (alwaysRun = true)
	public void tearDown() {
		driver.quit();
		sleep(4000);
	}
	
	public String limpiarValor(String valor) {
		valor = valor.replace("$", "");
		valor = valor.replace(".", "");
		valor = valor.replaceAll(",", ".");
		return valor;
	}

	
	//@Test (groups= "SCP", priority = 3)  //Victor pidio que lo obviaramos
	public void TS112559_CRM_SCP_Asignación_de_Value_Drivers_a_Oportunidades_Chatter_contextualizado_Escribir_comentario() {		
		sleep(3000);
	}
	
	@Test (groups= "SCP", priority = 3)
	public void TS112560_CRM_SCP_Asignación_de_Value_Drivers_a_Oportunidades_Chatter_contextualizado_Leer_comentario_escrito_con_otro_usuario() {	
		sleep(3000);		
		WebElement BenBoton = driver.findElement(By.cssSelector(".feeditemcontent.cxfeeditemcontent"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
		boolean check = false;
		List <WebElement> Nombres= driver.findElements(By.className("feeditemfirstentity"));
		for(WebElement a : Nombres){
			if(!(a.getText().contains(Cuenta))) {
				Assert.assertTrue(driver.findElement(By.cssSelector(".feeditemtext.cxfeeditemtext")).findElement(By.tagName("p")).isDisplayed());
				return;
			}
		}
		Assert.assertTrue(check);
	}
	
	@Test (groups= "SCP", priority = 3)
	public void TS112562_CRM_SCP_Asignación_de_Value_Drivers_a_Oportunidades_Ingreso_Desde_el_contacto() {		
		sleep(3000);
		boolean check = true;
		String[] datosOp = {"nombre de la oportunidad", "importe", "probabilidad (%)", "fecha de cierre", "etapa", "related value drivers"};
		List<String> titleTabla = new ArrayList<String>();
		WebElement oportunidad = driver.findElement(By.id("mainOppsTable")).findElement(By.className("headerRow"));
		List<WebElement> composicion = oportunidad.findElements(By.tagName("th"));		
		for(WebElement a : composicion) {
			titleTabla.add(a.getText().toLowerCase());
		}		
		for(String a : datosOp) {
			if(!(titleTabla.contains(a)))
				check = false;
		}
		Assert.assertTrue(check);	
		String[] datosVD = {"título", "descripción", "tipo", "origen", "oportunidades"};
		List<String> tituloTabla = new ArrayList<String>();
		WebElement valueDrivers = driver.findElement(By.id("mainTable")).findElement(By.className("headerRow"));
		List<WebElement> contenido = valueDrivers.findElements(By.tagName("th"));		
		for(WebElement a : contenido) {
			tituloTabla.add(a.getText().toLowerCase());
		}	
		for(String a : datosVD) {
			if(!(tituloTabla.contains(a)))
				check = false;
		}	
		Assert.assertTrue(check);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112565_CRM_SCP_Asignación_de_Value_Drivers_a_Oportunidades_Oportunidades() {
	     Assert.assertTrue(scp.goToOportunity());
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112638_CRM_SCP_Estructura_de_las_oportunidades_Bloques() {
		boolean check = true;
	    scp.goToOportunity();
    	sleep(3000);
    	String[] primerBloque = {"informaci\u00f3n adicional de ventas","valorizado de la oportunidad","estados proyectos en delta"};
    	String[] segundoBloque = {"detalle de oportunidad","productos (telecom)","contactos","funciones de contactos","actividades abiertas","competidores","notas y archivos adjuntos"};
    	List <WebElement> listA = driver.findElements(By.cssSelector(".brandTertiaryBrd.pbSubheader.tertiaryPalette"));
    	List<String> titleOne = new ArrayList<String>();
    	for(WebElement comparacion : listA) {
    		titleOne.add(comparacion.getText().toLowerCase());
    	}	    		
    	for(String a : primerBloque) {
    		if(!(titleOne.contains(a)))
    			check = false;
    	}
    	Assert.assertTrue(check);
    	check = true;
    	int i = 0;
    	List <WebElement> estructuraOp2 = driver.findElements(By.className("pbTitle"));
    	List<String> titleTwo = new ArrayList<String>();
 	    for(WebElement agregar : estructuraOp2) {
 	    	if(i == 1) {}
 	    	else
 	    		titleTwo.add(agregar.getText().toLowerCase());
 	    		i++;
 	    }
 	    for(String comparar : segundoBloque) {
    		if(!(titleTwo.contains(comparar)))
    			check = false;
    	}
    	Assert.assertTrue(check);	
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112641_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Competidores_Creacion() {
		String countBefore = "", countAfter = "";
	    scp.goToOportunity();
    	List <WebElement> compBefore = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compBefore) {
    		if(a.getText().toLowerCase().startsWith("competidores")) { 
    			countBefore = a.findElement(By.className("count")).getText();
    			a.click();
    		}
    	}	    	
    	sleep(500);
    	driver.findElement(By.name("newComp")).click();
    	sleep(3000);
    	driver.findElement(By.id("CompetitorName")).sendKeys("Almer");
    	driver.findElement(By.id("Strengths")).sendKeys("CRACK!");
    	sleep(500);
    	driver.findElement(By.id("bottomButtonRow")).findElement(By.name("save")).click();
    	sleep(4000);   	
    	List <WebElement> compAfter = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compAfter) {
    		if(a.getText().toLowerCase().startsWith("competidores"))
    			countAfter = a.findElement(By.className("count")).getText();
    	}	    	
    	Assert.assertTrue(!(countBefore.equals(countAfter)));	
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112642_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Competidores_Edicion() {
		String countBefore = "", countAfter = "";
	    scp.goToOportunity();
    	List <WebElement> compBefore = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compBefore) {
    		if(a.getText().toLowerCase().startsWith("competidores")) { 
    			countBefore = a.findElement(By.className("count")).getText();
    			a.click();
    		}
    	}	    
    	sleep(500);	    	
    	driver.findElement(By.cssSelector(".listRelatedObject.opportunityCompetitorBlock")).findElement(By.cssSelector(".dataRow.odd")).findElement(By.tagName("a")).click();
    	driver.findElement(By.id("CompetitorName")).clear();
    	driver.findElement(By.id("CompetitorName")).sendKeys("Edicion Automatica");
    	sleep(500);
    	driver.findElement(By.id("bottomButtonRow")).findElement(By.name("save")).click();
    	sleep(4000);
    	List <WebElement> compAfter = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compAfter) {
    		if(a.getText().toLowerCase().startsWith("competidores")) 
    			countAfter = a.findElement(By.className("count")).getText();
    	}	    	
    	Assert.assertTrue(countBefore.equals(countAfter));	
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112643_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Competidores_Eliminacion() {
		String countBefore = "", countAfter = "";
	    scp.goToOportunity();
    	List <WebElement> compBefore = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compBefore) {
    		if(a.getText().toLowerCase().startsWith("competidores")) { 
    			countBefore = a.findElement(By.className("count")).getText();
    			a.click();
    		}
    	}	    
    	sleep(500);
    	List <WebElement> Eliminar = driver.findElement(By.cssSelector(".listRelatedObject.opportunityCompetitorBlock")).findElement(By.cssSelector(".dataRow.odd")).findElements(By.tagName("a"));
    	Eliminar.get(1).click();
    	driver.switchTo().alert().accept();
    	driver.switchTo().defaultContent();	    	
    	List <WebElement> compAfter = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compAfter) {
    		if(a.getText().toLowerCase().startsWith("competidores")) 
    			countAfter = a.findElement(By.className("count")).getText();
    	}	    	
    	Assert.assertTrue(!(countBefore.equals(countAfter)));
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112649_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Notas_y_Archivos_Adjuntos_Adjuntar_Nota() {
		String countBefore = "", countAfter ="";
	    scp.goToOportunity();
    	List <WebElement> compBefore = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compBefore) {
    		if(a.getText().toLowerCase().startsWith("notas")) { 
    			countBefore = a.findElement(By.className("count")).getText();
    			a.click();
    		}
    	}
    	driver.findElement(By.name("newNote")).click();
    	sleep(3000);
    	driver.findElement(By.id("Title")).sendKeys("Nota Automatizada");
    	driver.findElement(By.id("Body")).sendKeys("Esta Nota ha sido creada por el Equipo de QA Autmoation");
    	sleep(500);
    	driver.findElement(By.id("bottomButtonRow")).findElement(By.name("save")).click();	    	
    	sleep(3000);
    	List <WebElement> compAfter = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compAfter) {
    		if(a.getText().toLowerCase().startsWith("notas"))
    			countAfter = a.findElement(By.className("count")).getText();
    	}
    	Assert.assertTrue(!(countBefore.equals(countAfter)));
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112650_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Notas_y_Archivos_Adjuntos_Visualizar_Nota() {
	    scp.goToOportunity();
    	String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
    	List <WebElement> compBefore = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compBefore) {
    		if(a.getText().toLowerCase().startsWith("notas"))
    			a.click();
    	}
    	driver.findElement(By.xpath("//*[@id=\"" + idOportunidad +"_RelatedNoteList_body\"]/table/tbody/tr[2]/td[2]/a")).click();	
    	sleep(3000);
    	List <WebElement> cuerpoNota = driver.findElements(By.className("data2Col"));
    	Assert.assertTrue(cuerpoNota.get(4).isDisplayed());
	}

	@Test (groups = "SCP", priority = 6)  //No se puede modificar el dolar Budget
	public void TS112665_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Cotizacion_del_dolar_segun_Budget() {
		scp.goToOportunity();
		sleep(500);
		Actions dobleClick = new Actions(driver);
		WebElement dolarBudget = driver.findElement(By.id("00N4100000c3bM8_ileinner"));
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + dolarBudget.getLocation().y + ")");
		dobleClick.doubleClick(dolarBudget).perform();
		driver.findElement(By.id("00N4100000c3bM8")).clear();
		driver.findElement(By.id("00N4100000c3bM8")).sendKeys("10");
		scp.goTop();
		driver.findElement(By.id("topButtonRow")).findElement(By.name("inlineEditSave")).click();
		sleep(1000);
		String valorDolar = driver.findElement(By.id("00N4100000c3bM8_ileinner")).getText();
		Assert.assertTrue(valorDolar.contains("10"));
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112639_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Actividades_abiertas_Tareas() {
		String countBefore = "", countAfter = "";
	    scp.goToOportunity();
    	List <WebElement> compBefore = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compBefore) {
    		if(a.getText().toLowerCase().startsWith("actividades abier")) { 
    			countBefore=a.findElement(By.className("count")).getText();
    			a.click();
    		}
    	}
    	sleep(500);
    	driver.findElement(By.name("task")).click();	    	
    	sleep(3000);
    	driver.findElement(By.name("tsk5")).sendKeys("Prueba Automatizada");
    	driver.findElement(By.id("bottomButtonRow")).findElement(By.name("save")).click();
    	sleep(4000);   	
    	List <WebElement> compAfter = driver.findElements(By.className("listTitle"));
    	for(WebElement a : compAfter) {
    		if(a.getText().toLowerCase().startsWith("actividades abier")) 
    			countAfter = a.findElement(By.className("count")).getText();
    	}	    	
    	Assert.assertTrue(!(countBefore.equals(countAfter)));
	}

	@Test (groups = "SCP", priority = 3)
	public void TS112644_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Detalle_de_la_oportunidad() {
		Boolean co = false, pro = false, tmi = false, nombreOp = false, nombreCu = false, lici = false, retro = false, fProventa = false, fproInst = false, etapa = false, fRealVen = false;
	    scp.goToOportunity();
    	List <WebElement> compBefore = driver.findElements(By.className("labelCol"));
    	for(WebElement a : compBefore) {
			if(a.getText().toLowerCase().startsWith("contacto"))
				co=true;
			if(a.getText().toLowerCase().startsWith("probabilidad"))
				pro=true;
			if(a.getText().toLowerCase().startsWith("tmi"))
				tmi=true;
			if(a.getText().toLowerCase().startsWith("nombre de la oportunidad"))
				nombreOp=true;
			if(a.getText().toLowerCase().startsWith("nombre de la cuenta"))
				nombreCu=true;
			if(a.getText().toLowerCase().startsWith("licitac"))
				lici=true;
			if(a.getText().toLowerCase().startsWith("retroactiva"))
				retro=true;
			if(a.getText().toLowerCase().startsWith("fecha probable de venta"))
				fProventa=true;
			if(a.getText().toLowerCase().startsWith("fecha probable de inst"))
				fproInst=true;
			if(a.getText().toLowerCase().startsWith("etapa"))
				etapa=true;
			if(a.getText().toLowerCase().startsWith("fecha real de venta"))
				fRealVen=true;
			if(co && pro && tmi && nombreOp && nombreCu && lici && retro && fProventa && fproInst && etapa && fRealVen)
				break;
    	}
    	Assert.assertTrue(co);
    	Assert.assertTrue(pro);
    	Assert.assertTrue(tmi);
    	Assert.assertTrue(nombreOp);
    	Assert.assertTrue(nombreCu);
    	Assert.assertTrue(lici);
    	Assert.assertTrue(retro);
    	Assert.assertTrue(fProventa);
    	Assert.assertTrue(fproInst);
    	Assert.assertTrue(etapa);	
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112647_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Historial_de_Campos() {
		String fecha = "";
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
	    driver.findElement(By.name("edit")).click();
	    sleep(3000);
	    List<WebElement> date = driver.findElements(By.className("dateFormat"));
	    fecha = date.get(1).getText();
	    fecha = fecha.substring(2, fecha.length()-2);
	    date.get(0).click();
	    date.get(1).click();
	    date.get(2).click();
	    driver.findElement(By.id("opp3")).clear();
	    sleep(200);
	    driver.findElement(By.id("opp3")).sendKeys("Dulce D\u00eda Cafeter\u00eda");
	    sleep(1000);
	    driver.findElement(By.name("save")).click();
	    sleep(3000);
	    WebElement modificacion = driver.findElement(By.cssSelector(".dataCol.col02.inlineEditWrite"));
	    Assert.assertTrue(modificacion.getText().startsWith(fecha) || modificacion.getText().endsWith("Dulce D\u00eda Cafeter\u00eda"));
	}	
	
	@Test (groups = "SCP", priority = 4)
	public void TS112651_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Productos_de_la_oportunidad() {
		boolean check = true;
		scp.goToOportunity();
		String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		List<WebElement> compBefore = driver.findElements(By.className("listTitle"));
		for (WebElement a : compBefore) {
			if (a.getText().toLowerCase().startsWith("productos"))
				a.click();
		}
		sleep(1000);
		String[] camposaVerificar = {"Acci\u00f3n", "Producto", "Cantidad", "Moneda", "Precio de venta", "Cargos Totales por Mes", "Plazo (meses)", "Total Mes por Plazo", "Cargo unica vez", "Cargo por única vez total", "Precio Total Contrato"};
		List<String> listaComparativa = new ArrayList<String>();
		check = true;
		List<WebElement> campos = driver.findElement(By.xpath("//*[@id=\"" + idOportunidad + "_RelatedLineItemList_body\"]/table/tbody/tr[1]")).findElements(By.tagName("th"));
		for (WebElement a : campos) {
			listaComparativa.add(a.getText());
		}
		for (String a : camposaVerificar) {
			if (!(listaComparativa.contains(a)))
				check = false;
		}
		Assert.assertTrue(check);
	}

	@Test (groups= "SCP", priority = 6)
	public void TS112664_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad() {
	    scp.goToOportunity();
    	sleep(3000);
    	boolean tAP = false, tAD = false, tCuP = false, mCP = false, mCD = false, mtc = false, impE = false, cDB = false;
    	List <WebElement> valorizado = driver.findElements(By.className("labelCol")); //Lista los Elementos de arriba
    	valorizado.addAll(driver.findElements(By.cssSelector(".last.labelCol")));
    	for(WebElement a : valorizado) {
    		System.out.println(a.getText());
    		if(a.getText().toLowerCase().startsWith("total abono (arg)"))
    			tAP = true;
    		if(a.getText().toLowerCase().startsWith("total abono (usd)"))
    			tAD = true;
    		if(a.getText().toLowerCase().startsWith("total cuv (arg)"))
    			tCuP = true;
    		if(a.getText().toLowerCase().startsWith("monto contrato (arg)"))
    			mCP = true;
    		if(a.getText().toLowerCase().startsWith("monto contrato (usd)"))
    			mCD  =true;
    		if(a.getText().toLowerCase().startsWith("monto total contrato (arg)"))
    			mtc = true;
    		if(a.getText().toLowerCase().startsWith("impacto ejercicio actual (arg)"))
    			impE = true;
    		if(a.getText().toLowerCase().startsWith("dolar budget"))
    			cDB = true;
    		if(tAP && tAD && tCuP && mCP && mCD && mtc && impE && cDB)
    			break;
    	}
    	Assert.assertTrue(tAP);
    	Assert.assertTrue(tAD);
    	Assert.assertTrue(tCuP);
    	Assert.assertTrue(mCP);
    	Assert.assertTrue(mCD);
    	Assert.assertTrue(mtc);
    	Assert.assertTrue(impE);
    	Assert.assertTrue(cDB);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112667_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Monto_Contrato_USD() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		System.out.println(idOportunidad);
		double totalCuvD, cantidad, plazo, preciodeVenta, montoContratoD;
		montoContratoD = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[3]/td[4]")).getText()));	
		totalCuvD  =Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[3]/td[9]")).getText()));
		cantidad = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[3]/td[2]")).getText()));
		plazo = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[3]/td[6]")).getText()));
		preciodeVenta = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[3]/td[4]")).getText()));
		Assert.assertEquals((cantidad*plazo*preciodeVenta) + totalCuvD , montoContratoD);
	}
	
	@Test (groups = "SCP", priority = 6)
	public void TS112670_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Total_Contrato_Pesos() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		double montoContratoTotalP, montoContratoP, MontoContratoD, dolar;
		montoContratoTotalP = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[4]/td[2]")).getText()));
		montoContratoP = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[3]/td[2]")).getText()));
		MontoContratoD = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[3]/td[4]")).getText()));
		dolar = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[5]/td[2]")).getText()));		
		Assert.assertTrue(((MontoContratoD*dolar) + montoContratoP) == montoContratoTotalP);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112666_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Monto_Contrato_Pesos() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		double totalCuvP, plazo, cargopormes, montoContratoP;
		montoContratoP = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[3]/td[2]")).getText()));
		totalCuvP = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[2]/td[9]")).getText()));
		plazo = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[2]/td[6]")).getText()));
		cargopormes = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr[2]/td[5]")).getText()));
		Assert.assertTrue(((plazo * cargopormes) + totalCuvP) == montoContratoP);
	}

	@Test (groups = "SCP", priority = 6)  //Los valores no son los mismos
	public void TS112669_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Total_Abono_PESOS() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		String idOportunidad=driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		//System.out.println(idOportunidad);
		double montoAbono = 0.0;
		double totalAbonoP = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[1]/td[2]")).getText()));
		List<WebElement> abono=driver.findElements(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr"));
		abono.remove(0);
		for(WebElement ab : abono) {
			//System.out.println(ab.getText());
			//System.out.println(ab.findElements(By.tagName("td")).get(2).getText());
			if(ab.findElements(By.tagName("td")).get(2).getText().startsWith("ARG")) {
				//System.out.println(ab.findElements(By.tagName("td")).get(3).getText());
				montoAbono = montoAbono + Double.parseDouble(limpiarValor(ab.findElements(By.tagName("td")).get(3).getText()));
			}
		}
		//System.out.println(montoAbono);
		//System.out.println(totalAbonoP);
		Assert.assertEquals(montoAbono, totalAbonoP);
	}
	
	@Test (groups = "SCP", priority = 3)
	public void TS112668_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Total_Abono_Dolares() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		double montoAbono = 0.0;
		double totalAbonoD = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[1]/td[4]")).getText()));
		List<WebElement> abono = driver.findElements(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr"));
		abono.remove(0);
		for(WebElement ab : abono) {
			if(ab.findElements(By.tagName("td")).get(2).getText().startsWith("USD"))
				montoAbono = montoAbono + Double.parseDouble(limpiarValor(ab.findElements(By.tagName("td")).get(4).getText()));
		}
		Assert.assertEquals(montoAbono, totalAbonoD);
	}

	@Test (groups = "SCP", priority = 6)
	public void TS112671_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Total_CUV_Dolares() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		double totalCuv = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[2]/td[4]")).getText()));
		double cuvs = 0.0;
		List<WebElement> abono = driver.findElements(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr"));
		abono.remove(0);
		for(WebElement ab : abono) {
			if(ab.findElements(By.tagName("td")).get(2).getText().startsWith("USD"))
				cuvs = cuvs+Double.parseDouble(limpiarValor(ab.findElements(By.tagName("td")).get(8).getText()));
		}
		Assert.assertEquals(totalCuv, cuvs);
	}

	@Test (groups= "SCP", priority = 6)
	public void TS112672_CRM_SCP_Estructura_de_las_oportunidades_Bloques_Valorizado_de_la_oportunidad_Total_CUV_Pesos() {
		scp.selectOporunity("Dulce D\u00eda Cafeter\u00eda");
		String idOportunidad = driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		double totalCuv = Double.parseDouble(limpiarValor(driver.findElement(By.xpath("//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[2]/td[2]")).getText()));
		double cuvs = 0.0;
		List<WebElement> abono = driver.findElements(By.xpath("//*[@id=\""+idOportunidad+"_RelatedLineItemList_body\"]/table/tbody/tr"));
		abono.remove(0);
		for(WebElement ab : abono) {
			if(ab.findElements(By.tagName("td")).get(2).getText().startsWith("ARG"))
				cuvs = cuvs+Double.parseDouble(limpiarValor(ab.findElements(By.tagName("td")).get(8).getText()));
		}
		Assert.assertEquals(totalCuv, cuvs);
	}
}