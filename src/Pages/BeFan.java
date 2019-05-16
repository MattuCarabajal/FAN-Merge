package Pages;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import DataProvider.ExcelUtils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

//import Tests.File;
import Tests.TestBase;

public class BeFan extends BasePage{

	//*********************************CONSTRUCTOR******************************************************//
	static WebDriver driver;
	TestBase tst = new TestBase();
	
	public BeFan(WebDriver driver){
		this.driver = driver;
			PageFactory.initElements(driver, this);
	}
	
	
	//*********************************ELEMENTOS******************************************************//
	
	//Login
	@FindBy(name="username")
	private WebElement user;
	
	@FindBy(name="txtPass")
	private WebElement password;
	
	@FindBy(name="btnIngresar")
	private WebElement IngresarBoton;
	
	//Ventana Importacion
	@FindBy(css=".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-not-empty")
	private WebElement prefijo;
	
	@FindBy(css=".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")
	private WebElement cantidad;
	
	@FindBy(name="btnAgregar")
	private WebElement agregar;
	
	@FindBy(id="fileinput")
	private WebElement adjuntar;
	
	@FindBy(linkText="Aceptar")
	private WebElement aceptar;
	
	//Ventana gestion
	
	@FindBy(css=".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")
	private WebElement Estado;
	
	@FindBy(css=".form-control.ng-pristine.ng-valid.ng-empty.ng-touched")
	private WebElement nombreArchivo;
	
	@FindBy(id="dataPickerDesde")
	private WebElement fechaDesde;

	@FindBy(id="dataPickerHasta")
	private WebElement fechaHasta;
	
	
	//Mensajes
	@FindBy(xpath="//*[@id='NotUpdatedPhoneMessage']/div/p/p[2]/span/strong")
	private WebElement NotUpdatedPhoneMessage;
	
	
	//********************************METODOS*******************************************************//
	
	public static WebDriver initDriver() {
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("start-maximized");
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    return driver;
	}
	
	public static void irABefan() {
		driver.get("http://befantest.personal.corp/#/signin");
	}
	public void loginBefan(String user, String password) {
		try {
		this.user.sendKeys(user);
		this.password.sendKeys(password);
		IngresarBoton.click();
		}catch(Exception e) {
			System.out.print("No se puede Ingresar en BeFan: ");
			e.printStackTrace();
		}
	}
	
	public void adjuntarArchivoBefan(String FileAddress) {
		adjuntar.sendKeys(FileAddress);
	}
	
	/**
	 * Selecciona una opcion de la lista despegable de SIM
	 * @param action
	 */
	public void opcionDeSim(String opcion) {
		List <WebElement> despegables=driver.findElements(By.className("dropdown-toggle"));
		for(WebElement op:despegables) {
			if(op.getText().toLowerCase().contains("sims"))
				op.click();
			}
		List<WebElement> opciones=driver.findElements(By.className("multi-column-dropdown")).get(1).findElements(By.tagName("li"));
		for(WebElement op:opciones) {
			if(op.getText().toLowerCase().contains(opcion.toLowerCase()))
				op.click();
			}
		
	}
	
	public void selectPrefijo(String prefijo) {
		Select Prefijo=new Select(this.prefijo);
		Prefijo.selectByVisibleText(prefijo);
	}
	
	public void setCantidad(String cantidad) {

		this.cantidad.sendKeys(cantidad);
	}

	//Victor
	
	//Metodos locos
	
	public String buscarUltimoArchivo() {
		File folder = new File("C:\\New_Download");
		File[] listOfFiles = folder.listFiles();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy hh_mm_ss");
		int i = 0;
		Instant elMejor = null;
		String elMejorNombre = "";
		int cont = 0;
		for (File x : listOfFiles) {
			if (x.getName().contains("PREACTIVACIONES DIARIAS ")) {
				String[] part1 = x.getAbsolutePath().split("\\\\");
				cont = part1.length;
				String[] part2 = part1[cont-1].split("\\.");
				String[] part3 = part2[0].split("PREACTIVACIONES DIARIAS ");
				String[] part4 = part3[1].split("  ");
				String[] part5 = part4[0].split("_");
				String[] part6 = part4[1].split("_");
				i = 0;
				for (String y : part5) {
					if (i==2) {
					} else {
						if (y.length()==1) {
							part5[i] = "0" + part5[i];
						}
					}
					i = i + 1;
				}
				i = 0;
				
				for (String y : part6) {
					if (y.length()==1) {
						part6[i] = "0" + part6[i];
					}
					i = i + 1;
				}
				
				
				String fecha = part5[2] + "-" + part5[1] + "-" + part5[0] + "T" + part6[0] + ":" + part6[1] + ":" + part6[2] + "Z"; 
				
				Instant instant = Instant.parse(fecha);
				if (elMejor==null) {
					elMejor = instant;
					elMejorNombre = x.getAbsolutePath();
				} else {
					if (instant.compareTo(elMejor)>0) {
						elMejor=instant;
						elMejorNombre = x.getAbsolutePath();
					}
				}
			}
		}
		if (elMejor==null) {
			return "No hay archivos descargados con el nombre buscado";
		}
		
		return (elMejorNombre);
	}
	
