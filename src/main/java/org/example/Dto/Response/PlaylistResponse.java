package org.example.Dto.Response;

import org.example.Dto.Track;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PlaylistResponse {
    private String id;
    private String username;
    private String title;
    private List<Track> trackLists;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
