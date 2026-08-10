package consler.conslerclient.launcher;

import consler.conslerclient.launcher.instance.InstanceInfo;
import dev.dirs.BaseDirectories;

import java.io.File;
import java.util.Map;

public class Launcher
{
    public static String instanceDir = new File(BaseDirectories.get().dataLocalDir, "ConslerClient").getAbsolutePath();

    public static void launch(String name)
    {
        Map<String, String> instanceInfo = InstanceInfo.load(name);

        LaunchOnline.launch(name, instanceInfo.get("version"), instanceInfo.get("modLoader"), instanceInfo.get("loaderVersion"));
    }

    public static void launch(String version, String username)
    {

    }
}