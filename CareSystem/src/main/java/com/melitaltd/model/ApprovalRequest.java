package com.melitaltd.model;

import lombok.Data;

@Data
public class ApprovalRequest {
    // 0 pending, 1 yes, 2 No, 3 auto approval
    private int approvalStatus;
}
