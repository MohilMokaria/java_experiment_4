
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myClass.Post;

@WebServlet("/SocialServlet")
public class SocialServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve database connection parameters from servlet context
        final String jdbcUri = getServletContext().getInitParameter("jdbcUri");
        final String dbUri = getServletContext().getInitParameter("dbUri");
        final String dbId = getServletContext().getInitParameter("dbId");
        final String dbPass = getServletContext().getInitParameter("dbPass");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        List<Post> postList = new ArrayList<>();

        try {
            // Load the JDBC driver class
            Class.forName(jdbcUri);

            try (Connection connection = DriverManager.getConnection(dbUri, dbId, dbPass)) {
                // SQL query to retrieve posts from the database
                String sql = "SELECT title, body, post_time FROM posttbl ORDER BY post_time DESC";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Iterate through the result set and create Post objects
                        while (resultSet.next()) {
                            Post p = new Post();
                            p.setTitle(resultSet.getString("title"));
                            p.setBody(resultSet.getString("body"));
                            p.setTime(resultSet.getTimestamp("post_time"));
                            postList.add(p);
                        }
                    }
                }

                // Set the postList as an attribute to be used in the JSP
                request.setAttribute("postList", postList);

                // Forward the request to the Social.jsp page
                RequestDispatcher rd = request.getRequestDispatcher("Social.jsp");
                rd.include(request, response);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            // Log any exceptions
            Logger.getLogger(ChatServlet.class.getName()).log(Level.SEVERE, null, ex);
            pw.println(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve database connection parameters from servlet context
        final String jdbcUri = getServletContext().getInitParameter("jdbcUri");
        final String dbUri = getServletContext().getInitParameter("dbUri");
        final String dbId = getServletContext().getInitParameter("dbId");
        final String dbPass = getServletContext().getInitParameter("dbPass");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        // Retrieve parameters from the request
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        Timestamp postedAt = new Timestamp(System.currentTimeMillis());

        try {
            // Load the JDBC driver class
            Class.forName(jdbcUri);

            try (Connection con = DriverManager.getConnection(dbUri, dbId, dbPass)) {
                // SQL query to insert a new post into the database
                PreparedStatement ps = con.prepareStatement("INSERT INTO posttbl(TITLE, BODY, POST_TIME) values(?,?,?)");

                ps.setString(1, title);
                ps.setString(2, body);
                ps.setTimestamp(3, postedAt);

                // Execute the query and redirect to the SocialServlet to refresh the posts
                int i = ps.executeUpdate();
                response.sendRedirect("SocialServlet");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Log any exceptions
            Logger.getLogger(ChatServlet.class.getName()).log(Level.SEVERE, null, ex);
            pw.println(ex.getMessage());
        }
    }
}
