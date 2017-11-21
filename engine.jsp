<html>
<style>
	
	table {
		font-family: arial,sans-serif;
		border-collapse: collapse;
		width: 75%;
	}

	th,td {
		border: 1px solid #dddddd;
		text-align: center;
		padding: 15px;
		border-bottom: 1px solid #ddd;
	}

	tr:hover {background-color: #dde;}

	img {
    width:100%;
}
body {
	background-image: url("http://www.campusselect.in/wp-content/uploads/2016/07/Medical-Shop-Software.jpg");
	height: 100%;
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
}
</style>

<head>
</head>
<body >
	<!--<img src="medicine.jpg" alt="HTML5 Icon">-->
	<br><br><br><br><br><br>
	<form method="post" name="frm" action="http://localhost:8080/DBMSAPP/Search">
	<center><table>
	<tr><td colspan=2>
	<h3>Search Item</h3></td></tr>
		<tr><td ><b>Location</b></td>
			<td> <input type="text" name="loc" placeholder="Enter location" required>
		</td></tr>

		<tr><td ><b>Domain</b></td>
			<td> <input type="text" name="domain" id="domain" placeholder="Enter domain">
		</td></tr>
		<tr><td ><b>Product</b></td>
			<td> <input type="text" name="Product" id="Product" placeholder="Enter product">
		</td></tr>
		<tr><td colspan=2 align="center">
		<input type="submit" name="submit" value="Search"></td></tr>
	</table></center>
	</form>
</body>
</html>

