package consler.conslerclient.ui.instance.manager;

import consler.conslerclient.launcher.Launcher;
import consler.conslerclient.ui.client.ClientController;
import consler.conslerclient.ui.instance.create.NewInstanceApplication;
import dev.dirs.BaseDirectories;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static consler.conslerclient.Main.APPDATA_DIR;

public class InstanceManagerController implements Initializable
{
    public static HostServices hostServices;

    @FXML
    private ListView<String> instanceList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadInstances();
        setupContextMenu();
    }

    private void loadInstances()
    {
        instanceList.getItems().clear();

        if (APPDATA_DIR.exists() && APPDATA_DIR.isDirectory())
        {
            File[] files = APPDATA_DIR.listFiles((dir, name) -> name.endsWith(".properties"));

            if (files != null)
            {
                for (File file : files)
                {
                    String fileName = file.getName();
                    instanceList.getItems().add(fileName.substring(0, fileName.length() - 11));
                }
            }
        }
    }

    private void setupContextMenu()
    {
        instanceList.setCellFactory(lv ->
        {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem launchItem = new MenuItem("Launch");
            launchItem.setOnAction(e -> Launcher.launch(cell.getItem()));

            MenuItem renameItem = new MenuItem("Rename");
            renameItem.setOnAction(e -> renameInstance(cell.getItem()));

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> deleteInstance(cell.getItem()));

            contextMenu.getItems().addAll(launchItem, renameItem, deleteItem);

            cell.textProperty().bind(cell.itemProperty());
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty)
                {
                    cell.setContextMenu(null);
                }
                else
                {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });
    }

    private void renameInstance(String oldName)
    {
        TextInputDialog dialog = new TextInputDialog(oldName);
        dialog.setTitle("Rename Instance");
        dialog.setHeaderText("Rename: " + oldName);
        dialog.setContentText("Enter new name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName ->
        {
            new File(APPDATA_DIR, oldName + ".properties").renameTo(new File(APPDATA_DIR, newName + ".properties"));
            new File(BaseDirectories.get().dataDir, oldName).renameTo(new File(BaseDirectories.get().dataDir, newName));

            refreshAllLists(newName);
        });
    }

    private void deleteInstance(String name)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Instance");
        alert.setHeaderText("Delete: " + name);
        alert.setContentText("Are you sure? This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            File propertiesFile = new File(APPDATA_DIR, name + ".properties");
            if (propertiesFile.exists()) propertiesFile.delete();

            File gameDir = new File(BaseDirectories.get().dataDir, name);
            deleteDirectory(gameDir);

            refreshAllLists(null);
        }
    }

    private void deleteDirectory(File dir)
    {
        if (dir != null && dir.exists())
        {
            File[] files = dir.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    if (file.isDirectory()) deleteDirectory(file);
                    else file.delete();
                }
            }
            dir.delete();
        }
    }

    private void refreshAllLists(String instanceToSelect)
    {
        loadInstances();
        if (ClientController.getInstance() != null)
        {
            ClientController.getInstance().loadInstances(instanceToSelect);
        }
    }

    @FXML
    void newInstanceButtonClicked() throws IOException
    {
        NewInstanceApplication.open();
    }

    @FXML
    void openFolderButtonClicked()
    {
        String selected = instanceList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        File folder = new File(APPDATA_DIR, selected);

        if (!folder.exists()) return;

        hostServices.showDocument(folder.toURI().toString());

    }
}