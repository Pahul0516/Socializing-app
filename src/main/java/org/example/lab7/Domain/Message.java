package org.example.lab7.Domain;

public class Message {
    Integer idMessage;
    Integer idReply;
    Integer idFromUser;
    Integer idToUser;
    String message;

    public Message(Integer idMessage, Integer idReply, Integer idFromUser, Integer idToUser, String message) {
        this.idMessage = idMessage;
        this.idReply = idReply;
        this.idFromUser = idFromUser;
        this.idToUser = idToUser;
        this.message = message;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public Integer getIdReply() {
        return idReply;
    }

    public void setIdReply(Integer idReply) {
        this.idReply = idReply;
    }

    public Integer getIdFromUser() {
        return idFromUser;
    }

    public void setIdFromUser(Integer idFromUser) {
        this.idFromUser = idFromUser;
    }

    public Integer getIdToUser() {
        return idToUser;
    }

    public void setIdToUser(Integer idToUser) {
        this.idToUser = idToUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
