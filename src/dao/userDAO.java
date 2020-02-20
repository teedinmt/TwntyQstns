/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package dao;

import static dao.DBConnection.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class userDAO {
    String userName;
    String passWord;
    
 public static ResultSet UserLogin(String userName, String passWord) throws SQLException {
    
  String sql = "SELECT * FROM user WHERE username=? AND password=?";
   PreparedStatement pst = conn.prepareStatement(sql);
    
   pst.setString(1, userName);
   pst.setString(2, passWord);
  // System.out.println("SQL for user login: \n");
  // System.out.println(sql);
   
           ResultSet rs = pst.executeQuery();
     return rs;
 }  
    
    
    
    
}
