package ru.practicum.ewmmain.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Dto пользователя {@link ru.practicum.ewmmain.model.user.User}.
 * @author Evgeny S
 * @see UserDto
 * @see ru.practicum.ewmmain.model.user.dto.UserShortDto
 * @see ru.practicum.ewmmain.model.user.User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNewDto {
    /**
     * Имя пользователя.
     */
    @NotBlank
    private String name;

    /**
     * Адрес электронной почты пользователя.
     */
    @NotBlank
    @Email
    private String email;
}
