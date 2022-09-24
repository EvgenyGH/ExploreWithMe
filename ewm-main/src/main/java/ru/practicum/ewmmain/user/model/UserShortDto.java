package ru.practicum.ewmmain.user.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    private Integer id;
    @NotBlank
    private String name;
}