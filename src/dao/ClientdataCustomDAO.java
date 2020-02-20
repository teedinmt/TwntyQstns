/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package dao;

import static dao.DBConnection.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientdataCustomDAO {

    static int questionID;
    static int answerID;


    public synchronized static void insertClientAnswers(String answer1, String answer2, String answer3, String answer4, String answer5, String answer6, String answer7) throws SQLException {
        try {
            String sql = "INSERT INTO customanswers (fk_idCustomquestions, answer1, answer2, answer3, answer4, answer5, answer6, answer7, fk_idClient)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement cquestionanswers = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            cquestionanswers.setInt(1, questionID);
            cquestionanswers.setString(2, answer1);
            cquestionanswers.setString(3, answer2);
            cquestionanswers.setString(4, answer3);
            cquestionanswers.setString(5, answer4);
            cquestionanswers.setString(6, answer5);
            cquestionanswers.setString(7, answer6);
            cquestionanswers.setString(8, answer7);
            cquestionanswers.setInt(9, Clientdata2DAO.idClient);

            int res = cquestionanswers.executeUpdate();
            ResultSet rs = cquestionanswers.getGeneratedKeys();
            if (rs.next()) {
                answerID = rs.getInt(1);
            }
            //TEST IT
            //   System.out.print("\nSQL for adding answers from pg3:  ");
            //  System.out.print(sql);       
            if (res == 1) {//one row was affected; namely teh one that was inserted!
                // System.out.println("\n a row of data was added, answerID: " + answerID);
            } else {
                //  System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("\n Error inserting into customanswers on page 3  " + ex);
        }
    }

    public static ResultSet getCustomQuestions() {
        try {
            String sql = "SELECT questionID, question1, question2, question3, question4, question5, question6, question7"
                    + " FROM customquestions";
            PreparedStatement listquestions = conn.prepareStatement(sql);
            ResultSet listcustomquestions = listquestions.executeQuery();   //create a customer result set...        
            //TEST it
            //       System.out.print("\nSQL for getting Custom Questions List from database: \n  ");
            //   System.out.print(sql);            
            return listcustomquestions;

        } catch (SQLException ex) {
            System.out.print("/n ***  There has been an error in getting the Questions from database:  " + ex);

        }

        return null;
    }

    public synchronized static void setCustomQuestions(String question1, String question2, String question3, String question4, String question5, String question6, String question7) throws SQLException {

        try {
            String questionsql = "INSERT INTO customquestions (question1, question2, question3, question4, question5, question6, question7)"
                    + "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement customquestions = conn.prepareStatement(questionsql, Statement.RETURN_GENERATED_KEYS);
            customquestions.setString(1, question1);
            customquestions.setString(2, question2);
            customquestions.setString(3, question3);
            customquestions.setString(4, question4);
            customquestions.setString(5, question5);
            customquestions.setString(6, question6);
            customquestions.setString(7, question7);
            int res = customquestions.executeUpdate();
            ResultSet rs = customquestions.getGeneratedKeys();
            if (rs.next()) {
                questionID = rs.getInt(1);
            }

            //TEST IT
            //   System.out.print("\nSQL for adding a new client:  ");
            //   System.out.print(questionsql);
            if (res == 1) {//one row was affected; namely teh one that was inserted!
                System.out.println("\n a row of data was added, a set of questions, ID: " + questionID);
            } else {
                System.out.println("\n questions were NOT added to the database");
            }
        } catch (SQLException ex) {
            System.out.println("\n\n *** Error in adding custom questions to the database:   " + ex);
        }

    }

    public static ResultSet getPreviousQuestions(int questionID) {
        try {

            if (questionID != 0) {
                String sql = "SELECT question1, question2, question3, question4, question5, question6, question7"
                        + " FROM customquestions"
                        + " WHERE questionID=" + questionID;
                PreparedStatement listquestions = conn.prepareStatement(sql);
                ResultSet listcustomquestions = listquestions.executeQuery();   //create a customer result set...        
                //TEST it
                //     System.out.print("\nSQL for getting Custom Questions List from database: \n  ");
                //  System.out.print(sql);            
                return listcustomquestions;
            } else {

            }
        } catch (SQLException ex) {
            System.out.print("/n ***  There has been an error in getting the Questions from database:  " + ex);

        }

        return null;
    }
}
