package PagesPOM;

import java.util.ArrayList;
import java.util.List;
import java.awt.AWTException;
import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import Pages.Accounts;

public class BasePageFw {
	
	final String locatoriframes = "iframe";
	@FindBy(how = How.TAG_NAME, using = locatoriframes)
	private List<WebElement> iframes;
	protected WebDriver driver;
	protected FluentWait<WebDriver> fluentWait;

	public FluentWait<WebDriver> getFluentWait() {
		return fluentWait;
	}

	public void setFluentWait(FluentWait<WebDriver> fluentWait) {
		this.fluentWait = fluentWait;
	}

	protected Select select;

	public BasePageFw() {
	}

	public WebDriver getDriver() {
		return driver;
	}

	public BasePageFw(WebDriver driver) {
		this.driver = driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	
	//METODOS QUE TRABAJAN SOBRE LISTAS
	public WebElement getBuscarElementoPorText(List<WebElement> listado, String parametroBusqueda) {
		//filtra por texto y retorna un elemento web 
		//System.out.println(listado.size());
		WebElement aux = null;
		for (WebElement x : listado) {			
			//System.out.println(x.getText()+"<--------------->"+parametroBusqueda);
			if(x.getText().contains(parametroBusqueda)) {
				aux=x;
			}
		}
		return aux;		
	}
	
	public boolean clickElementoPorText(List<WebElement> listado, String parametroBusqueda) {
		System.out.println(listado.size());
		boolean aux = false;
		for (WebElement x : listado) {			
			System.out.println(x.getText()+"<--------------->"+parametroBusqueda);
			if (x.getText().contains(parametroBusqueda)) {
				aux = true;
				getFluentWait().until(ExpectedConditions.elementToBeClickable(x));				
				x.click();
				break;
			}
		}
		return aux;		
	}
	
	public boolean clickElementoPorTextExacto(List<WebElement> listado, String parametroBusqueda) {
		//System.out.println(listado.size());
		boolean aux = false;
		for (WebElement x : listado) {			
			//System.out.println(x.getText()+"<--------------->"+parametroBusqueda);
			if (x.getText().equalsIgnoreCase(parametroBusqueda)) {
				aux = true;
				getFluentWait().until(ExpectedConditions.elementToBeClickable(x));//hay que esperar porque a veces no le puede hacer click aunque este como elemento en la lista
				x.click();
				break;
			}
		}
		return aux;		
	}
	
	public List<WebElement> listaDeElementosPorText(List<WebElement> listado, String parametroBusqueda) { 
		//filtra por texto y retorna un array con los elementos que machea 
		//System.out.println(listado.size()); 
		List<WebElement> aux = new ArrayList<WebElement>() ; 
		for (WebElement x : listado) { 			 
			//System.out.println(x.getText()+"<--------------->"+parametroBusqueda); 
			if(x.getText().contains(parametroBusqueda)) { 
				aux.add(x); 
			} 
		} 
		return aux; 
	} 
	
	public boolean matchText(List<WebElement> listaElementos, String textoaComparar){
		//de una lista de ElemetosWeb toma el texto y compara con un texto que puede estar formado por mas de una condicion(ver metodo Matches)
		for (WebElement x : listaElementos) {
	        System.out.println(x.getText()+" "+x.getText().matches(textoaComparar));
			if (x.getText().matches(textoaComparar))
				return true;	
		}												
		return false;
	}
	
	public boolean containsText(List<WebElement> listaElementos, String textoaComparar){
		//de una lista de ElemetosWeb toma el texto y compara a ver si contiene el Srting que toma como parametro
		for (WebElement x : listaElementos) {	
	        System.out.println(x.getText()+" "+x.getText().contains(textoaComparar));
			if (x.getText().toLowerCase().contains(textoaComparar.toLowerCase()))
				return true;		
		}																					
		return false;		
	}
	
	//FUNCIONES SOBRE FRAMES	
	public List<WebElement> getFrames() {
		fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName(locatoriframes), 0));
		System.out.println(iframes.size()+" frames");
		return iframes;
	}
	
	public WebElement getFrameByLocator(By byLocatorElement) {
		driver.switchTo().defaultContent();
		try {
			return getFrames().get(getIndexFrame(byLocatorElement));
		} catch (ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame.");
			return null;
		}
	}
	
	public WebElement getFrameForElement(WebElement Element) {
		driver.switchTo().defaultContent();
		try {
			return getFrames().get(getIndexFrame(Element));
		} catch (ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame.");
			return null;
		}
	}
	
	public WebElement cambioFrame(WebElement Element) {
		driver.switchTo().defaultContent();
		try {
			return getFrames().get(getIndexFrame(Element));
		} catch (ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame.");
			return null;
		}
	}
	
	public WebElement cambioFrame(By Element) {
		driver.switchTo().defaultContent();
		try {
			return getFrames().get(getIndexFrame(Element));
		} catch (ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame.");
			return null;
		}
	}
	
	public int getIndexFrame(WebElement webElementToFind) {
		int index = 0;
		driver.switchTo().defaultContent();
		for (WebElement frame : iframes) {
			try {
				driver.switchTo().frame(frame);
				// System.out.println(webElementToFind.getText()+" en el frame= "+index);
				// //prints the used index.
				System.out.println("frame encontrado :" + webElementToFind.getText());
				driver.switchTo().defaultContent();
				return index;
			} catch (NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();
			} catch (NullPointerException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();
			}
		}
		return -1;// if this is called, the element wasnt found.
	}
	
	public int getIndexFrame(By byForElement) { // working correctly
		int index = 0;
		driver.switchTo().defaultContent();
		List<WebElement> fra = getFrames();
		for (WebElement frame : fra) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(byForElement).getText(); // each element is in the same iframe.
				driver.findElement(byForElement).isDisplayed(); // each element is in the same iframe.
				System.out.println("frame encontrado :" + byForElement.toString());
				driver.switchTo().defaultContent();
				return index;
			} catch (NoSuchElementException noSuchElemExcept) {
				index++;
				System.out.println("frame no encontrado ");
				driver.switchTo().defaultContent();
			}
		}
		return -1; // if this is called, the element wasnt found.
	}
	

	//METODOS Select
	public Select getSelect(WebElement elemento) {
		//Toma un elementoWeb y lo devuelve como Select en caso de que se pueda
		return select = new Select(elemento);		
	}

	//METODOS javaScripts
	public JavascriptExecutor getEjecutorJavaScipt() {	 
		return ((JavascriptExecutor)driver);	
	}

	public void ejecutarCodigoJs(String codigoJavaScript) {
		JavascriptExecutor js= this.getEjecutorJavaScipt();
		js.executeScript(codigoJavaScript);
	}
	
	//METODOS ACTIONS
		
	public Actions getAction() {
		Actions accion = new Actions(driver);
		return accion;
	}
	
	public Accounts getAccounts() {
		Accounts acc = new Accounts(driver);
		return acc;
	}
	
	public Robot getRobot() {
		Robot acc = null;
		try {
			acc = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return acc;
	}
}