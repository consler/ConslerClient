package consler.conslerclient.ui.instance.create;

import consler.conslerclient.launcher.instance.Downloader;
import consler.conslerclient.ui.client.ClientController;
import consler.conslerclient.utils.ModLoaders;
import consler.conslerclient.utils.Versions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class NewInstanceController implements Initializable
{
    @FXML
    private TextField nameField;

    @FXML
    private ToggleGroup modLoaderGroup;

    @FXML
    private RadioButton loaderVanilla;

    @FXML
    private RadioButton loaderFabric;

    @FXML
    private RadioButton loaderForge;

    @FXML
    private RadioButton loaderNeoforge;

    @FXML
    private ComboBox<String> modLoaderVersionChoice;

    @FXML
    private ComboBox<String> versionChoice;

    @FXML
    private CheckBox filterReleases;

    @FXML
    private CheckBox filterSnapshots;

    @FXML
    private CheckBox filterBetas;

    @FXML
    private CheckBox filterAlphas;

    @FXML
    private CheckBox filterExperiments;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Add listeners to checkboxes to trigger list updates
        filterReleases.selectedProperty().addListener((obs, oldV, newV) -> updateMinecraftVersions());
        filterSnapshots.selectedProperty().addListener((obs, oldV, newV) -> updateMinecraftVersions());
        filterBetas.selectedProperty().addListener((obs, oldV, newV) -> updateMinecraftVersions());
        filterAlphas.selectedProperty().addListener((obs, oldV, newV) -> updateMinecraftVersions());
        filterExperiments.selectedProperty().addListener((obs, oldV, newV) -> updateMinecraftVersions());

        // Initial async load of Minecraft versions
        updateMinecraftVersions();

        modLoaderGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> onModLoaderChanged());
    }

    private void updateMinecraftVersions()
    {
        boolean releases = filterReleases.isSelected();
        boolean snapshots = filterSnapshots.isSelected();
        boolean betas = filterBetas.isSelected();
        boolean alphas = filterAlphas.isSelected();
        boolean experiments = filterExperiments.isSelected();

        versionChoice.setPromptText("Loading versions...");

        CompletableFuture.supplyAsync(() -> Versions.getFiltered(releases, snapshots, betas, alphas, experiments))
                .thenAcceptAsync(versions -> {
                    versionChoice.getItems().setAll(versions);

                    if (!versions.isEmpty())
                    {
                        versionChoice.getSelectionModel().selectFirst();
                        versionChoice.setPromptText("Select Version...");
                    }
                    else
                    {
                        versionChoice.setPromptText("No versions selected");
                    }
                }, Platform::runLater);
    }

    private void onModLoaderChanged()
    {
        if (loaderVanilla.isSelected() || modLoaderGroup.getSelectedToggle() == null)
        {
            modLoaderVersionChoice.getItems().clear();
            modLoaderVersionChoice.setDisable(true);
            modLoaderVersionChoice.setPromptText("N/A for Vanilla");
            return;
        }

        modLoaderVersionChoice.setDisable(true);
        modLoaderVersionChoice.getItems().clear();
        modLoaderVersionChoice.setPromptText("Loading versions...");

        CompletableFuture.supplyAsync(() ->
        {
            if (loaderFabric.isSelected()) return ModLoaders.listFabricVersions();
            else if (loaderForge.isSelected()) return ModLoaders.listForgeVersions();
            else if (loaderNeoforge.isSelected()) return ModLoaders.listNeoforgeVersions();

            return Collections.<String>emptyList();
        }).thenAcceptAsync(versions ->
        {
            modLoaderVersionChoice.getItems().setAll(versions);

            if (!versions.isEmpty())
            {
                modLoaderVersionChoice.getSelectionModel().selectFirst();
                modLoaderVersionChoice.setDisable(false);
                modLoaderVersionChoice.setPromptText("Select Loader Version...");
            } else
            {
                modLoaderVersionChoice.setPromptText("Failed to load versions");
            }
        }, Platform::runLater);
    }

    private String getSelectedModLoader()
    {
        if (loaderVanilla.isSelected()) return "Vanilla";
        if (loaderFabric.isSelected()) return "Fabric";
        if (loaderForge.isSelected()) return "Forge";
        if (loaderNeoforge.isSelected()) return "Neoforge";

        return null;
    }

    @FXML
    private void cancelButtonClicked()
    {
        NewInstanceApplication.close();
    }

    @FXML
    private void createButtonClicked() throws Exception
    {
        String instanceName = nameField.getText();

        Downloader.download(instanceName, versionChoice.getValue(), Objects.requireNonNull(getSelectedModLoader()), modLoaderVersionChoice.getValue());

        if (ClientController.getInstance() != null)
        {
            ClientController.getInstance().loadInstances(instanceName);
        }

        NewInstanceApplication.close();
    }
}