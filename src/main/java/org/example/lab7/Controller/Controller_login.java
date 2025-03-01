package org.example.lab7.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.lab7.Domain.Validator.ValidationException;
import org.example.lab7.Domain.Validator.Validator;
import org.example.lab7.Domain.Validator.ValidatorLogin;
import javafx.stage.Stage;
import org.example.lab7.Observer.Observer;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_login extends Service{

    private final Validator validator = new Validator();

    private Stage stage;
    public Label error_label;
    public Hyperlink link_register;
    public TextField email_text_field;
    public TextField password_text_field;
    public Button btn_login;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void open_register(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab7/Register.fxml"));
        Parent root = loader.load();
        Scene loginScene = new Scene(root);
        stage.setScene(loginScene);

        Controller_register controller = loader.getController();
        controller.setRepo_friendships(repo_friendships);
        controller.setRepo_users(repo_users);
        controller.setStage(stage);

        stage.show();
        root.requestFocus();
    }

    public void open_main_page(ActionEvent actionEvent) {
        String email = email_text_field.getText();
        String password = password_text_field.getText();

        try{
            validator.setStrategy(new ValidatorLogin(repo_users));
            validator.validate(email +" "+ password);
            Integer id_credentials = repo_users.findCredentialId(email, password);
            Integer id_user  = repo_users.findByIdCredentials(id_credentials).get().getId();


            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab7/Main_page.fxml"));
            Parent root = loader.load();
            Scene loginScene = new Scene(root);
            newStage.setScene(loginScene);

            Controller_main_page controller = loader.getController();
            controller.setRepo_friendships(repo_friendships);
            controller.setRepo_users(repo_users);
            controller.setId_user(id_user);
            controller.setStage(newStage);
            controller.initialize(repo_users,repo_friendships);

            registerObserver(controller);
            newStage.show();
            newStage.setMaximized(true);
            root.requestFocus();
        }
        catch(ValidationException e){
            error_label.setVisible(true);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleClickOutside() {
        email_text_field.getParent().requestFocus();
    }
}
