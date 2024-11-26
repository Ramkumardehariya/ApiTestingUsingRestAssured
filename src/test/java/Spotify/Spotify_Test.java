package Spotify;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Spotify_Test {
    String Token = "BQA9vsb474r-CaBse1CNETHBJ-dJKKOHcxq06wmQbH8JZHEsTuep6EYdNPJO0mxXqPnKQTzX2ZFIEkMBkuBUkszW3xlrlSAuryWZBfGYqR1qtbZnnyyev9BxGGig2YWynJzuS8AWTFEGQH0PGTP0huvemwQrbR9f4xvQGTKq4NBDtLNd3oprvQl7nwfdRyczqBKGaSY8qIN9r1rEflC_CZ89RxWeveRXVfnggcUaK0J24auzu9VhBLRcixl_N_XM4G5EXdGPrz1i3ZIpzP9O2qeJVR48L4UGyV5DokrAWg1-HggY6fQJ_h07cAa6VO16uKQdeszHv5Am-rFVnK0fYrc";

    String Id = null;

//-----------------------------------------USER----------------------------------------------------------------------

    @Test
    public void getCurrentProfile() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("https://api.spotify.com/v1/me");

        response.prettyPrint();
        Id = response.path("id");
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(dependsOnMethods = "getCurrentProfile")
    public void getUserProfile() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("https://api.spotify.com/v1/users/" + Id);

        response.prettyPrint();


        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getUserTopArtists() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("time_range", "medium_term")
                .queryParam("limit", 10)
                .queryParam("offset", 5)
                .when()
                .get("https://api.spotify.com/v1/me/top/artists");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void followPlaylist() {


        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("type", "playlist")

                .when()
                .put("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void unfollowPlaylist() {
        String playlistId = "3cEYpjA9oz9GiPac4AsH4n";

        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .when()
                .delete("https://api.spotify.com/v1/playlists/" + playlistId + "/followers");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getFollowedArtists() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("https://api.spotify.com/v1/me/following?type=artist");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void followArtistsOrUsers() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("type", "artist")
                .queryParam("ids", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                .when()
                .put("https://api.spotify.com/v1/me/following");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void unfollowArtistsOrUsers() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("type", "artist")
                .queryParam("ids", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                .when()
                .delete("https://api.spotify.com/v1/me/following");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void checkIfUserFollowsArtistsOrUsers() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("type", "artist")
                .queryParam("ids", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                .when()
                .get("https://api.spotify.com/v1/me/following/contains");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void checkIfCurrentUserFollowsPlaylist() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("ids", "jmperezperez")
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers/contains");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    //--------------------------------------------Tracks-----------------------------------------------------------------
    @Test
    public void CheckUserSavedAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks/contains?ids=18yVqkdbdRvS24c0Ilj2ci%2C1HGw3J3NxZO1TP1BTtVhpZ%2C7iHfbu1YPACw6oZPAFJtqe");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void getTrack() {
        Response trackResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/shows/contains?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");

        Assert.assertEquals(trackResponse.getStatusCode(), 200);
    }

    @Test
    public void getSeveralTracks() {
        Response tracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/tracks?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");

        Assert.assertEquals(tracksResponse.getStatusCode(), 200);
    }

    @Test
    public void getUserSavedTracks() {
        Response savedTracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/tracks?market=ES&limit=10&offset=5");

        Assert.assertEquals(savedTracksResponse.getStatusCode(), 200);
    }

    @Test
    public void saveTracksForCurrentUser() {
        Response saveTracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\"ids\": [\"4uLU6hMCjMI75M1A2tKUQC\"]}")
                .when()
                .put("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");

        Assert.assertEquals(saveTracksResponse.getStatusCode(), 200);
    }

    @Test
    public void removeUserSavedTracks() {
        Response removeTracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\"ids\": [\"4VqPOruhp5EdPBeR92t6lQ\"]}")
                .when()
                .delete("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");

        Assert.assertEquals(removeTracksResponse.getStatusCode(), 200);
    }

    @Test
    public void checkUserSavedTracks() {
        Response checkTracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/tracks/contains?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");

        Assert.assertEquals(checkTracksResponse.getStatusCode(), 200);
    }

    @Test
    public void getSeveralTracksAudioFeatures() {
        Response audioFeaturesResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audio-features?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");

        Assert.assertEquals(audioFeaturesResponse.getStatusCode(), 200);
    }

    @Test
    public void getTrackAudioFeatures() {
        Response audioFeaturesResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audio-features/11dFghVXANMlKmJXsNCbNl");

        Assert.assertEquals(audioFeaturesResponse.getStatusCode(), 200);
    }

    @Test
    public void getTrackAudioAnalysis() {
        Response audioAnalysisResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/11dFghVXANMlKmJXsNCbNl");

        Assert.assertEquals(audioAnalysisResponse.getStatusCode(), 200);
    }

    @Test
    public void getRecommendations() {
        Response recommendationsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/recommendations?limit=10&market=ES&seed_artists=4NHQUGzhtTLFvgF5SZesLK&seed_genres=classical%2Ccountry&seed_tracks=0c6xIDDpzE81m2q797ordA");

        Assert.assertEquals(recommendationsResponse.getStatusCode(), 200);
    }

    //    ---------------------------------------------- Shows-------------------------------------------------------------
    @Test
    public void getShow() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")         .when()
                .get("https://api.spotify.com/v1/shows/38bS44xjbVVZ3No3ByF1dJ");
        response.prettyPrint();
    }

    @Test
    public void getSeveralShows() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                .when()
                .get("https://api.spotify.com/v1/shows?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
        response.prettyPrint();
    }

    @Test
    public void getShowEpisodes() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                .when()
                .get("https://api.spotify.com/v1/shows/38bS44xjbVVZ3No3ByF1dJ/episodes");
        response.prettyPrint();
    }

    @Test
    public void getUserSavedShows() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                .when()
                .get("https://api.spotify.com/v1/me/shows");
        response.prettyPrint();
    }

    @Test
    public void saveShowsforCurrentUser() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                .when()
                .get("https://api.spotify.com/v1/me/shows?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
        response.prettyPrint();
    }

    @Test
    public void removeUserSavedShows() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                .when()
                .delete("https://api.spotify.com/v1/me/shows?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void CheckUserSavedShows() {
        Response response = given()
                .accept("application/json")
                .header("Authorization:", "BQBfi0WYDNxggDRPYmElLtHnzFMvn1IHuT0a_qPJgguImr_4Qh7LoeHByaOuzR89ZtZvcz7ncF10ICLu8GPMpmHRzkUN_uXgPlt2AKqWSkkXGyz9jKTkebuc91dXp_DX8NHZjA_n2AscnLcEEgt1Fg-xdYpSBeAFDPHJRi5mjgkqSHr2DC-cgn0sJLtUNVMg5jOSugkqfDRP3Ofln3ku5jEwvwH3Am10I1hxkQntDElBMi6M_RPERsDkGb4GJN28GFRckoYe8PAIo5OpyEUnsKFKoXwbgb3mCluN45dLDeLlBBlx0ac7any55X54zL7EY7xthbbyNN69x0IP7ZQCTCk")
                .when()
                .get("https://api.spotify.com/v1/me/shows/contains?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
        response.prettyPrint();
    }
//---------------------------------------------SEARCH------------------------------------------------------------------

    @Test
    public void searchForItem() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)

                .when()
                .get("https://api.spotify.com/v1/search?q=remaster%2520track%3ADoxy%2520artist%3AMiles%2520Davis&type=artist");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
