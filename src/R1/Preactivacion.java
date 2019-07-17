package R1;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DataProvider.ExcelUtils;
import Pages.BeFan;
import Pages.DPW;
import Pages.setConexion;
import Tests.TestBase;

public class Preactivacion extends TestBase {

	private WebDriver driver;
	String detalles;
	
	
	@BeforeClass (alwaysRun = true)
	public void initMayo() {
		driver = setConexion.setupEze();
		loginBeFANVictor(driver, "mayorista");
	}

	//@AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	@Test
    public void TS127617_BeFan_Movil_REPRO_Preactivacion_repro_Envio_de_lote_a_OM_Verificacion_de_lineas_enviadas() throws Exception {
    	BeFan Botones = new BeFan(driver);
        Object[][] testObjArray = ExcelUtils.getTableArray(dataProviderE2E(), "seriales", 1,1,8, "Preactivacion");
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
        nombreArchivo = "seriales" + nombreArchivo.substring(nombreArchivo.length()-18, nombreArchivo.length()-4);
        DPW.main();
        
	}
}