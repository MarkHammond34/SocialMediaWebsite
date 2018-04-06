<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="fragments/navbar.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="fragments/header.jspf"%>
<title>Trending</title>
</head>


<body>

	<div class="container">
		<%@ include file="fragments/messages.jspf"%>
		<div class="row">
			<h4>Trending</h4>
			<%@ include file="fragments/post-feed.jspf"%>
		</div>
	</div>
</body>
</html>