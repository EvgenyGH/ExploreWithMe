package ru.practicum.ewmmain.compilation.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationNewDto implements Serializable {
    @JsonSetter(nulls = Nulls.SKIP)
    private List<Integer> events = new LinkedList<>();

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean pinned = false;

    @NotNull
    @Size(max = 100)
    private String title;
}