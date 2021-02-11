
package deliverycompany;
import static deliverycompany.Data.CalculateDistance;
import static deliverycompany.Data.findSortingDeptWorker;
import static deliverycompany.Data.listOfWorkers;
import java.util.*;
import workers.Courier;
import workers.SortingDeptWorker;

public class Package {
   
    public Package(double weight, String initialStation, String finalStation, String recipientName, String recipientSurname){
        this.IdNumber = 100000 + numberOfPackages;
        this.weight = weight;
        this.initialStation = initialStation;
        this.finalStation = finalStation;
        this.currentStation = initialStation;
        this.recipientName = recipientName;
        this.recipientSurname = recipientSurname;
        
        numberOfPackages++;
        
        this.distanceLeft = CalculateDistance(initialStation, finalStation);
        
        SortingDeptWorker deptWorker = (SortingDeptWorker)listOfWorkers.get(findSortingDeptWorker(initialStation));
        deptWorker.packagesInQueue.add(this);
        deptWorker.station.listOfPackagesInside.add(this);
    }
   public static int numberOfPackages = 0;
   public  int IdNumber;
   public double weight;
   public String initialStation;
   public String finalStation;
   public String currentStation;
   public Date avaibleForCourierFrom; // jak sortingDeptworker zmieni status na "gotowa do przeowzu"
   public Boolean ifSomeoneHasIt = false;
   public Courier whoHasIt;
   public String recipientName;
   public String recipientSurname;
   public int distanceLeft;
   public String status = "nie dostarczona";
   
}
