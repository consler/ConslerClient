package consler.conslerclient.ui.client;

import atlantafx.base.theme.PrimerLight;
import consler.conslerclient.Main;
import consler.conslerclient.ui.instance.manager.InstanceManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application
{

    @Override
    public void start(Stage stage) throws IOException
    {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("client-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setTitle("Consler Client");

        ClientController.hostServices = getHostServices();
        InstanceManagerController.hostServices = getHostServices();

        stage.setScene(scene);
        stage.show();
    }
}
