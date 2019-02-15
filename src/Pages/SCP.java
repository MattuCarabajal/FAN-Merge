package Pages;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import Tests.TestBase;
import Tests.TestBase.waitFor;

public class SCP extends BasePage {
	final WebDriver driver;
	private static String downloadPath = "C:\\Users\\Pablo\\Downloads";
	private Select listSelect;
	 private List<WebElement> accountsList;
	
	@FindBy (how = How.ID, using = "Account_Tab")
	private WebElement tabAccount; //pestania cuentas
	
	@FindBy (how = How.ID, using = "Contact_Tab")
	private WebElement tabContact; //pestania contacto
	
	@FindBy (how = How.ID, using = "Opportunity_Tab")
	private WebElement tabOpportunity; //pestania oportunidad
	
	@FindBy (how = How.ID, using = "AllTab_Tab")
	private WebElement allTab; //pestania +    wt-Strategic-Client-Plan
	
	@FindBy (how = How.CLASS_NAME, using = "wt-Strategic-Client-Plan")
	private WebElement StrategicClient; //pestania plan de estrategia de cliente   
	
	//Constructor
	  public SCP(WebDriver driver){
	    this.driver = driver;
	        PageFactory.initElements(driver, this);
	}
	
	  //Hace click en el tab buscado por nombre
	public void clickOnTabByName(String tabName) {
		switch (tabName.toLowerCase()) {
		case "cuentas":
			tabAccount.click();
		break;
		case "contactos":
			tabContact.click();
		break;
		case "oportunidades":
			tabOpportunity.click();
		break;
		case "estrategia cliente":
			StrategicClient.click();
		break;
		case "+":
			allTab.click();
		break;
		}
    }
	
	//selecciona la visualizacion de la lista de acuerdo al texto
	public void listTypeAcc(String listaBuscar) {
		//driver.switchTo().defaultContent();
		//driver.switchTo().frame(getFrameForElement(driver, By.id("fcf")));
		listSelect = new Select(driver.findElement(By.tagName("select")));
		if (driver.findElement(By.id("fcf")).getText().equals(listaBuscar)) {
			driver.findElement(By.id("fcf")).findElement(By.className("btn")).click();
		}else {
			listSelect.selectByVisibleText(listaBuscar);//ojo con mayusculas y minusculas
		}
	}
	
	//selecciona la primera cuenta en la lista de cuentas recientes
	public void clickOnFirstAccRe() {
		driver.findElement(By.className("hotListElement")).findElement(By.cssSelector(".dataRow.even.first")).findElement(By.tagName("a")).click();
	}
	
