package Pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.RowIdLifetime;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import Tests.TestBase;

class item {
	String nombre;
	String precioUni;
	String cantidad;
	String ivaC;
	String ivaBase;
	String totalC;
	String chargeCode;
	String descuento;
}

public class CalculoImpuestos extends BasePage {
	//private CustomerCare cc;
	private List<item> productos;
	private String totalFactura;
	private String totalImpuestosInv;
	private List<String> impuestosInv;
	
	public String determinarCategoriaIVA(WebDriver driver) {
		TestBase TB = new TestBase();
		String condicion = null;
		driver.switchTo().frame(TB.cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		tabla.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(1).findElement(By.tagName("a")).click();
		sleep(8000);
		driver.switchTo().frame(TB.cambioFrame(driver, By.cssSelector(".hasMotif.accountTab.detailPage.sfdcBody.brandQuaternaryBgr.ext-webkit.ext-chrome")));
		List<WebElement> tablas = driver.findElements(By.className("bRelatedList"));
		for(WebElement UnaT: tablas) {
			if (UnaT.findElement(By.className("pbTitle")).getText().toLowerCase().contains("taxconditions")) {
				Actions builder = new Actions(driver);   
				builder.moveToElement(UnaT.findElement(By.cssSelector(".dataCell.cellCol1")).findElement(By.tagName("a"))).click().build().perform();
				//UnaT.findElement(By.cssSelector(".dataCell.cellCol1")).findElement(By.tagName("a")).click();
				sleep(8000);
				break;
			}
		}
		driver.switchTo().frame(TB.cambioFrame(driver, By.cssSelector(".hasMotif.Custom69Tab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		tablas = driver.findElements(By.cssSelector(".bRelatedList.first"));
		for(WebElement UnaT: tablas) {
			if (UnaT.findElement(By.className("pbTitle")).getText().toLowerCase().contains("taxconditiondetails")) {
				condicion = UnaT.findElement(By.cssSelector(".dataCell.cellCol3")).getText(); 
				break;
			}
		}
		return condicion;
	}
	
	public String obtenerTaxGroup(String ChargeCode) throws IOException {
		String TaxGroup = null;
		int CTG = 0, CCCF = 0;
		File directory = new File("facturacion//Charge Code Template v4.05.xlsx");
		FileInputStream file = new FileInputStream(new File(directory.getAbsolutePath()));
		// Crear el objeto que tendra el libro de Excel
		XSSFWorkbook libro = new XSSFWorkbook(file);
		int cantHojas = libro.getNumberOfSheets();
		for (int i = 0; i<cantHojas; i++) {
			CCCF=0;
			CTG=0;
			XSSFSheet hoja = libro.getSheetAt(i);
			Iterator<Cell> celdas = hoja.getRow(0).cellIterator();
			Cell celda;
		    while (celdas.hasNext()){
		    	celda = celdas.next();
		    	if(celda.getStringCellValue().toLowerCase().contains("charge code final"))
		    		CCCF = celda.getColumnIndex();
		    	else
		    		if(celda.getStringCellValue().toLowerCase().contains("tax group"))
			    		CTG = celda.getColumnIndex();
		    	
		    }
		    Iterator<Row> filas = hoja.iterator();
			Row fila;
			// Recorremos todas las filas para mostrar el contenido de cada celda
			if(CCCF != 0 && CTG != 0) {
				while (filas.hasNext()){
				    fila = filas.next();
				    //System.out.println(fila.getCell(CCCF, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				    if(fila.getCell(CCCF,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals(ChargeCode)) {
				    	TaxGroup=fila.getCell(CTG).getStringCellValue();
				    	break;
				    }
				}
			}
			if(TaxGroup!=null)
				break;
		}
	    System.out.println("TaxGroup "+TaxGroup);
		return TaxGroup;
	}
	
	public List<String> obtenerImpuestos(String categoriaIVA, String taxGroup) throws IOException{
		List<String> impuestos = new ArrayList<String>();
		boolean categoria = false;
		File directory = new File("facturacion//Impuestos Alicuotas v07.xlsx");
		FileInputStream file = new FileInputStream(new File(directory.getAbsolutePath()));
		// Crear el objeto que tendra el libro de Excel
		XSSFWorkbook libro = new XSSFWorkbook(file);
		XSSFSheet hoja = libro.getSheetAt(0);
		Iterator<Row> filas = hoja.iterator();
		Row fila;
		while (filas.hasNext()){
		    fila = filas.next();
		    try {
			    if(fila.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals(categoriaIVA)|| categoria ==true) {
			    	categoria = true;
			    	if(!fila.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals(categoriaIVA)&&(!fila.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals(""))) 
			    		categoria = false;
			    	if(fila.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals(taxGroup)&&categoria==true) {
			    		Iterator<Cell> celdas = fila.cellIterator();
			    		System.out.println("Llegue aca");
			    		Cell celda;
			    		celdas.next();
			    		celdas.next();
			    	    while (celdas.hasNext()){
			    	    	celda = celdas.next();
			    	    	try {
			    	    		impuestos.add(Double.toString(celda.getNumericCellValue()));
			    	    	}catch(java.lang.IllegalStateException ex0) {}
			    	    	catch(java.lang.NullPointerException ex1) {}
			    	    }
			    		break;
			    	}
			    }
		    }catch(java.lang.IllegalStateException ex1) {}
		}
		System.out.println("Impuestos: "+impuestos);
		
		return impuestos;
	}
	
	public void mostrarImpuestos(List<String> impuestos) {
		for(String UnI : impuestos) {
			System.out.println(UnI);
		}
	}
	
	public boolean calculadoraInversaDat(List<String> impuestos, String montoFac, String ivaBase, String ivaC) {
		double Iva = Double.parseDouble(impuestos.get(0));
		DecimalFormat df = new DecimalFormat("#.00");
		impuestos.remove(0);
		double otrosImpuestos=0;
		for(String UnI: impuestos) {
			otrosImpuestos += Double.parseDouble(UnI);
		}
		double montoNeto = (Double.parseDouble(montoFac) * 100)/ (((Iva+otrosImpuestos)*100)+100);
		Assert.assertTrue(df.format(Double.parseDouble(ivaBase)).equals((df.format(montoNeto*Iva))));
		System.out.println("Neto: "+montoNeto);
		double montoImpuestos = montoNeto*Iva;
		for(String UnI: impuestos) {
			montoImpuestos += montoNeto*Double.parseDouble(UnI);
		}
		double montoCalculado=montoNeto+montoImpuestos;
		System.out.println("Calculado: "+montoCalculado);
		System.out.println("Facturado: "+montoFac);
		return (montoCalculado-0.1<Double.parseDouble(montoFac)&&Double.parseDouble(montoFac)<montoCalculado+0.1);
	}
	
	public String obtenerDatoFacturaDat(String campo) throws IOException {
		String valor = null;
		File directory = new File("facturacion//archivo5871.dat");
		String cadena;
        FileReader f = new FileReader(new File(directory.getAbsolutePath()).toString());
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
        	if(cadena.toLowerCase().contains(campo.toLowerCase())) {
        		System.out.println(cadena);
        		valor = cadena.split(" ")[1];
        		break;
        	}
        }
        b.close();  
		return valor;
	}
	
	public List<item> obtenerProductosFacturaDat() throws IOException {
		String valor = null;
		item nuevoIt;
		totalFactura = null;
		productos = new ArrayList<item>();
		productos.clear();
		List<item> items = new ArrayList<item>();
		File directory = new File("facturacion//archivo5871.dat");
		String cadena;
        FileReader f = new FileReader(new File(directory.getAbsolutePath()).toString());
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
        	if(cadena.toLowerCase().contains("<FLD>CARGO_ITEM".toLowerCase())) {
        		nuevoIt = new item();
        		nuevoIt.nombre=cadena.split(" ")[1];
        		nuevoIt.cantidad=valor;
        		cadena = b.readLine();
        		if(cadena.toLowerCase().contains("<FLD>PR_UNIT_ITEM".toLowerCase())) {
        			nuevoIt.precioUni= cadena.split(" ")[1];
        			cadena = b.readLine();
        		}
        		if(cadena.toLowerCase().contains("<FLD>IVA_C".toLowerCase())) {
        			nuevoIt.ivaC= cadena.split(" ")[1];
        			cadena = b.readLine();
        		}
        		if(cadena.toLowerCase().contains("<FLD>IVA_BASE".toLowerCase())) {
        			nuevoIt.ivaBase= cadena.split(" ")[1];
        			cadena = b.readLine();
        		}
        		if(cadena.toLowerCase().contains("<FLD>TOTAL_C".toLowerCase())) {
        			nuevoIt.totalC= cadena.split(" ")[1];
        		}
        		items.add(nuevoIt);
        	}
        	if(cadena.toLowerCase().contains("<FLD>CANT_ITEM".toLowerCase())) {
        		valor = cadena.split(" ")[1];
        	}
        	if(cadena.toLowerCase().contains("<GLB>VALOR_TOTAL_F".toLowerCase())){
        		totalFactura=cadena.split(" ")[1];
        	}
        }
        b.close();  
        productos.addAll(items);
        return items;
	}
	
	public void mostrarProductos(List<item> productos) {
		for(item UnI : productos) {
			mostrarProducto(UnI);
		}
	}
	public void mostrarProducto(item UnI) {
		System.out.println("nombre:"+UnI.nombre);
		System.out.println("Precio:"+UnI.precioUni);
		System.out.println("Cantidad:"+UnI.cantidad);
		System.out.println("Iva C:"+UnI.ivaC);
		System.out.println("Iva Base:"+UnI.ivaBase);
		System.out.println("Total C:"+UnI.totalC);
		System.out.println("Descuento:"+UnI.descuento);
	}
	
	public void validarMontoProductosDat(String categoriaIva) throws IOException {
		double sumaUnitarios = 0;
		obtenerProductosFacturaDat();
		int i = 1;
		for(item UnI: productos) {
			System.out.println("*-*-*-*-*ITEM "+i+"*-*-*-*-*");
			i++;
			mostrarProducto(UnI);
			Assert.assertTrue(calculadoraInversaDat(obtenerImpuestos(categoriaIva, obtenerTaxGroup(ObtenerChargeCode(UnI.nombre))),UnI.totalC,UnI.ivaBase,UnI.ivaC));
			sumaUnitarios+=Double.parseDouble(UnI.totalC);
		}
		Assert.assertTrue(sumaUnitarios-0.1<Double.parseDouble(totalFactura)&&Double.parseDouble(totalFactura)<sumaUnitarios+0.1);
	}
	
	public void guardarFacturaInv(List<String> data) throws IOException {
		File archivo=new File("facturacion//inv//ultimaFactura.txt");
		if (archivo.exists())
			archivo.delete();
		FileWriter ArchiSa=new FileWriter(archivo.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(ArchiSa);
		PrintWriter wr = new PrintWriter(bw); 
		for(String UnD: data)
			wr.append(UnD+"\r\n");
		wr.close();
		bw.close();
		ArchiSa.close();
	}
	
	public String buscarArchivoEnDirectorio(String carpeta, String extension) {
		String nombre = null;
		File directory = new File(carpeta);
		directory = new File(directory.getAbsolutePath());
		File[] archivos = directory.listFiles();
		if (archivos != null) {
			for (int i = 0; i < archivos.length; i++) {
				if (!archivos[i].isDirectory()) { 
					if (archivos[i].getName().endsWith(extension)) {
						nombre = archivos[i].getName();
					}

				}
			}
		}
		return nombre;
	}
	
	public void separarFacturaDeInv(String cuenta, String cargo) throws IOException {
		boolean estaCuenta = false, estaCargo = false;
		List<String> items = new ArrayList<String>();
		
		File directory = new File("facturacion//"+buscarArchivoEnDirectorio("facturacion//",".txt"));
		String cadena;
        FileReader f = new FileReader(new File(directory.getAbsolutePath()).toString());
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
        	if(cadena.startsWith("00000")) {
        		if(estaCuenta == true && estaCargo == true) {
        			guardarFacturaInv(items);
        			break;
        		}
        		else {
        			estaCuenta = false;
        			estaCargo = false;
        			items.clear();
        		}
        	}
        	if(cadena.startsWith("10101")) 
        		if(cadena.contains(cuenta)) {
        			estaCuenta = true; System.out.println("****************ESTA CUENTA********************");}
        	if((cadena.startsWith("22011")||cadena.startsWith("22111")||cadena.startsWith("22211")) && estaCargo == false) 
        		if(cadena.toLowerCase().contains(cargo.toLowerCase())) {
        			estaCargo = true; System.out.println("****************ESTA CARGO********************");}
        	items.add(cadena);
        	System.out.println("Linea: "+cadena);
        }
        b.close();
        if(estaCuenta==false && estaCargo==false) {
        	guardarFacturaInv(null);
        }
	}
	public List<item> obtenerProductosFacturaInvTxt() throws IOException {
		item nuevoIt;
		totalFactura = null;
		totalImpuestosInv = null;
		productos = new ArrayList<item>();
		productos.clear();
		impuestosInv = new ArrayList<String>();
		impuestosInv.clear();
		List<item> items = new ArrayList<item>();
		File directory = new File("facturacion//inv//ultimaFactura.txt");
		String cadena;
        FileReader f = new FileReader(new File(directory.getAbsolutePath()).toString());
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
        	if(cadena.startsWith("22011")||cadena.startsWith("22111")) {
        		System.out.println("Cadena= "+cadena);
        		if(cadena.split("\\|").length>1) {
	        		nuevoIt = new item();
	        		nuevoIt.nombre=cadena.split("\\|")[1];
	        		nuevoIt.cantidad=cadena.split("\\|")[2];
	        		nuevoIt.precioUni=cadena.split("\\|")[3].replace(",", ".");
	        		nuevoIt.descuento=cadena.split("\\|")[4].replace(",", ".");
	        		nuevoIt.ivaC=cadena.split("\\|")[5].replace(",", ".");
	        		nuevoIt.totalC=cadena.split("\\|")[6].replace(",", ".");
	        		items.add(nuevoIt);
        		}
        	}
        	if(cadena.startsWith("22300")) {
        		if(cadena.split("\\|").length>1)
        			totalImpuestosInv = cadena.split("\\|")[1].replace(",", ".");
        	}
        	if(cadena.startsWith("22311")){
        		if(cadena.split("\\|").length>1)
        		impuestosInv.add(cadena.split("\\|")[2].replace(",", "."));
        	}
        	if(cadena.startsWith("35800")){
        		totalFactura = cadena.split("\\|")[1].replace(",", ".");
        	}
        }
        b.close();  
        productos.addAll(items);
        System.out.println(impuestosInv);
        if(totalImpuestosInv==null) {
        	totalImpuestosInv="0.0";
        }
        return items;
	}
	//La lista retornada poseera solo 4 elementos en estricto orden, monto de iva, monto de otros impuestos, monto total, monto con descuento.
	public List<Double> calculadoraInvTxt(List<String> impuestos, item cargo) {
		double Iva = Double.parseDouble(impuestos.get(0));
		List<Double> montos = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("#.00");
		impuestos.remove(0);
		double otrosImpuestos=0;
		for(String UnI: impuestos) {
			otrosImpuestos += Double.parseDouble(UnI);
		}
		Assert.assertTrue(Double.parseDouble(cargo.totalC)==(Double.parseDouble(cargo.precioUni)*Integer.parseInt(cargo.cantidad))+(Double.parseDouble(cargo.descuento)));
		montos.add(Double.parseDouble(cargo.totalC)*Iva);
		montos.add(Double.parseDouble(cargo.totalC)*otrosImpuestos);
		montos.add(Double.parseDouble(cargo.totalC)+montos.get(0)+montos.get(1));
		montos.add(montos.get(2)-Double.parseDouble(cargo.totalC)*Double.parseDouble(cargo.descuento));
		return (montos);
	}
	
