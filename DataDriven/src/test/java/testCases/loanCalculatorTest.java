package testCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.TestBase;
import listeners.TestListeners;
import utilities.ReadingExcel;

@Listeners(TestListeners.class)
public class loanCalculatorTest extends TestBase {

	@Test(dataProvider="loanCalcExcel")
	public void loanCalculatorTest(String LoanAmount ,String loanTermYear	,String loanTermMonth	,String InterestRate,	String compound,	String payBack, String expectedPaymentAmount) throws InterruptedException {
		//click on the loan calc link
		click("loancalc_XPATH");

		//validate the url
		if(driver.getTitle().equalsIgnoreCase("Loan Calculator")) {
			log.info(driver.getTitle()+" Page title is verified and Correct");
			}else {
				log.error(driver.getTitle()+" Page title is different from the expected");

			}
		/*
		//Enter
		enterText("loanAmount_XPATH","500000");
		//Enter
		enterText("loanTermYear_XPATH","30");
		//Enter
		enterText("loanTermMonth_XPATH","10");
		//Enter
		enterText("IntersetRate_XPATH","8");
		//select
		select("Compaunded_XPATH","text","Annually (APY)");

		//select
		select("payBack_XPATH","text","Every Day");
		 */

		//Enter
		enterText("loanAmount_XPATH",LoanAmount);
		//Enter
		enterText("loanTermYear_XPATH",loanTermYear);
		//Enter
		enterText("loanTermMonth_XPATH",loanTermMonth);
		//Enter
		enterText("IntersetRate_XPATH",InterestRate);
		//select
		select("Compaunded_XPATH","text",compound);

		//select
		select("payBack_XPATH","text",payBack);

		//select
		click("calculate_XPATH");
		//sleep
		sleep(2000);

		//capture
		String capturePaymentAmount=captureTextValue("Payment_XPATH");
		//validate the expected amount is the captured 
//		String expectedPaymentAmount="116.19";

		/*
		 * instead we can call the method in the base class
		 * 
		 */
		//		if(capturePaymentAmount.equals(expectedPaymentAmount)) {
		//			log.info("Captured Payment Amount DOESNOT MATCH with Expected Amount.");
		//
		//		}else {
		//			log.error("Captured Payment Amount DOESNOT MATCH with Expected Amount.");
		//		}

		formatStrings(capturePaymentAmount,expectedPaymentAmount);

	}
	@DataProvider
	public Object [][] loanCalcExcel() throws Exception{

		ReadingExcel excel=new ReadingExcel();

		return excel.readExcelData("C:\\Programming Related\\Eclipse\\eclipse-workspace\\DataDriven\\src\\test\\resources\\ExcelTestData\\CalculatorData.xlsx","LoanCalculatorTest");

	}




}