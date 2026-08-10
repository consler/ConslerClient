package consler.conslerclient.launcher;

import consler.conslerclient.exceptions.FailedToLaunchMinecraftException;
import consler.conslerclient.launcher.auth.Authorizarion;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.*;

import java.nio.file.Path;

public class LaunchOnline
{
    private static AuthInfos sessionAuth;

    public static void launch(String version)
    {

        new Thread(() ->
        {
            try
            {
                sessionAuth = Authorizarion.authorize();

                GameInfos infos = new GameInfos("Minecraft " + version, new GameVersion(version, GameType.V1_13_HIGHER_VANILLA), new GameTweak[]{});

                Path gameDirPath = infos.getGameDir().toFile().toPath();

                VanillaVersion vv = new VanillaVersion.VanillaVersionBuilder().withName(version).build();
                FlowUpdater fu = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vv).build();
                fu.update(gameDirPath);

                NoFramework noFramework = new NoFramework(gameDirPath, sessionAuth, GameFolder.FLOW_UPDATER);

                noFramework.launch(version, version, NoFramework.ModLoader.VANILLA);

                System.exit(0);

            }
            catch (Exception e)
            {
                throw new FailedToLaunchMinecraftException(e.getMessage());
            }
        }).start();
    }
}
