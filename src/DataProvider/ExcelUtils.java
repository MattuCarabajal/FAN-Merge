package DataProvider;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	public class ExcelUtils {
		 
		private static XSSFSheet ExcelWSheet;

		private static XSSFWorkbook ExcelWBook;
		

		private static XSSFCell Cell;

		private static XSSFRow Row;

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

	public static void setExcelFile(String Path,String SheetName) throws Exception {

		   try {

				// Open the Excel file

				FileInputStream ExcelFile = new FileInputStream(Path);

				// Access the required test data sheet

				ExcelWBook = new XSSFWorkbook(ExcelFile);

				ExcelWSheet = ExcelWBook.getSheet(SheetName);

				} catch (Exception e){

					throw (e);

				}

		}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public static String getCellData(int RowNum, int ColNum) throws Exception{
		String CellData;
	   try{
		   
		  Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		  CellData = Cell.getStringCellValue();
		  
		  if(CellData=="") CellData=" ***** ";
		  
		  }catch (java.lang.IllegalStateException e){
			  
			CellData = Double.toString(Cell.getNumericCellValue());
			if(CellData.contains("E")) 
    			{	
    			CellData = Double.toString(Cell.getNumericCellValue());
    			CellData = CellData.substring(0, CellData.indexOf("E")).replace(".","" );
    			}
			
			if(CellData.contains(".")) CellData=CellData.replace(".", "");
			}catch(java.lang.NullPointerException a) {CellData="**";}
	   
	   return CellData;
		}

	public static String getTestCaseName(String sTestCase)throws Exception{
		String value = sTestCase;
		System.out.print("Aqui va lo que queremos ver: "+value);
		try{
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			return value;
				}catch (Exception e){ e.printStackTrace();
			throw (e);
					}
		}

	public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
		int i;
		try {
			int rowCount = ExcelUtils.getRowUsed();
			for ( i=0 ; i<rowCount; i++){
				if  (ExcelUtils.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
					break;
				}
			}
			return i;
				}catch (Exception e){
			throw(e);
			}
		}

	public static int getRowUsed() throws Exception {
			try{
				int RowCount = ExcelWSheet.getLastRowNum();
				return RowCount;
			}catch (Exception e){
				System.out.println(e.getMessage());
				throw (e);
			}
		}

	/**
	 * Retorna una matriz completa con los datos de excel, segun laa dimensiones especificada.
	 * 
	 * OJO: El numero de Colummna a tomar despues de PrimerColumna, es la resta de PrimeraColumna-CantidadColumna
	 * @param FilePath
	 * @param SheetName
	 * @param primerRegistro
	 * @param PrimeraColumna
	 * @param cantidadColumna
	 * @return
	 * @throws Exception
	 */
	public static Object[][] getTableArray(String FilePath, String SheetName, int primerRegistro, int PrimeraColumna, int CantidadColumna) throws Exception {   

		   String[][] tabArray = null;

		   try {

			   FileInputStream ExcelFile = new FileInputStream(FilePath);
			   // Lectura de Excel
			   ExcelWBook = new XSSFWorkbook(ExcelFile);
			   ExcelWSheet = ExcelWBook.getSheet(SheetName);
			   
			   //Seleccion de Matriz
			   int startRow = primerRegistro;
			   int startCol = PrimeraColumna;
			   int ci,cj;
			   int totalRows = ExcelWSheet.getLastRowNum();

			   // Cantidad de Columnas a partir de la primera
			   int totalCols = CantidadColumna;

			   tabArray=new String[totalRows][totalCols];

			   ci=0;

			   for (int i=startRow;i<=totalRows;i++, ci++) {           	   
				  cj=0;
				   for (int j=startCol;j<=totalCols;j++, cj++){
					   tabArray[ci][cj]=getCellData(i,j);
					   System.out.print("["+tabArray[ci][cj]+"]");  
						}
				   System.out.println("\n");
					}
				}
			catch (FileNotFoundException e){
				System.out.println("Could not read the Excel sheet");
				e.printStackTrace();
				}

			catch (IOException e){
				System.out.println("Could not read the Excel sheet");
				e.printStackTrace();
				}

			return(tabArray);

			}
	
	
	/**
	 * Retorna una matriz de dimensiones especificadas segun el registro que aparezca en el primera columnna
	 * EJEMPLO: Todos los datos de las Cuentas Inactivas en Sales.
	 * 
	 * @param FilePath
	 * @param SheetName
	 * @param primerRegistro
	 * @param PrimeraColumna
	 * @param CantidadColumna
	 * @param Registro
	 * @return
	 * @throws Exception
	 */
	public static Object[][] getTableArray(String FilePath, String SheetName, int primerRegistro, int PrimeraColumna, int CantidadColumna, String Registro) throws Exception {   

		   String[][] tabArrayFiltrada = null;

		   try {

			   FileInputStream ExcelFile = new FileInputStream(FilePath);
			   // Lectura de Excel
			   ExcelWBook = new XSSFWorkbook(ExcelFile);
			   ExcelWSheet = ExcelWBook.getSheet(SheetName);
			   
			   //Seleccion de Matriz a Devolver
			   int startRow = primerRegistro;
			   int startCol = PrimeraColumna;
			   int ci,cj;
			   int totalRows = ExcelWSheet.getLastRowNum();

			   // Cantidad de Columnas a partir de la primera que se van a retornar
			   int totalCols = CantidadColumna;
			   
			   //Recorro la lista de comparacion para determinar la dimension del arreglo en i
			   int ndeRegistros=0;
			   for (int i=startRow;i<=totalRows;i++) {           	   
				  if(getCellData(i,0).contains(Registro)) {
					  ndeRegistros++;
				  }
			   }
			   
			   //Defino el Arreglo a Retornar
			   tabArrayFiltrada=new String[ndeRegistros][totalCols];
			   
			   //Recorro todo el Excel
			   ci=0;
			   for (int i=startRow;i<=totalRows;i++) {           	   
				  cj=0;
				  
				  //Guardo en el Arreglo a Retornar los Valores que necesito
				  if(getCellData(i,0).contains(Registro)) {
				   for (int j=startCol;j<=totalCols;j++, cj++){
					   
						   tabArrayFiltrada[ci][cj]=getCellData(i,j);
						   System.out.print("["+tabArrayFiltrada[ci][cj]+"]");
						   }
				   		ci++;
				   		System.out.println("\n");
						}
					}   
			}
			catch (FileNotFoundException e){
				System.out.println("Could not read the Excel sheet");
				e.printStackTrace();
				}

			catch (IOException e){
				System.out.println("Could not read the Excel sheet");
				e.printStackTrace();
				}
			return(tabArrayFiltrada);
			}
	

	/**
	 * Retorna una matriz de dimensiones especificadas segun el registro que aparezca en el primera columnna
	 * EJEMPLO: Todos los datos de las Cuentas Inactivas en Sales.
	 * 
	 * @param FilePath
	 * @param SheetName
	 * @param primerRegistro
	 * @param PrimeraColumna
	 * @param CantidadColumna
	 * @param Registro
	 * @return
	 * @throws Exception
	 */
	public static Object[][] getTableArray(String FilePath, String SheetName, String Registro, int primerRegistro, int PrimeraColumna, int CantidadColumna ) throws Exception {   

		   String[][] tabArrayFiltrada = null;

		   try {

			   FileInputStream ExcelFile = new FileInputStream(FilePath);
			   // Lectura de Excel
			   ExcelWBook = new XSSFWorkbook(ExcelFile);
			   ExcelWSheet = ExcelWBook.getSheet(SheetName);
			   
			   //Seleccion de Matriz a Devolver
			   int startRow = primerRegistro;
			   int startCol = PrimeraColumna;
			   int ci,cj;
			   int totalRows = ExcelWSheet.getLastRowNum();

			   // Cantidad de Columnas a partir de la primera que se van a retornar
			   int totalCols = CantidadColumna;
			   
			   //Recorro la lista de comparacion para determinar la dimension del arreglo en i
			   int ndeRegistros=0;
			   for (int i=startRow;i<=totalRows;i++) {           	   
				  if(getCellData(i,0).contains(Registro)) {
					  ndeRegistros++;
				  }
			   }
			   
			   //Defino el Arreglo a Retornar
			   tabArrayFiltrada=new String[ndeRegistros][totalCols];
			   
			   //Recorro todo el Excel
			   ci=0;
			   for (int i=startRow;i<=totalRows;i++) {           	   
				  cj=0;
				  
				  //Guardo en el Arreglo a Retornar los Valores que necesito
				  if(getCellData(i,0).contains(Registro)) {
				   for (int j=startCol;j<=totalCols;j++, cj++){
					   
						   tabArrayFiltrada[ci][cj]=getCellData(i,j);
						   System.out.print("["+tabArrayFiltrada[ci][cj]+"]");
						   }
				   		ci++;
				   		System.out.println("\n");
						}
					}   
			}
			catch (FileNotFoundException e){
				System.out.println("Could not read the Excel sheet");
				e.printStackTrace();
				}

			catch (IOException e){
				System.out.println("Could not read the Excel sheet");
				e.printStackTrace();
				}
			return(tabArrayFiltrada);
			}
}
