
package workers;

import deliverycompany.Package;
import java.util.*;
import java.time.*;
import static deliverycompany.Data.listOfWorkers;
import static deliverycompany.Data.findSortingDeptWorker;
import static deliverycompany.Data.CalculateDistance;
import static deliverycompany.Data.findPackage;
import static deliverycompany.Data.listOfPackages;
import static java.lang.Thread.sleep;

public class Courier extends Worker implements Runnable {
    
    private Thread t;
    private String threadName;
    
    public Boolean isBusy = false;
    public String currentStation = "centralna";
    
    public Courier (String name, String surname, String position, int startWorkAt, int finishWorkAt, double salary){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.startWorkAt = LocalTime.of(startWorkAt,0,0);
        this.finishWorkAt = LocalTime.of(finishWorkAt,0,0);
        this.salary = salary;
        
        if((LocalTime.now().isAfter(this.startWorkAt) || LocalTime.now().equals(this.startWorkAt))
                && (LocalTime.now().isBefore(this.finishWorkAt)) || LocalTime.now().equals(this.finishWorkAt)) {
            this.isWorking = true;
        } else {
            this.isWorking = false;
        }
        
        threadName = name + " " + surname;
        this.start();
    }
    
    @Override
    public Package checkPackage(int idNumber){
        Package packageToCheck = listOfPackages.get(findPackage(idNumber));
        if(packageToCheck.ifSomeoneHasIt) {
            System.out.println("Paczka w drodze.");
        }
        System.out.println("ID: " + packageToCheck.IdNumber);
        System.out.println("Stacja początkowa: " + packageToCheck.initialStation);
        System.out.println("Stacja końcowa: " + packageToCheck.finalStation);
        System.out.println("Pozostało kilometrów: " + packageToCheck.distanceLeft * 5);
        System.out.println("Adresat: " + packageToCheck.recipientName + " " + packageToCheck.recipientSurname);
        return packageToCheck;
    };
    
    public void transportPackage(Package pack, String initialStation, String destinationStation){
        
        System.out.println(name + " " + surname +" dostarcza paczke: " + pack.IdNumber);
        isBusy = true;
        pack.ifSomeoneHasIt = true;
        pack.whoHasIt = this;
        pack.currentStation = destinationStation;
        
        int distance = CalculateDistance(currentStation, initialStation) + CalculateDistance(initialStation, destinationStation);
        
        Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentStation = destinationStation;
                pack.distanceLeft -= distance;
               
                int stationWorker = findSortingDeptWorker(destinationStation);
                SortingDeptWorker deptWorker = (SortingDeptWorker)listOfWorkers.get(stationWorker);
                deptWorker.addPackageToDept(pack);
                isBusy = false;

                System.out.println(name + " " + surname + " dostarczyl paczke");
                timer.cancel();
            }
        }, distance * 1000, 1);
        
    }
    
    @Override
    public void run() {
        if(LocalTime.now().isAfter(finishWorkAt) || LocalTime.now().equals(finishWorkAt)) {
            endWork();
        } else {
            if(LocalTime.now().isAfter(startWorkAt) || LocalTime.now().equals(startWorkAt)) {
                startWork();
                currentStation = "centralna";
            }
        }
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Wątek" + threadName + "przerwany.");
        }
    }
    
    private void start() {
        if(t == null) {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}
