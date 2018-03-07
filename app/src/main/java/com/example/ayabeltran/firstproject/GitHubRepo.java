package com.example.ayabeltran.firstproject;

/**
 * Created by Lorenzo11 on 07/03/2018.
 */

public class GitHubRepo {

    private int id;
    private String email,
            username,
            password,
            firstname,
            lastname;

    public GitHubRepo(String email, String username, String password, String firstname, String lastname) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
//    public GitHubRepo(String s, String email, String username, String password, String firstname, String lastname) {
//        this.email = email;
//        this.username = username;
//        this.password = password;
//        this.firstname = firstname;
//        this.lastname = lastname;
//    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}