package ru.practicum.ewmmain.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    @NotBlank
    private final String name;
    @NotBlank
    @Email
    private final String email;
}