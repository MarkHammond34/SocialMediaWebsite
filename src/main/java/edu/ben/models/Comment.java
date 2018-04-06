package edu.ben.models;

public class Comment {

	private int commentId;
	private int userID;
	private String userFirstName;
	private String userLastName;
	private int postId;
	private String message;
	private int likeCount;
	private String username;

	public Comment() {
	}

	public Comment(int commentId, int userID, String userFirstName, String userLastName, int postId, String message,
			int likeCount) {
		this.commentId = commentId;
		this.userID = userID;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.postId = postId;
		this.message = message;
		this.likeCount = likeCount;
	}

	public void setUsername(String u) {
		this.username = u;
	}

	public String getUsername() {
		return username;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commentId;
		result = prime * result + likeCount;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + postId;
		result = prime * result + ((userFirstName == null) ? 0 : userFirstName.hashCode());
		result = prime * result + userID;
		result = prime * result + ((userLastName == null) ? 0 : userLastName.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (commentId != other.commentId)
			return false;
		if (likeCount != other.likeCount)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (postId != other.postId)
			return false;
		if (userFirstName == null) {
			if (other.userFirstName != null)
				return false;
		} else if (!userFirstName.equals(other.userFirstName))
			return false;
		if (userID != other.userID)
			return false;
		if (userLastName == null) {
			if (other.userLastName != null)
				return false;
		} else if (!userLastName.equals(other.userLastName))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userID=" + userID + ", userFirstName=" + userFirstName
				+ ", userLastName=" + userLastName + ", postId=" + postId + ", message=" + message + ", likeCount="
				+ likeCount + ", username=" + username + "]";
	}

}
