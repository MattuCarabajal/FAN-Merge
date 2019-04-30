package Pages;
import static org.testng.Assert.assertTrue;

import java.awt.Component;
import java.awt.Frame;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;  



public class TechnicalCareCSRDiagnosticoPage extends BasePage{
	
	final WebDriver driver;
	 		
	@FindBy (className="slds-checkbox--faux")
	private WebElement inconveniente;
	
	@FindBy(id="IssueSelectStep_nextBtn")
	private WebElement continuar;
	
	@FindBy(css=".imgItemContainer.ng-scope")
	private  List<WebElement> preguntas;
	
	@FindBy(id="KnowledgeBaseResults_nextBtn")
	private WebElement nextBtn;
	
	@FindBy(id="NetworkCategory_nextBtn")
	private WebElement nextCategoria;
	
	@FindBy(id="DeregisterSpeech_nextBtn")
	private WebElement Continuar;
	
	@FindBy(id="ConfigurationSending_nextBtn")
	private WebElement configuracion;
	
	@FindBy(xpath=".//*[@id='ServiceOperation|0']/div/div[1]/label[1]")
	private List<WebElement> serviciofunciona;
	
	
	@FindBy(xpath="//*[@class='imgItemContainer ng-scope']") 
	private List<WebElement> listaDeInconvenientes;

	
	@FindBy (id="ClosedCaseKnowledgeBase")
	private WebElement casoGenerado;
	
	@FindBy(id="ClosedCaseMessage")
	  private WebElement numReclamo;
	 
	
	@FindBy(xpath=".//*[@id='SimilCaseInformation']/div/p/p[3]/strong[1]")
	private WebElement existCaso; 
	
	@FindBy(id="ExistSimilCase_nextBtn") 
	private WebElement next;
	
	
	@FindBy(xpath=".//*[@id='ClosedCaseKnowledgeBase']/div/p/p/strong/strong")
	private WebElement numCaso;
	
	@FindBy(xpath=".//*[@id='SimilCaseInformation']/div/p/p[3]/strong[1]")
	private WebElement concCaso;
	
	@FindBy(id= "phSearchInput")
	private WebElement buscar;
	
	@FindBy(xpath=".//*[@id='DeregisterSpeechMessage']/div/p/p")
	private WebElement SpeechMessage;
	
	@FindBy(id="AdressInput")
	private WebElement direccion;
		
	@FindBy(id="GeoMock")
	private WebElement buscarEnMapa;
	
	@FindBy(id="MobileUpdate")
	private WebElement actualizarEquipo;
	
	@FindBy(id="MobileModel")
	private WebElement buscarModelos;
	
	@FindBy(xpath=".//*[@id='MobileModel']")
	private List<WebElement> buscarModelo;
	
	@FindBy(id="HlrDeregisterUpdate")
	private WebElement consultarHLR;
			
	@FindBy(id="SmsServiceDiagnosis_prevBtn")
	private WebElement diagnosticodeServicioSMS;
		
	@FindBy(className="slds-text-body_regular")
	private WebElement menuOpcion;
	
	@FindBy(xpath=".//*[@id='ServiceOperation|0']/div/div[1]/label/span[1]")
	private List<WebElement> diagnosticoOptions;
	
	@FindBy(xpath=".//*[@id='UpdatedPhone|0']/div/div[1]/label[2]/span[1]")
	private List<WebElement> UpdatedPhone;
	
	@FindBy (css= ".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--left")
	private WebElement search;
	
	@FindBy(xpath=".//*[@id='Case_body']/table/tbody/tr[2]/td[3]")								
	private WebElement estado;
	
	@FindBy(xpath=".//*[@id='IncorrectCategoryMessage']/div/p/p[2]/span/strong")								
	private WebElement nuevocasoconciliar;
	
	
	@FindBy(xpath=".//*[@id='OperationalServiceMessage']/div/p/p/span/strong")					
	private WebElement OperationalServiceMessage;
	
	@FindBy(xpath="//*[@id='NotUpdatedPhoneMessage']/div/p/p[2]/span/strong")
	private WebElement NotUpdatedPhoneMessage;

	private Object findElement;
	

