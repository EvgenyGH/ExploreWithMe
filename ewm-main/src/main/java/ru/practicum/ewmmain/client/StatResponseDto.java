package ru.practicum.ewmmain.client;

import lombok.*;

@Data
@AllArgsConstructor
public class StatResponseDto {
    private String app;

    private String uri;

    private Long hits;
}