import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
public class Basic2Servlet extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		//Cookie ck1[] = req.getCookies();
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null){
			res.sendRedirect("login.html");
		}
		String name =(String)session.getAttribute("user");
		String SessionId =session.getId();
		session.setMaxInactiveInterval(1*60); 
		out.println("<html>");
		out.println("<head>");
		out.println("<style>");
		out.println("body {");
		out.println("background-image:url('https://images7.alphacoders.com/697/thumb-1920-697207.jpg');");
		out.println(" background-repeat:no-repeat;");
		out.println("background-size:cover;");
		out.println("}");
		out.println(" ul {");
		out.println(" list-style-type: none;");
		out.println("margin: 0;");
		out.println(" padding:0;");
		out.println(" overflow: hidden;");
		out.println("  background-color:#333333;");
		out.println("}");
		out.println(".left {");
		out.println(" float:left;");
		out.println("}");
		out.println(".right {");
		out.println(" float:right;");
		out.println("}");
		out.println(" li a {");		
		out.println("  display:block;");
		out.println("  color: white; ");
		out.println("  text-align : center;");
		out.println("  padding : 16px;");
		out.println(" }");
		out.println(" li a:hover {");
		out.println("  background-color:#111111;");
		out.println(" }");
		out.println(" </style>");
		out.println("<title>Welcome to One Life -A PHARMACY PORTAL!</title>");
		out.println("<body>");	
		//out.println("<h1>Welcome to One life</h1>");	
		out.println("<ul>");
		out.println(" <li class=\"left\"><a href=\"PHARMACY.html\">PHARMACY</a></li>");
		out.println("<li class=\"left\"><a href=\"ABOUTUS.html\">ABOUT US</a></li>");
		out.println("<li class=\"right\"><a href=\"LogOutServlet\">LOG OUT</a></li>");
		out.println(" <li class=\"right\">Welcome "+name+"</li>");			   		
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
	}
}
