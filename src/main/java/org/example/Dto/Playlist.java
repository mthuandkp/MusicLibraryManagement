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
public class Playlist {
    private String id;
    private String userId;
    private String title;
    private List<String> trackLists;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
