package org.example.lab7.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab7.Domain.User;
import org.example.lab7.Domain.Validator.Validator;
import org.example.lab7.Domain.Validator.ValidatorModifyEmail;
import org.example.lab7.Domain.Validator.ValidatorModifyPassword;
import org.example.lab7.Domain.Validator.ValidatorRegisterName;
import java.sql.SQLException;
import java.util.Optional;

public class Controller_modifiy extends Service{
    private Stage stage;
    private Integer id_user;
    private final Validator validator = new Validator();

    @FXML
    public TextField last_name_textfield;
    @FXML
    public TextField first_name_textfield;
    @FXML
    public TextField email_name_textfield;
    @FXML
    public TextField password_name_textfield;
    @FXML
    public Button btn_close;
    @FXML
    public Label error_lastNameLabel;
    @FXML
    public Label error_firstNameLabel;
    @FXML
    public Label error_emailLabel;
    @FXML
    public Label error_passwordLabel;
    @FXML
    public Button btn_modify;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setId_user(Integer id) {
        this.id_user = id;
    }

    public void set_text_fields(){
        Optional<User> userOptional = repo_users.findOne(id_user);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            last_name_textfield.setText(user.getLastName());
            first_name_textfield.setText(user.getFirstName());
            email_name_textfield.setText(user.getEmail());
            password_name_textfield.setText(user.getPassword());
        } else {
            System.out.println("User not found with id: " + id_user);
        }
    }

    public void  handleClickOutside(){
        last_name_textfield.getParent().requestFocus();
    }

    public void close_modify() {
        stage.close();
    }

    public void modify_action(ActionEvent actionEvent) throws SQLException {
        boolean ok1 = false, ok2 = false, ok3 = false, ok4 = false;
        String lastname = last_name_textfield.getText();
        String firstname = first_name_textfield.getText();
        String email = email_name_textfield.getText();
        String password = password_name_textfield.getText();

        try {
            validator.setStrategy(new ValidatorRegisterName(repo_users));
            validator.validate(lastname);
            error_lastNameLabel.setVisible(false);
            ok1 = true;
        } catch (Exception e) {
            error_lastNameLabel.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorRegisterName(repo_users));
            validator.validate(firstname);
            error_firstNameLabel.setVisible(false);
            ok2 = true;
        } catch (Exception e) {
            error_firstNameLabel.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorModifyEmail(repo_users,repo_users.findOne(id_user).get().get_idCredentials()));
            validator.validate(email);
            error_emailLabel.setVisible(false);
            ok3 = true;
        } catch (Exception e) {
            error_emailLabel.setVisible(true);
        }

        try {
            validator.setStrategy(new ValidatorModifyPassword(repo_users,repo_users.findOne(id_user).get().get_idCredentials()));
            validator.validate(password);
            error_passwordLabel.setVisible(false);
            ok4 = true;
        } catch (Exception e) {
            error_passwordLabel.setVisible(true);
        }

        if (ok1 && ok2 && ok3 && ok4) {
            User user = new User(firstname,lastname,email,password);
            user.setId(id_user);
            user.set_idCredentials(repo_users.findOne(id_user).get().get_idCredentials());
            modify_user(user);
            close_modify();
        }
    }

}