	public TechnicalCareCSRDiagnosticoPage(WebDriver driver){
		this.driver = driver;
			PageFactory.initElements(driver, this);

	}
	
	////////////////////////////////////BUSACR SERVICIO////////////////////////////////////////////////////
	
	public void buscarServicio(String servicio)throws InterruptedException {
		sleep(8000);
		Accounts accPage = new Accounts(driver);
		driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".via-slds.addedValueServices-container")));
		sleep(8000);
		driver.findElement(By.cssSelector(".slds-form-element__control.slds-input-has-icon.slds-input-has-icon--left")).click();
		sleep(8000);
		driver.switchTo().activeElement().sendKeys(servicio);
		
	}

	public boolean elementExists(WebElement element) throws InterruptedException {
	    sleep(2000);
		    try {
 		      boolean isDisplayed = element.getSize().height > 0;
		       return isDisplayed;
		    }   catch (Exception ex) {
		         return false;
		  }
	}
	
////////////////////////////////////SELECCIONAR EL ASSET////////////////////////////////////////////////////
	
	public void clickOpcionEnAsset(String Asset,String Opcion) {
	    boolean assetEncontrado=false,opcion=false;
	    Opcion=Opcion.toLowerCase();
	    Accounts accPage = new Accounts(driver);
	    sleep(8000);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".console-card.active.expired")));
	    List<WebElement> asset=driver.findElements(By.cssSelector(".console-card.active"));
	    for(WebElement a:asset) {
	      
	      if(a.findElement(By.className("header-right")).getText().contains(Asset)) {
	        assetEncontrado=true;
	        List<WebElement> opciones=a.findElement(By.className("actions")).findElements(By.tagName("li"));
	        for(WebElement o:opciones) {
	          
	          if(o.findElement(By.tagName("span")).getText().toLowerCase().contains(Opcion)) {
	        	  sleep(3000);
	            o.findElement(By.tagName("span")).click();
	            opcion=true;
	            break;
	          }
	        }
	      }
	    if(assetEncontrado) break;
	    }
	    if(!assetEncontrado) System.out.println("Asset No encontrado");
	    if(!opcion) System.out.println("asset encontrado, Opcion No encontrada");
	  }
	
////////////////////////////////////VER DETALLES////////////////////////////////////////////////////
	
	public void verDetalles() {
		sleep (7000);
		BasePage cambioFrameByID=new BasePage();
		 driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
		    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).getLocation().y+" )");
		    sleep(7000);
		    driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
	}
	
////////////////////////////////////SELECT DIAGNOSTICAR////////////////////////////////////////////////////
	
	public void clickDiagnosticarServicio(String servicio) {
	      sleep(8000);
	      Accounts accPage = new Accounts(driver);
	      driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".slds-card__body.cards-container")));
	      List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
	      List<WebElement> servicios=tablas.get(selectionTable(servicio)).findElements(By.xpath("//table//tbody//tr"));
	      for(WebElement service : servicios) {
	        System.out.println(service.getText());
	        if(service.getText().toLowerCase().contains(servicio.toLowerCase())) {
	          ((JavascriptExecutor)driver).executeScript("window.scrollTo(0," + service.getLocation().y+")");
	          sleep(5000);
	          service.findElement(By.className("slds-cell-shrink")).click();
	          sleep(5000);
	          try {
	             sleep(8000);
	           List<WebElement> actions=  service.findElement(By.className("slds-cell-shrink")).findElements(By.xpath("//*[@class='dropdown__list']//li"));
	        for (WebElement opt : actions) {
	         if (opt.isDisplayed()) {
	          opt.click();
	          break;
	            }
	          }
	        }
	          catch(org.openqa.selenium.ElementNotVisibleException e) {
	            System.out.println("No es posible seleccionar el boton diagnosticar... Verifique!!");
	            e.printStackTrace();

	          }
	        }
	      } 
	  }
	
