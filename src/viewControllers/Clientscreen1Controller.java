/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package viewControllers;

import dao.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import twentyquestions.TwentyQuestions;

public class Clientscreen1Controller implements Initializable {

    @FXML
    private TextField fName;
    @FXML
    private TextField mName;
    @FXML
    private TextField lName;
    @FXML
    private ChoiceBox<String> asuffix;

    @FXML
    private TextField addresstext;
    @FXML
    private TextField citytext;
    @FXML
    private TextField statetext;
    @FXML
    private TextField postalCodetext;
    @FXML
    private TextField countytext;
    @FXML
    private TextField homePhonetext;

    @FXML
    private TextField emailtext;
    @FXML
    private TextField mobilePhonetext;
    @FXML
    private TextField workPhonetext;
    @FXML
    private ChoiceBox<String> contactPreferencetext;
    @FXML
    private TextField ssnumbertext;

    @FXML
    private ToggleGroup Male_Female;
    @FXML
    private RadioButton genderM;
    @FXML
    private RadioButton genderF;

    @FXML
    private ToggleGroup Yes_No;
    @FXML
    private RadioButton hispanicY;
    @FXML
    private RadioButton hispanicN;

    @FXML
    private CheckBox white;
    @FXML
    private CheckBox asian;
    @FXML
    private CheckBox am_indian_AK_native;
    @FXML
    private CheckBox black_af_american;
    @FXML
    private CheckBox hawaiian_pacific_islander;

    @FXML
    private TextField emContactName;
    @FXML
    private TextField emContactPhone;
    @FXML
    private TextField emContactAddress;
    @FXML
    private ChoiceBox<String> contactRelationship;

    @FXML
    private Button ExitButton;
    @FXML
    private Button saveAndContinueScreen1;
    @FXML
    private Label pleasecomplete2;
    @FXML
    private Label pleaseincludeonephone3;
    @FXML
    private Label ssnumberrequired4;
    @FXML
    private Label pleasechooseone5;
    @FXML
    private Label pleasechooseone6;
    @FXML
    private Label pleasecomplete7;
    @FXML
    private Label pleasechooseone8;
    @FXML
    private Label emailisrequired;

    @FXML
    private Label phonenumbertext;
    @FXML
    private Label ssnumberformat;
    @FXML
    private Label chooseapreference;
    @FXML
    private Label selectonerelationship;
    @FXML
    private ChoiceBox<String> choosemonth;
    @FXML
    private ChoiceBox<String> chooseday;
    @FXML
    private ChoiceBox<String> chooseyear;

