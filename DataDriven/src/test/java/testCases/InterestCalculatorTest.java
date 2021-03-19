package testCases;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.TestBase;
import listeners.TestListeners;
import utilities.ReadingExcel;
//@Listeners(TestListeners.class)
public class InterestCalculatorTest extends TestBase {

	//openBrowser is initialized by the beforeSuit method in the Extends Class "TestBase"
	@Test (dataProvider="interestCalculatorTestData")
	public void InterestCalculatorTest(String startingPrincipal, String annualContribution, String monthlyContribution, String contributeAtThe, String interestRate,
			String compunded, String afterYear, String taxRate, String infilationRate, String ExpectedEndingBalance) throws InterruptedException {
//		driver.findElement(By.xpath("/html/body/div[4]/div/table/tbody/tr/td[1]/ul/li[4]/a")).click();
				click("interestCalculator_XPATH");

		//validate the url
		pageTitle("Interest Calculator");
		//Entering data from excel	
		enterText("startingPrincipal_XPATH", startingPrincipal);
		enterText("annualContribution_XPATH",annualContribution);
		enterText("monthlyContribution_XPATH",monthlyContribution );

		//clicking Contribute at the Radio button is with conditional
		if (contributeAtThe.equalsIgnoreCase("beginning")) {
			click("contributeAtTheBeginning_XPATH");
			testReport.get().info("Confirmed compounding period is: "+ contributeAtThe);
		}else if(contributeAtThe.equalsIgnoreCase("end")){
			click("contributeAtTheEnd_XPATH");
			testReport.get().info("Confirmed compounding period is: "+ contributeAtThe);
		}else {
			testReport.get().info("Confirmed compounding period is: "+ contributeAtThe+" is uknown.");
			log.error("Confirmed compounding period is: "+ contributeAtThe+" is uknown.");
		}

		enterText("interestRate_XPATH", interestRate);
		select("compunded_XPATH","text",compunded);
		enterText("afterYear_XPATH",afterYear);
		enterText("taxRate_XPATH", taxRate);
		enterText("infilationRate_XPATH",infilationRate);
		click("calculateButton_XPATH");
		sleep(2000);
		String capturedEndBalance=captureTextValue("EndBalance_XPATH");
		formatStrings(capturedEndBalance,ExpectedEndingBalance );

	}

	//dataProvider
	@DataProvider
	public Object[][]interestCalculatorTestData() throws Exception{
		ReadingExcel excelFile=new ReadingExcel();
		return excelFile.readExcelData("C:\\Programming Related\\Eclipse\\eclipse-workspace\\DataDriven\\src\\test\\resources\\ExcelTestData\\CalculatorData.xlsx","InterestCalculatorTest");
	}













}
