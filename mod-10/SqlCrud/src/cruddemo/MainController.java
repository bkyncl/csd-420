/*MainController.java
* Module 10 Assignment 
* Name: Brittany Kyncl
* Date: 4.28.23
* Course: CSD420
*  This class serves as the controller for the FXML user interface. It handles user events, database connections, 
* and executing SQL statements. It uses the MysqlDataSource class for the database connection.
*/
package cruddemo;

import java.net.URL;
import java.util.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.fxml.*;
import java.sql.*;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// The MainController class implements the Initializable interface
public class MainController implements Initializable {

    // Database connection and query variables
    private Connection con;
    private MysqlDataSource dataSource = new MysqlDataSource();
    private String url = "jdbc:mysql://localhost:3306/databasedb?";
    private String username;
    private String password;
    private String sqlCommand;
    private Alert alert = new Alert(AlertType.ERROR);
    private PreparedStatement prpStmt;
    private ResultSet resultSet;
    private String tableName = "fans";

    // FXML controls
    @FXML private Label comboURL, lblConnectionStatus, currentTable, recordNumLabel;
    @FXML private TextField inputUsername, idTextField, firstnameTextField, lastnameTextField, teamTextField, recordID,
    idInput, firstnameInput, lastnameInput, teamInput;
    @FXML private PasswordField inputPassword;
    @FXML private Button btConnectDB, btnRefreshTable, btnShowRecord, btnClearRecord, 
    submit, delete, first, previous, next, last, create, cancel, btnClrtaSQLResult;
    @FXML private TextArea tasqlCommand, taSQLResult;
    @FXML private TableView<ObservableList<Object>> tableView;
    @FXML private GridPane editGrid, submitGrid;

