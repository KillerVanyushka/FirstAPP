package com.example.security.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    Object findAll(Pageable pageable);

    Optional<User> findById(Long id);

    Optional<User> findByFirstname(String firstname);

    void deleteById(Long id);

}
