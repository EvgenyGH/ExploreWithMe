package ru.practicum.ewmmain.model.event.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * Dto категорий события.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.category.dto.CategoryDto
 * @see ru.practicum.ewmmain.model.event.category.Category
 * @see ru.practicum.ewmmain.model.event.Event
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryNewDto {
    /**
     * Название категории.
     */
    @NotBlank
    private String name;
}
