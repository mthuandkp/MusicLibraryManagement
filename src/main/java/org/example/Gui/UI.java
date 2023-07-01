package org.example.Gui;

import org.example.Bus.PlaylistBus;
import org.example.Bus.TrackBus;
import org.example.Bus.UserBus;
import org.example.Constant.AppConstant;
import org.example.Constant.AppFunctionality;
import org.example.Dto.Response.PlaylistResponse;
import org.example.Dto.Response.TrackResponse;
import org.example.Dto.SearchTrackOnline;
import org.example.Dto.Track;
import org.example.Dto.User;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UI {
    private TrackBus trackBus;
    private PlaylistBus playlistBus;
    private HandleEvent handleEvent;
    private HandleInput handleInput;
    private UserBus userBus;
    private User userSession;

    public UI(User u) {
        this.userSession = u;
        trackBus = new TrackBus();
        handleInput = new HandleInput();
        //handleEvent = new HandleEvent();
        playlistBus = new PlaylistBus();
        userBus = new UserBus();
    }

    public void login() {
        clearConsole();
        //Header
        printHeader("Music Library Management System");
        //Body
        printStrikeThrough(AppConstant.WIDTH);
        printLabelFunctionality(" Please login !!! ");
        printStrikeThrough(AppConstant.WIDTH);

        //Handle
        List<String> loginInformation = handleInput.login();
        User user = userBus.login(loginInformation.get(0), loginInformation.get(1));
        if (user == null) {
            loginFail();
        } else {
            this.userSession = user;
            this.handleEvent = new HandleEvent(user);
            mainUI();
        }
    }

    public void mainUI() {
        clearConsole();
        //Header
        printHeader("Music Library Management System");
        //Body
        printStrikeThrough(AppConstant.WIDTH);
        AppFunctionality.MAIN_FUNC.forEach(this::printLabelFunctionality);
        printStrikeThrough(AppConstant.WIDTH);

        printLabelFunctionality("Management Tracks");
        printStrikeThrough(AppConstant.WIDTH);
        AppFunctionality.TRACK_FUNC.forEach(this::printLabelFunctionality);
        printStrikeThrough(AppConstant.WIDTH);

        printLabelFunctionality("Management Playlists");
        printStrikeThrough(AppConstant.WIDTH);
        AppFunctionality.PLAYLIST_FUNC.forEach(this::printLabelFunctionality);
        printStrikeThrough(AppConstant.WIDTH);
        AppFunctionality.EXIST_FUNC.forEach(this::printLabelFunctionality);
        printStrikeThrough(AppConstant.WIDTH);

        //Handle
        if(handleEvent == null){
            handleEvent = new HandleEvent(userSession);
        }
        handleEvent.handleMainUi();
    }

    private static void printHeader(String title) {
        printStrikeThrough(AppConstant.WIDTH);
        System.out.printf("| %-52s |\n", title);
        printStrikeThrough(AppConstant.WIDTH);
    }

    private void printLabelFunctionality(String name) {
        System.out.printf("| %-52s|\n", name);
    }

    public static void printStrikeThrough(Integer size) {
        System.out.printf(String.join("", Collections.nCopies(size, "-")) + "\n");
    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }


    public void loginFail() {
        System.out.println("Login fail. Please try again");
        pressAnyKey();
        login();

    }

    public void displayAllTrack() {
        clearConsole();
        // list of tracks
        printStrikeThrough(AppConstant.WIDTH);
        printLabelFunctionality("All track in system");
        printStrikeThrough(AppConstant.WIDTH);
        List<TrackResponse> trackList = trackBus.displayAllTrack();
        printTrackInList(trackList);

        pressAnyKey();
        mainUI();

    }


    public void viewDetailByTitleOrArtist(String keyword) {
        clearConsole();
        printStrikeThrough(AppConstant.WIDTH);
        printLabelFunctionality("Detail track in system with keyword: " + keyword);
        printStrikeThrough(AppConstant.WIDTH);

        List<TrackResponse> trackList = trackBus.viewDetailByTitleOrArtist(keyword);
        printTrackInTable(trackList);
        pressAnyKey();
        mainUI();
    }

    private void pressAnyKey() {
        System.out.print("Press Enter key to continue....");
        new Scanner(System.in).nextLine();
    }

    private void printTrackInTable(List<TrackResponse> trackList) {
        printStrikeThrough(AppConstant.WIDTH_TABLE_TRACK_DETAIL);
        System.out.printf(
                "|%-20s|%-12s|%-18s|%-15s|%-9s|%-10s|%-14s|%-24s|%-24s|\n",
                "Title", "Artist", "Album", "Genre", "Duration",
                "Creator's name", "Release Year", "Created At", "Updated At"
        );
        printStrikeThrough(AppConstant.WIDTH_TABLE_TRACK_DETAIL);

        trackList.stream().forEach(track -> {
            System.out.printf(
                    "|%-20s|%-12s|%-18s|%-15s|%-9s|%-10s|%-14s|%-24s|%-24s|\n",
                    track.getTitle(),
                    track.getArtist(),
                    track.getAlbum(),
                    track.getGenre(),
                    track.getDuration(),
                    track.getUsername(),
                    track.getReleaseYear(),
                    track.getCreatedAt(),
                    track.getUpdatedAt()
            );
        });
        printStrikeThrough(AppConstant.WIDTH_TABLE_TRACK_DETAIL);
    }

    public void searchByTitleOrArtistOrAlbumOrGenre(String keyword) {
        clearConsole();
        printStrikeThrough(AppConstant.WIDTH);
        printLabelFunctionality("List track in system with keyword: " + keyword);
        printStrikeThrough(AppConstant.WIDTH);

        List<TrackResponse> trackList = trackBus.searchByTitleOrArtistOrAlbumOrGenre(keyword);
        printTrackInList(trackList);
        pressAnyKey();
        mainUI();
    }

    private void printTrackInList(List<TrackResponse> trackList) {
        printStrikeThrough(AppConstant.WIDTH_TABLE_TRACK_LIST);
        System.out.printf(
                "|%-20s|%-12s|%-18s|%-15s|%-14s|%-20s|\n",
                "Title", "Artist", "Album", "Genre", "Release Year", "Creator's name"
        );
        printStrikeThrough(AppConstant.WIDTH_TABLE_TRACK_LIST);

        trackList.stream().forEach(track -> {
            System.out.printf(
                    "|%-20s|%-12s|%-18s|%-15s|%-14s|%-20s|\n",
                    track.getTitle(),
                    track.getArtist(),
                    track.getAlbum(),
                    track.getGenre(),
                    track.getReleaseYear(),
                    track.getUsername()
            );
        });
        printStrikeThrough(AppConstant.WIDTH_TABLE_TRACK_LIST);
    }

    public void searchPlaylistByTitle(String keyword) {
        clearConsole();
        printStrikeThrough(AppConstant.WIDTH);
        printLabelFunctionality("Playlist by title: " + keyword);
        printStrikeThrough(AppConstant.WIDTH);

        List<PlaylistResponse> playlists = playlistBus.searchPlaylistByTitle(keyword);
        printPlaylist(playlists);
        pressAnyKey();
        mainUI();
    }

    private void printPlaylist(List<PlaylistResponse> playlists) {
        printStrikeThrough(AppConstant.WIDTH_TABLE_PLAYLIST);
        System.out.printf(
                "|%-18s|%-30s|%-30s|%-25s|%-25s|\n",
                "Creator's name", "Title", "TrackList", "Created At", "Updated At"
        );
        printStrikeThrough(AppConstant.WIDTH_TABLE_PLAYLIST);

        playlists.stream().forEach(playlistResponse -> {
            List<Track> trackList = playlistResponse.getTrackLists();
            System.out.printf(
                    "|%-18s|%-30s|%-30s|%-25s|%-25s|\n",
                    playlistResponse.getUsername(),
                    playlistResponse.getTitle(),
                    trackList.size() > 0 ? trackList.get(0).getTitle() : "",
                    playlistResponse.getCreatedAt(),
                    playlistResponse.getUpdatedAt()
            );

            if (trackList.size() > 0) {
                trackList.remove(0);
            }

            trackList.stream().forEach(track -> {
                System.out.printf(
                        "|%-18s|%-30s|%-30s|%-25s|%-25s|\n",
                        "",
                        "",
                        track.getTitle(),
                        track.getCreatedAt(),
                        track.getUpdatedAt()
                );
            });

            printStrikeThrough(AppConstant.WIDTH_TABLE_PLAYLIST);


        });
    }

    public void addNewTrack(Track newTrack) {
        newTrack.setUserId(userSession.getId());
        String addNewTrackMessage = trackBus.addNewTrack(newTrack);
        System.out.println(addNewTrackMessage);
        pressAnyKey();
        mainUI();
    }

    public void UpdateExistTrack() {
        List<Track> trackList = trackBus.findByUserId(userSession.getId());
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printLabelFunctionality("All track of your account");
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        for (int i = 0; i < trackList.size(); i++) {
            printLabelFunctionality(String.format("%d. %s", i, trackList.get(i).getTitle()));
        }
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);

        if (trackList.size() == 0) {
            pressAnyKey();
            mainUI();
        } else {
            Integer select = handleInput.selectExistTrack(0, trackList.size() - 1);
            Track newTrack = handleInput.updateExistTrack(trackList.get(select));
            String resultMessage = trackBus.updateExistTrack(newTrack);
            System.out.println(resultMessage);
            pressAnyKey();
            mainUI();
        }
    }

    public void deleteTrack() {
        List<TrackResponse> trackList = trackBus.getResponseByUserId(userSession.getId());
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printLabelFunctionality("All track of your account");
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        for (int i = 0; i < trackList.size(); i++) {
            printLabelFunctionality(String.format("%d. %s", i, trackList.get(i).getTitle()));
        }
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);

        if (trackList.size() == 0) {
            pressAnyKey();
            mainUI();
        } else {
            Integer select = handleInput.selectExistTrack(0, trackList.size() - 1);

            String resultMessage = trackBus.deleteTrack(trackList.get(select).getId());
            System.out.println(resultMessage);
            pressAnyKey();
            mainUI();
        }
    }

    public void createNewPlaylist(String title) {
        String message = playlistBus.createNew(userSession.getId(), title);
        System.out.println(message);
        pressAnyKey();
        mainUI();
    }

    public void deletePlaylist() {
        List<PlaylistResponse> playlistResponseList = playlistBus.findByUserId(userSession.getId());
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printLabelFunctionality("All playlist of your account");
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        for (int i = 0; i < playlistResponseList.size(); i++) {
            printLabelFunctionality(String.format("%d. %s", i, playlistResponseList.get(i).getTitle()));
        }
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);

        if (playlistResponseList.size() == 0) {
            pressAnyKey();
            mainUI();
        } else {
            Integer select = handleInput.selectExistPlaylist(0, playlistResponseList.size() - 1);

            String resultMessage = playlistBus.deletePlaylist(playlistResponseList.get(select).getId());
            System.out.println(resultMessage);
            pressAnyKey();
            mainUI();
        }
    }

    public void viewAllPlaylist() {
        clearConsole();
        printStrikeThrough(AppConstant.WIDTH);
        printLabelFunctionality("All playlist: ");
        printStrikeThrough(AppConstant.WIDTH);

        List<PlaylistResponse> playlists = playlistBus.findAll();
        printPlaylist(playlists);
        pressAnyKey();
        mainUI();
    }

    public void addTrackToPlaylist() {
        List<PlaylistResponse> playlistResponseList = playlistBus.findByUserId(userSession.getId());
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printLabelFunctionality("All playlist of your account");
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        for (int i = 0; i < playlistResponseList.size(); i++) {
            printLabelFunctionality(String.format("%d. %s", i, playlistResponseList.get(i).getTitle()));
        }
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);

        if (playlistResponseList.size() == 0) {
            pressAnyKey();
            mainUI();
            return;
        }
        Integer select = handleInput.selectExistPlaylist(0, playlistResponseList.size() - 1);
        String selectedPlaylistId = playlistResponseList.get(select).getId();


        List<TrackResponse> trackResponseList = trackBus.findAll();
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printLabelFunctionality("All track in system");
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        for (int i = 0; i < trackResponseList.size(); i++) {
            printLabelFunctionality(String.format("%d. %s", i, trackResponseList.get(i).getTitle()));
        }
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);

        if (trackResponseList.size() == 0) {
            pressAnyKey();
            mainUI();
            return;
        }
        select = handleInput.selectExistTrack(0, trackResponseList.size() - 1);
        String selectedTrackId = trackResponseList.get(select).getId();


        String resultMessage = playlistBus.addTrackToList(selectedPlaylistId, selectedTrackId);
        System.out.println(resultMessage);
        pressAnyKey();
        mainUI();

    }

    public void searchOnline() {
        List<TrackResponse> trackResponseList = trackBus.findAll();
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printLabelFunctionality("All track in system");
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        for (int i = 0; i < trackResponseList.size(); i++) {
            printLabelFunctionality(String.format("%d. %s", i, trackResponseList.get(i).getTitle()));
        }
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);

        Integer select = handleInput.searchInYotube(0,trackResponseList.size()-1);
        String trackName = trackResponseList.get(select).getTitle();
        List<SearchTrackOnline> searchTrackOnlineList = trackBus.searchOnline(trackName);
        printStrikeThrough(AppConstant.WIDTH_TRACK_LIST);
        printSearchOnline(searchTrackOnlineList);

        pressAnyKey();
        mainUI();

    }

    private void printSearchOnline(List<SearchTrackOnline> searchTrackOnlineList) {
        printStrikeThrough(AppConstant.WIDTH_TABLE_SEARCH_ONLINE);
        System.out.printf(
                "|%-70s|%-50s|%-18s|%-15s|\n",
                "Title", "Link", "Duration", "Time"
        );
        printStrikeThrough(AppConstant.WIDTH_TABLE_SEARCH_ONLINE);

        searchTrackOnlineList.stream().forEach(trackOnline -> {
            System.out.printf(
                    "|%-70s|%-50s|%-18s|%-15s|\n",
                   trackOnline.getTitle(),
                    trackOnline.getUrl(),
                    trackOnline.getDuration(),
                    trackOnline.getDate()
            );
            printStrikeThrough(AppConstant.WIDTH_TABLE_SEARCH_ONLINE);

        });
    }
}
