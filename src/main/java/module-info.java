module loyalixa.oop.mastermiendgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens loyalixa.oop.mastermiendgame to javafx.fxml;
    exports loyalixa.oop.mastermiendgame;
}