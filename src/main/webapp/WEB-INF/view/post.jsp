<html>
<head>
<%@ include file="fragments/header.jspf"%>
<title>Post</title>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

</head>
<body>
	<%@ page import="edu.ben.models.User"%>
	<%@ page import="edu.ben.models.Comment"%>
	<%@ page import="edu.ben.DAOs.CommentDAO"%>

	<%@ include file="fragments/navbar.jspf"%>

	<%@ include file="fragments/messages.jspf"%>

	<div style="margin: 2% 5% 0% 5%">

		<div class="card text-white bg-info">
			<div class="card-body">
				<blockquote class="card-blockquote">

					<!-- let post author edit this post (if they are logged in) -->
					<c:if test="${userLoggedIn.userID == post.userID}">
						<a
							class="btn-floating halfway-fab waves-effect waves-light red modal-trigger"
							href="#editPost"><i class="material-icons">add</i></a>
					</c:if>

					<!-- like this post (must be logged in, author cannot like own post) -->
					<!-- display if user has liked this post or not, let user toggle between them -->
					<c:if
						test="${userLoggedIn.userID != null && userLoggedIn.userID != post.userID }">

						<!-- display filled star if this logged in user liked this post -->
						<c:if test="${liked}">
							<a
								class="btn-floating halfway-fab waves-effect waves-light blue modal-trigger"
								href="#likePost"><i class="material-icons">star</i></a>
						</c:if>

						<!-- display empty star if this logged in user did not like this post yet -->
						<c:if test="${!liked}">
							<a
								class="btn-floating halfway-fab waves-effect waves-light blue modal-trigger"
								href="#likePost"><i class="material-icons">star_border</i></a>
						</c:if>
					</c:if>

					<h4 style="padding-top: 20px">${post.title}</h4>
					<p>${post.description}</p>
					<p>
						<a href="${post.link}">${post.link}</a>
					</p>

					<div style="margin-right: 10">
						<ul class="collapsible" data-collapsible="accordion">
							<li>
								<div class="collapsible-header">
									<i class="material-icons">filter_drama</i>Image
								</div>
								<div class="collapsible-body">
									<img src="resources/images/${post.imagePath}"
										style='height: 100%; width: 100%; object-fit: contain'>
								</div>
							</li>
						</ul>
					</div>

					<p style="font-size: 20px; padding-bottom: 20px">
						<a href="profile?userId=${post.userID}&content=Posts">@${post.userUsername}</a>
						${post.likeCount} Likes <a
							class="btn-floating waves-effect waves-light blue modal-trigger"
							href="#viewlikes"><i class="material-icons">loyalty</i></a>
					</p>

					<ul style="list-style-type: none">
						<c:forEach var="tempTag" items="${post.tags}">
							<a href="search?searchString=${tempTag.toLowerCase()}">
								<div class="chip">${tempTag}</div>
							</a>
						</c:forEach>
					</ul>
					<br>

				</blockquote>
			</div>
		</div>

		<!-- if user is logged in and this is their post, they can delete any comment -->
		<!-- if user is logged in and their comment is their, they can edit only their comment -->
		<!--  if user is logged in, they can add a comment -->
		<!-- else, cant add a comment, cant edit any comment -->

		<ul class="collapsible white" data-collapsible="accordion"
			style="margin-left: 5%; margin-right: 5%;">
			<li>
				<div class="collapsible-header active">
					<h5>
						<i class="material-icons" style="font-size: 30px">lightbulb_outline</i>Comments:
					</h5>
				</div>
				<div class="collapsible-body">

					<div class="collection">
						<c:set var="count" value="1" scope="page" />
						<c:forEach var="comment" items="${post.comments}">

							<div class="collection-item avatar">
								<i class="material-icons circle">folder</i>
								<!-- replace with user icon -->

								<span class="title"><a
									href="profile?userId=${comment.userID}&content=Posts"
									style="font-size: 22px">@${comment.username}</a></span>
								<p style="font-size: 14px">${comment.message}</p>


								<!-- if userLoggedIn.userID == post.userID not working-->
								<!-- post author or comment author can delete comment -->
								<c:if
									test="${userLoggedIn.userID == post.userID || userLoggedIn.username == comment.username }">
									<button
										class="btn-floating modal-trigger waves-effect waves-light red"
										data-target="modal_delete${count}" style="float: right">
										<i class="material-icons">delete</i>
									</button>
								</c:if>

								<!-- if userLoggedIn.username == comment.username -->
								<!-- comment author can edit -->
								<c:if test="${userLoggedIn.username == comment.username}">
									<button
										class="btn-floating modal-trigger waves-effect waves-light light-blue"
										data-target="modal${count}" style="float: right">
										<i class="material-icons">edit</i>
									</button>
								</c:if>

								<!-- button for un/liking a coomment -->
								<!-- cannot like youre own post, but others can like your post -->
								<c:if
									test="${userLoggedIn!= null && userLoggedIn.userID != comment.userID }">

									<!-- check if this post has been liked by this user 
									and set variables for forms accordingly -->

									<%
										User u = (User) request.getSession().getAttribute("userLoggedIn");
												Comment c = (Comment) pageContext.getAttribute("comment");
												boolean likesComment = CommentDAO.likesComment(u.getUserID(), c.getCommentId());

												if (likesComment) {
													// set the star filled
													request.setAttribute("fill", "");

													// set the form to unlike the comment
													request.setAttribute("action", "unlike");
												}

												else {
													// set the star to unfilled
													request.setAttribute("fill", "_border");

													// set the form to like the comment
													request.setAttribute("action", "like");
												}
									%>

									<!-- clicking the star button toggles the like and dislike status of the comment -->
									<form method="post" action="toggleLike">
										<input type="hidden" name="postID" value="${post.postID}">
										<input type="hidden" name="action" value="${action }">
										<input type="hidden" name="commentID"
											value="${comment.commentId }">
										<button type="submit"
											class="btn-floating modal-trigger waves-effect waves-light light-blue"
											style="float: right">
											<i class="material-icons">star${fill}</i>
										</button>
									</form>
								</c:if>
							</div>

							<!-- modal to edit comment -->
							<div id="modal${count}" class="modal">

								<form method="post" action="editComment">

									<div class="modal-content">
										<p style="font-size: 22px">@${comment.username}</p>
										<input type="text" name="comment" style="font-size: 14px"
											value="${comment.message}"> <input type="hidden"
											name="commentID" value="${comment.commentId}"> <input
											type="hidden" name="postID" value="${post.postID}">
									</div>

									<div class="modal-footer">
										<button class="submit">Edit</button>
									</div>
								</form>

							</div>

							<!-- modal to confirm if comment should be deleted -->
							<div id="modal_delete${count}" class="modal">

								<form method="post" action="deleteComment">
									<div class="modal-content">
										<p>Are you sure you want to delete this comment?</p>
									</div>

									<input type="hidden" name="postID" value="${post.postID}">
									<input type="hidden" name="commentID"
										value="${comment.commentId}">

									<div class="modal-footer">
										<button class="submit">Yes</button>
									</div>
								</form>
							</div>

							<c:set var="count" value="${count+1}" scope="page" />
						</c:forEach>
					</div>

					<!-- allow logged in users to comment on this website -->

					<%
						User u = (User) request.getSession().getAttribute("userLoggedIn");
						if (u == null) {
							request.getSession().setAttribute("disabledMessage", "You must be logged in to comment.<br>");
							request.getSession().setAttribute("disabled", "disabled");
						} else {
							request.getSession().removeAttribute("disabledMessage");
							request.getSession().removeAttribute("disabled");
						}
					%>

					<!-- comment box -->
					<br>
					<div>
						<h5 id="reply-title" class="comment-reply-title">Comment on
							this Post</h5>

						<form action="newComment" method="post" id="commentForm">
							<p class="comment-notes">${disabledMessage}</p>

							<input type="hidden" name="postID" value="${post.postID}">
							<input type="hidden" name="userID" value="${userLoggedIn.userID}">

							<textarea form="commentForm" name="comment" cols="45" rows="13"
								maxlength="65525" required="required" ${disabled}></textarea>
							<br> <br>
							<button type="submit" id="submit" class="btn btn-primary"
								${disabled}>submit</button>

						</form>
					</div>

				</div>
			</li>
		</ul>

	</div>

	<!-- modal to display who liked this post -->
	<!-- fixed footer...needs to be vertically centered -->
	<div id="viewlikes" class="modal modal-fixed-footer">
		<div class="modal-content">

			<c:if test="${usersLiked.size() > 0 }">
				<h4>Users who've liked this post</h4>

				<ul class="collection">
					<c:forEach var="userLiked" items="${usersLiked}">
						<li class="collection-item avatar"><img
							src="resources/images/CafeRacer.jpg" alt="" class="circle">
							<span class="title">${userliked.firstName}
								${userLiked.lastName} - @${userLiked.username}</span>
							<p>${userLiked.bio}</p> <a
							href="profile?userId=${userLiked.userID}&content=Posts"
							class="secondary-content"><i class="material-icons">person_outline</i></a></li>
					</c:forEach>
				</ul>
			</c:if>

			<c:if test="${usersLiked == null || usersLiked.size() == 0 }">
				<h4>No one has liked this post yet. Be the first!</h4>
			</c:if>
		</div>
	</div>

	<!-- toggle liking and unliking a post -->
	<!-- may remove the modal part and just submit to the form with the button click itself -->
	<div id="likePost" class="modal">
		<div class="modal-content">

			<form method="post" action="likePost">
				<c:if test="${liked}">
					<p>Are you sure you want to remove this post from your likes?</p>
				</c:if>
				<c:if test="${!liked}">
					<p>Would you like to add this post to likes?</p>
				</c:if>

				<input type="hidden" name="postID" value="${post.postID}"> <input
					type="hidden" name="liked" value="${liked}">
				<div class="modal-footer">
					<button class="submit">Yes</button>
				</div>
			</form>
		</div>
	</div>

	<!-- edit post modal Structure -->
	<div id="editPost" class="modal">
		<div class="modal-content">
			<div style="text-align: center">
				<p>Fill in sections you'd like to update.</p>
			</div>
			<div class="row">
				<form class="col s12" method="post" action="updatePost">
					<input type="hidden" name="postId" value="${post.postID}" />
					<div class="row">
						<div class="input-field col s6">
							<i class="material-icons prefix">import_contacts</i> <input
								name="title" id="title" type="text" class="validate"> <label
								for="title">Title</label>
						</div>
						<div class="input-field col s6">
							<i class="material-icons prefix">link</i> <input name="link"
								id="link" type="text" class="validate"
								placeholder="a space will remove the current link"> <label
								for="link">Link</label>
						</div>
					</div>
					<div class="row">
						<div class="row">
							<div class="input-field col s6">
								<i class="material-icons prefix">fingerprint</i>
								<textarea name="newTags" id="newTags"
									class="materialize-textarea"></textarea>
								<label for="newTags">New Tags (separated by commas)</label>
							</div>
							<div class="input-field col s6">
								<i class="material-icons prefix">free_breakfast</i>
								<textarea name="description" id="description"
									class="materialize-textarea"></textarea>
								<label for="description">Description</label>
							</div>
						</div>
					</div>
					<div class="row">
						<p>Deselect tags you'd like to remove:</p>
						<ul style="list-style-type: none">
							<c:forEach var="tempTag" items="${post.tags}">
								<li
									style="float: left; display: inline; margin: 5px 7px 5px 7px"><input
									type="checkbox" name="${tempTag}" id="${tempTag}"
									checked="checked" /> <label for="${tempTag}">${tempTag}</label></li>
							</c:forEach>
						</ul>
					</div>
					<c:choose>
						<c:when test="${post.draft}">
							<div class="row">
								<div class="col s6 m6 center-align">
									<button class="btn waves-effect waves-light" name="submit"
										type="submit" value="Update">
										Update<i class="material-icons right">build</i>
									</button>
								</div>
								<div class="col s6 m6 center-align">
									<button class="btn waves-effect waves-light" name="submit"
										type="submit" value="postDraft">
										Post<i class="material-icons right">cloud_upload</i>
									</button>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="row">
								<div style="text-align: center">
									<button class="btn waves-effect waves-light" name="submit"
										type="submit" value="Update">
										Update<i class="material-icons right">cloud_upload</i>
									</button>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
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
		})(jQuery); </script>
	<script>var first= $.noConflict(true);</script>

	<script>
		 $(document).ready(function(){
			  $('.collapsible').collapsible({
				    accordion: false, // A setting that changes the collapsible behavior to expandable instead of the default accordion style
				  });
	</script>
	<script>var second = $.noConflict(true);</script>
</body>
</html>