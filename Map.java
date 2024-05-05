import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
	    rs = st.executeQuery("SELECT e.id, e.user, e.image_data, e.longitude, e.latitude, e.privacy, u.UserID " +
                    "FROM entries e " +
                    "JOIN Users u ON e.user = u.UserName");  

            List<String> jsonList = new ArrayList<>();

            while (rs.next()) {
                String jsonObject = constructJsonObject(rs, conn);
                jsonList.add(jsonObject);
            }

            String jsonArray = "[" + String.join(",", jsonList) + "]";

            out.println(jsonArray);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String constructJsonObject(ResultSet rs, Connection conn) throws SQLException, ClassNotFoundException, IOException {
        int id = rs.getInt("id");
    	byte[] image = getImageData(id);
        String user = rs.getString("user");
        int userID = rs.getInt("u.UserID");
        double longitude = rs.getDouble("longitude");
        double latitude = rs.getDouble("latitude");
        String privacy = rs.getString("privacy");

        List<Integer> friends = getFriendsForEntry(userID, conn);

	//ChatGPT-generated code below to generate JSON string
        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append("[");
        for (int i = 0; i < friends.size(); i++) {
            jsonArray.append(friends.get(i));
            if (i < friends.size() - 1) {
                jsonArray.append(",");
            }
        }
        jsonArray.append("]");
        String friendsJson = jsonArray.toString();
	//end generated code
	    
        StringBuilder json = new StringBuilder();
        json.append("{\"longitude\": ").append(longitude)
            .append(", \"latitude\": ").append(latitude)
            .append(", \"id\": ").append(id)
            .append(", \"userID\": ").append(userID)
            .append(", \"image\": \"").append(image)
            .append("\", \"user\": \"").append(user)
            .append("\", \"privacy\": \"").append(privacy)
            .append("\", \"friends\": ").append(friendsJson)
            .append("}");

        return json.toString();
    }

    private static List<Integer> getFriendsForEntry(int userID, Connection conn) throws SQLException {
        List<Integer> friends = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT friendID FROM Friends WHERE userID = " + userID);
            while (rs.next()) {
                friends.add(rs.getInt("friendID"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }
        return friends;
    }
    
    public static byte[] getImageData(int imageID) throws SQLException, ClassNotFoundException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        byte[] imageDataBytes = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Photo%20Diary?user=root&password=root");
            
            String sql = "SELECT image_data FROM entries WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, imageID);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                InputStream imageData = rs.getBinaryStream("image_data");
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                
                byte[] data = new byte[4096];
                int bytesRead;
                while ((bytesRead = imageData.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, bytesRead);
                }
                
                imageDataBytes = buffer.toByteArray();
            }
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in finally block
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        
        return imageDataBytes;
    }
    
}
