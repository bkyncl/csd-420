/*MainController.java
* Module 7 Assignment 
* Name: Brittany Kyncl
* Date: 4.15.23
* Course: CSD420
* MainController class to manage display of circles and stackpanes and user interactions of button and togglegroup
* containing methods for updating stackpane styles based on mouse events
*/
package shapedisplay;

import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

// The MainController class implements the Initializable interface,
// Manages the display and user interactions of the button, togglegroup, and four circles and their stackpane containers
public class MainController implements Initializable {

    // using @FXML annotation to inject the GUI elements defined in the shapes.fxml 
    // into instance variables of the MainController class.
    @FXML
    private Circle circle1, circle2, circle3, circle4;
    @FXML
    private StackPane stackpane1, stackpane2, stackpane3, stackpane4;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton option1, option2;
    @FXML
    private Button button2;

    // dynamicDisplay boolean to keep track of user selected dynamic display mode
    private Boolean dynamicDisplay = false;

    // lists for storing circles and stackpane instances to be used for applying styleclass changes via iteration
    List<Circle> circles;
    List<StackPane> panes;

    // method to update styleclass of stackpanes based on mouseEnter/mouseExit
    // current mouse hovered circle and boolean exit paremter to for current hovered circle and mouse entry/exit
    private void updateCircleHoverState(Circle hoveredCircle, Boolean exit) {
        // iterate over circles and stackpane containers to add bordery style to active stackpane container of hoverdcircle
        // and plainstackpain style to innactive containers
        for (int i = 0; i < circles.size(); i++) {
            Circle circle = circles.get(i);
            StackPane pane = panes.get(i);
            // applying changes to circle with enter/exit activity
            if (circle == hoveredCircle) {
                // if mouse exit remove border style and add plainstackpain style
                if (exit) {
                    // check current assigned styleclass to avoid adding duplicates
                    if (!pane.getStyleClass().contains("plainstackpain")) { 
                        pane.getStyleClass().add("plainstackpain");
                    }
                    pane.getStyleClass().remove("border");
                    //System.out.println("Exit: " + pane); // for testing correct styleclass change functionality
                } else {
                    // if mouse enter add border style and remove plainstackpain style
                    // check current assigned styleclass to avoid adding duplicates
                    if (!pane.getStyleClass().contains("border")) {
                        pane.getStyleClass().add("border");
                    }
                    pane.getStyleClass().remove("plainstackpain");
                    // System.out.println("Enter: " + pane); // for testing correct styleclass change functionality
                }
            // applying changes to remaining innactive circles and their stackpane container
            } else {
                if (!pane.getStyleClass().contains("plainstackpain")) {
                    pane.getStyleClass().add("plainstackpain");
                }
                pane.getStyleClass().remove("border");
                //System.out.println("Inactive: " + pane); // for testing correct styleclass change functionality
            }
        }
    }

    // method to update styleclass of stackpane containers based on mouseClick event of circle
    // current clickedcircle as paremter
    private void updateCircleClickState(Circle clickedCircle) {
        // Removing plainstackpain style from all stackpane containers before applying new style changes based on click state
        // purpose to avoid adding unnecessary duplicate styleclasses
        for (StackPane pane : panes) {
            pane.getStyleClass().remove("plainstackpain");
        }
        
        // iterate over circles and stackpane containers to add bordery style to active stackpane container of clickedcircle
        // and plainstackpain style to innactive containers
        for (int i = 0; i < circles.size(); i++) {
            Circle circle = circles.get(i);
            StackPane pane = panes.get(i);
            // if circle is clickedcircle add border style
            if (circle == clickedCircle) {
                pane.getStyleClass().add("border");
                //System.out.println("clicked: " + pane); // for testing correct styleclass change functionality
            // add plainstackpain style to innactive containers                                                                                                  
            } else {
                pane.getStyleClass().add("plainstackpain");
                //System.out.println("n/c: " + pane); // for testing correct styleclass change functionality
            }
        }
    }
    // overrided initialize method of the initializble class called when GUI is loaded
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ititialize lists with circle and stackpane instances
        circles = Arrays.asList(circle1, circle2, circle3, circle4);
        panes = Arrays.asList(stackpane1, stackpane2, stackpane3, stackpane4);
        
        // for loop to add mouse event listeners to each circle
        // functionality includes mouse enter/exit and mouseclick event listeners for each circle
        for (Circle circle : circles) {
            circle.setOnMouseEntered(event -> {
                // check for enabled dynamicDisplay before adding updateCircleHoverState() method for mouse enter
                if (dynamicDisplay) {
                    updateCircleHoverState(circle, false);
                }
            });
            circle.setOnMouseExited(event -> {
                // check for enabled dynamicDisplay before adding updateCircleHoverState() method for mouse exit
                if (dynamicDisplay) {
                    updateCircleHoverState(circle, true);
                }
            });
            circle.setOnMouseClicked(event -> {
                // check for enabled dynamicDisplay before adding updateCircleClickState() method for mouse eclick
                if (dynamicDisplay) {
                    updateCircleClickState(circle);
                }
            });
        }

        // Setting default toggle option as option1 = static display
        toggleGroup.selectToggle(option1);

        // disable dynamicdisplay for option1= static display
        option1.setOnAction(event -> {
            dynamicDisplay = false;
            // resetting style of all stackpane styles for static display to initial default display
            for (StackPane pane : panes) {
                pane.getStyleClass().remove("border");
            }
            stackpane1.getStyleClass().add("border");
        });
        // enable dynamic display for option2 = dynamic display
        option2.setOnAction(event -> {
            dynamicDisplay = true;
        });

        // adding event listener to button1 = exit button to close application when clicked
        button2.setOnAction(event -> Platform.exit());
    }
}
