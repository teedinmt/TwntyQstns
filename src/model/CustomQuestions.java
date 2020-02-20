/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package model;

//This class is used for creation and recall on the StaffScreenController.java
//and ClientdataCustomDAO.java
public class CustomQuestions {
    private String ID;
    private String Text;
    
    public CustomQuestions(String ID, String Text){
        this.ID = ID;
        this.Text = Text;
    }
}
