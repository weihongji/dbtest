import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection getConnection() {
		Connection conn = null;

		try {
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Test";
			conn = DriverManager.getConnection(url, "recware", "safari");
		}
		catch (Exception e) {
			System.out.println("Failed. " + e.toString());
		}

		return conn;
	}
}
