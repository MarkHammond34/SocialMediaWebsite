package edu.ben.DAOs;

import edu.ben.connection.ConnectionPool;
import edu.ben.models.Post;
import edu.ben.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

	public static void update(User user) {
		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE user SET " + "first_name = ?, " + "last_name = ?, " + "username = ?, "
					+ "password = ?, " + "bio = ? " + "WHERE userID = ?";

			PreparedStatement ps;

			ps = con.prepareStatement(query);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getBio());
			ps.setInt(6, user.getUserID());
			ps.executeUpdate();
			ps.close();

			TagDAO.deleteFromUserTag(user.getUserID());
			TagDAO.insertUserTagList(user.getUserID(), user.getTags());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static User getUserByUserId(int id) {
		User user = new User();

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM user WHERE userID='" + id + "'";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				user.setUserID(rs.getInt("userID"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setImagePath(rs.getString("profile_image_path"));
				user.setBio(rs.getString("bio"));
			}

			user.setTags(TagDAO.getTagsByUserID(user.getUserID()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public static User getUserByEmail(String email) {
		User user = new User();

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM user WHERE email='" + email + "'";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				user.setUserID(rs.getInt("userID"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setImagePath(rs.getString("profile_image_path"));
				user.setBio(rs.getString("bio"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public static ArrayList<Post> getLikesByUserId(int id) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM user_like ul, post p WHERE ul.postID = p.postID and ul.userID='" + id + "'";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {

				Post post = new Post();

				post.setTitle(rs.getString("title"));
				post.setDescription(rs.getString("description"));
				post.setLink(rs.getString("link"));
				post.setImagePath(rs.getString("image_path"));
				post.setPostID(rs.getInt("postID"));
				post.setActive(rs.getBoolean("active"));
				post.setDraft(rs.getBoolean("draft"));
				post.setLikeCount(rs.getInt("like_count"));
				post.setViewCount(rs.getInt("view_count"));
				post.setTackCount(rs.getInt("tack_count"));
				post.setCreatedOn(rs.getTimestamp("created_on"));

				int userId = rs.getInt("userID");
				post.setUserID(userId);
				post.setUser(getUserByUserId(userId));

				posts.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return posts;
	}
	
	public static ArrayList<Post> getDraftsByUserId(int id) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM post WHERE userID='" + id + "' and draft=1";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {

				Post post = new Post();

				post.setTitle(rs.getString("title"));
				post.setDescription(rs.getString("description"));
				post.setLink(rs.getString("link"));
				post.setImagePath(rs.getString("image_path"));
				post.setPostID(rs.getInt("postID"));
				post.setActive(rs.getBoolean("active"));
				post.setDraft(rs.getBoolean("draft"));
				post.setLikeCount(rs.getInt("like_count"));
				post.setViewCount(rs.getInt("view_count"));
				post.setTackCount(rs.getInt("tack_count"));
				post.setCreatedOn(rs.getTimestamp("created_on"));

				int userId = rs.getInt("userID");
				post.setUserID(userId);
				post.setUser(getUserByUserId(userId));

				posts.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return posts;
	}

	public static ArrayList<Post> getPostsByUserId(int id) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM post WHERE userID='" + id + "' and draft=0";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {

				Post post = new Post();

				post.setTitle(rs.getString("title"));
				post.setDescription(rs.getString("description"));
				post.setLink(rs.getString("link"));
				post.setImagePath(rs.getString("image_path"));
				post.setPostID(rs.getInt("postID"));
				post.setActive(rs.getBoolean("active"));
				post.setDraft(rs.getBoolean("draft"));
				post.setLikeCount(rs.getInt("like_count"));
				post.setViewCount(rs.getInt("view_count"));
				post.setTackCount(rs.getInt("tack_count"));
				post.setCreatedOn(rs.getTimestamp("created_on"));

				int userId = rs.getInt("userID");
				post.setUserID(userId);
				post.setUser(getUserByUserId(userId));

				posts.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return posts;
	}

	public static ArrayList<Post> getTacksByUserId(int id) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM user_tack ut, post p WHERE ut.postID = p.postID and ut.userID='" + id + "'";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {

				Post post = new Post();

				post.setTitle(rs.getString("title"));
				post.setDescription(rs.getString("description"));
				post.setLink(rs.getString("link"));
				post.setImagePath(rs.getString("image_path"));
				post.setPostID(rs.getInt("postID"));
				post.setActive(rs.getBoolean("active"));
				post.setDraft(rs.getBoolean("draft"));
				post.setLikeCount(rs.getInt("like_count"));
				post.setViewCount(rs.getInt("view_count"));
				post.setTackCount(rs.getInt("tack_count"));
				post.setCreatedOn(rs.getTimestamp("created_on"));

				int userId = rs.getInt("userID");
				post.setUserID(userId);
				post.setUser(getUserByUserId(userId));

				posts.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return posts;
	}

	public static ArrayList<User> getUsersMatchingSearch(String search) {

		ArrayList<User> users = new ArrayList<>(100);

		String sql = "SELECT * FROM user WHERE username LIKE '%" + search + "%' OR first_name LIKE '%" + search
				+ "%' OR last_name LIKE '%" + search + "%';";

		Connection con = null;
		ResultSet rs = null;

		try {
			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();

				user.setUserID(rs.getInt("userID"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setImagePath(rs.getString("profile_image_path"));
				user.setBio(rs.getString("bio"));

				users.add(user);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return users;

	}

	public static void followUser(int userID, int followedUserID) {
		String sql = "INSERT INTO user_follows (userID, followed_userID) values (" + userID + ", " + followedUserID
				+ ") ON DUPLICATE KEY UPDATE userID = " + userID + ", followed_userID = " + followedUserID + ";";

		System.out.println(sql);

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void unfollowUser(int userID, int followedUserID) {

		String sql = "DELETE FROM user_follows WHERE userID = " + userID + " AND followed_userID = " + followedUserID
				+ ";";

		System.out.println(sql);

		Connection con = null;
		ResultSet rs = null;

		try {
			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);

			ps.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static ArrayList<User> getFollowedUsersByUserID(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String sql = "SELECT * FROM user_follows uf WHERE uf.userId = " + userID + ";";

		System.out.println(sql);

		Connection con = null;
		ResultSet rs = null;

		try {
			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				int followedID = rs.getInt("followed_userID");
				User user = getUserByUserId(followedID);
				users.add(user);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return users;
	}

	public static ArrayList<User> getUsersFollowingUserID(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String sql = "SELECT * FROM user_follows uf WHERE uf.followed_userID = " + userID + ";";

		System.out.println(sql);

		Connection con = null;
		ResultSet rs = null;

		try {
			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				int followedID = rs.getInt("userID");
				User user = getUserByUserId(followedID);
				users.add(user);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return users;
	}

}
