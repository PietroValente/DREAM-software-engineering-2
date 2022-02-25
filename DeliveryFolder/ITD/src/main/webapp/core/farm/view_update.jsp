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
	max-width: 370px;
	padding: 15px;
	margin: auto;
	text-align: left;
}

.form-addLand {
	width: 100%;
	max-width: 150px;
	padding: 10px;
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

table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
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
<script type="text/javascript">
function hideModifyButton() {
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var yyyy = today.getFullYear();
	today = yyyy + '-' + mm + '-' + dd;
	if(today.toString().localeCompare('<%out.print(request.getParameter("Day"));%>') != 0) {
			document.getElementById("modifyButton").style.display = "none";
		}
	}
	window.onload = hideModifyButton;
</script>
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
						<a class="nav-link active" href="javascript:;"
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
	<h1 class="h2 mb-3 fw-normal">Your farm data</h1>
	<br>
	<div id="divChart" runat="server" align="center" />
	<canvas id="lineChart" width="800" height="270"></canvas>
	<br>
	<main class="form-general">
		<h2 class="h4 mb-4 fw-normal">General information <button class="btn btn-outline-secondary" id="oldCrops" type="submit" value="date" formmethod="get" data-toggle="modal" data-target="#threadModal">Old crops</button></h2>
		<p>
			Farm ID:
			<%
		String ID = request.getParameter("ID");
		out.print(ID);
		%><br> Address:
			<%
		String Address = request.getParameter("Address");
		out.print(Address);
		%><br> Phone:
			<%
		String Phone2 = request.getParameter("Phone");
		out.print(Phone2);
		%><br> Agronomist of reference:
			<%
		String Agronomist = request.getParameter("Agronomist");
		out.print(Agronomist);
		%><br> Water used:
			<%
		String Water = request.getParameter("Water");
		out.print(Water);
		%><br>
		</p>
		<div class="md-form md-outline input-with-post-icon datepicker">
			<form method="GET" action="viewdata.do">
				<input placeholder="Select date" type="date" id="date" name="date"
					class="form-control"
					value="<%out.print(request.getParameter("Day"));%>"> <br>
				<button class="w-100 btn btn-lg btn-outline-secondary" type="submit"
					value="date" formmethod="get">View day data</button>
			</form>
		</div>
	</main>
	<main class="form-addLand" id="form-addLand">
	</main>
	<main class="form-lands">

		<div id="lands"></div>

		<div style="text-align: center;">
			<form method="GET" action="modifyupdate.do">
				<input type="hidden" name="Day"
					value="<%out.print(request.getParameter("Day"));%>" />
				<button class="btn btn-outline-secondary" type="submit"
					id="modifyButton" value="date" formmethod="get">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
						fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
	  						<path
							d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"></path>
						</svg>
					Modify/update
				</button>
			</form>
		</div>
	</main>
	
	<!-- New Thread Modal -->
	<div class="modal fade" id="threadModal" tabindex="-1" role="dialog"
		aria-labelledby="threadModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
					<div
						class="modal-header d-flex align-items-center bg-secondary text-white">
						<h6 class="modal-title mb-0" id="threadModalLabel">Old crops</h6>
					</div>
					<div class="modal-body" id="old-crops">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-danger" data-dismiss="modal">Back</button>
					</div>
				</div>
			</div>
		</div>

	<script>
	var container = document.getElementById('lands');
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var yyyy = today.getFullYear();
	today = yyyy + '-' + mm + '-' + dd;
	<%for (int j = 0; j < Integer.parseInt(request.getParameter("LandNumber")); j += 1) {%>
	<%String i = ("" + j).trim();%>
		container.innerHTML += '<form method="GET" action="deleteLand.do">'
		+ '<input type="hidden" name="LandID" value="<%out.print(request.getParameter("Land" + i + "ID"));%>"/>'
		+ '<h2 class="h4 mb-4 fw-normal">Land ' +<%=i%>
		+ ' <button class="btn btn-outline-danger" id="deleteLandButton'+<%=i%>
		+'" type="submit" value="date" formmethod="get">Delete</button>'
		+ '</h2></form>';
		container.innerHTML += '<p>Land ID: '
				+
	<%out.print(request.getParameter("Land" + i + "ID"));%>
		+ '<br>Dimension: '
				+
	<%out.print(request.getParameter("Land" + i + "Dimension"));%>
		+ '<br>Empty: <b>'
				+
	<%out.print(request.getParameter("Land" + i + "Empty"));%>+'</b></p>';
	if('false'.localeCompare('<%out.print(request.getParameter("Land" + i + "Empty"));%>') == 0){
		container.innerHTML += '<p>Humidity: '
			+
		<%out.print(request.getParameter("Land" + i + "Humidity"));%>
		+ '<br>Host: '
				+
		"<%out.print(request.getParameter("Land" + i + "Host"));%>"
		+ '<br></p>';
		document.getElementById("deleteLandButton"+<%=i%>).style.display = "none";
	}
	if(today.toString().localeCompare('<%out.print(request.getParameter("Day"));%>') != 0) {
			document.getElementById("deleteLandButton"+<%=i%>).style.display = "none";
		}
	<%}%>
	if(today.toString().localeCompare('<%out.print(request.getParameter("Day"));%>') == 0) {
		container = document.getElementById('form-addLand');
		container.innerHTML += '<form method="GET" action="addLand.do">'
		+ '<button class="w-100 btn btn-lg btn-outline-success" '
		+ 'type="submit" value="date" formmethod="get">Add land</button>'
		+ '</form>';	
	}
	</script>
	<script>
		container = document.getElementById('old-crops');
		var command = ''
		+'<table>'
		  +'<tr>'
		    +'<th>Date</th>'
		    +'<th>Product</th>'
		    +'<th>Quantity</th>'
		    +'<th>Crop score</th>'
		  +'</tr>';
		<%for (int j = 0; j < Integer.parseInt(request.getParameter("CropsNumber")); j += 1) {%>
		<%String i = ("" + j).trim();%>
		command += ''  
		  +'<tr>'
		    +'<td>'+"<%out.print(request.getParameter("CropDate" + i));%>"+'</td>'
		    +'<td>'+"<%out.print(request.getParameter("CropProduct" + i));%>"+'</td>'
		    +'<td>'+"<%out.print(request.getParameter("CropQuantity" + i));%>"+'</td>'
		    +'<td>'+"<%out.print(request.getParameter("CropScore" + i));%>"+'</td>'
		  +'</tr>'
		 <%}%>
		 container.innerHTML += command + '</table>';
	</script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/mdbootstrap@4.5.14/js/mdb.min.js"></script>
	<script type="text/javascript">{//line
        var ctxL = document.getElementById("lineChart").getContext('2d');
        Chart.defaults.global.legend.display = false;
        var x = [];
        var y = [];
		<%for (int j = Integer.parseInt(request.getParameter("CropsNumber"))-1; j >= 0 ; j -= 1) {%>
		<%String i = ("" + j).trim();%>
			x.push("<%out.print(request.getParameter("CropDate" + i));%>");
			y.push("<%out.print(request.getParameter("CropScore" + i));%>");
		<%}%>
        var myLineChart = new Chart(ctxL, {
        type: 'line',
        data: {
            labels: x,
            datasets: [{
                label: "Score",
                data: y,
                backgroundColor: [
                    'rgba(255, 255, 255, .0)',
                    ],
                    borderColor: 'rgba(140, 17, 17, .5)',
                    borderWidth: 3,
                    pointBackgroundColor: 'rgba(140, 17, 17, .7)',
                    pointHoverBackgroundColor: 'rgba(140, 17, 17, .99)',
                    pointHoverBorderColor: 'rgba(140, 17, 17, .99)'
            }]
        },
        options: {
            responsive: false}
        });
        divChart.Controls.Add(myLineChart);}</script>
</body>
</html>