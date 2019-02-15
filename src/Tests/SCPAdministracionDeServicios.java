package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.SCP;
import Pages.setConexion;

public class SCPAdministracionDeServicios extends TestBase {
	private WebDriver driver;
	private static String downloadPath = "C:\\Users\\Florangel\\Downloads";
	
	@BeforeClass(groups = "SCP")
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginSCPAdminServices(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
	}
	
	@BeforeMethod(groups = "SCP")
	public void setup() {
		SCP pScp = new SCP(driver);
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		pScp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@AfterClass(groups = "SCP")
	public void tearDown() {
		//driver.quit();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	@Test(groups = "SCP", priority=6)  
    public void TS110248_Estructura_De_Los_Productos_Moneda() {  
      SCP pcp = new SCP(driver);  
      boolean estaMon= false;
     pcp.Desloguear_Loguear("permisos");
      pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement Producto = driver.findElement(By.cssSelector(".listRelatedObject.opportunityLineItemBlock")).findElement(By.cssSelector(".dataRow.even.first")).findElement(By.tagName("th"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+Producto.getLocation().y+")");
		Producto.findElement(By.tagName("a")).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement Detalles = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(8);
		assertTrue(Detalles.findElement(By.tagName("td")).getText().toLowerCase().equals("moneda"));
		String MonActual = Detalles.findElements(By.tagName("td")).get(1).getText();
		System.out.println("Actual: "+MonActual);
		Actions action = new Actions(driver); 
		action.moveToElement(Detalles.findElements(By.tagName("td")).get(1)).doubleClick().perform();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Select listSelect = new Select(Detalles.findElements(By.tagName("td")).get(1).findElement(By.tagName("select")));
		if(MonActual.equals("USD"))
			listSelect.selectByVisibleText("ARG");
		else
			listSelect.selectByVisibleText("USD");
		driver.findElement(By.id("topButtonRow")).findElement(By.tagName("input")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Detalles = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(8);
		String MonNuev = Detalles.findElements(By.tagName("td")).get(1).getText();
		assertFalse(MonActual.equals(MonNuev));
		pcp.Desloguear_Loguear("isabel");
    }  
	
	@Test(groups = "SCP", priority=6)  
    public void TS110249_Estructura_De_Las_Oportunidades_Moneda() {  
      SCP pcp = new SCP(driver);  
      boolean estaMon= false;
      //pcp.Desloguear_Loguear("permisos");
      pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("AIR S.R.L");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.elegiroportunidad("Prueba UAT");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> Detalles = driver.findElement(By.className("detailList")).findElements(By.tagName("tr"));
		for(WebElement UnaL : Detalles) {
			List<WebElement> campos = UnaL.findElements(By.tagName("td"));
			for(WebElement UnC : campos) {
				if (UnC.getText().toLowerCase().equals("moneda"))
					estaMon = true;
			}
		}
		assertTrue(estaMon);
		//pcp.Desloguear_Loguear("isabel");
    }  
	
	@Test(groups = "SCP", priority=6)  
    public void TS110250_Estructura_De_Los_Proyectos_TMI() {  
      SCP pcp = new SCP(driver);  
      pcp.Desloguear_Loguear("permisos");
      pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("AIR S.R.L");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.elegiroportunidad("Prueba UAT");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement TMI = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(3);
		int numTMI = Integer.parseInt(TMI.getText())+1;
	    System.out.println(Integer.toString(numTMI));
		Actions action = new Actions(driver); 
		action.moveToElement(TMI).doubleClick().perform();
	    TMI.findElement(By.tagName("input")).sendKeys(Integer.toString(numTMI));
	    driver.findElement(By.id("topButtonRow")).findElement(By.tagName("input")).click();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	    TMI = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(3);
	    assertTrue(TMI.getText().equals(Integer.toString(numTMI)));
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
		pcp.Desloguear_Loguear("isabel");
    }  
	
	@Test(groups = "SCP", priority=6)  
    public void TS110251_Estructura_De_Los_Proyectos_TMI_Fecha_Pasada() {  
      SCP pcp = new SCP(driver);  
      //pcp.Desloguear_Loguear("fabiana");
      /*pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("AIR S.R.L");*/
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement TMI = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(3);
		Actions action = new Actions(driver); 
		action.moveToElement(TMI).doubleClick().perform();
		  TMI.findElement(By.tagName("input")).clear();
	    TMI.findElement(By.tagName("input")).sendKeys("-100");
	    driver.findElement(By.id("topButtonRow")).findElement(By.tagName("input")).click();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	    assertTrue(driver.findElement(By.id("errorDiv_ep")).getText().contains("Error:El valor del TMI debe ser un n\u00famero positivo."));

		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
		//pcp.Desloguear_Loguear("isabel");
    }  
	
	@Test(groups = "SCP", priority=6)  
    public void TS110252_Estructura_De_Las_Oportunidades_Probabilidad() {  
      SCP pcp = new SCP(driver);  
      pcp.Desloguear_Loguear("permisos");
      pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.elegiroportunidad("Dulce D\u00eda Cafeter\u00eda");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement prob = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(3);
		Actions action = new Actions(driver); 
		action.moveToElement(prob).doubleClick().perform();
		String[] todos = {"alta","media","baja"};
	    try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	    Select listSelect = new Select(prob.findElement(By.tagName("select")));
	    List<WebElement> motivos = listSelect.getOptions();
	    assertTrue(verificarContenidoLista(todos,motivos));
	    try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
		//pcp.Desloguear_Loguear("isabel");
    }  
	
	@Test(groups = "SCP", priority=3)  
    public void TS110253_Estructura_De_Los_Contactos_Estado() {  
      SCP pcp = new SCP(driver);  
      boolean estaMon= false;
     // pcp.Desloguear_Loguear("fabiana");
     /* pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("AIR S.R.L");*/
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement Producto = driver.findElement(By.cssSelector(".listRelatedObject.accountContactRelationBlock")).findElement(By.cssSelector(".dataRow.even.first")).findElement(By.tagName("th"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+Producto.getLocation().y+")");
		Producto.findElement(By.tagName("a")).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement Detalles = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(5);
		assertTrue(Detalles.findElement(By.tagName("td")).getText().toLowerCase().equals("activo"));
		Actions action = new Actions(driver); 
		action.moveToElement(Detalles.findElements(By.tagName("td")).get(1)).doubleClick().perform();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Detalles.findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("topButtonRow")).findElement(By.tagName("input")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		Detalles = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(5);
		action.moveToElement(Detalles.findElements(By.tagName("td")).get(1)).doubleClick().perform();
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(Detalles.findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).isSelected());
		Detalles.findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).click();
		driver.findElement(By.id("topButtonRow")).findElement(By.tagName("input")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		
		//pcp.Desloguear_Loguear("isabel");
    }  
	
	
	@Test(groups = "SCP", priority=5)  
    public void TS110254_Estructura_De_Los_Servicios_Servicios_Nuestros() {  
      SCP pcp = new SCP(driver);  
      pcp.moveToElementOnAccAndClick("segundoTitulo",2);  
      try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
      assertTrue(driver.findElement(By.className("NotUsed")).getAttribute("style").contains("rgb(179, 253, 159)")&&(driver.findElement(By.className("NotUsedSampleText")).getText().toLowerCase().contains("nosotros")));  
        
    }  
      
    @Test(groups = "SCP", priority=4)  
    public void TS110255_Estructura_De_Los_Servicios_Servicios_De_La_Competencia() {  
      SCP pcp = new SCP(driver);  
      pcp.moveToElementOnAccAndClick("segundoTitulo",2);  
      assertTrue(driver.findElement(By.className("hasOpportunity")).getAttribute("style").contains("rgb(255, 158, 158)")&&(driver.findElement(By.className("hasOpportunitySampleText")).getText().toLowerCase().contains("competencia")));  
        
    }  
      
    @Test(groups = "SCP", priority=3)  
    public void TS110256_Estructura_De_Los_Servicios_Servicios_Futuros() {  
      SCP pcp = new SCP(driver);  
      pcp.moveToElementOnAccAndClick("segundoTitulo",2);  
      assertTrue(driver.findElement(By.className("hasPotentialBussiness")).getAttribute("style").contains("rgb(255, 255, 139)")&&(driver.findElement(By.className("hasPotentialBussinessSampleText")).getText().toLowerCase().contains("futuro")));  
        
    }  
	
	@Test(groups = "SCP", priority=3)
	public void TS112539_Administracion_de_Contexto_Sectorial_Agregar_Guardando() {
		BasePage Bp = new BasePage();
		SCP pcp = new SCP(driver);
		String sector = new String();
		List<WebElement> servicioList = driver.findElement(By.className("detailList")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnS : servicioList) {
			if (UnS.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("segmento vertical")) {
				sector = UnS.findElements(By.tagName("td")).get(3).getText();
				System.out.println("Sector: "+sector);
				break;
			}	
		}
		pcp.moveToElementOnAccAndClick("quintoTitulo",1);
		Select listSelect = new Select(driver.findElement(By.className("panel-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText(sector);
		driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("agregar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("modal-body")).findElement(By.tagName("input")).sendKeys("Contexto sectorial automatizado");
		listSelect = new Select(driver.findElement(By.className("modal-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText("Contexto General");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("modal-body")).findElement(By.tagName("textarea")).sendKeys("Contexto sectorial automatizado");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-footer")).get(0).findElement(By.cssSelector(".btn.btn-primary")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("guardar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SCP pScp = new SCP(driver);
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.listTypeAcc("Todas las cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnFirstAccRe();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Bp.getFrameForElement(driver, By.id("primerTitulo")));
		pcp.moveToElementOnAccAndClick("primerTitulo",1);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("hidden-Con")).click();
		boolean enc = false;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnaC : servicioList) {
			if ((UnaC.findElements(By.tagName("td")).get(1).getText().toLowerCase().contains("contexto sectorial automatizado"))&&(UnaC.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("contexto sectorial automatizado"))) {
				enc = true;
			}	
		}
		assertTrue(enc);
		
	}
	
	@Test(groups = "SCP", priority=4)
	public void TS112541_Administracion_de_Contexto_Sectorial_Borrar_Guardando() {
		SCP pcp = new SCP(driver);
		BasePage Bp = new BasePage();
		String sector = new String();
		List<WebElement> servicioList = driver.findElement(By.className("detailList")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnS : servicioList) {
			if (UnS.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("segmento vertical")) {
				sector = UnS.findElements(By.tagName("td")).get(3).getText();
				System.out.println("Sector: "+sector);
				break;
			}	
		}
		pcp.moveToElementOnAccAndClick("quintoTitulo",1);
		Select listSelect = new Select(driver.findElement(By.className("panel-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText(sector);
		driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnaC : servicioList) {
			if ((UnaC.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("contexto general"))&&(UnaC.findElements(By.tagName("td")).get(3).getText().toLowerCase().contains("contexto sectorial automatizado"))&&(UnaC.findElements(By.tagName("td")).get(4).getText().toLowerCase().contains("contexto sectorial automatizado"))) {
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+UnaC.findElements(By.tagName("td")).get(0).getLocation().y+")");
				UnaC.findElements(By.tagName("td")).get(0).click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		WebElement subir = driver.findElement(By.id("userNavLabel"));
		  ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+subir.getLocation().y+")");
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("guardar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SCP pScp = new SCP(driver);
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.listTypeAcc("Todas las cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnFirstAccRe();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Bp.getFrameForElement(driver, By.id("primerTitulo")));
		pcp.moveToElementOnAccAndClick("primerTitulo",1);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("hidden-Con")).click();
		boolean enc = true;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnaC : servicioList) {
			if ((UnaC.findElements(By.tagName("td")).get(1).getText().toLowerCase().contains("contexto sectorial automatizado"))&&(UnaC.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("contexto sectorial automatizado"))) {
				enc = false;
			}	
		}
		assertTrue(enc);
		
	}
	
	  @Test(groups = "SCP",priority=4)  
	    public void TS112545_Administracion_de_Contexto_Sectorial_Filtros_Sector() {  
	      SCP pcp = new SCP(driver);  
	      boolean filtroSirve = false;  
	        
	      pcp.moveToElementOnAccAndClick("quintoTitulo",1);  
	      Select listSelect = new Select(driver.findElement(By.className("panel-body")).findElement(By.tagName("select")));  
	      listSelect.selectByVisibleText("Entretenimiento");  
	      driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();  
	      try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
	      List<WebElement> servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));  
	      servicioList.remove(0);  
	      for (WebElement UnaC : servicioList) {  
	        if ((UnaC.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("contexto general"))&&(UnaC.findElements(By.tagName("td")).get(3).getText().toLowerCase().contains("probando filtro sector"))&&(UnaC.findElements(By.tagName("td")).get(4).getText().toLowerCase().contains("probando filtro sector"))) {  
	          filtroSirve = true;  
	          break;  
	        }  
	      }  
	      assertTrue(filtroSirve);  
	    }  
	      
	
	@Test(groups = "SCP",priority=4)
	public void TS112547_Administracion_de_Contexto_Sectorial_Ingreso_Desde_el_Contacto() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("quintoTitulo",1);
		//picklist sector
		assertTrue(driver.findElement(By.className("panel-body")).findElement(By.tagName("select")).isDisplayed());
		//boton seleccionar
		assertTrue(driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).getAttribute("Value").equals("Seleccionar")&&driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).getAttribute("type").equals("button"));
		List<WebElement> Itabla = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElement(By.tagName("tr")).findElements(By.tagName("th"));
		//columna borrar
		assertTrue(Itabla.get(0).isDisplayed());
		//columna id
		assertTrue(Itabla.get(1).getText().toLowerCase().equals("id"));
		//columna tipo
		assertTrue(Itabla.get(2).getText().toLowerCase().equals("tipo"));
		//columna titulo
		assertTrue(Itabla.get(3).getText().toLowerCase().equals("t\u00edtulo"));
		//columna descripcion
		assertTrue(Itabla.get(4).getText().toLowerCase().equals("descripci\u00f3n"));
	}
	
	@Test(groups = "SCP",priority=6)
	public void TS112548_Administracion_de_Servicios_Borrar_Categoria_del_Servicio() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("quintoTitulo",2);
		if (driver.findElement(By.className("panel-body")).findElement(By.tagName("h3")).getText().contains("Servicio automatizado")) {
			driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();
		}
	
		boolean enc = true;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		List<WebElement> servicioList = driver.findElement(By.className("panel-body")).findElements(By.tagName("h3"));
		for (WebElement UnaC : servicioList) {
			if (UnaC.getText().contains("Servicio automatizado")) {
				enc = false;
			}	
		}
		assertTrue(enc);
	}
	
	@Test(groups = "SCP",priority=6)
	public void TS112551_Administracion_de_Servicios_Borrar_Servicio() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("quintoTitulo",2);
		boolean enc = true;
		List<WebElement> servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement Uno : servicioList) {
			if (Uno.findElements(By.tagName("td")).get(1).getText().equals("Prueba automatizada")) {
				Uno.findElements(By.tagName("td")).get(0).click();
				break;
			}	
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement Uno : servicioList) {
			if (Uno.findElements(By.tagName("td")).get(1).getText().equals("Prueba automatizada")) {
				enc = false;
			}	
		}
		assertTrue(enc);
	}
	
	@Test(groups = "SCP",priority=2)
	public void TS112554_Administracion_de_Servicios_Creacion_Crear_Categoria_de_Servicio() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("quintoTitulo",2);
		List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().contains("Crear Categor\u00eda de Servicio")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-body")).get(0).findElement(By.tagName("input")).sendKeys("Servicio automatizado");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-footer")).get(0).findElement(By.cssSelector(".btn.btn-primary")).click();
		boolean enc = false;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.tagName("h3"));
		for (WebElement UnaC : servicioList) {
			if (UnaC.getText().contains("Servicio automatizado")) {
				enc = true;
			}	
		}
		assertTrue(enc);
		
	}
	
