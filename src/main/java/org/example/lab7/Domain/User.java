package org.example.lab7.Domain;

public class User extends Entity<Integer> {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer id_credentials;
    private String bot;

    public User(String firstName, String lastName, String email, String password) {
        super(-1);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void set_bot(String bot) {
        this.bot = bot;
    }

    public String get_bot() {return bot;}

    public void set_idCredentials(Integer id) {
        this.id_credentials = id;
    }

    public Integer get_idCredentials() {
        return this.id_credentials;
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
}
