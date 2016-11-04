package pe.edu.upc.carbook.share.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Juanxps on 10/24/16.
 */

public class User extends SugarRecord {

    private String email;
    private String password;

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

    public static User findByUserName(String username){
        List <User> users = User.find(User.class,"email = ?",username);
        if(users.size() > 0) return users.get(0);
        return null;
    }
}
