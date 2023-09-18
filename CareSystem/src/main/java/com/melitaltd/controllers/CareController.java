package com.melitaltd.controllers;

import com.melitaltd.model.ApprovalRequest;
import com.melitaltd.services.CareService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.melitaltd.StartCareSystemApplication.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/caresystem")
@Validated
@Slf4j
public class CareController {
    private final CareService careService;

    public CareController(CareService careService) {
        this.careService = careService;
    }

    @PatchMapping("/approve-order/{orderId}")
    public ResponseEntity<?> approveOrder(@NotNull @PathVariable Integer orderId,
                                          @NotNull @RequestBody ApprovalRequest approvalRequest) {
        this.careService.approveOrder(orderId, approvalRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getPendingForApprovalOrders(@RequestParam(defaultValue = "-1") int orderType) {
        return ResponseEntity.ok(this.careService.getOrdersByApprovalStatus(orderType));
    }

}
