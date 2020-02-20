/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package twentyquestions;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static twentyquestions.TwentyQuestions.stage;


public class TwentyQuestions extends Application {

    static Stage stage;

    @Override   
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
            main = loader.load();
            Scene scene = new Scene(main);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("\n Twenty Questions java starting...  ERROR loading login page\n" + ex.getMessage());
        }
      
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) throws IOException {   
   
     launch(args);

    }

  
}
