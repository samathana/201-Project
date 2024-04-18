import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnector {
	static public Integer registerUser(String user, String pass) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs= null;
		
		Integer userID = -1;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/UserInfo?user=root&password=root"); // Change this line if needed
			
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Users WHERE username='" + user + "'");
			if (!rs.next()) { // There's no user w/ that username
				// No user w/ that email either
				rs.close();
				st.execute("INSERT INTO Users (username, password) VALUES ('" + user + "','" + pass + "')");
				rs = st.executeQuery("SELECT LAST_INSERT_ID()");
				rs.next();
				userID = rs.getInt(1);
			}
			else {
				userID = -2;
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException in registerUser().");
			sqle.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
			catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return userID;
	}
}
