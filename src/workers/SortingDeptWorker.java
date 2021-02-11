
package workers;

import deliverycompany.Package;
import deliverycompany.SortingDepartment;
import functionalities.Pair;
import java.util.*;
import static deliverycompany.Data.CalculateDistance;
import static deliverycompany.Data.centralStation;
import static deliverycompany.Data.findPackage;
import static deliverycompany.Data.findStation;
import static deliverycompany.Data.listOfPackages;
import static deliverycompany.Data.listOfWorkers;
import static deliverycompany.Data.listOfProvinceSortingDepts;
import static deliverycompany.Data.listOfRegionalSortingDepts;
import static deliverycompany.Data.stationsLeft;
import java.time.*;

public class SortingDeptWorker extends Worker implements Runnable {
    
    private Thread t;
    private String threadName;
    
    public Queue <Package> packagesInQueue = new LinkedList <>();
    public String stationName;
    public SortingDepartment station;
    private int num;
    
    public SortingDeptWorker(String name, String surname, String position, String stationName, int startWorkAt, int finishWorkAt, double salary, int num){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.startWorkAt = LocalTime.of(startWorkAt, 0, 0);
        this.finishWorkAt = LocalTime.of(finishWorkAt, 0, 0);
        this.salary = salary;
        this.isWorking = false;
        this.stationName = stationName;
        this.num = num;
        this.threadName = name + " " + surname;
        
        if(stationName.equals(centralStation.name)) {
            station = centralStation;
        }
        else {
            Boolean ifFound = false;
            for(int i = 0; i < listOfProvinceSortingDepts.size(); i++) {
                if(stationName.equals(listOfProvinceSortingDepts.get(i).name)) {
                    station = listOfProvinceSortingDepts.get(i);
                    ifFound = true;
                    break;
                }
            }
            
            if(!ifFound) {
                for(int i = 0; i < listOfRegionalSortingDepts.size(); i++) {
                    if(stationName.equals(listOfRegionalSortingDepts.get(i).name)) {
                        station = listOfRegionalSortingDepts.get(i);
                        break;
                    }
                }
            }
        }
        this.start();
    }
    
    @Override
    public Package checkPackage(int idNumber){
        Package packageToCheck = listOfPackages.get(findPackage(idNumber));
        
        if(packageToCheck.ifSomeoneHasIt) {
            System.out.println("Paczka w drodze do: " + packageToCheck.currentStation);
            System.out.println("Przewozi ją: " + packageToCheck.whoHasIt.name + " " + packageToCheck.whoHasIt.surname);
        }
        else {
            System.out.println("Paczka znajduje się na stacji: " + packageToCheck.currentStation);
        }
        System.out.println("ID: " + packageToCheck.IdNumber);
        System.out.println("Stacja początkowa: " + packageToCheck.initialStation);
        System.out.println("Stacja końcowa: " + packageToCheck.finalStation);
        System.out.println("Pozostało kilometrów: " + packageToCheck.distanceLeft * 5);
        return packageToCheck;
    }
    
    int findPackageOnStation (int idNumber) {
        for(int i = 0; i < station.listOfPackagesInside.size(); i++) {
            if(idNumber == station.listOfPackagesInside.get(i).IdNumber) {
                return i;
            }
        }
        return -1;
    }
    
    void changePackageStatus(Package processedPackage){
        processedPackage.status = "dostarczona";
    }
    
    Package takePackage(){
        station.listOfPackagesInside.remove(findPackageOnStation(packagesInQueue.peek().IdNumber));
        return packagesInQueue.remove();
    }
    
    void givePackage(){
        
    }
    
    void addPackageToDept(Package pack){
        station.listOfPackagesInside.add(pack);
        packagesInQueue.add(pack);
    }
    
    String nextStation(String startStation, String endStation) {
        SortingDepartment start = findStation(startStation);
        SortingDepartment end = findStation(endStation);
        switch(start.departmentType) {
            case "regionalna":
                return start.stationAbove;
            case "wojewodzka":
                if(end.stationAbove.equals(start.name)) {
                    return end.name;
                }
                else {
                    return start.stationAbove;
                }
            case "centralna":
                if(end.departmentType.equals("regionalna")) {
                    return end.stationAbove;
                }
                else {
                    return end.name;
                }
        }
        return null;
    }
    