	//selecciona la primera oportunidad en la lista de oportunidades recientes
	public void firstoportunidad() {
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		WebElement element = driver.findElement(By.cssSelector(".bRelatedList.first"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
		driver.findElement(By.cssSelector(".bRelatedList.first")).findElement(By.cssSelector(".dataRow.even.first")).findElement(By.tagName("a")).click();
	}
	
	public void elegiroportunidad(String oportunidad) {
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement element = driver.findElement(By.cssSelector(".bRelatedList.first"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
		//driver.findElement(By.cssSelector(".pShowMore")).findElements(By.tagName("a")).get(1).click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		element = driver.findElement(By.cssSelector(".listRelatedObject.opportunityBlock"));
		List<WebElement> op = element.findElements((By.cssSelector(".dataCell")));
		for(WebElement e : op){
			System.out.println(e.getText());
			if(e.getText().equals(oportunidad)){
				e.findElement(By.tagName("a")).click();
				break;}}
	}
	
	
	public void clickEnCuentaPorNombre(String name)
	{
		boolean enc = false;
		List<WebElement> cuentas = driver.findElement(By.className("hotListElement")).findElements(By.cssSelector(".dataRow.odd"));
		cuentas.add(driver.findElement(By.className("hotListElement")).findElement(By.cssSelector(".dataRow.even.first")));
		cuentas.add(driver.findElement(By.className("hotListElement")).findElement(By.cssSelector(".dataRow.even")));
		for (WebElement unaCuenta : cuentas) {
			System.out.println("Cuenta:"+ unaCuenta.findElement(By.tagName("a")).getText());
			if(unaCuenta.findElement(By.tagName("a")).getText().toLowerCase().contains(name.toLowerCase())) {
				unaCuenta.findElement(By.tagName("a")).click();
				enc = true;
				break;
			}
		}
		if (enc == false)
			clickOnFirstAccRe();
	}
	
	public void goToMenu(String Name) {
	    
		  try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		  Name= Name.toLowerCase();
		  String actual = driver.findElement(By.id("tsidLabel")).getText();
		  if(Name.equals("ventas")) {
			  if(actual.toLowerCase().contains("sales"))
					  return;
		  }else {
			  if (actual.toLowerCase().contains(Name)){
				  return;}
			  else {
				    driver.findElement(By.id("tsid")).click();
				    try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				    switch(Name) {
				    case "scp": driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u3F000000CjqC\"]")).click();
				    break;
				    
				    case "ventas": driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u41000000QWha\"]")).click();
				    break;
				    
				    case "marketing": driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u41000000QWhf\"]")).click();
				    break;
				    
				    case "salesforce chatter": driver.findElement(By.xpath("//a[@href=\"/home/home.jsp?tsid=02u41000000QWhg\"]")).click();
				    break;
				    
				    case "service cloud console": driver.findElement(By.xpath("//a[@href=\"/console?tsid=02u41000000QWhZ\"]")).click();
				    break;
				    
				    case "chatter answers moderator": driver.findElement(By.xpath("//a[@href=\"/console?tsid=02u41000000QWhc\"]")).click();
				    break;
				    
				    case "appexchange": driver.findElement(By.xpath("//a[@href=\"https://appexchange.salesforce.com\"]")).click();
				    break;
				    
				    case "comunidad de desarrolladores": driver.findElement(By.xpath("//a[@href=\"http://developer.force.com\"]")).click();
				    break;
				    
				    case "success community": driver.findElement(By.xpath("//a[@href=\"https://success.salesforce.com\"]")).click();
				    break;
				}
		    }}
		  }
	
	/**
	 * El xpath debe ser construido en el metodo que invoque moveToElementOnAccAndClick y debe ser enviado como cadena por parametros, el parametro se llama referencia
	 * @param identificador
	 * @param referencia
	 */
	
	public void moveToElementOnAccAndClick(String identificador, int i) {
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage Bp= new BasePage();
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Bp.getFrameForElement(driver, By.id(identificador)));
		
		WebElement idele= driver.findElement(By.id(identificador));
		if(!(identificador.equals("primerTitulo"))) {
			idele.click();
		}
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		idele.findElements(By.tagName("a")).get(i).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
	}
	
	public void goTop() {
		  WebElement subir = driver.findElement(By.id("tsidButton"));
		  ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+subir.getLocation().y+")");
	}
	
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	  	    
	    for (int i = 0; i < dir_contents.length; i++) {
	        if (dir_contents[i].getName().contains(fileName))
	            return flag=true;
	            }

	    return flag;
	}
	
	public boolean goToOportunity() {
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		if(driver.findElement(By.cssSelector(".dataCell.droppableTD.ui-droppable.sorting_1")).isDisplayed()) {
			WebElement oportunidad=driver.findElement(By.cssSelector(".dataCell.droppableTD.ui-droppable.sorting_1")).findElement(By.tagName("a"));
			oportunidad.click();
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			if(driver.findElement(By.id("bodyCell")).isDisplayed())
				return true;
			else
				return false;
		}
		else return false;
	}
	public void moveToElementOnAccAndClick(String identificador, String referencia) {
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage Bp= new BasePage();
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(Bp.getFrameForElement(driver, By.id(identificador)));
		WebElement idele= driver.findElement(By.id(identificador));
		idele.click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		idele.findElement(By.xpath(referencia)).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public boolean validarservicioborrado(String categoria, String servicio, String color){
		boolean a = true;
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		List<WebElement> tabla = driver.findElements(By.xpath("//*[@id='tableList']/tbody/tr"));
		//Baja Hasta el servicio creado y lo borra
	for(WebElement S:tabla) {
		//System.out.println(S.getText());
		String D=S.getText().replaceAll(" ","").replaceAll("\r\n", "");
		//System.out.println(D);
		if(D.contains("01/01/2016")) {
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+S.getLocation().y+")");
				S.findElement(By.cssSelector(".btn.btn.btn-default.btn-sm")).click();
				break;
			}
	}
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	//Verifica si quedo vacia la lista y returna verdadero
	List<WebElement> Servicios=driver.findElements(By.xpath("//*[@id=\"tableList\"]/tbody/tr"));
	if(Servicios.isEmpty())
		return true;
	//Sino quedo vacia, verifica que no este disponible el que creo.
	for(WebElement S:Servicios) {
		//System.out.println(S.getText());
		String D=S.getText().replaceAll(" ","").replaceAll("\r\n", "");
		//System.out.println(D);
		if(D.contains("01/01/2016"))
			return false;
		}
	return a;
	}
	
	public void nuevoservicio(String categoria, String servicio, String color){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		driver.switchTo().defaultContent();
		setSimpleDropdown(driver.findElement(By.name("j_id0:j_id89:j_id106")), categoria);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(driver.findElement(By.id("j_id0:j_id89:serviciosRender")), servicio);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(driver.findElement(By.id("j_id0:j_id89:j_id113")), color);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("dateFormat")).getLocation().y+")");
		driver.findElement(By.className("dateFormat")).click();
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).getLocation().y+")");
		driver.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	}
	
	/**
	 * Se Creo para usar en el caso 112782 de Parque de servicio usando la fecha 01/01/16 
	 * @param categoria
	 * @param servicio
	 * @param color
	 */
	public void nuevoservicioEspecifico(String categoria, String servicio, String color){
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

		driver.switchTo().defaultContent();
		setSimpleDropdown(driver.findElement(By.name("j_id0:j_id89:j_id106")), categoria);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(driver.findElement(By.id("j_id0:j_id89:serviciosRender")), servicio);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(driver.findElement(By.id("j_id0:j_id89:j_id113")), color);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.className("dateFormat")).getLocation().y+")");
		
		driver.findElement(By.id("j_id0:j_id89:datepicker")).sendKeys("01/01/2016");
		
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).getLocation().y+")");
		driver.findElement(By.cssSelector(".glyphicon.glyphicon-plus")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	}
	
	public boolean validarservicionuevo(String categoria, String servicio, String color){
		boolean a = false;
		List<WebElement> tabla = driver.findElements(By.xpath("//*[@id='tableList']/tbody/tr"));
		//System.out.println(tabla.get((tabla.size()-1)).getText());
		if(!driver.findElement(By.xpath("//*[@id='tableList']/tbody/tr["+(tabla.size()-1)+"]")).getText().contains(servicio+categoria+color)){
			a = true;
		}

			return a;
		}
	
