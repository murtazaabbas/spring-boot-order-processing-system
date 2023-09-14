package com.melitaltd.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonalInformation {
    @NotNull(message = "personal information firstName cannot be null")
    @NotEmpty(message = "personal information firstName cannot be empty")
    private String firstName;
    @NotNull(message = "personal information lastName cannot be null")
    @NotEmpty(message = "personal information lastName cannot be empty")
    private String lastName;
    @NotNull(message = "personal information nationalID cannot be null")
    @NotEmpty(message = "personal information nationalID cannot be empty")
    private String nationalID;
}
