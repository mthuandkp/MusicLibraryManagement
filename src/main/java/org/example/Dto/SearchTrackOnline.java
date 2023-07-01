package org.example.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SearchTrackOnline {
    private String title;
    private String url;
    private String duration;
    private String date;
}
