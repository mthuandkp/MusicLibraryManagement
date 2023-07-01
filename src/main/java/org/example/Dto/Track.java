package org.example.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Track {
    private String id;
    private String userId;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer releaseYear;
    private Double duration; // minutes
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
