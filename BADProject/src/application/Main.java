// main.java
package application;

import Models.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.Cursor;

import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
// Hapus import javax.swing
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import java.util.stream.Collectors;

public class Main extends Application {
    static Stage primaryStage;
    private TableView<Map<String, Object>> queueTable; // Corrected line
    private Connection connection;
    BorderPane borderlogoregis; // Declare this variable
    private Image logoImage;
    private ListView<BorderPane> cartItems;
    // Login form
    FlowPane border1;
    BorderPane border2;
    BorderPane border3;
    Scene loginScene;
    GridPane gridLogin;
    FlowPane flowRegister;
    Label login;
    Label emaill;
    TextField emailtf;
    Label passwordl;
    PasswordField passwordf;
    Label rgstr;
    Label here;
    Button loginbtn;

    Image logoimage;
    ImageView logoview;

    Rectangle line;

    LinearGradient backgroundcolorlogin;

    // Register form
    FlowPane registerPane;
    BorderPane registerBorder;
    GridPane registerGrid;
    Scene registerScene;
    Label registerLabel;
    Label emaillr;
    TextField emailtfr;
    Label passwordlr;
    PasswordField passwordfr;
    Label confirmpassl;
    PasswordField confirmpassfr;
    Label dob;
    DatePicker dobpick;
    Label gender;
    RadioButton male, female;
    CheckBox termsbox;
    Label conditions;
    FlowPane termsncondiregist;

    ToggleGroup genderGroup;

    Button registerBtn;
    Label haveaccount;
    Label signin;
    Label hereregist;
    FlowPane haveaccregist;

    Image logoimageregist;
    ImageView logoviewregist;

    Rectangle lineregist;

    ArrayList<MsUser> userlist = new ArrayList<>();
    MsUser loginuser;

    // connect sql
    private Connect connect = Connect.getInstance();

    // select role page
    ArrayList<Product> productlist = new ArrayList<>();
    Scene rolepagescene;
    BorderPane backgroundpage;
    BorderPane bordermanager;
    BorderPane bordershopper;
    VBox boxmanager;
    VBox boxshopper;
    HBox hboxrolepage;
    Label managerl;
    Label shopperl;
    Label managerdesc;
    Label shopperdesc;
    Rectangle linemanager;
    Rectangle lineshopper;
    Button managerbutton;
    Button shopperbutton;
    Image logoimagerole;
    ImageView logoviewrole;
    Image logoM;
    Image logoS;
    ImageView logoMview;
    ImageView logoSview;

    // searchbar
    Scene searchscene;
    BorderPane backgroundshopperpage;
    BorderPane bordersearchbar;
    TextField searchbarf;
    Button searchbtn;
    Button mycartbtn;
    Button logoutbtn;
    Image logoshopper;
    ImageView logoviewshopper;
    HBox searchbarbox;
    Rectangle verticalline;

    // shopperhomepage
    BorderPane categorypane;
    BorderPane bottompane;
    GridPane gridhomepage;
    GridPane filterpane;
    Label welcomemessage;
    ListView<BorderPane> productview;
    Button applybtn;
    ComboBox<String> categorybox;
    Label filterl;
    Label showingprod;
    Label categoryl;
    Label usernamel;
    HBox welcomebox;
    VBox leftpane;
    HBox rightpane;
    HBox centercontent;
    HBox combinedLayout;
    VBox filterLayout;
    Label filterText;
    VBox showingLayout;
    VBox mainLayout;
    Label textshowing;
    Label totalproduct;
    Label textproduct;
    HBox showingBox;

    // detailpage
    BorderPane backgrounddetailpage;
    Scene detailScene;
    VBox detailBox;
    Rectangle photodetail;
    Rectangle linedetail;
    HBox alldetailprod;

    BorderPane bestsellertextpane;
    Label bestsellertext;
    BorderPane setitemdetail;
    Label setitemtext;

    // cartpage
    BorderPane backgroundinitcartpage;
    Scene cartScene;
    Label carttext;
    ArrayList<Cart> allcartlist = new ArrayList<Cart>();
    ArrayList<Cart> custcartlist = new ArrayList<Cart>();

    // billing summary border
    Label billingtext;
    Label totaltext;

    // manager homepage
    BorderPane menuborder;
    Scene managerscene;
    Menu menu;
    MenuBar menubar;
    MenuItem additemmenu;
    MenuItem managequeue;
    MenuItem logout;
    Label welcomelabel;

    // add item
    Label additemt, itemnamet, itemdesct, itemcategoryt, itempricet, quantityt;
    TextField itemnametf, itemcategorytf, itempricetf;
    TextArea itemdescta;
    Spinner<Integer> qtyspin;
    Button additembtn;
    GridPane additemformlayout;

    // queue manager
    Label queuemanagert;
    TableView<String> tablepackage;
    Button sendpackbtn;

    private Cart findInCart(int itemID) {
        for (Cart item : allcartlist) {
            if (item.getItemID() == itemID && item.getUserID() == loginuser.getUserID()) {
                return item;
            }
        }
        return null;
    }

    private ImageView createLogo() {
        ImageView logoView = null;
        try {
            Image logo = new Image("file:logo.png"); // Adjust the path as necessary
            logoView = new ImageView(logo);
            logoView.setFitHeight(50);
            logoView.setFitWidth(70);

            // Set mouse click event to navigate to home
            logoView.setOnMouseClicked(event -> {
                initshopperhomepage(); // Navigate to the shopper homepage
            });

        } catch (Exception e) {
            // Fallback to simple colored rectangle if image fails to load
            Rectangle fallback = new Rectangle(70, 50);
            fallback.setFill(Color.web("#2e2f47"));
            logoView = new ImageView(fallback.snapshot(null, null));
        }
        return logoView;
    }

