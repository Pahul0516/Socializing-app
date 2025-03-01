package org.example.lab7.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.lab7.Domain.*;
import org.example.lab7.Domain.Validator.Validator;
import org.example.lab7.Domain.Validator.ValidatorMessage;
import org.example.lab7.Observer.Observer;
import org.example.lab7.Repository.Database.RepositoryFriendshipsDatabase;
import org.example.lab7.Repository.Database.RepositoryUsersDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller_main_page extends Service implements Observer {


    private Stage stage;
    private Integer id_user;
    private Integer id_friendship = -1;
    private Integer pageNumberFriend = 0;
    private Integer maxPageNumberFriend = 0;
    private Integer pageNumberRequest = 0;
    private Integer maxPageNumberRequest = 0;
    private final Validator validator = new Validator();

    @FXML
    private Label Label_name;

    @FXML
    public TableView<FriendshipsDTO> tableViewFriendships;
    @FXML
    public TableView<RequestDTO> tableViewRequests;
    @FXML
    public TableColumn<FriendshipsDTO,Void> friendsTableColumn;
    @FXML
    public TableColumn<RequestDTO,Void> requestTableColumn;

    @FXML
    private Button btn_options;
    @FXML
    private ContextMenu contextMenu;

    @FXML
    public ListView<UserDTO> listViewSearch;
    @FXML
    private TextField search_textField;
    @FXML
    public ContextMenu contextMenuSearch;
    @FXML
    public CustomMenuItem customMenuItem;

    @FXML
    public VBox chatVBox;
    @FXML
    public Label chatLabelName;
    @FXML
    public Button btnPhoneCall;
    @FXML
    public Button btnVideoCall;
    @FXML
    public Button btnSent;
    @FXML
    public ListView<Message> chatListView;
    @FXML
    public TextField textFieldChat;

    @FXML
    public Button btnLeftFriend;
    @FXML
    public Button btnRightFriend;
    @FXML
    public Button btnLeftRequest;
    @FXML
    public Button btnRightRequest;

    ObservableList<FriendshipsDTO> friendsList = FXCollections.observableArrayList();
    ObservableList<RequestDTO> requestsList = FXCollections.observableArrayList();
    ObservableList<UserDTO> names = FXCollections.observableArrayList();
    ObservableList<Message> messages = FXCollections.observableArrayList();

    void initializeButtons() {
        Image phoneIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Phone-call.png")); // Ensure the path is correct
        ImageView phoneImageView = new ImageView(phoneIcon);
        phoneImageView.setFitWidth(15);
        phoneImageView.setFitHeight(15);

        btnPhoneCall.setGraphic(phoneImageView);
        btnPhoneCall.setStyle("-fx-background-color: transparent;");

        btnPhoneCall.setOnMouseEntered(event -> {
            btnPhoneCall.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });
        btnPhoneCall.setOnMouseExited(event -> {
            btnPhoneCall.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });


        Image videoIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Video_call.png")); // Ensure the path is correct
        ImageView videoImageView = new ImageView(videoIcon);
        videoImageView.setFitWidth(15);
        videoImageView.setFitHeight(15);

        btnVideoCall.setGraphic(videoImageView);
        btnVideoCall.setStyle("-fx-background-color: transparent;");

        btnVideoCall.setOnMouseEntered(event -> {
            btnVideoCall.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });

        btnVideoCall.setOnMouseExited(event -> {
            btnVideoCall.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });

        Image sentIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Send.png")); // Ensure the path is correct
        ImageView sentImageView = new ImageView(sentIcon);
        sentImageView.setFitWidth(15);
        sentImageView.setFitHeight(15);

        btnSent.setGraphic(sentImageView);
        btnSent.setStyle("-fx-background-color: transparent;");

        btnSent.setOnMouseEntered(event -> {
            btnSent.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });
        btnSent.setOnMouseExited(event -> {
            btnSent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });
        btnSent.setDefaultButton(true);

        Image leftArrowIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Left_Arrow.png")); // Ensure the path is correct
        ImageView leftImageView = new ImageView(leftArrowIcon);
        leftImageView.setFitWidth(15);
        leftImageView.setFitHeight(15);

        btnLeftFriend.setGraphic(leftImageView);
        btnLeftFriend.setStyle("-fx-background-color: transparent;");

        btnLeftFriend.setOnMouseEntered(event -> {
            btnLeftFriend.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });
        btnLeftFriend.setOnMouseExited(event -> {
            btnLeftFriend.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });

        Image leftArrowIcon2 = new Image(getClass().getResourceAsStream("/org/example/lab7/Left_Arrow.png")); // Ensure the path is correct
        ImageView leftImageView2 = new ImageView(leftArrowIcon2);
        leftImageView2.setFitWidth(15);
        leftImageView2.setFitHeight(15);

        btnLeftRequest.setGraphic(leftImageView2);
        btnLeftRequest.setStyle("-fx-background-color: transparent;");

        btnLeftRequest.setOnMouseEntered(event -> {
            btnLeftRequest.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });
        btnLeftRequest.setOnMouseExited(event -> {
            btnLeftRequest.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });

        Image rightArrowIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Right_Arrow.png")); // Ensure the path is correct
        ImageView rightImageView = new ImageView(rightArrowIcon);
        rightImageView.setFitWidth(15);
        rightImageView.setFitHeight(15);

        btnRightFriend.setGraphic(rightImageView);
        btnRightFriend.setStyle("-fx-background-color: transparent;");

        btnRightFriend.setOnMouseEntered(event -> {
            btnRightFriend.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });
        btnRightFriend.setOnMouseExited(event -> {
            btnRightFriend.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });

        Image rightArrowIcon2 = new Image(getClass().getResourceAsStream("/org/example/lab7/Right_Arrow.png")); // Ensure the path is correct
        ImageView rightImageView2 = new ImageView(rightArrowIcon2);
        rightImageView2.setFitWidth(15);
        rightImageView2.setFitHeight(15);

        btnRightRequest.setGraphic(rightImageView2);
        btnRightRequest.setStyle("-fx-background-color: transparent;");

        btnRightRequest.setOnMouseEntered(event -> {
            btnRightRequest.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
        });
        btnRightRequest.setOnMouseExited(event -> {
            btnRightRequest.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });
    }

    @FXML
    public void initialize(RepositoryUsersDatabase repositoryUsers, RepositoryFriendshipsDatabase repositoryFriendships) throws SQLException {

        this.repo_users = repositoryUsers;
        this.repo_friendships = repositoryFriendships;
        search_textField.textProperty().addListener((observable, oldValue, newValue) -> {

            FilteredList<UserDTO> filteredNames = new FilteredList<>(names, s -> true);
            listViewSearch.setItems(filteredNames);

            filteredNames.setPredicate(user -> user.getName().toLowerCase().startsWith(newValue.toLowerCase()));
            boolean hasMatches = !filteredNames.isEmpty();

            if (!newValue.isEmpty() && hasMatches) {
                // Update ListView items
                listViewSearch.setItems(filteredNames);

                // Calculate position of the TextField
                double textFieldY = search_textField.localToScene(0, 0).getY();
                double textFieldHeight = search_textField.getHeight();

                // Adjust ListView position dynamically
                listViewSearch.relocate(search_textField.getLayoutX(), textFieldY + textFieldHeight);
                listViewSearch.setVisible(true);
            } else {
                listViewSearch.setVisible(false);
            }
        });

        listViewSearch.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserDTO item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                }
                else {
                    // Create a Label for the name
                    Label nameLabel = new Label(item.getName());

                    // Create a Button for the action
                    Button addButton = new Button();

                    Image trashIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Add.png")); // Ensure the path is correct
                    ImageView trashImageView = new ImageView(trashIcon);
                    trashImageView.setFitWidth(15);
                    trashImageView.setFitHeight(15);

                    addButton.setGraphic(trashImageView);
                    addButton.setStyle("-fx-background-color: transparent;");

                    addButton.setOnAction(event -> {
                        search_textField.clear(); // Update the search bar with the selected name
                        listViewSearch.setVisible(false); // Hide the dropdown
                        Integer id_status = repositoryFriendships.create_friendshipStatus(id_user,item.getId_user(),"pending");
                        Friendship friendship = new Friendship(id_user,item.getId_user(),id_status, LocalDateTime.now());
                        try {
                            add_friendship(friendship);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        notifyObservers();
                        //loadData();
                    });

                    addButton.setOnMouseEntered(event -> {
                        addButton.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
                    });
                    addButton.setOnMouseExited(event -> {
                        addButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                    });

                    // Combine the Label and Button in an HBox
                    HBox cellBox = new HBox(10, nameLabel, addButton);
                    cellBox.setAlignment(Pos.CENTER);
                    cellBox.setSpacing(5);
                    setGraphic(cellBox);
                }
            }
        });


        tableViewFriendships.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                chatVBox.getParent().requestFocus();
                chatVBox.setVisible(true);
                FriendshipsDTO selectedFriendship = tableViewFriendships.getSelectionModel().getSelectedItem();
                if (selectedFriendship != null) {
                    chatLabelName.setText(repositoryUsers.findOne(selectedFriendship.getId_friend()).get().getFirstName() + " " + repositoryUsers.findOne(selectedFriendship.getId_friend()).get().getLastName());
                    id_friendship = selectedFriendship.getId_friendship();
                    notifyObservers();
                    System.out.println(id_friendship);
                }
            }
        });
        friendsTableColumn.setCellFactory(cell -> new TableCell<>() {
            private final HBox container = new HBox();
            private final Label nameText = new Label();
            //private final Label dateText = new Label();
            private final Button deleteButton = new Button();
            {
                container.setSpacing(10);
                container.setAlignment(Pos.CENTER);

                Image trashIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Trash.png")); // Ensure the path is correct
                ImageView trashImageView = new ImageView(trashIcon);
                trashImageView.setFitWidth(15);
                trashImageView.setFitHeight(15);

                deleteButton.setGraphic(trashImageView);
                deleteButton.setStyle("-fx-background-color: transparent;");

                deleteButton.setOnAction(event -> {
                    FriendshipsDTO friendshipDTO = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(friendshipDTO);

                    try {
                        Friendship friendship = repo_friendships.findOne(friendshipDTO.getId_friendship()).get();
                        User u1 = repo_users.findOne(friendship.getUser_id1()).get();
                        User u2 = repo_users.findOne(friendship.getUser_id2()).get();
                        if (u1.get_bot()==null && u2.get_bot() == null)
                            delete_friendship(repositoryFriendships.findOne(friendshipDTO.getId_friendship()).get());
                        else{
                            if (u1.get_bot() != null)
                                repo_users.delete(u1);
                            else
                                repo_users.delete(u2);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    notifyObservers();
                    handleClickOutside();
                });
                deleteButton.setOnMouseEntered(event -> {
                    deleteButton.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
                });
                deleteButton.setOnMouseExited(event -> {
                    deleteButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                });

                container.getChildren().addAll(nameText, deleteButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    FriendshipsDTO friendship = getTableView().getItems().get(getIndex());
                    String fullName = repo_users.findOne(friendship.getId_friend()).get().getFirstName() + " " + repo_users.findOne(friendship.getId_friend()).get().getLastName();
                    nameText.setText(fullName);
                    //dateText.setText(friendship.getCreated_at().toString());
                    setGraphic(container);
                }
            }
        });
        requestTableColumn.setCellFactory(cell-> new TableCell<>() {
            private final HBox container = new HBox();
            private final Label nameText = new Label();
            private final Button acceptButton = new Button();
            private final Button rejectButton = new Button();

            {
                container.setSpacing(10);
                container.setAlignment(Pos.CENTER);

                Image acceptIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Accepted.png")); // Ensure the path is correct
                ImageView acceptImageView = new ImageView(acceptIcon);
                acceptImageView.setFitWidth(15);
                acceptImageView.setFitHeight(15);

                Image rejectIcon = new Image(getClass().getResourceAsStream("/org/example/lab7/Rejected.png")); // Ensure the path is correct
                ImageView rejectImageView = new ImageView(rejectIcon);
                rejectImageView.setFitWidth(15);
                rejectImageView.setFitHeight(15);

                acceptButton.setGraphic(acceptImageView);
                acceptButton.setStyle("-fx-background-color: transparent;");
                rejectButton.setGraphic(rejectImageView);
                rejectButton.setStyle("-fx-background-color: transparent;");

                acceptButton.setOnAction(event -> {
                    RequestDTO requestDTO = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(requestDTO);
                    repo_friendships.setStatus(requestDTO.getId_status(), "accepted");
                    //loadData();
                    notifyObservers();
                    handleClickOutside();
                });
                rejectButton.setOnAction(event -> {
                    RequestDTO requestDTO = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(requestDTO);
                    repo_friendships.deleteFriendship(requestDTO.getId_status());
                    //loadData();
                    notifyObservers();
                    handleClickOutside();
                });

                acceptButton.setOnMouseEntered(event -> {
                    acceptButton.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
                });
                acceptButton.setOnMouseExited(event -> {
                    acceptButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                });

                rejectButton.setOnMouseEntered(event -> {
                    rejectButton.setStyle("-fx-background-color: rgba(169, 169, 169, 0.3); -fx-border-color: transparent;");
                });
                rejectButton.setOnMouseExited(event -> {
                    rejectButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                });

                container.getChildren().addAll(nameText, acceptButton, rejectButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // No content in the cell if empty
                } else {
                    RequestDTO requestDTO = getTableView().getItems().get(getIndex());
                    String fullName = repo_users.findOne(requestDTO.getId_user()).get().getFirstName() + " " + repo_users.findOne(requestDTO.getId_user()).get().getLastName();
                    nameText.setText(fullName);
                    setGraphic(container); // Set the HBox as the cell content
                }
            }
        });

        chatListView.setCellFactory(param -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);

                if (empty || message == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a Label for the message text
                    Label messageLabel = new Label(message.getMessage());
                    messageLabel.setWrapText(true); // Enable text wrapping
                    messageLabel.setMaxWidth(chatListView.getWidth() * 0.6); // Limit max width to 60% of ListView width

                    // Apply background and padding styling for a chat bubble
                    messageLabel.setPadding(new Insets(10));
                    messageLabel.setStyle("-fx-background-radius: 10; " +
                            "-fx-font-size: 14px;" +
                            (message.getIdFromUser() == id_user
                                    ? "-fx-background-color: lightblue;"
                                    : "-fx-background-color: lightgray;"));

                    // Wrap the Label in an HBox for alignment
                    HBox container = new HBox(messageLabel);
                    container.setMaxWidth(chatListView.getWidth() * 1); // Restrict container width
                    container.setAlignment(message.getIdFromUser() == id_user
                            ? Pos.CENTER_RIGHT  // Align to right for sent messages
                            : Pos.CENTER_LEFT); // Align to left for received messages

                    setGraphic(container); // Add the HBox to the cell
                }
            }
        });

        initializeButtons();
        loadData();
    }

    void loadData() {

        List<Friendship> list = new ArrayList<>();

        friendsList.clear();
        requestsList.clear();
        names.clear();

        names = FXCollections.observableArrayList(repo_users.findAlLNames(id_user));

        for (Friendship friendship : repo_friendships.findAllById1(id_user))
            list.add(friendship);
        for (Friendship friendship : repo_friendships.findAllById2(id_user))
            list.add(friendship);

        for (Friendship friendship:list) {
            if (Objects.equals(repo_friendships.getStatus(friendship.getId_status()), "accepted")) {
                if (Objects.equals(friendship.getUser_id1(), id_user) && !Objects.equals(friendship.getUser_id2(), id_user)) {
                    FriendshipsDTO friendshipDTO = new FriendshipsDTO(friendship.getId(),id_user, friendship.getUser_id2(), friendship.getFriendsFrom());
                    friendsList.add(friendshipDTO);
                }
                else {
                    if (!Objects.equals(friendship.getUser_id1(), id_user) && Objects.equals(friendship.getUser_id2(), id_user)) {
                        FriendshipsDTO friendshipDTO = new FriendshipsDTO(friendship.getId(), id_user, friendship.getUser_id1(), friendship.getFriendsFrom());
                        friendsList.add(friendshipDTO);
                    }
                }
            }
            else
            {
                if (Objects.equals(repo_friendships.getStatus(friendship.getId_status()), "pending")) {
                    if (!Objects.equals(friendship.getUser_id1(), id_user) && Objects.equals(friendship.getUser_id2(), id_user)) {
                        RequestDTO requestDTO = new RequestDTO(friendship.getUser_id1(),friendship.getId_status(), repo_users.findOne(friendship.getUser_id1()).get().getFirstName() + " " + repo_users.findOne(friendship.getUser_id1()).get().getFirstName());
                        requestsList.add(requestDTO);
                    }
                }
            }
        }


        // daca vrei sa afisezi datele fara paginare, scapa de liniile astea
        List<FriendshipsDTO> listF = repo_friendships.readPageFriendship(pageNumberFriend,id_user);
        friendsList.clear();
        friendsList = FXCollections.observableArrayList(listF);
        maxPageNumberFriend = repo_friendships.getMaxPagesFriends(id_user);
        List<RequestDTO> listR = repo_friendships.readPageRequest(pageNumberRequest,id_user,repo_users);
        requestsList.clear();
        requestsList = FXCollections.observableArrayList(listR);
        maxPageNumberRequest = repo_friendships.getMaxPagesRequest(id_user);
        // pana aici


        tableViewFriendships.setItems(friendsList);
        tableViewRequests.setItems(requestsList);

        List<Message> listMessages = repo_friendships.getMessages(id_friendship);
        messages = FXCollections.observableArrayList(listMessages);
        chatListView.setItems(messages);
        if (!messages.isEmpty()) {
            chatListView.scrollTo(messages.size() - 1);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        Label_name.setText("Welcome " + repo_users.findOne(id_user).get().getFirstName() + " " + repo_users.findOne(id_user).get().getLastName() + " ");
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public void btn_click(ActionEvent actionEvent) {
        double screenX = btn_options.localToScreen(btn_options.getBoundsInLocal()).getMinX();
        double screenY = btn_options.localToScreen(btn_options.getBoundsInLocal()).getMaxY();

        // Show the context menu below the button
        contextMenu.show(btn_options, screenX, screenY);
    }

    public void open_modify_account(ActionEvent actionEvent) throws Exception {
        showPopup();
        notifyObservers();
    }

    private void showPopup() throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab7/Modify_account.fxml"));
        Parent root = loader.load();

        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
        popupStage.setTitle("Popup Window");

        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.setResizable(false);
        popupScene.getRoot().requestFocus();

        Controller_modifiy controller = loader.getController();
        controller.setRepo_friendships(repo_friendships);
        controller.setRepo_users(repo_users);
        controller.setId_user(id_user);
        controller.setStage(popupStage);
        controller.set_text_fields();
        popupStage.showAndWait(); // Block interaction with the main window
        Label_name.setText("Welcome " + repo_users.findOne(id_user).get().getFirstName() + " " + repo_users.findOne(id_user).get().getLastName()+ " ");
    }

    public void handle_logout(ActionEvent actionEvent) throws IOException {
        stage.close();
    }

    public void action_delete(ActionEvent actionEvent) throws SQLException, IOException {
        delete_user(repo_users.findOne(id_user).get());
        stage.close();
        notifyObservers();
    }

    public void create_bot(ActionEvent actionEvent) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab7/Create_bot.fxml"));
        Parent root = loader.load();

        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
        popupStage.setTitle("Popup Window");

        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.setResizable(false);
        popupScene.getRoot().requestFocus();

        Controller_bot controller = loader.getController();
        controller.setRepo_friendships(repo_friendships);
        controller.setRepo_users(repo_users);
        controller.setId_user(id_user);
        controller.setStage(popupStage);
        popupStage.showAndWait(); // Block interaction with the main window
    }

    public void  handleClickOutside(){
        search_textField.getParent().requestFocus();
    }

    public void clicked_textField(MouseEvent mouseEvent) {
        listViewSearch.setVisible(false);
        // Calculate the screen position of the button
        double screenX = search_textField.localToScreen(search_textField.getBoundsInLocal()).getMinX();
        double screenY = search_textField.localToScreen(search_textField.getBoundsInLocal()).getMaxY();

        // Show the context menu below the button
        contextMenuSearch.show(search_textField, screenX, screenY);
    }

    @Override
    public void update() {
        loadData();
    }

    public void sendMessage(ActionEvent actionEvent) {
           String text = textFieldChat.getText();
           textFieldChat.clear();
           validator.setStrategy(new ValidatorMessage());

           Integer idToUser;
           if (repo_friendships.findOne(id_friendship).get().getUser_id1() == id_user) {
               idToUser = repo_friendships.findOne(id_friendship).get().getUser_id2();
           }
           else{
               idToUser = repo_friendships.findOne(id_friendship).get().getUser_id1();
           }

           User user_from = repo_users.findOne(id_user).get();
           User user_to = repo_users.findOne(idToUser).get();

           if (user_to.get_bot()!=null)
           {
               try {
                   validator.validate(text);
                   Message message = new Message(-1, 1, id_user, idToUser, text);
                   messages.add(message);
                   repo_friendships.writeMessage(message, id_friendship);
                   String raspuns = Ai_chat.ask(text, user_to.get_bot());
                   validator.validate(raspuns);
                   Message message2 = new Message(-1, 0, idToUser, id_user, raspuns);
                   messages.add(message2);
                   repo_friendships.writeMessage(message2, id_friendship);
               }
               catch (Exception e) {
                   e.printStackTrace();
               }
           }
           else {
               if (true) {

                   try {
                       validator.validate(text);
                       Message message = new Message(-1, 1, id_user, idToUser, text);
                       messages.add(message);
                       repo_friendships.writeMessage(message, id_friendship);
                   }
                   catch (Exception e) {
                       e.printStackTrace();
                   }
               } else {
                   try {
                       validator.validate(text);
                       Message message = new Message(-1, 0, id_user, idToUser, text);
                       messages.add(message);
                       repo_friendships.writeMessage(message, id_friendship);
                   }
                   catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }
           notifyObservers();
    }

    public void subPageFriend(ActionEvent actionEvent) {
        if (pageNumberFriend !=0 ) {
            pageNumberFriend--;
            notifyObservers();
        }
    }

    public void addPageFriend(ActionEvent actionEvent) {
        if (pageNumberFriend < maxPageNumberFriend) {
            pageNumberFriend++;
            notifyObservers();
        }
    }

    public void subPageRequest(ActionEvent actionEvent) {
        if (pageNumberRequest != 0) {
            pageNumberRequest--;
            notifyObservers();
        }
    }

    public void addPageRequest(ActionEvent actionEvent) {
        if (pageNumberRequest < maxPageNumberRequest) {
            pageNumberRequest++;
            notifyObservers();
        }
    }
}