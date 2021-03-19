package testCases;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utilities.ReadingExcel;

public class samplePat extends ReadingExcel{

//	public static Properties globalvariables = new Properties();
//	public static FileInputStream fis;
//	public static void loadGlobalVars() throws IOException {
//		fis = new FileInputStream(System.getProperty("C:\\Programming Related\\Eclipse\\eclipse-workspace\\DataDriven\\src\\test\\resources\\Properties\\GlobalVariable.properties"));
//
//		globalvariables.load(fis);
//	}


@Test
	public static Object[][] getExcelData_Calc() throws Exception{

		//		loadGlobalVars();
		//		String FilePath = globalvariables.getProperty("PatientPortalDataPath");
		//		String TestDataSheet = globalvariables.getProperty("DoctestS2");
		ReadingExcel myData = new ReadingExcel();
	return	myData.readExcelData("C:\\Users\\birr_\\Downloads\\doctorPortal.xlsx","TestData");
		 }


	@Test (dataProvider= "getExcelData_Calc", enabled=true)
	public void CalcTest(String TCID, String AutoPrice, String LoanTerm, String Rate, String DawnPmt, String Trade_In, String State, String SalesTax, String TRO_Fees, String Include_Fees) {

		System.out.println("TcId" + TCID);
		System.out.println("Auto price" + AutoPrice);
		System.out.println("Loan term" + LoanTerm);
		System.out.println("interest Rate" + Rate);
		System.out.println("Down Payment" + DawnPmt);
		System.out.println("TRADE In" + Trade_In);
		System.out.println("State" + State);
		System.out.println("Sales Tax" + SalesTax);
		System.out.println("TR&O" + TRO_Fees);
		System.out.println("Include_Fees" + Include_Fees);
	}

}