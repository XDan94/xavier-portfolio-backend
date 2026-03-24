package fr.xavier.portfolio.api.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(

        @NotBlank(message = "Le nom est requis")
        String name,

        @NotBlank(message = "L'email est requis")
        @Email(message = "Format email invalide")
        String email,

        @NotBlank(message = "Le sujet est requis")
        String subject,

        @NotBlank(message = "Le message est requis")
        @Size(min = 10, message = "Le message doit contenir au moins 10 caractères")
        String message

) {}