    public void validateadditem(String itemName, String itemDesc, String itemCategory, String itemPrice, int quantity) {
        if (itemName.isEmpty() || itemDesc.isEmpty() || itemCategory.isEmpty() || itemPrice.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
            return;
        } else if (itemName.length() < 5 || itemName.length() > 70) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Insert Error");
            errorAlert.setContentText("Item name must be between 5 and 70 characters.");
            errorAlert.showAndWait();
        } else if (itemDesc.length() < 10 || itemDesc.length() > 255) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Insert Error");
            errorAlert.setContentText("Item description must be between 10 and 255 characters.");
            errorAlert.showAndWait();
        } else {
            try {
                double itemPriceValue = Double.parseDouble(itemPrice);

                if (itemPriceValue < 0.50 || itemPriceValue > 900000) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Insert Error");
                    errorAlert.setContentText("Item price must be between $0.50 and $900,000.");
                    errorAlert.showAndWait();
                } else if (quantity <= 0 || quantity > 300) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Insert Error");
                    errorAlert.setContentText("Quantity must be between 1 and 300.");
                    errorAlert.showAndWait();
                } else {
                    Alert successalert = new Alert(Alert.AlertType.INFORMATION);
                    successalert.setTitle("Information");
                    successalert.setHeaderText("Insert Success");
                    successalert.setContentText("Item added to product catalog");
                    successalert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Insert Error");
                errorAlert.setContentText("Item price must be a valid number.");
                errorAlert.showAndWait();
            }
        }
    }

    public void handleAddItemButton(TextField itemNameField, TextArea itemDescField, TextField itemCategoryField,
            TextField itemPriceField, Spinner<Integer> quantitySpinner) {
        String itemName = itemNameField.getText().trim();
        String itemDesc = itemDescField.getText().trim();
        String itemCategory = itemCategoryField.getText().trim();
        String itemPrice = itemPriceField.getText().trim();
        int quantity = quantitySpinner.getValue();

        validateadditem(itemName, itemDesc, itemCategory, itemPrice, quantity);
    }

    public void showAddItemPopup(Stage ownerStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(ownerStage);
        popupStage.setTitle("Add Item");
        popupStage.setWidth(850);
        popupStage.setHeight(600);

        VBox content = new VBox(20);
        content.setStyle("-fx-background-color: #D6EAF8; -fx-padding: 20;");
        content.setPadding(new Insets(20));

        // Form fields
        Label titleLabel = new Label("Add New Item");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        // Item Name
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("5-70 characters");
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        // Description
        Label descLabel = new Label("Description:");
        TextArea descArea = new TextArea();
        descArea.setPrefRowCount(3);
        descArea.setPromptText("10-255 characters");
        form.add(descLabel, 0, 1);
        form.add(descArea, 1, 1);

        // Category
        Label catLabel = new Label("Category:");
        TextField catField = new TextField();
        form.add(catLabel, 0, 2);
        form.add(catField, 1, 2);

        // Price
        Label priceLabel = new Label("Price ($):");
        TextField priceField = new TextField();
        priceField.setPromptText("0.50 - 900,000");
        form.add(priceLabel, 0, 3);
        form.add(priceField, 1, 3);

        // Quantity
        Label qtyLabel = new Label("Quantity:");
        Spinner<Integer> qtySpinner = new Spinner<>(1, 300, 1);
        form.add(qtyLabel, 0, 4);
        form.add(qtySpinner, 1, 4);

        // Buttons
        HBox buttons = new HBox(10);
        Button addButton = new Button("Add Item");
        Button cancelButton = new Button("Cancel");

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");

        buttons.getChildren().addAll(addButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        // Add all to content
        content.getChildren().addAll(titleLabel, form, buttons);

        // Event handlers
        addButton.setOnAction(e -> {
            if (validateItemInput(
                    nameField.getText(),
                    descArea.getText(),
                    catField.getText(),
                    priceField.getText(),
                    qtySpinner.getValue())) {
                // Insert into database
                String query = String.format(
                        "INSERT INTO msitem (ItemName, ItemDesc, ItemCategory, ItemPrice, ItemStock) VALUES ('%s', '%s', '%s', %.2f, %d)",
                        nameField.getText(),
                        descArea.getText(),
                        catField.getText(),
                        Double.parseDouble(priceField.getText()),
                        qtySpinner.getValue());
                connect.execUpdate(query);

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText("Item Added");
                success.setContentText("New item has been added successfully!");
                success.showAndWait();

                popupStage.close();
            }
        });

        cancelButton.setOnAction(e -> popupStage.close());

        Scene scene = new Scene(content);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    private boolean validateItemInput(String name, String desc, String category, String price, int quantity) {
        if (name.isEmpty() || desc.isEmpty() || category.isEmpty() || price.isEmpty()) {
            showError("All fields must be filled out.");
            return false;
        }

        if (name.length() < 5 || name.length() > 70) {
            showError("Item name must be between 5 and 70 characters.");
            return false;
        }

        if (desc.length() < 10 || desc.length() > 255) {
            showError("Item description must be between 10 and 255 characters.");
            return false;
        }

        try {
            double priceValue = Double.parseDouble(price);
            if (priceValue < 0.50 || priceValue > 900000) {
                showError("Price must be between $0.50 and $900,000.");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Invalid price format.");
            return false;
        }

        if (quantity <= 0 || quantity > 300) {
            showError("Quantity must be between 1 and 300.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initQueueManager() {
        // Create main stage
        Stage queueStage = new Stage();
        queueStage.initModality(Modality.APPLICATION_MODAL);
        queueStage.initOwner(primaryStage);
        queueStage.setTitle("Queue Manager");
        queueStage.setMinWidth(800);
        queueStage.setMinHeight(600);

        // Create main container
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #d1e7ff;");

        // Create title
        Label title = new Label("Queue Manager");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2e2f47;");

        // Initialize table
        TableView<Map<String, Object>> queueTable = new TableView<>();
        queueTable.setStyle("-fx-background-color: white;");

        // Create and configure columns
        TableColumn<Map<String, Object>, Integer> transactionIDCol = new TableColumn<>("Transaction ID");
        transactionIDCol.setCellValueFactory(
                data -> new SimpleIntegerProperty((Integer) data.getValue().get("TransactionID")).asObject());

        TableColumn<Map<String, Object>, Integer> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(
                data -> new SimpleIntegerProperty((Integer) data.getValue().get("CustomerID")).asObject());

        TableColumn<Map<String, Object>, String> emailCol = new TableColumn<>("Customer Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue().get("CustomerEmail")));

        TableColumn<Map<String, Object>, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue().get("Date")));

        TableColumn<Map<String, Object>, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(data -> {
            Object value = data.getValue().get("Total");
            if (value != null) {
                return new SimpleDoubleProperty((Double) value).asObject();
            }
            return new SimpleDoubleProperty(0.0).asObject();
        });
        totalCol.setCellFactory(col -> new TableCell<Map<String, Object>, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
            }
        });

        TableColumn<Map<String, Object>, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue().get("Status")));

        // Add columns to table
        queueTable.getColumns().addAll(
                transactionIDCol,
                customerIDCol,
                emailCol,
                dateCol,
                totalCol,
                statusCol);

        // Set column widths
        transactionIDCol.setPrefWidth(100);
        customerIDCol.setPrefWidth(100);
        emailCol.setPrefWidth(200);
        dateCol.setPrefWidth(150);
        totalCol.setPrefWidth(100);
        statusCol.setPrefWidth(100);

        // Create Send Package button
        Button sendPackageBtn = new Button("Send Package");
        sendPackageBtn.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10 20; " +
                        "-fx-font-size: 14px;");

        // Add button event handler
        sendPackageBtn.setOnAction(e -> {
            Map<String, Object> selectedItem = queueTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a transaction to process.");
                return;
            }

            String status = (String) selectedItem.get("Status");
            if (!"In Queue".equals(status)) {
                showAlert(Alert.AlertType.WARNING, "Already Processed", "This package has already been sent.");
                return;
            }

            // Confirm before sending
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Action");
            confirm.setHeaderText("Send Package");
            confirm.setContentText("Are you sure you want to send this package?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    int transactionId = (Integer) selectedItem.get("TransactionID");
                    boolean updated = connect.updateTransactionStatus(transactionId, "Sent");
                    if (updated) {
                        // Refresh table data
                        loadQueueData(queueTable);

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Package has been marked as sent!");
                        success.showAndWait();
                    }
                }
            });
        });

        // Layout
        VBox contentBox = new VBox(20);
        contentBox.getChildren().addAll(title, queueTable, sendPackageBtn);
        mainContainer.setCenter(contentBox);

        // Initial data load
        loadQueueData(queueTable);

        // Create scene and show
        Scene scene = new Scene(mainContainer);
        queueStage.setScene(scene);
        queueStage.show();
    }

    private void loadQueueData(TableView<Map<String, Object>> queueTable) {
        List<Map<String, Object>> transactions = connect.getQueueManagerTransactions();
        queueTable.getItems().clear();
        queueTable.getItems().addAll(transactions);

        // Add placeholder when no data
        if (transactions.isEmpty()) {
            Label placeholder = new Label("No transactions available");
            placeholder.setStyle("-fx-text-fill: gray;");
            queueTable.setPlaceholder(placeholder);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void refreshQueueTable() {
        try {
            List<Map<String, Object>> transactions = connect.getQueueManagerTransactions(); // Fetch transactions from
                                                                                            // DB
            Platform.runLater(() -> {
                queueTable.getItems().clear();
                if (transactions.isEmpty()) {
                    queueTable.setPlaceholder(new Label("No transactions in queue"));
                } else {
                    queueTable.getItems().addAll(transactions);
                }
            });
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load transactions");
                alert.setContentText("Could not retrieve queue data. Please try again.");
                alert.showAndWait();
            });
        }
    }

    public void addcomponentcartpage() {
        searchbarbox.getChildren().addAll(logoviewshopper, searchbarf, searchbtn, verticalline, mycartbtn, logoutbtn);
        bordersearchbar.setCenter(searchbarbox);

        String emailusername = emailtf.getText();
        String username = emailusername.split("@")[0];
        carttext = new Label("'s Cart ");
        carttext.setFont(Font.font("Arial", 70));
        carttext.setTextFill(Color.WHITE);

        usernamel = new Label(username);
        usernamel.setFont(Font.font("Arial", 70));
        usernamel.setTextFill(Color.ORANGE);

        welcomebox = new HBox(usernamel, carttext);
        welcomebox.setPadding(new Insets(20));
    }

    public void setlayoutcartpage() {
        backgroundinitcartpage.setTop(bordersearchbar);
        backgroundinitcartpage.setLeft(welcomebox);

        backgroundinitcartpage.setStyle("-fx-background-color: #1b1c2d;");

        bordersearchbar.setBackground(new Background(
                new BackgroundFill(Color.web("#2e2f47"), new CornerRadii(100), new Insets(10))));
        BorderPane.setMargin(bordersearchbar, new Insets(10));
        bordersearchbar.setPrefHeight(100);

        searchbarbox.setAlignment(Pos.CENTER);
        HBox.setMargin(logoviewshopper, new Insets(0, 0, 0, 5));
        HBox.setMargin(searchbarf, new Insets(0, 5, 0, 0));
        HBox.setMargin(searchbtn, new Insets(0, 0, 0, 0));
        HBox.setMargin(verticalline, new Insets(0, 5, 0, 5));
        HBox.setMargin(mycartbtn, new Insets(0, 10, 0, 0));
        HBox.setMargin(logoutbtn, new Insets(0, 0, 0, 10));

        logoviewshopper.setFitWidth(70);
        logoviewshopper.setFitHeight(50);

        searchbarf.setPrefWidth(500);

        searchbtn.setStyle("-fx-background-color: #1b1c2d;");
        searchbtn.setTextFill(Color.WHITE);

        mycartbtn.setStyle("-fx-background-color: #2e2f47;");
        mycartbtn.setTextFill(Color.WHITE);

        logoutbtn.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, new Insets(0))));
        logoutbtn.setTextFill(Color.WHITE);
    }

    public void filtercart() {
        for (int i = 0; i < custcartlist.size(); i++) {
            // Implement filtering logic here
        }
    }

    public void initdetailpage() {
        backgrounddetailpage = new BorderPane();
        detailScene = new Scene(backgrounddetailpage, 900, 700);

        // Top navigation
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #2e2f47;");

        ImageView logo = createLogo();
        Button backButton = new Button("Back to Shop");
        backButton.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: white;");
        Button logoutBtn = new Button("Log Out");
        logoutBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search products...");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            // Implement search logic here
        });

        topBar.getChildren().addAll(logo, backButton, logoutBtn, searchField, searchButton);
        backgrounddetailpage.setTop(topBar);
    }

    private void addToCart(Product product, int quantity) {
        // Check if item already exists in cart
        Cart existingItem = findInCart(product.getItemID());

        if (existingItem != null) {
            // Update existing item quantity
            int newQuantity = existingItem.getQuantity() + quantity;
            if (newQuantity > product.getItemStock()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Stock Error");
                alert.setContentText("Total quantity in cart cannot exceed available stock!");
                alert.showAndWait();
                return;
            }

            // Update in memory
            existingItem.setQuantity(newQuantity);

            // Update database
            String updateQuery = String.format(
                    "UPDATE mscart SET Quantity = %d WHERE UserID = %d AND ItemID = %d",
                    newQuantity,
                    loginuser.getUserID(),
                    product.getItemID());
            connect.execUpdate(updateQuery);

        } else {
            // Add new item to cart
            Cart cartItem = new Cart(
                    loginuser.getUserID(),
                    product.getItemID(),
                    quantity,
                    product.getItemName(),
                    product.getItemPrice());
            allcartlist.add(cartItem);

            // Insert to database
            String insertQuery = String.format(
                    "INSERT INTO mscart (UserID, ItemID, Quantity, ItemName, ItemPrice) VALUES (%d, %d, %d, '%s', %.2f)",
                    loginuser.getUserID(),
                    product.getItemID(),
                    quantity,
                    product.getItemName(),
                    product.getItemPrice());
            connect.execUpdate(insertQuery);
        }

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setHeaderText("Item Added");
        success.setContentText("Item added to cart successfully!");
        success.showAndWait();
    }

    private String getCurrentUsername() {
        String email = loginuser.getUserEmail();
        return email.split("@")[0]; // Get username part before @
    }

    private List<Cart> getUserCartItems() {
        return allcartlist.stream()
                .filter(cart -> cart.getUserID() == loginuser.getUserID())
                .collect(Collectors.toList());
    }

    private void showDetailPage(Product product) {
        // Create new scene
        BorderPane detailPage = new BorderPane();
        detailPage.setStyle("-fx-background-color: #1b1c2d;");

        // Top navigation
        HBox topNav = new HBox(20);
        topNav.setStyle("-fx-background-color: #2e2f47; -fx-padding: 10;");
        topNav.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = createLogo();
        Button backBtn = new Button("Back to Shop");
        backBtn.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: white;");
        Button logoutBtn = new Button("Log Out");
        logoutBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");

        topNav.getChildren().addAll(logo, backBtn, logoutBtn);

        // Product details
        VBox details = new VBox(20);
        details.setPadding(new Insets(30));

        // Product image placeholder
        Rectangle photo = new Rectangle(300, 300);
        photo.setFill(Color.GRAY);

        // Product info
        Label nameLabel = new Label(product.getItemName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label priceLabel = new Label("$" + String.format("%.2f", product.getItemPrice()));
        priceLabel.setStyle("-fx-text-fill: #ffa500; -fx-font-size: 40px; -fx-font-weight: bold;");

        // Category and specs
        Label categoryLabel = new Label("Category: " + product.getItemCategory());
        categoryLabel.setStyle("-fx-text-fill: white;");

        Label specTitle = new Label("Specification:");
        specTitle.setStyle("-fx-text-fill: #ffa500; -fx-font-size: 16px;");

        Label specText = new Label(product.getItemDesc());
        specText.setStyle("-fx-text-fill: white; -fx-wrap-text: true;");

        // Add to cart section
        VBox cartSection = new VBox(10);
        cartSection.setStyle("-fx-background-color: #2e2f47; -fx-padding: 20; -fx-background-radius: 10;");

        Label bestSeller = new Label("Best Seller!");
        bestSeller.setStyle(
                "-fx-background-color: #9370db;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 5 10;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;");

        // Quantity spinner
        HBox qtyBox = new HBox(10);
        Label qtyLabel = new Label("Set item quantity");
        qtyLabel.setStyle("-fx-text-fill: white;");

        Spinner<Integer> qtySpinner = new Spinner<>(1, product.getItemStock(), 1);

        Label stockLabel = new Label("Stock: " + product.getItemStock());
        stockLabel.setStyle("-fx-text-fill: #ffa500;");

        qtyBox.getChildren().addAll(qtyLabel, qtySpinner, stockLabel);

        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle(
                "-fx-background-color: #ffa500;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-pref-width: 200;");

        cartSection.getChildren().addAll(bestSeller, qtyBox, addToCartBtn);

        // Layout
        HBox contentBox = new HBox(30);
        contentBox.getChildren().addAll(photo, details, cartSection);

        details.getChildren().addAll(
                nameLabel,
                priceLabel,
                categoryLabel,
                specTitle,
                specText);

        detailPage.setTop(topNav);
        detailPage.setCenter(contentBox);

        // Event handlers
        backBtn.setOnAction(e -> initshopperhomepage());
        logoutBtn.setOnAction(e -> initLoginForm());

        addToCartBtn.setOnAction(e -> {
            addToCart(product, qtySpinner.getValue());
        });

        Scene scene = new Scene(detailPage, 900, 700);
        switchscene(scene);
    }

    public void addcomponentdetailpage() {
        searchbarbox.getChildren().addAll(logoviewshopper, searchbarf, searchbtn, verticalline, mycartbtn, logoutbtn);
        bordersearchbar.setCenter(searchbarbox);
    }

    public void setlayoutdetailpage() {
        backgrounddetailpage.setTop(bordersearchbar);

        backgrounddetailpage.setStyle("-fx-background-color: #1b1c2d;");

        bordersearchbar.setBackground(new Background(
                new BackgroundFill(Color.web("#2e2f47"), new CornerRadii(100), new Insets(10))));
        BorderPane.setMargin(bordersearchbar, new Insets(10));
        bordersearchbar.setPrefHeight(100);

        searchbarbox.setAlignment(Pos.CENTER);
        HBox.setMargin(logoviewshopper, new Insets(0, 0, 0, 5));
        HBox.setMargin(searchbarf, new Insets(0, 5, 0, 0));
        HBox.setMargin(searchbtn, new Insets(0, 0, 0, 0));
        HBox.setMargin(verticalline, new Insets(0, 5, 0, 5));
        HBox.setMargin(mycartbtn, new Insets(0, 10, 0, 0));
        HBox.setMargin(logoutbtn, new Insets(0, 0, 0, 10));

        logoviewshopper.setFitWidth(70);
        logoviewshopper.setFitHeight(50);

        searchbarf.setPrefWidth(500);

        searchbtn.setStyle("-fx-background-color: #1b1c2d;");
        searchbtn.setTextFill(Color.WHITE);

        mycartbtn.setStyle("-fx-background-color: #2e2f47;");
        mycartbtn.setTextFill(Color.WHITE);

        logoutbtn.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, new Insets(0))));
        logoutbtn.setTextFill(Color.WHITE);
    }

    public void detailpageeventhandle() {
        logoutbtn.setOnAction(e -> {
            initLoginForm();
        });

        logoviewshopper.setOnMouseClicked(e -> {
            if (loginuser.getUserRole().equalsIgnoreCase("Manager")) {
                initmanagerhomepage();
            } else {
                initshopperhomepage();
            }
        });

        // Add back button
        Button backButton = new Button("Back to Shop");
        backButton.setOnAction(e -> initshopperhomepage());
        backButton.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: white;");

        // Add to layout
        HBox topButtons = new HBox(10);
        topButtons.getChildren().add(backButton);
        backgrounddetailpage.setTop(new VBox(bordersearchbar, topButtons));
    }

    public void initshopperhomepage() {
        backgroundshopperpage = new BorderPane();
        searchscene = new Scene(backgroundshopperpage, 900, 700);

        bordersearchbar = new BorderPane();
        searchbarf = new TextField("Search items in GoGoQuery Store");
        searchbtn = new Button("Search");
        mycartbtn = new Button("My Cart");
        logoutbtn = new Button("Log Out");

        // Use createLogo() for shopper homepage
        logoviewshopper = createLogo(); // This now includes the click event
        if (logoviewshopper != null) {
            logoviewshopper.setFitWidth(70);
            logoviewshopper.setFitHeight(50);
        }

        verticalline = new Rectangle(2, 40);
        verticalline.setFill(Color.GRAY);

        searchbarbox = new HBox(10);
        searchbarbox.getChildren().clear();
        searchbarbox.getChildren().addAll(logoviewshopper, searchbarf, searchbtn,
                verticalline, mycartbtn, logoutbtn);
        bordersearchbar.setCenter(searchbarbox);

        // Username
        String emailusername = emailtf.getText();
        String username = emailusername.split("@")[0];
        welcomemessage = new Label("Welcome, ");
        welcomemessage.setFont(Font.font("Arial", 70));

        usernamel = new Label(username);
        usernamel.setFont(Font.font("Arial", 70));

        // Filter components
        filterpane = new GridPane();
        categorypane = new BorderPane(filterpane);
        categoryl = new Label("Category");
        categorybox = new ComboBox<>();
        applybtn = new Button("Apply");
        filterl = new Label("Filter");

        // Product view
        productview = new ListView<>();
        productview.getItems().clear(); // Clear any existing items

        // Populate methods
        addcomponentshopperpage();
        setlayoutshopperpage();

        // Ensure product list is cleared before reading
        productlist.clear();
        readproduct();
        shopperpageeventhandler();

        // Populate product view
        updateProductView();

        switchscene(searchscene);
    }

    public BorderPane setlistproduct(Product product) {
        BorderPane borderlistproduct = new BorderPane();

        borderlistproduct.setPrefWidth(380);
        borderlistproduct.setPrefHeight(100);
        borderlistproduct.setStyle("-fx-background-color: #2e2f3d; -fx-border-color: #3c3d4a; -fx-border-width: 1;");
        borderlistproduct.setPadding(new Insets(10));

        Rectangle photo = new Rectangle(50, 50);
        photo.setFill(Color.GREY);
        photo.setArcWidth(5);
        photo.setArcHeight(5);

        VBox itemDetails = new VBox(5);
        itemDetails.setAlignment(Pos.CENTER_LEFT);

        Label itemName = new Label(product.getItemName());
        itemName.setTextFill(Color.WHITE);
        itemName.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label itemPrice = new Label("$" + String.format("%.2f", product.getItemPrice()));
        itemPrice.setTextFill(Color.ORANGE);
        itemPrice.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label stockLabel = new Label(product.getItemStock() + " Left");
        stockLabel.setTextFill(Color.RED);
        stockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        itemDetails.getChildren().addAll(itemName, itemPrice, stockLabel);

        borderlistproduct.setLeft(photo);
        BorderPane.setMargin(photo, new Insets(0, 10, 0, 0));

        borderlistproduct.setCenter(itemDetails);
        borderlistproduct.setUserData(product);

        return borderlistproduct;
    }

    public void addcomponentshopperpage() {
        // Clear existing components in filterpane
        filterpane.getChildren().clear();

        filterpane.add(categoryl, 0, 0);
        filterpane.add(categorybox, 0, 1);
        categorybox.setPromptText("Select a category");
        filterpane.add(applybtn, 1, 1);
        categoryl.setTextFill(Color.LIGHTGREY);
        categorypane.setBackground(new Background(
                new BackgroundFill(Color.web("#2e2f47"), new CornerRadii(10), new Insets(5))));

        // Ensure filterLayout is cleared
        if (filterLayout != null) {
            filterLayout.getChildren().clear();
        } else {
            filterLayout = new VBox(10);
        }

        filterText = new Label("Filter");
        filterText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        filterText.setTextFill(Color.LIGHTGREY);
        filterLayout.getChildren().addAll(filterText, categorypane);
        filterLayout.setAlignment(Pos.TOP_LEFT);
        filterLayout.setPadding(new Insets(20));
        filterLayout.setStyle("-fx-background-color: #2e2f47");

        // Ensure showingLayout is cleared
        if (showingLayout != null) {
            showingLayout.getChildren().clear();
        } else {
            showingLayout = new VBox(20);
        }

        showingLayout.getChildren().addAll(createShowingBox(), productview);
        showingLayout.setAlignment(Pos.TOP_LEFT);
        showingLayout.setPadding(new Insets(20));
        showingLayout.setStyle("-fx-background-color: #2e2f47;");

        // Recreate combinedLayout
        combinedLayout = new HBox(20);
        combinedLayout.getChildren().addAll(filterLayout, showingLayout);
        combinedLayout.setPadding(new Insets(20));
        combinedLayout.setStyle("-fx-background-color: #1b1c2d;");
        combinedLayout.setAlignment(Pos.TOP_LEFT);

        // Recreate welcomebox
        welcomebox = new HBox(welcomemessage, usernamel);

        // Recreate mainLayout
        mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(welcomebox, combinedLayout);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
    }

    public HBox createShowingBox() {
        Label textshowing = new Label("Showing ");
        textshowing.setTextFill(Color.WHITE);
        textshowing.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        totalproduct = new Label(String.valueOf(productlist.size()));
        totalproduct.setTextFill(Color.ORANGE);
        totalproduct.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label textproduct = new Label(" Products");
        textproduct.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textproduct.setTextFill(Color.WHITE);

        HBox showingBox = new HBox(10, textshowing, totalproduct, textproduct);
        showingBox.setPadding(new Insets(0, 20, 0, 0));

        return showingBox;
    }

    private List<Product> filterProducts(String query) {
        return productlist.stream()
                .filter(product -> product.getItemName().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    private void updateProductView() {
        productview.getItems().clear();

        // Filter products based on category if selected
        List<Product> filteredProducts = productlist;
        String selectedCategory = categorybox.getValue();
        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            filteredProducts = productlist.stream()
                    .filter(p -> p.getItemCategory().equals(selectedCategory))
                    .collect(Collectors.toList());
        }

        for (Product product : filteredProducts) {
            if (product.getItemStock() > 0) {
                BorderPane productPane = new BorderPane();
                productPane.setPadding(new Insets(15));
                productPane.setStyle("-fx-background-color: #2e2f47; -fx-border-color: #3c3d4a;");

                // Product Image
                Rectangle photoPlaceholder = new Rectangle(80, 80);
                photoPlaceholder.setFill(Color.GRAY);
                photoPlaceholder.setArcWidth(10);
                photoPlaceholder.setArcHeight(10);

                // Product Details VBox
                VBox details = new VBox(5);

                // Product Name
                Label nameLabel = new Label(product.getItemName());
                nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

                // Price
                Label priceLabel = new Label(String.format("$%.2f", product.getItemPrice()));
                priceLabel.setStyle("-fx-text-fill: #ffa500; -fx-font-size: 18px; -fx-font-weight: bold;");

                // Stock Badge
                Label stockLabel = new Label(product.getItemStock() + " Left");
                stockLabel.setStyle(
                        "-fx-background-color: #ff4444;" +
                                "-fx-text-fill: white;" +
                                "-fx-padding: 2 8;" +
                                "-fx-background-radius: 3;");

                details.getChildren().addAll(nameLabel, priceLabel, stockLabel);
                details.setAlignment(Pos.CENTER_LEFT);

                productPane.setLeft(photoPlaceholder);
                productPane.setCenter(details);

                // Store product data
                productPane.setUserData(product);

                productview.getItems().add(productPane);
            }
        }

        // Update total count
        int count = filteredProducts.size();
        if (totalproduct != null) {
            totalproduct.setText(String.valueOf(count));
        }
    }

    private void updateShowingBox(int count) {
        totalproduct.setText(String.valueOf(count));
    }

    public void setlayoutshopperpage() {
        backgroundshopperpage.setTop(bordersearchbar);
        backgroundshopperpage.setCenter(mainLayout);
        backgroundshopperpage.setStyle("-fx-background-color: #1b1c2d;");

        bordersearchbar.setBackground(new Background(
                new BackgroundFill(Color.web("#2e2f47"), new CornerRadii(100), new Insets(10))));

        BorderPane.setMargin(bordersearchbar, new Insets(10));
        bordersearchbar.setPrefHeight(100);

        searchbarbox.setAlignment(Pos.CENTER);
        HBox.setMargin(logoviewshopper, new Insets(0, 0, 0, 5));
        HBox.setMargin(searchbarf, new Insets(0, 5, 0, 0));
        HBox.setMargin(searchbtn, new Insets(0, 0, 0, 0));
        HBox.setMargin(verticalline, new Insets(0, 5, 0, 5));
        HBox.setMargin(mycartbtn, new Insets(0, 10, 0, 0));
        HBox.setMargin(logoutbtn, new Insets(0, 0, 0, 10));

        logoviewshopper.setFitWidth(70);
        logoviewshopper.setFitHeight(50);

        searchbarf.setPrefWidth(500);

        searchbtn.setStyle("-fx-background-color: #1b1c2d;");
        searchbtn.setTextFill(Color.WHITE);

        mycartbtn.setStyle("-fx-background-color: #2e2f47;");
        mycartbtn.setTextFill(Color.WHITE);

        logoutbtn.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, new Insets(0))));
        logoutbtn.setTextFill(Color.WHITE);

        welcomemessage.setTextFill(Color.WHITE);
        usernamel.setTextFill(Color.ORANGE);

        showingLayout.setPrefWidth(400);
        showingLayout.setPrefHeight(500);
        showingLayout.setMinWidth(400);
        showingLayout.setMinHeight(500);
        showingLayout.setMaxWidth(400);
        showingLayout.setMaxHeight(500);

        filterLayout.setPrefWidth(225);
        filterLayout.setPrefHeight(100);
        filterLayout.setMinWidth(225);
        filterLayout.setMinHeight(100);
        filterLayout.setMaxWidth(225);
        filterLayout.setMaxHeight(100);
    }

    private void initCartPage() {
        BorderPane cartPage = new BorderPane();
        cartPage.setStyle("-fx-background-color: #1b1c2d;");

        // Top navigation
        HBox topNav = new HBox(20);
        topNav.setStyle("-fx-background-color: #2e2f47; -fx-padding: 10;");
        topNav.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = createLogo();
        TextField searchField = new TextField();
        searchField.setPromptText("Search items in GoGoQuery Store");
        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #6c7ae0; -fx-text-fill: white;");

        Button cartBtn = new Button("My Cart");
        cartBtn.setStyle("-fx-background-color: #2e2f47; -fx-text-fill: white;");

        Button logoutBtn = new Button("Log Out");
        logoutBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");

        topNav.getChildren().addAll(logo, searchField, searchBtn, cartBtn, logoutBtn);

        // Cart content
        VBox cartContent = new VBox(20);
        cartContent.setPadding(new Insets(30));

        // User's cart label
        String username = getCurrentUsername();
        Label cartLabel = new Label(username + "'s Cart");
        cartLabel.setStyle("-fx-text-fill: white; -fx-font-size: 40px;");

        // Cart items list - Changed from VBox to ListView
        ListView<BorderPane> cartItemsListView = new ListView<>();
        cartItemsListView.setStyle("-fx-background-color: #2e2f47;");
        List<Cart> userCart = getUserCartItems();

        if (userCart.isEmpty()) {
            Label emptyCart = new Label("Your cart is empty!");
            emptyCart.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            VBox emptyBox = new VBox(emptyCart);
            emptyBox.setAlignment(Pos.CENTER);
            cartItemsListView.setPlaceholder(emptyBox);
        } else {
            for (Cart item : userCart) {
                BorderPane itemPane = createCartItemPane(item, cartItemsListView,
                        new SimpleDoubleProperty(calculateTotal()));
                cartItemsListView.getItems().add(itemPane);
            }
        }

        // Billing summary
        VBox billing = new VBox(10);
        billing.setStyle("-fx-background-color: #2e2f47; -fx-padding: 20; -fx-background-radius: 10;");

        Label billingTitle = new Label("Billing summary");
        billingTitle.setStyle("-fx-text-fill: white;");

        double total = calculateTotal();
        Label totalLabel = new Label(String.format("Total: $%.2f", total));
        totalLabel.setStyle("-fx-text-fill: #ffa500; -fx-font-size: 24px;");

        Label taxNote = new Label("*Tax and delivery cost included");
        taxNote.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");

        Button checkoutBtn = new Button("Checkout Items");
        checkoutBtn.setStyle(
                "-fx-background-color: #ffa500;" +
                        "-fx-text-fill: white;" +
                        "-fx-pref-width: 200;");

        billing.getChildren().addAll(billingTitle, totalLabel, taxNote, checkoutBtn);

        cartContent.getChildren().addAll(cartLabel, cartItemsListView);

        // Layout
        cartPage.setTop(topNav);
        cartPage.setCenter(cartContent);
        cartPage.setRight(billing);

        // Event handlers
        checkoutBtn.setOnAction(e -> {
            if (userCart.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Cart");
                alert.setHeaderText(null);
                alert.setContentText("Your cart is empty!");
                alert.showAndWait();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Checkout");
            confirm.setHeaderText("Confirm Purchase");
            confirm.setContentText("Do you want to proceed with checkout?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                connect.createTransaction(loginuser.getUserID(), userCart);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText(null);
                success.setContentText("Purchase successful!");
                success.showAndWait();

                allcartlist.clear();
                initshopperhomepage();
            }
        });

        Scene scene = new Scene(cartPage, 900, 700);
        switchscene(scene);
    }

    private void setupFilter() {
        // Get unique categories
        Set<String> categories = productlist.stream()
                .map(Product::getItemCategory)
                .collect(Collectors.toSet());

        categorybox.getItems().clear();
        categorybox.getItems().addAll(categories);

        applybtn.setOnAction(e -> {
            String selected = categorybox.getValue();
            if (selected != null && !selected.isEmpty()) {
                List<Product> filtered = productlist.stream()
                        .filter(p -> p.getItemCategory().equals(selected))
                        .collect(Collectors.toList());
                updateProductView(filtered);
            }
        });
    }

    public void initmanagerhomepage() {
        // Basic setup
        menuborder = new BorderPane();
        managerscene = new Scene(menuborder, 900, 700);

        // Create menu bar
        menubar = new MenuBar();
        menu = new Menu("Menu");
        additemmenu = new MenuItem("Add Item");
        managequeue = new MenuItem("Manage Queue");
        logout = new MenuItem("Log Out");

        // Add menu items to menu
        menu.getItems().addAll(additemmenu, managequeue, logout);
        menubar.getMenus().add(menu);

        // Logo and welcome message
        VBox topContent = new VBox(10);
        ImageView logoView = createLogo();
        welcomelabel = new Label("Welcome to Manager Dashboard");
        welcomelabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomelabel.setTextFill(Color.WHITE);
        topContent.getChildren().addAll(logoView, menubar);

        menuborder.setTop(topContent);
        menuborder.setCenter(welcomelabel);
        // menuborder.setStyle("-fx-background-color: #1b1c2d;");

        // Menu event handlers
        additemmenu.setOnAction(e -> showAddItemPopup(primaryStage));
        managequeue.setOnAction(e -> initQueueManager());
        logout.setOnAction(e -> initLoginForm());

        switchscene(managerscene);
    }

    private void loadManagerStats(VBox productsCard, VBox ordersCard, VBox revenueCard) {
        new Thread(() -> {
            try {
                // Get product count
                ResultSet productRs = connect.execQuery("SELECT COUNT(*) as count FROM msitem");
                int productCount = productRs.next() ? productRs.getInt("count") : 0;
                Platform.runLater(() -> updateStatsCard(productsCard, String.valueOf(productCount)));

                // Get pending orders
                ResultSet orderRs = connect
                        .execQuery("SELECT COUNT(*) as count FROM transaction_header WHERE Status='Pending'");
                int orderCount = orderRs.next() ? orderRs.getInt("count") : 0;
                Platform.runLater(() -> updateStatsCard(ordersCard, String.valueOf(orderCount)));

                // Get total revenue
                ResultSet revenueRs = connect.execQuery(
                        "SELECT COALESCE(SUM(i.ItemPrice * td.Quantity), 0) as revenue " +
                                "FROM transaction_header th " +
                                "JOIN transaction_detail td ON th.TransactionID = td.TransactionID " +
                                "JOIN msitem i ON td.ItemID = i.ItemID " +
                                "WHERE th.Status='Completed'");
                double revenue = revenueRs.next() ? revenueRs.getDouble("revenue") : 0.0;
                Platform.runLater(() -> updateStatsCard(revenueCard, String.format("$%.2f", revenue)));
            } catch (SQLException ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Error");
                    alert.setHeaderText("Failed to load statistics");
                    alert.setContentText("Could not retrieve manager dashboard statistics.");
                    alert.showAndWait();
                });
            }
        }).start();
    }

    private VBox createStatsCard(String title, String value) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setMinWidth(200);
        card.setMinHeight(120);
        card.setStyle("-fx-background-color: #2e2f47; -fx-background-radius: 10;");

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);

        Label valueLabel = new Label(value);
        valueLabel.setTextFill(Color.ORANGE);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private void updateStatsCard(VBox card, String value) {
        Platform.runLater(() -> {
            Label valueLabel = (Label) card.getChildren().get(1);
            valueLabel.setText(value);
        });
    }

    public void shopperpageeventhandler() {
        logoutbtn.setOnAction(e -> initLoginForm());

        productview.setOnMouseClicked(e -> {
            BorderPane selectedItem = productview.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Product selectedProduct = (Product) selectedItem.getUserData();
                if (selectedProduct != null) {
                    showDetailPage(selectedProduct);
                }
            }
        });

        mycartbtn.setOnAction(e -> initCartPage());

        searchbarf.setOnAction(e -> {
            String query = searchbarf.getText().toLowerCase();
            List<Product> filteredProducts = filterProducts(query);
            updateProductView(filteredProducts);
        });

        searchbtn.setOnAction(e -> {
            String query = searchbarf.getText().toLowerCase();
            List<Product> filteredProducts = filterProducts(query);
            updateProductView(filteredProducts);
        });

        logoviewshopper.setOnMouseClicked(e -> initshopperhomepage());
    }

    public void initrolepage() {
        logoviewrole = createLogo();
        backgroundpage.setTop(logoviewrole);
        backgroundpage = new BorderPane();
        rolepagescene = new Scene(backgroundpage, 900, 700);
        bordermanager = new BorderPane();
        bordershopper = new BorderPane();
        managerl = new Label("Manager");
        shopperl = new Label("Shopper");
        managerdesc = new Label("Manage products and deliveries, be the ruler!");
        shopperdesc = new Label("Search products, manage your cart, go shopping!");
        managerbutton = new Button("Register as Manager");
        shopperbutton = new Button("Register as Shopper");
        linemanager = new Rectangle(200, 1);
        lineshopper = new Rectangle(200, 1);
        boxmanager = new VBox(10);
        boxshopper = new VBox(10);
        logoM = new Image("logo.png");
        logoMview = new ImageView(logoM);
        logoSview = new ImageView(logoS);

        addcomponentrolepage();
        setlayoutrolepage();
        switchscene(rolepagescene);
        rolepageeventhandler();
    }

    public void rolepageeventhandler() {
        managerbutton.setOnAction(e -> {
            System.out.println("you login as manager");
            if (genderGroup.getSelectedToggle() == male) {
                addcustomer(emailtfr.getText(), passwordfr.getText(), "manager", "male",
                        String.valueOf(dobpick.getValue()));
            } else {
                addcustomer(emailtfr.getText(), passwordfr.getText(), "manager", "female",
                        String.valueOf(dobpick.getValue()));
            }
            Alert registinfo = new Alert(Alert.AlertType.INFORMATION);
            registinfo.setTitle("Register Information");
            registinfo.setHeaderText("Register Success!");
            registinfo.setContentText("Please log in with your newly created account.");
            registinfo.showAndWait();
            initLoginForm();
        });

        shopperbutton.setOnAction(e -> {
            System.out.println("you login as shopper");
            if (genderGroup.getSelectedToggle() == male) {
                addcustomer(emailtfr.getText(), passwordfr.getText(), "shopper", "male",
                        String.valueOf(dobpick.getValue()));
            } else {
                addcustomer(emailtfr.getText(), passwordfr.getText(), "shopper", "female",
                        String.valueOf(dobpick.getValue()));
            }
            Alert registinfo = new Alert(Alert.AlertType.INFORMATION);
            registinfo.setTitle("Register Information");
            registinfo.setHeaderText("Register Success!");
            registinfo.setContentText("Please log in with your newly created account.");
            registinfo.showAndWait();
            initLoginForm();
        });
    }

    public void addcomponentrolepage() {
        logoimagerole = new Image("logo.png");
        logoviewrole = new ImageView(logoimagerole);
        logoviewrole.setFitHeight(200);
        logoviewrole.setFitWidth(300);

        linemanager.setFill(Color.DARKGRAY);
        lineshopper.setFill(Color.DARKGRAY);

        boxmanager.getChildren().addAll(logoMview, managerl, linemanager, managerdesc, managerbutton);
        boxshopper.getChildren().addAll(logoSview, shopperl, lineshopper, shopperdesc, shopperbutton);
    }

    public void setlayoutrolepage() {
        backgroundpage.setTop(logoviewrole);
        BorderPane.setAlignment(logoviewrole, Pos.CENTER);

        hboxrolepage = new HBox(250, bordermanager, bordershopper);
        hboxrolepage.setAlignment(Pos.CENTER);
        backgroundpage.setCenter(hboxrolepage);

        bordermanager.setPrefSize(150, 150);
        bordershopper.setPrefSize(200, 150);
        bordermanager.setMinSize(150, 150);
        bordershopper.setMinSize(150, 150);
        bordermanager.setMaxSize(150, 150);
        bordershopper.setMaxSize(150, 150);

        bordermanager.setCenter(boxmanager);
        bordershopper.setCenter(boxshopper);
        boxmanager.setAlignment(Pos.CENTER);
        boxshopper.setAlignment(Pos.CENTER);

        backgroundpage.setStyle("-fx-background-color: #1b1c2d;");
        boxmanager.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));
        boxshopper.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));

        managerbutton.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        shopperbutton.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        managerbutton.setPrefWidth(150);
        shopperbutton.setPrefWidth(150);
    }

    public void readcart() {
        String query = "SELECT * FROM mscart";
        connect.execQuery(query);
        try {
            while (connect.rs.next()) {
                Integer UserID = connect.rs.getInt("UserID");
                Integer ItemID = connect.rs.getInt("ItemID");
                Integer Quantity = connect.rs.getInt("Quantity");
                String ItemName = connect.rs.getString("ItemName");
                Double ItemPrice = connect.rs.getDouble("ItemPrice");
                allcartlist.add(new Cart(UserID, ItemID, Quantity, ItemName, ItemPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addproductcart(int UserID, int ItemID, int Quantity, String ItemName, Double ItemPrice) {
        String query = "INSERT INTO msuser(UserID, ItemID, Quantity, ItemName, ItemPrice) VALUES ("
                + "'" + UserID + "',"
                + "'" + ItemID + "',"
                + "'" + Quantity + "',"
                + "'" + ItemName + "',"
                + "'" + ItemPrice + "')";

        connect.execUpdate(query);
    }

    public void readproduct() {
        String query = "SELECT * FROM msitem";
        connect.execQuery(query);
        try {
            while (connect.rs.next()) {
                Integer itemID = connect.rs.getInt("ItemID");
                String itemName = connect.rs.getString("ItemName");
                Double itemPrice = connect.rs.getDouble("ItemPrice");
                String itemDesc = connect.rs.getString("ItemDesc");
                String itemCategory = connect.rs.getString("ItemCategory");
                Integer itemStock = connect.rs.getInt("ItemStock");
                productlist.add(new Product(itemID, itemName, itemPrice, itemDesc, itemCategory, itemStock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readcustomer() {
        String query = "SELECT * FROM msuser";
        try (Statement stmt = connect.createStatement(); // This should work now
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Integer userID = rs.getInt("UserID");
                String userEmail = rs.getString("UserEmail");
                String userPassword = rs.getString("UserPassword");
                String userRole = rs.getString("UserRole");
                String userGender = rs.getString("UserGender");
                String userDOB = rs.getString("UserDOB");

                // Add the user to the list
                userlist.add(new MsUser(userID, userDOB, userEmail, userPassword, userRole, userGender));
            }
        } catch (SQLException e) {
            System.err.println("Error reading customer data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addcustomer(String userEmail, String userPassword, String userRole, String UserGender, String userDOB) {
        String query = "INSERT INTO msuser(UserDOB, UserEmail, UserPassword, UserRole, UserGender) VALUES ("
                + "'" + userDOB + "',"
                + "'" + userEmail + "',"
                + "'" + userPassword + "',"
                + "'" + userRole + "',"
                + "'" + UserGender + "')";

        connect.execUpdate(query);
    }

    public void checkemail(String inputedemail, String inputedpassword) {
        if (inputedemail.isEmpty() || inputedpassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return;
        }

        if (!checkinput(inputedemail, inputedpassword)) {
            Alert checkalert = new Alert(Alert.AlertType.ERROR);
            checkalert.setTitle("Invalid Login");
            checkalert.setHeaderText("Wrong Credentials!");
            checkalert.setContentText("You entered a wrong email or password");
            checkalert.showAndWait();
            return;
        }

        if (loginuser.getUserRole().equalsIgnoreCase("Manager")) {
            initmanagerhomepage();
        } else {
            initshopperhomepage();
        }
    }

    public boolean checkinput(String inputedemail, String inputedpassword) {
        readcustomer();
        for (MsUser msUser : userlist) {
            if (msUser.getUserEmail().equals(inputedemail)) {
                if (msUser.getUserPassword().equals(inputedpassword)) {
                    loginuser = msUser;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkemail(String inputemail) {
        readcustomer();
        for (MsUser msUser : userlist) {
            if (msUser.getUserEmail().equals(inputemail)) {
                return false;
            }
        }
        return true;
    }

    public void initLoginForm() {
        // Initialize base containers
        border1 = new FlowPane();
        border2 = new BorderPane();
        border3 = new BorderPane();
        loginScene = new Scene(border1, 900, 700);
        gridLogin = new GridPane();
        flowRegister = new FlowPane();

        // Create and style the logo
        logoview = createLogo();
        logoview.setFitHeight(100);
        logoview.setFitWidth(140);

        // Create logo container with proper alignment
        StackPane logoContainer = new StackPane(logoview);
        logoContainer.setPadding(new Insets(20, 20, 40, 20));
        logoContainer.setAlignment(Pos.CENTER);

        // Initialize login form components
        login = new Label("Login");
        login.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        login.setTextFill(Color.web("#333333"));

        emaill = new Label("Email");
        emaill.setFont(Font.font("Arial", 14));
        emailtf = new TextField();
        emailtf.setPromptText("Enter your email");
        emailtf.setPrefWidth(250);
        emailtf.setStyle("-fx-background-radius: 5;");

        passwordl = new Label("Password");
        passwordl.setFont(Font.font("Arial", 14));
        passwordf = new PasswordField();
        passwordf.setPromptText("Enter your password");
        passwordf.setPrefWidth(250);
        passwordf.setStyle("-fx-background-radius: 5;");

        // Login button styling
        loginbtn = new Button("Login");
        loginbtn.setPrefWidth(250);
        loginbtn.setStyle(
                "-fx-background-color: #FF6B00;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand");
        loginbtn.setOnMouseEntered(e -> loginbtn.setStyle(
                "-fx-background-color: #FF8533;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand"));
        loginbtn.setOnMouseExited(e -> loginbtn.setStyle(
                "-fx-background-color: #FF6B00;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand"));

        // Decorative line
        line = new Rectangle(250, 1);
        line.setFill(Color.web("#E0E0E0"));

        // Register link section
        rgstr = new Label("Are you new? Register");
        rgstr.setTextFill(Color.web("#666666"));
        here = new Label(" Here!");
        here.setTextFill(Color.web("#FF6B00"));
        here.setUnderline(true);
        here.setCursor(Cursor.HAND);
        here.setOnMouseEntered(e -> here.setTextFill(Color.web("#FF8533")));
        here.setOnMouseExited(e -> here.setTextFill(Color.web("#FF6B00")));

        // Layout setup
        gridLogin.setVgap(10);
        gridLogin.setHgap(10);
        gridLogin.setPadding(new Insets(20));
        gridLogin.setAlignment(Pos.CENTER);

        // Add components to grid
        gridLogin.add(login, 0, 0);
        gridLogin.add(line, 0, 1);
        GridPane.setMargin(line, new Insets(10, 0, 10, 0));

        gridLogin.add(emaill, 0, 2);
        gridLogin.add(emailtf, 0, 3);

        gridLogin.add(passwordl, 0, 4);
        gridLogin.add(passwordf, 0, 5);

        gridLogin.add(loginbtn, 0, 6);
        GridPane.setMargin(loginbtn, new Insets(10, 0, 20, 0));

        // Setup register flow
        flowRegister.getChildren().addAll(rgstr, here);
        flowRegister.setAlignment(Pos.CENTER);
        flowRegister.setPadding(new Insets(20));

        // Main container styling
        border3.setCenter(gridLogin);
        border3.setBottom(flowRegister);
        border3.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 2);");
        border3.setPrefWidth(300);
        border3.setPrefHeight(400);
        border3.setPadding(new Insets(20));

        // Setup main layout
        border2.setTop(logoContainer);
        border2.setCenter(border3);
        BorderPane.setMargin(border3, new Insets(0, 20, 20, 20));

        // Background styling
        backgroundcolorlogin = new LinearGradient(
                0, 0, 1, 0, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#1b1c2d")),
                new Stop(1, Color.web("#1b1c2d")));
        border1.setBackground(new Background(
                new BackgroundFill(backgroundcolorlogin, CornerRadii.EMPTY, Insets.EMPTY)));

        // Final layout setup
        border1.getChildren().add(border2);
        border1.setAlignment(Pos.CENTER);

        // Event handlers
        here.setOnMouseClicked(event -> initRegisterForm());
        loginbtn.setOnAction(event -> checkemail(emailtf.getText(), passwordf.getText()));

        // Set the scene
        switchscene(loginScene);
    }

    private StackPane createLogoContainer(ImageView logo, double height, double width) {
        ImageView logoView = logo;
        logoView.setFitHeight(height);
        logoView.setFitWidth(width);

        StackPane container = new StackPane(logoView);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);

        return container;
    }

    public void addComponentsLogin() {
        border2.setTop(logoview);
        border2.setCenter(border3);
        border3.setCenter(gridLogin);
        border3.setPrefWidth(300);
        border3.setPrefHeight(250);

        gridLogin.add(login, 0, 0);
        login.setFont(Font.font("Arial", 25));
        gridLogin.add(line, 0, 1);
        gridLogin.add(emaill, 0, 2);
        emaill.setFont(Font.font("Arial", 14));
        gridLogin.add(emailtf, 0, 3);
        emailtf.setPrefWidth(250);
        gridLogin.add(passwordl, 0, 4);
        passwordl.setFont(Font.font("Arial", 15));
        gridLogin.add(passwordf, 0, 5);
        passwordf.setPrefWidth(250);
        gridLogin.add(loginbtn, 0, 6);
        loginbtn.setPrefWidth(250);

        flowRegister.getChildren().add(rgstr);
        flowRegister.getChildren().add(here);
        border3.setBottom(flowRegister);
        flowRegister.setAlignment(Pos.CENTER);
        border3.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        border1.getChildren().add(border2);
        border1.setAlignment(Pos.CENTER);

        here.setOnMouseClicked(event -> initRegisterForm());
    }

    public void loginLayout() {
        gridLogin.setVgap(10);
        gridLogin.setHgap(10);
        gridLogin.setPadding(new Insets(10));

        loginbtn.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        loginbtn.setTextFill(Color.WHITE);
    }

    public void logineventhandler() {
        loginbtn.setOnAction(event -> {
            checkemail(emailtf.getText(), passwordf.getText());
        });
    }

    public void initRegisterForm() {
        borderlogoregis = new BorderPane();
        registerPane = new FlowPane();
        registerBorder = new BorderPane();
        registerGrid = new GridPane();
        registerScene = new Scene(registerPane, 900, 700);

        // Use createLogo() for register form
        logoviewregist = createLogo();
        logoviewregist.setFitHeight(200);
        logoviewregist.setFitWidth(300);
        borderlogoregis.setTop(logoviewregist);

        // Rest of the register form initialization...
        registerLabel = new Label("Register");
        registerLabel.setFont(Font.font("Arial", 25));
        // ... other components ...

        setlayoutregist();
        addcomponentregist();
        registereventhandler();

        switchscene(registerScene);
    }

    public void registereventhandler() {
        hereregist.setOnMouseClicked(event -> initLoginForm());
        registerBtn.setOnMouseClicked(e -> validateregistform());
    }

    public void setlayoutregist() {
        registerBorder.setPrefWidth(150);

        registerGrid.setVgap(10);
        registerGrid.setHgap(10);
        registerGrid.setPadding(new Insets(10));
        registerGrid.add(registerLabel, 0, 0);
        registerGrid.add(lineregist, 0, 1);
        registerGrid.add(emaillr, 0, 2);
        registerGrid.add(emailtfr, 0, 3);
        emailtfr.setPrefWidth(100);
        registerGrid.add(passwordlr, 0, 4);
        registerGrid.add(passwordfr, 0, 5);
        passwordfr.setPrefWidth(100);
        registerGrid.add(confirmpassl, 0, 6);
        registerGrid.add(confirmpassfr, 0, 7);
        confirmpassfr.setPrefWidth(100);
        registerGrid.add(dob, 0, 8);
        registerGrid.add(dobpick, 0, 9);
        registerGrid.add(gender, 0, 10);
        registerGrid.add(male, 0, 11);
        registerGrid.add(female, 0, 12);
        registerGrid.add(termsncondiregist, 0, 13);
        registerGrid.add(registerBtn, 0, 14);
        registerBtn.setPrefWidth(300);
        registerBtn.setTextFill(Color.WHITE);
        registerBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        registerGrid.add(haveaccregist, 0, 15);

        logoimageregist = new Image("Screenshot 2024-11-13 082751.png");
        logoviewregist = new ImageView(logoimageregist);
        logoviewregist.setFitHeight(200);
        logoviewregist.setFitWidth(300);
    }

    public void addcomponentregist() {
        borderlogoregis.setTop(logoviewregist);
        borderlogoregis.setCenter(registerBorder);
        registerBorder.setCenter(registerGrid);
        registerBorder
                .setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(5))));
        registerPane.getChildren().add(borderlogoregis);
        registerPane.setAlignment(Pos.CENTER);
        registerPane.setStyle("-fx-background-color: #1b1c2d;");
    }

    public boolean PasswordChecker(String passwordaplhanum) {
        boolean checkHuruf = false;
        boolean checkAngka = false;
        boolean checkSymbol = false;

        for (int i = 0; i < passwordaplhanum.length(); i++) {
            if (Character.isLetter(passwordaplhanum.charAt(i))) {
                checkHuruf = true;
            } else if (Character.isDigit(passwordaplhanum.charAt(i))) {
                checkAngka = true;
            } else {
                checkSymbol = true;
            }
        }

        return checkHuruf && checkAngka && !checkSymbol;
    }

    public boolean validateregistform() {
        String email = emailtfr.getText();
        String password = passwordfr.getText();
        String confirmPassword = confirmpassfr.getText();
        LocalDate dob = dobpick.getValue();
        RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
        boolean termsAccepted = termsbox.isSelected();

        if (email.isBlank()) {
            Alert emailAlert = new Alert(Alert.AlertType.ERROR);
            emailAlert.setTitle("Register Failed");
            emailAlert.setHeaderText("Register Error");
            emailAlert.setContentText("Email must be filled.");
            emailAlert.showAndWait();
        } else if (!email.endsWith("@gomail.com")) {
            Alert emailEndAlert = new Alert(Alert.AlertType.ERROR);
            emailEndAlert.setTitle("Register Failed");
            emailEndAlert.setHeaderText("Register Error");
            emailEndAlert.setContentText("Email must end with '@gomail.com'.");
            emailEndAlert.showAndWait();
        } else if (!email.matches("^[a-zA-Z0-9._@-]+$")) {
            Alert invalidCharAlert = new Alert(Alert.AlertType.ERROR);
            invalidCharAlert.setTitle("Register Failed");
            invalidCharAlert.setHeaderText("Register Error");
            invalidCharAlert
                    .setContentText("Email contains invalid characters. Only '@', '.', '_', and '-' are allowed.");
            invalidCharAlert.showAndWait();
        } else if (!checkemail(email)) {
            Alert emailTakenAlert = new Alert(Alert.AlertType.ERROR);
            emailTakenAlert.setTitle("Register Failed");
            emailTakenAlert.setHeaderText("Register Error");
            emailTakenAlert.setContentText("Email is already taken. Please use another email address.");
            emailTakenAlert.showAndWait();
        } else if (password.isBlank()) {
            Alert passwordAlert = new Alert(Alert.AlertType.ERROR);
            passwordAlert.setTitle("Register Failed");
            passwordAlert.setHeaderText("Register Error");
            passwordAlert.setContentText("Password must be filled.");
            passwordAlert.showAndWait();
        } else if (!PasswordChecker(password)) {
            Alert passwordFormatAlert = new Alert(Alert.AlertType.ERROR);
            passwordFormatAlert.setTitle("Register Failed");
            passwordFormatAlert.setHeaderText("Register Error");
            passwordFormatAlert.setContentText("Password must be alphanumeric.");
            passwordFormatAlert.showAndWait();
        } else if (!confirmPassword.equals(password)) {
            Alert passwordMatchAlert = new Alert(Alert.AlertType.ERROR);
            passwordMatchAlert.setTitle("Register Failed");
            passwordMatchAlert.setHeaderText("Register Error");
            passwordMatchAlert.setContentText("Passwords do not match.");
            passwordMatchAlert.showAndWait();
        } else if (dob == null) {
            Alert dobAlert = new Alert(Alert.AlertType.ERROR);
            dobAlert.setTitle("Register Failed");
            dobAlert.setHeaderText("Register Error");
            dobAlert.setContentText("Please select your date of birth.");
            dobAlert.showAndWait();
        } else if (LocalDate.now().minusYears(17).isBefore(dob)) {
            Alert ageAlert = new Alert(Alert.AlertType.ERROR);
            ageAlert.setTitle("Register Failed");
            ageAlert.setHeaderText("Register Error");
            ageAlert.setContentText("You must be at least 17 years old.");
            ageAlert.showAndWait();
        } else if (selectedGender == null) {
            Alert genderAlert = new Alert(Alert.AlertType.ERROR);
            genderAlert.setTitle("Register Failed");
            genderAlert.setHeaderText("Register Error");
            genderAlert.setContentText("Please select your gender.");
            genderAlert.showAndWait();
        } else if (!termsAccepted) {
            Alert termsAlert = new Alert(Alert.AlertType.ERROR);
            termsAlert.setTitle("Register Failed");
            termsAlert.setHeaderText("Register Error");
            termsAlert.setContentText("You must agree to the terms and conditions.");
            termsAlert.showAndWait();
        } else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Register Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Registration successful!");
            successAlert.showAndWait();
            initrolepage();
            return true;
        }

        return false;
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        initLoginForm();
        stage.setTitle("Go Go Query");
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setResizable(false); // Prevent resizing
        stage.show();
    }

    public static void switchscene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void updateProductView(List<Product> filteredProducts) {
        productview.getItems().clear();

        for (Product product : filteredProducts) {
            if (product.getItemStock() > 0) {
                BorderPane productPane = new BorderPane();
                productPane.setPadding(new Insets(10));
                productPane.setStyle("-fx-background-color: #2e2f47; -fx-border-color: #3c3d4a;");

                // Product Image (placeholder)
                Rectangle productImage = new Rectangle(80, 80);
                productImage.setFill(Color.GRAY);
                productImage.setArcWidth(10);
                productImage.setArcHeight(10);

                // Product Info Container
                VBox details = new VBox(5);

                // Name
                Label nameLabel = new Label(product.getItemName());
                nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

                // Price
                Label priceLabel = new Label(String.format("$%.2f", product.getItemPrice()));
                priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4CAF50;");

                // Stock
                Label stockLabel = new Label(String.format("%d in stock", product.getItemStock()));
                stockLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: orange;");

                // Category
                Label categoryLabel = new Label(product.getItemCategory());
                categoryLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #808080;");

                details.getChildren().addAll(nameLabel, priceLabel, stockLabel, categoryLabel);

                productPane.setLeft(productImage);
                productPane.setCenter(details);
                BorderPane.setMargin(productImage, new Insets(0, 10, 0, 0));

                productPane.setUserData(product);
                productview.getItems().add(productPane);
            }
        }

        // Update showing count
        if (totalproduct != null) {
            totalproduct.setText(String.valueOf(filteredProducts.size()));
        }
    }

    private double calculateTotal() {
        double total = 0.0;
        for (Cart item : allcartlist) {
            if (item.getUserID() == loginuser.getUserID()) {
                total += item.getItemPrice() * item.getQuantity();
            }
        }
        return total;
    }

    private BorderPane createCartItemPane(Cart cart, ListView<BorderPane> cartItemsView, DoubleProperty totalPrice) {
        BorderPane itemPane = new BorderPane();
        itemPane.setPadding(new Insets(10));
        itemPane.setStyle("-fx-background-color: #2e2f47; -fx-border-color: #3c3d4a;");

        // Item Details
        VBox itemDetails = new VBox(5);
        Label nameLabel = new Label(cart.getItemName());
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label priceLabel = new Label(String.format("$%.2f", cart.getItemPrice()));
        priceLabel.setStyle("-fx-text-fill: #4CAF50;");

        itemDetails.getChildren().addAll(nameLabel, priceLabel);

        // Quantity Spinner
        Spinner<Integer> quantitySpinner = new Spinner<>(0, 100, cart.getQuantity());
        quantitySpinner.setEditable(true);
        quantitySpinner.setPrefWidth(100);

        quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == 0) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Remove Item");
                confirm.setContentText("Remove this item from cart?");

                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    removeFromCart(cart);
                    cartItemsView.getItems().remove(itemPane);
                    totalPrice.set(calculateTotal());
                } else {
                    quantitySpinner.getValueFactory().setValue(oldVal);
                }
            } else {
                updateCartQuantity(cart, newVal);
                totalPrice.set(calculateTotal());
            }
        });

        // Remove Button
        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");

        removeBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Remove Item");
            confirm.setContentText("Remove this item from cart?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                removeFromCart(cart);
                cartItemsView.getItems().remove(itemPane);
                totalPrice.set(calculateTotal());
            }
        });

        // Layout
        itemPane.setLeft(itemDetails);
        itemPane.setCenter(quantitySpinner);
        itemPane.setRight(removeBtn);

        return itemPane;
    }

    private void removeFromCart(Cart cart) {
        // Remove from database
        String query = String.format(
                "DELETE FROM mscart WHERE UserID = %d AND ItemID = %d",
                cart.getUserID(),
                cart.getItemID());
        connect.execUpdate(query);

        // Remove from memory
        allcartlist.removeIf(item -> item.getUserID() == cart.getUserID() &&
                item.getItemID() == cart.getItemID());

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setHeaderText("Item Removed");
        success.setContentText("Item has been removed from cart!");
        success.showAndWait();
    }

    private void updateCartQuantity(Cart cart, int newQuantity) {
        // Update database
        String query = String.format(
                "UPDATE mscart SET Quantity = %d WHERE UserID = %d AND ItemID = %d",
                newQuantity,
                cart.getUserID(),
                cart.getItemID());
        connect.execUpdate(query);

        // Update memory
        cart.setQuantity(newQuantity);
    }
}