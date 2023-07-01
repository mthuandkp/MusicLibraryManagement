package org.example.Bus;

import org.example.Dao.PlaylistDao;
import org.example.Dto.Response.PlaylistResponse;

import java.util.List;

public class PlaylistBus {
    private PlaylistDao playlistDao = new PlaylistDao();
    public List<PlaylistResponse> searchPlaylistByTitle(String keyword) {
        return playlistDao.findByTitle(keyword);
    }

    public String createNew(String userId, String title) {
        Boolean createNewPlaylist = playlistDao.createNewPlaylist(userId, title);

        return createNewPlaylist ? "Create playlist successfully" : "Error when create playlist";
    }

    public List<PlaylistResponse> findByUserId(String id) {
        return playlistDao.findByUserId(id);
    }

    public String deletePlaylist(String id) {
        Integer deleteTrack = playlistDao.delete(id);
        switch (deleteTrack){
            case -1: {
                return "Playlist not found";
            }
            case 1: {
                return "Delete playlist successfully";
            }
            case 0: {
                return "Error when delete exist playlist";
            }
        }
        return "";
    }

    public List<PlaylistResponse> findAll() {
        return playlistDao.findAll();
    }

    public String addTrackToList(String selectedPlaylistId, String selectedTrackId) {
        Integer addToPlaylist = playlistDao.addToPlaylist(selectedPlaylistId,selectedTrackId);
        switch (addToPlaylist){
            case -1: {
                return "Track already add before";
            }
            case 1: {
                return "Add to playlist successfully";
            }
            case 0: {
                return "Error when add to playlist";
            }
        }
        return "";
    }
}
