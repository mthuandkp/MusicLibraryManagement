package org.example.Dao;

import org.example.Constant.AppConstant;
import org.example.Dto.Playlist;
import org.example.Dto.Response.PlaylistResponse;
import org.example.Dto.Track;
import org.example.Dto.User;
import org.example.Utils.DateUtils;
import org.example.Utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaylistDao {
    private UserDao userDao = new UserDao();
    private TrackDao trackDao = new TrackDao();

    public List<PlaylistResponse> findByTitle(String title) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlists = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());

        List<PlaylistResponse> playlistResponseList = playlists.stream()
                .filter(playlist -> playlist.getTitle().toLowerCase().contains(title.toLowerCase()))
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return playlistResponseList;
    }

    private PlaylistResponse convertToResponse(Playlist playlist) {
        User user = userDao.findById(playlist.getUserId());
        List<Track> trackList = playlist.getTrackLists()
                .stream()
                .map(id -> {
                    Track track = trackDao.findById(id);
                    return track;
                })
                .collect(Collectors.toList());
        return PlaylistResponse.builder()
                .id(playlist.getId())
                .username(user.getUsername())
                .trackLists(trackList)
                .title(playlist.getTitle())
                .createdAt(playlist.getCreatedAt())
                .updatedAt(playlist.getUpdatedAt())
                .build();
    }

    public PlaylistResponse findByExactlyTitle(String title) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlists = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());

        Optional<PlaylistResponse> playlistResponseOptional = playlists.stream()
                .filter(playlist -> playlist.getTitle().equals(title))
                .map(this::convertToResponse)
                .findFirst();

        return playlistResponseOptional.isPresent() ? playlistResponseOptional.get() : null;
    }

    public Boolean createNewPlaylist(String userId, String title) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlistLists = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());
        Playlist playlist = Playlist.builder()
                .id(DateUtils.getEpoch())
                .userId(userId)
                .title(title)
                .trackLists(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        playlistLists.add(playlist);
        return FileUtils.writeJsonFile(AppConstant.PLAYLIST_FILE_PATH, playlistLists);
    }

    public List<PlaylistResponse> findByUserId(String id) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlists = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());

        List<PlaylistResponse> playlistResponseList = playlists.stream()
                .filter(playlist -> playlist.getUserId().equals(id))
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return playlistResponseList;
    }

    public Integer delete(String id) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlistsList = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());
        Optional<Playlist> deletePlaylistOptional = playlistsList.stream()
                .filter(playlist -> playlist.getId().equals(id)).findFirst();
        if (!deletePlaylistOptional.isPresent()) {
            return -1;
        } else {
            Playlist playlist = deletePlaylistOptional.get();
            playlistsList.remove(playlist);

            return FileUtils.writeJsonFile(AppConstant.PLAYLIST_FILE_PATH, playlistsList) ? 1 : 0;
        }
    }

    public List<PlaylistResponse> findAll() {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlists = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());

        List<PlaylistResponse> playlistResponseList = playlists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return playlistResponseList;
    }

    public Integer addToPlaylist(String selectedPlaylistId, String selectedTrackId) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlistList = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());
        Playlist playlistUser = playlistList.stream()
                .filter(playlist->playlist.getId().equals(selectedPlaylistId))
                .findAny().get();


        playlistList.remove(playlistUser);

        List<String> trackInFile = playlistUser.getTrackLists();
        if (trackInFile.indexOf(selectedTrackId) >= 0) {
            return -1;
        }
        trackInFile.add(selectedTrackId);
        playlistUser.setTrackLists(trackInFile);
        playlistList.add(playlistUser);
        return FileUtils.writeJsonFile(AppConstant.PLAYLIST_FILE_PATH, playlistList) ? 1 : 0;
    }

    private Playlist findById(String selectedPlaylistId) {
        String json = FileUtils.readJsonFile(AppConstant.PLAYLIST_FILE_PATH);
        List<Playlist> playlists = new Gson().fromJson(json, new TypeToken<List<Playlist>>() {
        }.getType());

        Optional<Playlist> playlistResponseOptional = playlists.stream()
                .filter(playlist -> playlist.getId().equals(selectedPlaylistId))
                .findFirst();

        return playlistResponseOptional.isPresent() ? playlistResponseOptional.get() : null;
    }
}
