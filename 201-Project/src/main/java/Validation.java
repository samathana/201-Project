import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Validation")
public class Validation extends HttpServlet {
    private static final long serialVersionUID = 8778174121968337225L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //form parameters
        String color = request.getParameter("color");
        String why = request.getParameter("why");
        String greeting = request.getParameter("greeting");
        String[] courses = request.getParameterValues("classes");
        String month = request.getParameter("month");
        String email = request.getParameter("email");
        String rating = request.getParameter("range");

        // make JSON
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"color\": \"" + color + "\", ");
        jsonBuilder.append("\"why\": \"" + why + "\", ");
        jsonBuilder.append("\"greeting\": \"" + greeting + "\", ");
        jsonBuilder.append("\"courses\": [");
        if (courses != null && courses.length > 0) {
            for (int i = 0; i < courses.length; i++) {
                jsonBuilder.append("\"" + courses[i] + "\"");
                if (i < courses.length - 1) {
                    jsonBuilder.append(", ");
                }
            }
        }
        jsonBuilder.append("], ");
        jsonBuilder.append("\"month\": \"" + month + "\", ");
        jsonBuilder.append("\"email\": \"" + email + "\", ");
        jsonBuilder.append("\"rating\": \"" + rating + "\"");
        jsonBuilder.append("}");

        // Sending JSON response
        PrintWriter out = response.getWriter();
        out.print(jsonBuilder.toString());
        out.flush();
    }
}
