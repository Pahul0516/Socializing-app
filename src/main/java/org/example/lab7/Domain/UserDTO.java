package org.example.lab7.Domain;

public class UserDTO {
    Integer id_user;
    String name;

    public UserDTO(Integer id_user, String name) {
        this.id_user = id_user;
        this.name = name;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
