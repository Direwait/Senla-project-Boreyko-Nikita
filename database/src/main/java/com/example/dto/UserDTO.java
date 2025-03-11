package com.example.dto;

import com.example.Role;
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
    private String userUsername;
    private String userPassword;
    private String userEmail;
    private String userDescription;
    private Role role;
    private Date userRegistrationDate;
}
