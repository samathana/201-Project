import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

@WebServlet("/LikeServlet")
public class LikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageIDParam = request.getParameter("imageID");
        if (imageIDParam == null || imageIDParam.isEmpty()) {
            response.setContentType("text/plain");
            response.getWriter().write("Image ID or new caption parameter is missing");
            return;
        }

        int imageID = Integer.parseInt(imageIDParam);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/Photo%20Diary?user=root&password=root");
            // Retrieve current likeCount value
            String selectSql = "SELECT likeCount FROM entries WHERE id = ?";
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, imageID);
            rs = pstmt.executeQuery();
            
            int currentLikeCount = 0;
            if (rs.next()) {
                currentLikeCount = rs.getInt("likeCount");
            }

            // Increment the current likeCount value by one
            int newLikeCount = currentLikeCount + 1;

            // Update the database with the new likeCount value
            String updateSql = "UPDATE entries SET likeCount = ? WHERE id = ?";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newLikeCount);
            pstmt.setInt(2, imageID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setContentType("text/plain");
                response.getWriter().write("Like count updated successfully");
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("No image found with the specified ID");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Error updating like count");
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