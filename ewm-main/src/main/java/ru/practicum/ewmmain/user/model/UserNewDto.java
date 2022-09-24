package ru.practicum.ewmmain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNewDto {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
