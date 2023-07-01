package org.example.Gui;


import org.example.Constant.AppConstant;
import org.example.Dto.Track;
import org.example.Dto.User;

public class HandleEvent {
    private HandleInput handleInput;
    private User userSession;
    private UI ui;

    public HandleEvent() {
        handleInput = new HandleInput();
    }

    public HandleEvent(User u) {
        handleInput = new HandleInput();
        this.userSession = u;
        ui = new UI(u);
    }

    public void handleMainUi() {
        Integer MIN_SELECT = 0;
        Integer MAX_SELECT = 12;
        Integer userSelection = handleInput.getUserSelect(MIN_SELECT, MAX_SELECT);

        //Implement the func corresponding to the user's selection
        switch (userSelection) {
            case 0: {
                System.exit(AppConstant.EXIT_CODE);
            }
            case 1: {
                ui.displayAllTrack();
                break;
            }
            case 2: {
                String keyword = handleInput.viewDetailByTitleOrArtist();
                ui.viewDetailByTitleOrArtist(keyword);
                break;
            }
            case 3: {
                String keyword = handleInput.searchByTitleOrArtistOrAlbumOrGenre();
                ui.searchByTitleOrArtistOrAlbumOrGenre(keyword);
                break;
            }
            case 4: {
                String keyword = handleInput.searchPlaylistByTitle();
                ui.searchPlaylistByTitle(keyword);
                break;
            }
            case 5: {
                Track newTrack = handleInput.addNewTrack();
                ui.addNewTrack(newTrack);
                break;
            }
            case 6: {
                ui.UpdateExistTrack();
                break;
            }
            case 7: {
                ui.deleteTrack();
                break;
            }
            case 8: {
                String title = handleInput.createNewPlaylist();
                ui.createNewPlaylist(title);
                break;
            }
            case 9: {
                ui.deletePlaylist();
                break;
            }
            case 10: {
                ui.addTrackToPlaylist();
                break;
            }
            case 11: {
                ui.viewAllPlaylist();
                break;
            }
            case 12:{
                ui.searchOnline();
                break;
            }
        }

    }
}
