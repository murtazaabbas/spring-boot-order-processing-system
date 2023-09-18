package com.melitaltd.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "personal_information")
@Data
public class PersonalInformationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(mappedBy = "personalInformation")
    private OrderEntity orderEntity;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "national_id")
    private String nationalID;
}
