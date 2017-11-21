import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.rowset.serial.SerialException;
public class CartServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
        int product_id=0;
        int pharmacy_id=0;
        String product_name=null;
        String pharmacy_name=null;
        int product_cost=0;
        String orderqty;
        int ordq=0;
        int price=0;
        HttpSession session = req.getSession(false);
        PrintWriter out = res.getWriter();
        Cart cart;
        String datasource = "jdbc:mysql://localhost:3306/medical";
        if(session!=null){
            synchronized(session){    
       cart =(Cart)session.getAttribute("cart");
        if(cart==null){
            cart = new Cart();
            session.setAttribute("cart",cart);
        }
    }
    
        String todo = req.getParameter("todo");
        if(todo==null){
            todo="view";
        }
        if(todo.equals("add") || todo.equals("update")){
        String id = req.getParameter("id");
        if(id==null){
            out.println("Select a product!!");

        }
        String parts[] = id.split("-");
        int ph_id = Integer.parseInt(parts[0]);
        int pr_id = Integer.parseInt(parts[1]);
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String username="manisha";
            String passwd ="srkmanisha";
            Connection con = DriverManager.getConnection(datasource,username,passwd);
            Statement st = con.createStatement();
            String q1 ="select distinct p.product_id,ph.ph_id,p.product_name,ph.ph_name,c.product_cost from PRODUCT p,PHARMACY ph,MEDICINE_COUNT c where p.product_id=c.product_id and ph.ph_id=c.ph_id and ph.ph_id='"+ph_id+"' and p.product_id='"+pr_id+"' and count_product>0";
            ResultSet rs = st.executeQuery(q1);
            rs.next();
             product_id = rs.getInt("product_id");
             pharmacy_id = rs.getInt("ph_id");
             product_name = rs.getString("product_name");
             pharmacy_name = rs.getString("ph_name");
             product_cost = rs.getInt("product_cost");
             orderqty = req.getParameter("");
             ordq = Integer.parseInt(orderqty);
             price = ordq*product_cost;
            
        }
        catch(Exception e){

        }
        if(todo.equals("add")){
        cart.add(product_id,pharmacy_id,product_name,pharmacy_name,price,ordq); 
        }
        else if(todo.equals("update")){ 
            cart.update(product_id, pharmacy_id, ordq,price);

        }
        }
        else if(todo.equals("remove")){
            String id = req.getParameter("id");
            String part[] = id.split("-");
            int ph_id = Integer.parseInt(part[0]);
            int prod_id = Integer.parseInt(part[1]);
            cart.remove(prod_id, ph_id);
        }
        if(cart.isEmpty()){
            out.println("<h1>YOUR CART IS EMPTY!! </h1>");
        }
        else{
            out.println("<table border='1' cellpadding='6'>");
            out.println("<tr>");
            out.println("<th>Product Name</th>");
            out.println("<th>Pharmacy Name</th>");
            out.println("<th>Product Cost</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>REMOVE FROM CART</th>");
            out.println("</tr>");
            //CartItem item = cart.getItems();
            float total=0;
            for(CartItem item:cart.getItems() ){
                int product1_id= item.getProdId();
                int pharmacy1_id = item.getPhId();
                String id_pharm = String.valueOf(pharmacy1_id);
                String id_prod= String.valueOf(product1_id);
                String id = id_pharm+"-"+id_prod;
                String product1_name = item.getProdname();
                String pharmacy1_name = item.getPhname();
                int pcost = item.getPrice();
                int qty = item.getquantity();
                out.println("<tr>");
                out.println("<td>"+product1_name+"</td>");
                out.println("<td>"+pharmacy1_name+"</td>");
                out.println("<td>"+pcost+"</td>");
                out.println("<td>"+qty+"</td>");
                out.println("<td><form method = \"get\">");
                out.println("<input type='hidden' name='todo' value='update' />");
                out.println("<input type='hidden' name='id' value='"+id+"' />");
                out.println("<input type='text' name='quantity"+id+"' value ='"+qty+"' />");
                out.println("<input type='submit' value='update' />");
                out.println("</form></td>");
                out.println("<td><form method = 'get'>");
                out.println("<input type='submit' value='REMOVE'>");
                out.println("<input type='hidden' name='todo' value='remove' />"); 
                out.println("<input type='hidden' name='id' value='"+id+"' />");
                out.println("</form></td>");  
                out.println("</tr>");
                total=total+pcost;


            }
            out.println("<tr><td colspan='5' align='right'>Total Price: Rs");
            out.printf("%.2f</td></tr>", total);
            out.println("</table>");
            out.println("<p><a href='Search'>Select more Products</a></p>");
        }


    }    
}
}