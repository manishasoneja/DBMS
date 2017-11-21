import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Check extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		HttpSession session = req.getSession(false);
		/*if(session!=null){
			session.invalidate();
		}*/
		//System.out.println(session.getId());
		if(session==null){
		System.out.println("session to be created");	
		res.sendRedirect("./login.html");
		}
		else{
			System.out.println("session exist");
			res.sendRedirect("./basic2.jsp");
		}
		 
	}
}
