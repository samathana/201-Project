import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddFriendServelt")
public class AddFriendServelt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    String imageIDParam = request.getParameter("imageID");
    	    String username = request.getParameter("username");
    	    if (imageIDParam == null || imageIDParam.isEmpty() || username.isEmpty()) {
    	        response.setContentType("text/plain");
    	        response.getWriter().write("Image ID parameter is missing");
    	        return;
    	    }

    	    int myID = Integer.parseInt(imageIDParam);
    	    int friendID = 0;
    	    
    	    try {
    	        friendID = JDBCConnector.getUserID(username);
    	        System.out.println(friendID);
    	        
    	        Class.forName("com.mysql.cj.jdbc.Driver");
    	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Photo%20Diary?user=root&password=root");

    	        // Insert the friendship relationship into the Friends table
    	        String sql = "INSERT INTO Friends (userID, friendID) VALUES (?, ?)";
    	        PreparedStatement pstmt = conn.prepareStatement(sql);
    	        pstmt.setInt(1, myID);
    	        pstmt.setInt(2, friendID);
    	        
    	        int rowsAffected = pstmt.executeUpdate();
    	        
    	        if (rowsAffected > 0) {
    	            response.setContentType("text/plain");
    	            response.getWriter().write(String.valueOf(friendID));
    	        } else {
    	            response.setContentType("text/plain");
    	            response.getWriter().write("Failed to add friend");
    	        }
    	        
    	        pstmt.close();
    	        conn.close();
    	    } catch (ClassNotFoundException | SQLException e) {
    	        e.printStackTrace();
    	        response.setContentType("text/plain");
    	        response.getWriter().write("Error retrieving caption");
    	    }
    	}

}
