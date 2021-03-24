package com.acabeza.mailer.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import com.wrapper.spotify.requests.data.albums.GetAlbumsTracksRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfNewReleasesRequest;
import com.acabeza.mailer.model.MyAlbum;
import com.acabeza.mailer.model.Track;
import com.neovisionaries.i18n.CountryCode;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    private static final String accessToken = ClientCredentialsService.getAccessToken_Sync();

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();

    // private static GetListOfNewReleasesRequest getListOfNewReleasesRequest = spotifyApi.getListOfNewReleases()
    //// .country(CountryCode.NL)
    // .limit(50)
    // .offset(offset)
    // .build();

    public static List<MyAlbum> getListOfNewReleases_Sync(CountryCode country) {
        try {
            List<MyAlbum> albums = new ArrayList<MyAlbum>();

            int meer = 0;
            int i = 0;
            int offset = 0;
            do {

                offset = 50 * meer;
                // System.out.println("offset: " + offset);
                GetListOfNewReleasesRequest getListOfNewReleasesRequest = spotifyApi.getListOfNewReleases()
                        .country(country).limit(50).offset(offset).build();

                Paging<AlbumSimplified> albumSimplifiedPaging = getListOfNewReleasesRequest.execute();

                LocalDate date = LocalDate.now();
                DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                for (AlbumSimplified e : albumSimplifiedPaging.getItems()) {

                    LocalDate ld = LocalDate.parse(e.getReleaseDate(), DATEFORMATTER);
                    Period period = Period.between(ld, date);
                    // System.out.println("** " + ++i + " " + e.getName() + " Release "+ ld + " -- period: " +
                    // period.getDays() + " dagen geleden");

                    if (period.getDays() < 8) {

                        GetArtistRequest artistrequest = new GetArtistRequest.Builder(accessToken)
                                .id(e.getArtists()[0].getId()).build();
                        Artist artist = artistrequest.execute();
                        String genres = "";
                        for (String g : artist.getGenres()) {
                            genres += g + " - ";
                        }
                        // System.out.println("genres: " + genres);

                        MyAlbum album = new MyAlbum(e.getId(), e.getName(), e.getArtists()[0].getName(),
                                e.getAlbumType(), e.getReleaseDate(), e.getImages()[1].getUrl(), genres);

                        // System.out.println("***: " + ++i + " " + e.getArtists()[0].getName() + " - " + e.getName() +
                        // " - " + e.getId() + " - " + e.getImages()[1].getUrl() );

                        GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi.getAlbumsTracks(e.getId()).build();
                        Paging<TrackSimplified> trackSimplifiedPaging = getAlbumsTracksRequest.execute();

                        List<Track> tracks = new ArrayList<Track>();

                        for (TrackSimplified t : trackSimplifiedPaging.getItems()) {

                            if (t.getPreviewUrl() != null) {

                                String[] prevurl = t.getPreviewUrl().split("\\?cid");

                                Track track = new Track();
                                track.setName(t.getName());
                                track.setPrev_url(prevurl[0]);

                                tracks.add(track);

                                // System.out.println("*****t**: " + t.getName() + " " + prevurl[0] );
                            } else {

                                Track track = new Track();
                                track.setName(t.getName());

                                tracks.add(track);
                                // System.out.println("*****t**: " + t.getName());
                            }

                        }
                        if (tracks.size() > 0) {
                            album.setTracks(tracks);
                        } else {
                            Track track = new Track();
                            track.setName("geen");
                            tracks.add(track);
                        }
                        String[] test = { "een", "twee" };
                        album.setPrev_url(test);
                        albums.add(album);
                        // System.out.println();

                    }
                }

                // System.out.println("Total: " + albumSimplifiedPaging.getTotal());
                // offset += albumSimplifiedPaging.getTotal();
                // albumSimplifiedPaging = getListOfNewReleasesRequest.execute();
                // meer = albumSimplifiedPaging.getTotal();
                // System.out.println("new total: "+ meer);

                meer += 1;

            } while (meer < 2); // meer even op 0 laten om maar 1x te draaien (RATE LIMIT)
            return albums;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());

            return null;
        }
    }
}
