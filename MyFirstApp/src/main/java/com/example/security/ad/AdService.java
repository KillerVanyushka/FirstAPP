package com.example.security.ad;

import com.example.security.user.User;
import com.example.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    public Optional<Ad> findById(Long id) {
        return adRepository.findById(id);
    }



    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }


    public Optional<User> findByFirstname(String username) {
        return userRepository.findByFirstname(username);
    }


    public Ad updateAd(Ad existingAd, Ad updatedAd) {
        existingAd.setTitle(updatedAd.getTitle());
        existingAd.setDescription(updatedAd.getDescription());
        return adRepository.save(existingAd);
    }

    public void deleteById(Long id) {
        adRepository.deleteById(id);
    }

    public List<Ad> findAllSortedByTitleAsc() {
        return findAll().stream()
                .sorted(Comparator.comparing(Ad::getTitle))
                .collect(Collectors.toList());
    }

    public List<Ad> findAllSortedByTitleDesc() {
        return findAll().stream()
                .sorted(Comparator.comparing(Ad::getTitle).reversed())
                .collect(Collectors.toList());
    }

    public List<Ad> findAdsCheaperThan(double price) {
        return adRepository.findByPriceLessThan(price);
    }

    public List<Ad> findAdsMoreExpensiveThan(double price) {
        return adRepository.findByPriceGreaterThan(price);
    }

    public List<Ad> getAdsByExactPrice(double price) {
        return adRepository.findByPrice(price);
    }

}



