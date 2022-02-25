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

	<form method="POST" action="modifyupdate.do">
		<br> <br> <br> <br> <br>
		<h1 class="h2 mb-3 fw-normal">Modify/update farm data</h1>
		<h1 class="h2 mb-3 fw-normal">
			<%out.print(request.getParameter("Day"));%>
		</h1>
		<main class="form-general">
			<h2 class="h4 mb-4 fw-normal">General information</h2>
			<p>
							<input type="hidden" name="FarmID"
					value="<% String ID2 = request.getParameter("ID"); out.print(ID2); %>" />
				<table>
				<tr>
				    <td>Farm ID: </td>
				    <td> </td>
				    <td><b><% String ID = request.getParameter("ID"); out.print(ID); %></b></td>
				  </tr>
				  <tr>
				    <td><label for="Address">Address:</label></td>
				    <td> </td>
				    <td><input type="text"
					id="Address" name="Address" maxlength="40" size="15"
					placeholder="<% String Address = request.getParameter("Address"); out.print(Address); %>"
					value="<% String Address2 = request.getParameter("Address"); out.print(Address2); %>"></td>
				  </tr>
				  <tr>
				    <td><label for="Phone">Phone:</label></td>
				    <td> </td>
				    <td><input type="text" id="Phone"
					name="Phone" maxlength="40" size="15"
					placeholder="<% String Phone2 = request.getParameter("Phone"); out.print(Phone2); %>"
					value="<% String Phone3 = request.getParameter("Phone"); out.print(Phone3); %>"></td>
				  </tr>
				  <tr>
				    <td><label for="Water">Water:</label></td>
				    <td> </td>
				    <td><input type="text" id="Water"
					name="Water" maxlength="40" size="15"
					placeholder="<% String Water = request.getParameter("Water"); out.print(Water); %>"
					value="<% String Water2 = request.getParameter("Water"); out.print(Water2); %>"></td>
				  </tr>
				</table>
			</p>
		</main>
		<main class="form-lands">
			<div id="lands">
				<input type="hidden" name="date"
					value="<%out.print(request.getParameter("Day"));%>" />
			</div>
		</main>
		<main class="form-buttons">
			<div id="HASH" class="blue-msg">
				<button class="btn btn-outline-success" type="submit" value="date"
					formmethod="post">Confirm</button>
	</form>
	<form method="GET" action="viewdata.do">
		<button class="btn btn-outline-danger" type="submit" value="date"
			formmethod="get">Cancel</button>
	</form>
	</div>
	</main>

	<script>
	    var container = document.getElementById('lands');
	    var i = 0;
	    <% for(int j = 0; j < Integer.parseInt(request.getParameter("LandNumber")); j+=1) { %>
	    	<%String i=(""+j).trim();%>
	    	if('false'.localeCompare('<%out.print(request.getParameter("Land" + i + "Empty"));%>') == 0){
	    		container.innerHTML+='<h2 class="h4 mb-4 fw-normal">Land '+<%=i%>+'</h2>';
		    	container.innerHTML+='<input type="hidden" name="LandID'+i+'" value="'+<%out.print(request.getParameter("Land"+i+"ID"));%>+'" />';
		    	container.innerHTML+='<input type="hidden" name="Host'+i+'" value="'+"<%out.print(request.getParameter("Land"+i+"HostNumber"));%>"+'" />';
		    	container.innerHTML+='<p><table><tr><td>Land ID:</td><td>'+<%out.print(request.getParameter("Land"+i+"ID"));%>
		    	+'</td><tr><td>Host:</td><td>'+"<%out.print(request.getParameter("Land"+i+"Host"));%>"
		    	+'</td><tr><td><label for="Dimension'+i+'">Dimension:</label></td><td><input type="number" id="Dimension'
		    	+i+'" name="Dimension'+i+'" min="0" max="100000" placeholder="'
		    	+<%out.print(request.getParameter("Land"+i+"Dimension"));%>+'" value="'
		    	+<%out.print(request.getParameter("Land"+i+"Dimension"));%>+'"></td><tr><td> <label for="Humidity'
		    	+i+'">Humidity:</label></td><td><input type="number" id="Humidity'+i+'" name="Humidity'+i
		    	+'" min="0" max="100" size="17" placeholder="'+<%out.print(request.getParameter("Land"+i+"Humidity"));%>
		    	+'" value="'+<%out.print(request.getParameter("Land"+i+"Humidity"));%>
		    	+'"></td></tr><tr><td><br></td></tr><tr><td><label for="Crop'+i+'"><b>Crop:</b></label></td><td><input type="number" id="Crop'+i+'" name="Crop'
		    	+i+'" min="1" size="17" placeholder="Quantity in gr."></td></tr></table></p><br>';
	    	}else{
	    		container.innerHTML+='<h2 class="h4 mb-4 fw-normal">Land '+<%=i%>+'</h2>';
	    		container.innerHTML+='<div class="alert alert-danger" role="alert" style="width: 100px;">Empty</div>'
	    		container.innerHTML+='<input type="hidden" name="LandID'
		    	+i+'" value="'+<%out.print(request.getParameter("Land"+i+"ID"));%>+'" />';
		    	container.innerHTML+='<p><table><tr><td>Land ID:</td><td>'+<%out.print(request.getParameter("Land"+i+"ID"));%>
		    	+'</td></tr><tr><td><label for="Host'+i+'">Host:</label></td><td>'
			    +'<select name="Host'+i+'">'
			    	+'<option name="0" value="0"></option>'
					+'<option name="1" value="1">Wheat</option>'
					+'<option name="2" value="2">Rice</option>'
					+'<option name="3" value="3">Jowar</option>'
					+'<option name="4" value="4">Sorghum</option>'
					+'<option name="5" value="5">Garlic</option>'
					+'<option name="6" value="6">Turmeric</option>'
					+'<option name="7" value="7">Ginger</option>'
					+'<option name="8" value="8">Peppers</option>'
					+'<option name="9" value="9">Coriander</option>'
					+'<option name="10" value="10">Anise</option>'
					+'<option name="11" value="11">Pulses</option>'
					+'<option name="12" value="12">Lentil</option>'
					+'<option name="13" value="13">Fennel</option>'
					+'<option name="14" value="14">Chickpeas</option>'
					+'<option name="15" value="15">Okra</option>'
					+'<option name="16" value="16">Cauliflowers</option>'
					+'<option name="17" value="17">Pumpkins</option>'
					+'<option name="18" value="18">Potatoes</option>'
					+'<option name="19" value="19">Gourds</option>'
					+'<option name="20" value="20">Tomatoes</option>'
					+'<option name="21" value="21">Cabbages</option>'
					+'<option name="22" value="22">Onions</option>'
					+'<option name="23" value="23">Cashew_nuts</option>'
					+'<option name="24" value="24">Groundnut</option>'
					+'<option name="25" value="25">Tea</option>'
					+'<option name="26" value="26">Coffee</option>'
				+'</select>'
		    	+'</td></tr><tr><td><label for="Dimension'+i+'">Dimension:</label></td><td><input type="number" id="Dimension'
		    	+i+'" name="Dimension'+i+'" min="0" max="100000" size="17" placeholder="'
		    	+<%out.print(request.getParameter("Land"+i+"Dimension"));%>+'" value="'
		    	+<%out.print(request.getParameter("Land"+i+"Dimension"));%>+'"></td></tr><tr><td><label for="Humidity'
		    	+i+'">Humidity:</label></td><td><input type="number" id="Humidity'+i+'" name="Humidity'+i
		    	+'" min="0" max="100" size="17" placeholder="'+<%out.print(request.getParameter("Land"+i+"Humidity"));%>
		    	+'" value="'+<%out.print(request.getParameter("Land"+i+"Humidity"));%>
		    	+'"></td></tr></table></p>';
		}
		i = i + 1;
	<%}%>
		container.innerHTML += '<input type="hidden" name="numberLands" value="'+i+'" />';
	</script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/mdbootstrap@4.5.14/js/mdb.min.js"></script>

</body>
</html>