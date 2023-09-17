package com.melitaltd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    @JsonIgnore
    private OrderEntity order;
    @Column(name = "package")
    private ProductPackage productPackage;
}
