package consler.conslerclient.ui;

import consler.conslerclient.utils.Versions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable
{
    @FXML
    private ChoiceBox<String> launchChoiceBox;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        launchChoiceBox.setItems(Versions.list());

    }

    @FXML
    void launchButtonClicked()
    {

    }

}
