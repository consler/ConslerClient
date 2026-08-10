package consler.conslerclient.launcher;

import consler.conslerclient.exceptions.FailedToLaunchMinecraftException;
import consler.conslerclient.launcher.auth.Authorization;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;

public class LaunchOnline
{
    private static AuthInfos sessionAuth;

    public static void launch(String name, String version, String modLoader, String loaderVersion)
    {

        new Thread(() ->
        {
            try
            {
                sessionAuth = Authorization.authorize();

                NoFramework noFramework = new NoFramework(GameDirGenerator.createGameDir(name, true), sessionAuth, GameFolder.FLOW_UPDATER);

                switch(modLoader)
                {
                    case "Vanilla" -> noFramework.launch(version, loaderVersion, NoFramework.ModLoader.VANILLA);
                    case "Fabric" -> noFramework.launch(version, loaderVersion, NoFramework.ModLoader.FABRIC);
                    case "Forge" -> noFramework.launch(version, loaderVersion, NoFramework.ModLoader.FORGE);
                    case "Neoforge" -> noFramework.launch(version, loaderVersion, NoFramework.ModLoader.NEO_FORGE);
                    default -> throw new FailedToLaunchMinecraftException(modLoader + " is not a mod loader!");
                }
            }
            catch (Exception e)
            {
                throw new FailedToLaunchMinecraftException(e.getMessage());
            }
        }).start();
    }
}