////////////////////////////////////BUSACR SERVICIO CON SUBSERVICIO////////////////////////////////////////////////////
	
	public void clickDiagnosticarServicio(String servicio, String subServicio, boolean clickOnSubServicio) {
	    sleep(10000);
	    boolean sEncontrado=true;
	    Accounts accPage = new Accounts(driver);
	    sleep(5000);
	    driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".slds-card__body.cards-container")));
	    List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
	    //Listado de opciones
	    List<WebElement> servicios=tablas.get(0).findElements(By.xpath("//table//tbody//tr"));
	    for(WebElement S:servicios) {
	      if(S.getText().toLowerCase().contains(servicio.toLowerCase())) {
	        S.findElement(By.className("addedValueServices-arrowWrapper")).click();
	        sleep(2000);
	        sEncontrado=false;
	        break;
	      }
	    }
	    if(sEncontrado) { System.out.println("Servicio no encontrado."); return;}
	    
	    List<WebElement> sServicios=tablas.get(selectionTable(servicio)).findElements(By.xpath("//table//tbody//tr"));
	        for(WebElement service:sServicios) {
	          if(service.getText().toLowerCase().contains(subServicio.toLowerCase()) ) {
	            ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+service.getLocation().y+")");
	            sleep(1000);
	            service.findElement(By.className("slds-cell-shrink")).click();
	            sleep(2000);
	            if (clickOnSubServicio) {
	              
	            try {
	               sleep(2000);
	               List<WebElement> actions= service.findElement(By.className("slds-cell-shrink")).findElements(By.xpath("//*[@class='dropdown__list']//li"));
	            for (WebElement opt : actions) {
	             if (opt.isDisplayed()) {
	              opt.click();
	              break;
	                }
	              }
	            }
	              catch(org.openqa.selenium.ElementNotVisibleException e) {
	                System.out.println("No es posible seleccionar el boton diagnosticar... Verifique!!");
	                e.printStackTrace();

	              }
	            }
	          }
	          }    	sleep(5000);
	  }
	
////////////////////////////////////VALIDAR SERVICIO////////////////////////////////////////////////////
	public boolean validarOpcionesXSubServicio(String subServicio ) {
	    List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
	
		 List<WebElement> sServicios=tablas.get(0).findElements(By.xpath("//table//tbody//tr"));
	        for(WebElement service:sServicios) {
	          if(service.getText().toLowerCase().contains(subServicio.toLowerCase()) ) {
	            ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+service.getLocation().y+")");
	               sleep(2000);
	               List<WebElement> actions= service.findElement(By.className("slds-cell-shrink")).findElements(By.xpath("//*[@class='dropdown__list']//li"));
	            for (WebElement opt : actions) {
	             if (opt.isDisplayed()) {
	            	 System.out.println("*********"+opt.getText());
	            	 return true;
	                }
	              }
	          	}
	        }
	        return false;
	  }

	public boolean validarOpcionesXServicio(String servicio ) {
	    List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
		 List<WebElement> sServicios=tablas.get(0).findElements(By.xpath("//table//tbody//tr"));
	        for(WebElement service:sServicios) {
	          if(service.isDisplayed() && service.getText().toLowerCase().contains(servicio.toLowerCase()) ) {
	            ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+service.getLocation().y+")");
	               sleep(2000);
	               return true;
	              }
	          	}
	        
	        return false;
	  }
	
////////////////////////////////////VALIDAR ESTADO ACTIVO////////////////////////////////////////////////////
	public boolean validarEstado(String servicio ) {
	     sleep(5000);
	        Accounts accPage = new Accounts(driver);
	        driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".slds-card__body.cards-container")));
	        List<WebElement> tablas=driver.findElements(By.cssSelector(".slds-card__body.cards-container"));
	        //Listado de opciones
	        List<WebElement> servicios=tablas.get(0).findElements(By.xpath("//table//tbody//tr"));
	        for(WebElement service : servicios) {
	          if(service.getText().toLowerCase().contains(servicio.toLowerCase())) {
	                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+service.getLocation().y+")");
	            service.findElement(By.className("addedValueServices-arrowWrapper")).click();
	            sleep(2000);
	            List<WebElement> sServicios=tablas.get(1).findElements(By.xpath("//*[@class='dropdown__list']//li"));
	            for(WebElement subService : sServicios) {
	              if (subService.isDisplayed()) {
	               // System.out.println(subService.getText());
	          }
	            if(!service.getText().toLowerCase().contains("Activo") && service.isDisplayed() ) {
	                return false;
	 
	          }
	        }
	    }
	      return true;
	}
			return false;
	}
	
