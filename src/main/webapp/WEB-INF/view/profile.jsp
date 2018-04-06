<html>

<head>
<%@ include file="fragments/header.jspf"%>
<title>Profile</title>
</head>

<body>

	<!-- Compiled and minified JavaScript -->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

	<%@ include file="fragments/navbar.jspf"%>

	<%@ include file="fragments/messages.jspf"%>

	<div style="margin: 0% 2% 0% 0%">

		<div class="row">
			<div class="col s12 m3">
				<div class="card">
					<div class="card-image">
						<img src="resources/images/lacrosse.jpg">
						<c:choose>
							<c:when test="${userLoggedIn.userID == user.userID}">
								<a
									class="btn-floating halfway-fab waves-effect waves-light red modal-trigger"
									href="#editProfile"><i class="material-icons">add</i></a>
							</c:when>
						</c:choose>
					</div>
					<div class="card-content">

						<span class="card-title center-align">${user.firstName}
							${user.lastName} </span>
						<div class="row center-align">
							<p>${user.bio}</p>
						</div>

						<!-- check user not null -->
						<c:if test="${user.tags != null }">
							<div class="row center-align">
								<c:forEach var="tempTag" items="${user.tags}">
									<div class="chip">
										<a href="search?searchString=${tempTag}">${tempTag}</a>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<div class="row center-align">
							<p>
								<a href="profile?userId=${user.userID}&content=Posts">Posts</a>
								- <a href="profile?userId=${user.userID}&content=Likes">Likes</a>
								- <a href="profile?userId=${user.userID}&content=Tacks">Tacks</a>
								<c:if
									test="${userLoggedIn.userID != null && userLoggedIn.userID == user.userID}">
								- <a href="profile?userId=${user.userID}&content=Drafts">Drafts</a>
								</c:if>
								<!-- check user logged in and id match for displaying edit option -->
							</p>
						</div>

						<div class="row center-align">
							<p>
								<a href="profile?userId=${user.userID}&content=Following">Following</a>
								- <a href="profile?userId=${user.userID}&content=Followers">Followers</a>
							</p>
						</div>
						<div class="row">
							<c:if
								test="${userLoggedIn.userID != null && userLoggedIn.userID != user.userID}">

								<c:choose>
									<c:when test="${hasFollowedUser}">
										<div class="center-align">
											<form action="unfollow" method="post">

												<input type="hidden" name="id"
													value="${userLoggedIn.userID }" /> <input type="hidden"
													name="followed_userID" value="${user.userID}" /> <input
													class="white red-text btn"
													style="float: right; font-size: 11px;" type="submit"
													value="Unfollow" />

											</form>
										</div>

									</c:when>

									<c:otherwise>
										<div class="center-align">
											<form action="follow" method="post">

												<input type="hidden" name="id"
													value="${userLoggedIn.userID }" /> <input type="hidden"
													name="followed_userID" value="${user.userID}" /> <input
													class="btn" style="float: right;" type="submit"
													value="Follow" />

											</form>
										</div>

									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<div class="col s12 m9">

				<div class="row center-align">
					<h5>${content}</h5>
				</div>

				<c:if test="${content != 'Following' && content != 'Followers'}">
					<div class="row">
						<%@ include file="fragments/post-feed.jspf"%>
					</div>
				</c:if>

				<c:if test="${content == 'Following'}">
					<!-- display followed users -->
					<div class="row">
						<ul class="collection">
							<c:forEach var="userFollowed" items="${usersFollowed}">

								<li class="collection-item avatar"><a
									href="profile?userId=${userFollowed.userID}&content=Posts">
										<img src="resources/images/CafeRacer.jpg" alt=""
										class="circle">
								</a> <span class="title">${userFollowed.firstName}
										${userFollowed.lastName} - <a
										href="profile?userId=${userFollowed.userID}&content=Posts">@${userFollowed.username}</a>
								</span>
									<p>${userFollowed.bio}</p> <a
									href="profile?userId=${userFollowed.userID}&content=Posts"
									class="secondary-content"><i class="material-icons">person_outline</i>
								</a></li>

							</c:forEach>
						</ul>
					</div>
				</c:if>

				<c:if test="${content == 'Followers'}">
					<!-- display users following you -->
					<div class="row">
						<ul class="collection">
							<c:forEach var="userFollowedBy" items="${usersFollowedBy}">

								<li class="collection-item avatar"><a
									href="profile?userId=${userFollowedBy.userID}&content=Posts">
										<img src="resources/images/CafeRacer.jpg" alt=""
										class="circle">
								</a> <span class="title">${userFollowedBy.firstName}
										${userFollowedBy.lastName} - <a
										href="profile?userId=${userFollowedBy.userID}&content=Posts">@${userFollowedBy.username}</a>
								</span>
									<p>${userFollowedBy.bio}</p> <a
									href="profile?userId=${userFollowedBy.userID}&content=Posts"
									class="secondary-content"><i class="material-icons">person_outline</i>
								</a></li>

							</c:forEach>
						</ul>
					</div>
				</c:if>


			</div>
		</div>

		<!-- Modal Structure -->
		<div id="editProfile" class="modal">
			<div class="modal-content">
				<div style="text-align: center">
					<p>Fill in sections you'd like to update.</p>
				</div>
				<div class="row">
					<form class="col s12" method="post" action="updateUserProfile">
						<input type="hidden" name="id" value="${user.userID}" />
						<div class="row">
							<div class="input-field col s6">
								<i class="material-icons prefix">account_circle</i> <input
									name="firstName" id="firstName" type="text" class="validate">
								<label for="firstName">First Name</label>
							</div>
							<div class="input-field col s6">
								<i class="material-icons prefix">account_circle</i> <input
									name="lastName" id="lastName" type="text" class="validate">
								<label for="lastName">Last Name</label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s6">
								<i class="material-icons prefix">contacts</i> <input
									name="username" id="username" type="text" class="validate">
								<label for="username">Username</label>
							</div>
							<div class="input-field col s6">
								<i class="material-icons prefix">lock</i> <input name="password"
									id="password" type="password" class="validate"> <label
									for="password">Password</label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12">
								<i class="material-icons prefix">mode_edit</i>
								<textarea name="bio" id="bio" class="materialize-textarea"></textarea>
								<label for="bio">Biography</label>
							</div>
						</div>

						<div class="row">
							<p>Deselect interests you'd like to remove:</p>
							<ul style="list-style-type: none">
								<c:forEach var="tempTag" items="${user.tags}">
									<li
										style="float: left; display: inline; margin: 5px 7px 5px 7px">
										<input type="checkbox" name="${tempTag}" id="${tempTag}"
										checked="checked" /> <label for="${tempTag}">${tempTag}</label>
									</li>
								</c:forEach>
							</ul>

						</div>
						<div class="row">
							<div class="input-field col s12">
								<i class="material-icons prefix">insert_emoticon</i>
								<textarea name="newTags" id="newTags"
									class="materialize-textarea"></textarea>
								<label for="newTags">New Interests (separated by commas)</label>
							</div>
						</div>

						<div class="row">
							<div style="text-align: center">
								<button class="btn waves-effect waves-light" type="submit"
									value="Update">
									Update<i class="material-icons right">cloud_upload</i>
								</button>
							</div>
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

	</div>
</body>

</html>