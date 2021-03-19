package utilities;

import java.io.*;
import java.sql.Time;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import base.TestBase;
public class TakeScreenShot extends TestBase{
	//create a method to take screen shoot

		public static String takeScreenShot(String testName) throws IOException {
			
//The screenshot file should be stored in the root folder
			//and  the file name should like: current time+test name
			//example: 15_35_13_loancalctest.jpeg
			Date date= new Date();
			String file=date.toString().replace(":", "_").replace(" ", "_");
			
			String fileName= file.substring(0,19);
			
			
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath=System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots\\"+fileName+"_"+testName+"_Screenshoot.png";
			
			FileUtils.copyFile(screenshot, new File(screenshotPath));
			return screenshotPath;
		}
		
		
}