////////////////////////////////////SELECT INCONVENENTES////////////////////////////////////////////////////
	public void selectionInconvenient(String inconvenientName) {
		sleep(4000);
	      driver.switchTo().frame(getFrameForElement(driver, By.id("IssueSelectStep")));
	      	sleep(4000);
	      		for (WebElement opt : getlistaDeInconvenientes()) {
	      			if (opt.getText().equalsIgnoreCase(inconvenientName)) {
	      				opt.click();
	      				sleep(8000);
	      				break;
	        }
	    
	      }	     
	  }
	

	public boolean validarInconveniente(String inconvenientName) {
			sleep(8000);
			driver.switchTo().frame(getFrameForElement(driver, By.id("IssueSelectStep")));
			for (WebElement opt : getlistaDeInconvenientes()) {
				if(opt.getText().contains(inconvenientName)) {
		        	return true;
				}
		}
			return false;
	}	
	
	public void continuar() {
		scrollToElement(getContinuar());
		sleep(2000);
		continuar.click();
		sleep(8000);
		
	}
	
	public void enviodeconfiguracion() {
		scrollToElement(getConfiguracion());
		sleep(2000);
		configuracion.click();
		sleep(8000);
		
	}
	
	/**
	 * Seleciona cuenta despues de diagnosticar
	 * @param opcion
	 * @author Quelys
	 */
