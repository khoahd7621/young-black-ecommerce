package com.khoahd7621.youngblack.models.user.dto;

import com.khoahd7621.youngblack.constants.Constants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDTOChangePasswordRequest {
    @NotBlank(message = "Old password is required")
    @Size(min = Constants.MIN_LENGTH_PASSWORD, max = Constants.MAX_LENGTH_PASSWORD, message = "The old password must contain at least 6 characters and be up to 24 characters")
    private String oldPassword;
    @NotBlank(message = "New password is required")
    @Size(min = Constants.MIN_LENGTH_PASSWORD, max = Constants.MAX_LENGTH_PASSWORD, message = "The new password must contain at least 6 characters and be up to 24 characters")
    private String newPassword;
    @NotBlank(message = "Confirm password is required")
    @Size(min = Constants.MIN_LENGTH_PASSWORD, max = Constants.MAX_LENGTH_PASSWORD, message = "The confirm password must contain at least 6 characters and be up to 24 characters")
    private String confirmPassword;
}