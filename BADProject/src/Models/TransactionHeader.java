package Models;

import java.util.Date;

public class TransactionHeader {
	int TransactionID;
	int UserID;
	Date DateCrated;
	String Status;
	public int getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public Date getDateCrated() {
		return DateCrated;
	}
	public void setDateCrated(Date dateCrated) {
		DateCrated = dateCrated;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public TransactionHeader(int transactionID, int userID, Date dateCrated, String status) {
		super();
		TransactionID = transactionID;
		UserID = userID;
		DateCrated = dateCrated;
		Status = status;
	}
}
