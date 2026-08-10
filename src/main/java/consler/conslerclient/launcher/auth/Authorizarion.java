package consler.conslerclient.launcher.auth;

import consler.conslerclient.exceptions.FailedToLaunchMinecraftException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;

import java.util.UUID;

public class Authorizarion
{
    public static AuthInfos authorize()
    {

        try
        {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            MicrosoftAuthResult authResult = authenticator.loginWithWebview();

            return new AuthInfos(authResult.getProfile().getName(), authResult.getAccessToken(), authResult.getProfile().getId());
        }
        catch (Exception e)
        {
            throw new FailedToLaunchMinecraftException(e.getMessage());
        }

    }

    public static AuthInfos authorize(String username)
    {
        return new AuthInfos(username, "0", UUID.fromString(username).toString());
    }
}
