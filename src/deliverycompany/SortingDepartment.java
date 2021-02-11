
package deliverycompany;


import java.util.ArrayList;
public class SortingDepartment {
    
    public String name;
    public ArrayList <Package> listOfPackagesInside = new ArrayList <Package> ();
    public String departmentType;
    int distanceToStationAbove;
    
    public String stationAbove = "none";
    
    public SortingDepartment(String name, String departmentType) {
        this.name = name;
        this.departmentType = departmentType;
    }
    
    public SortingDepartment(String name, String departmentType, String stationAbove, int distanceToStationAbove) {
        this.name = name;
        this.departmentType = departmentType;
        this.stationAbove = stationAbove;
        this.distanceToStationAbove = distanceToStationAbove;
    }
}