//-------------------------------------PLAYLIST-------------------------------------------------------------------------

    @Test
    public void getPlaylist() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void updatePlaylistDetails() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .body("{\n" +
                        "    \"name\": \"Updated Playlist Name\",\n" +
                        "    \"description\": \"Updated playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    public void getPlaylistItems() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .queryParam("market", "ES")
                .queryParam("fields", "items(added_by.id,track(name,href,album(name,href)))")
                .queryParam("limit", 10)
                .queryParam("offset", 5)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/tracks");

        response.prettyPrint();


        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void updatePlaylistItems() {

        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"range_start\": 1,\n" +
                        "  \"insert_before\": 3,\n" +
                        "  \"range_length\": 2\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/tracks");


        response.prettyPrint();


        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void addItemsToPlaylist() {

        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"uris\": [\n" +
                        "    \"spotify:track:1a2b3c4d5e6f7g8h9i0j\"\n" +
                        "  ],\n" +
                        "  \"position\": 1\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/tracks?position=1");


        response.prettyPrint();


        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void removeItemsFromPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"tracks\": [\n" +
                        "    {\"uri\": \"spotify:track:1a2b3c4d5e6f7g8h9i0j\"}\n" +
                        "  ],\n" +
                        " \"snapshot_id\": \"SNAPSHOT_ID\"\n"+
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/tracks");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void getCurrentUserPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/playlists?limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }



    @Test
    public void getUserPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/users/smedjan/playlists?limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void createPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \"New Playlist\",\n" +
                        "  \"description\": \"New playlist description\",\n" +
                        "  \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/smedjan/playlists");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }



    @Test
    public void getFeaturedPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists?locale=sv_SE&limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }



    @Test
    public void getCategoryPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner/playlists?limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getPlaylistCoverImage() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/images");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }




    //    ------------------------------------------------PLAYRE----------------------------------------------------------
    @Test
    public void getPlaybackState() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/player?market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void transferPlayback() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"device_ids\": [\n" +
                        "    \"74ASZWbe4lXaubB36ztrGX\"\n" +
                        "  ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/player");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }



    @Test
    public void getAvailableDevices() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/player/devices");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getCurrentlyPlayingTrack() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/player/currently-playing?market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void startResumePlayback() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                        "  \"offset\": {\n" +
                        "    \"position\": 5\n" +
                        "  },\n" +
                        "  \"position_ms\": 0\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/me/player/play");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 405);
    }

    @Test
    public void pausePlayback() {
        String accessToken = "0d1841b0976bae2a3a310dd74c0f3df354899bc8";

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/player/pause");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void skipToNext() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .post("https://api.spotify.com/v1/me/player/next?device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    public void skipToPrevious() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .post("https://api.spotify.com/v1/me/player/previous?device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void seekToPosition() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/player/seek?position_ms=25000&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void setRepeatMode() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/player/repeat?state=context&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void setPlaybackVolume() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/player/volume?volume_percent=50&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void togglePlaybackShuffle() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/player/shuffle?state=true&device_id=0d1841b0976bae2a3a310dd74c0f3df354899bc8");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }



    @Test
    public void getRecentlyPlayedTracks() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/player/recently-played?limit=10&after=1484811043508");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void getUserQueue() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/player/queue");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }


    @Test
    public void addItemToPlaybackQueue() {
        String uri = "spotify:track:4iV5W9uYEdYUVa79Axb7Rh";
        String deviceId = "0d1841b0976bae2a3a310dd74c0f3df354899bc8";

        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .post("https://api.spotify.com/v1/me/player/queue?uri=" + uri + "&device_id=" + deviceId);

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 403);
    }

