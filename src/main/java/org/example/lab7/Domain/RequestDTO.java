package org.example.lab7.Domain;

public class RequestDTO {

    Integer id_user;
    Integer id_status;
    String name;

    public RequestDTO(Integer id_user, Integer id_status, String name) {
        this.id_user = id_user;
        this.id_status = id_status;
        this.name = name;
    }

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}
