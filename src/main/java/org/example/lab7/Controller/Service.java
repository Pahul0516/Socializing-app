package org.example.lab7.Controller;
import org.example.lab7.Domain.Friendship;
import org.example.lab7.Domain.User;
import org.example.lab7.Observer.Observer;
import org.example.lab7.Observer.Subject;
import org.example.lab7.Repository.Database.RepositoryFriendshipsDatabase;
import org.example.lab7.Repository.Database.RepositoryUsersDatabase;


import java.sql.*;

public abstract class Service implements Subject {

    protected RepositoryFriendshipsDatabase repo_friendships;
    protected RepositoryUsersDatabase repo_users;

    public void setRepo_friendships(RepositoryFriendshipsDatabase repo_friendships) {
        this.repo_friendships = repo_friendships;
    }

    public void setRepo_users(RepositoryUsersDatabase repo_users) {
        this.repo_users = repo_users;
    }

    public User add_user(String firstname, String lastname, String username, String password) throws SQLException {
        User user  = new User(firstname, lastname, username, password);
        return repo_users.create(user).get();
    }

    public void modify_user(User user) throws SQLException {
        repo_users.update(user);
    }

    public void delete_user(User user) throws SQLException {
        repo_users.delete(user);
    }

    public void delete_friendship(Friendship friendship) throws SQLException {
        repo_friendships.delete(friendship);
    }

    public void add_friendship(Friendship friendship) throws SQLException {
        repo_friendships.create(friendship);
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }
}
