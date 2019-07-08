package Funcionalidades;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import com.gargoylesoftware.htmlunit.javascript.host.file.FileReader;

import DataProvider.ExcelUtils;
import Pages.BasePage;
import Pages.BeFan;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.DPW;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SCP;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.BeFanPom;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import PagesPOM.VentaDePackFw;
import Tests.CBS_Mattu;
import Tests.MDW;
import Tests.TestBase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Regresion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private VentaDePackFw vt;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private BeFan Botones;
	private MDW mdw;
	private SCP scp;
	String detalles;
	
	//@BeforeClass (alwaysRun = true)
	public void Sit03() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit03();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		//cc.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilMayorista")
	public void initMayo() {
		driver = setConexion.setupEze();
		loginBeFANVictor(driver, "mayorista");
		Botones = new BeFan(driver);
		mdw = new MDW();
		cbsm = new CBS_Mattu();
		contact = new ContactSearch(driver);
		ges = new GestionDeClientes_Fw(driver);
	//	loginBeFAN(driver);
	}
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		contact = new ContactSearch(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		vt = new VentaDePackFw(driver);
		Botones = new BeFan(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		vt = new VentaDePackFw(driver);
		Botones = new BeFan(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		vt = new VentaDePackFw(driver);		
		Botones = new BeFan(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	//================================================== TEST CASES ==================================================\\
	
	@Test(groups = { "PreactivacionBeFan", "PerfilMayorista" })
    public void preactivacion() throws Exception {
		// PREACTOIVACION
		for (int cant=0; cant<=10; cant++) {
    	BeFan Botones = new BeFan(driver);
        Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(),"seriales",1,1,8,"Preactivacion");
        String nombreArchivo = "SerialBalido";
        int filaExcel = 0;
        for (int i = 0; i < testObjArray.length; i++) {
            if (testObjArray[i][1].toString().equalsIgnoreCase(nombreArchivo)) {
                filaExcel = i;
                break;
            }
        }
        String path = testObjArray[filaExcel][0].toString();
        String deposito = testObjArray[filaExcel][2].toString();
        String prefijo1 = testObjArray[filaExcel][3].toString();
        String cantidad = testObjArray[filaExcel][7].toString();
        int numeroDeLineas = Integer.parseInt(cantidad);
        seleccionOpcion(driver, "sims", "importacion");
        seleccionDeposito(driver, deposito);
        seleccionPrefijo(driver, prefijo1);
        sendKeysBy(driver, By.cssSelector("input[type='number']"), cantidad, 0);
        clickBy(driver, By.name("btnAgregar"), 0);
        nombreArchivo = Botones.LecturaDeDatosTxt(path + "\\seriales.txt", numeroDeLineas);
        sendKeysBy(driver, By.id("fileinput"), nombreArchivo, 0);
        clickBy(driver, By.xpath("//button[contains(text(),'Importar')]"), 0);
        try {
        	Assert.assertTrue(getTextBy(driver, By.xpath("//h3[contains(text(),'correctamente')]"), 0).contentEquals("El archivo se import\u00f3 correctamente."));
        	clickBy(driver, By.cssSelector(".btn.btn-link"), 0);
        } catch(Exception e) {
        	clickBy(driver, By.cssSelector(".btn.btn-link"), 0);
        }
//        Assert.assertTrue(getTextBy(driver, By.xpath("//h3[contains(text(),'correctamente')]"), 0).contentEquals("El archivo se import\u00f3 correctamente."));
//        clickBy(driver, By.cssSelector(".btn.btn-link"), 0);
        nombreArchivo = "seriales" + nombreArchivo.substring(nombreArchivo.length()-18, nombreArchivo.length()-4);
        DPW.main();        
        File lotesSeriales = new File(System.getProperty("user.home") + "/Desktop/lotesSeriales.txt");
        FileWriter fw = new FileWriter(lotesSeriales.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(nombreArchivo + "\r\n");
        bw.close();
        sleep(900000);
        cant++;
		}
        
        
        
        
        
        // OBTENCION DE LAS LINEAS
//		String estado = "En Proceso";
//		seleccionOpcion(driver, "sims", "gestion");
//		sleep(60000);
//		clickBy(driver, By.xpath("//option[contains(text(), 'Estado')]"), 0);
//		clickBy(driver, By.xpath("//option[contains(text(), '" + estado + "')]"), 0);
//		sendKeysBy(driver, By.cssSelector("input[class='form-control ng-pristine ng-untouched ng-valid ng-empty']"), nombreArchivo, 0);
//		clickBy(driver, By.name("buscar"), 0);
//		clickBy(driver, By.cssSelector("td [class='btn btn-primary btn-xs']"), 0);
//		int columnaLineas = 2;
//		int columnaEstados = 8;
//		List<WebElement> columnas = driver.findElements(By.xpath("//div[@class='modal-body']//thead//th"));
//		for (int i = 0; i < columnas.size(); i++) {
//			if (columnas.get(i).getText().contains("nea")) {
//				columnaLineas = i + 1;
//			} else if (columnas.get(i).getText().contains("estado")) {
//				columnaEstados = i + 1;
//			}
//		}
//		// CLICK PARA QUE SE VISUALICEN DE 100 EN 100
//		esperarElemento(driver, By.cssSelector("select[ng-model='detalleCabeceraCtrl.container.cantCabecerasVistaActual']"), 0);
//		selectByText(driver.findElement(By.cssSelector("select[ng-model='detalleCabeceraCtrl.container.cantCabecerasVistaActual']")), "100");
//		//PAGINA EN LA QUE ESTA Y CANTIDAD DE PAGINAS QUE TIENE
//		String texto = driver.findElement(By.xpath("//div[@class='modal-body']//label[contains(text(), 'gina')]")).getText();
//		texto = texto.replaceAll("[^\\d/]", "");
//		int paginaFinal = Integer.parseInt(texto.substring(texto.indexOf("/")+1));
//		int paginaInicial = Integer.parseInt(texto.substring(0, texto.indexOf("/")));
//		File resultados = new File(System.getProperty("user.home") + "/Desktop/lineasPreactivas-" + nombreArchivo.substring(nombreArchivo.length() - 14) + ".txt");
//		BufferedWriter escribir = new BufferedWriter(new FileWriter(resultados));
//		for (int j = 1; j < paginaFinal + 1; j++) {
//			List<WebElement> lineas = driver.findElements(By.xpath("//div[@class='modal-body']//tbody//td[" + columnaLineas + "]"));
//			List<WebElement> estados = driver.findElements(By.xpath("//div[@class='modal-body']//tbody//td[" + columnaEstados + "]"));
//			Assert.assertTrue(lineas.size() == estados.size());
//			for (int i = 0; i < lineas.size(); i++) {
//				System.out.println(estados.get(i).getText());
//				if (estados.get(i).getText().equalsIgnoreCase("PendientePreactivar")) {
//					// Escribir la linea en el nuevo archivo .TXT
//					escribir.write(lineas.get(i).getText() + System.lineSeparator());
//				}
//			}
//			
//			if (paginaInicial < paginaFinal) {
//				clickBy(driver, By.cssSelector("button[ng-click='detalleCabeceraCtrl.container.siguiente()']"), 0);
//				String nuevaPagina = driver.findElement(By.xpath("//div[@class='modal-body']//label[contains(text(), 'gina')]")).getText().replaceAll("[^\\d/]", "");
//				paginaInicial = Integer.parseInt(nuevaPagina.substring(0, nuevaPagina.indexOf("/")));
//			}
//		}
//		escribir.close();
	}
	
	@Test(groups = { "PreactivacionBeFan", "PerfilMayorista" })
	public void obtenerLineasActivadas() {
		BufferedReader br = null;
		String fichero = System.getProperty("user.home") + "/Desktop/lotesSeriales.txt";
		String linea;
		
		// SELECCIONAR ESTADO 
//	    String estado = "En Proceso";
		String estado = "Procesado";
		
		int cant = 0;
		seleccionOpcion(driver, "sims", "gestion");
//		sleep(60000);
		clickBy(driver, By.xpath("//option[contains(text(), 'Estado')]"), 0);
		clickBy(driver, By.xpath("//option[contains(text(), '" + estado + "')]"), 0);
		try {
			File resultados = new File(System.getProperty("user.home") + "/Desktop/lineasPreactivas.txt");
			FileWriter fw = new FileWriter(resultados.getAbsoluteFile(), true);
			BufferedWriter escribir = new BufferedWriter(fw);
			br = new BufferedReader(new java.io.FileReader(fichero));
			while ((linea = br.readLine()) != null) {
				System.out.println(cant = cant + 1);
				System.out.println("LEYENDO " + linea);
//				  	sendKeysBy(driver, By.cssSelector("input[class='form-control ng-pristine ng-untouched ng-valid ng-empty']"), linea, 0);
				driver.findElement(By.cssSelector("input[class*='form-control ng']")).clear();
				sendKeysBy(driver, By.cssSelector("input[class*='form-control ng']"), linea, 0);
				clickBy(driver, By.name("buscar"), 0);
				sleep(5000);
				clickBy(driver, By.cssSelector("td [class='btn btn-primary btn-xs']"), 0);
				sleep(8000);
				int columnaLineas = 2;
				int columnaEstados = 8;
				List<WebElement> columnas = driver.findElements(By.xpath("//div[@class='modal-body']//thead//th"));
				for (int i = 0; i < columnas.size(); i++) {
					if (columnas.get(i).getText().contains("nea")) {
						columnaLineas = i + 1;
					} else if (columnas.get(i).getText().contains("estado")) {
						columnaEstados = i + 1;
					}
				}
				// CLICK PARA QUE SE VISUALICEN DE 100 EN 100 (DESCOMENTAR SI SE HACEN DE A 100)
//					esperarElemento(driver, By.cssSelector("select[ng-model='detalleCabeceraCtrl.container.cantCabecerasVistaActual']"), 0);
//					selectByText(driver.findElement(By.cssSelector("select[ng-model='detalleCabeceraCtrl.container.cantCabecerasVistaActual']")), "100");

				// PAGINA EN LA QUE ESTA Y CANTIDAD DE PAGINAS QUE TIENE
				sleep(3000);
				String texto = driver
						.findElement(By.xpath("//div[@class='modal-body']//label[contains(text(), 'gina')]")).getText();
				texto = texto.replaceAll("[^\\d/]", "");
				int paginaFinal = Integer.parseInt(texto.substring(texto.indexOf("/") + 1));
				int paginaInicial = Integer.parseInt(texto.substring(0, texto.indexOf("/")));
				for (int j = 1; j < paginaFinal + 1; j++) {
					List<WebElement> lineas = driver
							.findElements(By.xpath("//div[@class='modal-body']//tbody//td[" + columnaLineas + "]"));
					List<WebElement> estados = driver
							.findElements(By.xpath("//div[@class='modal-body']//tbody//td[" + columnaEstados + "]"));
					Assert.assertTrue(lineas.size() == estados.size());
					for (int i = 0; i < lineas.size(); i++) {
						System.out.println(estados.get(i).getText());
						if (estados.get(i).getText().equalsIgnoreCase("Activado")) {
							// Escribir la linea en el nuevo archivo .TXT
							escribir.write(lineas.get(i).getText() + System.lineSeparator());
						}
					}

					if (paginaInicial < paginaFinal) {
						clickBy(driver, By.cssSelector("button[ng-click='detalleCabeceraCtrl.container.siguiente()']"),
								0);
						String nuevaPagina = driver
								.findElement(By.xpath("//div[@class='modal-body']//label[contains(text(), 'gina')]"))
								.getText().replaceAll("[^\\d/]", "");
						paginaInicial = Integer.parseInt(nuevaPagina.substring(0, nuevaPagina.indexOf("/")));
					}
				}
				driver.findElement(By.xpath("//Button[text() = 'Cerrar']")).click();
				sleep(2000);

			}
			escribir.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Test(groups = { "PreactivacionBeFan", "PerfilMayorista" })
	public void obtenerSerialesConError() {
		BufferedReader br = null;
		String fichero = System.getProperty("user.home") + "/Desktop/lotesSeriales.txt";
		String linea;
		
		// SELECCIONAR ESTADO 
//	    String estado = "En Proceso";
		String estado = "Procesado";
		
		int cant = 0;
		seleccionOpcion(driver, "sims", "gestion");
//		sleep(60000);
		clickBy(driver, By.xpath("//option[contains(text(), 'Estado')]"), 0);
		clickBy(driver, By.xpath("//option[contains(text(), '" + estado + "')]"), 0);
		try {
			File resultados = new File(System.getProperty("user.home") + "/Desktop/SerialesConError.txt");
			FileWriter fw = new FileWriter(resultados.getAbsoluteFile(), true);
			BufferedWriter escribir = new BufferedWriter(fw);
			br = new BufferedReader(new java.io.FileReader(fichero));
			while ((linea = br.readLine()) != null) {
				System.out.println(cant = cant + 1);
				System.out.println("LEYENDO " + linea);
//				  	sendKeysBy(driver, By.cssSelector("input[class='form-control ng-pristine ng-untouched ng-valid ng-empty']"), linea, 0);
				driver.findElement(By.cssSelector("input[class*='form-control ng']")).clear();
				sendKeysBy(driver, By.cssSelector("input[class*='form-control ng']"), linea, 0);
				clickBy(driver, By.name("buscar"), 0);
				sleep(5000);
				clickBy(driver, By.cssSelector("td [class='btn btn-primary btn-xs']"), 0);
				sleep(8000);
				int columnaLineas = 2;
				int columnaEstados = 8;
				List<WebElement> columnas = driver.findElements(By.xpath("//div[@class='modal-body']//thead//th"));
				for (int i = 0; i < columnas.size(); i++) {
					if (columnas.get(i).getText().contains("Serie")) {
						columnaLineas = i + 1;
					} else if (columnas.get(i).getText().contains("estado")) {
						columnaEstados = i + 1;
					}
				}
				// CLICK PARA QUE SE VISUALICEN DE 100 EN 100 (DESCOMENTAR SI SE HACEN DE A 100)
//					esperarElemento(driver, By.cssSelector("select[ng-model='detalleCabeceraCtrl.container.cantCabecerasVistaActual']"), 0);
//					selectByText(driver.findElement(By.cssSelector("select[ng-model='detalleCabeceraCtrl.container.cantCabecerasVistaActual']")), "100");

				// PAGINA EN LA QUE ESTA Y CANTIDAD DE PAGINAS QUE TIENE
				sleep(3000);
				String texto = driver
						.findElement(By.xpath("//div[@class='modal-body']//label[contains(text(), 'gina')]")).getText();
				texto = texto.replaceAll("[^\\d/]", "");
				int paginaFinal = Integer.parseInt(texto.substring(texto.indexOf("/") + 1));
				int paginaInicial = Integer.parseInt(texto.substring(0, texto.indexOf("/")));
				for (int j = 1; j < paginaFinal + 1; j++) {
					List<WebElement> lineas = driver
							.findElements(By.xpath("//div[@class='modal-body']//tbody//td[" + columnaLineas + "]"));
					List<WebElement> estados = driver
							.findElements(By.xpath("//div[@class='modal-body']//tbody//td[" + columnaEstados + "]"));
					Assert.assertTrue(lineas.size() == estados.size());
					for (int i = 0; i < lineas.size(); i++) {
						System.out.println(estados.get(i).getText());
						if (estados.get(i).getText().equalsIgnoreCase("Error")) {
							// Escribir la linea en el nuevo archivo .TXT
							escribir.write(lineas.get(i).getText() + System.lineSeparator());
						}
					}

					if (paginaInicial < paginaFinal) {
						clickBy(driver, By.cssSelector("button[ng-click='detalleCabeceraCtrl.container.siguiente()']"),
								0);
						String nuevaPagina = driver
								.findElement(By.xpath("//div[@class='modal-body']//label[contains(text(), 'gina')]"))
								.getText().replaceAll("[^\\d/]", "");
						paginaInicial = Integer.parseInt(nuevaPagina.substring(0, nuevaPagina.indexOf("/")));
					}
				}
				driver.findElement(By.xpath("//Button[text() = 'Cerrar']")).click();
				sleep(2000);

			}
			escribir.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rNuevaNomina") 
	public void TS_001_Nominacion_Cliente_Nuevo_Telefonico(String sLinea, String sDni, String sNombre, String sApellido, String sGenero, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "TS_001";
		detalles = imagen + "- Nominacion - DNI:" + sDni;		
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-hint-parent.ng-scope"), 0));
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		cambioDeFrame(driver, By.id("DocumentTypeSearch"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.crearClienteNominacion("DNI", sDni, sGenero, sNombre, sApellido, sFnac, sEmail);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required")));
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("FinishProcess_nextBtn")));
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		Assert.assertTrue(a);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "rExistenteNomina") 
	public void TS85097_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_DOC_OfCom(String sLinea, String sDni, String sNombre, String sApellido) {
		imagen = "TS85097";
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		cambioDeFrame(driver,By.id("SearchClientDocumentType"),0);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-2")); 	
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-2")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(3000);
		contact.searchContact2("DNI", sDni, "Masculino");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Contact_nextBtn")));
		driver.findElement(By.id("Contact_nextBtn")).click();	
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(18000);
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rRecargasTC")
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134332";
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDNI);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);		
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		System.out.println("datoinicial: "+ datosInicial);
		System.out.println("dato final: "+datosFinal);
		Assert.assertTrue(datosInicial + 900 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}

	@Test (groups = "PerfilOficina", dataProvider="rMinDescuento")
	public void TS135657_CRM_Movil_REPRO_Venta_de_Pack_150_min_a_Personal_y_150_SMS_x_7_dias_Presencial_OOCC_Descuento_de_Saldo(String sDni, String sLinea, String sventaPack) {
		imagen = "TS135657";
		detalles = imagen+"-Venta de pack-DNI:"+sDni;
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		ges.BuscarCuenta("DNI", sDni);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packMinutos(sventaPack);
		vt.tipoDePago("descuento de saldo");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		sleep(45000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sventaPack));
	}
	
	@Test (groups = "PerfilAgente", dataProvider="rDatosEfectivo")
	public void Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis_Factura_de_Venta_efectivo_Agente(String sDNI, String sLinea, String sPack) throws AWTException{
		imagen = "Venta_de_Pack_1_GB_x_1_dia_whatsapp_gratis";
		detalles = imagen + "-Venta de pack-DNI:" + sDNI+ "-Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar SMS");
		vt.packDatos(sPack);
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(10000);
		cc.buscarCaso(sOrden);
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(30000);
		boolean a = false;
			for(int i= 0; i < 10 ; i++) {
				cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
				ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
				WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
				String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
				if(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated")) {
					a = true;
				}else {
					driver.navigate().refresh();
					sleep(15000);
					i++;
				}
			}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rSmsTC")
	public void TS123133_CRM_Movil_REPRO_Venta_De_Pack_internacional_30_SMS_al_Resto_del_Mundo_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sPack, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) throws InterruptedException, AWTException{
		//Pack modificado = Pack internacional 30 minutos LDI y 15 SMS int
		imagen = "TS123133";
		detalles = null;
		ges.BuscarCuenta("DNI", sDNI);
		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
		ges.irAGestionEnCard("Comprar Internet");
		vt.packLDI(sPack);		
		vt.tipoDePago("en factura de venta");
		driver.findElement(By.id("SetPaymentType_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDNI);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		sleep(20000);
		cc.buscarCaso(sOrden);
		boolean a = false;
			for (int i = 0; i < 10; i++) {
				cambioDeFrame(driver,By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"),0);
				ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[id = 'ep'] table tr "), 25));
				WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
				String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
				if (datos.equalsIgnoreCase("activada") || datos.equalsIgnoreCase("activated")) {
					a = true;
				} else {
					sleep(15000);
					driver.navigate().refresh();
					i++;
				}
			}
		Assert.assertTrue(a);
		Assert.assertTrue(cbs.validarActivacionPack(cbsm.Servicio_QueryFreeUnit(sLinea), sPack));
	}
	
	@Test(groups = { "GestionesPerfilTelefonico", "RenovacionDeCuota","E2E" }, dataProvider = "RenovacionCuotaConSaldo")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sDNI, String sLinea, String accid) {
		imagen = "TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito";
		detalles = "Renovacion de cuota " + imagen + "-DNI:" + sDNI;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDNI);
		detalles += "-Cuenta:" + accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("50 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(10000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
	}

	@Test(groups = { "GestionesPerfilOficina", "RenovacionDeCuota", "E2E" }, dataProvider = "RenovacionCuotaConSaldo")
	public void TS130056_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Efectivo_con_Credito(String sDNI, String sLinea, String accid) throws AWTException {
		imagen = "TS130056";
		detalles = "Renocavion de cuota: " + imagen + " - DNI: " + sDNI + " - Linea: " + sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDNI);
		detalles += "-Cuenta: " + accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"),2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for (WebElement elemento : elementos) {
			if (elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Factura de Venta");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SelectPaymentMethodsStep_nextBtn")));
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDNI +", Monto:"+orden.split("-")[2]);
		cbsm.Servicio_NotificarPago(orden.split("-")[0]);
		driver.navigate().refresh();
		sleep(5000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres Adicionales");
		System.out.println("Datos Inicial "+datosInicial);
		System.out.println("Datos final "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+25600)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);
	}
	
	@Test (groups = "PerfilAgente", dataProvider="rTCAgente")
	public void TS_Renovacion_De_Cuota_TC_Agente(String sDni, String sLinea, String accid, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular){
		imagen = "TS_009";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDni+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDni);
		System.out.println("id "+accid);
		detalles += "-Cuenta: "+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing"))); 
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(5000);
		driver.navigate().refresh();
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		System.out.println("Inicial: "+datosInicial);
		System.out.println("Final: "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);	
	}

}