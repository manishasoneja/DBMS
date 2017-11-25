import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

public class Suggest extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String dbName = "medical";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "manisha";
		String password = "srkmanisha";
		out.println("<style>div {height: 300px;overflow-y: scroll;}table {font-family: arial,sans-serif;border-collapse: collapse;width: 100%;font-size: 75%}th{border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;background-color:#aab7b8;}td {border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;}tr:hover {background-color:  #dde;}");
		out.println("body {");
		out.println("background-image:url('Medical-Shop-Software.jpg');");
		out.println(" background-repeat:no-repeat;");
		out.println("background-size:cover;");
		out.println("height: 100%;background-position: center;");
		out.println("}");
		out.println("</style>");

		CallableStatement st;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");
			String loc = request.getParameter("loc");
			//String domain = request.getParameter("domain");
			String product = request.getParameter("product");
			//out.println("product"+product);
                st = conn.prepareCall("{CALL SUGGESTION(?,?)}");
                st.setString(1, loc);
                st.setString(2, product);
				ResultSet rs = st.executeQuery();
				out.println("<div><table ><tr><th>Product name</th><th>Manufacturer</th><th>Pharmacy Name</th><th>cost</th><th>Quantity</th></tr></div>");
				while(rs.next()){
					
					out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getInt(4)+"</td></td><td>"+rs.getString(5)+"</td></tr>");
				}
				out.println("</table>");
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
