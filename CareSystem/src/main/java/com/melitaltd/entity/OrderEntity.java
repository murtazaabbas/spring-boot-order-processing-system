package com.melitaltd.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "personal_information_id" )
    private PersonalInformationEntity personalInformation;
    @Column(name = "installation_address")
    private String installationAddress;
    @Column(name = "installation_date_time",  columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime installationDateTime;
    @OneToMany(targetEntity = ProductEntity.class,cascade=CascadeType.ALL)
    @JoinColumn(name = "order_id",nullable = false)
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
