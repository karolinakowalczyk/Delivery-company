
package deliverycompany;

import UserInterface.LoginFrame;
import static deliverycompany.Data.load;
import java.io.*;

public class DeliveryCompany {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
       //dodanie elementów do list
       load();
       
       LoginFrame loginFrame = new LoginFrame();
       loginFrame.setVisible(true);
    }
}
