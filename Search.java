import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

public class Search extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int f=0;
		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String dbName = "medical";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "manisha";
		String password = "srkmanisha";
		out.println("<form method='get' action='ShoppingCart'>");
		out.println("<input type=\"hidden\" name='todo' value='add'>");
		out.println("<style>div {height:300px; overflow-y: scroll; position:relative; }table {font-family: arial,sans-serif;border-collapse:collapse;width: 100%; font-size: 75%;}th{border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;background-color:#aab7b8;}td {border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;}tr:hover {background-color:  #dde;} p {position: fixed; bottom:200; left:500;} ");
		out.println("body {");
		out.println("background-image:url('Medical-Shop-Software.jpg');");
		out.println(" background-repeat:no-repeat;");
		out.println("background-size:cover;");
		out.println("height: 100%;background-position: center;");
		out.println("}");
		out.println("</style>");
		Statement st;
		try {
			Class.forName(driver).newInstance();
			conn=DriverManager.getConnection(url+dbName,userName,password);
			String loc = request.getParameter("loc");
			String domain = request.getParameter("domain");
			String product = request.getParameter("Product");
			//out.println("<form method='get' action='CART'>");
			//out.println("domain"+ domain.length()+" "+"product"+" "+ product);
			//request.setParameter("loc",loc);
			//request.setParameter("product",product);
			if(domain.length()!=0 && product.length()!=0){
			    String query = "select distinct p.ph_name,p.ph_address,p.ph_phone,product_name,product_cost,product_quantity,p.ph_id,pp.product_id,manufacturer from PHARMACY p,PRODUCT pp,MEDICINE_COUNT mc,MEDICINE_MANUFACTURER mm where p.ph_id = mc.ph_id and pp.product_id = mc.product_id and mm.product_id=pp.product_id and p.ph_area REGEXP '"+loc+"' and pp.product_name REGEXP '"+product+"' and pp.condition_ REGEXP '"+domain+"'";
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				if(rs.next()){
				out.println("<h1>Searched items....</h1>");
				conn = DriverManager.getConnection(url+dbName,userName,password);
				System.out.println("Connected to the database");
				//String loc = request.getParameter("loc");
			//	if(rs.next()){
				out.println("<div><table><tr><th>Pharmacy Name</th><th>Pharmacy Address</th><th>Pharmacy Phone</th><th>Product</th><th>Manufacturer</th><th>Cost</th><th>Quantity</th><th>Order Quantity</th></tr></div>");
				//out.println("<div><table ><tr><th>pharmacy name</th><th>pharmacy address</th><th>phone</th><th>product</th><th>cost</th><th>Quantity</th></tr></div>");
				while(rs.next()){
					f=1;
					int phid = rs.getInt("ph_id");
					int pid = rs.getInt("product_id");
					String ph_id = String.valueOf(phid);
					String p_id = String.valueOf(pid);
					String id = ph_id+"-"+p_id;				
					out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getLong(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(9)+"</td><td>"+rs.getInt(5)+"</td></td><td>"+rs.getString(6)+"</td><td><input type='text' size='3' value='1' name='qty"+id+"'></td><td><input type='checkbox' name='id' value='"+id+"'></td></tr>");
				}	
					//	out.println("</div>");
						out.println("</table>");
						out.println("<p>");
						out.println("<form method='post' name='suggest' action='http://localhost:8080/searchpage/Suggest'>");
						out.println("<input type='submit' value='Suggestions' />");
						out.println("</form>");
						out.println("</p>");
			}

		}
			else if(domain.length()==0 && product.length()!=0){
				String query2 = "select distinct p.ph_name,p.ph_address,p.ph_phone,product_name,product_cost,product_quantity,p.ph_id,pp.product_id,manufacturer from PHARMACY p,PRODUCT pp,MEDICINE_COUNT mc,MEDICINE_MANUFACTURER mm where p.ph_id = mc.ph_id and pp.product_id = mc.product_id and mm.product_id=pp.product_id and p.ph_area REGEXP '"+loc+"' and pp.product_name REGEXP '"+product+"'and count_product>0";
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(query2);
			//	out.println("<form method='get' action='CART'>");
				if(rs.next()){
				out.println("<h1>Searched items</h1>");
				out.println("<div><table><tr><th>Pharmacy Name</th><th>Pharmacy Address</th><th>Pharmacy Phone</th><th>Product</th><th>Manufacturer</th><th>Cost</th><th>Quantity</th><th>Order Quantity</th></tr></div>");
				//if(rs.next()){
				//out.println("<div><table ><tr><th>Pharmacy Name</th><th>Pharmacy Address</th><th>Pharmacy Phone</th><th>Product</th><th>Cost</th><th>Quantity</th><th>Order Quantity</th></tr></div>");
				while(rs.next()){
					f=1;
					int phid = rs.getInt("ph_id");
					int pid = rs.getInt("product_id");
					String ph_id = String.valueOf(phid);
					String p_id = String.valueOf(pid);
					String id = ph_id+"-"+p_id;		
					out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getLong(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(9)+"</td><td>"+rs.getInt(5)+"</td><td>"+rs.getString(6)+"</td><td><input type='text' size='3' value='1' name='qty"+id+"'></td><td><input type='checkbox' name='id' value='"+id+"'></td></tr>");
				}
				out.println("</table>");
			}
		}
			else if (product.length()==0&& domain.length()!=0){	
				String query3 = "select distinct p.ph_name,p.ph_address,p.ph_phone,product_name,product_cost,product_quantity,p.ph_id,pp.product_id,manufacturer from PHARMACY p,PRODUCT pp,MEDICINE_COUNT mc,MEDICINE_MANUFACTURER mm where p.ph_id = mc.ph_id and pp.product_id = mc.product_id and mm.product_id=pp.product_id and p.ph_area REGEXP '"+loc+"' and pp.condition_ REGEXP '"+domain+"' and count_product>0";
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(query3);
				if(rs.next()){
				out.println("<h1>Searched items</h1>");
				out.println("<div><table><tr><th>Pharmacy Name</th><th>Pharmacy Address</th><th>Pharmacy Phone</th><th>Product</th><th>MANUFACTURER</th><th>Cost</th><th>Quantity</th><th>Order Quantity</th></tr></div>");
				//out.println("<div><table ><tr><th>Pharmacy Name</th><th>Pharmacy Address</th><th>Pharmacy Phone</th><th>Product</th><th>Cost</th><th>Quantity</th><th>Order Quantity</th></tr></div>");
				//out.println("<div><table ><tr><th>ph_name</th><th>ph_address</th><th>ph_phone</th><th>product</th><th>cost</th><th>Quantity</th></tr></div>");
				while(rs.next()){
					f=1;
					int phid = rs.getInt("ph_id");
					int pid = rs.getInt("product_id");
					String ph_id = String.valueOf(phid);
					String p_id = String.valueOf(pid);
					String id = ph_id+"-"+p_id;		
					//out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getLong(3)+"</td><td>"+rs.getString(4)+"</td><td><input type='text' size='3' value='1' name='qty'></td><td><input type='checkbox' name='id' value='"+id+"'></td></tr>");
					out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getLong(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(9)+"</td><td>"+rs.getInt(5)+"</td><td>"+rs.getString(6)+"</td><td><input type='text' size='3' value='1' name='qty"+id+"'></td><td><input type='checkbox' name='id' value='"+id+"'></td></tr>");
				}
				out.println("</table>");
			}
			conn.close();
			System.out.println("Disconnected from database");
		}
		/*out.println("<p><input type='submit' value='Add to Cart' /></p>");
		out.println("<p><input type='reset' value='Clear' /></p>");
		out.println("</form>");*/

		if(f==0){
		//	out.println("<h2>No such item in the location entered</h2>");
		//	RequestDispatcher rd = request.getRequestDispatcher("./engine.jsp");
		//	rd.include(request,response);
			response.sendRedirect("./engine.jsp");
			
		}
		out.println("<p>");	
		out.println("<input type='submit' value='Add to Cart' />");
		out.println("<input type='reset' value='Clear' />");
		out.println("</form>");
		out.println("</p>");
		out.println("<p>");		
		out.println("<form method='post' name='suggest' action='http://localhost:8080/DBMSAPP/Suggest'>");
		out.println("<input type='submit' value='Suggestions' />");
		out.println("<input type=\"hidden\" name='loc' value="+loc+">");
		out.println("<input type=\"hidden\" name='product' value="+product+">");
		out.println("</form>");
		out.println("</p>");		
	 } catch (Exception e) {
			e.printStackTrace();
		}
	}
}