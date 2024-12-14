// cart.java
package Models;

public class Cart {
    int UserID;
    int ItemID;
    int Quantity;
    String ItemName;
    Double ItemPrice;
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
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
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		this.ItemName = itemName;
	}
	public Double getItemPrice() {
		return ItemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.ItemPrice = itemPrice;
	}
    public Cart(int userID, int itemID, int quantity, String itemName, Double itemPrice) {
        UserID = userID;
        ItemID = itemID;
        Quantity = quantity;
        ItemName = itemName;
        ItemPrice = itemPrice;
    }
}
