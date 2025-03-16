package com.example.dto;

import com.example.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {
    private int userId;

    @NotBlank(message = "UserUsername cannot be empty")
    @Size(min = 4, max = 20, message = "UserUsername must be between 4 and 20 characters")
    private String userUsername;

    @NotBlank(message = "UserPassword cannot be empty")
    @Size(min = 4, max = 20, message = "UserPassword must be between 4 and 20 characters")
    private String userPassword;

    @NotBlank(message = "UserPassword cannot be empty")
    @Size(min = 4, max = 20, message = "UserEmail must be between 4 and 20 characters")
    private String userEmail;

    @NotBlank(message = "UserDescription cannot be empty")
    @Size(min = 4, max = 128, message = "UserDescription must be between 4 and 20 characters")
    private String userDescription;

    private Role role;
    private Date userRegistrationDate;
}
