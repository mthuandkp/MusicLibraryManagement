package org.example.Bus;

import org.example.Dao.TrackDao;
import org.example.Dto.Response.TrackResponse;
import org.example.Dto.SearchTrackOnline;
import org.example.Dto.Track;

import java.util.List;

public class TrackBus {
    private TrackDao trackDao = new TrackDao();
    public List<TrackResponse> displayAllTrack() {
        return trackDao.findAll();
    }

    public List<TrackResponse> viewDetailByTitleOrArtist(String keyword) {
        return trackDao.findByTitleOrArtist(keyword);
    }

    public List<TrackResponse> searchByTitleOrArtistOrAlbumOrGenre(String keyword) {
        return trackDao.searchByTitleOrArtistOrAlbumOrGenre(keyword);
    }

    public String addNewTrack(Track newTrack) {
        Integer addNewTrack = trackDao.addNew(newTrack);
        switch (addNewTrack){
            case -1: {
                return "User not found";
            }
            case 1: {
                return "Add new track successfully";
            }
            case 0: {
                return "Error when add new track";
            }
        }
        return "";
    }

    public List<Track> findByUserId(String id) {
        return trackDao.findAllByUserId(id);
    }

    public String updateExistTrack(Track updateTrack) {
        Integer addNewTrack = trackDao.update(updateTrack.getId(),updateTrack);
        switch (addNewTrack){
            case -1: {
                return "Track not found";
            }
            case 1: {
                return "Update exist track successfully";
            }
            case 0: {
                return "Error when update exist track";
            }
        }
        return "";
    }

    public String deleteTrack(String id) {
        Integer deleteTrack = trackDao.delete(id);
        switch (deleteTrack){
            case -1: {
                return "Track not found";
            }
            case 1: {
                return "Delete track successfully";
            }
            case 0: {
                return "Error when delete exist track";
            }
        }
        return "";
    }


    public List<TrackResponse> getResponseByUserId(String id) {
        return trackDao.getResponseById(id);
    }

    public List<TrackResponse> findAll() {
        return trackDao.findAll();
    }

    public List<SearchTrackOnline> searchOnline(String trackName) {
        return trackDao.searchOnline(trackName);
    }
}
