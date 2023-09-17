package com.melitaltd.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "trace_id")
    private String traceId;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private PersonalInformationEntity personalInformation;
    @Column(name = "installation_address")
    private String installationAddress;
    @Column(name = "installation_date_time")
    private LocalDateTime installationDateTime;
    @OneToMany(mappedBy="order")
    private List<ProductEntity> products;
    @Column(name = "approve")
    // 0 pending, 1 yes, 2 No, 3 auto approval
    private int approve=3;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
