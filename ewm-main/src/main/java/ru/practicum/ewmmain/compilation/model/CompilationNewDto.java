package ru.practicum.ewmmain.compilation.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationNewDto implements Serializable {
    private List<Integer> events;

    private Boolean pinned;

    @NotNull
    @Size(max = 100)
    private String title;
}