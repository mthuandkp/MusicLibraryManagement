package org.example.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FavoriteTrack {
    private String useId;
    private List<String> trackList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
