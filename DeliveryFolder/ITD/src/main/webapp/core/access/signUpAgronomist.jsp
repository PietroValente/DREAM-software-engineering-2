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
		<h1 class="h2 mb-3 fw-normal">Agronomist sign up</h1>
		<main class="form-general">
			<p>
				<input type="hidden" id="category" name="category" value="Agronomist">
				<table>
				<tr>
				    <td>Category: </td>
				    <td> </td>
				    <td><b>Agronomist</b></td>
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
				  <tr>
				    <td><label for="province">Province: </label></td>
				    <td> </td>
				    <td>
				    <select name="province">
						<option name="Adilabad">Adilabad</option>
						<option name="Bhadradri Kothagudem">Bhadradri_Kothagudem</option>
						<option name="Hanumakonda">Hanumakonda</option>
						<option name="Hyderabad">Hyderabad</option>
						<option name="Jagitial">Jagitial</option>
						<option name="Jangaon">Jangaon</option>
						<option name="Jayashankar Bhupalpally">Jayashankar_Bhupalpally</option>
						<option name="Jogulamba Gadwal">Jogulamba_Gadwal</option>
						<option name="Kamareddy">Kamareddy</option>
						<option name="Karimnagar">Karimnagar</option>
						<option name="Khammam">Khammam</option>
						<option name="Kumuram Bheem">Kumuram_Bheem</option>
						<option name="Mahabubabad">Mahabubabad</option>
						<option name="Mahbubnagar">Mahbubnagar</option>
						<option name="Mancherial">Mancherial</option>
						<option name="Medak">Medak</option>
						<option name="Medchal-Malkajgiri">Medchal-Malkajgiri</option>
						<option name="Mulugu">Mulugu</option>
						<option name="Nagarkurnool">Nagarkurnool</option>
						<option name="Nalgonda">Nalgonda</option>
						<option name="Narayanpet">Narayanpet</option>
						<option name="Nirmal">Nirmal</option>
						<option name="Nizamabad">Nizamabad</option>
						<option name="Peddapalli">Peddapalli</option>
						<option name="Rajanna Sircilla">Rajanna_Sircilla</option>
						<option name="Ranga Reddy">Ranga_Reddy</option>
						<option name="Sangareddy">Sangareddy</option>
						<option name="Siddipet">Siddipet</option>
						<option name="Suryapet">Suryapet</option>
						<option name="Vikarabad">Vikarabad</option>
						<option name="Wanaparthy">Wanaparthy</option>
						<option name="Warangal">Warangal</option>
						<option name="Yadadri Bhuvanagiri">Yadadri_Bhuvanagiri</option>
					</select>
				    </td>
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