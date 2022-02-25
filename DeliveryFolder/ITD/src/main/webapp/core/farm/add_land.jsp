<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="generator" content="Codeply" />
<title>DREAM | farm</title>
<link rel="icon"
	href="<%=request.getContextPath()%>/core/images/logo_min.png"
	type="image/png">
<!-- Bootstrap core CSS -->
<link
	href="<%=request.getContextPath()%>/core/assets/dist/css/bootstrap.min.css"
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

input[type='number']{
    width: 160px;
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
			src="<%=request.getContextPath()%>/core/images/logo_white_navbar.png"
			alt="">
	</nav>


	<br>
	<br>
	<br>
	<br>
	<br>
	<h1 class="h2 mb-3 fw-normal">What is the size of the land you would like to add?</h1>
	<main class="form-general">
		<p>
		<form method="POST" action="addLand.do">
			<label for="Dimension">Dimension:</label>
			<input type="number" id="Dimension" name="Dimension" size="50" min="0" max="100000"  placeholder="Dimension in m^2"><br> 
			<br>
		</p>
	</main>
	<main class="form-buttons">
		<div id="HASH" class="blue-msg">
				<input type="hidden" name="LandID" value="<%out.print(request.getParameter("LandID"));%>"/>
				<button class="btn btn-outline-success" type="submit" value="date"
					formmethod="post">Confirm</button>
			</form>
			<form method="GET" action="viewdata.do">
				<button class="btn btn-outline-danger" type="submit" value="date"
					formmethod="get">Cancel</button>
			</form>
		</div>
	</main>

	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/mdbootstrap@4.5.14/js/mdb.min.js"></script>

</body>
</html>