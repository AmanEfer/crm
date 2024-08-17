package com.amanefer.crm.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotNull(message = "Name can't be null")
    @Size(min = 2, max = 20, message = "Name should contain from 3 to 20 symbols")
    private String name;

    @NotNull(message = "Password can't be null")
    @Size(min = 2, max = 20, message = "Password should contain from 3 to 20 symbols")
    private String password;

    @NotNull(message = "Email can't be null")
    @Email(message = "Enter the correct email")
    @Size(min = 2, max = 20, message = "Email should contain from 3 to 20 symbols")
    private String email;
}
