package roughTrials;

import java.util.Date;

public class dateTimeFileNameFormater {
	public static String getCurrentTime() {
		Date myDate= new Date();
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
	
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(getCurrentTime());
	}

}
