module consler.conslerclient.conslerclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens consler.conslerclient.conslerclient to javafx.fxml;
    exports consler.conslerclient.conslerclient;
}