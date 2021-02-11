
package workers;

import static deliverycompany.Data.findPackage;
import deliverycompany.Package;
import static deliverycompany.Data.findWorker;
import static deliverycompany.Data.listOfPackages;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.time.*;

public class Manager extends Worker {


    public Manager(String name, String surname, String position, int startWorkAt, int finishWorkAt, double salary){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.startWorkAt = LocalTime.of(startWorkAt, 0, 0);
        this.finishWorkAt = LocalTime.of(finishWorkAt, 0, 0);
        this.salary = salary;
        this.isWorking = false;
    }

    @Override
    public Package checkPackage(int idNumber){
        Package pack = listOfPackages.get(findPackage(idNumber));
        return pack;
    }

    void checkCourier(Courier courier){
       if (courier.isWorking == true ){
           System.out.println("Kurier jest wolny");
       }
       else {
           System.out.println("Kurier jest zajęty");
       }
    };

    void checkSortingDepartment(){
        System.out.println("Wybierz stację, którą chcesz sprawdzić");
        System.out.println("Wybierz co chcesz sprawdzić");
    };

    void changeWorkerSalary(){

        Scanner getData = new Scanner(System.in, "ISO-8859-2");

        String workerName;
        String workerSurname;
        Double workerSalary;

        System.out.println("Podaj imię pracownika, któremu chcesz zmienić pensję: ");
        workerName = getData.nextLine();
        System.out.println("Podaj jego nazwisko: ");
        workerSurname = getData.nextLine();
        System.out.println("Podaj nową pensję: ");
        workerSalary = getData.nextDouble();

       Worker worker = findWorker(workerName, workerSurname);

       if(worker == null) {
           System.out.println("Nie ma takiego pracownika.");
       }
       else {
           worker.salary = workerSalary;
           System.out.println("Zmieniono wynagrodzenie.");
       }

    }

    void checkListOfWorkers(){

        FileReader checkWorker;
        String line = "";

        try {
           checkWorker = new FileReader("workers.txt");
           BufferedReader bfr = new BufferedReader(checkWorker);
           while((line = bfr.readLine()) != null){
               System.out.println(line);
            }
           checkWorker.close();
        }
        catch (IOException e){
                System.out.println(e.getMessage());
         }
    }
}
