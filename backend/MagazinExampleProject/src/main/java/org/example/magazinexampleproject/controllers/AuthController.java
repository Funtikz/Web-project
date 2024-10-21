package org.example.magazinexampleproject.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.dto.auth.UserCredentialDto;
import org.example.magazinexampleproject.models.user.User;
import org.example.magazinexampleproject.repositories.user.UserRepository;
import org.example.magazinexampleproject.security.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentialDto userCredentialDto ,  HttpServletResponse response) {
        // 1. Ищем пользователя по email
        String email = userCredentialDto.getEmail();
        String password = userCredentialDto.getPassword();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // 3. Генерируем JWT с ролями
                String jwtToken = jwtService.generateJwtToken(user.getEmail(), user.getRoles());

                // 4. Создаем httpOnly cookie
                Cookie jwtCookie = new Cookie("__Host-auth-token", jwtToken);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(true); // Для работы через HTTPS, можешь отключить в dev-окружении
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(180 * 24 * 60 * 60); // Время жизни куки - 180 дней

                // 5. Добавляем куки в response
                response.addCookie(jwtCookie);

                return ResponseEntity.ok("Login successful");
            }
        }

        // 6. Если аутентификация неуспешна, возвращаем ошибку
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}
