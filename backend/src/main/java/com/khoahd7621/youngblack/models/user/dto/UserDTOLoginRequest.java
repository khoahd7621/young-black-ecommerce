package com.khoahd7621.youngblack.models.user.dto;

import com.khoahd7621.youngblack.constants.Constants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDTOLoginRequest {
    @NotBlank(message = "Email is required")
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = Constants.MIN_LENGTH_PASSWORD, max = Constants.MAX_LENGTH_PASSWORD, message = "The password must contain at least 6 characters and be up to 24 characters")
    private String password;
}
