package consler.conslerclient.launcher.auth;

import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;

import java.io.File;
import java.nio.file.Path;

import static consler.conslerclient.Main.APPDATA_DIR;

public class AuthInfosSaver
{

    private static final Path CONFIG_FILE = new File(APPDATA_DIR, "authinfos.txt").toPath();

    private static final Saver SAVER = new Saver(CONFIG_FILE);

    public static void saveAuthInfos(AuthInfos authInfos)
    {
        if (!APPDATA_DIR.exists())
        {
            APPDATA_DIR.mkdirs();
        }

        SAVER.set("name", authInfos.getUsername());
        SAVER.set("token", authInfos.getAccessToken());
        SAVER.set("uuid", authInfos.getUuid());
    }

    public static AuthInfos loadSavedSession()
    {
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