    private Pair findSuitableCourier(Package pack) {
        
        int stationsLeft = stationsLeft(pack.currentStation, pack.finalStation);
        int oneStatDistance = 0;
        int twoStatDistance = 0;
        int threeStatDistance = 0;
        int fourStatDistance = 0;
                        
        Pair bestResult = new Pair(-1, null);
        //station zero is currentStation
        String firstStation = "";
        String secondStation = "";
        String thirdStation = "";
        //fourth station is final station
        
        Courier courier;
        int currentBestCourierRank = 0;
        
        switch(stationsLeft) {
            case 1:
                //one
                firstStation = nextStation(pack.currentStation, pack.finalStation);
                oneStatDistance = CalculateDistance(pack.currentStation, firstStation) + 2;
                for (int i = 0; i < listOfWorkers.size(); i++) {
                    if(listOfWorkers.get(i) instanceof Courier) {
                        courier = (Courier)listOfWorkers.get(i);
                        if(!courier.isBusy && courier.isWorking) {
                            LocalTime timeUsed = LocalTime.of(0, 0, 0);
                        
                            timeUsed.plusSeconds(CalculateDistance(courier.currentStation, pack.currentStation) + oneStatDistance);
                        
                            LocalTime expectedTime = LocalTime.now().plusHours(timeUsed.getHour());
                            expectedTime.plusMinutes(timeUsed.getMinute());
                            expectedTime.plusSeconds(timeUsed.getSecond());
                        
                            if(expectedTime.isBefore(courier.finishWorkAt) || expectedTime.compareTo(courier.finishWorkAt) == 0) {
                                bestResult.first = i;
                                bestResult.second = firstStation;
                            }
                        }
                    }
                }
                break;
            case 2:
                //one
                firstStation = nextStation(pack.currentStation, pack.finalStation);
                oneStatDistance = CalculateDistance(pack.currentStation, firstStation) + 2;
                //two
                secondStation = nextStation(firstStation, pack.finalStation);
                twoStatDistance = oneStatDistance + CalculateDistance(firstStation, secondStation);
                
                for (int i = 0; i < listOfWorkers.size(); i++) {
                    if(listOfWorkers.get(i) instanceof Courier) {
                        courier = (Courier)listOfWorkers.get(i);
                        if(!courier.isBusy && courier.isWorking) {
                            LocalTime timeUsed = LocalTime.of(0, 0, 0);
                        
                            timeUsed.plusSeconds(CalculateDistance(courier.currentStation, pack.currentStation) + oneStatDistance);
                        
                            LocalTime expectedTime = LocalTime.now().plusHours(timeUsed.getHour());
                            expectedTime.plusMinutes(timeUsed.getMinute());
                            expectedTime.plusSeconds(timeUsed.getSecond());
                        
                            if(expectedTime.plusSeconds(twoStatDistance).isBefore(courier.finishWorkAt) || expectedTime.plusSeconds(twoStatDistance).compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 2) {
                                    
                                    currentBestCourierRank = 2;
                                    bestResult.first = i;
                                    bestResult.second = secondStation;
                                }
                            }
                            else if(expectedTime.isBefore(courier.finishWorkAt) || expectedTime.compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 1) {
                                    
                                    currentBestCourierRank = 1;
                                    bestResult.first = i;
                                    bestResult.second = firstStation;
                                }
                            }
                            
                        }
                    }
                }
                break;
            case 3:
                //one
                firstStation = nextStation(pack.currentStation, pack.finalStation);
                oneStatDistance = CalculateDistance(pack.currentStation, firstStation) + 2;
                //two
                secondStation = nextStation(firstStation, pack.finalStation);
                twoStatDistance = oneStatDistance + CalculateDistance(firstStation, secondStation);
                //three
                thirdStation = nextStation(secondStation, pack.finalStation);
                threeStatDistance = twoStatDistance + CalculateDistance(secondStation, thirdStation);
                
