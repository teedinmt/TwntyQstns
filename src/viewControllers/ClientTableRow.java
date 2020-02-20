/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt 
 */
package viewControllers;

import javafx.beans.value.ObservableValue;

//This is used on staff screen to list Clients in database
class ClientTableRow {     
    private ObservableValue<String> idClient;
    private ObservableValue<String> enrollmentdate;
    private ObservableValue<String> firstname;
    private ObservableValue<String> lastname;
    private ObservableValue<String> mobilephone;   
    
    public ClientTableRow (ObservableValue<String> idClient, ObservableValue<String> enrollmentdate, ObservableValue<String> firstname, ObservableValue<String> lastname, ObservableValue<String> mobilephone){
        this.idClient = idClient;
        this.enrollmentdate = enrollmentdate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobilephone = mobilephone;        
    }


    public ObservableValue<String> getEnrollmentdate() {
        return enrollmentdate;
    }

    public ObservableValue<String> getFirstname() {
        return firstname;
    }

    public ObservableValue<String> getLastname() {
        return lastname;
    }

    public ObservableValue<String> getPhonenumber() {
        return mobilephone;
    }

    public ObservableValue<String> getIdClient() {
        return idClient;
    }
    
    
}
