package com.melitaltd.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceErrorResponse {

    /**
     * The description.
     */
    private String description;

    /**
     * The error.
     */
    private String error;

    /**
     * The status.
     */
    private String status;
}
