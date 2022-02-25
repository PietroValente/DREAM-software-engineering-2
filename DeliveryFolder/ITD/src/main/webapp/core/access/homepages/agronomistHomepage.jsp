<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="generator" content="Codeply" />
<title>DREAM | home</title>
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

.center {
	margin: auto;
	width: 50%;
	padding: 10px;
}
</style>
</head>
<body>
	<!-- Navbar -->
	<nav id="navbar" class="navbar fixed-top navbar-dark bg-secondary">
		<img src="<%= request.getContextPath() %>/core/images/logo_white.png"
			alt="">
	</nav>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.bundle.min.js"></script>

	<form class="text-center">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<h1 class="h2 mb-3 fw-normal">
			Welcome back
			<% String fName = request.getParameter("firstname"); out.print(fName); %>
			<% String lName = request.getParameter("lastname"); out.print(lName); %>!
		</h1>
		<br>
		<br>
		<div class="center">
			<div class="alert alert-danger" role="alert">
				<h4 class="alert-heading">Agronomist homepage!</h4>
				<p>All features relating to agronomists have not been
					implemented and are assumed to work properly.</p>
			</div>
		</div>
	</form>
</body>
</html>