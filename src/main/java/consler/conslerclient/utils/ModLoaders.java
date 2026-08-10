package consler.conslerclient.utils;

import consler.conslerclient.exceptions.FailedToLoadModVersionsException;
import javafx.collections.ObservableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

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

    public static ObservableList<String> listFabricVersions()
    {
        ObservableList<String> versions = observableArrayList();
        try
        {
            URL url = URI.create("https://meta.fabricmc.net/v2/versions/loader").toURL();
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++)
            {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();
                versions.add(obj.get("version").getAsString());
            }
        }
        catch (Exception e)
        {
            throw new FailedToLoadModVersionsException("Failed to load Fabric versions");
        }
        return versions;
    }

    public static ObservableList<String> listForgeVersions()
    {
        return fetchVersionsFromMavenXML("https://maven.minecraftforge.net/net/minecraftforge/forge/maven-metadata.xml");
    }

    public static ObservableList<String> listNeoforgeVersions()
    {
        return fetchVersionsFromMavenXML("https://maven.neoforged.net/releases/net/neoforged/neoforge/maven-metadata.xml");
    }

    private static ObservableList<String> fetchVersionsFromMavenXML(String metadataUrl)
    {
        ObservableList<String> versions = observableArrayList();
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URI.create(metadataUrl).toURL().openStream());

            NodeList versionNodes = doc.getElementsByTagName("version");
            for (int i = 0; i < versionNodes.getLength(); i++)
            {
                versions.add(versionNodes.item(i).getTextContent());
            }
        }
        catch (Exception e)
        {
            throw new FailedToLoadModVersionsException("Failed to load neoforge/forge versions from Maven XML");
        }
        return versions;
    }
}