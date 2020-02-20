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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import twentyquestions.TwentyQuestions;


public class StaffcustomquestionsController implements Initializable {

    @FXML
    private Label question1label;
    @FXML
    private Label question2label;
    @FXML
    private Label question3label;
    @FXML
    private Label question4label;
    @FXML
    private Label question5label;
    @FXML
    private Label question6label;
    @FXML
    private Label question7label;
    @FXML
    private TextField question1text;
    @FXML
    private TextField question2text;
    @FXML
    private TextField question3text;
    @FXML
    private TextField question4text;
    @FXML
    private TextField question5text;
    @FXML
    private TextField question6text;
    @FXML
    private TextArea question7text;
    @FXML
    private Label labelexplainingq7;
    @FXML
    private Button setQuestionsButton;
    @FXML
    private Button returntoStaffScreenButton;
    int questionID;
    int questionset =0;
    String question1;
    String question2;
    String question3;
    String question4;
    String question5;
    String question6;
    String question7;
    @FXML
    private RadioButton clearallradiobutton;
    @FXML
    private ToggleGroup showhidequestions;
    @FXML
    private RadioButton showquestionsradiobutton;
    @FXML
    private RadioButton previousquestionsradiobutton;
    @FXML
    private Label noprevioustext;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       //set the current custom questions to the screen 
       ToggleGroup showhidquestions = new ToggleGroup();
       noprevioustext.setVisible(false);
      
    }

    @FXML
    private void setQuestionstoClientScreen(ActionEvent event) throws SQLException, IOException {
        question1 = question1text.getText();
        question2 = question2text.getText();
        question3 = question3text.getText();
        question4 = question4text.getText();
        question5 = question5text.getText();
        question6 = question6text.getText();
        question7 = question7text.getText();
        ClientdataCustomDAO.setCustomQuestions(question1, question2, question3, question4, question5, question6, question7);
        
           Parent reportmain = null;
            reportmain = FXMLLoader.load(getClass().getResource("/view/staffcustomscreenPreview.fxml"));
            Scene scene = new Scene(reportmain);
            Stage stage = TwentyQuestions.getStage();
            stage.setScene(scene);
            stage.show();
        
    }

    @FXML
    private void returntoStaffScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/staffScreen.fxml"));
        Parent reportmain = loader.load();
        Scene scene = new Scene(reportmain);
        Stage stage = TwentyQuestions.getStage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void showCurrentQuestions(ActionEvent event) {
         //set the current custom questions to the screen 
           ResultSet listcustomquestions = ClientdataCustomDAO.getCustomQuestions();
        try {
            while (listcustomquestions.next()) {
                questionID = listcustomquestions.getInt("questionID");
                questionset = questionID;
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
            noprevioustext.setVisible(true);
        }
        question1text.setText(question1);
        question2text.setText(question2);
        question3text.setText(question3);
        question4text.setText(question4);
        question5text.setText(question5);
        question6text.setText(question6);
        question7text.setText(question7);
    }

    @FXML
    private void clearAllQuestions(ActionEvent event) {
        
         question1text.clear();
        question2text.clear();
        question3text.clear();
        question4text.clear();
        question5text.clear();
        question6text.clear();
        question7text.clear();
        
    }

    @FXML
    private void getPreviousQuestions(ActionEvent event) {
          //set the current custom questions to the screen 
          
          if (questionset == 0){
              noprevioustext.setVisible(true);
          }
          else {
              questionID = questionset -1;
           ResultSet listquestionsprevious = ClientdataCustomDAO.getPreviousQuestions(questionID);
        try {
            while (listquestionsprevious.next()) {
               
                question1 = listquestionsprevious.getString("question1");
                question2 = listquestionsprevious.getString("question2");
                question3 = listquestionsprevious.getString("question3");
                question4 = listquestionsprevious.getString("question4");
                question5 = listquestionsprevious.getString("question5");
                question6 = listquestionsprevious.getString("question6");
                question7 = listquestionsprevious.getString("question7");
            }
        } catch (SQLException ex) {
            System.out.println("There has been an error getting questions from the database" +ex);
            noprevioustext.setVisible(true);
        }
        question1text.setText(question1);
        question2text.setText(question2);
        question3text.setText(question3);
        question4text.setText(question4);
        question5text.setText(question5);
        question6text.setText(question6);
        question7text.setText(question7);
    }

    }
    
    
    
    
}
