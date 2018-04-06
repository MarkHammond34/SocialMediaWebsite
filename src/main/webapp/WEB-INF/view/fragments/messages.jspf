<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ArrayList"%>
<div class="row">
	<c:if test="${!empty errorMessages}">
		<div class="col s3 error-box">
			<%
				ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMessages");
					for (String e : errors) {
			%>
			<%=e%>

			<%
				}
					errors.clear();
			%>
		</div>
	</c:if>
	<c:if test="${!empty warningMessages}">
		<div class="col s3 warning-box">
			<%
				ArrayList<String> warnings = (ArrayList<String>) request.getAttribute("warningMessages");
					for (String w : warnings) {
			%>
			<%=w%>
			<%
				}
					warnings.clear();
			%>
		</div>
	</c:if>
	<c:if test="${!empty successMessages}">
		<div class="col s3 success-box">
			<%
				ArrayList<String> successes = (ArrayList<String>) request.getAttribute("successMessages");
					for (String s : successes) {
			%>
			<%=s%>
			<%
				}
					successes.clear();
			%>
		</div>
	</c:if>
</div>