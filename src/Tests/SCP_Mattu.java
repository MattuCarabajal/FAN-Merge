package Tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.BasePage;
import Pages.Marketing;
import Pages.SCP;
import Pages.setConexion;

public class SCP_Mattu extends TestBase {

private WebDriver driver;
	
	//-------------------------------------------------------------------------------------------------
	//@Befor&After
	@BeforeClass(groups = "SCP")
	public void Init() throws Exception {
		this.driver = setConexion.setupEze();
		loginSCPConPermisos(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	@BeforeMethod(groups = "SCP")
	public void setUp() throws Exception {
		SCP pScp = new SCP(driver);
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		//pScp.clickEnCuentaPorNombre("Florencia Di Ci");
		pScp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	//@AfterClass(groups = "SCP")
	public void teardown() {
		driver.quit();
		sleep(5000);
	}
	
	//------------------------------------------------------------------------------------------------- 
    //TCC = 1 
	@Test(groups = "SCP", priority = 3)
	public void TS112722_Mosaico_de_Relacionamiento_por_Oportunidad_Ir_al_mosaico() {
		SCP prueba = new SCP(driver); 
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    prueba.moveToElementOnAccAndClick("tercerTitulo", 3); 
	    List <WebElement> wIrAlMosaico = driver.findElements(By.cssSelector(".sorting_1")); 
	    wIrAlMosaico.get(1).click(); 
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	    Assert.assertTrue(driver.findElement(By.className("panel-title")).getText().contains("Mosaico de Relacionamiento")); 
	}
	
	//------------------------------------------------------------------------------------------------- 
    //TCC = 2 
	@Test(groups = "SCP", priority = 3) 
	public void TS112723_Mosaico_de_Relacionamiento_por_Oportunidad_Nombre_de_la_oportunidad() { 
	  SCP prueba = new SCP(driver); 
	  prueba.moveToElementOnAccAndClick("tercerTitulo", 4); 
	  List <WebElement> wOportunity = driver.findElement(By.className("odd")).findElements(By.tagName("a")); 
	  wOportunity.get(1).click(); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	  Assert.assertTrue(driver.findElement(By.className("pageType")).isDisplayed());
	} 
	 
	//------------------------------------------------------------------------------------------------- 
	//TCC = 3 
	@Test(groups = "SCP", priority = 3) 
	public void TS112724_Mosaico_de_Relacionamiento_por_Oportunidad_Search() { 
	  SCP prueba = new SCP(driver); 
	  prueba.moveToElementOnAccAndClick("tercerTitulo", 3); 
	  try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  driver.findElement(By.xpath("//*[@id=\"mainTable_filter\"]/label/input")).sendKeys("Oportunidad 2"); 
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	
	  List<WebElement> wOportunity = driver.findElements(By.xpath("//*[@id=\"mainTable\"]/tbody/tr")); 
	  boolean bAssert = true; 
	   
	  for(WebElement wText:wOportunity) { 
	    if (!(wText.getText().toLowerCase().contains("oportunidad 2"))) { 
	      bAssert=false; 
	      break; 
	    } 
	  } 
	  Assert.assertTrue(bAssert);
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 4 
	@Test(groups = "SCP", priority = 3) 
	public void TS112725_Mosaico_de_Relacionamiento_por_Oportunidad_Triangulo_Ordenador() throws ParseException {
		SCP prueba = new SCP(driver);
		prueba.moveToElementOnAccAndClick("tercerTitulo", 3);
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 4, 2));
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 5
	@Test(groups = "SCP", priority = 3)
	public void TS112726_Mosaico_de_Relacionamiento_por_Oportunidad_Ver_video() {
		TestBase TB = new TestBase();
		SCP prueba = new SCP(driver);
		prueba.moveToElementOnAccAndClick("tercerTitulo", 3);
		TB.waitFor(driver, By.cssSelector(".btn.btn-xs.btn-default"));
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-default")).click();
		
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		sleep(5000);
		boolean bAssert = false;
		try {
			BasePage cambioFrameByID=new BasePage();
			driver.switchTo().defaultContent();
		    driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("ytp-cued-thumbnail-overlay")));
			TB.waitFor(driver, By.className("ytp-cued-thumbnail-overlay-image"));
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("ytp-cued-thumbnail-overlay-image")).getLocation().y+")");
			bAssert = driver.findElement(By.className("ytp-cued-thumbnail-overlay-image")).isDisplayed();
		}
		catch (Exception x) {
			//Always empty
		}
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		Assert.assertTrue(bAssert);
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 6
	@Test(groups = "SCP", priority = 3)
	public void TS112563_Asignacion_de_Value_Drivers_a_Oportunidades_MAS() {
		SCP prueba = new SCP(driver); 
		prueba.moveToElementOnAccAndClick("tercerTitulo", 1);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("mainTable")).getLocation().y+")");
		driver.findElement(By.cssSelector(".btn.btn-default.btn-xs.showMore")).click();
		
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.id("j_id0:j_id112:j_id451:0:j_id540")).getLocation().y+")");
		WebElement wBody = driver.findElement(By.xpath("//*[@id=\"mainTable\"]/tbody"));
		List<WebElement> wElementos = wBody.findElements(By.tagName("td"));
		List<WebElement> wSubElementos = wElementos.get(1).findElements(By.className("moreSpan"));
		List<WebElement> wSubSubElementos = wSubElementos.get(0).findElements(By.tagName("span"));
		Assert.assertTrue(wSubSubElementos.get(2).isDisplayed());
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 7
	@Test(groups = "SCP", priority = 3)
	public void TS112565_Asignacion_de_Value_Drivers_a_Oportunidades_Oportunidades() {
		SCP prueba = new SCP(driver); 
		prueba.moveToElementOnAccAndClick("tercerTitulo", 1);
		Marketing mMarketing = new Marketing(driver);
		List<WebElement> wList = mMarketing.traerColumnaElement(driver.findElement(By.id("mainOppsTable_wrapper")), 6, 1);
		String sName = wList.get(0).getText();
		wList.get(0).findElement(By.tagName("a")).click();
		Assert.assertTrue(driver.findElement(By.className("pageDescription")).getText().equalsIgnoreCase(sName));
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 8
	@Test(groups = "SCP", priority = 3)
	public void TS112566_Asignacion_de_Value_Drivers_a_Oportunidades_Ordenar_por_columnas() throws ParseException {
		SCP prueba = new SCP(driver); 
		prueba.moveToElementOnAccAndClick("tercerTitulo", 1);
		
		driver.findElement(By.xpath("//*[@id=\"mainOppsTable\"]/thead/tr/th[1]"));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 6, 2));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 6, 3));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 6, 4));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 6, 5));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"), 6, 6));
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 9
	@Test(groups = "SCP", priority = 3)
	public void TS112772_Panel_de_control_Busqueda_Anterior_pagina() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		sleep(5000);
		WebElement wMenuSiguiente = driver.findElement(By.cssSelector(".btn-group.pull-right"));
		List<WebElement> wMenu2 = wMenuSiguiente.findElements(By.tagName("input"));
		wMenu2.get(2).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wMenuAnterior = driver.findElement(By.cssSelector(".btn-group.pull-right"));
		List<WebElement> wMenu3 = wMenuAnterior.findElements(By.tagName("input"));
		wMenu3.get(1).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wMenuSiguiente2 = driver.findElement(By.cssSelector(".btn-group.pull-right"));
		Assert.assertTrue(wMenuSiguiente2.findElement(By.tagName("b")).getText().contains("Página: 1"));
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 10
	@Test(groups = "SCP", priority = 3)
	public void TS112773_Panel_de_control_Busqueda_Con_varios_resultados() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		TestBase TB = new TestBase();
		TB.waitFor(driver, By.id("filterDiv"));
		driver.findElement(By.id("filterDiv")).findElement(By.tagName("input")).sendKeys("APER\n\n");
		SCP prueba = new SCP(driver);
		boolean bContains = true;
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		boolean bSiguiente;
		do { 
			TB.waitFor(driver, By.id("mainOppsTable"));
			WebElement wBody = driver.findElement(By.id("mainOppsTable"));
			List <String> sColumna = prueba.TraerColumna(wBody, 3, 1);
			for (String sNombreDeLaCuenta:sColumna) {
				if (!sNombreDeLaCuenta.contains("aper")) {
					bContains = false;
					Assert.assertTrue(bContains);
				}
			}
			
			bSiguiente = driver.findElement(By.id("j_id0:pageForm:siguiente")).isEnabled();
			if(bSiguiente)driver.findElement(By.id("j_id0:pageForm:siguiente")).click();
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		} while (bSiguiente);
		
		Assert.assertTrue(bContains);
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 11
	@Test(groups = "SCP", priority = 3)
	public void TS112636_Estrategia_de_Crecimiento_Triangulo_ordenador() throws ParseException {
		SCP prueba = new SCP(driver); 
		prueba.moveToElementOnAccAndClick("tercerTitulo", 5);
		driver.findElement(By.xpath("//*[@id=\"mainTable\"]/thead/tr/th[1]")).click();
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.id("mainTable"), 5, 1));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.id("mainTable"), 5, 2));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.id("mainTable"), 5, 3));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.id("mainTable"), 5, 4));
		Assert.assertTrue(prueba.Triangulo_Ordenador_Validador(driver, By.id("mainTable"), 5, 5));
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 12
	@Test(groups = "SCP", priority = 3)
	public void TS112775_Panel_de_control_Busqueda_Primera_pagina() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("j_id0:pageForm:Ultima")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("j_id0:pageForm:Primera")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(!driver.findElement(By.id("j_id0:pageForm:Primera")).isEnabled());
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 13
	@Test(groups = "SCP", priority = 3)
	public void TS112777_Panel_de_control_Busqueda_Ultima_pagina() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("j_id0:pageForm:Ultima")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(!driver.findElement(By.id("j_id0:pageForm:Ultima")).isEnabled());
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 14
	@Test(groups = "SCP", priority = 3)
	public void TS112776_Panel_de_control_Busqueda_Siguiente_pagina() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("j_id0:pageForm:siguiente")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.id("j_id0:pageForm:anterior")).isEnabled());
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 15
	@Test(groups = "SCP", priority = 3)
	public void TS112778_Panel_de_control_Hipervinculos_Ir_a_Detalle_de_la_Cuenta() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SCP prueba = new SCP(driver);
		WebElement wBody = driver.findElement(By.id("mainOppsTable"));
		String sCliente = prueba.TraerColumna(wBody, 3, 1).get(0);
		driver.findElement(By.xpath("//*[@id=\"mainOppsTable\"]/tbody/tr[1]/td[3]/a")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Assert.assertTrue(driver.findElement(By.className("topName")).getText().toLowerCase().equals(sCliente));
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 16
	@Test(groups = "SCP", priority = 3)
	public void TS112779_Panel_de_control_Ingreso_Desde_salesforce() {
		WebElement wNavBar = driver.findElement(By.cssSelector(".zen-inlineList.zen-tabMenu"));
		List<WebElement> wMenu = wNavBar.findElements(By.tagName("li"));
		wMenu.get(4).click();
		
		Assert.assertTrue(driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTablePrincipal.dataTable")).isDisplayed());
		List <WebElement> wGraph = driver.findElements(By.cssSelector(".plot-container.plotly"));
		Assert.assertTrue(wGraph.get(0).isDisplayed());
		Assert.assertTrue(wGraph.get(1).isDisplayed());
	}
	
	//------------------------------------------------------------------------------------------------- 
	//TCC = 17
	@Test(groups = "SCP", priority = 3)
	public void TS112567_Asignacion_de_Value_Drivers_a_Oportunidades_Related_value_drivers() {
		SCP prueba= new SCP(driver);
		TestBase tTB = new TestBase();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		prueba.moveToElementOnAccAndClick("tercerTitulo", 1);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		WebElement wBody = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"));
		WebElement wSubBody = wBody.findElement(By.tagName("tbody"));
		List<WebElement> wRows = wSubBody.findElements(By.tagName("tr"));
		List<WebElement> wElements = wRows.get(0).findElements(By.tagName("td"));
		if (wElements.get(5).getText().isEmpty()) {
			Actions builder = new Actions(driver);
			
			Action dragAndDrop = builder.clickAndHold(driver.findElement(By.cssSelector(".StrategicInitiativeRow.DraggableRow.dataRow.NotUsed.ui-draggable.odd")))
			   .moveToElement(driver.findElement(By.id("0063F000002UbLjQAK")))
			   .release(driver.findElement(By.id("0063F000002UbLjQAK")))
			   .build();

			dragAndDrop.perform();
		}
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.className("PopupHolder"));
		action.moveToElement(we).build().perform();
		tTB.waitFor(driver, By.className("pbButton"));
		WebElement wDesvincular = driver.findElement(By.className("pbButton"));
		wDesvincular.findElement(By.className("btn")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		wBody = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed.dataTable"));
		List<String> sRelatedValueDrivers = prueba.TraerColumna(wBody, 6, 6);
		Assert.assertTrue(sRelatedValueDrivers.get(0).isEmpty());
	}
	
	//-------------------------------------------------------------------------------------------------
	//Cambio de cuenta
	/*prueba.Desloguear_Loguear("permisos");
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	SCP pScp = new SCP(driver);
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	pScp.clickOnTabByName("cuentas");
	try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	pScp.clickEnCuentaPorNombre("Florencia Di Ci");
	try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}*/
	
}