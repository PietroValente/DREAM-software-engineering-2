<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="generator" content="Codeply" />
<title>DREAM | report</title>
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

.form-lands {
	width: 100%;
	max-width: 800px;
	padding: 15px;
	margin: auto;
	text-align: left;
}

@
-webkit-keyframes chartjs-render-animation {
	from {opacity: 0.99
}

to {
	opacity: 1
}

}
@
keyframes chartjs-render-animation {
	from {opacity: 0.99
}

to {
	opacity: 1
}

}
.chartjs-render-monitor {
	-webkit-animation: chartjs-render-animation 0.001s;
	animation: chartjs-render-animation 0.001s;
}
</style>
</head>
<body class="text-center">
	<!-- Navbar -->
	<nav id="navbar" class="navbar fixed-top navbar-dark bg-secondary">
		<img
			src="<%= request.getContextPath() %>/core/images/logo_white_navbar.png"
			alt="">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsingNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="navbar-collapse collapse" id="collapsingNavbar">
			<ul class="navbar-nav">
				<li class="nav-item">
					<form id="home" method="GET" action="home.do">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('home').submit();">Home</a>
					</form>
				</li>
				<li class="nav-item active">
					<form id="viewdata" method="GET" action="viewdata.do">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('viewdata').submit();">View/update
							farm data</a>
					</form>
				</li>
				<li class="nav-item">
					<form id="askforhelp" method="GET" action="askforhelp.do">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('askforhelp').submit();">Ask
							for help</a>
					</form>
				</li>
				<li class="nav-item">
					<form id="viewranking" method="GET" action="viewranking.do">
						<a class="nav-link active" href="javascript:;"
							onclick="document.getElementById('viewranking').submit();">View
							ranking</a>
					</form>
				</li>
				<li class="nav-item">
					<form id="reportadvise" method="GET" action="reportadvise.do">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('reportadvise').submit();">Report/advise</a>
					</form>
				</li>
				<li class="nav-item">
					<form id="forum" method="GET" action="forum.do">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('forum').submit();">Forum</a>
					</form>
				</li>
				<li class="nav-item">
					<form id="weatherforecast" method="GET" action="weatherforecast.do"
						target="_blank">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('weatherforecast').submit();">Weather
							forecast</a>
					</form>
				</li>
			</ul>
			<br>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<form id="logout" method="GET" action="logout.do">
						<a class="nav-link" href="javascript:;"
							onclick="document.getElementById('logout').submit();">Log out</a>
					</form>
				</li>
			</ul>
		</div>
	</nav>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.bundle.min.js"></script>

	<br>
	<br>
	<br>
	<br>
	<br>
	<h1 class="h2 mb-3 fw-normal">Reports</h1>
	<main class="form-lands">
		<div id="reports"></div>
	</main>

	<script>
	    var container = document.getElementById('reports');
	    <% for(int j = 0; j < Integer.parseInt(request.getParameter("ReportNumber")); j+=1) { %>
	    	<%String i=(""+j).trim();%>
	    	container.innerHTML+='<h2 class="h4 mb-4 fw-normal">Report ID: '+"<%out.print(request.getParameter("Report"+i+"ID"));%>"+'</h2>';
	    	container.innerHTML+='<p>Farm ID: '+"<%out.print(request.getParameter("Report"+i+"Farm"));%>"+'<br>Agronomist: '+"<%out.print(request.getParameter("Report"+i+"Agronomist"));%>"+'<br>Date: '+"<%out.print(request.getParameter("Report"+i+"Date"));%>"+'<br>Advice: '+"<%out.print(request.getParameter("Report"+i+"Advice"));%>" +'<br>Description: '+"<%out.print(request.getParameter("Report"+i+"Description"));%>"+'<br></p>';
    	<% } %>
    </script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/mdbootstrap@4.5.14/js/mdb.min.js"></script>
</body>
</html>