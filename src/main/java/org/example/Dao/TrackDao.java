package org.example.Dao;

import org.example.Constant.AppConstant;
import org.example.Dto.Response.TrackResponse;
import org.example.Dto.SearchTrackOnline;
import org.example.Dto.Track;
import org.example.Dto.User;
import org.example.Utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrackDao {
    private UserDao userDao = new UserDao();
    public List<TrackResponse> findAll(){
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());
        List<TrackResponse> trackResponseList = trackList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return trackResponseList;
    }

    private TrackResponse convertToResponse(Track track) {
        User user = userDao.findById(track.getUserId());
        return TrackResponse.builder()
                .id(track.getId())
                .username(user.getUsername())
                .title(track.getTitle())
                .artist(track.getArtist())
                .album(track.getAlbum())
                .genre(track.getGenre())
                .releaseYear(track.getReleaseYear())
                .duration(track.getDuration())
                .createdAt(track.getCreatedAt())
                .updatedAt(track.getUpdatedAt())
                .build();
    }

    public List<Track> findAllByUserId(String userId){
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());

        List<Track> trackResponseList = trackList.stream()
                .filter(track -> track.getUserId().equals(userId))
                //.map(this::convertToResponse)
                .collect(Collectors.toList());
        return trackResponseList;
    }

    public Integer addNew(Track track){
        UserDao userDao = new UserDao();
        if(userDao.findById(track.getUserId()) == null){
            return -1;
        }
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());
        trackList.add(track);

        return FileUtils.writeJsonFile(AppConstant.TRACK_FILE_PATH, trackList) ? 1 : 0;
    }

    public List<TrackResponse> findByTitleOrArtist(String keyword){
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());

        trackList = trackList.stream()
                .filter(
                        track -> {
                            String lowerKeyword = keyword.toLowerCase();
                            return track.getTitle().toLowerCase().contains(lowerKeyword)
                                    || track.getArtist().toLowerCase().contains(lowerKeyword);
                        }
                )
                .collect(Collectors.toList());

        List<TrackResponse> trackResponseList = trackList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return trackResponseList;
    }

    public Integer update(String trackID, Track updateTrack){
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());
        Optional<Track> oldTrackOptional = trackList.stream()
                .filter(track -> track.getId().equals(trackID)).findFirst();
        if(!oldTrackOptional.isPresent()){
            return -1;
        }
        else{
            Track oldTrack = oldTrackOptional.get();
            trackList.remove(oldTrack);

            Track newTrack = oldTrack.toBuilder()
                    .album(updateTrack.getAlbum())
                    .title(updateTrack.getTitle())
                    .artist(updateTrack.getArtist())
                    .genre(updateTrack.getGenre())
                    .duration(updateTrack.getDuration())
                    .updatedAt(LocalDateTime.now())
                    .build();

            //add old track after update with new information
            trackList.add(newTrack);

            return FileUtils.writeJsonFile(AppConstant.TRACK_FILE_PATH,trackList) ? 1 : 0;
        }

    }

    public Integer delete( String trackID){
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());
        Optional<Track> deleteTrackOptional = trackList.stream()
                .filter(track -> track.getId().equals(trackID)).findFirst();
        if(!deleteTrackOptional.isPresent()){
            return -1;
        }
        else{
            Track oldTrack = deleteTrackOptional.get();
            trackList.remove(oldTrack);

            return FileUtils.writeJsonFile(AppConstant.TRACK_FILE_PATH,trackList) ? 1 : 0;
        }
    }

    public List<TrackResponse> searchByTitleOrArtistOrAlbumOrGenre(String keyword){
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());

        trackList = trackList.stream()
                .filter(
                        track -> {
                            String lowerKeyword = keyword.toLowerCase();
                            return track.getTitle().toLowerCase().contains(lowerKeyword)
                                    || track.getArtist().toLowerCase().contains(lowerKeyword)
                                    || track.getAlbum().toLowerCase().contains(lowerKeyword)
                                    || track.getGenre().toLowerCase().contains(lowerKeyword);
                        }
                )
                .collect(Collectors.toList());
        List<TrackResponse> trackResponseList = trackList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return trackResponseList;
    }


    public Track findById(String id) {
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());
        Optional<Track> trackOptional = trackList.stream()
                .filter(track -> track.getId().equals(id)).findFirst();

        return trackOptional.isPresent() ? trackOptional.get() : null;
    }

    public Track findByTitle(String title) {
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());
        Optional<Track> trackOptional = trackList.stream()
                .filter(track -> track.getTitle().equals(title)).findFirst();

        return trackOptional.isPresent() ? trackOptional.get() : null;
    }

    public List<TrackResponse> getResponseById(String id) {
        String json =  FileUtils.readJsonFile(AppConstant.TRACK_FILE_PATH);
        List<Track> trackList = new Gson().fromJson(json,new TypeToken<List<Track>>(){}.getType());

        List<TrackResponse> trackResponseList = trackList.stream()
                .filter(track -> track.getUserId().equals(id))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return trackResponseList;
    }

    public List<SearchTrackOnline> searchOnline(String trackName) {
        List<SearchTrackOnline> searchTrackOnlineList = new ArrayList<>();
        try {
            String url = "https://www.google.com/search?q="
                    + URLEncoder.encode(trackName + " youtube", StandardCharsets.UTF_8.toString());

            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div.g.dFd2Tb.PhX2wd");


            for(Element e : elements){
                String title = e.getElementsByClass("LC20lb MBeuO DKV0Md")
                        .first()
                        .text();

                String urlYoutube = e.getElementsByTag("a")
                        .first()
                        .attr("href");

                //V8fWH
                String duration = e.getElementsByClass("JIv15d")
                        .get(0)
                        .text();

                String date = e.getElementsByClass("P7xzyf")
                        .get(0)
                        .text().split("·")[2];

                searchTrackOnlineList.add(new SearchTrackOnline(
                        title,
                        urlYoutube,
                        duration,
                        date
                ));
            }
        } catch (Exception e) {
            //Handle error

        }
        return searchTrackOnlineList;
    }


    public static void main(String[] args) {
        TrackDao trackDao = new TrackDao();
        System.out.println(trackDao.searchOnline("Happy new year"));
    }
}
