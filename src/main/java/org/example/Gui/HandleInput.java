package org.example.Gui;

import org.example.Constant.AppConstant;
import org.example.Dao.PlaylistDao;
import org.example.Dao.TrackDao;
import org.example.Dto.Track;
import org.example.Utils.DateUtils;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HandleInput {
    private TrackDao trackDao = new TrackDao();
    private PlaylistDao playlistDao = new PlaylistDao();

    private void printError(String err) {
        System.out.printf("!!! %s !!!\n", err);
    }

    public Integer getUserSelect(Integer minSelect, Integer maxSelect) {
        Scanner scanner = new Scanner(System.in);
        Integer userSelection = -1;
        //Loop until user input correct selection
        while (true) {
            try {
                System.out.print("Please input your selection: ");
                userSelection = scanner.nextInt();

                if (userSelection < minSelect || userSelection > maxSelect) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                scanner.nextLine();
                printError(AppConstant.USER_SELECTION_INVALID);
            }
        }

        return userSelection;
    }

    public List<String> login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(" Please input username: ");
        String username = scanner.nextLine();
        System.out.print(" Please input password: ");
        String password = scanner.nextLine();

        return Arrays.asList(username, password);
    }

    public String viewDetailByTitleOrArtist() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input title or artist: ");
        String keyword = scanner.nextLine();
        return keyword;
    }

    public String searchByTitleOrArtistOrAlbumOrGenre() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input title or artist or album or genre: ");
        String keyword = scanner.nextLine();
        return keyword;
    }

    public String searchPlaylistByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input title of playlist: ");
        String keyword = scanner.nextLine();
        return keyword;
    }

    public Track addNewTrack() {
        Scanner scanner = new Scanner(System.in);
        String title = "";
        String artist = "";
        String album = "";
        String genre = "";
        Integer releaseYear;
        Double duration;


        while (true) {
            System.out.print("Please input title of track: ");
            title = scanner.nextLine();

            if (title.equals("")) {
                System.out.println("Track title can't empty");
            } else if (trackDao.findByTitle(title) != null) {
                System.out.printf("Track with title %s is already exists. Please input another track name\n", title);
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Please input artist of track: ");
            artist = scanner.nextLine();

            if (artist.equals("")) {
                System.out.println("Artist can't empty");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Please input album of track: ");
            album = scanner.nextLine();

            if (album.equals("")) {
                System.out.println("Album can't empty");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Please input genre of track: ");
            genre = scanner.nextLine();

            if (genre.equals("")) {
                System.out.println("Genre can't empty");
            } else {
                break;
            }
        }

        while (true) {
            try {
                System.out.print("Please input release year of track: ");
                releaseYear = scanner.nextInt();

                if (releaseYear < 1500 || releaseYear > Year.now().getValue()) {
                    System.out.printf("Release year must be in range [1500;%d]\n", Year.now().getValue());
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Release year invalid: " + e.getMessage());
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Please input duration of track: ");
                duration = scanner.nextDouble();

                if (duration < 0) {
                    System.out.println("Duration must be larger than 0");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Duration invalid: " + e.getMessage());
                scanner.nextLine();
            }
        }

        Track newTrack = Track.builder()
                .id(DateUtils.getEpoch())
                .title(title)
                .artist(artist)
                .genre(genre)
                .album(album)
                .releaseYear(releaseYear)
                .duration(duration)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return newTrack;


    }

    public Integer selectExistTrack(Integer min, Integer max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Please input number of track: ");
                Integer select = scanner.nextInt();

                if (select < min || select > max) {
                    System.out.printf("Select must be in range [%d;%d]\n", min, max);
                    continue;
                }

                return select;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Your select invalid");
            }
        }
    }

    public Track updateExistTrack(Track track) {
        Scanner scanner = new Scanner(System.in);

        String title = "";
        String artist = "";
        String album = "";
        String genre = "";
        Integer releaseYear;
        Double duration;


        while (true) {
            System.out.print("Please input title of track (Leave blank to keep original): ");
            title = scanner.nextLine();

            if (title.equals("")) {
                break;
            } else if (trackDao.findByTitle(title) != null) {
                System.out.printf("Track with title %s is already exists. Please input another track name\n", title);
            } else {
                track.setTitle(title);
                break;
            }
        }

        while (true) {
            System.out.print("Please input artist of track (Leave blank to keep original): ");
            artist = scanner.nextLine();

            if (artist.equals("")) {
                break;
            }
            track.setArtist(artist);
            break;
        }

        while (true) {
            System.out.print("Please input album of track (Leave blank to keep original): ");
            album = scanner.nextLine();

            if (album.equals("")) {
                break;
            }
            track.setAlbum(album);
            break;
        }

        while (true) {
            System.out.print("Please input genre of track (Leave blank to keep original): ");
            genre = scanner.nextLine();

            if (genre.equals("")) {
                break;
            }

            track.setGenre(genre);
            break;

        }

        while (true) {
            try {
                System.out.print("Please input release year of track(Input 0 to keep original): ");
                releaseYear = scanner.nextInt();

                if(releaseYear == 0){
                    break;
                }
                else if (releaseYear < 1500 || releaseYear > Year.now().getValue()) {
                    System.out.printf("Release year must be in range [1500;%d]\n", Year.now().getValue());
                } else {
                    track.setReleaseYear(releaseYear);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Release year invalid: " + e.getMessage());
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Please input duration of track (Input 0 to keep original): ");
                duration = scanner.nextDouble();

                if(duration == 0){
                    break;
                }
                else if (duration < 0) {
                    System.out.println("Duration must be larger than 0");
                } else {
                    track.setDuration(duration);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Duration invalid: " + e.getMessage());
                scanner.nextLine();
            }
        }


        track.setUpdatedAt(LocalDateTime.now());
        return track;
    }

    public String createNewPlaylist() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("Please input title of playlist: ");
            String title = sc.nextLine();

            if(title.equals("")){
                System.out.println("Title can't be empty");
            }
            else if(playlistDao.findByExactlyTitle(title) != null){
                System.out.printf("Title '%s' is already used by another playlist\n",title);
            }
            else{
                return title;
            }
        }
    }

    public Integer selectExistPlaylist(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Please input number of playlist: ");
                Integer select = scanner.nextInt();

                if (select < min || select > max) {
                    System.out.printf("Select must be in range [%d;%d]\n", min, max);
                    continue;
                }

                return select;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Your select invalid");
            }
        }
    }

    public Integer searchInYotube(Integer min, Integer max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Please input number of track: ");
                Integer select = scanner.nextInt();

                if (select < min || select > max) {
                    System.out.printf("Select must be in range [%d;%d]\n", min, max);
                    continue;
                }

                return select;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Your select invalid");
            }
        }
    }
}