	public boolean corroborarArchivo (String path) {
		boolean resultado = false;
		boolean algoFallo = false;
		int i = 0;
		try {
			BufferedReader in;
			in = new BufferedReader(new FileReader(path));
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				if (i==1) {
				      if (readLine.equals("<thead>")) {
				      } else {
				    	  algoFallo = true;
				      }
				} else {
					if (i==11) {
					      if (readLine.equals("</thead>")) {
					      } else {
					    	  algoFallo = true;
					      }
					} else {
						if ((i-12) % 10==0) {
						      if (readLine.equals("<tbody>")) {
						      } else {
						    	  if (readLine.equals("</tbody>")) {
						    	  } else {
							    	  algoFallo = true;
						    	  }
						      }
							}
						}
					}
				i = i + 1;
				}
			resultado = true;
			in.close();
		} catch (Exception e) {
			return false;
		}
		if (algoFallo==true) {
			return false;
		}
		return resultado;
	}
	
	public String soyEzpesial(String caso) throws Exception{
		
		File folder = new File("C:\\BefanArchivos\\salida");
		File[] listOfFiles = folder.listFiles();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");  
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
		int elMejor = 0;
		String finalmente = "false";
		ArrayList<String> estados = new ArrayList<String>();
		int cont = 0;
		for (File x : listOfFiles) {
			String[] part1 = x.getAbsolutePath().split("\\\\");
			cont = part1.length;
			String[] part2 = part1[cont-1].split("\\.");
			String[] parts3 = part2[0].split(time);
			if (parts3.length == 1) {
			} else {
				estados.add(parts3[1]);
			}
			
		}
		
		for (String x : estados) {
			if (elMejor == 0) {
					elMejor = Integer.parseInt(x);
			} else {
				if (Integer.parseInt(x)>elMejor) {
					elMejor = Integer.parseInt(x);
				}
			}	
		}
		File archivo = new File("C:\\BefanArchivos\\salida\\" + "Resultado" + time + Integer.toString(elMejor) + ".txt");
		String readLine = "";
		BufferedReader b = new BufferedReader(new FileReader(archivo));
		while ((readLine = b.readLine()) != null) {
			String[] lasNoches = readLine.split(",");
			if (lasNoches[0].equals(caso)) {
				finalmente = "true," + lasNoches[1] + "," + lasNoches[2];
			}
		}
		return finalmente;		
	}
	
	public List<String> SGDatosArchivos() {
		List <String> resultado = new ArrayList<String>();
		int h = 1;
		int i = 1;
		int k = 1;
		SGTablaVisible();
		int paginas = SGTablaCantPaginas();
		//Lo inicio con cualquier saraza para que quede vacio y no nulo
		
		for (h = 1; h <= paginas; h++) {
			if(paginas==1) {
			} else {
				SGTablaSigPag();
			}
			List <WebElement> columnasEnPagina = driver.findElements(By.xpath("//*[@id=\"exportarTabla\"]/thead/tr/th"));
			List <WebElement> elementosEnPagina = driver.findElements(By.xpath("//*[@id=\"exportarTabla\"]/tbody/tr"));							
			for(i = 1; i <= elementosEnPagina.size(); i++) {
				for (k = 1; k <= columnasEnPagina.size(); k++) {
					resultado.add(driver.findElement(By.xpath("//*[@id=\"exportarTabla\"]/tbody/tr[" + i +"]/td[" + k + "]")).getText());
				}
			}
			i = 1;
			elementosEnPagina = null;
		}
//		for (i = 1; i <=resultado.size();i++) {
//			System.out.println(resultado.get(i-1));
//		}
		
		return (resultado);
	}

	//Log in
	
	public void andaAlMenu(String opcion, String subopcion) {
		WebElement menu2 = null;
		
		tst.waitForClickeable(driver, By.xpath("/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/ul/li[3]/a"));
		List <WebElement> menu = driver.findElements(By.className("dropdown-toggle"));
		for (WebElement x : menu) {
				if (x.getText().toLowerCase().contains(opcion)) {
					x.click();
					break;
				}
		}
		
		
		tst.waitForVisible(driver, By.className("tpt-bg-subMenu"), 5);
		for (WebElement y : driver.findElements(By.className("col-sm-4"))) {
			if (y.getAttribute("ng-show").equals("headerCtrl.container.hasAccess(['sims_importacion', 'sims_gestion'])"))
				menu2 = y;
		}
		
		switch(subopcion) {
		case "importacion":
			try {
				for (WebElement x : menu2.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("importaci\u00f3n")) {
						x.click();
					}
				}			
			} catch(Exception e) {}
		case "gestion":
			try {
				for (WebElement x : menu2.findElements(By.tagName("a"))) {
					if (x.getText().toLowerCase().contains("gesti\u00f3n")) {
						x.click();
					}
				}
			} catch(Exception e) {}
		}
	}
	
	//Menu Simcard-Importacion
	public void SISeleccionDeDeposito(String deposito) {
		if (driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).isEmpty()) {
			selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-untouched.ng-valid.ng-not-empty.ng-dirty.ng-valid-parse")).get(0), deposito);
		} else {
			
			selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), deposito);
		}
		
	}
	
	public void SISeleccionDePrefijo (String prefijo) {
	
		if (driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).isEmpty()) {
			selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-valid.ng-dirty.ng-touched.ng-empty")).get(0), prefijo);
		} else {
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), prefijo);
		}
	}
	
	public void SISeleccionCantidadDePrefijo (String cantidadPrefijo) {
		
		if (driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).isEmpty()) {
			driver.findElement(By.cssSelector(".text.form-control.ng-valid.ng-dirty.ng-touched.ng-empty")).sendKeys(cantidadPrefijo);
		} else {
			driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(cantidadPrefijo);
		}
	}
	
	public void SIClickAgregar() {
	driver.findElement(By.name("btnAgregar")).click();
	}
	
	public String SIMensajeModal() {
		String mensaje;
		mensaje = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[1]/div[1]/h3")).getText();
		return mensaje;
	}
	
	public String SIMensajeModalMasDeUnMensaje() {
		String mensaje;
		mensaje = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[1]/div[1]/h3/h2")).getText();
		return mensaje;
	}
	
	public void SIImportarArchivo(String path) {
	WebElement uploadElement = driver.findElement(By.id("fileinput"));
	uploadElement.sendKeys(path);
	}
	
	public void SIClickImportar() {
	int cont = driver.findElements(By.cssSelector(".btn.btn-primary")).size();
	driver.findElements(By.cssSelector(".btn.btn-primary")).get(cont-1).click();
	}
	
	public String SICreacionArchivo(String nombreArch, String path, String serial1, String serial2) throws IOException {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now(); 
	String time = dtf.format(now);
	path = path + "\\" + nombreArch + time + ".txt";
	File archivo = new File(path);
	archivo.createNewFile();
	if (serial2 == "") {
	FileOutputStream outputStream = new FileOutputStream(path);
    byte[] strToBytes = serial1.getBytes();
    outputStream.write(strToBytes);
    outputStream.close();
	}
	else
	{
	 String serialF = serial1 + System.lineSeparator() + serial2;
		FileOutputStream outputStream = new FileOutputStream(path);
	    byte[] strToBytes = serialF.getBytes();
	    outputStream.write(strToBytes);
	    outputStream.close();
	}
	return path;
	}
	
	//Solo funciona con Cantidad <= 50 dado problemas de performance de OM
	public String LecturaDeDatosTxt(String path, int Cantidad) throws IOException {
		int cont = 1;
		File archivo = new File(path);
		File archivo2 = new File(path.substring(0,path.length()-4) + "temp.txt");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
		File archivo3 = new File(path.substring(0,path.length()-4) + time + ".txt");
		if (archivo.exists()) {
			BufferedReader b = new BufferedReader(new FileReader(archivo));
			BufferedWriter c = new BufferedWriter(new FileWriter(archivo2));
			BufferedWriter d = new BufferedWriter(new FileWriter(archivo3));
			String readLine = "";
			while ((readLine = b.readLine()) != null) {
				if (cont <= Cantidad) {
					d.write(readLine + System.lineSeparator());
				} else {
					c.write(readLine + System.lineSeparator());
				}
				cont = cont + 1;
			}
			
			b.close();
			c.close();
			d.close();
			archivo.delete();
			boolean ok = archivo2.renameTo(archivo);
			return path.substring(0,path.length()-4) + time + ".txt";
		} else {
			System.out.println("No existe el archivo");
			return "No existe el archivo";
		}
	}
	
	public String TraemeLosSeriales(String path) throws Exception, IOException {
		File archivo = new File(path);
		String seriales = "";
		if(archivo.exists()) {
			BufferedReader b = new BufferedReader(new FileReader(archivo));
			String readLine = "";
			while ((readLine = b.readLine()) != null) {
				seriales = seriales + readLine +  "|";
			}
			b.close();
			return seriales.substring(0, seriales.length()-1);
		} else {
			System.out.println("No existe el archivo");
			return "No existe el archivo";
		}
		
		
	}
	
	
	
	public void SIDesRenombreDeArchivo(String nombreArch, String path) {
	String[] parts = nombreArch.split("\\\\");
	String[] partes = parts[2].split("2");
	File archivo = new File(nombreArch);
	String path2 = path + "\\" + partes[0] + ".txt";
	File archivo2 = new File(path2);
	Boolean faio = archivo.renameTo(archivo2);
	}
	
	public void SIClickAceptarImportar() {
	driver.findElement(By.cssSelector(".btn.btn-link")).click();	
	}
	
	//Api BEFAN
	
	public void BefanProceso() throws Exception {

	        CloseableHttpClient httpclient = HttpClients.createDefault();
	/*        try {
	            HttpGet httpGet = new HttpGet("http://httpbin.org/get");
	            CloseableHttpResponse response1 = httpclient.execute(httpGet);
	            // The underlying HTTP connection is still held by the response object
	            // to allow the response content to be streamed directly from the network socket.
	            // In order to ensure correct deallocation of system resources
	            // the user MUST call CloseableHttpResponse#close() from a finally clause.
	            // Please note that if response content is not fully consumed the underlying
	            // connection cannot be safely re-used and will be shut down and discarded
	            // by the connection manager.
	            try {
	                System.out.println(response1.getStatusLine());
	                HttpEntity entity1 = response1.getEntity();
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity1);
	            } finally {
	                response1.close();
	            }
	*/
	        try {
	            HttpPost httpPost = new HttpPost("https://apiu.telecom.com.ar/catalogo/api/ProcesoMasivo/IniciarProceso");
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            httpPost.addHeader("Content-Type", "application/json");
	            httpPost.addHeader("Authorization", "Basic d2VidmFzOndlYnZhcw==");
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);
	            System.out.println(response2);
	            
	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity();
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity2);
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	    }
	
	//Menu Simcard-Gestion
	
	//Estados Procesado, En Proceso, Eliminado y Pendiente
	public void SGSeleccionEstado(String estado){
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), estado);
	}
	
	public void SGSeleccionDeposito(String deposito) {
	selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), deposito);
	}
	
	public void SGClickBuscar() {
	driver.findElement(By.cssSelector(".btn.btn-primary")).click();
	}
	
	public void SGFechaDesdeAhora() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
		String[] fecha = time.split("/");
		String izquierda = Keys.chord(Keys.ARROW_LEFT);
		String borrar = Keys.chord(Keys.BACK_SPACE);
		driver.findElement(By.id("dataPickerDesde")).click();
		driver.findElement(By.id("dataPickerDesde")).sendKeys(borrar + borrar + borrar + borrar + fecha[2] + izquierda + izquierda + izquierda + izquierda + izquierda + borrar + borrar + fecha[1] + izquierda + izquierda + izquierda + borrar + borrar + fecha[0]);
		sleep(500);		
	}
	
	public void SGTablaSigPag() {
		tst.waitForVisible(driver, By.xpath("/html/body/div[1]/div[2]/div/section/div[2]/div[2]/ul/button[3]"), 10);
		int paginaActual = SGTablaPagActual();
		int paginaTotal = SGTablaCantPaginas();
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/section/div[2]/div[2]/ul/button[3]")).click();
		tst.waitForVisibleWithText(driver, By.xpath("/html/body/div[1]/div[2]/div/section/div[2]/div[1]/div/div[2]"), "P\\u00e1gina " + (paginaActual+1) + "/" + paginaTotal, 10);
	}
	
	public int SGTablaPagActual() {
		List<WebElement> AUX = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/section/div[2]/div[1]/div/div[2]/label"));
		if(AUX.isEmpty()) {
			return(0);
		} else {
			String[] AUX2 = AUX.get(0).getText().split("/");
			String[] cant = AUX2[0].split(" ");
			return(Integer.parseInt(cant[1]));
		}
	}
	
	public boolean SGValidarResultado (List<String> input, int indice, int CantidadDeColumnas, String resultadoEsperado) {
		boolean resultado = false;
		List <String> resultados = new ArrayList<String>();
		int i = 1;
		for (String x: input) {
			if (i==indice) {
				if (x.equals(resultadoEsperado)) {
					resultados.add("ok");
				} else {
					resultados.add("no macho, no funco");
				}
				
			}
			i = i + 1;
			if (CantidadDeColumnas==i) {
				i = 0;
			}
		}
		
		if (resultados.isEmpty()) {
			
		} else {
			for (String x: resultados) {
				resultado = true;
				if (x.equals("no macho, no funco")) {
					resultado = false;
				}
			}
		}
		return resultado;
	}
	
	public void SGFechaDesde(String yyyyMMdd) {
		String[] fecha = new String[3];
		fecha[2] = yyyyMMdd.substring(0, 4);
		fecha[1] = yyyyMMdd.substring(4, 6);
		fecha[0] = yyyyMMdd.substring(6, 8);
		String izquierda = Keys.chord(Keys.ARROW_LEFT);
		String borrar = Keys.chord(Keys.BACK_SPACE);
		tst.waitForClickeable(driver, By.id("dataPickerDesde"));
		driver.findElement(By.id("dataPickerDesde")).click();
		driver.findElement(By.id("dataPickerDesde")).sendKeys(borrar + borrar + borrar + borrar + fecha[2] + izquierda + izquierda + izquierda + izquierda + izquierda + borrar + borrar + fecha[1] + izquierda + izquierda + izquierda + borrar + borrar + fecha[0]);	
	}
	
	public void SGFechaHasta(String yyyyMMdd) {
		String[] fecha = new String[3];
		fecha[2] = yyyyMMdd.substring(0, 4);
		fecha[1] = yyyyMMdd.substring(4, 6);
		fecha[0] = yyyyMMdd.substring(6, 8);
		String izquierda = Keys.chord(Keys.ARROW_LEFT);
		String borrar = Keys.chord(Keys.BACK_SPACE);
		tst.waitForClickeable(driver, By.id("dataPickerHasta"));
		driver.findElement(By.id("dataPickerHasta")).click();
		driver.findElement(By.id("dataPickerHasta")).sendKeys(borrar + borrar + borrar + borrar + fecha[2] + izquierda + izquierda + izquierda + izquierda + izquierda + borrar + borrar + fecha[1] + izquierda + izquierda + izquierda + borrar + borrar + fecha[0]);	
	}
	
	public boolean SGValidarFechas (String fechaDesde, String fechaHasta, List<String> fechaActual, int indice, int CantidadDeColumnas) {
		List<String> resultados = new ArrayList<String>();
		boolean resultado = false;
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		int i = 1;
		try {
			Date dateDesde = formatter.parse(fechaDesde);
			Date dateHasta = formatter.parse(fechaHasta);
			for (String x: fechaActual) {
				if (i == indice) {
					Date dateActual = formatter.parse(x);
					if ((dateActual.after(dateDesde) || dateActual.equals(dateDesde)) && dateActual.before(dateHasta)) {
						resultados.add("ok");
					} else {
						resultados.add("hola bebe");
					}
				}
				
				if (i == CantidadDeColumnas) {
					i = 0;
				}
				i = i + 1;
			}
					
		} catch (ParseException e) {
			resultados.add("hola bebe");
		}
		
		if (resultados.isEmpty()) {
			resultado = false;
		} else {
			resultado = true;
			for (String y: resultados) {
				if (y.equals("hola bebe")) {
					resultado = false;
				}
			}
		}

		return resultado;
	}
	
	public List<WebElement> SGColumnas() {
		tst.waitForVisible(driver, By.xpath("//*[@id=\"exportarTabla\"]/thead/tr/th"), 10);
		List <WebElement> resultado = driver.findElements(By.xpath("//*[@id=\"exportarTabla\"]/thead/tr/th"));
		return resultado;
	}
	
	public void SGVerDetalleBotonExportar() {
		tst.waitForClickeable(driver, By.id("botonExportar"));
		driver.findElement(By.id("botonExportar")).click();
	}
	
	public List<WebElement> SGVerDetalleColumnas() {
		tst.waitForVisible(driver, By.xpath("/html/body/div[1]/div/div/div/div[1]/div[2]/table/thead/tr/th"), 10);
		List <WebElement> resultado = driver.findElements(By.xpath("/html/body/div[1]/div/div/div/div[1]/div[2]/table/thead/tr/th"));
		return resultado;
	}
	
	public void SGClickVerDetalle(int indicador) {
		tst.waitForClickeable(driver, By.xpath("//*[@id=\"exportarTabla\"]/tbody/tr[" + indicador + "]/td[9]/button"));
		driver.findElement(By.xpath("//*[@id=\"exportarTabla\"]/tbody/tr[" + indicador + "]/td[9]/button")).click();
	}
	
	public int SGTablaCantPaginas() {
		List<WebElement> AUX = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/section/div[2]/div[1]/div/div[2]/label"));
		String[] cant = AUX.get(0).getText().split("/");
		if(cant[0].equals("")) {
			return(1);
		} else {
			return(Integer.parseInt(cant[1]));
		}
	}
	
	public boolean SGLeerCampoYValidar(String nombreArch, String[] listaEstados, String[] listaResultados) {
	boolean resultado = false;
	int cont = 0;
	int cont2 = 4;
	int cont3 = 0;
	ArrayList<String> estados = new ArrayList<String>();
	ArrayList<String> resultados = new ArrayList<String>();
	
	List<WebElement> tabla = driver.findElements(By.cssSelector(".ng-binding"));
	String[] parts = nombreArch.split("\\\\");
	String[] partes = parts[3].split("\\.");
	for (WebElement x : tabla) {
		cont = cont + 1;
		if (x.getText().contains(partes[0]) && (listaEstados[0] != "" || listaResultados[0] != "")) {
		driver.findElements(By.cssSelector(".btn.btn-primary.btn-xs")).get((cont - 14) / 8).click();
		sleep(500);
		List<WebElement> tabla2 = driver.findElements(By.cssSelector(".padding-left-15.ng-binding"));
		for (cont2 = 7;cont2 < tabla2.size();cont2 = cont2 + 8) {
			estados.add(driver.findElements(By.cssSelector(".padding-left-15.ng-binding")).get(cont2).getText());
			resultados.add(driver.findElements(By.cssSelector(".padding-left-15")).get(cont2+1).getText());
			
			if (listaEstados[cont2/4-1].equals(estados.get(cont2/4-1))) {
			} else
			{
				cont3 = 1;
			}
			if (listaResultados[cont2/4-1].equals(resultados.get(cont2/4-1))) {
			} else {
				cont3 = 1;
			}
		}
		sleep(1000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(0).click();
		if (cont3 == 1) {
			resultado = false;
		} else {
			resultado = true;			
		}
		} else {
			if (x.getText().contains(partes[0])) {
				resultado = true;
			}
		}
		}
	return resultado;
	}
	
	public void SGTablaVisible () {
		tst.waitForVisible(driver, By.xpath("//*[@id=\"exportarTabla\"]/thead/tr/th[1]"), 10);
	}
	
	//Menu Cupos-Importacion
	
	public void CIImportarArchivo(String agente, String depositoLogico) throws Exception{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");  
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
	String path = "C:\\BefanArchivos\\ImpCupos" + time + ".txt";
		File cupos = new File(path);
		BufferedWriter c = new BufferedWriter(new FileWriter(cupos));
		DateTimeFormatter dft2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String time2 = dft2.format(now);
		
		String[] fecha = time2.split("/");
		if (Integer.parseInt(fecha[1])>=8) {
			fecha[2] = Integer.toString((Integer.parseInt(fecha[2])+1));
			fecha[1] = "01";
		} else
		{
			fecha[1] = Integer.toString((Integer.parseInt(fecha[1])+5));
			fecha[0] = "20";
		}
		
		c.write(agente + ";" + depositoLogico + ";" + time2 + ";" + fecha[0] + "/" + fecha[1] + "/" + fecha[2] + ";30000");
		c.close();
	WebElement uploadElement = driver.findElement(By.id("fileinput"));
	uploadElement.sendKeys(path);
	driver.findElement(By.cssSelector(".btn.btn-primary.btn-sm.btn-block.btn-continuar")).click();
	}
	
	//Menu Cupos-Gestion
	//Eliminar todos los cupos
	
	public void CGeliminar(String agente, String deposito) {
	int cont = 0;
	selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), agente);
	sleep(500);
	selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), deposito);
	sleep(500);
	selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), "Vigente");
	sleep(500);
	driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
	sleep(500);
	cont = (driver.findElements(By.name("eliminar"))).size();
	for (int i = 1; i <= cont; i++) {
		System.out.println(i);
		driver.findElements(By.name("eliminar")).get(0).click();
		sleep(500);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
		sleep(500);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(0).click();
		sleep(500);
	}
	driver.findElements(By.cssSelector(".btn.btn-link")).get(0).click();
	}
	
	public String cantidadCuposAgente(String agente, String deposito) {
		String cupos = "";
		ArrayList<String> resultados = new ArrayList<String>();
		int cont = 0;
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), agente);
		sleep(500);
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), deposito);
		sleep(500);
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), "Vigente");
		sleep(500);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(500);
		cont = driver.findElements(By.cssSelector(".ng-binding")).size();
		cont = cont -24;
		int cont2 = 22;
		
		while (cont2<=cont) {
			resultados.add(driver.findElements(By.cssSelector(".ng-binding")).get(cont2).getText());
			cont2 = cont2+1;
			resultados.add(driver.findElements(By.cssSelector(".ng-binding")).get(cont2).getText());
			cont2 = cont2+1;
			resultados.add(driver.findElements(By.cssSelector(".ng-binding")).get(cont2).getText());
			cont2 = cont2+8;
		}
		
		for (String x : resultados) {
			cupos = cupos + x + ",";
		}
		return cupos.substring(0, cupos.length()-1);
	}
	
	
	public void modificarCupos(String agente, String deposito) {
		int i = 0;
		int cuposTotales = 1;
		int cont2 = 0;
		int k = 0;
		int j = 0;
		boolean ricardo = false;
		String cupos = "";
		ArrayList<String> resultados = new ArrayList<String>();
		int cont = 0;
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), agente);
		sleep(500);
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), deposito);
		sleep(500);
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), "Vigente");
		sleep(500);
		driver.findElements(By.cssSelector(".btn.btn-primary")).get(0).click();
		sleep(500);
		
		
		while (cuposTotales != 0) {
		i = 0;
		resultados.clear();
		cont = driver.findElements(By.cssSelector(".ng-binding")).size();
		cont = cont -24;
		cont2 = 22;
		j = 0;
		cupos = "";
		ricardo=false;
		k = 0;
		
		while (cont2<=cont) {
			resultados.add(driver.findElements(By.cssSelector(".ng-binding")).get(cont2).getText());
			cont2 = cont2+1;
			resultados.add(driver.findElements(By.cssSelector(".ng-binding")).get(cont2).getText());
			cont2 = cont2+1;
			resultados.add(driver.findElements(By.cssSelector(".ng-binding")).get(cont2).getText());
			cont2 = cont2+8;
		}
		
		for (String x : resultados) {
			cupos = cupos + x + ",";
		}
		
		cupos = cupos.substring(0, cupos.length()-1);
		
		String[] holaQueTalTuComoEsta = cupos.split(",");
		
		cuposTotales = 0;
		while(j<holaQueTalTuComoEsta.length) {
			cuposTotales = cuposTotales + Integer.parseInt(holaQueTalTuComoEsta[j]);
			j = j + 3;
		}
		
		if (holaQueTalTuComoEsta.length==0) {
			
		} else {
			while (ricardo==false) {
				if (Integer.parseInt(holaQueTalTuComoEsta[i]) == 0) {

				} else {
				driver.findElements(By.name("modificarGuardar")).get(k).click();
				sleep(3500);
				driver.findElement(By.name("cantidadTotal")).click();
				sleep(3500);
				driver.findElement(By.name("cantidadTotal")).clear();
				sleep(3500);
				driver.findElement(By.name("cantidadTotal")).sendKeys(Integer.toString(Integer.parseInt(holaQueTalTuComoEsta[i+1])+Integer.parseInt(holaQueTalTuComoEsta[i+2])));
				sleep(3500);
				driver.findElements(By.name("modificarGuardar")).get(k).click();
				sleep(3500);
				driver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();
				sleep(3500);
				driver.findElements(By.cssSelector(".btn.btn-link")).get(0).click();
				sleep(3500);
				ricardo=true;
				}
				if (cuposTotales==0) {
					ricardo=true;
				} else {
					i = i + 3;
					k = k + 1;
				}

			}
		}
		
		}
	}
	
