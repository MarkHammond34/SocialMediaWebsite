<html>
<head>
<%@ include file="fragments/header.jspf"%>
<style>
.centered-form {
	margin-top: 60px;
}

.centered-form .panel {
	box-shadow: rgba(0, 0, 0, 0.3) 10px 10px 10px;
}

#message {
	color: red;
	font-size: 11px;
}

.error-box {
	border-style: solid;
	border-width: 3px;
	border-radius: 5px;
	border-color: #ff0000;
	background: #ff7f7f;
	padding: 5px;
	margin: 5px;
}

.success-box {
	border-style: solid;
	border-width: 3px;
	border-radius: 5px;
	border-color: #008000;
	background: #99cc99;
	padding: 5px;
	margin: 5px;
}

.warning-box {
	border-style: solid;
	border-width: 3px;
	border-radius: 5px;
	border-color: #ffff00;
	background: #ffffb2;
	padding: 5px;
	margin: 5px;
}
</style>
<title>Login</title>
</head>
<body>

	<!-- Compiled and minified JavaScript -->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<%@ include file="fragments/messages.jspf"%>
	<div
		style="margin: 4% 30% 0% 30%; background-color: white; box-shadow: 10px 10px 5px #888888">

		<div style="text-align: center; padding-top: 1%">
			<h3 class="light">Login</h3>
			<p id="message">${message}</p>
		</div>
		<div class="row">
			<form class="col s12" method="post" action="login">
				<div class="row">
					<div class="input-field col s6">
						<input name="username" id="username" type="text" class="validate">
						<label for="username">Username</label>
					</div>
					<div class="input-field col s6">
						<input name="password" id="password" type="password"
							class="validate"> <label for="password">Password</label>
					</div>
				</div>
				<div style="text-align: center">
					<button class="btn waves-effect waves-light" type="submit"
						value="Login">Login</button>
				</div>
			</form>
		</div>
		<div style="text-align: center; padding-bottom: 1%">
			<p>
				Don't have an account? <a href="register">Register</a> | Forgot your
				password? <a class="modal-trigger" href="#resetPass">Reset</a>
			</p>
		</div>
	</div>

	<!-- Modal Structure -->
	<div id="resetPass" class="modal">
		<div class="modal-content">
			<div style="text-align: center">
				<p>Enter your email and we'll send you a temporary password!</p>
			</div>
			<div class="row">
				<form class="col s12" method="post" action="resetPass">
					<div class="row">
						<div class="input-field col s12">
							<i class="material-icons prefix">account_circle</i> <input
								id="email" name="email" type="email" class="validate"> <label
								for="icon_prefix">email</label>
						</div>
					</div>
					<div style="text-align: center">
						<button class="btn waves-effect waves-light" type="submit"
							value="Login">
							Send<i class="material-icons right">send</i>
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		(function($) {
			$(function() {
				//initialize all modals           
				$('.modal').modal();
				//or by click on trigger
				$('.trigger-modal').modal();
			});
		})(jQuery);
	</script>

</body>
</html>