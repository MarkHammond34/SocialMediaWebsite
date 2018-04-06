package edu.ben.DAOs;

import edu.ben.connection.ConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TagDAO {

	/**
	 * Inserts into TAG with the tag and similar tag that are passed in. If record
	 * already exists, it adds on to count and updates the last incremented time
	 * stamp to current time
	 * 
	 * @param tag
	 *            tag being inserted
	 * @param similarTag
	 *            similar tag being inserted
	 * @return results an integer representing how many rows were affected (should
	 *         be 1)
	 */
	public static int insertIntoTag(String tag, String similarTag) {

		int results = -1;

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "INSERT INTO tag (tag, similar_tag, count) VALUES "
					+ "(?, ?, 1) ON DUPLICATE KEY UPDATE count=count+1, last_incremented=current_timestamp(); ";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, tag);
			ps.setString(2, similarTag);
			results = ps.executeUpdate();

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

		return results;
	}

	/**
	 * Inserts into POST_TAG with the postID and tag that are passed in.
	 * 
	 * @param postID
	 *            id of post.
	 * @param tag
	 *            tag being inserted
	 * @return results an integer representing how many rows were affected (should
	 *         be 1)
	 */
	public static int insertIntoPostTag(int postID, String tag) {

		int results = -1;

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "INSERT INTO post_tag (postID, tag) VALUES (?, ?)";
			
			System.out.println(postID + " " + tag);

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, postID);
			ps.setString(2, tag);
			results = ps.executeUpdate();

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

		return results;
	}

	/**
	 * Gets all the tags from a post.
	 * 
	 * @param postID
	 *            id of post.
	 * @return tags a list of tags for the desired post.
	 */
	public static ArrayList<String> getTagsByPostID(int postID) {

		ArrayList<String> tags = new ArrayList<String>();

		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "SELECT tag FROM post_tag WHERE postID=?;";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, postID);

			rs = ps.executeQuery();

			while (rs.next()) {
				tags.add(rs.getString("tag"));
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

		return tags;
	}

	/**
	 * Gets an list of string representing similar tags to the tag that is passed
	 * in.
	 * 
	 * @param tag
	 *            tag being used to find similar tags
	 * @return tags list of similar tags
	 */
	public static ArrayList<String> findSimilarTags(String tag) {

		ArrayList<String> tags = new ArrayList<String>();

		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "SELECT similar_tag FROM tag WHERE tag=?;";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, tag);

			rs = ps.executeQuery();

			while (rs.next()) {
				tags.add(rs.getString("similar_tag"));
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

		return tags;
	}

	/**
	 * Inserts tags into TAG and POST_TAG without repeating
	 * 
	 * @param postID
	 *            id of the post
	 * @param tags
	 *            list of string representing tags
	 */
	public static void insertTagList(int postID, ArrayList<String> tags) {
		String tag, similarTag;
		for (int i = 0; i < tags.size(); i++) {
			tag = tags.get(i);
			insertIntoPostTag(postID, tag);
			for (int j = i + 1; j < tags.size(); j++) {
				similarTag = tags.get(j);
				insertIntoTag(tag, similarTag);
			}
		}
	}

	public static void recycleTagTable() {

	}

	public static ArrayList<String> getTagsByUserID(int userID) {

		ArrayList<String> tags = new ArrayList<String>();

		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "SELECT tag FROM user_tag WHERE userID='" + userID + "';";

			PreparedStatement ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String temp = rs.getString("tag");
				tags.add(temp);
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

		return tags;
	}

	public static int insertIntoUserTag(int userID, String tag) {

		int results = -1;

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "INSERT INTO user_tag (userID, tag) VALUES (?, ?) ON DUPLICATE KEY UPDATE userID=?, tag=?; ";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userID);
			ps.setString(2, tag);
			ps.setInt(3, userID);
			ps.setString(4, tag);
			results = ps.executeUpdate();

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

		return results;
	}

	public static void insertUserTagList(int userID, ArrayList<String> tags) {

		String tag, similarTag;

		for (int i = 0; i < tags.size() - 1; i++) {
			tag = tags.get(i);
			insertIntoUserTag(userID, tag);

			for (int j = i + 1; j < tags.size(); j++) {
				similarTag = tags.get(j);
				insertIntoTag(tag, similarTag);

				if (j == tags.size() - 1 && i == tags.size() - 2) {
					insertIntoUserTag(userID, similarTag);
				}
			}
		}
	}

	public static int deleteFromUserTag(int userID) {

		int results = -1;

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "delete from user_tag where userID=" + userID + ";";

			PreparedStatement ps = con.prepareStatement(sql);
			results = ps.executeUpdate();

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

		return results;
	}
	
	public static int deleteFromPostTag(int postID) {

		int results = -1;

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String sql = "delete from post_tag where postID=" + postID + ";";

			PreparedStatement ps = con.prepareStatement(sql);
			results = ps.executeUpdate();

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

		return results;
	}
}