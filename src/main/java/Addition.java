import com.charlesmuchogo.java_servlet.email;
import com.zaxxer.hikari.HikariDataSource;
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
        String username = req.getParameter("username");

        String status = null;






        Connection conn = null;
        HikariDataSource dataSource;


        try {
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/users");
            dataSource.setUsername("root");
            dataSource.setPassword("");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");

             conn = dataSource.getConnection();

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserDetails(email, phone_number, password) values(?,?,?) ");
            stmt.setString(1,email);
            stmt.setString(2,phoneNumber);
            stmt.setString(3,password);


            int rs = stmt.executeUpdate();

            if(rs > 0){
              // req.setAttribute("status", "success");
                status = "Registered successfully";
                email obj = new email();
                obj.sendEmail(email);

            }else {
              //  req.setAttribute("status", "failed");
                status = "Failed to register user";

            }

        } catch ( SQLException e) {
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
