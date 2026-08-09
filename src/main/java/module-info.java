module consler.conslerclient.conslerclient
{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires flowupdater;
    requires openlauncherlib;
    requires org.jetbrains.annotations;
    requires atlantafx.base;
    requires com.google.gson;

    opens consler.conslerclient to javafx.fxml;
    exports consler.conslerclient;
    exports consler.conslerclient.ui;
    opens consler.conslerclient.ui to javafx.fxml;
}