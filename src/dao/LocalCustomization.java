
/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package dao;
 /* The purpose of this class: to customize the program for a specific entity
 *  --SETS UP constants for data pathways for db connection, db location, report locations, error messages
 *  --CONTAINS notes about the modification of specific files or entity requests
 */

/*     (For Example, Customized notes could be added as follows . . . 
ENTITY:   "xxxxxxx  Program"    Version 1.0 
Customized as follows:
>> using G drive for MS Access file: G:\ABEReg\Private
>> using G drive for reports: G:\ABEReg\Private\GeneratedReports
>> requesting Contact Preference contains only two options, 
        --see Clientscreen1Controller file, @line 152
>> requesting How did you Hear about Us be a list to choose from
         --see Clientscreen2Controller file,  @line 130
>> in Clientscreen2Controller, a PDF is created as the applicant clicks "submit"
 (yes, this repeats the code to set up a PDF report from the StaffScreenController) 
END OF  "Xxxxxxx Program"    Version 1.0
 */ 
public class LocalCustomization {
//Example how this might be set up for another location:
  //Note:  DATABASE LOCATION used in DBConnection file, around line 26   
  // public static String PATHTODB= "\\\\xxxx.xxxx.edu\\groups\\ABEReg\\Private\\twntyqstns.accdb";
  // public static String PATHTOGETPDF = "G:\\ABEReg\\Private\\";      
  // public static String PATHTOSAVEPDF = "G:\\ABEReg\\Private\\GeneratedReports\\";   
  // public static String PATHTOSAVESPREADSHEETS = "G:\\ABEReg\\Private\\GeneratedReports\\";     
//Notes: FILENAMES above used in reports, StaffScreenController file, around lines
//Notes:  301,  363,  458, 561, 885
        
//DATABASE LOCATION and FILENAMES  for Programmer's Home connection, flash drive in router:
public static String PATHTODB = "\\\\readyshare\\USB_Storage\\twntyqstns.accdb";
public static String PATHTOGETPDF = "\\\\readyshare\\USB_Storage\\";
public static String PATHTOSAVEPDF = "\\\\readyshare\\USB_Storage\\GeneratedReports\\";
public static String PATHTOSAVESPREADSHEETS = "\\\\readyshare\\USB_Storage\\GeneratedReports\\";
       
//Customized error messages:  first is check pathway to network location, second is db file,
//see LoginPageController.java file for how these are implemented
//These can be changed based on a customized installation
    public static String CUSTOMERROR1 = "You appear to not be connecting to the location of your database.\n Are you using the correct network login?";
    public static String CUSTOMERROR2 = "The database file needed to run this program was not found.\n Check to be sure the file has not been moved.";
    public static String CUSTOMERROR3 = "The pathway for saving reports is missing...\n Recreate the ReportsGenterated folder in your network pathway.";

}
