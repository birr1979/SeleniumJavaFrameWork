package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestBase {
	//Base is a Super class for all test cases files >>all will extend this class
	/*
	 * Initialize: 
	 * - Webdriver
	 * -property files
	 * Logs
	 * Excel reading 
	 * explicit wait
	 * implicit wait
	 * key common methods
	 * screenshoots
	 * highlither
	 * extent reports
	 * 
	 */

	public static Properties Config= new Properties();
	public static Properties ObjRepo= new Properties();
	public static FileInputStream fis;
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Logger log= LogManager.getLogger(TestBase.class.getName());
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest> testReport= new ThreadLocal<ExtentTest>();
	public static Date myDate;
	public static WebElement element;

	@BeforeSuite
	public void setUp() {
		log.info("Begin Execution testCase");
		/*
		 * setup config, objRepo, logging, extent reports
		 * invocke browser
		 * 
		 */
		try {
			String filePath="C:\\Programming Related\\Eclipse\\eclipse-workspace\\DataDriven\\src\\test\\resources\\Properties\\Config.properties";
			fis= new FileInputStream(filePath);
			log.info("Config file Found successfully ");
		} catch (FileNotFoundException e) {
			log.info(e.getMessage());
			log.info("Config file not Found");
		}

		//load the file
		try {
			Config.load(fis);
			log.info("File load  successfully");
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			log.info(" Error while loading config file");
		}

		//load Obj.repo
		try {
			fis= new FileInputStream("C:\\Programming Related\\Eclipse\\eclipse-workspace\\DataDriven\\src\\test\\resources\\Properties\\ObjRepo.properties");
			log.info("File found properties successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//Load Obj repor

		try {
			ObjRepo.load(fis);
			log.info("Obj Repo file loaded successfully");
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			log.info(" Error while loading ObjRepo file");
		}

		/*
		 * 
		 */

		//initialize the browser
		try {
			if(Config.getProperty("browser").equalsIgnoreCase("firefox")) {
				driver=new FirefoxDriver();
				log.info("Browser initialized: "+Config.getProperty("browser").toUpperCase());
			}
			else if (Config.getProperty("browser").equalsIgnoreCase("chrome")) {
				driver=new ChromeDriver();
				log.info("Browser initialized: "+Config.getProperty("browser").toUpperCase());
			}
			wait= new WebDriverWait(driver,Integer.valueOf(Config.getProperty(("explicit.wait"))));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.valueOf(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);

		} catch (RuntimeException e) {
			log.error("Error while initializing browser: " +Config.getProperty("browser").toUpperCase()+": "+(e.toString()) +"\n or Invalid browser name provided /no driver found.");
			System.exit(1);
		}
		//Creating Extent reporter classes

		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"//src//test//resources//ExtentReports//"+getCurrentTime()+"extentReporter.html");
		sparkReporter.config().setEncoding("utf-8");
		sparkReporter.config().setDocumentTitle("BirrProduction MMW's");
		sparkReporter.config().setReportName("BIRRPRODUCTION MMWS");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setTimeStampFormat("EEEE,MMMM dd,yyyy,hh:mm a '('zzz')'");

		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Automation Engineer", "Birhanu Sendek");
		extent.setSystemInfo("QA Automation", "Selenium TestBase");
		extent.setSystemInfo("Company", "BIRR PRODUCTION MMW's");

	}

	/*
	 * 
	 */

	@AfterSuite(enabled=true)
	public void tearDwon() throws IOException {
		log.info("$$$$$$$$$$$$$$$$$$$$$$$$$ Ending Execution testCase $$$$$$$$$$$$$$$$$$$$$$$$$$");
		driver.quit();
		fis.close();
		extent.flush();
	}

	/*
	 * 
	 */
	@BeforeMethod
	public void browserNavigation() {
		try {
			driver.navigate().to(Config.getProperty("URL"));
			log.info("Navigated to :"+ Config.getProperty("URL").toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error Navigating to :"+ Config.getProperty("URL").toUpperCase());
		}
	}


	/*
	 * 
	 */

	// Method to select from a drop down element
	public static void select(String key, String optionType, String option) {
		try {
			if (key.endsWith("_XPATH")) {
				WebElement dropDownElement = driver.findElement(By.xpath(ObjRepo.getProperty(key)));
				Select options = new Select(dropDownElement);
				if (optionType.equals("value")) {
					options.selectByValue(option);
				} else if (optionType.equals("text")) {
					options.selectByVisibleText(option);
				} else if (optionType.equals("index")) {
					options.selectByIndex(Integer.valueOf(option));
				}
			} else if (key.endsWith("_CSS")) {
				WebElement dropDownElement = driver.findElement(By.cssSelector(ObjRepo.getProperty(key)));
				Select options = new Select(dropDownElement);

				if (optionType.equals("value")) {
					options.selectByValue(option);
				} else if (optionType.equals("text")) {
					options.selectByVisibleText(option);
				} else if (optionType.equals("index")) {
					options.selectByIndex(Integer.valueOf(option));
				}
			}
			log.info(option+ " was Slecected from "+ key+ " Drop Down");
			testReport.get().log(Status.INFO,  option+ " was Slecected from "+ key+ " Drop Down");
		}catch(Throwable t) {
			highlightElement(key);

			t.getMessage();
			log.error(option+ " error while selecting from "+ key+ " Drop Down");
			Assert.fail(option+ " error while selecting from "+ key+ " Drop Down");

		}
	}

	/*
	 * 
	 */
	//Method to click

	public static void click(String key) {
		try {
			if (key.endsWith("_XPATH")) {
				driver.findElement(By.xpath(ObjRepo.getProperty(key))).click();
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(ObjRepo.getProperty(key))).click();
			}
			log.info("clicking "+ key+ " Element");
			testReport.get().log(Status.INFO,  "clicking "+ key+ " Element");
		}catch(Throwable t) {
			log.error("Error while clicking "+ key+ " Element");
		}
	}
	/*
	 * 
	 */
	//Method to enter a text
	public static void enterText(String key, String value) {

		try {
			if (key.endsWith("_XPATH")) {
				driver.findElement(By.xpath(ObjRepo.getProperty(key))).clear();
				driver.findElement(By.xpath(ObjRepo.getProperty(key))).sendKeys(value);
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(ObjRepo.getProperty(key))).clear();
				driver.findElement(By.cssSelector(ObjRepo.getProperty(key))).sendKeys(value);
			}
			log.info("entering a text"+ key+ " Element "+value+" value");
			testReport.get().log(Status.INFO,  "entering a text "+ key+ " Element "+value+" value");

		} catch (Exception e) {
			highlightElement(key);
			e.printStackTrace();
			log.error("Error while clicking "+ key+ " Element");
			Assert.fail("Error while clicking "+ key+ " Element");
		}
	}
	/*
	 * 
	 */

	//method to capture a text
	public static String captureTextValue(String key) {
		try {
			highlightElement(key);

			String textValue="";
			if(key.endsWith("_XPATH")) {
				textValue=driver.findElement(By.xpath(ObjRepo.getProperty(key))).getText();
			} else if(key.endsWith("_CSS")) {
				textValue=driver.findElement(By.cssSelector(ObjRepo.getProperty(key))).getText();
			}
			log.info("Text Captured from "+ key+ " Element");
			testReport.get().log(Status.INFO,  "Text Captured from "+ key+ " Element");
			return textValue;
		} catch (Exception e) {
			highlightElement(key);
			e.printStackTrace();
			log.error("Error while capturing "+ key+ " Element");
			log.info(e.getMessage());
		}
		return null;
	} 
	/*
	 * 
	 */

	//capture page title
	public static void pageTitle(String expectedPageTitle) {
		try {

			String actualPageTitle=driver.getTitle();
			if(actualPageTitle.equalsIgnoreCase(expectedPageTitle)) {
				log.info(" Title retrived."+"Actual page title: <b>"+actualPageTitle+"</b> is equal to Expected page title.");
				testReport.get().info(" Title retrived."+"Actual page title: <b>"+actualPageTitle+"</b> is equal to Expected page title.");
			}else {
				testReport.get().info(" Title retrived."+"Actual page title: <b>"+actualPageTitle+"</b> is not equal to Expected page title: <b>"+expectedPageTitle+"</b>");
				log.error(" Title retrived."+"Actual page title: <b>"+actualPageTitle+"</b> is not equal to Expected page title: <b>"+expectedPageTitle+"</b>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			log.error("Error while getting page title");
			Assert.fail();
		}
	}

	/*
	 * 
	 */
	//Pause the test Sleep method
	public static void sleep(int sleepTime) throws InterruptedException {
		try {
			Thread.sleep(sleepTime);
			log.info("Sleep method applied pausing the execution......");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 */

	//Method for formating string to double for comparison
	public static boolean formatStrings(String raw_CapturedValue, String raw_ExpectedValue) {
		boolean valuesMatch=false;
		try {
			if(raw_CapturedValue.contains("$")) {
				raw_CapturedValue=raw_CapturedValue.replace("$","");
				log.info("Removing $....................");

				if(raw_CapturedValue.contains(",")) 
					raw_CapturedValue=raw_CapturedValue.replace(",","");
				log.info("Removing ,....................");
			}
			log.info("Converting done..............");

		}
		catch (Exception e){
			log.error("Error While removing the $ and ,  sign");
		}


		//convert the captured value to double
		double formated_captured=0;
		double formated_expected=0;
		try {
			formated_captured =Double.valueOf(raw_CapturedValue);
			log.info("captured is Converted to double format = "+formated_captured);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error while converting to double");
		}

		//And the expected to double
		try {
			formated_expected =Double.valueOf(raw_ExpectedValue);
			log.info("Expected is Converted to double format = "+ formated_expected);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error while converting to double");
		}
		//compair the 2 values
		if(formated_expected==(formated_captured)) {
			log.info("Captured Amount MATCH's with Expected Amount.");
			testReport.get().log(Status.INFO,  "Successfully compaired the Expected String with Captured String");


		}else {
			log.error("Captured Amount DOESNOT MATCH with Expected Amount.");
			Assert.assertSame(formated_expected,formated_captured, "Captured Amount DOESNOT MATCH with Expected Amount.");
		}
		return 		valuesMatch;
	}


	/*
	 * 
	 */


	//create a method to return file name as date and time
	public static String getCurrentTime() {
		myDate= new Date();
		String AMPM;

		String fileName=myDate.toString().replace(":", "_").replace(" ", "_").substring(11,19);
		String []x=fileName.split("_");

		int hour=Integer.valueOf(x[0]);
		if (hour!=12) {
			AMPM="PM";
			hour=hour-12;
		}else {
			AMPM="AM";
		}
		if(hour==0) {
			hour=12;
		}
		String finalDateTime=fileName+"@"+hour+"_"+x[1]+" "+AMPM;
		return finalDateTime;
	}



	//Create a method to highlight an element
	public static void highlightElement(String key) {

		JavascriptExecutor jsExecutor=(JavascriptExecutor)driver;
		if (key.endsWith("_XPATH")) {
			element=driver.findElement(By.xpath(ObjRepo.getProperty(key)));
		}else if (key.endsWith("_CSS")){
			element=driver.findElement(By.cssSelector(ObjRepo.getProperty(key)));

		}
		jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
	}



	public static void unHighlightElement(String key) {

		JavascriptExecutor jsExecutor=(JavascriptExecutor)driver;
		if (key.endsWith("_XPATH")) {
			element=driver.findElement(By.xpath(ObjRepo.getProperty(key)));
		}else if (key.endsWith("_CSS")){
			element=driver.findElement(By.cssSelector(ObjRepo.getProperty(key)));

		}
		jsExecutor.executeScript("arguments[0].style.border='0px solid blue'", element);
	}







}
