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
    Connection con=null;
    Connection con1 =null;
    Connection con2 =null;
    Connection con3 =null;
    Connection con4 =null;
    Connection con5 = null;
    float total=0;    
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
             con5 = DriverManager.getConnection(url,username,passwd);
             con = DriverManager.getConnection(url,username,passwd);
             con1 = DriverManager.getConnection(url,username,passwd);
             con2 = DriverManager.getConnection(url,username,passwd);
             con3 = DriverManager.getConnection(url,username,passwd);
             con4 = DriverManager.getConnection(url,username,passwd);
             con.setAutoCommit(false);
             con1.setAutoCommit(false);
             con2.setAutoCommit(false);
             con3.setAutoCommit(false);
             con4.setAutoCommit(false);
            Statement st = con.createStatement();
            Statement st1 = con1.createStatement();
            Statement st2 = con2.createStatement();
            Statement st3 = con3.createStatement();
            Statement st4 = con4.createStatement();
            Statement st5 = con5.createStatement();
            PreparedStatement ps ;
          /*  String count_rows = "select count(order_no) from ORDERS";
               ResultSet count_ = st1.executeQuery(count_rows);
                count_.next();
                int count = count_.getInt(1);
                count = count+1;
                count_.close();*/
   /*        String get_email = "select cust_email from CUSTOMER where cust_id='"+user_name+"';";
            System.out.println(get_email);
            ResultSet r5 = st5.executeQuery(get_email);
            r5.next();*/
      /*      String email = r5.getString(1);      
            session.setAttribute("email",email);  */    
            String cart_query = "select * from "+user_name+"_cart";
            ResultSet roq = st.executeQuery(cart_query);
            String address = req.getParameter("address");
            address = address.trim();
            String area = req.getParameter("area");
            area = area.trim();
            String count_rows = "select count(order_no) from ORDERS";
            ResultSet count_ = st1.executeQuery(count_rows);
             count_.next();
             int count = count_.getInt(1);
             count = count+1;
             count_.close(); 
             out.println("<h1>"+user_+"'s order</h1>");
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
            String get_count = "select count_product from MEDICINE_COUNT where ph_id="+ph_id+" and product_id="+pp_id+";";
            ResultSet rt1 = st3.executeQuery(get_count);
            rt1.next();
            int count_available = rt1.getInt(1);
            int update_= count_available-ordq;
            String update_table = "update MEDICINE_COUNT set count_product="+update_+" where ph_id="+ph_id+" and product_id="+pp_id+";";
            System.out.println(update_table);
            int up_ = st2.executeUpdate(update_table); 
            out.println("<tr>");            
            out.println("<td>"+prod_name+"</td>");    
            out.println("<td>"+ph_name+"</td>");  
            out.println("<td>"+address_ph+"</td>");  
            out.println("<td>"+ordq+"</td>");  
            out.println("<td>"+price+"</td>");
            out.println("</tr>");
            total = total + price;    
        }
        String delete_cart = "delete from "+user_name+"_cart";
        int del = st.executeUpdate(delete_cart);
        con.commit();
        con1.commit();
        con2.commit();
        con3.commit();
        con4.commit();
        out.println("<tr><td colspan='5' align='right'>Total Price: Rs");
        out.printf("%.2f</td></tr>", total);
        out.println("</tbody>");
        out.println("Your order number is "+count);
    /*    String Subject = "Order Confirmation";
        String message = "The order number for your order is"+count;*/
    //    session.setAttribute("ord_no",count);
     /*   Mailer m = new  Mailer();
        m.send("manishasoneja.ms@gmail.com", "12031997",email,Subject, message);*/
    }
        catch(Exception e){
            e.printStackTrace();
            try{
            con.rollback();
            con1.rollback();
            con2.rollback();
            con3.rollback();
            con4.rollback();
            out.println("<h2>"+e.toString()+"</h2>");
            }
            catch(Exception e2){

            }
        }
        out.println("</table>");
        out.println("<a href='basic2.jsp'>BACK TO HOME</a>");
        out.println("</body>");            
       /* out.println("<form method='post' action='./MailServlet'>");
        out.println("<input type='submit' value='SEND'></input>");
        out.println("</form>");*/
        out.println("</html>");    
    }
}