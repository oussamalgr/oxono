module org.example.oxonog62227 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens g62227.dev3.oxono.javafx to javafx.fxml;
    exports g62227.dev3.oxono.javafx;
}
