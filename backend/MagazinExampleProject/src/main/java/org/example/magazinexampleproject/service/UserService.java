package org.example.magazinexampleproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.magazinexampleproject.dto.user.UserDto;
import org.example.magazinexampleproject.models.carts.Cart;
import org.example.magazinexampleproject.models.user.User;
import org.example.magazinexampleproject.repositories.cart.CartRepository;
import org.example.magazinexampleproject.repositories.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    // Поиск по почте
    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User this email " + email + " not found")
        );
        return toDto(user);
    }

    //Регистрация нового пользователя
    @Transactional
    public String addUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + userDto.getEmail() + " already exists");
        }
        User entity = toUser(userDto);
        if (entity == null) {
            throw new RuntimeException("Mapped entity is null");
        }
        Cart cart = new Cart();
        cart.setUser(entity);
        cart.setTotalPrice(0);
        Cart save = cartRepository.save(cart);
        entity.setCart(cart);
        entity.setRoles(Set.of("USER"));
        entity.setPassword(passwordEncoder.encode(entity.getPassword())); // Хеширование пароля
        userRepository.save(entity);
        return "User successfully added";
    }

    //Удаления пользователя по ID
    @Transactional
    public String deleteUserById(Long id){
        userRepository.deleteById(id);
        return "User successfully deleted";
    } //TODO доделать нормальный метод



    private User toUser(UserDto userDto){
        User user = new User();
        user.setRoles(userDto.getRoles());
        user.setId(userDto.getId());
        user.setCart(userDto.getCart());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return  user;
    }

    private UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setRoles(user.getRoles());
        userDto.setId(user.getId());
        userDto.setCart(user.getCart());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
