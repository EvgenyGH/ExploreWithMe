package ru.practicum.ewmmain.model.event.category.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dto категорий события.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.category.Category
 * @see ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto
 * @see ru.practicum.ewmmain.model.event.Event
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    /**
     * id категории.
     */
    @NotNull
    @Min(0)
    private Integer id;

    /**
     * Название категории.
     */
    @NotBlank
    private String name;
}
