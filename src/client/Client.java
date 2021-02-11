
package client;

import deliverycompany.Package;
import static deliverycompany.Data.listOfPackages;
import static deliverycompany.Data.listOfRegionalSortingDepts;
import static deliverycompany.Data.listOfUsers;
public class Client {
    
    public static String currentlyLoggedIn;
    
    public String name;
    public String surname;
    public String login;
    public String password;
    
    public Client(String login, String password, String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }
    
    public static Package checkPackage(int id){
        for(int i = 0; i < Package.numberOfPackages; i++) {
            if(listOfPackages.get(i).IdNumber == id)
            {
                return listOfPackages.get(i);
            }
        }
        return null;
    }
    
    public static Boolean checkIfLoginCorrect(String login) {
        for(int i = 0; i < listOfUsers.size(); i++) {
            if(login.equals(listOfUsers.get(i).login)) {
                return false;
            }
        }
        return true;
    }
    
    public static Boolean checkIfCorrect(String name) {
        for(int i = 0; i < listOfRegionalSortingDepts.size(); i++) {
            if(name.equals(listOfRegionalSortingDepts.get(i).name)) {
                return true;
            }
        }
        return false;
    }
  
    public static void createPackage(double weight, String initialStation, String finalStation, String recipientName, String recipientSurname){
           listOfPackages.add(new Package(weight, initialStation, finalStation, recipientName, recipientSurname));
    }
}
