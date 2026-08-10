package consler.conslerclient.utils;

import javafx.collections.ObservableList;

import static javafx.collections.FXCollections.observableArrayList;

public class ModLoaders
{
    public static ObservableList<String> list()
    {
        ObservableList<String> modLoaders = observableArrayList();

        modLoaders.add("Vanilla");
        modLoaders.add("Fabric");
        modLoaders.add("Forge");
        modLoaders.add("Neoforge");

        return modLoaders;
    }
}
