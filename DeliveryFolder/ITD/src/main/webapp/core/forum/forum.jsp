<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="generator" content="Codeply" />
<title>DREAM | forum</title>
<link rel="icon"
	href="<%=request.getContextPath()%>/core/images/logo_min.png"
	type="image/png">
<!-- Bootstrap core CSS -->
<link
	href="<%=request.getContextPath()%>/core/assets/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<base target="_self">

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/mdbootstrap@4.5.14/js/mdb.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.bundle.min.js"></script>

</head>

<body class="text-center">
	<!-- Navbar -->
	<nav id="navbar" class="navbar fixed-top navbar-dark bg-secondary">
		<img
			src="<%=request.getContextPath()%>/core/images/logo_white_navbar.png"
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
						<a class="nav-link" href="javascript:;"
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
						<a class="nav-link active" href="javascript:;"
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

	<br>
	<br>
	<br>
	<br>
	<br>
	<h1 class="h2 mb-3 fw-normal">DREAM FORUM</h1>
	<main class="form-button">
		<!-- Inner main -->
		<div class="inner-main">
			<!-- Inner main header -->
			<div class="inner-main-header">
				<button class="btn btn-outline-secondary has-icon btn-block"
					type="button" data-toggle="modal" data-target="#threadModal">
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
						viewBox="0 0 24 24" fill="none" stroke="currentColor"
						stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
						class="feather feather-plus mr-2">
                        <line x1="12" y1="5" x2="12" y2="19"></line>
                        <line x1="5" y1="12" x2="19" y2="12"></line>
                    </svg>
					NEW DISCUSSION
				</button>
			</div>
		</div>
	</main>
	<!-- /Inner main header -->

	<!-- Inner main body -->
	<main class="form-general">
		<!-- Forum List -->
		<div class="inner-main-body p-2 p-sm-3 collapse forum-content show" id="discussions">

		</div>
		<!-- /Forum List -->
		<!-- /Inner main body -->
	</div>
	<!-- /Inner main -->


	<!-- New Thread Modal -->
	<div class="modal fade" id="threadModal" tabindex="-1" role="dialog"
		aria-labelledby="threadModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
					<div
						class="modal-header d-flex align-items-center bg-secondary text-white">
						<h6 class="modal-title mb-0" id="threadModalLabel">New
							Discussion</h6>
					</div>
					<form method="POST" action="forum.do">
					<div class="modal-body">
						<div class="form-group" style="text-align: left;">
							<label for="title">Title</label> <input type="text"
								class="form-control" id="title" name="title"  placeholder="Enter title" maxlength="199"
								autofocus="" />
						</div>
						<div class="form-group" style="text-align: left;">
							<label for="body" style="text-align: left;">Body</label>
							<textarea type="text" class="form-control" id="body" name="body"
								placeholder="Enter body" autofocus=""
								style="height: 150px; vertical-align: text-top" maxlength="499"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-outline-success" formmethod="post">Post</button>
						</form>
						<button type="button" class="btn btn-outline-danger" data-dismiss="modal">Cancel</button>
					</div>
			</div>
		</div>
	</main>

	<style>
#navbar img {
	display: block;
	height: 70px;
	margin: auto;
}

#navbar p {
	height: 10px;
}

.form-general {
	width: 100%;
	max-width: 800px;
	margin: auto;
}

.form-button {
	width: 100%;
	max-width: 300px;
	padding: 15px;
	margin: auto;
}

.card-body {
	text-align: left;
}
</style>
	<script>
	var container = document.getElementById('discussions');
	var command = '';
	<%for (int j = 0; j < Integer.parseInt(request.getParameter("DiscussionsNumber")); j += 1) {%>
		<%String i = ("" + j).trim();%>
		command += '<form id="discussionDetails'+<%out.print(i);%>+'" method="GET" action="discussionDetails.do">'
				+'<div class="card mb-2">'
				+'<div class="card-body p-2 p-sm-3">'
					+'<div class="media forum-item">'
					+'<input type="hidden" id="Discussion'+<%out.print(i);%>+'" name="Discussion" value="'+"<%out.print(request.getParameter("ID" + i));%>"+'">'
					+'<a class="card-link"> <img src="data:image/png;base64,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" class="rounded-circle" width="50" alt="User" />' 
					+'<small class="d-block text-center text-muted">'+"<%out.print(request.getParameter("Name" + i));%>"+'<br>'+"<%out.print(request.getParameter("Surname" + i));%>"+'</small></a>'
						+'<div class="media-body">'
						+'<p style="text-align: center"><small class="text-muted ml-2">'+"<%out.print(request.getParameter("Time" + i));%>"+'</small></p>'
							+'<h6 style="text-align: center"><a href="javascript:{}" onclick="document.getElementById('+"'"+'discussionDetails'+ <%out.print(i);%> +"'"+').submit(); return false;" class="text-body" >'+"<%out.print(request.getParameter("Subject" + i));%>"+'</a></h6>'
						+'</div>'
					+'</div>'
				+'</div>'
			+'</div>'
		+'</form>'
	<%}%>
	container.innerHTML += command;
	</script>
</body>

</html>