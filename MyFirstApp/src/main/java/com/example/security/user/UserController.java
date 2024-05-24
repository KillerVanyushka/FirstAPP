package com.example.security.user;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor

public class UserController {
    @Autowired
    private final UserService userService;



    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{Firstname}")
    public ResponseEntity<User> getUserByFirstname(@PathVariable String Firstname) {
        Optional<User> user=userService.findByFirstname(Firstname);
        return user.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userService.findById(id);
        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = existingUserOptional.get();
        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setLastname(updatedUser.getLastname());
        existingUser.setEmail(updatedUser.getEmail());


        User savedUser = userService.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(id, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok("Пароль успешно изменен.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sorted/asc")
    public ResponseEntity<List<User>> getUsersSortedByFirstnameAsc() {
        List<User> users = userService.findAllSortedByFirstnameAsc();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sorted/desc")
    public ResponseEntity<List<User>> getUsersSortedByFirstnameDesc() {
        List<User> users = userService.findAllSortedByFirstnameDesc();
        return ResponseEntity.ok(users);
    }

}
