import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("userID");
    	Part imagePart = request.getPart("image");
        InputStream imageStream = imagePart.getInputStream();
        String caption = request.getParameter("caption");
        String longString = request.getParameter("longitude");
        String latString = request.getParameter("latitude");
        String privacy = request.getParameter("privacy");

        int userID = Integer.parseInt(id.trim());
        
        
        double longitude;
        longitude = Double.parseDouble(longString);
        double latitude;
        latitude = Double.parseDouble(latString);
        
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
       
        System.out.println(caption + " " + longitude + " " + privacy);       
        try {
        	String username = JDBCConnector.getName(userID);
        	System.out.println(username);
            int imageID = JDBCConnector.storeImage(username, imageStream, caption, formattedTime, longitude,latitude, privacy);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            if (imageID != -1) {
                response.getWriter().write("Post created successfully with image ID: " + imageID);
            } else {
                response.getWriter().write("Error creating post");
            }
        } finally {
            if (imageStream != null) {
                imageStream.close();
            }
        }
    }
}





