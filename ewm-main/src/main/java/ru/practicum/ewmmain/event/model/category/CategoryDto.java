package ru.practicum.ewmmain.event.model.category;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
        @NotNull
        @Min(0)
        private Integer id;
        @NotBlank
        private String name;
}
