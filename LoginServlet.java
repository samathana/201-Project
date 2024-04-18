

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * action of login button
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public int loginUser(String username, String password) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int userID=-1;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sch?user=root&password=root");
			st = conn.createStatement();
			//String name = "Jack";
			rs = st.executeQuery("SELECT * from Users WHERE username='" + username +"' AND password='"
					+password+"'");
			//ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			//ps.setString(1, name); // set first variable in prepared statement
			//rs = ps.executeQuery();
			if (rs.next()) {
				userID=rs.getInt(1);
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return userID;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		PrintWriter pw=response.getWriter();
		
		//username and password have to be the names of those element tags
		String user= request.getParameter("user");
		String pass=request.getParameter("pass");
		int userID=loginUser(user,pass);
		Gson gson= new Gson();
		
		if (userID==-1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error="Username or password is incorrect";
			pw.write(gson.toJson(error));
			pw.flush();
			
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			pw.write(gson.toJson(userID));
			pw.flush();
		}
		
		
		pw.close();
		
	}   
    
}
