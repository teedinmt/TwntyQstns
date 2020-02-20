/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package viewControllers;

import dao.Clientdata2DAO;
import dao.DBConnection;
import dao.LocalCustomization;
import dao.reportsDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import twentyquestions.TwentyQuestions;

public class StaffScreenController implements Initializable {

    @FXML
    private Button ExitButton;
    @FXML
    private TableView<ClientTableRow> clientTable;
    @FXML
    private TableColumn<ClientTableRow, String> anId;
    @FXML
    private TableColumn<ClientTableRow, String> anenrollmentdate;
    @FXML
    private TableColumn<ClientTableRow, String> fname;
    @FXML
    private TableColumn<ClientTableRow, String> lname;
    @FXML
    private TableColumn<ClientTableRow, String> phone;

    @FXML
    private CheckBox sendlisttospreadsheet;
    @FXML
    private CheckBox printnamesandnumbers;
    @FXML
    private Label staffTitle;

    @FXML
    private CheckBox printIndividualPDF;

    @FXML
    private Button changecustomquestionsbutton;
    @FXML
    private Label reportPDFcreatedmessage;
    @FXML
    private Label alertnoclientselected;
    @FXML
    private Label spreadsheet1message;
    @FXML
    private Label namesandnumbersmessage;
    @FXML
    private TextField reportPDFtitle;
    @FXML
    private TextField spreadsheettitle1;
    @FXML
    private TextField spreadsheettitle2;
    @FXML
    private Button deselectAllbutton;

    ObservableList<ClientTableRow> clientList = FXCollections.observableArrayList();

    Client selectedClient;
    String reportdate;
    @FXML
    private Label PDFfileNotetext;
    @FXML
    private Label Noteredtext;
    @FXML
    private CheckBox createcustomquestions;
    @FXML
    private Label customquestionreportmessage1;
    @FXML
    private TextField customquestionstitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            reportPDFcreatedmessage.setVisible(false);
            alertnoclientselected.setVisible(false);
            spreadsheet1message.setVisible(false);
            namesandnumbersmessage.setVisible(false);

            reportPDFtitle.setVisible(false);
            spreadsheettitle1.setVisible(false);
            spreadsheettitle2.setVisible(false);
            customquestionreportmessage1.setVisible(false);
            customquestionstitle.setVisible(false);
            listClients();
        } catch (Exception e) {
            System.out.print("/nThere has been an error in setting the data to the customer info screen" + e);
        }
    }

    @FXML
    private void deselectAllandRefresh(ActionEvent event) {

        clientTable.getSelectionModel().clearSelection();
        sendlisttospreadsheet.setSelected(false);
        printnamesandnumbers.setSelected(false);
        printIndividualPDF.setSelected(false);
        createcustomquestions.setSelected(false);

        //reset screen messages
        reportPDFcreatedmessage.setVisible(false);
        alertnoclientselected.setVisible(false);
        spreadsheet1message.setVisible(false);
        namesandnumbersmessage.setVisible(false);
        reportPDFtitle.setVisible(false);
        spreadsheettitle1.setVisible(false);
        spreadsheettitle2.setVisible(false);
        customquestionreportmessage1.setVisible(false);
        customquestionstitle.setVisible(false);
    }

