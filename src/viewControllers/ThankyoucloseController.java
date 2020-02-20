/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package viewControllers;

import dao.DBConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class ThankyoucloseController implements Initializable {

    @FXML
    private Button exitbuttonlastscreen;
    @FXML
    private Label thankyouatend;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void exitprogramlastscreen(ActionEvent event)throws SQLException {
        try {
            DBConnection.closeDBConnection(); //closing the database
        } catch (Exception ex) {
            Logger.getLogger(Clientscreen1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    
}
