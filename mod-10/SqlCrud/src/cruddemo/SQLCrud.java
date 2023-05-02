/*SQLCrud.java
* Module 10 Assignment 
* Name: Brittany Kyncl
* Date: 4.28.23
* Course: CSD420
* 
*/

package cruddemo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SQLCrud extends Application {

    public static void main(String[] args) {
        // launch application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use FXML loader to load sqlclient.fxml which describes UI layout and assign to root node
        Parent root = FXMLLoader.load(getClass().getResource("sqlcrud.fxml"));

        // create new scene containing UI as root node
        Scene scene = new Scene(root);


        // set title of window
        primaryStage.setTitle("SQL Client Demo");
        // set scene to primary stage
        primaryStage.setScene(scene);
 
        primaryStage.setResizable(true);


        // show window
        primaryStage.show();
    }
    
}


