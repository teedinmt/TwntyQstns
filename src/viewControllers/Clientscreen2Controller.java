/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package viewControllers;

import dao.Clientdata1DAO;
import dao.Clientdata2DAO;
import dao.DBConnection;
import dao.LocalCustomization;
import dao.reportsDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import twentyquestions.TwentyQuestions;

public class Clientscreen2Controller implements Initializable {

    @FXML
    private ComboBox<String> pg2studentType;
    @FXML
    private ChoiceBox<String> pg2HearAboutUs;
    @FXML
    private TextField pg2PreviousPrograms;
    @FXML
    private TextField pg2lastSchoolAttended;

    @FXML
    private ChoiceBox<String> pg2EducationCompleted;
    @FXML
    private ToggleGroup US_NonUS;
    @FXML
    private RadioButton pg2EducationUS;
    @FXML
    private RadioButton pg2EducationNonUS;
    @FXML
    private ComboBox<String> pg2EmpStatus;

    @FXML
    private TextField pg2ListedDisability;

    @FXML
    private Button continuetopage3Button;

    @FXML
    private CheckBox cultural;
    @FXML
    private CheckBox disabled;
    @FXML
    private CheckBox migrant_worker;
    @FXML
    private CheckBox displaced;
    @FXML
    private CheckBox foster_youth;
    @FXML
    private CheckBox homeless;
    @FXML
    private CheckBox english_lang_learner;
    @FXML
    private CheckBox ex_offender;
    @FXML
    private CheckBox seasonal_worker;
    @FXML
    private CheckBox long_term_unemployed;
    @FXML
    private CheckBox public_assistance;
    @FXML
    private CheckBox emancipated_minor;
    @FXML
    private CheckBox exhausting_TANF;
    @FXML
    private CheckBox single_parent;
    @FXML
    private CheckBox low_literacy;

    @FXML
    private Label choosestudenttype;
    @FXML
    private Label choosehighestlevelcompleted;
    @FXML
    private Label chooseemploymentstatus;
    @FXML
    private Button ExitProgramButton;