    String dateOfbirth;
    public static String adateOfbirth;
    ObservableList<String> suffixes = FXCollections.observableArrayList();
    ObservableList<String> contactPreferences = FXCollections.observableArrayList();
    ObservableList<String> contactRelationships = FXCollections.observableArrayList();
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<String> days = FXCollections.observableArrayList();
    ObservableList<String> years = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            setScreen1Data();
        } catch (Exception e) {
            System.out.print("/nThere has been an error in setting the data to the customer info screen" + e);
        }
    }

    public void setScreen1Data() {
        suffixes.addAll("Jr.", "Sr.", " ");

        //Example of customizing this screen: 
        //customization for Xxxxxx Program:  only allow "Email Only" or "Text Only" in contactPreferences List
        //contactPreferences.addAll("Email Only", "Text Only");
        contactPreferences.addAll("Any Phone or Time", "Email Only", "Home Only", "Mobile Phone", "Text Only");
        contactRelationships.addAll("spouse", "parent", "sibling", "friend", "child", "co-worker", "other");
        months.addAll("Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec");
        days.addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        years.addAll("2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990",
                "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980",
                "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970",
                "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960",
                "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950");

//TODO Note: Currently there is no check for not allowing to enter 31 days for months without 31 days!
        ToggleGroup Yes_No = new ToggleGroup();
        ToggleGroup Male_Female = new ToggleGroup();

        asuffix.setItems(suffixes);
        contactPreferencetext.setItems(contactPreferences);
        contactRelationship.setItems(contactRelationships);
        choosemonth.setItems(months);
        chooseday.setItems(days);
        chooseyear.setItems(years);

        //hidden fields for data validation
        pleasecomplete2.setVisible(false);
        pleaseincludeonephone3.setVisible(false);
        ssnumberrequired4.setVisible(false);
        pleasechooseone5.setVisible(false);
        pleasechooseone6.setVisible(false);
        pleasecomplete7.setVisible(false);
        pleasechooseone8.setVisible(false);
        phonenumbertext.setVisible(false);
        ssnumberformat.setVisible(false);
        emailisrequired.setVisible(false);

        selectonerelationship.setVisible(false);
        chooseapreference.setVisible(false);

    }

    @FXML
    private void continueFromPage1(ActionEvent event) throws IOException, SQLException {

        if (addresstext.getText().isEmpty() || citytext.getText().isEmpty() || statetext.getText().isEmpty() || postalCodetext.getText().isEmpty() || countytext.getText().isEmpty()) {
            pleasecomplete2.setVisible(true);
        } else if (fName.getText().isEmpty() || lName.getText().isEmpty()) {
            pleasecomplete2.setVisible(true);
        } else if (mobilePhonetext.getText().isEmpty() && workPhonetext.getText().isEmpty() && homePhonetext.getText().isEmpty()) {
            pleaseincludeonephone3.setVisible(true);
        } else if (!homePhonetext.getText().isEmpty() && !homePhonetext.getText().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            phonenumbertext.setVisible(true);
        } else if (!mobilePhonetext.getText().isEmpty() && !mobilePhonetext.getText().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            phonenumbertext.setVisible(true);
        } else if (!workPhonetext.getText().isEmpty() && !workPhonetext.getText().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            phonenumbertext.setVisible(true);
        } else if (contactPreferencetext.getValue() == null) {
            chooseapreference.setVisible(true);
        } else if (emailtext.getText().isEmpty()) {
            emailisrequired.setVisible(true);
        } else if (!emContactPhone.getText().isEmpty() && !emContactPhone.getText().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            phonenumbertext.setVisible(true);
        } else if (contactRelationship.getValue() == null) {
            selectonerelationship.setVisible(true);
        } else if (!white.isSelected() && !asian.isSelected() && !black_af_american.isSelected() && !am_indian_AK_native.isSelected() && !hawaiian_pacific_islander.isSelected()) {
            pleasechooseone6.setVisible(true);
        } else if (emContactName.getText().isEmpty() || emContactPhone.getText().isEmpty() || emContactAddress.getText().isEmpty()) {
            pleasecomplete7.setVisible(true);
        } else if (ssnumbertext.getText().isEmpty()) {
            ssnumberrequired4.setVisible(true);
        } else if (!ssnumbertext.getText().matches("\\d{3}[-\\.\\s]\\d{2}[-\\.\\s]\\d{4}")) {
            ssnumberformat.setVisible(true);
        } else if (!genderM.isSelected() && !genderF.isSelected()) {
            pleasechooseone5.setVisible(true);
        } else if (!hispanicY.isSelected() && !hispanicN.isSelected()) {
            pleasechooseone8.setVisible(true);
        } else {
            //all "IF" verification needs run BEFORE this is ever initiated
            saveRaceData2();
            saveRaceData1();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/clientscreen2.fxml"));
            Parent reportmain = loader.load();

            //controller for passing client from screen1
            Clientscreen2Controller controller = loader.getController();

            controller.transferClientCoreInfo(fName.getText(), mName.getText(), lName.getText(), asuffix.getValue(),
                    ssnumbertext.getText(), choosemonth.getValue(), chooseday.getValue(), chooseyear.getValue(), mobilePhonetext.getText(), workPhonetext.getText(),
                    emailtext.getText(), contactPreferencetext.getValue());
            controller.transferAddressInfo(addresstext.getText(), citytext.getText(), statetext.getText(),
                    postalCodetext.getText(), countytext.getText(), homePhonetext.getText());
            controller.transferEmContactInfo(emContactName.getText(), emContactPhone.getText(),
                    emContactAddress.getText(), contactRelationship.getValue());
            controller.transferRaceData2(Yes_No.getSelectedToggle().toString(), Male_Female.getSelectedToggle().toString());
            //  controller.transferRaceData2(hispanicRB.getText(), genderRB.getText());//  (this throws a null pointer error)
            controller.transferRaceData1(selectedraces);
            Scene scene = new Scene(reportmain);
            Stage stage = TwentyQuestions.getStage();
            stage.setScene(scene);
            stage.show();
        }
    }

    RadioButton hispanicRB;
    RadioButton genderRB;

    private void saveRaceData2() {
        RadioButton hispanicRB = (RadioButton) Yes_No.getSelectedToggle();
        RadioButton genderRB = (RadioButton) Male_Female.getSelectedToggle();

    }

    //race data is save into an array to pass to screen 2 controller
    String race1;
    String race2;
    String race3;
    String race4;
    String race5;
    ArrayList<String> selectedraces = new ArrayList<String>();

    private void saveRaceData1() throws SQLException {

        if (white.isSelected()) {
            String race1 = "White";
            selectedraces.add(race1);
        }
        if (asian.isSelected()) {
            String race2 = "Asian";
            selectedraces.add(race2);
        }
        if (am_indian_AK_native.isSelected()) {
            String race3 = "American Indian/Alaska Native";
            selectedraces.add(race3);
        }
        if (black_af_american.isSelected()) {
            String race4 = "Black/African American";
            selectedraces.add(race4);
        }
        if (hawaiian_pacific_islander.isSelected()) {
            String race5 = "Native Hawaiian/other Pacific Islander";
            selectedraces.add(race5);

        } else { // prompt user to make a choice     
            pleasechooseone6.setVisible(true);
        }
        // System.out.println("selectedraces in Screen 1 : " + selectedraces);
    }

    @FXML
    private void exitProgramonMain(ActionEvent event) throws SQLException {
        try {
            DBConnection.closeDBConnection(); //closing the database
        } catch (Exception ex) {
            Logger.getLogger(Clientscreen1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
