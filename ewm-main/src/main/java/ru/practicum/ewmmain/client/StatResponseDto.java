package ru.practicum.ewmmain.client;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatResponseDto {
    private String app;

    private String uri;

    private Integer hits;
}