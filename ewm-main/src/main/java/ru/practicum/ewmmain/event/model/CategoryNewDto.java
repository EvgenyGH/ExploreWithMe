package ru.practicum.ewmmain.event.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryNewDto {
    @NotBlank
    private String name;
}
