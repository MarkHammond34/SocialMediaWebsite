<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="fragments/navbar.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@ include file="fragments/header.jspf"%>
<title>HoneyComb - Results</title>
</head>

<body>

	<!-- Compiled and minified JavaScript -->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<div style="margin: 0% 10% 0% 10%">

		<%@ include file="fragments/messages.jspf"%>
		<div class="row">
			<%@ include file="fragments/createpost.jspf"%>
			<h4 style="display: inline-block;">${resultsType}</h4>

			<!-- 
			<h2>Spring MVC file upload example</h2>
			<form method="POST" action="<c:url value='/upload' />"
				enctype="multipart/form-data">


				Please select a file to upload : <input type="file" name="file" />
				<input type="submit" value="upload" />

			</form>
			-->

			<!-- sorting form -->
			<form id="sort-form" action="sort" method="post"
				style="display: inline-block; float: right;">

				<input type="hidden" name="resultsType" value="${resultsType}" />

				<!-- sort type selection -->
				<select class="browser-default" name="sortType"
					onchange="this.form.submit()">
					<option>Sort By</option>
					<option value="trending">Trending</option>

					<c:if test="${userLoggedIn != null }">
						<option value="relevant">Relevant</option>
						<option value="followed">Followed</option>
					</c:if>

				</select>

				<!-- post ids for sort -->
				<c:forEach var="post" items="${postList}">

					<input type="hidden" name="postID" value="${post.postID}" />

				</c:forEach>

			</form>

			<c:choose>

				<c:when
					test="${fn:length(postList) == 0 && fn:length(userSearchResults) == 0}">
					<p>No results found.</p>
				</c:when>

				<c:otherwise>

					<!-- display post list -->
					<div class="row">
						<div class="col-xs-12">
							<div class="card-blocks">
								<c:forEach var="post" items="${postList}">
									<div class="card-item panel">
										<%@ include file="fragments/post.jspf"%>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>

					<!-- display user list -->
					<div class="row">
						<ul class="collection">
							<c:forEach var="user" items="${userSearchResults}">

								<li class="collection-item avatar"><a
									href="profile?userId=${user.userID}&content=Posts"> <img
										src="resources/images/CafeRacer.jpg" alt="" class="circle">
								</a> <span class="title">${user.firstName} ${user.lastName} -
										<a href="profile?userId=${user.userID}&content=Posts">@${user.username}</a>
								</span>
									<p>${user.bio}</p> <a
									href="profile?userId=${user.userID}&content=Posts"
									class="secondary-content"><i class="material-icons">person_outline</i>
								</a></li>

							</c:forEach>
						</ul>
					</div>

				</c:otherwise>

			</c:choose>

		</div>
	</div>

</body>

</html>