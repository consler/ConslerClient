package consler.conslerclient.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;

import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import static javafx.collections.FXCollections.observableArrayList;

public class Versions
{
    private static final String MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";

    public static ObservableList<String> list()
    {
        ObservableList<String> versionNames = observableArrayList();
        try
        {
            URL url = URI.create(MANIFEST_URL).toURL();
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray versions = json.getAsJsonArray("versions");

            for (JsonElement version : versions)
            {
                String name = version.getAsJsonObject().get("id").getAsString();
                versionNames.add(name);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return versionNames;
    }
}
