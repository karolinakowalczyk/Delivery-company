
package deliverycompany;

import client.Client;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import workers.Courier;
import workers.Manager;
import workers.SortingDeptWorker;
import workers.Worker;

public abstract class Data {
    
    public static ArrayList <Package> listOfPackages = new ArrayList <Package> ();
    
    public static ArrayList <Worker> listOfWorkers = new ArrayList <Worker> ();
    
    public static ArrayList <SortingDepartment> listOfProvinceSortingDepts = new ArrayList <SortingDepartment> ();
    public static ArrayList <SortingDepartment> listOfRegionalSortingDepts = new ArrayList <SortingDepartment> ();
    
    public static ArrayList <Client> listOfUsers = new ArrayList <Client> ();
    
    public static SortingDepartment centralStation;
    
    public static int CalculateDistance(String initialStation, String finalStation) {
        int distance = 0;
        
        int inStat = 0;
        int finStat = 0;
        
        for(int i = 0; i < listOfRegionalSortingDepts.size(); i++) {
            if(initialStation.equals(listOfRegionalSortingDepts.get(i).name)) {
                inStat = i;
            }
            else if(finalStation.equals(listOfRegionalSortingDepts.get(i).name)) {
                finStat = i;
            }
        }
        
        distance = listOfRegionalSortingDepts.get(inStat).distanceToStationAbove + listOfRegionalSortingDepts.get(finStat).distanceToStationAbove;
        if(!listOfRegionalSortingDepts.get(inStat).stationAbove.equals(listOfRegionalSortingDepts.get(finStat).stationAbove)) {
            for(int i = 0; i < listOfProvinceSortingDepts.size(); i++) {
                if(listOfRegionalSortingDepts.get(inStat).stationAbove.equals(listOfProvinceSortingDepts.get(i).name)) {
                    distance += listOfProvinceSortingDepts.get(i).distanceToStationAbove;
                }
                else if(listOfRegionalSortingDepts.get(finStat).stationAbove.equals(listOfProvinceSortingDepts.get(i).name)) {
                    distance += listOfProvinceSortingDepts.get(i).distanceToStationAbove;
                }
            }
        }
        return distance;
    }
    
    public static int stationsLeft(String startStationName, String endStationName) {
        SortingDepartment startStation = findStation(startStationName);
        SortingDepartment endStation = findStation(endStationName);
        
        if(endStation.name.equals(startStation.name)) {
            return 0;
        }
        
        switch (endStation.departmentType) {
            case "regionalna":
            switch (startStation.departmentType) {
                case "regionalna":
                    if(startStation.stationAbove.equals(endStation.stationAbove)) {
                        //obecna -> wojewodzka -> docelowa
                        return 2;
                    }
                    else {
                        //obecna -> wojewodzka -> centralna -> wojewodzka -> docelowa
                        return 4;
                    }
                case "wojewodzka":
                    if(startStation.name.equals(endStation.stationAbove)) {
                        //obecna -> docelowa
                        return 1;
                    }
                    else {
                        //obecna -> centralna -> wojewodzka -> docelowa
                        return 3;
                    }
                default:
                    return 2;
            }

            case "wojewodzka":
            switch (startStation.departmentType) {
                case "regionalna":
                    if(startStation.stationAbove.equals(endStation.name)) {
                        //obecna -> docelowa
                        return 1;
                    } else {
                        //obecna -> wojewodzka -> centralna -> docelowa
                        return 3;
                    }
                case "wojewodzka":
                    //obecna -> centralna -> docelowa
                    return 2;
                default:
                    //obecna -> docelowa
                    return 1;
            }

            default:
                if(startStation.departmentType.equals("regionalna")) {
                    return 2;
                }
                else {
                    return 1;
                }
        }
    }
    
    public static SortingDepartment findStation(String name) {

        if(name.equals(centralStation.name)) {
            return centralStation;
        }
        for(int i = 0; i < listOfProvinceSortingDepts.size(); i++) {
            if(name.equals(listOfProvinceSortingDepts.get(i).name)) {
                return listOfProvinceSortingDepts.get(i);
            }
        }
        for(int i = 0; i < listOfRegionalSortingDepts.size(); i++) {
            if(name.equals(listOfRegionalSortingDepts.get(i).name)) {
                return listOfRegionalSortingDepts.get(i);
            }
        }
        System.out.println("Blad podczas findStation("+name+"), nie znaleziono");
        return null;
    }
    
    public static int findPackage(int idNumber) {
        for(int i = 0; i < listOfPackages.size(); i++) {
            if(idNumber == listOfPackages.get(i).IdNumber) {
                return i;
            }
        }
        return -1;
    }
    
    public static Worker findWorker(String name, String surname) {
       
        for(int i = 0; i < listOfWorkers.size(); i++) {
            if(name.equals(listOfWorkers.get(i).name) && surname.equals(listOfWorkers.get(i).surname)) {
                return listOfWorkers.get(i);
            }
        }
        return null;
    }
    
