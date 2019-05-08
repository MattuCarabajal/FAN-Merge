package Pages;

import java.util.List;

import org.junit.FixMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import Tests.TestBase;
import javafx.scene.control.ScrollToEvent;


public class TechnicalCareCSRAutogestionPage extends BasePage {
	
	private WebDriver driver;
	
	
	@FindBy(id="ChannelSelection")
	private WebElement channelSelection;
	
	@FindBy(id="ServiceSelection")
	private WebElement ServiceSelection;
	
	@FindBy(id="ServiceSelection")
	private List<WebElement> Service;
			
	@FindBy(id="MotiveSelection")
	private WebElement MotiveSelection;
	
	@FindBy(id="SelfManagementStep_nextBtn")
	private WebElement selfManagementStepBtn;
	
	@FindBy(xpath=".//*[@class='borderOverlay']") 
	private  List<WebElement> borderOverlay;
	
	@FindBy(xpath=".//*[@id='KnowledgeBaseResults_nextBtn']") 
	private WebElement knowledgeBaseBtn;

	@FindBy(xpath=".//*[@id='CreatedCaseText']/div/p/p[1]/strong") 
	private WebElement numCaso;
	
	@FindBy(xpath=".//*[@id=\"ClosedCaseText\"]/div/p/p/strong")
	private WebElement numGestion;
	
	@FindBy(xpath=".//*[@id='SimilCaseInformation']/div/p/p[3]/strong[1]")
	private WebElement existCaso;
	
	@FindBy(id= "phSearchInput")
	private WebElement search;

	@FindBy(xpath="//*[@id='Case_body']/table/tbody/tr[2]/th/a") 
	private WebElement caseBody;

	@FindBy(xpath="//*[@id=\"ep\"]/div[2]/div[2]/table/tbody/tr[7]/td[3]")
	private WebElement optionContainer;

	@FindBy(xpath=" //*[@id='00Nc0000002Ja0S_ilecell']") 
	private WebElement verificar;
	
	@FindBy(xpath="//*[@class='imgItemContainer ng-scope']") 
	private List<WebElement> listaDeInconvenientes;
	
	@FindBy(xpath= "//*[@id='bottomButtonRow']/input[5]")
	private WebElement cerrarcaso;
	
	@FindBy(id="cas7")
	private WebElement estado;
	
	@FindBy(id="cas6")
	private WebElement motivo;
	
	@FindBy(xpath=".//*[@id='topButtonRow']/input[1]")
	private WebElement guardar;	

	
	public TechnicalCareCSRAutogestionPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	
	
////...................................................MenudeOpciones......................................///
	
		/*public void listadoDeSeleccion(String seleccion, String servicio, String motivo) {
		selectByText(getChannelSelection(),seleccion);
		selectByText(getServiceSelection(),servicio);
		selectByText(getMotiveSelection(),motivo) ;	
	}*/
		
		public void listadoDeSeleccion(String canal,String servicio,String inconveniente) throws InterruptedException{
	           sleep(5000);
	           driver.switchTo().frame(getFrameForElement(driver, By.id("SelfManagementFields")));
	           selection(channelSelection,canal);
	           selection(ServiceSelection,servicio);
	           selection(MotiveSelection,inconveniente);
	           scrollToElement(selfManagementStepBtn);
	           selfManagementStepBtn.click();
		}       
	  private void selection(WebElement elemento, String opcion){
		  sleep(5000);
	    elemento.click();
		  sleep(3000);
	        List<WebElement> servicio= driver.findElements(By.xpath(".//*[@class='slds-list--vertical vlc-slds-list--vertical']/li"));
	           for(WebElement opt : servicio ){
	              if (opt.getText().toLowerCase().contains(opcion.toLowerCase())) {
	                 opt.click();
	                      break;
	                    }
	                  }
	                }
		
		/*public void canal(String canal) throws InterruptedException{
		       sleep(5000);
		       driver.switchTo().frame(getFrameForElement(driver, By.id("SelfManagementFields")));
		       channelSelection.click();
		        sleep(5000);
		          WebElement tabla = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical"));
		            List<WebElement> canales= tabla.findElements(By.tagName("li"));
		              for(WebElement opt : canales ){
		                if (opt.getText().toLowerCase().contains(canal.toLowerCase())) {
		                	opt.click();
		                      	System.out.println("Se selecciono el servicio: " +opt.getText());
		                      		sleep(3000);
		                      			break; 
				            			  }
				            		  }
				            	  } */          
		                	        
		                
		          
		  
	////...................................................listaOpciones......................................///
		
		public  boolean verificarOpciones(WebElement element,String data){
		    Select select = new Select(element);
		    List<WebElement> options = select.getOptions();

		    for (WebElement opt : options) {
		        if (opt.getText().toLowerCase().contains(data.toLowerCase())) {
		         return true;
		        }
		    }
		    return false;
		}
		

		
////...................................................verificarsiexistecaso......................................///
	
	public boolean elementExists(WebElement element) throws InterruptedException {
	    sleep(2000);

	    try {
	      boolean isDisplayed = element.getSize().height > 0;
	      return isDisplayed;
	    } catch (Exception ex) {
	      return false;
	    }
	  }
		
