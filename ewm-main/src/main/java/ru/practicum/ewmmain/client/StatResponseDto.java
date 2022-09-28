package ru.practicum.ewmmain.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatResponseDto {
    private String app;

    private String uri;

    private Integer hits;
}