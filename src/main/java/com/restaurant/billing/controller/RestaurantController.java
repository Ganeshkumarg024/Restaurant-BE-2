package com.restaurant.billing.controller;

import com.restaurant.billing.dto.auth.TenantDto;
import com.restaurant.billing.dto.auth.UpdateRestaurantProfileRequest;
import com.restaurant.billing.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/profile")
    public ResponseEntity<TenantDto> getRestaurantProfile() {
        return ResponseEntity.ok(restaurantService.getRestaurantProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<TenantDto> updateRestaurantProfile(@RequestBody UpdateRestaurantProfileRequest request) {
        return ResponseEntity.ok(restaurantService.updateRestaurantProfile(request));
    }

    @PostMapping(value = "/logo", consumes = "multipart/form-data")
    public ResponseEntity<TenantDto> uploadLogo(@RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        MultipartFile uploadFile = logo != null ? logo : (file != null ? file : image);
        if (uploadFile == null || uploadFile.isEmpty()) {
            throw new IllegalArgumentException(
                    "No file provided. Please upload a file with parameter name 'logo', 'file', or 'image'");
        }
        return ResponseEntity.ok(restaurantService.uploadLogo(uploadFile));
    }
}
