package pe.edu.upc.carbook.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Juanxps on 10/24/16.
 */

public class User extends SugarRecord {

    private String email;
    private String password;
    private String Name;
    private String LastName;
    private String Rol;

    public User(){

    }

    public User(String email, String password, String name, String lastName, String rol) {
        this.email = email;
        this.password = password;
        Name = name;
        LastName = lastName;
        Rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public static User findByUserName(String username){
        List <User> users = User.find(User.class,"email = ?",username);
        if(users.size() > 0) return users.get(0);
        return null;
    }
}
