package ru.practicum.ewmmain.model.compilation.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Dto объекта Compilation
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.compilation.Compilation
 * @see CompilationDto
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationNewDto implements Serializable {
    /**
     * События.
     */
    @JsonSetter(nulls = Nulls.SKIP)
    private List<Integer> events = new LinkedList<>();

    /**
     * Закрепление события на главной странице.
     */
    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean pinned = false;

    /**
     * Заголовок.
     */
    @NotNull
    @Size(max = 100)
    private String title;
}