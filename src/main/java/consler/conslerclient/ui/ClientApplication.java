package consler.conslerclient.ui;

import atlantafx.base.theme.PrimerLight;
import consler.conslerclient.Main;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("client-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        stage.setMaximized(true);
        stage.setTitle("Consler Client");

        stage.setScene(scene);
        stage.show();
    }
}
