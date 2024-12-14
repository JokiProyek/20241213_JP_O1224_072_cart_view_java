// TranasactionDetail.java
package Models;

public class TransactionDetail {
	int TransactionID;
	int ItemID;
	int Quantity;
	public TransactionDetail(int transactionID, int itemID, int quantity) {
		super();
		TransactionID = transactionID;
		ItemID = itemID;
		Quantity = quantity;
	}
	public int getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}
	public int getItemID() {
		return ItemID;
	}
	public void setItemID(int itemID) {
		ItemID = itemID;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
}
