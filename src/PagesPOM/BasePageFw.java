package PagesPOM;




import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import Pages.Accounts;




public class BasePageFw {
	
	protected WebDriver driver;
	protected FluentWait<WebDriver> fluentWait;
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
		this.driver=driver;
	}
	
//METODOS QUE TRABAJAN SOBRE LISTAS
	public WebElement getBuscarElementoPorText(List<WebElement> listado, String parametroBusqueda){
		//filtra por texto y retorna un elemento web 
		System.out.println(listado.size());
		WebElement aux = null;
		for (WebElement x : listado) {
			
			System.out.println(x.getText()+"<--------------->"+parametroBusqueda);
			if(x.getText().contains(parametroBusqueda)) {
				aux=x;
			}
		}
		return aux;
		 
		
	}
	
	public boolean getMacheaText(List<WebElement> listaElementos, String textoaComparar){
		//de una lista de ElemetosWeb toma el texto y compara con un texto que puede estar formado por mas de una condicion(ver metodo Matches)
		boolean temp=false;
		for (WebElement x : listaElementos) {	
	        System.out.println(x.getText()+" "+x.getText().matches(textoaComparar));
			if (x.getText().matches(textoaComparar))
				temp = true;		
		}
																					
		return temp;
		
	}
	
	public boolean containText(List<WebElement> listaElementos, String textoaComparar){
		//de una lista de ElemetosWeb toma el texto y compara a ver si contiene el Srting que toma como parametro
		boolean temp=false;
		for (WebElement x : listaElementos) {	
	        System.out.println(x.getText()+" "+x.getText().contains(textoaComparar));
			if (x.getText().contains(textoaComparar))
				temp = true;		
		}
																					
		return temp;
		
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
	Accounts acc =new Accounts(driver);
	return acc;
	
	}
	
	








}
