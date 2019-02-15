package Tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.HomeBase;
import Pages.TechCare_Ola1;
import Pages.setConexion;

public class TechnicalCareCSRAutogestionA extends TestBase{

	private WebDriver driver;
	
	@BeforeClass(alwaysRun=true)
	public void init() throws Exception
	{
		this.driver = setConexion.setupEze();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		login(driver);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		goInitToConsolaFanF3(driver);
	    try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	     //Alerta Aparece Ocasionalmente
	       try {
				driver.switchTo().alert().accept();
				driver.switchTo().defaultContent();
			}catch(org.openqa.selenium.NoAlertPresentException e) {}

       CustomerCare cerrar = new CustomerCare(driver);
       cerrar.cerrarultimapestana();
		
		//Selecciona Cuentas
       TechCare_Ola1 page=new TechCare_Ola1(driver);
		sleep(3000);
		page.selectAccount("Adrian Tech");
		driver.switchTo().defaultContent();
		sleep(3000);
		
		//selecciona el campo donde esta la busquedad escribe y busca
		searchAndClick(driver, "Diagnóstico de Autogestión");
       
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		
		
	}
	
	
	//@AfterMethod(alwaysRun=true)
	public void after() {
		sleep(2000);
		
	}
	
	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		sleep(1000);
		CustomerCare cerrar = new CustomerCare(driver);
		cerrar.cerrarultimapestana();
		HomeBase homePage = new HomeBase(driver);
		sleep(1000);
		homePage.selectAppFromMenuByName("Ventas");
		sleep(1000);
		driver.quit();
		sleep(1000);
	}
	
	/**Verificar que al seleccionar canal (web) servicio (Internet Movil). el Inconveniente, pueda contar con la siguiente lista de opciones:
- Modulo caido
- Informacion Incorrecta
- Informacion Incompleta
- Inconveniente para alta de FOL
- Incon. alta de Nros. Amigos
- Inconveniente para alta DA
- No envia configuracion
- No informa Pin y Puk
- Inconveniente con Informe de pago
-Incon.con Compra de packs
- Otros ( para completar campo)
	 * @author Almer
	 */
	@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
	public void TS51276_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_WEB_Servicio_Internet_Movil() {
		//
		String[] consultar= {"otros","modulo caído","informacion incorrecta","información incompleta",
							"inconveniente para alta de fol", "incon.  alta de nros. amigos","inconveniente para alta da",
							"no envía configuración","no informa pin y puk","inconveniente con informe de pago","incon.con compra de packs"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
		
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("WEB");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Internet Movil");
		
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));		
	}
	
	/**Verificar que al seleccionar canal (web) servicio (Internacional). el Inconveniente, pueda contar con la siguiente lista de opciones:
	- Modulo caido
	- Informacion Incorrecta
	- Informacion Incompleta
	- Inconveniente para alta de FOL
	- Incon. alta de Nros. Amigos
	- Inconveniente para alta DA
	- No envia configuracion
	- No informa Pin y Puk
	- Inconveniente con Informe de pago
	-Incon.con Compra de packs
	- Otros ( para completar campo)
		 * @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51278_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_WEB_Servicio_Internacional() {
			//
			String[] consultar= {"otros","modulo caído","informacion incorrecta","información incompleta",
								"inconveniente para alta de fol", "incon.  alta de nros. amigos","inconveniente para alta da",
								"no envía configuración","no informa pin y puk","inconveniente con informe de pago","incon.con compra de packs"};
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			BasePage cambioFrameByID=new BasePage();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			//driver.findElement(By.id("ChannelSelection")).click();
			
			Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
			sCanal.selectByVisibleText("WEB");
			Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
			sServicio.selectByVisibleText("Internacional");
			
			List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
			
			assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		
		
		/**Verificar que al seleccionar canal(web) servicio (Apps, Musica y +). el Inconveniente, pueda contar con la siguiente lista de opciones:
	- Modulo caido
	- Informacion Incorrecta
	- Informacion Incompleta
	- Inconveniente para alta de FOL
	- Incon. alta de Nros. Amigos
	- Inconveniente para alta DA
	- No envia configuracion
	- No informa Pin y Puk
	- Inconveniente con Informe de pago
	-Incon.con Compra de packs
	- Otros ( para completar campo)
		 * @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51273_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_WEB_Servicio_Apps_Musica_y_mas() {
			//
			String[] consultar= {"otros","modulo caído","informacion incorrecta","información incompleta",
								"inconveniente para alta de fol", "incon.  alta de nros. amigos","inconveniente para alta da",
								"no envía configuración","no informa pin y puk","inconveniente con informe de pago","incon.con compra de packs"};
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			BasePage cambioFrameByID=new BasePage();
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
			try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
			//driver.findElement(By.id("ChannelSelection")).click();
			
			Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
			sCanal.selectByVisibleText("WEB");
			Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
			sServicio.selectByVisibleText("Apps, Musica y +");
			
			List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
			assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		/**Verificar que al seleccionar canal(0800) servicio (0800-888-7382 (Activaciones)). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- La caracteristica No existe
		- La linea esta muda
		- Llamada fallo
		- Tono ocupado 
		- La llamada se cae
		- Informa Sistema fuera de Servicio
		- Informacion Incorrecta
		- No valida contraseña
		- Incov con derivacion a representante
		- Otros ( para completar campo)
			 * @author Almer
			 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51249_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_0800_y_Servicio_0800_888_7382() {
				//
				String[] consultar= {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado",
									"la llamada se cae", "informa sistema fuera de servicio","informacion incorrecta",
									"inconv con derivación a representante","otros","no valida contraseña"};
				try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
				BasePage cambioFrameByID=new BasePage();
				driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
				try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
				//driver.findElement(By.id("ChannelSelection")).click();
				
				Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
				sCanal.selectByVisibleText("0800");
				Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
				sServicio.selectByVisibleText("0800-888-7382 (Activaciones)");
				
				List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
				//for(WebElement a:motivoIncoveniente)
					//System.out.println(a.getText().toLowerCase());
				assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
			}
			
			
			/**Verificar que al seleccionar canal(0800) servicio (0800-888-4422 (Atención Pymes). el Inconveniente, pueda contar con la siguiente lista de opciones:
			- La caracteristica No existe
			- La linea esta muda
			- Llamada fallo
			- Tono ocupado 
			- La llamada se cae
			- Informa Sistema fuera de Servicio
			- Informacion Incorrecta
			- No valida contraseña
			- Incov con derivacion a representante
			- Otros ( para completar campo)
				 * @author Almer
				 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51247_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_0800_y_Servicio_0800_888_4422() {
					//
		String[] consultar= {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado",
										"la llamada se cae", "informa sistema fuera de servicio","informacion incorrecta",
										"inconv con derivación a representante","otros","no valida contraseña"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
					
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("0800");
	    Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("0800-888-4422 (Atención Pymes");
					
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
			//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
				}
				
		/**Verificar que al seleccionar canal(0800) servicio (0800-444-0800 (AC)). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- La caracteristica No existe
		- La linea esta muda
		- Llamada fallo
		- Tono ocupado 
		- La llamada se cae
		- Informa Sistema fuera de Servicio
		- Informacion Incorrecta
		- No valida contraseña
		- Incov con derivacion a representante
		- Otros ( para completar campo)
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51243_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_0800_y_Servicio_0800() {
						//
		String[] consultar= {"la caracteristica no existe","la linea esta muda","llamada fallo","tono ocupado",
											"la llamada se cae", "informa sistema fuera de servicio","informacion incorrecta",
											"inconv con derivación a representante","otros","no valida contraseña"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("0800");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("0800-444-0800 (AC)");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		/**Verificar que al seleccionar canal(USSD) servicio (*724# (IPago)). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- No Interactúa
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51311_CRM_Fase_3_Technical_Care_CSR_uSSD_Visualizacion_de_Lista_de_Inconvenientes_Asterisco_724() {
						//
		String[] consultar= {"no interactúa"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("USSD");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("*724# (IPago)");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		
		/**Verificar que al seleccionar canal(USSD) servicio (*156# (roaming)). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- No Interactúa
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51307_CRM_Fase_3_Technical_Care_CSR_uSSD_Visualizacion_de_Lista_de_Inconvenientes_Asterisco_156() {
						//
		String[] consultar= {"no interactúa"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("USSD");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("*156# (roaming)");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		/**Verificar que al seleccionar canal(USSD) servicio (*150# (saldo)). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- No Interactúa
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51309_CRM_Fase_3_Technical_Care_CSR_uSSD_Visualizacion_de_Lista_de_Inconvenientes_Asterisco_150() {
						//
		String[] consultar= {"no interactúa"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("USSD");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("*150# (saldo)");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		
		/**Verificar que al seleccionar canal(WAP) servicio (Estado de la cuenta). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- informacion incorrecta
		-información incompleta
		-abre aplicación y cierra automáticamente
		-sitio caído/ no carga información
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51297_CRM_Fase_3_Technical_Care_CSR_Autogestion_WAP_Visualizacion_de_Lista_de_Inconvenientes_Estado_de_la_Cuenta() {
						//
		String[] consultar= {"informacion incorrecta","información incompleta","abre aplicación y cierra automáticamente","sitio caído/ no carga información"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("WAP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Estado de la cuenta");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		/**Verificar que al seleccionar canal(WAP) servicio (Email). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- informacion incorrecta
		-información incompleta
		-abre aplicación y cierra automáticamente
		-sitio caído/ no carga información
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51305_CRM_Fase_3_Technical_Care_CSR_Autogestion_WAP_Email_Visualizacion_de_Lista_de_Inconvenientes() {
						//
		String[] consultar= {"informacion incorrecta","información incompleta","abre aplicación y cierra automáticamente","sitio caído/ no carga información"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("WAP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Email");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}
		
		/**Verificar que al seleccionar canal(WAP) servicio (Domicilio de facturacion). el Inconveniente, pueda contar con la siguiente lista de opciones:
		- informacion incorrecta
		-información incompleta
		-abre aplicación y cierra automáticamente
		-sitio caído/ no carga información
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51303_CRM_Fase_3_Technical_Care_CSR_Autogestion_WAP_Domicilio_de_acturacion_Visualizacion_de_Lista_de_Inconvenientes() {
						//
		String[] consultar= {"informacion incorrecta","información incompleta","abre aplicación y cierra automáticamente","sitio caído/ no carga información"};
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("WAP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Domicilio de facturacion");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(verificarContenidoLista(consultar, motivoIncoveniente));
		}

		/**Verificar que al seleccionar canal(APP) servicio (Centros de Atencion). el Inconveniente, pueda contar con al menos una opcion
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51289_CRM_Fase_3_Technical_Care_CSR_Autogestion_Verificacion_de_la_seleccion_Canal_App_y_Servicio_Centros_de_Atencion() {
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("APP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Centros de Atencion");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(motivoIncoveniente.size()>0);
		}
		
		/**Verificar que al seleccionar canal(APP) servicio (Mis Consumos). el Inconveniente, pueda contar con al menos una opcion
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51288_CRM_Fase_3_Technical_Care_CSR_Autogestion_APP_Mis_Consumos_Visualizacion_de_Lista_de_Inconvenientes() {
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("APP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Mis Consumos");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(motivoIncoveniente.size()>0);
		}
		
		/**Verificar que al seleccionar canal(WEB) servicio (Mis Consumos). el Inconveniente, pueda contar con al menos una opcion
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51287_CRM_Fase_3_Technical_Care_CSR_Autogestion_Visualizacion_de_Lista_de_Inconvenientes_Canal_WEB_Servicio_Mis_Consumos() {
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("WEB");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Mis Consumos");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(motivoIncoveniente.size()>0);
		}
		
		/**Verificar que al seleccionar canal(APP) servicio (Club Personal). el Inconveniente, pueda contar con al menos una opcion
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51292_CRM_Fase_3_Technical_Care_CSR_Autogestion_APP_Club_Personal_Visualizacion_de_Lista_de_Inconvenientes() {
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("APP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Club Personal");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(motivoIncoveniente.size()>0);
		}
		
		/**Verificar que al seleccionar canal(APP) servicio (Centros de Atencion). el Inconveniente, pueda contar con al menos una opcion
		* @author Almer
		 */
		@Test(groups= {"Fase3","TechnicalCare","Autogestion","Ola2"})
		public void TS51290_CRM_Fase_3_Technical_Care_CSR_Autogestion_APP_Centros_de_Atencion_Visualizacion_de_Lista_de_Inconvenientes() {
	
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("ChannelSelection")));
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
		//driver.findElement(By.id("ChannelSelection")).click();
						
		Select sCanal=new Select(driver.findElement(By.id("ChannelSelection")));
		sCanal.selectByVisibleText("APP");
		Select sServicio=new Select(driver.findElement(By.id("ServiceSelection")));
		sServicio.selectByVisibleText("Centros de Atencion");
						
		List <WebElement> motivoIncoveniente=driver.findElement(By.id("MotiveSelection")).findElements(By.tagName("option"));
		//for(WebElement a:motivoIncoveniente)
		//System.out.println(a.getText().toLowerCase());
		assertTrue(motivoIncoveniente.size()>0);
		}
		
		
}
