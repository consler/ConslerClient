module consler.conslerclient.conslerclient
{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javafx.web;
    requires javafx.swing;
    requires flowupdater;
    requires openlauncherlib;
    requires org.jetbrains.annotations;
    requires atlantafx.base;
    requires com.google.gson;
    requires openauth;

    opens consler.conslerclient to javafx.fxml;
    exports consler.conslerclient;
    exports consler.conslerclient.ui.client;
    exports consler.conslerclient.ui.instance.manager;
    exports consler.conslerclient.ui.instance.create;
    opens consler.conslerclient.ui.client to javafx.fxml;
    opens consler.conslerclient.ui.instance.manager to javafx.fxml;
    opens consler.conslerclient.ui.instance.create to javafx.fxml;
}