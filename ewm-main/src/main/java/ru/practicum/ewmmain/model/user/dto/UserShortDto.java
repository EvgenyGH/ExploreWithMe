package ru.practicum.ewmmain.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Dto пользователя {@link ru.practicum.ewmmain.model.user.User}.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.user.dto.UserNewDto
 * @see ru.practicum.ewmmain.model.user.dto.UserDto
 * @see ru.practicum.ewmmain.model.user.User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    /**
     * id пользователя.
     */
    private Integer id;

    /**
     * Имя пользователя.
     */
    @NotBlank
    private String name;
}