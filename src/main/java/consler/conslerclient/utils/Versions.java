package consler.conslerclient.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Versions
{
    private static final String MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";

    public static class MCVersion
    {
        private final String id;
        private final String type;

        public MCVersion(String id, String type)
        {
            this.id = id;
            this.type = type;
        }

        public String getId() { return id; }
        public String getType() { return type; }
    }

    private static List<MCVersion> cachedVersions = null;

    /**
     * Fetches and caches the version manifest from Mojang (newest to oldest).
     */
    public static synchronized List<MCVersion> fetchAll()
    {
        if (cachedVersions != null)
        {
            return cachedVersions;
        }

        List<MCVersion> list = new ArrayList<>();
        try
        {
            URL url = URI.create(MANIFEST_URL).toURL();
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray versions = json.getAsJsonArray("versions");

            for (JsonElement version : versions)
            {
                JsonObject obj = version.getAsJsonObject();
                String id = obj.get("id").getAsString();
                String type = obj.get("type").getAsString();
                list.add(new MCVersion(id, type));
            }
            cachedVersions = list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Filters cached versions based on selected categories.
     */
    public static List<String> getFiltered(boolean releases, boolean snapshots, boolean betas, boolean alphas, boolean experiments)
    {
        List<MCVersion> all = fetchAll();
        List<String> filtered = new ArrayList<>();

        for (MCVersion version : all)
        {
            String type = version.getType().toLowerCase();
            String id = version.getId().toLowerCase();

            boolean isRelease = "release".equals(type);
            boolean isSnapshot = "snapshot".equals(type) && !id.contains("experimental");
            boolean isBeta = "old_beta".equals(type);
            boolean isAlpha = "old_alpha".equals(type);
            boolean isExperiment = id.contains("experimental") || "pending".equals(type);

            if ((isRelease && releases)
                    || (isSnapshot && snapshots)
                    || (isBeta && betas)
                    || (isAlpha && alphas)
                    || (isExperiment && experiments))
            {
                filtered.add(version.getId());
            }
        }

        return filtered;
    }
}