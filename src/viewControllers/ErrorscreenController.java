/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package viewControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class ErrorscreenController implements Initializable {

    @FXML
    private Label errorscreentext1;
    @FXML
    private Button exitbuttononerrorscreen;
    @FXML
    private Label errorscreentext2;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //  TwentyQuestions.getStage().setTitle("Twenty Questions");
    
    }    

    @FXML
    private void ExitProgram(ActionEvent event) {
         System.exit(0);
    }
    
}