	public void validarMontoProductosInvTxt(String categoriaIva) throws IOException {
		double sumaIva = 0, sumaOtrosImpuestos= 0, sumaTotales= 0, TotalconDescuentos = 0;
		DecimalFormat df = new DecimalFormat("#.00");
		List<Double> montos = new ArrayList<Double>();
		obtenerProductosFacturaInvTxt();
		int i = 1;
		for(item UnI: productos) {
			montos.clear();
			System.out.println("*-*-*-*-*ITEM "+i+"*-*-*-*-*");
			i++;
			mostrarProducto(UnI);
			montos = (calculadoraInvTxt(obtenerImpuestos(categoriaIva, obtenerTaxGroup(ObtenerChargeCode(UnI.nombre))),UnI));
			sumaIva+=montos.get(0);
			sumaOtrosImpuestos+=montos.get(1);
			sumaTotales+=montos.get(2);
			TotalconDescuentos+=montos.get(3);
		}
		System.out.println("sumaIva "+sumaIva);
		System.out.println("sumaotrosimpuestos "+sumaOtrosImpuestos);
		System.out.println("sumaTotales "+sumaTotales);
		System.out.println("TotalconDescuentos "+TotalconDescuentos);
		Assert.assertTrue(Double.parseDouble(totalImpuestosInv)-0.1<=(sumaIva+sumaOtrosImpuestos)&&(sumaIva+sumaOtrosImpuestos)<=Double.parseDouble(totalImpuestosInv)+0.1);
		if(impuestosInv.size()>0)
		Assert.assertTrue(Double.parseDouble(impuestosInv.get(0))-0.1<=sumaIva&&sumaIva<=Double.parseDouble(impuestosInv.get(0))+0.1);
		Assert.assertTrue(Double.parseDouble(totalFactura)-0.1<=TotalconDescuentos&&TotalconDescuentos<=Double.parseDouble(totalFactura)+0.1);
	}
	
	public String ObtenerChargeCode(String cargo) throws IOException {
		String chargeCode = null;
		File directory = new File("facturacion//FAN- Productos y chargecodes asociados- v1.0.xlsx");
		FileInputStream file = new FileInputStream(new File(directory.getAbsolutePath()));
		// Crear el objeto que tendra el libro de Excel
		XSSFWorkbook libro = new XSSFWorkbook(file);
		int cantHojas = libro.getNumberOfSheets();
		for (int i = 1; i<cantHojas; i++) {
			XSSFSheet hoja = libro.getSheetAt(i);
			Iterator<Row> filas = hoja.iterator();
			Row fila;
			// Recorremos todas las filas para mostrar el contenido de cada celda
			while (filas.hasNext()){
			    fila = filas.next();
			    if(fila.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equalsIgnoreCase(cargo)) {
			    	chargeCode=fila.getCell(0).getStringCellValue();
			    	break;
			    }
			}
			if(chargeCode!=null)
				break;
		}
		if(chargeCode.charAt(chargeCode.length()-1)==' ') {
			chargeCode = chargeCode.substring(0, chargeCode.length()-1);
		}
		return chargeCode;
	}
}
