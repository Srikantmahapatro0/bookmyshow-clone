package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.dtos.SignUpRequestDto;
import com.example.bookmyshow.dtos.SignUpResponseDto;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = new SignUpResponseDto();
        User user = null;

        try {
            user = userService.signUp(
                    requestDto.getEmail(),
                    requestDto.getPassword()
            );

            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setUserId(user.getId());
        } catch (Exception ex) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}