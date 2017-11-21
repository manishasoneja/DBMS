import java.sql.*;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ShoppingCart extends HttpServlet{

    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException{
        int f=0;
        int pharmacy_id=0;
        int product_id=0;
        String pharmacy_name=null;
        String product_name=null;
        int pro_cost=0;
        // price=0;
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<html>");
        out.println("<style>table {font-family: arial,sans-serif;border-collapse:collapse;width: 100%; font-size: 75%;}th{border: 2px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;background-color:#aab7b8;}td {border: 1px solid #dddddd;text-align: center;padding: 15px;border-bottom: 1px solid #ddd;}tr:hover {background-color:  #dde;} p {position: fixed; bottom:200; left:500;} textarea {display: inline;padding: 10px;font-size:inherit;display:inline;margin-left: 25px; width: 50%; border:1px solid black;margin-bottom: 25px;}");
        out.println("body {");
		out.println("background-image:url('Medical-Shop-Software.jpg');");
		out.println(" background-repeat:no-repeat;");
		out.println("background-size:cover;");
		out.println("height: 100%;background-position: center;");
        out.println("}");
        out.println("</style>");
        out.println("<body>");
        HttpSession session = req.getSession(false);
            Statement st=null;
            String user_ =(String) session.getAttribute("user");
            String user_name = (String)session.getAttribute("user_id");
            System.out.println(user_);
            if(session.getAttribute("user")==null){
                resp.sendRedirect("login.html");
            }
            else{
                out.println("<h1>"+user_+"'s cart"+"</h1>");
            try{
                
                String driverName="com.mysql.jdbc.Driver";
                Class.forName(driverName);
                String url ="jdbc:mysql://localhost:3306/medical";    
                String userName="manisha";
                String passwd = "srkmanisha";
                Connection con =DriverManager.getConnection(url,userName,passwd);
                st = con.createStatement();
                DatabaseMetaData dbm = con.getMetaData();
                ResultSet tables = dbm.getTables(null, null, user_name+"_cart", null);
                if(tables.next()){
                    //f=1;
                    //out.println("<h1>exist</h1>");
                    tables.close();
                }
                else{    
                String create_query = "create table "+user_name+"_cart(product_id int,pharmacy_id int,product_name varchar(255),pharmacy_name varchar(255),ordq int,price float,primary key(product_id,pharmacy_id),foreign key(product_id) references PRODUCT(product_id) on delete cascade on update cascade,foreign key(pharmacy_id) references PHARMACY(ph_id) on delete cascade on update cascade);"; 
                out.println(create_query);
                int rs = st.executeUpdate(create_query);
                }  
            //    out.println(f);
               String todo = req.getParameter("todo");
               if(todo==null){
                   todo="view";
               }
               if(todo.equals("add") || todo.equals("update")){
                   if(todo.equals("add")){
                   String ids[] = req.getParameterValues("id");
                   /*for(String id:ids){
                       out.println(id);
                   }*/
                   if(ids==null){
                        out.println("<h1>No item selected</h1>");
                   }
                   else{
                       for(String id:ids){
                           String part[] = id.split("-");
                           int ph_id = Integer.parseInt(part[0]);
                           int pp_id = Integer.parseInt(part[1]);
                           String q1 ="select distinct p.product_id,ph.ph_id,p.product_name,ph.ph_name,c.product_cost from PRODUCT p,PHARMACY ph,MEDICINE_COUNT c where p.product_id=c.product_id and ph.ph_id=c.ph_id and ph.ph_id='"+ph_id+"' and p.product_id='"+pp_id+"' and count_product>0";
                      //     out.println(q1);
                           ResultSet r1 = st.executeQuery(q1);                               
                           if(r1.next()){
                           // out.println("hello");   
                            product_id = r1.getInt(1);  
                            pharmacy_id = r1.getInt(2); 
                            product_name = r1.getString(3);
                            pharmacy_name = r1.getString(4);
                            pro_cost = r1.getInt(5);
                        //   out.println(product_id+" "+pharmacy_id+" "+pharmacy_name+" "+pro_cost);
                           }
                           r1.close();
                           int qty = Integer.parseInt(req.getParameter("qty"+id));
                           float price = qty*pro_cost;   
                           String check_update = "select * from "+user_name+"_cart where product_id="+product_id+" and pharmacy_id="+pharmacy_id+";";
                      //     out.println(check_update);
                           ResultSet iq = st.executeQuery(check_update);
                           if(iq.next()){
                               int exist_quantity = iq.getInt(5);
                               int new_quantity = exist_quantity+qty;
                               float new_price = pro_cost*new_quantity;
                              String perform_update_qty = "update "+user_name+"_cart set ordq="+new_quantity+" , price="+new_price+" where product_id="+product_id+" and pharmacy_id="+pharmacy_id+";";
                          //    out.println(perform_update_qty);
                              int r3 = st.executeUpdate(perform_update_qty);  
                           }  
                           else{     
                           String insert_query = "insert into "+user_name+"_cart values("+product_id+","+pharmacy_id+",'"+product_name+"','"+pharmacy_name+"',"+qty+","+price+")"; 
                       //   out.println(insert_query);
                           int r2 = st.executeUpdate(insert_query);
                           }
                          
                        }
//                        req.setParameter(todo,"view");
                   }

                }
                else if(todo.equals("update")){
                    String id =req.getParameter("id");
                    String part[] = id.split("-");
                    int phid = Integer.parseInt(part[0]);
                    int prid = Integer.parseInt(part[1]);
                    String qty1 = req.getParameter("qty"+id);
                    int qty2 = Integer.parseInt(qty1);
                    String find_price = "select product_cost from MEDICINE_COUNT where ph_id="+phid+" and product_id="+prid+";";
                    ResultSet find = st.executeQuery(find_price);
                    find.next();
                    float price= find.getInt(1)*qty2;
                    String update_query = "update "+user_name+"_cart set ordq="+qty2+", price="+price+" where pharmacy_id="+phid+" and product_id="+prid+";";
                    int up = st.executeUpdate(update_query);

                }
            }
            if(todo.equals("remove")){
                String id = req.getParameter("id");
                String parts[]=id.split("-");
                int phid=Integer.parseInt(parts[0]);
                int prid = Integer.parseInt(parts[1]);
                String delete_query = "delete from "+user_name+"_cart where product_id="+prid+" and pharmacy_id="+phid+";";
                int del = st.executeUpdate(delete_query);    

            }
                String check_empty = "select count(*) from "+user_name+"_cart";
                ResultSet rcheck = st.executeQuery(check_empty);
                rcheck.next();
                if(rcheck.getInt(1)!=0){
                   float totalprice=0;
                   String view_cart = "select * from "+user_name+"_cart";
                   ResultSet r4 = st.executeQuery(view_cart);
                   out.println("<table><tr><th>Product Name</th><th>Pharmacy Name</th><th>Price</th><th>Quantity Ordered</th><th>Remove</th></tr>");
                   out.println(); 
                   while(r4.next()){
                       int ph_id = r4.getInt(2);
                       int pr_id = r4.getInt(1);
                       float price_ =r4.getFloat(6);
                       String phid = String.valueOf(ph_id);
                       String prid = String.valueOf(pr_id);
                       String id = phid+"-"+prid;
                       out.println("<tbody>");
                       out.println("<tr>");
                       out.println("<td>"+r4.getString(3)+"</td>");
                       out.println("<td>"+r4.getString(4)+"</td>");
                       out.println("<td>"+price_+"</td>");
                       out.println("<td><form method='get'>");
                       out.println("<input type='hidden' name='todo' value='update' />");
                       out.println("<input type='hidden' name='id' value='" + id + "' />");
                       out.println("<input type='text' size='3' name='qty" + id + "' value='" + r4.getInt(5) + "' />" );
                       out.println("<input type='submit' value='Update' />");
                       out.println("</form></td>"); 
                      out.println("<td><form method='get'>");
                      out.println("<input type='submit' value='Remove'>");
                      out.println("<input type='hidden' name='todo' value='remove' />");
                      out.println("<input type='hidden' name='id' value='"+id+"' />");
                      out.println("</form></td>");
                      out.println("</tr>");
                      out.println("</tr>");    
                      totalprice = totalprice+price_;                 
                   }
                   out.println("<tr><td colspan='5' align='right'>Total Price: Rs");
                   out.printf("%.2f</td></tr>", totalprice);
                   out.println("</tbody>");
                   out.println("</table>");
                 //  out.printf("Total Amount=%f",totalprice);
                 out.println("<br>");
                 out.println("<form method='get' action='OrderServlet'>");
              //   out.println("Address: ");
                 out.println("<textarea rows='5'cols='30' name='address'> ");
                 out.println("Enter Address:");
                 out.println("</textarea><br>");
            //     out.println("Area:    ");
                 out.println("<textarea rows='5'cols='30' name='area'> ");
                 out.println("Enter Area:");
                 out.println("</textarea><br>");
                 out.println("<input type='submit' value='CHECK OUT' />");
                 out.println("</form>");  
               }


        }  
        
            catch(ClassNotFoundException e1){
                   e1.printStackTrace(); 
            }                
            catch(Exception e2){
                e2.printStackTrace();
            }
            out.println("</body>");
            out.println("</html>");
    

        }
    }
}