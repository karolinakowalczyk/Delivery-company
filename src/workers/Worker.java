
package workers;

import deliverycompany.Package;
import java.time.*;

public abstract class Worker {
    public String name;
    public String surname;
    public String position;
    public LocalTime startWorkAt;
    public LocalTime finishWorkAt;
    public double salary;
    public Boolean isWorking;
    
    public abstract Package checkPackage(int idNumber);
    
    void startWork(){
        isWorking = true;
    };
    
    void endWork(){
        isWorking = false;
    }
}
