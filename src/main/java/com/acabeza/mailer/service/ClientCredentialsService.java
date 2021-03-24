package com.acabeza.mailer.service;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.apache.hc.core5.http.ParseException;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

public class ClientCredentialsService {

    private static final String clientId = System.getenv("Spotify_clientId");
    private static final String clientSecret = System.getenv("Spofity_clientSecret");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
            .setClientSecret(clientSecret).build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    public static void clientCredentials_Sync() throws Exception {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            if (clientCredentials.getExpiresIn() <= 0) {

                // Set access token for further "spotifyApi" object usage
                spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            }

            // System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            //
            // System.out.println("Access token: " + clientCredentials.getAccessToken());

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getAccessToken_Sync() {
        try {

            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage

            // System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            //
            // System.out.println("Access token: " + clientCredentials.getAccessToken());

            return clientCredentials.getAccessToken();

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
