<html>
	<head>
		<title>Profile</title>
		<link rel="stylesheet" type="text/css" href="style1.css">
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	</head>
	<form autocomplete method="POST" action="http://localhost:8080/DBMSAPP/SaveProfile">
	<i class="fa fa-pencil-square-o"></i>	
	Name:<input type="text" name="name" value='<%=request.getAttribute("user_id")%>' placeholder="Full Name" pattern="[a-zA-Z\s]{1,40}" title="only letters">
	<i class="fa fa-pencil-square-o"></i>
	Mobile No:<input type="text" name="number" value='<%=request.getAttribute("user_mob")%>' placeholder="Mobile Number" pattern="[0-9]{10}" title="10 digit number">
	<i class="fa fa-pencil-square-o"></i>
	Email id:<input type="email" name="email" value='<%=request.getAttribute("user_email")%>' placeholder="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" title="character set followed by @ character set followed by . and two three letters">
	<!--Address: <textarea class="scrollabletextbox" name="address"></textarea>-->
	<!--Area: <textarea name="area"></textarea>-->
	<input type="submit" value="SAVE">
</html>