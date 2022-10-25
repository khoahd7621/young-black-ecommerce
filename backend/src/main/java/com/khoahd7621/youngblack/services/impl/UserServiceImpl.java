package com.khoahd7621.youngblack.services.impl;

import java.util.Optional;

import com.khoahd7621.youngblack.dtos.response.ExceptionResponse;
import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.dtos.response.NoData;
import com.khoahd7621.youngblack.exceptions.custom.CustomNotFoundException;
import com.khoahd7621.youngblack.dtos.request.user.UserDTOChangePasswordRequest;
import com.khoahd7621.youngblack.dtos.response.user.UserDTOResponse;
import com.khoahd7621.youngblack.dtos.request.user.UserDTOUpdateRequest;
import com.khoahd7621.youngblack.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.khoahd7621.youngblack.entities.User;
import com.khoahd7621.youngblack.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.youngblack.dtos.request.user.UserDTORegisterRequest;
import com.khoahd7621.youngblack.mappers.UserMapper;
import com.khoahd7621.youngblack.repositories.UserRepository;
import com.khoahd7621.youngblack.services.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SuccessResponse<NoData> userRegister(UserDTORegisterRequest userDTORegisterRequest)
            throws CustomBadRequestException {
        Optional<User> userOpt = userRepository.findByEmail(userDTORegisterRequest.getEmail());
        if (userOpt.isPresent()) {
            throw new CustomBadRequestException(ExceptionResponse.builder()
                    .code(-1).message("This email already exists").build());
        }
        userOpt = userRepository.findByPhone(userDTORegisterRequest.getPhone());
        if (userOpt.isPresent()) {
            throw new CustomBadRequestException(ExceptionResponse.builder()
                    .code(-1).message("This phone number already exists").build());
        }
        User user = userMapper.toUser(userDTORegisterRequest);
        userRepository.save(user);
        return new SuccessResponse<>(NoData.builder().build(), "Register successfully");
    }

    @Override
    public SuccessResponse<UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        User user = authService.getUserLoggedIn();
        return new SuccessResponse<>(userMapper.toUserDTOResponse(user),
                "Get current user's information successfully");
    }

    @Override
    public SuccessResponse<UserDTOResponse> updateUser(UserDTOUpdateRequest userDTOUpdateRequest)
            throws CustomBadRequestException, CustomNotFoundException {
        User user = authService.getUserLoggedIn();
        Optional<User> userOptionalWithPhone = userRepository.findByPhone(userDTOUpdateRequest.getPhone());
        if (userOptionalWithPhone.isPresent() && userOptionalWithPhone.get().getId() != user.getId()) {
            throw new CustomBadRequestException(ExceptionResponse.builder()
                    .code(-1).message("Phone already existed").build());
        }
        user.setFirstName(userDTOUpdateRequest.getFirstName());
        user.setLastName(userDTOUpdateRequest.getLastName());
        user.setPhone(userDTOUpdateRequest.getPhone());
        user.setAddress(userDTOUpdateRequest.getAddress());
        userRepository.save(user);
        return new SuccessResponse<>(userMapper.toUserDTOResponse(user), "Update successfully");
    }

    @Override
    public SuccessResponse<NoData> changePassword(UserDTOChangePasswordRequest userDTOChangePasswordRequest)
            throws CustomBadRequestException, CustomNotFoundException {
        if (!userDTOChangePasswordRequest.getNewPassword().equals(userDTOChangePasswordRequest.getConfirmPassword())) {
            throw new CustomBadRequestException(ExceptionResponse.builder()
                    .code(-1).message("Confirm password not match new password").build());
        }
        User user = authService.getUserLoggedIn();
        if (!passwordEncoder.matches(userDTOChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new CustomBadRequestException(ExceptionResponse.builder()
                    .code(-1).message("Old password is invalid").build());
        }
        if (passwordEncoder.matches(userDTOChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new CustomBadRequestException(ExceptionResponse.builder()
                    .code(-1).message("New password is the same with old password! Nothing change").build());
        }
        user.setPassword(passwordEncoder.encode(userDTOChangePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return new SuccessResponse<>(NoData.builder().build(), "Change password successfully");
    }

}
