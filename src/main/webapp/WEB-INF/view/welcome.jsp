<html>
<head>
<%@ include file="fragments/header.jspf"%>
<title>HoneyComb</title>

</head>
<body>
	<%@ include file="fragments/navbar.jspf"%>

	<!-- Compiled and minified JavaScript -->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<div style="margin: 2% 10% 0% 10%">

		<%@ include file="fragments/messages.jspf"%>

		<%@ include file="fragments/createpost.jspf"%>

		<%@ include file="fragments/post-feed.jspf"%>

	</div>
</body>
</html>