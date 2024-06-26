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

@WebServlet("/DisplayCaptionServlet")
public class DisplayCaptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageIDParam = request.getParameter("imageID");
        if (imageIDParam == null || imageIDParam.isEmpty()) {
            response.setContentType("text/plain");
            response.getWriter().write("Image ID parameter is missing");
            return;
        }

        int imageID = Integer.parseInt(imageIDParam);
        String caption = null;
        String username = null;
        String likeCount = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Photo%20Diary?user=root&password=root");

            // Select caption and user from entries table
            String sql = "SELECT caption, user, likeCount FROM entries WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, imageID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                caption = rs.getString("caption");
                username = rs.getString("user");
                likeCount = rs.getString("likeCount");
                response.setContentType("text/plain");

    	        int friendID = JDBCConnector.getUserID(username);
                response.getWriter().write(caption + "\n" + username + "\n" + likeCount + "\n" + friendID);
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("No image found with the specified ID");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Error retrieving caption");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
