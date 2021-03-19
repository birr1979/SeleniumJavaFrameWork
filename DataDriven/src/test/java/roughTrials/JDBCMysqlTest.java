package roughTrials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
//import listeners.TestNG_listeners;

public class JDBCMysqlTest {
	
	@Test (enabled=true)
	public static void mysqlDML() throws SQLException {
		//for insert, delete, update
		//creat a connection to the database
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/traffic_violations_db","root","1979");
		//query
		Statement statement =con.createStatement();
		//execut the query
		String s="Insert into Officers values(1234,'birahnu sendek',455,'manager',9)";
		//store the result in set if its select
		statement.execute(s);
		//close the connection
		System.out.println("MYSQL Query Excuted.........");
		con.close();
	}
	
	@Test
	public static void mysqlSelect() throws SQLException {
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
	
	

}
