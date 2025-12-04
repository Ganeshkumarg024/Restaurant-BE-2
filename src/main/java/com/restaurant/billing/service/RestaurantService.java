package com.restaurant.billing.service;

import com.restaurant.billing.dto.auth.TenantDto;
import com.restaurant.billing.entity.Tenant;
import com.restaurant.billing.exception.ResourceNotFoundException;
import com.restaurant.billing.repository.TenantRepository;
import com.restaurant.billing.security.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final TenantRepository tenantRepository;
    private final FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public TenantDto getRestaurantProfile() {
        UUID tenantId = TenantContext.getTenantId();
        log.info("Fetching restaurant profile for tenant: {}", tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        return TenantDto.fromEntity(tenant);
    }

    @Transactional
    public TenantDto updateRestaurantProfile(com.restaurant.billing.dto.auth.UpdateRestaurantProfileRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        log.info("Updating restaurant profile for tenant: {}", tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        // Update only the fields that are provided
        if (request.getRestaurantName() != null) {
            tenant.setRestaurantName(request.getRestaurantName());
        }
        if (request.getOwnerEmail() != null) {
            tenant.setOwnerEmail(request.getOwnerEmail());
        }
        if (request.getOwnerName() != null) {
            tenant.setOwnerName(request.getOwnerName());
        }
        if (request.getOwnerPhone() != null) {

            tenant.setOwnerPhone(request.getOwnerPhone());
        }
        if (request.getAddress() != null) {
            tenant.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            tenant.setCity(request.getCity());
        }
        if (request.getState() != null) {
            tenant.setState(request.getState());
        }
        if (request.getPincode() != null) {
            tenant.setPincode(request.getPincode());
        }
        if (request.getGstin() != null) {
            tenant.setGstin(request.getGstin());
        }
        if (request.getCurrency() != null) {
            tenant.setCurrency(request.getCurrency());
        }
        if (request.getTimezone() != null) {
            tenant.setTimezone(request.getTimezone());
        }
        if (request.getTaxRate() != null) {
            tenant.setTaxRate(request.getTaxRate());
        }
        if (request.getServiceChargeRate() != null) {
            tenant.setServiceChargeRate(request.getServiceChargeRate());
        }

        tenant = tenantRepository.save(tenant);
        log.info("Restaurant profile updated successfully for tenant: {}", tenantId);

        return TenantDto.fromEntity(tenant);
    }

    @Transactional
    public TenantDto uploadLogo(MultipartFile file) throws IOException {
        UUID tenantId = TenantContext.getTenantId();
        log.info("Uploading logo for tenant: {}", tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        // Delete old logo if exists
        if (tenant.getLogoPath() != null) {
            fileStorageService.deleteFile(tenant.getLogoPath());
        }

        // Store new logo
        String logoPath = fileStorageService.storeLogo(file);
        tenant.setLogoPath(logoPath);

        tenant = tenantRepository.save(tenant);
        log.info("Logo uploaded successfully for tenant: {}", tenantId);

        return TenantDto.fromEntity(tenant);
    }
}