	public void clickOnButtons() throws InterruptedException  {
		//scrollToElement(selfManagementStepBtn);
		//selfManagementStepBtn.click();
		sleep(4000);
		if (!elementExists(existCaso)) { 
		borderOverlay.get(1).click();
		sleep(3000);
		scrollToElement(knowledgeBaseBtn);
		knowledgeBaseBtn.click();
		
		}
	}
	
	
	public void selectionInconvenient(String inconvenientName) {	
		sleep(5000);
		driver.switchTo().frame(getFrameForElement(driver, By.cssSelector(".imgItem.ng-binding.ng-scope")));
		  sleep(5000);
	      	for(WebElement p:getBorderOverlay()) {
	      		System.out.println(p.getText());
	      		   if(p.getText().equalsIgnoreCase(inconvenientName));
	      		   		sleep(5000);
	      			   p.click();    		
	      			   scrollToElement(knowledgeBaseBtn);
	      			   knowledgeBaseBtn.click();
	      				sleep(5000);
	      						break;
	      				}
	      		} 
	
			
	
	public boolean cerrarCaso(String estado, String motivo) {
		TestBase tb = new TestBase();
		driver.switchTo().frame(tb.cambioFrame(driver, By.xpath("//*[@id=\"ep\"]")));
		driver.findElement(By.name("close")).click();
		sleep(5000);
		selectByText(driver.findElement(By.id("cas7")), estado);
		selectByText(driver.findElement(By.id("cas6")), motivo);
		driver.findElement(By.name("save")).click();
		sleep(5000);	
		return driver.findElement(By.id("cas7_ileinner")).getText().equalsIgnoreCase(estado);
	}
		
	
	public void ServiceOwner(){
	BasePage cambioFrameByID=new BasePage();
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("srchErrorDiv_Case")));
	getCaseBody().click();		
	sleep(5000);
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ep")));
	scrollToElement(getVerificar());
	WebElement ServiceOwner = getVerificar();
	ServiceOwner.isDisplayed();
	//((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+ServiceOwner.getLocation().y+")");
	 sleep(5000);
	//driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("labelCol")));
	
	//List<WebElement>menu=getOptionContainer();
	//menu.get(7).isDisplayed();
	
	
	
	}
	
////...................................................verificarcaso...........................................///
	
	public String verificarCaso() throws InterruptedException {
		String caso="";
		if(elementExists(existCaso)) {
			 caso=existCaso.getText();
			}
			else {		
				driver.switchTo();
				caso=numCaso();
			}
		driver.switchTo().defaultContent();
		buscarCaso(caso);
		driver.switchTo().frame(getFrameForElement(driver, By.id("Case_body")));
	
		return caso;			
	
			}
	
	public String verificarNumDeGestion() throws InterruptedException {
		String caso="";
		if(elementExists(existCaso)) {
			 caso=existCaso.getText();
			 sleep(4000);
			}
			else {		
				caso=numDeGestion();
				sleep(4000);
			}
		driver.switchTo().defaultContent();
		buscarCaso(caso);
		driver.switchTo().frame(getFrameForElement(driver, By.id("Case_body")));
		//ServiceOwner();
		return caso;			
	
			}
	

	public void buscarCaso(String caso) throws InterruptedException{
				search.click();
				search.clear();
				search.sendKeys(caso);
				search.submit();
				sleep(5000);
		
			}

	
	public String numCaso() throws InterruptedException{
		String caso =  numCaso.getText();
		sleep(2000);
		driver.switchTo().defaultContent();
		sleep(2000);
	return caso;
	}
	
	public String numDeGestion()  throws InterruptedException {
		sleep(3000);
		String caso =  numGestion.getText();
		driver.switchTo().defaultContent();
		sleep(2000);
		return caso;
	}
		
	public WebElement getExistCaso() {
		return existCaso;
	}


	public WebElement getChannelSelection() {
		return channelSelection;
	}


	public WebElement getServiceSelection() {
		return ServiceSelection;
	}
	
	public WebElement getMotiveSelection() {
		return MotiveSelection;
	}
	
	public WebElement getNumCaso(){
		return numCaso;
	}
	

	public WebElement getCaseBody(){
		return caseBody;
	}

	public List<WebElement> getOptionContainer(){
		return optionContainer.findElements(By.tagName("li"));
	}
	
	public List<WebElement> getService() {
		return Service;
	}
	
	public WebElement getVerificar(){
		return verificar;
	}
	
		
	public List<WebElement> getListaDeInconvenientes() {
		return listaDeInconvenientes;
	}



	public WebElement getEstado() {
		return estado;
	}



	public WebElement getMotivo() {
		return motivo;
	}
	

	public WebElement getCerrarcaso() {
		return cerrarcaso;
	}



	public List<WebElement> getBorderOverlay() {
		return borderOverlay;
	}



	public void scrollToElement(WebElement element) {
		((JavascriptExecutor)driver)
	        .executeScript("arguments[0].scrollIntoView();", element);
	  }



	
	

	

}