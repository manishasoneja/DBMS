import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
public class LogInServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html>");
		ServletContext sc = getServletContext();
		//out.println("<body>");
		//out.println("<h1>Login Page</h1>");
		try{
			int flag=0;
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			String dataSource = "jdbc:mysql://localhost:3306/medical";
			String userName = "manisha";
			String password = "srkmanisha";
			Connection con = DriverManager.getConnection(dataSource,userName,password);
			Statement st = con.createStatement();
			String user = req.getParameter("name");
			String passwd  = req.getParameter("psw");
			//out.println("user name:"+user);
			//out.println("password:"+passwd);
			String q1 = "select cust_id,cust_passwd,cust_name from CUSTOMER where cust_id='"+user+"';";
			//out.println("hello");
			ResultSet rs = st.executeQuery(q1);
			while(rs.next()){
			String user_name=rs.getString("cust_id");
			String pass = rs.getString("cust_passwd");
			String name = rs.getString("cust_name");
			//Cookie ck = new Cookie(name,user_name);
			//out.println("obtained user id and password is"+user_name+" "+pass);		 	
			if(user_name.equals(user) && pass.equals(passwd)){
				flag=1;
				HttpSession session = req.getSession();
				if(session.getAttribute("user")==null){
				session.setAttribute("user",name);
				session.setAttribute("user_id",user_name);
				System.out.println("new session");
				//session.setMaxInactiveInterval(2*60);
				//Cookie ck = new Cookie("name",user_name);
				//ck.setMaxAge(2*60);
				//res.addCookie(ck);
				//req.setAttribute("uname",name);
				//RequestDispatcher rd = sc.getRequestDispatcher("/basic2.jsp");		
				//RequestDispatcher rd = sc.getRequestDispatcher("/MyServlet");
				//rd.include(req,res);
				res.sendRedirect("./basic2.jsp");
			//	out.println("SUCCESFULL");
			}
			else{
				res.sendRedirect("./basic2.jsp");
			}
		}
			else{
				if(user_name.equals(user)){
					flag=1;
					out.println("INCORRECT PASSWORD");
					RequestDispatcher rd = req.getRequestDispatcher("./login.html");
					rd.include(req,res);
					//out.println("INCORRECT PASSWORD");
				}
				/*else{
					out.println("ACCOUNT DOESNT EXIST");
				}*/		
			}
			
			
		
		}
		if(flag==0){
			out.println("ACCOUNT DOESNT EXIST");
			RequestDispatcher rd = req.getRequestDispatcher("./signup.html");
			rd.include(req,res);
			//out.println("ACCOUNT DOESNT EXIST");
		}
		out.close();
			
		}
		catch(ClassNotFoundException e1){
			e1.printStackTrace();
		}
		catch(SQLException e2){
			e2.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}
}
