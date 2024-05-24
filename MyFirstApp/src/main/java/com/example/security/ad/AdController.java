package com.example.security.ad;

import com.example.security.user.User;
import com.example.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdService adService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads = adService.findAll();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id) {
        Optional<Ad> ad = adService.findById(id);
        return ad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping("/{userId}")
    public ResponseEntity<Ad> createAd(@PathVariable Long userId, @RequestBody Ad ad) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        ad.setUser(user);

        Ad createdAd = adService.save(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdById(@PathVariable Long id) {
        Optional<Ad> ad = adService.findById(id);
        if (ad.isPresent()) {
            adService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable Long id, @RequestBody Ad updatedAd) {
        Optional<Ad> existingAdOptional = adService.findById(id);
        if (existingAdOptional.isPresent()) {
            Ad existingAd = existingAdOptional.get();
            existingAd.setTitle(updatedAd.getTitle());
            existingAd.setDescription(updatedAd.getDescription());

            Ad savedAd = adService.save(existingAd);
            return ResponseEntity.ok(savedAd);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sorted/asc")
    public ResponseEntity<List<Ad>> getAdsSortedByTitleAsc() {
        List<Ad> ads = adService.findAllSortedByTitleAsc();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/sorted/desc")
    public ResponseEntity<List<Ad>> getAdsSortedByTitleDesc() {
        List<Ad> ads = adService.findAllSortedByTitleDesc();
        return ResponseEntity.ok(ads);
    }

    @PostMapping("/cheaper")
    public ResponseEntity<List<Ad>> getAdsCheaperThan(@RequestBody PriceFilterDTO filter) {
        List<Ad> ads = adService.findAdsCheaperThan(filter.getPrice());
        return ResponseEntity.ok(ads);
    }

    @PostMapping("/expensive")
    public ResponseEntity<List<Ad>> getAdsMoreExpensiveThan(@RequestBody PriceFilterDTO filter) {
        List<Ad> ads = adService.findAdsMoreExpensiveThan(filter.getPrice());
        return ResponseEntity.ok(ads);
    }

    @PostMapping("/price-exact")
    public List<Ad> getAdsByExactPrice(@RequestBody PriceFilterDTO priceFilterDTO) {
        return adService.getAdsByExactPrice(priceFilterDTO.getPrice());
    }




}

