import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SetProfile extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		PrintWriter out = res.getWriter();
		String user_id=null;
		String user_email=null;
		long user_mob=0;
		HttpSession session = req.getSession(false); 
		if(session!=null){
			String user_ = (String)session.getAttribute("user_id");
			
			try{
				String driverName = "com.mysql.jdbc.Driver";
				Class.forName(driverName);
				String dataSource = "jdbc:mysql://localhost:3306/medical";
				String userName = "manisha";
				String password = "srkmanisha";
				Connection con = DriverManager.getConnection(dataSource,userName,password);
				Statement st = con.createStatement();
				String q1 = "select cust_name,cust_mob,cust_email from CUSTOMER where cust_id='"+user_+"'";
				ResultSet rs = st.executeQuery(q1);
				while(rs.next()){
					 user_id = rs.getString("cust_name");
					 user_mob = rs.getLong("cust_mob");
					 user_email = rs.getString("cust_email");
				}
				req.setAttribute("user_id",user_id);
				req.setAttribute("user_mob",user_mob);
				req.setAttribute("user_email",user_email);
				out.println(user_id+" "+user_mob+" "+user_email);								
			}

			catch(Exception e){
			}
			RequestDispatcher rd = req.getRequestDispatcher("Profile.jsp");
			rd.forward(req,res);
		}

	}
}  
