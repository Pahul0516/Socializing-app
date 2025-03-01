package org.example.lab7.Domain;

import java.time.LocalDateTime;

public class Friendship extends Entity<Integer> {
    Integer user_id1;
    Integer user_id2;
    Integer id_status;
    LocalDateTime friendsFrom;

    public Friendship(Integer user_id1, Integer user_id2,Integer id_status, LocalDateTime friendsFrom) {
        super(-1);
        this.user_id1 = user_id1;
        this.user_id2 = user_id2;
        this.id_status = id_status;
        this.friendsFrom = friendsFrom;
    }

    public Integer getId_status() {
        return id_status;
    }

    public Integer getUser_id1() {
        return user_id1;
    }

    public void setUser_id1(Integer user_id1) {
        this.user_id1 = user_id1;
    }

    public Integer getUser_id2() {
        return user_id2;
    }

    public void setUser_id2(Integer user_id2) {
        this.user_id2 = user_id2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }
}