	@Test(groups = "SCP",priority=6)
	public void TS112556_Administracion_de_Servicios_Creacion_Crear_Servicio() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("quintoTitulo",2);
		List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().contains("Nuevo Servicio")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-body")).get(1).findElement(By.tagName("input")).sendKeys("Prueba automatizada");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-footer")).get(1).findElement(By.cssSelector(".btn.btn-primary")).click();
		boolean enc = false;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement Uno : servicioList) {
			if (Uno.findElements(By.tagName("td")).get(1).getText().equals("Prueba automatizada")) {
				enc = true;
			}	
		}
		assertTrue(enc);

	}
	
	@Test(groups = "SCP",priority=1)
	public void TS112557_Administracion_de_Servicios_Ingreso_Desde_El_Contacto() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("quintoTitulo",2);
		boolean enc = true;
		boolean ccs = false;
		boolean ns = false;
		boolean bbs = false;
		List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().contains("Nuevo Servicio")) {
				ns = true;
			}else {
				if (UnS.getText().contains("Crear Categor\u00eda de Servicio")) {
					ccs = true;
				}
			}
		}
		assertTrue(ns&&ccs);
		enc = driver.findElement(By.className("panel-body")).findElement(By.tagName("h3")).isDisplayed();
		assertTrue(enc);
		servicioList = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).findElements(By.tagName("tr"));
		enc= servicioList.get(0).findElements(By.tagName("th")).get(1).getText().contains("Servicio"); 
		assertTrue(enc);
		servicioList.remove(0);
		for (WebElement Uno : servicioList) {
			if (Uno.findElements(By.tagName("td")).get(0).isEnabled()) {
				bbs = true;
			}	
		}
		assertTrue(bbs);
	}
	
	@Test(groups = "SCP", priority=3)
	public void TS112576_Configurar_Reporte_SCP_Exportar_a_Word() {
		SCP pcp = new SCP(driver);
		pcp.moveToElementOnAccAndClick("cuartoTitulo",3);
		String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();
		List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("export to word")||UnS.getText().toLowerCase().contains("exportar a word")) {
				UnS.click();
				break;
			}
		}
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		usuario=usuario.replace(' ', '_');
		usuario=usuario.concat("-Configurar_Reporte_SCP.doc");
		assertTrue(pcp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");
	}
	
	  @Test(groups = "SCP",priority=3)  
	    public void TS112577_Configurar_Reporte_SCP_Guardar() {  
	      SCP pcp = new SCP(driver);  
	      boolean botonG = false;  
	      pcp.moveToElementOnAccAndClick("cuartoTitulo",3);  
	      List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));  
	      for (WebElement UnS : servicioList) {  
	        if (UnS.getText().toLowerCase().contains("guardar")) {  
	          botonG = true;  
	          UnS.click();  
	        }  
	      }  
	      assertTrue(botonG);  
	      System.out.println("Se debe verificar que mas hacer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");  
	    }  
	
	@Test(groups = "SCP",priority=3)
	public void TS112578_Configurar_Reporte_SCP_Ingreso_Desde_El_Contacto() {
		SCP pcp = new SCP(driver);
		boolean botonG= false;
		boolean botonW = false;
		pcp.moveToElementOnAccAndClick("cuartoTitulo",3);
		List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("export to word")||UnS.getText().toLowerCase().contains("exportar a word")) {
				botonW = true;
			}else {
				if (UnS.getText().toLowerCase().contains("guardar")) {
					botonG = true;
				}
			}
		}
		assertTrue(botonW&&botonG);
		servicioList = driver.findElement(By.className("panel-body")).findElements(By.className("h1"));
		List<WebElement> reportList = driver.findElement(By.className("panel-body")).findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
		assertTrue(servicioList.size()<=reportList.size());
		
	}
	
	@Test(groups = "SCP", priority=3)  
    public void TS112579_Configurar_Reporte_SCP_Ver_Video() {  
      SCP pcp = new SCP(driver);  
      boolean botonG = false;  
      pcp.moveToElementOnAccAndClick("cuartoTitulo",3);  
      List<WebElement> botones = driver.findElements(By.cssSelector(".btn.btn-xs.btn-default"));  
      for (WebElement UnS : botones) {  
        if (UnS.getText().toLowerCase().contains("ver video")) {  
          botonG = true;  
          UnS.click();  
          try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
        }  
      }  
      assertTrue(botonG);  
      ArrayList<String> allTabs = new ArrayList<String>(driver.getWindowHandles());  
        driver.switchTo().window(allTabs.get(1));  
      assertTrue(driver.findElement(By.cssSelector(".video-stream.html5-main-video")).isDisplayed());  
      driver.close();  
      driver.switchTo().window(allTabs.get(0));  
      try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
    }  
      
	@Test(groups = "SCP",priority=3)
	public void TS112580_Contexto_Sectorial_Cadena_De_Valor_Y_Procesos() {
		BasePage Bp = new BasePage();
		SCP pcp = new SCP(driver);
		java.util.Date fecha = new Date();
		String sector = new String();
		List<WebElement> servicioList = driver.findElement(By.className("detailList")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnS : servicioList) {
			if (UnS.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("segmento vertical")) {
				sector = UnS.findElements(By.tagName("td")).get(3).getText();
				System.out.println("Sector: "+sector);
				break;
			}	
		}
		pcp.moveToElementOnAccAndClick("quintoTitulo",1);
		Select listSelect = new Select(driver.findElement(By.className("panel-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText(sector);
		driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("agregar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("modal-body")).findElement(By.tagName("input")).sendKeys("Contexto sectorial automatizado");
		listSelect = new Select(driver.findElement(By.className("modal-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText("Cadena de Valor y Procesos");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("modal-body")).findElement(By.tagName("textarea")).sendKeys(fecha.toString());
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-footer")).get(0).findElement(By.cssSelector(".btn.btn-primary")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("guardar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SCP pScp = new SCP(driver);
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.listTypeAcc("Todas las cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnFirstAccRe();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Bp.getFrameForElement(driver, By.id("primerTitulo")));
		pcp.moveToElementOnAccAndClick("primerTitulo",1);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("hidden-Cad")).click();
		boolean enc = false;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).get(3).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnaC : servicioList) {
			if ((UnaC.findElements(By.tagName("td")).get(1).getText().toLowerCase().contains("contexto sectorial automatizado"))&&(UnaC.findElements(By.tagName("td")).get(2).getText().equals(fecha.toString()))) {
				enc = true;
			}	
		}
		assertTrue(enc);
		
	}
	
	@Test(groups = "SCP",priority=3)
	public void TS112583_Contexto_Sectorial_Casos_De_Exito_Sectorial() {
		BasePage Bp = new BasePage();
		SCP pcp = new SCP(driver);
		java.util.Date fecha = new Date();
		String sector = new String();
		List<WebElement> servicioList = driver.findElement(By.className("detailList")).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnS : servicioList) {
			if (UnS.findElements(By.tagName("td")).get(2).getText().toLowerCase().contains("segmento vertical")) {
				sector = UnS.findElements(By.tagName("td")).get(3).getText();
				System.out.println("Sector: "+sector);
				break;
			}	
		}
		pcp.moveToElementOnAccAndClick("quintoTitulo",1);
		Select listSelect = new Select(driver.findElement(By.className("panel-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText(sector);
		driver.findElement(By.className("panel-body")).findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("agregar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("modal-body")).findElement(By.tagName("input")).sendKeys("Contexto sectorial automatizado");
		listSelect = new Select(driver.findElement(By.className("modal-body")).findElement(By.tagName("select")));
		listSelect.selectByVisibleText("Casos de \u00e9xito Sectorial");
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("modal-body")).findElement(By.tagName("textarea")).sendKeys(fecha.toString());
		try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElements(By.className("modal-footer")).get(0).findElement(By.cssSelector(".btn.btn-primary")).click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));
		for (WebElement UnS : servicioList) {
			if (UnS.getText().toLowerCase().contains("guardar")) {
				UnS.click();
				break;
			}	
		}
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		SCP pScp = new SCP(driver);
		pScp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.listTypeAcc("Todas las cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pScp.clickOnFirstAccRe();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Bp.getFrameForElement(driver, By.id("primerTitulo")));
		pcp.moveToElementOnAccAndClick("primerTitulo",1);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("hidden-Cas")).click();
		boolean enc = false;
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		servicioList = driver.findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed")).get(5).findElements(By.tagName("tr"));
		servicioList.remove(0);
		for (WebElement UnaC : servicioList) {
			if ((UnaC.findElements(By.tagName("td")).get(1).getText().toLowerCase().contains("contexto sectorial automatizado"))&&(UnaC.findElements(By.tagName("td")).get(2).getText().equals(fecha.toString()))) {
				enc = true;
			}	
		}
		assertTrue(enc);
		
	}
	
    @Test(groups = "SCP", priority=3)  
    public void TS112631_Estrategia_De_Crecimiento_Exportar_A_Excel() {  
      SCP pcp = new SCP(driver);  
      pcp.moveToElementOnAccAndClick("tercerTitulo",5);  
      String usuario = driver.findElement(By.cssSelector(".nav.navbar-nav.navbar-right")).findElement(By.tagName("a")).getText();  
      List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));  
      for (WebElement UnS : servicioList) {  
        if (UnS.getText().toLowerCase().contains("export to excel")||UnS.getText().toLowerCase().contains("exportar a excel")) {  
          UnS.click();  
          break;  
        }  
      }  
      try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
      usuario=usuario.replace(' ', '_');  
      usuario=usuario.concat("-Estrategia_de_Crecimiento.xls");  
      assertTrue(pcp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");  
    }  
      
    @Test(groups = "SCP",priority=3)  
    public void TS112632_Estrategia_De_Crecimiento_Guardar() {  
      SCP pcp = new SCP(driver);  
      boolean botonG = false;  
      java.util.Date fecha = new Date();
      pcp.Desloguear_Loguear("permisos");
      try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
      pcp.moveToElementOnAccAndClick("tercerTitulo",5);  
      try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
      Actions action = new Actions(driver);   
		action.moveToElement(driver.findElement(By.id("mainBnpTable")).findElements(By.tagName("tr")).get(1).findElement(By.className("inlineEditWrite"))).doubleClick().perform();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("inlineEditCompoundDiv")).findElement(By.tagName("textarea")).clear();
		driver.findElement(By.className("inlineEditCompoundDiv")).findElement(By.tagName("textarea")).sendKeys("a"+fecha.toString());
		driver.findElement(By.id("InlineEditDialog_buttons")).findElement(By.className("zen-btn")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	      
      List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));  
      for (WebElement UnS : servicioList) {  
        if (UnS.getText().toLowerCase().contains("guardar")) {  
          botonG = true;  
          UnS.click();  
        }  
      }  
      assertTrue(botonG); 
      try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
      servicioList = driver.findElement(By.id("mainBnpTable")).findElements(By.tagName("tr"));
      servicioList.remove(0);
      botonG=false;
      for (WebElement UnS : servicioList) { 
    	  System.out.println(UnS.findElement(By.tagName("td")).getText());
          if (UnS.findElement(By.cssSelector(".dataCell.sorting_1")).findElement(By.tagName("span")).getText().equals("a"+fecha.toString())) {  
            botonG = true;  
            break;
          }  
        }  
       assertTrue(botonG);	 
    }  
    
    @Test(groups = "SCP", priority=3)  
    public void TS112635_Estrategia_De_Crecimiento_Search() {  
      SCP pcp = new SCP(driver);  
      boolean filtroBien = true;  
      boolean estaBuscado = false;  
      pcp.moveToElementOnAccAndClick("tercerTitulo",5);  
      List<WebElement> primeros = driver.findElement(By.id("mainTable")).findElements(By.tagName("tr"));  
      primeros.remove(0);  
      String textoBuscar = primeros.get(0).findElement(By.tagName("td")).getText();  
      driver.findElement(By.id("mainTable_filter")).findElement(By.tagName("input")).sendKeys(textoBuscar);  
      try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
      primeros = driver.findElement(By.id("mainTable")).findElements(By.tagName("tr"));  
      primeros.remove(0);  
      for (WebElement uno : primeros) {  
        if (uno.getAttribute("class").equals("StrategicInitiativeRow DraggableRow dataRow NotUsed odd") || (uno.getAttribute("class").equals("StrategicInitiativeRow DraggableRow dataRow NotUsed even"))) {  
          if(uno.findElement(By.tagName("td")).getText().equals(textoBuscar))   
            estaBuscado = true;  
          else   
            filtroBien = false;  
        }  
      }  
      assertTrue(estaBuscado&&filtroBien);  
      try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
    }  
      
    @Test(groups = "SCP", priority=3)  
    public void TS112637_Estrategia_De_Crecimiento_Ver_Video() {  
      SCP pcp = new SCP(driver);  
      boolean botonG = false;  
      pcp.moveToElementOnAccAndClick("tercerTitulo",5);  
      List<WebElement> botones = driver.findElements(By.cssSelector(".btn.btn-xs.btn-default"));  
      for (WebElement UnS : botones) {  
        if (UnS.getText().toLowerCase().contains("ver video")) {  
          botonG = true;  
          UnS.click();  
          try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
        }  
      }  
      assertTrue(botonG);  
      ArrayList<String> allTabs = new ArrayList<String>(driver.getWindowHandles());  
        driver.switchTo().window(allTabs.get(1));  
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));  
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".html5-video-player.unstarted-mode.ytp-hide-controls")).getLocation().y+")");  
      assertTrue(driver.findElement(By.cssSelector(".html5-video-player.unstarted-mode.ytp-hide-controls")).isDisplayed());  
      assertTrue(driver.findElement(By.cssSelector(".ytp-title-link.yt-uix-sessionlink")).getText().toLowerCase().contains("strategic client plan"));  
      assertTrue(driver.findElement(By.cssSelector(".ytp-title-link.yt-uix-sessionlink")).getAttribute("href").contains("youtube"));  
      driver.close();  
      driver.switchTo().window(allTabs.get(0));  
      try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
      
    }  
      
    @Test(groups = "SCP", priority=3)  
    public void TS112673_Estructura_De_Los_Contactos_Detalle_De_Contacto() {  
      SCP pcp = new SCP(driver);  
      boolean estaMon= false;
      
      /*pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("AIR S.R.L");*/
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//driver.findElement(By.cssSelector(".listRelatedObject.accountContactRelationBlock")).findElement(By.className("pShowMore")).findElements(By.tagName("a")).get(1).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.cssSelector(".listRelatedObject.accountContactRelationBlock")).findElement(By.cssSelector(".dataRow.even.first")).findElements(By.tagName("a")).get(3).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String[] campos = {"propietario del contacto","tel\u00e9fono","nombre","celular","nombre de la cuenta","email","fecha de nacimiento","pictureurl","t\u00edtulo","picture","activo","domicilio","creado por","descripci\u00f3n","ejecutivo de cuenta"};//
		List<WebElement> Detalles = driver.findElement(By.className("detailList")).findElements(By.tagName("tr"));
		List<WebElement> cReales = new ArrayList<WebElement>();
		for(WebElement UnaL : Detalles) {
			cReales.add(UnaL.findElements(By.tagName("td")).get(0));
			try {
			cReales.add(UnaL.findElements(By.tagName("td")).get(2));
			}catch(IndexOutOfBoundsException ex1) {
				
			}
		}
		assertTrue(verificarContenidoLista(campos,cReales));
		assertTrue(driver.findElement(By.className("noStandardTab")).findElement(By.tagName("h3")).getText().toLowerCase().contains("notas"));
    }
    
    @Test(groups = "SCP", priority=3)  
    public void TS112788_Parque_De_Servicios_Ver_Video() {  
      SCP pcp = new SCP(driver);  
      boolean botonG = false;  
      pcp.moveToElementOnAccAndClick("segundoTitulo",2);  
      List<WebElement> botones = driver.findElements(By.cssSelector(".btn.btn-xs.btn-default"));  
      for (WebElement UnS : botones) {  
        if (UnS.getText().toLowerCase().contains("ver video")) {  
          botonG = true;  
          UnS.click();  
          try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
        }  
      }  
      assertTrue(botonG);  
      ArrayList<String> allTabs = new ArrayList<String>(driver.getWindowHandles());  
        driver.switchTo().window(allTabs.get(1));  
        sleep(1000);
        assertTrue(driver.getCurrentUrl().contains("youtube.com"));
        assertTrue(driver.getTitle().contains("Telecom")); 
      driver.close();  
      driver.switchTo().window(allTabs.get(0));  
      try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
    }  
	
	@Test(groups = "SCP", priority=3)
	public void TS112787_Parque_De_Servicios_Ingreso_Desde_El_Contacto() {
		SCP pcp = new SCP(driver);
		List<String> tdo = new ArrayList<String>();
		String[] todos = {"servicios","editar estado","proveedor actual","gastos anuales","tomar acci\u00f3n antes de", "compra a nivel","decision maker","otra informaci\u00f3n"};
		pcp.moveToElementOnAccAndClick("segundoTitulo",2);
		List<WebElement> servicioList = driver.findElement(By.xpath("//*[@id='tableList']/tbody/tr[2]")).findElements(By.tagName("th")); 
		for (int i = 0 ; i<=7 ; i++) {
			tdo.add(todos[i]);
		}
		assertTrue(servicioList.get(0).getText().isEmpty());
		servicioList.remove(0);
		for (WebElement UnS : servicioList) {
			if(!UnS.getText().equals("Pais")) {
				assertTrue(tdo.contains(UnS.getText().toLowerCase()));
			}
		}
	}
	

	@Test(groups = "SCP", priority=3) 
		  public void TS112790_Plan_de_accion_Chatter_Contextualizado_Leer_Comentario_Escrito_Con_Otro_Usuario() { 
		    SCP pcp = new SCP(driver); 
		    java.util.Date fecha = new Date();
		    System.out.println (fecha);
		    pcp.Desloguear_Loguear("isabel");
		    pcp.Desloguear_Loguear_Comentar("persimos", "isabel", fecha.toString(), "cuartoTitulo", 2); 
		    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			pcp.clickOnTabByName("cuentas");
			try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
			try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			pcp.moveToElementOnAccAndClick("cuartoTitulo", 2);
		    pcp.validarcomentarioajeno(fecha.toString());
		    pcp.Desloguear_Loguear("permisos");
	}

	@Test(groups = "SCP", priority=3) 
	  public void TS112791_Plan_de_accion_Doble_Click_Para_Editar() { 
	    SCP pcp = new SCP(driver); 
	    pcp.Desloguear_Loguear("permisos");
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		pcp.moveToElementOnAccAndClick("cuartoTitulo", 2);
		WebElement modificar= driver.findElement(By.id("mainTable")).findElement(By.className("odd")).findElements(By.tagName("td")).get(2);
		Actions action = new Actions(driver);   
		action.moveToElement(modificar.findElement(By.tagName("span")).findElement(By.tagName("span"))).doubleClick().perform();
		assertTrue(modificar.findElement(By.className("inlineEditDiv")).isDisplayed());
		//pcp.Desloguear_Loguear("isabel");
	 }
	
	@Test(groups = "SCP", priority=3)  
    public void TS112793_Plan_De_Accion_Exportar_A_Excel() {  
      SCP pcp = new SCP(driver);  
      pcp.moveToElementOnAccAndClick("cuartoTitulo",2);  
      String usuario = driver.findElements(By.cssSelector(".nav.navbar-nav.navbar-right")).get(1).findElement(By.tagName("a")).getText();  
      List<WebElement> servicioList = driver.findElements(By.cssSelector(".btn.btn-default.btn-sm"));  
      for (WebElement UnS : servicioList) {  
        if (UnS.getText().toLowerCase().contains("export to excel")||UnS.getText().toLowerCase().contains("exportar a excel")) {  
          UnS.click();  
          break;  
        }  
      }  
      try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}  
      usuario=usuario.replace(' ', '_');  
      usuario=usuario.concat("-Plan_de_Acci\u00f3n.xls");  
      assertTrue(pcp.isFileDownloaded(downloadPath, usuario), "Failed to download Expected document");  
    }  
	 
	 @Test(groups = "SCP",priority=3) 
	  public void TS112799_Plan_Accion_Ver_Video() { 
	    SCP pcp = new SCP(driver); 
	    boolean botonG = false; 
	    pcp.moveToElementOnAccAndClick("cuartoTitulo",2); 
	    List<WebElement> botones = driver.findElements(By.cssSelector(".btn.btn-xs.btn-default")); 
	    for (WebElement UnS : botones) { 
	      if (UnS.getText().toLowerCase().contains("ver video")) { 
	        botonG = true; 
	        UnS.click(); 
	        try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	      } 
	    } 
	    assertTrue(botonG); 
	    ArrayList<String> allTabs = new ArrayList<String>(driver.getWindowHandles()); 
	      driver.switchTo().window(allTabs.get(1)); 
	      assertTrue(driver.getCurrentUrl().contains("youtube.com"));
	        assertTrue(driver.getTitle().contains("Telecom")); 
	    driver.close(); 
	    driver.switchTo().window(allTabs.get(0)); 
	    try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();} 
	  } 

}
