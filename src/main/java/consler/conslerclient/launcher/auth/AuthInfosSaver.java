package consler.conslerclient.launcher.auth;

import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;

import java.io.File;

public class AuthInfosSaver
{
    private static final File APPDATA_DIR = new File(System.getenv("HOME"), ".ConslerClient");
    private static final File CONFIG_FILE = new File(APPDATA_DIR, "authinfos.txt");

    private static final Saver SAVER = new Saver(CONFIG_FILE);

    private static void saveAuthInfos(AuthInfos authInfos)
    {
        if (!APPDATA_DIR.exists())
        {
            APPDATA_DIR.mkdirs();
        }

        SAVER.set("name", authInfos.getUsername());
        SAVER.set("token", authInfos.getAccessToken());
        SAVER.set("uuid", authInfos.getUuid());
    }

    private static AuthInfos loadSavedSession()
    {
        if (!CONFIG_FILE.exists()) return null;

        String name = SAVER.get("name");
        String token = SAVER.get("token");
        String uuid = SAVER.get("uuid");

        if (name != null && token != null && uuid != null)
        {
            return new AuthInfos(name, token, uuid);
        }

        return null;
    }


}
