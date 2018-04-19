package main.model;

/**
 * Created by Moha on 2/21/2018.
 */

public class User {

    private String name;
    private String password;

    public User(String mname,String mpassword){

        name=mname;
        password=mpassword;
    }


    public User(){

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
