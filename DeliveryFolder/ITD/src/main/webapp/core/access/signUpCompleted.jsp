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
	max-width: 500px;
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

	<br> <br> <br> <br> <br> <br>
	<main class="form-general">
	<div class="alert alert-success" role="alert">
	  <h4 class="alert-heading">Registration completed!</h4>
	  <p>Welcome on board, from this moment you are present in the system and you will be able to use all the services after having logged in.</p>
	  <hr>
	  <p class="mb-0">Remember to write down this ID code that has been assigned to you by the system and that you will use to log in together with the password.</p>
	  <p class="mb-0">Your ID is <b><% String fName = request.getParameter("id"); out.print(fName); %></b></p>
	</div>
	</main>
	<main class="form-buttons">	
		<div class="md-form md-outline input-with-post-icon datepicker">
			<form method="POST" action="continue.do">
				<button class="w-100 btn btn-lg btn-outline-secondary" type="submit"
					value="continue" formmethod="post"> Log in </button>
			</form>
		</div>
	</main>	
	
</body>
</html>