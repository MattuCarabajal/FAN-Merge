package Tests;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.SCP;
import Pages.setConexion;

public class SCP_Joaquin extends TestBase{
	private WebDriver driver;
	
	
	
	@BeforeMethod(groups = {"SCP", "Filtros"})
	  public void setUp() throws Exception {
	    try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    SCP prueba= new SCP(driver);
	   // prueba.goToMenu("SCP");
	    prueba.clickOnTabByName("cuentas");
	    prueba.clickOnFirstAccRe();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
	}
	
	@AfterClass(groups = {"SCP", "Filtros"})
	public void teardown() {
		driver.quit();
		sleep(3000);
	}
	
	
	@BeforeClass(groups = {"SCP", "Filtros"})
	public void Init() throws Exception
	{
	  this.driver = setConexion.setupEze();
	  loginSCPConPermisos(driver);
	  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	private boolean isFileDownloaded_Ext(String dirPath, String ext){
		boolean flag=false;
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        flag = false;
	    }
	    
	    for (int i = 1; i < files.length; i++) {
	    	if(files[i].getName().contains(ext)) {
	    		flag=true;
	    	}
	    }
	    return flag;
	}
	
	
	@Test(groups = "SCP", priority = 2)
	public void TS112605_Cronograma_de_cuenta_Agregar_Evento_Para_Nuestros_Clientes() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Evento Para Nuestros Clientes");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Evento Para Nuestros Clientes')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = "SCP", priority = 2)
	public void TS112606_Cronograma_de_cuenta_Agregar_Evento_Relevante_de_la_Industria() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Evento Relevante de la Industria");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Evento Relevante de la Industria')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = "SCP", priority = 2)
	public void TS112607_Cronograma_de_cuenta_Agregar_Otros() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Otro");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Otro')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = "SCP", priority = 2)
	public void TS112608_Cronograma_de_cuenta_Agregar_Publicacion_RFP_Comunicaciones() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Publicación RFP Comunicaciones");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Publicación RFP Comunicaciones')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = "SCP", priority = 2)
	public void TS112609_Cronograma_de_cuenta_Agregar_Publicacion_RFP_TI() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Publicación RFP TI");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Publicación RFP TI')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = "SCP", priority = 2)
	public void TS112611_Cronograma_de_cuenta_Agregar_Vencimiento() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Vencimiento Contrato Activo con Nosotros");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Vencimiento Contrato Activo con Nosotros')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = "SCP", priority = 2)
	public void TS112612_Cronograma_de_cuenta_Agregar_Vencimiento_Contrato_Activo_con_la_Competencia() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("segundoTitulo", 3);
		
		sleep(1000);
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
		
		sleep(1000);
		driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
		
		Select categoria = new Select(driver.findElement(By.xpath("//select[@class='resetHito']")));
		categoria.selectByVisibleText("Vencimiento Contrato Activo con la Competencia");
		driver.findElement(By.xpath("//textarea[@class='resetHito']")).sendKeys("Agregar filtro");
		driver.findElement(By.cssSelector(".btn-primary")).click();
		
		sleep(2500);
		List<WebElement> elementos = driver.findElements(By.xpath("//td[@class='data2Col']//tr[contains(.,'Vencimiento Contrato Activo con la Competencia')]"));
		for (WebElement elem : elementos) {
			if (elem.getText().contains("Agregar filtro")) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112616_Cronograma_de_cuenta_Filtros_Evento_Para_Nuestros_Clientes() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(5).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("colorEvento"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112617_Cronograma_de_cuenta_Filtros_Evento_Relevante_de_la_Industria() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(3).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("eventoRelevante"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112618_Cronograma_de_cuenta_Filtros_Multiples() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(7).click();
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(0).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("Otros") || e.getAttribute("class").contains("Vencimiento"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112619_Cronograma_de_cuenta_Filtros_Otros() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(7).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("Otros"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112620_Cronograma_de_cuenta_Filtros_Publicación_RFP_Comunicaciones() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(4).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("RFPComunicaciones"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112621_Cronograma_de_cuenta_Filtros_Publicación_RFP_TI() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(6).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("RFPTI"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112623_Cronograma_de_cuenta_Filtros_Todos() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElement(By.className("todos")).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			String atributoClass = e.getAttribute("class");
			Assert.assertTrue(atributoClass.contains("Otros") || atributoClass.contains("colorEvento") ||
							  atributoClass.contains("eventoRelevante") || atributoClass.contains("RFPComunicaciones") ||
							  atributoClass.contains("RFPTI") || atributoClass.contains("Vencimiento") ||
							  atributoClass.contains("colorVencimientoCompetencia") || atributoClass.contains("colorServicio"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112624_Cronograma_de_cuenta_Filtros_Vencimiento() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(0).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("Vencimiento"));
		}
	}
	
	@Test(groups = {"SCP", "Filtros"}, priority = 2)
	public void TS112625_Cronograma_de_cuenta_Filtros_Vencimiento_Contrato_Activo_con_la_Competencia() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(2).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container"));
		
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("colorVencimientoCompetencia"));
		}
	}
	
	@Test(groups = "SCP", priority = 3)
	public void TS112729_Negocio_del_cliente_Contexto_Estratégico_del_Cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(1);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Contexto Estratégico del Cliente"));
	}
	
	@Test(groups = "SCP", priority = 3)
	public void TS112732_Negocio_del_cliente_Desafios_Iniciativas_de_negocio_del_cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(2);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Desafíos | Iniciativas de Negocio del Cliente"));
	}
	
	@Test(groups = "SCP", priority = 3)
	public void TS112735_Negocio_del_cliente_Descripción_del_Cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(0);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Descripción del Cliente"));
	}
	
	@Test(groups = "SCP", priority = 3)
	public void TS112739_Negocio_del_cliente_Evolución_de_la_satisfacción_del_cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(4);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Evolución de la Satisfacción del Cliente"));
	}
	
	@Test(groups = "SCP", priority = 3)
	public void TS112742_Negocio_del_cliente_Exportar_a_Excel() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		driver.findElements(By.cssSelector(".botones")).get(4).click();
		sleep(2000);
		driver.findElements(By.xpath("//button[@class='btn btn-default btn-sm']")).get(1).click();
		sleep(3000);
		
		Assert.assertTrue(isFileDownloaded_Ext("C:\\Users\\florangel\\Downloads\\", ".xls"));
	}
	
	@Test(groups = "SCP", priority = 3)
	public void TS112743_Negocio_del_cliente_Guardar() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		driver.findElements(By.cssSelector(".botones")).get(4).click();
		sleep(2000);
		int i = 0;
		List<WebElement> tablas = driver.findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
		for (WebElement t : tablas) {
			if (t.isDisplayed()) {
				WebElement tbody = t.findElement(By.tagName("tbody"));
				WebElement celda = tbody.findElement(By.tagName("span")).findElement(By.tagName("span"));
				Actions builder = new Actions(driver);
				builder.moveToElement(celda).doubleClick().build().perform();
				celda.findElement(By.tagName("input")).sendKeys("test");
				driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
				sleep(2000);
				celda.findElement(By.tagName("input")).submit();
				break;
			}
			i++;
		}
		driver.findElements(By.cssSelector(".botones")).get(4).click();
		sleep(2000);
		List<WebElement> tablas2 = driver.findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
		WebElement tbody = tablas2.get(i).findElement(By.tagName("tbody"));
		WebElement celda = tbody.findElement(By.tagName("span")).findElement(By.tagName("span"));
		
		Assert.assertTrue(celda.getText().equalsIgnoreCase("test"));
	}
}
