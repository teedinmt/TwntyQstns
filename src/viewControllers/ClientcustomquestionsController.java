/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package viewControllers;

import dao.ClientdataCustomDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import twentyquestions.TwentyQuestions;

public class ClientcustomquestionsController implements Initializable {
    @FXML
    private TextArea answer1text;
    @FXML
    private TextArea answer2text;
    @FXML
    private TextField answer3text;

    @FXML
    private TextField answer4text;
    @FXML
    private TextField answer5text;
    @FXML
    private TextArea answer6text;
    @FXML
    private TextArea answer7text;


    @FXML
    private Label question1text;
    @FXML
    private Label question2text;
    @FXML
    private Label question3text;
    @FXML
    private Label question4text;
    @FXML
    private Label question5text;

    @FXML
    private Label question6text;
    @FXML
    private Label question7text;
    
    String question1;
    String question2;
    String question3;
    String question4;
    String question5;
    String question6;
    String question7;
    @FXML
    private Button submitApplicationButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set the current custom questions to the screen 
           ResultSet listcustomquestions = ClientdataCustomDAO.getCustomQuestions();
        try {
            while (listcustomquestions.next()) {
                question1 = listcustomquestions.getString("question1");
                question2 = listcustomquestions.getString("question2");
                question3 = listcustomquestions.getString("question3");
                question4 = listcustomquestions.getString("question4");
                question5 = listcustomquestions.getString("question5");
                question6 = listcustomquestions.getString("question6");
                question7 = listcustomquestions.getString("question7");
            }
        } catch (SQLException ex) {
            System.out.println("There has been an error getting questions from the database" +ex);
            
        }
 
       
        //Data to Screen
        question1text.setText(question1);
        question2text.setText(question2);
        question3text.setText(question3);
        question4text.setText(question4);
        question5text.setText(question5);
        question6text.setText(question6);
        question7text.setText(question7);
        
   

    }

    
    @FXML
    public void submitApplication(ActionEvent event) throws SQLException, IOException {
      saveCustomQuestionAnswers();      
     
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/thankyouclose.fxml"));
            Parent reportmain = loader.load();
          Scene scene = new Scene(reportmain);
            Stage stage = TwentyQuestions.getStage();
            stage.setScene(scene);
            stage.show();

    }

    private void backToPage2(ActionEvent event) throws IOException {
       
         FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/clientscreen2.fxml"));
           Parent reportmain = loader.load();
            Scene scene = new Scene(reportmain);
            Stage stage = TwentyQuestions.getStage();
            stage.setScene(scene);
            stage.show();
       
    }

    private void saveCustomQuestionAnswers() throws SQLException {
        String answer1 = answer1text.getText();
        String answer2 = answer2text.getText();
         String answer3 = answer3text.getText();
         String answer4 = answer4text.getText();
         String answer5 = answer5text.getText();
         String answer6 = answer6text.getText();
         String answer7 = answer7text.getText();
        
       ClientdataCustomDAO.insertClientAnswers(answer1, answer2, answer3, answer4, answer5, answer6, answer7);
    }


}
