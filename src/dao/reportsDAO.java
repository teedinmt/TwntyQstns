/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package dao;

import static dao.DBConnection.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reportsDAO {

    public static ResultSet getaClientPDF(int id) throws SQLException {
        try {

            String sql = "SELECT c.enrollmentdate, c.socialsecurity, c.lastname, c.firstname, c.middlename, c.suffix, c.dateOfbirth,"
                    + " g.gender, g.hispanic, g.listofraces, e.completed, e.schoolingbase, m.status, m.barrier, m.listofbarriers,"
                    + " a.address, a.city, a.state, a.zipcode, a.county, a.homephone, c.mobilephone, c.workphone, c.email, c.contactpreference, r.emphone,"
                    + " r.emname, r.emaddress, r.emrelation, m.listeddisability, e.studenttype, e.heardabout, e.previousprogram, e.lastschoolattended, "
                    + "c.idGender_Race, c.idEmployment"
                    + " FROM client c, gender_race g, education e, employment m, address a, emergencycontacts r"
                    + " WHERE c.idAddress=a.idAddress AND c.idGender_Race=g.idGender_Race AND c.idEducation=e.idEducation"
                    + " AND c.idEmployment=m.idEmployment AND c.idEmergencyContacts=r.idEmergencyContacts"
                    + " AND c.idClient =" + id;

            PreparedStatement clientdetails = conn.prepareStatement(sql);
            ResultSet clientpdf = clientdetails.executeQuery();

            //         System.out.print("/nSQL for getting a client's info:" );
            // System.out.print(sql); 
            return clientpdf;

        } catch (SQLException ex) {
            System.out.print("/nThere has been an error in getting a client's info" + ex);
        }
        return null;

    }

    public static ResultSet getaClientRacelist(int racelistid) throws SQLException {
        try {

            String sql = "SELECT * "
                    + " FROM race "
                    + " WHERE fk_idGender_Race= " + racelistid;
            PreparedStatement racedetails = conn.prepareStatement(sql);
            ResultSet clientracelist = racedetails.executeQuery();
            // System.out.print("/nSQL for getting a client's races: " );
            //System.out.print(sql); 

            return clientracelist;

        } catch (SQLException ex) {
            System.out.print("/nThere has been an error in getting a client's race list info" + ex);
        }
        return null;
    }

    public static ResultSet saveNamesAndNumbers() throws SQLException {
        try {
            String sql = "SELECT c.enrollmentdate, c.firstname, c.lastname, c.mobilephone, a.homephone, c.email"
                    + " FROM client c, address a"
                    + " WHERE c.idAddress = a.idAddress";

            PreparedStatement namesnumbers = conn.prepareStatement(sql);
            ResultSet namesnumberslist = namesnumbers.executeQuery();
            return namesnumberslist;

        } catch (SQLException ex) {
            System.out.print("/nThere has been an error in the Save Name and Numbers method \n" + ex);
        }
        return null;
    }

    public static ResultSet createSpreadsheetData() throws SQLException {
        try {
            //get data for race, make it a string
            //get data for declaredbarriers, make it a string
            //get rest of data
            String sql = "SELECT c.idClient, c.enrollmentdate, c.socialsecurity, c.lastname, c.firstname, c.middlename, c.suffix, c.dateOfbirth,"
                    + " g.gender, g.hispanic, g.listofraces, e.completed, e.schoolingbase, m.status, m.barrier, m.listofbarriers,"
                    + " a.address, a.city, a.zipcode, a.county, a.homephone, c.mobilephone, c.workphone, c.email, c.contactpreference, r.emphone,"
                    + " r.emname, r.emaddress, r.emrelation, m.listeddisability"
                    + " FROM client c, gender_race g, education e, employment m, address a, emergencycontacts r"
                    + " WHERE c.idAddress=a.idAddress AND c.idGender_Race=g.idGender_Race AND c.idEducation=e.idEducation AND c.idEmployment=m.idEmployment AND c.idEmergencyContacts=r.idEmergencyContacts";

            PreparedStatement clientdata = conn.prepareStatement(sql);
            ResultSet clientdatalist = clientdata.executeQuery();
            return clientdatalist;

        } catch (SQLException ex) {
            System.out.print("/nThere has been an error in retrieving the spreadsheet data from the database \n" + ex);
            ex.printStackTrace();
        }
        return null;
    }

    public static ResultSet getCustomQuestions() {
        try {
            String sql = "SELECT questionID, question1, question2, question3, question4, question5, question6, question7"
                    + " FROM customquestions";

            PreparedStatement customquestions = conn.prepareStatement(sql);
            ResultSet question = customquestions.executeQuery();
            return question;

        } catch (SQLException ex) {
            System.out.print("/nThere has been an error in the Save Name and Numbers method \n" + ex);
        }
        return null;
    }

    public static ResultSet getCustomAnswers(int questionID) {
        try {
            String sql1 = "SELECT a.fk_idCustomquestions, c.firstname, c.lastname, a.answer1, a.answer2, a.answer3, a.answer4, a.answer5, a.answer6, a.answer7, a.fk_idClient"
                    + " FROM customanswers a, client c"
                    + " WHERE a.fk_idClient = c.idClient AND a.fk_idCustomquestions = " + questionID;

            PreparedStatement clientanswers = conn.prepareStatement(sql1);
            ResultSet answerlist = clientanswers.executeQuery();

            //TEST it
            //  System.out.print("\nSQL for getting Custom Questions List from database: \n  ");
            // System.out.print(sql1);            
            return answerlist;

        } catch (SQLException ex) {
            System.out.print("\n ***  There has been an error in getting all the customers answers from the database:  \n" + ex + "\n");
            ex.printStackTrace();
        }

        return null;
    }
}