    ObservableList<String> statusList = FXCollections.observableArrayList();
    ObservableList<String> typeList = FXCollections.observableArrayList();
    ObservableList<String> completedList = FXCollections.observableArrayList();
    ObservableList<String> hearaboutusList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setScreen2Data();
        } catch (Exception e) {
            System.out.print("/nThere has been an error in setting the data to the customer info screen" + e);
        }
    }

    public void setScreen2Data() {
        statusList.addAll("Employed Full Time", "Employed Part Time", "Unemployed", "Not Looking for Work",
                "Unavailable for Work", "Retired", "Employed with Separation Notice");
        typeList.addAll("New", "Continuing", "Returning");
        completedList.addAll("No Schooling", "Kindergarten", "Grades 1-5", "Grades 6-8", "Grades 9-12", "Secondary School Diploma",
                "Secondary School Equivalent(GED/HISET)", "Unknown", "Some Postsecondary Education, No Degree", "Postsecondary or Professional Degree");
        hearaboutusList.addAll("Newspaper", "Radio", "Website", "Friend/Family member", "High School", "Flyer / Poster", "Community Agency", "Other");

        pg2EmpStatus.setItems(statusList);
        pg2EducationCompleted.setItems(completedList);
        pg2studentType.setItems(typeList);
        pg2HearAboutUs.setItems(hearaboutusList);

        //hidden fields for validation later      
        choosestudenttype.setVisible(false);
        choosehighestlevelcompleted.setVisible(false);
        chooseemploymentstatus.setVisible(false);

    }

    //***  Receiving data from screen1's controller
    private void formatdOfbirth(String bmonth, String bday, String byear) {
        nmonth = "00/";
        if (bmonth.equals("Jan")) {
            nmonth = ("01/");
        }
        if (bmonth.equals("Feb")) {
            nmonth = "02/";
        }
        if (bmonth.equals("March")) {
            nmonth = "03/";
        }
        if (bmonth.equals("April")) {
            nmonth = "04/";
        }
        if (bmonth.equals("May")) {
            nmonth = "05/";
        }
        if (bmonth.equals("June")) {
            nmonth = "06/";
        }
        if (bmonth.equals("July")) {
            nmonth = "07/";
        }
        if (bmonth.equals("Aug")) {
            nmonth = "08/";
        }
        if (bmonth.equals("Sept")) {
            nmonth = "09/";
        }
        if (bmonth.equals("Oct")) {
            nmonth = "10/";
        }
        if (bmonth.equals("Nov")) {
            nmonth = "11/";
        }
        if (bmonth.equals("Dec")) {
            nmonth = "12/";
        }
        adateOfbirth = nmonth + bday + "/" + byear;
    }

    private String afirstname;
    private String amiddlename;
    private String alastname;
    private String asocialsecurity;
    public String adateOfbirth;
    private String bmonth;
    private String nmonth; //month as two digits for report purposes
    private String bday;
    private String byear;
    private String asuffix;
    private String amobilephone;
    private String aworkphone;
    private String anemail;
    private String acontactpreference;

    public void transferClientCoreInfo(String firstname, String middlename, String lastname,
            String bsuffix, String socialsecurity, String birthmonth, String birthday, String birthyear, String mobilephone, String workphone, String email, String contactpreference) {
        afirstname = firstname;
        amiddlename = middlename;
        alastname = lastname;
        asocialsecurity = socialsecurity;
        bmonth = birthmonth;
        bday = birthday;
        byear = birthyear;
        asuffix = bsuffix;
        amobilephone = mobilephone;
        aworkphone = workphone;
        anemail = email;
        acontactpreference = contactpreference;
        formatdOfbirth(bmonth, bday, byear);
        //TEST
        // System.out.print("\n\nNew Client Info passed in from Screen 1:  " + afirstname + " " + alastname + "   ss: "
        // + asocialsecurity + " \n" + "dob: " + nmonth +bday +byear + "  OR, "+adateOfbirth +" suffix:" + asuffix + " \n" + "   phones:   mobile-" + amobilephone + " work-" + aworkphone + " \nemail:  " + anemail + " contact preference:" + acontactpreference + " \n"
        // + "");
    }

    private String anaddress;
    private String acity;
    private String astate;
    private String azipcode;
    private String acounty;
    private String ahomephone;

    public void transferAddressInfo(String address, String city, String state, String zipcode, String county, String homephone) {
        anaddress = address;
        acity = city;
        astate = state;
        azipcode = zipcode;
        acounty = county;
        ahomephone = homephone;
        //TEST
        //   System.out.print("\n\nAddress Info from Screen 1:  " + anaddress + "   " + acity + ",   " +astate+ ",  "
        //         + azipcode + "   " + "county: " + acounty + "    homephone: " + ahomephone);

    }

    private String acontactname;
    private String acontactphone;
    private String acontactaddress;
    private String acontactrelation;

    public void transferEmContactInfo(String contactname, String contactphone, String contactaddress, String contactrelation) {
        acontactname = contactname;
        acontactphone = contactphone;
        acontactaddress = contactaddress;
        acontactrelation = contactrelation;
        // System.out.print("\n\nEmContact Info from Screen 1:  " + acontactname + " " + acontactphone + "   "
        //   + acontactaddress + " " + acontactrelation);
    }

    /* Developer Note: I tried using various methods to get the value as a Button OR a String, but none
    worked, so here we will have to use "contains" to extract the Yes, No, Male, Female options */
    String ahispanicyesno;
    String agenderM_F;
    String genderM_F;
    String hispanicyesno;

    public void transferRaceData2(String hispanicvalue, String gendervalue) {
        ahispanicyesno = hispanicvalue;
        agenderM_F = gendervalue;
        //TEST
        // System.out.print("\n\ntransferRaceData2 from Screen 1: \n hispanicvalue:  " + hispanicvalue + "   gendervalue:  " + gendervalue +"\n\n\n");
        if (hispanicvalue.contains("Yes")) {
            hispanicyesno = "Yes";
        } else if (hispanicvalue.contains("No")) {
            hispanicyesno = "No";
        } else {
            //      System.out.println("\nNeither Yes nor No was found..");
        }

        if (gendervalue.contains("Male")) {
            genderM_F = "Male";
        } else if (gendervalue.contains("Female")) {
            genderM_F = "Female";
        } else {
            //    System.out.println("Neither Male nor Female was found..");
        }
        //  System.out.print("\ntransferRaceData2 from Screen 1:  hispanicyesno:  " + hispanicyesno + "   genderM_F:  " + genderM_F +"\n\n\n");
    }

    ArrayList<String> aselectedraces;

    public void transferRaceData1(ArrayList<String> selectedraces) {
        aselectedraces = selectedraces;
    }

    // ENTERING DATA INTO THE DATABASE when "Continue to next page" is clicked
    private void enterEducationInfo() throws SQLException {
        String studenttype = pg2studentType.getValue();
        String heardabout = pg2HearAboutUs.getValue();
        String previousprogram = pg2PreviousPrograms.getText();
        String lastschoolattended = pg2lastSchoolAttended.getText();
        String completed = pg2EducationCompleted.getValue();
        RadioButton school = (RadioButton) US_NonUS.getSelectedToggle();
        String schoolingbase = school.getText();
        Clientdata2DAO.addEducation(studenttype, heardabout, previousprogram, lastschoolattended, completed, schoolingbase);
    }

    private void enterEmploymentInfo() throws SQLException {
        String status = pg2EmpStatus.getValue();
        String listeddisability = pg2ListedDisability.getText();
        Clientdata2DAO.addEmployment(status, listeddisability);
    }

    /*For selected barriers, FIRST make an SQL insert for each;
    SECOND add each to an arraylist to enter as a string for easy spreadsheet population */
    ArrayList<String> selectedbarriers = new ArrayList<String>();
    String barrierslist;
    String barrieryesorno;  //set to "yes" or "no" based on data checked by user

    private void enterBarrierInfo() {
        if (cultural.isSelected()) {
            String declaredbarrier = "cultural barrier";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (disabled.isSelected()) {
            String declaredbarrier = "disabled";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }

        if (displaced.isSelected()) {
            String declaredbarrier = "displaced homemaker";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }

        if (english_lang_learner.isSelected()) {
            String declaredbarrier = "English Language Learner";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (ex_offender.isSelected()) {
            String declaredbarrier = "ex offender";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (exhausting_TANF.isSelected()) {
            String declaredbarrier = "exhausting TANF within 2 yrs";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }

        if (foster_youth.isSelected()) {
            String declaredbarrier = "foster care youth";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (homeless.isSelected()) {
            String declaredbarrier = "homeless";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }

        if (long_term_unemployed.isSelected()) {
            String declaredbarrier = "long term unemployed";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (low_literacy.isSelected()) {
            String declaredbarrier = "low literacy levels";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (migrant_worker.isSelected()) {
            String declaredbarrier = "migrant farm worker";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (seasonal_worker.isSelected()) {
            String declaredbarrier = "seasonal farm worker";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }

        if (single_parent.isSelected()) {
            String declaredbarrier = "single parent/guardian";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (public_assistance.isSelected()) {
            String declaredbarrier = "public assistance";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        if (emancipated_minor.isSelected()) {
            String declaredbarrier = "emancipated minor";
            Clientdata2DAO.addBarrier(declaredbarrier);
            selectedbarriers.add(declaredbarrier);
        }
        //TEST IT
        barrierslist = selectedbarriers.toString();
        System.out.println("The list of chosen barriers: " + barrierslist + " \nselectedbarriers.toString(): " + selectedbarriers.toString());

        //save "yes" or "no" to the database.... 
        if ((selectedbarriers.isEmpty()) && (pg2ListedDisability.getText().isEmpty())) {
            // if (barrierslist.isEmpty()&& pg2ListedDisability.getText().isEmpty()){
            barrieryesorno = "no";
        } else {
            barrieryesorno = "yes";
        }
        Clientdata2DAO.addStringBarrierList(barrieryesorno, barrierslist);
    }

    private void enterClientInfo() throws SQLException {
        Locale locale = new Locale("en", "US");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String enrollmentdate = dateFormat.format(new Date());
        Clientdata2DAO.addClient(enrollmentdate, afirstname, amiddlename, alastname, asocialsecurity, adateOfbirth, asuffix, amobilephone, aworkphone, anemail, acontactpreference);

    }

    private void enterAddressInfo() throws SQLException {
        Clientdata1DAO.insertAddressData(anaddress, acity, astate, azipcode, acounty, ahomephone);
    }

    private void enterEmContactData() throws SQLException {
        Clientdata1DAO.insertEmContactData(acontactname, acontactphone, acontactaddress, acontactrelation);

    }

    public void enterRaceData2() throws SQLException {
        Clientdata1DAO.insertRaceData2(hispanicyesno, genderM_F);
    }

    public void enterRaceData1Items() throws SQLException {
        int numberOfItems = aselectedraces.size();
        for (int r = 0; r < numberOfItems; r++) {
            String race = (String) aselectedraces.get(r);
            Clientdata1DAO.insertRaceData1(race);
        }
        String raceslist = aselectedraces.toString();
        Clientdata1DAO.insertStringRaceList(raceslist);
        //TEST
        // System.out.println(" \n\nraces string: " +aselectedraces.toString()+ "  \nSize:  " +aselectedraces.size()+"String raceslist: " +raceslist);

    }

    @FXML
    private void continueToPage3(ActionEvent event) throws IOException, SQLException {

        if (pg2EducationCompleted.getValue() == null) {
            choosehighestlevelcompleted.setVisible(true);
        } else if (pg2EmpStatus.getValue() == null) {
            chooseemploymentstatus.setVisible(true);

        } else if (US_NonUS.getSelectedToggle() == null) {
            chooseemploymentstatus.setVisible(true);
        } else {

            try {
                //  enter data from screen 1 into database
                enterAddressInfo();
                enterEmContactData();
                enterRaceData2();
                enterRaceData1Items();
                // enter data from screen 2 into database
                enterEducationInfo();
                enterEmploymentInfo();
                enterBarrierInfo();
                enterClientInfo();
            } catch (Exception e) {
                System.out.println("The Network Connection has been lost or interrupted" + e);
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error");
                err.setHeaderText("Network Connection Failure");
                err.setContentText("A network connection has been lost. \n Data could not be saved to the database.");
                err.showAndWait();
            }
            try {

//***FVCC Customization ... creating and saving PDF report upon client submission
                createPDFnow(Clientdata2DAO.idClient);
            } catch (Exception e) {
                System.out.println("There was an error saving the pdf" + e);
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error");
                err.setHeaderText("A PDF was not created");
                err.setContentText("A PDF report could not be created, but that is okay. \n Let a staff person know and then continue.");
                err.showAndWait();
            }

            //continue on to screen 3
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/clientcustomquestions.fxml"));
            Parent reportmain = loader.load();
            Scene scene = new Scene(reportmain);
            Stage stage = TwentyQuestions.getStage();
            stage.setScene(scene);
            stage.show();
        }

    }
    //CREATE PDF STARTS HERE
    //additional fields for variables to enter into the PDF form
    static int idClient;
    private String enrollmentdate;
    private String lastname;
    private String firstname;
    private String mobilephone;
    private String homephone;

    String socialsecurity;
    String middlename;
    String suffix;
    String dateOfbirth;
    String gender;
    String hispanic;
    String completed;
    String schoolingbase;
    String status;
    String barrier;

    String address;
    String city;
    String state;
    String zipcode;
    String county;

    String workphone;
    String email;
    String contactpreference;
    String emphone;

    String emname;
    String emaddress;
    String emrelation;
    String listeddisability;
    String heardabout;
    String previousprogram;
    String lastschoolattended;
    String studenttype;
    int idGender_Race;
    int idEmployment;
    String listedraces;
    String barrierslisted;

    public void createPDFnow(int idClient) throws SQLException, IOException {
        int id = idClient;

        ResultSet clientpdf = reportsDAO.getaClientPDF(id);
        while (clientpdf.next()) {
            enrollmentdate = clientpdf.getString("enrollmentdate");
            socialsecurity = clientpdf.getString("socialsecurity");
            lastname = clientpdf.getString("lastname");
            firstname = clientpdf.getString("firstname");
            middlename = clientpdf.getString("middlename");
            suffix = clientpdf.getString("suffix");
            dateOfbirth = clientpdf.getString("dateOfbirth");

            gender = clientpdf.getString("gender");
            hispanic = clientpdf.getString("hispanic");
            listedraces = clientpdf.getString("listofraces");
            completed = clientpdf.getString("completed");
            schoolingbase = clientpdf.getString("schoolingbase");
            status = clientpdf.getString("status");
            barrier = clientpdf.getString("barrier");
            barrierslisted = clientpdf.getString("listofbarriers");

            address = clientpdf.getString("address");

            city = clientpdf.getString("city");
            state = clientpdf.getString("state");
            zipcode = clientpdf.getString("zipcode");
            county = clientpdf.getString("county");
            homephone = clientpdf.getString("homephone");
            mobilephone = clientpdf.getString("mobilephone");
            workphone = clientpdf.getString("workphone");
            email = clientpdf.getString("email");
            contactpreference = clientpdf.getString("contactpreference");
            emphone = clientpdf.getString("emphone");

            emname = clientpdf.getString("emname");
            emaddress = clientpdf.getString("emaddress");
            emrelation = clientpdf.getString("emrelation");
            listeddisability = clientpdf.getString("listeddisability");

            studenttype = clientpdf.getString("studenttype");
            heardabout = clientpdf.getString("heardabout");
            previousprogram = clientpdf.getString("previousprogram");
            lastschoolattended = clientpdf.getString("lastschoolattended");

            idGender_Race = clientpdf.getInt("idGender_Race");
            idEmployment = clientpdf.getInt("idEmployment");
        }
        // Creating PDF document object 
        System.out.print("\nSetting up PDF now");

        File file = new File(LocalCustomization.PATHTOGETPDF + "WIOA_Application12PDF.pdf");
        PDDocument clientreport = PDDocument.load(file);

        PDDocumentCatalog docCatalog = clientreport.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

        //setting client's answers  in the PDF, first column        
        PDField firstcolumnfield = acroForm.getField("intakedatetext");
        firstcolumnfield.setValue(enrollmentdate);
        firstcolumnfield = acroForm.getField("lastnametext");
        firstcolumnfield.setValue(lastname);
        firstcolumnfield = acroForm.getField("firstnametext");
        firstcolumnfield.setValue(firstname);
        firstcolumnfield = acroForm.getField("suffixtext");
        firstcolumnfield.setValue(suffix);
        firstcolumnfield = acroForm.getField("socialsecuritytext");
        firstcolumnfield.setValue(socialsecurity);
        firstcolumnfield = acroForm.getField("dobtext");
        firstcolumnfield.setValue(dateOfbirth);

        //checkmarks gender, hispanic, 
        if (gender.equals("Male")) {
            PDCheckBox markedgender = (PDCheckBox) acroForm.getField("male");
            markedgender.check();
        } else {
            PDCheckBox markedgender = (PDCheckBox) acroForm.getField("female");
            markedgender.check();
        }

        if (hispanic.equals("No")) {
            PDCheckBox latino = (PDCheckBox) acroForm.getField("latinono");
            latino.check();
        } else {
            PDCheckBox latino = (PDCheckBox) acroForm.getField("latinoyes");
            latino.check();
        }
        //for races marked        
        if (listedraces.contains("White")) {
            //then do the check();            
            PDCheckBox race1 = (PDCheckBox) acroForm.getField("raceWhite");
            race1.check();
        }
        if (listedraces.contains("American Indian/Alaska")) {
            PDCheckBox race2 = (PDCheckBox) acroForm.getField("raceAmIndian");
            race2.check();
        }
        if (listedraces.contains("Asian")) {
            PDCheckBox race3 = (PDCheckBox) acroForm.getField("raceAsian");
            race3.check();
        }
        if (listedraces.contains("Black/African")) {
            PDCheckBox race4 = (PDCheckBox) acroForm.getField("raceBlack");
            race4.check();
        }
        if (listedraces.contains("Native Hawaiian/other Pacific Islander")) {
            PDCheckBox race5 = (PDCheckBox) acroForm.getField("raceNativeHw");
            race5.check();
        }

        //last school attended
        firstcolumnfield = acroForm.getField("lastschooltext");
        firstcolumnfield.setValue(lastschoolattended);

        //level of education completed        
        if (completed.equals("No Schooling")) {
            PDCheckBox noschooling = (PDCheckBox) acroForm.getField("noschooling");
            noschooling.check();
        }
        if (completed.equals("Kindergarten")) {
            PDCheckBox kindergarten = (PDCheckBox) acroForm.getField("kindergarten");
            kindergarten.check();
        }
        if (completed.equals("Grades 1-5")) {
            PDCheckBox grades15 = (PDCheckBox) acroForm.getField("grades15");
            grades15.check();
        }
        if (completed.equals("Grades 6-8")) {
            PDCheckBox grades68 = (PDCheckBox) acroForm.getField("grades68");
            grades68.check();
        }
        if (completed.equals("Grades 9-12")) {
            PDCheckBox grades912 = (PDCheckBox) acroForm.getField("grades912");
            grades912.check();
        }
        if (completed.equals("Secondary School Diploma")) {
            PDCheckBox secondarydiploma = (PDCheckBox) acroForm.getField("secondarydiploma");
            secondarydiploma.check();
        }
        if (completed.equals("Secondary School Equivalent(GED/HISET)")) {
            PDCheckBox secDiplomaEquiv = (PDCheckBox) acroForm.getField("secDiplomaEquiv");
            secDiplomaEquiv.check();
        }
        if (completed.equals("Unknown")) {
            PDCheckBox unknown = (PDCheckBox) acroForm.getField("unknown");
            unknown.check();
        }
        if (completed.equals("Some Postsecondary Education, No Degree")) {
            PDCheckBox somePostSec = (PDCheckBox) acroForm.getField("somePostSec");
            somePostSec.check();
        }
        if (completed.equals("Postsecondary or Professional Degree")) {
            PDCheckBox PostProfessional = (PDCheckBox) acroForm.getField("postProfessional");
            PostProfessional.check();
        }

        if ("US Based Schooling".equals(schoolingbase)) {
            PDCheckBox schooling = (PDCheckBox) acroForm.getField("usbasedschooling");
            schooling.check();
        } else {
            PDCheckBox schooling = (PDCheckBox) acroForm.getField("nonusbasedschooling");
            schooling.check();
        }

        //student type 
        if (studenttype.contains("New")) {
            PDCheckBox newstudent = (PDCheckBox) acroForm.getField("newstudent");
            newstudent.check();
        }
        if (studenttype.contains("Continuing")) {
            PDCheckBox continuingstudent = (PDCheckBox) acroForm.getField("continuingstudent");
            continuingstudent.check();
        }
        if (studenttype.contains("Returning")) {
            PDCheckBox returningstudent = (PDCheckBox) acroForm.getField("returningstudent");
            returningstudent.check();
        }

        firstcolumnfield = acroForm.getField("previousschooltext");
        firstcolumnfield.setValue(previousprogram);

        //setting client's answers  second column
        //employment status 
        if (status.equals("Employed Full Time")) {
            PDCheckBox fulltime = (PDCheckBox) acroForm.getField("fulltime");
            fulltime.check();
        }

        if (status.equals("Employed Part Time")) {
            PDCheckBox parttime = (PDCheckBox) acroForm.getField("parttime");
            parttime.check();
        }
        if (status.equals("Unemployed")) {
            PDCheckBox unemployed = (PDCheckBox) acroForm.getField("unemployed");
            unemployed.check();
        }
        if (status.equals("Not Looking for Work")) {
            PDCheckBox notlooking = (PDCheckBox) acroForm.getField("notlooking");
            notlooking.check();
        }
        if (status.equals("Retired")) {
            PDCheckBox retired = (PDCheckBox) acroForm.getField("retired");
            retired.check();
        }
        if (status.equals("Employed with Separation Notice")) {
            PDCheckBox employedwithseparation = (PDCheckBox) acroForm.getField("employedwithseparation");
            employedwithseparation.check();
        }

        // FVCC request:
        /* The "yes"/"no" is automatic, based on what else the client marked; 
        this was because users tend to not understand what "barriers" means, 
        but they can identify specific things they have experienced.*/
        if (barrier.equals("yes")) {
            PDCheckBox embarrieryes = (PDCheckBox) acroForm.getField("embarrieryes");
            embarrieryes.check();

            if (barrierslisted.contains("cultural barrier")) {
                PDCheckBox bcultural = (PDCheckBox) acroForm.getField("bcultural");
                bcultural.check();
            }

            if (barrierslisted.contains("disabled")) {
                PDCheckBox bdisabled = (PDCheckBox) acroForm.getField("bdisabled");
                bdisabled.check();
            }

            if (barrierslisted.contains("displaced homemaker")) {
                PDCheckBox bdisplaced = (PDCheckBox) acroForm.getField("bdisplaced");
                bdisplaced.check();
            }

            if (barrierslisted.contains("English Language Learner")) {
                PDCheckBox bell = (PDCheckBox) acroForm.getField("bell");
                bell.check();
            }
            if (barrierslisted.contains("ex offender")) {
                PDCheckBox bexoffender = (PDCheckBox) acroForm.getField("bexoffender");
                bexoffender.check();
            }

            if (barrierslisted.contains("exhausting TANF within 2 yrs")) {
                PDCheckBox bexhaustingTANF = (PDCheckBox) acroForm.getField("bexhaustingTANF");
                bexhaustingTANF.check();
            }

            if (barrierslisted.contains("foster care youth")) {
                PDCheckBox bfostercare = (PDCheckBox) acroForm.getField("bfostercare");
                bfostercare.check();
            }
            if (barrierslisted.contains("homeless")) {
                PDCheckBox bhomeless = (PDCheckBox) acroForm.getField("bhomeless");
                bhomeless.check();
            }

            if (barrierslisted.contains("long term unemployed")) {
                PDCheckBox blongtermunemployed = (PDCheckBox) acroForm.getField("blongtermunemployed");
                blongtermunemployed.check();
            }

            if (barrierslisted.contains("low literacy levels")) {
                PDCheckBox blowliteracy = (PDCheckBox) acroForm.getField("blowliteracy");
                blowliteracy.check();
            }

            if (barrierslisted.contains("migrant farm worker")) {
                PDCheckBox bmigrantfarmwrkr = (PDCheckBox) acroForm.getField("bmigrantfarmwrkr");
                bmigrantfarmwrkr.check();
            }

            if (barrierslisted.contains("seasonal farm worker")) {
                PDCheckBox bmigrantseasonalwrkr = (PDCheckBox) acroForm.getField("bmigrantseasonalwrkr");
                bmigrantseasonalwrkr.check();
            }
            if (barrierslisted.contains("single parent/guardian")) {
                PDCheckBox bsingleparent = (PDCheckBox) acroForm.getField("bsingleparent");
                bsingleparent.check();
            }

            if (barrierslisted.contains("public assistance")) {
                PDCheckBox publicassistance = (PDCheckBox) acroForm.getField("bpublicassistance");
                publicassistance.check();
            }

            if (barrierslisted.contains("emancipated minor")) {
                PDCheckBox emancipatedminor = (PDCheckBox) acroForm.getField("bemancipatedminor");
                emancipatedminor.check();
            }

            //if client entered a disability into the textfield, ...
            if (!listeddisability.isEmpty()) {
                PDField secondcolumnfield = acroForm.getField("disabilitytext");
                secondcolumnfield.setValue(listeddisability);
                PDCheckBox physicalormentaldisability = (PDCheckBox) acroForm.getField("bphysicalmentaldisability");
                physicalormentaldisability.check();
            }
            PDField secondcolumnfield = acroForm.getField("hearabouttext");
            secondcolumnfield.setValue(heardabout);
        } else {
            PDCheckBox embarrierno = (PDCheckBox) acroForm.getField("embarrierno");
            embarrierno.check();

        }

        // setting client's answers to third column
        PDField thirdcolumnfield = acroForm.getField("addresstext");
        thirdcolumnfield.setValue(address);
        thirdcolumnfield = acroForm.getField("citytext");
        thirdcolumnfield.setValue(city);
        thirdcolumnfield = acroForm.getField("statetext");
        thirdcolumnfield.setValue(state);
        thirdcolumnfield = acroForm.getField("zipcodetext");
        thirdcolumnfield.setValue(zipcode);

        thirdcolumnfield = acroForm.getField("countytext");
        thirdcolumnfield.setValue(county);
        thirdcolumnfield = acroForm.getField("homephonetext");
        thirdcolumnfield.setValue(homephone);
        thirdcolumnfield = acroForm.getField("workphonetext");
        thirdcolumnfield.setValue(workphone);
        thirdcolumnfield = acroForm.getField("mobilephonetext");
        thirdcolumnfield.setValue(mobilephone);
        thirdcolumnfield = acroForm.getField("emailtext");
        thirdcolumnfield.setValue(email);

        // contact preference
        if (contactpreference.contains("Any Phone")) {
            PDCheckBox anyphone = (PDCheckBox) acroForm.getField("anyphone");
            anyphone.check();
        }

        if (contactpreference.contains("Mobile Phone")) {
            PDCheckBox mobilephone = (PDCheckBox) acroForm.getField("mobilephoneonly");
            mobilephone.check();
        }
        if (contactpreference.contains("Email Only")) {
            PDCheckBox emailonly = (PDCheckBox) acroForm.getField("emailonly");
            emailonly.check();
        }
        if (contactpreference.contains("Home Only")) {
            PDCheckBox homeonly = (PDCheckBox) acroForm.getField("homeonly");
            homeonly.check();
        }
        if (contactpreference.contains("Text Only")) {
            PDCheckBox textonly = (PDCheckBox) acroForm.getField("textonly");
            textonly.check();
        }
        thirdcolumnfield = acroForm.getField("emnametext");
        thirdcolumnfield.setValue(emname);
        thirdcolumnfield = acroForm.getField("emaddresstext");
        thirdcolumnfield.setValue(emaddress);
        thirdcolumnfield = acroForm.getField("emphonetext");
        thirdcolumnfield.setValue(emphone);
        thirdcolumnfield = acroForm.getField("emrelationtext");
        thirdcolumnfield.setValue(emrelation);

        try {

            //**Note: THIS REPORT uses the enrollment date instead of reportdate
            String filenamed = lastname + "_" + firstname + "_" + enrollmentdate;
            clientreport.save(LocalCustomization.PATHTOSAVEPDF + filenamed + ".pdf");

            clientreport.close();
        } catch (Exception e) {
            System.out.println("There has been an error is saving the file " + e);
        }
    }

    @FXML
    private void ExitProgram(ActionEvent event) throws IOException {
        try {
            DBConnection.closeDBConnection(); //closing the database
        } catch (Exception ex) {
            Logger.getLogger(Clientscreen1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);

    }

}
