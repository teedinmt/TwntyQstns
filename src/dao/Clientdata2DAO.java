/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package dao;

import static dao.DBConnection.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Clientdata2DAO {

    String completed;
    String studenttype;
    String heardabout;
    String previousprogram;
    String lastschoolattended;
    String highesteducation;
    String schoolingbase;
    String status;
    String listeddisability;
    String barrier;
    String declaredbarrier;

    static int idEducation = 0;
    static int idEmployment = 0;
    static int idEmpBarrier = 0;
    static int thisidEmployment;
    public static int idClient = 0;

    public static ResultSet getClientList() {
        try {
            String sql = "SELECT idClient, enrollmentdate, firstname, lastname, mobilephone"
                    + " FROM client";
            PreparedStatement clist = conn.prepareStatement(sql);
            ResultSet clientlist = clist.executeQuery();   //create a customer result set...        
            //TEST it
            //System.out.print("\nSQL for getting Client List from database: \n  ");
            //System.out.print(sql);            
            return clientlist;
        } catch (SQLException ex) {
            System.out.print("/n ***  There has been an error in getting Client List from database:  " + ex);

        }
        return null;
    }

    //add Education and get idEducation
    public synchronized static void addEducation(String studenttype, String heardabout, String previousprogram, String lastschoolattended, String completed, String schoolingbase) throws SQLException {

        try {
            String sql = "INSERT INTO education (studenttype, heardabout, previousprogram, lastschoolattended, completed, schoolingbase)"
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement edudata = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            edudata.setString(1, studenttype);
            edudata.setString(2, heardabout);
            edudata.setString(3, previousprogram);
            edudata.setString(4, lastschoolattended);
            edudata.setString(5, completed);
            edudata.setString(6, schoolingbase);
            int res = edudata.executeUpdate();
            ResultSet rs = edudata.getGeneratedKeys();
            if (rs.next()) {
                idEducation = rs.getInt(1);
            }
            //TEST IT
            //System.out.print("\nSQL for adding education data from pg2:  ");
            //System.out.print(sql);
            if (res == 1) {//one row was affected; namely the one that was inserted!
                //   System.out.println("\n a row of data was added, idEducation is " + idEducation);
            } else {
                //    System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("Error in adding education info from pg2" + ex);
        }
    }
    
    //add Employment and get idEmployment
    public synchronized static void addEmployment(String status, String listeddisability) {
        try {
            String sql = "INSERT INTO employment (status, listeddisability)"
                    + "VALUES(?,?)";
            PreparedStatement empdata = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            empdata.setString(1, status);
            empdata.setString(2, listeddisability);

            int res = empdata.executeUpdate();
            ResultSet rs = empdata.getGeneratedKeys();
            if (rs.next()) {
                idEmployment = rs.getInt(1);
            }
            thisidEmployment = idEmployment;   // for use later as foreign key to barriers
            //TEST IT
            // System.out.print("\nSQL for adding employment data pg2:  ");
            // System.out.print(sql);
            if (res == 1) {//one row was affected; namely teh one that was inserted!
                // System.out.println("\n a row of data was added, idEmployment is " + idEmployment);
            } else {
                // System.out.println("\n row of data was NOT added, employment data pg2");
            }
        } catch (SQLException ex) {
            System.out.println("  Error in adding employment info pg2  " + ex);
        }
    }

    public synchronized static void addBarrier(String declaredbarrier) {
        try {
            String sql = "INSERT INTO employmentbarriers (declaredbarrier, fk_idEmployment)"
                    + "VALUES(?,?)";
            PreparedStatement empbardata = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            empbardata.setString(1, declaredbarrier);
            empbardata.setInt(2, thisidEmployment);
            int res = empbardata.executeUpdate();
            ResultSet rs = empbardata.getGeneratedKeys();
            if (rs.next()) {
                idEmpBarrier = rs.getInt(1);
            }

            //TEST IT
            //System.out.print("\nSQL for adding employment barrier:  ");
            //System.out.print(sql);
            if (res == 1) {//one row was affected; namely teh one that was inserted!
                // System.out.println("\n a row of data was added, a checked barrier with idEmpBarrier  " + idEmpBarrier + "  added to idEmployment " + thisidEmployment);
            } else {
                //     System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("Error in adding a barrier to employment barriers table " + ex);
        }
    }

    //add barriers as a string for reporting and for autofilling "yes" /"no" field
    public synchronized static void addStringBarrierList(String barrieryesorno, String barrierslist) {
        //TESTING variables being pass in . . .
        System.out.println("addStringBarrierList values added: " + barrieryesorno + " and " + barrierslist);

        try {
            String sql = "UPDATE employment"
                    + " SET barrier = ?, listofbarriers = ?"
                    + " WHERE idEmployment = ?";
            PreparedStatement barrierstring = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            barrierstring.setString(1, barrieryesorno);
            barrierstring.setString(2, barrierslist);
            barrierstring.setInt(3, thisidEmployment);

            int res = barrierstring.executeUpdate();
            //  System.out.print("\nSQL for adding barrier list as a string:  ");
            // System.out.print(sql);
            if (res == 1) {
                //      System.out.println("\n stringbarrierlist was added to " + thisidEmployment);
            } else {
                //    System.out.println("\n stringbarrierlist  was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("\n\nError in adding the barrierslist as a string to employment table \n" + ex);
        }
    }

    //finally adding client using all the ids we have collected
    public synchronized static void addClient(String enrollmentdate, String firstname, String middlename, String lastname,
            String socialsecurity, String dateOfbirth, String suffix, String mobilephone, String workphone, String email, String contactpreference) throws SQLException {
        try {
            String sqlc = "INSERT INTO client (enrollmentdate, firstname, middlename, lastname,\n"
                    + "socialsecurity, dateOfbirth, suffix, mobilephone, workphone, email, contactpreference,\n"
                    + "idAddress, idEmergencyContacts, idGender_Race, idEducation, idEmployment)"
                    + "VALUES(?,?,?,?,"
                    + "?,?,?,?,?,?,?,"
                    + "?,?,?,?,?)";
            PreparedStatement clientdata = conn.prepareStatement(sqlc, Statement.RETURN_GENERATED_KEYS);
            //System.out.print("\nSQL for adding a new client:  ");
            //  System.out.print(sqlc);

            clientdata.setString(1, enrollmentdate);
            clientdata.setString(2, firstname);
            clientdata.setString(3, middlename);
            clientdata.setString(4, lastname);

            clientdata.setString(5, socialsecurity);
            clientdata.setString(6, dateOfbirth);
            clientdata.setString(7, suffix);
            clientdata.setString(8, mobilephone);
            clientdata.setString(9, workphone);
            clientdata.setString(10, email);
            clientdata.setString(11, contactpreference);

            clientdata.setInt(12, Clientdata1DAO.idAddress);
            clientdata.setInt(13, Clientdata1DAO.idEmergencyContacts);
            clientdata.setInt(14, Clientdata1DAO.idGender_Race);
            clientdata.setInt(15, idEducation);
            clientdata.setInt(16, idEmployment);

            int res = clientdata.executeUpdate();
            ResultSet rs = clientdata.getGeneratedKeys();
            // System.out.print("\nvariables: " + enrollmentdate + " " + firstname + " " + middlename + " " + lastname + " \n" + socialsecurity + " " + dateOfbirth);
            if (rs.next()) {
                idClient = rs.getInt(1);
            }
            //TEST IT
            // System.out.print("\nSQL for adding a new client:  ");
            //  System.out.print(sqlc);
            if (res == 1) { //one row was affected; namely teh one that was inserted!
                // System.out.println("\n a row of data was added, a new Client with idClient: " + idClient);
            } else {
                //    System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("\n\n *** Error in adding a client:   " + ex);
        }
    }

}