    @Override // Override the start method in the Application class
    public void initialize(URL url, ResourceBundle rb) {
        // Set the text for the comboURL label
        comboURL.setText("jdbc:mysql://localhost:3306/databasedb?");
        
        // Add event handlers for buttons
        btConnectDB.setOnAction(e -> connectToDB());
        btnRefreshTable.setOnAction(e -> processSQLSelect());
        btnClrtaSQLResult.setOnAction(e -> taSQLResult.setText(""));
        first.setOnAction(e -> navigateResultSet("first"));
        previous.setOnAction(e -> navigateResultSet("previous"));
        next.setOnAction(e -> navigateResultSet("next"));
        last.setOnAction(e -> navigateResultSet("last"));
        btnShowRecord.setOnAction(e -> navigateResultSet("show"));
        btnClearRecord.setOnAction(e -> navigateResultSet("clear"));
        submit.setOnAction(e -> updateRecord());
        delete.setOnAction(e -> deleteRecord());
        create.setOnAction(e -> createRecord());
        cancel.setOnAction(e -> {
            for (Node node : submitGrid.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).clear();
                }
            }
        });

        // add a shutdown hook to close the connection and result set
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (prpStmt != null) {
                    prpStmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
        
            }
        }));
    }

    /** Connect to DB */
    private void connectToDB() {
        // Get database information from the user input
        username = inputUsername.getText().trim();
        password = inputPassword.getText().trim();

        // Attempt connection to the database
        try {
            
            dataSource.setUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            con = dataSource.getConnection();

            // successfull connection message
            lblConnectionStatus.setText("Connected to " + url);
            processSQLSelect();
        }
        catch (java.lang.Exception ex) {
            taSQLResult.appendText(ex.toString()+ "\n");
            lblConnectionStatus.setText("Error Connecting to Database\n");
        } 
    }
    
    /** Execute SQL SELECT commands */
    private void processSQLSelect() {
        try {
            sqlCommand = "SELECT * FROM " + tableName;

            // Check if the connection to the database has been established
            if (con == null) {
                alert.setContentText("Connect to a database first");
                alert.showAndWait();
            }
            else{
                // Prepare a statement to execute the SELECT command on the database
                prpStmt = con.prepareStatement(sqlCommand, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
                // Execute a SELECT SQL command
                resultSet = prpStmt.executeQuery();
            }
        
            // Clear any existing data from the table view
            tableView.getItems().clear();
            tableView.getColumns().clear();
    
            // Create columns for each column in the result set
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                final int columnIndex = i;
                TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                // Add a value factory to the column to bind the column to data
                column.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get(columnIndex - 1)));
                tableView.getColumns().add(column);
            }
    
            // Add rows to the table view
            while (resultSet.next()) {
                ObservableList<Object> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    // Add each column to the row
                    row.add(resultSet.getObject(i));
                }
                // Add the row to the table view
                tableView.getItems().add(row);
            }
            // Populate grid pane with first record
            if (resultSet.first()) {
                populateGridPane(resultSet);
            }
            // Set the label text to display the current table name
            currentTable.setText("fans");
    
        } catch (SQLException ex) {
            taSQLResult.appendText(ex.toString()+ "\n");
        }
    }
    // Method to populate a GridPane with data from a current record in the ResultSet
    private void populateGridPane(ResultSet resultSet) {
        try {
            // Set the text of textfields to corresponding values in the ResultSet
            idTextField.setText(resultSet.getString("ID"));
            firstnameTextField.setText(resultSet.getString("firstname"));
            lastnameTextField.setText(resultSet.getString("lastname"));
            teamTextField.setText(resultSet.getString("favoriteteam"));
        } catch (SQLException ex) {
            taSQLResult.appendText(ex.toString()+ "\n");
        } 
    }
    // Method to navigate result set bases on direction parameter
    private void navigateResultSet(String direction) {
        try {
            switch (direction) {
                case "first": // Move to the first row in the result set
                    if (resultSet.first()) {
                        populateGridPane(resultSet);
                        recordID.setText("");
                    }
                    break;
                case "previous": // Move to the previous row in the result set
                    if (resultSet.previous()) {
                        populateGridPane(resultSet);
                        recordID.setText("");
                    } else { // If there is no previous row, move to the last row
                        resultSet.last();
                        populateGridPane(resultSet);
                        recordID.setText("");
                    }
                    break;
                case "next": // Move to the next row in the result set
                    if (resultSet.next()) {
                        populateGridPane(resultSet);
                        recordID.setText("");
                    } else { // If there is no next row, move to the first row
                        resultSet.first();
                        populateGridPane(resultSet);
                        recordID.setText("");
                    }
                    break;
                case "last": // Move to the last row in the result set
                    if (resultSet.last()) {
                        populateGridPane(resultSet);
                        recordID.setText("");
                    }
                    break;
                case "show":
                    try {  // Get the ID of the record to show
                        int id = Integer.parseInt(recordID.getText());
                        // Search the result set for the record with the specified ID
                        boolean found = resultSet.first();
                        while(found && resultSet.getInt("ID") != id) {
                            found = resultSet.next();
                        }
                        // If the record is found, populate the GridPane with its data
                        if(found) {
                            populateGridPane(resultSet);
                        } else {
                            taSQLResult.appendText("Record with ID " + id + " not found.\n");
                        }
                    } catch (NumberFormatException | SQLException ex) {
                        taSQLResult.appendText(ex.toString()+ "\n");
                    }
                    break;
                case "clear":
                    try {
                        // Move the cursor to the current row in the result set
                        resultSet.moveToCurrentRow();
                        
                        // Display the fields of the current row in the text fields
                        idTextField.setText(resultSet.getString("ID"));
                        firstnameTextField.setText(resultSet.getString("firstname"));
                        lastnameTextField.setText(resultSet.getString("lastname"));
                        teamTextField.setText(resultSet.getString("favoriteteam"));
                        
                        taSQLResult.appendText("Edit grid refreshed.\n");
                    } catch (SQLException ex) {
                        taSQLResult.appendText("Error refreshing edit grid.\n");
                        taSQLResult.appendText(ex.toString() + "\n");
                    }
                    break;
                default:
                    // Do nothing
                    return;
            }
        } catch (SQLException ex) {
            taSQLResult.appendText(ex.toString()+ "\n");
        } 
    }
    // method to update current record in result set with data entered in textfields
    private void updateRecord() {
        try {
            String id = idTextField.getText();
            String firstname = firstnameTextField.getText();
            String lastname = lastnameTextField.getText();
            String team = teamTextField.getText();

            // Check if any of the required fields are empty
            if (id.isEmpty() || firstname.isEmpty() || lastname.isEmpty()  || team.isEmpty()) {
                taSQLResult.appendText("Error updating record: all fields must be filled.\n");
                return;
            }

            // Check if the result set is in an updateable state
            if (resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY) {
                taSQLResult.appendText("Error updating record: result set is not updatable.\n");
                return;
            }
    
            // Move the cursor to the current row in the ResultSet
            resultSet.updateInt("ID", Integer.parseInt(id));
            resultSet.updateString("firstname", firstname);
            resultSet.updateString("lastname", lastname);
            resultSet.updateString("favoriteteam", team);

            // Update the row in the result set
            resultSet.updateRow();
            taSQLResult.appendText("Record with ID " + id + " updated successfully!\n");
    
        } catch (SQLException ex) {
            taSQLResult.appendText("Error updating record.\n");
            taSQLResult.appendText(ex.toString()+ "\n");
        }
    }
    // Method to delete record from database based on ID
    private void deleteRecord() {
        try {
            // Check if a record ID was entered
            if (recordID.getText().isEmpty()) {
                taSQLResult.appendText("Error, no record entered.\n");
                return;
            }
            // Get the ID of the record to be deleted
            int id = Integer.parseInt(recordID.getText());

            // Search for the record in the result set
            boolean found = resultSet.first();
            while(found && resultSet.getInt("ID") != id) {
                found = resultSet.next();
            }
            // If the record was found, prompt the user for confirmation before deleting it
            if(found) {
                // Display confirmation dialog box
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete record with ID: " + id + " ?");
                Optional<ButtonType> result = alert.showAndWait();

                // If the user confirms, delete the record from the result set
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Delete the current row from the ResultSet
                    resultSet.deleteRow();
                    resultSet.moveToCurrentRow();
                    populateGridPane(resultSet);
                    recordID.setText("");
                    // Update the UI to show that the record was deleted
                    taSQLResult.appendText("Record with ID " + id + " deleted successfully!\n");
                }
            } else {
                // If the record was not found, display an error message
                taSQLResult.appendText("Record with ID " + id + " not found.\n");
            }
            
        } catch (SQLException ex) {
            taSQLResult.appendText("Error deleting record.\n");
            taSQLResult.appendText(ex.toString()+ "\n");
        }
    }
    public void createRecord() {
        try {
            // Get the values from the input fields
            String id = idInput.getText();
            String firstname = firstnameInput.getText();
            String lastname = lastnameInput.getText();
            String team = teamInput.getText();

            // Validate if all fields have been filled
            if (id.isEmpty() || firstname.isEmpty() || lastname.isEmpty()  || team.isEmpty()) {
                taSQLResult.appendText("Error updating record: all fields must be filled.\n");
                return;
            }
            
            // Move the cursor to the insert row in the ResultSet
            resultSet.moveToInsertRow();
            
            // Set the column values for the new row
            resultSet.updateInt("ID", Integer.parseInt(id));
            resultSet.updateString("firstname", firstname);
            resultSet.updateString("lastname", lastname);
            resultSet.updateString("favoriteteam", team);
            
            // Insert the new row into the ResultSet and database
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            taSQLResult.appendText("Record with ID " + id + " inserted successfully!\n");

        } catch (SQLException ex) {
            taSQLResult.appendText("Error inserting record.\n");
            taSQLResult.appendText(ex.toString()+ "\n");
        }
    }
}

