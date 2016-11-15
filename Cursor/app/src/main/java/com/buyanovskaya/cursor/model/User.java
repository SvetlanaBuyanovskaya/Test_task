package com.buyanovskaya.cursor.model;

public class User {

    private String firstName;
    private String lastName;
    private String users;
    private int id;

    public User() {
    }
    public User( String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsers(){users=firstName+" "+lastName;
        return users;}
    public void setUsers(String users){ this.users=users;}

}