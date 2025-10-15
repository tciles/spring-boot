package fr.eni.demoSpringFramework.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamDTO(
        @NotBlank
        @Size(min = 3, max = 50)
        String name
) {
}
