package consler.conslerclient.launcher;

import consler.conslerclient.launcher.instance.InstanceInfo;

import java.util.Map;

public class Launcher
{
    public static void launch(String name)
    {
        Map<String, String> instanceInfo = InstanceInfo.load(name);

        LaunchOnline.launch(name, instanceInfo.get("version"), instanceInfo.get("modLoader"), instanceInfo.get("loaderVersion"));
    }

    public static void launch(String version, String username)
    {

    }
}