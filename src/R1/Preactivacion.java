package R1;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DataProvider.ExcelUtils;
import Pages.BeFan;
import Pages.DPW;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class Preactivacion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private BeFan bf;
	
	
	@BeforeClass (alwaysRun = true)
	public void initMayo() {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		bf = new BeFan(driver);
		log = new LoginFw(driver);
		loginBeFANVictor(driver, "mayorista");
	}

	//@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- MAYORISTA -------------------------------------------------------\\
	
	@Test
    public void TS127617_BeFan_Movil_REPRO_Preactivacion_repro_Envio_de_lote_a_OM_Verificacion_de_lineas_enviadas() throws Exception {
        Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(), "seriales", 1,1,8, "Preactivacion");
        String nombreArchivo = "SerialBalido";
        int filaExcel = 0;
        for (int i=0; i<testObjArray.length; i++) {
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
        nombreArchivo = bf.LecturaDeDatosTxt(path + "\\seriales.txt", numeroDeLineas);
        sendKeysBy(driver, By.id("fileinput"), nombreArchivo, 0);
        clickBy(driver, By.xpath("//button[contains(text(),'Importar')]"), 0);
        try {
        	Assert.assertTrue(getTextBy(driver, By.xpath("//h3[contains(text(),'correctamente')]"), 0).contentEquals("El archivo se import\u00f3 correctamente."));
        	clickBy(driver, By.cssSelector(".btn.btn-link"), 0);
        } catch(Exception e) {
        	clickBy(driver, By.cssSelector(".btn.btn-link"), 0);
        }
        nombreArchivo = "seriales" + nombreArchivo.substring(nombreArchivo.length()-18, nombreArchivo.length()-4);
        DPW.main();
        sleep(10000);
        seleccionOpcion(driver, "sims", "gestion");
		clickBy(driver, By.xpath("//option[contains(text(), 'Estado')]"), 0);
		clickBy(driver, By.xpath("//option[contains(text(), 'En Proceso')]"), 0);
		driver.findElement(By.cssSelector("input[class*='form-control ng']")).clear();
		sendKeysBy(driver, By.cssSelector("input[class*='form-control ng']"), nombreArchivo, 0);
		clickBy(driver, By.name("buscar"), 0);
		sleep(5000);
		clickBy(driver, By.cssSelector("td [class='btn btn-primary btn-xs']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("botonExportar")));
		String linea = driver.findElement(By.cssSelector("[class='modal-body'] tbody tr td:nth-child(2)")).getText();
		sleep(1800000);
		log.loginOOCC();
		ges.irAConsolaFAN();
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		driver.findElement(By.id("PhoneNumber")).sendKeys("2616052355");
		driver.findElement(By.id("SearchClientsDummy")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--icon slds-m-right--x-small ng-scope']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-table slds-table--bordered slds-tree slds-table--tree table tableCSS'] tbody th div a")).getText().equalsIgnoreCase("Repro" + linea));
	}
}