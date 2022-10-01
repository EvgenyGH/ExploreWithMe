package ru.practicum.ewmmain.model.event.category.dto;

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
