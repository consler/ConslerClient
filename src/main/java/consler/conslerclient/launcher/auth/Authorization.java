package consler.conslerclient.launcher.auth;

import consler.conslerclient.exceptions.FailedToLaunchMinecraftException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;

import java.util.UUID;

public class Authorization
{
    public static AuthInfos authorize()
    {
        if(AuthInfosSaver.loadSavedSession() != null) return AuthInfosSaver.loadSavedSession();
        try
        {
            return authorizeWithWebView();
        }
        catch (Exception e)
        {
            throw new FailedToLaunchMinecraftException(e.getMessage());
        }

    }

    public static AuthInfos authorize(String username) //offline
    {
        return new AuthInfos(username, "0", UUID.fromString(username).toString());
    }

    public static AuthInfos authorizeWithWebView() throws MicrosoftAuthenticationException
    {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult authResult = authenticator.loginWithWebview();

        AuthInfos authInfos = new AuthInfos(authResult.getProfile().getName(), authResult.getAccessToken(), authResult.getProfile().getId());

        AuthInfosSaver.saveAuthInfos(authInfos);

        return authInfos;
    }
}
