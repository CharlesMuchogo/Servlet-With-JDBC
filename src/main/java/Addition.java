import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/add")
public class Addition extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        PrintWriter out = res.getWriter();
        String email = req.getParameter("emailAddress");
        String phoneNumber = req.getParameter("mobileNumber");
        String password = req.getParameter("password");
        RequestDispatcher dispatcher = null;
        String status = null;

        final String databaseUrl = "jdbc:mysql://localhost:3306/users";
        final String dbusername = "root";
        final  String dbpassword = "";
        Connection conn = null;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
          conn = DriverManager.getConnection(databaseUrl, dbusername, dbpassword);
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserDetails(email, phone_number, password) values(?,?,?) ");
            stmt.setString(1,email);
            stmt.setString(2,phoneNumber);
            stmt.setString(3,password);

            int rs = stmt.executeUpdate();

            if(rs > 0){
              // req.setAttribute("status", "success");
                status = "Registered successfully";

            }else {
              //  req.setAttribute("status", "failed");
                status = "Failed to register user";

            }

        } catch (ClassNotFoundException | SQLException e) {
            if(e.toString().contains("Duplicate entry")){
                status = "User Exists !!!";
            }else{
                throw new RuntimeException(e);
            }

        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        out.println( status);


    }
}
