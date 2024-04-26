import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Map")
public class Map extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Photo%20Diary?user=root&password=root");
            st = conn.createStatement();
            rs = st.executeQuery("SELECT id, image_data, longitude, latitude FROM entries");
            
            StringBuilder json = new StringBuilder("[");
            while (rs.next()) {
            	int id = rs.getInt("id");
            	String image = rs.getString("image_data");
            	double longitude = rs.getDouble("longitude");
            	double latitude = rs.getDouble("latitude");
            	json.append("{\"longitude\": ").append(longitude)
            	    .append(", \"latitude\": ").append(latitude)
            	    .append(", \"id\": ").append(id)
            	    .append(", \"image\": \"").append(image).append("\"},");
            }
            json.setCharAt(json.length() - 1, ']'); // replace last comma with closing bracket
            out.println(json);
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}