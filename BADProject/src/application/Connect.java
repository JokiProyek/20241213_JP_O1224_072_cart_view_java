// connect.java
package application;

import Models.Cart;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "root"; // sesuai MYSQL_ROOT_PASSWORD di docker
	private final String DATABASE = "gogoquery";
	private final String HOST = "127.17.0.1:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s?allowPublicKeyRetrieval=true&useSSL=false",
			HOST, DATABASE);

	public ResultSet rs;
	public ResultSetMetaData rsm;
	private Connection con;
	private Statement st;
	private static Connect connect;

	public static Connect getInstance() {
		if (connect == null)
			return new Connect();
		return connect;
	}

	private Connect() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public Statement createStatement() throws SQLException {
		return con.createStatement();
	}

	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
			rs = null; // Set rs to null if there's an error
		}
		return rs;
	}

	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {

		}

		return ps;
	}

	public boolean createTransaction(int userID, List<Cart> cartItems) {
		try {
			con.setAutoCommit(false);

			// Insert transaction header with 'In Queue' status
			String headerQuery = "INSERT INTO transaction_header (UserID, Status) VALUES (?, 'In Queue')";
			PreparedStatement headerStmt = con.prepareStatement(headerQuery, Statement.RETURN_GENERATED_KEYS);
			headerStmt.setInt(1, userID);
			headerStmt.executeUpdate();

			// Get generated transaction ID
			ResultSet rs = headerStmt.getGeneratedKeys();
			int transactionID = -1;
			if (rs.next()) {
				transactionID = rs.getInt(1);
			}

			// Insert transaction details
			String detailQuery = "INSERT INTO transaction_detail (TransactionID, ItemID, Quantity) VALUES (?, ?, ?)";
			PreparedStatement detailStmt = con.prepareStatement(detailQuery);

			// Update stock and insert details for each item
			for (Cart item : cartItems) {
				// Insert transaction detail
				detailStmt.setInt(1, transactionID);
				detailStmt.setInt(2, item.getItemID());
				detailStmt.setInt(3, item.getQuantity());
				detailStmt.executeUpdate();

				// Update item stock
				String updateStockQuery = "UPDATE msitem SET ItemStock = ItemStock - ? WHERE ItemID = ?";
				PreparedStatement stockStmt = con.prepareStatement(updateStockQuery);
				stockStmt.setInt(1, item.getQuantity());
				stockStmt.setInt(2, item.getItemID());
				stockStmt.executeUpdate();
			}

			// Clear the cart after successful transaction
			String clearCartQuery = "DELETE FROM mscart WHERE UserID = ?";
			PreparedStatement clearCartStmt = con.prepareStatement(clearCartQuery);
			clearCartStmt.setInt(1, userID);
			clearCartStmt.executeUpdate();

			con.commit();
			return true;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Map<String, Object>> getQueueManagerTransactions() {
		List<Map<String, Object>> transactions = new ArrayList<>();
		String query = "SELECT " +
				"th.TransactionID, " +
				"th.UserID as CustomerID, " +
				"u.UserEmail as CustomerEmail, " +
				"DATE_FORMAT(th.TransactionDate, '%Y-%m-%d %H:%i:%s') as Date, " +
				"(SELECT SUM(td.Quantity * i.ItemPrice) " +
				"FROM transaction_detail td " +
				"JOIN msitem i ON td.ItemID = i.ItemID " +
				"WHERE td.TransactionID = th.TransactionID) as Total, " +
				"th.Status " +
				"FROM transaction_header th " +
				"JOIN msuser u ON th.UserID = u.UserID " +
				"ORDER BY th.TransactionDate DESC";

		try {
			ResultSet rs = execQuery(query);
			while (rs.next()) {
				Map<String, Object> transaction = new HashMap<>();
				transaction.put("TransactionID", rs.getInt("TransactionID"));
				transaction.put("CustomerID", rs.getInt("CustomerID"));
				transaction.put("CustomerEmail", rs.getString("CustomerEmail"));
				transaction.put("Date", rs.getString("Date"));
				transaction.put("Total", rs.getDouble("Total"));
				transaction.put("Status", rs.getString("Status"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public boolean updateQueueStatus(int queueID, String status) {
		try {
			String query = "UPDATE queue_manager SET Status = ? WHERE QueueID = ?";
			PreparedStatement stmt = con.prepareStatement(query); // Change 'connection' to 'con'
			stmt.setString(1, status);
			stmt.setInt(2, queueID);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateTransactionStatus(int transactionID, String status) {
		try {
			String query = "UPDATE transaction_header SET Status = ? WHERE TransactionID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, status);
			stmt.setInt(2, transactionID);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
