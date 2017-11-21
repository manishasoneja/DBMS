import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class LogOutServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		HttpSession session = req.getSession(false);
		if(session!=null){
			session.invalidate();
		}
		System.out.println(session.getId());
		res.sendRedirect("login.html");
		 
	}
}
