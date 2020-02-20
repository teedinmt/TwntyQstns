/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package model;

import java.time.LocalDate;
import java.util.Date;

//This is used when selecting a Client on the StaffScreenController
public class Client {

    private Date dateEnrolled;
    private String fname;
    private String mname;
    private String lname;
    private String socialsecurity;
    private LocalDate dateOfbirth;
    private String suffix;
    private String mobilephone;
    private String workphone;
    private String email;
    private String contactpreference;
    
     Client newclient;
  
    public Client(Date dateEnrolled, String fname, String mname, String lname, String socialsecurity, LocalDate dateOfbirth, String suffix, String mobilephone, String workphone, String email, String contactpreference) {
        this.dateEnrolled = dateEnrolled;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.socialsecurity = socialsecurity;
        this.dateOfbirth = dateOfbirth;
        this.suffix = suffix;
        this.mobilephone = mobilephone;
        this.workphone = workphone;
        this.email = email;
        this.contactpreference = contactpreference;      
    }

   
}