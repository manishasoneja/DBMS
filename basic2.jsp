<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
<style>
  body {
    background-image:url("thumb-1920-697207.jpg");
    background-repeat:no-repeat;
    background-size:cover;    
  }   
  ul {
            list-style-type: none;
            margin: 0;
            padding:0;
            overflow: hidden;
                background-color:#333333;
        }
     .left {
            float:left;
        }
     .right {
         float:right;    
     }
     
        li a {
            display:block;
            color: white;
            text-align : center;
            padding : 16px;
            <!-- text-decoration:none;-->

        } 
	

        li a:hover {
            background-color:#111111;
        }
    .color1 {
		float:left;
		display:block;
                color: white;
                text-align : center;
                padding : 16px;
            <!-- text-decoration:none;-->
		background-color:#111111;
			
	}
    .color2 {
		float:right;
		display:block;
                color: white;
                text-align : center;
                padding : 16px;
            <!-- text-decoration:none;-->
		background-color:#111111;		
	}		
		
        


          </style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to One life -A PHARMACY PORTAL!</title>
</head>
<body>
<%
	String userId=null;
	String SessionId=null;
	if(session.getAttribute("user")==null){
		response.sendRedirect("./login.html");
	}
	//HttpSession session = request.getSession();
	SessionId=session.getId();
	userId=(String)session.getAttribute("user");	
	//session.setMaxInactiveInterval(2*60);	
	//Cookie[] cookies = request.getCookies();
	//Object user = request.getAttribute("uname");
	
	/*if(cookies!=null){
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("name")){
				userId=cookie.getValue();
				//break;
			}
			if(cookie.getName().equals("JSESSIONID")){
				SessionId=cookie.getValue();
			}
		}
}*/
%>
<ul>
                    <li class="left"><a href="engine.jsp">PHARMACY</a></li>
                 <!--   <li class="left"><a href="DOCTORS.html">DOCTORS</a></li>-->
                    <li class="left"><a href="ABOUTUS.html">ABOUT US</a></li>
                    <li class="right"><a href="./LogOutServlet">LOG OUT</a><li>
                    <li class="right"><a href="./ShoppingCart">CART</a></li>   
                    <li class="right"><a href="./SetProfile">PROFILE</li> 
                    <li class="color2">Hello, <%=userId %></li>                    
                    
                    
            </ul>

<!--<h1>Hello, <%=userId %></h1>-->
<!--<h2>Your session id is:<%=SessionId %></h2>-->
</body>
</html>
