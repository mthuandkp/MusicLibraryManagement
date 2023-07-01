package org.example.Dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrackResponse {
    private String id;
    private String username;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer releaseYear;
    private Double duration; // minutes
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
