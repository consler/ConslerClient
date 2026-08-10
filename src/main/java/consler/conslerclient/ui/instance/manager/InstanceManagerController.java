package consler.conslerclient.ui.instance.manager;


import consler.conslerclient.ui.instance.create.NewInstanceApplication;
import javafx.fxml.FXML;

import java.io.IOException;

public class InstanceManagerController
{
    @FXML
    void newInstanceButtonClicked() throws IOException
    {
        NewInstanceApplication.open();
    }
}