// Log out de befan
	
	public void LogOutBefan(WebDriver driver) {
		driver.findElements(By.cssSelector(".caret")).get(0).click();
		sleep(500);
		driver.findElement(By.name("salir")).click();
	}
	
	/**
	 * Hace click en un boton segun el Etiqueta del boton.
	 * Sirve para el boton Importar y el boton buscar de la ventana Gestion.
	 */
	public void clickEnBoton(String EtiquetaBoton) {
		List<WebElement> botones=driver.findElements(By.cssSelector(".btn.btn-primary"));
		System.out.println("Numero de Botones: "+botones.size());
	    for(WebElement opcion:botones)
	    	if(opcion.getText().equalsIgnoreCase(EtiquetaBoton))
	    		opcion.click();
	}
	
	public  int numeroDiasEntreDosFechas(Date fecha1, Date fecha2){
		
	     long startTime = fecha1.getTime();
	     long endTime = fecha2.getTime();
	     long diffTime = endTime - startTime;
	     return (int)TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
	}
	
	public Date fechaAvanzada() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, +1);
		cal.add(Calendar.MONTH, +1);
		//cal.add(Calendar.MINUTE, +1);
		date = cal.getTime();
		return (date);
	}
	
        
	
	/**Estados Predefinidos:
	 * 
	 * -Pendiente
	 * -Eliminado
	 * -En Proceso
	 * -Procesado.
	 * 
	 * @param estado
	 */
	public void selectEstado(String estado) {
		Select sEstado=new Select(this.Estado);
		sEstado.selectByVisibleText(estado);
	}
	
	public void setNombreArchivo(String nombreArchivo) {
		driver.findElement(By.className("col-lg-3")).findElement(By.tagName("input")).sendKeys(nombreArchivo);
		//this.nombreArchivo.sendKeys(nombreArchivo);
	}
	

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde.clear();
		this.fechaDesde.sendKeys(fechaDesde);
	}

	public void setFechaDesde() {
		this.fechaDesde.clear();
		this.fechaDesde.sendKeys("/00200F");
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta.clear();
		this.fechaHasta.sendKeys(fechaHasta);
	}
	
	public void SeleccionarFechas(String Desde, String Hasta) {
		setFechaDesde(Desde);
		setFechaHasta(Hasta);
	}
	
	public void clickAceptar() {
		this.aceptar.click();
	}
	
	public boolean verificarMensajeExitoso() {
		boolean confirmacion = false;
		for(WebElement x : driver.findElements(By.className("modal-body"))) {
			if(x.getText().toLowerCase().contains("satisfactoriamente")) {
				System.out.println(x.getText());
				confirmacion = true;
			}
		}
		return confirmacion;
	}
	
	public void cerrar() {
	driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'CERRAR')]")).click();
	}
	public void eliminar() {
	driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'ELIMINAR')]")).click();
	}
	
	public boolean buscarRegion(String sRegion) {
		driver.findElement(By.xpath("//*[@type='search']")).clear();
		driver.findElement(By.xpath("//*[@type='search']")).sendKeys(sRegion);
		sleep(3000);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
		
		boolean bAssert = false;
		
		for (WebElement wAux : wList) {
			if (wAux.getText().toLowerCase().contains(sRegion.toLowerCase())) {
				bAssert = true;
			}
			else {
				break;
			}
		}
		
		return bAssert;
	}
	
	public List<String> agregarPrefijos(String sRegion) {
		buscarRegion(sRegion);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group panel-group-alternative ng-scope']"));
		
		for (WebElement wAux : wList) {
			if (wAux.findElement(By.xpath("//*[@class='collapsed'] //*[@class='ng-binding']")).getText().equalsIgnoreCase(sRegion)) {
				wAux.click();
				wAux.findElement(By.xpath("//*[@class='row ng-scope'] //*[@class='btn btn-link']")).click();
				break;
			}
		}
		sleep(5000);
		List<String> sPrefijos = new ArrayList<String>();
		//List<WebElement> wPrefijos = driver.findElements(By.id("compatibility"));
		sPrefijos.add(driver.findElement(By.xpath("//*[@id='compatibility'][1] //label")).getText());
		System.out.println(driver.findElement(By.xpath("//*[@id='compatibility'][1] //label")).getText());
		driver.findElement(By.xpath("//*[contains(@class,'check-filter')] [1]")).click();
		sPrefijos.add(driver.findElement(By.xpath("//*[@id='compatibility'][2] //label")).getText());
		System.out.println(driver.findElement(By.xpath("//*[@id='compatibility'][2] //label")).getText());
		driver.findElement(By.xpath("//*[@id='compatibility'][2] //label /.. /input")).click();
		driver.findElement(By.xpath("//*[@ng-show='mensajeAgregarCtrl.container.showConfirmation'] //*[@class='btn btn-primary']")).click();
		sleep(3000);
		verificarMensajeExitoso();
		driver.findElement(By.xpath("//*[contains(@ng-show, 'container.showSuccess')] //*[@class='btn btn-primary']")).click();
		driver.navigate().refresh();
		
		buscarRegion(sRegion);
		wBody = driver.findElement(By.className("panel-data"));
		wList = wBody.findElements(By.xpath("//*[@class='panel-group panel-group-alternative ng-scope']"));
		
		for (WebElement wAux2 : wList) {
			if (wAux2.findElement(By.xpath("//*[@class='collapsed'] //*[@class='ng-binding']")).getText().equalsIgnoreCase(sRegion)) {
				wAux2.click();
				break;
			}
		}
		
		return sPrefijos;
	}
	
	public void buscarYAbrirRegion(String sRegion) {
		buscarRegion(sRegion);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group panel-group-alternative ng-scope']"));
		
		for (WebElement wAux : wList) {
			if (wAux.findElement(By.xpath("//*[@class='collapsed'] //*[@class='ng-binding']")).getText().equalsIgnoreCase(sRegion)) {
				wAux.click();
				break;
			}
		}
	}
	public void buscarR (String Region) {
	sleep(5000);
	driver.findElement(By.className("panel-data"));
	List <WebElement> region = driver.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
	for(WebElement x : region) {
	if(x.getText().toLowerCase().contains(Region)) {
		System.out.println(x.getText());
		x.click();
		break;
		}
	}	
}
	
	public boolean regiontExists(WebElement element) throws InterruptedException {
	    sleep(2000);
		    try {
 		      boolean isDisplayed = element.getSize().height > 0;
		       return isDisplayed;
		    }   catch (Exception ex) {
		         return false;
		  }
	}
	
	public void region() throws InterruptedException {
		//String Region="";
		if(regiontExists(driver.findElement(By.cssSelector(".alert.alert-dismissable.alert-danger")))) {
			System.out.println(driver.findElement(By.cssSelector(".alert.alert-dismissable.alert-danger")).getText());
			driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).getText().equalsIgnoreCase("Cancelar");
			driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).click();
			}
		else {
			driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Agregar')]")).click();
			sleep(5000);
			
			Assert.assertTrue(verificarMensajeExitoso());
			driver.findElement(By.xpath("//*[@ng-show='mensajeAgregarRegionCtrl.container.showSuccess']//*[@class='btn btn-primary']")).click();
		}
		//return true;
	
	}
	
	public boolean buscarRegionInexistente(String sRegion) {
		driver.findElement(By.xpath("//*[@type='search']")).clear();
		driver.findElement(By.xpath("//*[@type='search']")).sendKeys(sRegion);
		sleep(3000);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		
		boolean bAssert = false;
		List<WebElement> wList = new ArrayList<WebElement>();
		try {
			wList = wBody.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
		}
		catch(Exception eE) {
			bAssert = true;
		}
		
		if (bAssert) {
			Assert.assertTrue(bAssert);
		}
		else {
			bAssert = true;
			for (WebElement wAux : wList) {
				if (wAux.getText().equalsIgnoreCase(sRegion)) {
					bAssert = false;
				}
			}
		}
		
		return bAssert;
	}
	
}