public void servicioexportarexcel(){
		
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id='j_id0:j_id89:j_id91']/div/div/button[2]")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    Assert.assertTrue(isFileDownloaded_Ext(downloadPath, "Parque_de_servicios.xls"), "Failed to download document which has extension .xls");
	}
public void servicioguardar(){
	try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.findElement(By.xpath("//*[@id='j_id0:j_id89:j_id91']/div/div/button[1]")).click();	
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

public void comentarycompartir(String comentario){
	TestBase TB = new TestBase();
	TB.waitFor(driver, By.cssSelector(".publisherTextAreaInner"));
	WebElement element = driver.findElement(By.cssSelector(".publisherTextAreaInner"));
	((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
	TB.waitFor(driver, By.id("publishereditablearea"));
	driver.findElement(By.id("publishereditablearea")).click();
	TB.waitFor(driver, By.tagName("iframe"));
	WebElement iframe =driver.findElement(By.tagName("iframe"));
	driver.switchTo().frame(iframe);
	WebElement description;
	try {description=driver.findElement(By.xpath("//body[@class='chatterPublisherRTE cke_editable cke_editable_themed cke_contents_ltr cke_show_borders placeholder']"));
	}catch(org.openqa.selenium.NoSuchElementException ex1){description=driver.findElement(By.xpath("//body[@class='chatterPublisherRTE cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"));
	}
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	description.click();
	description.sendKeys(comentario);
	driver.switchTo().defaultContent();
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	driver.findElement(By.id("publishersharebutton")).click();
	
}

public void validarcomentario(String comentario){
	try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

	List <WebElement> comentarios = driver.findElements(By.cssSelector(".feeditemtext.cxfeeditemtext"));
	System.out.println(comentarios.size());
	Assert.assertTrue(comentarios.get(0).getText().equals(comentario));
	Assert.assertEquals(driver.findElement(By.cssSelector(".feeditemtopics")).getText(), "Haga clic para agregar temas:   Sin sugerencias. A\u00f1ada sus propios temas.");
}


public void validarcomentarioajeno(String comentario){
	try {Thread.sleep(15000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	String cuentaactiva = driver.findElement(By.id("userNavLabel")).getText();
	List <WebElement> comentarios = driver.findElements(By.cssSelector(".feeditemcontent.cxfeeditemcontent"));
	((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+comentarios.get(0).getLocation().y+")");
	Assert.assertTrue(comentarios.get(0).findElement(By.cssSelector(".feeditemtext.cxfeeditemtext")).getText().equals(comentario));
	Assert.assertFalse(comentarios.get(0).findElement(By.cssSelector(".feeditemfirstentity")).getText().equals(cuentaactiva));

}

public boolean cuentalogeada(String cuenta){
	boolean a=false;
  TestBase TB = new TestBase();
  TB.waitFor(driver, By.id("userNavLabel"));
	String cuentaactiva = driver.findElement(By.id("userNavLabel")).getText();
	if(cuentaactiva.equals(cuenta)){
		a=true;}
	return a;}




	public void Desloguear_Loguear(String usuario) {
		driver.findElement(By.id("userNav")).click();
		TestBase TB = new TestBase();
		List<WebElement> opcionesMenu = driver.findElement(By.id("userNav-menuItems")).findElements(By.tagName("a"));
		for (WebElement UnaO : opcionesMenu) {
			if(UnaO.getText().toLowerCase().contains("finalizar sesi\u00f3n")) {
				UnaO.click();
				break;
			}
		}
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("userDropdown")).click();
		sleep(3000);
		driver.findElement(By.id("logout")).click();
		sleep(5000);
		driver.get(TestBase.urlSCP);
		if (usuario.toLowerCase().contains("fabiana"))
			TB.loginSCPUsuario(driver);
		else
			if (usuario.toLowerCase().contains("isabel"))
				TB.loginSCPAdminServices(driver);
			else
				TB.loginSCPConPermisos(driver);
	}
	
	public void Desloguear_Loguear_Comentar(String usuario, String otroUsuario, String comentario, String identificador, int indice) {
		Desloguear_Loguear(otroUsuario);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickOnTabByName("cuentas");
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		clickEnCuentaPorNombre("Cuenta Bien Hecha SCP");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		moveToElementOnAccAndClick(identificador,indice);
		comentarycompartir(comentario);
		Desloguear_Loguear(usuario);
	}
	
	public List<String> TraerColumna(WebElement wBody, int iColumnas, int iColumna) {
		//wBody = WebElement del cuerpo completo del cuadro
		//iColumnas = cantidad de columnas
		//iColumna = columna requerida
		WebElement wSubBody = wBody.findElement(By.tagName("tbody"));
		List<WebElement> wRows = wSubBody.findElements(By.tagName("tr"));
		List<WebElement> wElements =   new ArrayList<WebElement>();
		for (int i = 0; i < wRows.size(); i++) {
			wElements.clear();
			wElements = wRows.get(i).findElements(By.tagName("td"));
			if (wElements.size() < iColumnas) {
				wRows.remove(i);
			}
		}
		List<String> sElements =  new ArrayList<String>();
		for (WebElement wAux:wRows) {
			if (!wAux.getText().isEmpty()) {
				List<WebElement> wColumns = wAux.findElements(By.tagName("td"));
				sElements.add(wColumns.get(iColumna - 1).getText().toLowerCase());
			}
		}
		
		return sElements;
	}
	
	public boolean Triangulo_Ordenador_Validador(WebDriver driver, By eBody, int iColumns, int iColumn) throws ParseException {
		//eBody = selector del cuerpo completo del cuadro
		//iColumnas = cantidad de columnas
		//iColumna = columna a ordenar
		TestBase TB = new TestBase();
		TB.waitFor(driver, eBody);
		WebElement wBody = driver.findElement(eBody);
		List<String> sElements = TraerColumna(wBody, iColumns, iColumn);
		WebElement wHeader = driver.findElement(eBody).findElement(By.tagName("thead"));
		List <WebElement> wMenu = wHeader.findElements(By.tagName("th"));
		wMenu.get(iColumn - 1).click();
		List<String> wOrderedElements = TraerColumna(wBody, iColumns, iColumn);
		boolean bBoolean = true;
		if (!wMenu.get(iColumn - 1).getText().contains("Fecha")) {
			Collections.sort(sElements);
			
			for(int i = 0; i < sElements.size(); i++) { 
				if (!sElements.get(i).toLowerCase().equals(wOrderedElements.get(i).toLowerCase())) {
					System.out.println("'" + sElements.get(i) + "'\tes igual a\t'" + wOrderedElements.get(i) + "'");
				 bBoolean = false;
				}
			}
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ArrayList<String> fechas = new ArrayList<String>();
			for (String sAux:sElements) {
				fechas.add(sAux.split(" ")[0]);
			}
			
			  String fech = new String();
			     String fecha2= new String();
			     Date date1 = new Date();//sdf.parse(fech); 
			     Date date2 = new Date(); //sdf.parse(fecha2); 
			     for(int i = 0; i<=fechas.size()-1;i++) {
			      fech=fechas.get(i);
			      fecha2=fechas.get(i+1);
			      date1 = sdf.parse(fech);
			      date2 = sdf.parse(fecha2);
			      if(date1.compareTo(date2)>0) {
			          System.out.println(date2+" es menor que "+date1);
			          Assert.assertTrue(false);
			      }
			     }
			       Assert.assertTrue(true);
		}
		
		return bBoolean;
		
	}
	
	public String SelectorMasUno(String sText, int iPosition, int iIndex) {
		//sTexto = texto completo
		//iPosici�n = posici�n en string sTexto donde tiene que cambiar el valor
		//iIndex = nuevo valor
		sText = sText.substring(0,iPosition) + iIndex + sText.substring(iPosition + 1,sText.length());
		return sText;
	}
	
	public String[][] ListaById(By element, String sId, int iPosition) {
		List<WebElement> wAuxList = driver.findElements(element);
		
		String[][] sList = new String [wAuxList.size()][wAuxList.size()];
		
		int i;
		for (i = 0; i < wAuxList.size(); i++) {
			sId = SelectorMasUno(sId, iPosition, i);
			sList[i][0] = sId;
			sList[i][1] = driver.findElement(By.id(sId)).getText();
			
		}
		
		return sList;
	}
	
public void ValidarEstadosDELTA(String DELTA){
	TestBase TB = new TestBase();
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

	TB.waitFor(driver, By.cssSelector(".brandTertiaryBrd.pbSubheader.tertiaryPalette"));
	WebElement element = driver.findElement(By.cssSelector(".brandTertiaryBrd.pbSubheader.tertiaryPalette"));
	  ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.xpath("/html/body/table/tbody/tr[1]")));		  
		switch(DELTA){
		case "oportunidad":
			Assert.assertEquals(driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/th[1]")).getText(), "Precio Total Contrato (Oportunidad - ARG)");
			break;
		case"proyecto":
			Assert.assertEquals(driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/th[2]")).getText(), "Precio Total Contrato (Proyectos - ARG)");
			break;
		}
	driver.switchTo().defaultContent();

}

public void validarcompetidores(){
	try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	boolean acc = false; //Accion
	boolean nmbre = false; //Nombre del competidoor
	boolean pntf = false;  //Puntos Fuertes
	boolean pntd = false;  //Puntos Debiles
	WebElement element = driver.findElement(By.cssSelector(".listRelatedObject.opportunityCompetitorBlock"));
	((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
	List<WebElement> col = element.findElements(By.tagName("th"));
	for(WebElement e: col){
		if(e.getText().equals("Acci\u00f3n")){
			acc = true;
			System.out.println(e.getText());}
		
		if(e.getText().equals("Nombre del competidor")){
			nmbre = true;
			System.out.println(e.getText());}
		
		if(e.getText().equals("Puntos fuertes")){
			pntf = true;
			System.out.println(e.getText());}
		
		if(e.getText().equals("Puntos d\u00e9biles")){
			pntd= true;
			System.out.println(e.getText());}}	
Assert.assertTrue(acc&&nmbre&&pntf&&pntd);
	}

	public void validarinfoventas(){
		List<WebElement> secc = driver.findElements(By.cssSelector(".pbSubsection"));
		WebElement element = secc.get(1);
		 ArrayList<String> txt1 = new ArrayList<String>();
		 ArrayList<String> txt2 = new ArrayList<String>();
		 txt2.add("Tipo");
		 txt2.add("Raz\u00f3n perdida");
		 txt2.add("Estado de la oportunidad");
		 txt2.add("Creado por");
		 txt2.add("Propietario de oportunidad");
		 txt2.add("\u00daltima modificaci\u00f3n por");
		 txt2.add("Descripci\u00f3n");

		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
		List<WebElement> vnts = element.findElements(By.cssSelector(".labelCol"));
		for(WebElement e: vnts){
			System.out.println(e.getText());
			 txt1.add(e.getText());
		 }
		 Assert.assertTrue(txt1.containsAll(txt2));
	}

		//Entra desde una cuenta > oportunidad > productos
	public void IngresarAlProductos(String producto){
		boolean exist = false;
		WebElement element = driver.findElement(By.cssSelector(".listRelatedObject.opportunityLineItemBlock"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
		List<WebElement> op = element.findElements((By.cssSelector(".dataCell")));
		for(WebElement e : op){
			System.out.println(e.getText());
			if(e.getText().equals(producto)){
				e.findElement(By.tagName("a")).click();
				break;}}
	}
	
	//Entra desde una cuenta > oportunidad > productos
	public void ModificarProducto(String ModificarEliminar, String campo , String dato, String GuardarCancelar){
		List<WebElement> btns = driver.findElements(By.cssSelector(".btn"));
		for(WebElement e: btns){
			if(e.getAttribute("value").toLowerCase().equals(ModificarEliminar.toLowerCase())){
				e.click();
				if(ModificarEliminar.toLowerCase().equals("eliminar")){
					 Alert alert = driver.switchTo().alert();
					   alert.accept();}
				break;}}
		Actions action = new Actions(driver);   
		switch(campo.toLowerCase()){
		case "precio":
			driver.findElement(By.id("UnitPrice_ileinner")).click();
			action.moveToElement(driver.findElement(By.id("UnitPrice_ileinner"))).doubleClick().perform();
			driver.findElement(By.id("UnitPrice")).clear();
			driver.findElement(By.id("UnitPrice")).sendKeys(dato);
				break;
		case "cantidad":
			driver.findElement(By.id("Quantity")).clear();
			driver.findElement(By.id("Quantity")).sendKeys(dato);
				break;
		case "plazo":
		
			driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(6).findElement(By.tagName("input")).clear();
			driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(6).findElement(By.tagName("input")).sendKeys(dato);
				break;
		case "cargo unico":			  
			driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(7).findElement(By.tagName("input")).clear();
			driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(7).findElement(By.tagName("input")).sendKeys(dato);
				break;
		case "moneda":
	
			setSimpleDropdown(driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(7).findElement(By.tagName("select")), dato);
				break;
		case "descripcion":			  
		
			driver.findElement(By.id("Description_ileinneredit")).clear();
			driver.findElement(By.id("Description_ileinneredit")).sendKeys(dato);
				break;
		case "comentarios":			  
		
			driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(12).findElement(By.tagName("input")).clear();
			driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(12).findElement(By.tagName("input")).sendKeys(dato);
				break;}
		
		List<WebElement> btns2 = driver.findElements(By.cssSelector(".btn"));
		for(WebElement e: btns2){
			if(e.getAttribute("value").toLowerCase().equals(GuardarCancelar.toLowerCase())){
				e.click();
			break;}
			}
	}
	
	public void VerificarCampoModificado(String campo , String dato){
		switch(campo.toLowerCase()){
		case "precio":
		Assert.assertTrue(driver.findElement(By.id("UnitPrice_ileinner")).getText().contains(dato));
				break;
		case "cantidad":
			Assert.assertTrue(driver.findElement(By.id("Quantity_ileinner")).getText().contains(dato));
				break;
		case "plazo":
			Assert.assertTrue(driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(6).findElement(By.tagName("div")).getText().contains(dato));
				break;
		case "cargo unico":		
			Assert.assertTrue(driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(7).findElement(By.tagName("div")).getText().contains(dato));
				break;
		case "moneda":
			Assert.assertTrue(driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(8).findElement(By.tagName("div")).getText().contains(dato));
				break;
		case "descripcion":			  
			Assert.assertTrue(	driver.findElement(By.id("Description_ileinner")).getText().contains(dato));
				break;
		case "comentarios":			  
			Assert.assertTrue(driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(12).findElement(By.tagName("div")).getText().contains(dato));
				break;}
	}
	
	public String CargosTotalesPorMes(){
		//TestBase TB = new TestBase();
		sleep(5000);
		String a;
		a = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(3).getText();
		return a;
	}
	
	public void ValidarMontoContrato(){
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String CUV0 = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(7).findElement(By.tagName("div")).getText().substring(1).replaceAll(",", ".");
		System.out.println("Cargo unico: "+CUV0);

		double CUV= Double.parseDouble(CUV0); 
		String cant0 = driver.findElement(By.id("Quantity_ileinner")).getText().replaceAll(",", ".");
		System.out.println("cantidad: "+cant0);
		double cant = Double.parseDouble(cant0);
		int plazo = Integer.parseInt(driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(6).findElement(By.tagName("div")).getText()); 
		System.out.println("plazo: "+ plazo);
		String abono0 =driver.findElement(By.id("UnitPrice_ileinner")).getText().substring(1).replaceAll(",", ".");
		System.out.println("abono: "+abono0);
		double abono = Double.parseDouble(abono0);	 
		String TC = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("div")).get(1).getText().substring(1).replace(".","");
		TC = (TC.replaceAll(",", "."));
		System.out.println("totalcontrato: "+TC);
		double TotalContrato = Double.parseDouble(TC); 
		double FORMULA = (cant*plazo*abono)+CUV;
		String contrato = Double.toString(TotalContrato);
		String resultado = Double.toString(FORMULA);
		System.out.println("resultado formula: "+resultado);
		System.out.println("aparece en la app: "+contrato);

		Assert.assertEquals(contrato, resultado);
		}
	//   
	public String SacarTotalMesXPlazo(){
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String antes = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(3).findElements(By.tagName("div")).get(1).getText().substring(1).replace(".","");
		System.out.println("Antes: "+antes);
		return antes;
	}
	
	public void ValidarTotalMesXPlazo(String antes){
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		String despues = driver.findElement(By.className("detailList")).findElements(By.tagName("tr")).get(3).findElements(By.tagName("div")).get(1).getText().substring(1).replace(".","");
		System.out.println("Antes: "+antes);
		Assert.assertFalse(despues.equals(antes));
	}
	
	public void selectOporunity(String oportunidad) {
		clickOnTabByName("oportunidades");
		sleep(3000);
		Select sOp=new Select(driver.findElement(By.id("fcf")));
		sOp.selectByVisibleText("Todas las oportunidades");
		sleep(200);
		driver.findElement(By.name("go")).click();
		sleep(2000);
		List<WebElement> lOp=driver.findElements(By.className("x-grid3-row"));
		lOp.add(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-first")));
		boolean flag=false;
		for(WebElement nOp: lOp) {
			List<WebElement> tagsOp=nOp.findElements(By.tagName("a"));
			for(WebElement nombre:tagsOp) {
			if(nombre.getText().toLowerCase().contains(oportunidad.toLowerCase())) {
				nombre.click();
				flag=true;
				break;}
				}
			if(flag)
				break;
			}
		if(!flag) {System.out.println("Oportunidad: "+oportunidad+" No encontrada.");}
		sleep(3000);
	}
	
	public boolean verificarExistenciaDeCuenta(String NombreCuenta) {
		clickOnTabByName("cuentas");
		sleep(2000);
		driver.findElement(By.name("go")).click();
		sleep(3000);
		List<WebElement> listaDeCuentas=driver.findElements(By.className("x-grid3-row-table"));
		listaDeCuentas.add(driver.findElement(By.cssSelector(".x-grid3-row.x-grid3-row-first")));
		for(WebElement list:listaDeCuentas) {
			//System.out.println(list.getText());
			if(list.getText().toLowerCase().contains(NombreCuenta.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	
	public void EjecutarInterfazBatch() {
		Runtime aplicacion = Runtime.getRuntime(); 
        try{aplicacion.exec("cmd.exe /K start C:\\CargarDatosSCP.bat"); 
        	System.out.println("Carga de Datos en Interfaces Batch Realizada");}
        catch(Exception e){System.out.println(e);}
	}
	
	public void scroll(By elemento) {
			  try {
			  ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(elemento).getLocation().y+")");
			  }catch(org.openqa.selenium.NoSuchElementException e) {
				  System.out.print("No se puede hacer Scroll: ");
				  e.printStackTrace();
			  }
			  
	}
	
	public void CrearOportunidad(String NombreOportunidad) {
		sleep(1000);
		
		//Nombre Oportunidad
		WebElement NombreOp=driver.findElement(By.id("opp3"));
		NombreOp.sendKeys(NombreOportunidad);
		
		//Etapa
		Select etapa=new Select(driver.findElement(By.id("opp11")));
		etapa.selectByIndex(1);
		
		//Contacto (Pendiente con el ID)
		driver.findElement(By.id("CF00N4100000c3bM7")).sendKeys("Automatizacion");
		
		//Fechas
		List<WebElement> date=driver.findElements(By.className("dateFormat"));
		date.get(0).click();
    	date.get(1).click();
    	date.get(2).click();
    	sleep(2000);
    	//GuardarYNuevo
    	driver.findElement(By.name("save_new")).click();
    	sleep(4000);
	}
	
}