package fr.eni.demoSpringFramework.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayerDTO(
        @NotBlank
        @Size(min = 2, max = 30)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 30)
        String lastName,

        @Email
        String email,

        @NotNull
        Integer teamId
) {
}
