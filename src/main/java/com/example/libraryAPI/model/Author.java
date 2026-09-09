package com.example.libraryAPI.model;

public class Author {
    private int id;
    private String firstName;
    private String lastName;

    public Author() {
    }

    public Author(String firstname, String lastname) {
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public Author(int id, String firstname, String lastname) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
