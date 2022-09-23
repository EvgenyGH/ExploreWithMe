package ru.practicum.ewmmain.user.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoShort implements Serializable {
    private Integer id;
    @NotBlank
    private String name;
}