////////////////////////////////////SELECT RESPUESTA////////////////////////////////////////////////////
	public void seleccionarRespuesta(String opcion) {
	    Accounts accPage = new Accounts(driver);
	      driver.switchTo().frame(accPage.getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	      	List<WebElement> preguntas=driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
	      		for(WebElement p:preguntas) {
	      		if(p.getText().toLowerCase().contains(opcion)) {
	      			sleep(3000);
	      			p.click();    				
	      			sleep(5000);
	      			return;
	      	}
	   	}
	 }
	
	public void categoriaRed(String categoria) {
		sleep(5000);
	      driver.switchTo().frame(getFrameForElement(driver, By.cssSelector(".imgItemContainer.ng-scope")));
	      	sleep(5000);
	      		for (WebElement opt : getPreguntas()) {
	      			if (opt.getText().equalsIgnoreCase(categoria)) {
	      				scrollToElement(opt);
	      				sleep(3000);
	      				opt.click();
	      						break;
	      						
	        }
	    
	      }	     
	  }
	public void validacionDeCobertura(String categoria) {
		sleep(4000);
	      driver.switchTo().frame(getFrameForElement(driver, By.xpath(".//*[@id='SignalAnswer|0']")));
	      	sleep(2000);
	      		for (WebElement opt : getPreguntas()) {
	      			if (opt.getText().equalsIgnoreCase(categoria)) {
	      				sleep(3000);
	      				opt.click();
	      					sleep(8000);
	      						break;
	      						
	        }
	    
	      }	     
	  }
	    
	
	
	public boolean speech() throws InterruptedException {
		String speech=" ";
			if(elementExists(SpeechMessage)) {
				speech=SpeechMessage.getText();
				getNextSpeech().click();
			}
			else {
				speech=SpeechMessage.getText();
			}
			return true;
				}
	
	public boolean validarSpeech() throws InterruptedException {
		String speech=" ";
			if(elementExists(SpeechMessage)) {
				speech=SpeechMessage.getText();
			}
			else {
				speech=SpeechMessage.getText();
			}
			return true;
				}
	
////////////////////////////////////VALIDA SI EL SERVICIO FUNCIONA.... SI O NO////////////////////////////////////////////////////
	
	public boolean serviciofunciona(String opcion) {
	      Accounts accPage = new Accounts(driver);
	        driver.switchTo().frame(accPage.getFrameForElement(driver, By.xpath("//*[@id='ServiceOperation']")));
	      			if (opcion.equalsIgnoreCase("NO")) {
	      					diagnosticoOptions.get(1).click();
	      					return true;
	          }
	              else if(opcion.equalsIgnoreCase("SI")){
	            	  diagnosticoOptions.get(0).click();
	            	  return true;
	          }
	      			return false;
	      }
	
	public boolean serviciofuemodificado(String opcion) {
	      Accounts accPage = new Accounts(driver);
	        driver.switchTo().frame(accPage.getFrameForElement(driver, By.xpath("//*[@id=\'UpdatedPhone\']")));
	      			if (opcion.equalsIgnoreCase("NO")) {
	      				UpdatedPhone.get(0).click();
	      					return true;
	          }
	              else if(opcion.equalsIgnoreCase("SI")){
	            	  UpdatedPhone.get(1).click();
	            	  return true;
	          }
	      			return false;
	      }
	
	
	public boolean estadoConciliador(String categoriaRed,String catogoriaRed2 ,String estado) throws InterruptedException {
		String caso="";
		if(elementExists(existCaso)) {
			caso=existCaso.getText();	
			}
		else {			
				categoriaRed(categoriaRed);
				clickContinuar();
			 	sleep (4000);
				categoriaRed(catogoriaRed2);
			 	sleep (5000);
			    clickContinuar();
				sleep (9000);
				//Conciliador();
				sleep (5000);
				driver.switchTo();
				caso=	Conciliador();
		}	
		//buscar		// hacer todo lo demas	// Buscar dentro de la tabla lo que quieras para comparar  estado

		driver.switchTo().defaultContent();	
		buscar.click();
		buscar.clear();
		buscar.sendKeys(caso);
		buscar.submit();
		sleep(5000);
		driver.switchTo().frame(getFrameForElement(driver, By.id("Case_body")));
		
		return getEstado().getText().equalsIgnoreCase(estado);	
		}
		
	
	public boolean estadoDelServicio(String categoriaRed,String catogoriaRed2, String catogoriaRed3,String opcion, String estado) throws InterruptedException {
		String caso="";
		if(elementExists(existCaso)) {
			caso=existCaso.getText();	
			}
		else {			
				categoriaRed(categoriaRed);
				clickContinuar();
			 	sleep (8000);
				categoriaRed(catogoriaRed2);
			    clickContinuar();
				sleep (4000);
				speech();
				driver.switchTo();
				categoriaRed(catogoriaRed3);
				driver.findElement(By.id("Deregister_nextBtn")).click();
				sleep (8000);
				serviciofunciona(opcion);
				sleep (5000);
				caso=	confirmacionDeGestion();
		}	
		//buscar		// hacer todo lo demas	// Buscar dentro de la tabla lo que quieras para comparar  estado

		driver.switchTo().defaultContent();	
		buscar.click();
		buscar.clear();
		buscar.sendKeys(caso);
		buscar.submit();
		sleep(5000);
		driver.switchTo().frame(getFrameForElement(driver, By.id("Case_body")));
		return getEstado().getText().equalsIgnoreCase(estado);	
		}
	
	public boolean estadoDelServicioSinModificaciones(String categoriaRed,String catogoriaRed2, String catogoriaRed3, String catogoriaRed4, String opcion, String opcion2, String estado) throws InterruptedException {
		String caso="";
		if(elementExists(existCaso)) {
			caso=existCaso.getText();	
			}
		else {			
				categoriaRed(categoriaRed);
				clickContinuar();
			 	sleep (8000);
				categoriaRed(catogoriaRed2);
			    clickContinuar();
				sleep (4000);
				speech();
				categoriaRed(catogoriaRed3);
				clickContinua();
				//driver.findElement(By.id("Deregister_nextBtn")).click();
				sleep (8000);
				categoriaRed(catogoriaRed4);
				clickContinua();
				serviciofunciona(opcion);
				driver.switchTo();
				sleep (5000);
				serviciofuemodificado(opcion2);
				sleep (5000);
				clickContinua();
				driver.switchTo();
				caso=	confirmacionDeGestionSMS();
		}	
		//buscar		// hacer todo lo demas	// Buscar dentro de la tabla lo que quieras para comparar  estado

		driver.switchTo().defaultContent();	
		buscar.click();
		buscar.clear();
		buscar.sendKeys(caso);
		buscar.submit();
		sleep(5000);
		driver.switchTo().frame(getFrameForElement(driver, By.id("Case_body")));
		return getEstado().getText().equalsIgnoreCase(estado);	
		}
		
	
	public void Verificacion_de_la_posicion_en_el_mapa(String categoriaRed,String saldo,String direccion, String catogoriaRed3, String catogoriaRed4, String Equipo, String catogoriaRed5) throws InterruptedException {
			 // tech.verificarCaso();
			TechCare_Ola1 page=new TechCare_Ola1(driver);
		    seleccionarRespuesta(categoriaRed);
		    clickContinuar();
		    sleep(5000);
		    page.seleccionarPreguntaFinal(saldo);
		    clickContinuar();
		    sleep(5000);
		    buscarDireccion(direccion);
		    sleep(5000);
		    categoriaRed(catogoriaRed3);
		    categoriaRed(catogoriaRed4);
		    sleep(3000);
		   clickContinua();
		   actualizarEquipo(Equipo);
		   sleep(3000);
		   clickContinua();
		   categoriaRed(catogoriaRed5);
		   enviodeconfiguracion();
		   sleep(5000);
		}
	
	public void buscarCaso() throws InterruptedException{

		String caso = numCaso.getText().substring(0, numCaso.getText().indexOf(","));
		driver.switchTo().defaultContent();
		//driver.switchTo().frame(getFrameForElement(driver, By.id("searchButtonContainer")));
		buscar.click();
		buscar.clear();
		buscar.sendKeys(caso);
		buscar.submit();
		sleep(5000);
		driver.switchTo().frame(getFrameForElement(driver, By.id("Case_body")));
		
		
}
	
	public String Conciliador() throws InterruptedException{
		String caso =  nuevocasoconciliar.getText();
		sleep(2000);
		driver.switchTo().defaultContent();
		sleep(2000);
	return caso;
	}
	
	public String confirmacionDeGestion() throws InterruptedException{
		String caso =  OperationalServiceMessage.getText();
		driver.switchTo().defaultContent();
	return caso;
	}
	
	public String confirmacionDeGestionSMS() throws InterruptedException{
		String caso =  NotUpdatedPhoneMessage.getText();
		driver.switchTo().defaultContent();
	return caso;
	}
	
	
	public void actualizarEquipo(String modelo) throws InterruptedException{
	       sleep(3000);
	       driver.switchTo().frame(getFrameForElement(driver, By.id("MobileIdentification")));
	       actualizarEquipo.click();
	       buscarModelos.click();
	       sleep(5000);
	          WebElement tabla = driver.findElement(By.cssSelector(".slds-list--vertical.vlc-slds-list--vertical"));
	            List<WebElement> modelos= tabla.findElements(By.tagName("li"));
	              for(WebElement opt : modelos ){
	                if (opt.getText().toLowerCase().contains(modelo.toLowerCase())) {    
	                	opt.click();
	                    System.out.println("Se selecciono el modelo: " +opt.getText());
	                    sleep(3000);
	               break; 
	          }
	         }
	  }
	

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
	
	public boolean reclamo() throws InterruptedException {
		  String numreclamo=" ";
	       if(elementExists(numReclamo)) {
	        numreclamo=numReclamo.getText();
	 	      }
	 	      else {
	 	        numreclamo=numReclamo.getText();
	 	      }
	 	      return true;
	 	 }
	 
	
	public void buscarDireccion(String Direccion) {
		direccion.clear();
		direccion.sendKeys(Direccion);
		sleep(1000);
		direccion.sendKeys(Keys.ARROW_DOWN);
		sleep(2000);
		direccion.submit();
		sleep(5000);
		buscarEnMapa.click();
		sleep(5000);
		buscarEnMapa.click();
		sleep(5000);

	}
	
			
	public void scrollToElement(WebElement element) {
		((JavascriptExecutor)driver)
	        .executeScript("arguments[0].scrollIntoView();", element);
	  }

	public WebElement  getmenuOpcion(int opcion) {
	      List<WebElement>menuOptions=driver.findElements(By.className("slds-text-body_regular"));
	            return menuOptions.get(opcion);
		
	}
	
	
	public WebDriver getDriver() {
		return driver;
	}



	public WebElement getInconveniente() {
		return inconveniente;
	}


	public WebElement getContinuar() {
		return continuar;
	}
	
	public WebElement getConfiguracion() {
		return configuracion;
	}


	public WebElement getNextBtn() {
		return nextBtn;
	}


	public List<WebElement> getPreguntas() {
		return preguntas;
	}

	public List< WebElement> getlistaDeInconvenientes() {
		return listaDeInconvenientes;
	}


	public WebElement getNextCategoria() {
		return nextCategoria;
	}
	
	public WebElement getNextSpeech() {
		return Continuar;
	}


	public WebElement getCasoGenerado() {
		return casoGenerado;
	}
	


	public WebElement getExistCaso() {
		return existCaso;
	}

	public WebElement getDireccion() {
		return direccion;
	}


	public WebElement getBuscarEnMapa() {
		return buscarEnMapa;
	}


	public WebElement getActualizarEquipo() {
		return actualizarEquipo;
	}


	public List<WebElement> getBuscarEquipo() {
		return buscarModelo;
	}


	public WebElement getConsultarHLR() {
		return consultarHLR;
	}


	public List<WebElement> getServiciofunciona() {
		return serviciofunciona;
	}

	public WebElement getMenuOpcion() {
		return menuOpcion;
	}


	
	public WebElement getNumeroCaso() {
		return numCaso;
	}


	public WebElement getNext() {
		return next;
	}
	
	private int selectionTable(String serviceName) {
		switch (serviceName.toUpperCase()) {
		case "SMS":
			return 1;
		case "VOZ":
			return 2;
		default:
			return 0;
		}
	}

	public WebElement getSearch() {
		return search;
	}

	public WebElement getSpeechMessage() {
		return SpeechMessage;
	}
	
	//ALmer
	public void clickContinuar() {
		sleep(1000);
		WebElement cancelar=driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+cancelar.getLocation().y+")");
		sleep(2000);
		//try {driver.findElement(By.id("IssueSelectStep_nextBtn")).click(); }
		//catch(org.openqa.selenium.NoSuchElementException Inco) {
			try{driver.findElement(By.id("KnowledgeBaseResults_nextBtn")).click();}
			catch(org.openqa.selenium.ElementNotVisibleException BasedeConocmiento) {
				try{driver.findElement(By.id("NetworkCategory_nextBtn")).click();}
				catch(org.openqa.selenium.NoSuchElementException CategoriadeRed) {
					try{driver.findElement(By.id("CoverageValidation_nextBtn")).click();}
					catch(org.openqa.selenium.NoSuchElementException PosicionGeo) {
						try{driver.findElement(By.id("ConfigurationSending_nextBtn")).click();}
						catch(org.openqa.selenium.NoSuchElementException EnvíodeConfiguración) {
							try{driver.findElement(By.id("MobileIdentification_nextBtn")).click();}
							catch(org.openqa.selenium.NoSuchElementException IdentificaciondelEquipo) {
								try{driver.findElement(By.id("Deregister_nextBtn")).click();}
								catch(org.openqa.selenium.NoSuchElementException Deregistro) {
									driver.findElement(By.id("Address Section_nextBtn")).click();
									try{driver.findElement(By.id("SmsServiceDiagnosis_nextBtn")).click();}
									catch(org.openqa.selenium.NoSuchElementException DiagnósticodeservicioSMS) {
										driver.findElement(By.id("Address Section_nextBtn")).click();
								}
							}
						}	
					}
				}
			}
		}
	}
	
public void clickContinua() {
	try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	WebElement BenBoton = driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope"));
	((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+BenBoton.getLocation().y+")");
	WebElement continuar= driver.findElement(By.xpath("//*[text() = 'Continuar']"));
	try {Thread.sleep(500);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	try {
	continuar.click();
	try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	catch(org.openqa.selenium.ElementNotVisibleException a) {
		List <WebElement> continuar2=driver.findElements(By.className("ng-binding"));
		for(WebElement c:continuar2) {
			if(c.getText().contains("Continuar")) {
				c.click();
				break;}}}		
}

public WebElement getEstado() {
	return estado;
}


}
	
		
	
	
	