//---------------------------Markets-------------------------------------------------------------------------------


    @Test
    public void getAvailableMarkets() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/markets");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
//--------------------------------------------Genres ----------------------------------------------------


    @Test
    public void getAvailableGenreSeeds() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    //--------------------------------------Episodes--------------------------------------------------------------
    @Test
    public void getEpisode() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/episodes/512ojhOuo1ktJprKbVcKyQ?market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 404);
    }


    @Test
    public void getSeveralEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/episodes?ids=77o6BIVlYM3msb4MMIL1jH%2C0Q86acNRm6V9GYx55SXKwf&market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
    }
    @Test
    public void getUsersSavedEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/episodes?market=ES&limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void saveEpisodesForCurrentUser() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"ids\": [\n" +
                        "    \"77o6BIVlYM3msb4MMIL1jH\",\n" +
                        "    \"0Q86acNRm6V9GYx55SXKwf\"\n" +
                        "  ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/episodes");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void removeUserSavedEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"ids\": [\n" +
                        "    \"7ouMYWpwJ422jRcDASZB7P\",\n" +
                        "    \"4VqPOruhp5EdPBeR92t6lQ\",\n" +
                        "    \"2takcwOaAZWiXQijPHIx7B\"\n" +
                        "  ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/episodes");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void checkUserSavedEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/episodes/contains?ids=77o6BIVlYM3msb4MMIL1jH%2C0Q86acNRm6V9GYx55SXKwf");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    //    --------------------------------- Chapters---------------------------------------------------------------------
    @Test
    public void getChapter() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/chapters/0D5wENdkdwbqlrHoaJ9g29?market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 404);
    }



    @Test
    public void getSeveralChapters() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/chapters?ids=0IsXVP0JmcB2adSE338GkK%2C3ZXb8FKZGU0EHALYX6uCzU%2C0D5wENdkdwbqlrHoaJ9g29&market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    //----------------------------------------------- Categories---------------------------------------------------
    @Test
    public void getSeveralBrowseCategories() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/categories?locale=sv-ES&limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void getSingleBrowseCategory() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner?locale=sv-EU");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }



    //    -------------------------------------------Audiobook------------------------------------------------------------
    @Test
    public void getAudiobook() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/7iHfbu1YPACw6oZPAFJtqe?market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 404);
    }


    @Test
    public void getSeveralAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci%2C1HGw3J3NxZO1TP1BTtVhpZ%2C7iHfbu1YPACw6oZPAFJtqe&market=ES");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getAudiobookChapters() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/7iHfbu1YPACw6oZPAFJtqe/chapters");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getUsersSavedAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks?limit=10&offset=5");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void saveAudiobooksForUser() {
        String requestBody = "{\n" +
                "  \"ids\": [\n" +
                "    \"18yVqkdbdRvS24c0Ilj2ci\",\n" +
                "    \"1HGw3J3NxZO1TP1BTtVhpZ\",\n" +
                "    \"7iHfbu1YPACw6oZPAFJtqe\"\n" +
                "  ]\n" +
                "}";

        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("https://api.spotify.com/v1/me/audiobooks");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void removeSavedAudiobooksForUser() {
        String requestBody = "{\n" +
                "  \"ids\": [\n" +
                "    \"18yVqkdbdRvS24c0Ilj2ci\",\n" +
                "    \"1HGw3J3NxZO1TP1BTtVhpZ\",\n" +
                "    \"7iHfbu1YPACw6oZPAFJtqe\"\n" +
                "  ]\n" +
                "}";

        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .delete("https://api.spotify.com/v1/me/audiobooks");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void checkUserSavedAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks/contains?ids=18yVqkdbdRvS24c0Ilj2ci%2C1HGw3J3NxZO1TP1BTtVhpZ%2C7iHfbu1YPACw6oZPAFJtqe");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
    }


    //    ----------------------------Artists------------------------------------------------------------------------------
    @Test
    public void getArtist() {
        Response artistResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg");

        Assert.assertEquals(artistResponse.getStatusCode(), 200);
    }


    @Test
    public void getSeveralArtists() {
        Response artistsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx%2C57dN52uHvrHOxijzpIgu3E%2C1vCWHaC5f2uS3yhpwWbIA6");

        Assert.assertEquals(artistsResponse.getStatusCode(), 200);
    }
    @Test
    public void getArtistsAlbums() {
        Response albumsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums?include_groups=single%2Cappears_on&market=ES&limit=10&offset=5");

        Assert.assertEquals(albumsResponse.getStatusCode(), 200);
    }


    @Test
    public void getArtistsTopTracks() {
        Response topTracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?market=ES");

        Assert.assertEquals(topTracksResponse.getStatusCode(), 200);
    }

    @Test
    public void getArtistsRelated() {
        Response relatedArtistsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/related-artists");

        Assert.assertEquals(relatedArtistsResponse.getStatusCode(), 200);
    }

