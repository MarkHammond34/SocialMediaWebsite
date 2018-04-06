<html>
<head>
<%@ include file="fragments/header.jspf"%>
<title>Register</title>
<style>
.centered-form {
	margin-top: 60px;
}

/*
.centered-form .panel {
	box-shadow: rgba(0, 0, 0, 0.3) 10px 10px 10px;
}
*/
#message {
	color: red;
	font-size: 11px;
}
</style>
</head>
<body>

	<%@ include file="fragments/messages.jspf"%>

	<div class="container">
		<div class="centered-form">
			<div
				class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4 
        	col-lg-4 col-lg-offset-4 col-xl-4 col-xl-offset-4 white"
				style="padding: 20px;">

				<div class="panel panel-default">

					<div class="panel-heading center-align">
						<h3 class="panel-title">Register</h3>
					</div>

					<div class="panel-body">
						<div id="message" align="center">${message}</div>
						<form role="form" method="post" action="register">

							<div class="row">

								<div class="col s12 m6">
									<div class="form-group">
										<input type="text" name="first_name" id="first_name"
											class="form-control input-sm" placeholder="First Name">
									</div>
								</div>

								<div class="col s12 m6">
									<div class="form-group">
										<input type="text" name="last_name" id="last_name"
											class="form-control input-sm" placeholder="Last Name">
									</div>
								</div>
							</div>
							<div class="row">

								<div class="col s12 m6">
									<input type="email" name="email" id="email"
										class="form-control input-sm" placeholder="Email Address">
								</div>

								<div class="col s12 m6">
									<input type="text" name="username" id="username"
										class="form-control input-sm" placeholder="Username">
								</div>
							</div>

							<div class="row">

								<div class="col s12 m6">
									<div class="form-group">
										<input type="password" name="password" id="password"
											class="form-control input-sm" placeholder="Password">
									</div>
								</div>

								<div class="col s12 m6">
									<div class="form-group">
										<input type="password" name="password_confirmation"
											id="password_confirmation" class="form-control input-sm"
											placeholder="Confirm Password">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="center-align">
									<input style="display: inline-block;" type="submit"
										value="Register" class="btn btn-info btn-block">
								</div>
							</div>
							<div class="center-align">
								<p style="text-align:center">
									Already have an account? <a href="login">Login</a>
								</p>

							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>