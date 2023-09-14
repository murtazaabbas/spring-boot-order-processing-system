package com.melitaltd.model;

import com.melitaltd.services.customvalidator.FutureLocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {

    @NotNull(message = "personal information cannot be null")
    private PersonalInformation personalInformation;

    @NotNull(message = "installation address cannot be null")
    @NotEmpty(message = "installation address cannot be empty")
    private String installationAddress;

    @FutureLocalDateTime
    private LocalDateTime installationDateTime;

    @NotNull(message = "products detail cannot be null")
    @NotEmpty(message = "products detail cannot be empty")
    private List<Product> products;
}
