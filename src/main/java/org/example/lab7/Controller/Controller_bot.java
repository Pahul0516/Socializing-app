package org.example.lab7.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab7.Domain.Ai_Bot;
import org.example.lab7.Domain.Friendship;
import org.example.lab7.Domain.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

public class Controller_bot extends Service{

    private Stage stage;
    private Integer id_user;
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @FXML
    public TextField last_name_textfield;
    @FXML
    public TextField first_name_textfield;
    @FXML
    public Button btn_create_bot;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setId_user(Integer id) {
        this.id_user = id;
    }

    public static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            result.append(CHAR_POOL.charAt(index));
        }
        return result.toString();
    }

    public void  handleClickOutside(){
        last_name_textfield.getParent().requestFocus();
    }

    public void create_bot_action(ActionEvent actionEvent) throws SQLException {
        Ai_Bot bot = new Ai_Bot(last_name_textfield.getText() + " " + first_name_textfield.getText());
        User u = new User(first_name_textfield.getText(), last_name_textfield.getText(),generateRandomString(3), generateRandomString(3));
        u.set_bot(bot.get_thread());
        repo_users.create(u);
        Integer id_status = repo_friendships.create_friendshipStatus(id_user,u.getId(),"accepted");
        Friendship friendship = new Friendship(id_user,u.getId(),id_status, LocalDateTime.now());
        add_friendship(friendship);
        notifyObservers();
        stage.close();
    }
}
