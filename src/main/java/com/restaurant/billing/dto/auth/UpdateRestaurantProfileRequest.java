package com.restaurant.billing.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantProfileRequest {
    @JsonProperty("name")
    private String restaurantName;

    @JsonProperty("email")
    private String ownerEmail;

    private String ownerName;

    @JsonProperty("phone")
    private String ownerPhone;

    private String address;
    private String city;
    private String state;
    private String pincode;
    private String gstin;
    private String currency;
    private String timezone;
    private BigDecimal taxRate;
    private BigDecimal serviceChargeRate;
}
