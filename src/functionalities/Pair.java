
package functionalities;

public class Pair {
    public int first;
    public String second;
    public String login;
    public String password;
    
    public Pair(int first, String second) {
        this.first = first;
        this.second = second;
    }
    
    public Pair(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
