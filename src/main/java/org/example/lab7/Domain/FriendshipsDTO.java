package org.example.lab7.Domain;

import java.time.LocalDateTime;

public class FriendshipsDTO {
    Integer id_friendship;
    Integer id_user;
    Integer id_friend;
    LocalDateTime created_at;

    public FriendshipsDTO(Integer id_friendship, Integer id_user, Integer id_friend, LocalDateTime created_at) {
        this.id_friendship = id_friendship;
        this.id_user = id_user;
        this.id_friend = id_friend;
        this.created_at = created_at;
    }

    public Integer getId_friendship() {
        return id_friendship;
    }

    public void setId_friendship(Integer id_friendship) {
        this.id_friendship = id_friendship;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_friend() {
        return id_friend;
    }

    public void setId_friend(Integer id_friend) {
        this.id_friend = id_friend;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
