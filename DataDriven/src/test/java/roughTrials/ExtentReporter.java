package roughTrials;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

	public ExtentSparkReporter sr;
	public ExtentReports e;
	public ExtentTest t;


	@BeforeTest
	public void configRepo() throws UnsupportedEncodingException {
		sr= new ExtentSparkReporter(System.getProperty("user.dir")+"//src//test//resources//ExtentReports//extent.html");
		sr.config().setEncoding("utf-8");
		sr.config().setDocumentTitle("BirrProduction MMW's");
		sr.config().setReportName("BIRRPRODUCTION MMWS");
		sr.config().setTheme(Theme.DARK);

		e= new ExtentReports();
		e.attachReporter(sr);
		e.setSystemInfo("Automation Engineer", "Birhanu Sendek");
		//		e.setGherkinDialect("Language:Amharic");
		e.setReportUsesManualConfiguration(true);
		e.setSystemInfo("Company", "user, company name will appear here.");

	}

	//

	@Test(enabled=false)
	public void Testcase1() {
		t=e.createTest("TestCase1");

		System.out.println("Excuting Test Case 1");

	}
	@Test(enabled= true)
	public void Testcase3() {
		t=e.createTest("TestCase3");
	throw new SkipException("Skipping the test case 3");

	}

	//
	@Test (enabled=false)
	public  void mysqlSelect() throws SQLException {
		t=e.createTest("Batabase Testing on Select statement JDBC");

		Logger log=LogManager.getLogger(JDBCMysqlTest.class.getName());
		//for insert, delete, update
		//creat a connection to the database
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/traffic_violations_db","root","1979");
		//query
		Statement statement =con.createStatement();
		//execut the query
		String s="SELECT * FROM officers";
		//store the result in set if its select
		statement.execute(s);

		//Store the data in the result set
		ResultSet rs=statement.executeQuery(s);
		while(rs.next()) {
			int officerID=rs.getInt("OfficerID");
			String Name=rs.getString("name");
			System.out.println(officerID+" "+ Name);
		}

		//close the connection
		System.out.println("MYSQL Query executed.........");
		con.close();
		log.info("Database connected and data retrived, Excution success.");
	}

	//

	@Test (enabled=true)
	public void Testcase2() {
		t=e.createTest("TestCase2");

		System.out.println("Excuting Test Case 2");
		Assert.assertEquals("name", "aname");
	}

	//this is where we implement the extent report 


	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus()==ITestResult.FAILURE){
			String logText="<b>"+"TestCase: "+result.getMethod().getMethodName().toUpperCase()+" FAILED" +"<b>";
			Markup m= MarkupHelper.createLabel(logText, ExtentColor.RED);
			t.fail(m);

		}else if (result.getStatus()==ITestResult.SUCCESS) {
			String logText="<b>"+"TestCase: "+result.getMethod().getMethodName().toUpperCase()+" PASSED " +"<b>";
			Markup m= MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			t.pass(m);

		}else if (result.getStatus()==ITestResult.SKIP) {
			String logText="<b>"+"TestCase: "+result.getMethod().getMethodName().toUpperCase()+" SKIPPED" +"<b>";
			Markup m= MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
			t.skip(m);

		}
	}


	@AfterTest
	public void endReporter() {
		e.flush();
	}





	static int x;


	@Test
	public void case1() {
				x++;
		System.out.println("I am the test ");
	}

	@Test
	public void cas2() {
				x++;
		System.out.println("I am the test ");
		throw new SkipException("its skipped by me");
	}


	@Test
	public void case3() {

		System.out.println("I am the test ");
				Assert.assertEquals("name", "myname");
	}






















}
