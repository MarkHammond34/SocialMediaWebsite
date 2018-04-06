package edu.ben.models;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Post implements Comparable<Post> {

	private String title;
	private String description;
	private String link;
	private String imagePath;

	private String userFistName;
	private String userLastName;
	private String userUsername;

	private int postID;
	private int userID;

	private User user;

	private boolean active;
	private boolean draft;

	private int likeCount;
	private int viewCount;
	private int tackCount;

	private Timestamp createdOn;

	private ArrayList<String> tags;
	private ArrayList<Comment> comments;

	private int trendingScore;

	public Post() {
	}

	public Post(int postID, int userID, String title, String desc, String link, String imagePath, boolean active,
			boolean draft, int likeCount, int viewCount, int tackCount, Timestamp createdOn) {

		this.postID = postID;
		this.userID = userID;
		this.title = title;
		this.description = desc;
		this.link = link;
		this.imagePath = imagePath;
		this.active = active;
		this.draft = draft;
		this.likeCount = likeCount;
		this.viewCount = viewCount;
		this.tackCount = tackCount;
		this.createdOn = createdOn;
		this.trendingScore = -1;

		tags = new ArrayList<String>();

	}

	public Post(String title, String desc, String link, String imagePath) {

		this.postID = -1;
		this.userID = -1;
		this.title = title;
		this.description = desc;
		this.link = link;
		this.imagePath = imagePath;
		this.active = true;
		this.draft = false;
		this.likeCount = -1;
		this.viewCount = -1;
		this.tackCount = -1;
		this.trendingScore = -1;

		tags = new ArrayList<String>();

	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public int getPostID() {
		return postID;
	}

	public int getUserID() {
		return userID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public void setTackCount(int tackCount) {
		this.tackCount = tackCount;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public int getTackCount() {
		return tackCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public int getCommentCount() {
		return -1;
	}

	public String getUserFistName() {
		return userFistName;
	}

	public void setUserFistName(String userFistName) {
		this.userFistName = userFistName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public void setTrendingScore(int trendingScore) {
		this.trendingScore = trendingScore;
	}

	public int getTrendingScore() {
		return trendingScore;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int compareTo(Post comparePost) {

		return comparePost.trendingScore - trendingScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (draft ? 1231 : 1237);
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + likeCount;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + postID;
		result = prime * result + tackCount;
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + trendingScore;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userFistName == null) ? 0 : userFistName.hashCode());
		result = prime * result + userID;
		result = prime * result + ((userLastName == null) ? 0 : userLastName.hashCode());
		result = prime * result + ((userUsername == null) ? 0 : userUsername.hashCode());
		result = prime * result + viewCount;
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
		Post other = (Post) obj;
		if (active != other.active)
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (draft != other.draft)
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (likeCount != other.likeCount)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (postID != other.postID)
			return false;
		if (tackCount != other.tackCount)
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (trendingScore != other.trendingScore)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userFistName == null) {
			if (other.userFistName != null)
				return false;
		} else if (!userFistName.equals(other.userFistName))
			return false;
		if (userID != other.userID)
			return false;
		if (userLastName == null) {
			if (other.userLastName != null)
				return false;
		} else if (!userLastName.equals(other.userLastName))
			return false;
		if (userUsername == null) {
			if (other.userUsername != null)
				return false;
		} else if (!userUsername.equals(other.userUsername))
			return false;
		if (viewCount != other.viewCount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [title=" + title + ", description=" + description + ", link=" + link + ", imagePath=" + imagePath
				+ ", userFistName=" + userFistName + ", userLastName=" + userLastName + ", userUsername=" + userUsername
				+ ", postID=" + postID + ", userID=" + userID + ", user=" + user + ", active=" + active + ", draft="
				+ draft + ", likeCount=" + likeCount + ", viewCount=" + viewCount + ", tackCount=" + tackCount
				+ ", createdOn=" + createdOn + ", tags=" + tags + ", comments=" + comments + ", trendingScore="
				+ trendingScore + "]";
	}

}
