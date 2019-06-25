package PagesPOM;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;


public class BeFanPom extends BasePageFw {
	//elementos

	@FindBy(css= "[id=exportarTabla] tbody tr td button")
	private WebElement boton_VerDetalle;
	
	
	@FindAll({
		@FindBy(css= "[class*='tpt-nav'] [class='dropdown']")
	})
	private WebElement dropdown_toggle;
	

	@FindAll({
		@FindBy(css= "[class=col-lg-2] select")
	})
	private List<WebElement> elementosDesplegablesGestion;
	
	@FindAll({
		@FindBy(css= "[class='dropdown open'] ul div div ul li ")
	})
	private List<WebElement> elementosSims;
	
	@FindBy(css= "button[class='btn btn-primary']")
	private WebElement botonBuscarGestion;
	
	@FindBy(css= "tbody [class='text-center ng-scope']")
	private WebElement resultadoBusaquedaGestion;
	
	@FindBy(id= "dataPickerDesde")
	private WebElement inputFechaDesde;
	
	@FindBy(id= "dataPickerHasta")
	private WebElement inputFechaHasta;
	
	final String referenciaDetalle ="[class ='ng-scope block-ui block-ui-anim-fade'] div [class='modal-body'] tbody tr td";
	@FindBy(css= referenciaDetalle)
	private List<WebElement> elementos_Detalle;
	
	WebElement aux=null;//instancio una variable auxiliar para enviar elementos buscados internamente
	
	//constructor
	public BeFanPom(WebDriver driver) {
		super.driver = driver;
		super.setDriver(driver);
		
		PageFactory.initElements(driver, this);
		super.fluentWait = new FluentWait<WebDriver>(driver);
		
		super.fluentWait.withTimeout(30, TimeUnit.SECONDS)
		.pollingEvery(5, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class)
		.ignoring(NullPointerException.class)
		.ignoring(TimeoutException.class)
		.ignoring(ElementNotVisibleException.class);
		
		
	}
	
	//metodos get
	
	public WebElement getSims() {
								//debe retornar el Elemento que contenga el desplegable de sims
		fluentWait.until(ExpectedConditions.elementToBeClickable(dropdown_toggle));
		return dropdown_toggle;
	}
	
	public WebElement selectSims(String nombreOpcion) {
		//selecciona de los elementos de Sims el que coincide con el NOmbre deOpcion
		this.getSims().click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(elementosSims.get(0)));
		aux =super.getBuscarElementoPorText(elementosSims, nombreOpcion);
		
		return aux;
	}

	
	public WebElement getBotonBuscar_Gestion() {
		//Devuelve el Boton siguiente de la vista Gestion
		fluentWait.until(ExpectedConditions.elementToBeClickable(botonBuscarGestion));
		return botonBuscarGestion;
		
	}
	
	
	public void selectOpcion(WebElement desplegable, String nombreOpcionVisible) {
		//recibe un WebElement que lo pasa a Select y selecciona la opcion de acuerdo al String que debe coincidir con el texto Visible <-(ojo con esto)
		fluentWait.until(ExpectedConditions.elementToBeClickable(desplegable));
		super.getSelect(desplegable).selectByVisibleText(nombreOpcionVisible);
		
	}
	

	public boolean getMacheaText(List<WebElement> listaElementos, String textoaComparar) {
		
		return super.matchText(listaElementos, textoaComparar);		
	}
	
	public boolean containsText(List<WebElement> listaElementos, String textoaComparar) {
		
		return super.containsText(listaElementos, textoaComparar);		
	}	
	
	public WebElement getDesplegableEstado_Gestion() {
		//debe retornar el Elemento que contenga el desplegable de Estado
		fluentWait.until(ExpectedConditions.elementToBeClickable(elementosDesplegablesGestion.get(0)));
		return elementosDesplegablesGestion.get(0);
	}
	
	
	public WebElement getDesplegableRegion_Gestion() {
		//debe retornar el Elemento que contenga el desplegable de Region
		fluentWait.until(ExpectedConditions.elementToBeClickable(elementosDesplegablesGestion.get(1)));
		fluentWait.until(ExpectedConditions.visibilityOf(elementosDesplegablesGestion.get(1)));	
		return elementosDesplegablesGestion.get(1);
	}
	
	public List<WebElement> getDetalle_Gestion() {
		//retorna  la lista de elemento que se encuentra en la primer fila de tabla resultado de la busqueda Gestion
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(referenciaDetalle),1));
		return elementos_Detalle;
		
	}
	
	public WebElement getBotonVerDetalle_Gestion() {
		//retorna el primer boton VERDETALLE que encuentra
		fluentWait.until(ExpectedConditions.elementToBeClickable(boton_VerDetalle));
		return boton_VerDetalle;
	}
	
	public void editValorPorId_Js(String id ,String valor) {
		
		String codigoJs = "document.getElementById('"+id+"').value+='"+ valor+"';";
		super.ejecutarCodigoJs(codigoJs);
		
	}
	
	public void setFechaHasta_Gestion(String dia, String mes,String ano) {
		
		//String fecha=dia+'/'+mes+'/'+ano;<---------------------- Esto no Funciona ya que si se edita los valores solo por JS luego no busca la gestion
		String idFecha = inputFechaHasta.getAttribute("id");
		System.out.println(idFecha);
		if(ano.matches("\\d{4}") && mes.matches("\\d{2}") && dia.matches("\\d{2}")) {
			fluentWait.until(ExpectedConditions.elementToBeClickable(inputFechaDesde));
			inputFechaHasta.clear();			
			inputFechaHasta.sendKeys(dia);			
			this.editValorPorId_Js(idFecha,"/");
			inputFechaHasta.sendKeys(mes);
			this.editValorPorId_Js(idFecha,"/");
			inputFechaHasta.sendKeys(ano);
			inputFechaHasta.click();
		}else {
			System.out.println("forma to de fecha  no valido");
		}

		
		
	}

	
	public void setFechaDesde_Gestion(String dia, String mes,String ano) {//editando
		
		String fecha=dia+'/'+mes+'/'+ano;
		String idFecha = inputFechaDesde.getAttribute("id");
		System.out.println(idFecha);
		if(ano.matches("\\d{4}") && mes.matches("\\d{2}") && dia.matches("\\d{2}")) {
			fluentWait.until(ExpectedConditions.elementToBeClickable(inputFechaDesde));
			inputFechaDesde.clear();
			inputFechaDesde.sendKeys(dia);
			this.editValorPorId_Js(idFecha,"/");
			inputFechaDesde.sendKeys(mes);
			this.editValorPorId_Js(idFecha,"/");
			inputFechaDesde.sendKeys(ano);
			System.out.println(inputFechaDesde.getText());
		}else {
			System.out.println("forma to de fecha  no valido");
		}

		
		
	}
	
	
}
