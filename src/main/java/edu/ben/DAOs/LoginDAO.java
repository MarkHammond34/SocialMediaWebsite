package edu.ben.DAOs;

import edu.ben.connection.ConnectionPool;
import edu.ben.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class LoginDAO {

	public static User login(String username) {
		// select from db, return info
		// return null if not exist in db
		// exists(username) // do *select

		DataSource ds;
		Connection con = null;
		ResultSet rs = null;
		User u = null;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String select = "SELECT * FROM HONEYCOMB.USER " + "WHERE USERNAME = ? AND ACTIVE = ?;";

			// System.out.println("wrote the sql");

			PreparedStatement ps = con.prepareStatement(select);
			ps.setString(1, username);
			ps.setBoolean(2, true);

			// System.out.println("added sql params");

			rs = ps.executeQuery();
			// System.out.println("execute queryr");

			while (rs.next()) {
				u = new User();
				// System.out.println("rs dot next");
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setUserID(rs.getInt("userID"));
				// get the object from the rs;

				// u.setTags(TagDAO.getTagsByUserID(u.getUserID()));

				return u;
			}
		} catch (Exception e) {
			System.out.println("errorrrrr");
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
				System.out.println("erroroor pt2");
				e.printStackTrace();
			}
		}

		return u; // probably null at this point
	}

	public static boolean exists(String user) {

		int results = 0;
		DataSource ds;
		Connection con = null;
		ResultSet rs = null;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String select = "SELECT * FROM HONEYCOMB.USER WHERE USERNAME = ? OR EMAIL = ?;";

			PreparedStatement ps = con.prepareStatement(select);
			ps.setString(1, user);
			ps.setString(2, user);
			rs = ps.executeQuery();

			while (rs.next()) {
				results++;
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

		// atleast one instance of this username found in db
		return results > 0;
	}

	public void delete(String username) {
		if (exists(username)) {
			String delete = "DELETE FROM USER WHERE USERNAME = ?";

			DataSource ds;
			Connection con = null;
			ResultSet rs = null;
			User u = null;

			try {
				ds = ConnectionPool.setUpPool();
				con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(delete);

				ps.setString(1, username);
				ps.executeUpdate();

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

		}
	}

	public static User register(String firstName, String lastName, String username, String email, String pwd) {
		// insert into db

		if (exists(username) || exists(email)) {
			// do not insert if it alraedy exists in db
			return null;
		} else {

			DataSource ds;
			Connection con = null;
			ResultSet rs = null;
			User u = null;

			try {
				ds = ConnectionPool.setUpPool();
				con = ds.getConnection();

				String insert = "INSERT INTO HONEYCOMB.USER (first_name, last_name, username, email, password, "
						+ "phone_number, login_attempts, created_on, active) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(insert);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, username);
				ps.setString(4, email);
				ps.setString(5, pwd);

				ps.setString(6, "0");
				ps.setInt(7, 0);
				ps.setTimestamp(8, new Timestamp(new java.util.Date().getTime()));
				ps.setBoolean(9, true);
				// set all ps values

				ps.executeUpdate();
				u = new User();
				u.setFirstName(firstName);
				u.setLastName(lastName);
				u.setUsername(username);
				System.out.println("insertetd");
				u.setUserID(UserDAO.getUserByEmail(email).getUserID());

				return u;

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

			// return the user object you have just now!

			return u;
		}
	}

	public static void incrLoginAttempts(int userID) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "UPDATE user SET login_attempts=login_attempts+1 WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);
			ps.setInt(1, userID);
			ps.executeUpdate();

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

	}

	public static void resetLoginAttempts(int userID) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "UPDATE user SET login_attempts=0 WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);
			ps.setInt(1, userID);
			ps.executeUpdate();

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
	}

	public static int getLoginAttempts(int userID) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		int result = -1;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "SELECT login_attempts FROM user WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			rs.next();

			result = rs.getInt("login_attempts");

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

		return result;
	}

	public static void toggleAccount(int userID, boolean active) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "UPDATE user SET active=? WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);

			ps.setBoolean(1, active);
			ps.setInt(2, userID);
			ps.executeUpdate();

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
	}

	public static boolean isActive(int userID) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		int result = -1;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "SELECT active FROM user WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			rs.next();

			result = rs.getInt("active");

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

		return result == 1;
	}

	public static void setCode(int userID, String code) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "UPDATE user SET unlockcode=? WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);

			ps.setString(1, code);
			ps.setInt(2, userID);

			ps.executeUpdate();

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

	}

	public static String getCode(int userID) {
		DataSource ds = null;
		Connection con = null;
		ResultSet rs = null;

		String result = "s";

		try {
			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String update = "SELECT unlockcode FROM user WHERE userID=?";
			PreparedStatement ps = con.prepareStatement(update);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			rs.next();

			result = rs.getString("unlockcode");

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

		return result;
	}

}
