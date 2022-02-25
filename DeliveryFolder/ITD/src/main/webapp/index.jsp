<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author"
	content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.88.1">

<title>DREAM | index</title>

<link rel="icon" href="core/images/logo_min.png" type="image/png">

<!-- Bootstrap core CSS -->
<link href="core/assets/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>

<!-- Custom styles for this template -->
<link href="index.css" rel="stylesheet">

<script type="text/javascript">
	function errors() {
		var container = document.getElementById('errorMessage');
		if (window.location.search.localeCompare("?wrongAccess") == 0) {
			container.innerHTML+= ''
				+'<div class="alert alert-danger alert-dismissible fade show" role="alert">'
					+'<p id="error" style="text-align: center">Wrong ID or password.</p>'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
						+'<span aria-hidden="true">&times;</span>'
					+'</button>'
				+'</div>';
			window.scrollTo(0, document.body.scrollHeight);
		} else if (window.location.search.localeCompare("?wrongCode") == 0) {
			container.innerHTML+= ''
				+'<div class="alert alert-danger alert-dismissible fade show" role="alert">'
					+'<p id="error" style="text-align: center">Code not valid.</p>'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
						+'<span aria-hidden="true">&times;</span>'
					+'</button>'
				+'</div>';
			window.scrollTo(0, document.body.scrollHeight);
		} else if (window.location.search.localeCompare("?genericError") == 0) {
			container.innerHTML+= ''
				+'<div class="alert alert-danger alert-dismissible fade show" role="alert">'
					+'<p id="error" style="text-align: center">Something went wrong. Please log in again</p>'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
						+'<span aria-hidden="true">&times;</span>'
					+'</button>'
				+'</div>';
			window.scrollTo(0, document.body.scrollHeight);
		}
	}
	window.onload = errors;
</script>
</head>
<body class="text-center">
	<main class="form-index">
		<img class="mb-4" src="core/images/logo.png" alt="" height="157">
		<ul class="nav nav-pills mb-3 red" id="pills-tab" role="tablist">
			<li class="nav-item" role="presentation"><a
				class="nav-link active" id="pills-log-in-tab" data-toggle="pill"
				href="#pills-log-in" role="tab" aria-controls="pills-log-in"
				aria-selected="true">Log in</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="pills-sign-in-tab" data-toggle="pill" href="#pills-sign-in"
				role="tab" aria-controls="pills-sign-in" aria-selected="false">Sign-in</a>
			</li>
		</ul>
		<div class="tab-content border-0" id="pills-tabContent">
			<div class="tab-pane active" id="pills-log-in" role="tabpanel"
				aria-labelledby="pills-log-in-tab">
				<form method="POST" action="loginUser.do">
					<div class="form-floating">
						<input type="number" class="form-control" id="floatingInput"
							placeholder="ID" min="0" max="9999999999" name="id">
					</div>
					<br>
					<div class="form-floating">
						<input type="password" class="form-control" id="floatingPassword"
							placeholder="Password" name="password">
					</div>
					<br>
					<button class="w-100 btn btn-lg btn-outline-secondary"
						type="submit" value="log in" formmethod="post">Log in</button>
				</form>
				<br>
				<div id="errorMessage"></div>
			</div>
			<div class="tab-pane fade" id="pills-sign-in" role="tabpanel"
				aria-labelledby="pills-sign-in-tab">
				<form method="POST" action="checkCode.do">
					<div class="form-floating">
						<input type='text' class="form-control" id="floatingInput"
							placeholder="Insert your code" maxlength="10" name="code">
					</div>
					<br>
					<button class="w-100 btn btn-lg btn-outline-secondary"
						type="submit" value="enter" formmethod="post">Verify code</button>
				</form>
				<br>
			</div>
		</div>
		<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" />
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	</main>
</body>
</html>