//-------------------------------------------------------------Albums--------------------------------------------------

    @Test
    public void getAlbum() {
        Response albumResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy?market=ES");

        Assert.assertEquals(albumResponse.getStatusCode(), 200);
    }
    @Test
    public void getSeveralAlbums() {
        Response albumsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/albums?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc&market=ES");

        Assert.assertEquals(albumsResponse.getStatusCode(), 200);
    }


    @Test
    public void getAlbumTracks() {
        Response albumTracksResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy/tracks?market=ES&limit=10&offset=5");

        Assert.assertEquals(albumTracksResponse.getStatusCode(), 200);
    }



    @Test
    public void getUsersSavedAlbums() {
        Response savedAlbumsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/albums?limit=10&offset=5&market=ES");

        Assert.assertEquals(savedAlbumsResponse.getStatusCode(), 200);
    }


    @Test
    public void saveAlbumsForCurrentUser() {


        Response saveAlbumsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc");

        Assert.assertEquals(saveAlbumsResponse.getStatusCode(), 200);
    }
    @Test
    public void removeUsersSavedAlbums() {
        String requestBody = "{\"ids\": [\"382ObEPsp2rxGrnsizN5TX\", \"1A2GTWGtFfWp7KSQTwWOyo\", \"2noRn2Aes5aoNVsU6iWThc\"]}";

        Response removeAlbumsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .delete("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc");

        Assert.assertEquals(removeAlbumsResponse.getStatusCode(), 200);
    }
    @Test
    public void checkUsersSavedAlbums() {
        Response checkAlbumsResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/albums/contains?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc");

        Assert.assertEquals(checkAlbumsResponse.getStatusCode(), 200);
    }


    @Test
    public void getNewReleases() {
        Response newReleasesResponse = given()
                .header("Authorization", "Bearer " + Token)
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases?limit=10&offset=5");

        Assert.assertEquals(newReleasesResponse.getStatusCode(), 200);
    }

}