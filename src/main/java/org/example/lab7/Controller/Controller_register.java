package org.example.lab7.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab7.Domain.Validator.*;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_register extends Service{

    private final Validator validator = new Validator();
    private Stage stage;

    @FXML
    public Label error_password;
    @FXML
    public Label error_email;
    @FXML
    public Label error_confirm_password;
    @FXML
    public Label error_lastname;
    @FXML
    public Label error_firstname;
    @FXML
    public TextField lastname_text_field;
    @FXML
    public TextField firstname_text_field;
    @FXML
    public TextField email_text_field;
    @FXML
    public TextField password1_text_field;
    @FXML
    public TextField password2_text_field;
    @FXML
    public Button btn_register;
    @FXML
    public Hyperlink link_login;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void open_login(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab7/Login.fxml"));
        Parent root = loader.load();
        Scene loginScene = new Scene(root);
        stage.setScene(loginScene);

        Controller_login controller = loader.getController();
        controller.setRepo_friendships(repo_friendships);
        controller.setRepo_users(repo_users);
        controller.setStage(stage);

        stage.show();
        root.requestFocus();
    }

    public void  handleClickOutside(){
        firstname_text_field.getParent().requestFocus();
    }

    public void add_user(ActionEvent actionEvent) throws SQLException, IOException {
        boolean ok1=false,ok2=false,ok3=false,ok4=false,ok5=false;
        String firstname = firstname_text_field.getText();
        String lastname = lastname_text_field.getText();
        String email = email_text_field.getText();
        String password = password1_text_field.getText();
        String confirm_password = password2_text_field.getText();

        try {
            validator.setStrategy(new ValidatorRegisterEmail(repo_users));
            validator.validate(email);
            error_email.setVisible(false);
            ok1=true;
        }
        catch (ValidationException e) {
            error_email.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorRegisterPassword(repo_users));
            validator.validate(password);
            error_password.setVisible(false);
            ok2=true;
        }
        catch (ValidationException e) {
            error_password.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorRegisterConfirmPassword(repo_users));
            validator.validate(password+" "+confirm_password);
            error_confirm_password.setVisible(false);
            ok3=true;
        }
        catch (ValidationException e) {
            error_confirm_password.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorRegisterName(repo_users));
            validator.validate(firstname);
            error_firstname.setVisible(false);
            ok4=true;
        }
        catch (ValidationException e) {
            error_firstname.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorRegisterName(repo_users));
            validator.validate(lastname);
            error_lastname.setVisible(false);
            ok5=true;
        }
        catch (ValidationException e) {
            error_lastname.setVisible(true);
        }

        if (ok1 && ok2 && ok3 && ok4 && ok5) {
            Integer id = add_user(firstname, lastname, email, password).getId();


            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab7/Main_page.fxml"));
            Parent root = loader.load();
            Scene loginScene = new Scene(root);
            newStage.setScene(loginScene);

            Controller_main_page controller = loader.getController();
            controller.setRepo_friendships(repo_friendships);
            controller.setRepo_users(repo_users);
            controller.setId_user(id);
            controller.setStage(newStage);
            controller.initialize(repo_users,repo_friendships);

            registerObserver(controller);
            controller.notifyObservers();
            newStage.show();
            newStage.setMaximized(true);
            root.requestFocus();
        }
    }
}