    public static int findCourier(String name, String surname) {
        
        for(int i = 0; i < listOfWorkers.size(); i++) {
            if(listOfWorkers.get(i) instanceof Courier) {
                if(name.equals(listOfWorkers.get(i).name) && surname.equals(listOfWorkers.get(i).surname)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int findSortingDeptWorker(String name, String surname) {
        
        for(int i = 0; i < listOfWorkers.size(); i++) {
            if(listOfWorkers.get(i) instanceof SortingDeptWorker) {
                if(name.equals(listOfWorkers.get(i).name) && surname.equals(listOfWorkers.get(i).surname)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int findSortingDeptWorker(String station) {
       
        for(int i = 0; i < listOfWorkers.size(); i++) {
            if(listOfWorkers.get(i) instanceof SortingDeptWorker) {
                SortingDeptWorker deptWorker = (SortingDeptWorker)listOfWorkers.get(i);
                if(station.equals(deptWorker.stationName)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private static void loadWorkers() {
        int num = 0;
       try {
           BufferedReader workers = new BufferedReader(new FileReader("listOfWorkers.txt"));
           String lineJustFetched = null;
           workers.readLine();
           while(true) {
               lineJustFetched = workers.readLine();
               if(lineJustFetched == null) {
                   break;
               }
               else {
                   
                   String[] wordsArray = lineJustFetched.split("\t");
                   switch (wordsArray[2]) {
                       case "manager":
                           listOfWorkers.add(new Manager(wordsArray[0], wordsArray[1], wordsArray[2], Integer.parseInt(wordsArray[3]), Integer.parseInt(wordsArray[4]), Double.parseDouble(wordsArray[5])));
                           break;
                       case "courier":
                           listOfWorkers.add(new Courier(wordsArray[0], wordsArray[1], wordsArray[2], Integer.parseInt(wordsArray[3]), Integer.parseInt(wordsArray[4]), Double.parseDouble(wordsArray[5])));
                           break;
                       case "sortingDeptWorker":
                           listOfWorkers.add(new SortingDeptWorker(wordsArray[0], wordsArray[1], wordsArray[2], wordsArray[3], Integer.parseInt(wordsArray[4]), Integer.parseInt(wordsArray[5]), Double.parseDouble(wordsArray[6]), num));
                           num++;
                           break;
                   }
               }
           }
           workers.close();
           System.out.println("Zakonczono wczytywanie pracownikow");
           
       } catch(Exception e) {
           System.out.println("Wystąpił błąd podczas wczytywania pracownika.");
           System.out.println(e);
       }
    }
    
    private static void loadStations() {
        try {
           BufferedReader stations = new BufferedReader(new FileReader("listOfStations.txt"));
           String lineJustFetched = null;
           String[] wordsArray;
           stations.readLine();
           while(true) {
               lineJustFetched = stations.readLine();
               if(lineJustFetched == null) {
                   break;
               }
               else {
                   wordsArray = lineJustFetched.split("\t");
                   
                   switch (wordsArray[1]) {
                       case "regionalna":
                           listOfRegionalSortingDepts.add(new SortingDepartment(wordsArray[0], wordsArray[1], wordsArray[2], Integer.parseInt(wordsArray[3])));
                           break;
                       case "wojewodzka":
                           listOfProvinceSortingDepts.add(new SortingDepartment(wordsArray[0], wordsArray[1], wordsArray[2], Integer.parseInt(wordsArray[3])));
                           break;
                       case "centralna":
                           centralStation = new SortingDepartment(wordsArray[0], wordsArray[1]);
                           break;
                       default:
                           System.out.println(lineJustFetched);
                           break;
                   }
               }
           }
           stations.close();
           System.out.println("Zakonczono wczytywanie stacji");
       } catch (Exception e) {
           System.out.println("Wystąpił błąd podczas wczytywania stacji.");
       }
    }
    
    private static void loadPackages() {
        try {
           BufferedReader packages = new BufferedReader(new FileReader("listOfPackages.txt"));
           String lineJustFetched = null;
           String[] wordsArray;
           packages.readLine();
           while(true) {
               lineJustFetched = packages.readLine();
               if(lineJustFetched == null) {
                   break;
               }
               else {
                   wordsArray = lineJustFetched.split("\t");
                   listOfPackages.add(new Package(Double.parseDouble(wordsArray[0]), wordsArray[1], wordsArray[2], wordsArray[3], wordsArray[4]));
               }
           }
           packages.close();
                      System.out.println("Zakonczono wczytywanie paczek");

       } catch (Exception e) {
           System.out.println("Wystąpił błąd podczas wczytywania paczek");
           System.out.println(e);
       }
    }
    
    private static void loadUsers() {
        try {
            BufferedReader users = new BufferedReader(new FileReader("listOfUsers.txt"));
            String lineJustFetched = null;
            String[] wordsArray;
            users.readLine();
            
            while(true) {
                lineJustFetched = users.readLine();
                if(lineJustFetched == null) {
                    break;
                }
                else {
                    wordsArray = lineJustFetched.split("\t");
                    listOfUsers.add(new Client(wordsArray[0], wordsArray[1], wordsArray[2], wordsArray[3]));
                }
            }
            users.close();
            System.out.println("Zakończono wczytywanie użytkowników");
        }
        catch (Exception e) {
            System.out.println("Wystąpił błąd podczas wczytywania użytkowników");
            System.out.println(e);
        }
    }
    
    public static void load() throws FileNotFoundException, IOException {
        loadStations();
        loadWorkers();
        loadPackages();
        loadUsers();
    }
    
    private static void saveWorkers() {
        try {
            PrintStream fileStream = new PrintStream(new File("listOfWorkers.txt"));
            
            fileStream.println();
            
            for(int i = 0; i < listOfWorkers.size(); i++) {
                if(listOfWorkers.get(i) instanceof Manager) {
                    Manager manager = (Manager)listOfWorkers.get(i);
                    fileStream.println(manager.name + "\t" 
                            + manager.surname + "\t" 
                            + manager.position + "\t" 
                            + manager.startWorkAt.getHour() + "\t" 
                            + manager.finishWorkAt.getHour() + "\t" 
                            + manager.salary);
                } else if(listOfWorkers.get(i) instanceof Courier) {
                    Courier courier = (Courier)listOfWorkers.get(i);
                    fileStream.println(courier.name + "\t" 
                            + courier.surname + "\t" 
                            + courier.position + "\t" 
                            + courier.startWorkAt.getHour() + "\t" 
                            + courier.finishWorkAt.getHour() + "\t" 
                            + courier.salary);
                } else if(listOfWorkers.get(i) instanceof SortingDeptWorker) {
                    SortingDeptWorker sworker = (SortingDeptWorker)listOfWorkers.get(i);
                    fileStream.println(sworker.name + "\t"
                            + sworker.surname + "\t"
                            + sworker.position + "\t"
                            + sworker.stationName + "\t"
                            + sworker.startWorkAt.getHour() + "\t"
                            + sworker.finishWorkAt.getHour() + "\t"
                            + sworker.salary);
                }
            }
        } catch (Exception ex) {
            System.out.println("Błąd podczas zapisywania pracowników");
            System.out.println(ex);
        }
        
    }
    
    private static void savePackages() {
        try {
            PrintStream fileStream = new PrintStream(new File("listOfPackages.txt"));
            
            fileStream.println();
            
            for (int i = 0; i < listOfPackages.size(); i++) {
                Package pack = listOfPackages.get(i);
                fileStream.println(pack.weight + "\t" 
                        + pack.initialStation + "\t" 
                        + pack.finalStation + "\t" 
                        + pack.recipientName + "\t" 
                        + pack.recipientSurname);
            }
        } catch (Exception ex) {
            System.out.println("Błąd podczas zapisywania paczek");
            System.out.println(ex);
        }
    }
    
    private static void saveUsers() {
        try {
            PrintStream fileStream = new PrintStream(new File("listOfUsers.txt"));
            
            fileStream.println();
            
            for (int i = 0; i < listOfUsers.size(); i++) {
                Client user = listOfUsers.get(i);
                fileStream.println(user.login + "\t"
                        + user.password + "\t"
                        + user.name + "\t"
                        + user.surname);
            }
        } catch (Exception ex) {
            System.out.println("Błąd podczas zapisywania paczek");
            System.out.println(ex);
        }
    }
    
    public static void save() {
        saveWorkers();
        savePackages();
        saveUsers();
    }
    
    private static Boolean userAlreadyExists(String login) {
        for (int i = 0; i < listOfUsers.size(); i++) {
            if(login.equals(listOfUsers.get(i).login)) {
                return true;
            }
        }
        return false;
    }
    
    public static void registerUser() {
        Scanner getData = new Scanner(System.in, "ISO-8859-2");

        String login;
        String password;
        String name;
        String surname;
        while(true) {
            System.out.println("Podaj login: ");
            login = getData.nextLine();
            if(userAlreadyExists(login)) {
                System.out.println("Ta nazwa użytkownika jest zajęta. Użyj innej.");
            }
            else {
                break;
            }
        }
        System.out.println("Podaj hasło");
        password = getData.nextLine();
        name = getData.nextLine();
        surname = getData.nextLine();
        
        listOfUsers.add(new Client(login, password, name, surname));
    }
    
    public static Boolean logInUser(String login, String password) {
        for (int i = 0; i < listOfUsers.size(); i++) {
            Client user = listOfUsers.get(i);
            if(login.equals(user.login) && password.equals(user.password)) {
                return true;
            }
        }
        return false;
    }
}
