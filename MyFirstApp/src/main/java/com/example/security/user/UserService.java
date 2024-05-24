package com.example.security.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public Optional<User> findById(Long id) {
//        return userRepository.findById(id);
//    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findByFirstname(String Firstname) {
        return userRepository.findByFirstname(Firstname);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Пользователь с указанным ID не найден."));


        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("Старый пароль неверен.");
        }

        String newPasswordHash = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordHash);
        userRepository.save(user);
    }

    public List<User> findAllSortedByFirstnameAsc() {
        return findAll().stream()
                .sorted(Comparator.comparing(User::getFirstname))
                .collect(Collectors.toList());
    }

    public List<User> findAllSortedByFirstnameDesc() {
        return findAll().stream()
                .sorted(Comparator.comparing(User::getFirstname).reversed())
                .collect(Collectors.toList());
    }
}
