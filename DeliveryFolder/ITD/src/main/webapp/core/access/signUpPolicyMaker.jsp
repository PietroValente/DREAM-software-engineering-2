<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="generator" content="Codeply" />
<title>DREAM | sign up</title>
<link rel="icon"
	href="<%= request.getContextPath()%>/core/images/logo_min.png"
	type="image/png">
<!-- Bootstrap core CSS -->
<link
	href="<%= request.getContextPath() %>/core/assets/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<base target="_self">
<style>
#navbar img {
	display: block;
	height: 70px;
	margin: auto;
}

#navbar p {
	height: 10px;
}

.center {
	margin: auto;
	width: 20%;
	padding: 10px;
}

.form-general {
	width: 100%;
	max-width: 330px;
	padding: 15px;
	margin: auto;
	text-align: left;
}

.form-buttons {
	width: 100%;
	max-width: 200px;
	padding: 15px;
	margin: auto;
	text-align: left;
}

.form-lands {
	width: 100%;
	max-width: 800px;
	padding: 15px;
	margin: auto;
	text-align: left;
}

#HASH {
	display: flex;
	justify-content: space-between;
}

input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	margin: 0;
}
</style>
</head>
<body class="text-center">

	<nav id="navbar" class="navbar fixed-top navbar-dark bg-secondary">
		<img
			src="<%= request.getContextPath() %>/core/images/logo_white_navbar.png"
			alt="">
	</nav>

	<form method="POST" action="CreateUser.do">
		<br> <br> <br> <br> <br> <br>
		<h1 class="h2 mb-3 fw-normal">Policy Maker sign up</h1>
		<main class="form-general">
			<p>
				<input type="hidden" id="category" name="category" value="Policy Maker">
				<table>
				<tr>
				    <td>Category: </td>
				    <td> </td>
				    <td><b>Policy Maker</b></td>
				  </tr>
				  <tr>
				  	<td><br></td>
				  </tr>
				  <tr>
				    <td><label for="firstname">First Name: </label></td>
				    <td> </td>
				    <td><input type="text" id="firstname" name="firstname" maxlength="40" size="15"/></td>
				  </tr>
				  <tr>
				    <td><label for="lastname">Last Name: </label></td>
				    <td> </td>
				    <td><input type="text" id="lastname" name="lastname" maxlength="40" size="15"/></td>
				  </tr>
				  <tr>
				    <td><label for="password">Password:</label></td>
				    <td> </td>
				    <td><input type="text" id="password" name="password" maxlength="40" size="15"/></td>
				  </tr>
				</table>
				<br><br>
				<div class="modal-footer">
					<button type="submit" class="btn btn-outline-success" value="enter" formmethod="post">Confirm</button>
				</div>
			</p>
		</main>
		</form>
</body>
</html>