/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package twentyquestions;

import dao.DBConnection;
import dao.LocalCustomization;
import dao.userDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController implements Initializable {

    @FXML
    private Label textWelcome;
    @FXML
    private Button startnewapplication;
    @FXML
    private Button Login_Button;
    @FXML
    private TextField PasswordTextField;
    @FXML
    private TextField UserNameTextField;
    @FXML
    private Label UserNameLabel;
    @FXML
    private Label PasswordLabel;

    @FXML
    private Label connectionNOTsuccessful;
    @FXML
    private TextArea customtextarea;
    @FXML
    private Button exiterrorbutton;
    @FXML
    private Button ExitProgramButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TwentyQuestions.getStage().setTitle("Twenty Questions");

        try {
//checking to see if the db pathway and PDF file exists if not, show error screen
//and do not allow client to continue since data would not be saved
            String foldercheck = LocalCustomization.PATHTOGETPDF;
            String localdb = LocalCustomization.PATHTODB;
            String rpfoldercheck = LocalCustomization.PATHTOSAVEPDF;
            File file = new File(localdb);
            File path = new File(foldercheck);
            File rpath = new File(rpfoldercheck);
            boolean dbexists = file.exists();
            boolean pathexists = path.exists();
            boolean reportspathexists = rpath.exists();                      

            if (!pathexists) {
                //sets all normal elements to invisible
                ExitProgramButton.setVisible(false);
                textWelcome.setVisible(false);
                startnewapplication.setVisible(false);
                Login_Button.setVisible(false);
                PasswordTextField.setVisible(false);
                UserNameTextField.setVisible(false);
                UserNameLabel.setVisible(false);
                PasswordLabel.setVisible(false);
               //show only error information and EXIT button
                connectionNOTsuccessful.setVisible(true);
                customtextarea.setVisible(true);
                customtextarea.setText(LocalCustomization.CUSTOMERROR1);
                exiterrorbutton.setVisible(true);
                System.out.println(" MISSING PATH: The location " + foldercheck + " was not found");

            } else if (!dbexists) {
                System.out.println("MISSING FILE: The database file " + localdb + " was NOT found ");
                TwentyQuestions.getStage().setTitle("Twenty Questions");
                //again set normal elements to invisible
                ExitProgramButton.setVisible(false);
                textWelcome.setVisible(false);
                startnewapplication.setVisible(false);
                Login_Button.setVisible(false);
                PasswordTextField.setVisible(false);
                UserNameTextField.setVisible(false);
                UserNameLabel.setVisible(false);
                PasswordLabel.setVisible(false);
                 //show custom error message 2
                connectionNOTsuccessful.setVisible(true);
                customtextarea.setVisible(true);
                customtextarea.setText(LocalCustomization.CUSTOMERROR2);
                exiterrorbutton.setVisible(true);

            } 
            
            else if (!reportspathexists) {
                //set all normal elements to invisible
                ExitProgramButton.setVisible(false);
                textWelcome.setVisible(false);
                startnewapplication.setVisible(false);
                Login_Button.setVisible(false);
                PasswordTextField.setVisible(false);
                UserNameTextField.setVisible(false);
                UserNameLabel.setVisible(false);
                PasswordLabel.setVisible(false);
                //show custom error message 3
                connectionNOTsuccessful.setVisible(true);
                customtextarea.setVisible(true);
                customtextarea.setText(LocalCustomization.CUSTOMERROR3);
                exiterrorbutton.setVisible(true);
                System.out.println(" MISSING PATH: The location for saving files, " + rpath + " was not found");
            }
             
             else {
                try {//making connection to database
                    System.out.println("\nCHECK PATH: The " + foldercheck + " pathway exists");
                    System.out.println("\n CHECK FILE: The database file " + localdb + " was found");
                    //start the program as normal
                    TwentyQuestions.getStage().setTitle("Twenty Questions");
                    DBConnection.makeDBConnection();
                    connectionNOTsuccessful.setVisible(false);
                    customtextarea.setVisible(false);
                    exiterrorbutton.setVisible(false);

                } catch (Exception ex) {
                    System.out.println("\n A Database connection could not be made\n");
                    System.out.println("Note from TwentyQuestions.java here. Error:   " + ex.getMessage());
                }
            }

        } catch (Exception e) {

            System.out.println("Error in LoginPageController class -- " + e);
        }

    }

    @FXML
    private void LoginButton(ActionEvent event) throws SQLException, IOException, Exception {

        // connect to database
        DBConnection.makeDBConnection();

        //2 check staff user login
        String username = UserNameTextField.getText();
        String password = PasswordTextField.getText();
        ResultSet rslogin = userDAO.UserLogin(username, password);

        if (rslogin.next()) {
            Parent main = null;
            main = FXMLLoader.load(getClass().getResource("/view/staffScreen.fxml"));
            Scene scene = new Scene(main);
            Stage stage = TwentyQuestions.getStage();
            stage.setScene(scene);
            stage.show();
            System.out.println("\nA Staff User Login from aestaff was successful\n");

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Unsuccessful");
            alert.setHeaderText("User was not able to log in");
            alert.setContentText("Check username or password");
            alert.showAndWait();
        }
        //TODO  Here is where we need to also add an error message if the database
        //simply doesn't connect. Right now the program pauses but shows no error.
    }

    @FXML
    private void startNewApplication(ActionEvent event) throws IOException {
        Parent main = null;
        main = FXMLLoader.load(getClass().getResource("/view/clientscreen1.fxml"));
        Scene scene = new Scene(main);
        Stage stage = TwentyQuestions.getStage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void errorExitProgram(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        try {
            DBConnection.closeDBConnection(); //closing the database
        } catch (Exception ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
