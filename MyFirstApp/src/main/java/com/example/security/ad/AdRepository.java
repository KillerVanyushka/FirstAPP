package com.example.security.ad;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByPriceLessThan(double price);
    List<Ad> findByPriceGreaterThan(double price);
    List<Ad> findByPrice(double price);



}

