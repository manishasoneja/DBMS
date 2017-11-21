import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;

public class OrderServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<style>table {font-family: arial,sans-serif;border-collapse:collapse;width: 100%; font-size: 75%;}th{border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;background-color:#aab7b8;}td {border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;}tr:hover {background-color:  #dde;} p {position: fixed; bottom:200; left:500;}");
        out.println("body {");
		out.println("background-image:url('Medical-Shop-Software.jpg');");
		out.println(" background-repeat:no-repeat;");
		out.println("background-size:cover;");
		out.println("height: 100%;background-position: center;");
        out.println("}");
        out.println("</style>");
        out.println("<body>");
        HttpSession session = req.getSession(false);
        String user_name = (String)session.getAttribute("user_id");
        String user_ = (String) session.getAttribute("user");
        try{
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            String url ="jdbc:mysql://localhost:3306/medical";
            String username = "manisha";
            String passwd = "srkmanisha";
            Connection con = DriverManager.getConnection(url,username,passwd);
            Connection con1 = DriverManager.getConnection(url,username,passwd);
            Connection con2 = DriverManager.getConnection(url,username,passwd);
             Connection con3 = DriverManager.getConnection(url,username,passwd);
             Connection con4 = DriverManager.getConnection(url,username,passwd);
             
            Statement st = con.createStatement();
            Statement st1 = con1.createStatement();
            Statement st2 = con2.createStatement();
            Statement st3 = con3.createStatement();
            Statement st4 = con4.createStatement();
            PreparedStatement ps ;
          /*  String count_rows = "select count(order_no) from ORDERS";
               ResultSet count_ = st1.executeQuery(count_rows);
                count_.next();
                int count = count_.getInt(1);
                count = count+1;
                count_.close();*/
            String cart_query = "select * from "+user_name+"_cart";
            ResultSet roq = st.executeQuery(cart_query);
            out.println(user_+"'s order");
            String address = req.getParameter("address");
            address = address.trim();
            String area = req.getParameter("area");
            area = area.trim();
            out.println("<table><tr><th>Product Name</th><th>Pharmacy Name</th><th>Pharmacy Address</th><th>Quantity</th><th>Price</th></tr>");
            while(roq.next()){
            int pp_id = roq.getInt(1);
            int ph_id = roq.getInt(2);
            String prod_name = roq.getString(3);
            String ph_name = roq.getString(4);
            int ordq = roq.getInt(5);
            float price = roq.getFloat(6);
            String pharm_details = "select ph_address,ph_area from PHARMACY where ph_id="+ph_id;
            ResultSet ph_details = st4.executeQuery(pharm_details);
            ph_details.next();
            String address_ph = ph_details.getString(1);
            out.println("<td>"+prod_name+"</td>");    
            out.println("<td>"+ph_name+"</td>");  
            out.println("<td>"+address_ph+"</td>");  
            out.println("<td>"+ordq+"</td>");  
            out.println("<td>"+price+"</td>");
            out.println("</tr>");
            String get_count = "select count_product from MEDICINE_COUNT where ph_id="+ph_id+" and product_id="+pp_id+";";
            ResultSet rt1 = st3.executeQuery(get_count);
            rt1.next();
            int count_available = rt1.getInt(1);
            int update_= count_available-ordq;
            String update_table = "update MEDICINE_COUNT set count_product="+update_+" where ph_id="+ph_id+" and product_id="+pp_id+";";
            System.out.println(update_table);
            int up_ = st2.executeUpdate(update_table);
           String count_rows = "select count(order_no) from ORDERS";
           ResultSet count_ = st1.executeQuery(count_rows);
            count_.next();
            int count = count_.getInt(1);
            count = count+1;
            count_.close(); 
            String order_query = "insert into ORDERS values(?,?,?,?,?,?,?,?);";
            ps = con.prepareStatement(order_query);
            ps.setInt(1,count);
            ps.setString(2,user_name);
            ps.setInt(3,ph_id);
            ps.setInt(4,pp_id);
            ps.setString(5,address);
            ps.setString(6,area);  
            ps.setInt(7,ordq);
            ps.setFloat(8,price);  
   //         System.out.println(order_query);
            int q1 = ps.executeUpdate();    
        }
        
        String delete_cart = "delete from "+user_name+"_cart";
        int del = st.executeUpdate(delete_cart);
    }
        catch(Exception e){
            e.printStackTrace();

        }
        out.println("</body>");
        out.println("</html>");                
    }
}