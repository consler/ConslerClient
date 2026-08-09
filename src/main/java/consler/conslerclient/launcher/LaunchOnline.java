package consler.conslerclient.launcher;

import consler.conslerclient.exceptions.FailedToLaunchMinecraftException;
import consler.conslerclient.launcher.auth.Authorize;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.*;

import java.nio.file.Path;

public class LaunchOnline
{
    private static AuthInfos session_auth;

    public static void launch(String version)
    {

        new Thread(() ->
        {
            try
            {
                session_auth = Authorize.authorize(session_auth);

                GameInfos infos = new GameInfos("Minecraft " + version, new GameVersion(version, GameType.V1_13_HIGHER_VANILLA), new GameTweak[]{});

                Path game_dir_path = infos.getGameDir().toFile().toPath();

                VanillaVersion vv = new VanillaVersion.VanillaVersionBuilder().withName(version).build();
                FlowUpdater fu = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vv).build();
                fu.update(game_dir_path);

                NoFramework noFramework = new NoFramework(game_dir_path, session_auth, GameFolder.FLOW_UPDATER);

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
