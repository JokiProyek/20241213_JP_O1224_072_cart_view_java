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
    private final String PASSWORD = "root";  // sesuai MYSQL_ROOT_PASSWORD di docker
    private final String DATABASE = "gogoquery";
    private final String HOST = "127.17.0.1:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s?allowPublicKeyRetrieval=true&useSSL=false", HOST, DATABASE);

	public ResultSet rs;
	public ResultSetMetaData rsm;
	private Connection con;
	private Statement st;
	private static Connect connect;
	
	public static Connect getInstance() {
		if(connect==null)  return new Connect();
		return connect;
	}
	
	private Connect() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(CONNECTION,USERNAME,PASSWORD);
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
			con.setAutoCommit(false); // Use 'con' instead of 'connection'
	
			// Insert transaction header
			String headerQuery = "INSERT INTO transaction_header (UserID, Status) VALUES (?, 'Pending')";
			PreparedStatement headerStmt = con.prepareStatement(headerQuery, Statement.RETURN_GENERATED_KEYS); // Use 'con'
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
			PreparedStatement detailStmt = con.prepareStatement(detailQuery); // Use 'con'
			
			for (Cart item : cartItems) {
				detailStmt.setInt(1, transactionID);
				detailStmt.setInt(2, item.getItemID());
				detailStmt.setInt(3, item.getQuantity());
				detailStmt.executeUpdate();
	
				// Update item stock
				String updateStockQuery = "UPDATE msitem SET ItemStock = ItemStock - ? WHERE ItemID = ?";
				PreparedStatement stockStmt = con.prepareStatement(updateStockQuery); // Use 'con'
				stockStmt.setInt(1, item.getQuantity());
				stockStmt.setInt(2, item.getItemID());
				stockStmt.executeUpdate();
			}
	
			// Insert into queue manager
			String queueQuery = "INSERT INTO queue_manager (TransactionID, Status) VALUES (?, 'Pending')";
			PreparedStatement queueStmt = con.prepareStatement(queueQuery); // Use 'con'
			queueStmt.setInt(1, transactionID);
			queueStmt.executeUpdate();
	
			con.commit(); // Use 'con'
			return true;
		} catch (SQLException e) {
			try {
				con.rollback(); // Use 'con'
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				con.setAutoCommit(true); // Use 'con'
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
public List<Map<String, Object>> getQueueManagerTransactions() {
	List<Map<String, Object>> transactions = new ArrayList<>();
	try {
		String query = "SELECT qm.QueueID, qm.Status, th.TransactionID, u.UserEmail, " +
					   "SUM(td.Quantity * mi.ItemPrice) as TotalPrice " +
					   "FROM queue_manager qm " +
					   "JOIN transaction_header th ON qm.TransactionID = th.TransactionID " +
					   "JOIN msuser u ON th.UserID = u.UserID " +
					   "JOIN transaction_detail td ON th.TransactionID = td.TransactionID " +
					   "JOIN msitem mi ON td.ItemID = mi.ItemID " +
					   "GROUP BY qm.QueueID, qm.Status, th.TransactionID, u.UserEmail";
		
		execQuery(query);
		
		while (rs.next()) {
			Map<String, Object> transaction = new HashMap<>();
			transaction.put("QueueID", rs.getInt("QueueID"));
			transaction.put("Status", rs.getString("Status"));
			transaction.put("TransactionID", rs.getInt("TransactionID"));
			transaction.put("UserEmail", rs.getString("UserEmail"));
			transaction.put("TotalPrice", rs.getDouble("TotalPrice"));
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
}