                for (int i = 0; i < listOfWorkers.size(); i++) {
                    if(listOfWorkers.get(i) instanceof Courier) {
                        courier = (Courier)listOfWorkers.get(i);
                        if(!courier.isBusy && courier.isWorking) {
                            LocalTime timeUsed = LocalTime.of(0, 0, 0);
                        
                            timeUsed.plusSeconds(CalculateDistance(courier.currentStation, pack.currentStation) + oneStatDistance);
                        
                            LocalTime expectedTime = LocalTime.now().plusHours(timeUsed.getHour());
                            expectedTime.plusMinutes(timeUsed.getMinute());
                            expectedTime.plusSeconds(timeUsed.getSecond());
                        
                            if(expectedTime.plusSeconds(threeStatDistance).isBefore(courier.finishWorkAt) || expectedTime.plusSeconds(threeStatDistance).compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 3) {
                                    
                                    currentBestCourierRank = 3;
                                    bestResult.first = i;
                                    bestResult.second = thirdStation;
                                }
                            }
                            else if(expectedTime.plusSeconds(twoStatDistance).isBefore(courier.finishWorkAt) || expectedTime.plusSeconds(twoStatDistance).compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 2) {
                                    
                                    currentBestCourierRank = 2;
                                    bestResult.first = i;
                                    bestResult.second = secondStation;
                                }
                            }
                            else if(expectedTime.isBefore(courier.finishWorkAt) || expectedTime.compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 1) {
                                    
                                    currentBestCourierRank = 1;
                                    bestResult.first = i;
                                    bestResult.second = firstStation;
                                }
                            }
                        }
                    }
                }
                break;
            case 4:
                //one
                firstStation = nextStation(pack.currentStation, pack.finalStation);
                oneStatDistance = CalculateDistance(pack.currentStation, firstStation) + 2;
                //two
                secondStation = nextStation(firstStation, pack.finalStation);
                twoStatDistance = oneStatDistance + CalculateDistance(firstStation, secondStation);
                //three
                thirdStation = nextStation(secondStation, pack.finalStation);
                threeStatDistance = twoStatDistance + CalculateDistance(secondStation, thirdStation);
                //four
                fourStatDistance = pack.distanceLeft + 2;
                
                for (int i = 0; i < listOfWorkers.size(); i++) {
                    if(listOfWorkers.get(i) instanceof Courier) {
                        courier = (Courier)listOfWorkers.get(i);
                        if(!courier.isBusy && courier.isWorking) {
                            LocalTime timeUsed = LocalTime.of(0, 0, 0);
                        
                            timeUsed.plusSeconds(CalculateDistance(courier.currentStation, pack.currentStation) + oneStatDistance);
                        
                            LocalTime expectedTime = LocalTime.now().plusHours(timeUsed.getHour());
                            expectedTime.plusMinutes(timeUsed.getMinute());
                            expectedTime.plusSeconds(timeUsed.getSecond());
                        
                            if(expectedTime.plusSeconds(fourStatDistance).isBefore(courier.finishWorkAt) || expectedTime.plusSeconds(fourStatDistance).compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 4) {
                                    
                                    currentBestCourierRank = 4;
                                    bestResult.first = i;
                                    bestResult.second = pack.finalStation;
                                }
                            }
                            else if(expectedTime.plusSeconds(threeStatDistance).isBefore(courier.finishWorkAt) || expectedTime.plusSeconds(threeStatDistance).compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 3) {
                                    
                                    currentBestCourierRank = 3;
                                    bestResult.first = i;
                                    bestResult.second = thirdStation;
                                }
                            }
                            else if(expectedTime.plusSeconds(twoStatDistance).isBefore(courier.finishWorkAt) || expectedTime.plusSeconds(twoStatDistance).compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 2) {
                                    
                                    currentBestCourierRank = 2;
                                    bestResult.first = i;
                                    bestResult.second = secondStation;
                                }
                            }
                            else if(expectedTime.isBefore(courier.finishWorkAt) || expectedTime.compareTo(courier.finishWorkAt) == 0) {
                                if(currentBestCourierRank < 1) {
                                    
                                    currentBestCourierRank = 1;
                                    bestResult.first = i;
                                    bestResult.second = firstStation;
                                }
                            }
                        }
                    }
                }
                break;
        }
        return bestResult;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(num * 5);
            while(true) {
                if(packagesInQueue.isEmpty()) {
            
                    //Jesli nie ma paczek do przerobienia
                    Thread.sleep(100);                
                }
                else {
                    //Jesli sa paczki do przerobienia
                    
                    Package processedPackage = takePackage();                    
                    if(processedPackage.currentStation.equals(processedPackage.finalStation)) {
                        //paczka zostala dostarczona
                        changePackageStatus(processedPackage);
                        
                    }
                    else {
                        //wyszukiwanie kuriera do przewiezienia paczki
                        while(true) {
                            Pair courier = findSuitableCourier(processedPackage);
                            
                            if(courier.first != -1) {
                                //danie paczki kurierowi
                                Courier suitableCourier = (Courier)listOfWorkers.get(courier.first);
                                suitableCourier.isBusy = true;
                                Thread.sleep(2000);
                                suitableCourier.transportPackage(processedPackage, stationName, courier.second);
                                break;
                            }
                            else {
                                //nie ma dostepnych kurierow
                                Thread.sleep(2000);
                            }
                        }
                    }
                }
            }
        } 
        catch (InterruptedException ex) {
            System.out.println("Watek " + threadName + "przerwany.");        }
    };
    
    private void start() {
        if(t == null) {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}
