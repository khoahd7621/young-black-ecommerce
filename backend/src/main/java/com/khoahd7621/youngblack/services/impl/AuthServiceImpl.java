package com.khoahd7621.youngblack.services.impl;

import com.khoahd7621.youngblack.constants.EAccountStatus;
import com.khoahd7621.youngblack.dtos.response.ExceptionResponse;
import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.entities.User;
import com.khoahd7621.youngblack.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.youngblack.exceptions.custom.CustomNotFoundException;
import com.khoahd7621.youngblack.dtos.request.user.UserDTOLoginRequest;
import com.khoahd7621.youngblack.dtos.response.user.UserDTOResponse;
import com.khoahd7621.youngblack.mappers.UserMapper;
import com.khoahd7621.youngblack.repositories.UserRepository;
import com.khoahd7621.youngblack.securities.CustomUserDetails;
import com.khoahd7621.youngblack.services.AuthService;
import com.khoahd7621.youngblack.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public SuccessResponse<UserDTOResponse> loginHandler(UserDTOLoginRequest userDTOLoginRequest)
            throws CustomBadRequestException {
        Optional<User> userOpt = userRepository.findByEmail(userDTOLoginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(userDTOLoginRequest.getPassword(), user.getPassword())) {
                if (user.getStatus() == EAccountStatus.ACTIVE) {
                    UserDTOResponse userDTOResponse = userMapper.toUserDTOResponse(user);
                    userDTOResponse.setAccessToken(jwtTokenUtil.generateAccessToken(user));
                    userDTOResponse.setRefreshToken(jwtTokenUtil.generateRefreshToken(user));
                    return new SuccessResponse<>(userDTOResponse, "Login successfully");
                }
                if (user.getStatus() == EAccountStatus.INACTIVE) {
                    throw new CustomBadRequestException(ExceptionResponse.builder()
                            .code(-1).message("The account has not been activated").build());
                }
                if (user.getStatus() == EAccountStatus.BLOCK) {
                    throw new CustomBadRequestException(ExceptionResponse.builder()
                            .code(-1).message("Account has been blocked").build());
                }
            }
        }
        throw new CustomBadRequestException(ExceptionResponse.builder()
                .code(-1).message("Email or password is incorrect").build());
    }

    @Override
    public User getUserLoggedIn() throws CustomNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }
        throw new CustomNotFoundException(ExceptionResponse.builder()
                .code(-1).message("User does not exist").build());
    }

}
