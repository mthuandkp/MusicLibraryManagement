package org.example.Constant;

import java.util.Arrays;
import java.util.List;

public class AppFunctionality {
    public final static List<String> MAIN_FUNC = Arrays.asList(
            "1. Display all tracks",
            "2. View detail by title or artist",
            "3. Search track by title, artist, album or genre",
            "4. Search playlist by title"
    );

    public final static List<String> EXIST_FUNC = Arrays.asList(
            "0. Exit"
    );


    public static final List<String> TRACK_FUNC = Arrays.asList(
        "5. Add new track",
        "6. Update exist track",
        "7. Delete track"
    );

    public static final List<String> PLAYLIST_FUNC = Arrays.asList(
            "8. Create new playlist",
            "9. Delete playlist",
            "10. Add track to playlist",
            "11. View playlist",
            "12. Search track online"
    );
}
