/*ShapeDisplay.java
* Module 7 Assignment 
* Name: Brittany Kyncl
* Date: 4.15.23
* Course: CSD420
* Program to load FXML file, create scene containing UI as root node, add external css stylesheet, and add to primary stage then launch ShapeDisplay application
* application displays shapes, containers, and controls in UI layout described in shapes.fxml file.
*/
package shapedisplay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShapeDisplay extends Application {

    // start method to set up initial stage and scene, load FXML file, then called when application is launched 
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Use FXML loader to load shapes.fxml which describes UI layout and assign to root node
        Parent root = FXMLLoader.load(getClass().getResource("shapes.fxml"));

        // create new scene containing UI as root node
        Scene scene = new Scene(root);

        // adding mystyle.css stylesheet
        scene.getStylesheets().add("shapedisplay/mystyle.css");

        // set title of window
        primaryStage.setTitle("CSS Styling of JavaFX ShapeDisplay Program");
        // set scene to primary stage
        primaryStage.setScene(scene);

        // Setting the minimum and maximum height and width of the application window to maintain display 
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(540);
        primaryStage.setMinHeight(540);
        primaryStage.setMinWidth(700);

        // show window
        primaryStage.show();
    }

    public static void main(String[] args) {
        // launch application
        launch(args);
    }
}
