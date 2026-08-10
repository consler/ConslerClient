package consler.conslerclient.ui.client;

import consler.conslerclient.launcher.Launcher;
import consler.conslerclient.ui.instance.create.NewInstanceApplication;
import consler.conslerclient.ui.instance.manager.InstanceManagerApplication;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable
{

    public static HostServices hostServices;

    @FXML
    private ChoiceBox<String> launchChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    void launchButtonClicked()
    {
        Launcher.launch(launchChoiceBox.getValue());
    }

    @FXML
    void instanceManageButtonClicked() throws IOException {

        InstanceManagerApplication.open();
    }

    @FXML
    void newInstanceButtonClicked() throws IOException
    {
        NewInstanceApplication.open();
    }

    @FXML
    void githubPageButtonClicked()
    {
        if (hostServices != null)
        {
            hostServices.showDocument("https://github.com/consler/ConslerClient");
        }
    }

}
