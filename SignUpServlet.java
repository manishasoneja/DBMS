import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class SignUpServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html>");
			//out.println("<body bgcolor='cyan'>");
			//out.println("<h1>My First Servlet Page</h1>");
			ServletConfig config = getServletConfig();
			//String driverName = config.getInitParameter("driverName");
			//String dataSource = config.getInitParameter("dataSource");
			//out.println("<h2>"+driverName+" "+dataSource+"</h2>"); 
			try{
				String driverName = "com.mysql.jdbc.Driver";
				//out.println("started");
				Class.forName(driverName);
				//out.println("authenticated");
				String dataSource = "jdbc:mysql://localhost:3306/medical";
				String user="manisha";
				String password="srkmanisha";
				Connection con = DriverManager.getConnection(dataSource,user,password);
				Statement st = con.createStatement();
				String Name = req.getParameter("name");
				String user_name = req.getParameter("user_name");
				String passwd = req.getParameter("psw");
				String mob_no = req.getParameter("no");		
				String email_id = req.getParameter("email");                                   // out.println(Name+" "+user_name+" "+passwd+" "+mob_no+" "+email_id);                      
				String q1 = "insert into CUSTOMER values('"+user_name+"','"+passwd+"','"+Name+"','"+mob_no+"','"+email_id+"')";
				
				//out.println(q1);
				int r1 = st.executeUpdate(q1);
				out.println(r1);
				RequestDispatcher rd=req.getRequestDispatcher("./basic1.html");
				rd.forward(req,res);
				
				
				
				
			}
			catch(ClassNotFoundException e1){							//out.println("class not found");		
				e1.printStackTrace();
				
		}


			catch(SQLException e2){
				out.println("combination of email and username exist");
				RequestDispatcher rd=req.getRequestDispatcher("./signup.html");
                                rd.include(req,res);


				e2.printStackTrace();
			}
			out.println("</body>");
			out.println("</html>");
			
		
		
	}
	
	
}
