package org.example.magazinexampleproject.controllers;

import lombok.RequiredArgsConstructor;
import org.example.magazinexampleproject.dto.user.UserDto;
import org.example.magazinexampleproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public UserDto getCurrentUser() {
        // Получаем текущую аутентификацию из SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Получаем email (или имя пользователя) из Authentication
        String email = authentication.getName();

        // Используем UserService для поиска пользователя по email
        UserDto currentUser = userService.findByEmail(email);

        // Возвращаем информацию о текущем пользователе
        return currentUser;
    }

}
