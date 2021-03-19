package listeners;

import java.io.IOException;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.TestBase;
import utilities.TakeScreenShot;


public class TestListeners extends TestBase implements ITestListener {
	
	/*
	 * Basic Html sysntax
	 * <b>bold </b>
	 */
	public static String screenshotPath;
	public void onTestStart(ITestResult result) {
		System.out.println("******************Test Started: "+result.getMethod().getMethodName()+"***************************");
		
	}

	public void onTestSuccess(ITestResult result) {
		String logText="<b>"+"TestCase: "+result.getMethod().getMethodName().toUpperCase()+" PASSED " +"<b>";
		Markup m= MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.pass(m);
		testReport.get().log(Status.INFO, "*** TEst PASSED ***");
		testReport.get().log(Status.INFO, result.getMethod().getMethodName());
			
		System.out.println("*********************Test Sucess: "+result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		/*
		 * 1. include stackTrance
		 * e. screenshot
		 * 
		 * 
		 * */
		String testName=result.getMethod().getMethodName();
		//convert the exception to string and store it
		String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
	
				
		testReport.get().info("<details open> <summary><strong> Exception Occurred: Click to view Details: </strong></summary>"+exceptionMessage+"</details>");
		
		
		
//		testReport.get().info("<details>" + "<summary>"+"Exception Occurred: Click to view Details. "+"</summary>"+exceptionMessage.replaceAll(",", "<br>")+"</details>" + "\n");
		//failure message
		String FailureMessage="<b>"+"Test Case: "+testName.toUpperCase()+" FAILED " +"</b>";
		Markup m= MarkupHelper.createLabel(FailureMessage, ExtentColor.RED);
		test.fail(m);
		testReport.get().log(Status.INFO, "TEst FAILED $$$$$$$$$$$$$$$$$");

		System.out.println("*****************Test Failed: "+result.getMethod().getMethodName());
		//take the screenshot
		
		try {
			screenshotPath = TakeScreenShot.takeScreenShot(testName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//adding the screenshot
		try {
			testReport.get().addScreenCaptureFromPath(screenshotPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		testReport.get().info("<b>"+"font color= red"+ "Screenshoot of failure"+"</font>"+"</b>", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("_********************Test Skipped: "+result.getMethod().getMethodName());
		
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	System.out.println("******************8skipped with success percentage: "+result.SUCCESS_PERCENTAGE_FAILURE);
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		System.out.println("***********Test Timeout (failed): "+result.getMethod().getMethodName());
		
	}

	@Override
	public void onStart(ITestContext context) {
		//create the extent reports
		test= extent.createTest(context.getName());
		testReport.set(test);
		
		System.out.println("**************Test Suit Started: "+context.getSkippedTests());
		
	}

	@Override
	
	public void onFinish(ITestContext context) {
		System.out.println("********************Test Suit Finished: "+context.getSkippedTests());
		
	}

	
	

	

}