//for formatting the spreadsheets
    Row row;
    org.apache.poi.ss.usermodel.Sheet sheet;

    private void autosizeCells() {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int columnIndex = cell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex);
        }
    }

    //Set List of Clients to the screen    
    private static class ClientRow {

        public ClientRow(ObservableValue<String> idClient, ObservableValue<String> fname, ObservableValue<String> lname, ObservableValue<String> address, ObservableValue<String> phone) {
            this.idClient = idClient;
            this.fname = fname;
            this.lname = lname;
            this.address = address;
            this.phone = phone;
        }
        private ObservableValue<String> idClient;
        private ObservableValue<String> fname;
        private ObservableValue<String> lname;
        private ObservableValue<String> address;
        private ObservableValue<String> phone;
    }

    private void listClients() throws SQLException {
        anId.setCellValueFactory(cellData -> {
            return cellData.getValue().getIdClient();
        });
        anenrollmentdate.setCellValueFactory(cellData -> {
            return cellData.getValue().getEnrollmentdate();
        });
        fname.setCellValueFactory(cellData -> {
            return cellData.getValue().getFirstname();
        });
        lname.setCellValueFactory(cellData -> {
            return cellData.getValue().getLastname();
        });
        phone.setCellValueFactory(cellData -> {
            return cellData.getValue().getPhonenumber();
        });
        // get clients from database
        clientList.clear();
        ResultSet clientlist = Clientdata2DAO.getClientList();
        while (clientlist.next()) {
            String idClient = clientlist.getString("idClient");
            String enrollmentdate = clientlist.getString("enrollmentdate");
            String firstname = clientlist.getString("firstname");
            String lastname = clientlist.getString("lastname");
            String mobilephone = clientlist.getString("mobilephone");
            ClientTableRow clientrow = new ClientTableRow(new ReadOnlyStringWrapper(idClient),
                    new ReadOnlyStringWrapper(enrollmentdate),
                    new ReadOnlyStringWrapper(firstname),
                    new ReadOnlyStringWrapper(lastname),
                    new ReadOnlyStringWrapper(mobilephone));
            //add the row to the list
            clientList.add(clientrow);
        }
        clientTable.getItems().setAll(clientList);
        //get today's date for the reports
        Locale locale = new Locale("en", "US");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        reportdate = dateFormat.format(new java.util.Date());
    }

    /* TODO  //for future versions:
    
        private void deleteSelectedCustomer(ActionEvent event) {
            //alert user that client will be permanently deleted from the database
        ClientTableRow selectedClient = clientTable.getSelectionModel().getSelectedItem();
        String id = selectedClient.getIdClient().getValue();
           // System.out.println("Client ID #: " + id + "  has been clicked.");
        int idClient = Integer.parseInt(id);    
          //  System.out.println("idClient value is  " + idClient);
        //Now need to call delete sql statement.
        }  

     */
    @FXML
    private void gotoCustomQuestionsScreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/staffcustomquestions.fxml"));
        Parent reportmain = loader.load();
        Scene scene = new Scene(reportmain);
        Stage stage = TwentyQuestions.getStage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exitProgramonMain(ActionEvent event) {
        try {
            DBConnection.closeDBConnection(); //closing the database
            System.exit(0);

        } catch (Exception ex) {
            Logger.getLogger(Clientscreen1Controller.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\n From ExitProgramonMain: closing the db connection failed" + ex + "\n OR exiting the program failed:  " + ex);
        }

    }

    @FXML
    private void sendListtoSpreadsheet(ActionEvent event) throws IOException, SQLException {
        saveClientsToSpreadSheet();
    }

    String question1;
    String question2;
    String question3;
    String question4;
    String question5;
    String question6;
    String question7;
    int questionID;

    @FXML
    private void createCustomQuestionsReport(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
        //get current questions 
        ResultSet question = reportsDAO.getCustomQuestions();
        while (question.next()) {
            questionID = question.getInt("questionID");
            question1 = question.getString("question1");
            question2 = question.getString("question2");
            question3 = question.getString("question3");
            question4 = question.getString("question4");
            question5 = question.getString("question5");
            question6 = question.getString("question6");
            question7 = question.getString("question7");
        }

        String nnfilenamed = "CustomQuestions_" + reportdate;
        Workbook questions = new XSSFWorkbook();

        try (OutputStream fileOut = new FileOutputStream(LocalCustomization.PATHTOSAVESPREADSHEETS + nnfilenamed + ".xlsx")) {
            questions.write(fileOut);
        }
        CreationHelper createHelper = questions.getCreationHelper();
// create a new sheet
        sheet = questions.createSheet("CustomQuestions");
// declare a row object and add cells
        row = sheet.createRow(0);
        row.createCell(1).setCellValue("? set ID");
        row.createCell(2).setCellValue("First Name");
        row.createCell(3).setCellValue("Second Name");

        row.createCell(4).setCellValue(question1);
        row.createCell(5).setCellValue(question2);
        row.createCell(6).setCellValue(question3);
        row.createCell(7).setCellValue(question4);
        row.createCell(8).setCellValue(question5);
        row.createCell(9).setCellValue(question6);
        row.createCell(10).setCellValue(question7);

        ResultSet answerlist = reportsDAO.getCustomAnswers(questionID);
//now to iterate through the rows to get each record
        int i = 1;
        while (answerlist.next()) {
            row = sheet.createRow(i);
            row.createCell(1).setCellValue(answerlist.getString("fk_idCustomquestions"));
            row.createCell(2).setCellValue(answerlist.getString("firstname"));
            row.createCell(3).setCellValue(answerlist.getString("lastname"));
            row.createCell(4).setCellValue(answerlist.getString("answer1"));
            row.createCell(5).setCellValue(answerlist.getString("answer2"));
            row.createCell(6).setCellValue(answerlist.getString("answer3"));
            row.createCell(7).setCellValue(answerlist.getString("answer4"));
            row.createCell(8).setCellValue(answerlist.getString("answer5"));
            row.createCell(9).setCellValue(answerlist.getString("answer6"));
            row.createCell(10).setCellValue(answerlist.getString("answer7"));
            i++;
        }
        //do a little formatting 
        autosizeCells();

        try (OutputStream fileOut = new FileOutputStream(LocalCustomization.PATHTOSAVESPREADSHEETS + nnfilenamed + ".xlsx")) {
            questions.write(fileOut);
            fileOut.close();
            customquestionreportmessage1.setVisible(true);
            customquestionstitle.setText(nnfilenamed);
            customquestionstitle.setVisible(true);
            //System.out.println("An Excel sheet of Custom Questions/Answers has been created and saved");
        }
    }

    //INDIVIDUAL  PDF  Report begins here 
    @FXML
    private void printIndividualPDF(ActionEvent event) throws IOException {

        alertnoclientselected.setVisible(false);
        ClientTableRow selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            String id = selectedClient.getIdClient().getValue();
            // System.out.println("Client ID #: " + id + "  has been clicked.");
            int thisid = Integer.parseInt(id);

            try {
                createPDFReport(thisid);

            } catch (SQLException ex) {
                Logger.getLogger(StaffScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alertnoclientselected.setVisible(true);

        }

    }
    //CREATING REPORTS BEGINS HERE

    //For creating the spreadsheet report of names and numbers
    static int idClient;
    private String enrollmentdate;
    private String lastname;
    private String firstname;
    private String mobilephone;
    private String homephone;

    @FXML
    private void printNamesandNumbers(ActionEvent event) throws SQLException, IOException {
        ResultSet namesnumberslist = reportsDAO.saveNamesAndNumbers();
        //  System.out.print("/Names and Numbers\n");
        while (namesnumberslist.next()) {
            enrollmentdate = namesnumberslist.getString("enrollmentdate");
            firstname = namesnumberslist.getString("firstname");
            lastname = namesnumberslist.getString("lastname");
            mobilephone = namesnumberslist.getString("mobilephone");
            homephone = namesnumberslist.getString("homephone");
            email = namesnumberslist.getString("email");
            //   System.out.print("  " + enrollmentdate + " " + firstname + " " + lastname + ", mobile: " + mobilephone + ", home: " + homephone + "\n");
        }
        saveNamesNumbersToSpreadSheet();
    }

    private void saveNamesNumbersToSpreadSheet() throws FileNotFoundException, IOException, SQLException {
        //System.out.println("Today's date for spreadsheet name: " + reportdate);        
        String nnfilenamed = "NamesandNumbers_" + reportdate;
        // first, create a new workbook
        Workbook wbnumbers = new XSSFWorkbook();

        //TESTING  
        try (OutputStream fileOut = new FileOutputStream(LocalCustomization.PATHTOSAVESPREADSHEETS + nnfilenamed + ".xlsx")) {
            //try (OutputStream fileOut = new FileOutputStream("C:\\Users\\MT\\Documents\\" + nnfilenamed + ".xlsx")) {
            wbnumbers.write(fileOut);
        }
        CreationHelper createHelper = wbnumbers.getCreationHelper();
// create a new sheet
        sheet = wbnumbers.createSheet("Phone #s");
// declare a row object and add cells
        row = sheet.createRow(0);
        row.createCell(1).setCellValue("Date Enrolled");
        row.createCell(2).setCellValue("First Name");
        row.createCell(3).setCellValue("Last Name");
        row.createCell(4).setCellValue("Mobile Phone");
        row.createCell(5).setCellValue("Home Phone");
        row.createCell(6).setCellValue("Email");

        ResultSet clients = reportsDAO.saveNamesAndNumbers();
//now to iterate through the rows to get each record
        int i = 1;
        while (clients.next()) {
            row = sheet.createRow(i);
            row.createCell(1).setCellValue(clients.getString("enrollmentdate"));
            row.createCell(2).setCellValue(clients.getString("firstname"));
            row.createCell(3).setCellValue(clients.getString("lastname"));
            row.createCell(4).setCellValue(clients.getString("mobilephone"));
            row.createCell(5).setCellValue(clients.getString("homephone"));
            row.createCell(6).setCellValue(clients.getString("email"));
            i++;
        }
        //do a little formatting, setting cells to auto width 
        autosizeCells();

        try (OutputStream fileOut = new FileOutputStream(LocalCustomization.PATHTOSAVESPREADSHEETS + nnfilenamed + ".xlsx")) {
            wbnumbers.write(fileOut);
            fileOut.close();
            namesandnumbersmessage.setVisible(true);
            spreadsheettitle2.setText(nnfilenamed);
            spreadsheettitle2.setVisible(true);
            //System.out.println("An Excel sheet of Names and Phone Numbers has been created and saved");
        }
    }

    private void saveClientsToSpreadSheet() throws FileNotFoundException, IOException, SQLException {
        // first, create a new workbook
        Workbook wbclients = new XSSFWorkbook();

        //System.out.println("Today's date for clientspreadsheet: " + reportdate);
        String clientspreadsheet = "AllData_" + reportdate;

        try (OutputStream fileOut = new FileOutputStream(LocalCustomization.PATHTOSAVESPREADSHEETS + clientspreadsheet + ".xlsx")) {
            wbclients.write(fileOut);
        }
        CreationHelper createHelper = wbclients.getCreationHelper();
// setting up a new sheet with titles in the columns
        sheet = wbclients.createSheet("All Data");
        System.out.println("An Excel sheet for Client Data has been created");
        row = sheet.createRow(0);
        row.createCell(1).setCellValue("idClient");
        row.createCell(2).setCellValue("Enrolled");
        row.createCell(3).setCellValue("SS#");
        row.createCell(4).setCellValue("Last Name");
        row.createCell(5).setCellValue("First Name");
        row.createCell(6).setCellValue("Middle Name");
        row.createCell(7).setCellValue("suffix");
        row.createCell(8).setCellValue("Date of Birth");

        row.createCell(9).setCellValue("Gender");
        row.createCell(10).setCellValue("Hispanic?");
        row.createCell(11).setCellValue("Races Checked");

        row.createCell(12).setCellValue("Last grade completed");
        row.createCell(13).setCellValue("Schooling base");
        row.createCell(14).setCellValue("Work Status");
        row.createCell(15).setCellValue("Any Barriers?");
        row.createCell(16).setCellValue("Declared Barriers");

        row.createCell(17).setCellValue("Address");
        row.createCell(18).setCellValue("City");
        row.createCell(19).setCellValue("Zip");
        row.createCell(20).setCellValue("County");
        row.createCell(21).setCellValue("Home Phone");
        row.createCell(22).setCellValue("Mobile Phone");
        row.createCell(23).setCellValue("Work Phone");
        row.createCell(24).setCellValue("Email");
        row.createCell(25).setCellValue("Contact Preference");

        row.createCell(26).setCellValue("Em Contact Phone");
        row.createCell(27).setCellValue("Em Contact Name");
        row.createCell(28).setCellValue("Em Contact Address");
        row.createCell(29).setCellValue("Em Contact Realationship");
        row.createCell(30).setCellValue("Listed Disability");

        ResultSet clientdatalist = reportsDAO.createSpreadsheetData();

//iterate through the rows to get each record
        int i = 1;
        while (clientdatalist.next()) {
            row = sheet.createRow(i);
            row.createCell(1).setCellValue(clientdatalist.getString("idClient"));
            row.createCell(2).setCellValue(clientdatalist.getString("enrollmentdate"));
            row.createCell(3).setCellValue(clientdatalist.getString("socialsecurity"));
            row.createCell(4).setCellValue(clientdatalist.getString("lastname"));
            row.createCell(5).setCellValue(clientdatalist.getString("firstname"));
            row.createCell(6).setCellValue(clientdatalist.getString("middlename"));
            row.createCell(7).setCellValue(clientdatalist.getString("suffix"));
            row.createCell(8).setCellValue(clientdatalist.getString("dateOfbirth"));

            row.createCell(9).setCellValue(clientdatalist.getString("gender"));
            row.createCell(10).setCellValue(clientdatalist.getString("hispanic"));
            row.createCell(11).setCellValue(clientdatalist.getString("listofraces"));

            row.createCell(12).setCellValue(clientdatalist.getString("completed"));
            row.createCell(13).setCellValue(clientdatalist.getString("schoolingbase"));
            row.createCell(14).setCellValue(clientdatalist.getString("status"));
            row.createCell(15).setCellValue(clientdatalist.getString("barrier"));
            row.createCell(16).setCellValue(clientdatalist.getString("listofbarriers"));

            row.createCell(17).setCellValue(clientdatalist.getString("address"));
            row.createCell(18).setCellValue(clientdatalist.getString("city"));
            row.createCell(19).setCellValue(clientdatalist.getString("zipcode"));
            row.createCell(20).setCellValue(clientdatalist.getString("county"));
            row.createCell(21).setCellValue(clientdatalist.getString("homephone"));
            row.createCell(22).setCellValue(clientdatalist.getString("mobilephone"));
            row.createCell(23).setCellValue(clientdatalist.getString("workphone"));
            row.createCell(24).setCellValue(clientdatalist.getString("email"));
            row.createCell(25).setCellValue(clientdatalist.getString("contactpreference"));
            row.createCell(26).setCellValue(clientdatalist.getString("emphone"));

            row.createCell(27).setCellValue(clientdatalist.getString("emname"));
            row.createCell(28).setCellValue(clientdatalist.getString("emaddress"));
            row.createCell(29).setCellValue(clientdatalist.getString("emrelation"));
            row.createCell(30).setCellValue(clientdatalist.getString("listeddisability"));
            i++;
        }
        //  System.out.println("An Excel sheet has been filled with client data");

        autosizeCells();

//write the output to a file... 
        try (OutputStream fileOut = new FileOutputStream(LocalCustomization.PATHTOSAVESPREADSHEETS + clientspreadsheet + ".xlsx")) {
            wbclients.write(fileOut);
            fileOut.close();
            spreadsheet1message.setVisible(true);
            spreadsheettitle1.setText(clientspreadsheet);
            spreadsheettitle1.setVisible(true);

            //      System.out.println("An Excel sheet for Client Data has been saved");
        } catch (Exception e) {
            System.out.print("/nFILE may be in use by another program error" + e);
        }
    }

    //additional fields for variables to enter into the PDF form
    private String socialsecurity;
    private String suffix;
    private String dateOfbirth;
    private String gender;
    private String hispanic;
    private String completed;
    private String schoolingbase;
    private String status;
    private String barrier;

    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String county;

    private String workphone;
    private String email;
    private String contactpreference;
    private String emphone;

    private String emname;
    private String emaddress;
    private String emrelation;
    private String listeddisability;
    private String heardabout;
    private String previousprogram;
    private String lastschoolattended;
    private String studenttype;
    private String listedraces;
    private String barrierslisted;

    private void createPDFReport(int thisid) throws SQLException, IOException {
        int id = thisid;
        ResultSet clientpdf = reportsDAO.getaClientPDF(id);

        while (clientpdf.next()) {
            enrollmentdate = clientpdf.getString("enrollmentdate");
            socialsecurity = clientpdf.getString("socialsecurity");
            lastname = clientpdf.getString("lastname");
            firstname = clientpdf.getString("firstname");

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

        // Xxxxx Program request:
        /* The "yes"/"no" is automatic, based on what else the client marked; 
        this was because users tend to not understand what "employment barrier" means on the form, 
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
            //check if client entered a disability into the textfield, ...
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
            reportPDFcreatedmessage.setVisible(true);
            reportPDFtitle.setText(filenamed);
            reportPDFtitle.setVisible(true);

            System.out.println("A client PDF has been saved:  " + filenamed + ".pdf");
        } catch (Exception e) {
            System.out.println("There has been an error is saving a PDF file " + e);
        }

    }

}
