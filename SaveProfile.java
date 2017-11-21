import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SaveProfile extends HttpServlet{
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		PrintWriter out = res.getWriter();
        String user_name=req.getParameter("name");
		String user_mob=req.getParameter("number");
        String user_email = req.getParameter("email");
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
                String q1 ="update CUSTOMER set cust_name='"+user_name+"' where cust_id='"+user_+"'";
                String q2 ="update CUSTOMER set cust_email='"+user_email+"' where cust_id='"+user_+"'";
                String q3 ="update CUSTOMER set cust_mob='"+user_mob+"' where cust_id='"+user_+"'";
				int rs1 = st.executeUpdate(q1);
                int rs2 = st.executeUpdate(q2);
                int rs3 = st.executeUpdate(q3);
                st.close();
                con.close();						
			}

			catch(Exception e){
                e.printStackTrace();
            }
            session.invalidate();
			RequestDispatcher rd = req.getRequestDispatcher("login.html");
			rd.forward(req,res);
		}

    }
}
