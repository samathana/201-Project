

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
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		PrintWriter pw=response.getWriter();
		System.out.println("in service");
		//username and password have to be the names of those element tags
		String user= request.getParameter("user");
		String pass=request.getParameter("pass");
		
		
		Connection conn = null;
		Statement st = null;
		
		ResultSet rs = null;
		int temp=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Photo%20Diary?user=root&password=root");
			
			
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * from users WHERE username='" + user +"' AND password='"
					+pass+"'");
			System.out.println("back id");
			if (rs.next()) {
				temp=rs.getInt(1);
				System.out.println("got id");
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException e) {
			
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
		
		if (temp==0) {
			//no user
			pw.println("Username or password is incorrect");
			
		} else {
			//print the user's id
			pw.println(temp);
		}
		pw.flush();
		pw.close();
		
	}   
    
}
