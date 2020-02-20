/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package dao;

import static dao.DBConnection.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Clientdata1DAO {

    String lName;
    String fName;
    String mName;
    String ssnumber;
    String gender;
    String race;
    String hispanic;

    String address;
    String state;
    String city;
    String zipcode;
    String county;
    String homephone;

    String emContactName;
    String emContactAddress;
    String emContactPhone;
    String contactRelationship;
    String email;

    String mobilePhone;
    String workPhone;
    String contactpreference;

    //for returning ids from insert statements
    //these will then be used when inserting the client
    static int idAddress = 0;
    static int idRace = 0;
    static int idGender_Race = 0;
    static int idEmergencyContacts = 0;
    static int thisidGender_Race;

    public synchronized static void insertRaceData1(String race) throws SQLException {
        try {
            String sql1 = "INSERT INTO race (raceidentified, fk_idgender_race) "
                    + "VALUES (?,?)";
            PreparedStatement race1data = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            race1data.setString(1, race);
            race1data.setInt(2, thisidGender_Race);
            int res = race1data.executeUpdate();
            ResultSet rs = race1data.getGeneratedKeys();
            if (rs.next()) {
                idRace = rs.getInt(1);
            }
            //TEST IT
            //System.out.print("\nSQL for adding race 1 data:  ");
            //System.out.print(sql1);
            if (res == 1) {//one row was affected; namely teh one that was inserted!
                //    System.out.println("\n a row of data was added, idRace is " + idRace + " to idGender_Race " + thisidGender_Race);
            } else {
                //    System.out.println("\n row of data was NOT added");
            }

        } catch (SQLException ex) {
            System.out.println("Error in adding race info" + ex);
        }
    }

//For ease of creating reports, a raceslist is also added as a "string"
    public synchronized static void insertStringRaceList(String raceslist) throws SQLException {
        try {
            String sql = "UPDATE gender_race "
                    + " SET listofraces = ?"
                    + " WHERE idGender_Race = ?";
            PreparedStatement racestring = conn.prepareStatement(sql);
            racestring.setString(1, raceslist);
            racestring.setInt(2, thisidGender_Race);
            int res = racestring.executeUpdate();

            //TEST IT
            //  System.out.print("\nSQL for adding stringracelist to gender_race table:  ");
            //  System.out.print(sql);
            if (res == 1) {
                //    System.out.println("\n string raceslist was added, " + thisidGender_Race + ":  " + raceslist);
            } else {
                //    System.out.println("\n raceslist data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("\n\nError in adding raceslist string to gender_race table: \n" + ex);
        }
    }

    // add race data and get back idGender_Race
    public synchronized static void insertRaceData2(String hispanic, String gender) throws SQLException {
        try {
            String sql2 = "INSERT INTO gender_race (hispanic, gender)"
                    + "VALUES (?,?)";
            PreparedStatement race2data = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            //add  gender_race data   get back gender_race id
            race2data.setString(1, hispanic);
            race2data.setString(2, gender);
            int res = race2data.executeUpdate();
            ResultSet rs = race2data.getGeneratedKeys();
            if (rs.next()) {
                idGender_Race = rs.getInt(1);
            }
            thisidGender_Race = idGender_Race;
            //TEST IT      
            //  System.out.print("\nSQL for adding race 2 data:  ");
            //  System.out.print(sql2);
            if (res == 1) {//one row was affected; namely teh one that was inserted!
                //       System.out.println("\nhispanic value: " + hispanic + " and  gender value:  " + gender);
                //      System.out.println("\n a row of data was added, idGender_Race returned is  " + idGender_Race + "//" + thisidGender_Race);
            } else {
                //     System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("Error in adding race info" + ex);
        }
    }

    //add EmergencyContacts and get idEmergencyContacts
    public synchronized static void insertEmContactData(String emContactName, String emContactPhone, String emContactAddress, String contactRelationship) throws SQLException {
        try {
            String sql = "INSERT INTO emergencycontacts (emname, emphone, emaddress, emrelation)"
                    + " VALUES (?,?,?,?)";
            PreparedStatement condata = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //get customer info from the screen  
            condata.setString(1, emContactName);
            condata.setString(2, emContactPhone);
            condata.setString(3, emContactAddress);
            condata.setString(4, contactRelationship);
            int res = condata.executeUpdate();
            ResultSet rs = condata.getGeneratedKeys();
            if (rs.next()) {
                idEmergencyContacts = rs.getInt(1);
            }
            //TEST IT
            //  System.out.print("\nSQL for adding emergency contacts:  ");
            //  System.out.print(sql);
            if (res == 1) {//one row was affected; namely thr one that was inserted!
                //      System.out.println("\n a row of data was added, idEmergencyContacts returned is  " + idEmergencyContacts);
            } else {
                //      System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("Error in adding emergency contact info" + ex);
        }

    }

    //add address data and get idAddress
    public synchronized static void insertAddressData(String address, String city, String state, String zipcode, String county, String homephone) {
        try {
            String sql = "INSERT INTO address (address, city, state, zipcode, county, homephone)"
                    + " VALUES (?,?,?,?,?,?)";
            PreparedStatement adddata = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            adddata.setString(1, address);
            adddata.setString(2, city);
            adddata.setString(3, state);
            adddata.setString(4, zipcode);
            adddata.setString(5, county);
            adddata.setString(6, homephone);
            int res = adddata.executeUpdate();
            ResultSet rs = adddata.getGeneratedKeys();
            if (rs.next()) {
                idAddress = rs.getInt(1);
            }
            //TEST IT
            //  System.out.print("\nSQL for adding addressinfo:  ");
            //  System.out.print(sql);
            if (res == 1) {//one row was affected; namely thr one that was inserted!
                //     System.out.println("\n a row of data was added, idAddress returned is  " + idAddress);
            } else {
                //     System.out.println("\n row of data was NOT added");
            }
        } catch (SQLException ex) {
            System.out.println("Error in adding address info" + ex);
        }
    }

}
