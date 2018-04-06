<html>
<head>
<%@ include file="fragments/header.jspf"%>
<title>Unlock</title>
</head>
<body>
<%@ include file="fragments/navbar.jspf"%>
<%@ include file="fragments/messages.jspf"%>
<div class="container">

<form method="post" action="unlockuser">

<input type="hidden" name="code" value="${code}">
<input type="hidden" name="userID" value="${userID}">
<button class="submit">Unlock</button>

</form>

</div>
</body>
</html>