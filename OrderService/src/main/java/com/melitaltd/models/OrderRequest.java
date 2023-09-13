package com.melitaltd.models;

import com.melitaltd.services.customvalidator.FutureLocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {

    @NotNull(message = "personal information cannot be null")
    @NotEmpty(message = "personal information cannot be empty")
    private PersonalInformation personalInformation;

    @NotNull(message = "installation address cannot be null")
    @NotEmpty(message = "installation address cannot be empty")
    private String installationAddress;

    @NotEmpty(message = "installation date time cannot be empty")
    @FutureLocalDateTime
    private LocalDateTime installationDateTime;

    @NotNull(message = "products detail cannot be null")
    @NotEmpty(message = "products detail cannot be empty")
    private List<Product> products;
}
