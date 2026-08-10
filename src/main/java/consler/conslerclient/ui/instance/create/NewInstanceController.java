package consler.conslerclient.ui.instance.create;

import consler.conslerclient.utils.ModLoaders;
import consler.conslerclient.utils.Versions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class NewInstanceController implements Initializable
{
    @FXML
    private ComboBox<String> versionChoice;

    @FXML
    private ComboBox<String> modLoaderChoice;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources)
    {
        versionChoice.setItems(Versions.list());

        modLoaderChoice.setItems(ModLoaders.list());
    }

    @FXML
    private void cancelButtonClicked()
    {
        NewInstanceApplication.close();
    }
}
