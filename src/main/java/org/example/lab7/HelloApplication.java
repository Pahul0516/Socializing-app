package org.example.lab7;

import javafx.scene.Parent;
import org.example.lab7.Controller.Controller_welcome;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.lab7.Repository.Database.RepositoryFriendshipsDatabase;
import org.example.lab7.Repository.Database.RepositoryUsersDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        String url = "jdbc:postgresql://localhost:5432/Map_DB";
        String username = "postgres";
        String password = "Qwertyuiop12";

        try{
            Connection connection = DriverManager.getConnection(url, username, password);

            RepositoryFriendshipsDatabase friendshipsRepository = new RepositoryFriendshipsDatabase(connection);
            RepositoryUsersDatabase usersRepository = new RepositoryUsersDatabase(connection);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Welcome.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Controller_welcome controller = fxmlLoader.getController();
            controller.setRepo_friendships(friendshipsRepository);
            controller.setRepo_users(usersRepository);
            controller.setStage(stage);


            stage.setTitle("Hi there!");
            stage.setScene(scene);
            stage.show();
            root.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}