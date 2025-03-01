module org.example.lab7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.management;
    requires java.desktop;
    requires java.net.http;
    requires com.google.gson;
    requires java.sql;

    opens org.example.lab7 to javafx.fxml;
    exports org.example.lab7;
    exports org.example.lab7.Controller;
    exports org.example.lab7.Repository;
    exports org.example.lab7.Domain;
}